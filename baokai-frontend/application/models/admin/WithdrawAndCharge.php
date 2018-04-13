<?php	
class WithdrawAndCharge extends BaseModel{
	
	public function getDefaultMap(){
		return array(
 				"account"=>"没有数据",
				"onlineCharge"=>"没有数据",
				"onlineWithdraw"=>"没有数据",
				"chargeFee"=>"没有数据",
				"withdrawFee"=>"没有数据",
				"manualAddCoin"=>"没有数据",
				"manualReduceCoin"=>"没有数据",
				"chargeSum"=>"没有数据",
				"withdrawSum"=>"没有数据",
		);
	}
}