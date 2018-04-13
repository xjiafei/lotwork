<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class MY_Controller extends CI_Controller {
	protected $mApp_info;
	protected $mPlatform;

	/**
	 * this is base controller for all the interface
	 */
	public function __construct()
	{
		parent::__construct();
		$this->load->library('ErrHandler');
		
		
		$app_id = $this->uri->segment(3, 0);
		$this->mApp_info = $this->getAppInfo($app_id);
		$this->mPlatform = $this->getPlatform($app_id);
	        log_message('debug', 'ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss');	
		$json_content = $this->decrypt($_POST['content']);
		$post_content = json_decode(trim($json_content),true);
		
		if($post_content == null && !is_array($post_content))
		{
			echo ErrHandler::getSysInfo(-100, true);
			echo "zzzz";
			echo $_POST['content'];
			die;
		}
		$_POST = $post_content;
		
		if($this->mApp_info != null && $app_id != 7)
		{
			$_POST["app_id"] = $this->mApp_info->app_id;
			$_POST["come_from"] = $this->mApp_info->come_from;
			$_POST["appname"] = $this->mApp_info->push_id;
		}
	}
	
	public function getPlatform($app_id)
	{
		$rtn = 0;
		$platfrom_3 = array("1", "2", "3", "4", "5", "6");
		$platfrom_admin = array("7");
		$platfrom_4 = array("9", "10");
                $platfrom_bm = array("13", "14");
                $platfrom_bm_agent = array("8", "11", "12");
		if(in_array($app_id, $platfrom_3))
		{
			$rtn = PLATFORM_3;
		}
		else if(in_array($app_id, $platfrom_admin))
		{
			$rtn = PLATFORM_ADMIN;
		}
		else if(in_array($app_id, $platfrom_4))
		{
			$rtn = PLATFORM_4;
		}
                else if(in_array($app_id, $platfrom_bm))
		{
			$rtn = PLATFORM_BM;
		}
                else if(in_array($app_id, $platfrom_bm_agent))
		{
			$rtn = PLATFORM_BM_AGENT;
		}
		return $rtn;
	}
	
	public function getAppInfo($app_id)
	{
		// cache app_info
		$this->load->model('Mem_cache_model');
		$app_info = $this->Mem_cache_model->get(MC_APP_INFO . "_" . $app_id);
		
		if($app_info == null)
		{
			$this->load->model('App_info_model');
			$app_info = $this->App_info_model->getAppInfo($app_id);
			$this->Mem_cache_model->save(MC_APP_INFO . "_" . $app_id, $app_info, MC_APP_INFO_CACHE_TIME);
		}
		return $app_info;
	}
	
	public function decrypt($encrypt_string)
	{
		$dataDecode = mcrypt_decrypt(MCRYPT_RIJNDAEL_128, $this->mApp_info->key, pack("H*", $encrypt_string),MCRYPT_MODE_CBC, "0000000000000000");
		$this->load->library('LogMgr');
		LogMgr::UserLog("[" . date("Y-m-d H:i:s") . "] " . $this->uri->uri_string() . " " . $dataDecode . "\n");
		
		return $dataDecode;
	}
	
	public function encrypt($string)
	{
		$dataEncode = bin2hex(mcrypt_encrypt(MCRYPT_RIJNDAEL_128, $this->mApp_info->key, $string, MCRYPT_MODE_CBC, "0000000000000000"));
		LogMgr::UserLog("[" . date("Y-m-d H:i:s") . "] " . $this->uri->uri_string() . " " . $dataEncode . "\n");
		return $dataEncode;
	}
	
	public function echoJsonPost($array)
	{
// 		header("Content-Type: application/json");
		echo $this->encrypt(json_encode($array));
	}
	
	public function checkParams($params, $data)
	{
		$rtn = true;
		foreach ($params as $value) {
			if(!isset($data[$value]))
			{
				$rtn = false;
				break;	
			}
		}
		return $rtn;
	}
	
	public function echoStrPost($str)
	{
		echo $this->encrypt($str);
	}
	
	public function my_post2($url,$post)
	{
		$this->curl->create($url);
		$this->curl->post($post);
		
		// 4.0禁止使用此参数
		//$this->load->library('UtilMgr');
		//$this->curl->http_header( "X-Forwarded-For" , UtilMgr::getRealIP());
		//$this->curl->http_header( "X-Forwarded-For" , $_SERVER["HTTP_X_FORWARDED_FOR"]);
		
		$return = $this->curl->execute();
		
		if(isset($_POST['debug'] ))
		{
			echo "=====DEBUG MODE:===== \r\n";
			echo "URL: $url \r\n";
			echo "Original POST:\r\n";
			var_dump($_POST);
			echo "Response DATA:\r\n";
			var_dump($return);
			echo $this->curl->error_string;
		}
		
		if($getArray)
		{
			return $return;
		}
		if($return)
		{
			$rtn = $this->encrypt($return);
			if($post["userid"] != null && $post["amount"] != null)
			{
				$this->load->model('Migrate_model');
				$mid = 	$this->Migrate_model->insertData($post);
				if($mid)
				{
					$json = $this->decrypt($rtn);
					$update = json_decode(trim($json),true);
					$update["mid"] = $mid;
					$this->Migrate_model->updateData($update);
				}
			}
			echo $rtn;
		}
		else
		{
			$this->echoJsonPost(ErrHandler::getSysInfo(-98));
			die();
		}
		
		// Errors
		$this->curl->error_code; // int
		$this->curl->error_string;
	}
	
	public function my_post($url,$post,$user_params = array(),$getArray = false)
	{
		$post_url = $url;
		$post_content = array();
		if($this->mPlatform == PLATFORM_3)
		{
			$all_post = array();
			$session_id = '';
			$come_from = '';
			
			$all_post['_POST_DATA'] = $post;
			
			if(isset($_POST['CGISESSID']))
			{
				$session_id = $_POST['CGISESSID'];
			}
			if(isset($_POST['come_from']))
			{
				$come_from = $_POST['come_from'];
			}
			
			$post_url .= '&token='.$session_id;
			$post_url .= '&come_from='.$come_from;
			$post_url  = trim($post_url);
			
			$post_content['content'] = json_encode($all_post);
			
		}
		else if($this->mPlatform == PLATFORM_4)
		{
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
				$ary["head"]["sessionId"] = $_POST["CGISESSID"];
			}
			$ary["body"]["param"] = $post;
			$ary["body"]["pager"] = array();
			$ary["body"]["pager"] = array();
			$ary["body"]["pager"]["startNo"] = "";
			$ary["body"]["pager"]["endNo"] = "";
			
			$post_content = json_encode($ary);
		}
		else if($this->mPlatform == PLATFORM_BM_AGENT)
		{
			$post_content = $_POST;
		}
		// Start session (also wipes existing/previous sessions)
		$this->curl->create($post_url);
		
		// Option & Options
// 		$this->curl->option(CURLOPT_BUFFERSIZE, 10);
// 		$this->curl->options(array(CURLOPT_BUFFERSIZE => 10));

		if($this->mPlatform == PLATFORM_4)
		{
	 		$this->curl->option(CURLOPT_HTTPHEADER , array(
	 			"content-length: " . strlen($post_content),
	 			"Content-Type: application/firefrog; charset=utf-8"
	 	    	)
	 		);
	 		
		}
		
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
		
		if(!$return)
		{
			$this->load->library('LogMgr');
			LogMgr::ErrorLog("[" . date("Y-m-d H:i:s") . "] " . $this->curl->error_string . "\r\n");
			$this->echoJsonPost(ErrHandler::getSysInfo(-98));
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
		
		if($this->mPlatform == PLATFORM_4)
		{
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
				|| $act == "cancelTask")
			{
				LogMgr::UserLog("[" . date("Y-m-d H:i:s") . "] " . $this->uri->uri_string() . " return:" . $return . "\n");
			}
		}
		if($getArray)
		{
			return $return;
		}
		if($return)
		{
			echo $this->encrypt($return);
		}
		
		// Errors
		//$this->curl->error_code; // int
		//$this->curl->error_string;
	}
	
	public function getArrayResult($url,$post,$user_params = array())
	{
		$result = $this->my_post($url, $post, $user_params,true);
		
		$result_arr = json_decode($result,true);
		
		return $result_arr;
	}
	
	public function filterWords($str)
	{
		$words = unserialize (FILTER_WORDS);
		$rtn = $str;
			
		for($i = 0;$i < count($words);$i++)
		{
			$rtn = str_replace($words[$i], "熊猫", $rtn);
			
		}
		return $rtn;
	}
        
        public function sessionAgent()
	{
		//$data = array();
		//$data["flag"] = "login";
		//$data["username"] = "app023";
		//$data["loginpass_source"] = "46f94c8de14fb36680850768ff1b7f2a";
		//$data["loginpass"] = "4d3d4e9b019ffb43e0f514f25c5a8159";
                //$json = $this->getArrayResult(FRONT_LOGIN, $data);
		//return $json["token"];
                
                $url = FRONT_LOGIN . "&come_from=3";
        	
		$data = array();
		$data["flag"] = "login";
		$data["username"] = "app023";
		$data["loginpass_source"] = "46f94c8de14fb36680850768ff1b7f2a";
		$data["loginpass"] = "4d3d4e9b019ffb43e0f514f25c5a8159";
		
		$post['_POST_DATA'] = $data;
		$post_content['content'] = json_encode($post);
	
		$this->curl->create($url);
		$this->curl->post($post_content);
		$this->curl->http_header( "X-Forwarded-For" , $_SERVER['REMOTE_ADDR'] );
		$rs = $this->curl->execute();
		
		$json = json_decode(trim($rs),true);
                return $json["token"];
	}
        
        public function sessionAgent2()
	{
		//$data = array();
		//$data["flag"] = "login";
		//$data["username"] = "app023";
		//$data["loginpass_source"] = "46f94c8de14fb36680850768ff1b7f2a";
		//$data["loginpass"] = "4d3d4e9b019ffb43e0f514f25c5a8159";
                //$json = $this->getArrayResult(FRONT_LOGIN, $data);
		//return $json["token"];
                
                $url = FRONT_LOGIN . "&come_from=3";
        	
		$data = array();
		$data["flag"] = "login";
		$data["username"] = "app002";
		$data["loginpass_source"] = "46f94c8de14fb36680850768ff1b7f2a";
		$data["loginpass"] = "4d3d4e9b019ffb43e0f514f25c5a8159";
		
		$post['_POST_DATA'] = $data;
		$post_content['content'] = json_encode($post);
	
		$this->curl->create($url);
		$this->curl->post($post_content);
		$this->curl->http_header( "X-Forwarded-For" , $_SERVER['REMOTE_ADDR'] );
		$rs = $this->curl->execute();
		
		$json = json_decode(trim($rs),true);
                return $json["token"];
	}
        
        public function wrapBM($url, $post = null, $skip = false) 
        {
            $platform = -1;
            $app_id = $this->uri->segment(3, 0);
            switch ($app_id) {
                case 13:
                    $platform = 1;
                    break;
                case 14:
                    $platform = 2;
                    break;
                default:
                    break;
            }
            //$headers = apache_request_headers();
            //if($headers['debug']) header("InnerURL:".$url);
            $this->load->library('BomaoMgr');
            $json = BomaoMgr::my_post($url, $platform, $post);
            if($skip) return $json;
            else echo $this->encrypt($json);
        }
        
        public function responseCustomBM($isSuccess, $data = null) 
        {
            $ary = array(
                'isSuccess' => $isSuccess,
                'type' => $isSuccess == 1 ? 'success' : 'error',
                'data' => $data
            );
            $this->echoJsonPost($ary);
        }
        
        public function responseCustomBMSuccess($data = null) 
        {
            $this->responseCustomBM(1, $data);
        }
        
        public function responseCustomBMError($msg = null, $errno = -1, $data = null) 
        {
            $ary = array(
                'isSuccess' => 0,
                'type' => 'error',
                'Msg' => $msg,
                'errno' => $errno,
                'data' => $data
            );
            $this->echoJsonPost($ary);
        }
}


/* End of file MY_Controller.php */
/* Location: ./application/core/MY_Controller.php */
