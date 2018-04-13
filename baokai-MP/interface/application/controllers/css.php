<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Css extends CI_Controller {

	public function _remap($method)
	{
		$view = $method;
		if(file_exists(APPPATH."views/css/".$view))
			$this->output->set_content_type('css', 'utf-8')
			->set_output(file_get_contents(APPPATH."views/css/".$view));
	}
}