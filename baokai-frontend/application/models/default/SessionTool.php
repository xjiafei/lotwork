<?php
class SessionTool {
	private $_rediska;
	private $_config;
	private $_redisAlias;
	private $_redisIndexSet;
	private $_redisIndexId;
	function __construct($config){
		$this->_config = $config;
		$this->_redisAlias = Zend_Registry::get('rediska');
		//$this->_redisAlias = $this->_rediska->on($this->_config->FRONTEND_ALIAS);
	}
	
	
	private function _setIndexId($userId){
		$this->_redisIndexId = md5('ANVO'.$userId);
	}
	
	//获取session 索引
	private function _getIndexSet(){
		
		return $this->_redisAlias->getSet($this->_redisIndexId);
	}
	
	//清空多登陆用户session信息
	public function truncate($userId){
		$this->_setIndexId($userId);
    	$aSeessionId = $this->_getIndexSet();
		foreach ($aSeessionId as $iSesionId){
			$status = $this->_redisAlias->delete($iSesionId);//删除session 内容
			//如果session 已经删除 则删除关联索引集合
			if($status ==1 && !$this->_redisAlias->exists($iSesionId)){
				$this->_redisAlias->deleteFromSet($this->_redisIndexId,$iSesionId); //删除管理索引
			}
		}
		$this->_redisAlias->delete($this->_redisIndexId);
	}
	
	//更新某个用户的多用户session信息
	public function update($userId,$sessionInfo=array()){
		$this->_setIndexId($userId);
		$aSeessionId = $this->_getIndexSet();
		foreach ($aSeessionId as $iSesionId){
			
			try {
				$sessionData = Zend_Json::decode($this->_redisAlias->get($iSesionId));
			} catch (Exception $e){
				$sessionData = NULL;
			}
			if(!is_null($sessionData)){
				$sessionData['datas']['info'] = array_merge($sessionData['datas']['info'],$sessionInfo);
				$this->_redisAlias->set($iSesionId,Zend_Json::encode($sessionData));
			} else {
				$this->_redisAlias->deleteFromSet($this->_redisIndexId,$iSesionId); //删除管理索引
			}
		}
	}
}