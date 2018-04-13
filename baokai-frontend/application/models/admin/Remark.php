<?php
class Remark extends Client {
	
	private $_data = array();

	function __construct(){
		parent::__construct();
	}
	
	//获取所有附言
	public function getAllRemarks($param,$pageNum=1,$pagesize=10){
		if(count($param)<=0){
			$param = (object)$param;
		}
		$data = array(
			'param'=>$param
		);
		if($pageNum>=0){
			$data['pager'] = array(
					'startNo'=>$pageNum*$pagesize+1,
					'endNo'=>($pageNum+1)*$pagesize
			);
		}
		$uri['remark'] = 'getAllRemarks';
		$result = $this->inRequestV2($data,$uri);
		if(isset($result['head']['status']) && $result['head']['status'] == '0'){
			foreach ($result['body']['result'] as $key=>$value){
				$result['body']['result'][$key]['id'] = $value['id'];
				$result['body']['result'][$key]['user_id'] = $value['userId'];
				$result['body']['result'][$key]['account'] = !empty($value['account']) ? $value['account'] : '';
				$result['body']['result'][$key]['topAgent'] = !empty($value['topAgent']) ? $value['topAgent'] : '';
				$result['body']['result'][$key]['userType'] = $value['vipLvl'];
				$result['body']['result'][$key]['gmt_created'] = $value['gmtCreated'];
				$result['body']['result'][$key]['gmt_modified'] = !empty($value['gmtModified']) ? date('Y-m-d H:i:s',getSrtTimeByLong($value['gmtModified'])) : '';
				$result['body']['result'][$key]['remark'] = !empty($value['remark']) ? $value['remark'] : '';
				$result['body']['result'][$key]['status'] = getSrtTimeByLong($value['gmtCansetremark']) > time() ? 1 : 0;
			}
			return $result['body'];
		} else {
			return null;
		}
	}
	
	//解除附言绑定
	public function cancleRemark($userId){
		$data = array(
			'param'=>intval($userId)
		);
		$uri['remark'] = 'cancelRemark';
		$result = $this->inRequestV2($data, $uri);
		if(isset($result['head']['status']) && $result['head']['status'] == '0'){
			return true;
		} else {
			return false;
		}
	}
	
	//解除用户锁定
	public function cancleLock($userId){
		$data = array(
			'param'=>intval($userId)
		);
		$uri['remark'] = 'cancelLock';
		$result = $this->inRequestV2($data, $uri);
		if(isset($result['head']['status']) && $result['head']['status'] == '0'){
			return true;
		} else {
			return false;
		}
	}
	
	//获取所有的回收列表
	public function getAllRecyleRemarks($param,$pageNum,$pagesize=10){
		if(count($param)<=0){
			$param = (object)$param;
		}
		$data = array(
			'param'=>$param
		);
		if($pageNum>=0){
			$data['pager'] = array(
					'startNo'=>$pageNum*$pagesize+1,
					'endNo'=>($pageNum+1)*$pagesize
			);
		}
		$uri['remark'] = 'getAllRecyleRemarks';
		$result = $this->inRequestV2($data, $uri);
		if(isset($result['head']['status']) && $result['head']['status'] == '0'){
			if(count($result['body']['result'])>0){
				foreach ($result['body']['result'] as $key=>$value){
					$result['body']['result'][$key]['remark'] = !empty($result['body']['result'][$key]['remark']) ? $result['body']['result'][$key]['remark'] : '';
					$result['body']['result'][$key]['userName'] = !empty($result['body']['result'][$key]['userName']) ? $result['body']['result'][$key]['userName'] : '';
					$result['body']['result'][$key]['createTime'] = $result['body']['result'][$key]['createTime']>0 ? date('Y-m-d H:i:s',getSrtTimeByLong($result['body']['result'][$key]['createTime'])) : '';
				}
				return $result['body'];
			}
		}
		$result['body']['result'] = array();
		$result['body']['pager']['total'] = 0;
		return $result['body'];
	}
	
	//回收附言
	public function recyleRemark($id,$remark){
		$data = array(
			'param'=>array(
					'id'=>$id,
					'remark'=>$remark
			)
		);
		$uri['remark'] = 'recyleRemark';
		$result = $this->inRequestV2($data, $uri);
		if(isset($result['head']['status']) && $result['head']['status'] == '0'){
			return true;
		} else {
			return false;
		}
	}
	
	//设置最大修改时间
	public function setCanModifiedDays($days){
		$data = array(
			'param'=>$days
		);
		$uri['remark'] = 'setCanModifiedDays';
		$result = $this->inRequestV2($data, $uri);
		if(isset($result['head']['status']) && $result['head']['status'] == '0'){
			return true;
		} else {
			return false;
		}
	}
	
	//获取最大修改时间
	public function getModifiedDays(){
		$data = array(
			'param'=>array(
			)
		);
		$uri['remark'] = 'getModifiedDays';
		$result = $this->inRequestV2($data, $uri);
		$days =30;
		if(isset($result['head']['status']) && $result['head']['status'] == '0'){
			$days =  intval($result['body']['result']);
		}
		return $days;
	}
	
	//导入用户
	public function importUser($aUserId=array()){
		$data = array(
			'param'=>array(
				'uId' =>$aUserId
			)
		);
		$uri['remark'] = 'exportUsers';
		$result = $this->inRequestV2($data, $uri);
		if(isset($result['head']['status']) && $result['head']['status'] == '0'){
			return true;
		} else {
			return false;
		}
	}
}