<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Project_rec_model extends CI_Model {
	
	private $mTableName = "project_rec";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function addProject($data)
	{
		$this->db->set('app_id'		, $data['app_id']);
		$this->db->set('token'		, $data['token']);
		$this->db->set('method_id'	, $data['method_id']);
		$this->db->set('issue'		, $data['issue']);
		$this->db->set('code'		, $data['code']);
		$this->db->set('proj_amt'	, $data['proj_amt']);
		$this->db->set('straight'	, $data['straight']);
		$this->db->set('proj_times'	, $data['proj_times']);
		$this->db->set('money'		, $data['money']);
		$this->db->set('time'		, date("Y-m-d H:i:s"));
		
		$this->db->insert($this->mTableName);
		
		return $this->db->affected_rows();
	}
	
	function getProjectList()
	{
		$rs = $this->db->query("SELECT a.proj_id, d.platform, d.device, d.app_name, a.token, c.lot_name, b.method_name, a.issue, a.code, a.proj_amt, a.straight, a.proj_times, a.money, a.time"
					. " FROM  `" . $this->mTableName . "` AS a"
					. " INNER JOIN method AS b ON a.method_id = b.method_id"
					. " INNER JOIN lottery AS c ON b.lot_id = c.lot_id"
					. " INNER JOIN app_info AS d ON a.app_id = d.app_id"
					. " ORDER BY a.proj_id DESC"
					. " LIMIT 0 , 30;");
		return $rs->result_array();
	}
	
	function getDailyReport($data)
	{
		// $day = '2015-05-13'
		$start = $data["day"] . " 00:00:00";
		$end = $data["day"] . " 23:59:59";
		$sql = "SELECT L.lot_name,SUM(money) AS total"
			. " FROM `" . $this->mTableName . "` AS P"
			. " INNER JOIN method AS M ON P.method_id = M.method_id"
			. " INNER JOIN lottery AS L ON M.lot_id = L.lot_id"
			. " WHERE time >= ? and time <= ?"
			. " GROUP BY L.lot_name;";
		
		$rs = $this->db->query($sql, array($start, $end));
		return $rs->result_array();
	}
	
	function getMonthlyReport($data)
	{
		$app_id = $data["app_id"]!=null?$data["app_id"]:9;
		$end = $data["end"]!=null?$data["end"]:date('Y-m-d', time());
		$start = $data["start"]!=null?$data["start"]:date('Y-m-d', strtotime(date("Y-m-d", strtotime($end)) . " -1 month"));
		
		//echo $app_id . "-" . $start . "-" . $end;
		
		$sql = "SELECT L.lot_id,L.lot_name, SUM(money) AS money, DATE_FORMAT(time, '%Y-%m-%d')AS time"
			. " FROM `" . $this->mTableName . "` AS P"
			. " INNER JOIN method AS M ON P.method_id = M.method_id"
			. " INNER JOIN lottery AS L ON M.lot_id = L.lot_id"
			. " WHERE app_id = ? and time >= ? and time < ?"
			. " GROUP BY L.lot_name, DATE_FORMAT(time, '%Y-%m-%d');";
			
		$rs = $this->db->query($sql, array($app_id, $start, $end));
		return $rs->result_array();
	}
	
	function getMonthlyMobileReport($data)
	{
		$end = $data["end"]!=null?$data["end"]:date('Y-m-d', time());
		$start = $data["start"]!=null?$data["start"]:date('Y-m-d', strtotime(date("Y-m-d", strtotime($end)) . " -1 month"));
		
		$sql = "SELECT P.app_id, SUM(money) AS money, DATE_FORMAT(time, '%Y-%m-%d')AS time"
			. " FROM `" . $this->mTableName . "` AS P"
			. " WHERE app_id IN (9,10) AND time >= ? and time < ?"
			. " GROUP BY P.app_id, DATE_FORMAT(time, '%Y-%m-%d');";
			
		$rs = $this->db->query($sql, array($start, $end));
		return $rs->result_array();
	}
	
	function getMonthlyCountReport($data)
	{
		$end = $data["end"]!=null?$data["end"]:date('Y-m-d', time());
		$start = $data["start"]!=null?$data["start"]:date('Y-m-d', strtotime(date("Y-m-d", strtotime($end)) . " -1 month"));
		
		$sql = "SELECT P.app_id, COUNT(DISTINCT(P.token)) AS num, DATE_FORMAT(time, '%Y-%m-%d')AS time"
			. " FROM `" . $this->mTableName . "` AS P"
			. " WHERE app_id IN (9,10) AND time >= ? and time < ?"
			. " GROUP BY P.app_id, DATE_FORMAT(time, '%Y-%m-%d');";
			
		$rs = $this->db->query($sql, array($start, $end));
		return $rs->result_array();
	}
}
