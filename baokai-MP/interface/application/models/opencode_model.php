<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Opencode_model extends CI_Model {
	
	private $mTableName = "opencode";
	
	function __construct()
	{
		parent::__construct();
	}
	
	function addOpencode($data)
	{
            if($a = $this->db->insert($this->mTableName, $data)){

                $res = $this->db->query("SELECT LAST_INSERT_ID()")->row_array();
                return $res['LAST_INSERT_ID()'];

           } else {
               return false;
           }
	}
        
        function getOpencode($lotteryId) {
            //get the larilest opencode by time
            $date = date("YmdHis", "-6 hours");
            //开奖后6小时内有效 
            if ($lotteryId) {
                $where  = " lottery = $lotteryId ";
            } else {
                $where  = " 1 AND ";
            }
            $sql = "select * from " . $this->$mTableName ." WHERE  $where  time >= $date AND numbers is not null order by id desc limit 1";
            echo $sql;
            $a = $this->db->query($sql);
            var_dump($a);
            return $a;
        }
        
        function isExisted($lottery, $issue)
        {
		$this->db->where('lottery', $lottery);
		$this->db->where('issue', $issue);
		$query = $this->db->get($this->mTableName);
		
		return $query->num_rows()>0?true:false;
        }
        
        function getOpencodeList($amount = 21)
        {
		$this->db->order_by("id", "desc"); 
		$this->db->order_by("lottery", "desc"); 
		$this->db->order_by("issue", "desc"); 
		
		$this->db->limit($amount);
		
		$rs = $this->db->get($this->mTableName);
		
		return $rs->result_array();
        }
}
