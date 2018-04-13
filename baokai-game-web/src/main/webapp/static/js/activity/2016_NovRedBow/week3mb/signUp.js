function signUp(){
	var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}
	$.ajax({
		url:'/activitymb/redbowsignup',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source, month:201611, week:3, token : $("#tokenValue").val()},
		success:function(data){
			if(data == 1 ){            //表示获取红包成功
				//querySignUp();
				//redbow3chekbets();
				alert("报名成功");
				location.reload(true);
			}else if(data == 2){
				alert("已报名");
			}else{
				alert("报名失败");
			}
		}
	});
}

function querySignUp(){
	var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}
	$.ajax({
		url:'/activitymb/redbowquerysignup',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source, month:201611, week:3, token : $("#tokenValue").val()},
		success:function(data){
			if(data == 0 ){            //可以報名
				$(".signbtn").css("background","");
				$(".signbtn").css("background-size", "");
				
			}else if(data == 2){
				$(".signbtn").css("background","url(" + $("#imgPath").val() + "/images/activity/2016_NovRedBow/activity3mb/images/signup.png)no-repeat");
				$(".signbtn").css("background-size", "100% auto");
				
			}
		}
	});
}

var fitPage = function(){
var w = document.body.clientWidth;
w = w > 720 ? 720: w;
w = (w / 720) * 100 ;
document.getElementById('html').style.fontSize = w + 'px';
}
fitPage();
var t;
var func = function(){
clearTimeout(t);
t = setTimeout(fitPage, 25);
}
window.onresize = func();



(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
        m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
                              })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

      ga('create', 'UA-63536434-1', 'auto');
      ga('send', 'pageview');

    $(function(){
        //google analytics 点击统计
        $("#signup").on('click',function(){
          ga('send', 'event', '点击報名', 'click(点击)');
        });

        $(".g1").on('click',function(){
          ga('send', 'event', '時時彩', 'click(点击)');
        });

        $(".g2").on('click',function(){
          ga('send', 'event', '吉利分分彩', 'click(点击)');
        });

        $(".g3").on('click',function(){
          ga('send', 'event', '順利秒秒彩', 'click(点击)');
        });
      });