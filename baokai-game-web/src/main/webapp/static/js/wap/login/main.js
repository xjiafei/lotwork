$(document).ready(function () {
    $('.container').css({
        'width': window.innerWidth + 'px',
        'height': window.innerHeight + 'px'
    });
    $('.container .page').css('zoom', window.innerWidth / 640);
    $('.container').css('background', '#fff').show();


    //点击右上角
    $('.title span').bind('click',function(evt){
        location.href = '/index/userdetail';
    })

});
