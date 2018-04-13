<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Upload extends CI_Controller {
	
	public function __construct()
	{
		parent::__construct();
	}
	
	public function uploadFiles()
	{
		$ary = array();
		$sessionId = $_POST["CGISESSID"];
		$ary["sessionId"] = $sessionId;
		
		for($x = 0; $x < 5; $x++){
			$filename = $_FILES["idCard".$x]['name'];
			$filedata = $_FILES["idCard".$x]['tmp_name'];
			if(!$filename){
				continue;
			} else {
				$ary["filename".$x] = $filename;
				$ary["filedata".$x] = "@$filedata";
				//array_push($ary, $filename, "@$filedata");
			}
		}
		
		$ch = curl_init();
		$options = array(
		  CURLOPT_URL => SUPPORT_UPLOAD_FILES_4,
		  CURLOPT_POST => true,
		  CURLOPT_POSTFIELDS => $ary, // 直接給array
		  CURLOPT_RETURNTRANSFER=>true,
		);
		curl_setopt_array($ch, $options);
		$res = curl_exec($ch);
		curl_close($ch);
		$data = json_decode(trim($res), true);
		
		//沒有回應
		if(!$data)
		{
			$this->load->library('LogMgr');
			LogMgr::ErrorLog("[" . date("Y-m-d H:i:s") . "] " . $this->curl->error_string . "\r\n");
			echo ErrHandler::getSysInfo(-98, true);
			die();	
		}
		
		//無法解析token
		if($data["head"]["status"] == 7){
			$msg = array();
			$msg["messagetype"] = "101004";
			$msg["content"] = "用户不存在";
			echo json_encode($msg);
			die();	
		}
		echo $data["body"]["result"];
	}
}