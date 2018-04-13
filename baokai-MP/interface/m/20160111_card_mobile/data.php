<?php
// $prize_arr = array(
// 	'0' => array('id'=>1,'prize'=>rand(1,10)*0.1,'scratch'=>1),
// 	'1' => array('id'=>2,'prize'=>rand(2,20)*0.1,'scratch'=>1),
// 	'2' => array('id'=>3,'prize'=>rand(30,45)*0.1,'scratch'=>0),
// 	'3' => array('id'=>4,'prize'=>rand(45,68)*0.1,'scratch'=>1),
// 	'4' => array('id'=>5,'prize'=>rand(68,101)*0.1,'scratch'=>0),
// 	'5' => array('id'=>6,'prize'=>rand(113,169)*0.1,'scratch'=>0),
// 	'6' => array('id'=>7,'prize'=>rand(153,229)*0.1,'scratch'=>0),
// 	'7' => array('id'=>8,'prize'=>rand(228,341)*0.1,'scratch'=>0),
// 	'8' => array('id'=>9,'prize'=>rand(338,506)*0.1,'scratch'=>0),
// 	'9' => array('id'=>10,'prize'=>rand(513,769)*0.1,'scratch'=>0),
// 	'10' => array('id'=>11,'prize'=>rand(77,115),'scratch'=>0),
// 	'11' => array('id'=>12,'prize'=>rand(115,173),'scratch'=>0),
// 	'12' => array('id'=>13,'prize'=>rand(173,259),'scratch'=>0),
// 	'13' => array('id'=>14,'prize'=>rand(258,386),'scratch'=>0),
// 	'14' => array('id'=>15,'prize'=>585,'scratch'=>0),
// 	'15' => array('id'=>16,'prize'=>874,'scratch'=>0),
// 	'16' => array('id'=>17,'prize'=>1313,'scratch'=>0),
// 	'17' => array('id'=>18,'prize'=>1969,'scratch'=>0),
// 	'18' => array('id'=>19,'prize'=>2963,'scratch'=>0),
// 	'19' => array('id'=>18,'prize'=>4425,'scratch'=>0),
// 	'20' => array('id'=>19,'prize'=>11888,'scratch'=>0),
// );


$prize_arr = array(
    "prize" => array(1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1011,1012,1013,1014,1015,1016,1017,1018,1019,1020),
    "doublePrize" => array(1,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0),
    "hisPrize" => array(1001,1002,1003,1004,1005,1005,1006,1007,1008,1009,1010,1011,1012,1013,1014,1015,1016,1017,1018,1019,1020),
    "open" =>array(1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0),
    "standard"=> array(1,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0),
    "scratch" => array(1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0),
);

//print_r($arr);

$res['isSuccess'] = 1;
// $res['bonusTotal'] = 986765;
// $res['bonusPercent'] = 87;
// $res['hisAmount'] = 6550;
// $res['amount'] = 11118110;
$res['prize'] = $prize_arr['prize']; //中奖项
$res['doublePrize'] = $prize_arr['doublePrize']; //是否中奖翻倍
$res['hisPrize'] = $prize_arr['hisPrize']; //中奖项
$res['open'] = $prize_arr['open']; //是否开启
$res['standard'] = $prize_arr['standard']; //是否达标
$res['scratch'] = $prize_arr['scratch']; //是否刮奖

echo json_encode($res);
exit;

