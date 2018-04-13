<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Shared extends MY_Controller {
    
    public function __construct()
    {
        parent::__construct();
    }
    
    public function getVersion()
    {
        $rtn = new stdClass();
        
        $this->load->model('App_info_model');
        $app_info = $this->App_info_model->getAppInfo($this->mApp_info->app_id);
        
        $rtn->version = $app_info->version;
        $rtn->must_upgrade = $app_info->must_upgrade;
        $rtn->downloadUrl = $app_info->downloadUrl;
        $rtn->msg = $app_info->msg;
        
        $this->echoStrPost(str_replace('\/','/',json_encode($rtn)));
    }
    
    public function getAdInfo()
    {
        $this->load->model('Activity_model');
        $rs = $this->Activity_model->getActivity($_POST["app_id"]);

        foreach ($rs as $key => $value)
        {
            //2016新手活動入口判斷
            if (strpos($rs[$key]["act_url"], "/m/nbm/") > 0) {
                if (isset($_POST["CGISESSID"]) && !empty($_POST["CGISESSID"])) {
                    $post_content = '{"head":{"sowner":0,"rowner":0,"msn":0,"msnsn":0,"userId":"","userAccount":"","sessionId":"' . $_POST["CGISESSID"] .'"},"body":{"param":{},"pager":{"startNo":0,"endNo":0}}}';
                    $this->curl->create(HOST_4 . '/information/getNBEntryInfo');
                    $this->curl->option(CURLOPT_HTTPHEADER , array(
                        "content-length: " . strlen($post_content),
                        "Content-Type: application/json; charset=utf-8"));
                    $this->curl->post($post_content);
                    $return = $this->curl->execute();

                    $result = json_decode(trim($return),true);

                    if (isset($result['body']['result']['isTrain']) && $result['body']['result']['isTrain']) {} else {
                        unset($rs[$key]);
                        continue;
                    }
                } else {
                    unset($rs[$key]);
                    continue;
                }
            }

            $url = $rs[$key]["act_url"];
            
            if(strpos($url,"?") > 0)
            {
                $url .= "&";
            }
            else
            {
                $url .= "?";
            }
            
            //$url .= "token=" . $_POST["CGISESSID"] . "&come_from=" . $this->mApp_info->come_from;
            $url .= "come_from=" . $this->mApp_info->come_from;
            if($this->mApp_info->come_from == "3")
            {
                $url .= "&device=1";
            }
            else
            {
                $url .= "&device=2";
            }


            //$sid = urlencode($_POST["CGISESSID"]);
            $sid = $_POST["CGISESSID"];
            $sid = str_replace("/", "-", $_POST["CGISESSID"]);
            $sid = str_replace("%", "$", $sid);
            $sid = str_replace("=", "!", $sid);
            $sid = str_replace("+", "*", $sid);

            $url.= "&sid=" . $sid;
            $rs[$key]["act_url"] = $url;
            //var_dump($rs[$key]["act_url"]);
        }
        $rs = array_values($rs);
        $this->echoStrPost(str_replace('\/','/',json_encode($rs)));
    }
    
    public function addDownloadCount()
    {
        $rtn = new stdClass();
        
        $this->load->model('Counter_model');
        $rs = $this->Counter_model->addDownloadCount($_POST["app_id"], $_POST["initial"], $_POST["version"]);
        
        $rtn->status = $rs>0?1:0;
        
        $this->echoStrPost(json_encode($rtn));
    }
    
    public function proxyMultiply()
    {
        $rtn = new stdClass();
        
        $this->load->model('App_multiply_model');
        $rs = $this->App_multiply_model->getAppMultiply($_POST["appCode"]);
        
        if($rs != null){
            $rtn->link = $rs['link']; 
            $rtn->image = $rs['image']; 
        }
        $this->echoStrPost(str_replace('\/','/',json_encode($rtn)));
    }
    
    public function addAppMultiplyLog()
    {
        $rtn = new stdClass();
        
        $this->load->model('App_multiply_log_model');
        if(!$this->App_multiply_log_model->IsDataExisted($_POST))
        {
            $rs = $this->App_multiply_log_model->addData($_POST);
            $rtn->status = $rs>0?1:0;
        }else{
            $rtn->status = 1;
        }
        
        $this->echoStrPost(json_encode($rtn));
    }
}