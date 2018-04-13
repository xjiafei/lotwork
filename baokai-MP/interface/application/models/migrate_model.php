<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Migrate_model extends CI_Model {
	
	private $mTableName = "migrate";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function insertData($data)
	{
		$this->db->set('userid'		, $data['userid']);
		$this->db->set('amount'		, $data['amount']);
		$this->db->set('create_time'	, date("Y-m-d H:i:s"));
		
		$this->db->insert($this->mTableName);
		
		return $this->db->insert_id();
	}
	
	function updateData($data)
	{
		$this->db->set('status'	, $data['status']);
		$this->db->set('msg'	, $data['msg']);
		
		$this->db->where('mid', $data['mid']);
		$this->db->update($this->mTableName); 
	
		return $this->db->affected_rows();
	}
	
	function getMigrateList($data)
	{
		$data["start"] = $data["start"]==null?0:$data["start"];
		$data["end"] = $data["end"]==null?20:$data["end"];
		
		$rs = $this->db->get($this->mTableName 
					, $data["end"] , $data["start"]);
		return $rs->result_array();
	}
}
