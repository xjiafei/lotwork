<?php

require_once SITE_ROOT . '/application/include/CodePlugin.php';
require_once SITE_ROOT . '/application/models/admin/User.php';

class Default_IndexController extends CustomControllerAction 
{
    protected $_captcha, $_clientObject, $_redisIndexInfo, $_user;
    protected $_loginParam;
	
	public function init( ){
		parent::init( ) ;
		$this->_captcha = new ValidateCode();
		$this->_clientObject = new Client();
		$this->_login = new Member();
		$this->_redisIndexInfo = Zend_Registry::get('rediska');
        $this->_user = new User();
	}
	


	public function indexAction( ){
		//=================检查是否设置安全信息=================================
		//安全密码,安全问题,绑定邮箱,预设留言
		$aSecurityInfo = array('withdrawPasswd','quStruc','emailActived','cipher');
		$iSecurityCnt  = 0;
		foreach ($aSecurityInfo as $value){
			if(isset($this->_sessionlogin->info[$value]) && !empty($this->_sessionlogin->info[$value])){
				$this->view->$value = 1;
				$iSecurityCnt ++ ;
			} else {
				$this->view->$value = 0;
			}
		}
		
		$user_id = $this->_sessionlogin->info['id'];
		$game =  $this->redis_client->get(md5('defindgamemenu' . $user_id));
		$playgame = json_decode($game);
		// 安全等级 高：4 中：3 低：2
		$this->view->safeLevel = ($iSecurityCnt/4)*100;
		if ($playgame->{'gameLastId'} == '')
		{
			$this->view->playgameid = '99101';
            $this->view->playgamename = 'cqssc';
		}
		else
		{
			$this->view->playgameid = $playgame->{'gameLastId'};
            $this->view->playgamename = $playgame->{'gameLastName'};
		}

        //******************Start  20170712  新开发的首页需要 6 个用户最后购买过的彩种   ********
        $this->view->defaultsixgamemenu =  $this->redis_client->get(md5('firstpagedefaultsixgamemenu' . $user_id)); 		
        		
		//是否绑卡
		$result = array('lastNotice'=>array(),'qqs'=>array(),'loginArea'=>array());
	
		$aUri['index'] = 'queryIndexInfo';
		$data['param']['userId'] = $this->_sessionlogin->info['id'];
		$res = $this->_clientObject->inRequestV2($data, $aUri);
	
		$noticearr =  $this->redis_client->get(md5('noticedata' . $this->_sessionlogin->info['id']));
		$noticeobj = json_decode($noticearr);
		
		if(isset($res['head']['status']) && $res['head']['status'] =='0'){
			if(isset($res['body']['result']) && count($res['body']['result'])>0){
				//公告
				if(isset($res['body']['result']['lastNotice'] ) && count($res['body']['result']['lastNotice'])>0){
					
					foreach ($res['body']['result']['lastNotice'] as $key=>$value){
						
						$result['lastNotice'][$key]['id']    = $value['id'];
						$result['lastNotice'][$key]['title'] = mb_substr($value['title'],0,14,'utf8');
						$result['lastNotice'][$key]['gmtEffectBegin'] = date('m-d',getSrtTimeByLong($value['gmtEffectBegin']));
						$result['lastNotice'][$key]['noticelevel'] = $value['noticelevel'];
						
						if ($noticeobj->{$value['id']} == $value['id'])
						{
							$result['lastNotice'][$key]['redflag'] = '1';
						}
						else
						{
							$result['lastNotice'][$key]['redflag'] = '0';
							
							if($result['lastNotice'][$key]['noticelevel'] == "0")
							{
								$this->view->notice0 = '0';
							}
							
							if($result['lastNotice'][$key]['noticelevel'] == "1")
							{
								$this->view->notice1 = '0';
							}
							
							if($result['lastNotice'][$key]['noticelevel'] == "2")
							{
								$this->view->notice2 = '0';
							}
						}
						
						$notice[$value['id']] = $value['id'];
					}
				}
				
				$noticedata = Zend_Json::encode($notice);
		        
				$this->redis_client->set(md5('noticedata' . $this->_sessionlogin->info['id']),$noticedata, 365*24*60*60);
				if(isset($res['body']['result']['qqs']) && count($res['body']['result']['qqs'])>0){
					foreach ($res['body']['result']['qqs'] as $key=>$value){
						$result['qqs'][$key]['qq']    = $value['qq'];
					}
				}
				if(isset($res['body']['result']['loginArea']) && !empty($res['body']['result']['loginArea'])){
					$result['loginArea'] = $res['body']['result']['loginArea'];
				} else {
					$result['loginArea'] = '';
				}
			}
		}
		//$adSpaces = $this->_login->getAllAdSpace();
		$gameIdArray = array(99101,99104,99103,99106,99107,99105,99111,99114,99112,99108,99109,99301,99302,99303,99305,99307,99201,99401,99501,99502,99601,99306,99701,99602,99603);
        $gameResult = $this->getGameResult($gameIdArray);
		$i = 0;
		while ($i<50)
		{
		    $randon = array_rand($gameIdArray);
		    $winnerdata = $gameResult[$gameIdArray[$randon]];
			if(isset($winnerdata ['wins']))
			{
				$randcount = rand(0,count($winnerdata ['wins']));
				if($winnerdata ['wins'][$randcount ]['username']!="")
				{
				    $winner[$i]['lotteryId'] =  $winnerdata ['lotteryId'];
				    $winner[$i]['lottery'] =  $winnerdata ['lottery'];
				    $winner[$i]['username'] =  $winnerdata ['wins'][$randcount]['username'];
				    $winner[$i]['prize'] =  $winnerdata ['wins'][$randcount]['prize'];
					$i++;
				}
			}
			else
            { 

                $i ++;
            }
	    }
        if(count($winner)>0){
            shuffle($winner);
        }
		$this->view->gameResult = $gameResult;
		$this->view->winner = $winner;

		$lastLoginDate = '';
		if($this->_sessionlogin->info['lastLoginDate']>0){
			$lastLoginDate = date('Y-m-d H:i:s',getSrtTimeByLong($this->_sessionlogin->info['lastLoginDate']));
		}
		
		
		$nickname = $this->_sessionlogin->info['username'];
		$nickImg = $this->_sessionlogin->info['headImg'];

		$this->view->result    = $result;
		$this->view->nowTime    = getSendTime();
		$this->view->lastLoginDate = $lastLoginDate;
		
		$this->view->nickname = $nickname;
		$this->view->nickImg = $nickImg;
		//=================检查是否设置安全信息=================================
		$this->view->title     = '宝开彩票首页';
		$this->view->isIndexPage     = 1;

        if(isset($this->_sessionlogin->isLogin) && $this->_sessionlogin->isLogin=='1'){
            if($this->isLevelRecycleFirstLogin($this->_sessionlogin->info['account']) == 'isLevelRecycle') {
                $res_rsa = $this->encrypt_RSA(md5('safecodeedit' . $user_id));
                $this->view->rsa_n = $res_rsa['rsa_n'];
                $this->view->rsa_e = $res_rsa['rsa_e'];
                $this->view->display('default/ucenter/pwdChange.tpl');
            } else {
                        $mobile=$this->mobile();
                        if($mobile=="1")
	                {
                           $this->view->display('default/ucenter/wapIndex.tpl');
	                }
		        if($mobile=="0")
	                {
                           $this->view->display('default/ucenter/index.tpl');
			}

            }
        } else {
            $this->_loginParam = new Rediska_Key(md5('frontLoginParam'.session_id()));
            if(!$this->_loginParam->getValue()){
                $this->_loginParam->setValue(md5(getSendTime().rand(0, 99999)));
                $this->_loginParam->expire(intval(60 * 60 * 24));
            }
            $this->view->imageurl = '/login/changevcode';
            $this->view->code = '/login/changevcode';
            $this->view->registerurl = '/register/?id=14402640&exp=1791343064194&pid=13714760&token=dd82';
            $this->view->loginRand = $this->_loginParam->getValue();
            $this->view->display('default/login/index.tpl');
        }

    }
	public function mobile ()
    {
        $device='/(alcatel|amoi|android|avantgo|blackberry|benq|cell|cricket|'.
            'docomo|elaine|htc|iemobile|iphone|ipaq|ipod|j2me|java|'.
            'midp|mini|mmp|motorola|nokia|palm|panasonic|philips|'.
            'phone|sagem|sharp|smartphone|sony|symbian|t-mobile|telus|'.
            'vodafone|wap|webos|wireless|xda|xoom|zte)/i';
        if (preg_match($device, $_SERVER['HTTP_USER_AGENT'])) {return 1;}
        else {return 0;}
    }

	public function userdetailAction( ){
		$this->view->vipLvl = $this->_sessionlogin->info['vipLvl'];
		$this->view->display('default/ucenter/userDetail.tpl');
	}

	public function electronicAction( ){
		$this->view->display('default/ucenter/electronic.tpl');
	}
	
	//用户手册
	public function manualAction(){
		$this->view->display("default/ucenter/manual/index.tpl");
	}
	
	//用户视频手册
	public function userAction(){
		$this->view->display("default/ucenter/video/user.tpl");
	}
	
	//代理视频手册
	public function proxyAction(){
		$this->view->display("default/ucenter/video/proxy.tpl");
	}
	
	//充值活动
	public function rechargeAction(){
		$this->view->display("default/ucenter/recharge/recharge.tpl");
	}
	
	//实时获取可用余额
	public function getuserbalAction(){
		if(!isset($this->_sessionlogin->info['id']) || $this->_sessionlogin->info['id']<=0){
			echo Zend_Json::encode(array('status'=>'error','data'=>0));
			exit;
		}
		$user_id = $this->_sessionlogin->info['id'];
		$data['param'] = $user_id;
		$aUri['fund'] = 'getUserBal';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		if(isset($res['body']['result'])){
			$this->_sessionlogin->info['availBal'] = floatval($res['body']['result']);
			echo Zend_Json::encode(array('status'=>'ok','data'=>getMoneyFomat(floatval($res['body']['result'])/$this->_moneyUnit,2)));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','data'=>getMoneyFomat($this->_sessionlogin->info['availBal']/$this->_moneyUnit,2)));
			exit;
		}
	}
	
	/**********刷新验证码页面***********/
	public function changevcodeAction( ){
	    
	    $this->_captcha->setImage(array('width'=>115,'height'=>30,'type'=>'png'));
    	$this->_captcha->setCode( array('characters'=>'2-9,A,B,C,D,E,F,G,H,J,K,M,N,P,Q,R,S,T,U,V,W,X,Y','length'=>4,'deflect'=>FALSE,'multicolor'=>FALSE) );
    	$this->_captcha->setFont( array("space"=>10,"size"=>18,"left"=>10,"top"=>25,"file"=>'') ); 
    	$this->_captcha->setMolestation( array("type"=>FALSE,"density"=>'fewness') );
    	$this->_captcha->setBgColor( array('r'=>255,'g'=>255,'b'=>255) );
    	$this->_captcha->setFgColor( array('r'=>0,'g'=>0,'b'=>0) );
    	$this->codeSession = new Zend_Session_Namespace('code') ;
    	$this->codeSession->code = $this->_captcha->getcode();
    	$this->_captcha->paint();
    	
	}
	
	/**********安全登陆页面*************/
	public function sectloginAction( ){

		$this->view->imageurl = '/index/changevcode';
		$this->view->display('default/login/safe-login.tpl') ;
		
	}
	
	/**********找回密码页面*************/
	public function findpageAction( ){

		$this->view->display('default/ucenter/find-pwd-login.tpl') ;
		
	}
	
	/*********安全登陆的下一页面********/
	public function sectnextAction( ){

		$this->view->display('default/login/safe-login-next.tpl') ;	

	}
	//合作机会
	public function collaborationAction(){
		$this->view->display('default/ucenter/help/collaboration.tpl');
	}
	//获取重庆时时彩数据
	public function getgametimedownAction(){
		$user_id = $this->_sessionlogin->info['id'];
		$game =  $this->redis_client->get(md5('defindgamemenu' . $user_id));
		$playgame = json_decode($game);
		if ($playgame->{'gameLastId'} == '')
		{
			$playgameid = '99101';	
		}
		else
		{
			$playgameid = $playgame->{'gameLastId'};
		}
		$GameResult = $this->getGameResult(array($playgameid));
		if(isset($GameResult[$playgameid]) && count($GameResult[$playgameid])>0){
			echo Zend_Json::encode(array('status'=>'ok','data'=>array('result'=>$GameResult[$playgameid],'nowTime'=>getSendTime())));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error'));
			exit;
		}
	}
	
	public function getbannerAction(){
               
                $this->view->dynamicroot = $this->_config->dynamicroot;
		
                $adSpaces = $this->_login->getAllAdSpace();
		
		echo Zend_Json::encode(array('status'=>'ok','data'=>$adSpaces));
		
		exit;
	}
	
	public function lotterymenuqueryAction(){
		
		$user_id = $this->_sessionlogin->info['id'];
		
		//$gamemenu = Zend_Json::encode(array ('game5'=>'test5','game6'=>'test6','game7'=>'test7','game8'=>'test8'));
		
		//$this->redis_client->set(md5('defindgamemenu' . $user_id),$gamemenu, 2*24*60*60);
		
		$game =  $this->redis_client->get(md5('defindgamemenu' . $user_id));
		
		/*if(!isset($game))
		{
	        $gamemenu = Zend_Json::encode(array ('game0'=>'cqssc','game1'=>'slmmc','game2'=>'jlffc','game3'=>'jxssc','game4'=>'sd115','game5'=>'jsk3','game6'=>'fc3d'));
		    $this->redis_client->set(md5('defindgamemenu' . $user_id),$gamemenu, 2*24*60*60);
		}
		
		$game =  $this->redis_client->get(md5('defindgamemenu' . $user_id));*/
		
		echo Zend_Json::encode(array('status'=>'ok','data'=>$game));
		
		exit;
	}
	
	
	public function lotterymenusendAction(){
		
		$user_id = $this->_sessionlogin->info['id'];
		$gamedata = $this->_request->getPost("menudata");
		$gamedata  =  explode (',', $gamedata);
		$game =  $this->redis_client->get(md5('defindgamemenu' . $user_id));
		
		foreach ($gamedata as $key => $value)
		{
         
            $gamearr['game'.$key] = $value;
		}
		
		$playgame = json_decode($game);
		
		if($playgame->{'gameLastId'} == '')
		{
			$gamearr['gameLastId'] = '99101';	
		}
		else
		{
			$gamearr['gameLastId'] = $playgame->{'gameLastId'} ;
		}
		if($playgame->{'gameLastName'} == '')
		{
			$gamearr['gameLastName'] = 'cqssc';	
		}
		else
		{
			$gamearr['gameLastName'] = $playgame->{'gameLastName'} ;
		}
		
		$gamemenu = Zend_Json::encode($gamearr);
		
        $this->redis_client->set(md5('defindgamemenu' . $user_id),$gamemenu, 365*24*60*60);
		
		$game =  $this->redis_client->get(md5('defindgamemenu' . $user_id));
	
		echo Zend_Json::encode(array('status'=>'ok','data'=>$game));
		
		exit;
	}
	
	//获取重庆时时彩数据
	public function getgameprizelistAction(){
		$GameResult = $this->getGameResult(array(99101,99102,99104,99103,99106,99107,99105,99111,99114,99112,99108,99109,99301,99302,99303,99304,99305,99201,99401,99501,99502,99601,99306));
		if(count($GameResult)>0){
			echo Zend_Json::encode(array('status'=>'ok','data'=>$GameResult));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error'));
			exit;
		}
	}
	
	function getGameResult($gameIdArray){
		foreach ($gameIdArray as $key=>$value){
			$gameIdArray[$key] = 'firefrog_index_lastdata_'.$value;
		}
		
		$data = $this->redis_client->mget($gameIdArray);
		
		unset($gameIdArray);
		$gameResult = array();
		foreach ($data as $key=>$value){
			$value = Zend_Json::decode($value);
			if(isset($value['pondAmount'])){
				$value['pondAmount'] = str_split($value['pondAmount']);
			}
			//if(isset($value['saleEndTime']) && $value['lotteryId'] =='99101'){
			if(isset($value['saleEndTime'])){
				$value['saleEndDate']= $this->timediff(time(), getSrtTimeByLong($value['saleEndTime']));
				//$value['saleEndDate']= getSrtTimeByLong($value['saleEndTime']);
				//$gameResult[$value['saleEndDate']] = $this->timediff(time(), getSrtTimeByLong($value['saleEndTime']));
			}
			if(isset($value['omityTrend'])){
				$omityTrend = explode(';', $value['omityTrend']);
				foreach ($omityTrend as $sub_key=>$sub_value){
					if(!empty($sub_value)){
						$omityTrend1[$sub_key] =explode(',', $sub_value);
					}
				}
				$value['omityTrend'] = $omityTrend1;
				unset($omityTrend,$omityTrend1);
			}
			if(isset($value['wins'])){
				$i=0;
				$wins = array();
				foreach ($value['wins'] as $sub_key=>$sub_value){
                    $username = '';
					if(getCNSubStr($sub_key,0,1)!='$')
					{

					$username=getCNSubStr($sub_key,0,3);
                    $username.="***";
//					$username.=getCNSubStr($sub_key,-2,1);

					}
					else
					{
						$username.=getCNSubStr($sub_key,1,strlen($sub_key)-1);
					}
					/*if(getStrlen(getCNSubStr($sub_key,0,2))>2){
						$username .= getCNSubStr($sub_key,0,1).'***';
					} else {
						$username .= getCNSubStr($sub_key,0,2).'***';
					}
					if(getStrlen($sub_key)>4){
						
						if(getStrlen(getCNSubStr($sub_key, -2,2))>2){
							$username .= getCNSubStr($sub_key, -1,2);
						} else {
							$username .= getCNSubStr($sub_key, -2,2);
						}
					}*/
					$wins[$i]['username']  = $username;
					$wins[$i++]['prize'] = getMoneyFomat($sub_value/$this->_moneyUnit,0);
				}
				$value['wins'] = $wins;
			}
			if(isset($value['lotteryId'])){
				$gameResult[$value['lotteryId']] = $value;
			} else {
				$key = substr($key, strripos($key, '_')+1);
				$gameResult[$key] = $value;
			}
		}
		return $gameResult;
	}
	
	//求时间的差值
	function timediff($begin_time,$end_time) {
	     if($begin_time < $end_time){
	        $starttime = $begin_time;
	        $endtime = $end_time;
	     }
	     else{
	        $starttime = $end_time;
	        $endtime = $begin_time;
	     }
	     $timediff = $endtime-$starttime;
	     $days = sprintf("%02d", $timediff/86400); //sprintf("%04d", 2);//
	     $remain = $timediff%86400;
	     $hours = sprintf("%02d", $remain/3600);
	     $remain = $remain%3600;
	     $mins = sprintf("%02d", $remain/60);
	     $secs = sprintf("%02d", $remain%60);
	     
	     $res = array("day" => str_split($days),"hour" => str_split($hours),"min" => str_split($mins),"sec" => str_split($secs));
	     return $res;
	}

        //判断一代回收后用户初次登入
        public function isLevelRecycleFirstLogin($account) {
           $flag = '';
           $data = array();
           if($account!=null){
                $data['username'] = $account;
                //检查回收记录
                $response = $this->_user->isLevelRecycleFirstLogin($data);
                $result = $response['body']['result'];
                if($result['status']=='SUCCESS' && $result['message']=='isLevelRecycleFirstLogin'){
                    $flag = 'isLevelRecycle';
                }
           }
           return $flag;
        }
        
         public function super2000Action(){
                //------------ super 2000  start------------
                $data['param']['userId'] = $this->_sessionlogin->info['id'];
                $aUri['activity'] = 'super2000';
                $res = $this->_clientObject->inRequestV2($data, $aUri);

                $super2000status = $res['body']['result']['status'];
                $super2000prize = $res['body']['result']['prize'];

                echo Zend_Json::encode(array('state'=>$super2000status,'prize'=>$super2000prize));
                exit;
                //------------ super 2000  end------------
         }
         
         public function awardAction(){
			 $prize = $this->_request->getPost('prize','');
			 $checkData['param']['userId'] = $this->_sessionlogin->info['id'];
			 $checkUrl['activity'] = 'super2000';
			 $check_result = $this->_clientObject->inRequestV2($checkData, $checkUrl);
			 if($check_result['body']["result"]["status"] == 0){
				exit;
			 }elseif($check_result['body']["result"]["status"] == 2){
				if($prize > 20){
					exit; 
				} 
			 }elseif($check_result['body']["result"]["status"] == 1){
				if($prize != 8){
					exit; 
				} 
			 }
			 
             $user_id = $this->_sessionlogin->info['id'];
             
             $param['amount'] = $prize * 10000;
             $param['userId'] = $user_id;
             $param['reason'] = 'PM-PGXX-3';
             $param['operator'] = '0';
             $param['isAclUser'] = '0';
             $param['note'] = '超级2000活动奖金';
             $data['param'] = array( $param );
            
             $url['activity'] = 'eggclick';
             $java_result = $this->_clientObject->inRequestV2($data, $url);
             if(isset($java_result['body']['result']) && count($java_result['body']['result']) > 0)	//java 回傳成功
             {
                 //die(var_dump("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"));
                 $prize_result['isSuccess'] = 1;
                 //再呼叫寫log的java api
                 $log_param['activityId'] = 5;	//活動編號,SUPER2000固定為5
                 $log_param['memo'] = '超级2000活动';
                 $log_param['prize'] = $prize;
                 $log_param['userId'] = $user_id;
                 $url1 = Zend_Registry::get( "config" )->JAVA_SERVER;
                 $url1 .= Zend_Registry::get('firefog')->activity->log;         
                 
                 $java_log_result = send_http_req($url1, $log_param); 
             }
//             echo $java_log_result;
             
         }

 }
