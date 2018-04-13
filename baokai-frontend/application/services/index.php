<?php

class user {
	protected $_user;
    //public $_explicitType = 'UserVO';
	public function validate($param) 
	{
		
		$_user = new Employees();
		$username = $param['username'];
		$password = $param['password'];
		$re=$_user->findbyStr('NAME="'.$username.'" and password="'.encryptLoginPasswd($password).'"');
		if(sizeof($re)>0)
		{
		  return $re;	
		}
		else
		{
			return false;
		}
		}
	
	
	}
?>
