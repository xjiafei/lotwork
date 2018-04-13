<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Event_guaguacard_prize extends CI_Model {
	
	private $mTableName = "event_guaguacard_prize";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function addPrize($user_id, $lv, $lv_cnname, $prize, $amount, $status)
	{
		$this->db->set('user_id'	, $user_id);
		$this->db->set('lv'		, $lv);
		$this->db->set('lv_cnname'	, $lv_cnname);
		$this->db->set('prize'		, $prize);
		$this->db->set('amount'		, $amount);
		$this->db->set('status'		, $status);
		$this->db->set('create_time'	, date("Y-m-d H:i:s"));
		
		$this->db->insert($this->mTableName);
		
		return $this->db->affected_rows();
	}
	
	function enablePrize($user_id, $amount)
	{
		$this->db->set('status'		, 0);
		
		$this->db->where('user_id'	, $user_id);
		$this->db->where('status'	, -1);
		$this->db->where('amount <='	, $amount);
		
		$this->db->update($this->mTableName);
		return $this->db->affected_rows();
	}
	
	function getLvPrize($user_id, $lv)
	{
		$sql = "SELECT lv_cnname, amount, prize, status FROM " . $this->mTableName
			. " WHERE user_id = ?"
			. " AND lv = ?";
			
		$rs = $this->db->query($sql, array($user_id, $lv));
		return $rs->first_row('array');
	}
	
	function updateLvPrize($user_id, $lv, $status){
		$this->db->set('status'		, $status);
		$this->db->set('get_time'	, date("Y-m-d H:i:s"));
		
		$this->db->where('user_id'	, $user_id);
		$this->db->where('lv'	, $lv);
		$this->db->where('status'	, 0);
		
		$this->db->update($this->mTableName);
		return $this->db->affected_rows();
	}
	
	
	function getPrizeByUserId($user_id)
	{
		$sql = "SELECT lv_cnname, amount, prize, status FROM " . $this->mTableName
			. " WHERE user_id = ?"
			. " ORDER BY lv";
			
		$rs = $this->db->query($sql, array($user_id));
		return $rs->result_array();
	}
	
	function IsPrizeExisted($user_id, $lv)
	{
		$bln = false;
		$sql = "SELECT * FROM " . $this->mTableName
			. " WHERE user_id = ?"
			. " AND lv = ?";
			
		$rs = $this->db->query($sql, array($user_id, $lv));
		if($rs->num_rows() != 0)
		{
			$bln = true;
		}
		return $bln;
	}
}
