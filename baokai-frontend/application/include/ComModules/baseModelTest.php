<?php
//version 1.3 还有优化空间
/* include_once("Services/json.php");
include_once("Services/jsonpath.php");


该module 已经废弃，仅做测试用 使用 BaseModel.php
 */
interface fireFogSystemFace {
	function set($head);
	function get();
}
class baseModelTest    {
	private $client = null ; 
	private  $_sessionlogin;
	
 	public function __construct(){
 		$this->_sessionlogin = new Zend_Session_Namespace('datas') ;
 	}
 	public function getUserSessionId(){
 		$id = $this->_sessionlogin->info["id"] ;
 		return $id?$id:0;
 	}

	private function isPutRequest(){
		try {
			$this->__set("body", $this->BRequest);
			return true ;
		}catch (Exception $e){
			return false ;
		}
	}
		
	public  function __set($property_name,$value){
	
		$this->$property_name=$value;
	
	}
	
	public function __unset($property_name)
	{
		unset($this->$property_name);
	}
	private function putClientRequest($data){	
		$response = null ;
		$this->client = new Client();
		if(isset($data->{'BRequest'})&&isset($data->{'bodyResponse'})){
			unset($data->{'BRequest'}) ;
			unset($data->{'bodyResponse'}) ;
		}
		$urls = require 'application/urls.php' ;
		if(array_key_exists(get_class($this),$urls)){
				$url = $urls[get_class($this)] ;//还要加异常处理
				var_dump(json_encode($data));
				$response = $this->client->inRequest($data, $url,true);
		}
		return $response ;
	}
	
	function searchMultiArray(array $array, $search, $mode = 'key') {
		$res = array();
		foreach (new RecursiveIteratorIterator(new RecursiveArrayIterator($array)) as $key => $value) {
			if ($search === ${
				${
					"mode"}
			}){
				if($mode == 'key'){
					
					$res[] = $value;
				}else{
					$res[] = $key;
				}
			}
		}
		return $res;
	}
	
	function getArray($array, $index) { //根据键名称提取键值
		if (!is_array($array)) return null;
		if (isset($array[$index])) return $array[$index];
		foreach ($array as $item) {
			$return = $this->getArray($item, $index);
			if (!is_null($return)) {
				return $return;
			}
		}
		return null;
	}
	
	function array_multi2single($array)
	{
		static $result_array=array();
		foreach($array as  $varname => $value)
		{
			if(is_array($value))
			{
				$this->array_multi2single($value);
			}
			else
				$result_array[$varname]=$value;
		}
		return $result_array;
	}
	
	function find_array_key($find, $src_array)
	{
		if (is_array($src_array))
		{
			if (isset($src_array[$find]))
			{
				return $src_array[$find];
			}
			else
			{
				foreach ($src_array as $key => $value)
				{	
					if (is_array($value))
					{
						return $this->find_array_key($find, $value);
					}
				}
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	function array_search_re($needle, $haystack, $a=0, $nodes_temp=array()){
		global $nodes_found;
		$a++;
		foreach ($haystack as $key1=>$value1) {
			$nodes_temp[$a] = $key1;
			if (is_array($value1)){
				$this->array_search_re($needle, $value1, $a, $nodes_temp);
			}
			else if ($value1 === $needle){
				$nodes_found[] = $nodes_temp;
			}
		}
		return $nodes_found;
	}
	
	public function load(){
		$return_array = array();
		$tmp = $this->bodyResponse;
			$arry = $this->putClientRequest($this->BRequest) ;
			$aray = $this->array_multi2single($arry["body"]) ;			
			foreach (array_keys($tmp) as $varname => $varvalue) {
				//printf("%s: ~~ %s<br/>",json_encode( $aray[$varvalue]),$varvalue);
				//printf("%s: ~~ <br/>",array_key_exists($varvalue,$aray));
				if(!array_key_exists($varvalue,$aray)){
					array_push($return_array,$varvalue) ;
				}
			}
			//print_r(json_encode($arry["body"]["result"]));
			//$result = $this->array_search_re(34, $arry["body"]);
			//print_r(json_encode($result));
			
			return array("backData"=>$arry, "lost"=>$return_array) ;
	}	
}