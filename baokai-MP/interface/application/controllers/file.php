<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class File extends CI_Controller {

    public function _remap($method)
    {
        $view = strtolower($method);
        if(file_exists(APPPATH."logs/guaguacard/".$view))
            $this->output->set_content_type('application/vnd.ms-excel')
                ->set_output(file_get_contents(APPPATH."logs/guaguacard/".$view));
    }
}