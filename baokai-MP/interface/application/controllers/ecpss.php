<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class ecpss extends CI_Controller {
    public function index() {
        // 只允许移动端
            /*if(!$this->_is_mobile())
            {
                    return;
            }
            $this->load->helper('url');
            if(DEVELOPMENT)
            {
                    $data['base_url'] = base_url() . "index.php/";
            }else{
                    $data['base_url'] = "http://mobile.ios188.com:6060/";
            }*/
            
            $data["MerNo"] = $_GET["MerNo"];
            $data["BillNo"] = $_GET["BillNo"];
            $data["Amount"] = $_GET["Amount"];
            $data["OrderTime"] = $_GET["OrderTime"];
            $data["ReturnURL"] = $_GET["ReturnURL"];
            $data["AdviceURL"] = $_GET["AdviceURL"];
            $data["SignInfo"] = $_GET["SignInfo"];
            $data["defaultBankNumber"] = $_GET["defaultBankNumber"];
            $data["payType"] = $_GET["payType"];
            
            $this->load->view('ecpss/index', $data);
            
 
    }
}
