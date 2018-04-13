//12月活動

function signUp(){
	
	var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}
	$.ajax({
		url:'/activitymb/signup20161204',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source, week:4 ,month:201612,token:$("#token").val()},
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
		url:'/activitymb/querysignup20161204',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source, month:201612, week:3, actId:20161204,token:$("#token").val()},
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

// 一月活動

function janSignUp(){
	
	var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}
	$.ajax({
		url:'/activitymb/jansignupmb',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source, week:4 ,month:201612,token:$("#token").val()},
		success:function(data){
			if(data == 2 ){            //表示获取红包成功
				alert("报名成功");
				$(".btn-viewDetail1").css("display","none");
				$(".btn-viewDetail1ed").css("display","");
			}else {
				alert("报名失败");
				$(".btn-viewDetail1").css("display","");
				$(".btn-viewDetail1ed").css("display","none");
			}
		}
	});
	
}

function queryJanSignUp(){
	var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}
	$.ajax({
		url:'/activitymb/queryjansignupmb',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source, month:201612, week:3, actId:20161204,token:$("#token").val()},
		success:function(data){
			//非0 : 已經報名, 0 : 尚未報名
			if(data == 0){   //可以報名
				$(".btn-viewDetail1").css("display","");
				$(".btn-viewDetail1ed").css("display","none");
			}else {
				$(".btn-viewDetail1").css("display","none");
				$(".btn-viewDetail1ed").css("display","");
			}
		}
	});
}

function redirectDetailPage(){
	$(".btn-viewDetail").attr("href", "/activitymb/decbowmbfour?sid=" + $("#token").val());
	$(".btn-viewDetail").click();
}





	