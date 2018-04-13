<?php

$handle = fopen("counter.csv", "r");
if ($handle) {
	
	$user_id = "";
	while (($line = fgets($handle)) !== false) {
        	// process the line read.
        	
		$words = explode(",", $line);
		
		
		$device = "";
		if($words[1] == "1")
		{
			$device = "iphone";
		}
		else if($words[1] == "2")
		{
			$device = "android";
		}
		
		
		
		if($user_id != $words[0])
		{
			echo $words[0] . "," . $device . "," . $words[2] . "," . $words[3] . "," . $words[4] . "<br/>";
		}
		$user_id = $words[0];
	}
} else {
    // error opening the file.
} 
fclose($handle);


?>