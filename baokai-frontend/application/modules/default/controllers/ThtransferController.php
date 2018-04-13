<?php
require_once SITE_ROOT . '/application/include/Client.php';

class Default_ThtransferController extends Zend_Controller_Action {

    public $_clientObject;

    public function init() {
        $this->_clientObject = new Client();
        $this->redis_client = Zend_Registry::get('redis_client');
    }

    public function transferAction() {
        $input_charset = getSecurityInput($this->_request->getPost("input_charset"));
        $notify_url = getSecurityInput($this->_request->getPost("notify_url"));
        $pay_type = getSecurityInput($this->_request->getPost("pay_type"));
        $merchant_code = getSecurityInput($this->_request->getPost("merchant_code"));
        $order_no = getSecurityInput($this->_request->getPost("order_no"));
        $order_amount = getSecurityInput($this->_request->getPost("order_amount"));
        $order_time = getSecurityInput($this->_request->getPost("order_time"));
        $req_referer = getSecurityInput($this->_request->getPost("req_referer"));
        $bankCode = getSecurityInput($this->_request->getPost("bank_code"));
        $customer_ip = getSecurityInput($this->_request->getPost("customer_ip"));
        $sign = getSecurityInput($this->_request->getPost("sign"));
        $this->view->path_js = JS_ROOT;
        $this->view->input_charset = $input_charset;
        $this->view->notify_url = $notify_url;
        $this->view->pay_type = $pay_type;
        $this->view->merchant_code = $merchant_code;
        $this->view->order_no = $order_no;
        $this->view->order_amount = $order_amount;
        $this->view->order_time = $order_time;
        $this->view->req_referer = $req_referer;
        $this->view->customer_ip = $customer_ip;
        $this->view->bank_code = $bankCode;
        $this->view->sign = $sign;
         $value = $this->redis_client->get('thpay_key');
        if($value!=null){
        	$value = $value.'|'.$order_no;
        	$this->redis_client->set('thpay_key',$value, 7*24*60*60);
        }else{
        	$this->redis_client->set('thpay_key',$order_no, 7*24*60*60);
        }
        $this->view->display('default/ucenter/transfer.tpl');
    }
    
    /**
     * 通匯送出前之註記
     */
    public function logsubmitAction(){
    	$order_no = getSecurityInput($this->_request->getPost("order_no"));
    	$value = $this->redis_client->get('thtransfersubmitog');
    	if($value!=null){
    		$value = $value.'|'.$order_no;
    		$this->redis_client->set('thtransfersubmitog',$value, 7*24*60*60);
    	}else{
    		$this->redis_client->set('thtransfersubmitog',$order_no, 7*24*60*60);
    	}
    	 
    	exit;
    }

}
