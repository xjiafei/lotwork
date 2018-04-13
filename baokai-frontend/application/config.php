<?php
$aConfig =  array (
	'site_root' => SITE_ROOT,
	'smarty_dir' => SITE_ROOT."/application/lib/Smarty/",
	'template_dir' => SITE_ROOT."/application/views/",
	'compile_dir' => SITE_ROOT."/application/views/templates_c",
	'plugins_dir' => SITE_ROOT."/application/lib/smarty_plugins",
	'upload_dir'=>SITE_ROOT, //上传根目录 /home/img-server/dynamic
	'caching' => 0,
	'compile_check' => TRUE,
	'ISADMIN' => '', /* [''|TRUE|FALSE] 三个选项,'':前台和后台,TRUE:只有后台,FALSE:只有前台 */
	'timeout' => 3600, /* 超时秒数 */
	'imgroot' => 'http://static.28dian.cc/static',
	'jsroot' => 'http://static.28dian.cc/static',
	'dynamicroot' => '', //动态上传文件服务器路径 http://static.28dian.cc/dynamic
	'host' => '',
	'username' => '',
	'password' => '',
	'database' => '',
	'driver' => '',
	'path_img'=>'../..',
	'test'=>false,
	'seed'=>"xAJ/Df.F@'jk+`\a15/*5T",
	'REDIS_SERVER_F' => "127.0.0.1",
	'REDIS_SERVER' => "127.0.0.1",
	'REDIS_SERVER_S' => "127.0.0.1",
	'FRONTEND_ALIAS' => "FrontEnd_Redis",
	'www_url' => "http://www.28dian.com",
	'JAVA_SERVER' => "http://127.0.0.1",
	
	//每日活動的上架時間
	'daily_activity_begin_time' => "2015-10-06 07:00:00",
	'daily_activity_end_time' => "2015-10-19 23:59:59",
	//每日活動是否執行ip次數檢查
	'daily_activity_check_ip' => true,
	//X 平台 Landing page 的 URL
	'blockfilepath'  =>  SITE_ROOT."/geoip" ,
 	'x_landing_page' => 'http://10.13.22.171/index.html',
    'im_url'=>'http://www.28dian.com/imservice/chat',
    'vip_api'=>'http://op.pge:8888'
); 
$aProductConfig = array();
include_once '/usr/local/conf/product.conf';
$config = array_merge($aConfig, $aProductConfig);
return $config;
