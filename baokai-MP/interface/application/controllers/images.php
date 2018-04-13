<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Images extends CI_Controller {

    public function _remap($method)
    {
        $view = strtolower($method);
        if(file_exists(APPPATH."views/images/".$view))
            $this->output->set_content_type('jpeg')
                ->set_output(file_get_contents(APPPATH."views/images/".$view));
        else
            $this->output->set_content_type('jpeg')
                ->set_output(file_get_contents(APPPATH."views/images/404.jpg"));
    }
}