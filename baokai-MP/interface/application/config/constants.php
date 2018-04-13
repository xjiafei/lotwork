<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');

/*
|--------------------------------------------------------------------------
| File and Directory Modes
|--------------------------------------------------------------------------
|
| These prefs are used when checking and setting modes when working
| with the file system.  The defaults are fine on servers with proper
| security, but you may wish (or even need) to change the values in
| certain environments (Apache running a separate process for each
| user, PHP under CGI with Apache suEXEC, etc.).  Octal values should
| always be used to set the mode correctly.
|
*/
define('FILE_READ_MODE', 0644);
define('FILE_WRITE_MODE', 0666);
define('DIR_READ_MODE', 0755);
define('DIR_WRITE_MODE', 0777);

/*
|--------------------------------------------------------------------------
| File Stream Modes
|--------------------------------------------------------------------------
|
| These modes are used when working with fopen()/popen()
|
*/

define('FOPEN_READ',							'rb');
define('FOPEN_READ_WRITE',						'r+b');
define('FOPEN_WRITE_CREATE_DESTRUCTIVE',		'wb'); // truncates existing file data, use with care
define('FOPEN_READ_WRITE_CREATE_DESTRUCTIVE',	'w+b'); // truncates existing file data, use with care
define('FOPEN_WRITE_CREATE',					'ab');
define('FOPEN_READ_WRITE_CREATE',				'a+b');
define('FOPEN_WRITE_CREATE_STRICT',				'xb');
define('FOPEN_READ_WRITE_CREATE_STRICT',		'x+b');

// 现有平台
define('PLATFORM_3', '1' );
define('PLATFORM_ADMIN', '2' );
define('PLATFORM_4', '3' );
define('PLATFORM_BM', '4' );
define('PLATFORM_BM_AGENT', '5' );
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////	 					    development setup	                                       /////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// 是否为测试环境
define('DEVELOPMENT', false);
// 测试环境所需过滤文字
define ("FILTER_WORDS", serialize (array ("鳳凰", "宝开", "158")));

// 定义3.0所有URL中转请求
//define("HOST", "http://www.passport.app");	// 测试
//define("HOST", "http://ios.phl5b.org");		// 测试
define("HOST", "http://ios188.com");		// 生产

// 定义4.0所有URL中转请求
//define("HOST_4", "http://iphong.joy188.com:888");	// 测试(本机上需要加端口888，更新上测试环境时必须拿掉)
define("HOST_4", "http://mobile.xlotteryxwqc");			// 生产

// 定义BM Agent所有URL中转请求
//define("HOST_BM_AGENT", "http://admin.bomao.com");		// 测试
define("HOST_BM_AGENT", "http://admin.bcproduct.com");	// 生产

//博猫域名
//define("HOST_BM", "http://cat.test.com"); //内网
//define("HOST_BM", "http://www.bocat.com"); //测试
define("HOST_BM", "http://www.bomao.com"); //生产
#include 'constants_Bomao.php';

include 'constants_3.php';
include 'constants_4.php';

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////	                                        BM _AGENT平台			                               /////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//登陆
define('FRONT_LOGIN_BM_AGENT',  HOST_BM_AGENT .  '/agent-api/verification' );

//代理资料
define('FRONT_AGENTL_INFO_BM_AGENT',  HOST_BM_AGENT .  '/agent-api/info' );

/////////////////////////////////////////////////////////////////////////////////////
/////                       		Memcache	                	/////
/////////////////////////////////////////////////////////////////////////////////////

// Memcache
// 所有应用资料
define('MC_APP_INFO',  'db_app_info' );
define('MC_APP_INFO_CACHE_TIME',  1800 );

define('MC_LOTTERY_METHOD',  'db_lottery_method' );
define('MC_LOTTERY_METHOD_CACHE_TIME',  1800 );

define('MC_LOTTERY_LIST',  'db_lottery_list' );
define('MC_LOTTERY_LIST_CACHE_TIME',  1800 );

define('MC_METHOD_LIST',  'db_method_list' );
define('MC_METHOD_LIST_CACHE_TIME',  1800 );


/* End of file constants.php */
/* Location: ./application/config/constants.php */
