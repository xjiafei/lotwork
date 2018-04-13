<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');

session_start();

class Event160125 extends CI_Controller {
    private $request;
    private $s_data;

    public function __construct()
    {
        parent::__construct();
        $this->request = json_decode(file_get_contents('php://input'), true);
        $this->load->model('Mem_cache_model');
        if(!$this->Mem_cache_model->get("event160125_init")) $this->_init();
        if(($this->s_data = $_SESSION['S_DATA']) == null) $this->s_data = array();
        $_SESSION['S_DATA'] = null;
    }

    function _init()
    {
        $this->Mem_cache_model->save("admin_user", "admin", 0);
        $this->Mem_cache_model->save("admin_pass", "123qwe", 0);
        $this->Mem_cache_model->save("admin_session", "NOT SET", 0);
        $this->Mem_cache_model->save("event160125_init", true, 0);
    }

    public function hello()
    {
        echo 'hello 999<br/>';

        echo 'bye<br/>';
    }

    public function index($frame = "1")
    {
        if (!(isset($_SESSION['CGSESSION']) && 
            $_SESSION['CGSESSION'] == $this->Mem_cache_model->get("admin_session")))
        {
            header('Location: '.'/event160125/login');
            die();
        }
        switch ($frame)
        {
            case "3":
                $this->load->view('event160125/manager', $this->s_data);
                break;
            case "2":
                $this->load->model('event_160125');
                $u = $_POST["u"]."";
                $d = $_POST["d"]."";
                if(count($this->s_data["eventday"] = $this->event_160125->getDate()) > 0 && $d == "")
                    $d = $this->s_data["eventday"][0]["eventday"];
                if($u != "")
                {
                    $this->s_data["record"] = $this->event_160125->queryUser($u);
                    $this->s_data["download"] = "u=".$u;
                }
                elseif($d != "")
                {
                    $this->s_data["record"] = $this->event_160125->queryEventday($d);
                    $this->s_data["download"] = "d=".$d;
                }
                $this->load->view('event160125/query', $this->s_data);
                break;
            case "1":
            default:
                $this->load->view('event160125/upload', $this->s_data);
                break;
        }
    }

    public function login()
    {
        $this->load->view('event160125/login', $this->s_data);
    }

    public function verify()
    {
        $user = $_POST["user"];
        $pass = $_POST["pass"];
        if($user == $this->Mem_cache_model->get("admin_user") &&
                $pass == $this->Mem_cache_model->get("admin_pass"))
        {
            $this->Mem_cache_model->save("admin_session", 
                    $_SESSION['CGSESSION'] = md5($user.md5($pass)), 0);
            header('Location: '.'/event160125');
        }
        else
        {
            $_SESSION['S_DATA'] = array("msg" => "帐号或密码错误");
            header('Location: '.'/event160125/login');
        }
        die();
    }

    public function pass()
    {
        $pass = $_POST["pass"];
        $this->Mem_cache_model->save("admin_pass", $pass, 0);
        $_SESSION['S_DATA'] = array("msg_pass" => "密码修改成功");
        header('Location: '.'/event160125/index/3');
        die();
    }

    public function upload()
    {
        $file_name = "./application/logs/event160125/".$_FILES["fileUpload"]["name"];
        if ($_FILES["fileUpload"]["error"] > 0)
            $_SESSION['S_DATA'] = array("msg_upload" => '<font color="red">上传错误 ('.$_FILES["fileUpload"]["error"].')</font>');
        elseif(file_exists($file_name))
            $_SESSION['S_DATA'] = array("msg_upload" => '<font color="red">档案已存在</font>');
        else
        {
            $this->load->model('event_160125');
            $up = 0;
            $shuffle = array();
            $data_err = "";
            $fp = fopen($_FILES["fileUpload"]["tmp_name"], "r");
            srand(time());
            while ($data = fgetcsv ($fp, 1000, ","))
            {
                if(count($data) == 4)
                {
                    $data[0] = str_replace("\xEF\xBB\xBF",'',$data[0]);
                    if(intval($data[0]) == 0)
                    {
                        $data_err .= $data[0].','.$data[1].',<font color="red">'.$data[3].'</font> => <font color="red">用户ID解析错误('.intval($data[0]).')</font><br/>';
                        continue;
                    }
                    elseif(strlen($data[3]) != 10)
                    {
                        $data_err .= $data[0].','.$data[1].',<font color="red">'.$data[3].'</font> => <font color="red">日期格式错误</font><br/>';
                        continue;
                    }
                    $amount = doubleval($data[2]);
                    $prize = 0;
                    $prize2 = 0;
                    if($amount >= 100000)       $prize = $amount * .02;
                    elseif($amount >= 10000)    $prize = $amount * .015;
                    elseif($amount >= 5000)     $prize = $amount * .012;
                    elseif($amount >= 1000)     $prize = $amount * .01;
                    elseif($amount >= 500)      $prize = $amount * .008;
                    $prize = doubleval(substr(sprintf("%.2f", $prize), 0, -1));
                    if(!$this->event_160125->addData($data[0], $data[1], $amount, $prize, $prize2, $data[3]))
                    {
                        $data_err .= $data[0].',<font color="blue">'.$data[1].','.$data[3].'</font> => <font color="blue">此笔资料已存在</font><br/>';
                        continue;
                    }
                    $shuffle[$up] = array("user_id" => $data[0], "prize2" => $prize, "eventday" => $data[3]);
                    if(fmod(++$up, 10) == 0)
                    {
                        $lucky = $shuffle[count($shuffle) - rand(0, 10) - 1];
                        $this->event_160125->addDouble($lucky["user_id"], $lucky["prize2"], $lucky["eventday"]);
                    }
                }
            }
            fclose($fp);
            move_uploaded_file($_FILES["fileUpload"]["tmp_name"], $file_name);
            $_SESSION['S_DATA'] = array("msg_upload" => '<font color="green">上传成功</font><br/>'.$data_err);
        }
        header('Location: '.'/event160125');
        die();
    }

    public function download($file)
    {
        $file_name = "./application/logs/event160125/".$file;
        if(file_exists($file_name))
            $this->output->set_content_type('text/csv')
                ->set_output(file_get_contents($file_name));
        else echo 'Error File Name';
    }

    public function download2()
    {
        $this->load->model('event_160125');
        $u = $_REQUEST["u"]."";
        $d = $_REQUEST["d"]."";
        header("Content-Type: application/vnd.ms-excel; charset=utf-8");
        header("Content-Disposition: attachment; filename=" . $filename);
        if($u != "")
            $this->_csvout($this->s_data["record"] = $this->event_160125->queryUser($u));
        elseif($d != "")
            $this->_csvout($this->s_data["record"] = $this->event_160125->queryEventday($d));
        else echo 'Error';
    }

    function _csvout($content)
    {
        header('Content-type', 'application/vnd.ms-excel');
        header('Content-Disposition', 'attachment; filename="query.csv"');
        $fp = fopen('php://output', 'w');
        $title = array('用户Id', '用户名称', '当日销量', '奖金金额', 'X2奖金', '领取奖金',
            '日期', '汇入日期', '领奖日期');
        foreach ($title as $i => $one)
            $head[$i] = iconv('UTF-8', 'GB2312', $one);

        //fputcsv($fp, $head);
        fputcsv($fp, $title);
        foreach ($content as $one)
        {
            $row = array();
            foreach ($one as $j => $v)
                $row[$j] = iconv('UTF-8', 'GB2312', $v);
            //fputcsv($fp, $row);
            fputcsv($fp, $one);
        }
        fclose($fp);
    }

    public function activity()
    {
        $_SESSION['userId'] = $_GET["userId"];
        $sid = $_GET["sid"];
        $sid = str_replace("-", "/", $sid);
        $sid = str_replace("$", "%", $sid);
        $sid = str_replace("!", "=", $sid);
        $sid = str_replace("*", "+", $sid);
        $_SESSION["CGISESSID"] = $sid;
        $this->load->view('event160125/activity', $this->s_data);
    }

    public function data()
    {
        $this->load->model('event_160125');
        $data = $this->event_160125->queryUid($_SESSION['userId']);

        $now = strtotime(date('Y-m-d', strtotime(" -12 hours ")));
        $start = strtotime('2016-01-25');
        $end = strtotime('2016-02-13');
        $events = ($end - $start) / (60*60*24) + 1;
        $prize_arr = array(
            "isSuccess" => 1,
            "prize" => array_fill(0, $events, 0),
            "doublePrize" => array_fill(0, $events, 0),
            "hisPrize" => array_fill(0, $events, 0),
            "open" => array_fill(0, $events, 0),
            "standard"=> array_fill(0, $events, 0),
            "scratch" => array_fill(0, $events, 0)
        );

        $open = ($now - $start) / (60*60*24);
        if($open > $events) $open = $events;
        $idx = 0;
        while($idx < $open)
            $prize_arr["open"][$idx++] = 1;

        foreach ($data as $row)
        {
            $eventday = strtotime($row["eventday"]);
            $offset = ($eventday - $start) / (60*60*24);

            $prize_arr['prize'][$offset] = doubleval($row["prize"]);
            $prize_arr['doublePrize'][$offset] = doubleval($row["prize2"]) > 0 ? 1 : 0;
            $prize_arr['hisPrize'][$offset] = doubleval($row["prize"]);
            $prize_arr['standard'][$offset] = $row["amount"] >= 500 ? 1: 0;
            $prize_arr['scratch'][$offset] = $row["claim"] == "1" ? 1 : 0;
        }
        echo json_encode($prize_arr);
    }

    public function claim($eventidx)
    {
        $user_id = $_SESSION['userId'];
        $sid = $_SESSION["CGISESSID"];
        $eventday = date("Y/m/d",strtotime('2016-01-25') + $eventidx*60*60*24);
        $this->load->model('event_160125');
        $record = $this->event_160125->getClaimRecord($user_id, $eventday);
        if(count($record) == 0)
        {
            echo json_encode(array("isSuccess" => false, "message" => "请求数据错误"));
            return;
        }
        elseif($record[0]["claim"] == "1")
        {
            echo json_encode(array("isSuccess" => false, "message" => "奖项已领过"));
            return;
        }
        elseif(doubleval($record[0]["prize"]) == 0)
        {
            echo json_encode(array("isSuccess" => false, "message" => "未达标无法领奖"));
            return;
        }
        $fullprize = doubleval($record[0]["prize"]) + doubleval($record[0]["prize2"]);
        $fullprize = doubleval(substr(sprintf("%.2f", $fullprize), 0, -1));

        $post = array(
            "head" => array("sowner" => "", "rowner" => "", "msn" => "",
                "msnsn" => "", "userId" => "", "userAccount" => "",
                "sessionId" => $sid),
            "body" => array("pager" => array("startNo" => "", "endNo" => ""))
        );

        $post["body"]["param"] = array(
            "system" => "1",
            "prize" => $fullprize,
            "note" => "天天刮返利 ".$eventday
        );

        $post_content = json_encode($post);
        $this->curl->create(HOST_4."/event/doMoneyAct");
        $this->curl->option(CURLOPT_HTTPHEADER , array(
            "content-length: " . strlen($post_content),
            "Content-Type: application/firefrog; charset=utf-8"
            )
        );

        $this->curl->post($post_content);
        $return = $this->curl->execute();

        $this->event_160125->claimLog($user_id, $sid, $fullprize, $eventday,
                substr($post_content, 0, 250), substr($return, 0, 250));
        if(strlen($return) < 20)
            echo json_encode(array("isSuccess" => false, "message" => "服务器忙碌中, 请稍后重试"));
        else
        {
            $this->event_160125->claimPrize($user_id, $eventday);
            echo json_encode(array("isSuccess" => true));
        }
    }
    
    public function debug777()
    {
        var_dump($_SESSION);
        $this->load->model('Mem_cache_model');
        echo '<br/>event160125_init ';
        var_dump($this->Mem_cache_model->get("event160125_init"));
        echo '<br/>admin_user ';
        var_dump($this->Mem_cache_model->get("admin_user"));
        echo '<br/>admin_pass ';
        var_dump($this->Mem_cache_model->get("admin_pass"));
        echo '<br/>admin_session ';
        var_dump($this->Mem_cache_model->get("admin_session"));
    }
    
    public function debug999()
    {
        $post = array(
            "head" => array("sowner" => "", "rowner" => "", "msn" => "",
                "msnsn" => "", "userId" => "", "userAccount" => "",
                "sessionId" => "3%/JzBZK04H30dZBsryhmUj896B+qpKJL5L5+4Atm+ls7mqidZ74lEg=="),
            "body" => array("pager" => array("startNo" => "", "endNo" => ""))
        );

        $post["body"]["param"] = array(
            "system" => "1",
            "prize" => 99.9,
            "note" => "天天刮返利"
        );

        $post_content = json_encode($post);
        $this->curl->create(HOST_4."/event/doMoneyAct");
        $this->curl->option(CURLOPT_HTTPHEADER , array(
            "content-length: " . strlen($post_content),
            "Content-Type: application/firefrog; charset=utf-8"
            )
        );

        $this->curl->post($post_content);
        $return = $this->curl->execute();

        var_dump($return);
    }

    public function stext()
    {
        session_start();
        if(!isset($_SESSION["favcolor"]))
        {
            $_SESSION["favcolor"] = "green";
            $_SESSION["favanimal"] = "cat";
            echo "Session variables are set.";
        }
        else print_r($_SESSION);
    }

    public function mctest()
    {
        $this->load->driver('cache');

        $key = 'testmckey';
        $data = time();

        if($this->cache->memcached->is_supported())
            echo "supported memcached";
        else
            echo "not supported memcached";

        $is_success = $this->cache->memcached->save($key, $data, 60);
        if($is_success)
            echo "save success";
        else
            echo "save false";

        $str = $this->cache->memcached->get($key);
        print_r("TEST SAVE AND GET str=".$str);

        echo '<br/><br/><br/>';

        $this->load->model('Mem_cache_model');
        $this->Mem_cache_model->save("YYY_KKK", "999", MC_APP_INFO_CACHE_TIME);
        $zzz = $this->Mem_cache_model->get("YYY_KKK");
        var_dump($zzz);
        echo '<br/><br/><br/>';

        $memcache = new Memcache; 
        $memcache->connect('127.0.0.1', 11211) or die ("Could not connect"); 

        $version = $memcache->getVersion(); 
        echo "Server's version: ".$version."<br/>\n"; 

        $tmp_object = new stdClass; 
        $tmp_object->str_attr = 'test'; 
        $tmp_object->int_attr = 123; 

        $memcache->set('key', $tmp_object, false, 10) or die ("Failed to save data at the server");
        echo "Store data in the cache (data will expire in 10 seconds)<br/>\n";

        $get_result = $memcache->get('key');
        echo "Data from the cache:<br/>\n";

        var_dump($get_result);
        echo "<br/><br/><br/><br/>";

        $this->load->model('testmcmodel');
        //$this->load->helper('url');
        $this->testmcmodel->testMc();
        echo "<br>view-end";
        // $data = "testmc";
        // $this->load->view('testmcview',$data);
    }
}