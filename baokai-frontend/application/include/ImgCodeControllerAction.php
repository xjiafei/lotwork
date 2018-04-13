
<?php 

class ImgCode extends Zend_Captcha_Image
{
protected function _generateImage($id, $word)
    {
        if (!extension_loaded("gd")) {
            require_once SITE_ROOT.'/application/lib/Zend/Form/Exception.php';
            throw new Zend_Form_Exception("Image CAPTCHA requires GD extension");
        }
        if (!function_exists("imagepng")) {
            require_once SITE_ROOT.'/application/lib/Zend/Form/Exception.php';
            throw new Zend_Form_Exception("Image CAPTCHA requires PNG support");
        }
        if (!function_exists("imageftbbox")) {
            require_once SITE_ROOT.'/application/lib/Zend/Form/Exception.php';
            throw new Zend_Form_Exception("Image CAPTCHA requires FT fonts support");
        }
        $font = $this->getFont();
        if (empty($font)) {
            require_once SITE_ROOT.'/application/lib/Zend/Form/Exception.php';
            throw new Zend_Form_Exception("Image CAPTCHA requires font");
        }
       @header("Content-Type:image/png"); //这是我添加的
        $w     = $this->getWidth();
        $h     = $this->getHeight();
        $fsize = $this->getFontSize();
        
        $img_file   = $this->getImgDir() . $id . $this->getSuffix();
        $img        = imagecreatetruecolor($w, $h);
        $text_color = imagecolorallocate($img, 0, 0, 0);
        $bg_color   = imagecolorallocate($img, 255, 255, 255);
        imagefilledrectangle($img, 0, 0, $w-1, $h-1, $bg_color);
        $textbox = imageftbbox($fsize, 0, $font, $word);
        $x = ($w - ($textbox[2] - $textbox[0])) / 2;
        $y = ($h - ($textbox[7] - $textbox[1])) / 2;
        imagefttext($img, $fsize, 0, $x, $y, $text_color, $font, $word);
        
       // generate noise
//        for ($i=0; $i<100; $i++) {
//           imagefilledellipse($img, mt_rand(0,$w), mt_rand(0,$h), 2, 2, $text_color);
//        }
        for($i=0; $i<5; $i++) {
           imageline($img, mt_rand(0,$w), mt_rand(0,$h), mt_rand(0,$w), mt_rand(0,$h), $text_color);
        }
        
        // transformed image
        $img2     = imagecreatetruecolor($w, $h);
        $bg_color = imagecolorallocate($img2, 255, 255, 255);
        imagefilledrectangle($img2, 0, 0, $w-1, $h-1, $bg_color);
        // apply wave transforms
        $freq1 = $this->_randomFreq();
        $freq2 = $this->_randomFreq();
     $freq3 = $this->_randomFreq();
     $freq4 = $this->_randomFreq();
        $ph1 = $this->_randomPhase();
        $ph2 = $this->_randomPhase();
        $ph3 = $this->_randomPhase();
        $ph4 = $this->_randomPhase();
        $szx = $this->_randomSize();
        $szy = $this->_randomSize();

        for ($x = 0; $x < $w; $x++) {
            for ($y = 0; $y < $h; $y++) {
                $sx = $x + (sin($x*$freq1 + $ph1) + sin($y*$freq3 + $ph3)) * $szx;
                $sy = $y + (sin($x*$freq2 + $ph2) + sin($y*$freq4 + $ph4)) * $szy;
   
                if ($sx < 0 || $sy < 0 || $sx >= $w - 1 || $sy >= $h - 1) {
                    continue;
                } else {
                    $color    = (imagecolorat($img, $sx, $sy) >> 16)         & 0xFF;
                    $color_x  = (imagecolorat($img, $sx + 1, $sy) >> 16)     & 0xFF;
                    $color_y  = (imagecolorat($img, $sx, $sy + 1) >> 16)     & 0xFF;
                    $color_xy = (imagecolorat($img, $sx + 1, $sy + 1) >> 16) & 0xFF;
                }
                if ($color == 255 && $color_x == 255 && $color_y == 255 && $color_xy == 255) {
                    // ignore background
                    continue;
                } elseif ($color == 0 && $color_x == 0 && $color_y == 0 && $color_xy == 0) {
                    // transfer inside of the image as-is
                    $newcolor = 0;
                } else {
                    // do antialiasing for border items
                    $frac_x  = $sx-floor($sx);
                    $frac_y  = $sy-floor($sy);
                    $frac_x1 = 1-$frac_x;
                    $frac_y1 = 1-$frac_y;
                    $newcolor = $color    * $frac_x1 * $frac_y1
                              + $color_x  * $frac_x  * $frac_y1
                              + $color_y  * $frac_x1 * $frac_y
                              + $color_xy * $frac_x  * $frac_y;
                }
                imagesetpixel($img2, $x, $y, imagecolorallocate($img2, $newcolor, $newcolor, $newcolor));
            }
        }
      
        // generate noise
//        for ($i=0; $i<100; $i++) {
//            imagefilledellipse($img2, mt_rand(0,$w), mt_rand(0,$h), 2, 2, $text_color);
//        }
//        for ($i=0; $i<5; $i++) {
//           imageline($img2, mt_rand(0,$w), mt_rand(0,$h), mt_rand(0,$w), mt_rand(0,$h), $text_color);
//        }
        
      imagepng($img2); //这里我去掉了 $img_file 这个参数
        imagedestroy($img);
        imagedestroy($img2);
    }
}