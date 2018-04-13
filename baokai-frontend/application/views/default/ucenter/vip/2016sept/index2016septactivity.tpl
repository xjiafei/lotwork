<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>金秋九月</title>
<meta name="description" content="金秋九月">
<meta name="keywords" content="金秋九月">
<link href="{$path_img}/images/activity/sept/index/css/style.css" rel="stylesheet">

</head>
<body>
<div class="allBg">
	<div class="banner">

	</div>
	<div class="main">
		<div class="act">
			<a href="javascript:;" class="joinBtn" id="register1">立即报名</a>
			<a href="/vip/activityweek1" id="more1" class="moreInfo">活动详情</a>
		</div>
		<div class="act">
			<a href="javascript:;" class="joinBtn" id="register2">立即报名</a>
			<a href="/vip/activityweek2"  id="more2" class="moreInfo">活动详情</a>
		</div>
		<div class="act">
			<a href="javascript:;" class="joinBtn" id="register3">立即报名</a>
			<a href="/vip/activityweek3" id="more3" class="moreInfo">活动详情</a>

		</div>

		<div class="footer">
			<p>©2003-2016 宝开彩票 All Rigths Resrved</p>
		</div>
	</div>
</div>
<div class="alert">
	<p>您已完成报名<br>祝您顺利完成活动</p>
	<p><a class="confirm" href="#">确定</a></p>
</div>
<div class="alert2" >
	<p>您已报名参加</p>
	<p><a class="confirm" href="#">确定</a></p>
</div>

<script type="text/javascript" src="{$path_img}/js/jquery-1.9.1.min.js"></script>
<script>
	var sdata1 = "month=9&source=web&startTime=20160905&endTime=20160910";
	var sdata2 = "month=9&source=web&startTime=20160912&endTime=20160918";
	//var sdata2 = "month=9&source=web&startTime=20160909&endTime=20160911"; //for test
	var sdata3 = "month=9&source=web&startTime=20160919&endTime=20160925";
	
$(function(){
	calculateTime();
	var isRegister= false;
	var isRegister2= false;
	var isRegister3= false;
	$.ajax({
	        url:'/vip/registerinfo',
	        dataType:'json',
	        method:'post',
	        data:sdata1,
	        success:function(res){
	            if (Number(res['isSuccess']) == 1){
	            	//$('#registerCount').html(res['registerCount']);
	                //判断是否已经报名
	                if(res['isRegistered']){
	                    //$('#register1').hide();
	                    $('#register1').addClass('finish');
						isRegister=true;
	                }else{
	                    //$('#register').click(function(){
	                    //    $('#register1').hide();
	                    //    $('.alert').show();
	                    //});
	                }
					checkWeek2Register();
	            }
	        }
	    });
	
	//點擊註冊活動1
	$('#register1').click(function(){
		if(isRegister){
			return;
		}else{
			 $.ajax({
	        url:'/vip/application',
	        dataType:'json',
	        method:'post',
	        data:sdata1,
	        success:function(res){
				console.log(res);
	            if (Number(res['isSuccess']) == 1){
	                //判断是否已经报名
	                if(res['isRegistered']){
	                  $('.alert2').show();
	                  $('#register1').addClass('finish');
					  isRegister=true;
	                }else if( res['registerOk']){
							$('.alert').show();
							$('#register1').addClass('finish');
	                		isRegister=true;
	                }
	            }
	        }
	    });		
		}
	   
	})
	
	//點擊註冊活動2
	$('#register2').click(function(){
		if(isRegister2){
			return;
		}else{
			$.ajax({
				url:'/vip/application',
				dataType:'json',
				method:'post',
				data:sdata2,
				success:function(res){
					console.log(res);
					if (Number(res['isSuccess']) == 1){
						//判断是否已经报名
						if(res['isRegistered']){
						  $('.alert2').show();
						  $('#register2').addClass('finish');
					      isRegister2=true;						  
						}else if( res['registerOk']){						  
						  $('.alert').show();
						  $('#register2').addClass('finish');
					   	  isRegister2=true
						}
					}
				}
			});
		}		
	})
	//點擊註冊活動3
	$('#register3').click(function(){
		if(isRegister3){
			return;
		}else{
			$.ajax({
				url:'/vip/application',
				dataType:'json',
				method:'post',
				data:sdata3,
				success:function(res){
					console.log(res);
					if (Number(res['isSuccess']) == 1){
						//判断是否已经报名
						if(res['isRegistered']){
						  $('.alert2').show();
						  $('#register3').addClass('finish');
					      isRegister3=true;
						}else if( res['registerOk']){
							$('.alert').show();
							$('#register3').addClass('finish');
							isRegister3=true;
						}
					}
				}
			});
		}		
	})
	
	function calculateTime(){

			var now = new Date();
			var activity3EndTime = new Date('2016/09/19 00:00');
			var spantime = (activity3EndTime.getTime()-now.getTime())/1000;
	        if(spantime<0){
				$('#register3').hide();
		   }
    }
    $('.pop .close').click(function(){
        $('.mask').hide();
        $('.pop').fadeOut();
    })
	
	$('.confirm').click(function(){
	$(this).parent().parent().hide();
})	
});

function checkWeek2Register(){
$.ajax({
	    url:'/vip/registerinfo',
	    dataType:'json',
	    method:'post',
	    data:sdata2,
	    success:function(res){
	        if (Number(res['isSuccess']) == 1){
	            //$('#registerCount').html(res['registerCount']);
	            //判断是否已经报名
	            if(res['isRegistered']){
	                //$('#register2').hide();
	                $('#register2').addClass('finish');
					isRegister2=true;
	            }else{
	               
	            }
				checkWeek3Register();
	        }
	    }
	});
}

function checkWeek3Register(){
$.ajax({
	    url:'/vip/registerinfo',
	    dataType:'json',
	    method:'post',
	    data:sdata3,
	    success:function(res){
	        if (Number(res['isSuccess']) == 1){
	            //$('#registerCount').html(res['registerCount']);
	            //判断是否已经报名
	            if(res['isRegistered']){
	               // $('#register3').hide();
	                $('#register3').addClass('finish');
					isRegister3=true;
	            }else{
	               
	            }
				
	        }
	    }
	});
}
</script>
	<script src="{$path_js}/js/jquery-1.9.1.min.js"></script>
    {literal}
 <script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
  ga('create', 'UA-63536434-1', 'auto');
  ga('send', 'pageview');
  
	$(function(){
		//google analytics 点击统计
		$("#register1").on('click',function(){
			ga('send', 'event', '聚合页活动1', 'click(点击)');
		});
		$("#more1").on('click',function(){
			ga('send', 'event', '聚合页详情1', 'click(点击)');
		});
		$("#register2").on('click',function(){
			ga('send', 'event', '聚合页活动2', 'click(点击)');
		});
		$("#more2").on('click',function(){
			ga('send', 'event', '聚合页详情2', 'click(点击)');
		});
		$("#register3").on('click',function(){
			ga('send', 'event', '聚合页活动3', 'click(点击)');
		});
		$("#more3").on('click',function(){
			ga('send', 'event', '聚合页详情3', 'click(点击)');
		});
	})



</script>
	{/literal}

</body>

</html>