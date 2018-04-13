<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Event_guaguacard_upload extends CI_Model {
	
	private $mTableName = "event_guaguacard_upload";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function addData($username, $filename)
	{
		$this->db->set('username'	, $username);
		$this->db->set('upload_time'	, date("Y-m-d H:i:s"));
		$this->db->set('filename'	, $filename);
		
		$this->db->insert($this->mTableName);
		return $this->db->affected_rows();
	}
	
	function getData(){
		$sql = "SELECT *"
			. " FROM " . $this->mTableName ;
				
		$rs = $this->db->query($sql);
		return $rs->result_array();
	}
	
}
