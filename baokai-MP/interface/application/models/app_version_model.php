<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class App_version_model extends CI_Model {
	
	private $mTableName = "app_version";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function getAppVersion($app_id)
	{
		$this->db->select('version, download_url');
		$this->db->from($this->mTableName);
		$this->db->where('app_id', $app_id);
		
		return $this->db->get();
	}
	
	function updateAppVersion($data)
	{
		$this->db->set('version'		, $data['version']);
		$this->db->set('must_upgrade'		, $data['must_upgrade']);
		$this->db->set('download_url'		, $data['download_url']);
		$this->db->set('msg'			, $data['msg']);
		$this->db->set('latest_update_date'	, date("Y-m-d"));
		
		$this->db->where('app_id', $data['app_id']);
		$this->db->update($this->mTableName); 
	
		return $this->db->affected_rows();
	}
}



/* End of file app_version_model.php */
/* Location: ./application/models/app_version_model.php */