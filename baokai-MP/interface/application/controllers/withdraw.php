<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Withdraw extends MY_Controller {
	
	public function __construct()
	{
		parent::__construct();
	}
	
	public function init()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$this->my_post(WITHDRAW_INIT, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			//$this->my_post(WITHDRAW_INIT_4, $_POST);
			$this->load->library('StrMgr');
			$rtn = $this->getArrayResult(WITHDRAW_INIT_4, $_POST);
			
			
			foreach($rtn["banks"] as $key => $value)
			{
				$rtn["banks"][$key]["account"] = StrMgr::toAsterisk($rtn["banks"][$key]["account"]);
			}
			
			
			
			$this->echoJsonPost($rtn);
			
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                    $this->wrapBM(BM_WITHDRAW_INIT, $_POST);
		}
	}
	
	public function verify()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$_POST["flag"] = "withdraw";
			$_POST["check"] = "";
			$_POST["submit"] = "下一步";
			$this->my_post(WITHDRAW_VERIFY, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			//$this->my_post(WITHDRAW_VERIFY_4, $_POST);
			
			$rtn = $this->getArrayResult(WITHDRAW_VERIFY_4, $_POST);
			$this->load->library('StrMgr');
			if($rtn["datas"]["cardid"])
			{
				$rtn["datas"]["cardid"] = StrMgr::toAsterisk($rtn["datas"]["cardid"]);
			}
			$this->echoJsonPost($rtn);
		}
	}

	public function commit()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$_POST["flag"] = "confirm";
			$_POST["check"] = "";
			$_POST["submit"] = "下一步";
			$this->my_post(WITHDRAW_COMMIT, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$_POST["secpwd"] = md5(md5(md5($_POST["secpwd"])));
			
			$_POST["questionpwd"] = md5($_POST["questionpwd"]);
			
			
			$this->load->library('UtilMgr');
			$_POST["ipAddr"] = UtilMgr::ipToLong(UtilMgr::getRealIP());
			$this->my_post(WITHDRAW_COMMIT_4, $_POST);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                    $this->wrapBM(BM_WITHDRAW_COMMIT, $_POST);
		}
	}
}