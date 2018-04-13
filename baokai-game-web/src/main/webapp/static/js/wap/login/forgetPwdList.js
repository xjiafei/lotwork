$(document).ready(function () {
    $('.container').css({
        'width': window.innerWidth + 'px',
        'height': window.innerHeight + 'px'
    });
    $('.container .page').css('zoom', window.innerWidth / 640);
    $('.container').show();

    $('.inputOL li').bind('click', function (evt) {
        if ($(this).attr('isclick') == 'true') {
            location.href = $(this).attr('_href');
        }else{
            return;
        }
    })
	
    //点击上面的返回按钮
    $('.title span').bind('click',function(evt){
        history.go(-1);
    });

})