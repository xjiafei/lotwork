<?php
sleep(3);
$output = array(
	"isSuccess" => 1,
	"msg"       => "恭喜您投注成功~!",
	"type"      => "success",
	"data"      => array(
		"handingcharge"   => null,
		"tplData"         => array(
			"msg"               => "恭喜您投注成功~!",
			"balls"             => null,
			"bitDate"           => null,
			"lotteryType"       => null,
			"currentGameNumber" => null
		),
		"blockadeInfo"    => null,
		"orderData"       => null,
		"overMutipleData" => null,
		"projectId"       => 'DCDSGSGDFHGDFHFDF',
		"writeTime"       => date('Y-m-d H:i:s'),
		"number"		  => '20150303-0'.mt_rand(10,99),
		"totalprice"      => mt_rand(10000000, 99999999),
		"orderId"		  => mt_rand(500000000, 999999999),
		"result"          => null,
		"winMoney"        => null,
		"winNum"          => null,
		"list"            => null
	)
);

$json = json_encode( $output );
echo $json;

?>