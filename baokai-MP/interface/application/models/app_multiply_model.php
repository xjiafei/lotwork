<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class App_multiply_model extends CI_Model {
	
	private $mTableName = "app_multiply";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function getAppMultiplyActivity()
	{
		$this->db->select('app_code, link, status, image, latest_update');
		$this->db->from($this->mTableName);
		
		$rs = $this->db->get();
		
		return $rs->result_array();
	}
	
	function updateAppMultiply($data)
	{
		$this->db->set('link'			, $data['link']);
		$this->db->set('image'			, $data['image']);
		$this->db->set('status'			, $data['status']);
		$this->db->set('latest_update'	, date("Y-m-d H:i:s"));
		$this->db->where('app_code', $data['appCode']);
		$this->db->update($this->mTableName); 
		
		return $this->db->affected_rows();
	}
	
	function getAppMultiply($app_code)
	{
		$this->db->select('link,image');
		$this->db->from($this->mTableName);
		$this->db->where('app_code', $app_code);
		$this->db->where('status', 1);
		
		$rs = $this->db->get();
		return $rs->first_row('array');
	}
	
	function addData($data)
	{
		$this->db->set('link'		, $data['link']);
		$this->db->set('image'		, $data['image']);
		$this->db->set('status'		, $data['status']);
		$this->db->set('latest_update'	, date("Y-m-d H:i:s"));
		
		$this->db->insert($this->mTableName);
		
		return $this->db->affected_rows();
	}
}