<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Admin extends MY_Controller {
	
	public function __construct()
	{
		parent::__construct();
	}
	
	public function login()
	{
		$rtn = new stdClass();
		
		$this->load->model('Admin_model');
		$rs = $this->Admin_model->login($_POST["account"], $_POST["password"]);
		
		$rtn->status = $rs;
		
		$this->echoStrPost(json_encode($rtn));
	}
	
	public function getMyAppInfo()
	{
		$rtn = new stdClass();
		
		$this->load->model('App_info_model');
		$app_info = $this->App_info_model->getAdminAppInfo();	
		
		$this->echoStrPost(str_replace('\/','/',json_encode($app_info)));
	}
	
	public function updateAppVersion()
	{
		$rtn = new stdClass();
		$params = array('app_id', 'version', 'must_upgrade', 'download_url', 'msg');
		if(!$this->checkParams($params, $_POST))
		{
			$this->echoErrorData();
			//$this->echoJsonPost(ErrHandler::getSysInfo(-99));
			//die();
		}
	        $this->load->model('App_version_model');
		$rs = $this->App_version_model->updateAppVersion($_POST);
		$rtn->status = $rs>0?1:0;
		$this->echoStrPost(str_replace('\/','/',json_encode($rtn)));
	}
	
	public function getAdInfo()
	{
		$this->load->model('Activity_model');
		//$rs = $this->Activity_model->getAdminActivity($_POST["app_id"]);
		$rs = $this->Activity_model->getAdminActivity();
		$this->echoStrPost(str_replace('\/','/',json_encode($rs)));
	}
	
	public function updateAd()
	{
		$rtn = new stdClass();
		$params = array('act_id', 'act_url', 'img_url', 'start_time', 'end_time', 'enabled');
		
		if(!$this->checkParams($params, $_POST))
		{
			$this->echoErrorData();
			//$this->echoJsonPost(ErrHandler::getSysInfo(-99));
			//die();
		}
	        
	        $this->load->model('Activity_model');
		$rs = $this->Activity_model->updateActivity($_POST);
		$rtn->status = $rs>0?1:0;
		$this->echoStrPost(str_replace('\/','/',json_encode($rtn)));	
	}
	
	public function addAd()
	{
		$rtn = new stdClass();
		$params = array('app_id', 'act_url', 'img_url', 'start_time', 'end_time', 'enabled');
		
		if(!$this->checkParams($params, $_POST))
		{
			$this->echoErrorData();
			//$this->echoJsonPost(ErrHandler::getSysInfo(-99));
			//die();
		}
	        
	        $this->load->model('Activity_model');
		$rs = $this->Activity_model->addActivity($_POST);
		$rtn->status = $rs>0?1:0;
		$this->echoStrPost(str_replace('\/','/',json_encode($rtn)));
	}
	
	public function getDownloadCountList()
	{
		$this->load->model('Counter_model');
		$rs = $this->Counter_model->getDownloadCountList();
		$this->echoStrPost(json_encode($rs));
	}
	
	public function getLoginCountList()
	{
		$this->load->model('Counter_model');
		$rs = $this->Counter_model->getLoginCountList($_POST);
		$this->echoStrPost(json_encode($rs));
	}
	
	public function getOpenCodeList()
	{
		$this->load->model('Opencode_model');
		$rs = $this->Opencode_model->getOpencodeList();
		$this->echoStrPost(json_encode($rs));
	}
	
	public function getPushMsgList()
	{
		$this->load->model('Pushcli_model');
		$rs = $this->Pushcli_model->getPushMsgList($_POST);
		$this->echoStrPost(json_encode($rs));
	}
	
	public function updatePushMsg()
	{
		$rtn = new stdClass();
		$params = array('id', 'pushmsg', 'pushstatus', 'triggertime');
		
		if(!$this->checkParams($params, $_POST))
		{
			$this->echoErrorData();
			//$this->echoJsonPost(ErrHandler::getSysInfo(-99));
			//die();
		}
	        
	        $this->load->model('Pushcli_model');
		$rs = $this->Pushcli_model->updatePushMsg($_POST);
		$rtn->status = $rs>0?1:0;
		$this->echoStrPost(str_replace('\/','/',json_encode($rtn)));	
	}
	
	public function getUserPushInfoList()
	{
		$this->load->model('Push_model');
		$rs = $this->Push_model->getPushInfoList();
		$this->echoStrPost(json_encode($rs));
	}
	
	public function getProjectList()
	{
		$this->load->model('Project_rec_model');
		$rs = $this->Project_rec_model->getProjectList();
		$this->echoStrPost(json_encode($rs));	
	}
	
	public function getDailyReport()
	{
		$this->load->model('Project_rec_model');
		$rs = $this->Project_rec_model->getDailyReport($_POST);
		$this->echoStrPost(json_encode($rs));
	}
	
	public function getMigrateList()
	{
		$this->load->model('Migrate_model');
		$rs = $this->Migrate_model->getMigrateList($_POST);
		$this->echoStrPost(json_encode($rs));
	}
	
	public function getMonthlyDownloadReport()
	{
		$this->load->model('Counter_model');
		$rs = $this->Counter_model->getMonthlyReport($_POST);
		$this->echoStrPost(json_encode($rs));
	}
	
	public function getMonthlyBuyReport()
	{
		$this->load->model('Project_rec_model');
		$rs = $this->Project_rec_model->getMonthlyReport($_POST);
		$this->echoStrPost(json_encode($rs));
	}
	
	public function getMonthlyRechargeReport()
	{
		$this->load->model('Recharge_model');
		$rs = $this->Recharge_model->getMonthlyReport($_POST);
		$this->echoStrPost(json_encode($rs));
	}

	public function getMonthlyMobileReport()
	{
		$this->load->model('Project_rec_model');
		$rs = $this->Project_rec_model->getMonthlyMobileReport($_POST);
		$this->echoStrPost(json_encode($rs));
	}
	
	public function getMonthlyCountReport()
	{
		$this->load->model('Project_rec_model');
		$rs = $this->Project_rec_model->getMonthlyCountReport($_POST);
		$this->echoStrPost(json_encode($rs));
	}
	
	public function getAppDomainActivity()
	{
		$this->load->model('App_domain_model');
		$rs = $this->App_domain_model->getAppDomainActivity();
		$this->echoStrPost(str_replace('\/','/',json_encode($rs)));
	}		
	
	public function updateAppDomain()
	{
		$rtn = new stdClass();
		$params = array('app_id', 'domain');
		if(!$this->checkParams($params, $_POST))
		{
			$this->echoErrorData();
			//$this->echoJsonPost(ErrHandler::getSysInfo(-99));
			//die();
		}
	        $this->load->model('App_domain_model');
		$rs = $this->App_domain_model->updateAppDomain($_POST);
		$rtn->status = $rs>0?1:0;
		$this->echoStrPost(str_replace('\/','/',json_encode($rtn)));
	}
	
	public function getAppDomain() //給information/doRetSetting用的
	{
		$this->load->model('App_domain_model');
		$rs = $this->App_domain_model->getAppDomain($_POST["app_id"]);
		$this->echoStrPost(str_replace('\/','/',json_encode($rs)));
	}	
	
	public function getAppMultiply()
	{
		$this->load->model('App_multiply_model');
		$rs = $this->App_multiply_model->getAppMultiplyActivity();
		$this->echoStrPost(str_replace('\/','/',json_encode($rs)));
	}
	
	public function addAppMultiply()
	{
		$rtn = new stdClass();
		$this->load->model('App_multiply_model');
		$rs = $this->App_multiply_model->addData($_POST);
		$rtn->status = $rs>0?1:0;
		$this->echoStrPost(str_replace('\/','/',json_encode($rtn)));
	}
	
	public function updateAppMultiply()
	{
		$rtn = new stdClass();
		
		$this->load->model('App_multiply_model');
		$rs = $this->App_multiply_model->updateAppMultiply($_POST);
		$rtn->status = $rs>0?1:0;
		$this->echoStrPost(str_replace('\/','/',json_encode($rtn)));
	}
	
	public function getAppMultiplyLog()
	{
		$this->load->model('App_multiply_log_model');
		$rs = $this->App_multiply_log_model->getData();
		$this->echoStrPost(str_replace('\/','/',json_encode($rs)));
	}
	
	public function getAppMultiplyLogin()
	{
		$this->load->model('App_multiply_login_model');
		$rs = $this->App_multiply_login_model->getData();
		$this->echoStrPost(str_replace('\/','/',json_encode($rs)));
	}
	
	public function test()
	{
		//echo phpinfo();	
	}
	
}



/* End of file admin.php */
/* Location: ./application/controllers/admin.php */