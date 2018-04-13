<?php
class Activity extends Client {
	
	public function __construct(){
		parent::__construct();
	}
	
	//迁移用户活动
	public function newusrePrize($param){
		$aUri['activity'] = 'migrateById';
		$data['param'] = $param;
		$result = $this->inRequestV2($data, $aUri);
		$aContent = array();
		if(isset($result['body']['result']) && count($result['body']['result'])>0){
			$aContent['id'] 		= $result['body']['result']['id'];
			$aContent['betTime']    = getSrtTimeByLong($result['body']['result']['betTime']);
			$aContent['fundTime']   = getSrtTimeByLong($result['body']['result']['fundTime']);
			$aContent['updateTime'] = getSrtTimeByLong($result['body']['result']['updateTime']);
			$aContent['testTime']   = getSrtTimeByLong($result['body']['result']['testTime']);
			if($aContent['updateTime']>0 &&$aContent['fundTime']>0 &&$aContent['betTime']>0){
				$aContent['lastTime'] 	= $aContent['betTime'];
				if($aContent['lastTime'] < $aContent['updateTime']){
					$aContent['lastTime'] = $aContent['updateTime'];
				}
				if($aContent['lastTime'] < $aContent['fundTime']){
					$aContent['lastTime'] = $aContent['fundTime'];
				}
			}
		}
		return $aContent;
	}
	
	/*
	//每日抽獎
    public function dailybonus($param)
    {
        $prize_arr = array(
            '0' => array('id'=>1,'prize'=>rand(1,5)*0.1,'v'=>80),
            '1' => array('id'=>2,'prize'=>rand(6,10)*0.1,'v'=>20),
        );

        foreach ($prize_arr as $key => $val) {
            $arr[$val['id']] = $val['v'];
        }

        $rid = $this->getRand($arr); //根据概率获取奖项id
        $res['msg'] = 1; 
        $res['prize'] = $prize_arr[$rid-1]['prize']; //中奖项

        $aUri['activity'] = 'eggclick';
        $param['amount'] = $res['prize'] * 10000;
        $param['reason'] = 'PM-PGXX-3';
        $param['operator'] = '0';
        $param['isAclUser'] = '0';
        $data['param'] = array( $param );
        $result = $this->inRequestV2($data, $aUri);

        $aContent = array('msg' => '0', 'error' => '操作失敗');
        if( isset( $result['body']['result'] ) && count( $result['body']['result'] ) > 0 )
        {

            // 帳號最後抽獎日期
            $dailyBonus = new Rediska_Key(md5('dailyBonus'.$param['userId']));
            $dailyBonus->setAndExpire(date("Y-m-d"), 2*24*60*60);

            // IP & 日期 當天抽獎次數
            $dailyIP = new Rediska_Key(md5('dailyBonus'.$param['ip'].date("Y-m-d")));
            $dailyIP->setAndExpire((int)$param['ip_num']+1, 2*24*60*60);

            $aContent = $res;
		}
        $aContent = $res;
		return $aContent;
    }
    */
	
	//2015-09 大富翁活動
	public function monopoly($param)
	{
		$redis_client = Zend_Registry::get('redis_client');
		//回傳 1 or 10
		$getBigPrize = function ($prize_ary) use ($redis_client) {
			//取得這個小時中大獎的人數
			$today = date("Y-m-d");	//今天日期
			$hour = date("H");	//目前的小時
			if((int)$hour < 14)	//尚未到達 14:00
			{
				return 1;
			}
			$num = $redis_client->hGet(md5('dailyBigPrizeHour'.$today), $hour);
			if((int)$num >= 2)	//中大獎人數已滿2人了
			{
				return 1;
			}
			return rand_ary($prize_ary);
		};
		
		//回傳的結構
		$prize_result = array(
			'isSuccess' => 0,	//是否成功（1:成功 0:失敗）
			'prize' => '',	//獎金
			'points' => array(),	//骰子點數陣列
		);
		//獎項機率陣列
		$prize_arr = array(
			'3' => array('id'=>3,'prize'=>rand(1,10)*0.1,'v'=>5),
			'4' => array('id'=>4,'prize'=>$getBigPrize(array(1,10)),'v'=>5),
			'5' => array('id'=>5,'prize'=>rand(1,10)*0.1,'v'=>5),
			'6' => array('id'=>6,'prize'=>rand(1,5)*0.2,'v'=>5),
			'7' => array('id'=>7,'prize'=>rand(1,10)*0.1,'v'=>5),
			'8' => array('id'=>8,'prize'=>rand(1,10)*0.1,'v'=>5),
			'9' => array('id'=>9,'prize'=>$getBigPrize(array(1,10)),'v'=>5),
			'10' => array('id'=>10,'prize'=>$getBigPrize(array(1,10)),'v'=>5),
			'11' => array('id'=>11,'prize'=>rand(1,10)*0.1,'v'=>5),
			'12' => array('id'=>12,'prize'=>rand(1,10)*0.1,'v'=>5),
			'13' => array('id'=>13,'prize'=>rand(1,5)*0.2,'v'=>5),
			'14' => array('id'=>14,'prize'=>rand(1,10)*0.1,'v'=>5),
			'15' => array('id'=>15,'prize'=>$getBigPrize(array(1,10)),'v'=>5),
			'16' => array('id'=>16,'prize'=>rand(1,10)*0.1,'v'=>5),
			'17' => array('id'=>17,'prize'=>rand(1,5)*0.2,'v'=>10),
			'18' => array('id'=>18,'prize'=>rand(1,10)*0.1,'v'=>10),
		);
		//print_r($prize_arr);
		//根據機率抽獎
		foreach ($prize_arr as $key => $val) {
			$arr[$val['id']] = $val['v'];
		}
		$sum = $this->getRand($arr);	//抽獎
		$prize_result['prize'] = $prize_arr[$sum]['prize'];	//中獎金額
		//分配骰子点数陣列
		$dices_ary = array(
			'3' => array(array(1,1,1)),
			'4' => array(array(1,1,2)),
			'5' => array(array(1,1,3),array(1,2,2)),
			'6' => array(array(1,1,4),array(1,2,3),array(2,2,2)),
			'7' => array(array(1,1,5),array(1,2,4),array(1,3,3),array(2,2,3)),
			'8' => array(array(1,1,6),array(1,2,5),array(1,3,4),array(2,2,4),array(2,3,3)),
			'9' => array(array(1,2,6),array(1,3,5),array(1,4,4),array(2,2,5),array(2,3,4),array(3,3,3)),
			'10' => array(array(1,3,6),array(1,4,5),array(2,2,6),array(2,3,5),array(2,4,4),array(3,3,4)),
			'11' => array(array(1,4,6),array(1,5,5),array(2,3,6),array(2,4,5),array(3,3,5),array(3,4,4)),
			'12' => array(array(1,5,6),array(2,4,6),array(2,5,5),array(3,3,6),array(3,4,5)),
			'13' => array(array(1,6,6),array(2,5,6),array(3,4,6),array(3,5,5),array(4,4,5)),
			'14' => array(array(2,6,6),array(3,5,6),array(4,4,6),array(4,5,5)),
			'15' => array(array(3,6,6),array(4,5,6),array(5,5,5)),
			'16' => array(array(4,6,6),array(5,5,6)),
			'17' => array(array(5,6,6)),
			'18' => array(array(6,6,6)),
		);
		$dices = $dices_ary[$sum];
		$i = array_rand($dices);//随机取数组
		$tmp_ary = $dices[$i];
		shuffle($tmp_ary);	//骰子點數,打亂順序
		$prize_result['points'] = $tmp_ary;
		//print_r($prize_result);
		//呼叫java
		$url['activity'] = 'eggclick';
		$param['amount'] = $prize_result['prize'] * 10000;
		$param['reason'] = 'PM-PGXX-3';
		$param['operator'] = '0';
		$param['isAclUser'] = '0';
		$param['note'] = '大富翁活动奖金';
		$data['param'] = array( $param );
		$java_result = $this->inRequestV2($data, $url);
		//print_r($java_result);
		if(isset($java_result['body']['result']) && count($java_result['body']['result']) > 0)	//java 回傳成功
		{
			$prize_result['isSuccess'] = 1;
			
			//再呼叫寫log的java api
			$log_param['activityId'] = 2;	//活動編號,大富翁固定為2
			$log_param['memo'] = '大富翁活動';
			$log_param['prize'] = $prize_result['prize'];
			$log_param['userId'] = $param['userId'];
			$url = Zend_Registry::get( "config" )->JAVA_SERVER;
			$url .= Zend_Registry::get('firefog')->activity->log;
			//echo $url;
			$java_log_result = send_http_req($url, $log_param);
			//print_r($java_log_result);
		}
		return $prize_result;
	}

	//计算概率
	public function getRand($proArr) {
		$result = '';
		//概率数组的总概率精度
		$proSum = array_sum($proArr);
		//概率数组循环
		foreach ($proArr as $key => $proCur) {
			$randNum = mt_rand(1, $proSum);
			if ($randNum <= $proCur) {
				$result = $key;
				break;
			} else {
				$proSum -= $proCur;
			}
		}
		unset ($proArr);
		return $result;
	}
}
