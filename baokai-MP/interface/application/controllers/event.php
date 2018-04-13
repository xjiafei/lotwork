<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Event extends CI_Controller {
	
	public function __construct()
	{
		parent::__construct();
	}
	
	public function doEggAct()
	{
		/*
		$res['msg'] = 0;
		$res['isSuccess'] = 1; 
		$res['prize'] = 0.4; //中奖项
		$res['hisPrize'] = 0.3;
		echo json_encode($res);exit;
		*/
		// http://10.3.7.168:8080/mobileapi/index.php/Event/doEggAct/9?come_from=4&device=2&sid=3%25%2FJzBZK04H1v2dIW9VYtB9b1jLtjQ%2BVwzPECXkh5AkKjJw1mmY75Fw%3D%3D&uuid=xxxxxxxxxxxxxxxxxxx1&userId=776&prize=0.2
		
		$prize = array(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0);
		$rtn = new stdClass();
		$rtn->isSuccess = 1;	//判断请求是否成功
		$rtn->step = 1;
		
		$_POST["prize"] = 0;
		$sid = $_POST["sid"];
		$sid = str_replace("-", "/", $sid);
		$sid = str_replace("$", "%", $sid);
		$sid = str_replace("!", "=", $sid);
		$sid = str_replace("*", "+", $sid);
		//$_POST["CGISESSID"] = urldecode($_POST["sid"]);
		$_POST["CGISESSID"] = $sid;
		//$_POST["system"] = "2";
		
		$_POST["system"] = $_POST["device"];
		$today = date("Y-m-d");
		if(($_POST["userId"] == null || $_POST["userId"] == "") 
			|| ($_POST["uuid"] == null || $_POST["uuid"] == "")
			 || ($_POST["CGISESSID"] == null || $_POST["CGISESSID"] == ""))
		{
			$rtn->isSuccess = 0;
			$rtn->step = 2;
			echo json_encode($rtn);
			return;
		}
		$this->load->model('event_egg_model');
		
		if($this->event_egg_model->IsTodayGetPrize($_POST["userId"], $today))
		{
			// 已经领过
			$rtn->msg = 0;
			// 取得奖金值
			$egg = $this->event_egg_model->getEggPrize($_POST["userId"], $today);
			$rtn->hisPrize = $egg["prize"];
			$rtn->prize = $egg["prize"];
			$rtn->step = 3;
		}else{
			// 检查 uuid 今天有没有用过
			if(!$this->event_egg_model->IsUuidExisted($_POST["uuid"], $today))
			{
				// 取得奖金值
				$egg = $this->event_egg_model->getEggPrize($_POST["userId"], $today);
				if($egg == null)
				{
					// 没取到则新增一笔
					$this->event_egg_model->addEggPrize($_POST["userId"], $_POST["uuid"], $prize[rand(0,9)], $_POST["device"], $today);
					$egg = $this->event_egg_model->getEggPrize($_POST["userId"], $today);
					$rtn->step = 4;
				}
				
				// 未取得奖金
				if($egg["status"] == 0)
				{
					$rtn->step = 5;
					$_POST["prize"] = $egg["prize"];
					//$_POST["prize"] = 20;
					//$rtn->post = $_POST;
					$qq = $this->getArrayResult(EVENT_DO_EGG_ACT_4, $_POST);
					//$rtn->qq = $qq;
					
					if($qq["status"] == "success" || $qq["messagetype"] == "109987")
					{
						$rtn->step = 6;
						$rtn->hisPrize = $egg["prize"];
						$rtn->prize = $egg["prize"];
						$rs = $this->event_egg_model->logEggPrize($egg["egg_id"]);
					}else
					{
						$rtn->step = 7;
						$rtn->hisPrize = 0;
						$rtn->prize = 0;
					}
					
					$rtn->msg = 1;
				}else{
					$rtn->step = 8;
					$rtn->msg = 0;
					$rtn->hisPrize = $egg["prize"];
					$rtn->prize = $egg["prize"];
				}
			}else{
				$rtn->step = 9;
				$rtn->msg = -1;
				$rtn->hisPrize = 0;
				$rtn->prize = 0;
			}
		}
		echo json_encode($rtn);
	}
	
	public function doGuaguacardAct()
	{
		$rtn = new stdClass();
		$rtn->isSuccess = 1;	//判断请求是否成功
		$rtn->step = 1;
		$rtn->prize = 0;
		
		$sid = $_POST["sid"];
		$sid = str_replace("-", "/", $sid);
		$sid = str_replace("$", "%", $sid);
		$sid = str_replace("!", "=", $sid);
		$sid = str_replace("*", "+", $sid);
		$_POST["CGISESSID"] = $sid;
		$_POST["system"] = $_POST["device"];
		
		if(($_POST["userId"] == null || $_POST["userId"] == "") 
			|| ($_POST["uuid"] == null || $_POST["uuid"] == "")
			 || ($_POST["CGISESSID"] == null || $_POST["CGISESSID"] == ""))
		{
			$rtn->isSuccess = 0;
			$rtn->step = 2;
			echo json_encode($rtn);
			return;
		}
		
		$user_id = $_POST["userId"];
		$lv = $_POST["lv"];
		$this->load->model('event_guaguacard_prize');
		
		$rs = $this->event_guaguacard_prize->getLvPrize($user_id, $lv);
		if($rs != null){
			if($rs["status"] == 0){
				$_POST["prize"] = $rs["prize"];
				$qq = $this->getArrayResult(EVENT_DO_GUAGUACARD_ACT_4, $_POST);
				if($qq["status"] == "success" || $qq["messagetype"] == "109987")
				{
					$rtn->step = 3;
					$rtn->prize = $rs["prize"];
					$this->event_guaguacard_prize->updateLvPrize($user_id, $lv, 1);
				}else{
					$rtn->step = 4;
				}
			}
			$rtn->step = 5;
		}else{
			$rtn->step = 6;
		}
		echo json_encode($rtn);
	}
	
	public function getArrayResult($url,$post,$user_params = array())
	{
		$result = $this->event_post($url, $post, $user_params,true);
		$result_arr = json_decode($result,true);
		return $result_arr;
	}
	
	public function event_post($url,$post,$user_params = array(),$getArray = false)
	{
		$this->load->library('LogMgr');
		$this->load->library('ErrHandler');
		$post_url = $url;
		$post_content = array();
		
		$ary = array();
		$ary["head"] = array();
		$ary["head"]["sowner"] = "";
		$ary["head"]["rowner"] = "";
		$ary["head"]["msn"] = "";
		$ary["head"]["msnsn"] = "";
		$ary["head"]["userId"] = "";
		$ary["head"]["userAccount"] = "";
		if($_POST["CGISESSID"] != null)
		{
			$ary["head"]["sessionId"] = str_replace(' ', '+', $_POST["CGISESSID"]);
		}
		$ary["body"]["param"] = $post;
		$ary["body"]["pager"] = array();
		$ary["body"]["pager"] = array();
		$ary["body"]["pager"]["startNo"] = "";
		$ary["body"]["pager"]["endNo"] = "";
		
		$post_content = str_replace('\/','/',json_encode($ary));
		//var_dump($post_content);
		
		// Start session (also wipes existing/previous sessions)
		$this->curl->create($post_url);
		
		// Option & Options
// 		$this->curl->option(CURLOPT_BUFFERSIZE, 10);
// 		$this->curl->options(array(CURLOPT_BUFFERSIZE => 10));

 		$this->curl->option(CURLOPT_HTTPHEADER , array(
 			"content-length: " . strlen($post_content),
 			"Content-Type: application/firefrog; charset=utf-8"
 	    	)
 		);
		
		// 4.0禁止使用此参数
		//$this->load->library('UtilMgr');
		//$this->curl->http_header( "X-Forwarded-For" , UtilMgr::getRealIP());
		//$this->curl->http_header( "X-Forwarded-For" , $_SERVER["HTTP_X_FORWARDED_FOR"]);
		
		// More human looking options
// 		$this->curl->option('buffersize', 10);
		
		// Login to HTTP user authentication
// 		$this->curl->http_login('username', 'password');
		
		// Post - If you do not use post, it will just run a GET request
		$this->curl->post($post_content);
		
		// Cookies - If you do not use post, it will just run a GET request
// 		$this->curl->set_cookies($vars);
		
		// Proxy - Request the page through a proxy server
		// Port is optional, defaults to 80
// 		$this->curl->proxy($url);
		
		// Proxy login
// 		$this->curl->proxy_login('username', 'password');
		
		// Execute - returns responce
	 	//header("Content-Type: application/firefrog");
		
		$return = $this->curl->execute();
		
		//var_dump($return);

		if(!$return)
		{
			LogMgr::ErrorLog("[" . date("Y-m-d H:i:s") . "] " . $this->curl->error_string . "\r\n");
			echo ErrHandler::getSysInfo(-98);
			die();	
		}
		
		if(isset($_POST['debug'] ))
		{
			echo "=====DEBUG MODE:===== \r\n";
			echo "URL: $post_url \r\n";
			echo "Original POST:\r\n";
			var_dump($_POST);
			echo "JSON DATA:\r\n";
			var_dump($post_content);
			echo "Response DATA:\r\n";
			var_dump($return);
			echo $this->curl->error_string;
		}
		
		$tmp = json_decode(trim($return),true);
		
		if($tmp["body"]["result"] == null 
		|| (array_key_exists("status", $tmp["body"]["result"]) && $tmp["body"]["result"]["status"] == null)
		|| (array_key_exists("orderId", $tmp["body"]["result"]) && $tmp["body"]["result"]["orderId"] == null && $tmp["body"]["result"]["gameOrderDTO"] == null && $tmp["body"]["result"]["gameOrderResponseDTO"] == null && $tmp["body"]["result"]["overMutipleDTO"] == null)
		|| (array_key_exists("gamePlanId", $tmp["body"]["result"]) && $tmp["body"]["result"]["gamePlanId"] == null && $tmp["body"]["result"]["gameOrderResponseDTO"] == null && $tmp["body"]["result"]["overMutipleDTO"] == null)
		)
		//if($tmp["head"]["status"] != 0)
		{
			$return = ErrHandler::getErrInfo($this->mPlatform, $tmp["head"]["status"], true);
		}
		else
		{
			$return = json_encode($tmp["body"]["result"]);
		}
		$act = $this->uri->segment(2, 0);
		if($act == "login" 
			|| $act == "buy"
			|| $act == "cancelGame"
			|| $act == "cancelTask"
			|| $act == "doEggAct"
			|| $act == "doGuaguacardAct")
		{
			LogMgr::UserLog("[" . date("Y-m-d H:i:s") . "] " . $this->uri->uri_string() . " return:" . $return . "\n");
		}
		
		if($getArray)
		{
			return $return;
		}
		if($return)
		{
			echo $return;
		}
		
		// Errors
		//$this->curl->error_code; // int
		//$this->curl->error_string;
	}
}