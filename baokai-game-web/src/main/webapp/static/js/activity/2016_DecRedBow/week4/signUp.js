function signUp(){
	
	var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}
	$.ajax({
		url:'/activity/signup20161204',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source, week:4 ,month:201612},
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
		url:'/activity/querysignup20161204',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source, month:201612, week:3, actId:20161204},
		success:function(data){
			if(data == 0 ){   //可以報名
				$(".btn-join").css("display","");
				$(".btn-joined").css("display","none");
			}else if(data == 2){
				$(".btn-join").css("display","none");
				$(".btn-joined").css("display","");
			}
			else if(data == 4){
				alert("活動已結束");
				$(".btn-join").css("display","none");
				$(".btn-joined").css("display","");
				return;
				
			}
		}
	});
	getFront200();
	getRank();
	
}

//撈取排名前200名
function getFront200(){
	var tableSt= "";
	$.ajax({
		url:'/activity/getfront200',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{ month:201612, week:3, actId:20161204},
		success:function(data){
			var count =0;
			console.log("==="+data.length /10);
			for(var i =0;i<data.length;i++){
				
				
				
				
				if(data[i].rank == 1){
					tableSt += "<div class='swiper-slide'>";
				}
				
				if(data[i].rank % 50 == 0){
					if(data[i].rank == 200){
						tableSt += "<tr><th>"+data[i].rank+"</th><td>"+data[i].account+"</td></tr></tbody></table></div>";
					}else{
						tableSt += "<tr><th>"+data[i].rank+"</th><td>"+data[i].account+"</td></tr></tbody></table></div><div class='swiper-slide'>";
					}
				}else{
					
					if(data[i].rank % 10 == 1){
						tableSt +=   "<table class='table'><tbody><tr><th>"+data[i].rank+"</th><td>"+data[i].account+"</td></tr>";							
					}else if(data[i].rank % 10 == 0){
						tableSt +=   "<tr><th>"+data[i].rank+"</th><td>"+data[i].account+"</td></tr></tbody></table>";
					}else{
						tableSt +=   "<tr><th>"+data[i].rank+"</th><td>"+data[i].account+"</td></tr>";
					}
				}
				
				
			}
			$("#swiperId").html(tableSt);
			
			var swiper = new Swiper('.swiper-container', {
		
			  nextButton: '.next',
			  prevButton: '.prev',
			  onSlideChangeEnd: function(swiper){
				 
				var index = swiper.activeIndex;
				var html = (index*50 + 1) + ' - ' + (index*50 + 50)
				document.querySelector('.J_range_number').innerHTML = html;
			  }
			});
			
		}
		
	});

	
	
	
	
	
}
//取得用戶排名
function getRank(){
	$.ajax({
		url:'/activity/getrank',
		type:'POST',
		dataType:'json',
		cache: false,
		success:function(data){
			var stRank= "";
			var stRank= "";
			if(data == null || data == 'null'){
				stRank += "您当前排名<br/>" + "---";
			}else{
				stRank += "您当前排名<br/>" + data;
			}
			if(data > 300){
				stRank = "继续努力~";
			}
			$(".current-desc").html(stRank);
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
      ga('send', 'event', '12月第四周活动', '打开活动页面');

      var oJoin = document.querySelector('.btn-join');
      if (oJoin) {
		oJoin.addEventListener('click', function(){
		  signUp();
          ga('send', 'event', '12月第四周活动报名', '点击【立即参加】按钮');
        }, false);
      }







	