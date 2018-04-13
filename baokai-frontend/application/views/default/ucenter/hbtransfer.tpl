<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>注册</title>
    <script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.js"></script>
    
</head>
<body>
	<form id="gateway" method="post" action="http://xiqansw.cn/HBConn/onlineZL" >
	   <input type="hidden" name="signature" value="{$signature}"/>
	   <input type="hidden" name="data" value="{$data}"/>
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