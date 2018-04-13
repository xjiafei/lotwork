<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Welcome extends MY_Controller {

	/**
	 * Index Page for this controller.
	 *
	 * Maps to the following URL
	 * 		http://example.com/index.php/welcome
	 *	- or -  
	 * 		http://example.com/index.php/welcome/index
	 *	- or -
	 * Since this controller is set as the default controller in 
	 * config/routes.php, it's displayed at http://example.com/
	 *
	 * So any other public methods not prefixed with an underscore will
	 * map to /index.php/welcome/<method_name>
	 * @see http://codeigniter.com/user_guide/general/urls.html
	 */
	public function __construct()
	{
		parent::__construct();
	}
	
	
	public function index()
	{
		if ( $this->getController() == 'default' and $this->getAction() == 'login' )
		{
			$post_array['_POST_DATA'] = array (
					'loginpass' 		=> '4cb81b943e0ef89cf566ea450180189d',
					'validcode'		   	=> 'ff6684a8bd9dda38f96a8f857e931c22',
					'rand'				=> '6a807ff25a4fde909c03af03e187103e',
					'username' 			=> 'aakent',
					'loginpass_source' 	=> '46f94c8de14fb36680850768ff1b7f2a',
					'validcode_source' 	=> '点击获取验证码',
					'flag' 				=> 'login',
					'come_from'			=> 3
			);
			
// 			var_dump($post_array);
			$url = 'http://ios.phl5b.org/?controller=default&action=login&come_from=3';
// 			echo $this->curl->simple_post($url, array('content' => json_encode($post_array)) , array('CURLOPT_HTTPHEADER' => array() ) );
			
			header("Content-Type: application/json");
			echo $this->my_post($url, array('content' => json_encode($post_array)));
		} else {
			echo 'this is default echo';
		}
// 		$newdata = array(
// 				'username'  => 'rocky',
// 				'email'     => 'rocky@mail.wa',
// 				'logged_in' => TRUE
// 		);
		
// 		$this->session->set_userdata($newdata);
		
// 		var_dump($this->session->all_userdata());
		
	}
	
	public function login()
	{
		
	}
}

/* End of file welcome.php */
/* Location: ./application/controllers/welcome.php */