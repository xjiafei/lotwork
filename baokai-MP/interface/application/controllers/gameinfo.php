<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

/**
 * 文件 : /controller/gameinfo.php
 *  参与游戏列表，游戏详情，追号列表，追号详情，撤单，撤追号弹
 */

class Gameinfo extends MY_Controller {

            public function __construct()
            {
                parent::__construct();
            }
            
            public function cancelGame() {
                    $defineName = strtoupper(__CLASS__ . '_' .  __FUNCTION__);
                    $url = $this->getTransferUrl($defineName);
                    $url .= $_POST['content']['id'];
                    if ($url) {
                            $this->echoPost($url, $_POST['content']);
                    }
            }
            
            public function cancelTask() {
                    $defineName = strtoupper(__CLASS__ . '_' .  __FUNCTION__);
                    $url = $this->getTransferUrl($defineName);
                    if ($url) {
                            $this->echoPost($url, $_POST['content']);
                    }
            }
            
    
    
    
}

/* End of file welcome.php */
/* Location: ./application/controllers/welcome.php */