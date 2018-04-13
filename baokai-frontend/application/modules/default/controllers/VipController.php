<?php

require_once SITE_ROOT . '/application/include/Client.php';

class Default_VipController extends CustomControllerAction {
	
	public $_clientObject;
	public function init(){
		parent::init();
		$this->_clientObject = new Client();
	}
	
	public function indexAction(){
		
		$this->view->display('default/ucenter/vip/indexnew.tpl');
	}
	/*
	public function activityAction ()
	{
		$mobile=$this->mobile();

		if($mobile=="1")
		{
            $this->view->display('default/ucenter/vip/indexactivitymb.tpl');//return "1";
		}
                
        if($mobile=="0")
		{
		    //$this->view->display('default/ucenter/vip/indexactivity.tpl');//return "0";
			$this->view->display('default/ucenter/vip/2016sept/index2016septactivity.tpl');
			//$this->view->display('default/ucenter/vip/2016july/index2016julyactivity.tpl');
	    }
	}
	*/
	/**
	 * 活動
	 */
	public function activityAction()
	{	
		
		$this->view->display('default/ucenter/vip/2016sept/index2016septactivity.tpl');
		//$this->view->display('default/ucenter/vip/2016july/index2016julyactivity.tpl');
	}

	/**
	 * 活動1詳情
	 */
	public function activityweek1Action()
	{	
		$this->view->path_img = IMG_ROOT;
		//$token = getSecurityInput($this->_request->get("sid"));
		//$this->view->token = $token ;

		$this->view->display('default/ucenter/vip/2016sept/activity1.tpl');//return "0";
	}
	
	/**
	 * 活動2詳情
	 */
	public function activityweek2Action()
	{	
		$this->view->path_img = IMG_ROOT;
		
		//$token = getSecurityInput($this->_request->get("sid"));
		//$this->view->token = $token ;
		$this->view->display('default/ucenter/vip/2016sept/activity2.tpl');//return "0";
	
	}
	
	/**
	 * 計算活動倒數時間
	 */
	public function getnowtimeAction(){
		
		$nowDate = mktime(date("Y-m-d H:i:s"));
		$week1Date = mktime("0","0","0","7","11","2016");
		$week2Date = mktime("0","0","0","7","18","2016");
		$week3Date = mktime("0","0","0","7","25","2016");
		echo Zend_Json::encode(array('isSuccess'=>1,'time1'=>($week1Date-$nowDate)*1000,'time2'=>($week2Date-$nowDate)*1000,'time3'=>($week3Date-$nowDate)*1000));
		exit;
	}
	
	public function registerinfoAction(){
		$data=$this->getActivityInfo();
		$aUri['activity'] = 'activityIndex';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$info = $res['body']['result'];
		echo Zend_Json::encode(array('isSuccess'=>'1','isRegistered'=>$info['isRegister'],'registerCount'=>$info['registerCount']));
		exit;
	}
	
	public function applicationAction ()
	{
		$data=$this->getActivityInfo();
		$aUri['activity'] = 'application';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$message = 'nono';
		$isRegistered=false; //是否報名過
		$registerOk = false; //是否報名成功
		switch ($res['body']['result']){
			case 1:
				$message ='报名成功';
				$registerOk = true;
				break;
			case 2:
				$message = '已经报名';
				$isRegistered=true;
				break;
			case 0:
				$message = '报名失败';
				break;

		}

		echo Zend_Json::encode(array('isSuccess'=>'1','isRegistered'=>$isRegistered,'message'=>$message,'registerOk'=>$registerOk));
		exit;
	}
	
	public function mobile ()
	{
		$device='/(alcatel|amoi|android|avantgo|blackberry|benq|cell|cricket|'.
            'docomo|elaine|htc|iemobile|iphone|ipad|ipaq|ipod|j2me|java|'.
            'midp|mini|mmp|mobi|motorola|nokia|palm|panasonic|philips|'.
            'phone|sagem|sharp|smartphone|sony|symbian|t-mobile|telus|'.
            'vodafone|wap|webos|wireless|xda|xoom|zte)/i';
        if (preg_match($device, $_SERVER['HTTP_USER_AGENT'])) {return 1;}
        else {return 0;}
	}
	
	private function getActivityInfo(){
		$uName = $this->_sessionlogin->info['account'];
		$uMonth = getSecurityInput($this->_request->getPost("month"));
		$tokan = getSecurityInput($this->_request->getPost("token"));
		$source = getSecurityInput($this->_request->getPost("source"));
		$startTime = getSecurityInput($this->_request->getPost("startTime"));
		$endTime = getSecurityInput($this->_request->getPost("endTime"));
		$action = getSecurityInput($this->_request->getPost("action"));
		$data['param']['token'] = $tokan;
		$data['param']['account'] = $uName;
		$data['param']['month'] = intval($uMonth);
		$data['param']['source'] = $source;
		$data['param']['startTime'] = $startTime;
		$data['param']['endTime'] = $endTime;
		return $data;
	}
	
	public function maylabaAction()
	{
		$this->view->display('default/ucenter/vip/maylaba.tpl');
	}
	
	public function getmaylabaAction()
	{
		$userid = $this->_sessionlogin->info['id'];
		//$money = getMoneyFomat($eggMoney/$this->_moneyUnit,2);
	    //介接API準備
		$data['param']['userId'] = intval($userid);
		$aUri['activity'] = 'maylabar';
		$res = $this->_clientObject->inRequestV2($data, $aUri);//回傳結果,取money
		//print_r($res);
		//exit;
		for($i=0;$i<count($res['body']['result']['data']);$i++)
		{
			$username = '';
			$username=getCNSubStr($res['body']['result']['data'][$i]['name'],0,1);
			$username.="***";
			$username.=getCNSubStr($res['body']['result']['data'][$i]['name'],-1,1);
			$win[$i]['name']= $username;
			$win[$i]['win']=  floor($res['body']['result']['data'][$i]['win']/$this->_moneyUnit);
		}
		
		$money = round($res['body']['result']['betAmount']/$this->_moneyUnit,2);
		$moneylen=explode(".",$money);
		
		if(strlen($moneylen[0])>=7)
		{
			$betAmount=$moneylen[0];
		}
		else
		{
			$betAmount=$money;
		}
		
		$result =  array(
			'isSuccess' => $res['body']['result']['isSuccess'],
			'message' => '您的网络不稳定，请您刷新页面。',
			'isPrize' => $res['body']['result']['isPrize'],
			'hisBonus' => round($res['body']['result']['hisBonus']/$this->_moneyUnit,2),
			'endTime' => mktime(23,59,59,date('d'),date('m'),date('Y'))-time(),//mktime(date('H'),date('i'),date('s'),date('d'),date('m'),date('Y')),
			'betAmount' => $betAmount,
			'betRatio' => $res['body']['result']['betRatio'],
			'data' => $win
		);
		
	    echo json_encode($result);
	}
	
	public function getmaylotteryAction()
	{
		$userid = $this->_sessionlogin->info['id'];
		
		//$money = getMoneyFomat($eggMoney/$this->_moneyUnit,2);
		
		//介接API準備
		$data['param']['userId'] = intval($userid);
		$aUri['activity'] = 'labaraward';
		$res = $this->_clientObject->inRequestV2($data, $aUri);//回傳結果,取money
		/*print_r($res);
		exit;*/
		$bonus = round($res['body']['result']['bonus']/$this->_moneyUnit,2);
		$money = round($res['body']['result']['betAmount']/$this->_moneyUnit,2);
		$moneylen=explode(".",$money);
		
		if(strlen($moneylen[0])>=7)
		{
			$betAmount=$moneylen[0];
		}
		else
		{
			$betAmount=$money;
		}
		$result =  array(
			'isSuccess' => $res['body']['result']['isSuccess'],
			'message' => '您的网络不稳定，请您刷新页面。',
			'multiple' => $res['body']['result']['multiple'],
			'betAmount' => $betAmount,
			'bonus' => $bonus 
		);
       
	    echo json_encode($result);
	}
	
	public function aoyunAction()
	{
		$userid = $this->_sessionlogin->info['id'];
		
		//介接API
		$data['param']['userId'] = intval($userid);
		$aUri['activity'] = 'Olympic';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		//Print_r($res);
		//exit;
		$prize = $res['body']['result']['prize'];
		$RebateRatio = $res['body']['result']['rebateRatio'];
		$plusRatio = $res['body']['result']['plusRatio'];
		$betAmount = $res['body']['result']['betAmount'];
		$chargeAmount = $res['body']['result']['chargeAmount'];
		$medals = $res['body']['result']['medals'];
		$status = $res['body']['result']['status'];
		$result = $res['body']['result']['result'];
		
		$this->view->prize       = $prize;
		$this->view->status   = $status ;
		$this->view->result   = $result ;
		$this->view->RebateRatio    = $RebateRatio.'%';
		$this->view->plusRatio    = $plusRatio.'%';
		$this->view->betAmount    = $betAmount;
		$this->view->chargeAmount    = $chargeAmount;
		$this->view->medals    = $medals;
		$this->view->display('default/ucenter/vip/2016august/aoyun.tpl');
	}
	
	public function aoyunsignAction()
	{
		//$userid = $this->_sessionlogin->info['id'];
		$useraccount = $this->_sessionlogin->info["account"];
		
		//介接API
		//$data['param']['userId'] = intval($userid);

		$data['param']['account'] = $useraccount;
		$data['param']['month'] = '8';
		$data['param']['source'] = 'web';
		$aUri['activity'] = 'application';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		//Print_r($res);
		//exit;
		$result =  array(
			'status' => $res['body']['result']	
		);
       
	    echo json_encode($result);
	}
	
        //搶紅包活動
        /*
            
         *          */
        public function qianghongbaoAction(){
            $this->view->info = $this->_sessionlogin->info['id'];
            $this->view->display('default/ucenter/vip/2016august/qiangHongBao.tpl');
        }
        //活動未開始 status:2 isFinished=false
        //活動期間 每日翻牌時間未到 status:0 isFinished=false todayEnd=false
        //活動期間 今日已翻牌 status:0 isFinished=false todayEnd=true ?
        //活動期間 今日已結束 status:0 isFinished=false todayEnd=true ?
        public function qianghongbaodataAction(){
           $user_data = $this->_request->getParam( "userId" );
            //介接API
            $data['param']['userId'] = intval($user_data);
            $aUri['activity'] = 'Qianghongbao';
            $res = $this->_clientObject->inRequestV2($data, $aUri);
            $result = $res['body']['result']['result'];
            //資料轉換
            $status = $res['body']['result']['status'];// 燈號  0 : 未開始翻牌 1:翻牌中  //判斷時間狀態
            $qualification = ($res['body']['result']['qualification']==1)? true : false; //紅包 參加資格 0: 無參加資格 1:有參加資格
            $rest = $res['body']['result']['rest'];//剩餘紅包數
            $redEnvelope = str_pad($res['body']['result']['redEnvelope'],4,'0',STR_PAD_LEFT); //紅包金額翻牌
            $redEnvelopeView = $res['body']['result']['redEnvelope']; //紅包金額 顯示
            $betsTotal = $res['body']['result']['betsTotal']; //需投注總額
            $betAmount = $res['body']['result']['betAmount'];  //已投注金額
            $leftAmount = $res['body']['result']['leftAmount']; //用戶仍需投注金額
            if(isset($res['body']['result']['isFinished'])){
                $isFinished = ($res['body']['result']['isFinished']==0)? false : true; //用戶今日遊戲是否完成 完成 : 1  沒完成 : 0
            }else{
                $isFinished = false; //用戶今日遊戲是否完成 完成 : 1  沒完成 : 0
            }         
            $todayFinished = ($res['body']['result']['todayFinished']==1)? true : false; ; //當日遊戲是否結束 結束 : 1 沒結束 : 0
            $todayStartTime = $res['body']['result']['todayStartTime']; //今日活動開始時間
            $todayEndTime = $res['body']['result']['todayEndTime']; //今日活動結束時間
            $nowTime = $res['body']['result']['nowTime']; //伺服器時間
                       
            if($result != 1){//取得伺服器資料失敗
                $status = 0;
                $qualification = false;
                $isFinished = false;
            }
            $aData = array (
                'status' =>"$status", //0: 游戏未开始  1：游戏进行中  2： 游戏预告期 (8月23日之前)
                'todayEnd' => $todayFinished, //当日游戏是否结束
                'finished' => array( //用户今日游戏是否参加
                                      'isFinished'=> $isFinished, //是否完成  true:已翻牌  false:未翻牌
                                      'prize'=> "$redEnvelope", //中奖金额
                                      'amount'=> "$betsTotal", //需要投注总金额
                                      'leftAmount'=> "$leftAmount"  //达到目标还需要投注
                                    ),                                                
                'nowTime' =>  $nowTime ,//date("Y/m/d H:i:s"),// "2016/07/25 10:36:06", //当前服务器时间
                'startTime' => $todayStartTime, //今日游戏开始时间
                'stopTime' => $todayEndTime, //今日游戏结束时间
                'qualification' => $qualification, //是否有参加资格 :名单内用户 : true, 非名单内用户 false
                'surplusPrize' => "$rest", // 红包剩余数量
                'przie' => "$redEnvelopeView" //中奖金额  似乎無效
            );
            /*
            $aData = array (
                                'status' =>"0", //0: 游戏未开始  1：游戏进行中  2： 游戏预告期 (8月23日之前)
                                'todayEnd' => false, //当日游戏是否结束
                                'finished' => array( //用户今日游戏是否参加
                                                      'isFinished'=> false, //是否完成  true:已翻牌  false:未翻牌
                                                      'prize'=> "0", //中奖金额
                                                      'amount'=> "0", //需要投注总金额
                                                      'leftAmount'=> "0"  //达到目标还需要投注
                                                    ),                                                
                                'nowTime' =>  "2016/08/04 17:36:06" ,//date("Y/m/d H:i:s"),// "2016/07/25 10:36:06", //当前服务器时间
                                'startTime' => "2016/08/04 20:00:00", //今日游戏开始时间
                                'stopTime' => "2016/08/04 22:00:00", //今日游戏结束时间
                                'qualification' => true, //是否有参加资格 :名单内用户 : true, 非名单内用户 false
                                'surplusPrize' => "10", // 红包剩余数量
                                'przie' => "0" //中奖金额
		);
             * */
            
            //$bindCardResult = $this->getbindBankCardInfo($aData);
            if(isset($res['body']['result']['result']) && $result >0){
                    echo Zend_Json::encode($aData);
                    exit;
            }else{
                    echo Zend_Json::encode(array('status'=>'error'));
                    echo Zend_Json::encode($aData);
                    exit;
            }
	}
        //搶紅包資料
        //記錄使用者開牌資料
        public function qianghongbaoprizeAction(){
            $user_data = $this->_request->getParam( "userId" );
            //介接API
            $data['param']['userId'] = intval($user_data);
            $aUri['activity'] = 'Qianghongbaoprize';
            $res = $this->_clientObject->inRequestV2($data, $aUri);
            $isSuccess = $res['body']['result']['status'];// 获取成功  0 ： 失败(含紅包已抽完)  1 ：获取成功
            $redEnvelope = str_pad($res['body']['result']['redEnvelope'],4,'0',STR_PAD_LEFT); //紅包金額
            $betsTotal = str_pad($res['body']['result']['betsTotal'],4,'0',STR_PAD_LEFT); //需投注總額            
            $leftAmount = str_pad($res['body']['result']['leftAmount'],4,'0',STR_PAD_LEFT); //用戶仍需投注金額
		$aData = array (
                                'isSuccess' => "$isSuccess", //获取成功  0 ： 失败  1 ：获取成功
                                'msg' => "success",
                                'data' => array( 'prize'=> "$redEnvelope",  //中奖金额
                                                  'amount'=> "$betsTotal", //需要投注总金额
                                                  'leftAmount'=> "$leftAmount" //达到目标还需要投注
                                                )                                           
		);		
		//$bindCardResult = $this->getbindBankCardInfo($aData);
		if(isset($res['body']['result']['result']) && $result >0){
			echo Zend_Json::encode($aData);
			exit;
		}else{
                        echo Zend_Json::encode($aData);
			exit;
		}
	}
	
	//九月點燈活動week2 LightData
	public function lightdataAction(){
		$userid = $this->_sessionlogin->info['id'];

        //介接API
        $data['param']['userId'] = intval($userid);
        $aUri['activity'] = 'lightdata';
        $res = $this->_clientObject->inRequestV2($data, $aUri);
        //$result = $res['body']['result']['result'];
		$leftMoney=$res['body']['result']['leftMoney'];
		$level=$res['body']['result']['level'];
		$hasRight=$res['body']['result']['hasRight'];
		if($hasRight != 1){//
            $hasRight=false;
        }else{
			$hasRight=true;
		}
		
		$isFinished=$res['body']['result']['isFinished'];
		if($isFinished != 1){//
            $isFinished=false;
        }else{
			$isFinished= true;
		}
		$isVip=$res['body']['result']['isVip'];
		if($isVip != 1){//
            $isVip=false;
        }else{
			$isVip= true;
		}
		$isTop=$res['body']['result']['isTop'];
		if($isTop != 1){//
            $isTop=false;
        }else{
			$isTop= true;
		}
		$nowLevel= $res['body']['result']['nowLevel'];
	
		$aData = array (
			 'leftMoney'=> $leftMoney, //下一盞燈還需投注多少錢
			'level' => $level,       //可以點到幾盞燈
			//'level' => '6',       //可以點到幾盞燈
			'isGet' => false ,    //已領錢
			"hasRight" => $hasRight ,  //有報名
			//"isFinished" => $isFinished , //已經點燈領錢
			"isVip" => $isVip, //
			"nowLevel" => $nowLevel ,
			//"nowLevel" => '6',
			"isTop" => $isTop

		);
		echo Zend_Json::encode($aData);
		exit;
	}
	
	//九月點燈活動week2 派獎 activityaward
	public function activityawardAction(){
		$userid = $this->_sessionlogin->info['id'];
		$level= getSecurityInput($this->_request->getParam("level"));
        //介接API
        $data['param']['userId'] = intval($userid);
		$data['param']['drawLv'] = $level;
        $aUri['activity'] = 'openDraw';
        $res = $this->_clientObject->inRequestV2($data, $aUri);
        $result = $res['body']['result']['result'];
		$isFinished = $res['body']['result']['isDraw'];
		$isSuccess = $res['body']['result']['isSuccess'];
		if($isFinished =='Y'){
			$isFinished=true;
		}else{
			$isFinished=false;
		}
		if($isSuccess !=1){
			$isSuccess=true;
		}else{
			$isSuccess=false;
		}
		$aData = array (
			 //'isSuccess'=> true, //是否派錢
			 'isSuccess'=> $isSuccess, //是否派錢
			 'msg' => "",
              'data' => array( 'isFinished'=> $isFinished //中奖金额
                               //'isFinished'=> false 
                             )  
		);
		echo Zend_Json::encode($aData);
		exit;
	}
	
	public function activityweek3Action(){
		$id = $this->_sessionlogin->info['id'];
		$data['param']['userId'] = intval($id);
		$aUri['activity'] = 'getVipLvl';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$viplvl = $res['body']['result']['viplvl'];
		$this->view->viplvl = $viplvl;
		$this->view->display('default/ucenter/vip/2016sept/activity3.tpl');
	}
	
	public function week3getdataAction()	//僅檢查是否抽過獎但不會扔骰子
	{
		$id = $this->_sessionlogin->info['id'];
		$data['param']['userId'] = intval($id);
		$vipLvl = $this->_sessionlogin->info['vipLvl'];
		$data['param']['vipLvl'] = $vipLvl;
		$aUri['activity'] = 'Septweek3';
		//die();
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$info = $res['body']['result'];
		//print_r($info);
		//exit;
		//js 所需的結果架構
		$result = array('isJoin'=>$info['join'],
				'isFinished'=>$info['finished'],
				'prize'=>$info['prize'],
				'betToday'=>$info['betToday'], //今日是否達標
				'position'=>array(
						'level'=>$info['level'],
						'day'=>$info['day']
				));
		//print_r(Zend_Json::encode($result));
		//die();
	
		echo Zend_Json::encode($result);
	}
	
		//十月活動第一週 打鬼子
	public function octactivity1Action()
	{	
		$this->view->display('default/ucenter/vip/2016oct/octActivity1index.tpl');
	}

	public function octinfoAction(){

		$userid = $this->_sessionlogin->info['id'];
		$data['param']['userId'] = intval($userid);
		$aUri['activity'] = 'octAcivity1info';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$activityInfo = $res['body']['result'];
		$aData = array (
			 //'isSuccess'=> true, //是否派錢
			 'isSuccess'=> $activityInfo['isSuccess'], //是否派錢
              'data' => array( 'isGetPrize'=> $activityInfo['isGetPrize'],
              					'leftAmount' => $activityInfo['leftAmount'],
              					'betAmount' => $activityInfo['betAmount'],
              					'betScale' => $activityInfo['betScale'].'%',
              					'betMutile' => $activityInfo['betMutile'],
              					'prize' => $activityInfo['prize']
                             )  
		);
		echo Zend_Json::encode($aData);
		exit;
	}

	public function shotkillAction(){

		$userid = $this->_sessionlogin->info['id'];
		$data['param']['userId'] = intval($userid);
		$aUri['activity'] = 'octAcivity1ShotKill';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$activityInfo = $res['body']['result'];
		$aData = array (
			 //'isSuccess'=> true, //是否派錢
			 'isSuccess'=> $activityInfo['isSuccess'], //是否派錢
              'data' => array( 'isGetPrize'=> $activityInfo['isGetPrize'],
              					'leftAmount' => $activityInfo['leftAmount'],
              					'betAmount' => $activityInfo['betAmount'],
              					'betScale' => $activityInfo['betScale'].'%',
              					'betMutile' => $activityInfo['betMutile'],
              					'prize' => $activityInfo['prize']
                             )  
		);
		echo Zend_Json::encode($aData);
		exit;
	}

	//十月活動第二週 今秋十月-鸿运当头
	public function octactivity2Action()
	{	
		$this->view->display('default/ucenter/vip/2016oct/octActivity2.tpl');
	}

	public function oct2infoAction(){

		$userid = $this->_sessionlogin->info['id'];
		$data['param']['userId'] = intval($userid);
		$aUri['activity'] = 'Oct2Info';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$aData = array (
			 'isSuccess'=> $res['body']['result']['isSuccess']
		);
		echo Zend_Json::encode($aData);
		exit;
	}

	public function oct2signupAction(){

		$userid = $this->_sessionlogin->info['id'];
		$data['param']['userId'] = intval($userid);
		$aUri['activity'] = 'Oct2SignUp';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$aData = array (
			 'isSuccess'=> $res['body']['result']['isSuccess']
		);
		echo Zend_Json::encode($aData);
		exit;
	}

	//十月活動第三週 不废话一起壕
	public function octactivity3Action()
	{	
		$this->view->display('default/ucenter/vip/2016oct/octActivity3.tpl');
	}

	public function oct3infoAction(){

		$userid = $this->_sessionlogin->info['id'];
		$data['param']['userId'] = intval($userid);
		$aUri['activity'] = 'Oct3Info';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$aData = array (
			 'isSuccess'=> $res['body']['result']['isSuccess']
		);
		echo Zend_Json::encode($aData);
		exit;
	}

	public function oct3signupAction(){

		$userid = $this->_sessionlogin->info['id'];
		$data['param']['userId'] = intval($userid);
		$aUri['activity'] = 'Oct3SignUp';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$aData = array (
			 'isSuccess'=> $res['body']['result']['isSuccess']
		);
		echo Zend_Json::encode($aData);
		exit;
	}

	//十月活動第四週 RichMan
	public function octactivity4Action()
	{	
		$this->view->display('default/ucenter/vip/2016oct/octActivity4.tpl');
	}

	public function oct4infoAction(){

		$userid = $this->_sessionlogin->info['id'];
		$data['param']['userId'] = intval($userid);
		$aUri['activity'] = 'Oct4Info';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$aData = array (
			 'Signup'=> $res['body']['result']['signUp'],
			 'TodayDeposit'=> $res['body']['result']['todayDeposit'],
			 'TodayBets'=> $res['body']['result']['todayBets'],
			 'TodayPrize'=> $res['body']['result']['todayPrize'],
			 'DepositCount'=> $res['body']['result']['depositCount'],
			 'Addr' => $res['body']['result']['isAddr']
		);
		echo Zend_Json::encode($aData);
		exit;
	}

	public function oct4signupAction(){

		$userid = $this->_sessionlogin->info['id'];
		$data['param']['userId'] = intval($userid);
		$aUri['activity'] = 'Oct4SignUp';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$aData = array (
			 'isSuccess'=> $res['body']['result']['isSuccess']
		);
		echo Zend_Json::encode($aData);
		exit;
	}

	public function oct4addrAction(){

		$userid = $this->_sessionlogin->info['id'];
		$data['param']['userId'] = intval($userid);
		$aUri['activity'] = 'Oct4Addr';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		exit;
	}
}