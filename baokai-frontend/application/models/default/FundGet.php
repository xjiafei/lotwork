<?php
class FundGet extends BaseModel{
	protected function getTestData(){
		return 'application/testdata/FundBankCardInfo.php';
	}

	public function getDefaultMap(){
		return array(
				'userBankStruc' => array(
						'id' =>'0',
						'bankId' => '0', //银行id
						'bankAccount'=>'用户名', //开户人姓名
						'bankNumber'=>'0000000000000000000', //卡号
						'branchName'=>'支行名称', //支行名称
						'mcBankId' =>'0', //Mc银行ID
// 						'branchAddr' => '****', //支行地址
						'bindDate' => date('Y/m/d'),
						'bindcardType' =>'0',
						'nickName' =>'昵称'
				),
				'overTime' => 137234516754,
                                                                                           'dbNowTime' => 137234516755,
		);
	}
}