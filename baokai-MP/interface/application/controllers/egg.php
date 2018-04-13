<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class egg extends CI_Controller {
	public function index()
	{
		// 活动时间
		$start = date_create('2015-09-04');
		$end = date_create('2015-10-7');
		$now = new DateTime("now");
		
		if($now < $start || $now > $end)
		{
			//return;
		}
		// 只允许移动端
		if(!$this->_is_mobile())
		{
			return;
		}
		$this->load->helper('url');
		
		if(DEVELOPMENT)
		{
			$data['base_url'] = base_url() . "index.php/";
		}else{
			$data['base_url'] = "http://mobile.ios188.com:6060/";
		}
		
		
		$data["come_from"] = $_GET["come_from"];
		$data["device"] = $_GET["device"];
		$data["sid"] = $_GET["sid"];
		$data["uuid"] = $_GET["uuid"];
		$data["userId"] = $_GET["userId"];
		
		$this->load->view('egg/index', $data);
	}
	
	function _is_mobile(){
		$regex_match = "/(nokia|ipad|itouch|ipod|iphone|android|motorola|^mot\-|softbank|foma|docomo|kddi|up\.browser|up\.link|";
		$regex_match .= "htc|dopod|blazer|netfront|helio|hosin|huawei|novarra|CoolPad|webos|techfaith|palmsource|";
		$regex_match .= "blackberry|alcatel|amoi|ktouch|nexian|samsung|^sam\-|s[cg]h|^lge|ericsson|philips|sagem|wellcom|bunjalloo|maui|";
		$regex_match .= "symbian|smartphone|midp|wap|phone|windows ce|iemobile|^spice|^bird|^zte\-|longcos|pantech|gionee|^sie\-|portalmmm|";
		$regex_match .= "jig\s browser|hiptop|^ucweb|^benq|haier|^lct|opera\s*mobi|opera\*mini|320x320|240x320|176x220";
		$regex_match .= ")/i";
		return preg_match($regex_match, strtolower($_SERVER['HTTP_USER_AGENT']));
	}
}