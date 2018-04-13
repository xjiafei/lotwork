<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
	<title>宝开彩票-今秋十月-鸿运当头</title>
	<style type="text/css">
		*{
			margin:0; 
			padding:0;
		}
		body{
			background: url({$path_img}/images/activity/octActivity/week2/bg2.jpg) no-repeat center;
		}
		.box{
			width:1000px;
			height: 1300px; 
			background: url({$path_img}/images/activity/octActivity/week2/cont2.jpg) no-repeat center;
			margin:0 auto; 
			position: relative; 
			display: none;
		}
		.btn{
			display:block;
			width:316px; 
			height: 89px; 
			background: url({$path_img}/images/activity/octActivity/week2/btn2.png) no-repeat center; 
			position: absolute; 
			left:260px; 
			top:405px;
		}

		.btn3{
			display:block;
			width:316px; 
			height: 89px; 
			background: url({$path_img}/images/activity/octActivity/week2/btn3.png) no-repeat center; 
			position: absolute; 
			left:260px; 
			top:405px;
		}
		.btn_m{
			width:233px; 
			height: 71px; 
			background-size:100%; 
			left:195px; 
			top:287px;
		}
		#register:hover{
			background-size:101%;
		}
		
		.box_m{
			width:641px;
			height: 1480px; 
			background: url({$path_img}/images/activity/octActivity/week2/cont2_m.jpg) no-repeat left top;
			margin:0 auto; 
			position: relative; 
			background-size:cover;
		}
		
		.alert {
			background-color: #333738;
			background-image: url({$path_img}/images/activity/octActivity/week2/alert.png);
			background-repeat: no-repeat;
			background-position: center 10px;
			color: #fff;
			border-radius: 2px;
			box-shadow: 0 0 5px rgba(0,0,0,.3);
			width: 260px;
			position: fixed;
			z-index: 999;
			left: 50%;
			top: 50%;
			padding: 60px 0px 30px;
			margin-left: -130px;
			margin-top: -100px;
			text-align: center;
			font-size: 15px;
			line-height: 20px;
			display: none;
		}
		
		.confirm {
			background: #e65a1e;
			color: #fff;
			padding: 6px 30px;
			border-radius: 4px;
		}

		.alert p {
			padding: 10px;
		}
	</style>
</head>
<body>
	<div id="box" class="box">
		<a id="register" class="btn" href="javascript:;"></a>
		<a id="check" class="btn3" href="javascript:;"></a>
	</div>
	<div class="alert">
		<p>您已完成报名<br>祝您顺利完成活动</p>
		<p><a class="confirm" href="javascript:;">确定</a></p>
	</div>
</body>
<script type="text/javascript" src="{$path_js}/js/activity/octActivity/week1/web/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	window.onload = function(){
		//var zoom = window.innerWidth / 641;
		var box = document.getElementById('box');

		var btn = document.getElementById('register');
		if(window.innerWidth < 640){
			//box.className = 'box box_m';
			//btn.className = 'btn btn_m';
			//box.style.zoom = zoom;				
		}
		box.style.display = 'block';

	$.ajax({
	    url:'/vip/oct2info',
	    dataType:'json',
	    method:'post',
	    success:function(data){
	    	if (data['isSuccess']){
				$('#register').hide();
				$('#check').show();
	        }else{
	        	$('#register').show();
				$('#check').hide();
	        }
	    }
	});	
}
		
	//點擊報名
	$('#register').click(function(){
	    $.ajax({
	        url:'/vip/oct2signup',
	        dataType:'json',
	        method:'post',
	        success:function(data){
	            if (data['isSuccess']){
					$('.alert').show();
	            }
	        }
	    });	
	})
	
	$(".confirm").click(function(){
		$('.alert').hide();
		$('#register').hide();
		$('#check').show();
	})
	</script>
</html>