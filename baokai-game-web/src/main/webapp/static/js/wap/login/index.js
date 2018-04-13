$(document).ready(function () {
    $('.container').css({
        'width': window.innerWidth + 'px',
        'height': window.innerHeight + 'px'
    });
    $('.container .page').css('zoom', window.innerWidth / 640);
    $('.container').show();

    //充值提现
    $('.title div').bind('click', function (evt) {
        if ($(this).attr('class') == 'uptop') {
            location.href = $(this).attr('_href');
        }else if($(this).attr('class') == 'withdraw'){
            location.href = $(this).attr('_href');
        }
    })

    //操作列表
    $('#oplist li').bind('click',function(evt){
        location.href = $(this).attr('_href');
    })

    //退出
    $('.logout').bind('click',function(evt){
        location.href = $(this).attr('_href');
    })

    //底部下载链接
    $('.bottom li').bind('click',function(evt){
        location.href = $(this).attr('_href');
        //console.log($(this).attr('_href'));
    })

    //点击上面的返回按钮
    $('.title span').bind('click',function(evt){
        history.go(-1);
    });
	
	//窗口缩放
	$(window).on('resize',function(){
		$('.container').css({
        'width': window.innerWidth + 'px',
        'height': window.innerHeight + 'px'
		});
		$('.container .page').css('zoom', window.innerWidth / 640);
		$('.container').css('background', '#fff').show();
	});
})