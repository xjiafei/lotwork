<?php defined('BASEPATH') OR exit('No direct script access allowed');

class LogMgr {

	function __construct()
	{
		
	}

	public static function UserLog($data)
	{
		LogMgr::Log(date("Y-m-d"). ".txt", $data); 
	}
	
	public static function ErrorLog($data)
	{
		LogMgr::Log(date("Y-m-d"). "_error.txt", $data); 
	}
	
	
	public static function Log($filename, $data)
	{
		try
		{
			$file = fopen("./application/logs/" . $filename ,"a+");
			fwrite($file, $data);
			fclose($file);
		}
		catch (Exception $e)
		{ 
			// do nothing
		}  
	}
}

// End Class

/* End of file LogMgr.php */
/* Location: ./application/libraries/LogMgr.php */