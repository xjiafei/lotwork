<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Pushcli_model extends CI_Model {
	private $mTableName = "userpushhistory";
	private $push_valid_hour = 12;
        
	function __construct()
	{
		parent::__construct();
	}

        function addPushMsg($insertData)
        {
            return $this->db->insert($this->mTableName, $insertData);
        }
    
        function getPushEvent()
        {
            $sql = "SELECT * FROM userpushhistory ".
                    "WHERE pushstatus = '0' AND now() > triggertime ".
                    "AND extract(hour from timediff(now(), triggertime)) < ? limit 1;";
            $query = $this->db->query($sql, array($this->push_valid_hour));
            return $query->first_row('array');
        }
        
        function pushStartLog($id, $active_record)
        {
            return $this->db->update($this->mTableName,
                    array("pushstatus" => 1,
                        "starttime" => date("Y-m-d H:i:s"),
                        "active_record" => $active_record), array('id' => $id));
        }
        
        function pushFinishLog($id, $effect_record)
        {
            return $this->db->update($this->mTableName,
                    array("pushstatus" => 2,
                        "finishtime" => date("Y-m-d H:i:s"),
                        "effect_record" => $effect_record), array('id' => $id));
        }
        
        function pushErrorLog($id, $pushstatus, $errormsg)
        {
            return $this->db->update($this->mTableName,
                    array("pushstatus" => $pushstatus,
                        "finishtime" => date("Y-m-d H:i:s"),
                        "error" => $errormsg), array('id' => $id));
        }
        
        function getPushCliList()
        {
            $query = $this->db->order_by("triggertime", "asc")->get_where($this->mTableName, "pushstatus = 0", 100);
            return $query->result_array();
        }
        
        function getPushMsgList($data)
        {
        	$data["amount"] = $data["amount"]==null?20:$data["amount"];
        	
        	if($data["app_id"])
		{
			$this->db->where('userpushhistory.app_id', $data["app_id"]);
		}
		if($data["start_time"])
		{
			$this->db->where('triggertime >=', $data["start_time"]);
		}
		if($data["end_time"])
		{
			$this->db->where('triggertime <', $data["end_time"]);
		}
        	
		$this->db->order_by("id", "desc"); 
		$this->db->limit($data["amount"]);
		
		$rs = $this->db->get($this->mTableName);
		
		return $rs->result_array();
        }
        
	function updatePushMsg($data)
	{
		$this->db->set('pushmsg'	, $data['pushmsg']);
		$this->db->set('pushstatus'	, $data['pushstatus']);
		$this->db->set('triggertime'	, $data['triggertime']);
		
		$this->db->where('id', $data['id']);
		$this->db->update($this->mTableName); 
	
		return $this->db->affected_rows();
	}
}