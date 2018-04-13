<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Push_model extends CI_Model {
	private $mTableName = "userpushinfo";
	private $session_valid_hour = 120;
        
	function __construct()
	{
		parent::__construct();
	}
	
	function registerUserPushInfo($data)
	{
            return $this->db->replace($this->mTableName, $data);
	}
        
        function insertUserPushInfo($data)
	{
            $where = array('userid' => $data->userid, 'app_id' => $data->app_id);
            $query = $this->db->select('userid')->get_where($this->mTableName, $where);
            if($query->num_rows() > 0) return 0;
            $this->db->insert($this->mTableName, $data);
            return 1;
	}
        
        function clearDeviceid($deviceid)
	{
            return $this->db->where('deviceid', $deviceid)->update($this->mTableName, array('deviceid' => ''));
	}
        
        function getPushStatus($userid, $app_id)
        {
            $where = array('userid' => $userid, 'app_id' => $app_id);
            $query = $this->db->select('userid, pushstatus as openstatus')->get_where($this->mTableName, $where);
            return $query->num_rows() == 1?$query->row():array('userid' => $userid, 'openstatus' => '0');
        }
        
        function setPushStatus($userid, $app_id, $pushstatus)
        {
            $where = array('userid' => $userid, 'app_id' => $app_id);
            return $this->db->update($this->mTableName, array("pushstatus" => $pushstatus), $where);
        }
        
        function getPushList($app_id, $deviceidtype = 0)
        {
            $sql = "SELECT userid, deviceid, deviceidtype FROM userpushinfo ".
                    "WHERE app_id = ? AND deviceid <> '' AND pushstatus = 1 AND deviceidtype = ? ".
                    "AND extract(hour from timediff(now(), lastregister)) < ? ;";
                    //"AND userid in ('826289','826287','826114','826116','826117','826118','826119','826120');";
            $query = $this->db->query($sql, array($app_id, $deviceidtype, $this->session_valid_hour));
            return $query->result_array();
            /*
            app023- 826289
            app022 - 826287
            app001 - 826114
            app002 - 826116
            app003 - 826117
            app004 - 826118
            app005 - 826119
            app006 - 826120
            peric1111 - 1101956
            */
        }
        
        function getPushInfoList()
        {
            $query = $this->db->order_by("lastregister", "desc")->get_where($this->mTableName, "deviceid <> ''", 100);
            return $query->result_array();
        }
}