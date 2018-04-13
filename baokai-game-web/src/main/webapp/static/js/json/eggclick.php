<?php
$prize_arr = array(
	'0' => array('id'=>1,'prize'=>rand(1,5)*0.1,'v'=>80),
	'1' => array('id'=>2,'prize'=>rand(6,10)*0.1,'v'=>20),
);


foreach ($prize_arr as $key => $val) {
	$arr[$val['id']] = $val['v'];
}
//print_r($arr);

$rid = getRand($arr); //根据概率获取奖项id
$res['msg'] = 1; 
$res['prize'] = $prize_arr[$rid-1]['prize']; //中奖项
echo json_encode($res);exit;


//计算概率
function getRand($proArr) {
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
?>