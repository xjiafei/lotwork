$(document).ready(function(){
	$('.menu-list li:eq(8)').attr("class","current");
		var pass = '',
		userName = false,
		password = false,
		passConfirm = false,
		passCard = false,
		phone = false,
		eMail = false,
		//用户名
		usernameDom = $('#userName'),
		//密码框
		passWordDom = $('#passWord'),
		//密码提示
		passWordTips = '<span style="color:#ff0000">6-20位字符组成，区分大小写<span>'
		//密码卡
		confirmPassWordDom = $('#confirmPassWord'),
		//确认密码框
		passwordCardDom = $('#passwordCard'),
		//电话
		phoneDom = $('#input-phone'),
		//邮箱
		emailDom = $('#input-email'),
		//表单
		formDom = $('#info-content'),
		//修改按钮
		editButtonDom = $('#editButton'),
		//用户列表弹窗
		userListDom = $('#userList').text(),
		//遮罩
		mask = phoenix.Mask.getInstance(),
		//弹窗
		message = phoenix.Message.getInstance(),
		//ajax栈
		ajaxStack = 0;

		//检测用户名
		var checkUserName = function(){
			userName=true;
			var el = usernameDom,v = $.trim(el.val()),tipsDom = el.next();
			if(v == ''){
				tipsDom.html('<span style="color:#ff0000">用户名不能为空</span>');
				userName = false
				return false;
			}
			if((/[^a-zA-Z0-9]/).test(v)){
				tipsDom.html('<span style="color:#ff0000">用户名只能由字母和数字组成</span>');
				userName = false
				return false;
			}
			if(v.length < 3 || v.length > 16){
				tipsDom.html('<span style="color:#ff0000">长度有误，请输入3-16位字符</span>');
				userName = false
				return false;
			}
			if(!(/^[a-zA-Z0-9]{3,16}$/).test(v)){
				tipsDom.html('<span style="color:#ff0000">3-16位字符，由字母和数字组成</span>');
				userName = false
				return false;
			}
			$.ajax({
			url: baseUrl + '/aclAdmin/checkAccountUnique',
			async:false,
			dataType: 'json',
			type: 'POST',
			data: 'account='+v,
			cache:false,
			type: 'POST',
			beforeSend:function(){
			},
			success:function(data){
				if(Number(data['status']) == 1){
					tipsDom.html('');
					userName = true;
				}else{
					tipsDom.html('<span style="color:#ff0000">' + data['message'] + '</span>');
					userName = false;
				}
			},
			complete: function(){
			}
			});
			
			return userName;
		}
		usernameDom.focus(function(){
			var tipsDom = $(this).next();
			tipsDom.html('3-16位字符，由字母和数字组成');
		});
		usernameDom.blur(checkUserName);

		//检查密码
		var checkPassword = function(){
			var tipsDom = passWordDom.next(),v = $.trim(passWordDom.val());
			if(v == ''){
				tipsDom.html('<span style="color:#ff0000">密码不能为空<span>');
				password = false
				return false;
			}
			if(!(/^.{6,20}$/).test(v)){
				tipsDom.html('<span style="color:#ff0000">长度有误，请输入6-20位字符<span>');
				password = false
				return false;
			}
			
			password = true;
			tipsDom.html('');
			pass = passWordDom.val();
			
			return password;
		};
		//密码提示
		passWordDom.focus(function(){
			var tipsDom = $(this).next();
			tipsDom.html('6-20位字符组成，区分大小写');
		});
		passWordDom.blur(checkPassword);

		//确认密码检查
		var checkConfirmPassword = function(){
			var tipsDom = confirmPassWordDom.next(),v = $.trim(confirmPassWordDom.val());
			if(v == ''){
				tipsDom.html('<span style="color:#ff0000">确认密码不能为空</span>');
				passConfirm = false
				return passConfirm;
			}
			
			if($.trim(confirmPassWordDom.val()) != $.trim(passWordDom.val())){
				tipsDom.html('<span style="color:#ff0000">两次输入的密码不一致</span>');
				passConfirm = false
				return passConfirm;
			}
			
			passConfirm = true;
			tipsDom.html('');
			return passConfirm;
		};
		confirmPassWordDom.focus(function(){
			var tipsDom = $(this).next();
			tipsDom.html('请重新输入一遍密码');
		});
		confirmPassWordDom.blur(checkConfirmPassword);


		//密码卡绑定
		var checkCard = function(){
			var tipsDom = passwordCardDom.next(),v = $.trim(passwordCardDom.val());
			if(v == ''){
				tipsDom.html('<span style="color:#ff0000">密码卡不能为空</span>');
				passCard = false
				return false;
			}
			if(!(/^\d{9}$/).test(v)){
				tipsDom.html('<span style="color:#ff0000">密码卡序列号由9位数字组成</span>');
				passCard = false
				return false;
			} 
			//校验密码卡有且只能绑定一个用户    
			$.ajax({
            url: baseUrl+'/aclAdmin/checkBindPwd',
            async:false,
            dataType: 'json',
            type: 'POST',
            data: "bindPwd="+v+"&id=-1",   
            success:function(data){
					if(Number(data['status']) == 0)
					{
						tipsDom.html('<span style="color:#ff0000">密码卡已被使用</span>');
						passCard = false;
						return false;
					}else
					{
						passCard = true;
						tipsDom.html('<span style="color:#ff0000"></span>');
					}
			   }
			});  
			return passCard;
		};
		passwordCardDom.focus(function(){
			var tipsDom = $(this).next();
			if($.trim(pass) == ''){
				tipsDom.html('密码卡序列号由9位数字组成');
			}
		});
		passwordCardDom.blur(checkCard);
		
		
		//电话
		var checkPhone = function(){
			var tipsDom = phoneDom.next(),v = $.trim(phoneDom.val());
			if(v == ''){
				tipsDom.html('<span style="color:#ff0000">电话不能为空</span>');
				return phone = false;
			}
			if(!(/^.{1,20}$/).test(v))
		    {
				tipsDom.html('<span style="color:#ff0000">1-20位字符组成</span>');
				return phone = false;
			}
			phone = true;
			tipsDom.html('');
			return phone;
			
		};
		phoneDom.focus(function(){
			var tipsDom = $(this).next();
			tipsDom.html('1-20位字符组成');
		});
		phoneDom.blur(checkPhone);
		
		
		
		//Email
		var checkEmail = function(){
			var tipsDom = emailDom.next(),v = $.trim(emailDom.val());
			if(v == ''){
				tipsDom.html('<span style="color:#ff0000">Email不能为空</span>');
				email = false;
				return false;
			}
			if(v.length < 6 || v.length > 50){
				tipsDom.html('<span style="color:#ff0000">长度有误，请输入6-50位字符</span>');
				email = false;
				return false;
			}
			if(!(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/).test(v)){
				tipsDom.html('<span style="color:#ff0000">请输入正确的email</span>');
				email = false;
				return false;
			}else{
				email = true;
				tipsDom.html('');
			}
			return email;
		};
		emailDom.focus(function(){
			var tipsDom = $(this).next();
			tipsDom.html('6-50位字符组成，例如abc@163.com');
		});
		emailDom.blur(checkEmail);

			
			//修改提交 
			var submitTimer;
			editButtonDom.click(function(){
				var data = formDom.serialize(),
				groupDom = $('#J-group-select'),
				fn = arguments.callee;
				
				if(!(checkUserName() && checkPassword() && checkConfirmPassword() && checkCard() && checkPhone() && checkEmail())){
					return;
				}
				if($.trim(groupDom.val()) == ''){
					groupDom.next().html('<span style="color:#ff0000">请选择所属权限组</span>');
					return;
				}else{
					groupDom.next().html('');
				}
				if(ajaxStack >= 0){
					clearTimeout(submitTimer);
					submitTimer = setTimeout(function(){
						if(ajaxStack <= 0){
							submitDataFn();
							clearTimeout(submitTimer);
						}
					}, 20);
				}else{
					ajaxStack = 0;
					clearTimeout(submitTimer);
				}
				});
			
			var submitDataFn = function(){
				var data = formDom.serialize(),
				groupDom = $('#J-group-select'),
				successDom = $('#successTips'),
				errorDom = $('#errorTips');
				$.ajax({
					url: baseUrl+'/aclAdmin/createUser?randomNum='+Math.random(),
					dataType: 'json',
					method:'POST',
					data: data,
					success:function(result){
						if(Number(result['status']) == 1){
							successDom.show();
							mask.show();
							setTimeout(function(){
								successDom.hide();
								mask.hide();
								location.href = location.href;
							},2000)
						}else{
							errorTips.show();
							mask.show();
							setTimeout(function(){
								errorTips.hide();
								mask.hide()
							},2000)
						}
					},
					complete: function(){

					}
				});
		};
});