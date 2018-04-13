<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class App_domain_model extends CI_Model {
	
	private $mTableName = "app_domain";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function getAppDomainActivity()
	{
		$this->db->select('app_domain.app_id, app_domain.domain, app_domain.latest_update_date, app_info.platform, app_info.device, app_info.app_name, app_info.version');
		$this->db->from($this->mTableName);
		$this->db->join('app_info', 'app_domain.app_id = app_info.app_id');
		
		$rs = $this->db->get();
		
		return $rs->result_array();
	}
	
	function updateAppDomain($data)
	{
		$this->db->set('domain'				, $data['domain']);
		$this->db->set('latest_update_date'	, date("Y-m-d H:i:s"));
		
		$this->db->where('app_id', $data['app_id']);
		$this->db->update($this->mTableName); 
		
		return $this->db->affected_rows();
	}
	
	function getAppDomain($app_id)
	{
		$this->db->select('domain');
		$this->db->from($this->mTableName);
		$this->db->where('app_id', $app_id);
		
		$rs = $this->db->get();
		return $rs->first_row('array');
	}
}