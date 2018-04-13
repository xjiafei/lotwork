<?php
class Default_ErrorController extends Zend_Controller_Action {
	
	public function errorAction() {
		$errors = $this->_getParam ( 'error_handler' );
		$this->view->path_img = Zend_Registry::get('config')->imgroot;
		switch ($errors->type) {
			case Zend_Controller_Plugin_ErrorHandler::EXCEPTION_NO_ROUTE:
			case Zend_Controller_Plugin_ErrorHandler::EXCEPTION_NO_CONTROLLER :
			case Zend_Controller_Plugin_ErrorHandler::EXCEPTION_NO_ACTION :
				// 404 error -- controller or action not found
				$this->getResponse()->setHttpResponseCode(404);
				$this->getResponse ()->setRawHeader ( 'HTTP/1.1 404 Not Found' );
				// var_dump('404');
				$priority = Zend_Log::NOTICE;
				$this->log('HTTP/1.1 404 Not Found',$priority);
				$this->view->display ( 'default/error/404.tpl' );
				break;
			default :
				// application error
				$this->getResponse()->setHttpResponseCode(500);
				$priority = Zend_Log::CRIT;
				$this->view->display ( 'default/error/500.tpl' );
				$this->log('HTTP/1.1 500 Error',$priority);
				break;
		}
		
		// Clear previous content
		$this->getResponse ()->clearBody ();
		
		// $this->view->content = $content;
	}
	
	public function log($message,$priority = Zend_log::ERR )
	{
		$logger = Zend_Registry::get('logger');
		$logger->setEventItem('modules', $this->_request->getModuleName() );
		$logger->setEventItem('controller', $this->_request->getControllerName() );
		$logger->setEventItem('action', $this->_request->getActionName() );
		$logger->log($message, $priority);
	}
}

?>