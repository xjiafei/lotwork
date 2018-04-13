<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Front extends MY_Controller {

	/**
	 * Index Page for this controller.
	 *
	 * Maps to the following URL
	 * 		http://example.com/index.php/welcome
	 *	- or -  
	 * 		http://example.com/index.php/welcome/index
	 *	- or -
	 * Since this controller is set as the default controller in 
	 * config/routes.php, it's displayed at http://example.com/
	 *
	 * So any other public methods not prefixed with an underscore will
	 * map to /index.php/welcome/<method_name>
	 * @see http://codeigniter.com/user_guide/general/urls.html
	 */
	public function __construct()
	{
		parent::__construct();
	}
	
	public function login()
	{
$this->load->library('LogMgr');
		LogMgr::ErrorLog("testttttttttttttttttttttt");
		if($this->mPlatform == PLATFORM_3)
		{
			$_POST["flag"] = "login";
			//$this->my_post(FRONT_LOGIN, $_POST);
			
			$json = $this->getArrayResult(FRONT_LOGIN, $_POST);
			
			if($json["userid"] && $json["istester"] == "0")
			{
				$rtn = new stdClass();
				$this->load->model('Counter_model');
				$rs = $this->Counter_model->addLoginCount($this->mApp_info->app_id, $json["userid"], $this->mApp_info->version);
			}
			$this->echoJsonPost($json);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->load->library('UtilMgr');
			$_POST["loginIp"] = UtilMgr::ipToLong(UtilMgr::getRealIP());
			$_POST["username"] = strtolower($_POST["username"]);
			$_POST["loginpassSource"] = md5(md5($_POST["loginpassSource"]));
			
			$_POST["device"] = $this->mApp_info->come_from==3?2:4;
			
			$json = $this->getArrayResult(FRONT_LOGIN_4, $_POST);
			
			//if(!array_key_exists("source", $json))
			//{
			//	$json["source"] = "3.0";
			//}
			$json["source"] = "4.0";
			if($json["userid"])
			{
				$rtn = new stdClass();
				$this->load->model('Counter_model');
				$rs = $this->Counter_model->addLoginCount($this->mApp_info->app_id, $json["userid"], $this->mApp_info->version);
				
				if($_POST["appCode"] != null && $_POST["uuid"] && $json["username"] != null)
				{
					$this->load->model('App_multiply_login_model');
					
					
					if($this->App_multiply_login_model->IsDataExisted($_POST["appCode"], $_POST["uuid"], $json["username"]))
					{
						$this->App_multiply_login_model->updateCount($_POST["appCode"], $_POST["uuid"], $json["username"]);
					}else{
						$this->App_multiply_login_model->addData($_POST["appCode"], $_POST["uuid"], $json["username"]);
					}
				}
			}
			
			$this->echoJsonPost($json);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $this->wrapBM(BM_FRONT_LOGIN, $_POST);
		}
		else if($this->mPlatform == PLATFORM_BM_AGENT)
		{
			$json = $this->getArrayResult(FRONT_LOGIN_BM_AGENT, $_POST);
			$this->echoJsonPost($json);
		}
	}
	
	public function logout()
	{
		$_POST["flag"] = "logout";
		$this->my_post(FRONT_LOGOUT, $_POST);
	}
	
	
}
