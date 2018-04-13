<?php
class Register extends Client{
	
	public function init(){
		parent::init();
	}
	
	public function createUser($param){
		$data['param'] = $param;
		$aUrl['prize'] = 'createUser';
		$res = $this->inRequestV2($data, $aUrl);
		$errorNum = '102067';
		$errorType = '5';
		if(isset($res['head']['status'])){
			if($res['head']['status'] =='0'){
				return TRUE; 
			} else if($res['head']['status'] == '100002'){
				return array('errorNum'=>'100002','errorType'=>'1');
			} else if($res['head']['status'] == '1078'){
				return array('errorNum'=>'1078','errorType'=>'6');
			} else if($res['head']['status'] == '102109'){
				return array('errorNum'=>'102109','errorType'=>'6');
			} else if($res['head']['status']=='903'){
				return array('errorNum'=>'903','errorType'=>'5');
			}
		} else {
			return array('errorNum'=>'102067','errorType'=>'5');
		}
	}
	
	
	public function createPtUser($param){
		$data['param'] = $param;
		$aUrl['prize'] = 'createPtUser';
		$res = $this->inRequestV2($data, $aUrl);
		$errorNum = '102067';
		$errorType = '5';
		if(isset($res['head']['status'])){
			if($res['head']['status'] =='0'){
				return TRUE; 
			} else if($res['head']['status'] == '100002'){
				return array('errorNum'=>'100002','errorType'=>'1');
			} else if($res['head']['status'] == '1078'){
				return array('errorNum'=>'1078','errorType'=>'6');
			} else if($res['head']['status'] == '102109'){
				return array('errorNum'=>'102109','errorType'=>'6');
			} else if($res['head']['status']=='903'){
				return array('errorNum'=>'903','errorType'=>'5');
			}
		} else {
			return array('errorNum'=>'102067','errorType'=>'5');
		}
	}
	
	//检测注册链接是否有效
	public function checkUrl($id='',$pid='',$token=''){
		$enrypt = subStr(md5($id.'|'.$pid),28,32);
		$type = substr($pid, -1);
		if($token == $enrypt){
			/* if($type =='2'){//$type =='1'
				$aUrl['prize'] = 'checkRegisted';
				$data['param']['id'] = intval(getSecurityInput($id));
				$res = $this->inRequestV2($data, $aUrl);
				if(isset($res['head']['status']) && $res['head']['status'] =='0'){
					if(isset($res['body']['result']) && $res['body']['result']>0){
						return false;
					} else {
						return true;
					}
				}
				return false;
			} */
			return true;
		} else {
			return false;
		}
	}
	
	//查询数据库中链接有效性
	public function checkUrlDB($link){
		
		$aUrl['prize'] = 'queryUrl';
		$data['param']['url'] = $link;
		$res = $this->inRequestV2($data, $aUrl);
		if(isset($res['head']['status']) && $res['head']['status'] =='0'){
			if(isset($res['body']['result']) && $res['body']['result']>0){
				if($res['body']['result']['days'] !='-1' && getSrtTimeByLong($res['body']['result']['gmtCreated'])+$res['body']['result']['days']*86400<time()){
					return FALSE;
				}
				return $res['body']['result'];
			}
		}
		return FALSE;
	}
	
	//用户名是否合法
	public function islegalAccount($username){
        $data['param']['account'] = $username;
        $uri['prize'] = 'islegalAccount';
        $res = $this->inRequestV2($data, $uri);
        if(isset($res['head']['status']) && $res['head']['status'] ==0){
        	if(isset($res['body']['result']) && !empty($res['body']['result'])){
        		return $res['body']['result'];
        	} else {
        		return 0;
        	}
        } else {
        	return $res['head']['status'];
        }
    }
    //注册页广告位
    public function getAdSpaceById($id){
    	$uri['login'] = 'getAdSpaceById';
    	$data['param'] =array('id' => $id);
    	$res = $this->inRequestV2($data, $uri);
    	return $res;
    }
	
}