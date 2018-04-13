$(document).ready(function () {
    $('.container').css({
        'width': window.innerWidth + 'px',
        'height': window.innerHeight + 'px'
    });
    $('.container .page').css('zoom', window.innerWidth / 640);
    $('.container').show();

    //---------------------------------------------------------------------------//

    //用户名输入框的聚焦和离焦事件
    $('#username,#vcode').focus(function (evt) {
        $(this).attr('placeholder', '');
    }).blur(function (evt) {
        if (this.id == 'username') {
           $(this).attr('placeholder', '请输入您的用户名');
            checkUsername(function () {
                $ajax('/login/checkusername','post',{'username':$.trim($('#username').val())},function(data){
                   console.log(data);
				    if (data['isSuccess'] == 1) {
                     $('#findpwd_username .inputOL li b').css('visibility', 'visible');
                     $('.login_tips span').html('').hide();
                     } else if (data['isSuccess'] == 0) {
					 $('#findpwd_username .inputOL li b').css('visibility', 'hidden');
                     $('.login_tips span').html('用户名不存在').show();
                     return;
                     }
                });
            });
        } else if (this.id == 'vcode') {
            $(this).attr('placeholder', '请输入验证码');
        }
    });


    //点击下一步
    $('#next').bind('click',function(evt){
        checkUsername(checkCode);
    })

    //点击右侧小圆圈刷新验证码
    $('.inputOL li i').bind('click',function(){
       reCode();
    });


    //用户名检查
    function checkUsername(callback) {
        var _name = $.trim($('#username').val());
        if (_name == '') {
            $('.login_tips span').html('用户名不能为空').show();
            $('#findpwd_username .inputOL li b').css('visibility', 'hidden');
            return;
        } else if (_name.length < 3 || _name.length > 16) {
            $('.login_tips span').html('用户名长度有误，请输入3-16位字符').show();
            $('#findpwd_username .inputOL li b').css('visibility', 'hidden');
            return;
        } else {
            $('.login_tips span').html('').hide();
        }
        if (callback) {
            callback();
        }
    }

    //验证码检查
    function checkCode(callback) {
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
        var params = {"username":_name, "vcode":_code};

        //点击下一步同时验证用户名和验证码
        $ajax('/login/checkvcode','post',{"vcode":_code},function(data){
            if(data['status'] == 1){
                $ajax('?stp=0','post',params,function(_data){
					console.log(_data);
                    if(_data['isSuccess'] == 1){
                        location.href = '?stp=1';
                    }else{
                        reCode();
						$('.login_tips span').html('验证码不正确').show();
                    }
                });
            }else{
				reCode();
                $('.login_tips span').html('验证码不正确').show();
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
            },
            success: function (data) {
                callback(data);
            },
            complete: function () {
                $('#next').removeAttr('disabled');
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

    //点击上面的返回按钮
    $('.title span').bind('click',function(evt){
        history.go(-1);
    });
    //点击回到登录页
    $('.backIndex').bind('click',function(evt){
        location.href='/login/';
    })

})