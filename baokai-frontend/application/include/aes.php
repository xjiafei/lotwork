<?php
class aes { 
	// CRYPTO_CIPHER_BLOCK_SIZE 32
	private $_secret_key = 'default_secret_key';
	private $_iv = '';
	public function setKey($key) {
		$this->_secret_key = $key;
	}
	public function setIv($iv){
		$this->_iv = $iv;
	}
	public function encode($data) {
		$td = mcrypt_module_open ( MCRYPT_RIJNDAEL_256, '', MCRYPT_MODE_CBC, '' );
		$iv = mcrypt_create_iv ( mcrypt_enc_get_iv_size ( $td ), MCRYPT_RAND );
		$iv = $this->_iv;
		mcrypt_generic_init ( $td, $this->_secret_key, $iv );
		$encrypted = mcrypt_generic ( $td, $data );
		mcrypt_generic_deinit ( $td );
		return $iv . $encrypted;
	}
	public function decode($data) {
		$td = mcrypt_module_open ( MCRYPT_RIJNDAEL_256, '', MCRYPT_MODE_CBC, '' );
		$iv = mb_substr ( $data, 0, 32, 'latin1' );
		$iv = $this->_iv;
		mcrypt_generic_init ( $td, $this->_secret_key, $iv );
		$data = mb_substr ( $data, 32, mb_strlen ( $data, 'latin1' ), 'latin1' );
		$data = mdecrypt_generic ( $td, $data );
		mcrypt_generic_deinit ( $td );
		mcrypt_module_close ( $td );
		return trim ( $data );
	}
}
