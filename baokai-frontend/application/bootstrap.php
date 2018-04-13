<?php
define('START_TIME', microtime(true));
// in here we define the root directory of our site
// is the directory above htdocs
define("SITE_ROOT",  $_SERVER['DOCUMENT_ROOT']);

date_default_timezone_set('Asia/Shanghai');

/* Report all errors directly to the screen for simple diagnostics in the dev environment */
error_reporting(E_ALL & ~E_NOTICE);
//error_reporting(E_ERROR);  //E_ALL track
ini_set('display_startup_errors', 0); //1 track
ini_set('display_errors', 0); //1 track
ini_set("log_errors",1);
ini_set("error_log",SITE_ROOT.'/log/error_log.'.date('Y-m-d').'.log');
require_once  SITE_ROOT.'/application/include/Common.php';  //有待于改进
// Set our include path
$include_path = get_include_path().PATH_SEPARATOR;
$include_path .= SITE_ROOT.'/application/lib'.PATH_SEPARATOR;
$include_path .= SITE_ROOT.'/application/forms'.PATH_SEPARATOR;
$include_path .= SITE_ROOT.'/application/include'.PATH_SEPARATOR;
$include_path .= SITE_ROOT.'/application/include/ComModules'.PATH_SEPARATOR;
$include_path .= SITE_ROOT.'/application/include/Common'.PATH_SEPARATOR;

$matches = NULL;
preg_match("/\/(?P<module>[a-z]*)/i", $_SERVER["REQUEST_URI"], $matches);
switch($matches["module"]){
    case "admin":
    	define("MODULE_NAME",  'admin');
    break;
    default:
    	define("MODULE_NAME",  'default');
    break;
}
$include_path .= getExplorerDir(SITE_ROOT.'/application/models/'.MODULE_NAME);
//$include_path .= SITE_ROOT.'/application/curls'.PATH_SEPARATOR;
set_include_path($include_path);

 
/* Set up autoload so we don't have to explicitely require each Zend Framework class */ 
require_once SITE_ROOT."/application/lib/Zend/Loader/Autoloader.php"; 
Zend_Loader_Autoloader::getInstance()->pushAutoloader(NULL, 'Smarty_' );
Zend_Loader_Autoloader::getInstance()->setFallbackAutoloader(true);
//使用关闭Warnings警告，调试时打开(false)，网站发布时最好设置为true
Zend_Loader_Autoloader::getInstance()->suppressNotFoundWarnings(true);

// load config file
$config = new Zend_Config(require_once 'application/config.php');
Zend_Registry::set('config', $config);
$firefog = new Zend_Config(require_once 'application/urls.php');
Zend_Registry::set('firefog', $firefog);
$urlsfilter = new Zend_Config(require_once 'application/urlsfilter.php');
$blockmode = new Zend_Config(require_once 'application/blockmode.php');
Zend_Registry::set('blockmode', $blockmode);
Zend_Registry::set('urlsfilter', $urlsfilter);


if($config->ISADMIN == TRUE || MODULE_NAME =='admin'){
	$key = 'ANVOAID';
} else {
	$key = 'ANVOID';
}


//$cdb=new Zend_Config('config.php','general'); 
/*
$params = array ('host'     => $config->host,
                 'username' => $config->username,
                 'password' => $config->password,
                 'dbname'   => $config->database);
$db = Zend_Db::factory( $config->driver, $params);     
//$tdb = Zend_Db::factory($cdb->db); 
Zend_Db_Table_Abstract::setDefaultAdapter($db);
$db->query("SET NAMES utf8; ");
*/   

define('JS_ROOT', $config->jsroot);
define('IMG_ROOT', $config->imgroot);
define('IM_URL', $config->im_url);


$locale = new Zend_Locale('zh_CN');
Zend_Registry::set('Zend_Locale', $locale);

// important when developing using modules, so you can have various modules,
// for example an admin module, an e-commerce module and the default module
$vr = new Zend_Controller_Action_Helper_ViewRenderer();
$vr->setView(new SmartyView());
$vr->setNeverRender(true); 
Zend_Controller_Action_HelperBroker::addHelper($vr);
// set up error log
// I am using apache error log file here, probably better to use your own log file
$writer = new Zend_Log_Writer_Stream( $config->site_root.'/log/'.date('Y-m-d') .'.log');
$format = '%timestamp% %priorityName% (%priority%): %message% [%modules%]-[%controller%]-[%action%] '. PHP_EOL;
$formatter = new Zend_Log_Formatter_Simple($format);
$writer->setFormatter($formatter);
$logger = new Zend_Log($writer);
$filter = new Zend_Log_Filter_Priority(Zend_Log::ERR);  //调整日志级别
$logger->addFilter($filter);
Zend_Registry::set('logger',$logger);

//修改session name
session_name($key);
$domain = getdomain(getWebSiteDomain());
session_set_cookie_params(-1,'/',$domain,FALSE,TRUE);

require_once 'application/redis.php' ;
$rediska = new Rediska($app_options);
Zend_Registry::set('rediska', $rediska);

//使用標準的 redis client 物件
$redis_client = new Redis();
$redis_client->connect($config->REDIS_SERVER_S, "6379", "5");
Zend_Registry::set('redis_client', $redis_client);

$saveHandler = new Rediska_Zend_Session_SaveHandler_Redis($session_options);
Zend_Session::setSaveHandler($saveHandler);
//save handler,coz we will use it to modify lifetime while system login successed
Zend_Registry::set('saveHandler', $saveHandler);


// Set the singleton instance of the front controller
$front = Zend_Controller_Front::getInstance(); 
$front->setParam("prefixDefaultModule",true);
$front->setParam('noErrorHandler', false);

$front->setParam('useDefaultControllerAlways', false) /*使用默认index控制器 true,不适用默认控制器*/
	->setControllerDirectory(
		array(
			'default'=>SITE_ROOT.'/application/modules/default/controllers',
			'admin'=>SITE_ROOT.'/application/modules/admin/controllers'
		)
    ) ->addModuleDirectory(SITE_ROOT.'/application/modules') ->throwExceptions(false);  /****关掉异常false 显示异常true****/
$front->registerPlugin(new Zend_Controller_Plugin_ErrorHandler(array('module'=>MODULE_NAME)));

try	{
	$front->dispatch();
} 
catch(Exception $e) {
	// handle exceptions yourself
	echo $e;
	
}



	 