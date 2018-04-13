<?php
class FireFogException extends Exception {
	public function __construct($message=null, $code = 0) {

		parent::__construct($message, $code);

	}

	public function __toString() {

		return "异常原因说明:{ {$this->message}}\n";//.__CLASS__."抛出位置：". $this->getFile()."的".$this->getLine()."行\n";
	}

}