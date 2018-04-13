<?php
class SmartyPlugin extends Zend_Controller_Plugin_Abstract
{

        //dispatchLoopStartup() 在 Zend_Controller_Front 进入其分发循环（dispatch loop）前被调用
    public function dispatchLoopStartup(Zend_Controller_Request_Abstract $request)
    {
          $module = $request->module; 
          $template_dir = SITE_ROOT."/application/modules/".$module.'/views/'; 
         
          $vr=new Zend_Controller_Action_Helper_ViewRenderer();

          $vr->setView(new SmartyView($template_dir));
          $vr->setViewSuffix('tpl');
          Zend_Controller_Action_HelperBroker::addHelper($vr);   
    }
}