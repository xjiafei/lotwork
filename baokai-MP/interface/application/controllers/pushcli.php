<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Pushcli extends CI_Controller {
    private $appleProxyURL = 'http://app.proxy.wa/applePushProxy.php';
    private $androidProxyURL = 'http://app.proxy.wa/androidPushProxy.php';
    private $request;

    public function __construct()
    {
        parent::__construct();
        $this->request = json_decode(($_POST['content']?$_POST['content']:file_get_contents('php://input')), true);
    }

    public function addPushMsg()
    {
        $request = $this->request;
        if(!$request)
        {
            echo '{"content":"no request data", "messagetype":-1000}';
            die;
        }
        if(!$request['app_id'])
        {
            echo '{"content":"app_id field error", "messagetype":-1010}';
            die;
        }
        if(!$request['pushmsg'])
        {
            echo '{"content":"pushmsg field error", "messagetype":-1020}';
            die;
        }
        $insertData = array(
            'pushmsg' => $request['pushmsg'],
            'triggertime' => $request["triggertime"]?$request["triggertime"]:date("Y-m-d H:i:s"),
            'createtime' => date("Y-m-d H:i:s"),
            'app_id' => $request['app_id'],
            'deviceidtype' => $request["deviceidtype"]?$request["deviceidtype"]:0
        );
        $this->load->model('Pushcli_model');
        if ($this->Pushcli_model->addPushMsg($insertData))
            echo '{"content":"success", "messagetype":100}';
        else
            echo '{"content":"insert faild", "messagetype":-1030}';
    }
    
    public function doPushProcess()
    {
        $this->load->model('Pushcli_model');
        $event = $this->Pushcli_model->getPushEvent();
        if(!count($event))
        {
            if(!$this->input->is_cli_request())
                echo '{"content":"no push cli", "messagetype":100}';
            die;
        }
        $id = $event['id'];
        $this->load->model('Push_model');
        $devices = $this->Push_model->getPushList($event['app_id'], $event['deviceidtype']);
        $this->Pushcli_model->pushStartLog($id, count($devices));
        if(count($devices) == 0)
        {
            $this->Pushcli_model->pushFinishLog($id, 0);
            echo '{"content":"active device not found with app_id:'.$event['app_id'].'", "messagetype":100}'."\n";
            die;
        }
        
        switch ($event['app_id'])
        {
            case 1:
            //case 3:
            case 5:
            case 9:
                $this->pushAppleDevices($event, $devices, 'applepush_caipiao.pem');
                break;
            case 13:
                $this->pushAppleDevices($event, $devices, 'applepush_caipiao_bomao.pem');
                break;
            case 2:
            //case 4:
            case 6:
            case 10:
            case 14:
                $this->pushAndroidDevices($event, $devices);
                break;
            default:
                $error = "app_id:".$event['app_id']." not in switch case";
                $this->Pushcli_model->pushErrorLog($id, -100, $error);
                echo '{"content":"'.$error.'", "messagetype":-100}'."\n";
                break;
        }
    }
    
    public function pushAppleDevices($event, $devices, $pem)
    {
        $successCount = 0;
        $msg = $event['pushmsg'];
        foreach ($devices as $device)
        {
            //$result = $this->sendApplePush($device['deviceid'], $msg);
            $result = $this->sendApplePushProxy($device['deviceid'], $msg, $pem);
            if ($return != -1) $successCount++;
        }
        $this->Pushcli_model->pushFinishLog($event['id'], $successCount);
        echo '{"content":pushAppleDevices Finish('.$successCount.'/'.count($devices).')", "messagetype":200}'."\n";
    }
    
    public function pushAndroidDevices($event, $devices)
    {
        $successCount = 0;
        $msg = $event['pushmsg'];
        $batchArray = array();
        foreach ($devices as $device)
        {
            $batchArray[] = $device['deviceid'];
            if(count($batchArray) > 9)
            {
                //$result = $this->sendAndroidPush($batchArray, $msg);
                $result = $this->sendAndroidPushProxy($batchArray, $msg);
                $successCount += $result;
                $batchArray = array();
            }
        }
        if(count($batchArray) > 0)
        {
            //$result = $this->sendAndroidPush($batchArray, $msg);
            $result = $this->sendAndroidPushProxy($batchArray, $msg);
            $successCount += $result;
        }
        $this->Pushcli_model->pushFinishLog($event['id'], $successCount);
        echo '{"content":pushAndroidDevices Finish('.$successCount.'/'.count($devices).')", "messagetype":200}'."\n";
    }
    
    function sendApplePush($deviceID, $message)
    {
        $pushkeyfile = './application/controllers/applepush_caipiao.pem';
        //$pushkeyfile;
        //switch ($event['app_id'])
        //{
        //    case 1:
        //        $pushkeyfile = './application/controllers/applepush_caipiao.pem';
        //        break;
        //    case 5:
        //        $pushkeyfile = './application/controllers/applepush_kefu.pem';
        //        break;
        //    default:
        //        break;
        //}
        if(!file_exists($pushkeyfile))
        {
		$error = "app_id:".$event['app_id']." apple push key not found[".$pushkeyfile."]";
		$this->load->model('Pushcli_model');
		$this->Pushcli_model->pushErrorLog($id, -100, $error);
		echo '{"content":"'.$error.'", "messagetype":-100}'."\n";
		die;
        }
        $url = "ssl://gateway.push.apple.com:2195";
        $ctx = stream_context_create();
        $passphrase = "123qweasd";
	stream_context_set_option($ctx, 'ssl', 'local_cert', $pushkeyfile);
	stream_context_set_option($ctx, 'ssl', 'passphrase', $passphrase);
	$fp = stream_socket_client($url
				, $err
				, $errstr
				, 60
				, STREAM_CLIENT_CONNECT|STREAM_CLIENT_PERSISTENT
				, $ctx);
        if (!$fp) return -1;
        $body['aps'] = array('alert' => $message, 'sound' => 'default');
        $payload = json_encode($body);
        $msg = chr(0).pack('n', 32).pack('H*', $deviceID).pack('n', strlen($payload)).$payload;
        $result = fwrite($fp, $msg, strlen($msg));
        fclose($fp);
        return $result;
    }
    
    function sendApplePushProxy($deviceID, $message, $pem)
    {
        $this->curl->create($this->appleProxyURL);
        $this->curl->post(json_encode(array('deviceID' => $deviceID, 'message' => $message, 'pem_file' => $pem)));
        $result = $this->curl->execute();
        return (int)$result;
    }
    
    function proxyAppleDemo1()
    {
        echo 'ph______'.$this->appleProxyURL.'______';
        $this->curl->create($this->appleProxyURL);
        $this->curl->post(json_encode(array('deviceID' => '5dcf3405d5b174e233f6496c8d9d80c088fb37fa90cd961179276e1f1220da3c',
            'message' => 'hello___'.date("Y-m-d H:i:s"), 'pem_file' => 'applepush_caipiao.pem')));
       echo $this->curl->execute();
    }
    
    function proxyAppleDemo2()
    {
        echo 'bomao______'.$this->appleProxyURL.'______';
        $this->curl->create($this->appleProxyURL);
        $this->curl->post(json_encode(array('deviceID' => '0989e7b0a9bd187ceb9daabb9b731e293bf1ee1d9426be2ba8e1f5bb5ab9a6ac',
            'message' => 'hello___'.date("Y-m-d H:i:s"), 'pem_file' => 'applepush_caipiao_bomao.pem')));
       echo $this->curl->execute();
    }
    
    function sendAndroidPush($deviceIDs, $message)
    {
        $fields = array('registration_ids' => $deviceIDs, 'data' => array('message' => $message));
        $headers = array('Content-Type: application/json',
                    'Authorization: key=AIzaSyDhHHhwioLwheyWlQNw_oR67T-wRTrpR4o');
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, "https://android.googleapis.com/gcm/send");
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
        $result = curl_exec($ch);
        curl_close($ch);
 
        $jresult = json_decode($result, true);
        //echo var_export ($jresult, 1);
        return $jresult['success']?$jresult['success']:0;
    }
    
    function sendAndroidPushProxy($deviceIDs, $message)
    {
        $this->curl->create($this->androidProxyURL);
        $this->curl->post(json_encode(array('deviceIDs' => $deviceIDs, 'message' => $message)));
        $result = $this->curl->execute();
        $jresult = json_decode($result, true);
        //echo var_export ($jresult, 1);
        return $jresult['success']?$jresult['success']:0;
    }
    
    function proxyAndroidDemo()
    {
        echo $this->androidProxyURL.'______';

        $this->curl->create($this->androidProxyURL);
        $this->curl->post(json_encode(array('deviceIDs' => 
            array('APA91bFsQtaYblXInkev5PsUVZkCRd2HIK1ko-lBGsqGJDRufFyR8ElQkp-3Yqp21iiurAlz168zi-AvPESGvPtwyJycUCa6TePRfomJzEAbSVlkudp_j0dKyWtP9mwcl0KDMT7TNs2VGdxPc9TBJbv3Ld4u3nEwDw'),
            'message' => 'hello___'.date("Y-m-d H:i:s"))));
        $result = $this->curl->execute();
        var_dump($result);
        //$jresult = json_decode($result, true);
        //echo var_export ($jresult, 1);
        //return $jresult['success']?$jresult['success']:0;
    }
    
    function sendjPush($deviceID, $message)
    {
        return 0;
    }
    
    function pushInfos()
    {
        $this->load->model('Push_model');
        if (version_compare(phpversion(), '5.4', '<'))
            echo json_encode($this->Push_model->getPushInfoList());
        else
        {
            $order  = array("\r\n", "\n", "\r");
            $replace = '<br />';
            echo str_replace($order, $replace, json_encode($this->Push_model->getPushInfoList(), JSON_PRETTY_PRINT));
        }
    }
    
    function pushClis()
    {
        $this->load->model('Pushcli_model');
        if (version_compare(phpversion(), '5.4', '<'))
            echo json_encode($this->Pushcli_model->getPushCliList());
        else
        {
            $order  = array("\r\n", "\n", "\r");
            $replace = '<br />';
            echo str_replace($order, $replace, json_encode($this->Pushcli_model->getPushCliList(), JSON_PRETTY_PRINT));
        }
    }
    
    public function import30PushData()
    {
        $this->load->model('Push_model');
        $array = json_decode(file_get_contents(APPPATH."controllers/applepush.txt"));
        $record = 0;
        foreach ($array as $value)
        {
            $value->lastregister = date("Y-m-d H:i:s");
            $record += $this->Push_model->insertUserPushInfo($value);
        }
        echo 'applepush Records: '.$record;
            
        echo '----------';
            
        $array = json_decode(file_get_contents(APPPATH."controllers/androidpush.txt"));
        $record = 0;
        foreach ($array as $value)
        {
            $value->lastregister = date("Y-m-d H:i:s");
            $record += $this->Push_model->insertUserPushInfo($value);
        }
        echo 'androidpush Records: '.$record;
    }
}