<?php 
class Testmc extends CI_Controller {
	
	function __construct(){
		parent::__construct();
	}
	function index(){  
		/*
		$memcache = new Memcache; 
		$memcache->connect('127.0.0.1', 11211) or die ("Could not connect"); 
		
		$version = $memcache->getVersion(); 
		echo "Server's version: ".$version."<br/>\n"; 
		
		$tmp_object = new stdClass; 
		$tmp_object->str_attr = 'test'; 
		$tmp_object->int_attr = 123; 
		
		$memcache->set('key', $tmp_object, false, 10) or die ("Failed to save data at the server"); 
		echo "Store data in the cache (data will expire in 10 seconds)<br/>\n"; 
		
		$get_result = $memcache->get('key'); 
		echo "Data from the cache:<br/>\n"; 
		
		var_dump($get_result); 
			
		echo "<br/><br/><br/><br/>"; 
			
			
		  $this->load->model('testmcmodel');  
		//$this->load->helper('url');
		$this->testmcmodel->testMc();
		echo "<br>view-end";
		// $data = "testmc";
		// $this->load->view('testmcview',$data);
		}
		*/
	}
	
	function testOracle()
	{
		$db = "10.3.7.20:1521/game";
		$conn = oci_connect("us_firefog", "firefog_123qwe", $db, 'UTF8');
		
		$qry = "select t1.column_name, comments from all_tab_columns t1, all_col_comments t2 where t1.table_name = t2.table_name and t1.column_name = t2.column_name and t1.table_name = 'AD' ORDER BY COLUMN_ID";
		$qry = "select * from AD";
		$stmt = oci_parse($conn, $qry);
		oci_execute($stmt, OCI_DEFAULT);
				
		
		while (oci_fetch($stmt)) {
			//echo " [" . oci_result($stmt, "COMMENTS") . "]<br/>";
			echo " [" . oci_result($stmt, "NAME") . "]<br/>";
			echo " [" . oci_result($stmt, "NAME") . "]<br/>";
		}	
	}
	
	function testJump()
	{
		$this->load->helper('url');
		$this->load->view('egg/test');
		
	}
}