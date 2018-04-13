
$(function(){
    /*main*/
    //main content
    var mainContent = '';
    //main flag (0:fail, 1:success)
    var mainIsSuccess = '';
    //main prize flag (false:none, true:done)
    var mainIsPrize = '';
    //main get total reward
    var mainTotalReward = '';
    //main get today end time
    var mainEndTime = '';
    //main get total bet amount
    var mainBetAmount = '';
    //main get exchange ratio 
    var mainBetRatio = '';
    //main get reward list
    var mainRewardList = '';

    /*slot*/
    //slot content
    var slotContent = '';
    //slot flag (0:fail, 1:success)
    var slotIsSuccess = '';
    //slot get total reward
    var slotTotalReward = '';
    //slot get multiple number
    var slotMultiple = '';

    /*reward list*/
    //reward list content
    var listContent = '';
    //get reward list
    var listRewardList = '';

    //main url
    var mainUrl = "/iapi/event/doMayActInit";
    //slot url
    var slotUrl = "/iapi/event/doMayActAward";
    //lotto lobby
    var homeUrl = 'phlotto://go_lottolobby';
    //net error
    var errMsg = '未知网络错误';

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

    token = getQueryString('sid');
    if (IsParameterReady(token)) {
        token = converString(token);
    }
    if (!IsParameterReady(token)) { 
        window.location.href = homeUrl;
    }
    
    function IsParameterReady(par) {
        if (typeof(par) == 'undefined' || !par) {
            if (par !== 0) {
                return false;
            }
        }
        return true;
    }

    function getRewardList() {
        $.ajax({
            url: mainUrl,
            dataType: 'json',
            type: 'post',
            contentType: "application/json; charset=utf-8",
            processData: false,
            data: '{"head":{"sowner":0,"rowner":0,"msn":0,"msnsn":0,"userId":0,"userAccount":"","sessionId":"'+token+'"},"body":{"param":{},"pager":{"startNo":0,"endNo":0}}}'
        }).done(function(result){
            if (IsParameterReady(result) && IsParameterReady(result['body']['result']['data'])) {
                listRewardList = result['body']['result']['data'];
                var data = listRewardList;
                var name = data[0];
                var win = data[1];
                var nameTemp = '';
                $("#notice ul").find('li').remove();
                for (var i = 0; i < data.length; i++) {
                    nameTemp = '';
                    nameTemp = data[i].name.substr(0, 1);
                    nameTemp += '***';
                    nameTemp += data[i].name.substr(-1, 1);
                    data[i].name = nameTemp;
                    data[i].win = parseInt(data[i].win, 10);
                    if (!isNaN(data[i].win)) {
                        data[i].win /= 10000;
                        data[i].win = Math.ceil(data[i].win);
                    } else {
                        data[i].win = 0;
                    }
                    var html = '<li>恭喜 ' + data[i].name + ' 获得 ' + data[i].win + '</li>';
                    $("#notice ul").append(html);
                }
            }
        });
    }

   
    $.ajax({
        url:mainUrl,
        dataType:'json',
        type: 'post',
        contentType: "application/json; charset=utf-8",
        processData: false,
        data: '{"head":{"sowner":0,"rowner":0,"msn":0,"msnsn":0,"userId":0,"userAccount":"","sessionId":"'+token+'"},"body":{"param":{},"pager":{"startNo":0,"endNo":0}}}',
        success:function(res){
            mainContent = res;
            mainIsSuccess = mainContent['body']['result']['isSuccess'];
            mainTotalReward = mainContent['body']['result']['hisBonus'];
            mainEndTime = mainContent['body']['result']['endTime'];
            mainBetAmount = mainContent['body']['result']['betAmount'];
            mainBetRatio = mainContent['body']['result']['betRatio'];
            mainRewardList = mainContent['body']['result']['data'];

            //bar
            if (IsParameterReady(mainBetAmount)) {
                    mainBetAmount = parseInt(mainBetAmount, 10);
                    if (!isNaN(mainBetAmount)) {
                        mainBetAmount /= 10000;
                        mainBetAmount = Math.ceil(mainBetAmount);
                    } else {
                        mainBetAmount = 0;
                    }
                    var barBetAmount = mainBetAmount;    
                    var betNum = [[0,1500],[1500,10000],[10000,50000],[50000,100000]];
                    var betDiff = 0;
                    for (var i = 0; i < betNum.length; i++) {
                        var cur = betNum[i];
                        var min = cur[0];
                        var max = cur[1];
                        if(barBetAmount>= min && barBetAmount<max){
                            betDiff = max - barBetAmount;
                            var l = ((barBetAmount -min)/max + i)*25;
                            break;
                        }
                    }
                    if (betDiff > 0){
                        $('#betDiff').html(betDiff);
                    }else{
                        $('.progress-bar-tips p').html('您今日投注已达到最高奖励等级');
                    }

                    $('.progress-bar-inner').width(l + '%');
                    var left = $('.progress-bar-inner').width() - $('.progress-bar-tips').outerWidth()/2;
                    $('.progress-bar-tips').css({'left':left});
            }
            //倒计时
            if (IsParameterReady(mainEndTime)) {
                var time = mainEndTime;
                setInterval(function(){
                    var second = Math.floor( (time/1000) % 60 );
                    var minute = Math.floor( (time/1000/60) % 60 );
                    var hour = Math.floor( (time/(1000*60*60)) % 24 );
                    if (time <= 0) {hour = minute = second = 0;} 
                    $(".end-time").html(hour + ':' + minute + '<strong>'+ second +'</strong>');
                    time-=1000;
                }, 1000);
            }
            //中獎名單
            if (IsParameterReady(mainRewardList)) {
                var data = mainRewardList;
                var name = data[0];
                var win = data[1];
                var nameTemp = '';
                $("#notice ul").find('li').remove();
                for (var i = 0; i < data.length; i++) {
                    nameTemp = '';
                    nameTemp = data[i].name.substr(0, 1);
                    nameTemp += '***';
                    nameTemp += data[i].name.substr(-1, 1);
                    data[i].name = nameTemp;
                    data[i].win = parseInt(data[i].win, 10);
                    if (!isNaN(data[i].win)) {
                        data[i].win /= 10000;
                        data[i].win = Math.ceil(data[i].win);
                    } else {
                        data[i].win = 0;
                    }
                    var html = '<li>恭喜 ' + data[i].name + ' 获得 ' + data[i].win + '</li>';
                    $("#notice ul").append(html);
                }
                //中奖公告滚动
                var $this = $("#notice");
                var scrollTimer;
                $this.hover(function(){
                    clearInterval(scrollTimer);
                }, function() {
                    scrollTimer = setInterval(function(){
                        scrollNews($this);
                    }, 2000);
                }).trigger("mouseleave");

                function scrollNews(obj){
                    var $self = obj.find("ul");
                    var lineHeight = $self.find("li:first").height();
                    $self.animate({
                        "marginTop": -lineHeight + "px"
                    }, 600, function(){
                        $self.css({
                            marginTop: 0
                        });
                        $self.find("li:nth-child(1)").appendTo($self);
                        $self.find("li:nth-child(1)").appendTo($self);
                    })
                }
            }
            //處理回傳狀態
            if (IsParameterReady(mainContent) &&
                IsParameterReady(mainIsSuccess)
                ) {
                //已領過
                if (mainIsSuccess === 2 &&
                    IsParameterReady(mainTotalReward)) {
                    mainTotalReward = parseInt(mainTotalReward, 10);
                    if (!isNaN(mainTotalReward)) {
                        mainTotalReward /= 10000;
                        mainTotalReward = Math.ceil(mainTotalReward);
                    } else {
                        mainTotalReward = 0;
                    }

                    var hisBonus = mainTotalReward;
                    $('.content1').hide();
                    $('.content2').show();
                    $('#hisBonus').html(hisBonus);
                    $('.progress-bar-inner').width(0+'%');
                    $('.progress-bar-tips').remove();
                //可領取
                } else if (mainIsSuccess === 1 &&
                    IsParameterReady(mainBetAmount) &&
                    IsParameterReady(mainBetRatio)
                    ) {
                    var betAmount = mainBetAmount;
                    var betRatio = mainBetRatio;

                    $('#betAmount').html(betAmount);
                    $('#betRatio').html(betRatio);
                    //未達標
                } else if (mainIsSuccess === 0 ) {
                    $('.content1').hide();
                    $('.content4').show();
                } else {
                    alert(errMsg);
                    window.location.href = homeUrl;
                }
            } else {
                alert(errMsg);
                window.location.href = homeUrl;
            }
        },
        error: function(xhr){
            if(xhr.status == '404' || xhr.status == '0' || xhr.status == '401' ){
                alert('请重新登录');
            }else{
                alert(errMsg);
            }
            window.location.href = homeUrl;
        }
    });
    var fontSize = $('#html').css('fontSize');
    fontSize = fontSize.slice(0,fontSize.length-2);
    var offsetW = Number(fontSize) * .7;
    var offsetH = Number(fontSize) * .5;
    //拉霸
    $('#flipball').flipball({
        ballsize: 1, // 彩球个数
        initball: '0', // 初始化彩球数据
        loop: 3, // 彩球滚动循环次数（必须为整数）
        timeout: 3000, // 彩球滚动动画执行时间基数
        delay: 150, // 每个彩球动画执行延迟时间基数
        offset: [offsetW, offsetH], // 球的宽高
        handbar: '.handle-hand'
    });

    var isFinished = true;
    $('.handle-hand').bind('click', function(){
        getRewardList();
        if(isFinished){
            isFinished = false;
            $.ajax({
                url:slotUrl,
                dataType:'json',
                type: 'post',
                contentType: "application/json; charset=utf-8",
                processData: false,
                data: '{"head":{"sowner":0,"rowner":0,"msn":0,"msnsn":0,"userId":0,"userAccount":"","sessionId":"'+token+'"},"body":{"param":{},"pager":{"startNo":0,"endNo":0}}}',
                success:function(res){
                    slotContent = res;
                    slotIsSuccess = slotContent['body']['result']['isSuccess'];
                    slotTotalReward = slotContent['body']['result']['bonus'];
                    slotMultiple = slotContent['body']['result']['multiple'];

                    if (IsParameterReady(slotContent) &&
                        IsParameterReady(slotIsSuccess)
                     ) {
                        if (Number(slotIsSuccess) === 1 &&
                            IsParameterReady(slotTotalReward) &&
                            IsParameterReady(slotMultiple)
                            ) {
                            //multiple值为1倍,1.5倍,2倍,10倍
                            slotTotalReward = parseInt(slotTotalReward, 10);
                            if (!isNaN(slotTotalReward)) {
                                slotTotalReward /= 10000;
                                slotTotalReward = Math.ceil(slotTotalReward);
                            } else {
                                slotTotalReward = 0;
                            }

                            var bonus = slotTotalReward;
                            var multiple = slotMultiple;
                            var multipleNum = [1,1.5,2,10];
                            for (var i = 0; i < multipleNum.length; i++){
                                var cur = multipleNum[i];
                                if(cur == multiple)
                                break;
                            }
                            $('#flipball').flipball().flip([i], true, function(){
                                setTimeout(function(){
                                    $('.mask').show();
                                    $('.pop').fadeIn();
                                    $('#betBonus').html(bonus+'元');
                                    $('#betMultiple').html(multiple);
                                    
                                },300)
                            })
                            $('.pop .btn').bind('click', function(){
                                $('.content1').hide();
                                $('.content2').show();
                                $('.progress-bar-inner').width(0+'%');
                                $('.progress-bar-tips').remove();
                                $('.pop').fadeOut();
                                $('.mask').hide();
                                $('#hisBonus').html(bonus);
                            });
                        } else {
                            alert(errMsg);
                            window.location.href = homeUrl;
                        }
                    } else {
                        alert(errMsg);
                        window.location.href = homeUrl;
                    }
                },
                error: function(xhr) {
                     if(xhr.status == '404' || xhr.status == '0' || xhr.status == '401' ){
                        alert('请重新登录');
                    }else{
                        alert(errMsg);
                    }
                    window.location.href = homeUrl;
                }
            });
        }
    });
});