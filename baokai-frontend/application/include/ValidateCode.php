<?php

class ValidateCode
{
	/*验证码字符设置 array 包括以下设置
	 * characters    string  允许的字符 ，每个字符用,隔开
     * length        int     验证码长度 
     * deflect       boolean 字符是否偏转 
     * multicolor    boolean 字符是否彩色
	 */
	private $aCode = array();

	/*字体信息
	 *   space  int     字符间隔 (px) 
     *   size   int     字体大小 (px) 
     *   left   int     第一个字符距离图像最左边的象素 (px) 
     *   top    int     字符距离图像最上边的象素 (px) 
     *   file   string  字体文件的路径 
	 */
	private $aFont = array();
	
	/*图像信息
	 *   type   string  图像类型 (选项: 'png', 'gif', 'wbmp', 'jpg') 默认为'png'
     *   width  int     图像宽 (px) 
     *   height int     图像高 (px)
	 */
	private $aImage = array();
	
	/*干扰信息
	 *  type    string  干扰类型 (选项: false, 'point', 'line') 
     *  density string  干扰密度 (选项: 'normal', 'muchness', 'fewness') 
	 */
	private $aMolestation = array();
	
	//背景色 (RGB)  r: 红色 (0 - 255) g: 绿色 (0 - 255) b: 蓝色 (0 - 255)
	private $aBgColor = array();
	
	//前景色 (RGB)  r: 红色 (0 - 255) g: 绿色 (0 - 255) b: 蓝色 (0 - 255)
	private $aFgColor = array();
	
	//字体文件默认路径
	private $sFontDir = '';
	
	//生成的验证码字符信息(保存生成的验证码，用于验证输入匹配)
	private $sAuthCode = '';
	
	/*
	 * 构造函数
	 */
	function __construct()
	{
	    
		if( FALSE != ($temp = SITE_ROOT.'/images/') )
		{//使用全局字体文件路径
			$this->sFontDir = $temp;
			unset( $temp );
		}
		else 
		{//默认路径
			$this->sFontDir = SITE_ROOT.'/images/';
		}
		
		$this->setCode(); 
        $this->setMolestation(); 
        $this->setImage(); 
        $this->setFont(); 
        $this->setBgColor();
        
	}
	
	/** 
      * 获取产生的authcode
      * @return string 
    */ 
    public function getcode() 
    { 
        return $this->sAuthCode; 
    }
	
	/** 
     * 绘制图像 
     *  
     * @access  public 
     * @param   string  $sFilename 文件名, 留空表示输出到浏览器 
     * @return  void 
     */ 
    public function paint( $filename='' )  
    { 
        // 创建图像 
        $rsIm = imagecreatetruecolor( $this->aImage['width'], $this->aImage['height'] ); 

        // 设置图像背景 
		$temp_bgColor = imagecolorallocate( $rsIm, $this->aBgColor['r'], $this->aBgColor['g'], $this->aBgColor['b'] ); 
        imagefilledrectangle( $rsIm, 0, 0, $this->aImage['width'],  $this->aImage['height'], $temp_bgColor ); 

        // 生成验证码相关信息 
        $temp_code = $this->generateCode();
         
        // 向图像中写入字符 
        $temp_num = count( $temp_code ); 
        $temp_currentLeft = $this->aFont['left']; 
        $temp_currentTop  = $this->aFont['top'];
        $temp_theCode = ''; 
        for ($i=0; $i<$temp_num; $i++)  
        { 
            $temp_fontCcolor = imagecolorallocate( $rsIm, 
            									   $temp_code[$i]['color']['r'],
            									   $temp_code[$i]['color']['g'],
            									   $temp_code[$i]['color']['b'] );
            									    
            imagettftext( $rsIm, $this->aFont['size'], $temp_code[$i]['angle'],  
                         $temp_currentLeft, $temp_currentTop, $temp_fontCcolor,  
                         $this->aFont['file'], $temp_code[$i]['char'] ); 
                         
            $temp_currentLeft += $this->aFont['size'] + $this->aFont['space']; 

            $temp_theCode .= $temp_code[$i]['char']; 
        } 
        $this->sAuthCode = $temp_theCode;//保存authcode 

        // 绘制图像干扰 
        $this->paintMolestation( $rsIm ); 

        // 输出 图象到文件
        if( isset($filename) && $filename!='' )  
        { 
            $this->aImage['func']( $rsIm, $filename.$this->aImage['type'] ); 
        } 
        else  
        { 
            header("Cache-Control: no-cache, must-revalidate"); 
            header("Content-type: ".$this->aImage['mime']); 
            $this->aImage['func']( $rsIm ); 
        } 
        imagedestroy( $rsIm ); 
    }
	
	/** 
     * 生成随机验证码 
     *  
     * @access  private 
     * @return  array  生成的验证码 
     */ 
    private function generateCode()  
    {// 创建允许的字符串 
        $temp_characters = explode( ',', $this->aCode['characters'] ); 
        $temp_num = count( $temp_characters ); 
        for( $i=0; $i<$temp_num; $i++ )  
        { 
            if( substr_count($temp_characters[$i], '-') > 0 )  
            {//设定单个范围 
                $temp_characterRange = explode('-', $temp_characters[$i]); 
                for( $j=ord($temp_characterRange[0]); $j<=ord($temp_characterRange[1] ); $j++)  
                { 
                    $temp_arrayAllow[] = chr($j); 
                } 
            } 
            else  
            { 
                $temp_arrayAllow[] = $temp_characters[$i]; 
            } 
        } 
        $temp_index = 0;
        while( list($key, $val) = each($temp_arrayAllow) )  
        { 
            $array_allow_tmp[$temp_index] = $val; 
            $temp_index ++; 
        } 
        $temp_arrayAllow = $array_allow_tmp; 

        // 生成随机字符串 
        //mt_srand((double)microtime() * 1000000); //播种随机数
        $temp_code = array(); 
        $temp_index = 0; 
        $i = 0; 
        while( $i < $this->aCode['length'] )  
        { 
            $temp_index = mt_rand( 0, count($temp_arrayAllow) - 1 ); 
            $temp_code[$i]['char'] = $temp_arrayAllow[$temp_index]; 
            if( $this->aCode['deflect'] )  
            { 
                $temp_code[$i]['angle'] = mt_rand(-30, 30); 
            } 
            else 
            { 
                $temp_code[$i]['angle'] = 0; 
            } 
            if ($this->aCode['multicolor'])  
            { 
                $temp_code[$i]['color']['r'] = mt_rand(0, 255); 
                $temp_code[$i]['color']['g'] = mt_rand(0, 255); 
                $temp_code[$i]['color']['b'] = mt_rand(0, 255); 
            } else 
            { 
                $temp_code[$i]['color']['r'] = $this->aFgColor['r']; 
                $temp_code[$i]['color']['g'] = $this->aFgColor['g']; 
                $temp_code[$i]['color']['b'] = $this->aFgColor['b']; 
            } 
            $i++; 
        } 
        return $temp_code; 
    } 
	
	/** 
     * 绘制图像干扰 
     *  
     * @access  private 
     * @param   resource	$rsIm 图像资源 
     * @return  void 
     */ 
    private function paintMolestation( &$rsIm )  
    { 
        // 总象素 
        $temp_numOfPels = ceil( $this->aImage['width']*$this->aImage['height']/5 ); 
        switch( $this->aMolestation['density'] )  
        { 
            case 'fewness': 
                $temp_density = ceil( $temp_numOfPels / 3 ); 
                break; 
            case 'muchness': 
                $temp_density = ceil( $temp_numOfPels / 3 * 2 ); 
                break; 
            case 'normal': 
                $temp_density = ceil( $temp_numOfPels / 2 );
                break; 
            default: 
            	$temp_density = ceil( $temp_numOfPels / 2 );
                break;
        } 

        switch( $this->aMolestation['type'] )  
        { 
            case 'point': 
                $this->paintPoints( $rsIm, $temp_density ); 
                break; 
            case 'line': 
                $temp_density = ceil( $temp_density / 30 ); 
                $this->paintLines( $rsIm, $temp_density ); 
                break; 
            case 'both': 
                $temp_density = ceil( $temp_density / 2 ); 
                $this->paintPoints( $rsIm, $temp_density ); 
                $temp_density = ceil( $temp_density / 30 ); 
                $this->paintLines( $rsIm, $temp_density ); 
                break; 
            default: 
                break; 
        } 
    }
	
	/** 
     * 画点 
     *  
     * @access  private 
     * @param   resource	$rsIm 图像资源 
     * @param   int			$iQuantity 点的数量（密度） 
     * @return  void 
     */ 
    private function paintPoints( &$rsIm, $iQuantity )  
    { 
        //mt_srand( (double)microtime()*1000000 ); 

        for( $i=0; $i<$iQuantity; $i++ )  
        { 
            $temp_randcolor = imagecolorallocate( $rsIm, mt_rand(0,255),  
                                            mt_rand(0,255), mt_rand(0,255) );
                                             
            imagesetpixel( $rsIm, mt_rand(0, $this->aImage['width']),  
                          mt_rand(0, $this->aImage['height']), $temp_randcolor); 
        } 
    }
    
    /** 
     * 画线 
     *  
     * @access  private 
     * @param   resource	$rsIm 图像资源 
     * @param   int      	$iQuantity 点的数量（密度） 
     * @return  void 
     */ 
    private function paintLines( &$rsIm, $iQuantity )  
    { 
		//mt_srand( (double)microtime()*1000000 ); 

        for( $i=0; $i<$iQuantity; $i++ )  
        { 
            $temp_randcolor = imagecolorallocate( $rsIm, mt_rand(0,255),  
                                            mt_rand(0,255), mt_rand(0,255) ); 
                                            
            imageline( $rsIm, mt_rand(0, $this->aImage['width']),  
                      mt_rand(0, $this->aImage['height']),  
                      mt_rand(0, $this->aImage['width']),  
                      mt_rand(0, $this->aImage['height']), $temp_randcolor ); 
        } 
    }
	
	 /** 
     * 设置验证码 
     *  
     * @access  public 
     * @param   array	$aCode   字符信息 
     * characters    string  允许的字符 
     * length        int     验证码长度 
     * deflect       boolean 字符是否偏转 
     * multicolor    boolean 字符是否彩色 
     * @return  void 
     */
	public function setCode( $aCode='' )
	{
		if( is_array($aCode) )
		{//用户指定设置
			if( !isset($aCode['characters']) || !is_string($aCode['characters']) )  
            { 
                $aCode['characters'] = '0-9'; 
            } 
            if( !isset($aCode['length']) || !(is_integer($aCode['length']) || $aCode['length']<=0) )  
            { 
                $aCode['length'] = 4; 
            } 
            if( !isset($aCode['deflect']) || !is_bool($aCode['deflect']) )  
            { 
                $aCode['deflect'] = TRUE; 
            } 
            if( !isset($aCode['multicolor']) || !is_bool($aCode['multicolor']) )  
            { 
                $aCode['multicolor'] = FALSE; 
            }
		}
		else
		{//默认设置
			$aCode = array( 'characters'=>'0-9', 'length'=>4, 'deflect'=>TRUE, 'multicolor'=>FALSE );
		}
		$this->aCode = $aCode;
	}
	
	/** 
     * 设置字体信息 
     *  
     * @access  public 
     * @param   array $aFont  字体信息 
     *   space  int     字符间隔 (px) 
     *   size   int     字体大小 (px) 
     *   left   int     第一个字符距离图像最左边的象素 (px) 
     *   top    int     字符距离图像最上边的象素 (px) 
     *   file   string  字体文件的路径 
     * @return  void 
     */ 
    public function setFont( $aFont='' )  
    { 
        if( is_array($aFont) ) 
        {//用户指定设置
            if( !isset($aFont['space']) || !is_integer($aFont['space']) || $aFont['space']<0 ) 
            { 
                $aFont['space'] = 5; 
            } 
            if( !isset($aFont['size']) || !is_integer($aFont['size']) || $aFont['size']<0 ) 
            { 
                $aFont['size'] = 12; 
            } 
            if( !isset($aFont['left']) || !is_integer($aFont['left']) || $aFont['left']<0 ||  
                $aFont['left']>$this->aImage['width'] )  
            { 
                $aFont['left'] = 5; 
            } 
            if( !isset($aFont['top']) || !is_integer($aFont['top']) || $aFont['top']<0 ||  
                $aFont['top']>$this->aImage['height'] )  
            { 
                $aFont['top'] = $this->aImage['height'] - 5; 
            } 
            if( !isset($aFont['file']) || !file_exists($aFont['file']) )  
            { 
                $aFont['file'] = $this->sFontDir . 'verdana.ttf'; 
            }  
        } 
        else 
        {//默认设置
            $aFont = array('space'=>5, 'size'=>12, 'left'=>5,  
                                'top'=>15,  
                                'file'=>$this->sFontDir . 'verdana.ttf'); 
        }
        $this->aFont = $aFont; 
    }
    
    /** 
     * 设置图像信息 
     *  
     * @access  public 
     * @param   array $aImage  图像信息 
     *   type   string  图像类型 (选项: 'png', 'gif', 'wbmp', 'jpg') 默认为'png'
     *   width  int     图像宽 (px) 
     *   height int     图像高 (px) 
     * @return  void 
     */
	public function setImage( $aImage='' )  
    { 
        if( is_array($aImage) ) 
        { 
            if( !isset($aImage['width']) || !is_integer($aImage['width']) || $aImage['width'] <= 0 )  
            { 
                $aImage['width'] = 70; 
            } 
            if( !isset($aImage['height']) || !is_integer($aImage['height']) || $aImage['height'] <= 0 )  
            { 
                $aImage['height'] = 20; 
            } 
            if( !isset($aImage['type']) )
            {
            	$aImage['type'] = 'png';
            } 
            $temp_information = $this->getImageType( $aImage['type'] ); 
            if( is_array($temp_information) )  
            { 
                $aImage['mime'] = $temp_information['mime']; 
                $aImage['func'] = $temp_information['func']; 
            }
            else  
            { 
                $aImage['type'] = 'png'; 
                $temp_information = $this->getImageType('png'); 
                $aImage['mime'] = $temp_information['mime']; 
                $aImage['func'] = $temp_information['func']; 
            } 
        }
        else
        { 
            $temp_information = $this->getImageType( 'png' ); 
            $aImage = array( 
                'type'=>'png',  
                'mime'=>$temp_information['mime'],  
                'func'=>$temp_information['func'],  
                'width'=>70,  
                'height'=>20); 
        }
        $this->aImage = $aImage; 
    }
	
    /** 
     * 获取图像类型 
     *  
     * @access  private 
     * @param   string $extension 扩展名 
     * @return  [mixed] 错误时返回 false 
     */ 
    private function getImageType( $sExtension )  
    {
    	$temp_information = array(); 
        switch( strtolower($sExtension) )  
        { 
            case 'png': 
                $temp_information['mime'] = image_type_to_mime_type(IMAGETYPE_PNG); 
                $temp_information['func'] = 'imagepng'; 
                break; 
            case 'gif': 
                $temp_information['mime'] = image_type_to_mime_type(IMAGETYPE_GIF); 
                $temp_information['func'] = 'imagegif'; 
                break; 
            case 'wbmp': 
                $temp_information['mime'] = image_type_to_mime_type(IMAGETYPE_WBMP); 
                $temp_information['func'] = 'imagewbmp'; 
                break; 
            case 'jpg': 
                $temp_information['mime'] = image_type_to_mime_type(IMAGETYPE_JPEG); 
                $temp_information['func'] = 'imagejpeg'; 
                break; 
            case 'jpeg': 
                $temp_information['mime'] = image_type_to_mime_type(IMAGETYPE_JPEG); 
                $temp_information['func'] = 'imagejpeg'; 
                break; 
            case 'jpe': 
                $temp_information['mime'] = image_type_to_mime_type(IMAGETYPE_JPEG); 
                $temp_information['func'] = 'imagejpeg'; 
                break; 
            default: 
                $temp_information = FALSE; 
        } 
        return $temp_information; 
    }
    
    /** 
     * 设置干扰信息 
     *  
     * @access  public 
     * @param   array $aMolestation  干扰信息 
     *  type    string  干扰类型 (选项: 'both', 'point', 'line') 
     *  density string  干扰密度 (选项: 'normal', 'muchness', 'fewness') 
     * @return  void 
     */ 
    public function setMolestation( $aMolestation='' )  
    { 
        if( is_array($aMolestation) )  
        { 
            if( !isset($aMolestation['type']) ||  
                ($aMolestation['type']!='point' &&  
                 $aMolestation['type']!='line' &&  
                 $aMolestation['type']!='both') )  
            { 
                $aMolestation['type'] = 'point'; 
            } 
            if( !isset($aMolestation['density']) ||  
                ($aMolestation['density']!='normal' &&  
                 $aMolestation['density']!='muchness' &&  
                 $aMolestation['density']!='fewness') )  
            { 
                $aMolestation['density'] = 'normal'; 
            }  
        } 
        else  
        { 
            $aMolestation = array( 'type'    => 'point', 'density' => 'normal' ); 
        }
        $this->aMolestation = $aMolestation; 
    }
	
	/** 
     * 设置背景色 
     *  
     * @access  public 
     * @param   array $aColor  RGB 颜色 
     *  r	int	红色
     *  g	int	绿色
     *  b	int	蓝色
     * @return  void 
     */ 
    public function setBgColor( $aColor='' )  
    { 
        if( is_array($aColor) )
        {
        	if( !isset($aColor['r']) || !is_integer($aColor['r']) ||
        		 $aColor['r'] < 0 || $aColor['r'] > 255 )
        	{
        		$aColor['r'] = 255;
        	}
        	if( !isset($aColor['g']) || !is_integer($aColor['g']) ||
        		 $aColor['g'] < 0 || $aColor['g'] > 255 )
        	{
        		$aColor['g'] = 255;
        	}
        	if( !isset($aColor['b']) || !is_integer($aColor['b']) ||
        		 $aColor['b'] < 0 || $aColor['b'] > 255 )
        	{
        		$aColor['b'] = 255;
        	}
        }  
        else  
        { 
            $aColor = array('r'=>255,'g'=>255,'b'=>255); 
        } 
		$this->aBgColor = $aColor;
		
        // 设置默认的前景色, 与背景色相反 
        $temp_fgcolor = array( 
            'r'=>255-$this->aBgColor['r'],  
            'g'=>255-$this->aBgColor['g'],  
            'b'=>255-$this->aBgColor['b'] 
        ); 
        $this->setFgColor( $temp_fgcolor ); 
    }
    
    /** 
     * 设置前景色 
     *  
     * @access  private 
     * @param   array   $aColor RGB 颜色 
     * @return  void 
     */ 
    public function setFgColor( $aColor )  
    { 
    	if( is_array($aColor) )
        {
        	if( !isset($aColor['r']) || !is_integer($aColor['r']) ||
        		 $aColor['r'] < 0 || $aColor['r'] > 255 )
        	{
        		$aColor['r'] = 255;
        	}
        	if( !isset($aColor['g']) || !is_integer($aColor['g']) ||
        		 $aColor['g'] < 0 || $aColor['g'] > 255 )
        	{
        		$aColor['g'] = 255;
        	}
        	if( !isset($aColor['b']) || !is_integer($aColor['b']) ||
        		 $aColor['b'] < 0 || $aColor['b'] > 255 )
        	{
        		$aColor['b'] = 255;
        	}
        }
        else  
        { 
            $aColor = array('r'=>0,'g'=>0,'b'=>0); 
        }
        $this->aFgColor = $aColor; 
    }
}
?>