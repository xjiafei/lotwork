
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
		data:{source:source, month:201611, week:4, token : $("#tokenValue").val()},
		success:function(data){
			if(data == 1 ){            //表示获取红包成功
				//querySignUp();
				alert("报名成功");
				redbow4query();
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
		data:{source:source, month:201611, week:4, token : $("#tokenValue").val()},
		success:function(data){
			if(data == 0 ){            //可以報名
				//$(".SignUpbtn a").css("background","url(../images/btn-signup.png)no-repeat)");
				//$(".SignUpEndbtn").css("display","none");
				//$("#signup").attr('disabled', false);
				//$("#signupEnd").attr('disabled', true);
				$(".SignUpDivCon").html(
				'<div class="SignUpbtn" onclick="signUp();">'+
					'<a id="signup" href="#"></a>'+
				'</div>'
				);
			}else if(data == 2){
				//$(".SignUpbtn a").css("background","url(../images/btn_already_reg.png)no-repeat)");
				//$(".SignUpEndbtn").css("display","");
				$(".SignUpDivCon").html(
				'<div class="SignUpEndbtn">'+
					'<a id="signup" href="#"></a>'+
				'</div>'
				);
			}
		}
	});
}



(function (i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function () {
                    (i[r].q = i[r].q || []).push(arguments)
                }, i[r].l = 1 * new Date();
        a = s.createElement(o),
                m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m)
    })(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');

    ga('create', 'UA-63536434-1', 'auto');
    ga('send', 'pageview');

    $(function () {
        //google analytics 点击统计
        $(".btn").on('click', function () {
            ga('send', 'event', '11月第四周活动全民齐嘉奖', 'wap报名按钮');
        });
    });