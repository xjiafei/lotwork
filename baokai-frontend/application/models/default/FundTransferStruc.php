<?php
class FundTransferStruc extends BaseModel{
	function getTestData(){
		return 'application/testdata/FundTransferStruc.php';
	}
	function getDefaultMap(){
		return array(
			'tCode' => '2011120947940291',
			'transferTime' => '1374806596134',
			'userAccount' => 'cantus',
			'transferAmt' => '3000',
			'isUpward' => '1',
			'rcvAccount' => 'TestUser'
		);
	}
}