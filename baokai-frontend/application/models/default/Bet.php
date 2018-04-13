<?php

class Bet extends Client{
    
    public function __construct(){
    	parent::__construct();
    
    }
    /*
     * @param int uId 用户ID
     * @param str pId 方案ID
     * @return bool
     */
    public function repeal($uId, $pId){
        $res = true;
        
        
        
        return $res;
    }
    
    public function get($lottery, $time, $status, $issue, $dateBegin, $dateEnd, $packageNo, $pageNo, $pageSize){
        $result = array(
            "TOTALNUM"=>4,
            "DATA"=>array(
                array(
                    "packageNo"=>"ABC77779",
                    "title"=>"重庆时时彩",
                    "issue"=>"20121218012",
                    "buyAmonut"=>"3000",
                    "status"=>"1",
                    "isChas"=>"0",
                    "buyTime"=>"2012-02-28 22:22:00",
                    "Id"=>1,
                ),
                array(
                    "packageNo"=>"ABC77771",
                    "title"=>"重庆时时彩",
                    "issue"=>"20121218013",
                    "buyAmonut"=>"1111",
                    "status"=>"3",
                    "isChas"=>"0",
                    "buyTime"=>"2012-02-28 22:22:00",
                    "Id"=>2,
                ),
                array(
                    "packageNo"=>"ABC77772",
                    "title"=>"重庆时时彩",
                    "issue"=>"20121218012",
                    "buyAmonut"=>"70",
                    "status"=>"2",
                    "isChas"=>"0",
                    "buyTime"=>"2012-02-28 22:22:00",
                    "Id"=>3,
                ),
                array(
                    "packageNo"=>"ABC77773",
                    "title"=>"重庆时时彩",
                    "issue"=>"20121218012",
                    "buyAmonut"=>"1000",
                    "status"=>"0",
                    "isChas"=>"0",
                    "buyTime"=>"2012-02-28 22:22:00",
                    "Id"=>4,
                ),
            )
        );
        return $result;
    }
}
?>