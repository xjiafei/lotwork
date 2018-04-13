<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Lottery_model extends CI_Model {
	
	private $mTableName = "lottery";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function getLotteryList()
	{
		$rs = $this->db->query("SELECT *"
					. " FROM  `" . $this->mTableName . "`");
		return $rs->result_array();
	}
	
	function getLotteryList4()
	{
		$rs = $this->db->query("SELECT 3_chan_id AS chan_id, 4_lot_id AS lot_id, lot_name"
					. " FROM  `" . $this->mTableName . "`"
					. " ORDER BY chan_id, sort");
		return $rs->result_array();
	}
	
}
