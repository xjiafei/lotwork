<?php

$now = date('Y/m/d H:i:s');

$balls = array(mt_rand(1,6), mt_rand(1,6), mt_rand(1,6));

$output = array(
	"username"   => "fd0011",
	"lastnumber" => "20150218-0".mt_rand(10,99),
	"lastballs"  => implode(",", $balls),
	"ballInfo"   => null,
	"isstop"     => 0,
	"number"     => "20150303-0".mt_rand(10,99),
	"issueCode"  => 20150303101067,
	"winlists"	 => array(
		array(
			"projectId" => 'DCDSGSGDFHGDFHFDF',
			"winMoney"  => mt_rand(10000000, 99999999)
		),
		array(
			"projectId" => 'DCDSGSGDFHGDFHFDK',
			"winMoney"  => mt_rand(10000000, 99999999)
		),
	),
	
	// "resulttime" => "",
	// "nowtime"    => "",
	// "nowstoptime"=> ""

	"resulttime" => "2015/03/03 17:37:12",
	"nowtime"    => "2015/03/03 17:40:12",
	"nowstoptime"=> "2015/03/03 17:42:12"

	// "nowtime"    => $now,
	// "nowstoptime"=> date('Y/m/d H:i:s', strtotime($now. ' + 2 minutes')),
	// "resulttime" => date('Y/m/d H:i:s', strtotime($now. ' - 3 minutes'))
);

$json = json_encode( $output );
echo $json;

?>