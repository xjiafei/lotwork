<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Bomao extends CI_Controller {
        
	public function __construct()
	{
            parent::__construct();
	}
	
	public function getLinks()
	{
            $this->curl->create('http://admin.bcproduct.com/domain-api/get-domains');
            $result = $this->curl->execute();
            echo $result;
	}
	
	public function checkVersion()
	{
            echo '{ "version" : 1.2, "size" : '.filesize(APPPATH."views/downloads/bomaolaunch.exe").
                    ', "md5" : "'.md5_file (APPPATH."views/downloads/bomaolaunch.exe").'" }';
	}
        
        public function download()
	{
            $data = file_get_contents(APPPATH."views/downloads/bomaolaunch.exe");
            $this->load->helper('download');
            force_download(urlencode('博猫登录器.exe'), $data);
	}
}