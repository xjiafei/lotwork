<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Event_egg_model extends CI_Model {
	
	private $mTableName = "event_egg";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function getEggPrize($user_id, $date)
	{
		$sql = "SELECT * FROM event_egg"
			. " WHERE user_id = ?"
			. " AND date = ?";
			
		$rs = $this->db->query($sql, array($user_id, $date));
		return $rs->first_row('array');
	}
	
	function logEggPrize($egg_id)
	{
		$this->db->set('status'		, 1);
		$this->db->set('get_time'	, date("Y-m-d H:i:s"));
		$this->db->where('egg_id'	, $egg_id);
		
		$this->db->update($this->mTableName);
		return $this->db->affected_rows();
	}
	
	function addEggPrize($user_id, $uuid, $prize, $system, $date)
	{
		$this->db->set('user_id'	, $user_id);
		$this->db->set('uuid'		, $uuid);
		$this->db->set('prize'		, $prize);
		$this->db->set('system'		, $system);
		$this->db->set('date'		, $date);
		$this->db->set('create_time'	, date("Y-m-d H:i:s"));
		
		$this->db->insert($this->mTableName);
		
		return $this->db->affected_rows();
	}
	
	function IsUuidExisted($uuid, $date)
	{
		/*
		echo "uuid:" . $uuid . "<br/>";
		echo "date:" . $date . "<br/>";
		*/
		$bln = false;
		$sql = "SELECT * FROM event_egg"
			. " WHERE uuid = ?"
			. " AND date = ?"
			. " AND status = 1";
			
		$rs = $this->db->query($sql, array($uuid, $date));
		//var_dump(count($rs->result_array()));
		
		//echo count($rs->result_array());
		
		if($rs->num_rows() != 0)
		{
			$bln = true;
		}
		return $bln;
	}
	
	function IsTodayGetPrize($user_id, $date)
	{
		/*
		echo "user_id:" . $user_id . "<br/>";
		echo "date:" . $date . "<br/>";
		*/
		$bln = false;
		$sql = "SELECT * FROM event_egg"
			. " WHERE user_id = ?"
			. " AND date = ?"
			. " AND status = 1";
			
		$rs = $this->db->query($sql, array($user_id, $date));
		//var_dump(count($rs->result_array()));
		
		//echo count($rs->result_array());
		
		if($rs->num_rows() != 0)
		{
			$bln = true;
		}
		return $bln;
	}
}
