<?php
class Remark extends Client{
	private $_data = null;
	function __construct(){
		parent::__construct();
	}
	
	//获取用户当前附言
	public function getUserRemark($userId){
		$data=array(
			'param'=>array(
				'userId' =>$userId
			)
		);
		$uri['remark'] = 'getRemark';
		$result = $this->inRequestV2($data,$uri);
		/* return array(
			'id' => '123',
			'remark' => '123123',
			'userId' => 31,
			'userAccount' => 'admin',
			'gmtCreated' => getSendTime()-200000,
			'gmtModified' => getSendTime()-86400*1000*30,
			'gmtCanSetRemark' => getSendTime()+86400*1000
		); */
		if(isset($result['head']['status']) && $result['head']['status'] =='0'){
			return $result['body']['result'];
		} else {
			return '';
		}
	}
	
	//获取系统附言
	public function getNextSystemRemark($userId){
		$data = array(
			'param'=>array(
				'userId' => $userId
			)
		);
		$uri['remark'] = 'getNextSystemRemark';
		$result = $this->inRequestV2($data, $uri);
// 		return rand(10000, 99999);
		if(isset($result['head']['status']) && $result['head']['status'] == '0'){
			if(isset($result['body'])){
				return $result['body']['result'];
			}
		}
		return 0;
	}
	
	//检测用户附言是否可用
	public function checkremarkexist($remark){
		
		$data=array(
			'param'=>array(
				'remark'=>strtolower($remark)
			)
		);
		$uri['remark'] = 'checkRemarkExist';
		$result = $this->inRequestV2($data,$uri);
		if(isset($result['head']['status']) && $result['head']['status'] =='0'){
			if(isset($result['body']['result'])){
				return $result['body']['result'];
			}
		}
		return true;
	}
	
	//保存附言
	public function saveRemark($userId,$remark){
		$data=array(
			'param'=>array(
					'userId' =>$userId,
					'remark'=>strtolower($remark)
			)
		);
		$uri['remark'] = 'saveRemark';
		$result = $this->inRequestV2($data,$uri);
		if(isset($result['head']['status']) && $result['head']['status'] =='0'){
			return $result['body']['result'];
		} else {
			return false;
		}
	}
	
	//修改附言
	public function editRemark($userId,$remark){
		$data=array(
			'param'=>array(
					'userId' =>$userId,
					'remark'=>strtolower($remark)
			)
		);
		$uri['remark'] = 'updateRemark';
		$result = $this->inRequestV2($data,$uri);
		if(isset($result['head']['status']) && $result['head']['status'] =='0'){
			return $result['body']['result'];
		} else {
			return false;
		}
	}
	
	//取消唯一附言
	public function delRemark($userId){
		$data=array(
			'param'=>$userId
		);
		$uri['remark'] = 'cancelRemark';
		$result = $this->inRequestV2($data,$uri);
		if(isset($result['head']['status']) && $result['head']['status'] =='0'){
			return true;
		} else {
			return false;
		}
	}
	
	//检测附言是否可用
	public function checkRemarkEnable($remark){
		$data=array(
			'param'=>array(
					'remark' =>strtolower($remark)
			)
		);
		$uri['remark'] = 'checkRemarkExist';
		$result = $this->inRequestV2($data,$uri);
		if(isset($result['head']['status']) && $result['head']['status'] =='0'){
			return true;
		} else {
			return false;
		}
	}
	
	//获取用户上次修改时间
	public function getModifiedDays(){
		$data=array(
			'param'=>array(
			)
		);
		$uri['remark'] = 'getModifiedDays';
		$result = $this->inRequestV2($data,$uri);
		if(isset($result['head']['status']) && $result['head']['status'] =='0'){
			return $result['body']['result'];
		} else {
			return 30;
		}
	}
	
	
}