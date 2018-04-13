<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>注册</title>
    <script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.js"></script>
    
</head>
<body>
	<form id="gateway" method="post" action="http://pay.095pay.com/api/order/pay" > 
		<input type="hidden" name="sign" value="{$sign}" />
		<input type="hidden" name="p1_mchtid" value="{$merId}" />
		<input type="hidden" name="p2_paytype" value="{$payType}" />	
		<input type="hidden" name="p3_paymoney" value="{$paymoney}" />			
		<input type="hidden" name="p4_orderno" value="{$orderno}" />	
		<input type="hidden" name="p5_callbackurl" value="{$asynURL}"/>
		<input type="hidden" name="p7_version" value="{$version}" />		
		<input type="hidden" name="p8_signtype" value="{$signType}"/>
		<input type="hidden" name="p11_isshow" value="{$isShow}" />	  
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