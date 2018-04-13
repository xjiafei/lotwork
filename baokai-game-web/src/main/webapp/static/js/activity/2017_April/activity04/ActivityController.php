<?php
/**
 * 活动页面
 *
 */
class Default_ActivityController extends CustomControllerAction{
	
	private $_oActivityModel;
	public function init(){
		parent::init();
		$this->_oActivityModel = new Activity();
		$this->_clientObject = new Client();
	}
	
	private function get_client_ip()
	{
		$ip = "";
		if (!empty($_SERVER["HTTP_CLIENT_IP"])){
			$ip = $_SERVER["HTTP_CLIENT_IP"];
		}
		else if (!empty($_SERVER["HTTP_X_FORWARDED_FOR"])) {
                $ip = $_SERVER["HTTP_X_FORWARDED_FOR"];
		} else {
			$ip = $_SERVER["REMOTE_ADDR"];
		}
		return $ip;
	}
	
	//首页入口
	public function indexAction(){
		$this->_redirect('/activity/newuserprize/');
	}
	
	private function initToken($token){
		$token = str_replace("-","/", $token);
		$token = str_replace("$","%", $token);
		$token = str_replace("!","=", $token);
		$token = str_replace("*","+", $token);
		return $token;
	}
	
	//活动 宝开新生-送礼金
	public function newuserprizeAction(){
		
		$startTime = '2014-12-15';
		$iWeek = $isEnabled = 0;
		$msg ='';
		$result = array();
		//是否在活动期内
		if(time()>=strtotime($startTime)&&time()<strtotime($startTime)+28*86400){
			$isEnabled = 1;
		} else if(time()<strtotime($startTime)){
			$isEnabled = -1;
			$msg = '活动尚未开始!';
		} else if(time()>strtotime($startTime)+28*86400-1){
			$isEnabled = -2;
			$msg = '活动已结束!';
		}
		if($isEnabled !=0 && time()>=strtotime($startTime)){
			$param['id'] = $this->_sessionlogin->info['id'];
			$result= $this->_oActivityModel->newusrePrize($param);
			if(isset($result['lastTime'])){
				$lastTime = $result['lastTime'];
				$diffTime = ($lastTime-strtotime($startTime))/86400;
				$iWeek    = intval(($diffTime)/7 +1);
			}
			//不在活动期内完成调查问卷 不算完成任务
			if(isset($result['testTime'])){
				if($result['testTime']>strtotime($startTime)+28*86400-1){
					$result['testTime'] = '';
				}
			}
		}
		
		//完成时间以及奖励
		for($i=1;$i<=4;$i++){
			$completeTime[$i]['startTime']=date('m.d',strtotime($startTime)+($i-1)*7*86400);
			$completeTime[$i]['endtTime'] =date('m.d',strtotime($startTime)+$i*7*86400-1);
			if($i<4){
				$completeTime[$i]['bonus'] = 50-($i-1)*15;
			}else {
				$completeTime[$i]['bonus'] = 15;
			}
		}
		
		$endTime   = date('Y.m.d',strtotime($startTime)+28*86400-1);
		$startTime = date('Y.m.d',strtotime($startTime));
		$this->view->result = $result;
		$this->view->completeTime = $completeTime;
		$this->view->iWeek  = $iWeek;
		$this->view->msg  = $msg;
		$this->view->isEnabled  = $isEnabled;
		$this->view->startTime = $startTime;
		$this->view->endTime = $endTime;
		$this->view->title = "宝开新生-送礼金";
		$this->view->display('default/ucenter/activity/newuser_prize.tpl');
	}
	
	//2015充值活动
	public function charger2015Action(){
		
		$this->view->display('default/ucenter/activity/2015_charger.tpl');
	}

	/*
	//每日抽獎 送0.1~1.0元
    public function dailybonusAction()
    {
        $dailyBonus = new Rediska_Key(md5('dailyBonus'.$this->_sessionlogin->info['id']));
        $dailyBonus_day = $dailyBonus->getValue(); // // 最後抽獎日期小於今天 0:未抽奖 1:已抽奖
        if ( $dailyBonus_day == '' ||  date("Y-m-d") > $dailyBonus_day ) {
            // 取IP值
            if (!empty($_SERVER["HTTP_CLIENT_IP"])){
                $ip = $_SERVER["HTTP_CLIENT_IP"];
            }elseif(!empty($_SERVER["HTTP_X_FORWARDED_FOR"])){
                $ip = $_SERVER["HTTP_X_FORWARDED_FOR"];
            }else{
                $ip = $_SERVER["REMOTE_ADDR"];
            }

            // 同IP如果有100个帐号，最多只能有三个帐号砸蛋
            $dailyIP = new Rediska_Key(md5('dailyBonus'.$ip.date("Y-m-d")));
            if ( $dailyIP->getValue() >= 3 ) {
                $result = array('msg' => '0', 'error' => '您今天已经参与过砸蛋活动了！');
                echo Zend_Json::encode($result);
                exit;
            }
            $param['ip'] = $ip;
            $param['ip_num'] = (int)$dailyIP->getValue();
        }
        else {
            $result = array('msg' => '0', 'error' => '您今天已经参与过砸蛋活动了！');
            echo Zend_Json::encode($result);
            exit;
        }

        $param['userId']    = $this->_sessionlogin->info['id'];
        $result = $this->_oActivityModel->dailybonus($param);

        echo Zend_Json::encode($result);
    }
    */
	
	//2015-09 大富翁活動
	public function monopolycheckAction()	//僅檢查是否抽過獎但不會扔骰子
	{
		//print_r($this->_sessionlogin->info);
		
		//js 所需的結果架構
		$result = array(
			'status' => 0,	//今日是否抽過獎（0:今日已參加過 1:今日尚未參加過 2:此IP於今日參加次數已滿 ）
			'isSuccess' => 1,	//是否成功（1:成功 0:失敗）
			'historyPrize' => '',	//歷史獎金
			'historyPoints' => '',	//歷史點數和
			'dailyBigPrizeAry' => array(),	//今日中大獎名單
		);
		
		//檢查是否再活動期間內
		$now = date("Y-m-d H:i:s");
		//取得活動上架時間
		$activity_begin_time = Zend_Registry::get('config')->daily_activity_begin_time;
		$activity_end_time = Zend_Registry::get('config')->daily_activity_end_time;
		if( ($now < $activity_begin_time) || ($now > $activity_end_time) )
		{
			//不在活動期間內
			echo json_encode($result);
			exit;
		}
		
		$ip = $this->get_client_ip();	//玩家IP
		$user_id = $this->_sessionlogin->info['id'];	//玩家編號
		$user_acc = $this->_sessionlogin->info['account'];	//玩家帳號
		$today = date("Y-m-d");	//今天日期
		$yesterday = date("Y-m-d", time()-86400);	//昨天日期	
		//echo $today."<br>";
		
		//取得此帳號最後的抽獎日期
		$dailyBonus_day = $this->redis_client->get(md5('dailyBonus' . $user_id));
		//echo '此帳號最後的抽獎日期 => ' . $dailyBonus_day . '<br>';
		
		//取得此IP 於今日的抽獎次數
		$dailyIP_num = $this->redis_client->get(md5('dailyBonus' . $ip . $today));
		//echo '取得此IP 於今日的抽獎次數 => ' . $dailyIP_num . '<br>';
		
		//今日中大獎名單,若key不存在會回傳空陣列
		$result['dailyBigPrizeAry'] = $this->redis_client->sMembers(md5('dailyBigPrize' . $today));
		//若今天的玩家數量未滿20位則抓昨天的名單來補
		if(count($result['dailyBigPrizeAry']) < 20)
		{
			$fetch_num = 20 - count($result['dailyBigPrizeAry']);
			$tmp_ary = $this->redis_client->sMembers(md5('dailyBigPrize' . $yesterday));
			$tmp_player = array_slice($tmp_ary, 0, $fetch_num);
			$result['dailyBigPrizeAry'] = array_merge($result['dailyBigPrizeAry'], $tmp_player);
		}
		//echo '今日中大獎名單 => ' . print_r($result['dailyBigPrizeAry'], true) . '<br>';
		
		/*
		//取得最後的獲得金額 與 獲得點數和
		$result['historyPrize'] = $this->redis_client->get(md5('dailyBonusPrize' . $user_id));
		$result['historyPoints'] = $this->redis_client->get(md5('dailyBonusPoint' . $user_id));
		echo '最後的獲得金額 => ' . $result['historyPrize'] . '<br>';
		echo '最後的獲得點數和 => ' . $result['historyPoints'] . '<br>';
		
		$hour = date("H");	//目前的小時
		$num = $this->redis_client->hGet(md5('dailyBigPrizeHour'.$today), $hour);
		echo '這個小時中大獎的人數 => ' . $num . '<br>';
		*/
		
		
		//--- 抽獎資格檢查 Start ---
		//檢查今天是否已經抽過獎
		if($dailyBonus_day >= $today)
		{
			//此帳號今天抽過了
			$result["status"] = 0;
			//取得最後的獲得金額 與 獲得點數和
			$result['historyPrize'] = $this->redis_client->get(md5('dailyBonusPrize' . $user_id));
			$result['historyPoints'] = $this->redis_client->get(md5('dailyBonusPoint' . $user_id));
			echo json_encode($result);
			exit;
		}
		
		//根據 config 檔案決定是否檢查ip
		$activity_check_ip = Zend_Registry::get('config')->daily_activity_check_ip;
		if($activity_check_ip)
		{
			if($dailyIP_num >= 3)	//已滿3次
			{
				$result["status"] = 2;
				die(json_encode($result));
			}
		}
		//--- 抽獎資格檢查 End ---
		
		
		//今日尚未抽獎
		$result["status"] = 1;
		
		echo json_encode($result);
	}
	
	public function monopolyAction()
	{
		//print_r($this->_sessionlogin->info);
		
		//js 所需的結果架構
		$result = array(
			'status' => 0,	//今日是否抽過獎（0:今日已參加過 1:今日尚未參加過 2:此IP於今日參加次數已滿 ）
			'isSuccess' => 0,	//是否成功（1:成功 0:失敗）
			'prize' => '',	//獎金
			'points' => array(),	//骰子點數陣列
			'historyPrize' => '',	//歷史獎金
			'historyPoints' => '',	//歷史點數和
			'dailyBigPrizeAry' => array(),	//今日中大獎名單
		);
		
		//寫一個 Lock key (第一個會成功True 在極短時間內第二個會失敗False)
		if($this->redis_client->setnx(md5('dailyLock' . $user_id), 1))
		{
			//設定存活時間
			$this->redis_client->expire(md5('dailyLock' . $user_id), 3);
		}
		else
		{
			echo json_encode($result);
			exit;
		}
		
		//檢查是否再活動期間內
		$now = date("Y-m-d H:i:s");
		//取得活動上架時間
		$activity_begin_time = Zend_Registry::get('config')->daily_activity_begin_time;
		$activity_end_time = Zend_Registry::get('config')->daily_activity_end_time;
		if( ($now < $activity_begin_time) || ($now > $activity_end_time) )
		{
			//不在活動期間內
			echo json_encode($result);
			exit;
		}
		
		$ip = $this->get_client_ip();	//玩家IP
		$user_id = $this->_sessionlogin->info['id'];	//玩家編號
		$user_acc = $this->_sessionlogin->info['account'];	//玩家帳號
		$today = date("Y-m-d");	//今天日期
		$yesterday = date("Y-m-d", time()-86400);	//昨天日期	
		//echo $today."<br>";
		
		//取得此帳號最後的抽獎日期
		$dailyBonus_day = $this->redis_client->get(md5('dailyBonus' . $user_id));
		//echo '此帳號最後的抽獎日期 => ' . $dailyBonus_day . '<br>';
		
		//取得此IP 於今日的抽獎次數
		$dailyIP_num = $this->redis_client->get(md5('dailyBonus' . $ip . $today));
		//echo '取得此IP 於今日的抽獎次數 => ' . $dailyIP_num . '<br>';
		
		//今日中大獎名單,若key不存在會回傳空陣列
		$result['dailyBigPrizeAry'] = $this->redis_client->sMembers(md5('dailyBigPrize' . $today));
		//若今天的玩家數量未滿20位則抓昨天的名單來補
		if(count($result['dailyBigPrizeAry']) < 20)
		{
			$fetch_num = 20 - count($result['dailyBigPrizeAry']);
			$tmp_ary = $this->redis_client->sMembers(md5('dailyBigPrize' . $yesterday));
			$tmp_player = array_slice($tmp_ary, 0, fetch_num);
			$result['dailyBigPrizeAry'] = array_merge($result['dailyBigPrizeAry'], $tmp_player);
		}
		//echo '今日中大獎名單 => ' . print_r($result['dailyBigPrizeAry'], true) . '<br>';
		
		//--- 抽獎資格檢查 Start ---
		//檢查今天是否已經抽過獎
		if($dailyBonus_day >= $today)
		{
			//此帳號今天抽過了
			$result["status"] = 0;
			//取得最後的獲得金額 與 獲得點數和
			$result['historyPrize'] = $this->redis_client->get(md5('dailyBonusPrize' . $user_id));
			$result['historyPoints'] = $this->redis_client->get(md5('dailyBonusPoint' . $user_id));
			echo json_encode($result);
			exit;
		}
		
		//根據 config 檔案決定是否檢查ip
		$activity_check_ip = Zend_Registry::get('config')->daily_activity_check_ip;
		if($activity_check_ip)
		{
			if($dailyIP_num >= 3)	//已滿3次
			{
				$result["status"] = 2;
				die(json_encode($result));
			}
		}
		//--- 抽獎資格檢查 End ---

		//今日尚未抽獎
		$result["status"] = 1;
		
		//準備呼叫 java 所需參數
		$param = array(
			'ip' => $ip, 
			'ip_num' => $dailyIP_num,
			'userId' => $user_id,
		);
		//隨機抽獎並呼叫Java
		$prize_result = $this->_oActivityModel->monopoly($param);
		$result['isSuccess'] = $prize_result['isSuccess'];
		$result['prize'] = $prize_result['prize'];
		$result['points'] = $prize_result['points'];
		if($result['isSuccess'] == 1)	//呼叫Java成功後要記錄中獎資料
		{
			if((int)($result['prize']) >= 10)	//中了大獎要記錄下來
			{
				$user_mask_acc = mb_substr($user_acc, 0, 1, 'UTF-8') . "***" . mb_substr($user_acc, -1, 1, 'UTF-8');
				$this->redis_client->sAdd(md5('dailyBigPrize' . $today), $user_mask_acc);
				$this->redis_client->expire(md5('dailyBigPrize' . $today), 2*24*60*60);
				
				//這個小時的大獎數+1
				$this->redis_client->hIncrBy(md5('dailyBigPrizeHour'.$today), date("H"), 1);
				$this->redis_client->expire(md5('dailyBigPrizeHour'.$today), 2*24*60*60);
			}
			//記錄帳號最後抽獎日期
			$this->redis_client->set(md5('dailyBonus' . $user_id), $today, 2*24*60*60);
			
			//記錄 IP & 日期 當天抽獎次數
			$this->redis_client->IncrBy(md5('dailyBonus' . $ip . $today), 1);
			$this->redis_client->expire(md5('dailyBonus' . $ip . $today), 2*24*60*60);
			
			//記錄此帳號最後抽獎的中獎金額
			$this->redis_client->set(md5('dailyBonusPrize' . $user_id), $result['prize'], 2*24*60*60);
			
			//記錄此帳號最後抽獎的骰子點數和
			$this->redis_client->set(md5('dailyBonusPoint' . $user_id), array_sum($result['points']), 2*24*60*60);
		}
		
		// unlock key
		$this->redis_client->del(md5('dailyLock' . $user_id));
		
		echo json_encode($result);
	}
	
	public function rookietaskAction()
	{
		$userid = $this->_sessionlogin->info['id'];
		
		//介接新手任務API
		$data['param']['userId'] = intval($userid);
		$aUri['beginMession'] = 'NewMission';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$isNewMission = $res['body']['result']['isNewMission'];
		$isBindCard = $res['body']['result']['bindCardStatus'];
		/*if($res['body']['result']['isBindCard']=='Y')
		{
			
			$isBindCard=1;
		}
		else
		{
			
			$isBindCard=0;
		}*/
		$bindCardDay = sprintf("%d",$res['body']['result']['bindCardDay']);
		$missionDay = sprintf("%d",$res['body']['result']['missionDay']);
		//$isFirstChagre = $res['body']['result']['isFirstChagre'];
		if($res['body']['result']['isFirstChagre']=='Y')
		{
			
			$isFirstChagre =1;
		}
		else
		{
			$isFirstChagre =0;
		}
		//$isFirstWithdraw = $res['body']['result']['isFirstWithdraw'];
		if($res['body']['result']['isFirstWithdraw']=='Y')
		{
			$isFirstWithdraw=1;
		}
		else
		{
			$isFirstWithdraw =0;
		}
		$bindCardText = $res['body']['result']['newMission']['bindCardText'];
		$chargeText = $res['body']['result']['newMission']['chargeText'];
		$withdrawText = $res['body']['result']['newMission']['withdrawText'];
		$dayBetFactor =  getMoneyFomat($res['body']['result']['newMission']['dayBetFactor']/$this->_moneyUnit,0);
		
		
		/*print_r($res);
		exit;*/
		
		//介接日常任務API
		$datadaily['param']['userId'] = intval($userid);
		$aUridaily['beginMession'] = 'DaillyMission';
		$resdaily = $this->_clientObject->inRequestV2($datadaily, $aUridaily);
		$tolBets1 = $resdaily['body']['result']['tolBets'][0];
		$tolBets2 = $resdaily['body']['result']['tolBets'][1];
		$tolBets3 = $resdaily['body']['result']['tolBets'][2];
		$tolBetDay = $resdaily['body']['result']['tolBetDay'];
		$betDayList = $resdaily['body']['result']['betDayList'];
		$dayBests1 = $resdaily['body']['result']['dayBests'][0];
		$dayBests2 = $resdaily['body']['result']['dayBests'][1];
		$dayBests3 = $resdaily['body']['result']['dayBests'][2];
		$dayBests4 = $resdaily['body']['result']['dayBests'][3];
		$dayBests5 = $resdaily['body']['result']['dayBests'][4];
		
		/*print_r($resdaily);
		exit;*/
		
		if($resdaily['body']['result']['dayBests'][0]['isAmount']=='N')
		{
			$dayBests1['amount']=0;
		}
		if($resdaily['body']['result']['dayBests'][1]['isAmount']=='N')
		{
			$dayBests2['amount']=0;
		}
		if($resdaily['body']['result']['dayBests'][2]['isAmount']=='N')
		{
			$dayBests3['amount']=0;
		}
		if($resdaily['body']['result']['dayBests'][3]['isAmount']=='N')
		{
			$dayBests4['amount']=0;
		}
		if($resdaily['body']['result']['dayBests'][4]['isAmount']=='N')
		{
			$dayBests5['amount']=0;
		}
		
		if($resdaily['body']['result']['dayBests'][0]['isLottery']=='N')
		{
			$dayBests1['lottery']=0;
		}
		if($resdaily['body']['result']['dayBests'][1]['isLottery']=='N')
		{
			$dayBests2['lottery']=0;
		}
		if($resdaily['body']['result']['dayBests'][2]['isLottery']=='N')
		{
			$dayBests3['lottery']=0;
		}
		if($resdaily['body']['result']['dayBests'][3]['isLottery']=='N')
		{
			$dayBests4['lottery']=0;
		}
		if($resdaily['body']['result']['dayBests'][4]['isLottery']=='N')
		{
			$dayBests5['lottery']=0;
		}
		
		if(empty($tolBetDay))
		{
			$tolBetDay="0";
		}
		
		if(empty($resdaily['body']['result']['tolBets'][0]['isSuccess']))
		{
			
			$tolBets1['isSuccess']==0;
		}
		else
		{
			$tolBets1['isSuccess']==1;
		}
		
		if(empty($resdaily['body']['result']['tolBets'][1]['isSuccess']))
		{
			$tolBets2['isSuccess']==0;
		}
		else
		{
			$tolBets2['isSuccess']==1;
		}
		
		if(empty($resdaily['body']['result']['tolBets'][2]['isSuccess']))
		{
			$tolBets3['isSuccess']==0;
		}
		else
		{
			$tolBets3['isSuccess']==1;
		}
		
		if(empty($resdaily['body']['result']['dayBests'][0]['isSuccess']))
		{
			$dayBests1['isSuccess']=0;
		}
		if(empty($resdaily['body']['result']['dayBests'][1]['isSuccess']))
		{
			$dayBests2['isSuccess']=0;
		}
		if(empty($resdaily['body']['result']['dayBests'][2]['isSuccess']))
		{
			$dayBests3['isSuccess']=0;
		}
		if(empty($resdaily['body']['result']['dayBests'][3]['isSuccess']))
		{
			$dayBests4['isSuccess']=0;
		}
		if(empty($resdaily['body']['result']['dayBests'][4]['isSuccess']))
		{
			$dayBests5['isSuccess']=0;
		}
        if($resdaily['body']['result']['tolBets'][0]['isAmount']=='N')
        {
            if($resdaily['body']['result']['tolBets'][0]['lotteryType']=='0')
            {
				$type="铜";
            }elseif($resdaily['body']['result']['tolBets'][0]['lotteryType']=='1')
            {
                $type="银";
            }else
            {
                $type="金";
            }
			$countMoney1=$resdaily['body']['result']['tolBets'][0]['lottery']."次".$type."蛋";
        }
        if($resdaily['body']['result']['tolBets'][1]['isAmount']=='N')
        {
            if($resdaily['body']['result']['tolBets'][1]['lotteryType']=='0')
            {
				$type="铜";
            }elseif($resdaily['body']['result']['tolBets'][1]['lotteryType']=='1')
            {
                $type="银";
            }else
            {
                 $type="金";
            }
			$countMoney2=$resdaily['body']['result']['tolBets'][1]['lottery']."次".$type."蛋";
        }
        if($resdaily['body']['result']['tolBets'][2]['isAmount']=='N')
        {
            if($resdaily['body']['result']['tolBets'][2]['lotteryType']=='0')
            {
				$type="铜";
            }elseif($resdaily['body']['result']['tolBets'][2]['lotteryType']=='1')
            {
                $type="银";
            }else
            {
				 $type="金";
            }
			$countMoney3=$resdaily['body']['result']['tolBets'][2]['lottery']."次".$type."蛋";
        }
        if($resdaily['body']['result']['tolBets'][0]['isAmount']=='Y')
        {
          
			$countMoney1=$resdaily['body']['result']['tolBets'][0]['amount']."元";
        }
        if($resdaily['body']['result']['tolBets'][1]['isAmount']=='Y')
        {
          
			$countMoney2=$resdaily['body']['result']['tolBets'][1]['amount']."元";
        }
		if($resdaily['body']['result']['tolBets'][2]['isAmount']=='Y')
        {
          
			$countMoney3=$resdaily['body']['result']['tolBets'][2]['amount']."元";
        }
		//print_r($resdaily);
		//exit();
		
		//介接答題API
		$dataquestion['param']['userId'] = intval($userid);
		$aUriquestion['beginMession'] = 'DaillyQuestion';
		$resquestion = $this->_clientObject->inRequestV2($dataquestion, $aUriquestion);
		$answersDays = $resquestion['body']['result']['answersDays'];
		//$dayAnsFinish = $resquestion['body']['result']['dayAnsFinish'];
		if($resquestion['body']['result']['dayAnsFinish']=='Y')
		{
			$dayAnsFinish=1;
		}
		else
		{
			$dayAnsFinish=0;
		}
		$question1 = $resquestion['body']['result']['questionData'][0];
		$question2 = $resquestion['body']['result']['questionData'][1];
		$question3 = $resquestion['body']['result']['questionData'][2];
		$ansMoney = getMoneyFomat($resquestion['body']['result']['ansMoney']/$this->_moneyUnit,2);
		$dayques1 = $resquestion['body']['result']['dayques'][0];
		$dayques2 = $resquestion['body']['result']['dayques'][1];
		$dayques3 = $resquestion['body']['result']['dayques'][2];
		
		if(empty($answersDays))
		{
			$answersDays="0";
		}
		
		//print_r($resquestion);
		//exit();
		
		//介接蛋量資訊API
		$dataegg['param']['userId'] = intval($userid);
		$aUriegg['beginMession'] = 'gotoEggLottery';
		$resegg = $this->_clientObject->inRequestV2($dataegg, $aUriegg);
		$coppor = $resegg['body']['result']['lotteryMap']['coppor'];
		$silver = $resegg['body']['result']['lotteryMap']['silver'];
		$golden = $resegg['body']['result']['lotteryMap']['golden'];
		
		//print_r($resegg);
		//exit;
        $datain['param']['userId'] = intval($userid);
		$aUriin['beginMession'] = 'NewCharge';
		$resinfo = $this->_clientObject->inRequestV2($datain,$aUriin);//回傳結果,取數據資料*/
		
		$i = 0;
		
		for($i=0;$i<count($resinfo['body']['result']['charges']);$i++)
		{
			$boundsinfo[$i]['chargeAmt']=$resinfo['body']['result']['charges'][$i]['chargeAmt'];
			$boundsinfo[$i]['chargePreium']=$resinfo['body']['result']['charges'][$i]['chargePreium'];
			$boundsinfo[$i]['chargePer']=$resinfo['body']['result']['charges'][$i]['chargePer'];
			$boundsinfo[$i]['chargeFactor']=$resinfo['body']['result']['charges'][$i]['chargeFactor'];
			$boundsinfo[$i]['bettingDate']=$resinfo['body']['result']['charges'][$i]['bettingDate'];
		}
		
		if(empty($coppor))
		{
			$coppor="0";
		}
		
		if(empty($silver))
		{
			$silver="0";
		}
		
		if(empty($golden))
		{
			$golden="0";
		}
		
		//$isNewMission = '1';
		
		if($isNewMission=='Y')
		{
			//echo $isBindCard;
			//exit;
            $guide =  $this->redis_client->get(md5('rookieguide' . $userid));
		    $viewguide = json_decode($guide);
            $rookie = $viewguide->{'rookie'};
            if($rookie=='1')
		    {
			    $rookie=false;
		    }else
            {
                $rookie=true;
            }
            
            $guidearr['rookie'] = '1';	
            $guidedate = Zend_Json::encode($guidearr);
		
            $this->redis_client->set(md5('rookieguide' . $userid),$guidedate, 365*24*60*60);
		    
            $output = array();
       
	        $output =  array(
            'isSuccess' => 1,//$isNewMission,
            'message' => '成功',
            'data' => array(
		        //新手任務
				'countdown' => array(
                    'isbinded' => $isBindCard,//
                    'bankDays' => $bindCardDay,//
                    'gameDays' => $missionDay
                ),
                'mission' => array(
                    'tipsBank' => $bindCardText,
                    'tipsCard' => $chargeText,
                    'tipsWithdraw' => $withdrawText,
                    'isFinish1' => $isBindCard  ,
                    //'isFinish1' => '1'  ,
                    'isFinish2' => $isFirstChagre,
                    'isFinish3' => $isFirstWithdraw,
					
					'bindcardLink' => '/bindcard/',
                    'rechargeLink' => '/fund?type=1/',
                    'withdrawLink' => '/withdraw/',
                    'rechargeInfo' => $boundsinfo

                ),
                'daily' => array(
                    'isbinded'  => $isBindCard ,
					
                    'countDays' => $tolBetDay,//連續投注天數_user
                    'countDay1' => $tolBets1['tolbetDate'],//系統連續投注天數
                    'countDay2' => $tolBets2['tolbetDate'],//系統連續投注天數
                    'countDay3' => $tolBets3['tolbetDate'],//系統連續投注天數
			        'countMoney1' => $countMoney1,//系統連續投注天數獎勵
                    'countMoney2' => $countMoney2,//系統連續投注天數獎勵
                    'countMoney3' => $countMoney3,//系統連續投注天數獎勵
				    'levelfirst' => $tolBets1['isSuccess'],//連續投注天數達標
                    'levelsecond' =>$tolBets2['isSuccess'],//連續投注天數達標
                    'levelthird' => $tolBets3['isSuccess'],//連續投注天數達標
					'levelfirstState' => $tolBets1['successStatus'],
                    'levelsecondState' => $tolBets2['successStatus'],
                    'levelthirdState' => $tolBets3['successStatus'],
					'dayBetFactor' => $dayBetFactor,
				    'dateList' => array($betDayList[0],$betDayList[1],$betDayList[2],$betDayList[3],$betDayList[4],$betDayList[5],$betDayList[6],$betDayList[7],$betDayList[8],$betDayList[9],$betDayList[10],$betDayList[11],$betDayList[12],$betDayList[13],$betDayList[14],$betDayList[15],$betDayList[16],$betDayList[17],$betDayList[18],$betDayList[19],$betDayList[20]),//$betDayList,//2:尚未開獎,1:有投注,0:沒投注
				    //日常任務除答題之外的項目
                    'achievedList' => array(
                        array('standerd'=>$dayBests1['daybetAmount'],'times'=>$dayBests1['lottery'],'reward'=>$dayBests1['amount'],'type'=>$dayBests1['lotteryType'],'achieve'=>$dayBests1['isSuccess']),//true:已完成
                        array('standerd'=>$dayBests2['daybetAmount'],'times'=>$dayBests2['lottery'],'reward'=>$dayBests2['amount'],'type'=>$dayBests2['lotteryType'],'achieve'=>$dayBests2['isSuccess']),
                        array('standerd'=>$dayBests3['daybetAmount'],'times'=>$dayBests3['lottery'],'reward'=>$dayBests3['amount'],'type'=>$dayBests3['lotteryType'],'achieve'=>$dayBests3['isSuccess']),
                        array('standerd'=>$dayBests4['daybetAmount'],'times'=>$dayBests4['lottery'],'reward'=>$dayBests4['amount'],'type'=>$dayBests4['lotteryType'],'achieve'=>$dayBests4['isSuccess']),
                        array('standerd'=>$dayBests5['daybetAmount'],'times'=>$dayBests5['lottery'],'reward'=>$dayBests5['amount'],'type'=>$dayBests5['lotteryType'],'achieve'=>$dayBests5['isSuccess'])	
                    )
                ),
			    //問題
                'question' => array(
                    'isFinished'=> $dayAnsFinish,
                    'getMoney'=> $ansMoney,
                    'answersDays'=> $answersDays,//連續答題天數_user
                    'answersDay1'=> $dayques1['answerDate'],//系統連續答題銅蛋天數
                    'answersDay2'=> $dayques2['answerDate'],//系統連續答題銀蛋天數
                    'answersDay3'=> $dayques3['answerDate'],///系統連續答題金蛋天數
                    'answersMoney1'=> $dayques1['lottery'],//系統連續答題銅蛋數
                    'answersMoney2'=> $dayques2['lottery'],//系統連續答題銀蛋數
                    'answersMoney3'=> $dayques3['lottery'],//系統連續答題金蛋數
					'prizeType1' => $dayques1['lotteryCss'],//銅蛋
                    'prizeType2' => $dayques2['lotteryCss'],//銀蛋
                    'prizeType3' => $dayques3['lotteryCss'],//金蛋
                    'answerList'=>array(
                        array(
                        'title'=> $question1['title'],
                        'answer'=> array(
                            $question1['answer'][0],
                            $question1['answer'][1],
                            $question1['answer'][2]
                        ),
                        'correct'=> $question1['correct']
                        ),
                        array(
                        'title'=> $question2['title'],
                        'answer'=> array(
                            $question2['answer'][0],
                            $question2['answer'][1],
                            $question2['answer'][2]
                        ),
                        'correct'=> $question2['correct']
                        ),
					     array(
                         'title'=> $question3['title'],
                        'answer'=> array(
                            $question3['answer'][0],
                            $question3['answer'][1],
                            $question3['answer'][2]
                        ),
                        'correct'=> $question3['correct']
                        ),
                    )
                ),
			    //蛋量資訊
                'egg' => array(
                    'coppor'=> $coppor,
                    'silver'=> $silver,
                    'golden'=> $golden
                    ) ,
                'firstTime' => $rookie   
                ),
               
            );
           
		}
		
		else
		{
            $output = array();
       
	        $output =  array(
            'isSuccess' => 0,//$isNewMission,
            'message' => '失敗',
            'data' => array(
		       
                )
            );
			
		}
		 echo json_encode($output);
	}
	
	public function getquestionmoneyAction()
	{
		
		$userid = $this->_sessionlogin->info['id'];
		
		//介接API準備
		$data['param']['userId'] = intval($userid);
		$aUri['beginMession'] = 'answerward';
		$res = $this->_clientObject->inRequestV2($data, $aUri);//回傳結果,取money
		
		$ansMoney = $res['body']['result']['ansMoney'];
		$answersDays = $res['body']['result']['answersDays'];
		$isNewMission = $res['body']['result']['isNewMission'];
		$errorAwardFlag = $res['body']['result']['errorAwardFlag'];
		$bindCardStatus = $res['body']['result']['bindCardStatus'];
		
		if(empty($answersDays))
		{
			$answersDays="0";
		}
		
		//$round = rand(3,100) * 10000 ;
		$money = getMoneyFomat($ansMoney/$this->_moneyUnit,2);
		//$isNewMission=='0';
		
		if($isNewMission=='Y')
		{
			if($errorAwardFlag=='N')
			{
                $output = array();
                $output =  array(
                'isSuccess' => '1',
                'message' => '成功',
                'data' => array(
                    'money' => $money,
			        'answersDays'=>$answersDays//連續答題天數_user
			
                    ),
		
                );
		    }
			else
			{
				$output = array();
				if($bindCardStatus == 3){
					$output =  array(
							'isSuccess' => '0',
							'message' => '今日已答題完畢',
							'data' => array(
									'money' => '0.00',
									'answersDays'=>$answersDays//連續答題天數_user
										
							),
					);
				} else {
					$output =  array(
							'isSuccess' => '0',
							'message' => '完成绑卡任务，才能开启日常任务！',
							'data' => array(
									'money' => '0.00',
									'answersDays'=>$answersDays//連續答題天數_user
										
							),
					);
				}
				
                
			}
		}
		else
		{
			$output = array();
            $output =  array(
            'isSuccess' => '0',
            'message' => '您已失去参与活动资格',
            'data' => array(
                'money' => '0.00',
			    'answersDays'=>$answersDays//連續答題天數_user
			
                ),
		
            );
		}
        echo json_encode($output);
	}
	
	public function geteggmoneyAction()
	{
		
		$userid = $this->_sessionlogin->info['id'];
		$eggdata = $this->_request->getPost("eggdata");
		$eggobj = json_decode($eggdata);
		$model = $eggobj->{'eggType'}; 
		
		//介接API準備
		$data['param']['userId'] = intval($userid);
		$data['param']['lotteryType'] = intval($model);	//蛋種類
		$aUri['beginMession'] = 'eggLotteryAward';
		$res = $this->_clientObject->inRequestV2($data, $aUri);//回傳結果,取money
		$eggMoney = $res['body']['result']['lotteryAwardAmt'];
		$isNewMission = $res['body']['result']['isNewMission'];
		$errorAwardFlag = $res['body']['result']['errorAwardFlag'];
		$bindCardStatus = $res['body']['result']['bindCardStatus'];
		
		$result = array();
		
		$money = getMoneyFomat($eggMoney/$this->_moneyUnit,2);
		
		if($isNewMission=='Y')
        {
            if($errorAwardFlag=='N')
			{
			    $result =  array(
                            'isSuccess' => '1',
                            'message' => '成功',
                            'data' => array(
                                'money' => $money
                            )
                        );
            }
			else
		    {
		    	if($bindCardStatus == 3){
		    		$result =  array(
		    				'isSuccess' => '0',
		    				'message' => '砸蛋已完成',
		    				'data' => array(
		    						'money' => '0.00'
		    				)
		    		);
		    	} else {
		    		$result =  array(
		    				'isSuccess' => '0',
		    				'message' => '完成绑卡任务，才能开启日常任务！',
		    				'data' => array(
		    						'money' => '0.00'
		    				)
		    		);
		    	}
		    }
		}
		else
		{
			$result =  array(
                        'isSuccess' => '0',
                        'message' => '您已失去参与活动资格',
                        'data' => array(
                            'money' => '0.00'
                        )
                    );
		}
	    echo json_encode($result);
	}
		
	public function messioninfoAction()
	{
		$userid = $this->_sessionlogin->info['id'];
		
		//介接API準備
		$data['param']['userId'] = intval($userid);
		$aUri['beginMession'] = 'NewCharge';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		$i = 0;
		
		for($i=0;$i<count($res['body']['result']['charges']);$i++)
		{
			$boundsinfo[$i]['chargeAmt']=$res['body']['result']['charges'][$i]['chargeAmt'];
			$boundsinfo[$i]['chargePreium']=$res['body']['result']['charges'][$i]['chargePreium'];
			$boundsinfo[$i]['chargePer']=$res['body']['result']['charges'][$i]['chargePer'];
			$boundsinfo[$i]['chargeFactor']=$res['body']['result']['charges'][$i]['chargeFactor'];
			$boundsinfo[$i]['bettingDate']=$res['body']['result']['charges'][$i]['bettingDate'];
		}
		
		$this->view->boundsinfo = $boundsinfo;
		
		$this->view->display('default/ucenter/activity/messioninfo.tpl');
	}
	
	//11月紅包活動
	public function redbowAction(){
		$this->view->display('default/ucenter/activity/2016_novRedbow.tpl');
	}
	
	//11月紅包活動
	public function redbow2Action(){
		$this->view->display('default/ucenter/activity/2016_novRedbowTwo.tpl');
	}
	
	//11月紅包活動
	public function redbow3Action(){
		$this->view->display('default/ucenter/activity/2016_novRedbowThree.tpl');
	}
	
	 //11月紅包活動
	public function redbow4Action(){
		$this->view->display('default/ucenter/activity/2016_novRedbowFour.tpl');
	} 

	 //12月紅包活動
	public function decredbowAction(){
		$this->view->display('default/ucenter/activity/2016_decRedbow.tpl');
	} 
	//12月紅包活動 (第二周)
	public function decredbowtwoAction(){
		$this->view->display('default/ucenter/activity/2016_decredbowtwo.tpl');
	} 
	//12月紅包活動 (第三周)
	public function decredbowthreeAction(){
		$this->view->display('default/ucenter/activity/2016_decredbowthree.tpl');
	} 
	//12月紅包活動 (第四周)
	public function decredbowfourAction(){
		$this->view->display('default/ucenter/activity/2016_decredbowfour.tpl');
	} 
	
	//12月活動聚合頁 (第四周)
	public function querygrouptimesettingAction(){
		$aUri['activity'] = 'querygrouptime04';
		$res = $this->_clientObject->inRequestV2($data, $aUri);

		$configTimes = $res['body']['result']['configTimes'];//開獎時間定義
		
		
		echo json_encode($configTimes);
	}
	
	//12月活動聚合頁 (第四周)
	public function decgrouppageAction(){
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		
		$aUri['activity'] = 'decGroupQuery';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		$userRankingWins = $res['body']['result']['userRankingWins'];//已開獎名單
		$activityConfigs = $res['body']['result']['activityConfigs'];//活動設定檔
		$configTimes = $res['body']['result']['configTimes'];//開獎時間定義
		$isWined = $res['body']['result']['isWined'];//是否中獎
		$isSecondActivityEnd = $res['body']['result']['isSecondActivityEnd'];
		
		$this->view->userRankingWins = json_encode($userRankingWins);
		$this->view->activityConfigs = json_encode($activityConfigs);
		$this->view->configTimes = json_encode($configTimes);
		$this->view->isWined = $isWined;//判斷是否已經中獎 0:未中獎, 1:已中獎
		$this->view->isSecondActivityEnd = $isSecondActivityEnd;// 0:尚未結束, 1:已經結束
		
		$this->view->display('default/ucenter/activity/group/2016_decgrouppage.tpl');
	}
	
	//12 月 聚合頁開獎名單
	public function groupwinlistAction(){
		$account = $this->_sessionlogin->info['account'];
		$week = getSecurityInput($this->_request->getPost("week"));
		$point = getSecurityInput($this->_request->getPost("point"));
		$data['param']['account'] = $account;
		$data['param']['week'] = $week;
		$data['param']['point'] = $point;
		
		$aUri['activity'] = 'groupDrawListQuery';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		$resultData = $res['body']['result'];
		echo json_encode($resultData);
	}
	
	//1月報名查詢, 聚合頁
	public function queryjansignupAction(){
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		$aUri['activity'] = 'queryJanSignUp';
		$res = $this->_clientObject->inRequestV2($data, $aUri);//回傳結果,取數據資料*/
		$isSigned = $res['body']['result']['isSigned'];
		
		echo $res['body']['result']['isSigned'];
	}
	
	//1月聚合頁 報名
	public function jansignupAction(){
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		
		$aUri['activity'] = 'janSignUp';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料
		
		echo $res['body']['result']['isSignUpSuccess'];
	}
	
	//
	
	public function redbow4queryAction(){
		//nov4Query
		$userid = $this->_sessionlogin->info['id'];
		//print_r($userid);
		$data['param']['userId'] = $userid;
		
		$aUri['activity'] = 'nov4Query';
		//print_r($aUri);
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		//print_r($res['body']['result']);
		
		echo json_encode(
				array (
				'_status' => $res['body']['result']['_status'],
				'_name' => $res['body']['result']['_name'],
				'_signIn' => $res['body']['result']['_signIn'],							//是否已经报名 true表示已经报名，false表示未报名
				'_range' => $res['body']['result']['_range'],								//当前用户排名
				'_money' => $res['body']['result']['_money'],
				'_list' => $res['body']['result']['_list']
					)
				); 
	}
	
	//11月活動3當日投注量
	public function redbow3chekbetsAction(){
		$userid = $this->_sessionlogin->info['id'];
		//print_r($userid);
		$data['param']['userId'] = $userid;
		
		$aUri['activity'] = 'redbow3chekbets';
		//print_r($aUri);
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$status = $res['body']['result']['status'];
		$prize = $res['body']['result']['prize'];
		$userLv = $res['body']['result']['userLv'];
		echo json_encode(
					array (
						'status' => $status,
						'mission' => $prize,
						'userLv' => $userLv
					)
				);
	}

	public function redbowactivityAction(){
		$this->view->display('default/ucenter/activity/2016_novRedbowActivity.tpl');
	}
		//11月紅包活動報名
	public function redbowsignupAction(){
		//$actId = getSecurityInput($this->_request->getPost("actId"));
		$account = $this->_sessionlogin->info['account'];
		//$month = 11;
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$week = getSecurityInput($this->_request->getPost("week"));
		
		$data['param']['week'] = $week;
		$data['param']['account'] = $account;
		$data['param']['month'] = intval($month);
		$data['param']['source'] = $source;
		
		$aUri['activity'] = 'novSignUp';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料
		
		echo $res['body']['result'];
	} 
	//查詢11月紅包活動報名資格
	public function redbowquerysignupAction(){
		//$actId = getSecurityInput($this->_request->getPost("actId"));
		$account = $this->_sessionlogin->info['account'];
		//$month = 11;
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$week = getSecurityInput($this->_request->getPost("week"));
		
		$data['param']['week'] = $week;
		$data['param']['account'] = $account;
		$data['param']['month'] = intval($month);
		$data['param']['source'] = $source;
		
		$aUri['activity'] = 'novQuerySignUp';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//---------------------------------------------------------------12月紅包活動報名
	//會員報名(第一周)
	public function signup20161201Action(){
		//$actId = getSecurityInput($this->_request->getPost("actId"));
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$week = getSecurityInput($this->_request->getPost("week"));
		$activitycode = '20161201';
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		$data['param']['activitycode'] = $activitycode;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$data['param']['week'] = $week;
		$aUri['activity'] = 'decSignUp';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//會員報名(第二周)
	public function signup20161202Action(){
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$week = getSecurityInput($this->_request->getPost("week"));
		$activitycode = '20161202';
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		$data['param']['activitycode'] = $activitycode;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$data['param']['week'] = $week;
		$aUri['activity'] = 'decSignUp02';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//會員報名(第三周)
	public function signup20161203Action(){
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$week = getSecurityInput($this->_request->getPost("week"));
		$activitycode = '20161203';
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		$data['param']['activitycode'] = $activitycode;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$data['param']['week'] = $week;
		$aUri['activity'] = 'decSignUp03';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//會員報名(第四周)
	public function signup20161204Action(){
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$week = getSecurityInput($this->_request->getPost("week"));
		$activitycode = '20161204';
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		$data['param']['activitycode'] = $activitycode;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$data['param']['week'] = $week;
		$aUri['activity'] = 'decSignUp04';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//報名查詢(第一周)
	public function querysignup20161201Action(){
		//$activitycode = '20161201';
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		//$data['param']['activitycode'] = $activitycode;
		$aUri['activity'] = 'novQuerySignUp';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//報名查詢(第二周)
	public function querysignup20161202Action(){
		//$activitycode = '20161202';
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		//$data['param']['activitycode'] = $activitycode;
		$aUri['activity'] = 'decQuerySignUp';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//報名查詢(第三周)
	public function querysignup20161203Action(){
		//$activitycode = '20161202';
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		//$data['param']['activitycode'] = $activitycode;
		$aUri['activity'] = 'decQuerySignUp03';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//報名查詢(第四周)
	public function querysignup20161204Action(){
		//$activitycode = '20161202';
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		//$data['param']['activitycode'] = $activitycode;
		$aUri['activity'] = 'decQuerySignUp04';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//取前200名(第四周)
	public function getfront200Action(){
		
		//$activitycode = '20161202';
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		//$data['param']['activitycode'] = $activitycode;
		$aUri['activity'] = 'decQuerySignUp_get200';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
	
		echo json_encode($res['body']['result']);
	}
	//取得目前排名(第四周)
	public function getrankAction(){
		
		//$activitycode = '20161202';
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		//$data['param']['activitycode'] = $activitycode;
		$aUri['activity'] = 'decQuerySignUp_rank';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		
		echo json_encode($res['body']['result']);
	}
	
	//-------------------------------end-----------------------------12月紅包活動報名
	
	
	
	
	
	
	//春節活動聚合頁 入口
	public function newyeargrouppageAction(){
		$this->view->display('default/ucenter/activity/group/2017_happyNewYear.tpl');
	}
	
	//春節活動聚合頁 活動時間
	public function newyeargroupdateAction(){
		
		$aUri['activity'] = 'newYearDateCheck';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		echo json_encode($res['body']['result']);
	}
	
	//春節活動聚合頁充值送兩倍 初始化
	public function newyearchargedoubleinitAction(){
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		
		//充值送兩倍
		$aUri['activity'] = 'newYearChargeDoubleInit';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		echo json_encode($res);
	}
	
	//春節活動聚合頁充值送兩倍 領取獎金
	public function newyearchargedoublegetawardAction(){
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		
		$aUri['activity'] = 'newYearChargeDoubleGetAward';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		echo json_encode($res);
	}
	
	//春節活動聚合頁整點來抽獎 初始化
	public function newyearlotteryinitAction(){
		$account = $this->_sessionlogin->info['account'];
		$userId = $this->_sessionlogin->info['id'];
		$data['param']['account'] = $account;
		$data['param']['userId'] = $userId;
		
		$aUri['activity'] = 'newYearLotteryInit';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		echo json_encode($res['body']['result']);
	}
	
	//春節活動聚合頁整點來抽獎 抽紅包
	public function newyearlotterylotteryAction(){
		$account = $this->_sessionlogin->info['account'];
		$userId = $this->_sessionlogin->info['id'];
		$data['param']['account'] = $account;
		$data['param']['userId'] = $userId;
		
		$aUri['activity'] = 'newYearLotteryLottery';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		//$res['body']['result']['isVip'] = $this->_sessionlogin->info['vipLvl']==0?false:true;
		
		echo json_encode($res['body']['result']);
	}	
	
	//春節活動聚合頁你充我就送 初始化
	public function newyearchargegiveinitAction(){
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		
		$aUri['activity'] = 'newYearChargeGiveInit';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		echo json_encode($res['body']['result']);
	}
	
	//春節活動聚合頁你充我就送 抽紅包
	public function newyearchargegiveredbowawardAction(){
		$account = $this->_sessionlogin->info['account'];
		$sn = getSecurityInput($this->_request->getPost("sn"));
		$data['param']['account'] = $account;
		$data['param']['sn'] = $sn;
		
		$aUri['activity'] = 'newYearChargeGiveRedBowAward';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		echo json_encode($res['body']['result']);
	}
	
	//春節活動聚合頁VIP領紅包 初始化
	public function newyearvipinitAction(){
		$userId = $this->_sessionlogin->info['id'];
		$data['param']['userId'] = $userId;
		
		$aUri['activity'] = 'newYearVipInit';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		echo json_encode($res['body']['result']);
	}
	
	//春節活動聚合頁VIP領紅包 抽紅包
	public function newyearvipredbowsaveAction(){
		$userId = $this->_sessionlogin->info['id'];
		$activityCode = getSecurityInput($this->_request->getPost("activityCode"));
		$newyeartype = getSecurityInput($this->_request->getPost("newyeartype"));
		
		$data['param']['userId'] = $userId;
		$data['param']['activityCode'] = $activityCode;
		$data['param']['newyeartype'] = $newyeartype;
		
		$aUri['activity'] = 'newYearVipRedBowSave';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		//$res['body']['result']['isVip'] = $this->_sessionlogin->info['vipLvl']==0?false:true;
		
		echo json_encode($res['body']['result']);
	}
	
	//------------------------------------20172月開春活動-----------------------
	public function openspringAction(){
		$this->view->display('default/ucenter/activity/2017_febOpenSpring.tpl');
	}
	
		//20172月開春活動 開春簽到送禮初始化
	public function openspringinitAction(){
	    $account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		
		//充值送兩倍
		$aUri['activity'] = 'openSpringInit';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		echo json_encode($res['body']['result']);
	}
	
   //20172月開春活動 開春簽到送禮領獎
	public function openspringawardAction(){
	   	$account = $this->_sessionlogin->info['account'];	
		$resultId = getSecurityInput($this->_request->getPost("redbow"));
		$data['param']['account'] = $account;
		$data['param']['resultId'] = $resultId;
		//充值送兩倍
		$aUri['activity'] = 'openSpringAward';
		//$aUri['activity'] = 'openSpringInit';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']['result']);
		//echo json_encode('4444');
	}
	
	//20172月開春活動 報名查詢(第2周)
	public function query20170202signupAction(){
		//$activitycode = '20161201';
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		//$data['param']['activitycode'] = $activitycode;
		$aUri['activity'] = 'querySignUp20170202';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//20172月開春活動 報名查詢(第3周)
	public function query20170203signupAction(){
		//$activitycode = '20161202';
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		//$data['param']['activitycode'] = $activitycode;
		$aUri['activity'] = 'querySignUp20170203';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	
	// 20172月開春活動會員報名(第2周)
	public function febsecondsignupAction(){
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$aUri['activity'] = 'signUp20170202';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	
	//20172月開春活動會員報名(第3周)
	public function febthirdsignupAction(){
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$aUri['activity'] = 'signUp20170203';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	
	//20172月開春活動 開春簽到送禮 浮層初始化
	public function redbowstateAction(){
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		//充值送兩倍
		$aUri['activity'] = 'openSpringRedbowState';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo $res['body']['result'];
	}
	
	//2017三月第一個活動 浮層初始化
	public function activity20170301statusAction(){
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		$aUri['activity'] = 'activity20170301init';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']['result']);
	}
	
	//2017三月第一個活動
	public function activity20170301initAction(){
		$game_server = getGameDomain(getWebSiteDomain());
		$this->view->game_server = json_encode($game_server);
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		$aUri['activity'] = 'activity20170301init';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$this->view->display('default/ucenter/activity/2017/March/activity01.tpl');
	}
	
	//2017三月第一個活動派獎
	public function activity20170301awardAction(){
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		$aUri['activity'] = 'activity20170301award';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']['result']);
	}
	
	//2017三月第四周活動
	public function activity20170304initAction(){			
		$game_server = getGameDomain(getWebSiteDomain());
		$this->view->game_server = $game_server;
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		$aUri['activity'] = 'activity20170304init';
		
		$res = $this->_clientObject->inRequestV2($data, $aUri);
                                             $signStatus = $res['body']['result']['signStatus'];
                                             $this->view->signStatus = $signStatus;
                                             $this->view->account = $account;
                                             $this->view->display('default/ucenter/activity/2017/March/activity04.tpl');
                       }
	
	//2017三月第四周活動報名
	public function activity20170304signupAction(){
       		$account = $this->_sessionlogin->info['account'];
                                             $source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		
		$data['param']['account'] = $account;
                                             $data['param']['source'] = $source;
		$data['param']['month'] = $month;
		$aUri['activity'] = 'activity20170304signup';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']['result']['status']);
	}
        
                       //2017四月第二周活動初始
	public function activity20170402initAction(){
                                                  		
       		$account = $this->_sessionlogin->info['account'];
                                              //介接API
                                             $aUri['activity'] = 'activity20170402init';
		$data['param']['account'] = $account;
                                             $res = $this->_clientObject->inRequestV2($data, $aUri);
                                             $leftMoney=$res['body']['result']['leftMoney'];
		$level=$res['body']['result']['level'];
		
                                             $nowLevel= $res['body']['result']['nowLevel'];
                                             $awards = array();
                                             $awardAry = array();
                                             
                                             for ($i = 7; $i >= 1; $i--) {
                                                //print_r("i:".$i."level : ".$level.",nowLevel : ".$nowLevel."<br>");
                                                    if ($nowLevel <= $i) {
                                                        $awardAry["got"] = 1; //已領取
                                                    } else {
                                                        if ($i == ($level - 1)) {
                                                            $awardAry["got"] = 2; //當下的層級
                                                        } else if ($i >= $level) {
                                                            $awardAry["got"] = 0;
                                                        } else {
                                                            
                                                            $awardAry["got"] = -1;
                                                        }
                                                    }
                                                array_push($awards, $awardAry);
                                            }
        
                                             //print_r($awards);
                                             //exit;
                                             $aData = array (
                                                 'shortOf'=> getMoneyFomat(floatval($leftMoney/10000),2), //下一盞燈還需投注多少錢
                                                 'awards'=> $awards,
                                                 'nowLevel'=>$nowLevel
                                             );
                                             
                                             $this->view->aData = json_encode($aData);
                                             $this->view->display('default/ucenter/activity/2017/April/activity02.tpl');
	}
                       //2017四月第二周活動初始
	public function activity20170402awardAction(){
                                            $typeAry = array(
                                                'rate0' =>7,'rate1' =>6,'rate2' =>5,'rate3' =>4,'rate4' =>3,'rate5' =>2,'rate6' =>1                                               
                                            );
                                            $account = $this->_sessionlogin->info['account'];
                                            $awardLevel= $typeAry[getSecurityInput($this->_request->getParam("level"))];
                                            $nowLevel = getSecurityInput($this->_request->getParam("nowLevel"));
                                   
                                            $aUri['activity'] = 'activity20170402award';
                                            $data['param']['account'] = $account;
                                            $data['param']['level'] = $awardLevel;
                                            $res = $this->_clientObject->inRequestV2($data, $aUri);
                                            
                                             $isSuccess = $res['body']['result']['isSuccess'];
                                             $awardAmount=$res['body']['result']['awardAmount'];
                                             if($isSuccess =='Y'){
                                                     $isSuccess=true;
                                                    $awardAmount =  intval($awardAmount/10000) ;
                                             }else{
                                                     $isSuccess=false;
                                             }
                                                 
                                             $aData = array (
                                                     //'isSuccess'=> true, //是否派錢
                                                     'isSuccess'=> $isSuccess, //是否派錢
                                                     'msg' => "",
                                                     'data' => array( 'awardAmount'=> $awardAmount )//中奖金额  
                                             );
                                             
                                             echo json_encode($aData);
                                             exit;
                       }
}
