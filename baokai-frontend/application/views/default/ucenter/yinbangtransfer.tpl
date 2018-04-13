<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>注册</title>
    <script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.js"></script>
    
</head>
<body>
	<form id="gateway" method="post" action="https://www.yinbangpay.com/gateway/orderPay" > 
		<input type="hidden" name="sign" value="{$sign}" />
		<input type="hidden" name="version" value="{$version}" />
		<input type="hidden" name="merId" value="{$merId}" />	
		<input type="hidden" name="encParam" value="{$encParam}" />
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