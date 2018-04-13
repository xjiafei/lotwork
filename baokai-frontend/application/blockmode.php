<?php
$aBlock =  array (
	'blockmode' => 1, /* [''|1|0] 當為TRUE  全開模式  FALSE 全都檔模式*/
	 'TW'       => 1,  // 國家內容  1 就是允許  0 就是不允許
	 'CN'	    => 1,

); 
$ablockConfig = array();
include_once '/usr/local/conf/blockproduct.conf';
$blocklist = array_merge($aBlock,$ablockConfig);
return $blocklist;