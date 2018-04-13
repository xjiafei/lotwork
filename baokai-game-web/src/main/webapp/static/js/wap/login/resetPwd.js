$(document).ready(function () {
    $('.container').css({
        'width': window.innerWidth + 'px',
        'height': window.innerHeight + 'px'
    });
   $('.container .page').css('zoom', window.innerWidth / 640);
    $('.container').show();
//------------------------------------------------------------------------//
	var checkPwd = false;
	var checkNext = false;
    //输入框的聚焦和离焦事件
    $('#pwd1,#pwd2').focus(function (evt) {
        $(this).attr('placeholder', '');
        if (this.id == 'pwd1') {
            $('#tip1').html('').hide();
        } else if (this.id == 'pwd2') {
            $('#tip2').html('').hide();
        }
    }).blur(function (evt) {
        if (this.id == 'pwd1') {
            if(checkPwd1()){
				$.ajax({
					url:'?stp=5&act=chck',
					type:'post',
					dataType:'json',
					data:{"pwd":$('#pwd1').val().trim()},
					success: function (data) {
						console.log(data);
						if(data['isSuccess'] !='1'){
							$('#tip1').html(data['data']).show();
							checkPwd = false;
						}else{
							checkPwd = true;
						}
					}
				})
			}
        } else if (this.id == 'pwd2') {
            $(this).attr('placeholder', '请再次输入登录密码');
			var pwd1 = $('#pwd1').val().trim();
			var pwd2 = $('#pwd2').val().trim();
			if(checkPwd1()){
				if(pwd2 == ''){
					$('#tip2').html('密码为空').show();
					checkNext =	false;
					return;
				}
				if(pwd1 != pwd2){
					$('#tip2').html('两次输入的密码不一致').show();
					checkNext =	false;
					return;
				}
				checkNext =	true;
			}
			
        }
    });


    //点击下一步
    $('#next').bind('click',function(evt){
		if(!checkPwd){
			return;
		}
		if($('#pwd2').val().trim() == ''){
			$('#tip2').html('密码为空').show();
			return;
		}
		var pwd1 = $('#pwd1').val().trim();
        if(checkNext){
            $.ajax({
                url:'?stp=5&act=reset',
                type:'post',
                dataType:'json',
                data:{"pwd":pwd1},
                beforeSend: function () {
                    $('#next').attr('disabled','disabled');
                },
                success: function (data) {
                    console.log(data);
                    if(data['isSuccess'] =='1'){
                        location.href = '?stp=6';
                    } else {
						$('#tip2').html(data['data']).show();
                    }
                },
                complete: function () {
                    $('#next').removeAttr('disabled');
                }
            })
        }
    })
    //检查密码
    function checkPwd1() {
        var pwd1 = $('#pwd1').val().trim();
        if(pwd1 == ''){
            $('#tip1').html('请输入6-20位密码').show();
            return false;
        }
        if(pwd1.length<6 || pwd1.length >20){
            $('#tip1').html('长度有误，请输入6-20位密码').show();
            return false;
        }
        if(pwd1.length >=6 && pwd1.length <9){
            var reg = /^\d+$/;
            if(reg.test(pwd1)){
                $('#tip1').html('密码不能是9位以下纯数字').show();
                return false;
            }
        }
		return true;
    }
	
    //点击上面的返回按钮
    $('.title span').bind('click',function(evt){
        history.go(-1);
    });
})