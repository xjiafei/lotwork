$(document).ready(function () {
    $('.container').css({
        'width': window.innerWidth + 'px',
        'height': window.innerHeight + 'px'
    });
    $('.container .page').css('zoom', window.innerWidth / 640);
    $('.container').show();

    //倒计时
    var time = 60;
    var resend = false;
    var inter = setInterval(function(){
        time--;
        $('.count').html(time+'s');
        if(time == 0){
            clearInterval(inter);
            $('.unbtn').removeClass('unbtn');
            $('.count').hide();
            resend = true;
        }
    },1000);

	//邮件列表
	var mailList = {
		'sina.com':'https://mail.sina.com.cn',
		'sina.cn':'https://mail.sina.com.cn',
		'vip.sina.com':'https://mail.sina.com.cn',
		'gmail.com':'https://accounts.google.com/ServiceLogin?sacu=1&scc=1&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&osid=1&service=mail&ss=1&ltmpl=default&rm=false#identifier',
		'163.com':'http://mail.163.com',
		'126.com':'http://mail.163.com',
		'yeah.net':'http://mail.163.com',
		'qq.com':'https://mail.qq.com',
		'foxmail.com':'https://mail.qq.com',
		'sohu.com':'http://mail.sohu.com/fe',
		'139.com':'http://mail.10086.cn',
		'189.cn':'http://webmail30.189.cn/w2'
	};
	
	
    //点击查看邮件和重新发送的事件
    $('.btn_login').bind('click',function(evt){
        if($(this).attr('btn') == 'check'){
            var mail = $('.tiptext span').html().split('@');
            location.href = mailList[mail[1]];
        }else if($(this).attr('btn') == 'resend'){
            if(resend){
				location.href = '?stp=2';
            }
        }
    })

    //点击上面的返回按钮
    $('.title span').bind('click',function(evt){
        history.go(-1);
    });
})