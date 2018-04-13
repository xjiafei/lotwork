<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Event_guaguacard_data extends CI_Model {
	
	private $mTableName = "event_guaguacard_data";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function addData($user_id, $username, $amount, $datadate)
	{
		$this->db->set('user_id'	, $user_id);
		$this->db->set('username'	, $username);
		$this->db->set('amount'		, $amount);
		$this->db->set('datadate'	, $datadate);
		$this->db->set('create_time'	, date("Y-m-d H:i:s"));
		
		$this->db->insert($this->mTableName);
		
		return $this->db->affected_rows();
	}
	
	
	function IsDataExisted($user_id, $datadate)
	{
		$bln = false;
		$sql = "SELECT * FROM " . $this->mTableName
			. " WHERE user_id = ?"
			. " AND datadate = ?";
			
		$rs = $this->db->query($sql, array($user_id, $datadate));
		if($rs->num_rows() != 0)
		{
			$bln = true;
		}
		return $bln;
	}
	
	function getDataByUserId($user_id)
	{
		$sql = "SELECT user_id, username, SUM(amount) AS total"
			. " FROM " . $this->mTableName 
			. " WHERE user_id = ?"
			. " GROUP BY user_id, username";
				
		$rs = $this->db->query($sql, array($user_id));
		return $rs->first_row('array');
	}
	
	function getUserAmountData($keyword = null)
	{
		$rs = null;
		if($keyword != null){
			$sql = "SELECT D.user_id, D.username, SUM(amount) AS total, P.prize, P2.lv_cnname AS last_lv_cnname, P2.prize AS last_prize"
				. " FROM " . $this->mTableName . " AS D"
				. " LEFT JOIN (SELECT user_id, SUM(prize) AS prize FROM event_guaguacard_prize WHERE status = 1 GROUP BY user_id) AS P ON D.user_id = P.user_id"
				. " LEFT JOIN (SELECT user_id, lv_cnname, prize FROM (SELECT * FROM event_guaguacard_prize WHERE status = 1 ORDER BY get_time DESC) AS G GROUP BY user_id) AS P2 ON P.user_id = P2.user_id"
				. " WHERE D.username = ?"
				. " GROUP BY D.user_id, D.username"
				. " ORDER BY total desc";
				
			$rs = $this->db->query($sql, array($keyword));
		}else{
			$sql = "SELECT D.user_id, D.username, SUM(amount) AS total, P.prize, P2.lv_cnname AS last_lv_cnname, P2.prize AS last_prize"
				. " FROM " . $this->mTableName . " AS D"
				. " LEFT JOIN (SELECT user_id, SUM(prize) AS prize FROM event_guaguacard_prize WHERE status = 1 GROUP BY user_id) AS P ON D.user_id = P.user_id"
				. " LEFT JOIN (SELECT user_id, lv_cnname, prize FROM (SELECT * FROM event_guaguacard_prize WHERE status = 1 ORDER BY get_time DESC) AS G GROUP BY user_id) AS P2 ON P.user_id = P2.user_id"
				. " GROUP BY D.user_id, D.username"
				. " ORDER BY total desc";
				
			$rs = $this->db->query($sql);
		}
		
		//var_dump(count($rs->result_array()));
		
		return $rs->result_array();
		//$ary = $rs->result_array();
		//var_dump($ary);
		//var_dump($rs->num_rows());
		//return $ary["total"];
	}
	
}
