<?php
require_once SITE_ROOT . '/application/include/Client.php';

class Default_WorthtransferController extends Zend_Controller_Action {

    public $_clientObject;

    public function init() {
        $this->_clientObject = new Client();
        $this->redis_client = Zend_Registry::get('redis_client');
    }

    public function transferAction() {
		
        $service = getSecurityInput($this->_request->getPost("service"));
        $merchant_ID = getSecurityInput($this->_request->getPost("merchant_ID"));
        $notify_url = getSecurityInput($this->_request->getPost("notify_url"));
        $return_url = getSecurityInput($this->_request->getPost("return_url"));
        $charset = getSecurityInput($this->_request->getPost("charset"));
        $title = getSecurityInput($this->_request->getPost("title"));
        $body = getSecurityInput($this->_request->getPost("body"));
        $order_no = getSecurityInput($this->_request->getPost("order_no"));
        $total_fee = getSecurityInput($this->_request->getPost("total_fee"));
        $payment_type = getSecurityInput($this->_request->getPost("payment_type"));
        $paymethod = getSecurityInput($this->_request->getPost("paymethod"));
        $defaultbank = getSecurityInput($this->_request->getPost("defaultbank"));
        $seller_email = getSecurityInput($this->_request->getPost("seller_email"));
        $isApp = getSecurityInput($this->_request->getPost("isApp"));
        $sign_type = getSecurityInput($this->_request->getPost("sign_type"));
        $sign = getSecurityInput($this->_request->getPost("sign"));
        $this->view->path_js = JS_ROOT;
        $this->view->service = $service;
		$this->view->merchant_ID = $merchant_ID;
        $this->view->notify_url = $notify_url;
        $this->view->return_url = $return_url;
        $this->view->charset = $charset;
        $this->view->title = $title;
        $this->view->body = $body;
        $this->view->order_no = $order_no;
        $this->view->total_fee = $total_fee;
        $this->view->payment_type = $payment_type;
        $this->view->paymethod = $paymethod;
        $this->view->defaultbank = $defaultbank;
        $this->view->seller_email = $seller_email;
        $this->view->isApp = $isApp;
        $this->view->sign_type = $sign_type;
        $this->view->sign = $sign;
        
		$value = $this->redis_client->get('worthpay_key');
        if($value!=null){
            $value = $value.'|'.$order_no;
            $this->redis_client->set('worthpay_key',$value, 7*24*60*60);
        }else{
            $this->redis_client->set('worthpay_key',$order_no, 7*24*60*60);
        }
        $this->view->display('default/ucenter/worthtransfer.tpl');
        
    }
}
