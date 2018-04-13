<?php
define('AES128_IV', "0000000000000000");;
		

if($_POST["action"] == "md5")
{
	echo md5($_POST["data"]);
}
else if($_POST["action"] == "Aes128Encode")
{
	echo bin2hex(mcrypt_encrypt(MCRYPT_RIJNDAEL_128, getKey($_POST["is_product"], $_POST["app_id"]), $_POST["data"], MCRYPT_MODE_CBC, AES128_IV));
}
else if($_POST["action"] == "Aes128Decode")
{
	echo mcrypt_decrypt(MCRYPT_RIJNDAEL_128, getKey($_POST["is_product"], $_POST["app_id"]), pack("H*", $_POST["data"]),MCRYPT_MODE_CBC, AES128_IV);
}
else if($_POST["action"] == "var_dump")
{
	//$data = json_decode($_POST["data"]);
	$data =  json_decode($_POST["data"],true);
	//echo "111";
	var_dump( $data);
}
else if($_POST["action"] == "req")
{
	$ary = array();
	$ary["start"] = date('Y-m-d h:i:s', strtotime('+8 hours'));
	$ary["data"] = http_req($_POST["url"], "content=" . $_POST["data"]);
	$ary["end"] = date('Y-m-d h:i:s', strtotime('+8 hours'));
	echo json_encode($ary);
	
	//echo http_req($_POST["url"], "content=" . $_POST["data"]);
}



function http_req($url, $post_string) {
	$ch = curl_init();
	curl_setopt($ch, CURLOPT_URL, $url);
	curl_setopt($ch, CURLOPT_POSTFIELDS, $post_string);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$data = curl_exec($ch);
	curl_close($ch);
	
	return $data;
}

function getKey($is_product, $app_id)
{
	$key = "";
	if($is_product == "1")
	{
		if($app_id == "1")
		{
			$key = "af90b53c9105b23c3d55c782e4b6c285";
		}
		else if($app_id == "2")
		{
			$key = "03a883877b520b2078db63deab6fe0f6";
		}
		else if($app_id == "3")
		{
			$key = "51905f70afcb51de711ba141e8e50f39";
		}
		else if($app_id == "4")
		{
			$key = "3cb8ff3e9accde8a964dfc706594b792";
		}
		else if($app_id == "5")
		{
			$key = "15e9da4c045e22db0e353e1a3e0db1e9";
		}
		else if($app_id == "6")
		{
			$key = "5c1eb23a588e33e687947f92335cb43b";
		}
		else if($app_id == "7")
		{
			$key = "234f01257821d0cd63ba4f6971f8b0e5";
		}
		else if($app_id == "8")
		{
			$key = "5089fa881630360a9b3361469c1a0c5d";	// BM
		}
		else if($app_id == "9")
		{
			$key = "91d48cc935186ecc04b2c1e93cf2f6bd";	// 9_product
		}
		else if($app_id == "10")
		{
			$key = "0d9767d3204f554549fb64a23f48c0e2";	// 10_product
		}
		else if($app_id == "11")
		{
			$key = "39dce61849899a00185d7120b181cf28";	// 11_product
		}
		else if($app_id == "12")
		{
			$key = "9e8053ebaf57633c5ae065cf21b04915";	// 12_product
		}
		else if($app_id == "13")
		{
			$key = "0c2b36dea476274fdcc1f47bfc6e7599";	// 13_product
		}
		else if($app_id == "14")
		{
			$key = "e62fb1cd207b957067e3d36df5ea6cdd";	// 14_product
		}
		
		
	}
	else
	{
		if($app_id == "1")
		{
			$key = "93b9bf05ce0894326dd03405a967341e";
		}
		else if($app_id == "2")
		{
			$key = "aca68ea2651edeb2d3817e76b5d611d1";
		}
		else if($app_id == "3")
		{
			$key = "a2e70691ee545ed423166632ea318c10";
		}
		else if($app_id == "4")
		{
			$key = "b036a85d73eb180d716fe9cb76147ab6";
		}
		else if($app_id == "5")
		{
			$key = "141aff1bfb749f52d024ba5cbcdaeeb5";
		}
		else if($app_id == "6")
		{
			$key = "84c0c64bdc9cbe5649322b459794837b";
		}
		else if($app_id == "7")
		{
			$key = "cf5d3659e2038cbd27da1bcd3c3c3f60";
		}
		else if($app_id == "8")
		{
			$key = "084243855820f9ca47f466f645784636";	// bm
		}
		else if($app_id == "9")
		{
			$key = "a98f6977bb7637d3111cbb1d31bca279";	// 9_test
		}
		else if($app_id == "10")
		{
			$key = "e3ad9b1ec40d036a01f4fc609c9c55b5";	// 10_test
		}
		else if($app_id == "11")
		{
			$key = "eb029096df5aab87d34a849559fde7e9";	// 11_test
		}
		else if($app_id == "12")
		{
			$key = "f563e6378d614c54c33c3b22b7ee43f6";	// 12_test
		}
		else if($app_id == "13")
		{
			$key = "e637ce5da58d2bf6cd38ce2ef56d69dd";	// 13_test
		}
		else if($app_id == "14")
		{
			$key = "91130be6141799ecc8641988fdfaed50";	// 14_test
		}
		
	}
	
	return $key;
}


?>