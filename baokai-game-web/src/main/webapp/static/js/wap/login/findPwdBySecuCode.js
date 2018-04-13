$(document).ready(function () {
    $('.container').css({
        'width': window.innerWidth + 'px',
        'height': window.innerHeight + 'px'
    });
    $('.container .page').css('zoom', window.innerWidth / 640);
    $('.container').show();

//------------------------------------------------------------//
	var checkPwd = false;
	
	$('#pwd').focus(function(evt){
        $(this).attr('placeholder','');
        $('.login_tips span').html('').hide();
    });
	
	
	$('#pwd').blur(function(evt){
        $(this).attr('placeholder','请输入安全密码');
            var re = $('#pwd').val().trim();
            if(re == ''){
                $('.login_tips span').html('安全密码为空').show();
				checkPwd = false;
                return;
            }else if(widthCheck(re)<6 ||  widthCheck(re) > 20){
				$('.login_tips span').html('长度有误，请输入6-20位字符').show();
				checkPwd = false;
                return;
			}
			checkPwd = true;
    });

	function widthCheck(str){
		var w = 0;  
			var tempCount = 0; 
			for (var i=0; i<str.length; i++) {  
			   var c = str.charCodeAt(i);  
			   //单字节加1  
			   if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
			    w++;  
			  
			   }else {     
			    w+=2;
			   }  
			 }
			return w;
	}
	
    //点击下一步
    $('#next').bind('click', function (evt) {
		$('.login_tips span').html('').hide();
			var pwd = $.trim($('#pwd').val());
			if(checkPwd){
				$.ajax({
				url: '?stp=4&act=1',
				type: 'post',
				dataType: 'json',
				data: {"safepwd": pwd},
				beforeSend: function () {
					$('#next').attr('disabled', 'disabled');
				},
				success: function (data) {
					console.log(data);
					if (data['isSuccess']) {
						location.href = '?stp=5';
					} else {
						$('.login_tips span').html(data['errors'][0][1]).show();
					}
				},
				complete: function () {
					$('#next').removeAttr('disabled');
				}
			});
		}
    });

    //点击上面的返回按钮
    $('.title span').bind('click', function (evt) {
        history.go(-1);
    });

})