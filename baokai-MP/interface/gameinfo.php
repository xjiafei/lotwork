<?php
/**
 * 文件 : /_app/controller/gameinfo.php
 * 功能 : 用户游戏信息的查看
 *
 * 功能:
 *  - actionGameList    参与游戏信息
 *  - actionGamedetail  查看游戏详情
 *  - actionCancelGame  用户撤单
 *  - actionTask        用户追号记录
 *  - actionTaskDetail  用户追号详情
 *  - actionCancelTask  取消追号  
 *
 * @author    floyd
 * @version   1.0.0
 * @package   highgame
 */

class controller_gameinfo extends basecontroller
{
    
        /**
        * 用于获取每日运营开始时间 
        */
        public $runStartTime = '';
        public function __construct() {
            parent::__construct();
            $oConfig     = new model_config();
            $aConfigValue = $oConfig->getConfigs( array('run_start_time') );
            if (is_array($aConfigValue) && key_exists('run_start_time', $aConfigValue)) {
                $trueTimeHour = '';
                $checkTime = explode(':', $aConfigValue['run_start_time']);
                $trueTimeHour = (strlen($checkTime[0]) == 1) ?  '0' . $checkTime[0] : $checkTime[0];
                $this->runStartTime = $trueTimeHour . ':' .  $checkTime[1] . ':00';
            } else {
                $this->runStartTime = "05:00:00";
            }

        }

    function getPLayInfos() {
       
		
        $_REQUEST['curmid'] = 50;
        $_REQUEST['pid'] = 50;
        
        $iCurrentMenuId = intval($_REQUEST['curmid']);
        $iUserId = intval($_SESSION['userid']);

        $nowTime =  date("Y-m-d H:i:s");
        $date = date("Y-m-d H:i:s", strtotime($nowTime) - 5*24*60*60);
        
        
        $iCurrentMenuId = intval($_REQUEST['curmid']);
        //01:获取当前菜单所对应的菜单信息[所对应的彩种ID]
        $oUserMenu  = new model_usermenu();
        $aCurrentMenu = $oUserMenu->userMenu($iCurrentMenuId," lotteryid ");
        if( empty($aCurrentMenu) )
        {
            //sysMsg( "数据错误", 2 );
            $errData = array('error' => 1, 'msg' => "操作错误");
            echo json_encode($errData);
            exit;
        }
        $iLotteryId = $aCurrentMenu['lotteryid'];
        
        //所有彩种的信息
        $iLotteryId = 0;
        
        $_GET['starttime'] = $date;
        //$_GET['starttime'] = "2013-03-05 05:00:00";
        $_GET['endtime'] =  $nowTime;
        $_GET['isgetdata'] = "1";
        $_GET['issue'] = "0";
        $_GET['lotteryid'] = $iLotteryId;
        $_GET['projectno'] = "";
        $_GET['select'] = "所有玩法";
        $_GET['username'] = "";
        
        
		$oMethod   = A::singleton( "model_method", $GLOBALS['aSysDbServer']['report'] );
		$oLottery = A::singleton( "model_lottery", $GLOBALS['aSysDbServer']['report'] );
		$oUser = A::singleton( 'model_user', $GLOBALS['aSysDbServer']['report'] );
		//参数整理
		$sWhere = " ";
		//开始时间
        if (isset($_GET['flag']) && $_GET['flag'] == "fromdg"){
            $_GET["starttime"] = date("Y-m-d H:i:s", time() - 3 * 60);
            $_GET['endtime'] = date("Y-m-d H:i:s", time());
            $_GET['username'] = $_SESSION['username'];
        }
		if( isset($_GET["starttime"])&&!empty($_GET["starttime"]) )
		{
			$sStartTime = getFilterDate($_GET["starttime"]);
		}
		else
		{
			$sStartTime = time() < strtotime(date("Y-m-d {$this->runStartTime}")) ? date("Y-m-d {$this->runStartTime}", strtotime("-1 days")) : date("Y-m-d {$this->runStartTime}");  //默认为当天
		}
		if(!empty($sStartTime) )
		{
			$sWhere .= " AND P.`writetime`>'".$sStartTime."'";
			$sHtml["starttime"] = $sStartTime;
		}
		//结束时间
		if( isset($_GET["endtime"])&&!empty($_GET["endtime"]) )
		{
			$sEndtime = getFilterDate($_GET["endtime"]);
		}
		else
		{
			$sEndtime = time() < strtotime(date("Y-m-d {$this->runStartTime}")) ? date( "Y-m-d {$this->runStartTime}") : date( "Y-m-d {$this->runStartTime}", strtotime("+1 days") );
		}
		if( !empty($sEndtime) )
		{
			$sHtml["endtime"] = $sEndtime;
			$sWhere .= " AND P.`writetime`<='".$sEndtime."'";
		}
        
		/* @var $oIssue model_issueinfo */
		$oIssue = A::singleton( "model_issueinfo", $GLOBALS['aSysDbServer']['report'] );
		//获取奖期
		$aIssue = array();
		//身份转化
		if( intval($_SESSION["usertype"])==2 )
		{//总代管理员
			$bIsAdmin = TRUE;
			$iUserId = $oUser->getTopProxyId( intval($_SESSION["userid"]), FALSE ); //获取总代
			if( $oUser->IsAdminSale( intval($_SESSION["userid"]) ) )
			{ //为销售
				$sUserWhere = " AND UT.`lvproxyid` IN ("
				."SELECT `topproxyid` FROM `useradminproxy` WHERE `adminid`='"
				.intval($_SESSION["userid"])."')";
			}
			else
			{
				$sUserWhere = " AND UT.`lvtopid`='".$iUserId."'";
			}
		}
		else
		{
			$bIsAdmin   = $oUser->isTopProxy( $iUserId );
			$sUserWhere = " AND (FIND_IN_SET('".$iUserId."',UT.`parenttree`) OR (UT.`userid`='".$iUserId."'))";
		}
        
		//对include的默认
		if( $bIsAdmin )
		{
			$bInclude = TRUE;
			$sHtml["include"] = 1;
		}
		else
		{
			if($oUser->isTopProxy($iUserId))
			{
				$bInclude = TRUE;
				$sHtml["include"] = 1;
			}
			else
			{
				$bInclude = FALSE;
				$sHtml["include"] = 0;
			}
		}
		$aLottery  = array(); //彩种组
		foreach( $oLottery->getLotteryByUser( $iUserId, $bIsAdmin, 'l.cnname, l.lotteryid' ) AS $l )
		{
			$aLottery[$l['lotteryid']] = $l['cnname'];
		}
		$aMethods  = array(); //玩法组
		if($bIsAdmin)
		{
			$aMethodByCrowd = $oMethod->methodGetAllListByCrowd('','');
		}
        
		else
		{
			$oUserMethod = new model_usermethodset( $GLOBALS['aSysDbServer']['report'] );
			$sFields     = " m.`methodid`, m.`lotteryid`, m.`methodname` ";
			$aMethodGroup= $oUserMethod->getUserMethodPrize( $iUserId, $sFields, '', "", "", FALSE, true );
		}
        
		$aTempArr = array();
		if(!empty($aMethodGroup))
		{
		    foreach( $aMethodGroup as $method )
		    {
		        $aTempArr[] = $method['methodid'];
		    }
		    $sFields = '`lotteryid`,`methodid`,`methodname`';
		    $sCondition = " M.`isclose`=0 AND (M.`pid` IN(".implode( ",", $aTempArr ).") OR M.`methodid` IN(".implode( ",", $aTempArr ).") )";
		    $aMethodByCrowd = $oMethod->methodGetAllListByCrowd('',$sCondition);
		}
		foreach ($aMethodByCrowd as $iLotteryId => $aCrowd)
		{
		    $aMethods[$iLotteryId] = $aCrowd['crowd'];
		}
		$GLOBALS['oView']->assign("lottery",        $aLottery);
		$GLOBALS['oView']->assign("data_method",    json_encode($aMethods)); //方便JS 调用玩法
        $issueList = $oIssue->getItems(0, date("Y-m-d"), 0, 0, 0, time(), 'saleend DESC');
        foreach ($issueList as $v)
        {
            $aIssue[$v['lotteryid']][] = array('issue' => $v['issue'], 'lotteryid' => $v['issue'], 'dateend' => $v['belongdate']);
        }

		$GLOBALS["oView"]->assign( "data_issue", json_encode($aIssue) );
		$iLotteryId = isset( $_GET["lotteryid"] ) && is_numeric( $_GET["lotteryid"] ) ? intval( $_GET["lotteryid"] ) : 0;
		$sHtml["lotteryid"] = $iLotteryId;
		//玩法
		$iCrowdId = isset($_GET["crowdid"])&&is_numeric($_GET["crowdid"]) ? intval($_GET["crowdid"]): 0;
		$sHtml["crowdid"] = $iCrowdId;
		$iPid = isset($_GET["pid"])&&is_numeric($_GET["pid"]) ? intval($_GET["pid"]): 0;
		$sHtml["pid"] = $iPid;
		$iMethodId = isset( $_GET["methodid"] )&&is_numeric( $_GET["methodid"] ) ? intval( $_GET["methodid"] ) :0;
		$sHtml["methodid"] = $iMethodId;
		if($sHtml["lotteryid"] >0 )
		{
			$sWhere .=" AND P.`lotteryid`='".$iLotteryId."' ";
			//按玩法群查询
            $aMInfo = array();
			if( $iCrowdId > 0 )
			{
//			    $mWhere .=" AND M.`crowdid`='".$iCrowdId."'";
                $aMInfo['crowdid'] = $iCrowdId;
			}
			//按玩法组查询
			if( $iPid > 0 )
			{
//			    $mWhere .=" AND M.`pid`='".$iPid."'";
                $aMInfo['pid'] = $iPid;
			}
			//按玩法查询
			if( $iMethodId > 0 )
			{
//			    $mWhere .=" AND M.`methodid`='".$iMethodId."'";
                $aMInfo['methodid'] = $iMethodId;
			}
			$sIssue         = isset( $_GET["issue"] )&&!empty( $_GET["issue"] )? daddslashes( $_GET["issue"] ):"0";
			$sHtml["issue"] = $sIssue;
			if( $sIssue!="0" )
			{
				$sWhere .= " AND P.`issue`='".$sIssue."'";
			}
		}
		else
		{
			$sHtml["methodid"]  = 0;
			$sHtml["issue"]     = 0;
		}
        
		//用户名以及是否包含(支持*号,不支持包含)
        $sFuzzyName = "";
		if( isset($_GET["username"])&&!empty($_GET["username"]) )
		{ //指定了用户名
			$sUserName = daddslashes( $_GET["username"] );
			if( strstr($sUserName,'*') )
			{ // 支持模糊搜索
//				$sWhere .= " AND UT.`username` LIKE '".str_replace( "*", "%", $sUserName )."'";
                $sFuzzyName = str_replace( "*", "%", $sUserName );
				$sHtml["include"] = 0; //支持*,不支持包含下级
				$iUserId = 0;
				$bInclude = FALSE;
				$sHtml["username"] = stripslashes_deep( $sUserName );
			}
			else
			{ //不支持模糊搜索
				$iUser = $oUser->getUseridByUsername( $sUserName ); //获取ID
				if( $iUser>0 )
				{ //需要检测当前搜索到的用户 和 当前用户的关系
					$iUserId = $iUser;
					$sHtml["username"] = stripslashes_deep( $sUserName );
					if( isset($_GET["include"]) && intval($_GET["include"])==1 )
					{
						$sHtml["include"] = 1;
						$bInclude = TRUE;
					}
					else
					{
						$sHtml["include"] = 0;
						$bInclude = FALSE;
					}
				}
				else
				{ //用户不存在
					$sWhere = " AND 1=0";
				}
			}
		}
		else
		{
			if( isset($_GET["include"])&&is_numeric( $_GET["include"] ) )
			{
				$bInclude   = TRUE;
				$iUserId    = 0;
				$sHtml["include"] = 1;
			}
		}
		if(isset($_GET['modes']) && array_key_exists( $_GET['modes'], $GLOBALS['config']['modes']) )
		{
			$sWhere .= ' AND P.`modes`='.intval($_GET['modes']);
			$sHtml["modes"] = $_GET['modes'];
		}
		//下面是Code
		if( isset($_GET["projectno"])&&!empty($_GET["projectno"]) )
		{
			$iProjectNo = model_projects::HighEnCode( daddslashes($_GET["projectno"]), "DECODE" );
			if( intval( $iProjectNo )>0 )
			{
				$sHtml["projectno"] = daddslashes( $_GET["projectno"] );
				$bInclude = TRUE;
				$iUserId = 0;
				$sWhere .= " AND P.`projectid`='".$iProjectNo."'";
			}
		}
        
//		$sWhere .= $sUserWhere;
		/* @var $oProject model_projects */
		$oProject = new model_projects( $GLOBALS['aSysDbServer']['report'] );
		$iPage = isset($_GET["p"])&&is_numeric($_GET["p"]) ?intval($_GET["p"]):1;
		$iIsGetData  = isset($_GET['isgetdata']) ? intval($_GET['isgetdata']) : 0;//是否查询数据
		$sHtml['isgetdata'] = $iIsGetData;
        $aFields = array();
        $aFields['projects']['all'] = "P.*";
        $aFields['usertree']['username'] = "UT.`username`";
        $aFields['method']['methodname'] = "M.`methodname`";
        $aFields['lottery']['cnname'] = "L.`cnname`";
        $aFields['lottery']['lotterytype'] = "L.`lotterytype`";
        $aFields['issueinfo']['code'] = "I.`code` AS `nocode`";
        $aFields['issueinfo']['statuscode'] = "I.`statuscode`";
		$aProjects = $iIsGetData == 0 ? array('affects' => 0, 'results' => array()) : $oProject->projectGetIosResult( $iUserId, $bInclude, $aFields, $sWhere,"P.`writetime` DESC", 500, $iPage, $sFuzzyName, $aMInfo, "", $sUserWhere, TRUE );
		$total["in"]  = 0.00;
		$total["out"] = 0.00;
        
        if (!isset($aProjects['results']) || empty($aProjects['results'])) {
            echo json_encode(array());
            exit();
        }

        foreach ($aProjects['results'] as $key => $value) {
            $enId = model_projects::HighEnCode("D".$value["issue"]."-".$value["projectid"],"ENCODE");
            $prizeStatus = $value['prizestatus'];
            $istatus = 0; //未开奖
            if ($value['isgetprize'] == 1) {
                if ($prizeStatus == 0) {
                     $istatus = 2;    //未派奖
                } elseif ($prizeStatus == 1) {
                    $istatus = 3;     //已经派奖
                }
            } elseif ($value['isgetprize'] == 2) {
                $istatus = 1;         //未中将
            }
            
            /*
            $sLottery = '';
            switch ($value['lotteryid']) {
                case 1:$sLottery = "重庆时时彩"; break;
                case 2:$sLottery = "黑龙江时时彩"; break;
                case 3:$sLottery = "江西时时彩"; break;
                case 4:$sLottery = "上海时时乐"; break;
                case 5:$sLottery = "山东11选5"; break;
                case 6:$sLottery = "新疆时时彩"; break;
                case 7:$sLottery = "江西多乐彩 11选5"; break;
                case 8:$sLottery = "广东十一运"; break;
                case 9:$sLottery = "北京快乐8"; break;
                case 10:$sLottery = "重庆11选5"; break;
                case 11:$sLottery = "宝开时时彩"; break;
                case 12:$sLottery = "天津时时彩"; break;
            }
            */
            //'lotteryname' => $sLottery,
            
            $reMethodid = $value['methodid'];
            if ($reMethodid == 31 || $reMethodid == 32 || $reMethodid == 33 || $reMethodid == 34) {
                $reMethodid = 30;
            } else {
                $reMethodid = $value['methodid'];
            }
            $returnDatas[] = array('methodid' => $reMethodid,   'code' => $value['code'], 'enid' => $enId,  'name' => $value['cnname'], 'ifwin' => $istatus, 'iscancel' => $value['iscancel'], 'methodname' => $value['methodname'], 'totalprice'=> $value['totalprice'], 'issue' => $value['issue'], 'time'=> $value['writetime'], 'bonus' => $value['bonus']);
        }
        
        //print_r($returnDatas);
        echo json_encode($returnDatas);
        
        exit();
        
		foreach($aProjects["results"] as $iProjectId=>&$aProject)
		{
			$aProject["projectid"] = model_projects::HighEnCode("D".$aProject["issue"]."-".$aProject["projectid"],"ENCODE");
			$total["in"]  = $total["in"] + $aProject["bonus"];
			$total["out"] = $total["out"]+ $aProject["totalprice"];
			$aProject['code'] = $oProject->AddslasCode($aProject['code'], $aProject['methodid']);
			//对号码进行整理
			if(strlen($aProject["code"])>20)
			{
				$str = "<a href=\"javascript:show_no('".$iProjectId."');\">详细号码</a>";
				$str .= "<div class=\"task_div\" id=\"code_".$iProjectId."\">号码详情";
				$str .= "[<a href=\"javascript:close_no('".$iProjectId."');\" class='fff600'>关闭</a>]<br/>";
				$str .="<textarea class=\"code\" readonly=\"readonly\">";
				$sTempCode      = "";
				$sProjectCode   = "";
				$aCodeDetail    = explode(",", $aProject["code"]);
				$iCodeLen = strlen($aCodeDetail[0]) + 1;//单个号码长度
				$iRowCodeLen = intval(40/$iCodeLen)*$iCodeLen;//一行的号码最大长度
				foreach ( $aCodeDetail as $sCode )
				{
					$sTempCode .= $sCode .",";
					$sProjectCode .= $sCode .",";
					if( strlen($sTempCode) >= $iRowCodeLen )
					{
						$sProjectCode = substr($sProjectCode, 0,-1);
						$sProjectCode .= "\r\n";
						$sTempCode = "";
					}
				}
				$sProjectCode = substr($sProjectCode, 0,-1);
				//                $code = str_replace( array("|"),array(","), $aProject["code"]);
				$str .= $sProjectCode."</textarea></div>";
				$str = str_replace( array("|"),array(","), $str);
				$aProject["code"] =$str;
			}
			else
			{
				$aProject["code"] =str_replace( array("|"),array(","), $aProject["code"]);
			}

			if( $aProject['codetype'] == 'input' && !strpos($aProject['methodname'], '混合') )
			{
				$aProject['methodname'] .= ' (单式)';
			}
			if($aProject['modes'] > 0)
			{
				$aProject['modes'] = $GLOBALS['config']['modes'][$aProject['modes']]['name'];
			}
			else
			{
				$aProject['modes'] = '';
			}

			if ( $aProject['statuscode'] != 2 )
			{
				$aProject['nocode'] = '';
			}
			else
			{
				if($aProject['lotterytype']==3)
				{
					$aProject['nocode'] = substr($aProject['nocode'], 0, 29).'<br />'.substr($aProject['nocode'], 30);
				}
			}
		}
        print_r($aProjects["results"] );
        
    }    

    
	/**
	 * 参与游戏信息
	 * URL: ./index.php?controller=gameinfo&action=gamelist
	 * @author SAUL
	 */
	function actionGameList()
	{ //查询下级以及自身的，不能超过自身
		/* @var $oMethod model_method */
		
		
        
        if ($_REQUEST['flag'] == 'playinfo') {
            $this->getPlayInfos();
            return true;
        }
		
		$iUserId = $_SESSION['userid'];
		$oMethod   = A::singleton( "model_method", $GLOBALS['aSysDbServer']['report'] );
		$oLottery = A::singleton( "model_lottery", $GLOBALS['aSysDbServer']['report'] );
		$oUser = A::singleton( 'model_user', $GLOBALS['aSysDbServer']['report'] );
		//参数整理
		$sWhere = " ";
		//开始时间
        if (isset($_GET['flag']) && $_GET['flag'] == "fromdg"){
            $_GET["starttime"] = date("Y-m-d H:i:s", time() - 3 * 60);
            $_GET['endtime'] = date("Y-m-d H:i:s", time());
            $_GET['username'] = $_SESSION['username'];
        }
		if( isset($_GET["starttime"])&&!empty($_GET["starttime"]) )
		{
			$sStartTime = getFilterDate($_GET["starttime"]);
		}
		else
		{
			$sStartTime = time() < strtotime(date("Y-m-d {$this->runStartTime}")) ? date("Y-m-d {$this->runStartTime}", strtotime("-1 days")) : date("Y-m-d {$this->runStartTime}");  //默认为当天
		}
		if(!empty($sStartTime) )
		{
			$sWhere .= " AND P.`writetime`>'".$sStartTime."'";
			$sHtml["starttime"] = $sStartTime;
		}
		//结束时间
		if( isset($_GET["endtime"])&&!empty($_GET["endtime"]) )
		{
			$sEndtime = getFilterDate($_GET["endtime"]);
		}
		else
		{
			$sEndtime = time() < strtotime(date("Y-m-d {$this->runStartTime}")) ? date( "Y-m-d {$this->runStartTime}") : date( "Y-m-d {$this->runStartTime}", strtotime("+1 days") );
		}
		if( !empty($sEndtime) )
		{
			$sHtml["endtime"] = $sEndtime;
			$sWhere .= " AND P.`writetime`<='".$sEndtime."'";
		}
		/* @var $oIssue model_issueinfo */
		$oIssue = A::singleton( "model_issueinfo", $GLOBALS['aSysDbServer']['report'] );
		//获取奖期
		$aIssue = array();
		//身份转化
		if( intval($_SESSION["usertype"])==2 )
		{//总代管理员
			$bIsAdmin = TRUE;
			$iUserId = $oUser->getTopProxyId( intval($_SESSION["userid"]), FALSE ); //获取总代
			if( $oUser->IsAdminSale( intval($_SESSION["userid"]) ) )
			{ //为销售
				$sUserWhere = " AND UT.`lvproxyid` IN ("
				."SELECT `topproxyid` FROM `useradminproxy` WHERE `adminid`='"
				.intval($_SESSION["userid"])."')";
			}
			else
			{
				$sUserWhere = " AND UT.`lvtopid`='".$iUserId."'";
			}
		}
		else
		{
			$bIsAdmin   = $oUser->isTopProxy( $iUserId );
			$sUserWhere = " AND (FIND_IN_SET('".$iUserId."',UT.`parenttree`) OR (UT.`userid`='".$iUserId."'))";
		}
		//对include的默认
		if( $bIsAdmin )
		{
			$bInclude = TRUE;
			$sHtml["include"] = 1;
		}
		else
		{
			if($oUser->isTopProxy($iUserId))
			{
				$bInclude = TRUE;
				$sHtml["include"] = 1;
			}
			else
			{
				$bInclude = FALSE;
				$sHtml["include"] = 0;
			}
		}
		$aLottery  = array(); //彩种组
		foreach( $oLottery->getLotteryByUser( $iUserId, $bIsAdmin, 'l.cnname, l.lotteryid' ) AS $l )
		{
			$aLottery[$l['lotteryid']] = $l['cnname'];
		}
		$aMethods  = array(); //玩法组
		if($bIsAdmin)
		{
			$aMethodByCrowd = $oMethod->methodGetAllListByCrowd('','');
		}
		else
		{
			$oUserMethod = new model_usermethodset( $GLOBALS['aSysDbServer']['report'] );
			$sFields     = " m.`methodid`, m.`lotteryid`, m.`methodname` ";
			$aMethodGroup= $oUserMethod->getUserMethodPrize( $iUserId, $sFields, '', "", "", FALSE, true );
		}
		$aTempArr = array();
		if(!empty($aMethodGroup))
		{
		    foreach( $aMethodGroup as $method )
		    {
		        $aTempArr[] = $method['methodid'];
		    }
		    $sFields = '`lotteryid`,`methodid`,`methodname`';
		    $sCondition = " M.`isclose`=0 AND (M.`pid` IN(".implode( ",", $aTempArr ).") OR M.`methodid` IN(".implode( ",", $aTempArr ).") )";
		    $aMethodByCrowd = $oMethod->methodGetAllListByCrowd('',$sCondition);
		}
		foreach ($aMethodByCrowd as $iLotteryId => $aCrowd)
		{
		    $aMethods[$iLotteryId] = $aCrowd['crowd'];
		}
		$GLOBALS['oView']->assign("lottery",        $aLottery);
		$GLOBALS['oView']->assign("data_method",    json_encode($aMethods)); //方便JS 调用玩法
        $issueList = $oIssue->getItems(0, date("Y-m-d"), 0, 0, 0, time(), 'saleend DESC');
        foreach ($issueList as $v)
        {
            $aIssue[$v['lotteryid']][] = array('issue' => $v['issue'], 'lotteryid' => $v['issue'], 'dateend' => $v['belongdate']);
        }

		$GLOBALS["oView"]->assign( "data_issue", json_encode($aIssue) );
		$iLotteryId = isset( $_GET["lotteryid"] ) && is_numeric( $_GET["lotteryid"] ) ? intval( $_GET["lotteryid"] ) : 0;
		$sHtml["lotteryid"] = $iLotteryId;
		//玩法
		$iCrowdId = isset($_GET["crowdid"])&&is_numeric($_GET["crowdid"]) ? intval($_GET["crowdid"]): 0;
		$sHtml["crowdid"] = $iCrowdId;
		$iPid = isset($_GET["pid"])&&is_numeric($_GET["pid"]) ? intval($_GET["pid"]): 0;
		$sHtml["pid"] = $iPid;
		$iMethodId = isset( $_GET["methodid"] )&&is_numeric( $_GET["methodid"] ) ? intval( $_GET["methodid"] ) :0;
		$sHtml["methodid"] = $iMethodId;
		if($sHtml["lotteryid"] >0 )
		{
			$sWhere .=" AND P.`lotteryid`='".$iLotteryId."' ";
			//按玩法群查询
            $aMInfo = array();
			if( $iCrowdId > 0 )
			{
//			    $mWhere .=" AND M.`crowdid`='".$iCrowdId."'";
                $aMInfo['crowdid'] = $iCrowdId;
			}
			//按玩法组查询
			if( $iPid > 0 )
			{
//			    $mWhere .=" AND M.`pid`='".$iPid."'";
                $aMInfo['pid'] = $iPid;
			}
			//按玩法查询
			if( $iMethodId > 0 )
			{
//			    $mWhere .=" AND M.`methodid`='".$iMethodId."'";
                $aMInfo['methodid'] = $iMethodId;
			}
			$sIssue         = isset( $_GET["issue"] )&&!empty( $_GET["issue"] )? daddslashes( $_GET["issue"] ):"0";
			$sHtml["issue"] = $sIssue;
			if( $sIssue!="0" )
			{
				$sWhere .= " AND P.`issue`='".$sIssue."'";
			}
		}
		else
		{
			$sHtml["methodid"]  = 0;
			$sHtml["issue"]     = 0;
		}
		//用户名以及是否包含(支持*号,不支持包含)
        $sFuzzyName = "";
		if( isset($_GET["username"])&&!empty($_GET["username"]) )
		{ //指定了用户名
			$sUserName = daddslashes( $_GET["username"] );
			if( strstr($sUserName,'*') )
			{ // 支持模糊搜索
//				$sWhere .= " AND UT.`username` LIKE '".str_replace( "*", "%", $sUserName )."'";
                $sFuzzyName = str_replace( "*", "%", $sUserName );
				$sHtml["include"] = 0; //支持*,不支持包含下级
				$iUserId = 0;
				$bInclude = FALSE;
				$sHtml["username"] = stripslashes_deep( $sUserName );
			}
			else
			{ //不支持模糊搜索
				$iUser = $oUser->getUseridByUsername( $sUserName ); //获取ID
				if( $iUser>0 )
				{ //需要检测当前搜索到的用户 和 当前用户的关系
					$iUserId = $iUser;
					$sHtml["username"] = stripslashes_deep( $sUserName );
					if( isset($_GET["include"]) && intval($_GET["include"])==1 )
					{
						$sHtml["include"] = 1;
						$bInclude = TRUE;
					}
					else
					{
						$sHtml["include"] = 0;
						$bInclude = FALSE;
					}
				}
				else
				{ //用户不存在
					$sWhere = " AND 1=0";
				}
			}
		}
		else
		{
			if( isset($_GET["include"])&&is_numeric( $_GET["include"] ) )
			{
				$bInclude   = TRUE;
				$iUserId    = 0;
				$sHtml["include"] = 1;
			}
		}
		if(isset($_GET['modes']) && array_key_exists( $_GET['modes'], $GLOBALS['config']['modes']) )
		{
			$sWhere .= ' AND P.`modes`='.intval($_GET['modes']);
			$sHtml["modes"] = $_GET['modes'];
		}
		//下面是Code
		if( isset($_GET["projectno"])&&!empty($_GET["projectno"]) )
		{
			$iProjectNo = model_projects::HighEnCode( daddslashes($_GET["projectno"]), "DECODE" );
			if( intval( $iProjectNo )>0 )
			{
				$sHtml["projectno"] = daddslashes( $_GET["projectno"] );
				$bInclude = TRUE;
				$iUserId = 0;
				$sWhere .= " AND P.`projectid`='".$iProjectNo."'";
			}
		}
//		$sWhere .= $sUserWhere;
		/* @var $oProject model_projects */
		$oProject = new model_projects( $GLOBALS['aSysDbServer']['report'] );
		$iPage = isset($_GET["p"])&&is_numeric($_GET["p"]) ?intval($_GET["p"]):1;
		$iIsGetData  = isset($_GET['isgetdata']) ? intval($_GET['isgetdata']) : 0;//是否查询数据
		$sHtml['isgetdata'] = $iIsGetData;
        $aFields = array();
        $aFields['projects']['all'] = "P.*";
        $aFields['usertree']['username'] = "UT.`username`";
        $aFields['method']['methodname'] = "M.`methodname`";
        $aFields['lottery']['cnname'] = "L.`cnname`";
        $aFields['lottery']['lotterytype'] = "L.`lotterytype`";
        $aFields['issueinfo']['code'] = "I.`code` AS `nocode`";
        $aFields['issueinfo']['statuscode'] = "I.`statuscode`";
		$aProjects = $iIsGetData == 0 ? array('affects' => 0, 'results' => array()) : $oProject->projectGetResult( $iUserId, $bInclude, $aFields, $sWhere,"P.`projectid` DESC", 25, $iPage, $sFuzzyName, $aMInfo, "", $sUserWhere, TRUE );
		$total["in"]  = 0.00;
		$total["out"] = 0.00;
        
        
        
		foreach($aProjects["results"] as $iProjectId=>&$aProject)
		{
			$aProject["projectid"] = model_projects::HighEnCode("D".$aProject["issue"]."-".$aProject["projectid"],"ENCODE");
			$total["in"]  = $total["in"] + $aProject["bonus"];
			$total["out"] = $total["out"]+ $aProject["totalprice"];
			$aProject['code'] = $oProject->AddslasCode($aProject['code'], $aProject['methodid']);
			//对号码进行整理
			if(strlen($aProject["code"])>20)
			{
				$str = "<a href=\"javascript:show_no('".$iProjectId."');\">详细号码</a>";
				$str .= "<div class=\"task_div\" id=\"code_".$iProjectId."\">号码详情";
				$str .= "[<a href=\"javascript:close_no('".$iProjectId."');\" class='fff600'>关闭</a>]<br/>";
				$str .="<textarea class=\"code\" readonly=\"readonly\">";
				$sTempCode      = "";
				$sProjectCode   = "";
				$aCodeDetail    = explode(",", $aProject["code"]);
				$iCodeLen = strlen($aCodeDetail[0]) + 1;//单个号码长度
				$iRowCodeLen = intval(40/$iCodeLen)*$iCodeLen;//一行的号码最大长度
				foreach ( $aCodeDetail as $sCode )
				{
					$sTempCode .= $sCode .",";
					$sProjectCode .= $sCode .",";
					if( strlen($sTempCode) >= $iRowCodeLen )
					{
						$sProjectCode = substr($sProjectCode, 0,-1);
						$sProjectCode .= "\r\n";
						$sTempCode = "";
					}
				}
				$sProjectCode = substr($sProjectCode, 0,-1);
				//                $code = str_replace( array("|"),array(","), $aProject["code"]);
				$str .= $sProjectCode."</textarea></div>";
				$str = str_replace( array("|"),array(","), $str);
				$aProject["code"] =$str;
			}
			else
			{
				$aProject["code"] =str_replace( array("|"),array(","), $aProject["code"]);
			}

			if( $aProject['codetype'] == 'input' && !strpos($aProject['methodname'], '混合') )
			{
				$aProject['methodname'] .= ' (单式)';
			}
			if($aProject['modes'] > 0)
			{
				$aProject['modes'] = $GLOBALS['config']['modes'][$aProject['modes']]['name'];
			}
			else
			{
				$aProject['modes'] = '';
			}

			if ( $aProject['statuscode'] != 2 )
			{
				$aProject['nocode'] = '';
			}
			else
			{
				if($aProject['lotterytype']==3)
				{
					$aProject['nocode'] = substr($aProject['nocode'], 0, 29).'<br />'.substr($aProject['nocode'], 30);
				}
			}
		}

        $uExtInfo = $oUser->getUserExtentdInfo( $iUserId, 0 );
        $bShowInclude = TRUE;
        if($uExtInfo['groupid'] == 4)
        {
        	$bShowInclude = FALSE;
        }
        
        
        $GLOBALS["oView"]->assign( "showInclude", $bShowInclude );
		$GLOBALS['oView']->assign( 'modes', $GLOBALS['config']['modes'] );
		$GLOBALS['oView']->assign( "total",    $total );
		$GLOBALS['oView']->assign( "aProject", $aProjects["results"] );
		$oPage = new pages( $aProjects["affects"], 25 );
		//die('++++++++++++');
		//for client echo
		//$this->formatDataEcho($aProjects);
		
		$GLOBALS['oView']->assign( "pageinfo", $oPage->show(1));
		$GLOBALS['oView']->assign( "s", $sHtml);
		$GLOBALS['oView']->assign( "actionlink", array('text'=>'清空查询条件',"href"=>url('gameinfo','gamelist')));
		$GLOBALS['oView']->assign( "ur_here", "参与游戏信息" );
		$oMethod->assignSysInfo();
        
		$GLOBALS['oView']->display( "gameinfo_gamelist.html" );
		EXIT;
	}

    //ios 游戏详情
    function iosGameDetail() {
        
		$iProjectId     = isset($_GET["id"])&&!empty($_GET["id"]) ? model_projects::HighEnCode($_GET["id"],"DECODE"):0;

        if( $iProjectId==0 )
		{
            $errData = array('error' => 1, 'msg' => "权限不足");
            echo json_encode($errData);
            exit;
		}
		/* @var $oUser model_user */
		$oUser = A::singleton( "model_user", $GLOBALS['aSysDbServer']['report'] );
		if( intval($_SESSION["usertype"])==2 )
		{ //总代管理员
			$iUserId = $oUser->getTopProxyId( intval($_SESSION["userid"]), FALSE ); //获取总代
			if( $oUser->IsAdminSale( intval($_SESSION["userid"]) ) )
			{ //为销售
				$sUserWhere = " AND UT.`lvproxyid` IN (SELECT `topproxyid` FROM `useradminproxy`"
				." WHERE `adminid`='".intval($_SESSION["userid"])."')";
			}
			else
			{
				$sUserWhere = " AND UT.`lvtopid`='".$iUserId."'";
			}
		}
		else
		{
			$iUserId = intval( $_SESSION["userid"] );
			$sUserWhere = " AND (FIND_IN_SET('".intval($iUserId)."',UT.`parenttree`)"
			." OR (UT.`userid`='".$iUserId."'))";
		}
		$oProject = new model_projects( $GLOBALS['aSysDbServer']['report'] );
        $aFields = array();
        $aFields['projects']['all'] = "P.*";
        $aFields['usertree']['username'] = "UT.`username`";
        $aFields['method']['methodname'] = "M.`methodname`";
        $aFields['method']['functionname'] = "M.`functionname`";
        $aFields['method']['nocount'] = "M.`nocount`";
        $aFields['lottery']['cnname'] = "L.`cnname`";
        $aFields['lottery']['lotterytype'] = "L.`lotterytype`";
        $aFields['issueinfo']['code'] = "I.`code` as `nocode`";
        $aFields['issueinfo']['canneldeadline'] = "I.`canneldeadline`";
        $aFields['issueinfo']['statuscode'] = "I.`statuscode`";
		$aProjects = $oProject->projectGetIosResult( 0, FALSE,
             $aFields,"AND `projectid`='".$iProjectId."'", "", 0, 1, "", "", "", $sUserWhere );
        

        
		if( empty($aProjects[0]) )
		{
			
            $errData = array('error' => 1, 'msg' => "注单不存在");
            echo json_encode($errData);
            exit;
		} 
        $returnData = array();
        

        
        foreach($aProjects as $iProjectId=>&$aProject)
		{
            
            $aProject["pid"] = $aProject["projectid"];
            
            
			$aProject["projectid"] = model_projects::HighEnCode("D".$aProject["issue"]."-".$aProject["projectid"],"ENCODE");
			$total["in"]  = $total["in"] + $aProject["bonus"];
			$total["out"] = $total["out"]+ $aProject["totalprice"];
            
            
            
            $aProject["iscancel"] = $aProject["iscancel"];
            
            //保留之前的号码 
            $aProject["originalcode"] = $aProject['code'];
            
			$aProject['code'] = $oProject->AddslasCode($aProject['code'], $aProject['methodid']);
			//对号码进行整理
			
			$aProject["code"] =str_replace( array("|"),array(","), $aProject["code"]);
			

			if( $aProject['codetype'] == 'input' && !strpos($aProject['methodname'], '混合') )
			{
				$aProject['methodname'] .= ' (单式)';
			}
			if($aProject['modes'] > 0)
			{
				$aProject['modes'] = $GLOBALS['config']['modes'][$aProject['modes']]['name'];
			}
			else
			{
				$aProject['modes'] = '';
			}

			if ( $aProject['statuscode'] != 2 )
			{
				$aProject['nocode'] = '';
			}
			else
			{
				if($aProject['lotterytype']==3)
				{
					$aProject['nocode'] = substr($aProject['nocode'], 0, 29).'<br />'.substr($aProject['nocode'], 30);
				}
			}
		}

        
        foreach ($aProjects as $key => $value) {
            
            $prizeStatus = $value['prizestatus'];
            $istatus = 0; //未开奖
            if ($value['isgetprize'] == 1) {
                if ($prizeStatus == 0) {
                     $istatus = 2;    //未派奖
                } elseif ($prizeStatus == 1) {
                    $istatus = 3;     //已经派奖
                }
            } elseif ($value['isgetprize'] == 2) {
                $istatus = 1;         //未中将
            }
            $issue = $value['issue'];
            $openCode = $oProject->getIssueCode($issue);
            //$originalcode = $oProject->getIosExpandcode($value['pid']);
            
            if($value['methodid'] == 30)
            {
                $original = str_replace("|", "&", $value['originalcode']) . "||||";
                // 7|8|9 => 7&8&9||||
            } else if ($value['methodid'] == 31) {
                $original = "|" . str_replace("|", "&", $value['originalcode']) . "|||";
            } else if ($value['methodid'] == 32) {
                $original = "||" . str_replace("|", "&", $value['originalcode']) . "||";
            } else if ($value['methodid'] == 33) {
                $original = "|||" . str_replace("|", "&", $value['originalcode']) . "|";
            } else if ($value['methodid'] == 34) {
                $original = "||||" . str_replace("|", "&", $value['originalcode']) ;
            } else {
                $original = $value['originalcode'];
            }
            
            //$returnData[] = array('enid' => $_GET["id"], 'ifwin'=> $istatus, 'opencode'=> $openCode[0]['code'],  'bonus' => $value['bonus'],  'time' => $value['writetime'], 'modes' => $value['modes'], 'price' => $value['totalprice'], 'multiple' => $value['multiple'], 'codedetails' => $value['code'], 'methodname' => $value['methodname'] );
            $returnData[] = array('enid' => $_GET["id"], 'ifwin'=> $istatus, 'iscancel' => $value['iscancel'], 'opencode'=> $openCode[0]['code'],  'bonus' => $value['bonus'],  'time' => $value['writetime'], 'modes' => $value['modes'], 'price' => $value['totalprice'], 'multiple' => $value['multiple'], 'codedetails' =>  $value['code'], 'methodname' => $value['methodname'], 'originalcode' => $original );

        }
        //print_r($returnData);
        
        echo json_encode($returnData);
        
    }


	/**
	 * 查看游戏详情
	 * URL：./index.php?controller=gameinfo&action=gamedetail
	 * @author SAUL
	 */
	function actionGamedetail()
	{
        
        
        if ($_REQUEST['flag'] == 'gameinfo') {
            $this->iosGameDetail();
            return true;        
            
        }
        
		$aLocation[0]   = array("title"=>'参与游戏信息',"url"=>url('gameinfo','gamelist'));
		$iProjectId     = isset($_GET["id"])&&!empty($_GET["id"]) ? model_projects::HighEnCode($_GET["id"],"DECODE"):0;
		if( $iProjectId==0 )
		{
			sysMsg( '权限不足', 2, $aLocation );
		}
		/* @var $oUser model_user */
		$oUser = A::singleton( "model_user", $GLOBALS['aSysDbServer']['report'] );
		if( intval($_SESSION["usertype"])==2 )
		{ //总代管理员
			$iUserId = $oUser->getTopProxyId( intval($_SESSION["userid"]), FALSE ); //获取总代
			if( $oUser->IsAdminSale( intval($_SESSION["userid"]) ) )
			{ //为销售
				$sUserWhere = " AND UT.`lvproxyid` IN (SELECT `topproxyid` FROM `useradminproxy`"
				." WHERE `adminid`='".intval($_SESSION["userid"])."')";
			}
			else
			{
				$sUserWhere = " AND UT.`lvtopid`='".$iUserId."'";
			}
		}
		else
		{
			$iUserId = intval( $_SESSION["userid"] );
			$sUserWhere = " AND (FIND_IN_SET('".intval($iUserId)."',UT.`parenttree`)"
			." OR (UT.`userid`='".$iUserId."'))";
		}
		$oProject = new model_projects( $GLOBALS['aSysDbServer']['report'] );
        $aFields = array();
        $aFields['projects']['all'] = "P.*";
        $aFields['usertree']['username'] = "UT.`username`";
        $aFields['method']['methodname'] = "M.`methodname`";
        $aFields['method']['functionname'] = "M.`functionname`";
        $aFields['method']['nocount'] = "M.`nocount`";
        $aFields['lottery']['cnname'] = "L.`cnname`";
        $aFields['lottery']['lotterytype'] = "L.`lotterytype`";
        $aFields['issueinfo']['code'] = "I.`code` as `nocode`";
        $aFields['issueinfo']['canneldeadline'] = "I.`canneldeadline`";
        $aFields['issueinfo']['statuscode'] = "I.`statuscode`";
		$aProject = $oProject->projectGetResult( 0, FALSE,
             $aFields,"AND `projectid`='".$iProjectId."'", "", 0, 1, "", "", "", $sUserWhere );
		if( empty($aProject[0]) )
		{
			sysMsg( '单子不存在', 2, $aLocation );
		}
		if ( $aProject[0]['statuscode'] != 2 )
		{
			$aProject[0]['nocode'] = '';
		}
        
        
		//注单编号
		if(intval($aProject[0]["taskid"])>0)
		{
			$oTask = new model_task( $GLOBALS['aSysDbServer']['report'] );
			$aTask = $oTask->getTaskInfo("T.`taskid`,T.`beginissue`"," and T.`taskid`='".$aProject[0]["taskid"]."'");
			$aProject[0]["taskid"] = model_projects::HighEnCode("T".$aTask[0]["beginissue"]."-".$aTask[0]["taskid"],"ENCODE");
		}
		$GLOBALS['oView']->assign( "ur_here", "查看注单详情" );
		$bigmoney = getConfigValue('bigordercancel','10000'); //大额撤单底线
		if( $aProject[0]["totalprice"] > $bigmoney )
		{
			$big = getConfigValue('bigordercancelpre', '0.01'); //大额撤单的手续费比例
			$money = $big * $aProject[0]["totalprice"];
			$GLOBALS["oView"]->assign( "need",  1 ); //需要收费
			$GLOBALS['oView']->assign( "money", $money );
		}
		if(strtotime($aProject[0]["canneldeadline"]) > time() && $aProject[0]['iscancel'] == 0 )
		{ //没有撤单 && 没有过最后的撤单时间(issueinfo表)
			if( intval($_SESSION["userid"])== intval($aProject[0]["userid"]) )
			{
				$GLOBALS['oView']->assign("can", 1 ); //能否撤单
			}
		}
		//获取扩展号码详情
		$prizelevel = $oProject->getExtendCode( "*", "`projectid`='".$aProject[0]["projectid"]."'",
                  "`level` ASC", 0 );
		$aProject[0]['code'] = $oProject->AddslasCode( $aProject[0]["code"], $aProject[0]['methodid'] );
		$aProject[0]["code"] = wordwrap( str_replace( array("|"),array(","), $aProject[0]["code"]),100,"<br/>" );
		$aProject[0]["projectid"] = model_projects::HighEnCode("D".$aProject[0]["issue"]."-".$aProject[0]["projectid"],"ENCODE");
		if($aProject[0]['modes'] > 0)
		{
			$aProject[0]['modes'] = $GLOBALS['config']['modes'][$aProject[0]['modes']]['name'];
		}
		else
		{
			$aProject[0]['modes'] = '';
		}
		$GLOBALS['oView']->assign( "project", $aProject[0] );
		//扩展号码整理
		$aPrizelevelDesc = unserialize( $aProject[0]['nocount'] );
		//获取中奖详情:数字型彩种
		/* @var $oGetPrize model_getprize */
		$oGetPrize = A::singleton("model_getprize", $GLOBALS['aSysDbServer']['report']);
		if($aProject[0]['isgetprize'] == 1 && $aProject[0]['prizestatus'] == 1 && $aProject[0]['lotterytype'] == 0)
		{
		    $aProjectPrize = $oGetPrize->getProjectPrize($iProjectId,$aProject[0]['methodid'],$prizelevel,$aProject[0]['nocode']);
		    if(!empty($aProjectPrize) && $aProjectPrize['totalprize'] != 0.00)
		    {
		        foreach ($aProjectPrize['detail'] as $iLevel => &$PrizeDetail)
		        {
		            $PrizeDetail['multiple'] = $aProject[0]['multiple'];
		            $aDesc = explode(":",$aPrizelevelDesc[$iLevel]['name']);
		            if(count($aDesc) == 2)
		            {
		                $PrizeDetail["leveldesc"] = $aDesc[0];
		            }
		            else
		            {
		                $PrizeDetail["leveldesc"] = $aPrizelevelDesc[$iLevel]['name'];
		            }
		            $PrizeDetail["singleprize"] = $PrizeDetail['prize']/$PrizeDetail['multiple']/$PrizeDetail['times'];
		        }
		        $GLOBALS['oView']->assign('projectprize', $aProjectPrize);
		    }
		}
		foreach($prizelevel as $i => $v)
		{
		    $aDesc = explode(":",$aPrizelevelDesc[$v['level']]['name']);
		    if(count($aDesc) == 2)
		    {
		        $prizelevel[$i]["leveldesc"] = $aDesc[0];
		        $prizelevel[$i]["levelcodedesc"] = $aDesc[1];
		    }
		    else 
		    {
		        $prizelevel[$i]["leveldesc"] = $aPrizelevelDesc[$v['level']]['name'];
		        $prizelevel[$i]["levelcodedesc"] = $aPrizelevelDesc[$v['level']]['name'];
		    }
		    $prizelevel[$i]["singleprize"] = $prizelevel[$i]["prize"]/$prizelevel[$i]["codetimes"];
			$prizelevel[$i]["expandcode"] = $oProject->AddslasCode( $v["expandcode"], $aProject[0]['methodid'] );
			$prizelevel[$i]["expandcode"] = wordwrap(
			str_replace( array("|"),array(","), $prizelevel[$i]["expandcode"] ),80,"<br>");
		}
		if($aProject[0]['lotterytype'] == 3 && $aProject[0]['codetype'] == 'dxds' && $aProject[0]['nocode'] != '')
		{//基诺趣味型玩法
			$aProjectCode = explode(",",$aProject[0]['code']);//用户购买号码
		    $aCode = explode(" ",$aProject[0]['nocode']);//开奖号码
		    $sNoHePan = $this->getkenoquweicode($aCode);
			$aCode = explode('<br>', $sNoHePan);
			$bCode = array();
			
			foreach($aCode as $k=>$s)
			{
				$s = preg_replace('/.*\((.*)\)/', '$1', $s);
				if( $k == 0 )
				{
					$bCode[] = str_replace(',', '', $s);
				}
				else
				{
					$ts = explode(',', $s);
					foreach($ts as $tts)
						$bCode[] = $tts. '盘';
				}
			}
		    $aResult = $this->getKenoQWPrize($aProjectCode, $bCode, $aPrizelevelDesc, $prizelevel);
		    $GLOBALS['oView']->assign("nohepan",$sNoHePan);
		    $GLOBALS['oView']->assign("samecode", $aResult['samecode']);
		    $GLOBALS['oView']->assign("realprize",$aResult['realprize']);
		    $GLOBALS['oView']->assign("totalcount",$aResult['totalcount']);
		    $GLOBALS['oView']->assign("totalprize",$aResult['totalprize']);
		}
		if($aProject[0]['lotterytype'] == 3 && $aProject[0]['nocode'] != ''
		&& $aProject[0]['codetype'] == 'digital' && $aProject[0]['bonus'] > 0)
		{//基诺任选型玩法
		    $aProjectCode = explode(",",$aProject[0]['code']);//用户购买号码
		    $aCode = explode(" ",$aProject[0]['nocode']);//开奖号码
		    $iSelNum = intval(substr($aProject[0]['functionname'],-1));//玩法最少选择的选择号码个数
		    $aResult = $this->getKenoPrize($aProjectCode, $aCode, $aPrizelevelDesc, $prizelevel, $iSelNum);
		    $GLOBALS['oView']->assign("samecode", $aResult['samecode']);
		    $GLOBALS['oView']->assign("realprize",$aResult['realprize']);
		    $GLOBALS['oView']->assign("totalcount",$aResult['totalcount']);
		    $GLOBALS['oView']->assign("totalprize",$aResult['totalprize']);
		}
		if($aProject[0]['lotterytype'] == 3 && $aProject[0]['codetype'] == 'digital')
		{//基诺任选型玩法
		    $aProjectCode = explode(",",$aProject[0]['code']);//用户购买号码
		    $aBonusCode = explode(" ",$aProject[0]['nocode']);//开奖号码
		    $iSelNum = intval(substr($aProject[0]['functionname'],-1));//玩法最少选择的选择号码个数
		    $GLOBALS['oView']->assign("codecount",$this->GetCombinCount(count($aProjectCode),$iSelNum));
		    $aCodeGroup = $this->getCombination($aProjectCode,$iSelNum);
		    $aCodeGroupResult = array();
		    foreach ($aCodeGroup as $iKey => $sCode)
		    {
		        $aCodeDetail = array();
		        if($aProject[0]['bonus'] > 0)
		        {
		            $aMinNumCount = array(1=>1,2=>2,3=>2,4=>2,5=>3,6=>3,7=>4);//各个玩法最少中奖号码个数,7中0单独计算
		            $sNewCode = '';
		            $aCurrCode = explode(",", $sCode);
		            $iSameCodeCount = 0;
		            foreach ($aCurrCode as $sCurrCode)
		            {
		                if(in_array($sCurrCode,$aBonusCode))
		                {
		                    $sNewCode .= "<font color=red>".$sCurrCode."</font>,";
		                    $iSameCodeCount++;
		                }
		                else
		                {
		                    $sNewCode .= "<font color=black>".$sCurrCode."</font>,";
		                }
		            }
		            $sNewCode = substr($sNewCode,0,-1);
		            $iLevel = $iSelNum;//奖级
		            if($iSelNum == 7 && $iSameCodeCount == 0)
		            {
		                $iLevel = 5;
		            }
		            else
		            {
		                if($iSameCodeCount >= $aMinNumCount[$iSelNum])
		                {
		                    $iLevel = $iSelNum+1-$iSameCodeCount;//对应奖级
		                }
		            }
		            if($iLevel > 0 && $iLevel < $iSelNum || ($iLevel == 1 && $iSelNum == 1 && $iSameCodeCount > 0))
		            {
		                $aDesc = explode(":",$aPrizelevelDesc[$iLevel]['name']);
		                $sLevelDesc = $aDesc[0];
		                $aCodeDetail['leveldesc'] = $sLevelDesc;
		            }
		            $aCodeDetail['code'] = $sNewCode;
		            $aCodeDetail['level'] = $iLevel;
		        }
		        else
		        {
		            $sNewCode = '';
		            $aCurrCode = explode(",", $sCode);
		            foreach ($aCurrCode as $sCurrCode)
		            {
		                if(in_array($sCurrCode,$aBonusCode))
		                {
		                    $sNewCode .= "<font color=red>".$sCurrCode."</font>,";
		                }
		                else
		                {
		                    $sNewCode .= "<font color=black>".$sCurrCode."</font>,";
		                }
		            }
		            $sNewCode = substr($sNewCode,0,-1);
		            $aCodeDetail['code'] = $sNewCode;
		        }
		        $aCodeDetail['order'] = $iKey+1;
		        $aCodeGroupResult[$iKey+1] = $aCodeDetail;
		    }
		    $GLOBALS['oView']->assign("aCodeGroup",$aCodeGroupResult);
		    //按奖级排序
		    usort($aCodeGroupResult, array("controller_gameinfo", "cmp"));
		    $GLOBALS['oView']->assign("aCodeGroupOrder",$aCodeGroupResult);
		}
		$GLOBALS['oView']->assign( "prizelevel", $prizelevel );
		$GLOBALS['oView']->assign("levelcount",count($prizelevel));
		$oProject->assignSysInfo();
		$GLOBALS['oView']->display( "gameinfo_gamedetail.html" );
		EXIT;
	}

	static function cmp($a, $b)
	{
		if(!isset($a['level'])) return -1;
		if(!isset($b['level'])) return 1;
		return ($a['level'] >= $b['level'] && isset($b['leveldesc'])) ? 1 : -1;
	}

	
	/**
     * 字符串排序
     * @param string $sString 需要排序的字符串
     * @return string
     * @author mark
     */
    private function strOrder( $sString = '', $bDesc = FALSE )
    {
        if( $sString == '')
        {
            return $sString;
        }
        $aString = str_split($sString);
        if($bDesc)
        {
            rsort($aString);
        }
        else
        {
            sort($aString);
        }
        return implode('',$aString);
    }
    
    
	/**
        * 计算排列组合的个数
        *
        * @author mark
        * 
        * @param integer $iBaseNumber   基数
        * @param integer $iSelectNumber 选择数
        * 
        * @return mixed
        * 
    */
	function GetCombinCount( $iBaseNumber, $iSelectNumber )
	{
	    if($iSelectNumber > $iBaseNumber)
	    {
	        return 0;
	    }
	    if( $iBaseNumber == $iSelectNumber || $iSelectNumber == 0 )
	    {
	        return 1;//全选
	    }
	    if( $iSelectNumber == 1 )
	    {
	        return $iBaseNumber;//选一个数
	    }
	    $iNumerator = 1;//分子
	    $iDenominator = 1;//分母
	    for($i = 0; $i < $iSelectNumber; $i++)
	    {
	        $iNumerator *= $iBaseNumber - $i;//n*(n-1)...(n-m+1)
	        $iDenominator *= $iSelectNumber - $i;//(n-m)....*2*1
	    }
	    return $iNumerator / $iDenominator;
	}

	/**
	 * 用户撤单
	 * URL: ./index.php?controller=gameinfo&action=cancelgame
	 * @author JAMES
	 */
	function actionCancelgame()
	{
		$sProjectNo   = !empty($_GET["id"]) ? $_GET["id"] : "";
		$aLocation[0] = array("title"=>'查看注单详情',"url"=>url('gameinfo', 'gamedetail', array('id'=>$sProjectNo)));
		$iProjectId   = !empty($sProjectNo) ? model_projects::HighEnCode($sProjectNo, "DECODE") : 0;
		if( $iProjectId == 0 )
		{
			sysMsg( '权限不足', 2, $aLocation );
		}
		/* @var $oGame model_gamemanage */
		$oGame      = A::singleton("model_gamemanage");
		$mResult    = $oGame->cancelProject( intval($_SESSION["userid"]), $iProjectId );
		if( $mResult === TRUE )
		{
			sysMsg( '撤单成功', 1, $aLocation );
		}
		else
		{
			sysMsg( $mResult, 2, $aLocation );
		}
	}



	/**
	 * 追号记录
	 * URL: ./index.php?controller=gameinfo&action=task
	 * @author SAUL
	 */
	function actionTask()
	{   //  查询自身+下级的追号记录
		$iUserId = $_SESSION['userid'];
		$oMethod   = A::singleton( "model_method", $GLOBALS['aSysDbServer']['report'] );
		$oLottery = A::singleton( "model_lottery", $GLOBALS['aSysDbServer']['report'] );
		$oUser = A::singleton( 'model_user', $GLOBALS['aSysDbServer']['report'] );
		$aLottery  = array(); //彩种组
		if( intval($_SESSION["usertype"])==2 )
		{//总代管理员
			$bIsAdmin = TRUE;
			$iUserId = $oUser->getTopProxyId(intval($_SESSION["userid"]), FALSE ); //获取总代
		}
		else
		{
			$bIsAdmin   = $oUser->isTopProxy( $iUserId );
		}
        if (isset($_GET['flag']) && $_GET['flag'] == "fromdg"){
            $_GET["starttime"] = date("Y-m-d H:i:s", time() - 3 * 60);
            $_GET['endtime'] = date("Y-m-d H:i:s", time());
            $_GET['username'] = $_SESSION['username'];
        }
		foreach( $oLottery->getLotteryByUser( $iUserId, $bIsAdmin, 'l.cnname, l.lotteryid' ) AS $l )
		{
			$aLottery[$l['lotteryid']] = $l['cnname'];
		}
		$aMethods  = array(); //玩法组
		if($bIsAdmin)
		{
			$aMethodByCrowd = $oMethod->methodGetAllListByCrowd('','');
		}
		else
		{
			$oUserMethod = new model_usermethodset( $GLOBALS['aSysDbServer']['report'] );
			$sFields     = " m.`methodid`, m.`lotteryid`, m.`methodname` ";
			$aMethodGroup= $oUserMethod->getUserMethodPrize( $iUserId, $sFields, '', "", "", FALSE, true );
		}
		$aTempArr = array();
		if(!empty($aMethodGroup))
		{
		    foreach( $aMethodGroup as $method )
		    {
		        $aTempArr[] = $method['methodid'];
		    }
		    $sFields = '`lotteryid`,`methodid`,`methodname`';
		    $sCondition = " M.`isclose`=0 AND (M.`pid` IN(".implode( ",", $aTempArr ).") OR M.`methodid` IN(".implode( ",", $aTempArr ).") )";
		    $aMethodByCrowd = $oMethod->methodGetAllListByCrowd('',$sCondition);
		}
		foreach ($aMethodByCrowd as $iLotteryId => $aCrowd)
		{
		    $aMethods[$iLotteryId] = $aCrowd['crowd'];
		}
		$GLOBALS['oView']->assign( "lottery",       $aLottery );
		$GLOBALS['oView']->assign( "data_method",   json_encode($aMethods) );
		//参数整理
		$sWhere = " ";
		//开始时间
		if( isset($_GET["starttime"])&&!empty($_GET["starttime"]) )
		{
			$sStartTime = getFilterDate($_GET["starttime"]);
		}
		else
		{
		    $sStartTime = time() < strtotime(date("Y-m-d {$this->runStartTime}")) ? date("Y-m-d {$this->runStartTime}", strtotime("-1 days")) : date("Y-m-d {$this->runStartTime}");  //默认为当天
		}
		if( !empty($sStartTime) )
		{
			$sWhere .= " AND T.`begintime`>='".$sStartTime."'";
			$sHtml["starttime"] = $sStartTime;
		}
		//结束时间
		if( isset($_GET["endtime"])&&!empty($_GET["endtime"]) )
		{
			$sEndTime = getFilterDate($_GET["endtime"]);
		}
		else
		{
			$sEndTime = time() < strtotime(date("Y-m-d {$this->runStartTime}")) ? date("Y-m-d {$this->runStartTime}") : date("Y-m-d {$this->runStartTime}", strtotime("+1 days"));  //默认为当天
		}
		if( !empty($sEndTime) )
		{
			$sHtml["endtime"] = $sEndTime;
			$sWhere .= " AND T.`begintime`<'".$sEndTime."'";
		}
		/* @var $oIssue model_issueinfo */
		$oIssue     = A::singleton( "model_issueinfo", $GLOBALS['aSysDbServer']['report'] );
		$aIssue = array();
        $issueList = $oIssue->getItems(0, date("Y-m-d"), 0, 0, 0, time(), 'saleend DESC');
        foreach ($issueList as $v)
        {
            $aIssue[$v['lotteryid']][] = array('issue' => $v['issue'], 'lotteryid' => $v['issue'], 'dateend' => $v['belongdate']);
        }
        
		$GLOBALS["oView"]->assign( "data_issue", json_encode($aIssue) );
		$iLotteryId = isset($_GET["lotteryid"])&&is_numeric($_GET["lotteryid"]) ? intval($_GET["lotteryid"]) : 0;
		$sHtml["lotteryid"] = $iLotteryId;
		$iCrowdId = isset($_GET["crowdid"])&&is_numeric($_GET["crowdid"]) ? intval($_GET["crowdid"]): 0;
		$sHtml["crowdid"] = $iCrowdId;
		$iPid = isset($_GET["pid"])&&is_numeric($_GET["pid"]) ? intval($_GET["pid"]): 0;
		$sHtml["pid"] = $iPid;
		$iMethodId = isset( $_GET["methodid"] )&&is_numeric( $_GET["methodid"] ) ? intval( $_GET["methodid"] ) :0;
		$sHtml["methodid"] = $iMethodId;
		if( $iLotteryId>0 )
		{
			$sWhere .=" AND T.`lotteryid`='".$iLotteryId."' ";
			//按玩法群查询
            $aMInfo = array();
			if( $iCrowdId > 0 )
			{
//			    $mWhere .=" AND M.`crowdid`='".$iCrowdId."'";
                $aMInfo['crowdid'] = $iCrowdId;
			}
			//按玩法组查询
			if( $iPid > 0 )
			{
//			    $mWhere .=" AND M.`pid`='".$iPid."'";
                $aMInfo['pid'] = $iPid;
			}
			//按玩法查询
			if( $iMethodId > 0 )
			{
//			    $mWhere .=" AND M.`methodid`='".$iMethodId."'";
                $aMInfo['methodid'] = $iMethodId;
			}
			$sIssue = isset($_GET["issue"])&&!empty($_GET["issue"]) ? daddslashes($_GET["issue"]): "0";
			$sHtml["issue"] = $sIssue;
			if( $sIssue<>"0" )
			{
				$sWhere .= " AND T.`beginissue`='".$sIssue."'";
			}
		}
		else
		{
			$sHtml["methodid"] = 0;
			$sHtml["issue"] = 0;
		}
		//用户身份的转化
		/* @var $oUser model_user */
		$oUser = A::singleton( "model_user", $GLOBALS['aSysDbServer']['report'] );
		if( intval($_SESSION["usertype"])==2 )
		{//销售
			$bIsAdmin   = TRUE;
			$iUserId    = $oUser->getTopProxyId( intval($_SESSION["userid"]), FALSE ); //获取总代
			if( $oUser->IsAdminSale( intval($_SESSION["userid"]) ) )
			{ //为销售
				$sUserWhere = " AND UT.`lvproxyid` in (SELECT `topproxyid` FROM `useradminproxy`"
				." WHERE `adminid`='".intval($_SESSION["userid"])."')";
			}
			else
			{
				$sUserWhere = " AND UT.`lvtopid`='".intval($iUserId)."'";
			}
		}
		else
		{
			$iUserId = intval($_SESSION["userid"]);
			$bIsAdmin = FALSE;
			$sUserWhere = " AND (FIND_IN_SET('".$iUserId."',UT.`parenttree`)"
			." OR (UT.`userid`='".$iUserId."'))";
		}
		if( $bIsAdmin )
		{
			$bInclude = TRUE;
			$sHtml["include"] = 1;
		}
		else
		{
			if($oUser->isTopProxy($iUserId))
			{
				$bInclude = TRUE;
				$sHtml["include"] = 1;
			}
			else
			{
				$bInclude = FALSE;
				$sHtml["include"] = 0;
			}
		}
		//用户名以及是否包含(支持*号,不支持包含)
		if(isset($_GET["username"])&&!empty($_GET["username"]))
		{ //指定了用户名
			$sUserName = daddslashes( $_GET["username"] );
			if( strstr($sUserName,'*') )
			{ // 支持模糊搜索
				$sUserWhere .= " AND UT.`username` LIKE '".str_replace( "*", "%", $sUserName )."'";
				$sHtml["include"] = 0; //支持*,不支持包含下级
				$iUserId = 0;
				$bInclude = FALSE;
				$sHtml["username"] = stripslashes_deep($sUserName);
			}
			else
			{ //不支持模糊搜索
				$iUser = $oUser->getUseridByUsername( $sUserName ); //获取ID
				if($iUser >0)
				{ //需要检测当前搜索到的用户 和 当前用户的关系
					$iUserId = $iUser;
					$sHtml["username"] = stripslashes_deep($sUserName);
					if( isset($_GET["include"]) && intval($_GET["include"])==1 )
					{
						$sHtml["include"] = 1;
						$bInclude = TRUE;
					}
					else
					{
						$sHtml["include"] = 0;
						$bInclude = FALSE;
					}
				}
				else
				{ //用户不存在
					$sWhere = " AND 1=0";
				}
			}
		}
		else
		{
			if(isset($_GET["include"])&&is_numeric($_GET["include"]))
			{
				$bInclude = TRUE;
				$iUserId = 0;
				$sHtml["include"] = 1;
			}
		}
		if(isset($_GET['modes']) && array_key_exists($_GET['modes'], $GLOBALS['config']['modes']))
		{
			$sWhere .= ' AND T.`modes`='.intval($_GET['modes']);
			$sHtml["modes"] = intval($_GET['modes']);
		}
		//下面是Code
		if( isset($_GET["taskno"])&&!empty($_GET["taskno"]) )
		{
			$iTaskId = model_projects::HighEnCode($_GET["taskno"], "DECODE" );
			if( $iTaskId>0 )
			{
				$sHtml["taskno"] = daddslashes($_GET["taskno"]);
				$sWhere .= " AND T.`taskid`='".intval($iTaskId)."'";
				$iUserId = 0;
				$bInclude = TRUE;
			}
		}
//		$sWhere .= $sUserWhere;
		$iPage = isset($_GET["p"])&&is_numeric($_GET["p"]) ?intval($_GET["p"]): 1;
		/* @var $oTask model_task */
		//$oTask = A::singleton("model_task");
		$oTask = new model_task( $GLOBALS['aSysDbServer']['report'] );
		$iIsGetData  = isset($_GET['isgetdata']) ? intval($_GET['isgetdata']) : 0;//是否查询数据
		$sHtml['isgetdata'] = $iIsGetData;
		$aTask = $iIsGetData == 0 ? array('affects' => 0, 'results' => array()) : $oTask->taskgetList( $iUserId, $bInclude,"",$sWhere, $sUserWhere, "T.`taskid` DESC", 25, $iPage, $aMInfo );
		$total["total"]  = 0.00;
		$total["finish"] = 0.00;
		$total["cancel"] = 0.00;
		foreach( $aTask["results"] as $iTaskId=>&$task )
		{
			$task["taskid"] = model_projects::HighEnCode("T".$task["beginissue"]."-".$task["taskid"],"ENCODE");
			$total["total"]  = $total["total"]  + $task["taskprice"];
			$total["finish"] = $total["finish"] + $task["finishprice"];
			$total["cancel"] = $total["cancel"] + $task["cancelprice"];
			$task['codes'] = model_projects::AddslasCode($task['codes'], $task['methodid']);
			//对号码进行整理
			if(strlen($task["codes"])>20)
			{
				$str = "<a href=\"javascript:show_no('".$iTaskId."');\">详细号码</a>";
				$str .= "<div class=\"task_div\" id=\"code_".$iTaskId."\">号码详情";
				$str .= "[<a href=\"javascript:close_no('".$iTaskId."');\" class='fff600'>关闭</a>]<br/>";
				$str .="<textarea class=\"code\" readonly=\"readonly\">";
				$sTempCode      = "";
				$sProjectCode   = "";
				$aCodeDetail    = explode(",", $task['codes']);
				$iCodeLen = strlen($aCodeDetail[0]) + 1;//单个号码长度
				$iRowCodeLen = intval(40/$iCodeLen)*$iCodeLen;//一行的号码最大长度
				foreach ( $aCodeDetail as $sCode )
				{
					$sTempCode .= $sCode .",";
					$sProjectCode .= $sCode .",";
					if( strlen($sTempCode) >= $iRowCodeLen )
					{
						$sProjectCode = substr($sProjectCode, 0,-1);
						$sProjectCode .= "\r\n";
						$sTempCode = "";
					}
				}
				$sProjectCode = substr($sProjectCode, 0,-1);
				$str .= $sProjectCode."</textarea></div>";
				$str = str_replace( array("|"),array(","), $str);
				$task["codes"] =$str;
			}
			else
			{
				$task["codes"] =str_replace( array("|"),array(","), $task["codes"]);
			}
			if( $task['codetype'] == 'input' && !strpos($task['methodname'], '混合') )
			{
				$task['methodname'] .= ' (单式)';
			}
			if($task['modes'] > 0)
			{
				$task['modes'] = $GLOBALS['config']['modes'][$task['modes']]['name'];
			}
			else
			{
				$task['modes'] = '';
			}
		}
        $uExtInfo = $oUser->getUserExtentdInfo( $iUserId, 0 );
        $bShowInclude = TRUE;
        if($uExtInfo['groupid'] == 4)
        {
        	$bShowInclude = FALSE;
        }
        $GLOBALS["oView"]->assign( "showInclude", $bShowInclude );
		$GLOBALS["oView"]->assign( "modes",    $GLOBALS["config"]['modes'] );
		$GLOBALS['oView']->assign( "total", $total );
		$GLOBALS['oView']->assign( "aTask", $aTask["results"] );
		$oPage = new pages( $aTask["affects"], 25 );
		$GLOBALS['oView']->assign( "pageinfo", $oPage->show(1) );
		$GLOBALS['oView']->assign( "s", $sHtml );
		$GLOBALS['oView']->assign( "actionlink", array("text"=>'清空查询条件',"href"=>url('gameinfo','task')) );
		$GLOBALS["oView"]->assign( "ur_here", "查看追号信息" );
		$oTask->assignSysInfo();
		$GLOBALS['oView']->display("gameinfo_task.html");
		EXIT;
	}



	/**
	 * 追号详情查看
	 * URL: ./index.php?controller=gameinfo&action=taskdetail
	 * @author SAUL
	 */
	function actionTaskDetail()
	{
		$aLocation[0]   = array( "title"=>'查看追号记录', "url"=>url('gameinfo','task') );
		$iTaskId        = isset($_GET["id"])&&!empty($_GET["id"]) ? model_projects::HighEnCode($_GET["id"],"DECODE") : 0;
		if( $iTaskId==0 )
		{
			sysMsg( '没有权限', 2, $aLocation );
		}
		/* @var $oUser model_user */
		$oUser = A::singleton( "model_user", $GLOBALS['aSysDbServer']['report'] );
		if( intval($_SESSION["usertype"])==2 )
		{//总代管理员
			$iUserId = $oUser->getTopProxyId( intval($_SESSION["userid"]), FALSE ); //获取总代
			if( $oUser->IsAdminSale( intval($_SESSION["userid"]) ) )
			{ //为销售
				$sTWhere = " AND T.`lvproxyid` IN (SELECT `topproxyid` FROM `useradminproxy` "
				."WHERE `adminid`='".intval($_SESSION["userid"])."')";
			}
			else
			{
				$sTWhere = " AND T.`lvtopid`='".$iUserId."'";
			}
		}
		else
		{
			$iUserId = intval( $_SESSION["userid"] );
			$sUserWhere = " AND (FIND_IN_SET('".$iUserId."',UT.`parenttree`)"
			." OR (UT.`userid`='".$iUserId."'))";
		}
		/* @var $oTask model_task */
		$oTask = A::singleton( "model_task", $GLOBALS['aSysDbServer']['report'] );
		$aTask = $oTask->getTaskInfo(""," AND T.`taskid`='".$iTaskId."'", $sUserWhere, $sTWhere, true);
		if( empty($aTask[0]) )
		{
			sysMsg('追号单不存在', 2, $aLocation );
		}
		if( intval($aTask[0]["userid"]) == intval($_SESSION["userid"]) )
		{
			$GLOBALS['oView']->assign("can", 1 ); //能够撤单
		}
		$oProject = A::singleton( "model_projects", $GLOBALS['aSysDbServer']['report'] );
		$aTask[0]['codes'] = $oProject->AddslasCode( $aTask[0]["codes"], $aTask[0]['methodid'] );
		$aTask[0]["codes"] = wordwrap( str_replace( array("|"),array(","), $aTask[0]['codes'] ),100,"<br/>" );
		$aTask[0]["taskid"] = model_projects::HighEnCode("T".$aTask[0]["beginissue"]."-".$aTask[0]["taskid"],"ENCODE");
		$aTaskDetail        = $oTask->taskdetailGetList( $iTaskId, $aTask[0]["lotteryid"] );
		foreach( $aTaskDetail as &$aDetail )
		{
			if( $aDetail["projectid"]>0 )
			{ //注单详情
				$aDetail["projectid"] = model_projects::HighEnCode("D".$aDetail["issue"]."-".$aDetail["projectid"], "ENCODE");
			}
		}
		if($aTask[0]['modes'] > 0)
		{
			$aTask[0]['modes'] = $GLOBALS['config']['modes'][$aTask[0]['modes']]['name'];
		}
		else
		{
			$aTask[0]['modes'] = '';
		}
		$GLOBALS["oView"]->assign( "task",          $aTask[0] );
		$GLOBALS['oView']->assign( "aTaskdetail",   $aTaskDetail );
		$GLOBALS['oView']->assign( "ur_here",       "查看追号详情");
		$oTask->assignSysInfo();
		$GLOBALS['oView']->display("gameinfo_taskdetail.html");
		EXIT;
	}



	/**
	 * 追号单撤单
	 * URL: ./index.php?controller=gameinfo&action=canceltask
	 * @author JAMES
	 */
	function actionCancelTask()
	{
		$sTaskNo      = !empty($_POST["id"]) ? $_POST["id"] : "";
		$aLocation[0] = array("title"=>'查看追号详情',"url"=>url('gameinfo', 'taskdetail', array('id'=>$sTaskNo)));
		$iTaskId      = !empty($sTaskNo) ? model_projects::HighEnCode($sTaskNo, "DECODE") : 0;
		if( $iTaskId == 0 )
		{
			sysMsg( '权限不足', 2, $aLocation );
		}
		$aId = !empty($_POST["taskid"]) ?$_POST["taskid"]: array();
		$oGame   = new model_gamemanage();
		$mResult = $oGame->cancelTask( intval($_SESSION["userid"]), $iTaskId, $aId );
		if( $mResult === TRUE )
		{
			sysMsg( '操作成功', 1, $aLocation );
		}
		else
		{
			sysMsg( $mResult, 2, $aLocation );
		}
	}
	
	
	/**
     * 获取指定组合的所有可能性
     * 
     * 例子：5选3
     * $aBaseArray = array('01','02','03','04','05');
     * ----getCombination($aBaseArray,3)
     * 1.初始化一个字符串：11100;--------1的个数表示需要选出的组合
     * 2.将1依次向后移动造成不同的01字符串，构成不同的组合，1全部移动到最后面，移动完成：00111.
     * 3.移动方法：每次遇到第一个10字符串时，将其变成01,在此子字符串前面的字符串进行倒序排列,后面的不变：形成一个不同的组合.
     *            如：11100->11010->10110->01110->11001->10101->01101->10011->01011->00111
     *            一共形成十个不同的组合:每一个01字符串对应一个组合---如11100对应组合01 02 03;01101对应组合02 03 05
     * 
     * 
     * @param  array $aBaseArray 基数数组
     * @param  int   $iSelectNum 选数
     * @author mark
     *
     */
    private function getCombination( $aBaseArray, $iSelectNum )
    {
        $iBaseNum = count($aBaseArray);
        if($iSelectNum > $iBaseNum)
        {
            return array();
        }
        if( $iSelectNum == 1 )
        {
            return $aBaseArray;
        }
        if( $iBaseNum == $iSelectNum )
        {
            return array(implode(',',$aBaseArray));
        }
        $sString = '';
        $sLastString = '';
        $sTempStr = '';
        $aResult = array();
        for ($i=0; $i<$iSelectNum; $i++)
        {
            $sString .='1';
            $sLastString .='1'; 
        }
        for ($j=0; $j<$iBaseNum-$iSelectNum; $j++)
        {
            $sString .='0';
        }
        for ($k=0; $k<$iSelectNum; $k++)
        {
            $sTempStr .= $aBaseArray[$k].',';
        }
        $aResult[] = substr($sTempStr,0,-1);
        $sTempStr = '';
        while (substr($sString, -$iSelectNum) != $sLastString)
        {
            $aString = explode('10',$sString,2);
            $aString[0] = $this->strOrder($aString[0], TRUE);
            $sString = $aString[0].'01'.$aString[1];
            for ($k=0; $k<$iBaseNum; $k++)
            {
                if( $sString{$k} == '1' )
                {
                    $sTempStr .= $aBaseArray[$k].',';
                }
            }
            $aResult[] = substr($sTempStr, 0, -1);
            $sTempStr = '';
        }
        return $aResult;
    }
    
    
	/**
	 * 获取基诺任选玩法奖金详情
	 * 
	 * @param array $aProjectCode 方案号码
	 * @param array $aCode 开奖号码
	 * @param array $aPrizelevelDesc 奖级描述
	 * @param int   $iSelNum 玩法最少选择的选择号码个数
	 * @param return array
	 * @author mark
	 *
	 */
	public function getKenoPrize($aProjectCode, $aCode, $aPrizelevelDesc, $prizelevel, $iSelNum)
	{
	    $aSameCode = array_intersect($aProjectCode, $aCode);//中奖号码
	    $aLevelCount = array(1=>1,2=>1,3=>2,4=>3,5=>3,6=>4,7=>5);//各个玩法奖级个数
	    $aLevelBonus = array();
	    foreach ($prizelevel as $aLevel)
	    {
	        $aLevelBonus[$aLevel['level']] = $aLevel['prize'];//获取各个奖级的奖金
	        $aLevelTimes[$aLevel['level']] = $aLevel['codetimes'];//获取各个奖级的奖金
	    }
	    $iInterCount = count($aSameCode);
	    $iCodeCount = count($aProjectCode);
	    $aMinNumCount = array(1=>1,2=>2,3=>2,4=>2,5=>3,6=>3,7=>4);//各个玩法最少中奖号码个数,7中0单独计算
	    $aRealPrize = array();
	    $iTotalCount = 0;
	    $fTotalPrize = 0.00;
	    if($iSelNum == 7 && in_array($iInterCount,array(0,1,2,3)))
	    {
	        $iLevel = 5;//任选七中零
	        $iBonusTimes = $iCodeCount > $iSelNum ? $this->GetCombinCount($iCodeCount-$iInterCount,$iSelNum): 1;
	        if($iBonusTimes > 0)
	        {
				$aDesc = explode(":",$aPrizelevelDesc[$iLevel]['name']);
	            $aRealPrize[$iLevel]["leveldesc"] = $aDesc[0];
	            $aRealPrize[$iLevel]['level'] = $iLevel;
	            $aRealPrize[$iLevel]['nocount'] = $iBonusTimes;
	            $aRealPrize[$iLevel]['singleprize'] = $aLevelBonus[$iLevel]/$aLevelTimes[$iLevel];
	            $aRealPrize[$iLevel]['codetimes'] = $aLevelTimes[$iLevel];
	            $aRealPrize[$iLevel]['prize'] = floatval( $aLevelBonus[$iLevel] * $iBonusTimes );
	            $iTotalCount += $iBonusTimes;
	            $fTotalPrize += $aRealPrize[$iLevel]['prize'];
	        }
	    }
	    else
	    {
	        for($i = $aMinNumCount[$iSelNum]; $i<=$iSelNum; $i++ )
	        {
	            $iLevel = $iSelNum+1-$i;//对应奖级
	            $iBonusTimes = $this->GetCombinCount($iInterCount,$i)*$this->GetCombinCount($iCodeCount-$iInterCount,$iSelNum-$i);//对应奖级中奖注数
	            if($iBonusTimes > 0)
	            {
	                $aDesc = explode(":",$aPrizelevelDesc[$iLevel]['name']);
	                if(count($aDesc) == 2)
	                {
	                    $aRealPrize[$iLevel]["leveldesc"] = $aDesc[0];
	                    $aRealPrize[$iLevel]["levelcodedesc"] = $aDesc[1];
	                }
	                else
	                {
	                    $aRealPrize[$iLevel]["leveldesc"] = $aDesc[0];
	                }
	                $aRealPrize[$iLevel]['level'] = $iLevel;
	                $aRealPrize[$iLevel]['nocount'] = $iBonusTimes;
	                $aRealPrize[$iLevel]['singleprize'] = $aLevelBonus[$iLevel]/$aLevelTimes[$iLevel];
	                $aRealPrize[$iLevel]['codetimes'] = $aLevelTimes[$iLevel];
	                $aRealPrize[$iLevel]['prize'] = floatval( $aLevelBonus[$iLevel] * $iBonusTimes );
	                $iTotalCount += $iBonusTimes;
	                $fTotalPrize += $aRealPrize[$iLevel]['prize'];
	            }
	        }
	    }
	    ksort($aRealPrize);
	    $aResult = array();
	    $aResult['samecode'] = implode( " ",$aSameCode);//方案号码与开奖号码相同的号码
	    $aResult['realprize'] = $aRealPrize;//真实奖金情况
	    $aResult['totalcount'] = $iTotalCount;//奖级个数
	    $aResult['totalprize'] = $fTotalPrize;
	    return $aResult;
	}
	
	/**
	 * 获取基诺趣味玩法奖金详情
	 * 
	 * @param array $aProjectCode 方案号码
	 * @param array $aCode 开奖号码
	 * @param array $aPrizelevelDesc 奖级描述
	 * @param return array
	 * @author floyd
	 *
	 */
	public function getKenoQWPrize($aProjectCode, $aCode, $aPrizelevelDesc, $prizelevel)
	{
	    $aSameCode = array_intersect($aProjectCode, $aCode);//中奖号码
		if(!$aSameCode)
			return array('samecode'=>'','realprize'=>array(),'totalcount'=>0,'totalprize'=>0);
		sort($aSameCode);
	    $aLevelBonus = array();
	    $aResult = array();
		$aResult['samecode'] = implode( " ",$aSameCode);//方案号码与开奖号码相同的号码
	    $aRealPrize = array();
	    $iTotalCount = 0;
	    $fTotalPrize = 0.00;
	    foreach ($prizelevel as $aLevel)
	    {
			if($aSameCode[0] == $aLevel['expandcode'])
			{
				$aLevel['nocount'] = 1;
				$aRealPrize[] = $aLevel;//真实奖金情况
				$iTotalCount++;//奖级个数
				$fTotalPrize += $aLevel['prize'];
			}
	    }
	    $aResult['realprize'] = $aRealPrize;//真实奖金情况
	    $aResult['totalcount'] = $iTotalCount;//奖级个数
	    $aResult['totalprize'] = $fTotalPrize;
	    return $aResult;
	}
	
	/**
	 * 获取基诺趣味型玩法开奖号码情况
	 * @param $aCode 开奖号码
	 * @return string
	 * @author mark
	 */
	public function getkenoquweicode($aCode = '')
	{
	    $iAddCount = 0;
	    $iBigCount = 0;//大号个数
	    $iSmallCount = 0;//小号个数
	    $iEevnCount = 0;//偶数号个数
	    $iOddCount = 0;//奇数号个数
	    foreach ($aCode as $iCode)
	    {
	        $iCode = intval($iCode);
	        $iAddCount += $iCode;
	        $iCode%2 == 0 ? $iEevnCount++ : $iOddCount++;
	        $iCode > 40 ? $iBigCount++ : $iSmallCount++;
	    }
	    if($iAddCount % 2 == 0)
	    {
	        $aFinalBonusCode['bjkl_heds'] ='双';
	    }
	    else
	    {
	        $aFinalBonusCode['bjkl_heds'] ='单';
	    }
	    $aFinalBonusCode['bjkl_hedx'] = '大';
	    if($iAddCount < 810)
	    {
	        $aFinalBonusCode['bjkl_hedx'] = '小';
	    }
	    if($iAddCount == 810)
	    {
	        $aFinalBonusCode['bjkl_hedx'] = '和';
	    }
	    $aFinalBonusCode['bjkl_sxpan'] = '上';
	    if($iBigCount > $iSmallCount)
	    {
	        $aFinalBonusCode['bjkl_sxpan'] = '下';//下盘
	    }
	    elseif($iBigCount == $iSmallCount)
	    {
	        $aFinalBonusCode['bjkl_sxpan'] = '中';//中盘
	    }
	    $aFinalBonusCode['bjkl_jopan'] = '奇';
	    if($iEevnCount > $iOddCount)
	    {
	        $aFinalBonusCode['bjkl_jopan'] = '偶';//偶盘
	    }
	    elseif($iEevnCount == $iOddCount)
	    {
	        $aFinalBonusCode['bjkl_jopan'] = '和';//和盘
	    }
	    $sNoHePan = '和值='.$iAddCount.'('.$aFinalBonusCode['bjkl_hedx'].','.$aFinalBonusCode['bjkl_heds'].')<br>';
	    $sNoHePan .= '盘面=('.$aFinalBonusCode['bjkl_sxpan'].','.$aFinalBonusCode['bjkl_jopan'].')';
	    return $sNoHePan;
	}
}
?>
