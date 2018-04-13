<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class App_multiply_log_model extends CI_Model {
	
	private $mTableName = "app_multiply_log";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function addData($data)
	{
		$this->db->set('app_code'	, $data['appCode']);
		$this->db->set('uuid'		, $data['uuid']);
		$this->db->set('create_time'	, date("Y-m-d H:i:s"));
		
		$this->db->insert($this->mTableName);
		
		return $this->db->affected_rows();
	}
	
	function IsDataExisted($data)
	{
		$bln = false;
		$sql = "SELECT * FROM " . $this->mTableName
			. " WHERE app_code = ?"
			. " AND uuid = ?";
			
		$rs = $this->db->query($sql, array($data['appCode'], $data['uuid']));
		if($rs->num_rows() != 0)
		{
			$bln = true;
		}
		return $bln;
	}
	
	function getData()
	{
		$sql = "SELECT * FROM " . $this->mTableName
			. " ORDER BY app_code;";
			
		$rs = $this->db->query($sql);
		return $rs->result_array();
	}
}