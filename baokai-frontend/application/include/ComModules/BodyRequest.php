<?php

class BodyRequest extends BaseModel{
	
	public function getDefaultMap(){
		return array(
				"pager"=> null,
				"param"=> null,
		);
	}
	
}