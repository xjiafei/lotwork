<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>账户安全</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<script type="text/javascript">
    (function() {       
        function async_load(){           
            var s = document.createElement('script');          
            s.type = 'text/javascript';          
            s.async = true;           
            s.src = "";           
            var x = document.getElementsByTagName('script')[0];          
            x.parentNode.insertBefore(s, x);      
        }       
    if (window.attachEvent)           
    window.attachEvent('onload', async_load);
    else 
    window.addEventListener('load', async_load, false);  
    })();
    </script>
</head>
<body>
	
	<!-- header start -->
	<div class="header">
		<div class="g_33">
			<h1 class="logo"><a title="首页" href="/index">宝开</a></h1>
		</div>
	</div>
	<!-- header end -->
	
	<div class="g_33">
		<div class="appeal-content">
			<div class="alert alert-success">
				<i></i>
				<div class="txt">
					<h4>恭喜您，账号申诉提交成功</h4>
					<p>我们将在5个工作日内审核您的申诉，申诉结果将通过联系邮箱通知您，请耐心等待，谢谢</p>
				</div>
			</div>
		</div>
	</div>
	
	<!-- //////////////底侧公共页面////////////// -->
	{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
	
<script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.min.js"></script>

</body>
</html>
