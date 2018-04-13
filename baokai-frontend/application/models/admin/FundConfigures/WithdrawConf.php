<?php

/* 提现配置 */
class WithdrawConf extends Client{
	
	//获取可提现用户白名单
	public function getWhiteDrawWhiteList($data){
		$aUrl['fund'] = 'whiteList';
		$result = $this->inRequestV2($data, $aUrl);
		if(isset($result['head']['status']) && $result['head']['status']=='0'){
			return $result['body'];
		}
		return array();
	}
	
	//添加可提现用户白名单
	public function addWhiteUser($data){
		
		$aUrl['fund'] = 'whiteList';
		$result = $this->inRequestV2($data, $aUrl);
		return $result;
	}
	
	//删除可提现用户白名单
	public function delWhiteUser($data){
		
		$aUrl['fund'] = 'whiteList';
		$result = $this->inRequestV2($data, $aUrl);
		if(isset($result['head']['status']) && $result['head']['status']=='0'){
			return true;
		}
		return false;
	}
	
}