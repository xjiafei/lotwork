<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class App_info_model extends CI_Model {
	
	private $mTableName = "app_info";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function getAppInfo($app_id)
	{
		$query = $this->db->get($this->mTableName);
		$rs = $query->result();
		$rtn = null;
		
		foreach ($rs as $row)
		{
			if(strcmp($row->app_id, $app_id) == 0)
			{
				$rtn = $row;	
			}
		}
		return $rtn;
	}
	
	function getAdminAppInfo()
	{
		$this->db->select('*');
		$query = $this->db->get($this->mTableName);
		return $query->result();
	}
}



/* End of file app_info_model.php */
/* Location: ./application/models/app_info_model.php */