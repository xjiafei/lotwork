<?php
class Admin_BankcardsmanageController extends Fundcommon
{
	private $_queryBankCardRecords,$_querySuspiciousCards,$_queryAllBankDao,$_param;
	public function init(){
		parent::init ();
		
		$this->_queryBankCardRecords =  new QueryBankCardRecords();
		$this->_querySuspiciousCards = new QuerySuspiciousCards();
		$this->_queryAllBankDao  = new QueryAllBankDao();
	}
	public function indexAction(){
		/* 银行卡绑定记录 */
		$this->_param = getSecurityInput($this->_request->get("parma"));
		$aParam = array(
				"sv2"=>"SettingsView2",//提供ajax数据借口
				"sv5"=>"SettingsView5",//可疑银行卡列表的数据接口
				"sv9"=>"SettingsView9",//后台管理员加锁
				"sv10"=>"SettingsView10",//绑卡历史记录
				"sv12"=>"SettingsView12",//可以银行卡删除请求 
				"sv41"=>"View41", //4.1 银行管理
				"sv42"=>"View42", //4.2 用户银行卡绑定管理
				"sv43"=>"View43", //4.3 银行卡黑名单
				"opt1"=>"addChargeBank", //新增充值银行
				"opt3"=>"addWithDrawBank", //新增提现银行
				"upbank"=>"updateBank", //编辑银行
				"opteradd"=>"opterAddBlackList",//添加黑名单
				"sv44"=>"saveBankCount", //保存可绑银行卡数
				"sv45"=>"savebankCardConf", //银行卡管理管理配置页面
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
	
	//银行管理
	public function View41(){
		$step = intval($this->_request->getParam('step',0));
		//银行卡绑定数目
		$result = $this->getconfigvaluebykey('fund', 'chargeCoute','getconfigvaluebychargeCoute');
		$aliResult = $this->getconfigvaluebykey('fund', 'aliPayChargeCoute','getconfigvaluebychargeCoute');
		
		$res = $this->getBankCardInfo("bankStruc");
		$this->view->res=$res;
		$this->view->bankCount = $result['val'];
		$this->view->aliBankCount = $aliResult['val'];
		$this->view->downArray = range(1, 10);
		$this->view->step = $step;
		$this->view->title = '银行管理';
		$this->view->display ( '/admin/funds/bankcards/cardsrecorders.tpl' );
	}
	
	//修改用户可绑银行卡数目
	public function saveBankCount(){
		$chargeCoute = intval(getSecurityInput($this->_request->getPost('chargeCoute','4')));
		$aliPayChargeCoute = intval(getSecurityInput($this->_request->getPost('aliPayChargeCoute','4')));
		
		if($chargeCoute<-1 || $chargeCoute>10 || $chargeCoute==0
		 ||$aliPayChargeCoute<-1 || $aliPayChargeCoute>10 || $aliPayChargeCoute==0){
			echo Zend_Json::encode(array('status'=>false));
			exit;
		}
		$result = $this->saveconfigvalue($chargeCoute,'fund', 'chargeCoute', 'saveConfigchargeCoute');
		$aliResult = $this->saveconfigvalue($aliPayChargeCoute,'fund', 'aliPayChargeCoute', 'saveConfigchargeCoute');
		if($result !=false && $aliResult!=false){
			echo Zend_Json::encode(array('status'=>true));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>false));
			exit;
		}
	}
	//显示绑卡界面
	public function View42(){
	
		$this->view->_POST= $this->_request->getParams();
		$this->view->title = '银行绑定管理';
		$this->view->display ( '/admin/funds/bankcards/binding.tpl' );
	}
	
	//显示黑名单页面
	public function View43(){
		
		$this->view->title = '银行卡黑名单管理';
		$this->view->display ( '/admin/funds/bankcards/blacklist.tpl' );
	}
	
	
	//银行卡管理加载页面
	public function savebankCardConf(){
		$request = $this->_request;
		$params = $request->getParams();
		$step = isset($params['step']) ? intval(getSecurityInput($params['step'])) : 0;
		//检测提交权限
		
		if($request->isPost() && isset($this->_aAclArray[$this->_param.'_post'][$step])){
			$data = array();
			if($params['step'] == 0){

				foreach ($this->_bankIcoArray as $key=>$value){
					if($key=="30"){
						//支付寶 0個人版  1企業版
						$data[$key."-0"]=array('code'=>$key,'deposit'=>0,'other'=>0);
						$data[$key."-1"]=array('code'=>$key,'deposit'=>0,'other'=>0);
					}else{
						$data[$key]=array('code'=>$key,'deposit'=>0,'other'=>0);
					}
				}
				//網銀
				if(isset($params['net_bank']) && count($params['net_bank'])>0){
					foreach ($params['net_bank'] as $value){
						$data[$value]['deposit'] = 1;
					}
				} 
				//快捷
				if(isset($params['quick_bank']) && count($params['quick_bank'])>0){
					foreach ($params['quick_bank'] as $value){
						$data[$value]['other'] = 1;
					}
				}
				//財富通
				if(isset($params['third_bank']) && count($params['third_bank'])>0){
					foreach ($params['third_bank'] as $value){
						$data[$value]['deposit'] = 1;
					}
				}
				//銀聯
				if(isset($params['unionpay_bank']) && count($params['unionpay_bank'])>0){
					foreach ($params['unionpay_bank'] as $value){
						$data[$value]['deposit'] = 1;
					}
				}

				//支付寶個人版
				if(isset($params['ali_bank_personal']) && count($params['ali_bank_personal'])>0){

						foreach ($params['ali_bank_personal'] as $value){
							$valueCombinate =$value."-0";
							
							$data[$valueCombinate]['vipOpen'] = is_null($params['aliVipOpenPersonal'])?0:1;						
							$data[$valueCombinate]['normalOpen'] = is_null($params['aliNormalOpenPersonal'])?0:1;
							if($data[$valueCombinate]['vipOpen']>0 || $data[$valueCombinate]['normalOpen']>0){
								$data[$valueCombinate]['deposit'] = 1;
							}
							$data[$valueCombinate]['version']=0;
						}
                   
				}
				//支付寶企業版
				if(isset($params['ali_bank_business']) && count($params['ali_bank_business'])>0){
						
						foreach ($params['ali_bank_business'] as $value){
							$valueCombinate =$value."-1";

							$data[$valueCombinate]['vipOpen'] = is_null($params['aliVipOpenBusiness'])?0:1;						
							$data[$valueCombinate]['normalOpen'] = is_null($params['aliNormalOpenBusiness'])?0:1;
							if($data[$valueCombinate]['vipOpen']>0 || $data[$valueCombinate]['normalOpen']>0){
								$data[$valueCombinate]['deposit'] = 1;
							}
							$data[$valueCombinate]['version']=1;
						}
				}
				//微信支付
				if(isset($params['wechat']) && count($params['wechat'])>0 ){
					foreach ($params['wechat'] as $value){
						$data[$value]['deposit'] = 1;
					}
				}
				//QQ錢包
				if(isset($params['qq']) && count($params['qq'])>0){
					foreach ($params['qq'] as $value){
						$data[$value]['vipOpen'] = is_null($params['qqVipOpen'])?0:1;						
						$data[$value]['normalOpen'] = is_null($params['qqNormalOpen'])?0:1;
						if($data[$value]['vipOpen']>0 || $data[$value]['normalOpen']>0){
							$data[$value]['deposit'] = 1;
						}
					}
				}
			} else if($params['step'] == 1) {
				foreach ($this->_bankIcoArray as $key=>$value){
					$data[$key]=array('code'=>$key,'withdraw'=>0);
				}
				if(isset($params['withdraw_bank']) && count($params['withdraw_bank'])>0){
					foreach ($params['withdraw_bank'] as $value){
						$data[$value]['withdraw'] = 1;
					}
				}
			} else if($params['step'] == 2) {
				foreach ($this->_bankIcoArray as $key=>$value){
					$data[$key]=array('code'=>$key,'moveDeposit'=>0 ,'moveQuickDeposit'=>0);
				}
				
				if(isset($params['move_quick_bank']) && count($params['move_quick_bank'])>0){

					foreach ($params['move_quick_bank'] as $value){
						if(($params['move_quick_type'] == 1)){	
							$data[$value]['moveQuickDeposit'] = 1;
						}else{
							$data[$value]['moveQuickDeposit'] = 0;
						}
					}

				}
				if(isset($params['move_bank']) && count($params['move_bank'])>0){

					foreach ($params['move_bank'] as $value){
						$data[$value]['moveDeposit'] = 1;
						if($value ==30){
							$data[$value]['moveVipOpen'] = is_null($params['aliMoveVipOpen'])?0:1;						
							$data[$value]['moveNormalOpen'] = is_null($params['aliMoveNormalOpen'])?0:1;
						}
					}
				}
				
				
				
			}
			foreach ($data as $value){
				$requsetData['param'][] = $value;
			}
// 			print_r(Zend_Json::encode($requsetData));exit;
// 			$requsetData['param']=$data;

			$res = $this->_queryAllBankDao->bankParamsSet($requsetData);
			unset($data,$requsetData);
			/* header ( 'Content-Type: application/json;charset=utf-8' );
			if(isset($res['head']['status']) && $res['head']['status'] =='0'){
				echo Zend_Json::encode(array('status'=>'true'));
				exit;
			} else {
				echo Zend_Json::encode(array('status'=>'false'));
				exit;
			} */
			
			
			
			
			
			
		}
		$res = $this->getBankCardInfo("bankStruc");
		$this->view->res=$res;
		$this->view->step=$step;
		$this->view->title = '银行卡管理';
		$this->view->display('/admin/funds/bankcards/bankCardMgr.tpl');
	}
	
	//添加黑名单银行卡
	public function opterAddBlackList(){
		
		if($this->_request->isPost()){
			$cardNumber = getSecurityInput($this->_request->getPost("cardNumber",0));
			$memo 		= getSecurityInput($this->_request->getPost("applyMemo",''));
			$aUri = array("fundadmin"=>"checkSuspiciousCard");
			$data1["param"]["cardNo"]= $cardNumber ;
			$res = $this->_clientobject->inRequestV2($data1, $aUri);
			if(isset($res['head']['status']) && $res['head']['status'] =='2011'){
				echo Zend_Json::encode(array('status'=>'error','data'=>'该卡已经添加!'));
				exit;
			}
			$data=array();
			$data["param"]["cardNumber"]= $cardNumber ;
			$data["param"]["memo"]		= $memo ;
			$status = $this->_querySuspiciousCards->addSuspiciousCards($data);
			if($status){
				echo Zend_Json::encode(array('status'=>'ok','data'=>'添加成功!'));
				exit;
				$this->_redirect('/admin/Bankcardsmanage/index?parma=sv43');
			} else {
				echo Zend_Json::encode(array('status'=>'error','data'=>'添加失败!'));
				exit;
			}
		}
		$this->view->title = '新增银行卡黑名单';
		$this->view->display ( '/admin/funds/bankcards/addBlacklist.tpl' );
		
	}
	
	//新增充值银行
	public function addChargeBank(){
		
		if($this->_request->isPost()){
			$chargeData = array ();
			$chargeData["param"]["name"] = getSecurityInput($this->_request->getPost('name'));
			$chargeData["param"]["logo"] = getSecurityInput($this->_request->getPost('logo'));
			$chargeData["param"]["code"] = intval(getSecurityInput($this->_request->getPost('code')));
			$chargeData["param"]["url"]  = getSecurityInput($this->_request->getPost('url'));
			$chargeData["param"]["mownecumId"] = intval(getSecurityInput($this->_request->getPost('mownecumId')));
			$res = $this->_queryAllBankDao->addBank($chargeData);
			if($res && intval(getSecurityInput($this->_request->getPost('rtnSet'))) =='2') {
				$bankStruc = array ();
				$bankStruc["id"] 		  = intval(getSecurityInput($this->_request->getPost('mownecumId')));
				$bankStruc["upLimit"] 	  = intval(getSecurityInput($this->_request->getPost('upLimit')))*$this->_moneyUnit;
				$bankStruc["lowLimit"] 	  = intval(getSecurityInput($this->_request->getPost('lowLimit')))*$this->_moneyUnit;
				$bankStruc["vipUpLimit"]  = intval(getSecurityInput($this->_request->getPost('vipUpLimit')))*$this->_moneyUnit;
				$bankStruc["vipLowLimit"] = intval(getSecurityInput($this->_request->getPost('vipLowLimit')))*$this->_moneyUnit;
				$bankStruc["rtnSet"] 	  = intval(getSecurityInput($this->_request->getPost('rtnSet')));
				$bankStruc["rtnMin"] 	  = intval(getSecurityInput($this->_request->getPost('rtnMin')))*$this->_moneyUnit;
				$bankStruc["rtnStruc"] = array();
				$sm = getSecurityInput($this->_request->getPost('sm'));
				$addMoney = getSecurityInput($this->_request->getPost('addMoney'));
				$setValue = getSecurityInput($this->_request->getPost('setValue'));
				//判断上下限有效性
				if($bankStruc["upLimit"]<$bankStruc["lowLimit"] || $bankStruc["vipUpLimit"] < $bankStruc["vipLowLimit"]){
					$this->log($this->getError('102058'));
					echo "<script language='javascript'>alert('".$this->getError('102058')."');window.history.go(-1);</script>";
					exit;
				}
				foreach ($sm as $key=>$value){
					$data = array();
					$data['sm']  = intval(getSecurityInput($value))*$this->_moneyUnit;
					$data['big'] = intval(getSecurityInput($addMoney[$key]))*$this->_moneyUnit;
					if($data['sm']>$data['big']){
						$this->log($this->getError('102056'));
						$this->addErr('102056');
						$this->res_add_url('新增充值银行','/admin/Bankcardsmanage/index?parma=opt1',false,true);
						$this->res_show(true,true,'funds');
					}
					$data['value'] = intval(getSecurityInput($setValue[$key]));
					$data['type']  = intval(getSecurityInput($this->_request->getPost('radiodd'.($key+1))));
					if($data['type'] =='1'){
						$data['value'] = $data['value'] * $this->_moneyUnit;
					}
					$bankStruc["rtnStruc"][] = $data;
					unset($data);
				}
				$data = array();
				$data['sm']		= intval(getSecurityInput($this->_request->getPost('sm5'))) * $this->_moneyUnit;
				$data['type']	= intval(getSecurityInput($this->_request->getPost('radiodd5')));
				$data['value']	= intval(getSecurityInput($this->_request->getPost('setValue5')));
				if($data['type'] == '1'){
					$data['value'] = $data['value']  * $this->_moneyUnit;
				}
				$bankStruc["rtnStruc"][] = $data;
				$addDataArrray['param'][]=$bankStruc;
				$res = $this->_queryAllBankDao->bankParamsSet_V2($addDataArrray);
				unset($data,$bankStruc,$addDataArrray);
			}
			if($res){
				$this->_redirect('/admin/Bankcardsmanage/index?parma=sv41');
			} else {
				$this->log('提现银行增加失败!');
			}
		}
		$bankList = $this->getUnbindCard();
		$this->view->bankList = $bankList;
		$this->view->title = '新增充值银行';
		$this->view->display ( '/admin/funds/bankcards/addCardsDeposit.tpl' );
	}
	
	//新增提现银行
	public function addWithDrawBank(){
		
		if($this->_request->isPost()){
			$data["param"]["id"] = intval(getSecurityInput($this->_request->getPost("mownecumId")));
			$data["param"]["name"] = getSecurityInput($this->_request->getPost("name"));
			$data["param"]["logo"] = getSecurityInput($this->_request->getPost("logo"));
			$data["param"]["code"] = intval(getSecurityInput($this->_request->getPost("code")));
			$data["param"]["url"]  =  getSecurityInput($this->_request->getPost("url"));
			$data["param"]["mownecumId"] = intval(getSecurityInput($this->_request->getPost("mownecumId")));
			if(empty($data["param"]["mownecumId"])){
				$this->addErr('102062');
				$this->res_add_url('新增提现银行','/admin/Bankcardsmanage/index?parma=opt3',false,true);
				$this->res_show(true,true,'funds');
			}
			$res = $this->_queryAllBankDao->addBank($data);
			if($res){
				$this->_redirect('/admin/Bankcardsmanage/index?parma=sv41&step=1');
			} else {
				$this->log('提现银行增加失败!');
				$bankList = $this->getUnbindCard();
				$this->view->bankList = $bankList;
				$this->view->display ( '/admin/funds/bankcards/addWithdrawCard.tpl' );
			}
		} elseif ($this->_request->isGet()) {
			$bankList = $this->getUnbindCard();
			$this->view->bankList = $bankList;
			$this->view->title = '新增提现银行';
			$this->view->display ( '/admin/funds/bankcards/addWithdrawCard.tpl' );
		}
	
	}
	
	//编辑银行
	public function updateBank(){
		$data["name"] = getSecurityInput($this->_request->getPost("name"));
		$data["logo"] = str_replace('ico-bank ', '', getSecurityInput($this->_request->getPost("logo")));
		$data["code"] = intval(getSecurityInput($this->_request->getPost("code")));
		$data["url"]  = getSecurityInput($this->_request->getPost("url"));
		$data["id"]   = $data["code"];
		$data["mownecumId"] = intval(getSecurityInput($this->_request->getPost("mownecumId")));
		$regdata['param']=$data;
		$res = $this->_queryAllBankDao->updateBank($regdata);
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo Zend_Json::encode($res);
	}
	
	//银行卡绑定记录
	public function SettingsView2($parma){
		
		
		$page 		=  intval(getSecurityInput($this->_request->getPost("page",0)));
		$perPageNum = intval(getSecurityInput($this->_request->getPost("perPageNum",0)));
		$start 		= ($page-1)*$perPageNum;
		$type		=  getSecurityInput($this->_request->getPost("type"));
		$userAccount=  getSecurityInput($this->_request->getPost("userName"));
		$bindcardType=  getSecurityInput($this->_request->getPost("bindcardType"));

		$data=array();
		$afreezeMethod = array('----','完全冻结','可登录，不可投注，不可充提','不可投注，可充提','不可转账，不可提现','系统冻结');
		$request = $this->getRequest();
 		if($request->isPost()) {
 			if($type == '1'){
 				//$userInfo   = $this->getUserInfo($userAccount);
				$data["param"]["userAccount"] = $userAccount ;
				//$data["param"]["userId"] = $userInfo['id'];
 			} else {
				$data["param"]["bankCard"] = $userAccount ;
 			}
 			$data["param"]["bindcardType"] = $bindcardType ;
		}
		
 		$res = $this->_queryBankCardRecords->queryBankCardRecords($data); 
		$modata  = $modatas = array();
		$modatas['text'] = $modatas['count'] = array();
		//$recorder = $res["result"][0];
		foreach ($res['result'][0] as $recorder){
			$modata["isVip"]    = isset($recorder['vipLvl']) && $recorder['vipLvl'] >0 ? '是' : '否';
			$modata["lockedId"] = $recorder["lockedId"] ? $recorder["lockedId"] : '' ;
			$modata["account"] 	= $recorder["account"] ? $recorder["account"] : '' ;
			$modata["bindCount"]= $recorder["bindCount"] ? $recorder["bindCount"] : '' ;
			$modata["status"] 	= $recorder["status"] ? $recorder["status"] : '' ;
			$modata["isFreeze"] 	= isset($recorder["isFreeze"]) && $recorder["isFreeze"]=='1' ? '已冻结' : '未冻结';;
			$modata["freezeMethod"] = isset($recorder["freezeMethod"]) ? $afreezeMethod[$recorder["freezeMethod"]] : $afreezeMethod[0];
			$modata["topAcc"] 		= isset($recorder["topAcc"]) ? $recorder["topAcc"] : '';
			$modata["operator"] 	= isset($recorder["operator"]) ? $recorder["operator"] : '';
			$modata["path_img"]  =   $this->_config->imgroot;
			$modata["vipLvl"]    = isset($recorder['vipLvl']) && $recorder['vipLvl'] >0 ? $recorder['vipLvl'] : '';
			$userBankStruc = $recorder["userBanks"];

			if(count($userBankStruc)>0){
				foreach ($userBankStruc as $sub_key=>$value){
					$bankId  = $this->_bankIcoArray[$value["bankId"]];
					$modata['userBanks'][$sub_key]["bankName"]    = $bankId["name"];
					$modata['userBanks'][$sub_key]["province"]    = $value["province"]? $value["province"] : '';
					$modata['userBanks'][$sub_key]["city"]    	  = $value["city"] ? $value["city"] : '' ;
					$modata['userBanks'][$sub_key]["branchName"]  = $value["branchName"];
					$modata['userBanks'][$sub_key]["bindDate"]    = date('Y-m-d G:i:s',getSrtTimeByLong($value["bindDate"]));
					if($value["bindcardType"]==0){
						$modata['userBanks'][$sub_key]["bankAccount"] = $this->getSecurityBankCardAucount($value["bankAccount"]);
						$modata['userBanks'][$sub_key]["bankNumber"]  = $this->getSecurityBankCardNum($value["bankNumber"]);
						
					}else{
						$modata['userBanks'][$sub_key]["bankAccount"] = $this->getSecurityBankCardAccountLastName($value["bankAccount"]);
						$modata['userBanks'][$sub_key]["bankNumber"]  = $this->getSecurityAliPayBankCardNum($value["bankNumber"]);
						
					}
					$modata['userBanks'][$sub_key]["nickName"]  =   isset($value["nickName"])?$value["nickName"]:'';
					
					
				}
			} else {
				$modata['userBanks'] = array();
			}
			array_push($modatas['text'],$modata) ;
		}
		array_push($modatas['count'],array("recordNum"=>$res["pager"]["total"])) ; //recordNum 页数 ，每页15条
// 		print_r($modatas);
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo json_encode($modatas) ;
	}	
	
	/* 银行卡配置 */
	public function SettingsView3($parma){
		$this->view->title = "银行卡配置";
		$this->view->display ( '/admin/funds/CardsBind/cardsconf.tpl' );
	}
	
	//加载黑名单银行卡数据
	public function SettingsView5($parma){
		
		$BankCard 	   = getSecurityInput($this->_request->getPost("BankCard",'')); //银行卡号
		$UserName 	   = getSecurityInput($this->_request->getPost("UserName",''));//用户名
		$BankUserName  = getSecurityInput($this->_request->getPost("BankUserName",''));//银行账户名
		$Daozhangtime1 = getSecurityInput($this->_request->getPost("Daozhangtime1",''));//列入黑名单时间查询开始时间时刻
		$Daozhangtime2 = getSecurityInput($this->_request->getPost("Daozhangtime2",''));//列入黑名单时间查询开始结束时刻
		$page 		   = intval(getSecurityInput($this->_request->getPost("page",0)));
		$perPageNum    = intval(getSecurityInput($this->_request->getPost("perPageNum",20)));
		$param= new ArrayObject(array());
		if($BankCard){
			$param["cardNo"]= $BankCard;
		}
		if($UserName){
			$param["account"]= $UserName;
		}
		if($BankUserName){
			$param["cardAcc"]= $BankUserName;
		}
		if($Daozhangtime1){
			$param["startDate"]= getQueryStartTime($Daozhangtime1);
		}
		if($Daozhangtime2){
			$param["endDate"]= getQueryEndTime($Daozhangtime2);
		}
		$data["param"] = $param;
		$data["pager"]["startNo"]= $page*$perPageNum+1;
		$data["pager"]["endNo"]= ($page+1)*$perPageNum;
		$recorders = $this->_querySuspiciousCards->querySuspiciousCards($data);
		$modata = $modatas = array();
		$modatas['text'] = $modatas['count'] = array();
		foreach ( $recorders["result"] as $recorder){
			$modata["cardNumber"] 	  = $recorder->getMember("cardNumber") ;
			$modata["id"] 			  = $recorder->getMember("id") ;
			$modata["gmtCreated"] 	  = date('Y-m-d H:i:s',getSrtTimeByLong($recorder->getMember("gmtCreated"))) ; //操作时间
			$modata["creatorAccount"] = $recorder->getMember("creatorAccount") ; //操作人
			$modata["bankAcc"] 		  = $recorder->getMember("bankAcc") ? $recorder->getMember("bankAcc") : '' ; //银行卡账户
			$modata["topAcc"] 		  = $recorder->getMember("topAcc") ? $recorder->getMember("topAcc") : '' ; //所属总代
			$modata["memo"] 		  = $recorder->getMember("memo") ? $recorder->getMember("memo") : '' ; //备注
			$modata["account"] 		  = $recorder->getMember("account") ? $recorder->getMember("account") : '' ; //用户账户
			array_push($modatas['text'],$modata) ;
		}
		array_push($modatas['count'],array("recordNum"=>$recorders["pager"]["total"])) ; //recordNum 页数 ，每页15条
		echo Zend_Json::encode($modatas);
		exit;
	}
	
	// 管理员加解锁
	public function SettingsView9($parma){
				
		$type 	  = getSecurityInput($this->_request->getPost('type',2));//1解锁,2锁定
		$lockedId = getSecurityInput($this->_request->getPost("lockedId"));
		$bindcardType = getSecurityInput($this->_request->getPost("bindcardType"));
	
		$overTime = time('-1 hour');
		if($type =='1'){
			if($bindcardType==1){//支付寶預設啟動中
				$overTime = null;
				
			}else{
				$overTime = strtotime('+1 hour');
			}
			
		}
		$data=array();
		$data["param"]["operator"] = $this->_sessionlogin->info['account'] ;
		$data["param"]["lockId"]   = intval($lockedId);
		$data["param"]["overTime"] = timetoMicro($overTime);
		$res = $this->_queryBankCardRecords->lockBankCard($data);
		if(isset($res['head']['status'])&& $res['head']['status'] =='0'){
			echo Zend_Json::encode(array('status'=>'ok','data'=>$type=='1'?'锁定成功':'解锁成功'));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','data'=>$type=='1'?'锁定失败':'解锁失败'));
			exit;
		}
	}
	
	//单用户绑卡记录
	public function SettingsView10($parma){
		// 具体数据回传给js的需要跟seven在重新沟通一下
		$username = getSecurityInput($this->_request->getPost("username"));
		$type	  = getSecurityInput($this->_request->getPost("type"));
		$bindcardType	  = getSecurityInput($this->_request->getPost("bindcardType"));
		$data = array();
		$afreezeMethod = array('----','完全冻结','可登录，不可投注，不可充提','不可投注，可充提','不可转账，不可提现','系统冻结');
		if($type == '1'){
			$data["param"]["userAccount"] = $username ;
			$data["pager"]["startNo"]= 1;
			$data["pager"]["endNo"]= 10;
		} else {
			$data["param"]["bankCard"] = $username ;
		}
		$data["param"]["bindcardType"]= $bindcardType;
		$res = $this->_queryBankCardRecords->queryBankCardHistory($data);
		$modata = $modatas = array();
		$modatas['text'] = $modatas['count'] = array();
		if (isset($res['body']['result']['bankCardHistorys']) && count($res['body']['result']['bankCardHistorys'])>0) {
			foreach ( $res['body']['result']['bankCardHistorys'] as $recorder){
				
				$modata["userId"] 		= isset($recorder["userId"]) ? $recorder["userId"] : '' ;
				$modata["topAcc"] 		= isset($recorder["topAcc"]) ? $recorder["topAcc"] : '' ;
				$modata["account"] 		= isset($recorder["account"]) ? $recorder["account"] : '' ;
				$modata["isFreeze"] 	= isset($recorder["isFreeze"]) && $recorder["isFreeze"]=='1' ? '已冻结' : '未冻结';
				$modata["freezeMethod"] = isset($recorder["freezeMethod"]) ? $afreezeMethod[$recorder["freezeMethod"]] : '';
				$modata["actionTime"] 	= date('Y-m-d H:i:s',getSrtTimeByLong($recorder["actionTime"])) ;
				$modata["action"] 		= isset($recorder["action"]) ? $recorder["action"] : '';
				$modata["bankName"] 	= isset($this->_bankIcoArray[$recorder["mcBankId"]]['name']) ? $this->_bankIcoArray[$recorder["mcBankId"]]['name'] : '' ;
				$modata["province"] 	= isset($recorder["province"]) ? $recorder["province"] : '' ;
				$modata["city"] 		= isset($recorder["city"]) ? $recorder["city"] : '' ;
				$modata["action"] 		= isset($recorder["action"]) ? $recorder["action"] : '' ;
				$modata["bankAccount"] 	= isset($recorder["bankAccount"]) ? $recorder["bankAccount"] : '' ;
				$modata["actionTime"] 	= date('Y-m-d H:i:s',getSrtTimeByLong($recorder["actionTime"])) ;
				$modata["bankName"] 	= isset($recorder["mcBankId"]) ? $this->_bankIcoArray[$recorder["mcBankId"]]['name'] : '' ;
				
				
				
				if($bindcardType==0){
					$modata["bankNumber"] 	= $this->getSecurityBankCardNum($recorder["bankNumber"]) ;
					$modata["bankAccount"] 	= $this->getSecurityBankCardAucount($modata["bankAccount"]) ;
				
				}else{
					$modata["bankNumber"] 	= $this->getSecurityAliPayBankCardNum($recorder["bankNumber"]) ;
					$modata["bankAccount"] 	= $this->getSecurityBankCardAccountLastName($modata["bankAccount"]) ;	
				} 
				$modata["nickName"]  =   isset($recorder["nickName"]) ? $recorder["nickName"] : '' ;
				
				
				
				array_push($modatas['text'],$modata);
			}
			array_push($modatas['count'],array("recordNum"=>"1")) ; //recordNum 页数 ，每页15条
		}
		echo Zend_Json::encode($modatas);
		exit;
	}
	
	//可疑银行卡的删除请求
	public function  SettingsView12($parma){
		
		if($this->_request->isPost()){
			$Id =  $this->_request->getPost("bankId");
			$data = array();
			$data["param"]["id"] = floatval($Id) ;
			$recorders = $this->_querySuspiciousCards->deleteSuspiciousCards($data);
			if(isset($recorders['head']['status']) && $recorders['head']['status'] =='0' ){
				echo Zend_Json::encode(array('status'=>'ok','data'=>'删除成功!'));
				exit;
			} else {
				echo Zend_Json::encode(array('status'=>'error','data'=>'删除失败!'));
				exit;
			}
		}
	}
}