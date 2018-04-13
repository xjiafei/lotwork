/*
 *
 * settings:
 *
 * awards 为存储每个等级状态的数组,got字段表示是否领过奖,true为领过,false为未领过, url为领奖链接
 * award数组元素的顺序对应一一对应不同的等级,比如用户达到"略有小成" 则添加3个元素, awards[0]存储'初学乍练'状态
 *
 * shortOf 存储距离下一个等级还需要多少钱
 *
 * */

//弹窗固定
function positionFixd(obj) {
    var $box = $(obj);
    function move() {
        var height = $box.height(),
            width = $box.width(),
            wH = $(window).height(),
            wW = $(window).width(),
            scroll = $(document).scrollTop();
        $box.css({left: (wW - width) / 2, top: scroll + (wH - height) / 2}, "fast")

    }

    $(document).ready(function () {
        setTimeout(move(), 100);
    })
    $(window).resize(function () {
        setTimeout(move(), 100);
    })
    $(window).scroll(function () {
        setTimeout(move(), 100);
    })

}
//计算显示领取按钮
function countStatus() {
    var len = settings.awards.length
    /*if (len < 7) {
        $('#rate' + len).append('<div class="fire"></div>')
        $('#rate' + len + ' .rate-words').css('color', '#ffffff')
        $('#rate' + len + ' .award-button').addClass('shortOf').html(settings.shortOf)
   }*/
   
    settings.awards.forEach(function (award, i) {
        
        if (settings.awards[i].got == 0) {
            $('#rate' + i + ' .award-button').addClass('getAward')
        } else if(settings.awards[i].got == 1){
            $('#rate' + i + ' .award-button').addClass('hasGot');
        } else if(settings.awards[i].got == 2){
            $('#rate' + i).append('<div class="fire"></div>')
            $('#rate' + i + ' .rate-words').css('color', '#ffffff')
            $('#rate' + i + ' .award-button').addClass('shortOf').html(settings.shortOf)
        } else {
            
        }
    });
}
            

$(document).ready(function () {
    var bRockW = 2248,
        sRockW = 2248
    var $bRock = $('#big-rock')
    var $sRock = $('#small-rock')
    var $popup = $('#popup')
    var $awardSum = $('#award-sum')
    var $mask = $('#mask')
    countStatus()
    positionFixd('#popup')
    //陨石动画
    $("#main").mousemove(function (event) {
        var screenW = $(document).width()
        var bCenter = (bRockW - screenW) / 2
        var sCenter = (sRockW - screenW) / 2
        $bRock.css('background-position-x', (-bCenter + (500 - event.pageX) / 4) + 'px')
        $sRock.css('background-position-x', (-sCenter + ( 500 - event.pageX ) / 8 ) + 'px')
        $bRock.css('background-position-y', (event.screenY) / 4 + 'px')
        $sRock.css('background-position-y', (event.screenY) / 8 + 'px')
    });
    //点击领奖事件
    $('.getAward').bind('click', function (event) {
        var id = event.target.parentNode.id //取到父层的id
             
        // 发送领奖请求
         $.ajax({
            method: "POST",
            url: '/activity/activity20170402award',
            data: {level:id,nowLevel:settings.nowLevel}
         }).done(function (data) {
             var info = JSON.parse(data);
             
             if(!info.isSuccess){
                    var message = '奖项领取錯誤!';
                    if(info.data.awardAmount == -100){
                        message = '请依顺序领奖喔~~!!';
                    }
                    
                    if(info.data.awardAmount == -101){
                        message = '已領取';
                        $('#' + id + ' .award-button').removeClass('getAward').addClass('hasGot').unbind('click')
                    }
                    alert(message);
                    return;
             }else{
                    $awardSum.html(info.data.awardAmount) // 将奖金写入弹窗
                    $popup.show()
                    $mask.show()
                    $('#' + id + ' .award-button').removeClass('getAward').addClass('hasGot').unbind('click')
             }
           
         });
        
    })
    $('#popup-button').on('click', function () {
        $popup.hide()
        $mask.hide()
    })
})
