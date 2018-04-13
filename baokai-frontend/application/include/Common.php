<?php

function objectToArray($d) {  
    if (is_object($d)) {  
        $d = get_object_vars($d);  
    }  
    if (is_array($d)) {  
        return array_map(__FUNCTION__, $d);  
    }  
    else {  
        return $d;  
    }  
}  

/**
 * 
 * 获取精确到毫秒维度的时间
 * @return long
 */
function getSendTime(){
	list($s1, $s2) = explode(' ', microtime());
	return (float)sprintf('%.0f', (floatval($s1) + floatval($s2)) * 1000);
}

/**
 * 
 * 补充0作为毫秒数
 * @param long $time
 * @return long
 */
function timetoMicro($time){
//    $diff = ($time<0)?14:13;
    return floatval($time."000");
}
/**
 * 获取查询的结束时间 2012/01/01 -> 2012/01/01 59:59
 * @param date $time
 * @return long
 */
function getQueryEndTime($time){
	return timetoMicro(strtotime($time)+86399);
}

/**
 * 获取查询的开始时间 2012/01/01 -> 2012/01/01
 * @param date $time
 * @return long
 */
function getQueryStartTime($time){
	return timetoMicro(strtotime($time));
}

/**
 * 用户登录密码加密算法
 * @param string $passwd
 * @return string
 */
function encryptLoginPasswd($passwd){
	//md5(md5(md5($passwd)))
	return md5(md5(md5($passwd)));
}

/**
 * 
 * 用户支付密码加密算法
 * @param string $passwd
 */
function encryptWithdrawPasswd($withDrawPasswd){
	//md5(md5(md5($withDrawPasswd)))
	return md5(md5(md5($withDrawPasswd)));
}
/**
 * 登录密码加密比较
 * @param string $passwd1
 * @param string $passwd2
 * @param sring $param
 * @return boolean
 */
function complieLoginPasswd($passwd1,$passwd2,$param){
	//md5(md5(md5($passwd1)))
	if(md5(md5($passwd1).$param) == $passwd2){
		return true;
	} else {
		return false;
	}
}

/*用户输入内容过滤函数*/
function getSecurityInput($str) {
	$tmpstr = trim($str);
	$tmpstr = strip_tags($tmpstr);
	$tmpstr = htmlspecialchars($tmpstr,ENT_QUOTES);
// 	$tmpstr = htmlentities($tmpstr);
	/*加入字符转义*/
	$tmpstr = addslashes($tmpstr);
	return $tmpstr;
}

/**
 * 
 * 去除末尾数毫秒位，能让date函数正常解析时间
 * @param long $time
 * @return long
 */
function getSrtTimeByLong($time){
//    $diff = ($time<0)?11:10;
//    return (int)($time/pow(10,(strlen($time)-$diff)));
    return substr((string)number_format($time,0,'',''),0,-3);
}

function explorerdir($dir,&$patharry)
{
	$dp=opendir($dir); //打开目录句柄
	if(!strchr($dir,".svn")){
		array_push($patharry, $dir);
	}
	while ($file = readdir($dp)) //遍历目录
	{
		if ($file !='.'&&$file !='..') //如果文件不是当前目录及父目录
		{
			$path=$dir."/".$file; //获取路径
			if(is_dir($path)) //如果当前文件为目录
			{
				explorerdir($path,$patharry);   //递归调用
			}
		}
	}
	closedir($dp);    //关闭文件名柄
}
function getExplorerDir($dir){
	$patharry = array();
	$include_path = get_include_path().PATH_SEPARATOR;
	explorerdir($dir,$patharry);
	foreach($patharry as $k1 => $v1){
		$include_path .= $v1.PATH_SEPARATOR;
	}
	unset($patharry);
	return $include_path ;
}

//utf-8环境 汉字3个字符 换算成汉字占两个字符,英文一个字符
function getStrLen($str=''){
	$strlen = (strlen($str) + mb_strlen($str,'UTF8'))/2;
	return $strlen;
}

//中文字符串截取
function getCNSubStr($str, $start=0, $length, $charset="utf-8", $suffix=true){

	if(function_exists("mb_substr"))
		return mb_substr($str, $start, $length, $charset);
	elseif(function_exists('iconv_substr')) {
		$ret = iconv_substr($str,$start,$length,$charset);
		//for iconv_substr:
		//If str is shorter than offset characters long, FALSE will be returned.
		if (empty($ret)){
			$ret = '';
		}
		return $ret;
	}
	$re['utf-8']   = "/[\x01-\x7f]|[\xc2-\xdf][\x80-\xbf]|[\xe0-\xef][\x80-\xbf]{2}|[\xf0-\xff][\x80-\xbf]{3}/";
	$re['gb2312'] = "/[\x01-\x7f]|[\xb0-\xf7][\xa0-\xfe]/";
	$re['gbk']    = "/[\x01-\x7f]|[\x81-\xfe][\x40-\xfe]/";
	$re['big5']   = "/[\x01-\x7f]|[\x81-\xfe]([\x40-\x7e]|\xa1-\xfe])/";
	preg_match_all($re[$charset], $str, $match);
	$slice = join("",array_slice($match[0], $start, $length));
	if($suffix) return $slice."…";
	return $slice;
	
}


//获取 金额小数位 $num 金额数字,$len 小数位长度
function getMoneyFomat($num,$len =''){
	$num = floatval(str_replace(',', '', $num));
	if(empty($len)){
		return number_format($num);
	} else if($len>0) {
		return number_format(substr(sprintf("%.".($len+1)."f", $num), 0, -1),$len);
	}
	
}


function getMoneyFomat_new($num,$len =''){
	
	if(empty($len)){
		return number_format($num);
	} else if($len>0) {
		return number_format(substr(sprintf("%.".($len+1)."f", $num), 0, -1),$len);
	}
	
}
/* 
 * 废弃不用 使用fundcomon公用类中的银行对应信息
 * function getBankById($bankId){
	
	$bankIcoArray = array(
			'1' => array('logo'=>'ICBC','name'=>'工商银行'),
			'2' => array('logo'=>'CMB','name'=>'招商银行'),
			'3' => array('logo'=>'CCB','name'=>'建设银行'),
			'4' => array('logo'=>'ABC','name'=>'农业银行'),
			'5' => array('logo'=>'BOC','name'=>'中国银行'),
			'6' => array('logo'=>'COMM','name'=>'交通银行'),
			'7' => array('logo'=>'CMBC','name'=>'民生银行'),
			'8' => array('logo'=>'CITIC','name'=>'中信银行'),
			'9' => array('logo'=>'SPDB','name'=>'浦发银行'),
			'10' => array('logo'=>'PSBC','name'=>'邮政储蓄银行'),
			'11' => array('logo'=>'CEB','name'=>'光大银行'),
			'12' => array('logo'=>'SPABANK','name'=>'平安银行'),
			'13' => array('logo'=>'GDB','name'=>'广发银行'),
			'14' => array('logo'=>'HXBANK','name'=>'华夏银行'),
			'15' => array('logo'=>'CIB','name'=>'兴业银行')
	);
	
	return $bankIcoArray[$bankId];
	
} */

function toExcel($objPHPExcel,$sheet){
	$objPHPExcel->getProperties()->setCreator("firefog")
	->setLastModifiedBy("firefog")
	->setTitle($sheet)
	->setSubject($sheet)
	->setDescription($sheet)
	->setKeywords("office 2007 openxml php")
	->setCategory("Test result file");
	// Add some data
	
	// Rename worksheet
	$objPHPExcel->getActiveSheet()->setTitle($sheet);
	// Set active sheet index to the first sheet, so Excel opens this as the first sheet
	$objPHPExcel->setActiveSheetIndex(0);
	// Redirect output to a client’s web browser (Excel2007)
	header('Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
	header('Content-Disposition: attachment;filename="'.$sheet.'.xlsx"');
	header('Cache-Control: max-age=0');
	$objWriter = PHPExcel_IOFactory::createWriter($objPHPExcel, 'Excel2007');
	$objWriter->save('php://output');
	exit;
}

/**
 * 计算上一个月的今天，如果上个月没有今天，则返回上一个月的最后一天
 * @param type $time
 * @return type
 */
//废弃掉不用,PHP中有专门获取的函数
/* function last_month_today($time){
	$last_month_time = mktime(date("G", $time), date("i", $time),
			date("s", $time), date("n", $time), 0, date("Y", $time));
	$last_month_t =  date("t", $last_month_time);

	if ($last_month_t < date("j", $time)) {
		return date("Y-m-t H:i:s", $last_month_time);
	}

	return date(date("Y-m", $last_month_time) . "-d", $time);
} */

function get_client_ip(){
	$realip = '0.0.0.0';
    if (isset($_SERVER)){
        if (isset($_SERVER["HTTP_X_FORWARDED_FOR"])){
        	$aHTTP_X_FORWARDED_FOR = explode(',', $_SERVER["HTTP_X_FORWARDED_FOR"]);
	    	if(count($aHTTP_X_FORWARDED_FOR) > 1){
	    		while ($ip = array_pop($aHTTP_X_FORWARDED_FOR)){
	    			if(!isInnerIP($ip)){
	    				$realip = $ip;
	    			}
	    		}
	    		
	    	}
	    	if($realip == '0.0.0.0'){
	    		$realip = array_shift($aHTTP_X_FORWARDED_FOR);
	    	}
        } else if (isset($_SERVER["HTTP_CLIENT_IP"])) {
            $realip = $_SERVER["HTTP_CLIENT_IP"];
        } else {
            $realip = $_SERVER["REMOTE_ADDR"];
        }
    } else {
        if (getenv("HTTP_X_FORWARDED_FOR")){
        	$aHTTP_X_FORWARDED_FOR = array_shift(explode(',', getenv("HTTP_X_FORWARDED_FOR")));
        	if(count($aHTTP_X_FORWARDED_FOR) > 1){
	    		while ($ip = array_pop($aHTTP_X_FORWARDED_FOR)){
	    			if(!isInnerIP($ip)){
	    				$realip = $ip;
	    			}
	    		}
	    		
	    	}
	    	if($realip == '0.0.0.0'){
	    		$realip = array_shift($aHTTP_X_FORWARDED_FOR);
	    	}
        } else if (getenv("HTTP_CLIENT_IP")) {
            $realip = getenv("HTTP_CLIENT_IP");
        } else {
            $realip = getenv("REMOTE_ADDR");
        }
    }
    return $realip;
}

/**
 * 判断Ip是否为内网IP地址
   私有IP：A类  10.0.0.0-10.255.255.255
 B类  172.16.0.0-172.31.255.255
 C类  192.168.0.0-192.168.255.255
   当然，还有127这个网段是环回地址
 **/
function isInnerIP($ipAddress){
	$isInnerIp = false;
	$aBegin = "10.0.0.0";
	$aEnd = "10.255.255.255";
	$bBegin = "172.16.0.0";
	$bEnd = "172.31.255.255";
	$cBegin = "192.168.0.0";
	$cEnd = "192.168.255.255";
	$isInnerIp = isInner($ipAddress,$aBegin,$aEnd) || isInner($ipAddress,$bBegin,$bEnd) || isInner($ipAddress,$cBegin,$cEnd) || $ipAddress =="127.0.0.1";
	return $isInnerIp;
}

function isInner($userIp,$begin,$end){
	$userIp = bindec(decbin(ip2long($userIp)));
	$begin = bindec(decbin(ip2long($begin)));
	$end = bindec(decbin(ip2long($end)));
	return ($userIp>=$begin) && ($userIp<=$end);
}


function isInnerCIDR($userIp, $begin, $end){
	$userIp = bindec(decbin(ip2long($userIp)));
	$begin = bindec(decbin(ip2long($begin)));
	if ($begin == 0){
		return false;
	}
	$end = bindec(decbin(ip2long($end)));
	if ($end == 0){
		$end = $begin;
	}
	return ($userIp>=$begin) && ($userIp<=$end);
}

//获取游戏域名
function getGameDomain($host){
	$domain = getdomain($host);
	$parse = @parse_url ( $host );
	if(isset($parse['port']) && $parse['port']!='80'){
		$domain = $domain.':'.$parse['port'];
	}
	$domain = getHttpUrl('em.'.$domain);
	return $domain;
}

//获取彩票域名
function getLoterryGameDomain($host){
	$domain = getdomain($host);
	$parse = @parse_url ( $host );
	if(isset($parse['port']) && $parse['port']!='80'){
		$domain = $domain.':'.$parse['port'];
	}
	$domain = getHttpUrl('www.'.$domain);
	return $domain;
}

//获取IM域名
function getFrontImDomain($host){
	$domain = getdomain($host);
	$parse = @parse_url ( $host );
        $domain = $domain.':2888';
	$domain = getHttpUrl('www.'.$domain);
	return $domain;
}

//获取后台域名
function getAdminDomain($host){
	$domain = getdomain($host);
	$parse = @parse_url ( $host );
	if(isset($parse['port']) && $parse['port']!='80'){
		$domain = $domain.':'.$parse['port'];
	}
	$domain = getHttpUrl('admin.'.$domain);
	return $domain;
}

//获取pt域名
function getPtAdminDomain($host){
	$domain = getdomain($host);
	$parse = @parse_url ( $host );
	if(isset($parse['port']) && $parse['port']!='80'){
		$domain = $domain.':'.$parse['port'];
	}
	$domain = getHttpUrl('ptadmin.'.$domain);
	return $domain;
}

//获取wg域名
function getWgDomain($host){
	$domain = getdomain($host);
	$parse = @parse_url ( $host );
	if(isset($parse['port']) && $parse['port']!='80'){
		$domain = $domain.':'.$parse['port'];
	}
	$domain = getHttpUrl('mk2.'.$domain);
	return $domain;
}

//获取pt域名
function getPtGameDomain($host){
	$domain = getdomain($host);
	$parse = @parse_url ( $host );
	if(isset($parse['port']) && $parse['port']!='80'){
		$domain = $domain.':'.$parse['port'];
	}
	$domain = getHttpUrl('pt.'.$domain);
	return $domain;
}

//获取域名
function getdomain($host){
	$host = strtolower ( $host );
	// 	if (strpos ( $host, '/' ) !== false) {
	$parse = @parse_url ( $host );
	$host = isset($parse ['host']) ? $parse ['host']: $parse ['path'];
	// 	}
	$topleveldomaindb=array('com','edu','gov','int','mil','net','org','biz','info','pro','name','museum','coop','aero','xxx','idv','mobi','cc','me','ph','cn','tw','net.cn','life','game');
	$str='';
	foreach ( $topleveldomaindb as $v ) {
		$str .= ($str ? '|' : '') . $v;
	}
	$matchstr = "[^\.]+\.(?:(" . $str . ")|\w{2}|((" . $str . ")\.\w{2}))$";
	if (preg_match ( "/" . $matchstr . "/ies", $host, $matchs )) {
		$domain = '' . $matchs ['0'];
	} else {
		$domain = $host;
	}
	return $domain;
}

//添加http头
function getHttpUrl($domain){
	if (!preg_match("/^(http|https):/", $domain)) {
		$domain = 'http://'.$domain;
	}
	return $domain;
}

//判读是否ajax请求
function isAjaxRequest(){
	if(isset($_SERVER['HTTP_X_REQUESTED_WITH']) && strtolower($_SERVER['HTTP_X_REQUESTED_WITH']) ==  'xmlhttprequest'){
		return true;
	}
	return false;
}

//获取网站域名
function getWebSiteDomain(){
	return  $_SERVER['HTTP_HOST'];
	/* $aUrl = parse_url($_SERVER['HTTP_REFERER']);
	$url = $aUrl['host'];
	if($aUrl['port']!='80'){
		$url .=':'.$aUrl['port'];
	}
	return $url; */
}


/**
 * 数字转换为中文
* @param  string|integer|float  $num  目标数字
* @param  integer $mode 模式[true:金额（默认）,false:普通数字表示]
* @param  boolean $sim 使用小写（默认）
* @return string
*/
function number2chinese($num,$mode = true,$sim = true){
	if(!is_numeric($num)) return '含有非数字非小数点字符！';
	$num = floatval($num);
	$char    = $sim ? array('','一','二','三','四','五','六','七','八','九') : array('零','壹','贰','叁','肆','伍','陆','柒','捌','玖');
	$unit    = $sim ? array('','十','百','千','','万','亿','兆') : array('','拾','佰','仟','','萬','億','兆');
	$retval = '';
	//$retval  = $mode ? '元':'点';

	//小数部分
	if(strpos($num, '.')){
		list($num,$dec) = explode('.', $num);
		$dec = strval(round($dec,2));
		if($mode){
			$retval .= "{$char[$dec['0']]}角{$char[$dec['1']]}分";
		}else{
			for($i = 0,$c = strlen($dec);$i < $c;$i++) {
				$retval .= $char[$dec[$i]];
			}
		}
	}
	//整数部分
	$str = $mode ? strrev(intval($num)) : strrev($num);
	for($i = 0,$c = strlen($str);$i < $c;$i++) {
		if($num>=10 && $num<20 && $i==1){
			$out[$i] = '';
		} else {
			$out[$i] = $char[$str[$i]];
		}
		if($mode){
			$out[$i] .= $str[$i] != '0'? $unit[$i%4] : '';
			if($i>1 and $str[$i]+$str[$i-1] == 0){
				$out[$i] = '';
			}
			if($i%4 == 0){
				$out[$i] .= $unit[4+floor($i/4)];
			}
		}
	}
	$retval = join('',array_reverse($out)) . $retval;
	return $retval;
}

function send_http_req($url , $post_data="")
{
	//初始化
	$ch = curl_init($url);
	
	//設定選項
	curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 5);
	curl_setopt($ch, CURLOPT_TIMEOUT, 15);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
	curl_setopt($ch , CURLOPT_HTTPHEADER , array(
		"Connection: close",
	));
	if($post_data != "")
	{
		if(is_array($post_data))	//post_data若是陣列，則組成 http query string 
		{
			$post_data = http_build_query($post_data);
		}
		curl_setopt($ch, CURLOPT_POST, 1);
		curl_setopt($ch, CURLOPT_POSTFIELDS, $post_data);
	}
	
	//執行（送出 Request 並接收資料）
	$output = curl_exec($ch);
	$info_ary = curl_getinfo($ch);
	//echo "HTTP 狀態碼：" . $info_ary["http_code"];
	if ($output === false)
	{
		return false;
	}
	//關閉
	curl_close($ch);
	return $output;
}

//隨機取得陣列中一個元素的值
function rand_ary($ary)
{
	$k = array_rand($ary);	//隨機返回一個key
	return $ary[$k];
}

//二進位轉十六進位(大寫)
function to_hex($data)
{
	return strtoupper(bin2hex($data));
}

//產生 32 個字元的獨一無二字串
function uniqToken()
{
	mt_srand((double)microtime()*1000000);
	$token = md5(uniqid(mt_rand(), true));        //參數2設為true，則不重複的效果會更好
	return $token;
}


function netMatch($IP, $CIDR) 
{  
    list ($net, $mask) = explode('/', $CIDR);  
	return ( ip2long($IP) & ~((1 << (32 - $mask)) - 1) ) == ip2long($net);  
}  

?>