<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Method_model extends CI_Model {
	
	private $mTableName = "method";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function getMethodList()
	{
		$rs = $this->db->query("SELECT *"
					. " FROM  `" . $this->mTableName . "` ");
		return $rs->result_array();
	}
	
	function getLotteryJoinMethod4()
	{
		$rs = $this->db->query("SELECT L.3_chan_id AS chan_id, L.4_lot_id AS lot_id, L.lot_name, S.s_id, S.s_name, M.4_method_id AS method_id, M.method_name, M.4_maxmultiple AS maxmultiple"
					. " FROM  `" . $this->mTableName . "` AS M"
					. " INNER JOIN  `lottery` AS L ON L.lot_id = M.lot_id"
					. " INNER JOIN  `subclass` AS S ON M.s_id = S.s_id"
					. " WHERE M.enable =1"
					. " AND 4_method_id !=  '0'"
					. " ORDER BY L.sort,L.3_chan_id, L.4_lot_id, S.sort, M.sort");
		return $rs->result_array();
	}
	
	function getMethodListOfPlatform3()
	{
		$rs = $this->db->query("SELECT `3_chan_id` AS chan_id, `3_lot_id` AS lottery_id, `3_method_id` AS method_id, method_name"
					. " FROM  `" . $this->mTableName . "` ");
		return $rs->result_array();
	}
	
	function getMethodListOfPlatformAdmin()
	{
		$rs = $this->db->query("SELECT `3_chan_id` AS chan_id, `lot_id` AS lottery_id, `method_id` AS method_id, method_name"
					. " FROM  `" . $this->mTableName . "` ");
		return $rs->result_array();
	}
	
	function getMethodListOfPlatform4()
	{
		$rs = $this->db->query("SELECT `3_chan_id` AS chan_id, `4_lot_id` AS lottery_id, `4_method_id` AS method_id, method_name"
					. " FROM  `" . $this->mTableName . "` "
					. " WHERE 4_method_id != '0'");
		return $rs->result_array();
	}
	
	function getMethodidWithPlatform3($chan_id, $lottery_id, $method_id)
	{
		$rtn = 0;
		$sql = "SELECT `method_id`"
			. " FROM  `" . $this->mTableName . "` "
			. " WHERE `3_chan_id` = ? AND `3_lot_id` = ? AND `3_method_id` = ?;";
					
		$rs = $this->db->query($sql, array($chan_id, $lottery_id, $method_id));
		if($rs->num_rows() > 0)
		{
			$rtn = $rs->row()->method_id;
		}		
		return $rtn;
	}
}
