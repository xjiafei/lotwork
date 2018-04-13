<?php	
class TransferRecorder extends BaseModel{
	
	public function getDefaultMap(){
		return array(
 				"sn"=>"没有数据",
				"transferTime"=>"没有数据",
				"userAccount"=>"没有数据",
				"transferAmt"=>"没有数据",
				"isUpward"=>"没有数据",
				"rcvAccount"=>"没有数据",
		);
	}
}