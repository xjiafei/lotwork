<?php defined('BASEPATH') OR exit('No direct script access allowed');

class BetMgr {

	function __construct()
	{
		
	}
	
	public static function getMethodWith3($chan_id_3, $lot_id_3, $method_id_3)
	{
		$methods = BetMgr::_getMethodList();
		$method = array();
		
		foreach($methods as $key => $value) 
		{
			if($value["3_chan_id"] == $chan_id_3 && $value["3_lot_id"] == $lot_id_3 && $value["3_method_id"] == $method_id_3 )
			{
				$method = $value;
				break;
			}
		}
		return $method;
	}
	
	public static function getMethodWith4($lot_id_4, $method_id_4)
	{
		$methods = BetMgr::_getMethodList();
		$method = array();
		
		foreach($methods as $key => $value) 
		{
			if($value["4_lot_id"] == $lot_id_4 && $value["4_method_id"] == $method_id_4 )
			{
				$method = $value;
				break;
			}
		}
		return $method;
	}
	
	public static function getLotteryWith3($chan_id_3, $lot_id_3)
	{
		$lotterys = BetMgr::_getLotteryList();
		$lottery = array();
		
		foreach($lotterys as $key => $value) 
		{
			if($value["3_chan_id"] == $chan_id_3 && $value["3_lot_id"] == $lot_id_3)
			{
				$lottery = $value;
				break;
			}
		}
		return $lottery;
	}
	
	public static function getLotteryWith4($lot_id_4)
	{
		$lotterys = BetMgr::_getLotteryList();
		$lottery = array();
		
		foreach($lotterys as $key => $value) 
		{
			if($value["4_lot_id"] == $lot_id_4)
			{
				$lottery = $value;
				break;
			}
		}
		return $lottery;
	}
	
	private static function _getMethodList()
	{
		// cache app_info
		$CI =& get_instance();
		$CI->load->model('Mem_cache_model');
		$rtn = $CI->Mem_cache_model->get(MC_METHOD_LIST);
		if($rtn == null)
		{
			$CI->load->model('Method_model');
			$rtn = $CI->Method_model->getMethodList();
			
			$CI->Mem_cache_model->save(MC_METHOD_LIST, $rtn, MC_METHOD_LIST_CACHE_TIME);
		}
		return $rtn;
	}
	
	
	private static function _getLotteryList()
	{
		// cache app_info
		$CI =& get_instance();
		$CI->load->model('Mem_cache_model');
		$rtn = $CI->Mem_cache_model->get(MC_LOTTERY_LIST);
		if($rtn == null)
		{
			$CI->load->model('Lottery_model');
			$rtn = $CI->Lottery_model->getLotteryList();
			
			$CI->Mem_cache_model->save(MC_LOTTERY_LIST, $rtn, MC_LOTTERY_LIST_CACHE_TIME);
		}
		return $rtn;
	}
}


/* End of file BetMgr.php */
/* Location: ./application/libraries/BetMgr.php */