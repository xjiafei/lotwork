$(document).ready(function(){
	$('.menu-list li:eq(9)').attr("class","current");
	var start = $('#J-time-start'),
		end = $('#J-time-end');
	start.focus(function(){
		(new phoenix.DatePicker({input:start})).show();
	});
	end.focus(function(){
		(new phoenix.DatePicker({input:end})).show();
	});
	
	
	var checkEmail = function(){
		var email = $('#J-input-email'),emailv = $.trim(email.val()),isPass = false;
		if(emailv == ''){
			email.parent().find('.ui-check').html('<i></i>发件人邮箱不能为空').show();
			isPass = false;
			email.focus();
		}else if(!(/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/).test(emailv)){
			email.parent().find('.ui-check').html('<i></i>邮箱格式填写不正确').show();
			isPass = false;
			email.focus();
		}else{
			email.parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-input-email').blur(function(){
		checkEmail();
	});
	var checkUsername = function(){
		var username = $('#J-input-username'),usernamev = $.trim(username.val()),isPass = false;
		if(usernamev == ''){
			username.parent().find('.ui-check').html('<i></i>收件人名称不能为空').show();
			isPass = false;
			//username.focus();
		}else if(!(/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/).test(usernamev)){
			username.parent().find('.ui-check').html('<i></i>邮箱格式填写不正确').show();
			isPass = false;
			//username.focus();
		}else{
			username.parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-input-username').blur(function(){
		checkUsername();
	});
	var checkTitle = function(){
		var title = $('#J-input-title'),titlev = $.trim(title.val()),isPass = false;
		if(titlev == ''){
			title.parent().find('.ui-check').html('<i></i>邮件标题不能为空').show();
			isPass = false;
			//title.focus();
		}else{
			title.parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-input-title').blur(function(){
		checkTitle();
	});
	var checkContent = function(){
		var content = $('#J-textarea-content'),contentv = $.trim(content.val()),isPass = false;
		if(contentv == ''){
			content.parent().find('.ui-check').html('<i></i>邮件内容不能为空').show();
			isPass = false;
			//content.focus();
		}else{
			content.parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-textarea-content').blur(function(){
		checkContent();
	});
	
	$('.J-button-submit').click(function(){
		var vala = $('#J-input-username').val();
	   	$(".rcvmail_out").val(vala);
		var valb = $('#J-input-title').val();
	   	$(".title_out").val(valb);
		var valc = $('#J-textarea-content').val();
	   	$(".content_out").val(valc);
		
		if(checkUsername() && checkTitle() && checkContent()){
			$('#J-form').submit();
		}
		else
		{
			return false;
		}
	});

	
});