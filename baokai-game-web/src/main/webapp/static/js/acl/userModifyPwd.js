$(document).ready(function(){
	$('.menu-list li:eq(8)').attr("class","current");
	var oldVal = '',
		newVal = '',
		confirmVal = '',
		id="",
		oldDom = $('#oldpassword'),
		newDom = $('#newpassword'),
		confirmDom = $('#confirmpassword'),
		formDom = $('#info-content'),
		submitButtonDom = $('#submit'),
		mask = phoenix.Mask.getInstance(),
		message = phoenix.Message.getInstance();
		oldDom.val('');
		newDom.val('');
		confirmDom.val('');

		oldDom.focus(function(){
			var par=$(this).parent();
			var tipsDom = par.find(".ui-prompt");
			par.find(".ui-check").hide();
			if($.trim($(this).val())==''){
				tipsDom.html('<i></i>请输入当前登录密码');
				tipsDom.show();
			}
		});
		newDom.focus(function(){
			var par=$(this).parent();
			var tipsDom = par.find(".ui-prompt");
			par.find(".ui-check").hide();
			if($.trim($(this).val())==''){
				tipsDom.html('<i></i>6-20位字符组成，区分大小写');
				tipsDom.show();
			}
		});
		confirmDom.focus(function(){
			var par=$(this).parent();
			var tipsDom = par.find(".ui-prompt");
			par.find(".ui-check").hide();
			if($.trim($(this).val())==''){
				tipsDom.html('<i></i>请再次输入登录密码');
				tipsDom.show();
			}
		});

		oldDom.blur(function(){
			var par=$(this).parent();
			var tipsDom = par.find(".ui-check"),tipsPrompt=par.find(".ui-prompt");
			if($(this).val() == ''){
				tipsDom.html('<i></i>请输入当前登录密码');
				par.find(".ui-prompt").hide();
				tipsDom.show();
			}else{
				var dataf = formDom.serialize();
				var oldv= $(this).val();
				$.ajax({
                url: baseUrl+'/aclAdmin/checkOldPwd',
                dataType: 'json',
                type: 'POST',
                data: dataf+"&id="+id,
                success:function(data){
						if(Number(data['status']) == 0)
						{
							par.find(".ui-prompt").hide();
                            par.find(".ui-check").html('<i></i>请输入正确的当前登录密码');
                            par.find(".ui-check").show();
						}else
						{
							tipsDom.html('');
				            oldVal = oldv;
							tipsDom.hide();
							tipsPrompt.hide();
						}
				   }
				});
			}
		});
		newDom.blur(function(){
			var par=$(this).parent(),tipsDom = par.find(".ui-check"),tipsPrompt=par.find(".ui-prompt"),
				content = $(this).val();
			if($.trim(content)=="")
			{
				tipsDom.html('<i></i>请输入新的登录密码');
				par.find(".ui-prompt").hide();
				tipsDom.show();
				
			}else if(content.length < 6 || content.length > 20){
				tipsDom.html('<i></i>6-20位字符组成，区分大小写');
				par.find(".ui-prompt").hide();
				tipsDom.show();
			}else{
				tipsDom.hide();
				tipsPrompt.hide();
			}
		});
		confirmDom.blur(function(){
			var par=$(this).parent(),tipsDom = par.find(".ui-check"),tipsPrompt=par.find(".ui-prompt"),
				password = newDom.val();

			if($.trim($(this).val()) == ''){
				par.find(".ui-prompt").hide();
				tipsDom.html('<i></i>请再次输入登录密码');
				tipsDom.show();
				return;
			}else if($(this).val() == password){
				tipsDom.html('');
				confirmVal = password;
				tipsPrompt.hide();
			}else{
				par.find(".ui-prompt").hide();
				tipsDom.html('<i></i>两次密码输入不一致');
				tipsDom.show();
			}
		});

		submitButtonDom.click(function(){
            var dataf = formDom.serialize(),
                successDom = $('#successTips'),
                errorDom = $('#errorTips'),
                oldDom = $('#oldpassword'),
                newDom = $('#newpassword'),
                confirmDom = $('#confirmpassword');
            //密码
            if($.trim(oldDom.val()) == ''){
                oldDom.parent().find(".ui-check").html('<i></i>请输入当前登录密码');
                oldDom.parent().find(".ui-check").show();
                return;
            }
			if($.trim(newDom.val()) == '')
			{
				newDom.parent().find(".ui-check").html('<i></i>请输入新的登录密码');
				newDom.parent().find(".ui-check").show();
				return;
			}
			if($.trim(newDom.val()).length <6 || $.trim(newDom.val()).length >20 )
			{
				newDom.parent().find(".ui-check").html('<i></i>6-20位字符组成，区分大小写');
				newDom.parent().find(".ui-check").show();
				return;
			}
			if($.trim(confirmDom.val())=='')
			{
				confirmDom.parent().find(".ui-check").html('<i></i>请再次输入登录密码');
				confirmDom.parent().find(".ui-check").show();
				return;
			}
			if($.trim(confirmDom.val()) == $.trim(newDom.val()))
			{
				confirmDom.parent().find(".ui-check").hide();
			}else
			{
			    confirmDom.parent().find(".ui-check").html('<i></i>两次密码输入不一致');
				confirmDom.parent().find(".ui-check").show();
				return;
			}
           /* $.ajax({
                url: baseUrl+'/aclAdmin/checkOldPwd',
                dataType: 'json',
                type: 'POST',
                data: dataf+"&id="+id,
                success:function(data){
                    if(Number(data['status']) == 1){*/
                        $.ajax({
                            url: baseUrl+'/aclAdmin/modifyPwd',
                            dataType: 'json',
                            type: 'POST',
                            data: dataf+"&id="+id,
                            success:function(data){
                                if(Number(data['status']) == 1){
                                    successDom.show();
                                    mask.show();
                                    setTimeout(function(){
                                        successDom.hide();
                                        mask.hide()
                                    },2000)
                                }else{
                                    errorDom.show();
                                    mask.show();
                                    setTimeout(function(){
                                        errorDom.hide();
                                        mask.hide()
                                    },2000)
                                }
                            },
                            complete: function(){

                            }
                        });
                   /* }else{
						oldDom.parent().find(".ui-prompt").hide();
                        oldDom.parent().find(".ui-check").html('<i></i>请输入正确的当前登录密码');
                        oldDom.parent().find(".ui-check").show();
                        return;
                    }
                },
                complete: function(){

                }
            });*/
        }); 

})