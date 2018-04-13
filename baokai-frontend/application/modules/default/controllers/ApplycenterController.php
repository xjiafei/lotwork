<?php
class Default_ApplycenterController extends Fundcommon{
	
	private $_pagesize,$_total,$_page,$_prizeUnit,$_diffValue,$_sscgadiffValue,$_sscproxydiffValue,$_othergadiffValue,$_otherproxydiffValue,$_2000diffValue,$_1diffValue,$_point2diffValue;
	private $_aAwardSeris,$_aRateName,$_aRateIndex,$_aAwardGroup,$_aAwardRedis,$_aBounsDetailAwardSeris;
	private $_gamePrize;
	
	public function init(){
		parent::init();
		$this->_pagesize = 15;
		$this->_total =0;
		$this->_page = 1;
		$this->_errView = 'ucenter';
		$this->_prizeUnit= 100; //奖金返点单位
		$this->_diffValue = 0.5;//开户时上下级最小点差
		$this->_diffValueOrigin = 0.5;//开户时上下级最小点差（初始）
		$this->_2000diffValue = 0.1;//开户时上下级最小点差
		$this->_WGdiffValue = 0.4;//WG开户时上下级最小点差
		$this->_1diffValue = 1;
		$this->_point2diffValue = 0.2;
		$this->_sscgadiffValue= 0.2;	//时时彩总代最小点差
		$this->_sscproxydiffValue= 0.1;	//时时彩代理最小点差
		$this->_othergadiffValue= 0.5;	//其它彩系总代最小点差
		$this->_otherproxydiffValue= 0.5;	//其它彩系代理最小点差
		//普通用户和奖金组全部为0的用户不能进入开户中心页面(奖金详情页除外)
		if($this->_sessionlogin->info['userLvl'] =='-1' || $this->_sessionlogin->info['isAllZero'] == '0'){
			if(!in_array($this->getRequest()->getActionName(), array('querybonusdetails','queryusergameaward'))){
				$this->_redirect('/index');
			}
		}
		$this->_gamePrize = new GamePrize();
		//hash redis
		$this->_aAwardRedis = new Rediska_Key_Hash(md5('ANVO_FUND_AWARD'.$this->_sessionlogin->info['id']));
		$this->_aAwardSeris = array(1=>'时时彩',3=>'11选5','快乐彩','低频','趣味彩','快三','宝开游艺'); //3D系列合并到低频系列
		$this->_aBounsDetailAwardSeris = array(1=>'时时彩',3=>'11选5','快乐彩','低频','趣味彩','快三'); //3D系列合并到低频系列
		$this->_aRateName = array(
			1=>array('直选返点','不定位返点','超級2000'),
			array('直选返点','不定位返点'),
			array('所有返点'),
			array('任选型返点','趣味型返点'),
			array('所有返点'),
            array('所有返点'),
            array('混选','直选','不定位返点'),
			array('所有返点'),
			array('正码--平码返点','特码--直码返点','特码--特肖返点','特码--色波&两面返点','特码--半波返点',
                  '正特码--一肖返点','正特码--不中返点','连肖(中)--二连肖、三连肖返点','连肖(中)--四连肖返点',
                  '连肖(中)--五连肖返点','连肖(不中)--二连肖、三连肖返点','连肖(不中)--四连肖返点','连肖(不中)--五连肖返点','连码返点')
		);
		$this->_aRateIndex = array(
				1=>array(1,2,8),
				array(1,2),
				array(3),
				array(4,5),
				array(3),
                array(3),
                array(6,7,22),
				array(8),
				array(11,1,9,10,12,13,14,15,16,17,18,19,20,21) //awardType 1 直選 ,9生肖,10色波
				
						);
		$this->_aAwardGroup = array(
			'1'=>array(
					'99101'=>'重庆时时彩',
					'99102'=>'江西时时彩',
					'99105'=>'黑龙江时时彩',
					'99103'=>'新疆时时彩',
					'99107'=>'上海时时乐',
					'99106'=>'宝开时时彩',
					'99104'=>'天津时时彩', 
					'99111'=>'宝开1分彩',					
					'99114'=>'腾讯分分彩',
					'99112'=>'秒开时时彩' 
					//,
					//'99113'=>'<font size="4">超级2000秒秒彩<br/><center>（APP版）</center></font>'
			),
			'3'=>array(
					'99301'=>'山东11选5',
					'99302'=>'江西11选5',
					'99303'=>'广东11选5', 
					'99305'=>'宝开11选5',
					'99306'=>'秒开11选5',
					'99307'=>'江苏11选5',
						
			),
			'4'=>array('99201'=>'北京快乐8'),
			'5'=>array(
					'99108'=>'3D',
					'99109'=>'排列5',
					'99401'=>'双色球'
			),
			'6'=>array('99701'=>'六合彩'),
			'7' => array(//原6
					'99501'=>'江苏快三',
                    '99601'=>'江苏骰宝',
					'99502'=>'安徽快三',
					'99602'=>'高频骰宝</br><center>娱乐厅</center>',
					'99603'=>'高频骰宝</br><center>至尊厅</center>'
			),
			'8' => array(
					//'88101'=>'休闲游戏', //#9443: FH4.0前台 - 无法生成开户链接，会出现至少选择一个奖金组 //休閒遊戲已不使用 edit by david 20160802
					'88102'=>'FHX宝开游艺',
			)
		);
		$toDay = new DateTime();
		$removeDay = new DateTime('2016-02-22T00:00:00');
		if($toDay >= $removeDay){
		unset($this->_aAwardGroup[1][99102]);
		}
		
		$aUrl['lottery'] = 'stopLottery';
		$data['param']['status'] = 0;
		$res = $this->_clientobject->inRequestV2($data, $aUrl);
		//count=1 有下架彩種
		if(count($res['body']['result'])>0){
			foreach ($res['body']['result']['list'] as $key=>$value){
				if($value['lotterySeriesCode'] == 2){
						unset($this->_aAwardGroup[5][$value['lotteryid']]);
				}elseif($value['lotterySeriesCode'] == 6){
						unset($this->_aAwardGroup[7][$value['lotteryid']]);
				}elseif($value['lotterySeriesCode'] == 9){
						unset($this->_aAwardGroup[6][$value['lotteryid']]);
				}else{
						unset($this->_aAwardGroup[$value['lotterySeriesCode']][$value['lotteryid']]);
				}
			}
		}
	}
	//初始化界面以及生成开户的操作
	public function indexAction(){
		$request = $this->getRequest();
		$lvl = $this->_sessionlogin->info['userLvl'];
		$iType = $this->_sessionlogin->info['userLvl'] == '0' ? 0 : 1;
		//抓取返點設定資料
		$userAwardListStruc = $this->_gamePrize->getGamePrizeList($this->_sessionlogin->info['id'],$iType);
		
								$type = intval(getSecurityInput($this->_request->getParam('type','1')));
		//保存配置信息
		if($request->isPost()){
			//生成开户的操作
			$prizeList 	 = array();
			$days 	 	 = intval(getSecurityInput($this->_request->getPost('days','-1')));
			$setUp 	 	 = intval(getSecurityInput($this->_request->getPost('SetUp')));
			$qq 	 	 = intval(getSecurityInput($this->_request->getPost('qq')));
			if(!in_array($days, array(-1,1,5,10,30))){
				$days = -1;
			}
			
																		//setUp(1:詳細設置/2:快捷設置)
			if($setUp == '2'){
				$setValue= floatval(getSecurityInput($this->_request->getPost('Rebate')));//保留返点
			}
			//type(0:玩家/1:代理)
			if($type == 0){
				$remarks = getSecurityInput($this->_request->getPost('Remarks',''));
			}
			
			$lotteryStatus = array();
			if($setValue<0){
				$setValue = 0;
			}
			foreach ($userAwardListStruc as $key=>$value){
				$groupSeriesCode = $value['lotterySeriesCode'];
                if ( $value['lotterySeriesCode']=='2' ) {
                    $groupSeriesCode = 5;
                }
                else if ( $value['lotterySeriesCode']=='6' ) {
                    $groupSeriesCode = 7;
                }else if ( $value['lotterySeriesCode'] == '9' ) {
					$groupSeriesCode = 6;
				}
				if(!array_key_exists($value['lotteryId'], $this->_aAwardGroup[$groupSeriesCode])){
					continue;
				}
				//时时彩 用时时彩点差   其它非六合彩用其它彩系点差
				if($groupSeriesCode=='1'){
					if($iType =='0'){
						$this->_diffValue=$this->_sscgadiffValue;
					}else{
						$this->_diffValue=$this->_sscproxydiffValue;
					}
				}else if($groupSeriesCode!='6'){
					if($iType =='0'){
						$this->_diffValue=$this->_othergadiffValue;
					}else{
						$this->_diffValue=$this->_otherproxydiffValue;
					}
				}
				//快捷配置
				if($setUp == '2'){

					//判断门限值是否在可配置范围内
					if ($setValue!=0 &&($value['directLimitRet']/$this->_prizeUnit < $setValue+$this->_diffValue || $value['threeLimitRet']/$this->_prizeUnit <$setValue+$this->_diffValue)){
						echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102074')));
						exit;
					} else {
						//可分配返点
						$value['directRet']   = floatval($setValue*$this->_prizeUnit);
						$value['threeoneRet'] = floatval($setValue*$this->_prizeUnit);
						$value['superRet'] = floatval($setValue*$this->_prizeUnit);
						$value['directLimitRet']   = floatval($setValue*$this->_prizeUnit);
						$value['threeLimitRet']    = floatval($setValue*$this->_prizeUnit);
						$value['superLimitRet']    = floatval($setValue*$this->_prizeUnit);
						$value['lhcYear']    = floatval($setValue*$this->_prizeUnit);
						$value['lhcColor']    = floatval($setValue*$this->_prizeUnit);
						$value['sbThreeoneRet']    = floatval($setValue*$this->_prizeUnit);
						$value['lhcFlatcode']    = floatval($setValue*$this->_prizeUnit);
						$value['lhcHalfwave']    = floatval($setValue*$this->_prizeUnit);
						$value['lhcOneyear']    = floatval($setValue*$this->_prizeUnit);
						$value['lhcNotin']    = floatval($setValue*$this->_prizeUnit);
						$value['lhcContinuein23']    = floatval($setValue*$this->_prizeUnit);
						$value['lhcContinuein4']    = floatval($setValue*$this->_prizeUnit);
						$value['lhcContinuein5']    = floatval($setValue*$this->_prizeUnit);
						$value['lhcContinuenotin23']    = floatval($setValue*$this->_prizeUnit);
						$value['lhcContinuenotin4']    = floatval($setValue*$this->_prizeUnit);
						$value['lhcContinuenotin5']    = floatval($setValue*$this->_prizeUnit);
						$value['lhcContinuecode']    = floatval($setValue*$this->_prizeUnit);
					}
					$prizeList[] = $value;
				} else { 
											//详细配置
					//用来判断彩种是否一个奖金组都没选择
					$lotteryKey = $value['lotterySeriesCode'].'_'.$value['lotteryId'];
										if(!isset($lotteryStatus[$lotteryKey])){
						$lotteryStatus[$lotteryKey] =  0;
					}
					
					$prize_key = '_'.$value['lotterySeriesCode'].'_'.$value['lotteryId'].'_'.$value['awardGroupId'];
					$directRet   = floatval(getSecurityInput($this->_request->getPost('directRet'.$prize_key)));
					$threeoneRet = floatval(getSecurityInput($this->_request->getPost('threeoneRet'.$prize_key)));
					$superRet = floatval(getSecurityInput($this->_request->getPost('superRet'.$prize_key)));
					$lhcYear = floatval(getSecurityInput($this->_request->getPost('lhcYear'.$prize_key)));
					$lhcColor = floatval(getSecurityInput($this->_request->getPost('lhcColor'.$prize_key)));
					$sbThreeoneRet = floatval(getSecurityInput($this->_request->getPost('sbThreeoneRet'.$prize_key)));
					$lhcFlatcode = floatval(getSecurityInput($this->_request->getPost('lhcFlatcode'.$prize_key)));
					$lhcHalfwave = floatval(getSecurityInput($this->_request->getPost('lhcHalfwave'.$prize_key)));
					$lhcOneyear = floatval(getSecurityInput($this->_request->getPost('lhcOneyear'.$prize_key)));
					$lhcNotin = floatval(getSecurityInput($this->_request->getPost('lhcNotin'.$prize_key)));
					$lhcContinuein23 = floatval(getSecurityInput($this->_request->getPost('lhcContinuein23'.$prize_key)));
					$lhcContinuein4 = floatval(getSecurityInput($this->_request->getPost('lhcContinuein4'.$prize_key)));
					$lhcContinuein5 = floatval(getSecurityInput($this->_request->getPost('lhcContinuein5'.$prize_key)));
					$lhcContinuenotin23 = floatval(getSecurityInput($this->_request->getPost('lhcContinuenotin23'.$prize_key)));
					$lhcContinuenotin4 = floatval(getSecurityInput($this->_request->getPost('lhcContinuenotin4'.$prize_key)));
					$lhcContinuenotin5 = floatval(getSecurityInput($this->_request->getPost('lhcContinuenotin5'.$prize_key)));
					$lhcContinuecode = floatval(getSecurityInput($this->_request->getPost('lhcContinuecode'.$prize_key)));
					
					$fp=fopen("test.txt","a+");
					fwrite($fp,$superRet);
					fclose($fp);					
					
					$value['status'] = intval(getSecurityInput($this->_request->getPost('status'.$prize_key,'0')));
										$status = $value['status'];
					if($value['status'] == 1){//獎金組  ，1 進行中，2已刪除
						/* if (($directRet!=0 && $value['directLimitRet']/$this->_prizeUnit < $directRet+$this->_diffValue) 
						|| ($threeoneRet!=0 && $value['threeLimitRet']/$this->_prizeUnit < $threeoneRet+$this->_diffValue)){
							echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102088')));
							exit;
						} */
						
						//设置返点过大时候 自动修正返点值  //原返點 /100 < 新返點+0.5
						if($value['lotteryId'] == '99701'){//六合彩，總代可不保留返點值
							if(($directRet!=0 && $iType!=0 && $value['directRet']/$this->_prizeUnit < $directRet+$this->_1diffValue)){
								$directRet = $value['directRet']/$this->_prizeUnit - $this->_1diffValue;
							}
						}else if(($directRet!=0 && $value['directRet']/$this->_prizeUnit < $directRet+$this->_diffValue)){
							if($value['lotteryId'] == '88101' || $value['lotteryId'] == '88102'){
								if(($directRet!=0 && $value['directRet']/$this->_prizeUnit < $directRet+$this->_WGdiffValue)){
									$directRet = $value['directRet']/$this->_prizeUnit - $this->_WGdiffValue;
								}
							}else{
								if(($directRet!=0 && $value['directRet']/$this->_prizeUnit < $directRet+$this->_diffValue)){
									$directRet = $value['directRet']/$this->_prizeUnit - $this->_diffValue;
								}
							}
						}
						if(($threeoneRet!=0 && $value['threeoneRet']/$this->_prizeUnit < $threeoneRet+$this->_diffValue)){
							$threeoneRet = $value['threeoneRet']/$this->_prizeUnit - $this->_diffValue;
						}
						if(($superRet!=0 && $value['superRet']/$this->_prizeUnit < $superRet+$this->_2000diffValue)){
							$superRet = $value['superRet']/$this->_prizeUnit - $this->_2000diffValue;
						}
						if(($lhcYear!=0 && $value['lhcYear']/$this->_prizeUnit < $lhcYear+$this->_diffValue)){
							$lhcYear = $value['lhcYear']/$this->_prizeUnit - $this->_diffValue;
						}
						if(($lhcColor!=0 && $value['lhcColor']/$this->_prizeUnit < $lhcColor+$this->_diffValue)){
							$lhcColor = $value['lhcColor']/$this->_prizeUnit - $this->_diffValue;
						}
						if(($lhcFlatcode!=0 && $value['lhcFlatcode']/$this->_prizeUnit < $lhcFlatcode+$this->_diffValue)){
							$lhcFlatcode = $value['lhcFlatcode']/$this->_prizeUnit - $this->_diffValue;
						}
						if(($lhcHalfwave!=0 && $value['lhcHalfwave']/$this->_prizeUnit < $lhcHalfwave+$this->_diffValue)){
							$lhcHalfwave = $value['lhcHalfwave']/$this->_prizeUnit - $this->_diffValue;
						}
						if(($lhcOneyear!=0 && $value['lhcOneyear']/$this->_prizeUnit < $lhcOneyear+$this->_point2diffValue)){
							$lhcOneyear = $value['lhcOneyear']/$this->_prizeUnit - $this->_point2diffValue;
						}
						if(($lhcNotin!=0 && $value['lhcNotin']/$this->_prizeUnit < $lhcNotin+$this->_diffValue)){
							$lhcNotin = $value['lhcNotin']/$this->_prizeUnit - $this->_diffValue;
						}
						if(($lhcContinuein23!=0 && $value['lhcContinuein23']/$this->_prizeUnit < $lhcContinuein23+$this->_diffValue)){
							$lhcContinuein23 = $value['lhcContinuein23']/$this->_prizeUnit - $this->_diffValue;
						}
						if(($lhcContinuein4!=0 && $value['lhcContinuein4']/$this->_prizeUnit < $lhcContinuein4+$this->_diffValue)){
							$lhcContinuein4 = $value['lhcContinuein4']/$this->_prizeUnit - $this->_diffValue;
						}
						if(($lhcContinuein5!=0 && $value['lhcContinuein5']/$this->_prizeUnit < $lhcContinuein5+$this->_1diffValue)){
							$lhcContinuein5 = $value['lhcContinuein5']/$this->_prizeUnit - $this->_1diffValue;
						}
						if(($lhcContinuenotin23!=0 && $value['lhcContinuenotin23']/$this->_prizeUnit < $lhcContinuenotin23+$this->_diffValue)){
							$lhcContinuenotin23 = $value['lhcContinuenotin23']/$this->_prizeUnit - $this->_diffValue;
						}
						if(($lhcContinuenotin4!=0 && $value['lhcContinuenotin4']/$this->_prizeUnit < $lhcContinuenotin4+$this->_diffValue)){
							$lhcContinuenotin4 = $value['lhcContinuenotin4']/$this->_prizeUnit - $this->_diffValue;
						}
						if(($lhcContinuenotin5!=0 && $value['lhcContinuenotin5']/$this->_prizeUnit < $lhcContinuenotin5+$this->_diffValue)){
							$lhcContinuenotin5 = $value['lhcContinuenotin5']/$this->_prizeUnit - $this->_diffValue;
						}
						if(($lhcContinuecode!=0 && $value['lhcContinuecode']/$this->_prizeUnit < $lhcContinuecode+$this->_diffValue)){
							$lhcContinuecode = $value['lhcContinuecode']/$this->_prizeUnit - $this->_diffValue;
						}
						if(($sbThreeoneRet!=0 && $value['sbThreeoneRet']/$this->_prizeUnit < $sbThreeoneRet+$this->_diffValue)){
							$sbThreeoneRet = $value['sbThreeoneRet']/$this->_prizeUnit - $this->_diffValue;
						}
						//返点为负数
						if($directRet<0){
							$directRet = 0;
						}
						if($threeoneRet<0){
							$threeoneRet = 0;
						}
						if($superRet<0){
							$superRet = 0;
						}
						if($lhcYear<0){
							$lhcYear = 0;
						}
						if($lhcColor<0){
							$lhcColor = 0;
						}
						if($sbThreeoneRet<0){
							$sbThreeoneRet = 0;
						}
						if($lhcFlatcode<0){
							$lhcFlatcode = 0;
						}
						if($lhcHalfwave<0){
							$lhcHalfwave = 0;
						}
						if($lhcOneyear<0){
							$lhcOneyear = 0;
						}
						if($lhcNotin<0){
							$lhcNotin = 0;
						}
						if($lhcContinuein23<0){
							$lhcContinuein23 = 0;
						}
						if($lhcContinuein4<0){
							$lhcContinuein4 = 0;
						}
						if($lhcContinuein5<0){
							$lhcContinuein5 = 0;
						}
						if($lhcContinuenotin23<0){
							$lhcContinuenotin23 = 0;
						}
						if($lhcContinuenotin4<0){
							$lhcContinuenotin4 = 0;
						}
						if($lhcContinuenotin5<0){
							$lhcContinuenotin5 = 0;
						}
						if($lhcContinuecode<0){
							$lhcContinuecode = 0;
						}
						//双色球,11选5 一个返点
						if(in_array($value['lotterySeriesCode'], array(3,5,6))) {
							$threeoneRet = $directRet;
						}
						
						$value['directRet']   = $directRet* $this->_prizeUnit;
						$value['threeoneRet'] = $threeoneRet* $this->_prizeUnit;
						$value['superRet'] = $superRet* $this->_prizeUnit;
						$value['lhcYear'] = $lhcYear* $this->_prizeUnit;
						$value['lhcColor'] = $lhcColor* $this->_prizeUnit;
						$value['sbThreeoneRet'] = $sbThreeoneRet* $this->_prizeUnit;
						$value['lhcFlatcode'] = $lhcFlatcode* $this->_prizeUnit;
						$value['lhcHalfwave'] = $lhcHalfwave* $this->_prizeUnit;
						$value['lhcOneyear'] = $lhcOneyear* $this->_prizeUnit;
						$value['lhcNotin'] = $lhcNotin* $this->_prizeUnit;
						$value['lhcContinuein23'] = $lhcContinuein23* $this->_prizeUnit;
						$value['lhcContinuein4'] = $lhcContinuein4* $this->_prizeUnit;
						$value['lhcContinuein5'] = $lhcContinuein5* $this->_prizeUnit;
						$value['lhcContinuenotin23'] = $lhcContinuenotin23* $this->_prizeUnit;
						$value['lhcContinuenotin4'] = $lhcContinuenotin4* $this->_prizeUnit;
						$value['lhcContinuenotin5'] = $lhcContinuenotin5* $this->_prizeUnit;
						$value['lhcContinuecode'] = $lhcContinuecode* $this->_prizeUnit;
						$lotteryStatus[$lotteryKey] = 1;
						$prizeList[] 	      = $value;
					}
				}
			}
			
			//每个彩种至少要选择一个奖金组
			if(in_array(0, $lotteryStatus)){
				echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102141')));
				exit;
			}
			//奖金组返点不能为空
			if(count($prizeList)<=0){
				echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102085')));
				exit;
			}
			$aUrl['prize'] = 'createURL';
			$data['param']['type']  = $type;
			$data['param']['days']  = $days;
			$data['param']['qq']  = $qq;
			if($type == '1'){//代理
				$data['param']['size']  = 1;
			} else {//玩家
				$data['param']['memo'] = $remarks;
			}
			$data['param']['urls']  = getWebSiteDomain().'/register/index/';
			$urls = $data['param']['urls'];
			$data['param']['userawardListStruc'] = $prizeList;
			$res = $this->_clientobject->inRequestV2($data, $aUrl);
			
						if(isset($res['head']['status']) && $res['head']['status']=='0'){
				echo Zend_Json::encode(array('status'=>'ok','data'=>$days));
				exit;
			} else {
				echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102072')));
				exit;
			}
		} else {//初始化界面
			$result = array();
			$emptyRewards = 0;
			$maxValue = NULL;
			
			if(count($userAwardListStruc)>0){
				foreach($userAwardListStruc as $key=>$value){
					if($value['lotterySeriesCode'] == '2'){
						$groupSeriesCode = 5;
                    }elseif ( $value['lotterySeriesCode'] == '6' ) {
						$groupSeriesCode = 7;
					}elseif ( $value['lotterySeriesCode'] == '9' ) {
						$groupSeriesCode = 6;
					}else {
						$groupSeriesCode = $value['lotterySeriesCode'];
					}
					if(!array_key_exists($value['lotteryId'], $this->_aAwardGroup[$groupSeriesCode])){
						continue;
					}
					//时时彩 用时时彩点差   其它非六合彩用其它彩系点差
					if($groupSeriesCode=='1'){
						if($iType =='0'){
							$this->_diffValue=$this->_sscgadiffValue;
						}else{
							$this->_diffValue=$this->_sscproxydiffValue;
						}
					}else if($groupSeriesCode!='6'){
						if($iType =='0'){
							$this->_diffValue=$this->_othergadiffValue;
						}else{
							$this->_diffValue=$this->_otherproxydiffValue;
						}
					}
					if($value['lotteryId'] == '88101' || $value['lotteryId'] == '88102'){
						$value['directLimitRet'] = $value['directLimitRet']/$this->_prizeUnit > $this->_WGdiffValue ? $value['directLimitRet']/$this->_prizeUnit - $this->_WGdiffValue : 0;
					}else if($value['lotteryId'] == '99701'){//六合彩
						if($iType ==0 && $type ==1){ //总代在设置直选返点时，没有最小级差（可以不保留返点）。只有代理玩家不用
							$value['directLimitRet'] = $value['directLimitRet']/$this->_prizeUnit > $this->_1diffValue ? $value['directLimitRet']/$this->_prizeUnit: 0;
						}else{
							$value['directLimitRet'] = $value['directLimitRet']/$this->_prizeUnit > $this->_1diffValue ? $value['directLimitRet']/$this->_prizeUnit - $this->_1diffValue : 0;	
						}
					}else{
						$value['directLimitRet'] = $value['directLimitRet']/$this->_prizeUnit > $this->_diffValue ? $value['directLimitRet']/$this->_prizeUnit - $this->_diffValue : 0;
					}
					
					$value['threeLimitRet']  = $value['threeLimitRet']/$this->_prizeUnit  > $this->_diffValue ? $value['threeLimitRet']/$this->_prizeUnit - $this->_diffValue  : 0;
					$value['superLimitRet']  = $value['superLimitRet']/$this->_prizeUnit  > $this->_2000diffValue ? $value['superLimitRet']/$this->_prizeUnit - $this->_2000diffValue  : 0;

					$value['lhcYearLimit']  = $value['lhcYearLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcYearLimit']/$this->_prizeUnit - $this->_diffValue  : 0;

					$value['lhcColorLimit']  = $value['lhcColorLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcColorLimit']/$this->_prizeUnit - $this->_diffValue  : 0;
					
					$value['sbThreeoneRetLimit']  = $value['sbThreeoneRetLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['sbThreeoneRetLimit']/$this->_prizeUnit - $this->_diffValue  : 0;

					$value['lhcFlatcodeLimit']  = $value['lhcFlatcodeLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcFlatcodeLimit']/$this->_prizeUnit - $this->_diffValue  : 0;

					$value['lhcHalfwaveLimit']  = $value['lhcHalfwaveLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcHalfwaveLimit']/$this->_prizeUnit - $this->_diffValue  : 0;

					$value['lhcOneyearLimit']  = $value['lhcOneyearLimit']/$this->_prizeUnit  > $this->_point2diffValue ? $value['lhcOneyearLimit']/$this->_prizeUnit - $this->_point2diffValue  : 0;

					$value['lhcNotinLimit']  = $value['lhcNotinLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcNotinLimit']/$this->_prizeUnit - $this->_diffValue  : 0;

					$value['lhcContinuein23Limit']  = $value['lhcContinuein23Limit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuein23Limit']/$this->_prizeUnit - $this->_diffValue  : 0;

					$value['lhcContinuein4Limit']  = $value['lhcContinuein4Limit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuein4Limit']/$this->_prizeUnit - $this->_diffValue  : 0;

					$value['lhcContinuein5Limit']  = $value['lhcContinuein5Limit']/$this->_prizeUnit  > $this->_1diffValue ? $value['lhcContinuein5Limit']/$this->_prizeUnit - $this->_1diffValue  : 0;

					$value['lhcContinuenotin23Limit']  = $value['lhcContinuenotin23Limit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuenotin23Limit']/$this->_prizeUnit - $this->_diffValue  : 0;

					$value['lhcContinuenotin4Limit']  = $value['lhcContinuenotin4Limit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuenotin4Limit']/$this->_prizeUnit - $this->_diffValue  : 0;

					$value['lhcContinuenotin5Limit']  = $value['lhcContinuenotin5Limit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuenotin5Limit']/$this->_prizeUnit - $this->_diffValue  : 0;

					$value['lhcContinuecodeLimit']  = $value['lhcContinuecodeLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuecodeLimit']/$this->_prizeUnit - $this->_diffValue  : 0;
						
					$result[$groupSeriesCode][$value['lotteryId']][] = $value;			
					
					if ($maxValue ===NULL) {
						$maxValue = $value['directLimitRet'];
					}
					$maxValue = $this->_gamePrize->getMinValue($maxValue, $value['directLimitRet'], $value['threeLimitRet'],$value['superLimitRet']);
				}
				ksort($result);
				
				
				
			} else {
// 				$this->addErr('');
				$emptyRewards = 1;
			}
			//奖金组排序
			foreach ($result as $key=>$value){
				foreach($value as $sub_key=>$sub_value){
					$value[$sub_key] = $this->_gamePrize->awardArray_sort($sub_value);
				}
				$result[$key] = $value;
			}
 			//print_r($result);
			//exit();
			 
			$diffvalue = $this->_diffValueOrigin;
			$value200 = $this->_2000diffValue ;//开户时上下级最小点差
			$wgvalue = $this->_WGdiffValue;//WG开户时上下级最小点差
			$ldc1 = $this->_1diffValue;
			$ldc2 = $this->_point2diffValue ;
			$this->view->emptyRewards = $emptyRewards;
			$this->view->result = $result;
			$this->view->type   = $type;
			$this->view->diffvalue = $diffvalue;
			$this->view->wgvalue = $wgvalue;//0.4
			$this->view->ldc1 = $ldc1;//1
			$this->view->ldc2 = $ldc2;//0.2
			$this->view->value200 = $value200;//0.1
			$this->view->maxValue   = $maxValue>$this->_diffValue ? $maxValue: 0;
			$this->view->aAwardSeris = $this->_aAwardSeris;
			$this->view->aAwardGroup = $this->_aAwardGroup;
			$this->view->aRateName = $this->_aRateName;
			if($type == 1){
				$this->view->display('default/ucenter/prize/prizesetting.tpl');
			} else {
				$this->view->display('default/ucenter/prize/usersetting.tpl');
			}
		}
	}
	
	//链接管理
	public function managerurlAction(){
		$page   = intval(getSecurityInput($this->_request->getParam('page')));
		$type   = intval(getSecurityInput($this->_request->getParam('type','1')));
		if(!empty($page)){
			$this->_page = $page;
		}
		$data['param']= array('type'=>$type);
		$data['pager'] =array(
					'startNo' => ($this->_page-1)*$this->_pagesize+1,
					"endNo"   =>$this->_page*$this->_pagesize
		);
		$reuslt = array();
		$aUrl['prize'] = 'urlList';
		$res = $this->_clientobject->inRequestV2($data, $aUrl);
		if(isset($res['head']['status']) && $res['head']['status']=='0'){
			if(count($res['body']['result'])>0){
				foreach ($res['body']['result'] as $key=>$value){
					unset($value['userawardListStruc']);
					unset($value['strUserawardListStruc']);
					$value['valiable'] = 1;
					if($value['days'] !='-1' && getSrtTimeByLong($value['gmtCreated'])+$value['days']*86400<time()){
						$value['valiable'] = 0;
					}
					$value['gmtCreated'] = date('Y-m-d H:i:s',getSrtTimeByLong($value['gmtCreated']));
					$value['url'] = 'http://'.getWebSiteDomain().'/register/?'.$value['url'];
					$reuslt[$key] = $value;
				}
			}
			$this->_total = $res['body']['pager']['total'];//总记录数
			$this->view->agentCnt = $res['body']['pager']['otherParam']['A'];//总记录数
			$this->view->userCnt = $res['body']['pager']['otherParam']['B'];//总记录数
		}
		$pages = CommonPages::getPages ( $this->_total, $this->_page, $this->_pagesize );
		$this->view->pages = $pages;
		$this->view->result = $reuslt;
		$this->view->display('default/ucenter/prize/urllist.tpl');
	}
	
	//根据ID获取链接详情
	public function geturlbyidAction(){
		$userid = $this->_sessionlogin->info ['id'];
		
		$id = intval(getSecurityInput($this->_request->get('id')));
		$aUrl['prize'] = 'geturlById';
		$data['param']['id'] = $id;
		$res = $this->_clientobject->inRequestV2($data, $aUrl);
		$creator = $res['body']['result']['creator'];
		
		if($userid != $creator){
			header("Location: /index");
			exit;
		}
		$result = $aUrl = array();
		$aUrl['url'] = 'http://'.getWebSiteDomain().'/register/?';
		if(isset($res['head']['status']) && $res['head']['status'] =='0'){
			$aUrl['exp'] = '';
			if(isset($res['body']['result']['days'])){
				$aUrl['days'] = $res['body']['result']['days'];
				if($aUrl['days']=='-1'){
					$aUrl['exp'] = '永久有效';
				} else {
					$aUrl['createTime'] = date('Y-m-d',getSrtTimeByLong($res['body']['result']['gmtCreated']));
					$aUrl['exp'] = date('Y-m-d',getSrtTimeByLong($res['body']['result']['gmtCreated'])+$aUrl['days']*86400);
				}
			}
			//开户链接
			if(isset($res['body']['result']['url'])){
				$aUrl['url'] .= $res['body']['result']['url'];
			}
			//开户链接奖金组
			if(count($res['body']['result']['userawardListStruc']>0)){
				foreach ($res['body']['result']['userawardListStruc'] as $key=>$value){
					
				
					
					$value['directRet'] = $value['directRet']/$this->_prizeUnit;
					$value['threeoneRet']  = $value['threeoneRet']/$this->_prizeUnit;
					$value['superRet']  = $value['superRet']/$this->_prizeUnit;
					$value['lhcYear']  = $value['lhcYear']/$this->_prizeUnit;
					$value['lhcColor']  = $value['lhcColor']/$this->_prizeUnit;
					$value['lhcFlatcode']  = $value['lhcFlatcode']/$this->_prizeUnit;
					$value['lhcHalfwave']  = $value['lhcHalfwave']/$this->_prizeUnit;
					$value['lhcOneyear']  = $value['lhcOneyear']/$this->_prizeUnit;
					$value['lhcNotin']  = $value['lhcNotin']/$this->_prizeUnit;
					$value['lhcContinuein23']  = $value['lhcContinuein23']/$this->_prizeUnit;
					$value['lhcContinuein4']  = $value['lhcContinuein4']/$this->_prizeUnit;
					$value['lhcContinuein5']  = $value['lhcContinuein5']/$this->_prizeUnit;
					$value['lhcContinuenotin23']  = $value['lhcContinuenotin23']/$this->_prizeUnit;
					$value['lhcContinuenotin4']  = $value['lhcContinuenotin4']/$this->_prizeUnit;
					$value['lhcContinuenotin5']  = $value['lhcContinuenotin5']/$this->_prizeUnit;
					$value['lhcContinuecode']  = $value['lhcContinuecode']/$this->_prizeUnit;
					$value['sbThreeoneRet']  = $value['sbThreeoneRet']/$this->_prizeUnit;
					if($value['lotterySeriesCode'] == '2'){
						$result[5][$value['lotteryId']][] = $value;
					}elseif($value['lotterySeriesCode'] == '6'){
						$result[7][$value['lotteryId']][] = $value;
					}elseif($value['lotterySeriesCode'] == '9'){
						$result[6][$value['lotteryId']][] = $value;
					}
					else {
						$result[$value['lotterySeriesCode']][$value['lotteryId']][] = $value;
					}
				}
			}
			ksort($result);
			//奖金组排序
			foreach ($result as $key=>$value){
				foreach($value as $sub_key=>$sub_value){
					$value[$sub_key] = $this->_gamePrize->awardArray_sort($sub_value);
				}
				$result[$key] = $value;
			}
		}
		$this->view->aUrl = $aUrl;
		$this->view->result = $result;
		$this->view->aAwardSeris = $this->_aAwardSeris;
		$this->view->aAwardGroup = $this->_aAwardGroup;
		$this->view->aRateName = $this->_aRateName;
		$this->view->display('default/ucenter/prize/urlsettinginfo.tpl');
	}
	
	//根据ID删除链接
	public function deleteurlbyidAction(){
		$id = intval(getSecurityInput($this->_request->getPost('id')));
		$aUrl['prize'] = 'deleteurlbyid';
		$data['param']['id'] = $id;
		$res = $this->_clientobject->inRequestV2($data, $aUrl);
		if(isset($res['head']['status']) && $res['head']['status'] =='0'){
			echo Zend_Json::encode(array('status'=>'ok','data'=>'删除成功!'));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','data'=>'删除失败!'));
			exit;
		}
	}
	
	//修改备注
	public function modifymemoAction(){
		$id = intval(getSecurityInput($this->_request->getPost('id')));
		$memo = getSecurityInput($this->_request->getPost('memo'));
		$data['param'] = array(
			'id'   => $id,
			'memo' => $memo
		);
		$aUrl['prize'] = 'modifyMemo';
		$res = $this->_clientobject->inRequestV2($data, $aUrl);
		if(isset($res['head']['status']) && $res['head']['status'] =='0'){
			echo Zend_Json::encode(array('status'=>'ok','data'=>'修改成功!'));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102073')));
			exit;
		}
	}
	
	//获取链接注册用户
	public function getregistersAction(){
		$id = intval(getSecurityInput($this->_request->getPost('id')));
		$aUrl['prize'] = 'getRegisters';
		$data['param']['id'] = $id;
		$res = $this->_clientobject->inRequestV2($data, $aUrl);
		if(isset($res['head']['status']) && $res['head']['status'] =='0'){
			$registerUser = '';
			if(count($res['body']['result'])>0){
				$registerUser =implode(',', $res['body']['result']);
			}
			echo Zend_Json::encode(array('status'=>'ok','data'=>trim($registerUser,',')));
			exit;
		}
		echo Zend_Json::encode(array('status'=>'eror'));
		exit;
	}
	
	//显示返点修改界面
	public function displaymodifyprizeAction(){
		
		$request = $this->getRequest();
		$iType = $this->_sessionlogin->info['userLvl'] == '0' ? 0 : 1;
		$userAwardListStruc = $this->_gamePrize->getGamePrizeList($this->_sessionlogin->info['id'],$iType);

		$userId = getSecurityInput($this->_request->getParam('id'));
		$userInfo = $this->getUserInfoById($userId);
		if($userInfo['userLvl']>0){
			$type = 1;
		} else {
			$type = 0;
		}
		$userLvl = $userInfo['userLvl'];
		$userTypeKey 	   = 'Applycenter_modifyprize_userType_'.$userId;
		$userIdKey 		   = 'Applycenter_modifyprize_userId_'.$userId;
		//如果不是当前的直接下级,则返回不让用户进入修改页面
		if(!isset($userInfo['parentId']) || $userInfo['parentId'] != $this->_sessionlogin->info['id']){
			$this->_redirect($_SERVER["HTTP_REFERER"]);
		}
		$result = array();
		$emptyRewards = 0;
		$maxValue = NULL;
			
		if(count($userAwardListStruc)>0){
			$userDownAwardArray = $this->_gamePrize->getGamePrizeList($userInfo['id']);
			foreach($userDownAwardArray as $key=>$value){
				if($value['lotteryId'] == '99701'){
					$value['directLimitRet'] = $value['directLimitRet']/$this->_prizeUnit > $this->_1diffValue ? $value['directLimitRet']/$this->_prizeUnit - $this->_1diffValue : 0;
				}else{
					$value['directLimitRet'] = $value['directLimitRet']/$this->_prizeUnit > $this->_diffValue ? $value['directLimitRet']/$this->_prizeUnit - $this->_diffValue : 0;
				}
				$value['threeLimitRet']  = $value['threeLimitRet']/$this->_prizeUnit  > $this->_diffValue ? $value['threeLimitRet']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['superLimitRet'] = $value['superLimitRet']/$this->_prizeUnit > $this->_2000diffValue ? $value['superLimitRet']/$this->_prizeUnit - $this->_2000diffValue : 0;
				$value['maxDirectRet']   = isset($value['maxDirectRet']) ? $value['maxDirectRet']  : $value['directRet'];
				$value['maxThreeOneRet'] = isset($value['maxThreeOneRet']) ? $value['maxThreeOneRet']: $value['threeoneRet'];
				$value['maxSuperRet'] = isset($value['maxSuperRet']) ? $value['maxSuperRet']: $value['superRet'];
				$value['lhcYearLimit']  = $value['lhcYearLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcYearLimit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['lhcColorLimit']  = $value['lhcColorLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcColorLimit']/$this->_prizeUnit - $this->_diffValue  : 0;

				$value['lhcFlatcodeLimit']  = $value['lhcFlatcodeLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcFlatcodeLimit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['lhcHalfwaveLimit']  = $value['lhcHalfwaveLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcHalfwaveLimit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['lhcOneyearLimit']  = $value['lhcOneyearLimit']/$this->_prizeUnit  > $this->_point2diffValue ? $value['lhcOneyearLimit']/$this->_prizeUnit - $this->_point2diffValue  : 0;
				$value['lhcNotinLimit']  = $value['lhcNotinLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcNotinLimit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['lhcContinuein23Limit']  = $value['lhcContinuein23Limit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuein23Limit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['lhcContinuein4Limit']  = $value['lhcContinuein4Limit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuein4Limit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['lhcContinuein5Limit']  = $value['lhcContinuein5Limit']/$this->_prizeUnit  > $this->_1diffValue ? $value['lhcContinuein5Limit']/$this->_prizeUnit - $this->_1diffValue  : 0;
				$value['lhcContinuenotin23Limit']  = $value['lhcContinuenotin23Limit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuenotin23Limit']/$this->_prizeUnit - $this->_diffValue  : 0;

				$value['lhcContinuenotin4Limit']  = $value['lhcContinuenotin4Limit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuenotin4Limit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['lhcContinuenotin5Limit']  = $value['lhcContinuenotin5Limit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuenotin5Limit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['lhcContinuecodeLimit']  = $value['lhcContinuecodeLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuecodeLimit']/$this->_prizeUnit - $this->_diffValue  : 0;
				

				$value['maxLhcYear']   = isset($value['maxLhcYear']) ? $value['maxLhcYear']  : $value['lhcYear'];
				$value['maxLhcColor']   = isset($value['maxLhcColor']) ? $value['maxLhcColor']  : $value['lhcColor'];
				$value['sbThreeoneRetLimit']  = $value['sbThreeoneRetLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['sbThreeoneRetLimit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['maxSbThreeoneRet']  = $value['sbThreeoneRetLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['sbThreeoneRetLimit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['maxLhcFlatcode']   = isset($value['maxLhcFlatcode']) ? $value['maxLhcFlatcode']  : $value['lhcFlatcode'];
				$value['maxLhcHalfwave']   = isset($value['maxLhcHalfwave']) ? $value['maxLhcHalfwave']  : $value['lhcHalfwave'];
				$value['maxLhcOneyear']   = isset($value['maxLhcOneyear']) ? $value['maxLhcOneyear']  : $value['lhcOneyear'];
				$value['maxLhcNotin']   = isset($value['maxLhcNotin']) ? $value['maxLhcNotin']  : $value['lhcNotin'];
				$value['maxLhcContinuein23']   = isset($value['maxLhcContinuein23']) ? $value['maxLhcContinuein23']  : $value['lhcContinuein23'];
				$value['maxLhcContinuein4']   = isset($value['maxLhcContinuein4']) ? $value['maxLhcContinuein4']  : $value['lhcContinuein4'];
				$value['maxLhcContinuein5']   = isset($value['maxLhcContinuein5']) ? $value['maxLhcContinuein5']  : $value['lhcContinuein5'];
				$value['maxLhcContinuenotin23']   = isset($value['maxLhcContinuenotin23']) ? $value['maxLhcContinuenotin23']  : $value['lhcContinuenotin23'];
				$value['maxLhcContinuenotin4']   = isset($value['maxLhcContinuenotin4']) ? $value['maxLhcContinuenotin4']  : $value['lhcContinuenotin4'];
				$value['maxLhcContinuenotin5']   = isset($value['maxLhcContinuenotin5']) ? $value['maxLhcContinuenotin5']  : $value['lhcContinuenotin5'];
				$value['maxLhcContinuecode']   = isset($value['maxLhcContinuecode']) ? $value['maxLhcContinuecode']  : $value['lhcContinuecode'];

				
				$userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']] = $value;
			}
			foreach($userAwardListStruc as $key=>$value){
				//时时彩 用时时彩点差   其它非六合彩用其它彩系点差
				if($value['lotterySeriesCode']=='1'){
					if($iType =='0'){
						$this->_diffValue=$this->_sscgadiffValue;
					}else{
						$this->_diffValue=$this->_sscproxydiffValue;
					}
				}else if($value['lotterySeriesCode']!='9'){
					if($iType =='0'){
						$this->_diffValue=$this->_othergadiffValue;
					}else{
						$this->_diffValue=$this->_otherproxydiffValue;
					}
				} 

				if($value['lotteryId'] == '88101' || $value['lotteryId'] == '88102'){
					$value['directLimitRet'] = $value['directLimitRet']/$this->_prizeUnit > $this->_WGdiffValue ? $value['directLimitRet']/$this->_prizeUnit - $this->_WGdiffValue : 0;
				}else if($value['lotteryId'] == '99701'){
					
					if($iType==0 && $userLvl >-1){//總代給一級不用保留,玩家除外
						$value['directLimitRet'] = $value['directLimitRet']/$this->_prizeUnit;
					}else{
						$value['directLimitRet'] = $value['directLimitRet']/$this->_prizeUnit > $this->_1diffValue ? $value['directLimitRet']/$this->_prizeUnit - $this->_1diffValue : 0;
					}
				}else{
					$value['directLimitRet'] = $value['directLimitRet']/$this->_prizeUnit > $this->_diffValue ? $value['directLimitRet']/$this->_prizeUnit - $this->_diffValue : 0;
				}
				
				$value['threeLimitRet']  = $value['threeLimitRet']/$this->_prizeUnit  > $this->_diffValue ? $value['threeLimitRet']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['superLimitRet']  = $value['superLimitRet']/$this->_prizeUnit  > $this->_2000diffValue ? $value['superLimitRet']/$this->_prizeUnit - $this->_2000diffValue  : 0;
				$value['lhcYearLimit']  = $value['lhcYearLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcYearLimit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['lhcColorLimit']  = $value['lhcColorLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcColorLimit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['sbThreeoneRetLimit']  = $value['sbThreeoneRetLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['sbThreeoneRetLimit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['lhcFlatcodeLimit']  = $value['lhcFlatcodeLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcFlatcodeLimit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['lhcHalfwaveLimit']  = $value['lhcHalfwaveLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcHalfwaveLimit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['lhcOneyearLimit']  = $value['lhcOneyearLimit']/$this->_prizeUnit  > $this->_point2diffValue ? $value['lhcOneyearLimit']/$this->_prizeUnit - $this->_point2diffValue  : 0;
				$value['lhcNotinLimit']  = $value['lhcNotinLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcNotinLimit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['lhcContinuein23Limit']  = $value['lhcContinuein23Limit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuein23Limit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['lhcContinuein4Limit']  = $value['lhcContinuein4Limit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuein4Limit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['lhcContinuein5Limit']  = $value['lhcContinuein5Limit']/$this->_prizeUnit  > $this->_1diffValue ? $value['lhcContinuein5Limit']/$this->_prizeUnit - $this->_1diffValue  : 0;
				$value['lhcContinuenotin23Limit']  = $value['lhcContinuenotin23Limit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuenotin23Limit']/$this->_prizeUnit - $this->_diffValue  : 0;

				$value['lhcContinuenotin4Limit']  = $value['lhcContinuenotin4Limit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuenotin4Limit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['lhcContinuenotin5Limit']  = $value['lhcContinuenotin5Limit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuenotin5Limit']/$this->_prizeUnit - $this->_diffValue  : 0;
				$value['lhcContinuecodeLimit']  = $value['lhcContinuecodeLimit']/$this->_prizeUnit  > $this->_diffValue ? $value['lhcContinuecodeLimit']/$this->_prizeUnit - $this->_diffValue  : 0;
				
				if(isset($userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]) && $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['status']==1){
					
					if($value['lotterySeriesCode'] == '2'){
						$result[5][$value['lotteryId']]['status'] = 1;
                    }elseif($value['lotterySeriesCode'] == '6'){
						$result[7][$value['lotteryId']]['status'] = 1;
					}elseif($value['lotterySeriesCode'] == '9'){
						$result[6][$value['lotteryId']]['status'] = 1;
					} else {
						$result[$value['lotterySeriesCode']][$value['lotteryId']]['status'] = 1;
					}
					$value['status'] = 1;
					$value['down_directLimitRet'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['directRet']/$this->_prizeUnit;
					$value['down_threeLimitRet'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['threeoneRet']/$this->_prizeUnit;
					$value['down_superLimitRet'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['superRet']/$this->_prizeUnit;
					$value['down_maxDirectRet'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['maxDirectRet']/$this->_prizeUnit;
					$value['down_maxThreeOneRet'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['maxThreeOneRet']/$this->_prizeUnit;
					$value['down_maxSuperRet'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['maxSuperRet']/$this->_prizeUnit;

					$value['down_lhcYearLimit'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['lhcYear']/$this->_prizeUnit;
					$value['down_lhcColorLimit'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['lhcColor']/$this->_prizeUnit;
					$value['down_lhcFlatcodeLimit'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['lhcFlatcode']/$this->_prizeUnit;
					$value['down_lhcHalfwaveLimit'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['lhcHalfwave']/$this->_prizeUnit;
					$value['down_lhcOneyearLimit'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['lhcOneyear']/$this->_prizeUnit;
					$value['down_lhcNotinLimit'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['lhcNotin']/$this->_prizeUnit;
					$value['down_lhcContinuein23Limit'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['lhcContinuein23']/$this->_prizeUnit;
					$value['down_lhcContinuein4Limit'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['lhcContinuein4']/$this->_prizeUnit;
					$value['down_lhcContinuein5Limit'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['lhcContinuein5']/$this->_prizeUnit;
					$value['down_lhcContinuenotin23Limit'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['lhcContinuenotin23']/$this->_prizeUnit;
					$value['down_lhcContinuenotin4Limit'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['lhcContinuenotin4']/$this->_prizeUnit;
					$value['down_lhcContinuenotin5Limit'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['lhcContinuenotin5']/$this->_prizeUnit;
					$value['down_lhcContinuecodeLimit'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['lhcContinuecode']/$this->_prizeUnit;

					$value['down_maxLhcYear'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['maxLhcYear']/$this->_prizeUnit;
					$value['down_maxLhcColor'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['maxLhcColor']/$this->_prizeUnit;
					$value['down_sbThreeoneRetLimit'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['sbThreeoneRet']/$this->_prizeUnit;
					$value['down_maxSbThreeoneRet'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['maxSbThreeoneRet']/$this->_prizeUnit;
					$value['down_maxLhcFlatcode'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['maxLhcFlatcode']/$this->_prizeUnit;
					$value['down_maxLhcHalfwave'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['maxLhcHalfwave']/$this->_prizeUnit;
					$value['down_maxLhcOneyear'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['maxLhcOneyear']/$this->_prizeUnit;
					$value['down_maxLhcNotin'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['maxLhcNotin']/$this->_prizeUnit;
					$value['down_maxLhcContinuein23'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['maxLhcContinuein23']/$this->_prizeUnit;
					$value['down_maxLhcContinuein4'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['maxLhcContinuein4']/$this->_prizeUnit;
					$value['down_maxLhcContinuein5'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['maxLhcContinuein5']/$this->_prizeUnit;
					$value['down_maxLhcContinuenotin23'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['maxLhcContinuenotin23']/$this->_prizeUnit;
					$value['down_maxLhcContinuenotin4'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['maxLhcContinuenotin4']/$this->_prizeUnit;
					$value['down_maxLhcContinuenotin5'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['maxLhcContinuenotin5']/$this->_prizeUnit;
					$value['down_maxLhcContinuecode'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['maxLhcContinuecode']/$this->_prizeUnit;
						
					
					$value['betType'] = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']]['betType'];
				} else {
					if($value['lotterySeriesCode'] == '2'){
						$result[5][$value['lotteryId']]['status'] = isset($result[5][$value['lotteryId']]['status']) ? $result[5][$value['lotteryId']]['status'] : 0;
                    }elseif($value['lotterySeriesCode'] == '6'){
						$result[7][$value['lotteryId']]['status'] = isset($result[7][$value['lotteryId']]['status']) ? $result[7][$value['lotteryId']]['status'] : 0;
					}elseif($value['lotterySeriesCode'] == '9'){
						$result[6][$value['lotteryId']]['status'] = isset($result[6][$value['lotteryId']]['status']) ? $result[6][$value['lotteryId']]['status'] : 0;
					} else {
						$result[$value['lotterySeriesCode']][$value['lotteryId']]['status'] = isset($result[$value['lotterySeriesCode']][$value['lotteryId']]['status']) ? $result[$value['lotterySeriesCode']][$value['lotteryId']]['status'] : 0;
					}
					$value['status'] = 0;
					$value['down_directLimitRet'] = '';
					$value['down_threeLimitRet'] = '';
					$value['down_superLimitRet'] = '';
					$value['down_maxLhcYear'] = '';
					$value['down_maxLhcColor'] = '';
					$value['down_maxSbThreeoneRet'] = '';
					$value['down_maxLhcFlatcode'] = '';
					$value['down_maxLhcHalfwave'] = '';
					$value['down_maxLhcOneyear'] = '';
					$value['down_maxLhcNotin'] = '';
					$value['down_maxLhcContinuein23'] = '';
					$value['down_maxLhcContinuein4'] = '';
					$value['down_maxLhcContinuein5'] = '';
					$value['down_maxLhcContinuenotin23'] = '';
					$value['down_maxLhcContinuenotin4'] = '';
					$value['down_maxLhcContinuenotin5'] = '';
					$value['down_maxLhcContinuecode'] = '';

				}
				if($value['lotterySeriesCode'] == '2'){
					$result[5][$value['lotteryId']][$value['awardGroupId']] = $value;
                }elseif($value['lotterySeriesCode'] == '6'){
					$result[7][$value['lotteryId']][$value['awardGroupId']] = $value;
				}elseif($value['lotterySeriesCode'] == '9'){
					$result[6][$value['lotteryId']][$value['awardGroupId']] = $value;
				} else {
					$result[$value['lotterySeriesCode']][$value['lotteryId']][$value['awardGroupId']] = $value;
				}
				
				if ($maxValue ===NULL) {
					$maxValue = $value['directLimitRet'];
				}
				$maxValue = $this->_gamePrize->getMinValue($maxValue, $value['directLimitRet'], $value['threeLimitRet'],$value['superLimitRet']);
			}
			$this->_aAwardRedis->set($userTypeKey,$type);
			$this->_aAwardRedis->set($userIdKey,$userId);
		} else {
			$emptyRewards = 1;
		}
		ksort($result);
		//奖金组排序
		foreach ($result as $key=>$value){
			foreach($value as $sub_key=>$sub_value){
				$value[$sub_key] = $this->_gamePrize->awardArray_sort($sub_value);
			}
			$result[$key] = $value;
		}
		$this->view->emptyRewards = $emptyRewards;
		$this->view->result = $result;
		$this->view->userId = $userId;
		$this->view->maxValue = $maxValue > $this->_diffValue ? $maxValue : 0;
		$this->view->aAwardSeris = $this->_aAwardSeris;
		$this->view->aAwardGroup = $this->_aAwardGroup;
		$this->view->aRateName = $this->_aRateName;
		$this->view->aRateIndex = $this->_aRateIndex;
		$this->view->title = '修改返点';
		if ($type == 0) {
			$this->view->display ( 'default/ucenter/prize/modifyprize_user.tpl' );
		} else {
			$this->view->display ( 'default/ucenter/prize/modifyprize_proxy.tpl' );
		}
	}

	//返点修改
	public function modifyprizeAction() {
		$id = intval(getSecurityInput($this->_request->getPost('id')));
		$request = $this->getRequest();
		$this->_diffValue= $this->_diffValue*$this->_prizeUnit;
		$this->_2000diffValue= $this->_2000diffValue*$this->_prizeUnit;
		$this->_WGdiffValue= $this->_WGdiffValue*$this->_prizeUnit;
		        $this->_1diffValue = $this->_1diffValue *$this->_prizeUnit;
		$this->_point2diffValue = $this->_point2diffValue *$this->_prizeUnit;
		$userInfo = $this->getUserInfoById($id);
		$userLvl = $userInfo['userLvl'];
		
		//获取hash 用到的key
		$userIdKey 	 = 'Applycenter_modifyprize_userId_'.$id;
		$userTypeKey = 'Applycenter_modifyprize_userType_'.$id;
		$userType 	 = $this->_aAwardRedis->get($userTypeKey);
		$userId 	 = $this->_aAwardRedis->get($userIdKey);
		if($id != $userId){
			echo Zend_Json::encode(array('status'=>'error1','data'=>$this->getError('903')));
			exit;
		}
		
		//如果不是当前的直接下级,则返回不让用户进入修改页面
		$userInfo = $this->getUserInfoById($userId);
		if(!isset($userInfo['parentId']) || $userInfo['parentId'] != $this->_sessionlogin->info['id']){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102140')));
			exit;
		}
		
		$prizeList = array();
		$iType = $this->_sessionlogin->info['userLvl'] == '0' ? 0 : 1;
		//当前用户奖金组
		$userAwardListStruc = $this->_gamePrize->getGamePrizeList($this->_sessionlogin->info['id'],$iType);

		if(count($userAwardListStruc)>0){
			//下级用户的奖金组
			$userDownAwardArray = $this->_gamePrize->getGamePrizeList($userId);
			$userAwardArray = array();
			foreach($userDownAwardArray as $key=>$value){
				$prize_key = $value['lotterySeriesCode'].'_'.$value['lotteryId'].'_'.$value['awardGroupId'];
				$userAwardArray[$prize_key] = $value;
				if($value['status'] ==1){
					$userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']]['status'] = 1;
				}
			}
			unset($userDownAwardArray);
			foreach($userAwardListStruc as $key=>$value){
				
				//获取参数
				$prize_key = $value['lotterySeriesCode'].'_'.$value['lotteryId'].'_'.$value['awardGroupId'];
				$directRet   = floatval(getSecurityInput($this->_request->getPost('directRet_'.$prize_key)))* $this->_prizeUnit ;
				$threeoneRet = floatval(getSecurityInput($this->_request->getPost('threeoneRet_'.$prize_key)))* $this->_prizeUnit ;
				$superRet = floatval(getSecurityInput($this->_request->getPost('superRet_'.$prize_key)))* $this->_prizeUnit ;
				$lhcYear = floatval(getSecurityInput($this->_request->getPost('lhcYear_'.$prize_key)))* $this->_prizeUnit ;
				$lhcColor = floatval(getSecurityInput($this->_request->getPost('lhcColor_'.$prize_key)))* $this->_prizeUnit ;
				$sbThreeoneRet = floatval(getSecurityInput($this->_request->getPost('sbThreeoneRet_'.$prize_key)))* $this->_prizeUnit ;
				$lhcHalfwave = floatval(getSecurityInput($this->_request->getPost('lhcHalfwave_'.$prize_key)))* $this->_prizeUnit ;
				$lhcFlatcode = floatval(getSecurityInput($this->_request->getPost('lhcFlatcode_'.$prize_key)))* $this->_prizeUnit ;
				$lhcOneyear = floatval(getSecurityInput($this->_request->getPost('lhcOneyear_'.$prize_key)))* $this->_prizeUnit ;
				$lhcNotin = floatval(getSecurityInput($this->_request->getPost('lhcNotin_'.$prize_key)))* $this->_prizeUnit ;
				$lhcContinuein23 = floatval(getSecurityInput($this->_request->getPost('lhcContinuein23_'.$prize_key)))* $this->_prizeUnit ;
				$lhcContinuein4 = floatval(getSecurityInput($this->_request->getPost('lhcContinuein4_'.$prize_key)))* $this->_prizeUnit ;
				$lhcContinuein5 = floatval(getSecurityInput($this->_request->getPost('lhcContinuein5_'.$prize_key)))* $this->_prizeUnit ;
				$lhcContinuenotin23 = floatval(getSecurityInput($this->_request->getPost('lhcContinuenotin23_'.$prize_key)))* $this->_prizeUnit ;
				$lhcContinuenotin4 = floatval(getSecurityInput($this->_request->getPost('lhcContinuenotin4_'.$prize_key)))* $this->_prizeUnit ;
				$lhcContinuenotin5 = floatval(getSecurityInput($this->_request->getPost('lhcContinuenotin5_'.$prize_key)))* $this->_prizeUnit ;
				$lhcContinuecode = floatval(getSecurityInput($this->_request->getPost('lhcContinuecode_'.$prize_key)))* $this->_prizeUnit ;				
				
				
				$status = intval(getSecurityInput($this->_request->getPost('status_'.$prize_key,'0')));
				//双色球 一个返点,11选五只有一个返点值
				if(in_array($value['lotterySeriesCode'], array(3,5,6,8))) {
					$threeoneRet = $directRet;
				}
				//不设置奖金组该返点
				if($status == 0){
					continue;
				}
				
				//下级每个彩种是否已设置奖金组
				if(isset($userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']]['status'])){
					$lottyStatus = $userAwardArray[$value['lotterySeriesCode']][$value['lotteryId']]['status'];
				} else {
					$lottyStatus = 0;
				}
				//下级的返点和状态
				$betType 	     = isset($userAwardArray[$prize_key]['betType']) ? $userAwardArray[$prize_key]['betType'] : null;
				$userStatus 	 = isset($userAwardArray[$prize_key]['status']) ? $userAwardArray[$prize_key]['status'] : 0;
				$userDirectRet   = isset($userAwardArray[$prize_key]['directRet']) ? $userAwardArray[$prize_key]['directRet'] : 0;
				$userSuperRet = isset($userAwardArray[$prize_key]['superRet']) ? $userAwardArray[$prize_key]['superRet'] : 0;
				$userThreeOneRet = isset($userAwardArray[$prize_key]['threeoneRet']) ? $userAwardArray[$prize_key]['threeoneRet'] : 0;
				$usermaxDirectRet= isset($userAwardArray[$prize_key]['maxDirectRet']) ? $userAwardArray[$prize_key]['maxDirectRet'] : 0;
				$usermaxThreeOneRet = isset($userAwardArray[$prize_key]['maxThreeOneRet']) ? $userAwardArray[$prize_key]['maxThreeOneRet'] : 0;
				$usermaxSuperRet = isset($userAwardArray[$prize_key]['maxSuperRet']) ? $userAwardArray[$prize_key]['maxSuperRet'] : 0;
				$userLhcYear = isset($userAwardArray[$prize_key]['lhcYear']) ? $userAwardArray[$prize_key]['lhcYear'] : 0;
				$userLhcColor = isset($userAwardArray[$prize_key]['lhcColor']) ? $userAwardArray[$prize_key]['lhcColor'] : 0;
				$userLhcFlatcode = isset($userAwardArray[$prize_key]['lhcFlatcode']) ? $userAwardArray[$prize_key]['lhcFlatcode'] : 0;
				$userSbThreeoneRet = isset($userAwardArray[$prize_key]['sbThreeoneRet']) ? $userAwardArray[$prize_key]['sbThreeoneRet'] : 0;
				$usermaxSbThreeoneRet= isset($userAwardArray[$prize_key]['maxSbThreeoneRet']) ? $userAwardArray[$prize_key]['maxSbThreeoneRet'] : 0;
				$userLhcHalfwave = isset($userAwardArray[$prize_key]['lhcHalfwave']) ? $userAwardArray[$prize_key]['lhcHalfwave'] : 0;
				$userLhcOneyear = isset($userAwardArray[$prize_key]['lhcOneyear']) ? $userAwardArray[$prize_key]['lhcOneyear'] : 0;
				$userLhcNotin = isset($userAwardArray[$prize_key]['lhcNotin']) ? $userAwardArray[$prize_key]['lhcNotin'] : 0;
				$userLhcContinuein23 = isset($userAwardArray[$prize_key]['lhcContinuein23']) ? $userAwardArray[$prize_key]['lhcContinuein23'] : 0;
				$userLhcContinuein4 = isset($userAwardArray[$prize_key]['lhcContinuein4']) ? $userAwardArray[$prize_key]['lhcContinuein4'] : 0;
				$userLhcContinuein5 = isset($userAwardArray[$prize_key]['lhcContinuein5']) ? $userAwardArray[$prize_key]['lhcContinuein5'] : 0;
				$userLhcContinuenotin23 = isset($userAwardArray[$prize_key]['lhcContinuenotin23']) ? $userAwardArray[$prize_key]['lhcContinuenotin23'] : 0;
				$userLhcContinuenotin4= isset($userAwardArray[$prize_key]['lhcContinuenotin4']) ? $userAwardArray[$prize_key]['lhcContinuenotin4'] : 0;
				$userLhcContinuenotin5 = isset($userAwardArray[$prize_key]['lhcContinuenotin5']) ? $userAwardArray[$prize_key]['lhcContinuenotin5'] : 0;
				$userLhcContinuecode = isset($userAwardArray[$prize_key]['lhcContinuecode']) ? $userAwardArray[$prize_key]['lhcContinuecode'] : 0;

				$usermaxLhcYear= isset($userAwardArray[$prize_key]['maxLhcYear']) ? $userAwardArray[$prize_key]['maxLhcYear'] : 0;
				$usermaxLhcColor= isset($userAwardArray[$prize_key]['maxLhcColor']) ? $userAwardArray[$prize_key]['maxLhcColor'] : 0;
				$usermaxLhcFlatcode= isset($userAwardArray[$prize_key]['maxLhcFlatcode']) ? $userAwardArray[$prize_key]['maxLhcFlatcode'] : 0;
				$usermaxLhcHalfwave= isset($userAwardArray[$prize_key]['maxLhcHalfwave']) ? $userAwardArray[$prize_key]['maxLhcHalfwave'] : 0;
				$usermaxLhcOneyear= isset($userAwardArray[$prize_key]['maxLhcOneyear']) ? $userAwardArray[$prize_key]['maxLhcOneyear'] : 0;
				$usermaxLhcNotin= isset($userAwardArray[$prize_key]['maxLhcNotin']) ? $userAwardArray[$prize_key]['maxLhcNotin'] : 0;
				$usermaxLhcContinuein23= isset($userAwardArray[$prize_key]['maxLhcContinuein23']) ? $userAwardArray[$prize_key]['maxLhcContinuein23'] : 0;
				$usermaxLhcContinuein4= isset($userAwardArray[$prize_key]['maxLhcContinuein4']) ? $userAwardArray[$prize_key]['maxLhcContinuein4'] : 0;
				$usermaxLhcContinuein5= isset($userAwardArray[$prize_key]['maxLhcContinuein5']) ? $userAwardArray[$prize_key]['maxLhcContinuein5'] : 0;
				$usermaxLhcContinuenotin23= isset($userAwardArray[$prize_key]['maxLhcContinuenotin23']) ? $userAwardArray[$prize_key]['maxLhcContinuenotin23'] : 0;
				$usermaxLhcContinuenotin4= isset($userAwardArray[$prize_key]['maxLhcContinuenotin4']) ? $userAwardArray[$prize_key]['maxLhcContinuenotin4'] : 0;
				$usermaxLhcContinuenotin5= isset($userAwardArray[$prize_key]['maxLhcContinuenotin5']) ? $userAwardArray[$prize_key]['maxLhcContinuenotin5'] : 0;
				$usermaxLhcContinuecode= isset($userAwardArray[$prize_key]['maxLhcContinuecode']) ? $userAwardArray[$prize_key]['maxLhcContinuecode'] : 0;
				
				//时时彩 用时时彩点差   其它非六合彩用其它彩系点差
				if($value['lotterySeriesCode']=='1'){
					if($iType =='0'){
						$this->_diffValue=$this->_sscgadiffValue*$this->_prizeUnit;
					}else{
						$this->_diffValue=$this->_sscproxydiffValue*$this->_prizeUnit;
					}
				}else if($value['lotterySeriesCode']!='6'){
					if($iType =='0'){
						$this->_diffValue=$this->_othergadiffValue*$this->_prizeUnit;
					}else{
						$this->_diffValue=$this->_otherproxydiffValue*$this->_prizeUnit;
					}
				}
				//代理的返点上限
				if($value['lotteryId'] == '88101' || $value['lotteryId'] == '88102'){
					$agentDirectRet  = $value['directRet'] > $this->_WGdiffValue ? $value['directRet'] - $this->_WGdiffValue : 0;
				}else if($value['lotteryId'] == '99701'){//六合彩
					if($iType==0 && $userLvl > -1){//總代設定一級代理時候可以不用保留返點&&玩家要保留
						$agentDirectRet  = $value['directRet'];
					}else{
						$agentDirectRet  = $value['directRet'] > $this->_1diffValue ? $value['directRet'] - $this->_1diffValue : 0;
					}
							
				}else{
					$agentDirectRet  = $value['directRet'] > $this->_diffValue ? $value['directRet'] - $this->_diffValue : 0;
				}
				$agentThreeOneRet= $value['threeoneRet'] > $this->_diffValue ? $value['threeoneRet'] - $this->_diffValue  : 0;
				$agentSuperRet= $value['superRet'] > $this->_2000diffValue ? $value['superRet'] - $this->_2000diffValue  : 0;
				$agentLhcYear= $value['lhcYear'] > $this->_diffValue ? $value['lhcYear'] - $this->_diffValue  : 0;
				$agentLhcColor= $value['lhcColor'] > $this->_diffValue ? $value['lhcColor'] - $this->_diffValue  : 0;
				$agentSbThreeoneRet= $value['sbThreeoneRet'] > $this->_diffValue ? $value['sbThreeoneRet'] - $this->_diffValue  : 0;
				$agentLhcFlatcode= $value['lhcFlatcode'] > $this->_diffValue ? $value['lhcFlatcode'] - $this->_diffValue  : 0;
				$agentLhcHalfwave= $value['lhcHalfwave'] > $this->_diffValue ? $value['lhcHalfwave'] - $this->_diffValue  : 0;
				$agentLhcOneyear= $value['lhcOneyear'] > $this->_point2diffValue ? $value['lhcOneyear'] - $this->_point2diffValue  : 0;
				$agentLhcNotin= $value['lhcNotin'] > $this->_diffValue ? $value['lhcNotin'] - $this->_diffValue  : 0;
				$agentLhcContinuein23= $value['lhcContinuein23'] > $this->_diffValue ? $value['lhcContinuein23'] - $this->_diffValue  : 0;
				$agentLhcContinuein4= $value['lhcContinuein4'] > $this->_diffValue ? $value['lhcContinuein4'] - $this->_diffValue  : 0;
				$agentLhcContinuein5= $value['lhcContinuein5'] > $this->_1diffValue ? $value['lhcContinuein5'] - $this->_1diffValue  : 0;
				$agentLhcContinuenotin23= $value['lhcContinuenotin23'] > $this->_diffValue ? $value['lhcContinuenotin23'] - $this->_diffValue  : 0;
				$agentLhcContinuenotin4= $value['lhcContinuenotin4'] > $this->_diffValue ? $value['lhcContinuenotin4'] - $this->_diffValue  : 0;
				$agentLhcContinuenotin5= $value['lhcContinuenotin5'] > $this->_diffValue ? $value['lhcContinuenotin5'] - $this->_diffValue  : 0;
				$agentLhcContinuecode= $value['lhcContinuecode'] > $this->_diffValue ? $value['lhcContinuecode'] - $this->_diffValue  : 0;
				
				
				//原有奖金组不能删除掉
				if($userStatus == 1 && $status==0){
					echo Zend_Json::encode(array('status'=>'error1','data'=>$this->getError('102131')));
					exit;
				}
				//普通用户不能增加奖金组
				if($lottyStatus ==1 && $userStatus == 0 && $status ==1 && $userType == 0){
					echo Zend_Json::encode(array('status'=>'error1','data'=>$this->getError('102132')));
					exit;
				}
				//返点为负数
				if($directRet<0){
					$directRet =0;
				}
				if($threeoneRet<0){
					$threeoneRet =0;
				}
				if($superRet<0){
					$superRet =0;
				}
				if($lhcYear<0){
					$lhcYear =0;
				}
				if($lhcColor<0){
					$lhcColor =0;
				}
				if($sbThreeoneRet<0){
					$sbThreeoneRet =0;
				}
				if($lhcFlatcode<0){
					$lhcFlatcode = 0;
				}
				if($lhcHalfwave<0){
					$lhcHalfwave = 0;
				}
				if($lhcOneyear<0){
					$lhcOneyear = 0;
				}
				if($lhcNotin<0){
					$lhcNotin = 0;
				}
				if($lhcContinuein23<0){
					$lhcContinuein23 = 0;
				}
				if($lhcContinuein4<0){
					$lhcContinuein4 = 0;
				}
				if($lhcContinuein5<0){
					$lhcContinuein5 = 0;
				}
				if($lhcContinuenotin23<0){
					$lhcContinuenotin23 = 0;
				}
				if($lhcContinuenotin4<0){
					$lhcContinuenotin4 = 0;
				}
				if($lhcContinuenotin5<0){
					$lhcContinuenotin5 = 0;
				}
				if($lhcContinuecode<0){
					$lhcContinuecode = 0;
				}		

				//小于用户现有返点
				if($directRet<$userDirectRet){
					$directRet = $userDirectRet;
				}
				if($threeoneRet<$userThreeOneRet){
					$threeoneRet = $userThreeOneRet;
				}
				if($superRet<$userSuperRet){
					$superRet = $userSuperRet;
				}
				if($lhcYear<$userLhcYear){
					$lhcYear = $userLhcYear;
				}
				if($lhcColor<$userLhcColor){
					$lhcColor = $userLhcColor;
				}
				if($sbThreeoneRet<$userSbThreeoneRet){
					$sbThreeoneRet = $userSbThreeoneRet;
				}
				if($lhcFlatcode<$userLhcFlatcode){
					$lhcFlatcode = $userLhcFlatcode;
				}
				if($lhcHalfwave<$userLhcHalfwave){
					$lhcHalfwave = $userLhcHalfwave;
				}
				if($lhcOneyear<$userLhcOneyear){
					$lhcOneyear = $userLhcOneyear;
				}
				if($lhcNotin<$userLhcNotin){
					$lhcNotin = $userLhcNotin;
				}
				if($lhcContinuein23<$userLhcContinuein23){
					$lhcContinuein23 = $userLhcContinuein23;
				}
				if($lhcContinuein4<$userLhcContinuein4){
					$lhcContinuein4 = $userLhcContinuein4;
				}
				if($lhcContinuein5<$userLhcContinuein5){
					$lhcContinuein5 = $userLhcContinuein5;
				}
				if($lhcContinuenotin23<$userLhcContinuenotin23){
					$lhcContinuenotin23 = $userLhcContinuenotin23;
				}
				if($lhcContinuenotin4<$userLhcContinuenotin4){
					$lhcContinuenotin4 = $userLhcContinuenotin4;
				}
				if($lhcContinuenotin5<$userLhcContinuenotin5){
					$lhcContinuenotin5 = $userLhcContinuenotin5;
				}
				if($lhcContinuecode<$userLhcContinuecode){
					$lhcContinuecode = $userLhcContinuecode;
				}
				//大于上级返点范围
				if(floatval($directRet)>floatval($agentDirectRet)){
					$directRet = $agentDirectRet;
				}
				if(floatval($threeoneRet)>floatval($agentThreeOneRet)){
					$threeoneRet= $agentThreeOneRet;
				}
				
				if(floatval($superRet)>floatval($agentSuperRet)){
					$superRet= $agentSuperRet;
				}
                if(floatval($lhcYear)>floatval($agentLhcYear)){
					$lhcYear= $agentLhcYear;
				}
				if(floatval($lhcColor)>floatval($agentLhcColor)){
					$lhcColor= $agentLhcColor;
				}
				if(floatval($sbThreeoneRet)>floatval($agentSbThreeoneRet)){
					$sbThreeoneRet= $agentSbThreeoneRet;
				}
				if(floatval($lhcFlatcode)>floatval($agentLhcFlatcode)){
					$lhcFlatcode= $agentLhcFlatcode;
				}
				if(floatval($lhcHalfwave)>floatval($agentLhcHalfwave)){
					$lhcHalfwave= $agentLhcHalfwave;
				}
				if(floatval($lhcOneyear)>floatval($agentLhcOneyear)){
					$lhcOneyear= $agentLhcOneyear;
				}
				if(floatval($lhcNotin)>floatval($agentLhcNotin)){
					$lhcNotin= $agentLhcNotin;
				}
				if(floatval($lhcContinuein23)>floatval($agentLhcContinuein23)){
					$lhcContinuein23= $agentLhcContinuein23;
				}
				if(floatval($lhcContinuein4)>floatval($agentLhcContinuein4)){
					$lhcContinuein4= $agentLhcContinuein4;
				}
				if(floatval($lhcContinuein5)>floatval($agentLhcContinuein5)){
					$lhcContinuein5= $agentLhcContinuein5;
				}
				if(floatval($lhcContinuenotin23)>floatval($agentLhcContinuenotin23)){
					$lhcContinuenotin23= $agentLhcContinuenotin23;
				}
				if(floatval($lhcContinuenotin4)>floatval($agentLhcContinuenotin4)){
					$lhcContinuenotin4= $agentLhcContinuenotin4;
				}
				if(floatval($lhcContinuenotin5)>floatval($agentLhcContinuenotin5)){
					$lhcContinuenotin5= $agentLhcContinuenotin5;
				}
				if(floatval($lhcContinuecode)>floatval($agentLhcContinuecode)){
					$lhcContinuecode= $agentLhcContinuecode;
				}

				
				//各玩法返点不一致时候使用原有返点值
				if($userDirectRet ==$usermaxDirectRet){
					$value['directRet']   = $directRet;
					$value['maxDirectRet']= $directRet;
				} else {
					$value['directRet']   = $userDirectRet;
					$value['maxDirectRet']= $usermaxDirectRet;
				}
				if($userThreeOneRet ==$usermaxThreeOneRet){
					$value['threeoneRet']    = $threeoneRet;
					$value['maxThreeOneRet'] = $threeoneRet;
				} else {
					$value['threeoneRet']   = $userThreeOneRet;
					$value['maxThreeOneRet']= $usermaxThreeOneRet;
				}
				if($userSuperRet ==$usermaxSuperRet){
					$value['superRet']    = $superRet;
					$value['maxSuperRet'] = $superRet;
				} else {
					$value['superRet']   = $userSuperRet;
					$value['maxSuperRet']= $usermaxSuperRet;
				}
				
				$value['lhcYear']    = $lhcYear;
				$value['lhcColor']    = $lhcColor;
				$value['lhcFlatcode']    = $lhcFlatcode;
				$value['lhcHalfwave']    = $lhcHalfwave;
				$value['lhcOneyear']    = $lhcOneyear;
				$value['lhcNotin']    = $lhcNotin;
				$value['lhcContinuein23']    = $lhcContinuein23;
				$value['lhcContinuein4']    = $lhcContinuein4;
				$value['lhcContinuein5']    = $lhcContinuein5;
				$value['lhcContinuenotin23']    = $lhcContinuenotin23;
				$value['lhcContinuenotin4']    = $lhcContinuenotin4;
				$value['lhcContinuenotin5']    = $lhcContinuenotin5;
				$value['lhcContinuecode']    = $lhcContinuecode;
			

				$value['maxLhcYear'] = $lhcYear;
				$value['maxLhcColor'] = $lhcColor;
				$value['sbThreeoneRet']    = $sbThreeoneRet;
				$value['maxSbThreeoneRet'] = $sbThreeoneRet;
				$value['maxLhcOneyear'] = $lhcOneyear;
				$value['maxLhcNotin'] = $lhcNotin;
				$value['maxLhcContinuein23'] = $lhcContinuein23;
				$value['maxLhcContinuein4'] = $lhcContinuein4;
				$value['maxLhcContinuein5'] = $lhcContinuein5;
				$value['maxLhcContinuenotin23'] = $lhcContinuenotin23;
				$value['maxLhcContinuenotin4'] = $lhcContinuenotin4;
				$value['maxLhcContinuenotin5'] = $lhcContinuenotin5;
				$value['maxLhcContinuecode'] = $lhcContinuecode;


				/*if($userLhcYear ==$usermaxLhcYear){
					$value['lhcYear']    = $lhcYear;
					$value['maxLhcYear'] = $lhcYear;
				} else {
					$value['lhcYear']   = $userLhcYear;
					$value['maxLhcYear']= $usermaxLhcYear;
				}
				if($userLhcColor ==$usermaxLhcColor){
					$value['lhcColor']    = $lhcColor;
					$value['maxLhcColor'] = $lhcColor;
				} else {
					$value['lhcColor']   = $userLhcColor;
					$value['maxLhcColor']= $usermaxLhcColor;
				}*/
				//返点范围最大值为返点值
				$value['directLimitRet'] = $value['directRet'];
				$value['threeLimitRet']  = $value['threeoneRet'];
				$value['superLimitRet']  = $value['superRet'];
				$value['lhcYearLimit']  = $value['lhcYear'];
				$value['lhcColorLimit']  = $value['lhcColor'];
				$value['sbThreeoneRetLimit']  = $value['sbThreeoneRet'];
				$value['lhcFlatcodeLimit']  = $value['lhcFlatcode'];
				$value['lhcHalfwaveLimit']  = $value['lhcHalfwave'];
				$value['lhcOneyearLimit']  = $value['lhcOneyear'];
				$value['lhcNotinLimit']  = $value['lhcNotin'];
				$value['lhcContinuein23Limit']  = $value['lhcContinuein23'];
				$value['lhcContinuein4Limit']  = $value['lhcContinuein4'];
				$value['lhcContinuein5Limit']  = $value['lhcContinuein5'];
				$value['lhcContinuenotin23Limit']  = $value['lhcContinuenotin23'];
				$value['lhcContinuenotin4Limit']  = $value['lhcContinuenotin4'];
				$value['lhcContinuenotin5Limit']  = $value['lhcContinuenotin5'];
				$value['lhcContinuecodeLimit']  = $value['lhcContinuecode'];



				$value['status']  = 1;
				$value['betType'] = $betType;
				$prizeList[] = $value;
			}
		}
		
	    $str = "Hello World";
        $file = fopen("test.text","w+"); //開啟檔案
        fwrite($file,$str);
        fclose($file);
		
		if(count($prizeList)<=0){
						echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102085')));
			exit;
		}
		$aUrl['proxy'] = 'changeBonus';
		//$data['param']['type']  = $type;
		$data['param']['userid']  = intval($userId);
		$data['param']['userAwardListStruc'] = $prizeList;
		 
		$res = $this->_clientobject->inRequestV2($data, $aUrl);
					if(isset($res['head']['status']) && $res['head']['status']=='0'){
			echo Zend_Json::encode(array('status'=>'ok'));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102127')));
			exit;
		}
		
	}
	
	//奖金详情
	public function querybonusdetailsAction(){

		$request = $this->getRequest();
		$type   = $this->_sessionlogin->info['userLvl'] == '0' ? 0 : 1;
		$userId = $this->_sessionlogin->info['id'];
		$userAwardListStruc = $this->_gamePrize->getGamePrizeList($userId,$type);
	    /*print_r($userAwardListStruc);
		exit();*/
		$result = array();
		$emptyRewards = 0;
		$maxValue = NULL;
		if(count($userAwardListStruc)>0){
			
			foreach($userAwardListStruc as $key=>$value){
				
			
				
				$value['directRet']   = isset($value['directRet']) ? $value['directRet']/$this->_prizeUnit : 0;
				$value['threeoneRet'] = isset($value['threeoneRet']) ? $value['threeoneRet']/$this->_prizeUnit : 0;
				$value['superRet'] = isset($value['superRet']) ? $value['superRet']/$this->_prizeUnit : 0;
				$value['maxDirectRet']   = isset($value['maxDirectRet']) ? $value['maxDirectRet']/$this->_prizeUnit : 0;
				$value['maxThreeOneRet'] = isset($value['maxThreeOneRet']) ? $value['maxThreeOneRet']/$this->_prizeUnit : 0;
				$value['superLimitRet'] = isset($value['superLimitRet']) ? $value['superLimitRet']/$this->_prizeUnit : 0;
				$value['maxSuperRet'] = isset($value['maxSuperRet']) ? $value['maxSuperRet']/$this->_prizeUnit : 0;

				$value['lhcYear']   = isset($value['lhcYear']) ? $value['lhcYear']/$this->_prizeUnit : 0;
				$value['lhcColor']   = isset($value['lhcColor']) ? $value['lhcColor']/$this->_prizeUnit : 0;
				$value['sbThreeoneRet']   = isset($value['sbThreeoneRet']) ? $value['sbThreeoneRet']/$this->_prizeUnit : 0;

				$value['lhcFlatcode']   = isset($value['lhcFlatcode']) ? $value['lhcFlatcode']/$this->_prizeUnit : 0;
				$value['lhcHalfwave']   = isset($value['lhcHalfwave']) ? $value['lhcHalfwave']/$this->_prizeUnit : 0;
				$value['lhcOneyear']   = isset($value['lhcOneyear']) ? $value['lhcOneyear']/$this->_prizeUnit : 0;
				$value['lhcNotin']   = isset($value['lhcNotin']) ? $value['lhcNotin']/$this->_prizeUnit : 0;
				$value['lhcContinuein23']   = isset($value['lhcContinuein23']) ? $value['lhcContinuein23']/$this->_prizeUnit : 0;
				$value['lhcContinuein4']   = isset($value['lhcContinuein4']) ? $value['lhcContinuein4']/$this->_prizeUnit : 0;
				$value['lhcContinuein5']   = isset($value['lhcContinuein5']) ? $value['lhcContinuein5']/$this->_prizeUnit : 0;
				$value['lhcContinuenotin23']   = isset($value['lhcContinuenotin23']) ? $value['lhcContinuenotin23']/$this->_prizeUnit : 0;
				$value['lhcContinuenotin4']   = isset($value['lhcContinuenotin4']) ? $value['lhcContinuenotin4']/$this->_prizeUnit : 0;
				$value['lhcContinuenotin5']   = isset($value['lhcContinuenotin5']) ? $value['lhcContinuenotin5']/$this->_prizeUnit : 0;
				$value['lhcContinuecode']   = isset($value['lhcContinuecode']) ? $value['lhcContinuecode']/$this->_prizeUnit : 0;


				if($value['lotterySeriesCode'] == '2'){
					$result[5][$value['lotteryId']][] = $value;
                } else if ($value['lotterySeriesCode'] == '6')  {
					$result[7][$value['lotteryId']][] = $value;
					//$result[$value['lotterySeriesCode']][$value['lotteryId']][] = $value;
				} else if ( $value['lotterySeriesCode'] == '9' ) {
						$result[6][$value['lotteryId']][] = $value;
				
				}else {
					$result[$value['lotterySeriesCode']][$value['lotteryId']][] = $value;
				}
			}
			ksort($result);
		} else {
			$emptyRewards = 1;
		}
		
		
		//奖金组排序
		foreach ($result as $key=>$value){
			foreach($value as $sub_key=>$sub_value){
				$value[$sub_key] = $this->_gamePrize->awardArray_sort($sub_value);
			}
			$result[$key] = $value;
		}
		
		if($this->_sessionlogin->info['userLvl'] == '-1'){
			foreach ($this->_aRateName as $key=>$value){
				foreach($value as $sub_key=>$sub_value){
					$this->_aRateName[$key][$sub_key] = str_replace('返点', '', $sub_value);
				}
			}
		}
		/*
		print_r($result);
		exit();
		*/
		$this->view->emptyRewards = $emptyRewards;
		$this->view->result = $result;
		$this->view->type   = $type;
		$this->view->userId   = $userId;
		$this->view->aAwardSeris = $this->_aBounsDetailAwardSeris;
		$this->view->aAwardGroup = $this->_aAwardGroup;
		$this->view->aRateName = $this->_aRateName;
		$this->view->aRateIndex = $this->_aRateIndex;
		$this->view->title = '奖金详情';
		$this->view->display('default/ucenter/prize/queryBonusdDetail.tpl');
	}
	
	//获取玩法返点
	public function queryusergameawardAction(){
		$lotterySeriesCode = floatval(getSecurityInput($this->_request->getPost('lotterySeriesCode')));
		$lotteryId = floatval(getSecurityInput($this->_request->getPost('lotteryId')));
		$awardGroupId = floatval(getSecurityInput($this->_request->getPost('awardGroupId')));
		$retstatus = floatval(getSecurityInput($this->_request->getPost('retstatus')));
		$userId = floatval(getSecurityInput($this->_request->getPost('userId')));
// 		$type = floatval(getSecurityInput($this->_request->getPost('type')));
		$awardType = floatval(getSecurityInput($this->_request->getPost('awardType')));
		$modifyprize = floatval(getSecurityInput($this->_request->getPost('modifyprize')));
		$sysAwardGroupId = floatval(getSecurityInput($this->_request->getPost('sysAwardGroupId')));
		
		//如果不是当前的直接下级,则返回不让用户查看玩法返点
		$userInfo = $this->getUserInfoById($userId);
		if($modifyprize =='1' && (!isset($userInfo['parentId']) || $userInfo['parentId'] !== $this->_sessionlogin->info['id'])){
			echo Zend_Json::encode(array('status'=>'error','html'=>$this->getError('102146')));
			exit;
		}
		$type = $userInfo['userLvl'] == 0 ? 0 : 1;
// 		$awardType = isset($this->_aRateIndex[$lotterySeriesCode][$retstatus-1]) ? $this->_aRateIndex[$lotterySeriesCode][$retstatus-1] : 1;
		$param =array(
			'lotteryId' 	=> $lotteryId,
			'type' 			=> $type,
			'awardType' 	=> $awardType,
			'userId' 		=> $userId,
			'sysAwardGroupId' => $sysAwardGroupId
		);
		$userAwardGroup = array();
		$result = $this->_gamePrize->queryUserGameAward($param);
		foreach ($result as $key=>$value){
				$userAwardGroup[$value['gameGroupCode']][$value['gameSetCode']][] = $value;
				$userAwardGroup[$value['gameGroupCode']]['cnt'] = isset($userAwardGroup[$value['gameGroupCode']]['cnt']) ? $userAwardGroup[$value['gameGroupCode']]['cnt']+1 : 1;
				$userAwardGroup[$value['gameGroupCode']][$value['gameSetCode']]['cnt'] = isset($userAwardGroup[$value['gameGroupCode']][$value['gameSetCode']]['cnt']) ? $userAwardGroup[$value['gameGroupCode']][$value['gameSetCode']]['cnt']+1 : 1;
		}
		$html = '';
		if($modifyprize =='1' && $userId !=$this->_sessionlogin->info['id']){
			$html .= "<input type='hidden' id='data-lotteryId' value='".$lotteryId."'>";
			$html .= "<input type='hidden' id='data-awardGroupId' value='".$sysAwardGroupId."'>";
			$html .= "<input type='hidden' id='data-lotterySeriesCode' value='".$lotterySeriesCode."'>";
			$html .= "<input type='hidden' id='data-retstatus' value='".$retstatus."'>";
			$html .= "<form name='modifyBonusForm' method='post'>";
		}
		$html .= "<table class='table table-border'><thead><tr><th>玩法群</th><th>玩法组</th><th>玩法/投注方式</th><th>奖金（元）</th>";
				//代理查看下级或者非普通用户可以查看到返点
				if($userInfo['userLvl'] !='-1' || $userInfo['id']!=$this->_sessionlogin->info['id']){
					$html .= "<th>返点</th></tr>";
				}
				$html .= "</thead><tbody>";
		foreach ($userAwardGroup as $key=>$value){
			$i=0;
			foreach ($value as $sub_key=>$sub_value){
				if($sub_key !='cnt'){
					foreach ($sub_value as $sub_key1=>$sub_value1){
						if(is_numeric($sub_key1)){
							$html .= "<tr>";
							if($sub_key1 == '0'){
								if($i == '0'){
									$html .= "<td rowspan='".$userAwardGroup[$key]['cnt']."'>".$sub_value1['groupCodeTitle']."</td>";
								}
								$html .= "<td rowspan='".$userAwardGroup[$key][$sub_key]['cnt']."'>".$sub_value1['setCodeTitle']."</td>";
							} else if ($userAwardGroup[$key]['cnt'] <=1) {
								$html .= "<td>".$sub_value1['groupCodeTitle']."</td>";
							} else if ($userAwardGroup[$key][$sub_key]['cnt']<=1) {
								$html .= "<td>".$sub_value1['setCodeTitle']."</td>";
							}
							$html .= "<td>".$sub_value1['methodCodeTitle']."</td>";//methodCodeTitle
							if(is_array($sub_value1['assistBMBonusList']) && count($sub_value1['assistBMBonusList'])>0){
								$html .= "<td>";
								foreach ($sub_value1['assistBMBonusList'] as $assitValue){
									$html .= "<div>".$assitValue['methodTypeTitle']."&nbsp;&nbsp;".sprintf("%.2f", $assitValue['actualBonus']/$this->_moneyUnit)."</div>";
								}
								$html .= "</td>";
							} else {
								$html .= "<td>".sprintf("%.2f", $sub_value1['actualBonus']/$this->_moneyUnit)."</td>";
							}
							//普通用户自己查看玩法详情时不能显示返点
							if($userInfo['userLvl'] !='-1' || $userInfo['id']!=$this->_sessionlogin->info['id']){
								if($modifyprize =='1' && $userId !=$this->_sessionlogin->info['id']){
									$html .= "<td><input name='".$sub_value1['mainBetTypeCode']."' class='w-1' value='".$sub_value1['retVal']/$this->_prizeUnit."' minvalue='".$sub_value1['retVal']/$this->_prizeUnit."' maxvalue='".($sub_value1['maxRetVal']/$this->_prizeUnit>$this->_diffValue ? $sub_value1['maxRetVal']/$this->_prizeUnit-$this->_diffValue : 0)."' onBlur=\"CheckingMaxAllFull(this,'".($sub_value1['maxRetVal']/$this->_prizeUnit>$this->_diffValue ? $sub_value1['maxRetVal']/$this->_prizeUnit-$this->_diffValue : 0)."','".$sub_value1['retVal']/$this->_prizeUnit."')\" />(范围为".$sub_value1['retVal']/$this->_prizeUnit."-".($sub_value1['maxRetVal']/$this->_prizeUnit>$this->_diffValue ? $sub_value1['maxRetVal']/$this->_prizeUnit-$this->_diffValue : 0).")</td>";
								}else {
									if(is_array($sub_value1['assistBMBonusList']) && count($sub_value1['assistBMBonusList'])>0 && $sub_value1['betMethodCode'] == 81 ){
                               		 	$html .= "<td>";
                                		foreach ($sub_value1['assistBMBonusList'] as $assitValue){
                                   			$html .= "<div>&nbsp;&nbsp;".$assitValue['retVal']/$this->_prizeUnit."%</div>";
                              			}
                               			$html .= "</td>";
                            		}else {
                                		$html .= "<td>".$sub_value1['retVal']/$this->_prizeUnit."%</td>";
                            		}
								}
							}
							$html .= "</tr>";
						}
					}
					$i++;
				}
			}
		}
		$html .= "</tbody></table>";
		if($modifyprize =='1' && $userId !=$this->_sessionlogin->info['id']){
			$html .= "</form>";
		}
		header ( 'Content-Type: application/json;charset=utf-8' );
		echo Zend_Json::encode(array('status'=>'ok','html'=>$html));
		exit;
	}
	
	//修改玩法返点
	public function modifybonusprizeAction(){
		$userId = floatval(getSecurityInput($this->_request->getPost('id')));
		$lotteryId = floatval(getSecurityInput($this->_request->getPost('lotteryId')));
		$lotterySeriesCode = floatval(getSecurityInput($this->_request->getPost('lotterySeriesCode')));
		$retstatus = floatval(getSecurityInput($this->_request->getPost('retstatus','1')));
		$awardGroupId = floatval(getSecurityInput($this->_request->getPost('awardGroupId')));
		if($userId ==0 || $lotteryId==0 || $lotterySeriesCode==0 || $retstatus==0 || $awardGroupId==0){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102148')));
			exit;
		}
		$bonusId = $this->_request->getPost('bonusId');
		$bonusValue = $this->_request->getPost('bonusValue');
		//用户不能修改自己返点
		if($userId == $this->_sessionlogin->info['id']){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102140')));
			exit;
		}
		//如果不是当前的直接下级,则返回不让用户修改玩法返点
		$userInfo = $this->getUserInfoById($userId);
		if(!isset($userInfo['parentId']) || $userInfo['parentId'] != $this->_sessionlogin->info['id']){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102140')));
			exit;
		}
		
		$userAwardListStruc = $this->_gamePrize->getGamePrizeList($userInfo['id']);
		$userAwardArray = array();
		foreach ($userAwardListStruc as $value){
			if($value['lotterySeriesCode']==$lotterySeriesCode && $value['lotteryId']==$lotteryId && $value['sysAwardGrouId'] == $awardGroupId){
				$userAwardArray = $value;
				break;
			}
		}
		$type = $userInfo['userLvl'] == 0 ? 0 : 1;
		$awardType = $this->_aRateIndex[$lotterySeriesCode][$retstatus-1];
		$param =array(
				'lotteryId' 	=> $lotteryId,
				'type' 			=> $type,
				'awardType' 	=> $awardType,
				'userId' 		=> $userInfo['id'],
				'sysAwardGroupId' => $awardGroupId
		);
		$userAwardGroup = array();
		$result = $this->_gamePrize->queryUserGameAward($param);
		foreach ($result as $key=>$value){
			$userAwardGroup[$value['mainBetTypeCode']] = $value;
		}
		
		unset($userAwardArray['betType']);
		unset($userAwardArray['sysAwardGrouId']);
		$bonusArray= array();
		foreach ($bonusId as $key=>$value){
			$userAwardArray['betTypeCode']= getSecurityInput($value);
			$userAwardArray['retValue']  = floatval(getSecurityInput($bonusValue[$key]))*$this->_prizeUnit;
			//上级如果返点范围是0 或者空的话 保持下级用户原有返点
			if(empty($userAwardGroup[$userAwardArray['betTypeCode']]['maxRetVal'])){
				$userAwardArray['retValue'] = $userAwardGroup[$userAwardArray['betTypeCode']]['retVal'];
			}else if($userAwardGroup[$userAwardArray['betTypeCode']]['retVal'] > $userAwardArray['retValue'] || $userAwardGroup[$userAwardArray['betTypeCode']]['maxRetVal']<$userAwardArray['retValue']){
				//检测返点 设置是否 在有效范围内
				echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('1036')));
				exit;
			} else if($userAwardArray['retValue']<0){
				//返点为负数
				echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102151')));
				exit;
			}
			$bonusArray[] = $userAwardArray;
		}
		if(count($bonusArray)<=0){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102147')));
			exit;
		}
		$data['param']['userId'] = floatval($userId);
		$data['param']['userAwardStrucList'] = $bonusArray;
		$aUri['proxy'] = 'modifyGameUserAward';
		
		$res = $this->_clientobject->inRequestV2($data, $aUri);
		if(isset($res['head']['status']) && $res['head']['status']=='1'){
			echo Zend_Json::encode(array('status'=>'ok','data'=>'玩法返点修改成功!'));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102148')));
			exit;
		}
	}
	
}
