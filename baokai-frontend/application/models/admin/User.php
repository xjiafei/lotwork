<?php
/*
 * 用户操作类
 * 凡是对用户操作的方法统一写在这里面
 * 方法
 *    getUserSearchData($data) 返回用户搜索返回的数据
 *    
 */
class User extends Client
{
    
    private $_header = null ;    
    
    function __construct() {
        parent::__construct();
        $header = new Header();
        $this->_header['head'] = $header->getDefaultMap();
    
    }
    
    /**
     * 返回用户搜索返回的数据
     * @param array $data
     * @return array
     */
    public function  getUserSearchData($data)
    {
        $searchdata = array(
            
        );
        
        return $searchdata;
    }
    
    /**
     * 读取冻结用户列表
     * @param type $data
     * @return array
     */
    public function getFreezeUserList($param='',$page=1, $size=1)
    {
        $start = ($page-1)*$size;
        if(!empty($param))
        {
            if(isset($param['email']))
            {
                $type = $param['email'];
            }
            if(isset($param['username']))
            {
                $type = $param['username'];
            }
            $data =array(
                "body" =>array(
                    "param" => array(
                        'account'  => $type,
                    ),
                "pager" => array(
                    'startNo' => $start,
                    "endNo"=>$start+$size-1
                    )
                )
            );
            $uri = Zend_Registry::get ( "firefog" )->freezeUser->freezeUserlist;
        }
        else
        {
            $data =array(
                "body" =>array(
                    "param" => array(
                        'account'  => '',
                    ),
                "pager" => array(
                     'startNo' => $start,
                    "endNo"=>$start+$size-1
                    )
                )
            );
            $uri = Zend_Registry::get ( "firefog" )->freezeUser->freezeUserlist;
        }
        
        return $this->inRequest ( array_merge($this->_header, $data), $uri );
    }
    
    /**
     * 读取总代的详情
     */
    public function gettopproxydetail($userid)
    {
        $data =array(
                "body" =>array(
                    "param" => array(
                        'userId'=>$userid
                    ),
                "pager" => array(
                    'startNo' => 0,
                    "endNo"=>0
                    )
                )
            );
        $uri = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->queryUserDetailInfo;
        
        return $this->inRequest ( array_merge($this->_header, $data), $uri );
    }

    public function getAccomplaintsList($param='', $page=1, $size=1)
    {
        $start = (intval($page)-1)*intval($size);
        $data =array(
            "body" =>array(
            "param" => $param,
            "pager" => array(
                'startNo' => $start,
                "endNo"=>intval($page)*intval($size)-1,
            )
            )
        );
        $uri = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->queryUserAppealByCriteria   ;
        $res =  $this->inRequest ( array_merge($this->_header, $data), $uri );
        return $res;
    }
    
    /**
     * 读取冻结用户历史数据
     * @return array
     */
    public function getFreezeUserHistoryList($param='', $page=1, $size=1)
    {
        $res = array();
        $start = ($page-1)*$size+1;

        $data =array(
            "body" =>array(
                "param" => $param,
                "pager" => array(
                    'startNo' => $start,
                    "endNo"=>$start+$size-1
                )
            )
        );

        $uri = Zend_Registry::get ( "firefog" )->freezeUser->queryUserFreezeLog   ;
       	return $this->inRequest ( array_merge($this->_header, $data), $uri );
    }	
    
    /**
     * 冻结用户
     * @return type
     */
    public function freezeuser($param)
    {
        $data =array(
                "body" =>array(
                    "param" => array(
                        'range'=>$param['range'],
                        'userId'=>$param['userid'],
                        'method'=>$param['method'],
                        'memo'=>$param['memo'],
                        'freezeAccount'=>$param['freezeAccount']
                    ),
                "pager" => array(
                    'startNo' => 0,
                    "endNo"=>0
                    )
                )
            );
        $uri = Zend_Registry::get ( "firefog" )->freezeUser->freezeUser;
        
        return $this->inRequest ( array_merge($this->_header, $data), $uri );
    }
    
    /**
     * 解冻用户
     * @return type
     */
    public function unfreezeuser($data)
    {
        $data =array(
                "body" =>array(
                    "param" => array(
                        'userId'=>$data['userid'],
                        'range'=>$data['range'],
                        'memo'=>$data['memo'],
                        'freezeAccount'=>$data['freezeAccount']
                    ),
                "pager" => array(
                    'startNo' => 0,
                    "endNo"=>0
                    )
                )
            );
        
        $uri = Zend_Registry::get( "firefog" )->freezeUser->unFreezeUser;
        return $this->inRequest ( array_merge($this->_header, $data), $uri );
    }

    /**
     * 读取用户名字
     * @return type
     */
    public function getusername($userid)
    {
        $data =array(
                "body" =>array(
                    'param'=>array(
                    'userId'=>$userid
                    ),
                "pager" => array(
                    'startNo' => 0,
                    "endNo"=>10
                    )
                )
            );
        $uri = Zend_Registry::get ( "firefog" )->queryUserByCriteria_proxy->queryUserDetailInfo;
        
        return $this->inRequest ( array_merge($this->_header, $data), $uri );
    }
    
    /**
     * 账户申诉里面的审核操作
     */
    public function plead($param)
    {
        $data =array(
                "body" =>array(
                    "param" => array(
                        'account'=>$param
                    ),
                "pager" => array(
                    'startNo' => 0,
                    "endNo"=>0
                    )
                )
            );
        $uri = Zend_Registry::get ( "firefog" )->proxy->getUserByAccount;
//        $uri = Zend_Registry::get ( "firefog" )->queryUserAppealDetail->queryUserAppealDetail;
        
        return $this->inRequest ( array_merge($this->_header, $data), $uri );
    }
    
    /**
     * 账户申诉里面申诉详情察看功能
     */
    public function getpleadinfo($param)
    {
         $data =array(
                "body" =>array(
                    "param" => array(
                        'account'=>$param
                    ),
                "pager" => array(
                    'startNo' => 0,
                    "endNo"=>0
                    )
                )
            );
        $uri = Zend_Registry::get ( "firefog" )->proxy->getUserByName;
        
        return $this->inRequest ( array_merge($this->_header, $data), $uri );
    }


    /**
     * 
     * @return type
     */
    public function pleadinfo($param)
    {
        $data =array(
                "body" =>array(
                    "param" => array(
                        'id'=>$param
                    ),
                "pager" => array(
                    'startNo' => 0,
                    "endNo"=>0
                    )
                )
            );
        $uri = Zend_Registry::get ( "firefog" )->queryUserAppealDetail->queryUserAppealDetail;
        
        return $this->inRequest ( array_merge($this->_header, $data), $uri );
    }
    
    /**
     * 客服填写审核资料
     */
    public function userAppealAudit($param)
    {
         $data =array(
                "body" =>array(
                    "param" => $param,
                "pager" => array(
                    'startNo' => 0,
                    "endNo"=>0
                    )
                )
            );
        $uri = Zend_Registry::get ( "firefog" )->queryUserAppealDetail->userAppealAudit;
        
        return $this->inRequest ( array_merge($this->_header, $data), $uri );
    }
    
    /**
     * 用用户名查询用户信息
     * @param type $account
     * @return type
     */
    public function getuserbyname($account)
    {
        $data =array(
                "body" =>array(
                    "param" => array(
                        'account'=>trim(strtolower($account)),
                    ),
                "pager" => array(
                    'startNo' => 0,
                    "endNo"=>0
                    )
                )
            );
        $uri = Zend_Registry::get ( "firefog" )->accountsSecurity->queryUserByName;
        
        return $this->inRequest ( array_merge($this->_header, $data), $uri );
    }
    
    /**
     * 保存用户信息
     * @param type $data
     * @return type
     */
    public function savepersonalinfo($data)
    {
        $uri = Zend_Registry::get ( "firefog" )->accountsSecurity->personalinfosmt;
        return $this->inRequest ( array_merge($this->_header, $data), $uri );
    }
    
    //获取奖金组
    public function getGamePrizeList($userid){
    	$userLvl = 1;
    	$userInfo = $this->getusername($userid);
    	if(isset($userInfo['body']['result']['userStruc']) && count($userInfo['body']['result']['userStruc'])>0){
    		$userLvl = $userInfo['body']['result']['userStruc']['userLvl'] == '0' ? 0 : '1';
    	}
    	$data['param']['userid'] = $userid;
    	$data['param']['type'] =  $userLvl;
    	$data['param']['awardType'] =  0;
    	$aUrl['prize'] = 'initCreateUrl';
    	$res = $this->inRequestV2($data, $aUrl);
    	$result = array();
    	if(isset($res['head']['status']) && $res['head']['status']=='0'){
    		$result = $res['body']['result']['userAwardListStruc'];
    	} /* else {
    	$str = '[{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":"1","awardName":"奖金组1500","directRet":8,"threeoneRet":8,"status":1,"directLimitRet":7,"threeLimitRet":7},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":"2","awardName":"奖金组1700","directRet":8,"threeoneRet":8,"status":1,"directLimitRet":7,"threeLimitRet":7},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":"3","awardName":"奖金组1800","directRet":3,"threeoneRet":8,"status":1,"directLimitRet":2,"threeLimitRet":7},{"lotterySeriesCode":2,"lotterySeriesName":"3D系","awardGroupId":"1","awardName":"奖金组1500","directRet":8,"threeoneRet":8,"status":1,"directLimitRet":7,"threeLimitRet":7},{"lotterySeriesCode":2,"lotterySeriesName":"3D系","awardGroupId":"2","awardName":"奖金组1700","directRet":8,"threeoneRet":8,"status":1,"directLimitRet":7,"threeLimitRet":7},{"lotterySeriesCode":2,"lotterySeriesName":"3D系","awardGroupId":"3","awardName":"奖金组1800","directRet":8,"threeoneRet":8,"status":1,"directLimitRet":7,"threeLimitRet":7},{"lotterySeriesCode":3,"lotterySeriesName":"11选5系","awardGroupId":"1","awardName":"奖金组1500","directRet":8,"threeoneRet":8,"status":1,"directLimitRet":7,"threeLimitRet":7},{"lotterySeriesCode":3,"lotterySeriesName":"11选5系","awardGroupId":"3","awardName":"奖金组1800","directRet":8,"threeoneRet":0,"status":1,"directLimitRet":7,"threeLimitRet":7}]';
    	$result = Zend_Json::decode($str);
    	} */
    	return $result;
    }
    
    
    //获取绑定宝开安全中心序列号
    public function getSecurityCardNumber($userId){
    	$data =array(
    			"body" =>array("param" => $userId)
    	);
    	$uri = Zend_Registry::get ( "firefog" )->accountsSecurity->getSecurityCardNumber;
    	$result = $this->inRequest ( array_merge($this->_header, $data), $uri );
    	if(isset($result['head']['status']) && $result['head']['status']=='0'){
    		if($result['body']['result']){
    			return $result['body']['result'];
    		}
    	}
    	return NULL;
    }
    
    //解除宝开安全中心令牌绑定
    public function unBindMobileToken($param){
    	$data =array(
    			"body" =>array("param" => $param)
    	);
    	$uri = Zend_Registry::get ( "firefog" )->accountsSecurity->unbindSecurityCard;
    	$result = $this->inRequest ( array_merge($this->_header, $data), $uri );
    	if(isset($result['head']['status']) && $result['head']['status']=='0'){
    		return TRUE;
    	}
    	return FALSE;
    }
    
    /**
     * 读取一代回收用户列表
     * @param type $data
     * @return array
     */
    public function getLevelRecycleUserInfo($param='',$page=1, $size=1)
    {
                 
        $start = ($page-1)*$size;
        if(!empty($param))
        {           
            if(isset($param['username']))
            {
                $type = $param['username'];
            }
            $data =array(
                "body" =>array(
                    "param" => array(
                        'account'  => $type,
                    ),
                "pager" => array(
                    'startNo' => $start,
                    "endNo"=>$start+$size-1
                    )
                )
            );
        }
        $uri = Zend_Registry::get ( "firefog" )->levelRecycleUser->levelRecycleUserlist;
        
        return $this->inRequest ( array_merge($this->_header, $data), $uri );
    }
    
    /**
     * 读取一代回收纪录
     * @param type $data
     * @return array
     */
    public function getLevelRecycleHistoryList($param='',$page=1, $size=1)
    {
                 
        $start = ($page-1)*$size;
        if(!empty($param))
        {           
            if(isset($param['username']))
            {
                $type = $param['username'];
            }
            $data =array(
                "body" =>array(
                    "param" => array(
                        'account'  => $type
                    ),
                "pager" => array(
                    'startNo' => $start,
                    "endNo"=>$start+$size-1
                    )
                )
            );
        }
        $uri = Zend_Registry::get ( "firefog" )->levelRecycleUser->levelRecycleHistorylist;
        
        return $this->inRequest ( array_merge($this->_header, $data), $uri );
    }
    
    /**
     * 检查一代回收密码修改
     * @param type $data
     * @return array
     */
    public function isLevelRecycleFirstLogin($param='')
    {        
        if(!empty($param))
        {           
            if(isset($param['username']))
            {
                $account = $param['username'];
            }
            $data =array(
                "body" =>array(
                    "param" => array(
                        'account'  => $account,
                    )
                )
            );
        }
        $uri = Zend_Registry::get ( "firefog" )->levelRecycleUser->isLevelRecycleFirstLogin;
        
        return $this->inRequest ( array_merge($this->_header, $data), $uri );
    }
    
    /**
     * 送出一代回收申请
     * @param type $data
     */
    public function sendLevelRecycleApply($param)
    {       
        if(!empty($param))
        {            
            $account = $param['account'];
            //$userId = $param['userId'];            
            $data =array(
                "body" =>array(
                    "param" => array(
                        'account'  => $account,
                        'userId' => $param['userId'],
                        'topAgent' => $param['topAgent'],
                        'availBal' => $param['availBal'],
                        'recycleReason' => $param['recycleReason'],
                        'operator' => $param['operator'],
                        'availPtBal' => $param['availPtBal'],
                        'lastLoginDate' => $param['lastLoginDate'],
                        'lastLoginIp' => $param['lastLoginIp'],
                        'lastLoginAddress' => $param['lastLoginAddress']
                    )
                )
            );
        }
        
        $response = '';
        $status = '';
        $message = '';
        //查询追号记录
        $planData =array(
                "body" =>array(
                    "param" => array(
                        'userId' => $param['userId']
                    )
                )
            );
        $uri = Zend_Registry::get ( "firefog" )->levelRecycleUser->checkPlanList;
        $planResult =  $this->inRequest ( array_merge($this->_header, $planData), $uri );
        if($planResult['body']['result']!=null){
            $totalNum  = $planResult['body']['result']['gameOrderResponseDTO']['totalAmount'];
            
            if($totalNum==0){
                //查詢投注紀錄            
                 $uri = Zend_Registry::get ( "firefog" )->levelRecycleUser->sendApplylevelRecycle;
                 $response = $this->inRequest ( array_merge($this->_header, $data), $uri );
                 return $response;
            }else{
                $status = 'FAIL';
                $message = '请先取消進行中的追号';                     
            }       
        }else{
            $status = 'FAIL';
            $message = '查询追号记录失败'; 
        }
        
        $response = array(
           "body" =>array(
               "result" => array(
                   'status'  => $status,
                   'message' => $message
               )
           )
        ); 
        
        return $response;
    }
    
    /**
     * 单独呼叫一代回收功能     
     * * @param type $data
     */
    public function callRecycleAction($param){
         if(!empty($param)){            
            $actionName = $param['actionName'];     
            $data =array(
                "body" =>array(
                    "param" => array(
                        'id' => $param['id'],
                        'account'  => $param['account'],
                        'userId' => $param['userId'],
                        'operator' => $param['operator'],
                        'recycleStatus' => $param['recycleStatus']
                    )
                )
            );
        }
                
        $uri = Zend_Registry::get ( "firefog" )->levelRecycleUser->$actionName;
        
        return $this->inRequest ( array_merge($this->_header, $data), $uri );
    }   
    
}
?>
