<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class City_model extends CI_Model {
	
	private $mTableName = "city";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function getCityList($pid)
	{
		$rs = $this->db->query("SELECT cid AS id, cname as name"
					. " FROM  `" . $this->mTableName . "`"
					. " WHERE enable = '1' and pid = ?"
					. " ORDER BY sort"
					, array($pid));
		return $rs->result_array();
	}
	
	
}
