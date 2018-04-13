<?php
return array (
		"body" => array (
				"result" => array (
						"userBankStruc" => array (
								array(
									'id' => '1111',
									'bankId'=>'1', //银行id
									'bankAccount'=>'张三', //开户人姓名
									'bankNumber'=>'9558822201001927423', //卡号
									'branchName'=>'支行名称1', //支行名称
									'mcBankId' =>'1', //Mc银行ID
// 									'branchAddr' => '****',
									'bindDate' => '2012/07/23'
							),
							array(
									'id' => '2222',
									'bankId'=>'2', //银行id
									'bankAccount'=>'李四', //开户人姓名
									'bankNumber'=>'6228481120647741516', //卡号
									'branchName'=>'支行名称2', //支行名称
									'mcBankId' =>'2', //Mc银行ID
// 									'branchAddr' => '****', 
									'bindDate' => '2012/07/22'
							),
							array(
									'id' => '3333',
									'bankId'=>'3', //银行id
									'bankAccount'=>'王五', //开户人姓名
									'bankNumber'=>'62284481690665212210', //卡号
									'branchName'=>'支行名称3', //支行名称
									'mcBankId' =>'3', //Mc银行ID
// 									'branchAddr' => '****',
									'bindDate' => '2012/07/24'
							),
							array(
									'id' => '4444',
									'bankId'=>'4', //银行id
									'bankAccount'=>'何柳', //开户人姓名
									'bankNumber'=>'6228480830703146415', //卡号
									'branchName'=>'支行名称4', //支行名称
									'mcBankId' =>'4', //Mc银行ID
// 									'branchAddr' => '****', //支行地址
									'bindDate' => '2012/07/23'
							)
						),
						'availWithdrawTime' => '2',
						'bal' => 22000,
// 						'availBal' => 18000,
						'withdrawStruc' => array (
								'user' => array (
										'time' => '1374806596134',
										'lowLimit' => '100',
										'upLimit' => '200000' 
								),
								'vip' => array (
										'time' => '1374806596134',
										'lowLimit' => '100',
										'upLimit' => '500000' 
								) 
						) 
				) 
		) 
);