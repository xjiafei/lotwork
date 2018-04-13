<?php
class Fundwithdraw extends BaseModel {
	public function getTestData() {
		return 'application/testdata/Fundwithdraw.php';
	}
	public function getDefaultMap() {
		return array (
				"userBankStruc" => array (
// 						array(
							'id' => '1',
							'bankId' => '1',
							'bankAccount' => '默认账户',
							'bankNumber' => '0000000000000000001',
							'branchName' => '支行名称1',
							'mcBankId' => '111',
							'branchAddr' => '****',
							'bindDate' => '2012/07/23'
// 						)
				),
				'availWithdrawTime' => '2',
				'bal' => 20000,
				'availBal' => 15000,
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
		);
	}
}