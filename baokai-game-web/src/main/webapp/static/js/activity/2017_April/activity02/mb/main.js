/*
 *
 * settings:
 *
 * awards 为存储每个等级状态的数组,got字段表示是否领过奖,true为领过,false为未领过, url为领奖链接
 * award数组元素的顺序一一对应不同的等级,比如用户达到"略有小成" 则添加3个元素, awards[0]存储'初学乍练'状态
 *
 * shortOf 存储距离达到下一个等级还需要多少钱
 *
 * */
/*var settings = {
    awards: [
        {
            got: false,
            url: ''
        },
        {
            got: false,
            url: ''
        },
        {
            got: true,
            url: ''
        },
        {
            got: false,
            url: ''
        },



    ],
    shortOf: 12312,//这个一定不能算错,比如不能出现数额超过当前等级上限,进度条会出错
    levels: [0, 1000, 10000, 80000, 150000, 200000, 500000, 800000]
}*/
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

$(document).ready(function () {
    var $popup = $('#popup')
    var $awardSum = $('#award-sum')
    var $mask = $('#mask')
    var $progressStatus = $('#progress-status')
    var $progress = $('#progress')
    var $shortOf = $('#shortOf')
    var $getAwardArea = $('#get-award-area')
    var len = settings.awards.length
    

    positionFixd('#popup')
    $("#right-scroll").click(function () {
        $('#rate').animate({
                scrollLeft: 500
            },
            800);
    });
    $("#left-scroll").click(function () {
        $('#rate').animate({
                scrollLeft: -500
            },
            800);
    });
    $('#popup-button').on('click', function () {
        $popup.hide()
        $mask.hide()
    })
    //点击领取点击后回调
    function clickGetAward(event) {
        
        $getAwardArea.unbind('click')//防止重复点击
        var index = 0
        if (len > 0) {
            for (var i = 0; i < len; i++) {
                if (!settings.awards[i].got) {
                    index = i
                    break
                }
            }
                      
            $.ajax({
                method: "POST",
                url: '/activitymb/activity20170402award',
                data: {level:"rate"+index,token:$('#token').val()}
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
                     $('#mask').show()
                     settings.awards[index].got = 1;
                     $('#rate' + index + ' .fire-area').removeClass('fire');
                     init();
                     
                }
            }).fail(function(){
                init()
            })
        }
    }
    //显示领取奖励的样式
    function showGetAward(index) {
        $progressStatus.addClass('get-award').html('领取奖励')
        $getAwardArea.css({ "cursor": "pointer"})
        $progress.css('width', '100%')
        $('#rate' + index +  ' .fire-area').addClass('fire')
        $('#rate' + index + ' .rate-words').css('color', '#ffffff')
        $getAwardArea.bind('click', clickGetAward)

    }
    //显示"当前销量还需"的样式
    function showShort(index) {
        var width = (1-settings.shortOfAmount / (settings.levels[index + 1] - settings.levels[index])) * 100
        $progressStatus.removeClass('get-award').addClass('progress-words').html('当前有效销量还需')
        $progress.css('width', width + '%')
        $shortOf.html(settings.shortOf)
        //$('#rate' + index + ' .fire-area').addClass('fire')
        //$('#rate' + index + ' .rate-words').css('color', '#ffffff')
        $getAwardArea.unbind('click')
    }
    //显示全部已领取的样式
    function showAllGot() {
        $progress.css('width',0)
        $getAwardArea.css({'background-color': '#2e2e2e', "border-color": "#2e2e2e"})
        $progressStatus.removeClass('get-award').addClass('gotAll').html('全部已领取')
        $getAwardArea.unbind('click')
    }

    //初始化状态
    //有領獎的 只秀領獎的按鈕  不會秀 投注差額 都领完了才会显示差额
    //  $('#rate' + i + ' .award-button').addClass('hasGot') 加上已領獎
    function init(){
        var allGot = true
        var gotCnt = 0;
        
        for (var i = 0; i < len; i++) {
            if (settings.awards[i].got == 2) {
                 showShort(i);
                 
            }else if(settings.awards[i].got == 1){
                 $('#rate' + i + ' .award-button').addClass('hasGot')
                 gotCnt++;
            }else if(settings.awards[i].got == 0){
                 showGetAward(i);
                 break;
            }else{
                
            }
        }
      
        if(gotCnt >= 7){
            showAllGot();
        }
    }
    init()
})
