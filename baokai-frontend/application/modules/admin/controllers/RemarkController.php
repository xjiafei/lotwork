<?php
class Admin_RemarkController extends Fundcommon {
	
	private $_remarkModel;
	private $_pagesize;
	private $_param;
	private $_page,$_totalPage,$_querySize,$_total;
	function init(){
		parent::init();
		$this->_remarkModel = new Remark();
		$this->_pagesize = 10;
		$this->_page = 1;
		$this->_totalPage = 1;
		$this->_querySize = 2000;
		$this->_total = 0;
	}
	
	public function indexAction(){
		$this->_param = getSecurityInput($this->_request->get("parma"));
		$aParam = array(
				"sv1"=>"displayManagerList",//唯一附言管理
				"sv2"=>"displayRecycleList",//附言回收管理
				"sv3"=>"remarkSetting",//附言配置管理
				"sv4"=>"cancleRemark",//解除绑定
				"sv5"=>"cancleLock",//解除锁定
				"sv6"=>"downManagerList",//下载附言管理列表
				"sv7"=>"downRecycleList",//下载附言回收管理
				"sv8"=>"saveRemarkSetting",//保存配置
				"sv9"=>"recycleRemark",//回收附言
				"sv10"=>"importUser",//回收附言
				"sv11"=>"remarkManagerList",//加载唯一附言管理数据
				"sv12"=>"remarkRecycleList",//加载唯一附言回收管理数据
		);
		if(array_key_exists($this->_param,$aParam) && array_key_exists($this->_param, $this->_aAclArray)){
				$this->$aParam[$this->_param]($this->_request);
				exit;
		}elseif(isAjaxRequest()){
			echo Zend_Json::encode(array('status'=>'error','data'=>$this->getError('102144')));
			exit;
		}else {
			foreach ($this->_aAclArray as $key=>$value){
				if(array_key_exists($key,$aParam)){
					$this->$aParam[$key]($this->_request);
					exit;
				}
			}
			$this->_redirect('/admin/Rechargemange/');
		}
	}
	public function displayManagerList(){
		$this->view->title   = '唯一附言管理';
		$this->view->display('/admin/funds/recharge/remark_manager.tpl');
	}
	
	public function displayRecycleList(){
		$this->view->title   = '唯一附言回收管理';
		$this->view->display('/admin/funds/recharge/remark_recycle.tpl');
	}
	//唯一附言管理首页
	public function remarkManagerList(){
		$param 	 = array();
		$userName 	= getSecurityInput(trim($this->_request->getPost('userName','')));
		$AgentName 	= getSecurityInput(trim($this->_request->getPost('AgentName','')));
		$remarkName = getSecurityInput(trim($this->_request->getPost('remarkName','')));
		$userType 	= getSecurityInput($this->_request->getPost('userType',''));
		$bindStatTime = getSecurityInput($this->_request->getPost('bindDate1',''));
		$bindEndTime  = getSecurityInput($this->_request->getPost('bindDate2',''));
		$status  	  = getSecurityInput($this->_request->getPost('status',''));
		$pageNum = intval(getSecurityInput($this->_request->getPost('page',1)));
		if(!empty($userName)){
			$param['account'] = strtolower($userName);
		}
		if(!empty($AgentName)){
			$param['topAccount'] = strtolower($AgentName);
		}
		if(!empty($remarkName)){
			$param['remark'] = strtolower($remarkName);
		}
		if($userType!=''){
			$param['vipLvl'] = intval($userType);
		}
		if(!empty($bindStatTime)){
			$param['startBindDate'] = getQueryStartTime($bindStatTime);
		}
		if(!empty($bindEndTime)){
			$param['endBindDate'] = getQueryEndTime($bindEndTime);
		}
		if($status!=''){
			$param['isFreeze'] = intval($status);
		}
		$result = $this->_remarkModel->getAllRemarks($param,$pageNum,$this->_pagesize);
		$total = $result['pager']['total'];
		echo Zend_Json::encode(array('text'=>$result['result'],'count'=>array('recordNum'=>$total)));
		exit;
	}
	
	//唯一附言回收管理
	public function remarkRecycleList(){
		$param 	 = array();
		$userName 	= getSecurityInput(trim($this->_request->getPost('userName','')));
		$remarkName = getSecurityInput(trim($this->_request->getPost('remarkName','')));
		$bindStatTime = getSecurityInput($this->_request->getPost('bindDate1',''));
		$bindEndTime  = getSecurityInput($this->_request->getPost('bindDate2',''));
		$pageNum = intval(getSecurityInput($this->_request->getPost('page',0)));
		
		if(!empty($userName)){
			$param['account'] = strtolower($userName);
		}
		if(!empty($remarkName)){
			$param['remark'] = strtolower($remarkName);
		}
		if(!empty($bindStatTime)){
			$param['bindStatTime'] =getQueryStartTime($bindStatTime);
		}
		if(!empty($bindEndTime)){
			$param['bindEndTime'] = getQueryEndTime($bindEndTime);
		}
		
		$result = $this->_remarkModel->getAllRecyleRemarks($param,$pageNum,$this->_pagesize);
		$total = $result['pager']['total'];
		echo Zend_Json::encode(array('text'=>$result['result'],'count'=>array('recordNum'=>$total)));
		exit;
	}
	
	//唯一附言配置管理
	public function remarkSetting(){
		$days = $this->_remarkModel->getModifiedDays();
		$this->view->days=$days;
		$this->view->title   = '配置管理';
		$this->view->display('/admin/funds/recharge/remark_setting.tpl');
	}
	
	//解除绑定
	public function cancleRemark(){
		$userId = getSecurityInput(trim($this->_request->getPost('userid','')));
		$status = $this->_remarkModel->cancleRemark($userId);
		if($status){
			echo Zend_Json::encode(array('status'=>1,'data'=>'解除绑定成功'));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>0,'data'=>'解除绑定失败'));
			exit;
		}
	}
	
	//解除锁定
	public function cancleLock(){
		$userId = getSecurityInput(trim($this->_request->getPost('userid','')));
		$status = $this->_remarkModel->cancleLock($userId);
		if($status){
			echo Zend_Json::encode(array('status'=>1,'data'=>'解除用户锁定成功'));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>0,'data'=>'解除用户锁定失败'));
			exit;
		}
	}

	//下载附言管理列表
	public function downManagerList(){
		$param 	= array();
		$userName 	= getSecurityInput(trim($this->_request->get('userName','')));
		$AgentName 	= getSecurityInput(trim($this->_request->get('AgentName','')));
		$remarkName = getSecurityInput(trim($this->_request->get('remarkName','')));
		$userType 	= getSecurityInput($this->_request->get('userType',''));
		$bindStatTime = getSecurityInput($this->_request->get('bindStartTime',''));
		$bindEndTime  = getSecurityInput($this->_request->get('bindEndTime',''));
		$status  = getSecurityInput($this->_request->get('status',''));
		if(!empty($userName)){
			$param['account'] = $userName;
		}
		if(!empty($AgentName)){
			$param['topAccount'] = $AgentName;
		}
		if(!empty($remarkName)){
			$param['remark'] = $remarkName;
		}
		if($userType!=''){
			$param['vipLvl'] = intval($userType);
		}
		if(!empty($bindStatTime)){
			$param['startBindDate'] = getQueryStartTime($bindStatTime);
		}
		if(!empty($bindEndTime)){
			$param['endBindDate'] = getQueryEndTime($bindEndTime);
		}
		if($status!=''){
			$param['isFreeze'] = intval($status);
		}
		$fileName = '附言管理列表';
		$aTitle = array(
			'account' => '用户名',
			'topAgent' => '所属总代',
			'userType' => '用户类型',
			'gmt_modified' => '绑定时间',
			'remark' => '唯一附言 ',
			'status' => '用户状态'
		);
		$aContent = array();
		$startTime= microtime(true);
		do {
			$data["pager"]["startNo"] = ($this->_page-1)*$this->_querySize+1;
			$data["pager"]["endNo"] = $this->_page*$this->_querySize;
			$result = $this->_remarkModel->getAllRemarks($param,-1);
			if(isset($result['result']) && count($result['result'])>0){
				foreach ($result['result'] as $key=>$value){
					foreach ($aTitle as $sub_key=>$sub_value){
						if($sub_key == 'userType'){
							$aContent[$key][$sub_key] = $value[$sub_key]>0 ? 'VIP用户' : '普通用户';
						} else if($sub_key == 'status'){
							$aContent[$key][$sub_key] = $value[$sub_key] =='0' ? '正常' : '锁定';
						} else {
							$aContent[$key][$sub_key] = $value[$sub_key];
						}
					}
				}
				$this->_totalPage = ceil($result['pager']['total']/$this->_querySize);
				$this->_total     = $result['pager']['total'];
			}
			if($this->_totalPage<=0){
				$this->_totalPage = 1;
			}
			$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$this->_page);
			$aContent = array();
		} while ($this->_page++ !=$this->_totalPage);
		
		$endTime = microtime(true);
		$diffTime = floor($endTime - $startTime);
		$modata = array();
		array_push($aContent,$modata);
		$modata['account']  = '下载数据:'.$this->_total.'条.';
		$modata['topAgent'] = '总耗时:'.$diffTime.'秒';
		array_push($aContent,$modata);
		$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$this->_page);
	}
	
	//下载附言回收列表
	public function downRecycleList(){
		$param 	= array();
		$userName 	= getSecurityInput(trim($this->_request->get('userName','')));
		$remarkName = getSecurityInput(trim($this->_request->get('remark','')));
		$bindStatTime = getSecurityInput($this->_request->get('fromDate',''));
		$bindEndTime  = getSecurityInput($this->_request->get('toDate',''));
		
		if(!empty($userName)){
			$param['account'] = $userName;
		}
		if(!empty($remarkName)){
			$param['remark'] = $remarkName;
		}
		if(!empty($bindStatTime)){
			$param['bindStatTime'] =getQueryStartTime($bindStatTime);
		}
		if(!empty($bindEndTime)){
			$param['bindEndTime'] = getQueryEndTime($bindEndTime);
		}
		
		$result = $this->_remarkModel->getAllRecyleRemarks($param,-1);
		$fileName = '附言回收列表';
		$aTitle = array(
				'remark' => '唯一附言',
				'userName' => '最后绑定用户',
				'createTime' => '解绑时间'
		);
		$aContent = array();
		$startTime= microtime(true);
		do {
			$data["pager"]["startNo"] = ($this->_page-1)*$this->_querySize+1;
			$data["pager"]["endNo"] = $this->_page*$this->_querySize;
			$result = $this->_remarkModel->getAllRemarks($param,-1);
			if(isset($result['result']) && count($result['result'])>0){
				foreach ($result['result'] as $key=>$value){
					foreach ($aTitle as $sub_key=>$sub_value){
						$aContent[$key][$sub_key] = $value[$sub_key];
					}
				}
				$this->_totalPage = ceil($result['pager']['total']/$this->_querySize);
				$this->_total     = $result['pager']['total'];
			}
			if($this->_totalPage<=0){
				$this->_totalPage = 1;
			}
			$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$this->_page);
			$aContent = array();
		} while ($this->_page++ !=$this->_totalPage);
		
		$endTime = microtime(true);
		$diffTime = floor($endTime - $startTime);
		$modata = array();
		array_push($aContent,$modata);
		$modata['remark']  = '下载数据:'.$this->_total.'条.';
		$modata['userName'] = '总耗时:'.$diffTime.'秒';
		array_push($aContent,$modata);
		$this->downLoadDataToCSV($fileName,$aTitle,$aContent,$this->_sessionlogin->info['account'],$this->_page);
	}
	
	//保存配置
	public function saveRemarkSetting(){
		$days = intval(getSecurityInput($this->_request->getPost('days')));
		$status = $this->_remarkModel->setCanModifiedDays($days);
		if($status){
			echo Zend_Json::encode(array('isSuccess'=>1,'msg'=>'保存配置成功'));
			exit;
		} else {
			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>'保存配置失败'));
			exit;
		}
	}
	
	//回收附言
	public function recycleRemark(){
		$id = intval(getSecurityInput($this->_request->getPost('id')));
		$remark = getSecurityInput($this->_request->getPost('remark'));
		$status = $this->_remarkModel->recyleRemark($id,$remark);
		if($status){
			echo Zend_Json::encode(array('status'=>1,'data'=>'回收成功'));
			exit;
		} else {
			echo Zend_Json::encode(array('status'=>0,'data'=>'回收失败'));
			exit;
		}
	}
	
	
	//导入用户
	public function importUser(){
		

		$adapter = new Zend_File_Transfer_Adapter_Http();
		$pics = array();
		$upload_dir = $this->_config->upload_dir.'/upload/';
		if (!is_dir($upload_dir)) {
			return 0;
		}
		$format = array('xls','xlsx');
		$adapter->addValidator('Extension', false, implode(",", $format));
		$adapter->addValidator('FilesSize', false, array (
				'min' => '1kB',
				'max' => '2MB'
		));
		$adapter->setDestination($upload_dir);//存放上传文件的文件夹
		$fileInfo = $adapter->getFileInfo();
		$fname = $fileInfo['file']['name'];
		//$fname=iconv("utf-8","gbk",$fname);
		if($adapter->isUploaded($fname) && $adapter->isValid($fname)){
			$adapter->receive($fname);
		} else {
			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>'上传失败!'));
			exit;
		}
		
		$file = $upload_dir.'/'.$fname;
		if(!file_exists($file)){
			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>'文件存在!'));
			exit;
		}
		$datasReader = PHPExcel_IOFactory::load($file);
		$sheets = $datasReader->getAllSheets();
		//如果有多个工作簿
		$countSheets = count($sheets);
		$sheetsinfo = array();
		$sheetData = array();
		if($countSheets==1){
			$sheet = $sheets[0];
			$sheetsinfo["rows"] = $sheet->getHighestRow();
			$sheetsinfo["column"] = PHPExcel_Cell::columnIndexFromString($sheet->getHighestColumn());
			for($row=1;$row<=$sheetsinfo["rows"];$row++){
				//读多列
				/* for($column=0;$column<$sheetsinfo["column"];$column++){
					$sheetData[$row][$column] = $sheet->getCellByColumnAndRow($column, $row)->getValue();
				} */
				//读单列
				$sheetData[$row] = $sheet->getCellByColumnAndRow(0, $row)->getValue();
			}
		}else{
			foreach ($sheets as $key => $sheet){
				$sheetsinfo[$key]["rows"] = $sheet->getHighestRow();
				$sheetsinfo[$key]["column"] = PHPExcel_Cell::columnIndexFromString($sheet->getHighestColumn());
				for($row=1;$row<=$sheetsinfo[$key]["rows"];$row++){
					//读多列
					/* for($column=0;$column<$sheetsinfo[$key]["column"];$column++){
						$sheetData[$key][$row][$column] = $sheet->getCellByColumnAndRow($column, $row)->getValue();
					} */
					//读单列
					$sheetData[$key][$row] = $sheet->getCellByColumnAndRow(0, $row)->getValue();
				}
			}
		}
		array_shift($sheetData);
		unlink($file);
		
		$status = $this->_remarkModel->importUser($sheetData);
		if(!$status){
			echo Zend_Json::encode(array('isSuccess'=>1,'msg'=>'恭喜您,文件已经上传成功!'));
			exit;
		} else {
			echo Zend_Json::encode(array('isSuccess'=>0,'msg'=>'写入数据库失败,请重新上传!'));
			exit;
		}
	}
}