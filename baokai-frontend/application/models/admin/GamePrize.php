<?php
class GamePrize extends Client {
	public function __construct(){
		parent::__construct();
	}
	
	//获取三值中最小值
	public function getMinValue($value1,$value2,$value3){
		$minValue = $value1;
		if($minValue > $value2){
			$minValue = $value2;
		}
		if($minValue > $value3){
			$minValue  = $value3;
		}
	
		return $minValue;
	}
	
	//根据奖金组名称排序奖金组
	public function awardArray_sort($array){
		foreach ($array as $key=>$value){
			$awardName[$key] = str_replace('奖金组', '', $value['awardName']);
		}
		array_multisort($awardName,SORT_DESC,$array);
		return $array;
	}
	
	
	
	//获取玩法返点
	public function queryUserGameAward($param){
		$data['param'] = $param;
		$aUrl['prize'] = 'queryUserGameAward';
		$res = $this->inRequestV2($data, $aUrl);
		if(isset($res['head']['status']) && $res['head']['status']=='0'){
			return $res['body']['result']['awardBonusStrucList'];
		}
		return array();
	}
}