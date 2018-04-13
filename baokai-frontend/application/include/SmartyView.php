<?php
class SmartyView extends Zend_View_Abstract
{
	protected $_smarty;

	public function __construct()
	{
		$config = Zend_Registry::get('config');
		
		require_once ($config->smarty_dir.'Smarty.class.php');

		$this->_smarty = new Smarty();
		//var_dump($config->template_dir);
		$this->_smarty->template_dir = $config->template_dir;
		$this->_smarty->compile_dir = $config->compile_dir;
        $this->_smarty->caching = $config->caching;
        $this->_smarty->compile_check = $config->compile_check;
        $this->_smarty->plugins_dir = array($config->smarty_dir.'plugins',
                                            $config->plugins_dir);		
	}

	public function getEngine()
	{
		return $this->_smarty;
	}

	public function __set($key, $val)
	{
		$this->_smarty->assign($key, $val);
	}
	//register_object
	public function __ref($key, $val)
	{
		$this->_smarty->assignbyRef($key, $val);
	}
	public function __get($key)
	{
		return $this->_smarty->get_template_vars($key);
	}

	public function __isset($key)
	{
		return $this->_smarty->get_template_vars($key) != null;
	}

	public function __unset($key)
	{
		$this->_smarty->clear_assign($key);
	}

	public function assign($spec, $value=null)
	{
		if (is_array($spec)) {
			$this->_smarty->assign($spec);
			return;
		}
		$this->_smarty->assign($spec, $value);
	}

	public function clearVars()
	{
		$this->_smarty->clear_all_assign();
	}

	public function render($name)
	{
		return $this->_smarty->fetch(strtolower($name));
	}
	public function display($resource_name, $cache_id = null, $compile_id = null)
	{
	    $this->_smarty->assign("EXETIME", number_format(microtime(true)-START_TIME, 8, '.', ''));
        return $this->_smarty->display($resource_name, $cache_id, $compile_id);
	}

	public function _run() {}
}