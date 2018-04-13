//init url
var initUrl = '/iapi/event/doOlympicInit';
//sign url
var signUrl = '/iapi/event/doOlympicSignUp';
//token
var token = '';
//init
var prize = '';
var status = '';
var rebateRatio = '';
var plusRatio = '';
var betAmount = '';
var chargeAmount = '';
var medals = 0;
var result = '';
//sign
var signStaus = '';

$(document).ready(function () {

    function getQueryString(name){
        var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
        var r = window.location.search.substr(1).match(reg);
        if (r != null){
            return unescape(r[2]);
        }
        return null;
    }

    function converString(strV) {   
        while(strV.search(/\-/) != -1) {
            strV = strV.replace(/\-/, "/");
        }
        while(strV.search(/\$/) != -1) {
            strV = strV.replace(/\$/, "%");
        }
        while(strV.search(/\!/) != -1) {
            strV = strV.replace(/\!/, "=");
        }
        while(strV.search(/\*/) != -1) {
            strV = strV.replace(/\*/, "+");
        } 
        return strV;
    }

    function IsParameterReady(par) {
        if (typeof(par) == 'undefined' || !par) {
            if (par !== 0) {
                return false;
            }
        }
        return true;
    }
    //get token
    token = getQueryString('sid');
    if (IsParameterReady(token)) {
        token = converString(token);
    } else {
        alert('请重新登录');
        return false;
    }

    $('.container').css({
        'width': window.innerWidth + 'px',
        'height': window.innerHeight + 'px'
    });

    $('.container .page').css('zoom', window.innerWidth / 750);
    $('.container').show();

    //查看计算规则
    $('.rule').bind('click', function (evt) {
        $('#main').hide();
        $('.curtion').show();
    });

    //返回
    $('.back').bind('click', function (evt) {
        $('.curtion').hide();
        $('#main').show();
    });

    //设置弹出层的高度，和内层的位置
    $('.cover').height($($('.page')[1]).height() + 'px');

    $.ajax({
        type: 'POST',
        url: initUrl,
        contentType: "application/json; charset=utf-8",
        processData: false,
        data: '{"head":{"sowner":0,"rowner":0,"msn":0,"msnsn":0,"userId":0,"userAccount":"","sessionId":"'+token+'"},"body":{"param":{},"pager":{"startNo":0,"endNo":0}}}',
        dataType: 'json',
        success: function(result) {

            status      = result['body']['result']['status'];
            prize       = result['body']['result']['prize'];
            rebateRatio = result['body']['result']['rebateRatio'];
            plusRatio   = result['body']['result']['plusRatio'];
            betAmount   = result['body']['result']['betAmount'];
            chargeAmount= result['body']['result']['chargeAmount'];
            medals      = result['body']['result']['medals'];
            result      = result['body']['result']['result'];

            $('.uptop, .t1').text(chargeAmount);
            $('.sales').text(betAmount);
            $('.money, .t6').text(prize);
            $('.t2').text(rebateRatio+'%');
            $('.t4').text(plusRatio+'%');
            $('.t5').text(medals);

            //有多少金牌
			// $('.ten').css('backgroundImage', 'url(' + Math.floor(parseInt(medals) / 10) + '.png');
            //$('.bit').css('backgroundImage', 'url(' + parseInt(medals) % 10 + '.png');
			var divs = document.getElementsByTagName('div');
			for(var i = 0; i < divs.length; i++){
				if(divs[i].className == 'gold ten'){
					divs[i].style.backgroundImage = 'url('+Math.floor(parseInt(medals) / 10)+'.png)';
				}else if(divs[i].className == 'gold bit'){
					divs[i].style.backgroundImage = 'url('+Math.floor(parseInt(medals) % 10)+'.png)';
				}
			}	

            if (result=='1' && status == '0' ) {
                $('.enter').show(); 
            } else {
                $('.enter').hide();
            }
        },
        error: function(xhr) {
            if(xhr.status == '404' || xhr.status == '0' || xhr.status == '401' ){
                alert('请重新登录');
            }else{
                alert('未知网络错误');
            }
        }
    });

    //点击立即报名
    $('.enter').bind('click',function(evt){

        $.ajax({
            type: 'POST',
            url: signUrl,
            contentType: "application/json; charset=utf-8",
            processData: false,
            data: '{"head":{"sowner":0,"rowner":0,"msn":0,"msnsn":0,"userId":0,"userAccount":"","sessionId":"'+token+'"},"body":{"param":{"month":8, "source":"mobile"},"pager":{"startNo":0,"endNo":0}}}',
            dataType: 'json',
            success: function(result) {

                signStaus   = result['body']['result'];

                if (signStaus !== 1) {
                    alert('报名失败，稍后再试');
                } else {
                    //alert('报名成功');
                    $('.enter').hide();
                    $('.cover').show();
                    //报名成功后点击确定
                    $('.confirm').bind('click',function(evt){
                        $('.enter').hide();
                        $('.cover').hide();
                    });
                }
            },
            error: function(xhr) {
                if(xhr.status == '404' || xhr.status == '0' || xhr.status == '401' ){
                    alert('请重新登录');
                }else{
                    alert('未知网络错误');
                }
            }
        });
    });//end of bind click
});//end of ready










