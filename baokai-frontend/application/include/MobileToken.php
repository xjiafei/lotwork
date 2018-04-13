<?php
class MobileToken {

	/**
	 * 验证用户序列号是否正确
	 *
	 * @param String $sSn
	 * @return boolean
	 */
	public function verifySN( $sSn ){
		if(strlen($sSn)!=16){
			return FALSE; // 长度错误
		}
	
		//前14码作为基础码，经运算后判断与后2码是否吻合
		$base_code = substr($sSn, 0, 14);
		$verify_code = substr($sSn, 14);
	
		//将此14码中奇数位与偶数位分别加总
		$sum_odd = 0;
		$sum_even = 0;
		for($i=0; $i<strlen($base_code); $i++){
			if($i%2==0){
				$sum_odd += $base_code[$i];
			}else{
				$sum_even += $base_code[$i];
			}
		}
		//===========新添加=================
		if($sum_odd == 0 || $sum_even == 0){
			return FALSE;
		}
		//=========新添加===================
		
		//将奇数与偶数相乘后取倒数，乘上13579，再mod 100取馀数作为验证码，馀数不足10则十位数补0
		$checksum = ((1 / ($sum_odd*$sum_even)) * 13579) % 100;
		if($checksum < 10) {
			$checksum = "0" . $checksum;
		}
// 		echo $checksum;
		if($checksum == $verify_code) {
			return TRUE;
		} else {
			return FALSE;
		}
	}
	
	
	
	/**
	* 验证动态安全码是否正确<br/>
		* <i>注意：需要加载扩展mcrypt</i>
		* @param String $sToken
		* @param String $sSn
		* @return boolean
		*/
		function actionVerifyToken($sToken, $sSn) {
			// return TRUE;
		if (strlen ( $sToken ) != 6) {
			return FALSE;
		}
		$aTime = array ();
		$aTime [0] = date ( "YmdHis" );
		$aTime [1] = date ( "YmdHis", time () + 30 );
		$aTime [2] = date ( "YmdHis", time () + 60 );
		$aTime [3] = date ( "YmdHis", time () - 30 );
		$aTime [4] = date ( "YmdHis", time () - 60 );
		
		$key = md5 ( $sSn );
		$iv_size = ( string ) $sSn;
		for($i = 0; $i < count ( $aTime ); $i ++) {
			$date_YmdHi = substr ( $aTime [$i], 0, 12 );
			$date_s = substr ( $aTime [$i], 12 );
			if ($date_s >= 0 && $date_s <= 29) {
				$data_to_encrypt = $date_YmdHi . "@";
			} else {
				$data_to_encrypt = $date_YmdHi . "#";
			}
			// 以aes128进行加密，再将加密结果从binary转换为16进制
			$dataEncode = bin2hex ( mcrypt_encrypt ( MCRYPT_RIJNDAEL_128, $key, $data_to_encrypt, MCRYPT_MODE_CBC, $iv_size ) );
			$dataEncode = base64_encode ( $dataEncode );
			// 将每个字符转为ascii所得的值加总
			$ascii_sum = "";
			for($k = 0; $k < strlen ( $dataEncode ); $k ++) {
				if ($k != strlen ( $dataEncode ) - 1) {
					ord ( $dataEncode [$k] ) . "($dataEncode[$k]) + ";
				} else {
					ord ( $dataEncode [$k] ) . "($dataEncode[$k])";
				}
				$ascii_sum += ord ( $dataEncode [$k] );
			}
			// 将此总和乘上110503（六位数中最小的质数）后 mod 859433（六位数中最大的质数）作为手机令牌
			$token = ($ascii_sum * 110503 % 859433);
			// 若不足6位数则尾数补0
			for($j = 0; $j < (6 - strlen ( $token )); $j ++) {
				$token .= "0";
			}
			if ($sToken == $token) {
				return TRUE;
			}
		}
		return FALSE;
	}
	
	
	/**
	 * 获取可用动态码<br/>
	 * <i>注意：需要加载扩展mcrypt</i>
	 * @param String $sToken
	 * @param String $sSn
	 * @return boolean
	 */
	function getActionToken($sSn) {
		
		$aTime = array ();
		$aTime [0] = date ( "YmdHis" );
		$aTime [1] = date ( "YmdHis", time () + 30 );
		$aTime [2] = date ( "YmdHis", time () + 60 );
		$aTime [3] = date ( "YmdHis", time () - 30 );
		$aTime [4] = date ( "YmdHis", time () - 60 );
	
		$key = md5 ( $sSn );
		$iv_size = ( string ) $sSn;
		$aToken = array();
		for($i = 0; $i < count ( $aTime ); $i ++) {
			$date_YmdHi = substr ( $aTime [$i], 0, 12 );
			$date_s = substr ( $aTime [$i], 12 );
			if ($date_s >= 0 && $date_s <= 29) {
				$data_to_encrypt = $date_YmdHi . "@";
			} else {
				$data_to_encrypt = $date_YmdHi . "#";
			}
			// 以aes128进行加密，再将加密结果从binary转换为16进制
			$dataEncode = bin2hex ( mcrypt_encrypt ( MCRYPT_RIJNDAEL_128, $key, $data_to_encrypt, MCRYPT_MODE_CBC, $iv_size ) );
			$dataEncode = base64_encode ( $dataEncode );
			// 将每个字符转为ascii所得的值加总
			$ascii_sum = "";
			for($k = 0; $k < strlen ( $dataEncode ); $k ++) {
				if ($k != strlen ( $dataEncode ) - 1) {
					ord ( $dataEncode [$k] ) . "($dataEncode[$k]) + ";
				} else {
					ord ( $dataEncode [$k] ) . "($dataEncode[$k])";
				}
				$ascii_sum += ord ( $dataEncode [$k] );
			}
			// 将此总和乘上110503（六位数中最小的质数）后 mod 859433（六位数中最大的质数）作为手机令牌
			$token = ($ascii_sum * 110503 % 859433);
			// 若不足6位数则尾数补0
			for($j = 0; $j < (6 - strlen ( $token )); $j ++) {
				$token .= "0";
			}
			$aToken[] = $token;
		}
		return $aToken;
	}
}