<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Activity_model extends CI_Model {
	
	private $mTableName = "activity";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function getActivity($app_id)
	{
		$this->db->select('act_id, app_id,subject, act_url, img_url, start_time, end_time, enabled');
		$this->db->from($this->mTableName);
		$this->db->where('app_id', $app_id);
		
		$this->db->where('start_time <', date("Y-m-d H:i:s"));
		$this->db->where('end_time >', date("Y-m-d H:i:s"));
		$this->db->where('enabled', '1');
		
		$rs = $this->db->get();
		
		return $rs->result_array();
	}
	
	function getAdminActivity()
	{
		$this->db->select('activity.act_id, activity.app_id, CONCAT_WS("-", app_info.platform , app_info.app_name, app_info.version) AS "platform-app-version", activity.act_name, activity.subject, activity.act_url, activity.img_url, activity.start_time, activity.end_time, activity.enabled', FALSE);
		$this->db->from($this->mTableName);
		$this->db->join('app_info', 'activity.app_id = app_info.app_id');
		$this->db->order_by('activity.app_id,activity.start_time');
		
		$rs = $this->db->get();
		
		return $rs->result_array();
	}
	
	function updateActivity($data)
	{
		$this->db->set('act_name'	, $data['act_name']);
		$this->db->set('subject'	, $data['subject']);
		$this->db->set('act_url'	, $data['act_url']);
		$this->db->set('img_url'	, $data['img_url']);
		$this->db->set('start_time'	, $data['start_time']);
		$this->db->set('end_time'	, $data['end_time']);
		$this->db->set('enabled'	, $data['enabled']);
		
		$this->db->where('act_id', $data['act_id']);
		$this->db->update($this->mTableName); 
	
		return $this->db->affected_rows();
	}
	
	function addActivity($data)
	{
		$this->db->set('app_id'		, $data['app_id']);
		$this->db->set('act_name'	, $data['act_name']);
		$this->db->set('subject'	, $data['subject']);
		$this->db->set('act_url'	, $data['act_url']);
		$this->db->set('img_url'	, $data['img_url']);
		$this->db->set('start_time'	, $data['start_time']);
		$this->db->set('end_time'	, $data['end_time']);
		$this->db->set('enabled'	, $data['enabled']);
		
		$this->db->insert($this->mTableName);
		
		return $this->db->affected_rows();
	}
}
