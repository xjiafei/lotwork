<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Counter_model extends CI_Model {
	
	private $mTableName = "counter";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function addDownloadCount($app_id, $initial, $version)
	{
		$this->load->library('UtilMgr');
		
		$this->db->set('behavir'	, "app_down");
		$this->db->set('app_id'		, $app_id);
		$this->db->set('initial'	, $initial);
		$this->db->set('version'	, $version);
		$this->db->set('ip'		, UtilMgr::getRealIP());
		$this->db->set('time'		, date("Y-m-d H:i:s"));
		
		$this->db->insert($this->mTableName);
		return $this->db->affected_rows();
	}
	
	function addLoginCount($app_id, $user_id, $version)
	{
		$this->load->library('UtilMgr');
		
		$num = 0;
		try
		{
			$this->db->set('behavir'	, "login");
			$this->db->set('app_id'		, $app_id);
			$this->db->set('user_id'	, $user_id);
			$this->db->set('initial'	, "0");
			$this->db->set('version'	, $version);
			$this->db->set('ip'		, UtilMgr::getRealIP());
			$this->db->set('time'		, date("Y-m-d H:i:s"));
			$this->db->insert($this->mTableName);
				
			$num = $this->db->affected_rows();
		}
		catch (Exception $e)
		{ 
			// do nothing
		}  
		return $num;
	}
	
	function getDownloadCountList()
	{
		$rs = $this->db->query("SELECT device, app_name, initial,  `counter`.version, COUNT( * ) AS Total"
					. " FROM  `counter` "
					. " INNER JOIN  `app_info` ON  `counter`.app_id =  `app_info`.app_id"
					. " WHERE `behavir` = 'app_down'"
					. " GROUP BY  `counter`.app_id, initial, version"
					. " ORDER BY device, app_name, version, initial");
		
		return $rs->result_array();
	}
	
	function getLoginCountList($data)
	{
		$this->db->select("`app_info`.app_id, device, app_name, `counter`.version, DATE_FORMAT(time, '%Y-%m-%d') AS Time, COUNT( * ) AS Total", FALSE);
		$this->db->from($this->mTableName);
		$this->db->join('app_info', 'counter.app_id = app_info.app_id');
		$this->db->where('behavir', 'login');
		if($data["app_id"])
		{
			$this->db->where('counter.app_id', $data["app_id"]);
		}
		if($data["version"])
		{
			$this->db->where('counter.version', $data["version"]);
		}
		if($data["start_time"])
		{
			$this->db->where('time >=', $data["start_time"]);
		}
		if($data["end_time"])
		{
			$this->db->where('time <', $data["end_time"]);
		}
		
		$this->db->group_by(array("`counter`.app_id", "version", "DATE_FORMAT(time, '%Y-%m-%d')")); 
		
		$this->db->order_by('device');
		$this->db->order_by('app_name');
		$this->db->order_by('version');
		$this->db->order_by('Time');
		
		$rs = $this->db->get();
		
		return $rs->result_array();
		/*
		$rs = $this->db->query("SELECT device, app_name, `counter`.version, DATE_FORMAT(time, '%Y-%m-%d') AS Time, COUNT( * ) AS Total"
					. " FROM  `counter` "
					. " INNER JOIN  `app_info` ON  `counter`.app_id =  `app_info`.app_id"
					. " WHERE `behavir` = 'login'"
					. " GROUP BY  `counter`.app_id, version, DATE_FORMAT(time, '%Y-%m-%d')"
					. " ORDER BY device, app_name, version, Time");
		
		return $rs->result_array();
		*/
	}
	
	function getMonthlyReport($data)
	{
		$initial = $data["initial"]!=null?$data["initial"]:1;
		$end = $data["end"]!=null?$data["end"]:date('Y-m-d', time());
		$start = $data["start"]!=null?$data["start"]:date('Y-m-d', strtotime(date("Y-m-d", strtotime($end)) . " -1 month"));
		
		//echo $initial . "-" . $start . "-" . $end;
		
		$sql = "SELECT app_id , COUNT(*) AS num, DATE_FORMAT(time, '%Y-%m-%d') AS time"
			. " FROM `" . $this->mTableName . "`"
			. " WHERE behavir = 'app_down' AND initial = ? AND app_id IN (9,10) AND  time >= ? and time < ?"
			. " GROUP BY app_id, DATE_FORMAT(time, '%Y-%m-%d');";
			
		$rs = $this->db->query($sql, array($initial, $start, $end));
		return $rs->result_array();
	}
}
