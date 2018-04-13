<?php
class ExceptionRefund extends  BaseModel{

	public function getDefaultMap(){
		
		return array(
				"exceptId" =>11,//异常id
				"bankId" =>888888888 ,//付款银行名称
				"cardAcct"=>"默认数据",//付款账号
				"cardNumber" => "默认数据", //付款卡号
				"bankName" => "默认数据",//付款银行分行名称
				"bankAddr"=>"默认数据", //付款银行分行地址
				"attachMent"=>"默认数据",//附件
				"applyMemo"=>"默认数据",//备注
		);
	}
}