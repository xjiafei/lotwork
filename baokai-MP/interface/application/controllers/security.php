<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

/**
 * 文件 : /controller/security.php
 *  频道间转帐， 频道余额,  帐变列表
 */

class Security extends MY_Controller {
    
        public function __construct() {
            parent::__construct();
        }
        
        public function bankToHigh()
	{
		$_POST["flag"] = "doTranfer";
		$_POST["toChannelId"] = "0";
		$this->my_post(SECURITY_BANK_TRANSFER_HIGH, $_POST);
	}
	
	public function highToBank()
	{
		$_POST["flag"] = "doTranfer";
		$_POST["toChannelId"] = "0";
		$this->my_post(SECURITY_HIGH_TRANSFER_BANK, $_POST);
	}
	
	public function bankToLow()
	{
		$_POST["flag"] = "doTranfer";
		$_POST["toChannelId"] = "0";
		$this->my_post(SECURITY_BANK_TRANSFER_LOW, $_POST);
	}
	
	public function lowToBank()
	{
		$_POST["flag"] = "doTranfer";
		$_POST["toChannelId"] = "0";
		$this->my_post(SECURITY_LOW_TRANSFER_BANK, $_POST);
	}
	
	public function checkSecpass()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$_POST["flag"] = "checksecpass";
			$this->my_post(SECURITY_CHECK_SECPASS, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$_POST["secpass"] = md5(md5(md5($_POST["secpass"])));
			$this->my_post(SECURITY_CHECK_SECPASS_4, $_POST);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
			$_POST["flag"] = "checksecpass";
			$this->my_post(SECURITY_CHECK_SECPASS, $_POST);
		}
	}
	
	public function addSecpass()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$_POST["flag"] = "changepass";
			$_POST["changetype"] = "secpass";
			$this->my_post(SECURITY_ADD_SECPASS, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$_POST["newpass2"] = md5(md5($_POST["newpass2"]));
			$_POST["confirmNewpass2"] = md5(md5($_POST["confirmNewpass2"]));
			$this->my_post(SECURITY_ADD_SECPASS_4, $_POST);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
			$this->wrapBM(BM_SECURITY_ADD_SECPASS, $_POST);
		}
	}
	
	public function modifySecpass()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$_POST["flag"] = "changepass";
			$_POST["changetype"] = "secpass";
			
			$this->my_post(SECURITY_MODIFY_SECPASS, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$_POST["oldpass2"] = md5(md5(md5($_POST["oldpass2"])));
			$_POST["newpass2"] = md5(md5($_POST["newpass2"]));
			$_POST["confirmNewpass2"] = md5(md5($_POST["confirmNewpass2"]));
			
			$this->my_post(SECURITY_MODIFY_SECPASS_4, $_POST);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
			$this->wrapBM(BM_SECURITY_MODIFY_SECPASS, $_POST);
		}
	}
	
	public function modifyLoginpass()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$_POST["flag"] = "changepass";
			$_POST["changetype"] = "loginpass";
			
			$this->my_post(SECURITY_MODIFY_LOGINPASS, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			/*
			$_POST["oldpass"] = md5($_POST["oldpass"]);
			$this->my_post(SECURITY_MODIFY_LOGINPASS_4, $_POST);
			*/
			$_POST["oldpass"] = md5(md5(md5($_POST["oldpass"])));
			$_POST["newpass"] = md5(md5($_POST["newpass"]));
			$_POST["confirmNewpass"] = md5(md5($_POST["confirmNewpass"]));
			$this->my_post(SECURITY_MODIFY_LOGINPASS_4, $_POST);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
			$this->wrapBM(BM_SECURITY_MODIFY_LOGINPASS, $_POST);
		}
	}
	
	public function safeQuestInit()
	{
		$ary = $this->getArrayResult(SECURITY_SAFE_QUEST_INIT_4, $_POST);
		$this->echoJsonPost($ary["list"]);
	}
	
	public function safeQuestSet()
	{
		$this->my_post(SECURITY_SAFE_QUEST_SET_4, $_POST);
	}
	
	public function safeQuestVerify()
	{
		$this->my_post(SECURITY_SAFE_QUEST_VERIFY_4, $_POST);
	}
	
	public function safeQuestEdit()
	{
		$this->my_post(SECURITY_SAFE_QUEST_EDIT_4, $_POST);
	}
	
	public function cardBindingInit()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$this->my_post(SECURITY_CARD_BINDING_INIT, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			//$this->my_post(SECURITY_CARD_BINDING_INIT_4, $_POST);
			$ary = $this->getArrayResult(SECURITY_CARD_BINDING_INIT_4, $_POST);
			if(isset($ary["provinceList"]))
			{
				
				$this->load->model('Province_model');
				$rs = $this->Province_model->getProvinceList();
				$ary["provinceList"] = $rs;
			}
			
			$bankList = array();
			foreach($ary["bankList"] as $key => $value)
			{
				$bid = $ary["bankList"][$key]["bankId"];
				if($bid == 1 || $bid == 2
					|| $bid == 3
					|| $bid == 4
					|| $bid == 6
					|| $bid == 7
					|| $bid == 8
					|| $bid == 9){
					array_push($bankList, $ary["bankList"][$key]);
				}
			}
			$ary["bankList"] = $bankList;
			$this->echoJsonPost($ary);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $this->wrapBM(BM_SECURITY_CARD_BINDING_INIT, $_POST);
		}
	}
	
	public function cardBindingConfirm()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$_POST["flag"] = "newbinding";
			$this->my_post(SECURITY_CARD_BINDING_CONFIRM, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(SECURITY_CARD_BINDING_CONFIRM_4, $_POST);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $this->wrapBM(BM_SECURITY_CARD_BINDING_CONFIRM, $_POST);
		}
	}
	
	public function cardBindingCommit()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$_POST["flag"] = "confirm";
			$this->my_post(SECURITY_CARD_BINDING_COMMIT, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$_POST["secpass"] = md5(md5(md5($_POST["secpass"])));
			$this->my_post(SECURITY_CARD_BINDING_COMMIT_4, $_POST);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
			$this->wrapBM(BM_SECURITY_CARD_BINDING_COMMIT, $_POST);
		}
	}
	
	public function cardBindingDelete()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$_POST["flag"] = "delcard";
			$this->my_post(SECURITY_CARD_BINDING_DELETE, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(SECURITY_CARD_BINDING_DELETE_4, $_POST);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $this->wrapBM(BM_SECURITY_CARD_BINDING_DELETE, $_POST);
		}
	}
	
	public function cardList()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$this->my_post(SECURITY_CARD_LIST, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->load->library('StrMgr');
			$rtn = $this->getArrayResult(SECURITY_CARD_LIST_4, $_POST);
			
			foreach($rtn["list"] as $key => $value)
			{
				$rtn["list"][$key]["account"] = StrMgr::toAsterisk($rtn["list"][$key]["account"]);
			}
			$this->echoJsonPost($rtn["list"]);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $this->wrapBM(BM_SECURITY_CARD_LIST, $_POST);
		}
	}
	
	public function cityList()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$_POST["flag"] = "getcitylist";
			$this->my_post(SECURITY_CITY_LIST, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->load->model('City_model');
			$rs = $this->City_model->getCityList($_POST["province"]);
			$this->echoJsonPost($rs);
		}
	}
}

?>