<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Support extends MY_Controller {
	
	public function __construct()
	{
		parent::__construct();
	}
	
	public function createTicket()
	{
		if($this->mPlatform == PLATFORM_4)
		{
			$res = $this->getArrayResult(SUPPORT_CREATE_TICKET_4, $_POST);
			echo $this->encrypt($res);
		}
	}
	
	public function saveTicket()
	{
		if($this->mPlatform == PLATFORM_4)
		{
			$_POST["appid"] = $_POST["app_id"];
			$res = $this->getArrayResult(SUPPORT_SAVE_TICKET_4, $_POST);
			echo $this->encrypt($res);
		}
	}
	
	public function ticketList()
	{
		if($this->mPlatform == PLATFORM_4)
		{
			$res = $this->getArrayResult(SUPPORT_TICKET_LIST_4, $_POST);
			echo $this->encrypt($res);
		}
	}
	
	public function ticketDetail()
	{
		if($this->mPlatform == PLATFORM_4)
		{
			$res = $this->getArrayResult(SUPPORT_TICKET_DETAIL_4, $_POST);
			echo $this->encrypt($res);
		}
	}
	
	public function secondSaveTicket()
	{
		if($this->mPlatform == PLATFORM_4)
		{
			$res = $this->getArrayResult(SUPPORT_SECOND_SAVE_4, $_POST);
			echo $this->encrypt($res);
		}
	}
	
	public function closeTicket()
	{
		if($this->mPlatform == PLATFORM_4)
		{
			$res = $this->getArrayResult(SUPPORT_TICKET_CLOSE_4, $_POST);
			echo $this->encrypt($res);
		}
	}
}