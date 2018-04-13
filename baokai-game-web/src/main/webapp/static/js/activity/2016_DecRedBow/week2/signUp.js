function signUp(){
	
	var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}
	$.ajax({
		url:'/activity/signup20161202',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source, week:2 ,month:201612},
		success:function(data){
			if(data == 1 ){            //表示获取红包成功
				alert("报名成功");
				$(".btn-join").css("display","none");
				$(".btn-joined").css("display","");
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
		url:'/activity/querysignup20161202',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source, month:201612, week:2, actId:20161202},
		success:function(data){
			if(data == 0 ){   //可以報名
				$(".btn-join").css("display","");
				$(".btn-joined").css("display","none");
			}else if(data == 2){
				$(".btn-join").css("display","none");
				$(".btn-joined").css("display","");
			}
			else if(data == 4){
				$(".btn-joined").css("display","none");
				$(".btn-overtime").css("display","");
			}
		}
	});
}

(function (doc, win) {
	var docEl = doc.documentElement,
		resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
		recalc = function () {
		  var clientWidth = docEl.clientWidth ;
		  if (!clientWidth) return;
		  htmlFontSize= 100 * (clientWidth / 320);
		  docEl.style.fontSize = htmlFontSize + 'px';
		};
	if (!doc.addEventListener) return;
	win.addEventListener(resizeEvt, recalc, false);
	doc.addEventListener('DOMContentLoaded', recalc, false);
	doc.addEventListener('touchstart', function(){}, false);
})(document, window);

(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

	ga('create', 'UA-63536434-1', 'auto');
	ga('send', 'pageview');
	ga('send', 'event', '12月第二周活动', '打开活动页面');

var oJoin = document.querySelector('.btn-join');
if (oJoin) {
	oJoin.addEventListener('click', function(){
	  signUp();
	  ga('send', 'event', '12月第二周活动报名', '点击【立即参加】按钮');
	}, false);
}




	