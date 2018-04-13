<?php
/*
 * 检测繁体字
 */
class ZHTW {
	//检测用户名是否使用繁体字
	public function checkZhtw($username){
		//新的繁体字列表替换下面的tw.txt文件即可即可
		$twstr = file_get_contents(SITE_ROOT.'/upload/tw.txt');
		$str_len = strlen($username);
		for ($i = 0; $i < $str_len;)
		{
			$str = $this->sub_str_by_screen($username, 1, FALSE, FALSE);
			$slen = strlen($str);
			$username = substr($username, $slen);
			$i += $slen;
			if ($slen === 1)
			{
				continue;
			}
			if (strpos($twstr, $str) !== FALSE)
			{
				return true;
			}
		}
	}
	
	
	private function sub_str_by_screen($str, $length, $append = TRUE, $htmlspecialchars = TRUE)
	{
		if ($str === '')
		{
			return '';
		}
		elseif ($length === 0 || ($length > 0 && isset($str{$length}) === FALSE))
		{
			if ($htmlspecialchars === TRUE)
			{
				return htmlspecialchars($str, ENT_QUOTES);
			}
			else
			{
				return $str;
			}
		}
		$str       = trim($str);
		$strlength = strlen($str);
		if ($length < 0)
		{
			$length = $strlength + $length;
			if ($length < 0)
			{
				$length = $strlength;
			}
		}
		$newstr = mb_substr($str, 0, $length, 'UTF-8');
		if ($append === TRUE && $str !== $newstr)
		{
			$newstr .= '...';
			/* 避免处理后的比处理前的还长 */
			if (isset($newstr{$strlength - 1}) === TRUE)
			{
				$newstr = $str;
			}
		}
		if ($htmlspecialchars === TRUE)
		{
			return htmlspecialchars($newstr, ENT_QUOTES);
		}
		else
		{
			return $newstr;
		}
	}
}