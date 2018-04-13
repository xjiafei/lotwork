<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Province_model extends CI_Model {
	
	private $mTableName = "province";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function getProvinceList()
	{
		$rs = $this->db->query("SELECT pid AS id, pname as name"
					. " FROM  `" . $this->mTableName . "`"
					. " WHERE enable = '1'"
					. " ORDER BY sort");
		return $rs->result_array();
	}
	
	
}
