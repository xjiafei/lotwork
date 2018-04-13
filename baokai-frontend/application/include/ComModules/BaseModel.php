<?php
//注：暂时支持解析的数据以数组格式输入

class BaseModel   {
	
	protected $_artDefaultMap;
	protected $is_load = false;
	protected $arry = array();
	protected $element = array();
	protected $DefaultMap = array();
	protected $data = array() ;
	private $_rowobj ;

	public function __construct($element = null/* ,$data=null,$url=null */){
		
		
		$this->_artDefaultMap = $this->getDefaultMap();
		$this->element = $element ;
		///////////////////////////////////////////////
		if (!$element){
 			if(method_exists($this,'getTestData')){
				$this->data = require $this->getTestData() ; //开发测试数据，上线必须注释掉
			} 
		///////////////////////////////////////////////
		if(array_key_exists("body",$this->data)){
			$this->data = $this->data['body']; //要提取body体中的数据
		}
		}
	}
	private function load(){
		
		if ($this->element){
			$this->data = $this->element ;
		}
		if($this->data){
			foreach($this->data as $k1 => $v1){
				$member = $k1;
				if(!property_exists($this,$member)){
					if(is_numeric($member)){
// 						eval('$this->'.$member.' = '.$v1.';');
						$this->$member = $v1;
					}elseif(is_array($v1)){
// 						eval('$this->$member = $v1 ;');
						$this->$member = $v1;
					}else{
// 						eval('$this->'.$member.' = "'.$v1.'";');
						$this->$member = $v1;
					}
				}
			}	
		}
		$this->is_load = true;
	}
	public function setMember($key,$val){
		if(!property_exists($this,$key)){
			if(is_numeric($val)){
// 				eval(' $this->'.$key.' = '.$val.';');
				$this->$key = $val;
				
			}elseif(is_object($val)){
// 				eval('$this->$key = $val ;');
				$this->$key = $val;
			}else{
// 				eval(' $this->'.$key.' = "'.($val).'";');
				$this->$key = $val;
			}
		}else{
			return false;
		}
	}

	public function getMember($key){
		if(!$this->is_load){
			$this->load();
		}
		if(property_exists($this,$key)){
// 			eval('$res = $this->'.$key.';' );
			$res = $this->$key;
			return $this->$key;
		}else{//属性不存在
			if(array_key_exists($key, $this->_artDefaultMap)){
				return $this->_artDefaultMap[$key] ;
			}else{
				//throw new FireFogException('非标准接口参数:'.$key) ;
				return false ;
			}
		}
		return false;
	}
	public function save($turl= null){
		//tmp 要传到java的数据 ，$turl java提供的接口
		//$head = new Header();
		$tmps=json_decode(json_encode($this),true);
		//$tmps["head"]= json_decode(json_encode($head->getDefaultMap()),true);
		if($turl){
			if(is_array($turl)){
// 				eval('$url = Zend_Registry::get ("firefog" )->'.key($turl).'->'.current($turl).' ;' );
				$url = Zend_Registry::get ("firefog" )->key($turl)->current($turl);
			}elseif(is_string($turl)){
// 				eval('$url = Zend_Registry::get ("firefog" )->$turl ;' );
				$url = Zend_Registry::get ("firefog" )->$turl;
			}else{
				//throw new FireFogException('接口url错误') ;
				return array();
			}
		}else{
			//throw new FireFogException('url为空') ;
			return array();
		}
		if($tmps && is_string($url)){
			$cilent = new Client();
			$this->_rowobj = $cilent->inRequestV2($tmps, $url);
			if(is_array($this->_rowobj)){
				return array($this->_rowobj["head"], $this->_rowobj["body"]);
			}else{
				//throw new FireFogException('服务器返回数据出错') ;
				return array();
			}
		}else{
				
			return array();
		
		}
		
		
	}
}