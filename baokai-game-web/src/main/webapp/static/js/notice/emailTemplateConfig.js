$(document).ready(function(){
	$('.menu-list li:eq(9)').attr("class","current");
	var form = $('#J-form');
	$('#J-select-mailtype').change(function(){
		form.find('.type-mailtype').text(this.options[this.options.selectedIndex].text);
	});
	
	var checkId = $("#checkId").val();
	if(checkId == 1){
		$('input:radio[name=smtpAuth]')[0].checked = true;
		form.find('.panel-user').show();
	}
	
	var isNeedUsername = function(){
		var radios = $('#J-panel-switch').find('input[type="radio"]'),v;
		radios.each(function(){
			if(this.checked){
				v = this.value;
				return false;
			}
		});
		return v == '1' ? true : false;
	};
	
	
	var checkServer = function(){
		var input = $('#J-input-server'),inputv = $.trim(input.val()),isPass = false;
		if(inputv == ''){
			input.parent().find('.ui-check').show();
			isPass = false;
			//input.focus();
		}else{
			input.parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-input-server').blur(function(){
		checkServer();
	});
	
	
	var checkPort = function(){
		var input = $('#J-input-port'),inputv = $.trim(input.val()),isPass = false;
		if(inputv == ''){
			input.parent().find('.ui-check').show();
			isPass = false;
			//input.focus();
		}else{
			input.parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-input-port').blur(function(){
		checkPort();
	});
	
	
	
	var checkSender = function(){
		var input = $('#J-input-sender'),inputv = $.trim(input.val()),isPass = false;
		if(inputv == ''){
			input.parent().find('.ui-check').html('<i></i>寄件人邮箱不能为空').show();
			isPass = false;
			//input.focus();
		}else if(!(/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/).test(inputv)){
			input.parent().find('.ui-check').html('<i></i>寄件人邮箱格式填写不正确').show();
			isPass = false;
			//input.focus();
		}else{
			input.parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-input-sender').blur(function(){
		checkSender();
	});
	
	
	var checkReceive = function(){
		var input = $('#J-input-receive'),inputv = $.trim(input.val()),isPass = false;
		if(inputv == ''){
			input.parent().find('.ui-check').show();
			isPass = false;
			input.focus();
		}else{
			input.parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-input-receive').blur(function(){
		checkReceive();
	});
	
	
	var checkUsername = function(){
		var input = $('#J-input-username'),inputv = $.trim(input.val()),isPass = false;
		if(!isNeedUsername()){
			return true;
		}
		if(inputv == ''){
			input.parent().find('.ui-check').show();
			isPass = false;
			//input.focus();
		}else{
			input.parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-input-username').blur(function(){
		checkUsername();
	});
	
	
	var checkPassword = function(){
		var input = $('#J-input-password'),inputv = $.trim(input.val()),isPass = false;
		if(!isNeedUsername()){
			return true;
		}
		if(inputv == ''){
			input.parent().find('.ui-check').show();
			isPass = false;
			//input.focus();
		}else{
			input.parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-input-password').blur(function(){
		checkPassword();
	});
	
	
	$('#J-input-port').keyup(function(){
		this.value = this.value.replace(/[^\d]/g, '');
	});
	
	
	
	$('#J-button-submit').click(function(){
		if(checkServer() && checkPort() && checkSender() && checkReceive() && checkUsername() && checkPassword()){
			form.submit();
		}
	});
	
	$('#J-button-test').click(function(){
		if(!(checkServer() && checkPort() && checkSender() && checkUsername() && checkPassword())){
			return false;
		}
	});
	
	
	form.on('click', '.radio-switch', function(){
		var v = this.value;
		if(v == '1'){
			form.find('.panel-user').show();
		}else{
			form.find('.panel-user').hide();
		}
	});
	
	$("#editData").click(function() {
	var vala = $("#J-input-username").val();
	var valb = $("#haccount").val();
	var valc = $("#J-input-sender").val();
	var vald = $("#hsender").val();
    if(vala!=valb)
    	{
    	alert("帐户不能修改!!");
    	return false;
    	}
    
    if(valc!=vald)
		{
		alert("寄件人邮箱不能修改!!");
		return false;
		}
	});
	
	$(".insertdata").click(function() {
	var vala = $("#J-input-username").val();
	var valb = $("#J-input-sender").val();
    if(vala.trim()!=valb.trim())
    	{
    	alert("账户及寄件人邮箱需一致!!");
    	return false;
    	}
	});
	
	$(window).load(function() {
        	var val = $("#insertstatus").val();
        	if(val=="false")
        		alert("新增設置成功");
        	if(val=="true")
         		alert("新增失败 资料重复");        	 
        });
	$(window).load(function() {
			var val = $("#updatestatus").val();
			if(val=="false")
				alert("修改設置成功");
			if(val=="true")
				alert("修改设置失敗");        	 
		});
  
	$(window).load(function() {
			var val = $("#deletestatus").val();
			if(val=="false")
				alert("删除设置成功");
			if(val=="true")
				alert("删除设置失敗");        	 
		});
  
	$(".deletehref").click(function() {
			var txt;
			var r = confirm("确定要删除吗?");
			if (r == true) {
				txt = "刪除成功";
			} else {
				return false;
			}
		}); 
	
	
	
});