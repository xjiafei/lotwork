$(document).ready(function () {
    $('.container').css({
        'width': window.innerWidth + 'px',
        'height': window.innerHeight + 'px'
    });
    $('.container .page').css('zoom', window.innerWidth / 640);
    $('.container').show();

    $('#next').bind('click',function(evt){
        location.href = '/login/';
    })

    //点击上面的返回按钮
    $('.title span').bind('click',function(evt){
        history.go(-1);
    });

})