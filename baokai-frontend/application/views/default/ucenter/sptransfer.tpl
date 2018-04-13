<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>注册</title>
    <script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.js"></script>
    
</head>
<body>
	<form id="gateway" method="post" action="http://47.93.233.232:28080/gateway/payGetewayOrder" >
	   <input type="hidden" name="MERCNUM" value="{$MERCNUM}"/>
	   <input type="hidden" name="TRANDATA" value="{$TRANDATA}"/>
  	   <input type="hidden" name="SIGN" value="{$SIGN}"/>
	</form>
	{literal}
	<script>

	
		$(document).ready(function($){
			document.getElementById("gateway").submit();
		
		});
		
	</script>
	{/literal}
</body>
</html>