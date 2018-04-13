<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>用户注册成功</title>
<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, minimal-ui">
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<link rel="stylesheet" href="{$path_img}/images/register_mobile/mobile.css" />
<script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.min.js"></script>
</head>
<body class="bg-success">
	<h4><strong><span id="userName"></span></strong>恭喜您注册成功</h4>
	<h5>
		<strong>立即体验中大奖！</strong>
		<a href="http://static.28dian.cc/static/dbyl/">下载宝开娱乐移动端</a>
	</h5>
	{literal}
	
	<script type="text/javascript">
		(function($){				
			var username=localStorage.getItem("userName_Mobile");
			if(username!='' && username != null){  
			
				$('#userName').text(username);
				
				localStorage.removeItem("userName_Mobile");      
			}
		})(jQuery);
	</script>
	{/literal}
</body>
</html>