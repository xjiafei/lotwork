<?php defined('BASEPATH') OR exit('No direct script access allowed');

class StrMgr {

	protected static $mNumStrings = '0123456789';

	function __construct()
	{
		
	}


	public static function getNumString($num = 16)
	{
		$rtn = "";
		$len = strlen(self::$mNumStrings);
		for ($i = 0; $i < $num; $i++) {
			$idx = mt_rand(0, $len - 1);
			$rtn .= self::$mNumStrings[$idx];
		}
		return $rtn;
	}

	public static function toAsterisk($str, $asteriskNum = 12, $keepNum = 4)
	{
		return str_repeat('*', $asteriskNum) .  substr($str , strlen($str) - $keepNum, $keepNum);
	}

}
