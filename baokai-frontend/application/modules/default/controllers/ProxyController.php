<?php
class Default_ProxyController extends Fundcommon {
    //protected $_pic;
    protected $_proxy;
    
    public function init() {
        parent::init ();
        $this->_proxy =  new Proxy();
//        $this->_valid->email = new MyValid_Email($this);
//        $this->_valid->pwd = new MyValid_Email($this);
//        $this->_valid->safeAns = new MyValid_SafeAnswer($this);
          $this->_valid->isInt = new MyValid_Int ( $this );
          $this->_valid->isWord = new MyValid_Word ( $this );
          $this->_valid->username = new MyValid_Username ( $this );
          //$this->_clientObject = new Client();
          //普通用户和奖金组全部为0的用户不能进入代理管理页面
          if($this->_sessionlogin->info['userLvl'] =='-1' || $this->_sessionlogin->info['isAllZero'] == '0'){
			$this->_redirect('/index');
          }
    }
    
    //代理首页
    public function indexAction() {
        $result = array('teamBal'=>0,'teamACount'=>0,'teamUCount'=>0,'teamCount'=>0,'todayRebates'=>0);
        
        $aUri['proxy'] = 'queryUserCount';
        $data['param'] = $this->_sessionlogin->info['id'];
        $res = $this->_clientobject->inRequestV2($data, $aUri);
        if(isset($res['head']['status']) && $res['head']['status'] == '0'){
        	if(isset($res['body']['result'])){
        		$result['teamBal'] = getMoneyFomat($res['body']['result']['SUMBAL']/$this->_moneyUnit,2);
        		$result['teamAvail'] = getMoneyFomat($res['body']['result']['SUMAVAIL']/$this->_moneyUnit,2);
        		$result['teamACount'] = $res['body']['result']['AGENTS'];
        		$result['teamUCount'] = $res['body']['result']['USERS'];
        		$result['teamCount']  = $result['teamACount']+$result['teamUCount'];
        	}
        }
        //当前web服务器时间
        $this->view->timeNow = date('Y-m-d H:i', time());
		//可查询提前几天的数据
		$this->view->dateDays = 7;
        $this->view->result = $result;
        $this->view->title ="代理首页";
        $this->view->display ( 'default/ucenter/proxy/proxy_view.tpl' );
    }
	
	//显示图表页面
	public function chartAction(){
		$this->view->display ( 'default/ucenter/proxy/proxy_chart.tpl' );
	}
	
	//页面数据
    public function loadchartdataAction(){
		$type 	   = intval(getSecurityInput(trim($this->_request->getParam('type','0')))); //查询类型
		$timeStart = getSecurityInput(trim($this->_request->getParam('timestart',date('Y-m-d')))); //开始时间
		$timeEnd   = getSecurityInput(trim($this->_request->getParam('timeend',date('Y-m-d')))); //结束时间
		$model 	   = getSecurityInput(trim($this->_request->getParam('model','h'))); //查询模式,h:小时 d:天
		/* if($type!=''){
			$data['type']  = $type;
		} */
		if($timeStart != ''){
			$data['param']['startTime'] = getQueryStartTime($timeStart);
		}
		if($timeEnd != ''){
			$data['param']['endTime'] = getQueryEndTime($timeEnd);
		}
		if($model !=''){
			$data['param']['type']   = $model == 'h' ? 1 : 2;
		}
		$data['param']['userId']  = $this->_sessionlogin->info['id'];
		$aUri['proxy'] = 'queryUserAgentCount';
		$res = $this->_clientobject->inRequestV2($data, $aUri);
		switch ($type){
			case 0:
				$queryType = 'withDraw';
				break;
			case 1:
				$queryType = 'charge';
				break;
			case 2:
				$queryType = 'bet';
				break;
			case 3:
				$queryType = 'reward';
				break;
			case 4:
				$queryType = 'newUser';
				break;
			default:
				$queryType = 'withDraw';
				break;
		}
		$data = array();
		if($model =='d'){
			$day = (strtotime($timeEnd) - strtotime($timeStart))/86400;
			for($i=0;$i<=$day;$i++){
				$data[$i] = array($i, 0);
			}
		} else {
			for($i=0;$i<24;$i++){
				$data[$i] = array($i, 0);
			}
		}
		//统计总量
		$total = array(
				'withdraw' => 0,
				'load' => 0,
				'buy' => 0,
				'rebates' => 0,
				'newMem' => 0
		);
		$fullData = array();
		if(isset($res['body']['result']) && count($res['body']['result'])>0){
			foreach ($res['body']['result'] as $key=>$value){
				
				$value['withDraw'] = $value['withDraw']/$this->_moneyUnit;
				$value['charge'] = $value['charge']/$this->_moneyUnit;
				$value['bet'] = $value['bet']/$this->_moneyUnit;
				$value['reward'] = $value['reward']/$this->_moneyUnit;
				//列表数据
				if($value['withDraw'] !=0 || $value['charge'] !=0 || $value['bet'] !=0 || $value['reward'] !=0 || $value['newUser']!=0){
					$sFullData['day'] 		= getSrtTimeByLong($value['day']);
					$sFullData['date'] 		= $model == 'h' ? date('Y-m-d H:i',$sFullData['day']+intval($value['time'])*3600) : date('Y-m-d',$sFullData['day']);
					$sFullData['withdraw'] 	= number_format(floatval($value['withDraw']),2);
					$sFullData['load'] 		= number_format(floatval($value['charge']),2);
					$sFullData['buy'] 		= number_format(floatval($value['bet']),2);
					$sFullData['rebates']  	= number_format(floatval($value['reward']),2);
					$sFullData['newMem'] 	= intval($value['newUser']);
					$fullData[] = $sFullData;
				}
				//统计数据
				$total['withdraw'] += $value['withDraw'];
				$total['load'] 	   += $value['charge'];
				$total['buy'] 	   += $value['bet'];
				$total['rebates']  += $value['reward'];
				$total['newMem']   += $value['newUser'];
				
				//格式化表格数据
				if($type !=4){
					$inValue = number_format($value[$queryType],2,'.','');
				} else {
					$inValue = $value[$queryType];
				}
				if($model =='d'){
					$day = (getSrtTimeByLong($value['day']) - strtotime($timeStart))/86400;
					if($day >=0){
						$data[$day] = array($day, $inValue);
					}
				} else {
					$data[$value['time']] = array($value['time'], $inValue);
				}
			}
		}
		//格式化统计数据
		foreach ($total as $key=>$value){
			if($key!='newMem'){
				$total[$key] = getMoneyFomat(floatval($value),2);
			}
		}
		$res = array(
			//图表数据
			'chartData' => array(
				'label' => ''.$type,
				'data' => $data
			),
			//完整记录
			'fullData' => $fullData,
			//统计总数
			'total' => $total
        );
        
        echo Zend_Json_Encoder::encode($res);

    }
    
    //客户中心
    public function cusmagAction(){
        //总下级数量
        $totMbm = 0;
        //当前页吗
        $page 	  = intval(getSecurityInput($this->_request->getPost("page",1)));
        //页数大小
        $pageSize = intval(getSecurityInput($this->_request->getPost("pageSize",10)));
        //自己or父级Id
        $uId = $this->_sessionlogin->info["id"];
        //请求用户Id
        $rqstId = intval($this->_request->getPost("rqstId"));
        //判断是否搜索
        $act = intval($this->_request->getPost("act"));
        
        //用户名
        $username = strtolower(getSecurityInput(trim($this->_request->getPost("username"))));
        //金额范围开始
        $moneyFrom = getSecurityInput($this->_request->getPost("moneyFrom"));
        //金额方位到
        $moneyTo = getSecurityInput($this->_request->getPost("moneyTo"));
        //金额排序 0 降序 1升序
        $moneySort = getSecurityInput($this->_request->getPost("moneySort"));
        //最后一次登录时间
        $lastLogin = getSecurityInput($this->_request->getPost("lastLogin"));
        //最后登录排序 0 降序 1升序
        $lastLoginSort = intval(getSecurityInput($this->_request->getPost("lastLoginSort")));
        $sortType = intval(getSecurityInput($this->_request->getPost("sortType",'0')));
        $list = $lvlPath = array();
        $type = $timeStart = $timeEnd = $isSearch = 0;
        if(!empty($username)||!empty($moneyFrom)||!empty($moneyTo)||!empty($lastLogin)){
            $isSearch = 1;
        }
        
        if(!empty($username)){
            //$this->_valid->username->isValid($username,"showNoLeft", true);
        }
        if(!empty($moneyFrom)){
            $this->_valid->isInt->isValid($moneyFrom,"showNoLeft", true);
        }
        if(!empty($moneyTo)){
            $this->_valid->isInt->isValid($moneyTo,"showNoLeft", true);
        }
        if(!empty($lastLogin)){
            $this->_valid->isInt->isValid($lastLogin,"showNoLeft", true);
        }
        if(!empty($moneySort)){
            $this->_valid->isInt->isValid($moneySort,"showNoLeft", true);
        }
        if(!empty($lastLoginSort)){
            $this->_valid->isInt->isValid($lastLoginSort,"showNoLeft", true);
        }
        if($sortType ==0){
        	$SortAesc=$moneySort;
        }else{
        	$SortAesc=$lastLoginSort;
        }
        if($isSearch){
            //最后一次登陆时间
            $timeEnd = timetoMicro(time());
            $timeStart = 0;
            switch($lastLogin){
                case 1:
                    $timeStart = getQueryStartTime('-6 days');
                	break;
                case 2:
                    $timeStart = getQueryStartTime('-1 months');
                	break;
                case 3:
                   $timeStart = getQueryStartTime('-3 months');
                	break;
                case 4:
                    $timeStart = getQueryStartTime('-6 months');
                	break;
                case 5:
                    $timeStart = getQueryStartTime('-1 years');
                	break;
                default:
                	$timeStart = $timeEnd = 0;
                	break;
            }
            $uid = $this->_sessionlogin->info["id"];
            $resTmp = $this->_proxy->getSubListByCondis($uid, $username, $moneyFrom*$this->_moneyUnit, $moneyTo*$this->_moneyUnit, $timeStart, $timeEnd, $page, $pageSize, $sortType, $SortAesc);
            
        }else{
           
         	//树形列表获取
            $tmpPath = $this->_request->getPost("lvlPath");
            if(!empty($tmpPath)){
                $lvlPath = Zend_Json::decode($tmpPath);
            }unset($tmpPath);
            
            //树形列表追加/减少
            $uName = $this->_request->getPost("uName");
            if(!empty($uName)){
                $isExist = false;
                while(list($k, $v)= each($lvlPath)){
                    if($isExist){
                        unset($lvlPath[$k]);
                    }else{
                        if($v["uId"] ==$rqstId){
                            $isExist = true;
                        }
                    }
                     
                }reset($lvlPath);
                if(!$isExist){
                    $lvlPath[] = array("uId"=>$rqstId,"name"=>$uName);
                }
            }
            
            //显示内容 0一级代理 1玩家
            $type = intval($this->_request->getPost("type"));
            
            if(empty($lvlPath)){
                $rqstId = $uId;
            }else{
                $rqstId = $lvlPath[count($lvlPath)-1]["uId"];
            }
            
            $result = $this->_proxy->getSubList($rqstId,$type, $sortType, $SortAesc,$page,$pageSize);
            $resTmp['result'] = $result['result']['subUsers'];
            $resTmp["pager"]["total"] = $result["pager"]["total"];
            $proxyCount = $result['result']['teamACount'];
            $userCount = $result['result']['teamUCount'];
            $this->view->proxyCount = $proxyCount;
            $this->view->userCount = $userCount;
        }
        if(!empty($resTmp)){
            $resList = $resTmp["result"];
            $totMbm = $resTmp["pager"]["total"];
            if(count($resList)>0){
	            foreach($resList as $item){
	                $date = "";
	                $date = getSrtTimeByLong($item["lastLoginDate"]);
	                $date = empty($date)?"-":date("Y-m-d H:i:s",$date);
	                $list[] = array(
	                    "uId" => $item["id"],
	                    "username" => $item["account"],
	                    "teamCount" => $item["teamUCount"]+$item["teamACount"],
	                    "userLvl" => $item["userLvl"]-$this->_sessionlogin->info['userLvl'],
	                    "userType" => $item["parentId"]==$this->_sessionlogin->info['id'] ? 1 : 0,
	                    "cusType" => $this->getUserType($item["userLvl"]-$this->_sessionlogin->info['userLvl']),
	                    "balance" => !empty($item["availBal"]) ? getMoneyFomat($item["availBal"]/$this->_moneyUnit,2) : 0,
	                    "lastLogin" =>$date,
	                );
	            }
            }
        }
        $pages = CommonPages::getPages ( $totMbm, $page, $pageSize );
        
        $this->view->ajxLvlPath = Zend_Json::encode($lvlPath);
        $this->view->lvlPath = $lvlPath;
        $this->view->moneySort = $moneySort;
        $this->view->lastLoginSort = $lastLoginSort;
        $this->view->username = $username;
        $this->view->moneyFrom = $moneyFrom;
        $this->view->moneyTo = $moneyTo;
        $this->view->lastLogin = $lastLogin;
        $this->view->type = $type;
        $this->view->list = $list;
        $this->view->pages = $pages;
        $this->view->pageSize = $pageSize;
        $this->view->isSearch = $isSearch;
        $this->view->proxyTitle = $this->getUserType(count($lvlPath)+1);
        $this->view->title = '客户管理';
        $this->view->display ( 'default/ucenter/proxy/cusMag_list.tpl' );
    }
    
    //查询客户下级请求
    public function querysubuserAction(){
    	//
		
		$account = $this->_request->getPost ( "account" );
		
    	$data = array (
    			"body" => array (
     			"param" => array (
    					"userId" =>intval($this->_sessionlogin->info["id"]),
						"account" =>$account,
    			  ), 
    			"pager" => array (
    					'startNo' => 1,
    					"endNo" => 10000
    			)
    	)
    	);
    	$resUser = $this->_proxy->querySubUser( $data  ) ; 
    	$accounts = array();
    	header ( 'Content-Type: application/json;charset=utf-8' );
    	if($resUser['body']['result']){
    		foreach ( $resUser['body']['result'] as $key=>$value){
    			array_push($accounts,array("id"=>$value['id'],"account"=>$value['account']));
    		}
    		echo json_encode($accounts);
    	}else{
    		echo json_encode(array("status"=>0));
    	}
    	
    	
    	
    }
    
    
    //查询客户下级客户请求
    public function querysubcustomerlistAction(){
    
    	/**
    	 * ******************查询客户下级客户请求*************************
    	 */
    	$data = array (
    			"body" => array (
    					"param" => array (
    							"userId" =>1,
    							"fromBal" => 1 ,
    							"toBal" => 12,
    							"lastLoginDate" => 1231231232
    					),
    					"pager" => array (
    							'startNo' => 1,
    							"endNo" => 3
    					)
    			)
    	);
    	
    	echo $this->_proxy->querySubCustomerList( $data );
    	/**
    	 * *******************************************
    	*/
    	
    	
    	
    }
    
    //下级投注查询
    public function betrecordAction(){
        
        //自己or父级Id
        $topUserId = $this->_sessionlogin->uId;
        
        //请求用户Id
        $uId = intval($this->_request->getParam("uId"));
        
        //用户名
        $username = $this->_request->getParam("username");
        
        //开始日期
        $dateBegin = $this->_request->getParam("dateBegin");
        
        //结束日期
        $dateEnd = $this->_request->getParam("dateEnd");
        
        //彩种Id
        $lotteryId = intval($this->_request->getParam("lottery"));
        
        //中奖状态
        $winState = $this->_request->getParam("winState");   //0:全部状态1:中奖2:未中奖3:等待开奖4:已撤销
        
        //追号状态
        $isChas = $this->_request->getParam("isChas");   //0:全部1:是2:否
        
        //是否包含下级
        $includeSub = ("on" == $this->_request->getParam("includeSub"))?"on":"";    //0:否1:是
        
        //方案编号
        $number = $this->_request->getParam("number");
        
        //当前页吗
        $page = (0==intval($this->_request->getParam("page")))?1:intval($this->_request->getParam("page"));
        
        $lotteryList = array(
                        array("Id"=>"1","title"=>"重庆时时彩"),
                        array("Id"=>"2","title"=>"天津时时彩"),
                        array("Id"=>"3","title"=>"重庆11选5")
        );
        
        $items = array (
                0 => array (
                        'pId' => 0,
                        'username' => 'vava(45)',
                        'lottery' => '重庆时时彩',
                        'amount' => '1000',
                        'state' => '4',
                        'chas' => '0',
                        'betTime' => '2012-02-28 22:22:00' 
                ),
                1 => array (
                        'pId' => 1,
                        'username' => 'vava',
                        'lottery' => '重庆时时彩',
                        'amount' => '1000',
                        'state' => '2',
                        'chas' => '0',
                        'betTime' => '2012-02-28 22:22:00' 
                ),
                2 => array (
                        'pId' => 2,
                        'username' => 'vava',
                        'lottery' => '重庆时时彩',
                        'amount' => '4000',
                        'state' => '3',
                        'chas' => '1',
                        'betTime' => '2012-02-28 22:22:00' 
                ),
        );
        
        //总下级数量
        $totMbm = 100;
        
        $pages = CommonPages::getPages ( $totMbm, $page );
        
//        $this->view->;
        $this->view->dateBegin = $dateBegin;
        $this->view->dateEnd = $dateEnd;
        $this->view->pages = $pages;
        $this->view->username = $username;
        $this->view->includeSub = $includeSub;
        $this->view->winState = $winState;
        $this->view->isChas = $isChas;
        $this->view->number = $number;
        $this->view->lottery = $lotteryId;
        $this->view->lotteryList = $lotteryList;
        $this->view->items = $items;
        $this->view->display ( 'default/ucenter/proxy/betRecord_list.tpl' );
    }
    
    
    public function prqueryAction() {
    	
    	$uid = $this->_request->getParam ( "uid" );
    	
    	if($uid !="" && $uid!=null){
    		$items = array (
    				0 => array (
    						'username' => 'vava',
    						'num'=>45,
    						'pgroup' => '代理',
    						'allfees' => '100,700.00',
    						'reback' => '10.00',
    						'reallfees' => '999,999.00',
    						'allbounes' => '999,999.00',
    						'alldeal' => '999,999.00'
    				),
    				1 => array (
    						'username' => 'vava02',
    						'num'=>300,
    						'pgroup' => '代理',
    						'allfees' => '100,700.00',
    						'reback' => '99.00',
    						'reallfees' => '888,563.00',
    						'allbounes' => '563,785.00',
    						'alldeal' => '999,999.00'
    				),
    				2 => array (
    						'username' => 'vava',
    						'num'=>300,
    						'pgroup' => '代理',
    						'allfees' => '100,700.00',
    						'reback' => '99.00',
    						'reallfees' => '888,563.00',
    						'allbounes' => '563,785.00',
    						'alldeal' => '999,999.00'
    				)
    		);
    	}else{
    		
    		$items = array (
    				0 => array (
    						'username' => 'vava',
    						'num'=>45,
    						'pgroup' => '代理',
    						'allfees' => '100,700.00',
    						'reback' => '10.00',
    						'reallfees' => '999,999.00',
    						'allbounes' => '999,999.00',
    						'alldeal' => '999,999.00'
    				),
    				1 => array (
    						'username' => 'vava02',
    						'num'=>300,
    						'pgroup' => '代理',
    						'allfees' => '100,700.00',
    						'reback' => '99.00',
    						'reallfees' => '888,563.00',
    						'allbounes' => '563,785.00',
    						'alldeal' => '999,999.00'
    				),
    				2 => array (
    						'username' => 'vava',
    						'num'=>300,
    						'pgroup' => '代理',
    						'allfees' => '100,700.00',
    						'reback' => '99.00',
    						'reallfees' => '888,563.00',
    						'allbounes' => '563,785.00',
    						'alldeal' => '999,999.00'
    				)
    		);
    		
    		
    	}

        $this->view->items = $items;
        $pages = CommonPages::getPages ( 100, 4, 10 );
        $this->view->assign('pages',$pages);
        $this->view->display ( 'default/ucenter/proxy/report_list.tpl' );
    }
    
    
    public function prsearchAction(){
    	
    	$uName = $this->_request->getPost("userName",null);
    	$lotteryType = $this->_request->getPost("lotteryType",0);
    	$beginDate = $this->_request->getPost("beginDate",null);
    	$endDate = $this->_request->getPost("endDate",null);

    	if ( $lotteryType==0){
    		
    		$items = array (
    				0 => array (
    						'username' => 'vava',
    						'num'=>45,
    						'pgroup' => '代理',
    						'allfees' => '100,700.00',
    						'reback' => '10.00',
    						'reallfees' => '999,999.00',
    						'allbounes' => '999,999.00',
    						'alldeal' => '999,999.00'
    				),
    				1 => array (
    						'username' => 'vava02',
    						'num'=>300,
    						'pgroup' => '代理',
    						'allfees' => '100,700.00',
    						'reback' => '99.00',
    						'reallfees' => '888,563.00',
    						'allbounes' => '563,785.00',
    						'alldeal' => '999,999.00'
    				),
    				2 => array (
    						'username' => 'vava',
    						'num'=>300,
    						'pgroup' => '代理',
    						'allfees' => '100,700.00',
    						'reback' => '99.00',
    						'reallfees' => '888,563.00',
    						'allbounes' => '563,785.00',
    						'alldeal' => '999,999.00'
    				)
    		);
    		$this->view->items = $items;
    		$this->view->lotteryType = $lotteryType;
    		$array = CommonPages::getPages ( 100, 3 );
    		$this->view->assign ( 'pages', $array );
    			}else {
    				$this->view->lotteryType = $lotteryType;
    				//echo $lotteryType;
    				$this->view->items = array(); 
    				$array = CommonPages::getPages ( 100, 3 );
    				$this->view->assign ( 'pages', $array );
    			}
    		$this->view->display ( 'default/ucenter/proxy/report_list.tpl' );
    
    }
    
}