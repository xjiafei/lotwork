$(document).ready(function () {
    $('.container').css({
        'width': window.innerWidth + 'px',
        'height': window.innerHeight + 'px'
    });
    $('.container .page').css('zoom', window.innerWidth / 640);
    $('.container').show();
 //-----------------------------------------------------------------------------//

    //用户名输入框的聚焦和离焦事件
    $('#username,#vcode').focus(function (evt) {
        $(this).attr('placeholder', '');
		$('.login_tips span').html('').hide();
    }).blur(function (evt) {
        checkUsername(function(){
            $ajax('/login/checkusername','post',{'username':$.trim($('#username').val())},function(data){
                /*var data = {status: 1, msg: 'success'}*/           //用ajax后注释该句
                 if (data['isSuccess'] == 1) {
                 $('#login_security_form_username .inputOL li b').css('visibility', 'visible');
                 $('.login_tips span').html('').hide();
                 } else if (data['isSuccess'] == 0) {
				 $('#login_security_form_username .inputOL li b').css('visibility', 'hidden');
                 $('.login_tips span').html('用户名不存在').show();
                 return;
                 }
            });
        });
    });

    //点击下一步
    $('#next').bind('click',function(evt){
        var _name = $.trim($('#username').val());
        var _code = $.trim($('#vcode').val());
        checkUsername(checkCode);
    })

    //点击右侧小圆圈刷新验证码
    $('.inputOL li i').bind('click',function(){
          reCode();
    });

    //密码框输入
    $('#pwd').focus(function(evt){
        $(this).attr('placeholder','');
    }).keypress(function(evt){
        $('.inputOL li b').css('visibility','visible');
    }).blur(function(evt){

    })

    //点击叉按钮清空密码输入框
    $('.inputOL li b').click(function(evt){
        $(this).css('visibility','hidden');
        $('#pwd').val('');
        $('#pwd').attr('placeholder','请输入您的密码');
    });

    //点击登录按钮
    $('#login').click(function(evt){
        checkPwd(function(_pwd){
            var _param = $('#param').val();
            _pwd = $.md5($.md5($.md5($.md5($.md5(_pwd)))) + _param);
            var params = {"password":_pwd,"param":_param};
            $ajax('/login/sectlogin', 'post', params, function (data) {
                console.log(data);
                if (data['errors'].length > 0) {
					$('.login_tips span').html(data['errors'][0][1]).show();
					location.reload();
                } else {
                    location.href = '/index';
                }
            })
        });
    })

    //用户名检查
    function checkUsername(callback) {
        var _name = $.trim($('#username').val());
        if (_name == '') {
            $('.login_tips span').html('用户名不能为空').show();
            $('#login_security_form_username .inputOL li b').css('visibility', 'hidden');
            return;
        } else if (_name.length < 3 || _name.length > 16) {
            $('.login_tips span').html('用户名长度有误，请输入3-16位字符').show();
            $('#login_security_form_username .inputOL li b').css('visibility', 'hidden');
            return;
        } else {
            $('.login_tips span').html('').hide();
        }
        if (callback) {
            callback();
        }
    }

    //密码非空检查
    function checkPwd(callback) {
        var _pwd = $.trim($('#pwd').val());
        if (_pwd == '') {
            $('.login_tips span').html('密码不能为空').show();
            return;
        }else{
            $('.login_tips span').html('').hide();
        }
        if(callback){
            callback(_pwd);
        }
    }

    //验证码检查
    function checkCode() {
        var _code = $.trim($('#vcode').val()).toUpperCase();
        if (_code == '') {
            $('.login_tips span').html('请输入验证码').show();
            return;
        } else if (!/^\w{4}$/.test(_code)) {
            $('.login_tips span').html('验证码不正确').show();
            return;
        } else {
            $('.login_tips span').html('').hide();
        }
        var _name = $.trim($('#username').val());
        var params = {"username":_name, "vcode":_code,"safemod":1};
        //点击下一步
        $ajax('/login/login','post',params,function(data){
            if(data['errors'].length > 0){
				$('.login_tips span').html(data['errors'][0][1]).show();
				reCode();
            }else{
                $('#login_security_form_username,.btn_login').hide();
                $('#login_security_form_pwd,#login').css('display','block');
				if(data['data']['cipher'] != null){
					$('#ciphertext').text(data['data']['cipher']);
					$('#cipher2').hide();
				}else{
					$('#cipher1').hide();
				}
            }
        })
    }



    //封装ajax请求
    function $ajax(url, method, data, callback) {
        $.ajax({
            url: url,
            type: method,
            dataType: 'json',
            data: data,
            beforeSend: function () {
                $('.login_tips span').html('').hide();
                $('#next').attr('disabled', 'disabled');
                $('#login').attr('disabled', 'disabled');
            },
            success: function (data) {
                callback(data);
            },
            complete: function () {
                $('#next').removeAttr('disabled');
                $('#login').removeAttr('disabled');
            },
            fail: function () {
                alert('ajax error');
            }
        });
    }

    //刷新验证码
    function reCode(){
        $('.inputOL li img').attr('src', $('.inputOL li img').attr('src').split('?')[0] + '?' + Math.floor(Math.random() * 10000));
    }

})