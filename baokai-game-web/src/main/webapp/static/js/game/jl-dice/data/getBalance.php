<?php
$balance = mt_rand(1000, 99999999999);
echo $balance;


// $balance = mt_rand(1000, 99999999) . '.' . rand(10, 99);
// $balance = floatval($balance);

// $output = array(
// 	'status' => 'ok',
// 	'title'  => '获取账户总余额',
// 	'balance'=> $balance,
// 	'currency' => number_format($balance, 2)
// );

// 错误
/*
$output = array(
	'status' => 'failure',
	'msg'  => '获取账户总余额出错'
);
*/
	
// $json = json_encode( $output );
// echo $json;

?>