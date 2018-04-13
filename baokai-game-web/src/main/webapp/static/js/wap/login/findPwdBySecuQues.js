$(document).ready(function () {
    $('.container').css({
        'width': window.innerWidth + 'px',
        'height': window.innerHeight + 'px'
    });
    $('.container .page').css('zoom', window.innerWidth / 640);
    $('.container').show();
//--------------------------------------------------------------------------------//
	var checkAn1 = false;
	var checkAn2 = false;
    var q = [$('.quest0').attr("placeholder"),$('.quest1').attr("placeholder"),$('.quest2').attr("placeholder")];
	var v = [$('.quest0').val(),$('.quest1').val(),$('.quest2').val()]
//问题
    $('.inputOL li i').bind('click',function(evt){
        if($(this).hasClass('last')){               //第二个问题
            var q2 = $('#q2').attr('value');
            var q1 = $('#q1').attr('value');
            for(var i = 0; i<v.length;i++){
                if(v[i] == q1 || v[i] == q2){
                    continue;
                }
                $('#q2').attr('value',v[i]).html(q[i]);
            }
        }else{                                      //第一个问题
            var q2 = $('#q2').attr('value');
            var q1 = $('#q1').attr('value');
            for(var i = 0; i<v.length;i++){
                if(v[i] == q1 || v[i] == q2){
                    continue;
                }
                $('#q1').attr('value',v[i]).html(q[i]);
            }
        }
    })


//答案
    $('#an1,#an2').focus(function(evt){
        $(this).attr('placeholder','');
        if(this.id == 'an1'){
            $('.login_tips span#tip1').html('').hide();
        }else if(this.id == 'an2'){
            $('.login_tips span#tip2').html('').hide();
        }
    });
	$('#an1,#an2').blur(function(evt){
        $(this).attr('placeholder','请输入答案');
        if(this.id == 'an1'){
            var re = $('#an1').val().trim();
            if(re == ''){
                $('.login_tips span#tip1').html('答案不能为空').show();
				checkAn1 = false;
                return;
            }else if(widthCheck(re)<4 ||  widthCheck(re) > 16){
				$('.login_tips span#tip1').html('长度有误，请输入4-16位字符').show();
				checkAn1 = false;
                return;
			}
			checkAn1 = true;
        }else if(this.id = 'an2'){
            var re = $('#an2').val().trim();
            if(re == ''){
                $('.login_tips span#tip2').html('答案不能为空').show();
				checkAn2 = false;
                return;
            }else if(widthCheck(re)<4 ||  widthCheck(re) > 16){
				$('.login_tips span#tip2').html('长度有误，请输入4-16位字符').show();
				checkAn2 = false;
                return;
			}
			checkAn2 = true;
        }
    });

    //点击下一步
    $('#next').bind('click', function (evt) {
		$('.login_tips span#tip3').html('').hide();
        var q1 = $('#q1').attr('value');
        var q2 = $('#q2').attr('value');
        var an1 = $.trim($('#an1').val());
        var an2 = $.trim($('#an2').val());
		if(checkAn1 && checkAn2){
			if(an1!='' && an2 != ''){
				$.ajax({
					url: '?stp=3&act=1',
					type:'post',
					dataType: 'json',
					data: {"questId": q1, "questId2": q2, "questAns": an1, "questAns2": an2},
					beforeSend: function () {
						$('#next').attr('disabled','disabled');
					},
					success: function (data) {
						console.log(data);
						if(data['isSuccess'] == 1) {
							location.href = '?stp=5';
						}else{
							$('.login_tips span#tip3').html(data['errors'][0][1]).show();
						}
					},
					complete: function () {
						$('#next').removeAttr('disabled');
					}
				})
			}
		}
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
	
    //点击上面的返回按钮
    $('.title span').bind('click', function (evt) {
        history.go(-1);
    });
})