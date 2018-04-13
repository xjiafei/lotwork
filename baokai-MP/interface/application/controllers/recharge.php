<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Recharge extends MY_Controller {
	
	
	public function __construct()
	{
		parent::__construct();
	}
	
	public function init()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$this->my_post(RECHARGE_INIT, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(RECHARGE_INIT_4, $_POST);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                    $this->wrapBM(BM_RECHARGE_INIT, $_POST);
		}
	}
	
	public function confirm()
	{
		if($this->mPlatform == PLATFORM_3)
		{
			$this->my_post(RECHARGE_CONFIRM, $_POST);
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			$_POST["secpwd"] = md5(md5(md5($_POST["secpwd"])));
			$this->my_post(RECHARGE_CONFIRM_4, $_POST);
		}
                if($this->mPlatform == PLATFORM_BM)
		{
                        $ary = array(
                            BM_SESSION_KEY => $_POST[BM_SESSION_KEY],
                            "deposit_mode" => 1,
                            "bank" => $_POST['bank'],
                            "amount" => $_POST['amount']
                        );
			$this->wrapBM(BM_RECHARGE_CONFIRM, $ary);
		}
	}
	
	public function quickInit()
	{
		//2:招商銀行, 3:建设银行, 30:支付寶, 51:银联充值
		$bankArray = array(-1); //-1屏蔽
		$netbankArray = array(-1); //-1屏蔽
                $mobilebankArray = array(30,35,40);
		if($this->mPlatform == PLATFORM_4)
		{
			$ary = $this->getArrayResult(RECHARGE_QUICK_INIT_4, $_POST);
			$rtn = array();
//            echo $ary["isvip"];
//			die(print_r($ary));
			foreach($ary["list"] as $key => $value)
			{
				if((in_array($value["bankId"], $bankArray) && $value["other"]==1) ||(in_array($value["bankId"], $netbankArray) && $value["other"]==0) 
					|| in_array($value["bankId"], $mobilebankArray))
				{
                    if($ary["isvip"] == 0)
                    {
                        $ary["list"][$key]["min"] = $value["min"];
                        $ary["list"][$key]["max"] = $value["max"];
                    }
                    else
                    {
                        $ary["list"][$key]["min"] = $value["vipMin"];
                        $ary["list"][$key]["max"] = $value["vipMax"];
                    }
					
					array_push($rtn, $ary["list"][$key]);
				}
			}
			
			$this->echoJsonPost($rtn);
		}
	}
	
	public function quickCommit()
	{
		if($this->mPlatform == PLATFORM_4)
		{
			$_POST["system"] = $this->mApp_info->come_from==3?1:2;
			$_POST["version"] =  $this->mApp_info->version;
	                $this->load->library('UtilMgr'); 
			$_POST["customerIp"] = UtilMgr::ipToLong(UtilMgr::getRealIP());
		
			$ary = $this->getArrayResult(RECHARGE_QUICK_COMMIT_4, $_POST);
			
			$this->load->model('Recharge_model');
			$rs = $this->Recharge_model->addRecharge($_POST["bankId"], $_POST["money"], $ary["url"]);
			
			if($ary["url"] != null)
			{
				//$strs = explode("?",$ary["url"]);
				//$ary["url"] = $strs[0] . "?" . urlencode($strs[1]);
				//$ary["url"] = urlencode($ary["url"]);
				//$ary["url"] = str_replace("", "", $ary["url"]);
			}
			$this->echoStrPost(str_replace('\/','/',json_encode($ary)));
			//$this->my_post(RECHARGE_QUICK_COMMIT_4, $_POST);
		}
	}
	
	public function chargeRecord()
	{
		if($this->mPlatform == PLATFORM_4)
		{
			$this->my_post(RECHARGE_CHARGE_RECORD_4, $_POST);
		}
	}
	
}
