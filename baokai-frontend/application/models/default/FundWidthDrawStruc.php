<?php
class FundWidthDrawStruc extends BaseModel{
	function getTestData(){
		return 'application/testdata/FundWidthDrawStruc.php';
	}
	
	function getDefaultMap(){
		return array(
			array(
				'sn' => '2011120947940291',
				'applyAccount' => '',
				'applyTime' => '1374806596134',
				'withdrawAmt' => '2000',
				'userBankStruc' => array(
						'bankId'=>'1', //银行id
						'bankAccount'=>'张三', //开户人姓名
						'bankNumber'=>'9558822201001927423', //卡号
						'branchName'=>'支行名称1', //支行名称
						'mcBankId' =>'111', //Mc银行ID
// 						'branchAddr' => '****',
						'bindDate' => '1375806596134'
				),
				'ipAddr' => '1374806596134',
				'riskType' => '正常',
				'memo' => 'asdfsfa',
				'mcRemitTime' => '1376806596134',
				'apprAccount' => 'Manager',
				'apprTime' => '1375806596134',
				'status' => '2'
			)
		);
	}
}