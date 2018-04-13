<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>注册</title>
    <script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.js"></script>
    
</head>
<body>
	<form id="gateway" method="post" action="https://pay.dinpay.com/gateway?input_charset=UTF-8" > 
		<input type="hidden" name="sign" value="{$sign}" />
		<input type="hidden" name="merchant_code" value="{$merchantCode}" />
		<input type="hidden" name="service_type" value="{$serviceType}" />	
		<input type="hidden" name="interface_version" value="{$interfaceVersion}" />			
		<input type="hidden" name="input_charset" value="{$inputCharset}" />	
		<input type="hidden" name="notify_url" value="{$notifyUrl}"/>
		<input type="hidden" name="sign_type" value="{$signType}" />		
		<input type="hidden" name="order_no" value="{$orderNo}"/>
		<input type="hidden" name="order_time" value="{$orderTime}" />	
		<input type="hidden" name="order_amount" value="{$orderAmount}"/>
		<input type="hidden" name="product_name" value="{$productName}" />	
		<input type="hidden" name="return_url" value="{$returnUrl}"/>	
		<input type="hidden" name="bank_code" value="{$bankCode}" />
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