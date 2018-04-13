<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');

//require_once '../../MD/Mobile_Detect.php';
include_once (realpath(dirname(dirname('../../'))).'/MD/Mobile_Detect.php');

class Slmmc extends CI_Controller {
    private $request;
    private $post;

    public function __construct()
    {
        parent::__construct();
        $this->request = json_decode(file_get_contents('php://input'), true);
        $this->post = array(
            "head" => array("sowner" => "", "rowner" => "", "msn" => "",
                "msnsn" => "", "userId" => "", "userAccount" => "",
                "sessionId" => $this->request["CGISESSID"]),
            "body" => array("pager" => array("startNo" => "", "endNo" => ""), "param" => array("activityType" => 0))
        );
        //判斷版本 (0:普通版 1:娛樂版 default: 0)
        if (isset($this->request["activityType"])) {
            $this->request["activityType"] = (int) $this->request["activityType"];
            if (in_array($this->request["activityType"], array(0, 1))) {
                $this->post["body"]["param"]["activityType"] = $this->request["activityType"];
            }

        }
    }

    public function hello(){
        echo 'hello 999<br/>';
        $detect = new Mobile_Detect();
        if($detect->isIphone()) echo "isIphone<br/>";
        if($detect->version('Android')) echo "Android<br/>";
        echo 'bye<br/>';
    }
    
    public function index()
    {
        $this->post["head"]["sessionId"] = $data["CGISESSID"] = $_SERVER["QUERY_STRING"];

        $post_content = json_encode($this->post);
        $this->curl->create(HOST_4."/slmmc/init");
        $this->curl->option(CURLOPT_HTTPHEADER , array(
            "content-length: " . strlen($post_content),
            "Content-Type: application/firefrog; charset=utf-8"
            )
        );
        $this->curl->post($post_content);
        $return = $this->curl->execute();

        $tmp = json_decode(trim($return),true);
        $src = $tmp["body"]["result"];
        $out["balance"] = $src["balance"];
        $out["defaultMethod"] = $src["defaultMethod"];
        $out["gameMethods"] = $src["gameMethods"];
        /*
        unset($out["gameMethods"][0]["childs"][0]["childs"][1]);
        unset($out["gameMethods"][1]["childs"][0]["childs"][1]);
        unset($out["gameMethods"][2]["childs"][0]["childs"][1]);
        unset($out["gameMethods"][2]["childs"][1]["childs"][3]);
        unset($out["gameMethods"][2]["childs"][1]["childs"][5]);
        unset($out["gameMethods"][2]["childs"][1]["childs"][6]);
        unset($out["gameMethods"][3]["childs"][0]["childs"][1]);
        unset($out["gameMethods"][3]["childs"][1]["childs"][3]);
        unset($out["gameMethods"][3]["childs"][1]["childs"][5]);
        unset($out["gameMethods"][3]["childs"][1]["childs"][6]);
        unset($out["gameMethods"][4]["childs"][0]["childs"][1]);
        unset($out["gameMethods"][4]["childs"][1]["childs"][3]);
        unset($out["gameMethods"][4]["childs"][1]["childs"][5]);
        unset($out["gameMethods"][4]["childs"][1]["childs"][6]);
        unset($out["gameMethods"][5]["childs"][0]["childs"][1]);
        unset($out["gameMethods"][5]["childs"][1]["childs"][1]);
        unset($out["gameMethods"][6]["childs"][0]["childs"][1]);
        unset($out["gameMethods"][6]["childs"][1]["childs"][1]);
        */
        $out["records"] = array();
        foreach($src["records"] as $key => $value)
        {
            $r["orderCode"] = $value["orderCode"];
            $r["time"] = $value["time"];
            $r["totalprice"] = $value["totalprice"];
            $r["numberRecord"] = $value["numberRecord"];
            $r["ifwin"] = $value["ifwin"];
            $r["enid"] = $value["enid"];
            $r["bonus"] = $value["bonus"];
            array_push($out["records"], $r);
        }
        $out["gameLimits"] = $src["gameLimits"];
        $out["retSwitch"] = $src["retSwitch"];
        $out["userAwardName"] = $src["userAwardName"];
        if(count($src["awardGroups"]) > 0)
        {
            $out["awardGroups"] = array();
            foreach($src["awardGroups"] as $key => $value)
            {
                if($value["betType"] == 1)
                {
                    unset($out["awardGroups"]);
                    break;
                }
                $p["awardGroupId"] = $value["gid"];
                $p["awardName"] = $value["awardName"];
                array_push($out["awardGroups"], $p);
            }
        }
        $data["init_data"] =json_encode($out);
        $this->load->view("../../m/slmmc/index.html", $data);
    }
    
    public function init()
    {
        if($this->request["CGISESSID"] == null)
        {
            echo "CGISESSID fail";
            return;
        }
        $post_content = json_encode($this->post);
        $this->curl->create(HOST_4."/slmmc/init");
        $this->curl->option(CURLOPT_HTTPHEADER , array(
            "content-length: " . strlen($post_content),
            "Content-Type: application/firefrog; charset=utf-8"
            )
        );
        $this->curl->post($post_content);
        $return = $this->curl->execute();
        //var_dump($post_content);
        //var_dump($return);die;
        $tmp = json_decode(trim($return),true);
        $src = $tmp["body"]["result"];
        $out["balance"] = $src["balance"];
        $out["defaultMethod"] = $src["defaultMethod"];
        $out["gameMethods"] = $src["gameMethods"];
        /*
        unset($out["gameMethods"][0]["childs"][0]["childs"][1]);
        unset($out["gameMethods"][1]["childs"][0]["childs"][1]);
        unset($out["gameMethods"][2]["childs"][0]["childs"][1]);
        unset($out["gameMethods"][2]["childs"][1]["childs"][3]);
        unset($out["gameMethods"][2]["childs"][1]["childs"][5]);
        unset($out["gameMethods"][2]["childs"][1]["childs"][6]);
        unset($out["gameMethods"][3]["childs"][0]["childs"][1]);
        unset($out["gameMethods"][3]["childs"][1]["childs"][3]);
        unset($out["gameMethods"][3]["childs"][1]["childs"][5]);
        unset($out["gameMethods"][3]["childs"][1]["childs"][6]);
        unset($out["gameMethods"][4]["childs"][0]["childs"][1]);
        unset($out["gameMethods"][4]["childs"][1]["childs"][3]);
        unset($out["gameMethods"][4]["childs"][1]["childs"][5]);
        unset($out["gameMethods"][4]["childs"][1]["childs"][6]);
        unset($out["gameMethods"][5]["childs"][0]["childs"][1]);
        unset($out["gameMethods"][5]["childs"][1]["childs"][1]);
        unset($out["gameMethods"][6]["childs"][0]["childs"][1]);
        unset($out["gameMethods"][6]["childs"][1]["childs"][1]);
        */
        $out["records"] = array();
        foreach($src["records"] as $key => $value)
        {
            $r["orderCode"] = $value["orderCode"];
            $r["time"] = $value["time"];
            $r["totalprice"] = $value["totalprice"];
            $r["numberRecord"] = $value["numberRecord"];
            $r["ifwin"] = $value["ifwin"];
            $r["enid"] = $value["enid"];
            $r["bonus"] = $value["bonus"];
            array_push($out["records"], $r);
        }
        $out["gameLimits"] = $src["gameLimits"];
        $out["retSwitch"] = $src["retSwitch"];
        $out["userAwardName"] = $src["userAwardName"];
        if(count($src["awardGroups"]) > 0)
        {
            $out["awardGroups"] = array();
            foreach($src["awardGroups"] as $key => $value)
            {
                if($value["betType"] == 1)
                {
                    unset($out["awardGroups"]);
                    break;
                }
                $p["awardGroupId"] = $value["gid"];
                $p["awardName"] = $value["awardName"];
                array_push($out["awardGroups"], $p);
            }
        }
        echo json_encode($out);
        die();
/*
        if($tmp["body"]["result"] == null 
        || (array_key_exists("status", $tmp["body"]["result"]) && $tmp["body"]["result"]["status"] == null)
        || (array_key_exists("orderId", $tmp["body"]["result"]) && $tmp["body"]["result"]["orderId"] == null && $tmp["body"]["result"]["gameOrderDTO"] == null && $tmp["body"]["result"]["gameOrderResponseDTO"] == null && $tmp["body"]["result"]["overMutipleDTO"] == null)
        || (array_key_exists("gamePlanId", $tmp["body"]["result"]) && $tmp["body"]["result"]["gamePlanId"] == null && $tmp["body"]["result"]["gameOrderResponseDTO"] == null && $tmp["body"]["result"]["overMutipleDTO"] == null)
        )
            var_dump($return);
        else
            echo json_encode($tmp["body"]["result"]);
*/
    }
    
    public function bet()
    {
        if($this->request["CGISESSID"] == null)
        {
            echo "CGISESSID fail";
            return;
        }

        $device = '';
        $detect = new Mobile_Detect();
        if($detect->isIphone()) $device = "ios";
        if($detect->version('Android')) $device = "android";

        //判斷版本 (0:普通版 1:娛樂版 default: 0)
        if (!isset($this->request["activityType"])) {
            $this->request["activityType"] = 0;
        } else {
            $this->request["activityType"] = (int) $this->request["activityType"];
            if (!in_array($this->request["activityType"], array(0, 1))) {
                $this->request["activityType"] = 0;
            }            
        }

        //判斷版本獎金返點模式 (1:高返點 2:高獎金 default: 1)
        if (!isset($this->request["awardMode"])) {
            $this->request["awardMode"] = 1;
        } else {
            $this->request["awardMode"] = (int) $this->request["awardMode"];
            if (!in_array($this->request["awardMode"], array(1, 2))) {
                $this->request["awardMode"] = 1;
            }            
        }

        $param = array(
            "version" => "2.8",
            "channel" => $device,
            "gameType" => "slmmc",
            "isTrace" => 0,
            "traceWinStop" => 0,
            "traceStopValue" => -1,
            "balls" => $this->request["balls"],
            "orders" => array(array("number" => "/", "issueCode" => 1, "multiple" => 1)),
            "amount" => $this->request["amount"],
            "activityType" => $this->request["activityType"],
            "awardMode" => $this->request["awardMode"]
        );

        $this->post["body"]["param"] = array();
        $this->post["body"]["param"]["data"] = json_encode($param);
        $post_content = json_encode($this->post);
        $this->curl->create(HOST_4."/slmmc/bet");
        $this->curl->option(CURLOPT_HTTPHEADER , array(
            "content-length: " . strlen($post_content),
            "Content-Type: application/firefrog; charset=utf-8"
            )
        );

        $this->curl->post($post_content);
        $return = $this->curl->execute();

        $tmp = json_decode(trim($return),true);
        $src = $tmp["body"]["result"];
        $out["isSuccess"] = $src["isSuccess"];
        $out["balance"] = $src["balance"];
        $out["data"] = array();
        $out["data"]["projectId"] = $src["data"]["projectId"];
        $out["data"]["writeTime"] = $src["data"]["writeTime"];
        $out["data"]["result"] = $src["data"]["result"];
        $out["data"]["totalprice"] = $src["data"]["totalprice"] * 0.0001;
        $out["data"]["winMoney"] = $src["data"]["winMoney"] * 0.0001;
        $out["data"]["list"] = array();
        foreach($src["data"]["list"] as $key => $value)
        {
            $r["code"] = $value["code"];
            $r["isWin"] = $value["isWin"];
            $r["methodName"] = $value["methodName"];
            $r["modes"] = $value["modes"];
            $r["multiple"] = $value["multiple"];
            $r["num"] = $value["num"];
            $r["projectid"] = $value["projectid"];
            $r["totalPrice"] = $value["totalPrice"] * 0.0001;
            $r["winMoney"] = $value["winMoney"] * 0.0001;
            $r["winNum"] = $value["winNum"];
            $r["writeTime"] = $value["writeTime"];
            array_push($out["data"]["list"], $r);
        }
        echo json_encode($out);
    }
    
    public function bet2()
    {
        if($this->request["CGISESSID"] == null)
        {
            echo "CGISESSID fail";
            return;
        }

        $device = '';
        $detect = new Mobile_Detect();
        if($detect->isIphone()) $device = "ios";
        if($detect->version('Android')) $device = "android";

        $param = array(
            "version" => "2.8",
            "channel" => $device,
            "gameType" => "slmmc",
            "isTrace" => 0,
            "traceWinStop" => 0,
            "traceStopValue" => -1,
            "balls" => $this->request["balls"],
            "orders" => array(array("number" => "/", "issueCode" => 1, "multiple" => 1)),
            "amount" => $this->request["amount"]
        );

        $this->post["body"]["param"] = array();
        $this->post["body"]["param"]["data"] = json_encode($param);
        $post_content = json_encode($this->post);
        $this->curl->create(HOST_4."/slmmc/bet");
        $this->curl->option(CURLOPT_HTTPHEADER , array(
            "content-length: " . strlen($post_content),
            "Content-Type: application/firefrog; charset=utf-8"
            )
        );

        $this->curl->post($post_content);
        $return = $this->curl->execute();
        
        var_dump($post_content);
        var_dump($return);die;
        $tmp = json_decode(trim($return),true);
        $src = $tmp["body"]["result"];
        $out["isSuccess"] = $src["isSuccess"];
        $out["balance"] = $src["balance"];
        $out["data"] = array();
        $out["data"]["projectId"] = $src["data"]["projectId"];
        $out["data"]["writeTime"] = $src["data"]["writeTime"];
        $out["data"]["result"] = $src["data"]["result"];
        $out["data"]["totalprice"] = $src["data"]["totalprice"] * 0.0001;
        $out["data"]["winMoney"] = $src["data"]["winMoney"] * 0.0001;
        $out["data"]["list"] = array();
        foreach($src["data"]["list"] as $key => $value)
        {
            $r["code"] = $value["code"];
            $r["isWin"] = $value["isWin"];
            $r["methodName"] = $value["methodName"];
            $r["modes"] = $value["modes"];
            $r["multiple"] = $value["multiple"];
            $r["num"] = $value["num"];
            $r["projectid"] = $value["projectid"];
            $r["totalPrice"] = $value["totalPrice"] * 0.0001;
            $r["winMoney"] = $value["winMoney"] * 0.0001;
            $r["winNum"] = $value["winNum"];
            $r["writeTime"] = $value["writeTime"];
            array_push($out["data"]["list"], $r);
        }
        echo json_encode($out);
    }
    
    public function setAwardGroup()
    {
        if($this->request["CGISESSID"] == null)
        {
            echo "CGISESSID fail";
            return;
        }

        $param = array(
            "lotteryId" => 99112,
            "awardGroupId" => $this->request["awardGroupId"],
        );

        $this->post["body"]["param"] = $param;
        $post_content = json_encode($this->post);

        $this->curl->create(GAME_ADDBONUSDATA_4);
        $this->curl->option(CURLOPT_HTTPHEADER , array(
            "content-length: " . strlen($post_content),
            "Content-Type: application/firefrog; charset=utf-8"
            )
        );
        $this->curl->post($post_content);
        $return = $this->curl->execute();

        $tmp = json_decode(trim($return),true);
        $src = $tmp["body"]["result"];
        $out["status"] = $src["status"];
        $out["messagetype"] = $tmp["head"]["status"];
        echo json_encode($out);
    }
}