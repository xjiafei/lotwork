<?php
class PluginImgcode extends Zend_Controller_Plugin_Abstract {  
    public $word;  
    public $imgdir;  
    public $imgname;  
    public $suffix;  
    public function __construct(){  
    //Zend_Loader::loadClass('ImgCode'); 
	$dir=SITE_ROOT;
	 
    $codeSession = new Zend_Session_Namespace('imgcode'); //在默认构造函数里实例化   
    $captcha = new ImgCode(array('font'=>$dir.'/images/verdana.ttf', //字体文件路径  
               'fontsize'=>24, //字号  
               'imgdir'=>$dir.'/images/captcha/', //验证码图片存放位置  
               'session'=>$codeSession, //验证码session值  
               'width'=>120, //图片宽  
               'height'=>50,   //图片高  
               'wordlen'=>4 )); //字母数  
      $captcha->setExpiration(5);  //每5秒  
      $captcha->setGcFreq(3);  //百分百删除旧文件  
      $captcha->generate(); //生成图片  
      $codeSession->word = $captcha->getWord();  
      $this->imgdir = $captcha->getImgdir(); //图像路径  
      $this->imgname = $captcha->getId(); //获取文件名，md5编码  
      $this->word = $captcha->getWord(); //认证码  
      $this->suffix= $captcha->getSuffix();  
    }  
    public function getImgurl(){  
        return $this->imgdir.$this->imgname.$this->suffix;  
    }  
    public function getImgname(){  
        return $this->imgname;  
    }  
    public function getWord(){  
        return $this->word;  
    }  
}  
?>