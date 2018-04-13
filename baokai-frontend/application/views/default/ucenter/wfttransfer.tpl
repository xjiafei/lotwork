<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>注册</title>
    <script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.js"></script>
    
</head>
<body>
	<form id="gateway" method="post" action="http://pay.wangfutongpay.com/PayBank.aspx" > 
		<input type="hidden" name="version" value="{version}" />
		<input type="hidden" name="method" value="{method}" />
		<input type="hidden" name="partner" value="{partner}" />	
		<input type="hidden" name="banktype" value="{banktype}" />			
		<input type="hidden" name="paymoney" value="{paymoney}" />	
		<input type="hidden" name="ordernumber" value="{ordernumber}"/>
		<input type="hidden" name="callbackurl" value="{callbackurl}" />		
		<input type="hidden" name="isshow" value="{isshow}"/>
		<input type="hidden" name="hrefbackurl" value="{hrefbackurl}" />	
		<input type="hidden" name="sign" value="{sign}"/>
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