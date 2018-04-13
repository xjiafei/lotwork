<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>注册</title>
    <script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.js"></script>
    
</head>
<body>
	<form id="remitQuery" method="post" action="https://pay.41.cn/remit/query" >
	   <input type="hidden" name="input_charset" value="UTF-8"/>
	   <input type="hidden" name="merchant_code" value="19461197"/>
	   <input type="hidden" name="merchant_order" value="1000001"/>
	   <input type="hidden" name="sign" value="87b453198203dce6a371ef7a363c472b"/>
	</form>
	<form id="gateway" method="post" action="https://pay.41.cn/gateway" >
	   <input type="hidden" name="input_charset" value="{$input_charset}"/>
	   <input type="hidden" name="notify_url" value="{$notify_url}"/>
        <input type="hidden" name="bank_code" value="{$bank_code}"/>
	   <input type="hidden" name="pay_type" value="{$pay_type}"/>
	   <input type="hidden" name="merchant_code" value="{$merchant_code}"/>
	   <input type="hidden" name="order_no" value="{$order_no}"/>
	   <input type="hidden" name="order_amount" value="{$order_amount}"/>
	   <input type="hidden" name="order_time" value="{$order_time}"/>
	   <input type="hidden" name="req_referer" value="{$req_referer}"/>
	   <input type="hidden" name="customer_ip" value="{$customer_ip}"/>
       <input type="hidden" name="sign" value="{$sign}"/>
	</form>
	{literal}
	<script>

		var thpay = {
			logSubmit:function(){
				$.ajax({
					url:'/Thtransfer/logsubmit',
			        method: "post",
					data:{order_no:$('[name=order_no]').val()}
				});
			}	
		};
	
		$(document).ready(function($){
			thpay.logSubmit();
			document.getElementById("gateway").submit();
			}
		);
		
	</script>
	{/literal}
</body>
</html>