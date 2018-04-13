<?php
class Admin_RechargemangeController extends Fundcommon
{
// 	protected $_funds;
// 	protected $_uploadsession ;
	protected $_pagesize,$_page;
	protected $_exceptionQuery,$_queryFundWithdrawOrder,$_fundWithdraw,$_exceptionRefundDao,$_queryAllBankDao;
	protected $_aRiskType,$_aWithDrawStatus,$_aRechargeStatus,$_aAuitStatus,$_appStatus,$_aTypeArray,$_aSystem;
    protected $_aWithDrawStatusMessageDetail,$_aRefundStatusMessage,$_thWithDrawStatusMessage;
	protected $_param;

	public function init() {
		parent::init ();
		$this->_exceptionQuery 		=  new ExceptionQueryDao();
		$this->_queryFundWithdrawOrder = new QueryFundWithdrawOrderDao();
		$this->_fundWithdraw 		= new FundWithdraw();
		$this->_exceptionRefundDao	= new  ExceptionRefundDao();
		$this->_queryAllBankDao 	= new QueryAllBankDao();
		$this->_page 	 = 0;
		$this->_pagesize = 10;
// 		$this->_funds =  new Funds();
// 		$this->_uploadsession = new Zend_Session_Namespace('uploadsession') ;
// 		$this->_customersdraw = new  CustomersDraw();
// 		$this->_chargeQuery = new ChargeQueryDao();
// 		$this->_bankParamsSet =new BankParamsSetDao();
// 		$this->_chargeDao	=new ChargeDao();
		$this->_aRiskType = array('正常','IP风险','黑名单卡提现','大额提现','频繁提现','全部审核','风险沉默用户','沉默用户转帐','风险用户转帐');
		$this->_aWithDrawStatus = array('未处理','待复审','处理中','已拒绝','提现完全成功','提现失败','提现部分成功','已退款');
		$this->_aRechargeStatus = array('未处理','已加游戏币','已没收','待复审','处理中','已全部退款','退款失败','已部分退款');
		$this->_aAuitStatus = array(3=>'退款',8=>'加币',9=>'没收');
		$this->_appStatus = array(1=>'未处理',2=>'審核通過',3=>'審核未通過');
                                             $this->_usrLvlStatus=array('全部','总代','一级代理','二级代理');
		$this->_aSystem = array(0 => '全部', 1 => 'IOS', 2 => 'Android', 3 => '4.0');
                                             $this->_aWithdrawModeStatus = array(0=>'未分配',1=>'DP',2=>'通汇');
                                             $this->_aTypeArray = array('全部','网银汇款','快捷充值','财付通充值','人工充值','银联充值(DP)','支付宝','银联充值(汇潮)');
                                             $this->_isvip = array(0=>'否',1=>'是',2=>'是',3=>'是',4=>'是',5=>'是',6=>'是');
                                             $this->_aWithDrawStatusMessage = array('处理失败','处理成功','部分成功','未处理','处理中','无效订单');
                                             $this->_thWithDrawStatusMessage = array(1=>'系统处理中',2=>'银行处理中',3=>'成功',4=>'失败');
		$this->_aWithDrawStatusMessageDetail = array('出款失败，已退款','该笔订单已进入已完成列表','该笔订单有部分提款已经处理成功，部分已退款处理'
											,'MOW已接收到该笔订单，目前正在等待处理，请稍后再次获取状态'
												,'MOW已接收到该笔订单，目前正在处理中，请稍后再次获取状态'
												,'MOW未接收到该笔订单，请先咨询确认后再退款处理');
		$this->_aRefundStatusMessage = array('出款失败，已退款','该笔订单已进入已完成列表','该笔订单有部分提款已经处理成功，部分已退款处理'
												,'MOW已接收到该笔订单，目前正在等待处理，请稍后再次获取状态'
												,'MOW已接收到该笔订单，目前正在处理中，请稍后再次获取状态'
												,'MOW未接收到该笔订单，将退回未处理，请先咨询确认后再退款处理。'
                                                                                                ,9=>'MOW未接收到该笔订单，将退回未处理，请先咨询确认后再退款处理。');
	}

	//默认加载入口
	public function indexAction(){
		/* 银行卡绑定记录 */
            		$this->_param = getSecurityInput($this->_request->getParam("parma"));
		$aParam = array(
			'sv1' => 'displayRechargeDefaultPage',//显示列表页模板结构
			'dv1' => 'loadRechargeData',//加载异常充值列表页数据
			'disauit'=> 'displayRechargeOpratePage',//显示异常充值操作默认界面
			'opt1'=> 'rechageApprOperate',//初审操作
			'ex'  => 'downloadRechargeData',//下载异常充值列表数据
            'bypass' => 'rechargeBypassSettings',//充值分流設置
			'sv2' => 'rechageSettings', //充值返还手续费配置和充值上下限配置
			'sv3' => 'displayWithDrawPage', //显示风险提现列表数据
			'da'  => 'loadWithDrawData',//加载风险提现数据
			'displaywdoprate' => 'displayWithDrawOpratePage', //加载提现操作界面
			'displaywdoprate2' => 'displayWithDrawOpratePage2', //加载退款操作界面
			'wd'  => 'auditFundWithdraw',//风险提现后台一审
			'wd2' => 'auditFundWithdraw2',//风险提现后台二审
			'wd3' => 'auditRefund',//风险提现處理中退款
			'manualoprate' => 'manualWithdrawOperate',//人工处理
			'manualfinish' => 'manualFinishOperate',//人工完成
			'cns' => 'queryWithDrawNowStatus',//獲得目前狀態
			'cufs' => 'checkAndUpdateRefundStatus',//獲得目前狀態
			'exdraw' => 'downloadWithDrawData',//下载风险提现数据
			'opt2'=> 'unLockRechargeWork', //解锁异常充值操作
			'opt4'=> 'unLockWithDrawWork', //解锁风险提现数据操作
			'getlevel'=> 'getUserLevel', //获取用户层级
			'rechargeReAuit' => 'rechargeReAuit', //待复审审核
			 'aprluntreated' => 'displayApprealDefaultPage', //充值申诉未处理
             'aprlall' => 'displayApprealAllPage', //充值申诉全部
			 'aprlpassed' => 'displayApprealPassed',//充值申诉通過
             'aprlunpassed' => 'displayApprealUnPassed',//充值申诉未通過
             'aprl' => 'loadApprealData',
             'review' => 'displayReviewPage',
             'abcf'=>'appealBankConf', //充值申訴银行管理配置页面
             'pabcf'=>'phoneAppealBankConf',//移动端充值申訴银行管理配置页面
             'abtf'=>'appealTipConf',//充值申訴提示配置
             'ts' => 'saveTips',//全刪全存
             'at' => 'addTip',//新增申訴提示
             'qt' => 'queryTips',//查詢申訴提示
             'ar' => 'appealReview',//申訴審核
             'prop' => 'queryAppealCountsByStatus',//查詢未處理筆數
             'chr' => 'checkHasReview',//檢查是否重複審核
             'exdwl' => 'downloadAppealData',//下载充值申訴数据
             'whitelist' => 'queryWhiteList' ,//充值分流白名單查詢
             'deleteBypassWhiteListData' => 'deleteBypassWhiteListData',//充值分流白名單刪除
             'saveItem' => 'saveItem',//新增充值分流白名單
             'saveBypassCfg' => 'saveBypassCfg'//充值分流設定保存
        );
        
		if(array_key_exists($this->_param,$aParam) && array_key_exists($this->_param, $this->_aAclArray)){
				$this->$aParam[$this->_param]($this->_request);
				exit;
		}elseif(isAjaxRequest()){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102144')));
			exit;
		}else {
			foreach ($this->_aAclArray as $key=>$value){
				if(array_key_exists($key,$aParam)){
					$this->$aParam[$key]($this->_request);
					exit;
				}
			}
			$this->_redirect('/admin/Rechargemange/');
		}
	}
	//====================================异常充值=============================================================
	//加载异常充值默认页面
	public function displayRechargeDefaultPage(){
		$this->view->bankArray = $this->_bankIcoArray;
		$this->view->aRechargeStatus = $this->_aRechargeStatus;
		$this->view->aAuitStatus = $this->_aAuitStatus;
		$this->view->title = '异常充值处理';
		$this->view->display ( '/admin/funds/recharge/exception/viewchager.tpl' );
	}
	
	public function displayApprealDefaultPage(){
		$this->view->appStatus = $this->_appStatus;
		$this->view->title = '充值申诉处理 - 未处理';
		$this->view->display ( '/admin/funds/recharge/appreal/recharge-appreal-untreated.tpl' );
	}
        
                    public function displayApprealAllPage(){
                           $this->view->appStatus = $this->_appStatus;
                           $this->view->title = '充值申诉处理 - 全部';
                           $this->view->display ( '/admin/funds/recharge/appreal/recharge-appreal-all.tpl' );
                    }
                 
                    public function displayApprealPassed(){
                           $this->view->appStatus = $this->_appStatus;
                           $this->view->title = '充值申诉处理 - 通过';
                           $this->view->display ( '/admin/funds/recharge/appreal/recharge-appreal-passed.tpl' );
                    }
                 
                    public function displayApprealUnPassed(){
                           $this->view->appStatus = $this->_appStatus;
                           $this->view->title = '充值申诉处理 - 未通过 ';
                           $this->view->display ( '/admin/funds/recharge/appreal/recharge-appreal-unpassed.tpl' );
                    }
                    
                    //充值分流設定保存
                    public function saveBypassCfg(){
                        $dpId = getSecurityInput($this->_request->getPost("dpId",0)) ;
                        $thId = getSecurityInput($this->_request->getPost("thId",0)) ;
						$hbId = getSecurityInput($this->_request->getPost("hbId",0)) ;
                        $dpSwitchStatus = getSecurityInput($this->_request->getPost("dpSwitchStatus","")) ;
                        $thSwitchStatus = getSecurityInput($this->_request->getPost("thSwitchStatus","")) ;
						$hbSwitchStatus = getSecurityInput($this->_request->getPost("hbSwitchStatus","")) ;
                        $dpLowLimit = getSecurityInput($this->_request->getPost("dpLowLimit",0)) ;
                        $dpUpLimit = getSecurityInput($this->_request->getPost("dpUpLimit",0)) ;
                        $dpDailyLimit = getSecurityInput($this->_request->getPost("dpDailyLimit")) ;
                        $thLowLimit = getSecurityInput($this->_request->getPost("thLowLimit",0)) ;
                        $thUpLimit = getSecurityInput($this->_request->getPost("thUpLimit",0)) ;
                        $thDailyLimit = getSecurityInput($this->_request->getPost("thDailyLimit")) ;
						$hbLowLimit = getSecurityInput($this->_request->getPost("hbLowLimit",0)) ;
                        $hbUpLimit = getSecurityInput($this->_request->getPost("hbUpLimit",0)) ;
                        $hbDailyLimit = getSecurityInput($this->_request->getPost("hbDailyLimit")) ;
                        $data = array();
                        $dpAry = array();
                        $thAry = array();
						$hbAry = array();
						
                        //----------------------  APP銀聯 ----------------------------------------------
						$dpForUnipayId = getSecurityInput($this->_request->getPost("dpForUnipayId",0)) ;
                        $ecpssForUnipayId = getSecurityInput($this->_request->getPost("ecpssForUnipayId",0)) ;
                        $dpForUnipaySwitchStatus = getSecurityInput($this->_request->getPost("dpForUnipaySwitchStatus","")) ;
                        $ecpssForUnipaySwitchStatus = getSecurityInput($this->_request->getPost("ecpssForUnipaySwitchStatus","")) ;
                        $dpForUnipayLowLimit = getSecurityInput($this->_request->getPost("dpForUnipayLowLimit",0)) ;
                        $dpForUnipayUpLimit = getSecurityInput($this->_request->getPost("dpForUnipayUpLimit",0)) ;
                        $dpForUnipayDailyLimit = getSecurityInput($this->_request->getPost("dpForUnipayDailyLimit")) ;
                        $ecpssForUnipayLowLimit = getSecurityInput($this->_request->getPost("ecpssForUnipayLowLimit",0)) ;
                        $ecpssForUnipayUpLimit = getSecurityInput($this->_request->getPost("ecpssForUnipayUpLimit",0)) ;
                        $ecpssForUnipayDailyLimit = getSecurityInput($this->_request->getPost("ecpssForUnipayDailyLimit")) ;
                        $data = array();
                        $dpForUnipayAry = array();
                        $ecpssForUnipayAry = array();
						//----------------------  APP銀聯 ----------------------------------------------
						
                        //----------------------  web微信 ----------------------------------------------
                        $dpForWechatId = getSecurityInput($this->_request->getPost("dpForWechatId",0)) ;
                        $worthForWechatId = getSecurityInput($this->_request->getPost("worthForWechatId",0)) ;
                        $dpForWechatSwitchStatus = getSecurityInput($this->_request->getPost("dpForWechatSwitchStatus","")) ;
                        $worthForWechatSwitchStatus = getSecurityInput($this->_request->getPost("worthForWechatSwitchStatus","")) ;
                        $dpForWechatLowLimit = getSecurityInput($this->_request->getPost("dpForWechatLowLimit",0)) ;
                        $dpForWechatUpLimit = getSecurityInput($this->_request->getPost("dpForWechatUpLimit",0)) ;
                        $dpForWechatDailyLimit = getSecurityInput($this->_request->getPost("dpForWechatDailyLimit")) ;
                        $worthForWechatLowLimit = getSecurityInput($this->_request->getPost("worthForWechatLowLimit",0)) ;
                        $worthForWechatUpLimit = getSecurityInput($this->_request->getPost("worthForWechatUpLimit",0)) ;
                        $worthForWechatDailyLimit = getSecurityInput($this->_request->getPost("worthForWechatDailyLimit")) ;
                        $data = array();
                        $dpForWechatAry = array();
                        $worthForWechatAry = array();
                        //----------------------  web微信 ----------------------------------------------

						$modatas = array();
                        $modatas['bypassList'] = array();
                        
						
						
						$dpAry['id'] = $dpId;
                        $dpAry['singleLowlimit'] = $dpLowLimit;
                        $dpAry['singleUplimit'] = $dpUpLimit;
                        $dpAry['dailyUplimit'] = $dpDailyLimit;
                        $dpAry['isOpen'] = $dpSwitchStatus;
                        
                        $thAry['id'] = $thId;
                        $thAry['singleLowlimit'] = $thLowLimit;
                        $thAry['singleUplimit'] = $thUpLimit;
                        $thAry['dailyUplimit'] = $thDailyLimit;
                        $thAry['isOpen'] = $thSwitchStatus;
						
						$hbAry['id'] = $hbId;
                        $hbAry['singleLowlimit'] = $hbLowLimit;
                        $hbAry['singleUplimit'] = $hbUpLimit;
                        $hbAry['dailyUplimit'] = $hbDailyLimit;
                        $hbAry['isOpen'] = $hbSwitchStatus;
                        
						
						//----------------------  APP銀聯 ----------------------------------------------
						$dpForUnipayAry['id'] = $dpForUnipayId;
                        $dpForUnipayAry['singleLowlimit'] = $dpForUnipayLowLimit;
                        $dpForUnipayAry['singleUplimit'] = $dpForUnipayUpLimit;
                        $dpForUnipayAry['dailyUplimit'] = $dpForUnipayDailyLimit;
                        $dpForUnipayAry['isOpen'] = $dpForUnipaySwitchStatus;
						
						$ecpssForUnipayAry['id'] = $ecpssForUnipayId;
                        $ecpssForUnipayAry['singleLowlimit'] = $ecpssForUnipayLowLimit;
                        $ecpssForUnipayAry['singleUplimit'] = $ecpssForUnipayUpLimit;
                        $ecpssForUnipayAry['dailyUplimit'] = $ecpssForUnipayDailyLimit;
                        $ecpssForUnipayAry['isOpen'] = $ecpssForUnipaySwitchStatus;
						
						
						//----------------------  APP銀聯 ----------------------------------------------
						//----------------------  web微信 ----------------------------------------------
                        $dpForWechatAry['id'] = $dpForWechatId;
                        $dpForWechatAry['singleLowlimit'] = $dpForWechatLowLimit;
                        $dpForWechatAry['singleUplimit'] = $dpForWechatUpLimit;
                        $dpForWechatAry['dailyUplimit'] = $dpForWechatDailyLimit;
                        $dpForWechatAry['isOpen'] = $dpForWechatSwitchStatus;
                        
                        $worthForWechatAry['id'] = $worthForWechatId;
                        $worthForWechatAry['singleLowlimit'] = $worthForWechatLowLimit;
                        $worthForWechatAry['singleUplimit'] = $worthForWechatUpLimit;
                        $worthForWechatAry['dailyUplimit'] = $worthForWechatDailyLimit;
                        $worthForWechatAry['isOpen'] = $worthForWechatSwitchStatus;
                        
                        
                        //----------------------  web微信 ----------------------------------------------

						$modatas['bypassList'][0] = $dpAry;
                        $modatas['bypassList'][1] = $thAry;
						$modatas['bypassList'][2] = $dpForUnipayAry;
						$modatas['bypassList'][3] = $ecpssForUnipayAry;
						$modatas['bypassList'][4] = $hbAry;
                        $modatas['bypassList'][5] = $dpForWechatAry;
                        $modatas['bypassList'][6] = $worthForWechatAry;


                        $data["param"]["bypassList"] = $modatas['bypassList'];
                        $aUrl['fundadmin'] = 'saveBypassCfg';
                        //die(var_dump($data));
                        $res = $this->_clientobject->inRequestV2($data, $aUrl);
                        
                        
                    }
                     //充值分流設置
                    public function rechargeBypassSettings(){
						$tab->_param = getSecurityInput($this->_request->getParam("tab"));
						$this->view->tab = $tab->_param;
                        $data['param']['type'] = '0';  //type='0' 充值
                        $aUrl['fundadmin'] = 'queryBypassConfigByType';
                        $res = $this->_clientobject->inRequestV2($data, $aUrl);

                        foreach ( $res["body"]["result"]["bypassCfgs"] as $recorder){
                            if($recorder["chargeWaySet"] == 2 && $recorder["agency"] == 0){  //DP
                                $this->view->dpSetting = $recorder;
                                $dpIsOver = 'N';
                                if($recorder["dailyCharge"] >= $recorder["dailyUplimit"]){
                                          $dpIsOver = 'Y';                               
                                }
                                $this->view->dpIsOver = $dpIsOver;
                            }else if($recorder["chargeWaySet"] == 2 &&$recorder["agency"] == 1){ //通匯
                                $this->view->thSetting = $recorder;
                                $thIsOver = 'N';
                                if($recorder["dailyCharge"] >= $recorder["dailyUplimit"]){
                                         $thIsOver = 'Y';                               
                                }
                                $this->view->thIsOver = $thIsOver;
                            }else if($recorder["chargeWaySet"] == 2 &&$recorder["agency"] == 4){ //匯博
                                $this->view->hbSetting = $recorder;
                                $hbIsOver = 'N';
                                if($recorder["dailyCharge"] >= $recorder["dailyUplimit"]){
                                         $hbIsOver = 'Y';                               
                                }
                                $this->view->hbIsOver = $hbIsOver;
                            }else if($recorder["chargeWaySet"] == 5 &&$recorder["agency"] == 0){ //APP銀聯DP
                                $this->view->appDpSetting = $recorder;
                                $appDpIsOver = 'N';
                                if($recorder["dailyCharge"] >= $recorder["dailyUplimit"]){
                                         $appDpIsOver = 'Y';                               
                                }
                                $this->view->appDpIsOver = $appDpIsOver;
                            }else if($recorder["chargeWaySet"] == 5 &&$recorder["agency"] == 3){ //APP銀聯匯潮
                                $this->view->appECPSSSetting = $recorder;
                                $appECPSSIsOver = 'N';
                                if($recorder["dailyCharge"] >= $recorder["dailyUplimit"]){
                                         $appECPSSIsOver = 'Y';                               
                                }
                                $this->view->appECPSSIsOver = $appECPSSIsOver;
                            }else if($recorder["chargeWaySet"] == 7 &&$recorder["agency"] == 0){ //微信DP
                                $this->view->wechatDpSetting = $recorder;
                                $wechatDpIsOver = 'N';
                                if($recorder["dailyCharge"] >= $recorder["dailyUplimit"]){
                                         $wechatDpIsOver = 'Y';                               
                                }
                                $this->view->wechatDpIsOver = $wechatDpIsOver;
                            }else if($recorder["chargeWaySet"] == 7 &&$recorder["agency"] == 7){ //微信華勢
                                $this->view->wechatWorthSetting = $recorder;
                                $wechatWorthIsOver = 'N';
                                if($recorder["dailyCharge"] >= $recorder["dailyUplimit"]){
                                         $wechatWorthIsOver = 'Y';                             
                                }
                                $this->view->wechatWorthIsOver = $wechatWorthIsOver;
                            }
                        }
                        $this->view->display ( '/admin/funds/recharge/bypassSetting.tpl' );
                    }
                    
                    public function queryWhiteList(){
                        $chargeChannel = getSecurityInput($this->_request->get("chargeChannel",'')) ;
						$chargeWaySet = getSecurityInput($this->_request->get("chargeWaySet",'')) ;
                        $pageNo = getSecurityInput($this->_request->get("pageNo",'')) ;
                        $aUrl['fundadmin'] = 'queryBypassWhiteList';
                        //chargeChannel 0:DP 1:通匯 2:匯潮
						//chargeWaySet  0:快捷充值 1:APP銀聯
                        $data['param']['chargeChannel'] = $chargeChannel;
						$data['param']['chargeWaySet'] = $chargeWaySet;
                        $data['param']['pageNo'] = $pageNo;
                        $res = $this->_clientobject->inRequestV2($data, $aUrl);
                        $delPermission = 'N';
                        $aclAry =  $this->_sessionlogin->info['acls'];
                        if(in_array("FUND_RECHARGE_DELETE_WHITELIST",$aclAry)){
                            $delPermission = 'Y';      
                        }
                        $modata = array();
                        $modatas = array();
                        $modatas['text'] = array();
                        $modatas['count'] = array();
                        $recordNum = 0;
                        foreach ( $res["body"]["result"]["bypassList"] as $recorder){
                            $modata["id"] = $recorder["id"] ;
                            $modata["userAccount"] = $recorder["userAccount"] ? $recorder["userAccount"] : '';
                            $modata["createTime"] = $recorder["createTime"] ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder["createTime"])) : '' ;
                            $modata["memo"] = $recorder["memo"] ? $recorder["memo"] : '';
                            array_push($modatas['text'],$modata) ;
                        }
                        
                        if(isset($res["body"]["pager"]["total"])){//總筆數
                            $recordNum = $res["body"]["pager"]["total"];
                        }
                        array_push($modatas['count'],array("recordNum"=>$recordNum));
                        $modatas['delPermission'] = $delPermission;
                        header ( 'Content-Type: application/json;charset=utf-8' );
                        echo json_encode($modatas) ;
                    }
                    
                    public function deleteBypassWhiteListData(){
                        $deleteId = $this->_request->getPost('deleteId','');
                        $data['param'][deleteId] = $deleteId;
                        $aUrl['fundadmin'] = 'deleteBypassWhiteListData';
                        $res = $this->_clientobject->inRequestV2($data, $aUrl);
                    }
                    
                    public function saveItem(){
                        $type = getSecurityInput($this->_request->getPost("saveType",0)) ;
						$chargeWaySet = getSecurityInput($this->_request->getPost("saveChargeWaySet",0)) ;
                        $account = getSecurityInput($this->_request->getPost("account","")) ;
                        $memo = getSecurityInput($this->_request->getPost("memo","")) ;
                        $itemData = array();
                        $itemData["chargeChannel"] = $type;
                        $itemData["account"] = strtolower($account);
                        $itemData["memo"] = $memo;
						$itemData["chargeWaySet"] = $chargeWaySet;
                        
                        //檢查是否已添加過
                        $cData=array();
                        $cData["param"] = $itemData;
                        $url['fundadmin'] = 'isAccountExist';
                        $cRes= $this->_clientobject->inRequestV2($cData, $url);
                        if($cRes['body']['result']['isExist'] == 'Y'){
                            echo $cRes['body']['result']['isExist'];
                            exit;
                        }
                          
                     
                        $data=array();
                        $data["param"] = $itemData;
                        $aUrl['fundadmin'] = 'saveItem';
                        $res = $this->_clientobject->inRequestV2($data, $aUrl);
                        $this->_redirect('/admin/Rechargemange/index?parma=abtf');
                    }
                                            
                    public function downloadAppealData(){
                        $Title = getSecurityInput($this->_request->get("Title",0)) ;
                        $fileName = '充值申诉';
                        $aTitle = array(
                            'appealSn' => '申请单号',
                            'account' => '用户名',
                            'userlvl' => '用户组',
                            'appealAmt' => '金额' ,
                            'appealTime' => '申诉发起时间' ,
                            'isvip' => '是否VIP' ,
                            'status' => '状态' ,
                            'type' => '审核类型',
                            'reviewer' => '审核人',
                            'reviewStartTime' => '审核开始时间' ,
                            'reviewEndTime' => '审核结束时间' ,
                            'memo' => '备注' 
                        );
                        
                        $intArray = array('bankNumber');
                        $data=array();
                        //检查权限
                        if(!isset($this->_aAclArray[$this->_param][$Title])){
                            $this->_redirect('/admin/Rechargemange/index?parma=aprlall');
                        }
                        
                        $title = getSecurityInput($this->_request->getPost("Title",0)) ;
                        $appealSn = getSecurityInput($this->_request->getPost("movnumber",0)) ;
                        $account = getSecurityInput($this->_request->getPost("account",0)) ;
                        $userlvl = getSecurityInput($this->_request->getPost("userlvl")) ;
                        $Daozhangtime1 = getSecurityInput($this->_request->getPost("Daozhangtime1",0)) ;
                        $Daozhangtime2 = getSecurityInput($this->_request->getPost("Daozhangtime2",0)) ;
                        $requestMoney1 = getSecurityInput($this->_request->getPost("requestMoney1",0)) ;
                        $requestMoney2 = getSecurityInput($this->_request->getPost("requestMoney2",0)) ;
                        $isvip = getSecurityInput($this->_request->getPost("isvip")) ;
                        $statusType = getSecurityInput($this->_request->getPost('statusType',''));

                        if($appealSn){
                            $data["param"]["appealSn"] = $appealSn ;
                        }
                        if($account){
                            $data["param"]["account"] = $account ;
                        }

                        if( (string)$userlvl  !=  "" ){
                            $data["param"]["userlvl"]= intval($userlvl) > -1?$userlvl:null;
                        }
                        
                       
                        if($Daozhangtime1){
                            $data["param"]["appealTimeFrom"] = getQueryStartTime($Daozhangtime1) ;
                        }
                        if($Daozhangtime2){
                            $data["param"]["appealTimeTo"] = getQueryEndTime($Daozhangtime2) ;
                        }
                        if($requestMoney1){
                            $data["param"]["refundAmtFrom"] = floatval($requestMoney1)*$this->_moneyUnit ;
                        }
                        if($requestMoney2){
                            $data["param"]["refundAmtTo"]   = floatval($requestMoney2)*$this->_moneyUnit  ;
                        }   
                        if($isvip !=''){
                            $data["param"]["isvip"]  =  intval($isvip) ;
                        }
                        if(in_array($title, array('0','1','2','3'))){
                            $data["param"]['title'] = $title;
                        }
                        if(in_array($statusType,_appStatus)){
                            $data["param"]['status'] = $statusType;
                        }
                        
                        if($statusType){
                            $status = $statusType;
                        } else {
                            $status = $this->_request->get("status");
                        }
                        
                        $aUri['fundadmin'] = 'getRechargeAppreals';
                        $modata = array();
                        $aContent = array();
                        $modatas = array();
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
                                    $modata["appealSn"] = $recorder["appealSn"] ? $recorder["appealSn"] : '' ;//申请单号
                                    $modata["account"] = $recorder["account"] ? $recorder["account"] : '';
                                    $modata["userlvl"] = $this->getUserType($recorder["userlvl"]) ;
                                    $modata["appealAmt"] = getMoneyFomat($recorder["appealAmt"]/$this->_moneyUnit,2) ;
                                    $modata["appealTime"] = $recorder["appealTime"] ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder["appealTime"])) : '' ;//银行到帐时间
                                    $modata["isvip"] = isset($this->_isvip[$recorder["isvip"]]) ? $this->_isvip[$recorder["isvip"]] : '';
                                    $modata["status"]  = isset($this->_appStatus[$recorder["status"] ]) ? $this->_appStatus[$recorder["status"] ] : '';
                                    $modata["type"] ='充值申诉';
                                    $modata["reviewer"] = $recorder["reviewer"] ?$recorder["reviewer"]:'';
                                    $modata["reviewStartTime"] = $recorder["reviewStartTime"] ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder["reviewStartTime"])) : '' ;
                                    $modata["reviewEndTime"] = $recorder["reviewEndTime"] ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder["reviewEndTime"])) : '' ;
                                    $modata["memo"] = $recorder["reviewMemo"] ? $recorder["reviewMemo"] : '' ;
                                    $aContent[$key] = $modata;
                                }
                                $totalPage = ceil($rsr['pager']['total']/$querySize);
                                $total = $rsr['pager']['total'];
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
                       
                    public function checkHasReview(){
                        $appealSn = getSecurityInput($this->_request->get("appealSn",''));
                        //取redis 判斷是否有人在審核
                        $key = "appealReview:".$appealSn;
                        $checkUser = $this->redis_client->get($key);
                        
                        $reviewer = 	$this->_sessionlogin->info['account'];
                        
                        if(null == $checkUser || $checkUser == $reviewer) {
                            $data['isCheck'] = 0;
                            $checkUser = $reviewer;
                            //若無人審核中則寫入redis
                            $this->redis_client->set($key, $reviewer, 3*60);
                        } else {
                            $data['isCheck'] = 1;
                            $data['checkUser'] = $checkUser;
                        }
                        echo Zend_Json::encode($data);
                    }
                    
                    public function displayReviewPage(){
                        $appealSn = getSecurityInput($this->_request->get("appealSn",''));
                        $type = getSecurityInput($this->_request->get("type",''));
                        $isReview = 'N';
                        $title_text = '查看';
                        if($type == 'aprluntreated' ){
                            $isReview='Y';
                            $title_text = '审核';
                        }
                        $this->view->assign('type',$type);
                        $this->view->assign('appealParam',$appealParam);
                        $this->view->assign('isReview', $isReview);
                        $this->view->assign('title_text',$title_text);
                        $this->view->title = '充值申诉处理';

                        if($appealSn){
                            $data["param"]["appealSn"] = $appealSn ;
                        }
                        $aUrl['fundadmin'] = 'getRechargeAppealReview';
                        $res1 = $this->_clientobject->inRequestV2($data, $aUrl);  
                                         
                        $fundChargeAppealVO = $res1['body']['result']['fundChargeAppealVO'];
                        $this->view->assign('userId',$fundChargeAppealVO['userId']);
                        $this->view->assign('appealSn',$fundChargeAppealVO['appealSn']);
                        $this->view->assign('chargeMemo',$fundChargeAppealVO['chargeMemo']);
                        $this->view->assign('argueAcct',$fundChargeAppealVO['argueAcct']);
                        $this->view->assign('chargeAmt',
                                getMoneyFomat($fundChargeAppealVO['chargeAmt']/$this->_moneyUnit,2));
                        //isset($value['payBankId']) ? $this->_bankIcoArray[$value['payBankId']]['name'] : '';
                        $this->view->assign('bankName',isset($fundChargeAppealVO['bankId'])?$this->_bankIcoArray[$fundChargeAppealVO['bankId']]['name']:'');
                        $this->view->assign('bankId',isset($fundChargeAppealVO['bankId'])?intval($fundChargeAppealVO['bankId']):0);
                    
                        $this->view->assign('vipLvl',isset($fundChargeAppealVO['vipLvl'])?$fundChargeAppealVO['vipLvl']:'');
                         
                        
                        if(intval($fundChargeAppealVO['bankId']) == 31){ 
                            $this->view->assign('chargeUserName',isset($fundChargeAppealVO['tenpayName'])?$fundChargeAppealVO['tenpayName']:'');
                            $this->view->assign('bankCardNumber',isset($fundChargeAppealVO['tenpayAccount'])?$fundChargeAppealVO['tenpayAccount']:'');
                        }else{
                            $this->view->assign('chargeUserName',isset($fundChargeAppealVO['chargeUserName'])?$fundChargeAppealVO['chargeUserName']:'');
                            $this->view->assign('bankCardNumber',isset($fundChargeAppealVO['bankCardNumber'])?$fundChargeAppealVO['bankCardNumber']:'');
                        }
                        $this->view->assign('electronicNumber',isset($fundChargeAppealVO['electronicNumber'])?$fundChargeAppealVO['electronicNumber']:'');
                        $this->view->assign('transactionNum',isset($fundChargeAppealVO['transactionNum'])?$fundChargeAppealVO['transactionNum']:'');
                         
                        $urlAry = json_decode($fundChargeAppealVO['uploadImages']);
                        $this->view->assign('urlAry',$urlAry);
                        $this->view->assign('realChargeAmt',getMoneyFomat($fundChargeAppealVO['chargeAmt']/$this->_moneyUnit,2));
                        $hasChargeData = 'N';                                                             
                        $this->view->assign('chargeTime',$fundChargeAppealVO['chargeTime']?date('Y-m-d H:i:s',getSrtTimeByLong($fundChargeAppealVO["chargeTime"])):'');
                        
                        if($fundChargeOrder  = $res1['body']['result']['fundChargeOrder']) {
                            $hasChargeData = 'Y';
                            $this->view->assign('topVip',$fundChargeOrder['topVip']);
                            $this->view->assign('sn',$fundChargeOrder['sn']);
                            $this->view->assign('userAct',$fundChargeOrder['userAct']);
                            $this->view->assign('applyTime',$fundChargeOrder['applyTime']?
                                    date('Y-m-d H:i:s',getSrtTimeByLong($fundChargeOrder["applyTime"])):'');
                            $this->view->assign('chargeTime',$fundChargeOrder['chargeTime']?
                                    date('Y-m-d H:i:s',getSrtTimeByLong($fundChargeOrder["chargeTime"])):'');
                            $this->view->assign('applyTime',$fundChargeOrder['applyTime']?
                                    date('Y-m-d H:i:s',getSrtTimeByLong($fundChargeOrder["applyTime"])) : '' );
									
							if($fundChargeOrder['depositMode'] === 5){ //5:銀聯
								if($fundChargeOrder['chargeMode']==1){ //1:DP
									//用戶申訴資料的申請渠道
									$this->view->assign('depositeMode', isset($fundChargeAppealVO['depositeMode']) ? $this->_aTypeArray[$fundChargeAppealVO['depositeMode']] : '');
									//平台訂單訊息的申請渠道
									$this->view->assign('depositMode', isset($fundChargeOrder['depositMode']) ? $this->_aTypeArray[$fundChargeOrder['depositMode']] : '');
								}if($fundChargeOrder['chargeMode']==3){ //3:匯潮
									$this->view->assign('depositeMode', isset($fundChargeAppealVO['depositeMode']) ? $this->_aTypeArray[7] : '');
									$this->view->assign('depositMode', isset($fundChargeOrder['depositMode']) ? $this->_aTypeArray[7] : '');
								}
							}else{
								$this->view->assign('depositMode', isset($fundChargeOrder['depositMode']) ? $this->_aTypeArray[$fundChargeOrder['depositMode']] : '');
							}
                            $this->view->assign('platfom', isset($fundChargeOrder['platfom']) ? $this->_aSystem[$fundChargeOrder['platfom']]:'');
                            $this->view->assign('accountHolder', $fundChargeOrder['revCard']['accountHolder']);
                            $this->view->assign('memo',$fundChargeOrder['memo']);
                            $this->view->assign('bankCardNo',$fundChargeOrder['revCard']['bankCardNo']); 
                            $this->view->assign('payBankId',isset($fundChargeOrder['payBank'])?$this->_bankIcoArray[$fundChargeOrder['payBank']['id']]['name']:'');
                            
                        }
                        
                        $this->view->assign('hasChargeData',$hasChargeData); 
                        if('Y' == $isReview){
                            $info = array();
                            $info['param']['tips']['tipsModel'] = '0' ;
                            //$info['param']['tips']['tipsGroupa'] = '0' ;
                            $info['param']['tips']['tipsGroupb'] = '1';

                            $aUrl['fundadmin'] = 'searchTips';
                            $tips = $this->_clientobject->inRequestV2($info, $aUrl);
                            $this->view->assign('tips',$tips["body"]["result"]["tipsList"]); 
                        } else {
                            $this->view->assign('appealStatus',isset($this->_appStatus[$fundChargeAppealVO["appealStatus"] ]) ? $this->_appStatus[$fundChargeAppealVO["appealStatus"]] : '');
                            $this->view->assign('appealMemo',$fundChargeAppealVO['appealMemo']);
                            $this->view->assign('reviewMemo',$fundChargeAppealVO['reviewMemo']);
                        }
                           
                        $this->view->display ( '/admin/funds/recharge/appreal/recharge-appreal-audit.tpl' );
                    }
                 
                    public function loadApprealData(){
                            $data = array();
                            $title = getSecurityInput($this->_request->getPost("Title",0)) ;
                            $movnumber = getSecurityInput($this->_request->getPost("movnumber",0)) ;
                            $account = getSecurityInput($this->_request->getPost("account",0)) ;
                            $userlvl = getSecurityInput($this->_request->getPost("userlvl",0)) ;
                            $vipLvl = getSecurityInput($this->_request->getPost("vipLvl",0)) ;
                            $Daozhangtime1 = getSecurityInput($this->_request->getPost("Daozhangtime1",0)) ;
                            $Daozhangtime2 = getSecurityInput($this->_request->getPost("Daozhangtime2",0)) ;
                            $requestMoney1 = getSecurityInput($this->_request->getPost("requestMoney1",0)) ;
                            $requestMoney2 = getSecurityInput($this->_request->getPost("requestMoney2",0)) ;
                            $isvip = getSecurityInput($this->_request->getPost("isvip",0)) ;
                            $statusType = getSecurityInput($this->_request->getPost('statusType',''));
                            
                            if($movnumber){
                                $data["param"]["appealSn"] = $movnumber ;
                            }
                            if($account){
                                $data["param"]["account"] = $account ;
                            }
                            
                            if( (string)$userlvl  !=  "" ){
                                $data["param"]["userlvl"]= intval($userlvl) > -1?$userlvl:null;
                            }
                            
                            $data["param"]["vipLvl"]= $vipLvl;
                                                        
                            if($Daozhangtime1){
                                $data["param"]["appealTimeFrom"] = getQueryStartTime($Daozhangtime1) ;
                            }
                            if($Daozhangtime2){
                                $data["param"]["appealTimeTo"] = getQueryEndTime($Daozhangtime2) ;
                            }
                            if($requestMoney1){
                                $data["param"]["refundAmtFrom"] = floatval($requestMoney1)*$this->_moneyUnit ;
                            }
                            if($requestMoney2){
                                $data["param"]["refundAmtTo"]   = floatval($requestMoney2)*$this->_moneyUnit  ;
                            }   
                            if($isvip !=''){
                                $data["param"]["isvip"]  =  intval($isvip) ;
                            }
                            if(in_array($title, array('0','1','2','3'))){
                                $data["param"]['title'] = $title;
                            }
                            if(in_array($statusType,$this->_appStatus)){
                                $data["param"]['status'] = $statusType;
                            }
                            
                            $auditPermission = 'N';                  
                            if($statusType){
                                $status = $statusType;
                                
                            } else {
                                $status = $this->_request->get("status");
                                if($status == 1){
                                    $aclAry =  $this->_sessionlogin->info['acls'];
                                    if(in_array("FUND_APPEAL_REVIEW",$aclAry)){
                                        $auditPermission = 'Y';      
                                    }
                                }
                            }
                            
                            $modata = array();
                            $modatas = array();
                            $recordNum = 0;
                            $modatas['text'] = array();
                            $modatas['count'] = array();
                            $this->_page = intval(getSecurityInput($this->_request->getPost("page",$this->_page)));
                            $this->_pagesize = intval(getSecurityInput($this->_request->getPost("perPageNum",$this->_pagesize)));
                            $start = $this->_page+1;
                            $data["param"]["status"] = $status;
                            $data["pager"]["startNo"] = $this->_page*$this->_pagesize+1; //启始值
                            $data["pager"]["endNo"] = $start*$this->_pagesize; //结束值
                            //送出request到Java
                            $aUrl['fundadmin'] = 'getRechargeAppreals';
                            $res1 = $this->_clientobject->inRequestV2($data, $aUrl);
                            
                            foreach ( $res1["body"]["result"] as $recorder){
                                $modata["appealTime"] = $recorder["appealTime"] ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder["appealTime"])) : '' ;//银行到帐时间
                                $modata["account"] =$recorder["account"] ? $recorder["account"] : '';
                                $modata["appealSn"] = $recorder["appealSn"] ? $recorder["appealSn"] : '' ;
                                $modata["appealAmt"] = getMoneyFomat($recorder["appealAmt"]/$this->_moneyUnit,2) ;
                                $modata["isvip"] = isset($this->_isvip[$recorder["isvip"]]) ? $this->_isvip[$recorder["isvip"]] : '';
                                $modata["vipLvl"] =$recorder["vipLvl"] ? $recorder["vipLvl"] : '';
                                $modata["memo"] = $recorder["memo"] ? $recorder["memo"] : '' ;
                                $modata["reviewStartTime"] = $recorder["reviewStartTime"] ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder["reviewStartTime"])) : '' ;
                                $modata["reviewEndTime"] = $recorder["reviewEndTime"] ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder["reviewEndTime"])) : '' ;
                                $modata["status"]  = isset($this->_appStatus[$recorder["status"] ]) ? $this->_appStatus[$recorder["status"] ] : '';
                                $modata["reviewer"] = $recorder["reviewer"] ?$recorder["reviewer"]:'';
                                $modata["userlvl"] = $this->getUserType($recorder["userlvl"]) ;
                                $modata["reviewMemo"] = $recorder["reviewMemo"] ? $recorder["reviewMemo"] : '' ;
                                
                                $modata["path_img"] = $this->_config->imgroot; ;
                                array_push($modatas['text'],$modata) ;
                            }
                            
                            
                            if(isset($res1["body"]["pager"]["total"])){
                                $recordNum = $res1["body"]["pager"]["total"];
                            }
                            array_push($modatas['count'],array("recordNum"=>$recordNum)) ; //recordNum 页数 ，每页15条
                            $modatas['auditPermission'] = $auditPermission;
                            header ( 'Content-Type: application/json;charset=utf-8' );
                            echo json_encode($modatas) ;
                       }
                    
                    //银行卡管理加载页面
                    public function appealBankConf(){
                        $request = $this->_request;
                        $params = $request->getParams();
                        $step = isset($params['step']) ? intval(getSecurityInput($params['step'])) : 0;
                        //检测提交权限
                        if($request->isPost() && isset($this->_aAclArray[$this->_param][$step])){
                            $data = array();
                            if($params['step'] == 0){
                                foreach ($this->_bankIcoArray as $key=>$value){
                                    $data[$key]=array('id'=>$key,'canRechargeAppeal'=>0,'otherCanRechargeAppeal'=>0);
                                }
                                if(isset($params['net_bank']) && count($params['net_bank'])>0){
                                    foreach ($params['net_bank'] as $value){
                                        $data[$value]['canRechargeAppeal'] = 1;
                                    }
                                } 
                                if(isset($params['quick_bank']) && count($params['quick_bank'])>0){
                                    foreach ($params['quick_bank'] as $value){
                                        $data[$value]['otherCanRechargeAppeal'] = 1;
                                    }
                                }
		
                                if(isset($params['third_bank']) && count($params['third_bank'])>0){
                                    foreach ($params['third_bank'] as $value){
		$data[$value]['canRechargeAppeal'] = 1;
                                    }
                                }
                            } else {
                                foreach ($this->_bankIcoArray as $key=>$value){
                                    $data[$key]=array('id'=>$key,'withdraw'=>0);
                                }
                                if(isset($params['withdraw_bank']) && count($params['withdraw_bank'])>0){
                                    foreach ($params['withdraw_bank'] as $value){
                                        $data[$value]['canRechargeAppeal'] = 1;
                                    }
                                }
                            }
                            
                            foreach ($data as $value){
                                $requsetData['param'][] = $value;
                            }
                            
                            $res = $this->_queryAllBankDao->bankParamsSet($requsetData);
                            unset($data,$requsetData);
                                                       
                            //修改申诉等待时间&冷却时间
                            if($params["waitTime"] != $params["orgWaitTime"] ||
                                    $params["cdTime"] != $params["orgCdTime"]){
                                $val = array(
                                    'wait_time'=>$params["waitTime"],
                                    'cd_time' =>$params["cdTime"]
                                );
                                $res = $this->saveconfigvalue($val, "fund", "recharge_appeal_time");
                            }
                        }
                        $res = $this->getBankCardInfo("bankStruc");
                        //Zend_Json::decode ( $resp->getBody (), Zend_Json::TYPE_OBJECT )
                        $timeVals =$this->getconfigvaluebykey("fund","recharge_appeal_time");
                        $times = Zend_Json::decode($timeVals['val']);
                        $this->view->res=$res;
                        $this->view->waitTime = $times['wait_time'];
                        $this->view->cdTime = $times['cd_time'];
                        $this->view->step=$step;
                        $this->view->title = '充值相关配置';
                        $this->view->display('/admin/funds/recharge/appealBankMgr.tpl');
                    }

                    //银行卡管理加载页面
                    public function phoneAppealBankConf(){
                        $request = $this->_request;
                        $params = $request->getParams();
                        $step = isset($params['step']) ? intval(getSecurityInput($params['step'])) : 0;
                        //检测提交权限
                        if($request->isPost() && isset($this->_aAclArray[$this->_param][$step])){
                            $data = array();
                            if($params['step'] == 0){
                                foreach ($this->_bankIcoArray as $key=>$value){
                                    $data[$key]=array('id'=>$key,'moveCanRechargeAppeal'=>0);
                                }
                                if(isset($params['third_bank']) && count($params['third_bank'])>0){
                                    foreach ($params['third_bank'] as $value){
                                        $data[$value]['moveCanRechargeAppeal'] = 1;
                                    }
                                }
                            }

                            foreach ($data as $value){
                                $requsetData['param'][] = $value;
                            }
                            
                            $res = $this->_queryAllBankDao->bankParamsSet($requsetData);
                            unset($data,$requsetData);
                                                       
                            //修改申诉等待时间&冷却时间
                            if($params["waitTime"] != $params["orgWaitTime"] ||
                                    $params["cdTime"] != $params["orgCdTime"]){
                                $val = array(
                                    'wait_time'=>$params["waitTime"],
                                    'cd_time' =>$params["cdTime"]
                                );
                                $res = $this->saveconfigvalue($val, "fund", "move_recharge_appeal");
                            }
                        }
                        $res = $this->getBankCardInfo("bankStruc");
                        //Zend_Json::decode ( $resp->getBody (), Zend_Json::TYPE_OBJECT )
                        $timeVals =$this->getconfigvaluebykey("fund","move_recharge_appeal");
                        $times = Zend_Json::decode($timeVals['val']);
                        $this->view->res=$res;
                        $this->view->waitTime = $times['wait_time'];
                        $this->view->cdTime = $times['cd_time'];
                        $this->view->step=$step;
                        $this->view->title = '充值相关配置';
                        $this->view->display('/admin/funds/recharge/phoneAppealBankMgr.tpl');
                    }
        
                    public function appealTipConf(){
                        //查詢所有TIPS
                        $res = array();
                        $passAry = array();
                        $unPassAry = array();
                        
                        $aUrl['fundadmin'] = 'searchTips';
                        $data['param']['tips']['tipsModel'] = 0;
                        $res = $this->_clientobject->inRequestV2($data, $aUrl);
                        
                        foreach($res["body"]["result"]["tipsList"] as $recorder){
                            if(0==intVal($recorder['tipsGroupb'])){
                                $unPassAry[] = $recorder;
                            } else if(1 == intVal($recorder['tipsGroupb']) ) {
                                $passAry[] = $recorder;
                            }
                        }
                        $this->view->assign("passAry",$passAry);
                        $this->view->assign("unPassAry",$unPassAry);
                        $this->view->title = '充值相关配置';
                        $this->view->display('/admin/funds/recharge/appealTips.tpl');
                    }

                    public function saveTips(){
                        $params = $this->_request->getPost('data','');
                        $data['param'] = $params;
                        $aUrl['fundadmin'] = 'addTips';
                        $res = $this->_clientobject->inRequestV2($data, $aUrl);
                    }
                    //加载异常充值列表的数据
                    public function addTip(){
                        $data=array();
                        $tipsList = array();
                        $modatas = array();
                        $arguetype = getSecurityInput($this->_request->getPost("arguetype",0)) ;
                        $content = getSecurityInput($this->_request->getPost("content",0)) ;
                        $memo = getSecurityInput($this->_request->getPost("memo",0)) ;
                        
                        $tipsList["tipsGroupa"] = "0";
                        $tipsList["tipsModel"] = "0";
                        $tipsList["tipsGroupb"] = $arguetype;
                        $tipsList["tipsMemo"] = $memo;
                        $tipsList["tipsContext"] = $content;
                        $data["param"] = $tipsList;
                        $aUrl['fundadmin'] = 'insertTip';
                        $res = $this->_clientobject->inRequestV2($data, $aUrl);
                        $this->_redirect('/admin/Rechargemange/index?parma=abtf');
                    }
                    
                    public function loadRechargeData(){
		$data=array();
		$status = array(0,1,2,3,4,5,6,7);
		$Title         = getSecurityInput($this->_request->getPost("Title",0)) ;
		$movnumber     = getSecurityInput($this->_request->getPost("movnumber",0)) ;
		$Daozhangtime1 = getSecurityInput($this->_request->getPost("Daozhangtime1",0)) ;
		$Daozhangtime2 = getSecurityInput($this->_request->getPost("Daozhangtime2",0)) ;
		$requestMoney1 = getSecurityInput($this->_request->getPost("requestMoney1",0)) ;
		$requestMoney2 = getSecurityInput($this->_request->getPost("requestMoney2",0)) ;
		$requestRealMoney1 = getSecurityInput($this->_request->getPost("requestRealMoney1",0)) ;
		$requestRealMoney2 = getSecurityInput($this->_request->getPost("requestRealMoney2",0)) ;
		$receivablesCard  = getSecurityInput($this->_request->getPost("receivablesCard",0)) ;
		$palyBankName     = getSecurityInput($this->_request->getPost("palyBankName",0)) ;
		$palyBankUserName = getSecurityInput($this->_request->getPost("palyBankUserName",0)) ;
		$statusType 	  = getSecurityInput($this->_request->getPost('statusType',''));
		$OperatingTimetime1 = getSecurityInput($this->_request->getPost("OperatingTimetime1",0)) ;
        $OperatingTimetime2 = getSecurityInput($this->_request->getPost("OperatingTimetime2",0)) ;
        if($OperatingTimetime1){
            $data["param"]["fromOperatingDate"]      = getQueryStartTime($OperatingTimetime1) ;
        }
        if($OperatingTimetime2){
            $data["param"]["toOperatingDate"]        = getQueryEndTime($OperatingTimetime2) ;
        }
		if($movnumber){
			$data["param"]["mcSn"]  			 = $movnumber ;
		}
		if($Daozhangtime1){
			$data["param"]["exactTimeFrom"]  	 = getQueryStartTime($Daozhangtime1) ;
		}
		if($Daozhangtime2){
			$data["param"]["exactTimeTo"]  		 = getQueryEndTime($Daozhangtime2) ;
		}
		if($requestMoney1){
			$data["param"]["realChargeAmtFrom"]  = floatval($requestMoney1)*$this->_moneyUnit ;
		}
		if($requestMoney2){
			$data["param"]["realChargeAmtTo"]  	 = floatval($requestMoney2)*$this->_moneyUnit  ;
		}
		if($requestRealMoney1){
			$data["param"]["refundAmtFrom"] = floatval($requestRealMoney1)*$this->_moneyUnit ;
		}
		if($requestRealMoney2){
			$data["param"]["refundAmtTo"]   = floatval($requestRealMoney2)*$this->_moneyUnit  ;
		}
		if($receivablesCard){
			$data["param"]["rcvEmail"]  		 = strval($receivablesCard) ;
		}
		if($palyBankName){
			$data["param"]["bankId"]  			 = intval($palyBankName) ;
		}
		if($palyBankUserName){
			$data["param"]["cardAcct"]  		 = $palyBankUserName ;
		}
		if(in_array($Title, array('0','2','3','4'))){
			$AuditOne1 = getSecurityInput($this->_request->getPost("AuditOne1",0)) ;
			$AuditOne2 = getSecurityInput($this->_request->getPost("AuditOne2",0)) ;
			$auditAdmin = getSecurityInput($this->_request->getPost("auditAdmin",0)) ;
			if($AuditOne1){
				$data["param"]["applyTimeFrom"]  = getQueryStartTime($AuditOne1) ;
			}
			if($AuditOne2){
				$data["param"]["applyTimeTo"]  	 = getQueryEndTime($AuditOne2) ;
			}
			if($auditAdmin){
				$data["param"]["applyAccunt"]  	 = $auditAdmin ;
			}
		}
		if(in_array($Title, array('1','2'))){
			//$data["param"]['currFirst'] = 1;
		}
		$aStatusArray= array(array(0,1,2,3,4,5,6,7,8,9),array(0),array(3,8,9),array(4),array(1,2,5,6,7));
		if(in_array($Title, array('0','2','4')) && $statusType!=''){
			$status = array(intval($statusType));
		} else {
			$status = $aStatusArray[$Title];
		}
		$modata = array();
		$modatas = array();
		$recordNum = 0;
		$modatas['text'] = array();
		$modatas['count'] = array();
		if(!isset($this->_aAclArray[$this->_param][$Title])){
			header ( 'Content-Type: application/json;charset=utf-8' );
			echo json_encode($modatas);
			exit;
		}
		$this->_page 	 = intval(getSecurityInput($this->_request->getPost("page",$this->_page)));
		$this->_pagesize = intval(getSecurityInput($this->_request->getPost("perPageNum",$this->_pagesize)));
		$start = $this->_page+1;
		$data["param"]["status"] = $status;
		$data["pager"]["startNo"] = $this->_page*$this->_pagesize+1; //启始值
		$data["pager"]["endNo"] = $start*$this->_pagesize; //结束值
		$rsr = $this->_exceptionQuery->exceptionQuery($data);
           $this->log('=====保存银行上下限失败====');
      
		foreach ( $rsr["result"] as $recorder){
			$modata["realChargeTime"] 	= $recorder->getMember("mcExactTime") ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder->getMember("mcExactTime"))) : '' ;//银行到帐时间
			$modata["bankId"] 			= $recorder->getMember("bankId") ;
			$modata["bankInfoName"] 	= $recorder->getMember("bankId")     ? $this->_bankIcoArray[$recorder->getMember("bankId")]['name'] : '' ;
			$modata["cardAcct"] 		= $recorder->getMember("cardAcct")   ? $recorder->getMember("cardAcct") : '' ;
			$modata["cardNumber"] 		= $recorder->getMember("cardNumber") ? $recorder->getMember("cardNumber") : '' ;
			$modata["bankName"] 		= $recorder->getMember("bankName")   ? $recorder->getMember("bankName") : '' ;
			$modata["bankAddr"] 		= $recorder->getMember("bankAddr")   ? $recorder->getMember("bankAddr") : '' ;
			$modata["rcvEmail"] 		= $recorder->getMember("rcvEmail")   ? $recorder->getMember("rcvEmail") : '' ;
			$modata["recBankEmail"] 	= $recorder->getMember("recBankEmail")  ? $recorder->getMember("recBankEmail") : '' ;
			$modata["rcvCardNumber"] 	= $recorder->getMember("rcvCardNumber") ? $recorder->getMember("rcvCardNumber") : '' ;
			$modata["rcvCardNumber"] 	= (strpos($modata["rcvCardNumber"],'@')>0 || !in_array(strlen($modata["rcvCardNumber"]), array(16,18,19))) ? $modata["rcvEmail"] : $this->getSecurityBankCardNum($modata["rcvCardNumber"]);
			$modata["recBankAccount"] 	= $recorder->getMember("recBankAccount") ? $recorder->getMember("recBankAccount") : '' ;
			$modata["rcvBank"] 			= $recorder->getMember("rcvBank")        ? $this->_bankIcoArray[$recorder->getMember("rcvBank")]['name'] : ''; //收款银行
			$modata["refundAmt"] 	    = getMoneyFomat($recorder->getMember("refundAmt")/$this->_moneyUnit,2) ;
			$modata["realChargeAmt"] 	= getMoneyFomat($recorder->getMember("realChargeAmt")/$this->_moneyUnit,2) ;
			$modata["apprMemo"] 		= $recorder->getMember("apprMemo")!=''    ? $recorder->getMember("apprMemo") : '' ; //审核备注
			$modata["apprAccount"] 		= $recorder->getMember("apprAccount")!='' ? $recorder->getMember("apprAccount") : '' ;
			$modata["memo"] 			= $recorder->getMember("memo")!=''        ? $recorder->getMember("memo") : ''; //付款附言
			$modata["applyMemo"] 		= $recorder->getMember("applyMemo")!=''   ? $recorder->getMember("applyMemo") : '' ; //申请备注
			$modata["mcSn"] 			= $recorder->getMember("mcSn")            ? $recorder->getMember("mcSn") :'' ;
			$modata["sn"] 				= $recorder->getMember("sn")            ? $recorder->getMember("sn") :'' ;
			$modata["id"] 				= $recorder->getMember("id")              ? ''.$recorder->getMember("id") :'' ;

			$modata["TradeStatus"] 		= $recorder->getMember("TradeStatus")     ? $recorder->getMember("TradeStatus") : '' ;
			$modata["applyTime"] 		= $recorder->getMember("applyTime") !=''  ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("applyTime"))) : '' ; //申请时间
			$modata["apprTime"] 		= $recorder->getMember("apprTime") !=''   ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("apprTime"))) : '' ; //一审时间
			$modata["applyAccount"] 	= $recorder->getMember("applyAccount")    ? $recorder->getMember("applyAccount") : '' ;
			$modata["mcNoticeTime"] 	= $recorder->getMember("mcNoticeTime") !='' ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("mcNoticeTime"))) : '' ; ;
			$modata["operatingTime"]  	= $recorder->getMember("operatingTime") !='' ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("operatingTime"))) : '' ; ;
			$modata["baseInfo"] 		= $recorder->getMember("baseInfo")    ? $recorder->getMember("baseInfo") : '' ;
			$modata["iStatus"] 			= $recorder->getMember("status");
			if(in_array($modata["iStatus"],array(3,8,9))){
				$modata["Status"] 			= isset($this->_aAuitStatus[$modata["iStatus"]]) ? $this->_aAuitStatus[$modata["iStatus"]] : '';
			} else {
				$modata["Status"] 			= isset($this->_aRechargeStatus[$modata["iStatus"]]) ? $this->_aRechargeStatus[$modata["iStatus"]] : '';
			}
			$modata["mcChannel"] 		= $recorder->getMember("mcChannel")       ? $recorder->getMember("mcChannel") : '' ;
			$modata["mcFee"] 			= getMoneyFomat($recorder->getMember("mcFee")/$this->_moneyUnit,2) ;//服务费总和
			$modata["mcBankFee"] 		= getMoneyFomat($recorder->getMember("mcBankFee")/$this->_moneyUnit,2) ;//用户银行手续费
			$modata["currApprer"] 		= $recorder->getMember("currApprer")!='' ? $recorder->getMember("currApprer") : '' ;
			$modata["currDate"] 		= $recorder->getMember("currDate") ;
			$modata["myLocked"]   		= $modata["currApprer"]!=''&& $modata["currApprer"]==$this->_sessionlogin->info['account'] ? true : false ;
			$modata["isAbleOprate"] 	= $modata["currApprer"]!=''&& $modata["currApprer"]!=$this->_sessionlogin->info['account'] ? false : true ;
                        $modata["canCheckMowOprate"] = (strtotime(date('Y-m-d H:i:s'))-strtotime($modata["apprTime"]))>30*60;
			$userChain 					= $recorder->getMember("userChain") ;
			$userChain 					= array_filter(explode('/', $userChain));
			$modata["userName"] 		= $userChain ? array_pop($userChain) : '';
			$modata["agentName"] 		= $userChain ? array_shift($userChain) : '';
			if(!in_array('FUND_RECHARGE_EXCEPTION_DISPLAYCOLS', $this->_sessionlogin->info['acls'])){
				$modata["bankName"] 		= '' ;
				$modata["bankAddr"] 		= '' ;
				$modata["userName"] 		= '';
				$modata["agentName"] 		= '';
			}
			$attachment = $recorder->getMember("attachment");
			if(!empty($attachment)){
				$attachmentArray = explode('|', $recorder->getMember("attachment"));
				$attachment= '';
				foreach ($attachmentArray as $key=>$value){
					$attachment .= '<a href="'.$this->_config->dynamicroot.'/upload/images/'.$value.'" target="_blank">附件'.($key+1).'</a>,';
				}
			}
			$modata["attachment"] = trim($attachment,",");
			array_push($modatas['text'],$modata) ;
		}
		if(isset($rsr["pager"]["total"])){
			$recordNum = $rsr["pager"]["total"];
		}
		array_push($modatas['count'],array("recordNum"=>$recordNum)) ; //recordNum 页数 ，每页15条
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode($modatas) ;
	}
        
                    public function queryTips(){
                        $params = $this->_request->getPost('data','');
                        
                        $data = array();
                        $data['param']['tips']['tipsModel'] = $params['tipsModel'] ;
                        //$data['param']['tips']['tipsGroupa'] = $params['tipsGroupa'] ;
                        $data['param']['tips']['tipsGroupb'] = $params['tipsGroupb'];
                                               
                        $aUrl['fundadmin'] = 'searchTips';
                        $res = $this->_clientobject->inRequestV2($data, $aUrl);
                        echo json_encode($res["body"]["result"]["tipsList"]);
                    }
                
                    public function appealReview(){
                        $params = $this->_request->getPost('data','');
                        $params['appealMemo']=htmlentities($params['appealMemo'],ENT_QUOTES,"UTF-8");
                        $data['param'] = $params;
                        $aUrl['fundadmin'] = 'appealReview';
                        $res = $this->_clientobject->inRequestV2($data, $aUrl);
                        $appealSn = $params['appealSn'];
                        $userId = $params['userId'];
                        $this->redis_client->sAdd('FUND_APPEAL_STATUS_'.$userId,$appealSn);
                    }
                    
                    public function queryAppealCountsByStatus(){
                        //提限提示權限(預設為無)
                        $modata = array();
                        $wd_purview =false;
                        //充值提示權限(預設為無)
                        $rc_purview=false;
						//提现单据提示權限(預設為無)
                        $wd_bill_purview=false;
						//充值单据提示權限(預設為無)
                        $cg_bill_purview=false;
                        $aclAry =  $this->_sessionlogin->info['acls'];
                        if(in_array("FUND_APPEAL_REVIEW",$aclAry)){
                            $rc_purview = true;
                        }
                        if(in_array("FUND_WITHDRAW_APPEAL_UNCHECK_CHECK",$aclAry)){
                            $wd_purview = true;
                        }
						if(in_array("FUND_WITHDRAW_RISK",$aclAry)){
                            $wd_bill_purview = true;
                        }
                        if(in_array("FUND_REPORT_CHARGE",$aclAry)){
                            $cg_bill_purview = true;
                        }
                        
                        $modata['rc_purview'] = $rc_purview;
                        $modata['wd_purview'] = $wd_purview;
                        $modata['wd_bill_purview'] = $wd_bill_purview;
                        $modata['cg_bill_purview'] = $cg_bill_purview;

                        if($rc_purview){
                            $data['param']= 1;
                            $aUrl['fundadmin'] = 'queryAppealCountsByStatus';
                            $res = $this->_clientobject->inRequestV2($data, $aUrl);
                            $modata['rcCount'] = $res["body"]["result"];
                        }
                        if($wd_purview){
                            $req['param']=0;
                            $Url['fundadmin'] = 'queryUncheckAppeal';
                            $rsr = $this->_clientobject->inRequestV2($req, $Url);
                            $modata['wdCount'] = $rsr['body']['result'];
                        }
						if($wd_bill_purview){
                            $req['param']=0;
                            $Url['fundadmin'] = 'queryUnhandleWithdraw';
                            $rsr = $this->_clientobject->inRequestV2($req, $Url);
                            $modata['wdBillCount'] = $rsr['body']['result'];
                        }
						if($cg_bill_purview){
                            $req['param']=0;
                            $Url['fundadmin'] = 'queryUnhandleCharge';
                            $rsr = $this->_clientobject->inRequestV2($req, $Url);
                            $modata['cgBillCount'] = $rsr['body']['result'];
                        }
                        echo  Zend_Json::encode($modata);
                        
                    }
                    
                    //加载添加游戏币,退款没收等操作默认页面 初审界面
	public function displayRechargeOpratePage(){
		$mcSn 		= getSecurityInput($this->_request->get("mcSn",''));
		$optionType = getSecurityInput($this->_request->get("optionType",'1'));
		$data=array();
		
		/* if($request->isGet()) { */
			$data["param"]["mcSn"] = $mcSn ;
			$data["pager"]["startNo"] = 0; //启始值
			$data["pager"]["endNo"] = 1; //结束值
			$rsr = $this->_exceptionQuery->exceptionQuery($data);
			$modata = array();
			if (isset($rsr['result'][0])){
				$recorder = $rsr['result'][0];
				$modata["_bankIcoArray"]= $this->_bankIcoArray;
				$modata["realChargeTime"] = $recorder->getMember("mcExactTime") ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder->getMember("mcExactTime"))) : '' ;
				$modata["mcSn"] 		= $recorder->getMember("mcSn");
				$modata["id"] 			= $recorder->getMember("id");
				$modata["bankId"] 		= $recorder->getMember("bankId") ? $recorder->getMember("bankId") : '' ;
				$modata["rcvBank"] 		= $recorder->getMember("rcvBank") ? $recorder->getMember("rcvBank") : '' ;
				$modata["bankInfo"] 	= $modata["bankId"]  ? $this->_bankIcoArray[$recorder->getMember("bankId")] : array();
				$modata["revBankInfo"] 	= $modata["rcvBank"] ? $this->_bankIcoArray[$recorder->getMember("rcvBank")] : array();
				$modata["cardNumber"] 	= $recorder->getMember("cardNumber");
				$modata["cardAcct"] 	= $recorder->getMember("cardAcct") ;
				$modata["rcvEmail"] 	= $recorder->getMember("rcvEmail") ? $recorder->getMember("rcvEmail") : '' ;
				$modata["recBankId"] 	= $recorder->getMember("recBankId") ;
				$modata["rcvAccName"] 	= $this->getSecurityBankCardAucount($recorder->getMember("rcvAccName")) ;
				$modata["realChargeAmt"]= getMoneyFomat($recorder->getMember("realChargeAmt")/$this->_moneyUnit,2) ;
				$modata["mcBankFee"] 	= getMoneyFomat($recorder->getMember("mcBankFee")/$this->_moneyUnit,2);
				$modata["memo"] 		= $recorder->getMember("memo");
				$modata["apprMemo"] 	= $recorder->getMember("apprMemo");
				$modata["bankAddr"] 	= $recorder->getMember("bankAddr") ;
				$modata["bankName"] 	= $recorder->getMember("bankName") ;
				$modata["status"] 		= $recorder->getMember("status") ;
				$modata["currApprer"] 	= $recorder->getMember("currApprer") ;
				$modata["currDate"] 	= $recorder->getMember("currDate") ;
				$userChain 				= $recorder->getMember("userChain") ;
				$userChain 				= array_filter(explode('/', $userChain));
				$modata["userName"] 	= $userChain ? array_pop($userChain) : '';
				$modata["agentName"] 	= $userChain ? array_shift($userChain) : '';
				$attachment 			= $recorder->getMember("attachment");
				if(!empty($attachment)){
					$attachmentArray = explode('|', $attachment);
					$attachment= '';
					foreach ($attachmentArray as $key=>$value){
						$attachment .= '<a href="'.$this->_config->dynamicroot.'/upload/images/'.$value.'" target="_blank">附件'.($key+1).'</a>,';
					}
				} else {
					$attachment ='';
				}
				$modata["attach"] = trim($attachment,",");
				
			}
			$aTitle = array(1=>'待复审 - 加游戏币','待复审- 退款','复审 - 退款','复审 - 退款','待复审 - 没收',8=>'复审 - 加游戏币','复审 - 没收');

			if(in_array($modata['status'],array(3,8,9))){
				if(!isset($this->_aAclArray[$this->_param][$modata['status']])){
					$this->_redirect($_SERVER['HTTP_REFERER']);
				}
				if(isset($modata['currApprer']) && $modata['currApprer'] !='' & $modata['currApprer'] !=$this->_sessionlogin->info['account']){
					$this->view->currApprer=$modata['currApprer'];
				} else {
					$res = $this->lockRechargeWork($modata["id"]);
					if($res){
						$this->view->vals = $modata ;
					} else {
						$this->view->isLocked=true;
					}
				}
				$this->view->type = $modata['status'];
				$this->view->title = $aTitle[$modata['status']];
				$this->view->display ( '/admin/funds/recharge/exception/exceptionReauit.tpl');
			} else {
				if(!isset($this->_aAclArray[$this->_param][$optionType])){
					$this->_redirect($_SERVER['HTTP_REFERER']);
				}
				if(isset($modata['currApprer']) && $modata['currApprer'] !='' & $modata['currApprer'] !=$this->_sessionlogin->info['account']){
					$this->view->currApprer=$modata['currApprer'];
				} else {
					$res = $this->lockRechargeWork($modata["id"]);
					if($res){
						$this->view->vals = $modata ;
					} else {
						$this->view->isLocked=true;
					}
				}
				$this->view->title = $aTitle[$optionType];
				$this->view->display ( '/admin/funds/recharge/exception/addgamecoin.tpl' );
			}
		/* }elseif($request->isPost()){
			$this->rechageApprOperate($optionType);
		} */
	}

	//加游戏币,复审,退款,没收等的操作逻辑处理
	public function rechageApprOperate(){
		$data = $res=array();
		$des = '';
		$attachMent = '';
		$exceptId   = getSecurityInput($this->_request->getPost('exceptId',0));
    	$status 	= intval(getSecurityInput($this->_request->getPost("status")));
		$type 		= intval(getSecurityInput($this->_request->getPost("optionType")));
		$applyMemo	= getSecurityInput($this->_request->getPost('applyMemo',''));
		if(!isset($this->_aAclArray[$this->_param][$type])){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102144')));
			exit;
		}
		if(count($this->_uploadsession->attachments)>0){
			$attachMent = implode('|', $this->_uploadsession->attachments);
			unset($this->_uploadsession->attachments);
		}
		$exAddGameCoin = new ExceptionGameCoin();
		
		if(($type == "1" && $status == 8) || ($type == "2" && $status == 3) || ($type == "5" && $status == 9)){
			$mcSn    = getSecurityInput($this->_request->getPost('mcSn',''));
			$data2["param"]["mcSn"]	= $mcSn;
			
			$rsr = $this->_exceptionQuery->exceptionQuery($data2);
			if(isset($rsr["result"]) && count($rsr["result"])>0){
				foreach ( $rsr["result"] as $key => $recorder){
					$exStatus = $recorder->getMember("status") ;
					if($exStatus!=0){
						echo Zend_Json::encode(array('status'=>'StatusError','data'=>'此订单不是未处理状态，请再重新确认订单状态'));
						exit;
					}
				}
			}
		}
		
		if($type == "1" ){ //加游戏币mowId
			$username1 = getSecurityInput($this->_request->getPost ("userName1"));
			$username2 = getSecurityInput($this->_request->getPost ("userName2"));
			if(empty($username1)){
				echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('1004')));
				exit;
			}
			if($username1 != $username2){
				echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102064')));
				exit;
			}
				
			$userInfo = $this->getUserInfo($username1);
			$data["param"]["exceptId"]	= $exceptId;
			$data["param"]["userId"]	= $userInfo['id'] ;
			$data["param"]["attachMent"]= $attachMent;
			$data["param"]["applyMemo"]	= $applyMemo;
			if($status == 8){
				$data["param"]["status"]	= $status;
				$res = $this->_exceptionRefundDao->exceptionRefund($data);
			} else {
				$res = $exAddGameCoin->addGameCoin($data);
			}
			$des = '发起加币';
		}elseif($type == "2"){ //发起退款
			$bankId 	= intval(getSecurityInput($this->_request->getPost ("bankId","")));
			$cardAcct 	= getSecurityInput($this->_request->getPost ("recBankAccount",""));
			$cardNumber = getSecurityInput($this->_request->getPost ("recBankNo",""));
			$bankName 	= getSecurityInput($this->_request->getPost ("bankName",""));
			//$bankAddr 	= getSecurityInput($this->_request->getPost ("bankAddr",""));
			if(empty($bankId) || empty($cardAcct) || empty($cardNumber)){
				echo Zend_Json::encode(array('status'=>'error','data'=>'请补充完整退款信息!'));
				exit;
			}
			$data["param"]["exceptId"]	= $exceptId;
			$data["param"]["bankId"]	= $bankId;
			$data["param"]["cardAcct"]	= $cardAcct;
			$data["param"]["cardNumber"]= $cardNumber;
			$data["param"]["bankName"]	= $bankName;
			//$data["param"]["bankAddr"]	= $bankAddr;
			$data["param"]["attachMent"]= $attachMent;
			$data["param"]["applyMemo"]	= $applyMemo;
			if($status == 3){
				$data["param"]["status"]	= $status;
				$res = $exAddGameCoin->exceptionRefund($data);
			} else {
				$data["param"]["status"] = 4;
				$res = $this->_exceptionRefundDao->exceptionRefund($data);
			}
			$des = '发起退款';
		}elseif($type == "5"){ //没收
				
			$data["param"]["exceptId"]  = $exceptId;
			$data["param"]["attachMent"] = $attachMent;
			$data["param"]["applyMemo"] = $applyMemo;
			
			if($status == 9){
				$data["param"]["status"]	= $status;
				$res = $exAddGameCoin->exceptionRefund($data);
			} else {
				$res = $exAddGameCoin->exceptionConfiscate($data);
			}
			$des = '发起没收';
		} else {
				$des = '补充信息';
		}
		
		if($res){
			$aUrl['fundadmin'] = 'yuchuliEnd';
			$data['param']['exceptId'] = $exceptId;
			$res1 = $this->_clientobject->inRequestV2($data, $aUrl);
			if(isset($res1['head']['status']) && $res1['head']['status'] == '0'){
				echo Zend_Json::encode(array('status'=>'ok','data'=>$des.'成功,转入待复审状态'));
				exit;
			}
		}
		echo Zend_Json::encode(array('status'=>'error','data'=>$des.'失败'));
		exit;
	}
	
	
	//待复审 审核
	public function rechargeReAuit(){
		$exceptId = getSecurityInput($this->_request->getPost('exceptId',0));
		$status   = intval(getSecurityInput($this->_request->getPost("status")));
		$type     = intval(getSecurityInput($this->_request->getPost("type")));
		$applyMemo= getSecurityInput($this->_request->getPost("applyMemo"));
		
		//权限判断
		if(!isset($this->_aAclArray[$this->_param][$status])){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102144')));
			exit;
		}
		$data["param"]["applyMemo"] = $applyMemo;
		$exAddGameCoin = new ExceptionGameCoin();
		if($type == 1){
			$data["param"]["exceptId"]	= $exceptId;
			switch ($status){
				case 3: //退款
					$data["param"]["status"]	= 4;
					$res = $exAddGameCoin->exceptionRefund($data);
					$typeStr= '退款';
					$strSuccess= $typeStr.'成功,流转入处理中';
					break;
				case 8: //加游戏币
					$data["param"]["status"]	= 8;
					$res = $exAddGameCoin->addGameCoin($data);
					$typeStr= '加游戏币';
					$strSuccess= $typeStr.'成功,流转入已完成';
					break;
				case 9: //没收
					$data["param"]["status"]	= 9;
					$res = $exAddGameCoin->exceptionConfiscate($data);
					$typeStr= '没收';
					$strSuccess= $typeStr.'成功,流转入已完成';
					break;
			}
			
			$strFailed = $typeStr.'审核失败,请重新审核或者联系管理员';
		} else {
			$data["param"]["exceptionId"]	= $exceptId;
                        $strSuccess= '已拒绝,订单流转入未处理';
                        $strFailed = '拒绝审核失败,请重新审核或者联系管理员';
			$res = $exAddGameCoin->exceptionRefundReject($data);
			if($res){
                            $aUrl['fundadmin'] = 'yuchuliEnd';
                            $data['param']['exceptId'] = $exceptId;
                            $res1 = $this->_clientobject->inRequestV2($data, $aUrl);
                            if(isset($res1['head']['status']) && $res1['head']['status'] == '0'){
                                    echo Zend_Json::encode(array('status'=>'ok','data'=>$strSuccess));
                                    exit;
                            }
			}else{
                            echo Zend_Json::encode(array('status'=>'error','data'=>$strFailed));
                            exit;
                        }
		}
		
		if($res){
			echo Zend_Json::encode(array('status'=>'ok','data'=>$strSuccess));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','data'=>$strFailed));
			exit;
		}
	}
	
	//导出异常充值处理数据
	public function downloadRechargeData(){
		$Title 		  	  = getSecurityInput($this->_request->get("Title",0)) ;
        $fileName = '异常充值处理';
        $aTitle = array(
                'mcSn' => 'MOW异常订单号',
                'memo' => '附言',
                'realChargeTime' => '银行到帐时间',
                'refundAmt' => '金额',
                'realChargeAmt' => '实际支付金额',
                'mcBankFee' => '手续费',
                'rcvBank' => '收款银行',
                'rcvEmail' => '收款卡',
                'bankInfoName' => '付款银行',
                'cardNumber' => '付款卡',
                'cardAcct' => '付款户名',
                'mcChannel' => '充值渠道',
                'why' => '充值异常原因',
        		'baseInfo' => '交易单号',
                'applyTime' => '一审时间',
                'applyAccount' => '一审管理员',
                'Status' => '状态',
                'apprMemo' => '备注',
                'userName' => '充值用户',
                'agentName' => '所属总代',
                'bankAddr' => '开户行',
                'operatingTime' => 'DP操作时间'
        );
        
        $intArray = array('rcvEmail','cardNumber');
        if($Title == '0'){
            unset($aTitle['realChargeAmt']);
            $intArray = array('refundAmt','mcBankFee');
        }
        
        //检查权限
        if(!isset($this->_aAclArray[$this->_param][$Title])){
            $this->_redirect('/admin/Rechargemange/index?parma=sv1&tabIndex='.$Title);
        }
        
        
		$movnumber 		  = getSecurityInput($this->_request->get("movnumber",0)) ;
		$Daozhangtime1 	  = getSecurityInput($this->_request->get("Daozhangtime1",0)) ;
		$Daozhangtime2 	  = getSecurityInput($this->_request->get("Daozhangtime2",0)) ;
		$requestMoney1 	  = getSecurityInput($this->_request->get("requestMoney1",0)) ;
		$requestMoney2 	  = getSecurityInput($this->_request->get("requestMoney2",0)) ;
        $receivablesCard  = getSecurityInput($this->_request->get("receivablesCard",0)) ;
        $palyBankName     = getSecurityInput($this->_request->get("palyBankName",0)) ;
        $palyBankUserName = getSecurityInput($this->_request->get("palyBankUserName",0)) ;
		$AuditOne1 		  = getSecurityInput($this->_request->get("AuditOne1",0)) ;
		$AuditOne2 		  = getSecurityInput($this->_request->get("AuditOne2",0)) ;
		$auditAdmin 	  = getSecurityInput($this->_request->get("auditAdmin",0)) ;
		$statusType 	  = getSecurityInput($this->_request->get("statusType",'')) ;
        $OperatingTimetime1 = getSecurityInput($this->_request->get ("OperatingTimetime1",0));
        $OperatingTimetime2 = getSecurityInput($this->_request->get ("OperatingTimetime2",0));
        if($OperatingTimetime1){
            $param["fromOperatingDate"] = getQueryStartTime($OperatingTimetime1);
        }
        if($OperatingTimetime2){
            $param["toOperatingDate"]   = getQueryEndTime($OperatingTimetime2);
        }
        if($movnumber){
			$data["param"]["mcSn"]  			= $movnumber ;
        }
        if($Daozhangtime1){
			$data["param"]["exactTimeFrom"]  	= getQueryStartTime($Daozhangtime1) ;
        }
        if($Daozhangtime2){
			$data["param"]["exactTimeTo"]  		= getQueryEndTime($Daozhangtime2) ;
        }
        if($requestMoney1){
            $data["param"]["realChargeAmtFrom"] = floatval($requestMoney1)*$this->_moneyUnit ;
        }
        if($requestMoney2){
			$data["param"]["realChargeAmtTo"]  	= floatval($requestMoney2)*$this->_moneyUnit  ;
        }
        if($receivablesCard){
			$data["param"]["rcvCardNumber"]  	= strval($receivablesCard) ;
        }
        if($palyBankName){
			$data["param"]["bankId"]  			= $palyBankName ;
        }
        if($palyBankUserName){
			$data["param"]["rcvAccName"]  		= $palyBankUserName ;
        }
        if($AuditOne1){
			$data["param"]["applyTimeFrom"]  	= getQueryStartTime($AuditOne1) ;
        }
        if($AuditOne2){
			$data["param"]["applyTimeTo"]  		= getQueryEndTime($AuditOne2) ;
        }
        if($auditAdmin){
			$data["param"]["applyAccunt"]  		= $auditAdmin ;
        }
        $aStatusArray= array(array(0,1,2,3,4,5,6,7,8,9),1=>array(0),4=>array(1,2,5,6,7));
        if(in_array($Title, array('0','1','4'))){
            $AuditOne1 = getSecurityInput($this->_request->get("AuditOne1",0)) ;
            $AuditOne2 = getSecurityInput($this->_request->get("AuditOne2",0)) ;
            $auditAdmin = getSecurityInput($this->_request->get("auditAdmin",0)) ;
            if($AuditOne1){
                $data["param"]["applyTimeFrom"]  = getQueryStartTime($AuditOne1) ;
            }
            if($AuditOne2){
				$data["param"]["applyTimeTo"]  	 = getQueryEndTime($AuditOne2) ;
            }
            if($auditAdmin){
				$data["param"]["applyAccunt"]  	 = $auditAdmin ;
            }
            
            if($statusType!=''){
                $status = array(intval($statusType));
            } else {
                $status = $aStatusArray[$Title];
            }
        } else {
            $status = $aStatusArray[4];
        }
        
        $data["param"]["status"]  = $status ;
        $total = 0;
        $page= $totalPage = 1;
        $querySize = 2000;
        $startTime= microtime(true);
        do {
            $data["pager"]["startNo"] = ($page-1)*$querySize+1;
            $data["pager"]["endNo"] = $page*$querySize;
            $rsr = $this->_exceptionQuery->exceptionQuery($data);
            if(isset($rsr["result"]) && count($rsr["result"])>0){
                foreach ( $rsr["result"] as $key => $recorder){
                    
                    $modata["mcSn"]             = $recorder->getMember("mcSn") ;
                    $modata["memo"]             = $recorder->getMember("memo")!='' ? $recorder->getMember("memo") : ''; //付款附言
                    $modata["realChargeTime"]   = $recorder->getMember("mcExactTime") ? date('Y-m-d H:i:s',getSrtTimeByLong($recorder->getMember("mcExactTime"))) : '' ;//银行到帐时间
                    $modata["realChargeAmt"]    = $recorder->getMember("realChargeAmt")/$this->_moneyUnit ;
                    $modata["refundAmt"]        = $recorder->getMember("refundAmt")/$this->_moneyUnit ;
                    $modata["mcBankFee"]        = floatval($recorder->getMember("mcBankFee"))/$this->_moneyUnit ;//用户银行手续费
                    $modata["rcvBank"]          = $recorder->getMember("rcvBank") ? $this->_bankIcoArray[$recorder->getMember("rcvBank")]['name'] : ''; //收款银行
                    $modata["rcvEmail"]         = $recorder->getMember("rcvEmail");
                    $modata["bankInfoName"]     = $recorder->getMember("bankId") ? $this->_bankIcoArray[$recorder->getMember("bankId")]['name'] : '';
                    $modata["cardNumber"]       = $recorder->getMember("cardNumber") ;
                    $modata["cardAcct"]         = $recorder->getMember("cardAcct") ;
                    $modata["mcChannel"]        = $recorder->getMember("mcChannel")? $recorder->getMember("mcChannel") : '' ;
                    $modata["why"]              = '附言违规';
                    $modata["baseInfo"]         = ($recorder->getMember("baseInfo"))? $recorder->getMember("baseInfo")."\t":'' ;
                    $modata["applyTime"]        = $recorder->getMember("applyTime") !='' ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("applyTime"))) : '' ; //申请时间
                    $modata["apprAccount"]      = $recorder->getMember("apprAccount")!='' ? $recorder->getMember("apprAccount") : '' ;
                    $modata["Status"]           = isset($this->_aRechargeStatus[$recorder->getMember("status")]) ? $this->_aRechargeStatus[$recorder->getMember("status")] : '异常数据' ;
                    $modata["apprMemo"]         = $recorder->getMember("apprMemo")!='' ? str_replace("\n", " ", $recorder->getMember("apprMemo")) : '' ; //审核备注
                    $modata["applyAccount"]     = $recorder->getMember("applyAccount") ;
                    $userChain                  = $recorder->getMember("userChain") ;
                    $userChain                  = array_filter(explode('/', $userChain));
        //          $modata["bankName"]         = $recorder->getMember("bankName") ;
                    $modata["userName"]         = array_pop($userChain);
                    $modata["agentName"]        = array_shift($userChain);
                    $modata["bankAddr"]         = $recorder->getMember("bankAddr") ;
                    $modata["operatingTime"]    = $recorder->getMember("operatingTime") ? " ".date('Y-m-d H:i:s',getSrtTimeByLong($recorder->getMember("operatingTime"))) : '' ;//加游戏币时间
                    $aContent[$key]             = $modata;
                }
                $totalPage = ceil($rsr['pager']['total']/$querySize);
                $total     = $rsr['pager']['total'];
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
        $modata['mcSn'] = '下载数据:'.$total.'条.';
        $modata['memo']   = '总耗时:'.$diffTime.'秒';
        array_push($aContent,$modata);
        $this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$page);
	}
	
	//锁定异常充值任务单
	public function lockRechargeWork($exceptId=''){
		if(empty($exceptId)){
			return false;
		}
		$aUrl['fundadmin'] = 'yuchuli';
		$data['param']['exceptId'] = $exceptId;
		$res = $this->_clientobject->inRequestV2($data, $aUrl);
		if(isset($res['head']['status']) && $res['head']['status'] == '0'){
			return true;
		}
		return false;
	}
	
	//解锁异常充值任务单
	public function unLockRechargeWork($exceptId=''){
		$id = getSecurityInput($this->_request->get('exceptId',0));
     
		if(!isset($this->_aAclArray[$this->_param])){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102144')));
			exit;
		}
		if(!empty($id)){
			$exceptId = $id;
		}
		if (empty($exceptId)) {
			echo Zend_Json::encode(array('status'=>'error','data'=>'解锁失败'));
			exit;
		}
		$aUrl['fundadmin'] = 'yuchuliEnd';
		$data['param']['exceptId'] = $exceptId;
		$res = $this->_clientobject->inRequestV2($data, $aUrl);
		if($res['head']['status'] == '0'){
			echo Zend_Json::encode(array('status'=>'ok','data'=>'解锁成功'));
			exit;
		}
		echo Zend_Json::encode(array('status'=>'error','data'=>'解锁失败'));
		exit;
	}
	
	//充值相关配置
	public function rechageSettings(){
		$request = $this->getRequest();
		$paramsList = $request->getParams();
		$index = getSecurityInput($this->_request->get("index",0)) ;
		$data["param"]= $configArray = $chargeData["param"] = $rtnStruc = array();
		if(empty($paramsList['step'])){
			$step = 0;
		} else {
			$step = intval($paramsList['step']);
		}
		//检查权限
		//檢查 1.充值上下限配置  FUND_RECHARGE_CONFIG_UPTO與  2.充值返送手续费配置  FUND_RECHARGE_CONFIG_RETURN 同時為空值
		if(!isset($this->_aAclArray[$this->_param][$step]) && !isset($this->_aAclArray[$this->_param][$step+1])){ 
			if($step >0){
				$step -=1;
			} else {
				$step +=1;
			}
			if(!isset($this->_aAclArray[$this->_param][$step])){
				$this->_redirect('/admin/Rechargemange/index?parma=sv2&step='.$step);
				exit;
			}
			$this->_redirect('/admin/Rechargemange/');
			exit;
		}
		if(empty($paramsList['type'])){
			$type = 0;
		} else {
			$type = intval($paramsList['type']);
		}
		if($request->isPost()) {
			//保存权限检查
			if(!isset($this->_aAclArray[$this->_param][$step.'_post'])){
				$this->_redirect('/admin/Rechargemange/');
				exit;
			}
			if($step=='0'){
				foreach ( $paramsList as $k1 => $v1){
					if(strpos($k1,'@')){
						$arrs = explode("@",$k1) ;
						$configArray[$arrs[0]]['code'] = intval(getSecurityInput($arrs[0]));
						$configArray[$arrs[0]][$arrs[1]] = floatval(getSecurityInput($v1))*$this->_moneyUnit;
					}
				}
				$data['param'] = array_values($configArray);
				//針對支付寶企業版更改code為30
				if($data['param']['0']['code']==34){
					$data['param']['0']['code']=30;
				}
				if(!$this->_queryAllBankDao->bankParamsSet_V2($data)){
					$this->log('保存银行上下限失败');
				}
				//保存倒计时配置数据
				$chargeData["param"]["module"]="fund";
				if($type ==1){
					$chargeData["param"]["function"]="chargeCouteThired";
				} else if ($type == 2) {
					$chargeData["param"]["function"]="chargeCouteCaifu";
				} else if ($type == 3) {
					$chargeData["param"]["function"]="chargeCouteUnionpay";
				} else if ($type == 4) {
					$chargeData["param"]["function"]="chargeCouteAlipay";
				}else if ($type == 5) {
					$chargeData["param"]["function"]="chargeCouteAlipayBi";
				}else if ($type == 6) {
					$chargeData["param"]["function"]="chargeCouteWechat";
				}else {
					$chargeData["param"]["function"]="chargeCountDown";
				}
				$chargeData["param"]["val"]=intval($this->_request->getPost ("charge_ratio"));
				$cdata =$this->_queryAllBankDao->chargeCountDown($chargeData,'saveConfigchargeCoute');
			} else {
				if($paramsList['id'] == ''){
					$this->addErr('102059');
					$this->res_add_url('充值相关配置','/admin/Rechargemange/index?parma=sv2&step='.$step,false,true);
					$this->res_show(true,true,'funds');
				}
				//公式计算金额
				if($paramsList['rtnSet'] =='2') {
					foreach ($paramsList['sm'] as $key=>$value){
						$rtnStruc[$key]['sm']    = floatval(getSecurityInput($value))*$this->_moneyUnit;
						if(array_key_exists($key,$paramsList['big'])){
							$rtnStruc[$key]['big'] = floatval(getSecurityInput($paramsList['big'][$key]))*$this->_moneyUnit;
							if(floatval($rtnStruc[$key]['sm'])>=floatval($rtnStruc[$key]['big'])){
								$this->addErr('102057');
								$this->res_add_url('充值相关配置','/admin/Rechargemange/index?parma=sv2&step='.$step,false,true);
								$this->res_show(true,true,'funds');
							}
						}
						$rtnStruc[$key]['type']  = isset($paramsList['radiodd'.$key]) ? intval(getSecurityInput($paramsList['radiodd'.$key])) : 1;
						if($rtnStruc[$key]['type'] =='1'){
							$rtnStruc[$key]['value'] = floatval(getSecurityInput($paramsList['setValue'][$key]))*$this->_moneyUnit;
						} else {
							$rtnStruc[$key]['value'] = floatval(getSecurityInput($paramsList['setValue'][$key]));
						}
					}
					$configArray['rtnStruc'] = $rtnStruc;
				}
				$configArray['code']       = getSecurityInput($paramsList['id']);
				$configArray['rtnMin']   = getSecurityInput($paramsList['rtnMin'])*$this->_moneyUnit;
				$configArray['rtnSet']   = getSecurityInput($paramsList['rtnSet']);
				$data['param'][]           = $configArray;
				//返现配置
				if(!$this->_queryAllBankDao->bankParamsSet_V2($data)){
					$this->log('保存银行返现配置失败');
				}
			}
		}
		
		//---------------获取银行列表----------------------------------------
		$bankList = array();
		$getdata['param']['userId'] = $this->_sessionlogin->info['id'];
		$res = $this->_queryAllBankDao->queryAllBank($getdata);
		if(isset($res["body"]["result"]["bankStruc"]) && count($res["body"]["result"]["bankStruc"])>0){
// 						$bankList = $res["body"]["result"]["bankStruc"];
			foreach($res["body"]["result"]["bankStruc"] as $key=>$value){
				//判断银行是否可用和是否可用来充值
				if($value['inUse'] !='0'){
					$bankList[$key] = $value;
					$bankList[$key]['upLimit']     = $value['upLimit']/$this->_moneyUnit;
					$bankList[$key]['lowLimit']    = $value['lowLimit']/$this->_moneyUnit;
					$bankList[$key]['vipUpLimit']  = $value['vipUpLimit']/$this->_moneyUnit;
					$bankList[$key]['vipLowLimit'] = $value['vipLowLimit']/$this->_moneyUnit;
					$bankList[$key]['daylimit']    = $value['daylimit']/$this->_moneyUnit;					
					$bankList[$key]['otheruplimit']     = $value['otheruplimit']/$this->_moneyUnit;
					$bankList[$key]['otherdownlimit']    = $value['otherdownlimit']/$this->_moneyUnit;
					$bankList[$key]['othervipuplimit']  = $value['othervipuplimit']/$this->_moneyUnit;
					$bankList[$key]['othervipdownlimit'] = $value['othervipdownlimit']/$this->_moneyUnit;
					$bankList[$key]['rtnMin']      = $value['rtnMin']/$this->_moneyUnit;
					if(!empty($value['rtnStruc'])){
						if(count($value['rtnStruc'])>0){
							foreach($value['rtnStruc'] as $sub_key=>$sub_value){
								$bankList[$key]['rtnStruc'][$sub_key]['sm'] = $sub_value['sm']/$this->_moneyUnit;
								$bankList[$key]['rtnStruc'][$sub_key]['big'] = $sub_value['big']/$this->_moneyUnit;
								if($sub_value['type'] == '1'){
									$bankList[$key]['rtnStruc'][$sub_key]['value'] = $sub_value['value']/$this->_moneyUnit;
								}
							}
						}
					}
				}
			}
		} else {
			$this->addErr('102047');
			$this->res_add_url('添加充值银行','/admin/Bankcardsmanage/index?parma=opt1');
			$this->res_show(true,true,'funds');
		}

		//---------------获取银行列表----------------------------------------
		if(empty($paramsList['id'])){
			$bank = current($bankList);
			$id = $bank['id'];
			unset($bank);
		} else {
			$id = $paramsList['id'];
		}
		//可充值的银行 为空
		if(count($bankList)>0){
			foreach ($bankList as $value){
				if($value['id'] == $id){
					//$value['rtnStruc'] = Zend_Json::decode('[{"big":"600","sm":"300","type":"2","value":"1"},{"big":"800","sm":"600","type":"1","value":"2"},{"big":"1000","sm":"800","type":"1","value":"3"},{"big":"2000","sm":"1000","type":"1","value":"4"},{"sm":"2000","type":"2","value":"5"}]');
					if(empty($value['rtnMin'])){
						$value['rtnMin'] ='0';
					}
					if(empty($value['rtnSet'])){
						$value['rtnSet'] ='2';
					}
					if(empty($value['rtnStruc']) || !is_array($value['rtnStruc'])){
						$value['rtnStruc'] = Zend_Json::decode('[{"big":"0","sm":'.$value['rtnMin'].',"type":"2","value":"0"},{"sm":"0","type":"1","value":"0"}]');
					}
					$this->view->retConfig = $value;
					$this->view->cnt = count($value['rtnStruc'])-1;
				}
			}
		} else {
			$this->log('没有可充值的银行');
		}
		//---------------获取倒计时配置----------------------------------------
		$chargeData["param"]["module"]="fund";
		$chargeData["param"]["function"]="chargeCountDown"; 
		$cdata =$this->_queryAllBankDao->getconfigCountDown($chargeData,'getconfigvaluebychargeCountDown');
		$chargeData["param"]["function"]="chargeCouteThired";
		$cdata1 =$this->_queryAllBankDao->getconfigCountDown($chargeData,'getconfigvaluebychargeCountDown');
		$chargeData["param"]["function"]="chargeCouteCaifu";
		$cdata2 =$this->_queryAllBankDao->getconfigCountDown($chargeData,'getconfigvaluebychargeCountDown');
		$chargeData["param"]["function"]="chargeCouteUnionpay";
		$cdata3 =$this->_queryAllBankDao->getconfigCountDown($chargeData,'getconfigvaluebychargeCountDown');
		$chargeData["param"]["function"]="chargeCouteAlipay";
		$cdata4 =$this->_queryAllBankDao->getconfigCountDown($chargeData,'getconfigvaluebychargeCountDown');
		$chargeData["param"]["function"]="chargeCouteAlipayBi";
		$cdata5 =$this->_queryAllBankDao->getconfigCountDown($chargeData,'getconfigvaluebychargeCountDown');	
		$chargeData["param"]["function"]="chargeCouteWechat";
		$cdata6 =$this->_queryAllBankDao->getconfigCountDown($chargeData,'getconfigvaluebychargeCountDown');		
		//---------------获取倒计时配置----------------------------------------
		$this->view->cdata = $cdata["body"]["result"]["val"];
		$this->view->cdata1 = $cdata1["body"]["result"]["val"];
		$this->view->cdata2 = $cdata2["body"]["result"]["val"];
		$this->view->cdata3 = $cdata3["body"]["result"]["val"];
		$this->view->cdata4 = $cdata4["body"]["result"]["val"];
		$this->view->cdata5 = $cdata5["body"]["result"]["val"];	
        $this->view->cdata6 = $cdata6["body"]["result"]["val"];	
		$this->view->index = $index; 
		$this->view->step = $step;
		$this->view->type = $type;
		$this->view->banklist = $bankList;
		$this->view->title = '充值相关配置';
		$this->view->display ( '/admin/funds/recharge/relatosettings.tpl' );
	}
	
	//获取用户层级
	public function getUserLevel($username=''){
		$name = getSecurityInput($this->_request->getPost('username',''));
		if(!empty($name)){
			$username = $name;
		}
		if(empty($username)){
			echo Zend_Json::encode(array('status'=>'0','data'=>$this->getError('1004')));
			exit;
		}
		if(!isset($this->_aAclArray[$this->_param])){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102144')));
			exit;
		}
		$userInfo = $this->getUserInfo($username);
		if(!$userInfo){
			echo Zend_Json::encode(array('status'=>'0','data'=>$this->getError('1003')));
			exit;
		}else {
			$aChainArray = array_filter(explode('/', $userInfo['userChain']));
			$userChain['top'] = array_shift($aChainArray);
			$low = end($aChainArray);
			if($low){
				$userChain['low'] = $low;
			} else {
				$userChain['low'] = '';
			}unset($low);
			array_pop($aChainArray);
			$userChain['mid'] = implode('>', $aChainArray);
			echo Zend_Json::encode(array('status'=>'1','data'=>$userChain));
			exit;
		}
	}
	//获取mow退款状态，更新异常充值状态
        public function checkAndUpdateRefundStatus(){
            $exceptId = getSecurityInput($this->_request->getPost('exceptId',0));
            $sn    = getSecurityInput($this->_request->getPost('sn',''));
            $mcSn    = getSecurityInput($this->_request->getPost('mcSn',''));
            $data = array();
            $data["param"]['exceptionId'] = $exceptId;
            $data["param"]['companyOrderNum'] = $sn;
            $data["param"]['mownecumOrderNum'] = $mcSn;
            $result = $this->_exceptionRefundDao->exceptionCheckAndUpdateReFundStatus($data);
            $resultStatus = $result['head']['status'];
            if($resultStatus==1){
                $resultBody = $result['body']['result'];
                $result['alertMsg'] = $this->_aRefundStatusMessage[$resultBody['status']];
            }else{
                $result = array();
                $result['alertMsg'] = '连线异常，请稍候重试';
            }
            echo Zend_Json::encode(array('status'=>$resultStatus,'data'=>$result));
            exit;
        }
	
	
	//====================================风险提现操作=========================================================
	
	//加载风险提现默认页面
	public function displayWithDrawPage(){
		$this->view->title = '风险提现处理';
		$this->view->aRiskType = $this->_aRiskType;
		$this->view->aWithDrawStatus = $this->_aWithDrawStatus;
                                             $this->view->aWithdrawModeStatus = $this->_aWithdrawModeStatus;
        $this->view->assign('allTips',$this->getTips(null,null,1));
		$this->view->display ( '/admin/funds/draw/drawdetail.tpl' );
	}

	//加载风险提现数据
	public function loadWithDrawData(){
		$account = $this->_sessionlogin->info['account'];
		$this->_page 	 = intval(getSecurityInput($this->_request->getPost("page",$this->_page)));
		$this->_pagesize = intval(getSecurityInput($this->_request->getPost("perPageNum",$this->_pagesize)));
		$data=array();
		$status = array(0,1,2,3,4,5,6,7);
		$request = $this->getRequest();
		//  	首次加载时不传相应值

		$Title = getSecurityInput($this->_request->getPost("Title",0)) ;
		$drawserial = getSecurityInput($this->_request->getPost("drawserial",0)) ;
		if($drawserial){
			$data["param"]["sn"]= $drawserial ;
		}
		$drawnumber = getSecurityInput($this->_request->getPost("drawnumber",0)) ;
		if($drawnumber){
			$data["param"]["applyCardNo"]= $drawnumber ;
		}
		$drawmoney1 = getSecurityInput($this->_request->getPost("drawmoney1",0)) ;
		if($drawmoney1){
			$data["param"]["withdrawAmtFrom"]  =  floatval($drawmoney1)*$this->_moneyUnit ;
		}
		$drawmoney2 = getSecurityInput($this->_request->getPost("drawmoney2",0)) ;
		if($drawmoney2){
			$data["param"]["withdrawAmtTo"]  =  floatval($drawmoney2)*$this->_moneyUnit ;
		}
		$drawrealmoney1 = getSecurityInput($this->_request->getPost("drawrealmoney1",0)) ;
		if($drawrealmoney1){
			$data["param"]["realWithDrawAmtFrom"]  =  floatval($drawrealmoney1)*$this->_moneyUnit ;
		}
		$drawrealmoney2 = getSecurityInput($this->_request->getPost("drawrealmoney2",0)) ;
		if($drawrealmoney2){
			$data["param"]["realWithDrawAmtTo"]  =  floatval($drawrealmoney2)*$this->_moneyUnit ;
		}
		$applyAccout = getSecurityInput($this->_request->getPost("drawname",0)) ;
		if($applyAccout){
			$data["param"]["applyAccount"]  =  $applyAccout ;
		}
		$riskstype = getSecurityInput($this->_request->getPost("riskstype")) ;
		if($riskstype!=''){
			$data["param"]["riskType"]  =  array(intval($riskstype)) ;
		}
		$isvip = getSecurityInput($this->_request->getPost("isvip","")) ;
		if($isvip !=''){
			$data["param"]["isVip"]  =  intval($isvip) ;
		}
		$AuditOneAdmmin = getSecurityInput($this->_request->getPost("AuditOneAdmmin",0)) ;
		if($AuditOneAdmmin){
			$data["param"]["apprAccount"]= $AuditOneAdmmin ;
		}
		$UserApplyTime1 = getSecurityInput($this->_request->getPost("UserApplyTime1",0)) ; 
		if($UserApplyTime1){
			$data["param"]["fromDate"]= getQueryStartTime($UserApplyTime1) ; //用户申请时间范围开始
		}
		$UserApplyTime2 = getSecurityInput($this->_request->getPost("UserApplyTime2",0)) ;
		if($UserApplyTime2){
			$data["param"]["toDate"]= getQueryEndTime($UserApplyTime2) ; // 用户申请时间范围结束
		}
		$AuditOneBeginTime1 = getSecurityInput($this->_request->getPost("AuditOneBeginTime1",0)) ;
		if($AuditOneBeginTime1){
			$data["param"]["yishenStartBegin"]= getQueryStartTime($AuditOneBeginTime1) ;
		}
		$AuditOneBeginTime2 = getSecurityInput($this->_request->getPost("AuditOneBeginTime2",0)) ;
		if($AuditOneBeginTime2){
			$data["param"]["yishenStartEnd"]= getQueryEndTime($AuditOneBeginTime2) ;
		}
		$AuditOneEndTime1 = getSecurityInput($this->_request->getPost("AuditOneEndTime1",0));
		if($AuditOneEndTime1){
			$data["param"]["yishenEndBegin"]=getQueryStartTime($AuditOneEndTime1);
		}
		$AuditOneEndTime2 = getSecurityInput($this->_request->getPost("AuditOneEndTime2",0)) ;
		if($AuditOneEndTime2){
			$data["param"]["yishenEndEnd"]=getQueryEndTime($AuditOneEndTime2);
		}
		$ReviewEndTime1 = getSecurityInput($this->_request->getPost("ReviewEndTime1",0)) ;
		if($ReviewEndTime1){
			$data["param"]["fromNoticeMowDate"] = getQueryStartTime($ReviewEndTime1) ;
		}
		$ReviewEndTime2 = getSecurityInput($this->_request->getPost("ReviewEndTime2",0)) ;
		if($ReviewEndTime2){
			$data["param"]["toNoticeMowDate"] = getQueryEndTime($ReviewEndTime2) ;
		}
		$ReviewAdmmin = getSecurityInput($this->_request->getPost("ReviewAdmmin",0)) ;
		if($ReviewAdmmin){
			$data["param"]["appr2Acct"] = $ReviewAdmmin ;
		}
		$MowReturnTime1 = getSecurityInput($this->_request->getPost("MowReturnTime1",0)) ;
		if($MowReturnTime1){
			$data["param"]["fromMowDate"] = getQueryStartTime($MowReturnTime1) ;
		}
                                             $withdrawMode = getSecurityInput($this->_request->getPost("applyChannel",0)) ;
                                             if($withdrawMode){
                                                                    $data["param"]["withdrawMode"] = $withdrawMode ;
                                             }
		$MowReturnTime2 = getSecurityInput($this->_request->getPost("MowReturnTime2",0)) ;
		if($MowReturnTime2){
			$data["param"]["toMowDate"] = getQueryEndTime($MowReturnTime2) ;
		}
		
		$aStatusArray = array(array(0,1,2,3,4,5,6,7),array(0),array(1),array(2),array(3,4,5,6,7));
		$searchStatus = getSecurityInput($this->_request->getPost('status',''));
		if(in_array($Title, array('0','4')) && $searchStatus!=''){
			$status = array(intval($searchStatus));
		} else {
			$status = $aStatusArray[$Title];
		}
		//是否只查看本人锁定订单
		if(in_array($Title, array('1','2'))){
			//$data["param"]['currFirst'] = 1;
		}
		$modata = array();
		$modatas = array();
		$recordNum = 0;
		$modatas['text'] = array();
		$modatas['count'] = array();
		if(!isset($this->_aAclArray[$this->_param][$Title])){
			header ( 'Content-Type: application/json;charset=utf-8' );
			echo json_encode($modatas);
			exit;
		}
		$data['param']['statuses'] = $status;
		$start = $this->_page+1 ;
		$data["pager"]["startNo"] = $this->_page*$this->_pagesize+1;
		$data["pager"]["endNo"] = $start*$this->_pagesize;
		$rsr = $this->_queryFundWithdrawOrder->queryFundWithdrawOrder($data);
		if(isset($rsr["result"])){
			foreach ( $rsr["result"] as $recorder){  
				$modata["sn"] 			= $recorder->getMember("sn") ;//申请单号
				$userBankStruc 			= $recorder->getMember("userBankStruc")  ;
				$modata["bankAccount"] 	= isset($userBankStruc["bankAccount"]) ? $userBankStruc["bankAccount"] : '';//
				$modata["id"] 			= isset($userBankStruc["id"]) ? $userBankStruc["id"] : '';//通过拒绝是根据这个id 来匹配的
				$modata["bankNumber"] 	= isset($userBankStruc["bankNumber"]) ? ($Title == '0' ? $this->getSecurityBankCardNum($userBankStruc["bankNumber"]) : $userBankStruc["bankNumber"]) : '';//
				$modata["BankName"] 	= isset($this->_bankIcoArray[$userBankStruc['bankId']]) ? $this->_bankIcoArray[$userBankStruc['bankId']]["name"] : '';//
				$modata["withdrawMode"] =  isset($this->_aWithdrawModeStatus[$recorder->getMember("withdrawMode")]) ? $this->_aWithdrawModeStatus[$recorder->getMember("withdrawMode")] : '' ;//申请渠道
                                                                                           $modata["withdrawAmt1"] = $recorder->getMember("withdrawAmt")/$this->_moneyUnit;//提现金额
				$modata["withdrawAmt"] 	= getMoneyFomat($modata["withdrawAmt1"],2) ;//提现金额
				$modata["realWithDrawAmt"] 	= $recorder->getMember("realWithDrawAmt")/$this->_moneyUnit;//提现金额
				$modata["realWithDrawAmt"] 	= getMoneyFomat($modata["realWithDrawAmt"],2) ;//提现金额
				$modata["applyAccount"] = $recorder->getMember("applyAccount")!=null ? $recorder->getMember("applyAccount") : '' ;//提现用户名
				$modata["ipAddr"] 		= long2ip($recorder->getMember("ipAddr")) ; //发起提现的ip地址
				$modata["applyTime"] 	= $recorder->getMember("applyTime") > 0 ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("applyTime"))) : '';//用户申请提现时间
				$modata["riskTypeNum"] 	= $recorder->getMember("riskType") ; //风险类型 0正常1 IP风险2黑名单卡提现3大额提现4频繁提现5全部审核
				$modata["riskType"] 	= isset($this->_aRiskType[$modata['riskTypeNum']]) ? $this->_aRiskType[$modata['riskTypeNum']] : "无类型";
				$modata["status"] 		= $recorder->getMember("status") ;
				$modata["status"] 		= isset($this->_aWithDrawStatus[$modata['status']]) ? $this->_aWithDrawStatus[$modata['status']] : "无类型";
				$modata["apprAccount"] 	= $recorder->getMember("apprAccount")!=null ? $recorder->getMember("apprAccount") : ''; //一审管理员
				$modata["beginApprTime"]= $recorder->getMember("apprBeginTime") > 0 ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("apprBeginTime"))) : ''; //一审开始时间
				$modata["apprTime"] 	= $recorder->getMember("apprTime") > 0 ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("apprTime"))) : '';//一审结束时间
				$modata["memo"] 		= $recorder->getMember("memo")!=null ? $recorder->getMember("memo") : '' ; //备注
				$modata["appr2Acct"] 	= $recorder->getMember("appr2Acct")!=null ? $recorder->getMember("appr2Acct") : '' ;//复审管理员
				$modata["Appr2Time"] 	= $recorder->getMember("appr2Time") > 0 ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("appr2Time"))) : '';//复审结束时间
				$modata["status"] 		= $recorder->getMember("status") ;
				$modata["status"] 		= isset($this->_aWithDrawStatus[$modata['status']]) ? $this->_aWithDrawStatus[$modata['status']] : "无类型";
				$modata["isVip"] 		= $recorder->getMember("isVip") =='1' ? $recorder->getMember("isVip") : '0' ; //是否vip
				$modata["mcNoticeTime"] = $recorder->getMember("mcNoticeTime") > 0 ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("mcNoticeTime"))) : ''; //mcNoticeTime  mow返回结果时间
				$modata["currApprer"]   = $recorder->getMember("currApprer")!='' ? $recorder->getMember("currApprer") : '';
				$modata["myLocked"]   	= $modata["currApprer"]!=''&& $modata["currApprer"]==$this->_sessionlogin->info['account'] ? true : false ;
				$modata["isAbleOprate"] = $modata["currApprer"]!=''&& $modata["currApprer"]!=$this->_sessionlogin->info['account'] ? false : true ;
				$modata["cancelAcct"] 	= $recorder->getMember("cancelAcct")!=null ? $recorder->getMember("cancelAcct") : '' ;//退款管理员
				$modata["cancelTime"]   = $recorder->getMember("cancelTime") > 0 ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("cancelTime"))) : ''; //退款时间 
				$modata["mcSn"] 		= $recorder->getMember("mcSn")            ? $recorder->getMember("mcSn") :'' ;  //MC單號
                                                                                           $modata["rootSn"] = $recorder->getMember("rootSn");
				$nowCheckUser = $this->redis_client->get('under_'.$modata["rootSn"]);
                                			$modata["nowCheckUser"] = $nowCheckUser!=null?$nowCheckUser:'' ;  //目前審核人員
                
                $modata["isShowReview"] = $recorder->getMember("isShowReview");
                $modata["seperateCount"] = $recorder->getMember("seperateCount");
                $modata["totalDrawAmount"] = $recorder->getMember("totalDrawAmount")/$this->_moneyUnit;
                $modata['tipData']= "提现用户名：".$modata['applyAccount']."<br>总提现金额：".$modata['totalDrawAmount']."元<br>主订单号：".$modata["rootSn"]."<br>提现账户名：".$modata['bankAccount']."<br>提现银行：".$modata['BankName']."<br>提现卡号：".
                                                                                         $modata['bankNumber']."<br>提现时间：".$modata['applyTime'] ."<br>拆分总单量：".$modata['seperateCount']."笔";

				//該資料是否被審核
                if($modata["nowCheckUser"]==null || $modata["nowCheckUser"]==$account){
                	$modata["isCheck"]= false;                	
                }else{
                	$modata["isCheck"]= true;
                }
				$attachment = $recorder->getMember("attachment");
				if(!empty($attachment)){
					$attachmentArray = explode('|', $attachment);
					$attachment= '';
					foreach ($attachmentArray as $key=>$value){
						if(!empty($value)){
							$attachment .= '<a href="'.$this->_config->dynamicroot.'/upload/images/'.$value.'" target="_blank">附件'.($key+1).'</a>,';
						}
					}
				}
				$modata["attach"] = trim($attachment,",");
				array_push($modatas['text'],$modata) ;
			}
                        
                        
		}
		if(isset($rsr["pager"]["total"])){
			$recordNum = $rsr["pager"]["total"];
		}
		array_push($modatas['count'],array("recordNum"=>$recordNum)) ; //recordNum 页数 ，每页15条
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode($modatas) ;
	}

	//人工处理
	public function manualWithdrawOperate(){
		$sn = getSecurityInput($this->_request->getPost('sn',''));
        $rootSn = getSecurityInput($this->_request->getPost('rootSn',''));
        $nowCheckUser = $this->redis_client->get('under_'.$rootSn);
		$account = $this->_sessionlogin->info['account'];
		$isCheck = false;
		if($nowCheckUser==null|| $nowCheckUser == $account){
			$nowCheckUser = $account;
			$this->redis_client->set('under_'.$rootSn,$nowCheckUser,60*4);
		}else{
			$isCheck = true;
		}
		if(empty($sn)){
			echo Zend_Json::encode(array('status'=>'error','data'=>'申请单号不能为空!'));
			exit;
		}
		$aUrl['fundadmin'] = 'manualWithdraw';
		$data = array();
		$data["param"]["sn"] = $sn;
		$data['param']["approveAct"]= $this->_sessionlogin->info['account'];
		$res = $this->_clientobject->inRequestV2($data, $aUrl);  
		$modata= array();
		$modata['isCheck'] = $isCheck;
		if(isset($res['head']['status']) && $res['head']['status'] == '0'){  
			echo Zend_Json::encode(array('status'=>'ok','data'=>$modata));
			exit;
		} else { //锁定失败 刷新页面
			echo Zend_Json::encode(array('status'=>'error','data'=>'操作失败!'));
			exit;
		} 
	}
	//人工完成
	public function manualFinishOperate(){
		$sn = getSecurityInput($this->_request->getPost('sn',''));
        $rootSn = getSecurityInput($this->_request->getPost('rootSn',''));
        $nowCheckUser = $this->redis_client->get('under_'.$rootSn);
		$account = $this->_sessionlogin->info['account'];
		$isCheck = false;
		if($nowCheckUser==null|| $nowCheckUser == $account){
			$nowCheckUser = $account;
			$this->redis_client->set('under_'.$rootSn,$nowCheckUser,60*4);
		}else{
			$isCheck = true;
		}
		if(empty($sn)){
			echo Zend_Json::encode(array('status'=>'error','data'=>'申请单号不能为空!'));
			exit;
		}
		$aUrl['fundadmin'] = 'manualFinish';
		$data = array();
		$data["param"]["sn"] = $sn;
		$res = $this->_clientobject->inRequestV2($data, $aUrl);  
		$modata= array();
		$modata['isCheck'] = $isCheck;
		if(isset($res['head']['status']) && $res['head']['status'] == '0'){  
			echo Zend_Json::encode(array('status'=>'ok','data'=>$modata));
			exit;
		} else { //锁定失败 刷新页面
			echo Zend_Json::encode(array('status'=>'error','data'=>'操作失败!'));
			exit;
		} 
	}
	
	//加载提现操作界面
	public function displayWithDrawOpratePage(){
		$sn = getSecurityInput($this->_request->getPost('sn',''));
        $rootSn = getSecurityInput($this->_request->getPost('rootSn',''));
        $nowCheckUser = $this->redis_client->get('under_'.$rootSn);
		$account = $this->_sessionlogin->info['account'];
		$isCheck = false;
		if($nowCheckUser==null|| $nowCheckUser == $account){
			$nowCheckUser = $account;
			$this->redis_client->set('under_'.$rootSn,$nowCheckUser,60*4);
		}else{
			$isCheck = true;
		}
		
		$type  = getSecurityInput($this->_request->getPost('type',0));
		$title = intval(getSecurityInput($this->_request->getPost('Title',0)));
		if($title == '2'){
			$type = $type + 3;
		}
		if(!isset($this->_aAclArray[$this->_param][$type])){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102144')));
			exit;
		}
		if(empty($sn)){
			echo Zend_Json::encode(array('status'=>'error','data'=>'申请单号不能为空!'));
			exit;
		}
		$data["param"]["sn"] = $sn;
		$rsr = $this->_queryFundWithdrawOrder->queryFundWithdrawOrderBySn($data);
		$modata = array();
		$recorder = $rsr["result"][0];
		$userBankStruc 			= $recorder->getMember("userBankStruc")  ;
		$modata["bankAccount"] 	= $userBankStruc["bankAccount"] !=null ? $userBankStruc["bankAccount"] : '';//
		$modata["bankNumber"] 	= $userBankStruc["bankNumber"] !=null ? $userBankStruc["bankNumber"] : '';//
		$modata["BankName"] 	= $this->_bankIcoArray[$userBankStruc['bankId']]["name"] ;//
		$modata["id"] 			= $recorder->getMember("id");
		$modata["applyAccount"] = $recorder->getMember("applyAccount")!=null ? $recorder->getMember("applyAccount") : '' ;//
		$modata["withdrawAmt"]  = $recorder->getMember("withdrawAmt") ;//
		$modata["withdrawAmt"]  = getMoneyFomat($modata["withdrawAmt"]/$this->_moneyUnit,2) ;//提现额度
		$modata["riskType"] 	= $recorder->getMember("riskType") ;
		$modata["riskType"] 	= isset($this->_aRiskType[$modata['riskType']]) ? $this->_aRiskType[$modata['riskType']] : "无类型";
		$modata["memo"] 		= $recorder->getMember("memo") ? $recorder->getMember("memo") : '' ;
		$modata["currApprer"] 	= $recorder->getMember("currApprer") ;
		$modata["currDate"] 	= $recorder->getMember("currDate") ;
		$modata["isAbleOprate"] = $modata["currApprer"]!=''&& $modata["currApprer"]!=$this->_sessionlogin->info['account'] ? false : true ;
		$modata["isAgainAppeal"] = $this->queryAndChcekHaveAppeal($rootSn) ;   //是否已經有申訴單
		$modata['nowCheckUser'] = $nowCheckUser;
		$modata['isCheck'] = $isCheck;
		$attachment = $recorder->getMember("attachment");
		if(!empty($attachment)){
			$attachmentArray = explode('|', $recorder->getMember("attachment"));
			$attachment= '';
			foreach ($attachmentArray as $key=>$value){
				$attachment .= '<a href="'.$this->_config->dynamicroot.'/upload/images/'.$value.'" target="_blank">附件'.($key+1).'</a>,';
			}
		} else {
			$attachment ='';
		}
		$modata["attach"] = trim($attachment,",");
		if($modata['currApprer'] !='' & $modata['currApprer'] !=$this->_sessionlogin->info['account']){
			echo Zend_Json::encode(array('status'=>'error','data'=>'该数据正被 '.$modata['currApprer'].' 审核!'));
			exit;
		} else {
			//锁定该条数据的代码逻辑===================================
                        // M#0005648 第一次打开“通过”按钮的时间，后面再打开不变更
                        $res['head']['status'] = 0;
                        $apprBeginTime = $recorder->getMember("apprBeginTime") ;
                        if ( empty($apprBeginTime) ) {
                            $aUrl['fundadmin'] = 'yuchulidraw';
                            $data2['param']['exceptId'] = $modata["id"];
                            $data2['param']['currStatus'] = $title;
                            $res = $this->_clientobject->inRequestV2($data2, $aUrl);
                        }
			//锁定逻辑=================================================
			if(isset($res['head']['status']) && $res['head']['status'] == '0'){ //锁定成功 把错做页面所需要的数据返回给页面
				echo Zend_Json::encode(array('status'=>'ok','data'=>$modata));
				exit;
			} else { //锁定失败 刷新页面
				echo Zend_Json::encode(array('status'=>'error','data'=>'锁定失败!'));
				exit;
			}
		}
	}
        
        private function queryAndChcekHaveAppeal($sn){
            $isHaveAppeal = 'N';
            $data2 = array(
            'param' => array(
            'withdrawSn' => $sn                                  
                 )
             );
            $aUri['fundadmin'] = 'queryAppealbySn';
            $result = $this->_clientobject->inRequestV2($data2, $aUri);
            $appealrecorder = $result["body"]["result"][0];
            if($appealrecorder!=null){
                $isHaveAppeal = $appealrecorder["isHaveAppeal"];
            }
            return $isHaveAppeal;
        }
        
        private function getTips($tipGroupA=null,$tipGroupB=null,$tipModel=null){
        
        	$result = array();
            $unCheckrejectTips = array();
            $unCheckpassTips = array();
            $unCheckcheckTips = array();
            $reviewrejectTips = array();
            $reviewpassTips = array();
            $reviewrcheckTips = array();
            $aUrl['fundadmin'] = 'searchTips';
            $data['param']['tips']['tipsGroupa'] = $tipGroupA;
            $data['param']['tips']['tipsGroupb'] = $tipGroupB;
            $data['param']['tips']['tipsModel'] = $tipModel;
            $res = $this->_clientobject->inRequestV2($data, $aUrl);
            foreach($res["body"]["result"]["tipsList"] as $recorder){
            	$groupa = $recorder['tipsGroupa'];
            	$groupb = $recorder['tipsGroupb'];
                switch($groupb){
                    case 0:
                    	if($groupa=='0'){
                    		array_push($unCheckrejectTips, $recorder);                    		
                    	}else{
                    		array_push($reviewrejectTips, $recorder);                    		
                    	}
                        break;
                    case 1:
                    	if($groupa=='0'){
                    		array_push($unCheckpassTips, $recorder);
                    	}else{
                    		array_push($reviewpassTips, $recorder);
                    	}                    	
                        break;
                    case 2:
                    	if($groupa=='0'){
                    		array_push($unCheckcheckTips, $recorder);
                    	}else{
                    		array_push($reviewrcheckTips, $recorder);
                    	}
                        break;
                }
            }
            $result['unCheckrejectTips']=$unCheckrejectTips;
            $result['reviewrejectTips']=$reviewrejectTips;
            $result['unCheckpassTips']=$unCheckpassTips;
            $result['reviewpassTips']=$reviewpassTips;
            $result['unCheckcheckTips']=$unCheckcheckTips;
            $result['reviewrcheckTips']=$reviewrcheckTips;            
            return $result;
        }
	
	//加载退款操作界面
	public function displayWithDrawOpratePage2(){
		$sn    = getSecurityInput($this->_request->getPost('sn',''));
		$nowCheckUser = $this->redis_client->get('under_'.$sn);
		$account = $this->_sessionlogin->info['account'];
		$isCheck = false;
		if($nowCheckUser==null|| $nowCheckUser == $account){
			$nowCheckUser = $account;
			$this->redis_client->set('under_'.$sn,$nowCheckUser,60*4);
		}else{
			$isCheck = true;
		}
		$type  = getSecurityInput($this->_request->getPost('type',0));
		$title = intval(getSecurityInput($this->_request->getPost('Title',0)));
		if($title == '2'){
			$type = $type + 3;
		}
		if(!isset($this->_aAclArray[$this->_param][$type])){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102144')));
			exit;
		}
		if(empty($sn)){
			echo Zend_Json::encode(array('status'=>'error','data'=>'申请单号不能为空!'));
			exit;
		}
		$data["param"]["sn"] = $sn;
		$data["param"]["currApprer"] = $this->_sessionlogin->info['account'];
		$rsr = $this->_queryFundWithdrawOrder->queryReFundWithdrawOrder($data);
		$modata = array();
		$recorder = $rsr["result"][0];
		$userBankStruc 			= $recorder->getMember("userBankStruc")  ;
		$modata["bankAccount"] 	= $userBankStruc["bankAccount"] !=null ? $userBankStruc["bankAccount"] : '';//
		$modata["bankNumber"] 	= $userBankStruc["bankNumber"] !=null ? $userBankStruc["bankNumber"] : '';//
		$modata["BankName"] 	= $this->_bankIcoArray[$userBankStruc['bankId']]["name"] ;//
		$modata["id"] 			= $recorder->getMember("id");
		$modata["sn"] 			= $recorder->getMember("sn");
		$modata["applyAccount"] = $recorder->getMember("applyAccount")!=null ? $recorder->getMember("applyAccount") : '' ;//
		$modata["withdrawAmt"]  = $recorder->getMember("withdrawAmt") ;//
		$modata["withdrawAmt"]  = getMoneyFomat($modata["withdrawAmt"]/$this->_moneyUnit,2) ;//提现额度
		$modata["riskType"] 	= $recorder->getMember("riskType") ;
		$modata["riskType"] 	= isset($this->_aRiskType[$modata['riskType']]) ? $this->_aRiskType[$modata['riskType']] : "无类型";
		$modata["memo"] 		= $recorder->getMember("memo") ? $recorder->getMember("memo") : '' ;
		$modata["currApprer"] 	= $recorder->getMember("currApprer") ;
		$modata["currDate"] 	= $recorder->getMember("currDate") ;
		$modata["isAbleOprate"] = $modata["currApprer"]!=''&& $modata["currApprer"]!=$this->_sessionlogin->info['account'] ? false : true ;
		$modata['nowCheckUser'] = $nowCheckUser;
		$modata['isCheck'] = $isCheck;
		$attachment = $recorder->getMember("attachment");
		if(!empty($attachment)){
			$attachmentArray = explode('|', $recorder->getMember("attachment"));
			$attachment= '';
			foreach ($attachmentArray as $key=>$value){
				$attachment .= '<a href="'.$this->_config->dynamicroot.'/upload/images/'.$value.'" target="_blank">附件'.($key+1).'</a>,';
			}
		} else {
			$attachment ='';
		}
		$modata["attach"] = trim($attachment,",");
		//锁定该条数据的代码逻辑===================================
		// M#0005648 第一次打开“通过”按钮的时间，后面再打开不变更
		$res['head']['status'] = 0;
		$apprBeginTime = $recorder->getMember("apprBeginTime") ;
		if ( empty($apprBeginTime) ) {
			$aUrl['fundadmin'] = 'yuchulidraw';
			$data2['param']['exceptId'] = $modata["id"];
			$data2['param']['currStatus'] = $title;
			$res = $this->_clientobject->inRequestV2($data2, $aUrl);
		}
		//锁定逻辑=================================================
		if(isset($res['head']['status']) && $res['head']['status'] == '0'){ //锁定成功 把错做页面所需要的数据返回给页面
			echo Zend_Json::encode(array('status'=>'ok','data'=>$modata));
			exit;
		} else { //锁定失败 刷新页面
			echo Zend_Json::encode(array('status'=>'error','data'=>'锁定失败!'));
			exit;
		}
	}
	
	
	
	//加载獲得目前狀態界面
	public function queryWithDrawNowStatus(){
		$sn    = getSecurityInput($this->_request->getPost('sn',''));
		$type  = getSecurityInput($this->_request->getPost('type',0));
		$title = intval(getSecurityInput($this->_request->getPost('Title',0)));
		if($title == '2'){
			$type = $type + 3;
		}
		if(!isset($this->_aAclArray[$this->_param][$type])){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102144')));
			exit;
		}
		if(empty($sn)){
			echo Zend_Json::encode(array('status'=>'error','data'=>'申请单号不能为空!'));
			exit;
		}
		$data["param"]["companyOrderNum"] = $sn;
		$data["param"]["mownecumOrderNum"] = $sn;
		$data["param"]["sn"] = $sn;
		$recorder = $this->_queryFundWithdrawOrder->checkAndUpdateWithDrawMowStatus($data);
		if(empty($recorder)){
			echo Zend_Json::encode(array('status'=>'error','data'=>'连线异常，请稍候重试'));
			exit;
		}
		$modata = array();
         $modata['withdrawMode']                                                = $recorder->getMember("withdrawMode")            ? $recorder->getMember("withdrawMode") :'' ; //MOW_API_平台訂單號
         $modata["mowApiStatus"] 		= $recorder->getMember("mowApiStatus")            ? $recorder->getMember("mowApiStatus") :'0' ; //MOW_API_狀態 
             
         if($modata['withdrawMode']){
             if($modata['withdrawMode']==1){
                 $modata["mowApiStatusC"] 		= isset($this->_aWithDrawStatusMessage[$modata['mowApiStatus']]) ? $this->_aWithDrawStatusMessage[$modata['mowApiStatus']] : "无效接口请求";  //MOW_API 狀態中文
                 $modata["mowApiStatusCD"] 		= isset($this->_aWithDrawStatusMessageDetail[$modata['mowApiStatus']]) ? $this->_aWithDrawStatusMessageDetail[$modata['mowApiStatus']] : "请立即联系技术处理";  //MOW_API 狀態中文
             }else{
                 $modata["mowApiStatusC"] 		= isset($this->_thWithDrawStatusMessage[$modata['mowApiStatus']]) ? $this->_thWithDrawStatusMessage[$modata['mowApiStatus']] : "无效接口请求";  //TH_API 狀態中文
                 $modata["mowApiStatusCD"] 		= "";
             }
             $modata["companyOrderNum"] 		= $recorder->getMember("companyOrderNum")            ? $recorder->getMember("companyOrderNum") :'' ; //MOW_API_平台訂單號
             $modata["mownecumOrderNum"] 		= $recorder->getMember("mownecumOrderNum")            ? $recorder->getMember("mownecumOrderNum") :'' ; //MOW_API_DP訂單號    
         }
                                             
		
		if($modata['currApprer'] !='' & $modata['currApprer'] !=$this->_sessionlogin->info['account']){
			echo Zend_Json::encode(array('status'=>'error','data'=>'该数据正被 '.$modata['currApprer'].' 审核!'));
			exit;
		} else {
			//锁定该条数据的代码逻辑===================================
            // M#0005648 第一次打开“通过”按钮的时间，后面再打开不变更
            $res['head']['status'] = 0;
            $apprBeginTime = $recorder->getMember("apprBeginTime") ;
           
			//锁定逻辑=================================================
			if(isset($res['head']['status']) && $res['head']['status'] == '0'){ //锁定成功 把错做页面所需要的数据返回给页面
				echo Zend_Json::encode(array('status'=>'ok','data'=>$modata));
				exit;
			} else { //锁定失败 刷新页面
				echo Zend_Json::encode(array('status'=>'error','data'=>'锁定失败!'));
				exit;
			}
		}
	}
	
	
	
	
	//风险提现一审操作
	public function auditFundWithdraw(){
		$attachMent = '';
		$id			= floatval(getSecurityInput($this->_request->getPost('drawbankID','')));
		//當審核決定時,將畫面鎖控打開
		$this->cancelRedisLock($id);
		$applyMemo	= getSecurityInput($this->_request->getPost('applyMemo',''));
		$status		= intval(getSecurityInput($this->_request->getPost('status','1')));
		$isAppeal		= getSecurityInput($this->_request->getPost('isAppeal','0'));
		$appealTips		= getSecurityInput($this->_request->getPost('appealTips'));
		if(!isset($this->_aAclArray[$this->_param][$status])){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102144')));
			exit;
		}
		if(count($this->_uploadsession->attachments)>0){
			$attachMent = implode('|', $this->_uploadsession->attachments);
			unset($this->_uploadsession->attachments);
		}
		//寫一個 Lock key (第一個會成功True 在極短時間內第二個會失敗False)
        if($this->redis_client->setnx(md5('fundaudit2' . $Id), 5))
        {
            //設定存活時間
            $this->redis_client->expire(md5('fundaudit2' . $Id), 5);
        }
        else
        {
            echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('903')));
            exit;
        }
		$data = array();
		$data['param']["id"]		= $id;
		$data['param']["approveAct"]= $this->_sessionlogin->info['account'];
		$data['param']["memo"]	    = $applyMemo;
		$data['param']["attach"] 	= $attachMent;
		$data['param']["status"] 	= $status;
		$data['param']["isAppeal"] 	= $isAppeal;
		$data['param']["appealTips"] 	= $appealTips;
                                 
		$res = $this->_fundWithdraw->auditFundWithdraw($data);
		if(isset($res['head']['status']) && $res['head']['status'] =='0'){
			//解锁逻辑===================
			$aUrl['fundadmin'] = 'yuchulidrawEnd';
			$data['param']['exceptId'] = $id;
			$data['param']['currStatus '] = 1;
			$res = $this->_clientobject->inRequestV2($data, $aUrl);
			if(isset($res['head']['status']) && $res['head']['status'] == '0'){
				echo Zend_Json::encode(array('status'=>'ok','data'=>'审核成功'));
				exit;
			} else {
				echo Zend_Json::encode(array('status'=>'error','data'=>'解锁失败'));
				exit;
			}
			//===========================
		} else if($res['head']['status'] =='100001'){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError($res['head']['status'])));
			exit;
		}else if($res['head']['status'] =='100002'){
			echo Zend_Json::encode(array('status'=>'error','data'=>'订单已被处理过'));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','data'=>'审核失败'));
			exit;
		}
	}
	
	//风险提现二审操作
	public function auditFundWithdraw2(){
		$Id     = floatval(getSecurityInput($this->_request->getPost("drawbankID")));
		//當審核決定時,將畫面鎖控打開
		$this->cancelRedisLock($Id);
		$status = intval(getSecurityInput($this->_request->getPost("status",2)));
		$applyMemo	= getSecurityInput($this->_request->getPost('applyMemo',''));
		$appealTips		= getSecurityInput($this->_request->getPost('appealTips'));
		if(!isset($this->_aAclArray[$this->_param][$status])){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102144')));
			exit;
		}
		//寫一個 Lock key (第一個會成功True 在極短時間內第二個會失敗False)
        if($this->redis_client->setnx(md5('fundaudit2' . $Id), 5))
        {
            //設定存活時間
            $this->redis_client->expire(md5('fundaudit2' . $Id), 5);
        }
        else
        {
            echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('903')));
            exit;
        }
		$data = array();
		$data['param']["id"]		= $Id;
		$data['param']["approveAct"]= $this->_sessionlogin->info['account'];
		$data['param']["status"] 	= $status;
		$data['param']["memo"]	 	= $applyMemo;
		$data['param']["appealTips"] 	= $appealTips;		
		$res = $this->_fundWithdraw->auditFundWithdraw2($data);
		if(isset($res['head']['status']) && $res['head']['status'] =='0'){
			//解锁逻辑===================
			$aUrl['fundadmin'] = 'yuchulidrawEnd';
			$data['param']['exceptId'] = $Id;
			$data['param']['currStatus '] = 2;
			$res = $this->_clientobject->inRequestV2($data, $aUrl);
			if(isset($res['head']['status']) && $res['head']['status'] == '0'){
				echo Zend_Json::encode(array('status'=>'ok','data'=>'审核成功'));
				exit;
			} else {
				echo Zend_Json::encode(array('status'=>'error','data'=>'解锁失败'));
				exit;
			}
			//===========================
		} else if($res['head']['status'] =='100001'){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError($res['head']['status'])));
			exit;
		} else if($res['head']['status'] =='100002'){
			echo Zend_Json::encode(array('status'=>'error','data'=>'订单已被处理过'));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','data'=>'审核失败'));
			exit;
		}
	}
	
	//风险提现--退款操作
	public function auditRefund(){
		$attachMent = '';
		$Id     = floatval(getSecurityInput($this->_request->getPost("drawbankID")));
		$sn     = getSecurityInput($this->_request->getPost("drawbankSN"));
		//當審核決定時,將畫面鎖控打開
		$this->cancelRedisLock($Id);
		$status = intval(getSecurityInput($this->_request->getPost("status")));
		$applyMemo	= getSecurityInput($this->_request->getPost('applyMemo',''));
		if(!isset($this->_aAclArray[$this->_param][$status])){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102144')));
			exit;
		}
		if(count($this->_uploadsession->attachments)>0){
			$attachMent = implode('|', $this->_uploadsession->attachments);
			unset($this->_uploadsession->attachments);
		}
		
		$data = array();
		$data['param']["id"]		= $Id;
		$data['param']["sn"]		= $sn;
		$data['param']["approveAct"]= $this->_sessionlogin->info['account'];
		$data['param']["status"] 	= $status;
		$data['param']["memo"]	 	= $applyMemo;
		$data['param']["attach"] 	= $attachMent;
		$res = $this->_fundWithdraw->auditRefund($data);
		if(isset($res['head']['status']) && $res['head']['status'] =='0'){
			//解锁逻辑===================
			$aUrl['fundadmin'] = 'yuchulidrawEndRefund';
			$data['param']['exceptId'] = $Id;
			$data['param']['currStatus '] = 2;
			$res = $this->_clientobject->inRequestV2($data, $aUrl);
			if(isset($res['head']['status']) && $res['head']['status'] == '0'){
				echo Zend_Json::encode(array('status'=>'ok','data'=>'审核成功'));
				exit;
			} else {
				echo Zend_Json::encode(array('status'=>'error','data'=>'解锁失败'));
				exit;
			}
			//===========================
		} else if($res['head']['status'] =='100001'){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError($res['head']['status'])));
			exit;
		} else if($res['head']['status'] =='100002'){
			echo Zend_Json::encode(array('status'=>'error','data'=>'订单已被处理过'));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','data'=>'审核失败'));
			exit;
		}
	}
	

	//后台客户提取备注
	public function withdrawremarkAction(){
		
		$data = array();
		$data["Id"]="";
		$data["approveAct"]="";
		$data["status"]="";
		$this->_fundWithdraw->withdrawRemark($data);
	}
	
	//导出风险提现数据
	public function downloadWithDrawData(){
		$Title = getSecurityInput($this->_request->get("Title",0)) ;
		$fileName = '风险提现';
		$aTitle = array(
				'sn'			=> '申请单号',
				'BankName'		=> '提现银行',
				'bankAccount'	=> '提现账户名',
				'bankNumber'	=> '提现卡号',
				'withdrawMode'	=> '提现渠道',				
				'withdrawAmt'	=> '提现金额',
				'realWithDrawAmt'=> '实际提现金额',
				'applyAccount'	=> '提现用户名',
				'isVip'			=> '是否VIP',
				'ipAddr'		=> 'IPv4地址',
				'applyTime'		=> '用户申请提现时间',
				'riskType'		=> '风险类型',
				'apprAccount'	=> '一审管理员',
				'beginApprTime'	=> '一审开始时间',
				'apprTime'		=> '一审结束时间',
				'Appr2Time'		=> '复审结束时间',
				'appr2Acct'		=> '复审管理员',
				'mcNoticeTime'	=> 'mow返回结果时间',
				'status'		=> '状态',
				'memo'			=> '备注'
		);
		$intArray = array('bankNumber');
		$data=array();
		if($Title == '0'){
			unset($aTitle['realWithDrawAmt']);
		}
		//检查权限
		if(!isset($this->_aAclArray[$this->_param][$Title])){
			$this->_redirect('/admin/Rechargemange/index?parma=sv3&tabIndex='.$Title);
		}
		$drawserial = getSecurityInput($this->_request->get("drawserial",0)) ;
		if($drawserial){
			$data["param"]["sn"]= $drawserial ;
		}
		$drawnumber = getSecurityInput($this->_request->get("drawnumber",0)) ;
		if($drawnumber){
			$data["param"]["applyCardNo"]= $drawnumber ;
		}
		$drawmoney1 = getSecurityInput($this->_request->get("drawmoney1",0)) ;
		if($drawmoney1){
			$data["param"]["withdrawAmtFrom"]  =  floatval($drawmoney1)*$this->_moneyUnit ;
		}
		$drawmoney2 = getSecurityInput($this->_request->get("drawmoney2",0)) ;
		if($drawmoney2){
			$data["param"]["withdrawAmtTo"]  =  floatval($drawmoney2)*$this->_moneyUnit ;
		}
		$applyChannel = getSecurityInput($this->_request->get("applyChannel",0)) ;
		if($applyChannel){
			$data["param"]["withdrawMode"]= $applyChannel ;
		}
		$drawrealmoney1 = getSecurityInput($this->_request->get("drawrealmoney1",0)) ;
		if($drawrealmoney1){
			$data["param"]["realWithDrawAmtFrom"]  =  floatval($drawrealmoney1)*$this->_moneyUnit ;
		}
		$drawrealmoney2 = getSecurityInput($this->_request->get("drawrealmoney2",0)) ;
		if($drawrealmoney2){
			$data["param"]["realWithDrawAmtTo"]  =  floatval($drawrealmoney2)*$this->_moneyUnit ;
		}
		$applyAccout = getSecurityInput($this->_request->get("drawname",0)) ;
		if($applyAccout){
			$data["param"]["applyAccount"]  =  $applyAccout ;
		}
		$riskstype = getSecurityInput($this->_request->get("riskstype",-1)) ;
		if($riskstype!=''){
			$data["param"]["riskstype"]  =  intval($riskstype) ;
		}
		$isvip = getSecurityInput($this->_request->get("isvip","")) ;
		if($isvip !=''){
			$data["param"]["isVip"]  =  intval($isvip) ;
		}
		$AuditOneAdmmin = getSecurityInput($this->_request->get("AuditOneAdmmin",0)) ;
		if($AuditOneAdmmin){
			$data["param"]["apprAccount"]= $AuditOneAdmmin ;
		}
		$UserApplyTime1 = getSecurityInput($this->_request->get("UserApplyTime1",0)) ; 
		if($UserApplyTime1){
			$data["param"]["fromDate"]= getQueryStartTime($UserApplyTime1) ; //用户申请时间范围开始
		}
		$UserApplyTime2 = getSecurityInput($this->_request->get("UserApplyTime2",0)) ;
		if($UserApplyTime2){
			$data["param"]["toDate"]= getQueryEndTime($UserApplyTime2) ; // 用户申请时间范围结束
		}
		$AuditOneBeginTime1 = getSecurityInput($this->_request->get("AuditOneBeginTime1",0)) ;
		if($AuditOneBeginTime1){
			$data["param"]["yishenStartBegin"]= getQueryStartTime($AuditOneBeginTime1) ;
		}
		$AuditOneBeginTime2 = getSecurityInput($this->_request->get("AuditOneBeginTime2",0)) ;
		if($AuditOneBeginTime2){
			$data["param"]["yishenStartEnd"]= getQueryEndTime($AuditOneBeginTime2) ;
		}
		$AuditOneEndTime1 = getSecurityInput($this->_request->get("AuditOneEndTime1",0));
		if($AuditOneEndTime1){
			$data["param"]["yishenEndBegin"]=getQueryStartTime($AuditOneEndTime1);
		}
		$AuditOneEndTime2 = getSecurityInput($this->_request->get("AuditOneEndTime2",0)) ;
		if($AuditOneEndTime2){
			$data["param"]["yishenEndEnd"]=getQueryEndTime($AuditOneEndTime2);
		}
		$ReviewEndTime1 = getSecurityInput($this->_request->get("ReviewEndTime1",0)) ;
		if($ReviewEndTime1){
			$data["param"]["fromNoticeMowDate"] = getQueryStartTime($ReviewEndTime1) ;
		}
		$ReviewEndTime2 = getSecurityInput($this->_request->get("ReviewEndTime2",0)) ;
		if($ReviewEndTime2){
			$data["param"]["toNoticeMowDate"] = getQueryEndTime($ReviewEndTime2) ;
		}
		$ReviewAdmmin = getSecurityInput($this->_request->get("ReviewAdmmin",0)) ;
		if($ReviewAdmmin){
			$data["param"]["appr2Acct"] = $ReviewAdmmin ;
		}
		$MowReturnTime1 = getSecurityInput($this->_request->get("MowReturnTime1",0)) ;
		if($MowReturnTime1){
			$data["param"]["fromMowDate"] = getQueryStartTime($MowReturnTime1) ;
		}
		$MowReturnTime2 = getSecurityInput($this->_request->get("MowReturnTime2",0)) ;
		if($MowReturnTime2){
			$data["param"]["toMowDate"] = getQueryEndTime($MowReturnTime2);
		}
		$aStatusArray = array(array(0,1,2,3,4,5,6,7),4=>array(3,4,5,6));
		$searchStatus = getSecurityInput($this->_request->get('status',''));
		if(in_array($Title, array('0','4'))){
			if($searchStatus!=''){
				$status = array(intval($searchStatus));
			} else {
				$status = $aStatusArray[$Title];
			}
		} else {
			$status = array(3,4,5,6);
		}
		$searchStatus = getSecurityInput($this->_request->get('status',''));
		if($searchStatus!=''){
			$status = array(intval($searchStatus));
		}
		$data['param']['statuses'] = $status;
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
			$rsr = $this->_queryFundWithdrawOrder->queryFundWithdrawOrder($data);
			if(isset($rsr["result"]) && count($rsr["result"])>0){
				foreach ( $rsr["result"] as  $key=>$recorder){
					$modata["sn"] 			= $recorder->getMember("sn") ;//申请单号
					$userBankStruc 			= $recorder->getMember("userBankStruc")  ;
					$modata["bankAccount"] 	= $userBankStruc["bankAccount"];//
					$modata["bankNumber"] 	= $Title == '0' ? $this->getSecurityBankCardNum($userBankStruc["bankNumber"]) : $userBankStruc["bankNumber"];//
					$modata["BankName"] 	= isset($this->_bankIcoArray[$userBankStruc['bankId']]["name"]) ? $this->_bankIcoArray[$userBankStruc['bankId']]["name"] : '' ;//
					$modata["withdrawAmt"] 	= $recorder->getMember("withdrawAmt")/$this->_moneyUnit;//提现金额
					$modata["withdrawAmt"] 	= getMoneyFomat($modata["withdrawAmt"],2) ;//提现金额
					$modata["realWithDrawAmt"] 	= $recorder->getMember("realWithDrawAmt")/$this->_moneyUnit;//实际提现金额
					$modata["realWithDrawAmt"] 	= getMoneyFomat($modata["realWithDrawAmt"],2) ;//实际提现金额
					$modata["applyAccount"] = $recorder->getMember("applyAccount")!=null ? $recorder->getMember("applyAccount") : '' ;//提现用户名
					$modata["ipAddr"] 		= long2ip($recorder->getMember("ipAddr")) ; //发起提现的ip地址
					$modata["applyTime"] 	= $recorder->getMember("applyTime") > 0 ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("applyTime"))) : '';//用户申请提现时间
					$modata["riskType"] 	= $recorder->getMember("riskType") ; //风险类型 0正常1 IP风险2黑名单卡提现3大额提现4频繁提现5全部审核
					$modata["riskType"] 	= isset($this->_aRiskType[$modata['riskType']]) ? $this->_aRiskType[$modata['riskType']] : "无类型";
					$modata["apprAccount"] 	= $recorder->getMember("apprAccount")!=null ? $recorder->getMember("apprAccount") : ''; //一审管理员
					$modata["beginApprTime"]= $recorder->getMember("apprBeginTime") > 0 ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("apprBeginTime"))) : ''; //一审开始时间
					$modata["apprTime"] 	= $recorder->getMember("apprTime") > 0 ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("apprTime"))) : '';//一审结束时间
					$modata["memo"] 		= $recorder->getMember("memo")!=null ? $recorder->getMember("memo") : '' ; //备注
					$modata["appr2Acct"] 	= $recorder->getMember("appr2Acct")!=null ? $recorder->getMember("appr2Acct") : '' ;//复审管理员
					$modata["Appr2Time"] 	= $recorder->getMember("appr2Time") > 0 ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("appr2Time"))) : '';//复审结束时间
					$modata["status"] 		= $recorder->getMember("status") ;
					$modata["status"] 	= isset($this->_aWithDrawStatus[$modata['status']]) ? $this->_aWithDrawStatus[$modata['status']] : "无类型";
					$modata["isVip"] 		= $recorder->getMember("isVip") =='1' ? '是' : '否'; //是否vip
					$modata["mcNoticeTime"] = $recorder->getMember("mcNoticeTime") > 0 ? date('Y-m-d G:i:s',getSrtTimeByLong($recorder->getMember("mcNoticeTime"))) : ''; //mcNoticeTime  mow返回结果时间
					$withdrawModeStr='';
					if($recorder->getMember("withdrawMode")=='1'){
						$withdrawModeStr = 'DP';
					}else if ($recorder->getMember("withdrawMode")=='2'){
						$withdrawModeStr = '通汇';
					}else{
						$withdrawModeStr = '未分配';
					}
					$modata["withdrawMode"] = $withdrawModeStr;
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
	
	//解锁风险提现任务单
	public function unLockWithDrawWork(){
		$id = floatval(getSecurityInput($this->_request->getPost('id',0)));
		$this->cancelRedisLock($id);
		$status = intval(getSecurityInput($this->_request->getPost('title',1)));
		if(!isset($this->_aAclArray[$this->_param])){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102144')));
			exit;
		}
		if (empty($id) || $id==0) {
			echo Zend_Json::encode(array('status'=>'error','data'=>'解锁失败,任务单ID不能为空!'));
			exit;
		}
		if (!in_array($status, array(1,2))) {
			echo Zend_Json::encode(array('status'=>'error','data'=>'解锁失败,请选择一审还是二审重新解锁!'));
			exit;
		}
		$aUrl['fundadmin'] = 'yuchulidrawEnd';
		$data['param']['exceptId'] = $id;
		$data['param']['currStatus '] = $status;
		$res = $this->_clientobject->inRequestV2($data, $aUrl);
		if(isset($res['head']['status']) && $res['head']['status'] == '0'){
			echo Zend_Json::encode(array('status'=>'ok','data'=>'解锁成功!'));
			exit;
		}
		echo Zend_Json::encode(array('status'=>'error','data'=>'解锁失败!'));
		exit;
	}
	
	//====================================风险提现操作=========================================================
	
	//多文件上传
	public function uploadAction(){
		$cnt = $this->multiFilesUpload('attachments');
		if($cnt>0){
			echo Zend_Json::encode(array('status'=>'0','data'=>$cnt));
		} else {
			echo Zend_Json::encode(array('status'=>'1'));
		}
	}
	
	private function cancelRedisLock($id){
		$aUri['fundadmin'] = 'queryWithdrawById';
		$data = array(
				'param' => array(
						'userId' =>$id
				)
		);
		$resultObj  = $this->_clientobject->inRequestV2($data, $aUri);
		$rootSn = $resultObj['body']['result']['rootSn'];
		$this->redis_client->del('under_'.$rootSn);
	}
	
}
