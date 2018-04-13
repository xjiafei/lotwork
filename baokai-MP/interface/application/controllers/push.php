<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Push extends MY_Controller {
        private $sucess = "sucess";
        private $fail = "fail";
        private $sucessStatus = 0;
        private $faidStatus = 1;
	public function __construct()
	{
            parent::__construct();
	}
	
	public function registerId()
	{
            $postData = array(
                'userid' => $_POST["userid"],
                'deviceid' => $_POST["deviceid"],
                'deviceidtype' => $_POST["deviceidtype"]?$_POST["deviceidtype"]:0,
                'lastregister' => date("Y-m-d H:i:s"),
                'app_id' => $_POST["app_id"]
            );
            $this->load->model('Push_model');
            $this->Push_model->clearDeviceid($_POST["deviceid"]);

            if ($this->Push_model->registerUserPushInfo($postData) == 1)
            {
                $result['error'] = $this->sucess;
                $result['messagetype'] = $this->sucessStatus;
                $this->echoStrPost(str_replace('\/','/',json_encode($result)));
            }
            else
            {
                $result['error'] = $this->fail;
                $result['messagetype'] = $this->faidStatus;
                $result['content'] = 'Mobile Platform Interrupt';
                $this->echoStrPost(str_replace('\/','/',json_encode($result)));
            }
	}
	
	public function switchStatus()
	{
            $this->load->model('Push_model');
            $result = $this->Push_model->getPushStatus($_POST['userid'], $_POST["app_id"]);
            $this->echoStrPost(str_replace('\/','/',json_encode($result)));
	}
	
	public function switchSet()
	{
            $this->load->model('Push_model');
            if($this->Push_model->setPushStatus($_POST['userid'], $_POST["app_id"], $_POST["openstatus"]))
            {
                $result['error'] = $this->sucess;
                $result['messagetype'] = $this->sucessStatus;
                $this->echoStrPost(str_replace('\/','/',json_encode($result)));
            }
            else
            {
                $result['error'] = $this->fail;
                $result['messagetype'] = $this->faidStatus;
                $result['content'] = 'Mobile Platform Interrupt';
                $this->echoStrPost(str_replace('\/','/',json_encode($result)));
            }
	}
}