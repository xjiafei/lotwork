<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Gametip extends CI_Controller {

    public function _remap($method)
    {
        $view = strtolower($method);
        $file = null;
        switch ($view) 
        {
            case "cqssc":
            case "llssc":
            case "jlffc":
            case "txffc":
            case "jxssc":
            case "jx-ssc":
            case "tjssc":
            case "xjssc":
            case "hljssc":
                $file = "gametip/cqssc.html";
                break;
            case "sd11x5":
            case "ll11x5":
            case "jx11x5":
            case "gd11x5":
            case "jx11-5":
            case "gd11-5":
			case "js11x5":
                $file = "gametip/sd115.html";
                break;
            case "p3":
            case "p5":
                $file = "gametip/p3.html";
                break;
            case "jsk3":
                $file = "gametip/jsk3.html";
                break;
			case "ahk3":
                $file = "gametip/ahk3.html";
                break;
            case "shssl":
                $file = "gametip/shssl.html";
                break;
            case "lhc":
                $file = "gametip/lhc.html";
                break;
	    case "jlsb":
		case "jssb":
                $file = "gametip/jldice1.html";
                break;
            default:
                $file = "gametip/".$view.".html";
                break;
        }
        if(file_exists(APPPATH."views/".$file))
            $this->load->view($file);
        else
            echo 'No Tips Found! ('.$view.')';
    }
}
