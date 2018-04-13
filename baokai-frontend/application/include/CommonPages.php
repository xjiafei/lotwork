<?php

class CommonPages
{
	//根据参数生成一组翻页数据 
	//$nums 总记录数
	//$currpage 当前页码
	//$abspage 每页显示数
	//jump 当前页码前后显示页码数量
	public static function getPages($nums, $currpage = 1, $abspage = 10, $jump = 7){
                if($nums <=0)
                    return;
		$result = array();
		$max = ceil( $nums/$abspage );
		$min = 1;
		$currpage = $currpage > $max ? $max : $currpage;
		$currpage = $currpage < $min ? $min : $currpage;
		$pre = $currpage - 1 < $min ? $min : $currpage - 1;
		$next = $currpage + 1 > $max ? $max : $currpage + 1;
		$steps = array();
		
		$j = 0;
		for( $i = 1; $i <= $max; $i++){
			$j++;
			if($max > $jump*2){
				if( $currpage - $i > $jump ){
					$prei = $currpage - $jump*2;
					$prei = $prei < $min ? $min : $prei;
					$steps[] = array("index"=>$prei,"text"=>"...");
					$i += $currpage - $jump - 2;
					continue;
				}
				if( $j > $jump*2 && $i > $currpage + $jump ){
					$nexti = ($i + $jump*2) > $max ? $max : ($i + $jump*2);
					$steps[] = array("index"=>$nexti,"text"=>"...");
					break;
				}
			}
	
			$steps[] = array("index"=>$i,"text"=>$i);
		}
		
	
		$result["min"] = array("index"=>$min,"text"=>$min);
		$result["max"] = array("index"=>$max,"text"=>$max);
		
		if( $pre >= 1){
			$result["pre"] = array("index"=>$pre,"text"=>$pre);
		}
		if( $next <= $max){
			$result["next"] = array("index"=>$next,"text"=>$next);
		}
		$result["currpage"] = array("index"=>$currpage,"text"=>$currpage);
		$result["steps"] = $steps;
		$result['count'] = $nums;
              $result['pageSize'] = $abspage;
		
		return $result;
	}
	
	
}




?>













