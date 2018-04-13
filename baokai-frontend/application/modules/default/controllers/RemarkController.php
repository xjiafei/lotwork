<?php
class Default_RemarkController extends Fundcommon {

	private $_remarkModel;
	private $_remarkTime;
	private $_sysRemark;
	private $_remarkName;
	public function init(){
		//屏蔽附言功能 left左边链接,充值页面的唯一附言链接,还有唯一附言设置的入口跳转
		$this->_redirect('/index/');
		parent::init();
		$this->_errView = 'ucenter';
		$this->_remarkModel = new Remark();
		$this->_remarkTime = 'lastCreateTime';
		$this->_remarkName = 'systemRemark';
		//设置获取系统附言频率 时间 30S每次
		$this->_sysRemark = new Rediska_Key_Hash(md5('systemRemark'.$this->_sessionlogin->info['id']));
	}
	
	//唯一附言首页
	public function indexAction(){
		$remark = '';
		$modfiedDays = $this->_remarkModel->getModifiedDays();
		$result = $this->_remarkModel->getUserRemark($this->_sessionlogin->info['id']);
		if(empty($result['gmtCansetremark']) || getSrtTimeByLong($result['gmtCansetremark'])-time()<=0 || $modfiedDays==0){
			$cansetremark = '1';
		} else {
			$cansetremark = '0';
		}
		if(isset($result['remark']) && !empty($result['remark'])){
			$remark = $result['remark'];
		}
// 		echo date('Y-m-d H:i:s',getSrtTimeByLong($result['gmtCansetremark']));
// 		echo "<br/>";
// 		echo date('Y-m-d H:i:s',getSrtTimeByLong($result['gmtModified']));
		$this->view->cansetremark = $cansetremark;
		$this->view->cancleDate = isset($result['gmtModified']) ? date('Y/m/d',getSrtTimeByLong($result['gmtModified'])) : '';
		$this->_sessionlogin->info['remark'] = $remark;
		$this->_sessionlogin->info['modfiedDays'] = $modfiedDays;
		$this->_sessionlogin->info['remarkModified'] = isset($result['gmtModified']) ? $result['gmtModified'] : '';
		$this->_sessionlogin->info['remarkCanSetRemark'] = isset($result['gmtCansetremark']) ? $result['gmtCansetremark'] : '';
		$this->view->remark = $remark;
		$this->view->modfiedDays = $modfiedDays;
		$this->view->title = "唯一附言管理";
		$this->view->display('default/ucenter/fund/remark_index.tpl');
	}
	
	
	//获取系统唯一附言
	public function getsystemremarkAction(){
		$lastCreateTime = $this->_sysRemark->exists($this->_remarkTime) ? $this->_sysRemark->get($this->_remarkTime) : 0;
		if(time()-$lastCreateTime<30){
			echo Zend_Json::encode(array('isSuccess'=>0,'data'=>array('time'=>time()-$lastCreateTime,'code'=>$this->_sysRemark->get($this->_remarkName))));
			exit;
		} else {
			$newRemark = $this->_remarkModel->getNextSystemRemark($this->_sessionlogin->info['id']);
			if($newRemark){
				$this->_sysRemark->set($this->_remarkTime,time());
				$this->_sysRemark->set($this->_remarkName,$newRemark);
				echo Zend_Json::encode(array('isSuccess'=>1,'data'=>array('code'=>$newRemark)));
				exit;
			} else {
				echo Zend_Json::encode(array('isSuccess'=>-1,'data'=>$this->getError('102118')));
				exit;
			}
		}
	}
	
	//检测用户自定义附言是否已经存在
	public function checkremarkexistAction(){
		$remarkName = strtolower(getSecurityInput(trim($this->_request->getPost('code',''))));
		if(empty($remarkName)){
			echo Zend_Json::encode(array('status'=>0,'data'=>$this->getError('102095')));
			exit;
		}
		$isEnable = $this->_remarkModel->checkremarkexist($remarkName);
		if($isEnable==false){
			echo Zend_Json::encode(array('isSuccess'=>1,'msg'=>'附言可用'));
			exit;
		} else {
			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102096')));
			exit;
		}
	}
	
	//保存唯一附言
	public function saveremarkAction(){
		$remarkName = strtolower(getSecurityInput(trim($this->_request->getPost('remarkName',''))));
		$type = getSecurityInput(trim($this->_request->getPost('type','')));
		if(empty($remarkName)){
			echo Zend_Json::encode(array('status'=>0,'data'=>$this->getError('102095'),'type'=>$type));
			exit;
		}
		if($type ==1){
			if(!preg_match("/^[0-9a-zA-Z]{5}$/",$remarkName)){
				echo Zend_Json::encode(array('status'=>0,'data'=>$this->getError('102099'),'type'=>$type));
				exit;
			}
		} else if(!preg_match("/^[0-9a-zA-Z]{6,16}$/",$remarkName)){
			echo Zend_Json::encode(array('status'=>0,'data'=>$this->getError('102097'),'type'=>$type));
			exit;
		}
		
		if(isset($this->_sessionlogin->info['remark'])){
			if(!empty($this->_sessionlogin->info['remark'])){
				echo Zend_Json::encode(array('isSuccess'=>0,'data'=>$this->getError('102115')));
				exit;
			}
		} /* else {
			echo Zend_Json::encode(array('isSuccess'=>0,'data'=>$this->getError('102098')));
			exit;
		} */
		if($this->_sessionlogin->info['modfiedDays']!=0 && !empty($this->_sessionlogin->info['remarkCanSetRemark']) && getSrtTimeByLong($this->_sessionlogin->info['remarkCanSetRemark'])-time()>0){
			$errStr = str_replace('30', $this->_sessionlogin->info['modfiedDays'], $this->getError('102117'));
			echo Zend_Json::encode(array('isSuccess'=>0,'data'=>$errStr));
			exit;
		}
		//检查附言是否可用
		$isEnable = $this->_remarkModel->checkremarkexist($remarkName);
		if($isEnable==true){
			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102096')));
			exit;
		}
		$status = $this->_remarkModel->saveRemark($this->_sessionlogin->info['id'],$remarkName);
		if($status){
			$this->_sessionlogin->info['remark'] = $remarkName;
			$this->_sessionlogin->info['remarkModified'] = getSendTime();
			echo Zend_Json::encode(array('isSuccess'=>1));
			exit;
		} else {
			echo Zend_Json::encode(array('isSuccess'=>0,'data'=>$this->getError('102098'),'type'=>$type));
			exit;
		}
	}
	//修改唯一附言
	public function editremarkAction(){
		$remarkName = strtolower(getSecurityInput(trim($this->_request->getPost('remarkName',''))));
		$type = getSecurityInput(trim($this->_request->getPost('type','')));
		if(empty($remarkName)){
			echo Zend_Json::encode(array('status'=>0,'data'=>$this->getError('102095'),'type'=>$type));
			exit;
		}
		if($type ==1){
			if(!preg_match("/^[0-9a-zA-Z]{5}$/",$remarkName)){
				echo Zend_Json::encode(array('status'=>0,'data'=>$this->getError('102099'),'type'=>$type));
				exit;
			}
		} else if(!preg_match("/^[0-9a-zA-Z]{6,16}$/",$remarkName)){
			echo Zend_Json::encode(array('status'=>0,'data'=>$this->getError('102097'),'type'=>$type));
			exit;
		}
		
		//检测 用户是否有修改唯一附言的 权限,修改时间 和是否已经设置附言
		if(isset($this->_sessionlogin->info['remark'])){
			if(empty($this->_sessionlogin->info['remark'])){
				echo Zend_Json::encode(array('isSuccess'=>0,'data'=>$this->getError('102114')));
				exit;
			} else if($this->_sessionlogin->info['modfiedDays']!=0 && time() - getSrtTimeByLong($this->_sessionlogin->info['remarkCanSetRemark'])<0){
				$errStr = str_replace('30', $this->_sessionlogin->info['modfiedDays'], $this->getError('102113'));
				echo Zend_Json::encode(array('isSuccess'=>0,'data'=>$errStr));
				exit;
			}
		} else {
			echo Zend_Json::encode(array('isSuccess'=>0,'data'=>$this->getError('102098')));
			exit;
		}
		//检查 附言是否可用
		$isEnable = $this->_remarkModel->checkremarkexist($remarkName);
		if($isEnable==true){
			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102096')));
			exit;
		}
		$status = $this->_remarkModel->editRemark($this->_sessionlogin->info['id'],$remarkName);
		if($status){
			$this->_sessionlogin->info['remark'] = $remarkName;
			$this->_sessionlogin->info['remarkModified'] = getSendTime();
			echo Zend_Json::encode(array('isSuccess'=>1));
			exit;
		} else {
			echo Zend_Json::encode(array('isSuccess'=>0,'data'=>$this->getError('102098'),'type'=>$type));
			exit;
		}
	}
	
	//取消唯一附言
	public function delremarkAction(){
		//检测 用户是否有取消唯一附言的 权限,修改时间 和是否已经设置附言
		if(empty($this->_sessionlogin->info['remark'])){
			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102116')));
			exit;
		}
		$status = $this->_remarkModel->delRemark($this->_sessionlogin->info['id']);
		if($status){
			unset($this->_sessionlogin->info['remark']);
			$this->_sessionlogin->info['remarkCanSetRemark'] = time()+$this->_sessionlogin->info['modfiedDays']*86400;
			echo Zend_Json::encode(array('isSuccess'=>1));
			exit;
		} else {
			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>$this->getError('102116')));
			exit;
		}
	}
	
}