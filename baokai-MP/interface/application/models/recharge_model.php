<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Recharge_model extends CI_Model {
	
	private $mTableName = "recharge";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function addRecharge($bankId, $money, $url)
	{
		$this->db->set('bankId'		, $bankId);
		$this->db->set('money'		, $money);
		$this->db->set('time'		, date("Y-m-d H:i:s"));
		$this->db->set('url'		, $url);
		
		$this->db->insert($this->mTableName);
		return $this->db->affected_rows();
	}
	
	function getMonthlyReport($data)
	{
		$end = $data["end"]!=null?$data["end"]:date('Y-m-d', time());
		$start = $data["start"]!=null?$data["start"]:date('Y-m-d', strtotime(date("Y-m-d", strtotime($end)) . " -1 month"));
		
		$sql = "SELECT COUNT(*) AS num,SUM(money) AS total, DATE_FORMAT(time, '%Y-%m-%d') AS time"
			. " FROM `" . $this->mTableName . "`"
			. " WHERE time >= ? and time < ?"
			. " GROUP BY DATE_FORMAT(time, '%Y-%m-%d');";
			
		$rs = $this->db->query($sql, array($start, $end));
		return $rs->result_array();
	}
}
