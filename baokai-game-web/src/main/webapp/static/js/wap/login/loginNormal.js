$(document).ready(function () {
    $('.container').css({
        'width': window.innerWidth + 'px',
        'height': window.innerHeight + 'px'
    });

    $('.container .page').css('zoom', window.innerWidth / 640);
    $('.container').show();
//-----------------------------------------------------------------------------//


    //用户名或密码聚焦及失焦
    $('input').focus(function (evt) {
        $(this).attr('placeholder', '');
    }).blur(function (evt) {
        if (this.id == 'username') {
            $(this).attr('placeholder', '请输入您的用户名');
            checkUsername(function () {
                $ajax('/login/checkusername','post',{'username':$.trim($('#username').val())},function(data){
                   console.log(data);
				    if (data['isSuccess'] == 1) {
                     $('#login_normal_form .inputOL li b').css('visibility', 'visible');
                     $('.login_tips span').html('').hide();
                     } else if (data['isSuccess'] == 0) {
					 $('#login_normal_form .inputOL li b').css('visibility', 'hidden');
                     $('.login_tips span').html('用户名不存在').show();
                     return;
                     }
                });
            });
        } else if (this.id == 'password') {
            $(this).attr('placeholder', '请输入您的密码');
        } else if (this.id == 'vcode') {
            $(this).attr('placeholder', '请输入验证码');
        }
    });

    //点击登录按钮
    $('#login').on('click', function (evt) {

        var _name = $.trim($('#username').val());
        var _pwd = $.trim($('#password').val());
        var _code = $.trim($('#vcode').val());
        var _param = $('#param').val();

        $('.login_tips span').html('').hide();

        checkUsername(checkPwd);
        _pwd =  $.md5($.md5($.md5($.md5($.md5(_pwd))))+_param);

        var params = {};
        if ($('#login_normal_form .inputOL li:last-child').css('display') == 'none') {
            params = {"username": _name, "password": _pwd, "param":_param};
        } else {
            checkCode();
			vVcode = $.trim(_code).toUpperCase();
            params = {"username": _name, "password": _pwd, "param":_param, "vcode": vVcode};
        }
        //console.log(params);
        $ajax('/login/login','post',params,function(data){
            if (data['errors'].length > 0) {            
                var showLastLi = $('#login_normal_form .inputOL li:last-child').css('display') ? 'block' : 'none';
                $('.login_tips span').html(data['errors'][0][1]).show();
                if (!showLastLi || (data['data'] && data['data'] > 2)) {
                    $('#login_normal_form .inputOL li:last-child').show();
                    $('#vcode').val('');
                    reCode();
                }
                if (data['errors'][0][0] == 'password') {
                    $('#pwd').focus();
                }
                if (data['errors'][0][0] == 'username') {
                    $('#username').focus();
                }
                $('#param').val(data['param']);
            } else {
                location.href = '/index';
            }
        });

    });

	//刷新验证码
    $('.inputOL li i').bind('click', function () {
         reCode();
    })

   //用户名检查
    function checkUsername(callback) {
        var _name = $.trim($('#username').val());
        if (_name == '') {
            $('.login_tips span').html('用户名不能为空').show();
            $('#login_normal_form .inputOL li b').css('visibility', 'hidden');
            return;
        } else if (_name.length < 3 || _name.length > 16) {
            $('.login_tips span').html('用户名长度有误，请输入3-16位字符').show();
            $('#login_normal_form .inputOL li b').css('visibility', 'hidden');
            return;
        } else {
            $('.login_tips span').html('').hide();
        }
        if (callback) {
            callback();
        }
    }

    //密码非空检查
    function checkPwd() {
        var _pwd = $.trim($('#password').val());
        if (_pwd == '') {
            $('.login_tips span').html('密码不能为空').show();
            return;
        }else{
            $('.login_tips span').html('密码不能为空').show();
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
                $('#login').attr('disabled', 'disabled');
            },
            success: function (data) {
                callback(data);
            },
            complete: function () {
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