<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Event_admin_user extends CI_Model {
	
	private $mTableName = "event_admin_user";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function login($username, $pwd)
	{
		$sql = "SELECT * FROM " . $this->mTableName
			. " WHERE username = ?"
			. " AND pwd = ?";
			
		$rs = $this->db->query($sql, array($username, $pwd));
		return $rs->first_row('array');
	}
	
	function updateLoginTime($username)
	{
		$this->db->set('login_time'	, date("Y-m-d H:i:s"));
		$this->db->where('username'	, $username);
		
		$this->db->update($this->mTableName);
		return $this->db->affected_rows();
	}
	
	function updatePwd($uid, $pwd)
	{
		$this->db->set('pwd'	, $pwd);
		$this->db->where('uid'	, $uid);
		
		$this->db->update($this->mTableName);
		return $this->db->affected_rows();
	}
	
	function updatePwdByName($username, $pwd)
	{
		$this->db->set('pwd'		, $pwd);
		$this->db->where('username'	, $username);
		
		$this->db->update($this->mTableName);
		return $this->db->affected_rows();
	}
}
