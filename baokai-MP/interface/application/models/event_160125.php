<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Event_160125 extends CI_Model {

    private $mTableName = "event_20160125";

    function __construct()
    {
        parent::__construct();
    }

    function addData($user_id, $username, $amount, $prize, $prize2, $eventday)
    {
        $this->db->where('user_id', intval($user_id));
        $this->db->where('eventday', $eventday);
        if($this->db->count_all_results($this->mTableName)) return 0;

        $this->db->insert($this->mTableName, array(
            "user_id" => intval($user_id),
            "username" => $username,
            "amount" => $amount,
            "prize" => $prize,
            "prize2" => $prize2,
            "claim" => 0,
            "eventday" => $eventday,
            "create_time" => date("Y-m-d H:i:s"),
            "claim_time" => date("Y-m-d H:i:s")
        ));
        return $this->db->affected_rows();
    }
    
    function addDouble($user_id, $prize2, $eventday)
    {
        $this->db->where('user_id', intval($user_id));
        $this->db->where('eventday', $eventday);
        $this->db->update($this->mTableName, array("prize2" => $prize2));
        return $this->db->affected_rows();
    }
    
    function getDate()
    {
        return $this->db->query("select DISTINCT eventday from ".$this->mTableName." order by eventday")->result_array();
    }
    
    function queryUid($user_id)
    {
        return $this->db->query("select * from ".$this->mTableName." where user_id = ? order by eventday" ,array(intval($user_id)))->result_array();
    }

    function queryUser($user)
    {
        return $this->db->query("select * from ".$this->mTableName." where username = ? order by eventday, user_id" ,array($user))->result_array();
    }
    
    function queryEventday($eventday)
    {
        return $this->db->query("select * from ".$this->mTableName." where eventday = ? order by user_id" ,array($eventday))->result_array();
    }
    
    function getClaimRecord($user_id, $eventday)
    {
        return $this->db->query("select * from ".$this->mTableName." where user_id = ? and eventday = ?" ,array(intval($user_id), $eventday))->result_array();
    }
    
    function claimPrize($user_id, $eventday)
    {
        $this->db->where('user_id', intval($user_id));
        $this->db->where('eventday', $eventday);
        $this->db->update($this->mTableName, array("claim" => 1, "claim_time" => date("Y-m-d H:i:s")));
        return $this->db->affected_rows();
    }
    
    function getLastEventday()
    {
        return $this->db->query("select eventday from ".$this->mTableName." order by eventday desc limit 1")->result_array();
    }
    
    function claimLog($user_id, $CGISESSID, $prize, $eventday, $request, $response)
    {
        $this->db->insert($this->mTableName."_log", array(
            "user_id" => intval($user_id),
            "CGISESSID" => $CGISESSID,
            "prize" => $prize,
            "eventday" => $eventday,
            "call_time" => date("Y-m-d H:i:s"),
            "request" => $request,
            "response" => $response
        ));
        return $this->db->affected_rows();
    }
}