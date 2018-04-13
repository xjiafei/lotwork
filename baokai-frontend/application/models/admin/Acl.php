<?php
class Acl {
	private $_className;
	private $_aAclArray;
	public function __construct($calssName){
		$this->__setClassName($calssName);
		$this->_aAclArray = array(
			'bankcardsmanage' => array(
				"sv2"=>"FUND_BANKCARD_BINDMANAGE",//提供ajax数据借口
				"sv5"=>"FUND_BANKCARD_BLACKLIST",//可疑银行卡列表的数据接口
				'sv41' => 'FUND_BANKCARD_BANKMANAGE', //银行管理
				'opt1' => 'FUND_BANKCARD_BANKMANAGE_RECHARGE_CREATE',//新增充值银行
				'opt3' => 'FUND_BANKCARD_BANKMANAGE_WITHDRAW_CREATE', //新增提现银行
				'upbank'=> 'FUND_BANKCARD_BANKMANAGE_EDIT',//编辑
				'sv44' => 'FUND_BANKCARD_BANKMANAGE_BINDCARDCOUNT_SUBMIT',//用户可绑定银行卡数 提交数据
				'sv45' => 'FUND_BANKCARD_BANKCARDMANAGE',//银行卡管理
				'sv45_post' => array(
					'0'=> 'FUND_BANKCARD_BANKCARDMANAGE_RECHARGE_SUBMIT', //充值开关提交数据
					'1'=> 'FUND_BANKCARD_BANKCARDMANAGE_WITHDRAW_SUBMIT',  //提现开关提交数据
					'2'=> 'FUND_BANKCARD_BANKCARDMANAGE_MOVE_SUBMIT'  //移动开关提交数据
				), //充值开关提交数据
				'sv42' => 'FUND_BANKCARD_BINDMANAGE',//用户银行卡绑定管理
				'sv9'  => 'FUND_BANKCARD_BINDMANAGE_CHANGESTATUS',//启动/锁定
				'sv10' => 'FUND_BANKCARD_BINDMANAGE_HISTORY',//查看历史记录
				'sv43' => 'FUND_BANKCARD_BLACKLIST',//银行卡黑名单管理
				'opteradd' => 'FUND_BANKCARD_BLACKLIST_CREATE',//新增黑名单
				'sv12' => 'FUND_BANKCARD_BLACKLIST_DELETE'//删除黑名单
			),
			'epreplenishment' => array(), //旧版本控制器
			'error' 		=> array(),
			'fundconfig' 	=> array(
                'saveDrawBypassCfg' => 'FUND_WITHDRAW_SAVE_BYPASS',
                'wcf0'=>'FUND_WITHDRAW_CONFIG_BYPASS',//提现分流配置
				'wcf1'=>'FUND_WITHDRAW_CONFIG_UPDOWN', //提现上下限配置
                'wcf2'=> 'FUND_WITHDRAW_CONFIG_AVALIWITHDRAW',//可提额度配置
				'wcf3'=> 'FUND_WITHDRAW_CONFIG_RISK',//风险提现门槛设置
                'wcf4'=>'FUND_WITHDRAW_DRAWTIPS', //審核提示配置
				'wcf5'=>'FUND_WITHDRAW_APPEALTIPS', //申訴提示配置
				'wcf6'=>'FUND_WITHDRAW_URGENCY', //緊急提示
                'wcf7'=>'FUND_WITHDRAW_SEPERATE', //提現分拆配置
                'wcf1_post'=>'FUND_WITHDRAW_CONFIG_UPDOWN_SUBMIT', //提现上下限配置提交数据
				'wcf2_post'=> 'FUND_WITHDRAW_CONFIG_AVALIWITHDRAW_SUBMIT',//可提额度配置提交数据
				'wcf3_post'=> 'FUND_WITHDRAW_CONFIG_RISK_SUBMIT',//风险提现门槛设置
                'wcf7_post'=> 'FUND_WITHDRAW_SEPERATE_SUBMIT',//提現分拆保存
				'sv4'=> 'FUND_WITHDRAW_WHITELIST',//可提现用户白名单
				'sv5'=> 'FUND_WITHDRAW_WHITELIST_ADD',//添加可提现用户白名单
				'sv6'=> 'FUND_WITHDRAW_WHITELIST_DELETE',//删除可提现用户白名单
				'sv7'=> 'FUND_WITHDRAW_WHITELIST',//获取可提现用户白名单
				'sv11'=>'FUND_WITHDRAW_APPEAL', //提款申訴處理
				'sv12'=>'FUND_WITHDRAW_WHITELIST', //申訴提示--查詢
				'saveDrawTips'=>'FUND_WITHDRAW_TIPS_SAVE', //審核提示儲存	
				'saveAppealTips'=> 'UND_WITHDRAW_APPEAL_TIPS_SAVE',//申訴提示儲存
				'loadappeal'=>'FUND_WITHDRAW_WHITELIST', //後台提款申訴處理
				//查詢緊急發布
				'searchUrgency' => 'FUND_WITHDRAW_URGENCY_SEARCH',
				//新增緊急發布
				'addUrgency' => 'FUND_WITHDRAW_URGENCY_ADD',
				'updateUrgency' => 'FUND_WITHDRAW_URGENCY_UPDATE',
				'appealdetail'=>'FUND_WITHDRAW_WHITELIST', //後台提款申訴查看功能
				'appealdo'=>'FUND_WITHDRAW_WHITELIST', //後台提款申訴審核功能
				'exappeal' => 'FUND_WITHDRAW_APPEAL_DOWNLOAD',
			),
			'fundsmanage' 	=> array(), //旧版本控制器
			'index' 		=> array(),
			'login' 		=> array(),
			'opterators' 	=> array(
				"sv1"=>"FUND_MANUAL_PROCEDURE",//人工资金操作审核流程
				"opter1"=>"FUND_MANUAL_PROCEDURE_CREATE",//人工资金操作建单
				"sv2"=>array(
						'1' => 'FUND_MANUAL_PROCEDURE_UNTREATED_PASS',
						'2' => 'FUND_MANUAL_PROCEDURE_UNTREATED_REFUSE',
				), //人工资金操作审核
				"de"=>array(
					'0' => 'FUND_MANUAL_PROCEDURE_ALL',
					'1' => 'FUND_MANUAL_PROCEDURE_UNTREATED',
					'2' => 'FUND_MANUAL_PROCEDURE_HANDLING',
					'3' => 'FUND_MANUAL_PROCEDURE_RESOLVED'
				),//加载人工资金数据
				"ex"=>"FUND_MANUAL_PROCEDURE_RESOLVED_EXPORT", //下载人工资金操作数据
				"qbbc"=>"FUND_MANUAL_PROCEDURE_CREATE", //查询相关用户绑卡信息
				"chkoptcharge"=>"FUND_MANUAL_PROCEDURE_HANDLING_CLOSE", //查询人工充值订单
				"delCharge"=>"FUND_MANUAL_PROCEDURE_HANDLING_CLOSE", //关闭人工充值订单
				'multilist' => 'FUND_MANUAL_BATCH',//显示批量操作页面
				'loadmutldata' => array(
						'0' => 'FUND_MANUAL_BATCH_UNTREAT',
						'1' => 'FUND_MANUAL_BATCH_COMPLETE'
				),//加载批量数据
				'multiupload' => 'FUND_MANUAL_BATCH_UPLOAD',//显示批量操作页面
				'dealmutil' => array(
					'1'=>'FUND_MANUAL_BATCH_UNTREAT_PASS',
					'2'=>'FUND_MANUAL_BATCH_UNTREAT_REFUSE'
				),//批量审核 人工操作
				'addmutil' => 'FUND_MANUAL_BATCH_UPLOAD_SUBMIT',//添加批量数据操作
				'aas'=>'FUND_MANUAL_AWARDS',//人工活动派奖操作
				'aasloadmutldata' => array(
						'0' => 'FUND_MANUAL_AWARDS_UNTREAT',
						'1' => 'FUND_MANUAL_AWARDS_COMPLETE'
				),//加载批量派獎数据
				'aassubmit' => 'FUND_MANUAL_AWARDS_UNTREAT_SUBMIT',//提交并派奖
			),
			'proxy' 		=> array(),
			'rechargemange' => array(
				'sv1' => 'FUND_RECHARGE_EXCEPTION',//显示列表页模板结构
				'dv1' => array(
						'0'=> 'FUND_RECHARGE_EXCEPTION_ALL', //加载异常充值列表全部数据
						'1'=> 'FUND_RECHARGE_EXCEPTION_UNTREATED', //加载异常充值列表页未处理数据
						'2'=> 'FUND_RECHARGE_EXCEPTION_REVIEW', //加载异常充值列表页待复审数据
						'3'=> 'FUND_RECHARGE_EXCEPTION_HANDLING', //加载异常充值列表页处理中数据
						'4'=> 'FUND_RECHARGE_EXCEPTION_SOLVED', //加载异常充值列表页已完成数据
				),//加载异常充值列表全部数据
				'disauit'=> array(
					'1'=> 'FUND_RECHARGE_EXCEPTION_UNTREATED_ADDCOINS',	//显示异常充值操作加游戏币界面
					'2'=> 'FUND_RECHARGE_EXCEPTION_UNTREATED_RETURNMONEY',	//显示异常充值操作退款界面
					'3'=> 'FUND_RECHARGE_EXCEPTION_REVIEW_REVIEW',	//显示异常充值操作复审退款界面
					'5'=> 'FUND_RECHARGE_EXCEPTION_UNTREATED_CONFISCATE',	//显示异常充值操作没收界面
					'8'=> 'FUND_RECHARGE_EXCEPTION_REVIEW_REVIEW',	//显示异常充值操作复审加游戏币界面
					'9'=> 'FUND_RECHARGE_EXCEPTION_REVIEW_REVIEW',	//显示异常充值操作复审没收界面
				),//显示异常充值操作加游戏币界面
				'opt1'=> array(
					'1'=> 'FUND_RECHARGE_EXCEPTION_UNTREATED_ADDCOINS',	//显示异常充值操作加游戏币界面
					'2'=> 'FUND_RECHARGE_EXCEPTION_UNTREATED_RETURNMONEY',	//显示异常充值操作退款界面
					'5'=> 'FUND_RECHARGE_EXCEPTION_UNTREATED_CONFISCATE',	//显示异常充值操作没收界面
				),//初审操作
				'rechargeReAuit' => array(
					'3'=> 'FUND_RECHARGE_EXCEPTION_REVIEW_REVIEW',	//显示异常充值操作复审退款界面
					'8'=> 'FUND_RECHARGE_EXCEPTION_REVIEW_REVIEW',	//显示异常充值操作复审加游戏币界面
					'9'=> 'FUND_RECHARGE_EXCEPTION_REVIEW_REVIEW',	//显示异常充值操作复审没收界面
				), //待复审 审核
				'ex'  => array(
					'0' => 'FUND_RECHARGE_EXCEPTION_ALL_DOWNLOAD',
					'1' => 'FUND_RECHARGE_EXCEPTION_UNTREATED_DOWNLOAD',
					'4' => 'FUND_RECHARGE_EXCEPTION_SOLVED_DOWNLOAD'
				),//下载异常充值全部数据
				'sv2' => array(
					'0' => 'FUND_RECHARGE_CONFIG_UPTO', //充值上下限配置
					'1' => 'FUND_RECHARGE_CONFIG_RETURN', //充值返送手续费配置
					'0_post' => 'FUND_RECHARGE_CONFIG_UPTO_SUBMIT', //提交上下限数据
					'1_post' => 'FUND_RECHARGE_CONFIG_RETURN_SUBMIT', //提交收续费配置数据
                                                                                                    '2' => 'FUND_APPEAL_TIPS_CONFIG',
                                                                                                    
				), //充值返还手续费配置和充值上下限配置
				'cufs' => 'FUND_RECHARGE_EXCEPTION_PROCESSING_CHECK',
				'sv3' => 'FUND_WITHDRAW_RISK', //显示风险提现列表数据
                                'da'  => array(
					'0'=> 'FUND_WITHDRAW_RISK_ALL',
					'1'=> 'FUND_WITHDRAW_RISK_UNTREATED',
					'2'=> 'FUND_WITHDRAW_RISK_REVIEW',
					'3'=> 'FUND_WITHDRAW_RISK_HANDLING',
					'4'=> 'FUND_WITHDRAW_RISK_RESOLVED',
				),//加载风险提现数据
				'wd'  => array(
					'1'=> 'FUND_WITHDRAW_RISK_UNTREATED_REVIEW',
					'2'=> 'FUND_WITHDRAW_RISK_UNTREATED_PASS',
					'3'=> 'FUND_WITHDRAW_RISK_UNTREATED_REFUSE',
				),//风险提现后台一审
				'wd2' => array(
					'2'=> 'FUND_WITHDRAW_RISK_REVIEW_PASS',
					'3'=> 'FUND_WITHDRAW_RISK_REVIEW_REFUSE',
				),//风险提现退款99
				'wd3' => array(
					'2'=> 'FUND_WITHDRAW_RISK_HANDLING_GETSTATUS',
					'7'=> 'FUND_WITHDRAW_RISK_HANDLING_REFUND',
				),//查詢目前獲得狀態99
				'cns' => array(
					'0' => 'FUND_WITHDRAW_RISK_UNTREATED_PASS',
					'1' => 'FUND_WITHDRAW_RISK_UNTREATED_REFUSE',
					'2' => 'FUND_WITHDRAW_RISK_UNTREATED_REVIEW',
					'3' => 'FUND_WITHDRAW_RISK_REVIEW_PASS',
					'4' => 'FUND_WITHDRAW_RISK_REVIEW_REFUSE',
				),//风险提现后台二审
				'exdraw' => array(
					'0'=> 'FUND_WITHDRAW_RISK_ALL_DOWNLOAD', //全部数据
					'4'=> 'FUND_WITHDRAW_RISK_RESOLVED_DOWNLOAD' //已完成
				),//下载风险提现数据
				'opt2'=> array(
					'1'=> 'FUND_RECHARGE_EXCEPTION_UNTREATED_ADDCOINS',	//显示异常充值操作加游戏币界面
					'2'=> 'FUND_RECHARGE_EXCEPTION_UNTREATED_RETURNMONEY',	//显示异常充值操作退款界面
					'5'=> 'FUND_RECHARGE_EXCEPTION_UNTREATED_CONFISCATE',	//显示异常充值操作没收界面
				), //解锁异常充值操作
				'opt4'=> array(
					'1'=> 'FUND_WITHDRAW_RISK_UNTREATED_REVIEW',
					'2'=> 'FUND_WITHDRAW_RISK_UNTREATED_PASS',
					'3'=> 'FUND_WITHDRAW_RISK_UNTREATED_REFUSE',
				), //解锁风险提现数据操作
				'getlevel'=> 'FUND_RECHARGE_EXCEPTION_UNTREATED_ADDCOINS', //获取用户层级
				'displaywdoprate' => array(
					'0' => 'FUND_WITHDRAW_RISK_UNTREATED_PASS',
					'1' => 'FUND_WITHDRAW_RISK_UNTREATED_REFUSE',
					'2' => 'FUND_WITHDRAW_RISK_UNTREATED_REVIEW',
					'3' => 'FUND_WITHDRAW_RISK_REVIEW_PASS',
					'4' => 'FUND_WITHDRAW_RISK_REVIEW_REFUSE',
				), //加载提现操作界面
				'displaywdoprate2' => array(
					'0' => 'FUND_WITHDRAW_RISK_UNTREATED_PASS',
					'1' => 'FUND_WITHDRAW_RISK_UNTREATED_REFUSE',
					'2' => 'FUND_WITHDRAW_RISK_UNTREATED_REVIEW',
					'3' => 'FUND_WITHDRAW_RISK_REVIEW_PASS',
					'4' => 'FUND_WITHDRAW_RISK_REVIEW_REFUSE',
				), 
                                                                                //加载充值申诉处理
                                                                                'aprl' => 'FUND_RECHARGE_APPREAL',
                                                                                'aprlall' => 'FUND_RECHARGE_APPREAL_ALL',
                                                                                'aprluntreated' => 'FUND_RECHARGE_APPREAL_UNTREATED',//显示列表页模板结构,
                                                                                'aprlpassed' => 'FUND_RECHARGE_APPREAL_PASSED',
                                                                                'aprlunpassed' => 'FUND_RECHARGE_APPREAL_UNPASSED', 
                                                                                'review' => 'FUND_RECHARGE_APPREAL_REVIEW',
                                                                                'abcf' => 'FUND_APPEAL_BANKS_CONFIG',
                                                                                'pabcf' => 'FUND_APPEAL_BANKS_CONFIG',
                                                                                'abtf' => 'FUND_APPEAL_TIPS_CONFIG',
                                                                                'ts' => 'FUND_APPEAL_TIPS_RESORT',
                                                                                'at' => 'FUND_APPEAL_TIPS_ADD',
                                                                                'qt' => 'FUND_APPEAL_TIPS_QUERY',
                                                                                'ar' => 'FUND_APPEAL_REVIEW',
                                                                                'prop' => 'FUND_APPEAL_UNTREATED_COUNTS',
                                                                                'chr' => 'FUND_APPEAL_REVIEW_CHECK',
                                                                                'exdwl' => 'FUND_APPEAL_ALL_DOWNLOAD',
                                                                                'bypass' => 'FUND_RECHARGE_BYPASS',
                                                                                'whitelist'=>'FUND_RECHARGE_WHITELIST',
                                                                                'deleteBypassWhiteListData' =>'FUND_RECHARGE_DELETE_WHITELIST',
                                                                                'saveItem' => 'FUND_RECHARGE_SAVEITEM',
                                                                                'saveBypassCfg' => 'FUND_SAVE_BYPASS',
																				'manualoprate' => 'FUND_WITHDRAW_RISK_UNTREATED_MANUAL',
																				'manualfinish' => 'FUND_WITHDRAW_RISK_HANDLING_MANUAL',
																					
                                                             ),
			'remark' 		=> array(
				"sv1"=>"FUND_RECHARGE_REMARK_MANAGER",//唯一附言管理
				"sv2"=>"FUND_RECHARGE_REMARK_RECYCLE",//附言回收管理
				"sv3"=>"FUND_RECHARGE_REMARK_SETTING",//附言配置管理
				"sv4"=>"FUND_RECHARGE_REMARK_MANAGER_UNBIND",//解除绑定
				"sv5"=>"FUND_RECHARGE_REMARK_MANAGER_UNLOCK",//解除锁定
				"sv6"=>"FUND_RECHARGE_REMARK_MANAGER_DOWNLOAD",//下载附言管理列表
				"sv7"=>"FUND_RECHARGE_REMARK_RECYCLE_DOWNLOAD",//下载附言回收管理
				"sv8"=>"FUND_RECHARGE_REMARK_SETTING_SAVE",//保存配置
				"sv9"=>"FUND_RECHARGE_REMARK_RECYCLE_OPT",//回收附言
				"sv10"=>"FUND_RECHARGE_REMARK_SETTING_IMPORT",//回收附言
				"sv11"=>"FUND_RECHARGE_REMARK_MANAGER",//加载唯一附言管理数据
				"sv12"=>"FUND_RECHARGE_REMARK_RECYCLE",//加载唯一附言回收管理数据
			),
			'reporting' 	=> array(
 				"sv51"=>"FUND_REPORT_CHARGE",//充值处理 5.1
				"sv52"=>"FUND_REPORT_WITHDRAW",//提现明细报表
				"sv521"=>"FUND_REPORT_WITHDRAW",//提现明细报表99
				"sv53"=>"FUND_REPORT_CHARGEWITHDRAW",//充值处理 5.3
				"sv54"=>"FUND_REPORT_GAMEDETAIL",//充值处理 5.4
				"sv55"=>"FUND_REPORT_FUNDDETAIL",//账户明细表
				"sv56"=>"FUND_REPORT_TOPWINLOSE",//总代盈亏报表
				"sv58"=>"FUND_REPORT_TOPWINLOSE",//玩法明细
				"rch"=>"FUND_REPORT_TOPWINLOSE",//充值明细表
				"chex"=>"FUND_REPORT_CHARGE_DOWNLOAD",//下载充值明细表
				"drex"=>"FUND_REPORT_WITHDRAW_DOWNLOAD", // 下载提现明细表
				"dchdr"=>"downloadChargeDrawData", //下载充提明细表
				"dadda"=>"FUND_REPORT_FUNDDETAIL_DOWNLOAD", //下载账户明细表
				"vddaset"=>"FUND_REPORT_CHARGE", //加载充值明细报表数据
				"vdtr"=>"FUND_REPORT_WITHDRAW", //加载提现明细报表数据
				"veda"=>"FUND_REPORT_CHARGEWITHDRAW", //加载总代充提报表数据
				"toda"=>"FUND_REPORT_GAMEDETAIL", //加载总代游戏币明细
				"adda"=>"FUND_REPORT_FUNDDETAIL", //加载账户明细报表数据
				"tada"=>"FUND_REPORT_TOPWINLOSE", //加载总代盈亏报表数据
				"sada"=>"FUND_REPORT_TOPWINLOSE", //加载下级盈亏报表数据
				"loadpm"=>"FUND_REPORT_TOPWINLOSE", //加载玩法明细
				"gtpm"=>"FUND_REPORT_TOPWINLOSE", //根据奖金组获取玩法
			),
			'transfer' 		=> array(),
			'user' 			=> array()
		);
	}
	private function __setClassName($classname){
		$this->_className = $classname;
	}
	public function  getAclArray(){
		return $this->_aAclArray[$this->_className];
	}
}