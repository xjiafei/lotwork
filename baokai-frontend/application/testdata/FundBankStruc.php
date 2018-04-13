<?php
// 银行卡列表 测试数据
return array (
		"body" => array (
				"result" => array (
						"bankStruc" => array (
								array (
										'id' => '1',
										'name' => '工商银行',
										'logo' => 'ICBC',
										'code' => '1',
										'url' => 'http://www.icbc.com.cn/',
										'inUse' => '1',
										'deposit' => '1',
										'withdraw' => '1',
										'bind' => '0',
										'mownecumId' => '1',
										'upLimit' => '40000',
										'lowLimit' => '200',
										'vipUpLimit' => '500000',
										'vipLowLimit' => '500',
										'rtnMin' => '1',
										'rtnStruc' => array("fun"=>1,"value"=>100),
										'rtnSet' => array("sm"=>1,"big"=>12,"type"=>1,"value"=>32),
										"other" => 1,
										"otheruplimit" => 10,
										"otherdownlimit" => 300,
										"othervipdownlimit" => '',
										"othervipuplimit" => '',
										"gmtCreatedString" => '',
										"gmtModifiedString" => ''
								),
								array (
										'id' => '2',
										'name' => '招商银行',
										'logo' => 'CMB',
										'code' => '2',
										'url' => 'http://www.cmb.com.cn/',
										'inUse' => '1',
										'deposit' => '1',
										'withdraw' => '1',
										'bind' => '1',
										'mownecumId' => '2',
										'upLimit' => '58000',
										'lowLimit' => '180',
										'vipUpLimit' => '500000',
										'vipLowLimit' => '500',
										'rtnMin' => '1',
										'rtnStruc' => array("fun"=>1,"value"=>100),
										'rtnSet' => array("sm"=>1,"big"=>12,"type"=>1,"value"=>32),
										"other" => 1,
										"otheruplimit" => 10,
										"otherdownlimit" => 300,
										"othervipdownlimit" => '',
										"othervipuplimit" => '',
										"gmtCreatedString" => '',
										"gmtModifiedString" => ''
								),
								array (
										'id' => '3',
										'name' => '建设银行',
										'logo' => 'CCB',
										'code' => '3',
										'url' => 'http://www.icbc.com.cn/',
										'inUse' => '1',
										'deposit' => '1',
										'withdraw' => '1',
										'bind' => '1',
										'mownecumId' => '3',
										'upLimit' => '70000',
										'lowLimit' => '400',
										'vipUpLimit' => '500000',
										'vipLowLimit' => '500',
										'rtnMin' => '1',
										'rtnStruc' => array("fun"=>1,"value"=>100),
										'rtnSet' => array("sm"=>1,"big"=>12,"type"=>1,"value"=>32),
										"other" => 1,
										"otheruplimit" => 10,
										"otherdownlimit" => 300,
										"othervipdownlimit" => '',
										"othervipuplimit" => '',
										"gmtCreatedString" => '',
										"gmtModifiedString" => ''
								),
								array (
										'id' => '4',
										'name' => '农业银行',
										'logo' => 'ABC',
										'code' => '4',
										'url' => 'http://www.abchina.com/cn/',
										'inUse' => '1',
										'deposit' => '1',
										'withdraw' => '1',
										'bind' => '1',
										'mownecumId' => '4',
										'upLimit' => '80000',
										'lowLimit' => '800',
										'vipUpLimit' => '40000',
										'vipLowLimit' => '500',
										'rtnMin' => '1',
										'rtnStruc' => array("fun"=>1,"value"=>100),
										'rtnSet' => array("sm"=>1,"big"=>12,"type"=>1,"value"=>32),
										"other" => 1,
										"otheruplimit" => 10,
										"otherdownlimit" => 300,
										"othervipdownlimit" => '',
										"othervipuplimit" => '',
										"gmtCreatedString" => '',
										"gmtModifiedString" => ''
								),
								array (
										'id' => '5',
										'name' => '中国银行',
										'logo' => 'BOC',
										'code' => '5',
										'url' => 'http://www.boc.cn/',
										'inUse' => '1',
										'deposit' => '1',
										'withdraw' => '1',
										'bind' => '1',
										'mownecumId' => '5',
										'upLimit' => '90000',
										'lowLimit' => '700',
										'vipUpLimit' => '560000',
										'vipLowLimit' => '300',
										'rtnMin' => '1',
										'rtnStruc' => array("fun"=>1,"value"=>100),
										'rtnSet' => array("sm"=>1,"big"=>12,"type"=>1,"value"=>32),
										"other" => 1,
										"otheruplimit" => 10,
										"otherdownlimit" => 300,
										"othervipdownlimit" => '',
										"othervipuplimit" => '',
										"gmtCreatedString" => '',
										"gmtModifiedString" => ''
								) 
						),
						"userBankStruc" => array (
								array(
									'id'=>'1',
									'bankId'=>'1', //银行id
									'bankAccount'=>'张三', //开户人姓名
									'bankNumber'=>'9558822201001927423', //卡号
									'branchName'=>'支行名称1', //支行名称
									'mcBankId' =>'1', //Mc银行ID
// 									'branchAddr' => '****',
									'bindDate' => '1387682479677'
							),
							array(
									'id'=>'1',
									'bankId'=>'2', //银行id
									'bankAccount'=>'李四', //开户人姓名
									'bankNumber'=>'6228481120647741516', //卡号
									'branchName'=>'支行名称2', //支行名称
									'mcBankId' =>'2', //Mc银行ID
// 									'branchAddr' => '****', 
									'bindDate' => '1387682479677'
							),
							array(
									'id'=>'1',
									'bankId'=>'3', //银行id
									'bankAccount'=>'王五', //开户人姓名
									'bankNumber'=>'62284481690665212210', //卡号
									'branchName'=>'支行名称3', //支行名称
									'mcBankId' =>'3', //Mc银行ID
// 									'branchAddr' => '****',
									'bindDate' => '1387682479677'
							),
							array(
									'id'=>'1',
									'bankId'=>'4', //银行id
									'bankAccount'=>'何柳', //开户人姓名
									'bankNumber'=>'6228480830703146415', //卡号
									'branchName'=>'支行名称4', //支行名称
									'mcBankId' =>'4', //Mc银行ID
// 									'branchAddr' => '****', //支行地址
									'bindDate' => '1387682479677'
							)
						) 
				) 
		) 
);