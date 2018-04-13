<?php
class uploadM {
	var $saveName; // 保存名
	var $savePath; // 保存路径
	var $fileFormat = array (
			'gif',
			'jpg',
			'doc',
			'application/octet-stream' 
	); // 文件格式&MIME限定
	var $overwrite = 0; // 覆盖模式
	var $maxSize = 0; // 文件最大字节
	var $ext; // 文件扩展名
	var $thumb = 0; // 是否生成缩略图
	var $thumbWidth = 130; // 缩略图宽
	var $thumbHeight = 130; // 缩略图高
	var $thumbPrefix = "_thumb_"; // 缩略图前缀
	var $errno; // 错误代号
	var $returnArray = array (); // 所有文件的返回信息
	var $returninfo = array (); // 每个文件返回信息
	                          
	// 构造函数
	                          // @param $savePath 文件保存路径
	                          // @param $fileFormat 文件格式限制数组
	                          // @param $maxSize 文件最大尺寸
	                          // @param $overwriet 是否覆盖 1 允许覆盖 0 禁止覆盖
	function Upload($savePath, $fileFormat = '', $maxSize = 0, $overwrite = 0) {
		$this->setSavepath ( $savePath );
		$this->setFileformat ( $fileFormat );
		$this->setMaxsize ( $maxSize );
		$this->setOverwrite ( $overwrite );
		$this->setThumb ( $this->thumb, $this->thumbWidth, $this->thumbHeight );
		$this->errno = 0;
	}
	
	// 上传
	// @param $fileInput 网页Form(表单)中input的名称
	// @param $changeName 是否更改文件名
	function run($fileInput, $changeName = 1) {
		if (isset ( $_FILES [$fileInput] )) {
			$fileArr = $_FILES [$fileInput];
			if (is_array ( $fileArr ['name'] )) { // 上传同文件域名称多个文件
				for($i = 0; $i < count ( $fileArr ['name'] ); $i ++) {
					$ar ['tmp_name'] = $fileArr ['tmp_name'] [$i];
					$ar ['name'] = $fileArr ['name'] [$i];
					$ar ['type'] = $fileArr ['type'] [$i];
					$ar ['size'] = $fileArr ['size'] [$i];
					$ar ['error'] = $fileArr ['error'] [$i];
					$this->getExt ( $ar ['name'] ); // 取得扩展名，赋给$this->ext，下次循环会更新
					$this->setSavename ( $changeName == 1 ? '' : $ar ['name'] ); // 设置保存文件名
					if ($this->copyfile ( $ar )) {
						$this->returnArray [] = $this->returninfo;
					} else {
						$this->returninfo ['error'] = $this->errmsg ();
						$this->returnArray [] = $this->returninfo;
					}
				}
				return $this->errno ? false : true;
			} else { // 上传单个文件
				$this->getExt ( $fileArr ['name'] ); // 取得扩展名
				$this->setSavename ( $changeName == 1 ? '' : $fileArr ['name'] ); // 设置保存文件名
				if ($this->copyfile ( $fileArr )) {
					$this->returnArray [] = $this->returninfo;
				} else {
					$this->returninfo ['error'] = $this->errmsg ();
					$this->returnArray [] = $this->returninfo;
				}
				return $this->errno ? false : true;
			}
			return false;
		} else {
			$this->errno = 10;
			return false;
		}
	}
}