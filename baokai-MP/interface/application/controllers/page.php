<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Page extends CI_Controller {

    public function _remap($method)
    {
        $view = strtolower($method);
        $file = null;
        switch ($view) 
        {
            case "mobile":
                $file = "page/mobile.html";
                break;
            case "sd11x5":
            case "ll11x5":
                $file = "page/sd115.html";
                break;
            default:
                $file = "page/".$view.".html";
                break;
        }
        $this->load->helper('url');
        
        echo site_url();
        echo base_url();
        //echo script_url();
        if(file_exists(APPPATH."views/".$file))
            $this->load->view($file);
        else
            echo 'No Page Found! ('.$view.')';
    }
}