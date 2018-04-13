//12月活動

function febSecondSignUp(){
	$("#btn2-join").css("display","none");
	var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}
	$.ajax({
		url:'/activity/febsecondsignup',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source ,month:201702},
		success:function(data){
			if(data == 1 ){            //表示获取红包成功
				alert("报名成功");
				$("#btn2-join").css("display","none");
				$("#btn2-joined").css("display","");
			}else if(data==3){
				alert("活动已结束");
				$("#btn2-join").css("display","none");
				$("#btn2-joined").css("display","none");
			}else{
				alert("报名失败");
				$("#btn2-join").css("display","");
				$("#btn2-joined").css("display","none");
			}
		}
	});
	
}

function query20170202SignUp(){
	var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}
	$.ajax({
		url:'/activity/query20170202signup',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source, month:201702},
		success:function(data){
			if(data == 0 ){   //可以報名
				$("#btn2-join").css("display","");
				$("#btn2-joined").css("display","none");
			}else if(data == 2){
				$("#btn2-join").css("display","none");
				$("#btn2-joined").css("display","");
			}
			else if(data == 4){
				$("#btn2-join").css("display","none");
				$("#btn2-joined").css("display","none");
			}
		}
	});
}

// 一月活動

function febThirdSignUp(){
	$("#btn3-join").css("display","none");
	var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}
	$.ajax({
		url:'/activity/febthirdsignup',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source ,month:201702},
		success:function(data){
			if(data == 1 ){            //表示获取红包成功
				alert("报名成功");
				$("#btn3-join").css("display","none");
				$("#btn3-joined").css("display","");
			}else if(data==3){
				alert("活动已结束");
				$("#btn3-join").css("display","none");
				$("#btn3-joined").css("display","none");
			}else{
				alert("报名失败");
				$("#btn3-join").css("display","");
				$("#btn3-joined").css("display","none");
			}
		}
	});
	
}

function query20170203SignUp(){
	var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}
	$.ajax({
		url:'/activity/query20170203signup',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source, month:201702},
		success:function(data){
			//非0 : 已經報名, 0 : 尚未報名
			if(data == 0){   //可以報名
				$("#btn3-join").css("display","");
				$("#btn3-joined").css("display","none");
			}else if(data == 2){
				$("#btn3-join").css("display","none");
				$("#btn3-joined").css("display","");
			}
			else if(data == 4){
				$("#btn3-join").css("display","none");
				$("#btn3-joined").css("display","none");
			}
		}
	});
}







	