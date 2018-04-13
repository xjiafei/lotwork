<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Admin_model extends CI_Model {
	
	private $mTableName = "admin";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function login($account, $password)
	{
		$this->db->select('*');
		$this->db->from($this->mTableName);
		$this->db->where('account', $account);
		$this->db->where('password', $password);
		$this->db->where('enabled', 1);
		
		$rs = $this->db->count_all_results();
		return $rs > 0?1:0;
	}
}
