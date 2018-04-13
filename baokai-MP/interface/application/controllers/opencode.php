<?php

class Opencode extends CI_Controller {
	private $checkSetCodeURL = 'http://draw.ff/e_c_service_set_codes/checkSetCodePro';
        private $selfRefererURL = 'http://ios1.phl5b.org/opencode/pushopencode';
        private $request;
        
	public function __construct()
	{
            parent::__construct();
            $this->request = $this->input->post();
            if(!$this->request) parse_str(file_get_contents('php://input'), $this->request);
        }
	
        public function pushOpencode()
        {
            if(!$this->request)
            {
                echo '{"content":"no request data", "messagetype":-1000}';
                file_put_contents('/var/tmp/pushopencode_php.log', '{"content":"no request data", "messagetype":-1000}');
                die;
            }
            file_put_contents('/var/tmp/pushopencode_php.log', var_export($this->request, 1));
            $check = $this->checkPostData();
            
            if ($check && is_array($check) && array_key_exists(0, $check) && is_array($check[0])) {
                //insert to db
                $this->load->model('Opencode_model');
////                $res = $this->Opencode_model->addOpencode($check[0]);
                
////		if ($res) {
                if (true) {
////                    $logId = $res;
                    $logId = $check[0]['record_id'];
                    
                    //echo  'insert local db sucess';
                    $postData = array();
                    $postStr = "";
                    foreach ($check[1] as $key => $value) {
                        $postData[$key] = $value;
                        $postStr .= $key."=".$value."&";
                        if ($key == "time") {
                            $postData['logId'] = $logId;
                            $postStr .= "logId=".$logId."&";
                        }
                    }

                    $postData['safestr'] = md5(substr($postStr, 0, -1));

                    $post_content = $postData;
////                    $this->code2Push($postData);
                    $this->curlPost($this->checkSetCodeURL, $post_content);
                }else {
                    //print_r($check);
                    echo 'insert local db fail';
                    die();
                }
            } else {
                echo $check.'data error';
                die();
            }
        }
        
        private function checkPostData() {
            $fieldArray = array();
            $postData = array();
            $strCheck = "";
            if (isset($this->request)) {
                if (!isset($this->request['customer']) || strlen($this->request['customer']) != 32) {
                    return 0;
                } else {
                    $strCheck .= "customer=".$this->request['customer']."&";
                    $fieldArray['customer'] = $this->request['customer'];
                    $postData['customer'] = $this->request['customer'];
                }
                
                if (!isset($this->request['recordId']) || $this->request['recordId'] == "") {
                    return 1;
                } else {
                    $strCheck .= "recordId=".$this->request['recordId']."&";
                    $fieldArray['record_id'] = $this->request['recordId'];
                    $postData['recordId'] = $this->request['recordId'];
                }
                
                if (!isset($this->request['lottery']) || $this->request['lottery'] == "") {
                    return 2;
                } else {
                    $strCheck .= "lottery=".$this->request['lottery']."&";
                    $fieldArray['lottery'] = $this->request['lottery'];
                    $postData['lottery'] = $this->request['lottery'];
                }
                
                if (!isset($this->request['issue']) || $this->request['issue'] == "") {
                    return 3;
                } else {
                    $strCheck .= "issue=".$this->request['issue']."&";
                    $fieldArray['issue'] = $this->request['issue'];
                    $postData['issue'] = $this->request['issue'];
                }
                
                if (!isset($this->request['time']) || strlen($this->request['time']) != 14) {
                    return 4;
                } else {
                    $strCheck .= "time=".$this->request['time']."&";
                    $fieldArray['time'] = $this->request['time'];
                    $postData['time'] = $this->request['time'];
                }
                
                if (!isset($this->request['number']) || $this->request['number'] == "") {
                    return 5;
                } else {
                    $strCheck .= "number=".$this->request['number']."&";
                    $fieldArray['number'] = $this->request['number'];
                    $postData['number'] = $this->request['number'];
                }
                
                if (!isset($this->request['verifiedTime']) || strlen($this->request['verifiedTime']) != 14) {
                    return 6;
                } else {
                    $strCheck .= "verifiedTime=".$this->request['verifiedTime']."&";
                    $fieldArray['verified_time'] = $this->request['verifiedTime'];
                    $postData['verifiedTime'] = $this->request['verifiedTime'];
                }
                
                if (!isset($this->request['earliestTime']) || strlen($this->request['verifiedTime']) != 14) {
                    return 7;
                } else {
                    $strCheck .= "earliestTime=".$this->request['earliestTime']."&";
                    $fieldArray['earliest_time'] = $this->request['earliestTime'];
                    $postData['earliestTime'] = $this->request['earliestTime'];
                }
                
                if (!isset($this->request['stopSaleTime']) || strlen($this->request['verifiedTime']) != 14) {
                    return 8;
                } else {
                    $strCheck .= "stopSaleTime=".$this->request['stopSaleTime']."&";
                    $fieldArray['stopsale_time'] = $this->request['stopSaleTime'];
                    $postData['stopSaleTime'] = $this->request['stopSaleTime'];
                }
                
                if (!isset($this->request['drawingTime']) || strlen($this->request['verifiedTime']) != 14) {
                    return 9;
                } else {
                    $strCheck .= "drawingTime=".$this->request['drawingTime'] ;
                    $fieldArray['drawing_time'] = $this->request['drawingTime'];
                    $postData['drawingTime'] = $this->request['drawingTime'];
                }
                
                if (!isset($this->request['safestr']) || strlen($this->request['safestr']) != 32) {
                    return 10;
                } else {
                    //$strCheck .= "drawingTime=".$this->request['drawingTime']."&";
                    $fieldArray['safestr'] = $this->request['safestr'];
                    //$postData['safestr'] = $this->request['safestr'];
                }
            } else {
                return 11;
            }
            
            //check the safestr
            if (md5($strCheck) != $this->request['safestr']) {
                //return 12;
            }
            return array($fieldArray, $postData);
        }

        private function curlPost($url, $post_content)
        {
            $this->curl->create($url);
            //$this->curl->http_header( "X-Forwarded-For" , $_SERVER['REMOTE_ADDR'] );
            $this->curl->http_header("Referer", $this->selfRefererURL);
            $this->curl->post($post_content);
            $return = $this->curl->execute(); //N16383 查询进程真实有效
            file_put_contents('/var/tmp/pushopencode_php2.log', var_export($return, 1));
            //echo $this->curl->error_code;
            //echo $this->curl->error_string;
            echo 'N4';//  N4 号码已接收，无需再次推送
        }
        
        public function getOpenCode()
        {
        	if(!$this->input->is_cli_request())
        	{
			echo '{"content":"no push cli", "messagetype":100}';
			die;
		}
		echo "execute getOpenCode";
        	
        	$url = FRONT_LOGIN . "&come_from=3";
        	
		$data = array();
		$data["flag"] = "login";
		$data["username"] = "app023";
		$data["loginpass_source"] = "46f94c8de14fb36680850768ff1b7f2a";
		$data["loginpass"] = "4d3d4e9b019ffb43e0f514f25c5a8159";
		
		$post['_POST_DATA'] = $data;
		$post_content['content'] = json_encode($post);
	
		$this->curl->create($url);
		$this->curl->post($post_content);
		$this->curl->http_header( "X-Forwarded-For" , $_SERVER['REMOTE_ADDR'] );
		$rs = $this->curl->execute();
		
		$json = json_decode(trim($rs),true);
		
		if($json["token"])
		{
			$urlLow = INFORMATION_HISTORY_INFO_LOW . "&token=" . $json["token"] . "&come_from=3";
			
			$dataLow = array();
			$dataLow["CGISESSID"] = $json["token"];
			$postLow['_POST_DATA'] = $dataLow;
			$post_contentLow['content'] = json_encode($postLow);
			
			$this->curl->create($urlLow);
			$this->curl->post($post_contentLow);
			$this->curl->http_header( "X-Forwarded-For" , $_SERVER['REMOTE_ADDR'] );
			$rsLow = $this->curl->execute();
			
			$jsonLow = json_decode(trim($rsLow),true);
			
			$lotteryid = "";
			$lottery = "";
			$lotteryname = "";
			foreach ($jsonLow as $key => $value)
			{
				if($lotteryid != $value["lotteryid"])
				{
					$lotteryid = $value["lotteryid"];
					if($lotteryid == "1")
					{
						$lottery = "F3D";
						$lotteryname = "3D";
					}
					else if($lotteryid == "2")
					{
						$lottery = "PLW";
						$lotteryname = "P5";
					}
					else if($lotteryid == "3")
					{
						$lottery = "SSQ";
						$lotteryname = "双色球";
					}
					
			                $this->load->model('Opencode_model');
			                
			                if($value["code"] != "")
			                {
				                if(!$this->Opencode_model->isExisted($lottery, $value["issue"]))
				                {
				                	$dataOpencode = array(
						                'customer' => "",
						                'record_id' => "0",
						                'lottery' => $lottery,
						                'issue' => $value["issue"],
						                'time' => date("Y-m-d H:i:s"),
						                'number' => $value["code"],
						                'verified_time' => date("Y-m-d H:i:s"),
						                'earliest_time' => date("Y-m-d H:i:s"),
						                'stopsale_time' => date("Y-m-d H:i:s"),
						                'drawing_time' => $value["time"],
						                'safestr' => ""
						            );
				                	$logId = $this->Opencode_model->addOpencode($dataOpencode);
	                				if ($logId) {
	                					$dataCode = array(
								                'lottery' => $lottery,
								                'issue' => $value["issue"],
								                'number' => $value["code"]
								            );
	                					
								$this->code2Push($dataCode);
	                				}
				                }
				        }
				}
			}
		}
        }
        

        private function code2Push($codeInfo)
        {
            $lot = $codeInfo['lottery'];
            $lotName = NULL;
            switch ($lot) {
                case 'JLFFC':
                    //$lotName = '宝开1分彩';
                    break;
                case 'CQSSC':
                    //$lotName = '重庆时时彩';
                    break;
                case 'F3D':
                    $lotName = '3D';
                    break;
                case 'PLW':
                    $lotName = 'P5';
                    break;
                case 'SSQ':
                    $lotName = '双色球';
                    break;
                default:
                    break;
            }
            if($lotName)
            {
                $pushMsg = $lotName.' 第'.$codeInfo['issue'].'期开奖号码为 '.$codeInfo['number'];
                $this->insertPush(1, $pushMsg);
                $this->insertPush(2, $pushMsg);
                $this->insertPush(9, $pushMsg);
                $this->insertPush(10, $pushMsg);
            }
        }

        private function insertPush($app_id, $pushmsg)
        {
            $insertData = array(
                'pushmsg' => $pushmsg,
                'triggertime' => date("Y-m-d H:i:s"),
                'createtime' => date("Y-m-d H:i:s"),
                'app_id' => $app_id,
                'deviceidtype' => 0
            );
            $this->load->model('Pushcli_model');
            $this->Pushcli_model->addPushMsg($insertData);
        }
}
?>