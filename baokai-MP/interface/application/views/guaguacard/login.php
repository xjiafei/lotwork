<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>刮刮卡管理后台</title>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<style type="text/css">
.vertical-center {
	text-align: center;
	position: fixed;
	/* center horizontally */
	top: 50%;
	left: 50%;
	transform: translate(-50%, -80%);
	border: solid 1px #000000;
}
.error_msg {
	color: #FF0000;	
}
</style>
</head>
<body>
	
<div class="vertical-center">
	<table border="0">
		<tr>
			<td colspan="2">刮刮卡管理后台</td>
		</tr>
		<tr>
			<td>username</td>
			<td><input type="text" id="txtUsername" /></td>
		</tr>
		<tr>
			<td>password</td>
			<td><input type="password" id="txtPassword" /></td>
		</tr>
		<tr>
			<td colspan="2"><input type="button" id="btnSubmit" value="Submit"/></td>
		</tr>
		<tr>
			<td colspan="2"><div id="txtMsg" class="error_msg"></div></td>
		</tr>
	</table>
</div>
	
	
	
	
<script type="text/javascript" src="<?php echo $base_url;?>js/jquery-1.9.1.min.js"></script>
<script>

$(document).ready(function(){
	$("#btnSubmit").click(function(){
			$("#txtMsg").html("");
			$.ajax({type: 'POST',
				url:'<?php echo $base_url;?>guaguacard/verify',
				dataType:'json',
				data: { "username": $("#txtUsername").val(), "pwd": $("#txtPassword").val()},
				success:function(rs){
					//alert(rs);
					//alert(JSON.stringify(rs));
					if(rs["msg"] != "")
					{
						$("#txtMsg").html(rs["msg"]);
					}else{
						url_redirect({url: '<?php echo $base_url;?>guaguacard/manager',
						                  method: "post",
						                  data: {'sno': rs["sno"]}
						                 });
					}	
				}
			});

		});
});

function url_redirect(options){
	var $form = $("<form />");
	
	$form.attr("action",options.url);
	$form.attr("method",options.method);
	
	for (var data in options.data)
	$form.append('<input type="hidden" name="'+data+'" value="'+options.data[data]+'" />');
	
	$("body").append($form);
	$form.submit();
}

</script>

</body>
</html>
