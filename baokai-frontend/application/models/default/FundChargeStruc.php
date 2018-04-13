<?php
class FundChargeStruc extends BaseModel{
	function getTestData(){
		return 'application/testdata/FundChargeStruc.php';
	}
	function getDefaultMap(){
		return array(
			array(
					'sn'=>'2011120947940291',
					'applyTime'=>'1374806596134',
					'chargeTime'=>'1376806596134',
					'applyAmt'=>'500.0000',
					'chargeAmt'=>'500.0000',
					'status'=>'2',
					'bankId'=>'1',
					'chargeMemo'=>'ddddddd',
					'account'=>'张三',
					'userAct'=>'cantus',
					'cardNumber'=>'9558822201001927423',
					'mcFee'=>'10'
			)
		);
	}
}