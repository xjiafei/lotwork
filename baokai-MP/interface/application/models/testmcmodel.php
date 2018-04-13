<?php
class Testmcmodel extends CI_Model {
function __construct() {
parent::__construct ();
$this->load->database ();
}
function testMc()
    {
        $this->load->driver('cache');
        //save($id, $data, $ttl = 60)
        $key = 'testmckey';
        $data = time();
        
        if($this->cache->memcached->is_supported()){
        	echo "supported memcached";
        }else{
        	echo "not supported memcached";
        }
        echo "<br><br>";
        
		$is_success = $this->cache->memcached->save($key, $data, 60);
		
		if($is_success){
			echo "save success";
		}else{
			echo "save false";
		}
		
		echo "<br>===========<br>";
		
		$str = $this->cache->memcached->get($key);
	        print_r("TEST SAVE AND GET str=".$str);
	        print_r($str);
	    }
    
}
?>