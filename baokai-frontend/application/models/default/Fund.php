<?php
class Fund extends BaseModel {
	protected function getTestData() {
		return 'application/testdata/FundBankStruc.php';
	}
	public function getDefaultMap() {
		return array(
			"bankStruc" => array (
					'id' => '1',
					'name' => '农业银行', // 银行名字
					'logo' => 'ABC', // 银行logo
					'code' => '001', // 银行编码
					'url' => 'http://www.abchina.com/cn/', // 银行链接
					'inUse' => '1', // 是否使用中
					'deposit' => '1', // 能否充值
					'withdraw' => '1', // 能否提现
					'bind' => '0', // 是否需要绑定
					'mownecumId' => '01', // Mownecum银行id
					'upLimit' => '50000', // 普通用户充值上限
					'lowLimit' => '100', // 普通用户充值下限
					'vipUpLimit' => '50', // vip用户充值上限
					'vipLowLimit' => '500000', // vip用户充值下限
					'rtnSet' => '1', // 手续费是否返还
					'rtnMin' => '300', // 手续费返还范围
					'rtnStruc' => array(array("sm"=>1,"big"=>12,"type"=>1,"value"=>32)) //手续费返还金额'
			),
			"userBankStruc" => array(
					'bankId'=>'0', //银行id
					'bankAccount'=>'用户名', //开户人姓名
					'bankNumber'=>'0000000000000000000', //卡号
					'branchName'=>'支行名称', //支行名称
					'mcBankId' =>'0', //Mc银行ID
// 					'branchAddr' => '****', //支行地址
					'bindDate' => '2012/07/23'
			)
		);
	}
}