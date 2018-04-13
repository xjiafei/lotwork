<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>注册</title>
    <script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.js"></script>
    
</head>
<body>
	<form id="gateway" method="post" action="https://ebank.payworth.net/portal" >
		<input type="hidden" name="service" value="{$service}"/>
	   	<input type="hidden" name="merchant_ID" value="{$merchant_ID}"/>
	   	<input type="hidden" name="notify_url" value="{$notify_url}"/>
	   	<input type="hidden" name="return_url" value="{$return_url}"/>
	   	<input type="hidden" name="charset" value="{$charset}"/>
	   	<input type="hidden" name="title" value="{$title}"/>
	   	<input type="hidden" name="body" value="{$body}"/>
	   	<input type="hidden" name="order_no" value="{$order_no}"/>
	   	<input type="hidden" name="total_fee" value="{$total_fee}"/>
	   	<input type="hidden" name="payment_type" value="{$payment_type}"/>
	   	<input type="hidden" name="paymethod" value="{$paymethod}"/>
	   	<input type="hidden" name="defaultbank" value="{$defaultbank}"/>
	   	<input type="hidden" name="seller_email" value="{$seller_email}"/>
	   	<input type="hidden" name="isApp" value="{$isApp}"/>
	   	<input type="hidden" name="sign_type" value="{$sign_type}"/>
	   	<input type="hidden" name="sign" value="{$sign}"/>
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