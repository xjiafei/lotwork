<?php
$a = array (
		"body" => array (
				"result" => array (
						'orderId' => '00001111',
						'revAccName' => '收款人张三',
						'rcvBankName' => '收款人张三的銀行',
						'rcvEmail' => 'bank@mail.wa',
						//'expireTime' => '1395670931000',
						'expireTime' => '15',
						'breakUrl' => '',
						'cards' => array (
								array(
									'bankId'=>'1', //银行id
									'bankAccount'=>'张三', //开户人姓名
									'bankNumber'=>'9558822201001927423', //卡号
									'branchName'=>'支行名称1', //支行名称
									'mcBankId' =>'111', //Mc银行ID
									//'branchAddr' => '****',
									'bindDate' => '2012/07/23'
							),
							array(
									'bankId'=>'2', //银行id
									'bankAccount'=>'李四', //开户人姓名
									'bankNumber'=>'6228481120647741516', //卡号
									'branchName'=>'支行名称2', //支行名称
									'mcBankId' =>'222', //Mc银行ID
									//'branchAddr' => '****', 
									'bindDate' => '2012/07/22'
							)
						),
						'chargeMemo' => 'wwwwwwwwww' 
				) 
		) 
);
$b = array(
    "body" => array(
        "result" => array(
            "orderId" => null,
            "revAccName" => "常素雅",
            "rcvBankName" => "河南省郑州沙口路支行",
            "rcvAccNum" => "6212261702000370015",
            "rcvEmail" => "123@gmail.com",
            "expireTime" => 15,
            "breakUrl" => null,
            'cards' => array (
                array(
                    'bankId'=>'1', //银行id
                    'bankAccount'=>'张三', //开户人姓名
                    'bankNumber'=>'9558822201001927423', //卡号
                    'branchName'=>'支行名称1', //支行名称
                    'mcBankId' =>'111', //Mc银行ID
                    //'branchAddr' => '****',
                    'bindDate' => '2012/07/23'
                ),
                array(
                    'bankId'=>'2', //银行id
                    'bankAccount'=>'李四', //开户人姓名
                    'bankNumber'=>'6228481120647741516', //卡号
                    'branchName'=>'支行名称2', //支行名称
                    'mcBankId' =>'222', //Mc银行ID
                    //'branchAddr' => '****', 
                    'bindDate' => '2012/07/22'
                )
            ),
            "chargeMemo" => "c562",
            "bankId" => 1,
            "mode" => 2
        ),
    )
);
return $b;
