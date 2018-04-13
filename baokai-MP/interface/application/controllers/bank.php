<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Bank extends MY_Controller {
	
	
	public function __construct()
	{
		parent::__construct();
	}
	
	public function bankReport()
	{
		$ordertype = is_null($_POST["ordertype"])?"0":$_POST["ordertype"];
		
		if($this->mPlatform == PLATFORM_3)
		{
			if($_POST["chan_id"] == "1")
			{
				$this->my_post(sprintf (BANK_BANKREPORT_LOW, $ordertype), $_POST);
			}
			else if($_POST["chan_id"] == "4")
			{
				$this->my_post(sprintf (BANK_BANKREPORT_HIGH, $ordertype), $_POST);
			}
		}
		else if($this->mPlatform == PLATFORM_4)
		{
			//$this->my_post(BANK_BANKREPORT_4, $_POST);
			
			$ary = $this->getArrayResult(BANK_BANKREPORT_4, $_POST);
			$this->echoJsonPost($ary["list"]);
		}
                else if($this->mPlatform == PLATFORM_BM)
		{
                        $_POST['pagesize'] = BM_PAGE_SIZE;
                        $this->wrapBM(BM_BANK_REPORT, $_POST);
		}
	}
}