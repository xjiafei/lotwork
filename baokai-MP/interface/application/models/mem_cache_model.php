<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Mem_cache_model extends CI_Model {

	function __construct() {
		parent::__construct();
		
		$this->load->driver('cache');
		if(!$this->cache->memcached->is_supported())
		{
			$this->load->library('LogMgr');
			LogMgr::ErrorLog("[" . date("Y-m-d H:i:s") . "] not supported memcached\r\n");
		}
	}

	public function get($key)
	{
		return $this->cache->memcached->get($key);
	}
	
	public function save($key, $data, $time = 3600)
	{
		$this->cache->memcached->save($key, $data, $time);
	}

}



// End Class

/* End of file Mem_cache_model.php */
/* Location: ./application/models/Mem_cache_model.php */



