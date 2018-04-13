<?php
require_once SITE_ROOT . '/application/include/Client.php';

class Default_ActivitymbController extends Zend_Controller_Action {
	public $_clientObject;
	
	public function init(){
		$this->_clientObject = new Client();
	}
	
	private function initToken($token){
		$token = str_replace("-","/", $token);
		$token = str_replace("$","%", $token);
		$token = str_replace("!","=", $token);
		$token = str_replace("*","+", $token);
		return $token;
	}
	
	//11月紅包活動
	public function redbow3mbAction(){
		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		
		$token = getSecurityInput($this->_request->get("sid"));
		//print_r($token);
		$this->view->token = $token ;
		$this->view->display('default/ucenter/activity/2016_decBowOnemb.tpl');
	}
	//12月紅包活動(第一周)VIEW
	public function decbowmbAction(){
		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$token = getSecurityInput($this->_request->get("sid"));
		//print_r($token);
		$this->view->token = $token ;
		$this->view->display('default/ucenter/activity/2016_decBowOnemb.tpl');
	}
	//12月紅包活動(第二周)VIEW
	public function decbowmbtwoAction(){
		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$token = getSecurityInput($this->_request->get("sid"));
		//print_r($token);
		$this->view->token = $token ;
		$this->view->display('default/ucenter/activity/2016_decBowTwomb.tpl');
	}
	//12月紅包活動(第三周)VIEW
	public function decbowmbthreeAction(){
		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$token = getSecurityInput($this->_request->get("sid"));
		//print_r($token);
		$this->view->token = $token ;
		$this->view->display('default/ucenter/activity/2016_decBowThreemb.tpl');
	}
	//12月紅包活動(第四周)VIEW
	public function decbowmbfourAction(){
		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$token = getSecurityInput($this->_request->get("sid"));
		//如果手機沒有token則報404,否則正常顯示
		if(empty($token)){
			$this->view->display('default/error/404.tpl');
		}else{
			//print_r($token);
			$this->view->token = $token ;
			$this->view->display('default/ucenter/activity/2016_decBowFourmb.tpl');
		}
	}
	//12月活動報名查詢(第一周)
	public function querysignup20161201Action(){
		
		$activitycode = '20161201';
		/* $account = $this->_sessionlogin->info['account']; */

		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		
		$data['param']['token'] = $token ;
		$data['param']['activitycode'] = $activitycode;
		$aUri['activity'] = 'novQuerySignUp';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//12月活動報名查詢(第二周)
	public function querysignup20161202Action(){
		$activitycode = '20161202';
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token ;
		$data['param']['activitycode'] = $activitycode;
		$aUri['activity'] = 'decQuerySignUp';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//12月活動報名查詢(第三周)
	public function querysignup20161203Action(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token ;
		$aUri['activity'] = 'decQuerySignUp03';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//12月活動報名查詢(第四周)
	public function querysignup20161204Action(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token ;
		$aUri['activity'] = 'decQuerySignUp04';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//12月活動報名(第一周)
	public function signup20161201Action(){
		//$actId = getSecurityInput($this->_request->getPost("actId"));
		$activitycode = '20161201';
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$week = getSecurityInput($this->_request->getPost("week"));

		$data['param']['token'] = $token ;
		$data['param']['activitycode'] = $activitycode;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$data['param']['week'] = $week;
		
		
		$aUri['activity'] = 'decSignUp';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//12月活動報名(第二周)
	public function signup20161202Action(){
		//$actId = getSecurityInput($this->_request->getPost("actId"));
		$activitycode = '20161201';
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$week = getSecurityInput($this->_request->getPost("week"));
		$data['param']['token'] = $token ;
		$data['param']['activitycode'] = $activitycode;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$data['param']['week'] = $week;
		
		
		$aUri['activity'] = 'decSignUp02';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//12月活動報名(第三周)
	public function signup20161203Action(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$week = getSecurityInput($this->_request->getPost("week"));
		$data['param']['token'] = $token ;
		$data['param']['activitycode'] = $activitycode;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$data['param']['week'] = $week;
		
		
		$aUri['activity'] = 'decSignUp03';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//12月活動報名(第四周)
	public function signup20161204Action(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$week = getSecurityInput($this->_request->getPost("week"));
		$data['param']['token'] = $token ;
		$data['param']['activitycode'] = $activitycode;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$data['param']['week'] = $week;
		
		
		$aUri['activity'] = 'decSignUp04';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//取前200名(第四周)
	public function getfront200Action(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token ;
		$aUri['activity'] = 'decQuerySignUp_get200';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
	
		echo json_encode($res['body']['result']);
	}
	//取得目前排名(第四周)
	public function getrankAction(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token ;
		//$data['param']['activitycode'] = $activitycode;
		$aUri['activity'] = 'decQuerySignUp_rank';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		
		echo json_encode($res['body']['result']);
	}
	
	//11月活動3當日投注量
	public function redbow3chekbetsAction(){
		//$userid = $this->_sessionlogin->info['id'];
		
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		
		//print_r('token='.$token);
		//exit;
		
		$data['param']['token'] = $token;
	
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
	
	//11月紅包活動報名
	public function redbowsignupAction(){
		//$account = $this->_sessionlogin->info['account'];
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		//print_r($token);
		$data['param']['token'] = $token;
		
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$week = getSecurityInput($this->_request->getPost("week"));
	
		$data['param']['week'] = $week;
		$data['param']['account'] = $account;
		$data['param']['month'] = intval($month);
		$data['param']['source'] = $source;
	
		$aUri['activity'] = 'novSignUp';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
	
		echo $res['body']['result'];
	}
	
	//查詢11月紅包活動報名資格
	public function redbowquerysignupAction(){
		//$account = $this->_sessionlogin->info['account'];
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		//print_r($token);
		$data['param']['token'] = $token;
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
	
	//11月紅包活動
	public function redbow4mbAction(){
		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
	
		$token = getSecurityInput($this->_request->get("sid"));
		//print_r($token);
		$this->view->token = $token ;
		$this->view->display('default/ucenter/activity/2016_novRedbowFourmb.tpl');
	}
	
	public function redbow4querymbAction(){
		//nov4Query
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		//print_r($token);
		$data['param']['token'] = $token;
	
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
	
	
	
	
	
	
	//12月活動聚合頁 (第四周)
	public function decgrouppagembAction(){
		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$token = getSecurityInput($this->_request->get("sid"));
		$this->view->token = $token ;
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
		
		$aUri['activity'] = 'decGroupQuery';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		$userRankingWins = $res['body']['result']['userRankingWins'];//已開獎名單
		$activityConfigs = $res['body']['result']['activityConfigs'];//活動設定檔
		$configTimes = $res['body']['result']['configTimes'];//開獎時間定義
		$isWined = $res['body']['result']['isWined'];//開獎時間定義
		$isSecondActivityEnd = $res['body']['result']['isSecondActivityEnd']; //一月第二週活動是否結束
		
		$this->view->userRankingWins = json_encode($userRankingWins);
		$this->view->activityConfigs = json_encode($activityConfigs);
		$this->view->configTimes = json_encode($configTimes);
		$this->view->isWined = $isWined;
		$this->view->isSecondActivityEnd = $isSecondActivityEnd;// 0:尚未結束, 1:已經結束
		
		
		$this->view->display('default/ucenter/activity/group/2016_decgrouppage_mb.tpl');
	}
	
	//12 月 聚合頁開獎名單
	public function groupwinlistmbAction(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$week = getSecurityInput($this->_request->getPost("week"));
		$point = getSecurityInput($this->_request->getPost("point"));
		$data['param']['token'] = $token;
		$data['param']['week'] = $week;
		$data['param']['point'] = $point;
		
		$aUri['activity'] = 'groupDrawListQuery';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		$resultData = $res['body']['result'];
		echo json_encode($resultData);
	}
	
	//1月報名查詢, 聚合頁
	public function queryjansignupmbAction(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
		$aUri['activity'] = 'queryJanSignUp';
		$res = $this->_clientObject->inRequestV2($data, $aUri);//回傳結果,取數據資料*/
		$isSigned = $res['body']['result']['isSigned'];
		
		echo $res['body']['result']['isSigned'];
	}
	
	//1月聚合頁 報名
	public function jansignupmbAction(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
		
		$aUri['activity'] = 'janSignUp';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料
		
		echo $res['body']['result']['isSignUpSuccess'];
	}
	
	
	//春節活動聚合頁 入口
	public function newyeargrouppageAction(){
		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$token = getSecurityInput($this->_request->get("sid"));
		$this->view->token = $token ;
		$this->view->display('default/ucenter/activity/group/2017_happyNewYear_mb.tpl');
	}
	
	//春節活動聚合頁 活動時間
	public function newyeargroupdateAction(){
		
		$aUri['activity'] = 'newYearDateCheck';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		echo json_encode($res['body']['result']);
	}
	
	
	//春節活動聚合頁充值送兩倍 初始化
	public function newyearchargedoubleinitAction(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
		
		//充值送兩倍
		$aUri['activity'] = 'newYearChargeDoubleInit';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		echo json_encode($res);
	}
	
	//春節活動聚合頁充值送兩倍 領取獎金
	public function newyearchargedoublegetawardAction(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
		
		$aUri['activity'] = 'newYearChargeDoubleGetAward';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		echo json_encode($res);
	}
	
	//春節活動聚合頁整點來抽獎 初始化
	public function newyearlotteryinitAction(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
		
		$aUri['activity'] = 'newYearLotteryInit';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		echo json_encode($res['body']['result']);
	}
	
	//春節活動聚合頁整點來抽獎 抽紅包
	public function newyearlotterylotteryAction(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
		
		$aUri['activity'] = 'newYearLotteryLottery';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		//$res['body']['result']['isVip'] = $this->_sessionlogin->info['vipLvl']==0?false:true;
		
		echo json_encode($res['body']['result']);
	}	
	
	//春節活動聚合頁你充我就送 初始化
	public function newyearchargegiveinitAction(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
		
		$aUri['activity'] = 'newYearChargeGiveInit';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		echo json_encode($res['body']['result']);
	}
	
	//春節活動聚合頁你充我就送 抽紅包
	public function newyearchargegiveredbowawardAction(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		
		$sn = getSecurityInput($this->_request->getPost("sn"));
		
		$data['param']['token'] = $token;
		$data['param']['sn'] = $sn;
		
		$aUri['activity'] = 'newYearChargeGiveRedBowAward';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		echo json_encode($res['body']['result']);
	}
	
	//春節活動聚合頁VIP領紅包 初始化
	public function newyearvipinitAction(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
		
		$aUri['activity'] = 'newYearVipInit';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		echo json_encode($res['body']['result']);
	}
	
	//春節活動聚合頁VIP領紅包 抽紅包
	public function newyearvipredbowsaveAction(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
		
		$activityCode = getSecurityInput($this->_request->getPost("activityCode"));
		$newyeartype = getSecurityInput($this->_request->getPost("newyeartype"));
		
		$data['param']['activityCode'] = $activityCode;
		$data['param']['newyeartype'] = $newyeartype;
		
		$aUri['activity'] = 'newYearVipRedBowSave';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		//$res['body']['result']['isVip'] = $this->_sessionlogin->info['vipLvl']==0?false:true;
		
		echo json_encode($res['body']['result']);
	}
	
		//--------20172月開春活動-----------------------
	public function openspringAction(){
		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$token = getSecurityInput($this->_request->get("sid"));
		$this->view->token = $token ;
		$this->view->display('default/ucenter/activity/2017_febOpenSpringmb.tpl');
	}
	
		//20172月開春活動 開春簽到送禮 浮層初始化
	public function redbowstateAction(){
		$token = getSecurityInput($this->_request->get("token"));
		$token = str_replace(" ","+",$token);
        $data['param']['token'] = $token;
		//充值送兩倍
		$aUri['activity'] = 'openSpringRedbowState';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode(
				array (
						'status' => $res['body']['result'],
						'url' =>  $_SERVER['HTTP_HOST'].'/activitymb/openspring'
				)
		);
	}
	
		//20172月開春活動 開春簽到送禮初始化
	public function openspringinitAction(){
	    $token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;

		$aUri['activity'] = 'openSpringInit';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		echo json_encode($res['body']['result']);
	}
	
	//20172月開春活動 開春簽到送禮領獎
	public function openspringawardAction(){
		$resultId = getSecurityInput($this->_request->getPost("redbow"));
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
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
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
		$aUri['activity'] = 'querySignUp20170202';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	//20172月開春活動 報名查詢(第3周)
	public function query20170203signupAction(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
		$aUri['activity'] = 'querySignUp20170203';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	
		// 20172月開春活動 會員報名(第2周)
	public function febsecondsignupAction(){
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
		$data['param']['account'] = $account;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$aUri['activity'] = 'signUp20170202';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	
		//20172月開春活動 會員報名(第3周)
	public function febthirdsignupAction(){
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
		$data['param']['account'] = $account;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$aUri['activity'] = 'signUp20170203';
		$res = $this->_clientObject->inRequestV2($data,$aUri);//回傳結果,取數據資料*/
		
		echo $res['body']['result'];
	}
	
	//2017三月第一個活動
	public function activity20170301initAction(){
		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$token = getSecurityInput($this->_request->get("sid"));
		$this->view->token = $token ;
		$data['param']['token'] = $this->initToken($token);
		$aUri['activity'] = 'activity20170301init';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$this->view->display('default/ucenter/activity/2017/March/mb/activitymb01.tpl');
	}
        
        	//20173月開春活動 開春簽到送禮 浮層初始化
	public function activity20170301stateAction(){
                            $token = getSecurityInput($this->_request->get("token"));
                            $token = str_replace(" ","+",$token);
                            $data['param']['token'] = $token;
                            //充值送兩倍
                            $aUri['activity'] = 'activity20170301init';
                            $res = $this->_clientObject->inRequestV2($data, $aUri);
                            $status = 0;
                            if($res['body']['result']['status'] == '-5'){
                                    $status = 2;
                            }else if ($res['body']['result']['status'] == '1' && $res['body']['result']['initData']['isQualify'] ==1 ){
                                    $status = 1;
                            }else{
                                    $status = 0;
                            }
                            echo json_encode(
                                                            array (
                                                                            'status' => $status,
                                                                            'url' =>  $_SERVER['HTTP_HOST'].'/activitymb/activity20170301init'
                                                            )
                            );
                    }
	
	//2017三月第一個活動派獎
	public function activity20170301awardAction(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
		$aUri['activity'] = 'activity20170301award';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']['result']);
	}
        
                       //2017三月第四週活動
	public function activity20170304initAction(){
		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$token = getSecurityInput($this->_request->get("sid"));
                                             $token = str_replace(" ","+",$token);
		$this->view->token = $token ;
		$data['param']['token'] = $this->initToken($token);
		$data['param']['account'] = $account;
		
                                             $aUri['activity'] = 'activity20170304init';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$signStatus = $res['body']['result']['signStatus'];
                                             $this->view->signStatus = $signStatus;
                                             $this->view->display('default/ucenter/activity/2017/March/mb/activitymb04.tpl');
	}
        
                      //2017三月第四個活動報名
	public function activity20170304signupAction(){
                                             $source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
                                             $data['param']['account'] = $account;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$aUri['activity'] = 'activity20170304signup';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']['result']['status']);
	}
	
	//2017四月問卷調查 浮層初始化
	public function activity201704surveyenterAction(){
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = str_replace(" ","+",$token);
		$data['param']['token'] = $token;
		$this->view->token = $token ;
		$aUri['activity'] = 'activity201704surveyEnter';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']);
	}
	
	//2017四月問卷調查
	public function activity201704surveyAction(){
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		$token = getSecurityInput($this->_request->get("token"));
		/* $token = 'BKL7z/tPtUad9Gs2hd0uJQ+uv9i7qZn+GpW+AX9D+kwJPc8gJW9GlQ=='; */
		$token = $this->initToken($token);
		$token = str_replace(" ","+",$token);
		$data['param']['token'] = $token;
		$this->view->token = $token ;
		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$aUri['activity'] = 'activity201704surveyInit';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$result = $res['body']['result'];
		$this->view->account          = $account;
		$this->view->systemTime       = $result['systemTime'];
		$this->view->startDate        = $result['startDate'];
        $this->view->endDate          = $result['endDate'];
		$this->view->award            = $result['award'];
		$this->view->awardList        = $result['awardList'] != null ? $result['awardList']: 0 ;
		$this->view->awardListSize    = count($result['awardList'])-1;
		$this->view->isQualifi        = $result['isQualifi'];
		$this->view->countHour        = $result['countHour'];
		$this->view->countMin         = $result['countMin'];
		$this->view->activityStart  = $result['activityStart'];
		/* print_r($result);
		exit; */
		$this->view->display('default/ucenter/activity/2017/April/activitySurveyMb.tpl');
	}
	//2017四月問卷調查，檢查用戶充值總額
	public function activitycheckbettotalAction(){
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
		$this->view->token = $token ;
		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$aUri['activity'] = 'checkUserBetTotal';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']['result']);
	}
	
	//2017四月問卷調查，開始搖獎
	public function activitystartawardAction(){
		$account = $this->_sessionlogin->info['account'];
		$data['param']['account'] = $account;
		$token = getSecurityInput($this->_request->getPost("token"));
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
		$this->view->token = $token ;
		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$aUri['activity'] = 'startaward';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']['result']);
	}
                       //2017四月第二周活動初始
	public function activity20170402initAction(){
            
                                             $this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
                                             $token = getSecurityInput($this->_request->getParam( "token" ));
                                             $token = $this->initToken($token);
                                             $token = str_replace(" ","+",$token);
                                             $this->view->token = $token ;
                                             
                                              //介接API
                                             $aUri['activity'] = 'activity20170402init';
		$data['param']['token'] = $token; 
               
                                             $res = $this->_clientObject->inRequestV2($data, $aUri);
                                             
                                             if($res['body']['result']['status'] < 0 ){
                                                    $aData = array (
                                                            'status'=> $res['body']['result']['status'] 
                                                    );
                                                    $this->view->aData = json_encode($aData);
                                                    $this->view->display('default/ucenter/activity/2017/April/mb/activity02.tpl');
                                                    return;
                                             }
                                             
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
                                            
                                            $aData = array (
                                                 'shortOf'=> getMoneyFomat(floatval($leftMoney/10000),2), //下一盞燈還需投注多少
                                                 'shortOfAmount' =>$leftMoney/10000,
                                                 'awards'=> $awards,
                                                 'nowLevel'=>$nowLevel,
                                                 'levels'=> array(0, 1000, 10000, 80000, 150000, 200000, 500000, 800000)
                                             );
                                             
                                             $this->view->aData = json_encode($aData);
                                             $this->view->display('default/ucenter/activity/2017/April/mb/activity02.tpl');
	}
                       //2017四月第二周活動初始
	public function activity20170402awardAction(){
                                            $typeAry = array(
                                                'rate0' =>7,'rate1' =>6,'rate2' =>5,'rate3' =>4,'rate4' =>3,'rate5' =>2,'rate6' =>1                                               
                                            );
                                            $token = getSecurityInput($this->_request->getParam( "token" ));
                                            $token = $this->initToken($token);
                                            
                                            $awardLevel = $typeAry[getSecurityInput($this->_request->getParam("level"))];
                                            
                                            $aUri['activity'] = 'activity20170402award';
                                            $data['param']['token'] = $token;
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
                                                     'isSuccess'=> $isSuccess, //是否派錢
                                                     'msg' => "",
                                                     'data' => array( 'awardAmount'=> $awardAmount )//中奖金额  
                                             );
                                             echo json_encode($aData);
                                             exit;
                       }
//2017四月第四周活動
	public function activity20170404initAction(){
		// print_r($_POST);
		// exit;
		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$token = getSecurityInput($this->_request->get("sid"));
        $token = str_replace(" ","+",$token);
		$this->view->token = $token ;
		$data['param']['token'] = $this->initToken($token);
		$data['param']['account'] = $account;
			
		$aUri['activity'] = 'activity20170404init';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
	
		$signStatus = $res['body']['result']['signStatus'];
		$status = $res['body']['result']['status'];
		$this->view->status = $status;
		$this->view->signStatus = $signStatus;
		$this->view->display('default/ucenter/activity/2017/April/mb/activity04mb.tpl');	
	}
	
	//2017四月第四周活動報名
	public function activity20170404signupAction(){
		
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$token = getSecurityInput($this->_request->getPost("token"));
		
		//print_r($token);exit;
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
        $data['param']['account'] = $account;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$aUri['activity'] = 'activity20170404signup';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']['result']['status']);
	}
	
	//20175月第一周活動
	public function activity20170501initAction(){

		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$token = getSecurityInput($this->_request->get("sid"));
        $token = str_replace(" ","+",$token);
		
		
		$data['param']['token'] = $this->initToken($token);
		$data['param']['account'] = $account;
		
		$aUri['activity'] = 'activity20170501init';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$awardStatus = $res['body']['result']['awardStatus'];
		$signStatus = $res['body']['result']['signStatus'];
		$status = $res['body']['result']['status'];
		$this->view->status = $status;
		$this->view->signStatus = $signStatus;
		$this->view->awardStatus = $awardStatus;
		$this->view->token = $token ;
		$this->view->display('default/ucenter/activity/2017/May/mb/activity01.tpl');	
	}
	
	//20175月第一周活動報名
	public function activity20170501signupAction(){
		
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$token = getSecurityInput($this->_request->getPost("token"));
		
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
        $data['param']['account'] = $account;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$aUri['activity'] = 'activity20170501signup';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']['result']['status']);
		
	}
	
	//20175月第一周取倍數
	public function activity20170501rateAction(){
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$token = getSecurityInput($this->_request->getPost("token"));
		
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
        $data['param']['account'] = $account;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		
		$aUri['activity'] = 'activity20170501rate';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']['result']);
	}
	//20175月第一周領獎
	public function activity20170501awardAction(){
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$token = getSecurityInput($this->_request->getPost("token"));
		
		$bet = getSecurityInput($this->_request->getPost("bet"));
		$rate = getSecurityInput($this->_request->getPost("rate"));
		$multiple = getSecurityInput($this->_request->getPost("multiple"));
		$award = getSecurityInput($this->_request->getPost("award"));
		
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
        $data['param']['account'] = $account;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		
		$data['param']['bet'] = $bet;
		$data['param']['rate'] = $rate;
		$data['param']['multiple'] = $multiple;
		$data['param']['award'] = $award;
		
		$aUri['activity'] = 'activity20170501award';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']['result']);
	}
	//2017五月第三週活動
	public function activity20170503initAction(){
		// print_r($_POST);
		// exit;
		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$token = getSecurityInput($this->_request->get("sid"));
        $token = str_replace(" ","+",$token);
		$this->view->token = $token ;
		$data['param']['token'] = $this->initToken($token);
		$data['param']['account'] = $account;
			
		$aUri['activity'] = 'activity20170503init';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		$awardStatus = $res['body']['result']['awardStatus'];
		$signStatus = $res['body']['result']['signStatus'];
		$status = $res['body']['result']['status'];
		
		$this->view->awardStatus = $awardStatus;
		$this->view->status = $status;
		$this->view->signStatus = $signStatus;
		$this->view->display('default/ucenter/activity/2017/May/mb/activity03.tpl');	
	}
	
	//2017五月第三週活動報名
	public function activity20170503signupAction(){
		
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$token = getSecurityInput($this->_request->getPost("token"));
		
		//print_r($token);exit;
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
        $data['param']['account'] = $account;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$aUri['activity'] = 'activity20170503signup';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']['result']['status']);
	}
	
	//2017六月第一週活動
	public function activity20170601initAction(){
		//增加跳轉到重慶時彩
		$game_server = getGameDomain(getWebSiteDomain());
		$this->view->game_server = $game_server;
		
		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$token = getSecurityInput($this->_request->get("sid"));
        $token = str_replace(" ","+",$token);
		$this->view->token = $token ;
		$data['param']['token'] = $this->initToken($token);
		$data['param']['account'] = $account;
			
		$aUri['activity'] = 'activity20170601init';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		
		$awardStatus = $res['body']['result']['awardStatus'];
		$signStatus = $res['body']['result']['signStatus'];
		$status = $res['body']['result']['status'];
		
		$this->view->awardStatus = $awardStatus;
		$this->view->status = $status;
		$this->view->signStatus = $signStatus;
		$this->view->display('default/ucenter/activity/2017/June/mb/activity01.tpl');	
	}
	
	//2017六月第一週活動報名
	public function activity20170601signupAction(){
		
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$token = getSecurityInput($this->_request->getPost("token"));
		
		//print_r($token);exit;
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
        $data['param']['account'] = $account;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$aUri['activity'] = 'activity20170601signup';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']['result']['status']);
	}
	
	//2017六月第三週活動
	public function activity20170603initAction(){

		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$token = getSecurityInput($this->_request->get("sid"));
        $token = str_replace(" ","+",$token);
		
		
		$data['param']['token'] = $this->initToken($token);
		$data['param']['account'] = $account;
		
		$aUri['activity'] = 'activity20170603init';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$awardStatus = $res['body']['result']['awardStatus'];
		$signStatus = $res['body']['result']['signStatus'];
		$status = $res['body']['result']['status'];
		$bet = $res['body']['result']['bet'];
		
		$this->view->status = $status;
		$this->view->signStatus = $signStatus;
		$this->view->awardStatus = $awardStatus;
		$this->view->token = $token;
		$this->view->bet = $bet;
		$this->view->display('default/ucenter/activity/2017/June/mb/activity03.tpl');	
	}
	
	//2017六月第三週活動報名
	public function activity20170603signupAction(){
		
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$token = getSecurityInput($this->_request->getPost("token"));
		
		//print_r($token);exit;
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
        $data['param']['account'] = $account;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$aUri['activity'] = 'activity20170603signup';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']['result']['status']);
	}
	
	//2017六月第三週派獎
	public function activity20170603awardAction(){
		
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$token = getSecurityInput($this->_request->getPost("token"));
		
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
        $data['param']['account'] = $account;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$aUri['activity'] = 'activity20170603award';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']['result']);
	}
	
	//2017七月第一週活動
	public function activity20170701initAction(){

		$this->view->path_img = IMG_ROOT;
		$this->view->path_js = JS_ROOT;
		$token = getSecurityInput($this->_request->get("sid"));
        $token = str_replace(" ","+",$token);
		
		
		$data['param']['token'] = $this->initToken($token);
		$data['param']['account'] = $account;
		
		$aUri['activity'] = 'activity20170701init';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		$awardStatus = $res['body']['result']['awardStatus'];
		$signStatus = $res['body']['result']['signStatus'];
		$status = $res['body']['result']['status'];
		$bet = $res['body']['result']['bet'];
		$extraAward = $res['body']['result']['extraAward'];
		$statusArray = $res['body']['result']['statusArray'];
		
		$this->view->status = $status;
		$this->view->signStatus = $signStatus;
		$this->view->awardStatus = $awardStatus;
		$this->view->token = $token;
		$this->view->bet = $bet;
		$this->view->extraAward = $extraAward;
		$this->view->statusArray = $statusArray;
		
		$this->view->display('default/ucenter/activity/2017/July/mb/activity01.tpl');	
	}
	
	//2017七月第一週活動報名
	public function activity20170701signupAction(){
		
		$source = getSecurityInput($this->_request->getPost("source"));
		$month = getSecurityInput($this->_request->getPost("month"));
		$token = getSecurityInput($this->_request->getPost("token"));
		
		//print_r($token);exit;
		$token = $this->initToken($token);
		$data['param']['token'] = $token;
        $data['param']['account'] = $account;
		$data['param']['source'] = $source;
		$data['param']['month'] = intval($month);
		$aUri['activity'] = 'activity20170701signup';
		$res = $this->_clientObject->inRequestV2($data, $aUri);
		echo json_encode($res['body']['result']['status']);
	}
}