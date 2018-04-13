<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Js extends CI_Controller {

	public function _remap($method)
	{
		$view = $method;
		if(file_exists(APPPATH."views/js/".$view)){
			$this->output->set_content_type('application/javascript')
				->set_output(file_get_contents(APPPATH."views/js/".$view));
		}else{
			//echo "找不到档案" . APPPATH."views/js/".$view;
		}
	}
}