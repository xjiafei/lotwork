<?php defined('BASEPATH') OR exit('No direct script access allowed');

class UtilMgr {

	function __construct()
	{
		
	}
	
	public static function getMicroSec()
	{
		$time = explode (" ", microtime () );
        	$time = $time [1] . "000";
	        return $time;  
	}
	
	public static function ipToLong($ip)
	{
		$res = 0;
		$ips = split("\\.", $ip);
		for($i = 3;$i >= 0; $i--)
		{
			$res |= $ips[3 - $i] << ($i * 8);
		}
		return sprintf('%u', $res);
	}

	public static function getRealIP()
	{
		$realip = NULL;
		if( isset($_SERVER) )
		{
			if( isset($_SERVER['HTTP_X_FORWARDED_FOR']) )
			{
				$arr = explode(',', $_SERVER['HTTP_X_FORWARDED_FOR']);
				foreach( $arr AS $ip )
				{
					$ip = trim($ip);
					if( $ip != 'unknown' )
					{
						$realip = $ip;
						break;
					}
				}
			}
			elseif( isset($_SERVER['HTTP_CLIENT_IP']) )
			{
				$realip = $_SERVER['HTTP_CLIENT_IP'];
			}
			else
			{
				if( isset($_SERVER['REMOTE_ADDR']) )
				{
					$realip = $_SERVER['REMOTE_ADDR'];
				}
				else
				{
					$realip = '0.0.0.0';
				}
			}
		}
		else
		{
			if( getenv('HTTP_X_FORWARDED_FOR') )
			{
				$realip = getenv('HTTP_X_FORWARDED_FOR');
			}
			elseif( getenv('HTTP_CLIENT_IP') )
			{
				$realip = getenv('HTTP_CLIENT_IP');
			}
			else
			{
				$realip = getenv('REMOTE_ADDR');
			}
		}
		preg_match("/[\d\.]{7,15}/", $realip, $onlineip);
		$realip = !empty($onlineip[0]) ? $onlineip[0] : '0.0.0.0';
		return $realip;
	}

}


/* End of file UtilMgr.php */
/* Location: ./application/libraries/UtilMgr.php */