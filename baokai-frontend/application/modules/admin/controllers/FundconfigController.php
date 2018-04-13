<?php

class Admin_FundconfigController extends Fundcommon {

    private $_downArray;
    private $_oWithDrawConf;
    private $_param;
    protected $_aAppealStatus;

    public function init() {
        parent::init();
        $this->_downArray = range(1, 10);
        $this->_oWithDrawConf = new WithdrawConf();
        $this->_aAppealStatus = array('', '未处理', '审核通过', '审核未通过','待定');
        $this->_aAppealVip = array('否', '是');
    }

    public function indexAction() {
        $this->_param = $this->_request->getParam('parma');
        
        $aParam = array(
            'wcf0' => 'drawBypass',//提现分流配置
            'wcf1' => 'drawreDetail', //提现上下限配置
            'wcf2' => 'drawnonSet', //不可提余额配置
            'wcf3' => 'drawSecurity', //风险提现门槛设置
            'wcf4' => 'examinestips', //審核提示配置
            'wcf5' => 'arguetips', //申訴提示配置
            'wcf6' => 'quicktips', //緊急提示
            'wcf7' => 'drawSeperate', //緊急提示
            'sv4' => 'displayWhiteList', //可提现用户白名单
            'sv5' => 'saveDrawWhiteUser', //添加可提现用户白名单
            'sv6' => 'delDrawWhiteUser', //删除可提现用户白名单
            'sv7' => 'getDrawWhiteList', //获取可提现用户白名单
            'sv11' => 'drawarguelist', //提現申訴處理
            'sv12' => 'arguequerytips', //審核提示配置的增加/刪除/新增的查詢
            'sv13' => 'drawarguelist123', //提現申訴處理(未使用)
        	'saveDrawTips' => 'saveDrawTips', //審核提示儲存           
        	'saveAppealTips' => 'saveAppealTips', //申訴配置儲存        	
            'loadappeal' => 'loadWithDrawAppealData', //加载提款申訴數據
            'appealdetail' => 'queryAppealDetail', //提現申訴查看功能
            'appealdo' => 'appealgogo', //提現申訴審核功能
        	'addUrgency'=>'addUrgency',
        	'exappeal' => 'downloadWithAppealData',//下载提現申訴数据 
        	'updateUrgency'=>'updateUrgency',
            'saveDrawBypass'=>'saveDrawBypass'
        );
        
        if (array_key_exists($this->_param, $aParam) && array_key_exists($this->_param, $this->_aAclArray)) {
            $this->$aParam[$this->_param]($this->_request);
            exit;
        } elseif (isAjaxRequest()) {
            echo Zend_Json::encode(array('status' => 'error', 'data' => $this->getError('102144')));
            exit;
        } else {
            foreach ($this->_aAclArray as $key => $value) {
                if (array_key_exists($key, $aParam)) {
                    $this->$aParam[$key]($this->_request);
                    exit;
                }
            }
            $this->_redirect('/admin/Rechargemange/');
        }
    }
    
    //提现分流配置
    public function drawBypass(){
        $data['param']['type'] = '1';
        $aUrl['fundadmin'] = 'queryBypassConfigByType';
        $res = $this->_clientobject->inRequestV2($data, $aUrl);
        foreach ( $res["body"]["result"]["bypassCfgs"] as $recorder){
            if($recorder["agency"] == 0){
                $this->view->dpSetting = $recorder;
            }else{
                $this->view->thSetting = $recorder;
            }
        }
        $this->view->tipIndex = 0;
        $this->view->display ( '/admin/funds/draw/drawbypass.tpl' );
    }
    
    public function drawSeperate(){
        if ($this->getRequest()->isPost() && isset($this->_aAclArray[$this->_param.  '_post'])) {
            $seperateAmt = intval(getSecurityInput($this->_request->getPost('seperateAmt', 0)));
            $singleCut = intval(getSecurityInput($this->_request->getPost('singleCut', 0)));
            $data = array(
                'seperateAmt' => $seperateAmt * $this->_moneyUnit, 'singleCut' => $singleCut * $this->_moneyUnit
            );
            $this->saveconfigvalue($data, 'fund', 'drawSeperate');
        }
        $res =$this->getconfigvaluebykey("fund","drawSeperate");
        
        $resJson = Zend_Json::decode($res['val']);
        $this->view->tipIndex = 7;
        $this->view->seperateAmt = $resJson['seperateAmt']/ $this->_moneyUnit;
        $this->view->singleCut = $resJson['singleCut']/ $this->_moneyUnit;
        $this->view->display ( '/admin/funds/draw/drawseperate.tpl' );
    }
      
    
    //提现上下限配置
    public function drawreDetail() {
        $res = array();
        if ($this->getRequest()->isPost() && isset($this->_aAclArray[$this->_param . '_post'])) {
            $time = intval(getSecurityInput($this->_request->getPost('time', 1)));
            $viptime = intval(getSecurityInput($this->_request->getPost('viptime', 1)));
            $lowlimit = floatval(getSecurityInput($this->_request->getPost('lowlimit', 0)));
            $uplimit = floatval(getSecurityInput($this->_request->getPost('uplimit', 0)));
            $viplowlimit = floatval(getSecurityInput($this->_request->getPost('viplowlimit', 0)));
            $vipuplimit = floatval(getSecurityInput($this->_request->getPost('vipuplimit', 0)));

            if ($time != '-1' && ($time < 1 || $time > 10)) {
                $this->addErr('102076');
                $this->log('提现上下限普通用户提现次数配置错误不能为:' . $time . '!');
            }
            if ($viptime != '-1' && ($viptime < 1 || $viptime > 10)) {
                $this->addErr('102077');
                $this->log('提现上下限VIP用户提现次数配置错误不能为:' . $viptime . '!');
            }
            if ($lowlimit >= $uplimit) {
                $this->addErr('102078');
                $this->log('提现上下限普通用户提现金额配置错误不能为:' . $viptime . '!');
            }
            if ($viplowlimit >= $vipuplimit) {
                $this->addErr('102079');
                $this->log('提现上下限VIP用户提现金额配置错误不能为:' . $viptime . '!');
            }
            if (!$this->isErrFree()) {
                $this->res_add_url('提现相关配置', '/admin/Fundconfig/index?parma=sv1', false, true);
                $this->res_show(true, true, 'funds');
            }
            $data = array(
                'user' => array('time' => $time, 'lowLimit' => $lowlimit * $this->_moneyUnit, 'upLimit' => $uplimit * $this->_moneyUnit),
                'vip' => array('time' => $viptime, 'lowLimit' => $viplowlimit * $this->_moneyUnit, 'upLimit' => $vipuplimit * $this->_moneyUnit)
            );
            if (!$res = $this->saveconfigvalue($data, 'fund', 'withdraw', 'saveConfigWithdraw')) {
                $this->log('提现上下限配置失败!');
                $this->addErr('102080');
                $this->res_add_url('提现相关配置', '/admin/Fundconfig/index?parma=sv1', false, true);
                $this->res_show(true, true, 'funds');
            }
        }
        if (count($res) <= 0) {
            $res = $this->getconfigvaluebykey('fund', 'withdraw', 'getconfigvaluebywithdraw');
        }
        $result = array();
        if (count($res['val'])) {
            foreach ($res['val'] as $key => $value) {
                $result[$key]['time'] = $value['time'];
                $result[$key]['lowLimit'] = $value['lowLimit'] / $this->_moneyUnit;
                $result[$key]['upLimit'] = $value['upLimit'] / $this->_moneyUnit;
            }
        }
        $this->view->tipIndex = 1;
        $this->view->downArray = $this->_downArray;
        $this->view->res = $result;
        $this->view->title = '提现上下限配置';
        $this->view->display('/admin/funds/draw/drawredetail.tpl');
    }

    //不可提余额配置
    public function drawnonSet() {
        $res = array();
        if ($this->getRequest()->isPost() && isset($this->_aAclArray[$this->_param . '_post'])) {
            $bel = intval(getSecurityInput($this->_request->getPost('bel')));
            if (!$res = $this->saveconfigvalue($bel, 'fund', 'bet', 'saveConfigbet')) {
                $this->addErr('102083');
                $this->log($this->getError('102083'));
                $this->res_add_url('可提现额度配置', '/admin/Fundconfig/index?parma=sv2', false, true);
                $this->res_show(true, true, 'funds');
            }
        }

        if (count($res) <= 0) {
            $res = $this->getconfigvaluebykey('fund', 'bet', 'getconfigvaluebybet');
        }
        $this->view->res = $res;
        $this->view->title = '可提现额度配置';
        $this->view->tipIndex = 2;
        $this->view->display('/admin/funds/draw/drawnonset.tpl');
    }

    //风险提现门槛设置
    public function drawSecurity() {
        $res = array();
        if ($this->getRequest()->isPost() && isset($this->_aAclArray[$this->_param . '_post'])) {
            $time = intval(getSecurityInput($this->_request->getPost('time', '1')));
            $amt = floatval(getSecurityInput($this->_request->getPost('amt', '1')));
            $part = intval(getSecurityInput($this->_request->getPost('part', '1')));
            if (!in_array($part, array(0, 1))) {
                $part = 1;
            }
            $data = array(
                'part' => $part,
                'amt' => $amt * $this->_moneyUnit,
                'time' => $time
            );
            if (!$res = $this->saveconfigvalue($data, 'fund', 'withdrawCheck', 'saveConfigWithdrawCheck')) {
                $this->addErr('102084');
                $this->log($this->getError('102084'));
                $this->res_add_url('风险提现-门槛配置', '/admin/Fundconfig/index?parma=sv2', false, true);
                $this->res_show(true, true, 'funds');
            }
        }
        //权限检查
        if (count($res) <= 0) {
            $res = $this->getconfigvaluebykey('fund', 'withdrawCheck', 'getconfigvaluebywithdrawCheck');
        }
        $res['val']['amt'] = $res['val']['amt'] / $this->_moneyUnit;
        $this->view->res = $res;
        $this->view->title = '风险提现-门槛配置';
        $this->view->tipIndex = 3;
        $this->view->display('/admin/funds/draw/drawsecurity.tpl');
    }

    public function displayWhiteList() {
        $this->view->title = '可提现额度白名单';
        $this->view->display('/admin/funds/draw/drawwhitelist.tpl');
    }

    public function drawarguelist() {
        $this->view->title = '提现申诉处理';
        $this->view->aWithDrawStatus = $this->_aAppealStatus;
        $this->view->assign('allTips',$this->getTips(0, null, 2));
        $this->view->display('/admin/funds/appealtips/drawarguelist.tpl');
    }

    //可提现用户白名单
    public function getDrawWhiteList() {
        $userName = getSecurityInput($this->_request->getPost('UserName'));
        $page = intval(getSecurityInput($this->_request->getPost("page")));
        $perPageNum = intval(getSecurityInput($this->_request->getPost("perPageNum")));
        $modata = array();
        $modatas = array();
        $recordNum = 0;
        $modatas['text'] = array();
        $modatas['count'] = array();
        //权限检查
        if (!isset($this->_aAclArray[$this->_param])) {
            echo Zend_Json::encode($modatas);
            exit;
        }

        if (!empty($userName)) {
            $param['accountList'] = array($userName);
        }
        $param['action'] = 2;
        $data['param'] = $param;
        $data['pager'] = array(
            'startNo' => $page * $perPageNum + 1,
            'endNo' => ($page + 1) * $perPageNum,
        );
        $res = $this->_oWithDrawConf->getWhiteDrawWhiteList($data);

        if (isset($res["result"]) && count($res["result"]) > 0) {
            foreach ($res["result"] as $recorder) {
                $modata["account"] = $recorder["account"] ? $recorder["account"] : '';
                $modata["topAcc"] = $recorder["topAcc"] ? $recorder["topAcc"] : '';
                $modata["manager"] = $recorder["operator"] ? $recorder["operator"] : '';
                $modata["date"] = $recorder["gmtCreatedString"] ? $recorder["gmtCreatedString"] : '';
                $modata["note"] = $recorder["note"] ? $recorder["note"] : '';
                array_push($modatas['text'], $modata);
            }
        }
        if (isset($res["pager"]["total"])) {
            $recordNum = $res["pager"]["total"];
        }
        array_push($modatas['count'], array("recordNum" => $recordNum)); //recordNum 页数 ，每页15条
        echo Zend_Json::encode($modatas);
        exit;
    }

    //審核提示配置
    public function examinestips() {
    	$res = array();
    	$aUri['fundadmin'] = 'searchDrawTips';
    	$tips['tipsModel'] = '1';
    	$data = array(
    			'param' => array(
    					'tips' =>$tips
    			)
    	);
    	$resultObj  = $this->_clientobject->inRequestV2($data, $aUri);
    	$res = $resultObj['body']['result'];
    	$result = array();
    	$this->view->res = $res;
    	$this->view->title = '审核提示配置';
        $this->view->tipIndex = 4;
    	$this->view->display('/admin/funds/appealtips/examinesetting.tpl');
    }

    //申訴提示配置頁面載入及更新
    public function arguetips() {
    	$res = array();
    	$aUri['fundadmin'] = 'searchAppealTips';
    	$tips['tipsModel'] = '2';
    	$data = array(
    			'param' => array(
    					'tips' =>$tips
    			)
    	);
    	$resultObj  = $this->_clientobject->inRequestV2($data, $aUri);
    	$res = $resultObj['body']['result'];
    	$result = array();
    	$this->view->res = $res;
        $this->view->tipIndex = 5;
        $this->view->title = '申诉提示配置';
        $this->view->display('/admin/funds/appealtips/arguesetting.tpl');
    }

    //緊急提示
    public function quicktips() {
    	$res = array();
    	$aUri['fundadmin'] = 'searchUrgency';
    	$data = null;
    	$resultObj  = $this->_clientobject->inRequestV2($data, $aUri);
    	$res = $resultObj['body']['result'];
    	$result = array();
    	$this->view->res = $res;
    	$this->view->title = '紧急提示配置';
        $this->view->tipIndex = 6;
    	$this->view->display('/admin/funds/appealtips/quicksetting.tpl');
    }

    //審核提示配置的增加/編輯/刪除查詢資料
    public function arguequerytips() {
        $res = array();
        if ($this->getRequest()->isPost() && isset($this->_aAclArray[$this->_param . '_post'])) {
            $time = intval(getSecurityInput($this->_request->getPost('time', 1)));
            $viptime = intval(getSecurityInput($this->_request->getPost('viptime', 1)));
            $lowlimit = floatval(getSecurityInput($this->_request->getPost('lowlimit', 0)));
            $uplimit = floatval(getSecurityInput($this->_request->getPost('uplimit', 0)));
            $viplowlimit = floatval(getSecurityInput($this->_request->getPost('viplowlimit', 0)));
            $vipuplimit = floatval(getSecurityInput($this->_request->getPost('vipuplimit', 0)));



            $data = array(
                'tipsa' => array('tipsText' => $time, 'createUser' => $lowlimit * $this->_moneyUnit),
                'tipsb' => array('tipsText' => $viptime, 'createUser' => $viplowlimit * $this->_moneyUnit),
            );
        }
        if (count($res) <= 0) {
            $res = $this->getconfigvaluebykey('fund', 'arguetips', 'getconfigvaluebyarguetips');
        }

        $result = array();
        if (count($res['val'])) {
            foreach ($res['val'] as $key => $value) {
                $result[$key]['tipsText'] = $value['tipsText'];
                $result[$key]['createUser'] = $value['createUser'];
            }
        }

        $this->view->downArray = $this->_downArray;
        $this->view->res = $result;

        $recorder = $result["body"]["result"];

        echo Zend_Json::encode(array('data' => $res));
        exit;
    }

    //加载风险提现数据
    public function loadWithDrawAppealData() {
    	$account = $this->_sessionlogin->info['account'];
        $this->_page = intval(getSecurityInput($this->_request->getPost("page", $this->_page)));
        $this->_pagesize = intval(getSecurityInput($this->_request->getPost("perPageNum", $this->_pagesize)));
        $data = array();
        $status = array(1, 2, 3);
        $request = $this->getRequest();
        //  	首次加载时不传相应值                       
        $Title = getSecurityInput($this->_request->getPost("Title", 0));
       //申訴單號碼
    	$drawserial = getSecurityInput($this->_request->get("drawserial",0)) ;
    	if($drawserial){
    		$data["param"]["appealSn"]= $drawserial ;
    	}
    	
    	//提現用戶名
    	$drawname = getSecurityInput($this->_request->get("drawname",0)) ;
    	if($drawname){
    		$data["param"]["userName"]= $drawname ;
    	}
    	
  	 	//提款金額 1
    	$drawmoney1 = getSecurityInput($this->_request->get("drawmoney1",0)) ;
    	if($drawmoney1){
    		$data["param"]["withdrawAmtFrom"]  =  floatval($drawmoney1)*$this->_moneyUnit ;
    	}
    	//提款金額2
    	$drawmoney2 = getSecurityInput($this->_request->get("drawmoney2",0)) ;
    	if($drawmoney2){
    		$data["param"]["withdrawAmtTo"]  =  floatval($drawmoney2)*$this->_moneyUnit ;
    	}
    	//是否vip
    	$isvip = getSecurityInput($this->_request->get("isvip","")) ;
    	if($isvip !=''){
    		$data["param"]["vipLvl"]  =  intval($isvip) ;
    	}
    	
    	$UserApplyTime1 = getSecurityInput($this->_request->get("UserApplyTime1",0)) ;
    	if($UserApplyTime1){
    		$data["param"]["fromWithdrawDate"]= getQueryStartTime($UserApplyTime1) ; //用户申请时间范围开始
    	}
    	$UserApplyTime2 = getSecurityInput($this->_request->get("UserApplyTime2",0)) ;
    	if($UserApplyTime2){
    		$data["param"]["toWithdrawDate"]= getQueryEndTime($UserApplyTime2) ; // 用户申请时间范围结束
    	}
		
		$UserArgueTime1 = getSecurityInput($this->_request->get("UserArgueTime1",0)) ;
    	if($UserArgueTime1){
    		$data["param"]["fromArgueDate"]= getQueryStartTime($UserArgueTime1) ; //申诉发起时间范围开始
    	}
    	$UserArgueTime2 = getSecurityInput($this->_request->get("UserArgueTime2",0)) ;
    	if($UserArgueTime2){
    		$data["param"]["toArgueDate"]= getQueryEndTime($UserArgueTime2) ; // 申诉发起时间范围结束
    	}
    	
    	$aStatusArray = array(array(1, 2, 3, 4), array(1), array(2), array(3), array(4));
        $searchStatus = getSecurityInput($this->_request->getPost('status', ''));
        if (in_array($Title, array('0')) && $searchStatus != '') {
            $status = array(intval($searchStatus));
        }else {
            $status = $aStatusArray[$Title];
        }
        $start = $this->_page + 1;
    	$data['param']['statuses'] = $status;
    	$data['param']['page'] = $this->_page;
    	$data['pager']['startNo'] = $this->_page * $this->_pagesize + 1;
    	$data['pager']['endNo'] = $start * $this->_pagesize;    	
    	
    	
        $modata = array();
        $modatas = array();
        $recordNum = 0;
        $modatas['text'] = array();
        $modatas['count'] = array();
        /* if(!isset($this->_aAclArray[$this->_param][$Title])){
          header ( 'Content-Type: application/json;charset=utf-8' );
          echo json_encode($modatas);
          exit;
          } */
        $aUri['fundadmin'] = 'queryAppealList';
        $rsr = $this->_clientobject->inRequestV2($data, $aUri);
        if (isset($rsr["body"]["result"])) {
            foreach ($rsr["body"]["result"] as $key => $value) {
                $modata["appealSn"] = $value["appealSn"];                                                       //申訴單號
                $modata["userName"] = $value["userName"];                                                      //提现用戶名                      
                $modata["withdrawAmt1"] = $value["withdrawAmt"] / $this->_moneyUnit;              //提现初始金额
                $modata["withdrawAmt"] = getMoneyFomat($modata["withdrawAmt1"], 2);       //提现真實金额                                
                $modata["withdrawTime"] = $value["withdrawTime"] > 0 ? date('Y-m-d G:i:s', getSrtTimeByLong($value["withdrawTime"])) : ''; //用户申请提现时间
				$modata["argueTime"] = $value["argueTime"] > 0 ? date('Y-m-d G:i:s', getSrtTimeByLong($value["argueTime"])) : ''; //用户申訴發起時間
                $modata["vip"] = $value["vipLvl"];                                                     //是否VIP				
                $modata["vipLvl"] = isset($this->_aAppealVip[$modata['vip']]) ? $this->_aAppealVip[$modata['vip']] : "";     //是否VIP中文
                $modata["status"] = $value["appealStatus"];                           //申訴狀態
                $modata["appealStatus"] = isset($this->_aAppealStatus[$modata['status']]) ? $this->_aAppealStatus[$modata['status']] : "无类型";    //申訴狀態中文
                $modata["appealMemo"] = $value["appealMemo"];                           //申訴備註
                $modata["appealAcct"] = $value["appealAcct"];                           //申訴審核人    
                $modata["appealTime"] = $value["appealTime"] > 0 ? date('Y-m-d G:i:s', getSrtTimeByLong($value["appealTime"])) : ''; //申訴審核時間
                $nowCheckUser = $this->redis_client->get('under_'.$modata["appealSn"]);
                $modata["nowCheckUser"] = $nowCheckUser!=null?$nowCheckUser:'' ;  //目前審核人員
                //該資料是否被審核
                if($modata["nowCheckUser"]==null || $modata["nowCheckUser"]==$account){
                	$modata["isCheck"]= false;                	
                }else{
                	$modata["isCheck"]= true;
                }
                array_push($modatas['text'], $modata);
            }
        }



        if (isset($rsr["body"]["pager"]["total"])) {
            $recordNum = $rsr["body"]["pager"]["total"];
        }
        array_push($modatas['count'], array("recordNum" => $recordNum)); //recordNum 页数 ，每页15条
        header('Content-Type: application/json;charset=utf-8');
        echo json_encode($modatas);
    }

    //獲得申訴處理詳情畫面
    public function queryAppealDetail() {
        $appealSn = getSecurityInput($this->_request->getPost('appealSn', ''));
        $nowCheckUser = $this->redis_client->get('under_'.$appealSn);
        $account = $this->_sessionlogin->info['account'];
        $isCheck = false;
        if($nowCheckUser==null|| $nowCheckUser == $account){
        	$nowCheckUser = $account;
        	$this->redis_client->set('under_'.$appealSn,$nowCheckUser,60*4);
        }else{
        	$isCheck = true;
        }
        
        $type = getSecurityInput($this->_request->getPost('type', 0));
        $title = intval(getSecurityInput($this->_request->getPost('Title', 0)));
        if ($title == '2') {
            $type = $type + 3;
        }

        $data["param"]["companyOrderNum"] = $sn;
        $data["param"]["mownecumOrderNum"] = $sn;
        $data["param"]["sn"] = $sn;

        /* 傳至JAVA的參數 */
        $data = array(
            'param' => array(
                'appealSn' => $appealSn,
                'account' => $account,
                'statuses' => $status,
                'userId' => $this->_sessionlogin->info['id'],
                'page' => $this->_page
            ),
            'pager' => array(
                'startNo' => $this->_page * $this->_pagesize + 1,
                "endNo" => $start * $this->_pagesize,
            )
        );


        $aUri['fundadmin'] = 'queryAppealList';
        $rsr = $this->_clientobject->inRequestV2($data, $aUri);
        $recorder = $rsr["body"]["result"][0];

        $modata = array();
        $modata["appealSn"] = $recorder["appealSn"];     //申訴單號                          
        $modata["userId"] = $recorder["userId"];       //提现用id        
        $modata["userName"] = $recorder["userName"];       //提现用戶名  
        $modata["drawCName"] = $recorder["userName"];       //提现用戶中文名  
        $modata["withdrawAmt"] = getMoneyFomat($recorder["withdrawAmt"]/$this->_moneyUnit,2);                  //提现金额                                                                  
        $modata["withdrawTime"] = $recorder["withdrawTime"] > 0 ? date('Y-m-d G:i:s', getSrtTimeByLong($recorder["withdrawTime"])) : ''; //用户申请提现时间
        $modata["bankName"] = $recorder["bankName"]; //提現銀行
        $modata["cardNumber"] = $this->getSecurityBankCardNum($recorder["cardNumber"]);  //銀行卡號
        $modata["vip"] = $recorder["vipLvl"];                                                     //是否VIP				
        $modata["vipLvl"] = isset($this->_aAppealVip[$modata['vip']]) ? $this->_aAppealVip[$modata['vip']] : "";     //是否VIP中文
        $modata["status"] = $recorder["appealStatus"];                           //申訴狀態
        $modata["appealStatus"] = isset($this->_aAppealStatus[$modata['status']]) ? $this->_aAppealStatus[$modata['status']] : "无类型";    //申訴狀態中文
        $modata["appealTips"] = $recorder["appealTips"];                           //申訴提示
        $modata["appealMemo"] = $recorder["appealMemo"];                           //申訴備註
        $modata["appealAcct"] = $recorder["appealAcct"];                           //申訴審核人    
        $modata["appealTime"] = $recorder["appealTime"] > 0 ? date('Y-m-d G:i:s', getSrtTimeByLong($recorder["appealTime"])) : ''; //申訴審核時間
        $modata['nowCheckUser'] = $nowCheckUser;
        $modata['isCheck'] = $isCheck;
        $modata['uploadImages'] = json_decode($recorder["uploadImages"]);
        $modata['dynamicroot'] = $this->view->dynamicroot;
        $res['head']['status'] = 0;

        //锁定逻辑=================================================
        if (isset($res['head']['status']) && $res['head']['status'] == '0') { //锁定成功 把错做页面所需要的数据返回给页面
            echo Zend_Json::encode(array('status' => 'ok', 'data' => $modata));
            exit;
        } else { //锁定失败 刷新页面
            echo Zend_Json::encode(array('status' => 'error', 'data' => '锁定失败!'));
            exit;
        }
    }

    private function getTips($tipGroupA = null, $tipGroupB = null, $tipModel = null) {
        $result = array();
        $rejectTips = array();
        $passTips = array();
        $waitTips = array();
        $checkTips = array();
        $aUrl['fundadmin'] = 'searchTips';
        $data['param']['tips']['tipsGroupa'] = $tipGroupA;
        $data['param']['tips']['tipsGroupb'] = $tipGroupB;
        $data['param']['tips']['tipsModel'] = $tipModel;
        $res = $this->_clientobject->inRequestV2($data, $aUrl);
        foreach ($res["body"]["result"]["tipsList"] as $recorder) {
            $tipsType = $recorder['tipsGroupb'];
            switch ($tipsType) {
                case 0:
                    array_push($rejectTips, $recorder);
                    break;
                case 1:
                    array_push($passTips, $recorder);
                    break;
                case 2:
                    array_push($checkTips, $recorder);
                    break;
                case 3:
                    array_push($waitTips, $recorder);
                    break;
            }
        }
        $result['rejectTips'] = $rejectTips;
        $result['passTips'] = $passTips;
        $result['checkTips'] = $checkTips;
        $result['waitTips'] = $waitTips;
        return $result;
    }

    /* 申訴審核送出按鈕 */

    public function appealgogo() {
        /* 初始化必要參數 */
        $appealSn = getSecurityInput($this->_request->getPost('appealSn', ''));
        $appealStatus = getSecurityInput($this->_request->getPost('appealStatus', '1'));
        $appealMemo = getSecurityInput($this->_request->getPost('appealMemo'));
        $appealTips = getSecurityInput($this->_request->getPost('appealTipsResult'));
        $page = getSecurityInput($this->_request->getPost('page'));
        $userId = getSecurityInput($this->_request->getPost('userId'));//幫我補傳申訴人的userId
        /* 整理+檢查參數 */
        if (!empty($page)) {
            $this->_page = $page;
        }


        $appealAcct = $this->_sessionlogin->info['account'];
        /* 傳至JAVA的參數 */
        $data = array(
            'param' => array(
                'appealSn' => $appealSn,
                'appealAcct' => $appealAcct,
                'appealStatus' => $appealStatus,
                'appealMemo' => $appealMemo,
                'appealTipsResult' => $appealTips,
                'page' => $this->_page
            ),
            'pager' => array(
                'startNo' => ($this->_page - 1) * $this->_pagesize + 1,
                "endNo" => $this->_page * $this->_pagesize,
            )
        );

        $aUri['fundadmin'] = 'updateAppealbySn';
        $result = $this->_clientobject->inRequestV2($data, $aUri);

        $recorder = $result["body"]["result"][0];
        
        //待定時不需要加
        if($appealStatus!='4'){
        	$this->redis_client->sAdd('FUND_APPEAL_STATUS_'.$userId,$appealSn);        	
        }
        
        echo Zend_Json::encode(array('isUpdateState' => $recorder["isUpdateState"]));
        exit;
    }

    //添加可提现用户白名单
    public function saveDrawWhiteUser() {
        $userName = getSecurityInput(rtrim($this->_request->getPost('userName'), ','));
        $memo = getSecurityInput($this->_request->getPost('memo', ''));

        //权限检查
        if (!isset($this->_aAclArray[$this->_param])) {
            echo Zend_Json::encode(array('isSuccess' => 0));
            exit;
        }

        if (!empty($userName)) {
            $param['accountList'] = explode(',', $userName);
            if (count($param['accountList']) > 500) {
                echo Zend_Json::encode(array('isSuccess' => 0, 'data' => $this->getError('102144')));
                exit;
            }
        } else {
            echo Zend_Json::encode(array('isSuccess' => 2));
            exit;
        }
        $param['operator'] = $this->_sessionlogin->info['account'];
        $param['note'] = $memo;
        $param['action'] = 1;
        $param['gmtCreated'] = getSendTime();
        $data['param'] = $param;
        $result = $this->_oWithDrawConf->addWhiteUser($data);
        if (isset($result['head']['status']) && $result['head']['status'] == '0') {
            echo Zend_Json::encode(array('isSuccess' => 1));
            exit;
        } else if (in_array($result['head']['status'], array('2013', '2014'))) {
            echo Zend_Json::encode(array('isSuccess' => 0, 'data' => $this->getError($result['head']['status'])));
            exit;
        } else {
            echo Zend_Json::encode(array('isSuccess' => 0, 'data' => '添加失败,请重试'));
            exit;
        }
    }

    //删除可提现用户白名单
    public function delDrawWhiteUser() {
        //权限检查
        if (!isset($this->_aAclArray[$this->_param])) {
            echo Zend_Json::encode(array('isSuccess' => 0, 'data' => $this->getError('102144')));
            exit;
        }
        $accountList = getSecurityInput($this->_request->getPost('id'));
        if (!empty($accountList)) {
            $param['accountList'] = explode(',', $accountList);
        } else {
            echo Zend_Json::encode(array('isSuccess' => 2));
            exit;
        }
        $param['action'] = 0;
        $data['param'] = $param;
        $status = $this->_oWithDrawConf->delWhiteUser($data);
        if ($status) {
            echo Zend_Json::encode(array('isSuccess' => 1));
            exit;
        } else {
            echo Zend_Json::encode(array('isSuccess' => 0));
            exit;
        }
    }
    
    //儲存審核提示配置
	public function saveDrawTips(){
       $params['tipsList'] = $this->_request->getPost('data','');
       $params['tips']['tipsModel'] = 1;
       $data['param'] = $params;
       $aUrl['fundadmin'] = 'addTips';
       $res = $this->_clientobject->inRequestV2($data, $aUrl);
    }
	
	//儲存申訴提示配置
    public function saveAppealTips(){
    	$params['tipsList'] = $this->_request->getPost('data','');
    	$params['tips']['tipsModel'] = 2;
    	$data['param'] = $params;
    	$aUrl['fundadmin'] = 'addTips';
    	$res = $this->_clientobject->inRequestV2($data, $aUrl);
    }
    
	//儲存緊急發布
    public function addUrgency(){
    	$params['urgencys']['urgencyContext'] = $this->_request->getPost('data','');
    	$data['param'] = $params;
    	$aUrl['fundadmin'] = 'addUrgency';
    	$res = $this->_clientobject->inRequestV2($data, $aUrl);
    }
    
    //撤銷緊急發布
    public function updateUrgency(){
    	$params['urgencys']['id'] = $this->_request->getPost('id','');
    	$params['urgencys']['cancelFlag'] = $this->_request->getPost('cancelFlag','');
    	$data['param'] = $params;
    	$aUrl['fundadmin'] = 'updateUrgency';
    	$res = $this->_clientobject->inRequestV2($data, $aUrl);
    }
    
    //导出提現申訴数据
    public function downloadWithAppealData(){
    	$Title = getSecurityInput($this->_request->get("Title",0)) ;
    	$fileName = '提现申诉';
    	$aTitle = array(
    			'appealSn'			=> '申诉单号',
    			'userName'		=> '提现用户名',
    			'withdrawAmt'	=> '提款金额',
    			'withdrawTime'	=> '提款时间',
    			'isVip'	=> '是否VIP',
    			'status'=> '状态',
    			'appealMemo'			=> '备注',
    			'appealAcct'		=> '审核人',
    			'appealTime'		=> '审核时间'
    	);
    	$intArray = array('bankNumber');
    	$data=array();
    	//检查权限
     	if(!isset($this->_aAclArray[$this->_param][$Title])){
     		$this->_redirect('/admin/Fundconfig/index?parma=sv11&tabIndex='.$Title);
     	}
    	
     	//申訴單號碼
    	$drawserial = getSecurityInput($this->_request->get("drawserial",0)) ;
    	if($drawserial){
    		$data["param"]["appealSn"]= $drawserial ;
    	}
    	
    	//提現用戶名
    	$drawname = getSecurityInput($this->_request->get("drawname",0)) ;
    	if($drawname){
    		$data["param"]["userName"]= $drawname ;
    	}
    	
  	 	//提款金額 1
    	$drawmoney1 = getSecurityInput($this->_request->get("drawmoney1",0)) ;
    	if($drawmoney1){
    		$data["param"]["withdrawAmtFrom"]  =  floatval($drawmoney1)*$this->_moneyUnit ;
    	}
    	//提款金額2
    	$drawmoney2 = getSecurityInput($this->_request->get("drawmoney2",0)) ;
    	if($drawmoney2){
    		$data["param"]["withdrawAmtTo"]  =  floatval($drawmoney2)*$this->_moneyUnit ;
    	}
    	//是否vip
    	$isvip = getSecurityInput($this->_request->get("isvip","")) ;
    	if($isvip !=''){
    		$data["param"]["vipLvl"]  =  intval($isvip) ;
    	}
    	
    	$UserApplyTime1 = getSecurityInput($this->_request->get("UserApplyTime1",0)) ;
    	if($UserApplyTime1){
    		$data["param"]["fromWithdrawDate"]= getQueryStartTime($UserApplyTime1) ; //用户申请时间范围开始
    	}
    	$UserApplyTime2 = getSecurityInput($this->_request->get("UserApplyTime2",0)) ;
    	if($UserApplyTime2){
    		$data["param"]["toWithdrawDate"]= getQueryEndTime($UserApplyTime2) ; // 用户申请时间范围结束
    	}
    	
        $aStatusArray = array(array(1, 2, 3, 4), array(1,4), array(2), array(3));
        $searchStatus = getSecurityInput($this->_request->getPost('status', ''));
        if (in_array($Title, array('0')) && $searchStatus != '') {
            $status = array(intval($searchStatus));
        }else {
            $status = $aStatusArray[$Title];
        }
    	
    	$data['param']['statuses'] = $status;
    	$aUri['fundadmin'] = 'queryAppealList';
    	
    	$modata = array();
    	$aContent = array();
    	$modatas['text'] = array();
    	$modatas['count'] = array();

    	$total = 0;
    	$page= $totalPage = 1;
    	$querySize = 2000;
    	$startTime= microtime(true);
    	do {
    		$data["pager"]["startNo"] = ($page-1)*$querySize+1;
    		$data["pager"]["endNo"] = $page*$querySize;
    		$rsr = $this->_clientobject->inRequestV2($data, $aUri);
    		if(isset($rsr['body']['result']) && count($rsr['body']['result'])>0){
    			foreach ( $rsr['body']['result'] as  $key=>$recorder){
    				$modata["appealSn"]= $recorder["appealSn"];//申请单号
    				$modata["withdrawAmt"] 	= $recorder["withdrawAmt"];//提现金额
    				$modata["withdrawAmt"] 	= getMoneyFomat($modata["withdrawAmt"]/$this->_moneyUnit,2) ;//提现金额
    				$modata["userName"] = $recorder["userName"]!=null ? $recorder["userName"] : '' ;//提现用户名
    				$modata["withdrawTime"] 	= $recorder["withdrawTime"] > 0 ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder["withdrawTime"])) : '';//用户申请提现时间
    				$modata["appealAcct"] 	= $recorder["appealAcct"]!=null ? $recorder["appealAcct"] : ''; //一审管理员
    				$modata["appealTime"]= $recorder["appealTime"] > 0 ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder["appealTime"])) : ''; //一审开始时间
    				$modata["appealMemo"] 		= $recorder["appealMemo"]!=null ? $recorder["appealMemo"] : '' ; //备注
    				$modata["status"]= $this->getStatusText($recorder['appealStatus']);
    				$modata["isVip"] 		= $recorder["vipLvl"] =='1' ? '是' : '否'; //是否vip
    				$aContent[$key] = $modata;
    			}
    			$totalPage = ceil($rsr['pager']['total']/$querySize);
    			$total 	   = $rsr['pager']['total'];
    		}
    		if($totalPage<=0){
    			$totalPage = 1;
    		}
    		$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$page,$intArray);
    		$aContent = array();
    	} while ($page++ !=$totalPage);
    	$endTime = microtime(true);
    	$diffTime = floor($endTime - $startTime);
    	$modata = array();
    	array_push($aContent,$modata);
    	$modata['sn'] = '下载数据:'.$total.'条.';
    	$modata['BankName']   = '总耗时:'.$diffTime.'秒';
    	array_push($aContent,$modata);
    	$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$page);
    }
    
 	private function getStatusText($status){
    	if($status=='1'){
    		return '未处理';
    	}else if($status=='2'){
    		return '审核通过';
    	}else if($status=='3'){
    		return '审核未通过';
    	}else if($status=='4'){
    		return '待定';
    	}
    }
    
}
