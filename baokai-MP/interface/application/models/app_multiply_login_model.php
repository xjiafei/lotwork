<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class App_multiply_login_model extends CI_Model {
	
	private $mTableName = "app_multiply_login";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function addData($appCode, $uuid, $username)
	{
		$this->db->set('app_code'		, $appCode);
		$this->db->set('uuid'			, $uuid);
		$this->db->set('username'		, $username);
		$this->db->set('last_login_time'	, date("Y-m-d H:i:s"));
		$this->db->set('counter'		, 1);
		
		$this->db->insert($this->mTableName);
		
		return $this->db->affected_rows();
	}
	
	function IsDataExisted($appCode, $uuid, $username)
	{
		$bln = false;
		$sql = "SELECT * FROM " . $this->mTableName
			. " WHERE app_code = ?"
			. " AND uuid = ?"
			. " AND username = ?";
			
		$rs = $this->db->query($sql, array($appCode, $uuid, $username));
		if($rs->num_rows() != 0)
		{
			$bln = true;
		}
		return $bln;
	}
	
	function updateCount($appCode, $uuid, $username)
	{
		$sql = "update `" . $this->mTableName . "` set counter = counter + 1, last_login_time = ?"
			. " WHERE app_code = ?"
			. " AND uuid = ?"
			. " AND username = ?";
		$rs = $this->db->query($sql, array(date("Y-m-d H:i:s"), $appCode, $uuid, $username));
	}
	
	function getData()
	{
		$sql = "SELECT * FROM " . $this->mTableName
			. " ORDER BY app_code, uuid, username;";
			
		$rs = $this->db->query($sql);
		return $rs->result_array();
	}
}