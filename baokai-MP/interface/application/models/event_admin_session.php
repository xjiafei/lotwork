<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Event_admin_session extends CI_Model {
	
	private $mTableName = "event_admin_session";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function getUserInfo($sno)
	{
		$sql = "SELECT * FROM " . $this->mTableName . " AS S"
			. " INNER JOIN event_admin_user AS U"
			. " ON S.uid = U.uid"
			. " WHERE S.sno = ?";
			
		$rs = $this->db->query($sql, array($sno));
		return $rs->first_row('array');
	}
	
	function addSession($uid, $sno)
	{
		$this->db->set('uid'		, $uid);
		$this->db->set('sno'		, $sno);
		$this->db->set('last_time'	, date("Y-m-d H:i:s"));
		
		$this->db->insert($this->mTableName);
		
		return $this->db->affected_rows();
	}
	
	function delSession($uid)
	{
		$this->db->where('uid'	, $uid);
		$this->db->delete($this->mTableName);
		return $this->db->affected_rows();
	}
}
