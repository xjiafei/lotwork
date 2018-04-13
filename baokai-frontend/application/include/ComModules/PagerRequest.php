<?php

class PagerRequest extends BaseModel{
	
	public function getDefaultMap(){
		return array(
				"startNo"=> 0,
				"endNo"=> 1,
		);
	}
}
