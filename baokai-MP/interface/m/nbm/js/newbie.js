//main content raw
var mainContentRaw = '';
//main content
var mainContent = '';
//member condition (0:invalid, 1:valid)
var mainSuccess = '';
//game days
var mainGameDays = '';

/*newbie mission*/

//card condition (0:none, 1:binded)
var newbieCardBind = '';
//initial charge condition (0:none, 1:done)
var newbieInitCharge = '';
//initial withdraw condition (0:none, 1:done)
var newbieInitWithdraw = '';
//bank card tips
var newbieBankTips = '';
//initial charge tips
var newbieInitChargeTips = '';
//initial withdraw tips
var newbieInitWithdrawTips = '';
//bank card link
var newbieCardLink = 'phlotto://go_cardbinding';
//initial charge link
var newbieInitChargeLink = 'phlotto://go_recharge';
//initial withdraw link
var newbieInitWithdrawLink = 'phlotto://go_withdraw';
//lotto lobby
var homeUrl = 'phlotto://go_lottolobby';

/*common mission*/

//question condition (0:none, 1:done)
var commonQuestionFinish = '';
//question reward
var commonQuestionReward = '';
//question answers days
var commonQuestionAnswerDays = '';
//get copper egg days
var commonCopperEggDays = '';
//get silver egg days
var commonSilverEggDays = '';
//get golden egg days
var commonGoldEggDays = '';
//get copper egg count
var commonCopperEggCount = '';
//get silver egg count
var commonSilverEggCount = '';
//get gold egg count
var commonGoldEggCount = '';
//get copper egg css
var commonCopperEggCss = '';
//get silver egg css
var commonSilverEggCss = '';
//get gold egg css
var commonGoldEggCss = '';
//get questionList
var commonQuestionList = '';
//get bet days
var commonBetDays = '';
//show bet days line 1
var commonBetDaysLine1  = '';
//show bet days line 2
var commonBetDaysLine2  = '';
//show bet days line 3
var commonBetDaysLine3  = '';
//show bet days line 1 reward
var commonBetDaysReward1 = '';
//show bet days line 2 reward
var commonBetDaysReward2 = '';
//show bet days line 3 reward
var commonBetDaysReward3  = '';

var commonBetDaysIsAmount1 ='';
var commonBetDaysIsAmount2 ='';
var commonBetDaysIsAmount3 ='';

var commonBetDaysLottery1 =''; 	
var commonBetDaysLottery2 ='';	
var commonBetDaysLottery3 	='';

var commonBetDaysIsLottery1 ='';
var commonBetDaysIsLottery2 ='';
var commonBetDaysIsLottery3 ='';

var commonBetDayslotteryType1 ='';
var commonBetDayslotteryType2 ='';
var commonBetDayslotteryType3 ='';

//get schedule list
var commonDateList = '';
//get bet achievement
var commonBetAchievement = '';
//get bet achievement reward
//commonBetAchievement[0-4]['reward']
//get bet achievement standard
//commonBetAchievement[0-4]['standard']
//get bet achievement egg number
//commonBetAchievement[0-4]['times']
//get bet achievement achieve flag
//commonBetAchievement[0-4]['achieve']
//get bet achievement egg type
//commonBetAchievement[0-4]['type']

/*egg drop*/

//golden egg count
var eggGoldCount = '';
//silver egg count
var eggSilverCount = '';
//copper egg count
var eggCopperCount = '';

//main url
var mainUrl = '/iapi/begin/mission/initMission';
var mainFlag = true;
var mainDelayFlag = '';
var mainDelayTime = 2000;
//common question url
var commonQuestionUrl = '/iapi/begin/mission/dailyAnswerAward';
//egg drop url
var eggDropUrl = '/iapi/begin/mission/eggLotteryAward';
var eggFlag = true;
var eggDelayFlag = '';
var eggDelayTime = 2000;
/*
mainUrl = 'fake_init.php';
commonQuestionUrl = 'fake_daily.php';
eggDropUrl = 'fake_egg.php';
*/
//token
var token = '';

$(function(){
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
    }

    if (!IsParameterReady(token)) {
        window.location.href = homeUrl;
    }

    //砸蛋轮播
    var list = [
        {content: '<img src="images/bg-egg1.png">'},
        {content: '<img src="images/bg-egg2.png">'},
        {content: '<img src="images/bg-egg3.png">'}
    ]
    var islider = new iSlider({  
        data: list,
        dom: document.getElementById("iSlider-wrapper"),
        isLooping: true,
        animateType: 'default',
        animateTime: 800,
        plugins: ['dot', 'button']

    });
    //tab
     var title = $('.newbie-tab-title>li'),
    content = $('.newbie-tab-content>li');   
    title.on('click',function(){
        mainLoad();
        var index = $(this).index();
        title.removeClass('current').eq(index).addClass('current');
        content.removeClass('current').eq(index).addClass('current');
        $('.activity-box').fadeIn();
        $('.questions-box').hide();
    });
    function tabChange(num) {
        mainLoad();
        title.removeClass('current').eq(num).addClass('current');
        content.removeClass('current').eq(num).addClass('current');
        $('.activity-box').fadeIn();
        $('.questions-box').hide();
    }
    tabChange(0);

    function mainLoad() {
        //initial card event
        $('.newbie-list a').eq(0).off('click');
        $('.newbie-list a').eq(0).attr("href", "javascript:void(0);");
        //initial charge event
        $('.newbie-list a').eq(1).off('click');
        $('.newbie-list a').eq(1).attr("href", "javascript:void(0);");
        //initial withdraw event
        $('.newbie-list a').eq(2).off('click');
        $('.newbie-list a').eq(2).attr("href", "javascript:void(0);");
        //initial question event
        $('.activity-answer').off('click');
        //initial egg event
        $('.islider-dot .btn').off('click');
        //initial egg event
        $(window).off('shake');

        if (mainFlag) {
            clearTimeout(mainDelayFlag);
            mainDelayFlag = setTimeout(function(){mainFlag = true}, mainDelayTime);
            mainFlag = false;
            $.ajax({
                type: 'POST',
                url: mainUrl,
                contentType: "application/json; charset=utf-8",
                processData: false,
                data: '{"head":{"sowner":0,"rowner":0,"msn":0,"msnsn":0,"userId":0,"userAccount":"","sessionId":"'+token+'"},"body":{"param":{},"pager":{"startNo":0,"endNo":0}}}',
                dataType: 'json'
            }).done(function(result) {
                mainFlag = true;

                mainContent = result;
                mainSuccess = mainContent['body']['result']['isSuccess'];
                if (mainSuccess !== 1) {
                    $('.mask').show();
                    $('#popCommon').find('p').html('因为您不符合参与活动的条件，您的活动资格已被取消；如果有疑问请咨询客服，谢谢~');
                    $('#popCommon').fadeIn();
                    $('#popCommon .btn').off('click').on('click',function(){
                        window.location.href = homeUrl;
                    });
                    return;
                }
                mainGameDays = mainContent['body']['result']['data']['countdown']['gameDays'];
                /*newbie mission*/
                newbieCardBind = mainContent['body']['result']['data']['mission']['isFinish1'];
                newbieInitCharge = mainContent['body']['result']['data']['mission']['isFinish2'];
                newbieInitWithdraw =  mainContent['body']['result']['data']['mission']['isFinish3'];
                newbieBankTips = mainContent['body']['result']['data']['mission']['tipsBank'];
                newbieInitChargeTips = mainContent['body']['result']['data']['mission']['tipsCard'];
                newbieInitWithdrawTips = mainContent['body']['result']['data']['mission']['tipsWithdraw'];
                /*common mission*/
                commonQuestionFinish = mainContent['body']['result']['data']['question']['isFinished'];
                commonQuestionReward = mainContent['body']['result']['data']['question']['getMoney'];
                commonQuestionAnswerDays = mainContent['body']['result']['data']['question']['answersDays'];
                commonCopperEggDays = mainContent['body']['result']['data']['question']['answersDay1'];
                commonSilverEggDays = mainContent['body']['result']['data']['question']['answersDay2'];
                commonGoldEggDays = mainContent['body']['result']['data']['question']['answersDay3'];
                commonCopperEggCount = mainContent['body']['result']['data']['question']['answersMoney1'];
                commonSilverEggCount = mainContent['body']['result']['data']['question']['answersMoney2'];
                commonGoldEggCount = mainContent['body']['result']['data']['question']['answersMoney3'];
                commonCopperEggCss = mainContent['body']['result']['data']['question']['prizeType1'];
                commonSilverEggCss = mainContent['body']['result']['data']['question']['prizeType2'];
                commonGoldEggCss = mainContent['body']['result']['data']['question']['prizeType3'];
                commonQuestionList = mainContent['body']['result']['data']['question']['answerList'];;
                commonBetDays = mainContent['body']['result']['data']['daily']['countDays'];
                commonBetDaysLine1  = mainContent['body']['result']['data']['daily']['countDay1'];
                commonBetDaysLine2  = mainContent['body']['result']['data']['daily']['countDay2'];
                commonBetDaysLine3  = mainContent['body']['result']['data']['daily']['countDay3'];
                commonBetDaysReward1 = mainContent['body']['result']['data']['daily']['countMoney1'];
                commonBetDaysReward2 = mainContent['body']['result']['data']['daily']['countMoney2'];
                commonBetDaysReward3  = mainContent['body']['result']['data']['daily']['countMoney3'];
				
				commonBetDaysIsAmount1 = mainContent['body']['result']['data']['daily']['isAmount1'];
                commonBetDaysIsAmount2 = mainContent['body']['result']['data']['daily']['isAmount2'];
                commonBetDaysIsAmount3 = mainContent['body']['result']['data']['daily']['isAmount3'];
				
				commonBetDaysLottery1 = mainContent['body']['result']['data']['daily']['lottery1'];
                commonBetDaysLottery2 = mainContent['body']['result']['data']['daily']['lottery2'];
                commonBetDaysLottery3 = mainContent['body']['result']['data']['daily']['lottery3'];
				
				commonBetDaysIsLottery1 = mainContent['body']['result']['data']['daily']['isLottery1'];
                commonBetDaysIsLottery2 = mainContent['body']['result']['data']['daily']['isLottery2'];
                commonBetDaysIsLottery3 = mainContent['body']['result']['data']['daily']['isLottery3'];
				
				commonBetDayslotteryType1 = mainContent['body']['result']['data']['daily']['lotteryType1'];
                commonBetDayslotteryType2 = mainContent['body']['result']['data']['daily']['lotteryType2'];
                commonBetDayslotteryType3 = mainContent['body']['result']['data']['daily']['lotteryType3'];
				
                commonDateList = mainContent['body']['result']['data']['daily']['dateList'];
                commonBetAchievement = mainContent['body']['result']['data']['daily']['achievedList'];
                /*egg drop*/
                eggGoldCount = mainContent['body']['result']['data']['egg']['golden'];
                eggSilverCount = mainContent['body']['result']['data']['egg']['silver'];
                eggCopperCount = mainContent['body']['result']['data']['egg']['copper'];

                if(IsParameterReady(mainContent) &&
                    IsParameterReady(mainSuccess) &&
                    IsParameterReady(mainGameDays) &&
                    IsParameterReady(newbieCardBind) &&
                    IsParameterReady(newbieInitCharge) &&
                    IsParameterReady(newbieInitWithdraw) &&
                    IsParameterReady(newbieBankTips) &&
                    IsParameterReady(newbieInitChargeTips) &&
                    IsParameterReady(newbieInitWithdrawTips) &&
                    IsParameterReady(commonQuestionFinish) &&
                    IsParameterReady(commonQuestionReward) &&
                    IsParameterReady(commonQuestionAnswerDays) &&
                    IsParameterReady(commonCopperEggDays) &&
                    IsParameterReady(commonSilverEggDays) &&
                    IsParameterReady(commonGoldEggDays) &&
                    IsParameterReady(commonCopperEggCount) &&
                    IsParameterReady(commonSilverEggCount) &&
                    IsParameterReady(commonGoldEggCount) &&
                    IsParameterReady(commonCopperEggCss) &&
                    IsParameterReady(commonSilverEggCss) &&
                    IsParameterReady(commonGoldEggCss) &&
                    IsParameterReady(commonQuestionList) &&
                    IsParameterReady(commonBetDays) &&
                    IsParameterReady(commonBetDaysLine1) &&
                    IsParameterReady(commonBetDaysLine2) &&
                    IsParameterReady(commonBetDaysLine3) &&
                    IsParameterReady(commonBetDaysReward1) &&
                    IsParameterReady(commonBetDaysReward2) &&
                    IsParameterReady(commonBetDaysReward3) &&
                    IsParameterReady(commonBetAchievement) &&
                    IsParameterReady(commonDateList) &&
                    IsParameterReady(eggGoldCount) &&
                    IsParameterReady(eggSilverCount) &&
                    IsParameterReady(eggCopperCount)
                ) {
                    mainSuccess = 1;
                } else {
                    window.location.href = homeUrl;
                }
                if(mainSuccess === 1) {
                    function newbieConditionClass(con) {
                        return con === 1? 'finished': '';
                    }
                    //card
                    $('.newbie-list li').eq(0).addClass(newbieCardBind === 3? 'finished': '');
                    $('.newbie-list p').eq(0).html(newbieBankTips);
                    $('.newbie-list a').eq(0).attr("href", newbieCardLink);
                    //charge
                    $('.newbie-list li').eq(1).addClass(newbieConditionClass(newbieInitCharge));
                    $('.newbie-list p').eq(1).html(newbieInitChargeTips);
                    $('.newbie-list a').eq(1).attr("href", newbieInitChargeLink);
                    //withdraw
                    $('.newbie-list li').eq(2).addClass(newbieConditionClass(newbieInitWithdraw));
                    $('.newbie-list p').eq(2).html(newbieInitWithdrawTips);
                    $('.newbie-list a').eq(2).attr("href", newbieInitWithdrawLink);
                    //處理綁卡事件 (0:未绑卡 1: 绑卡但未锁定 2:审核中 3: 已完成)
                    if (newbieCardBind === 1 || newbieCardBind === 2) {
                        $('.newbie-list a').eq(0).html("审核中...");
                        $('.newbie-list a').eq(0).attr("href", "javascript:void(0);");
                        $('.newbie-list a').eq(0).off('click').on('click', function() {
                            $('.mask').show();
                            $('#popCommon').find('p').html('我们正在审核您的绑卡申请，请您耐心等待');
                            $('#popCommon').fadeIn();
                        });
                    }
                    if (newbieCardBind !== 3) {
                        $('.newbie-list a').eq(1).attr("href", "javascript:void(0);");
                        $('.newbie-list a').eq(2).attr("href", "javascript:void(0);");
                        $('.newbie-list a').eq(1).off('click');
                        $('.newbie-list a').eq(1).on('click', function() {
                            $('.mask').show();
                            $('#popBank').fadeIn();
                        });
                        $('.newbie-list a').eq(2).off('click');
                        $('.newbie-list a').eq(2).on('click', function() {
                            $('.mask').show();
                            $('#popBank').fadeIn();
                        });
                    }
                    if (newbieCardBind === 3) {
                        $('.newbie-list a').eq(1).attr("href", "javascript:void(0);");
                        $('#popRecharge .btn').attr("href", newbieInitChargeLink);
                        //点击立即充值按钮
                        $('.newbie-list a').eq(1).off('click');
                        $('.newbie-list a').eq(1).on('click',function(){
                            $('.mask').show();
                            $('#popRecharge').show();
                        });
                        //点击立即充值弹窗按钮
                         $('#popRecharge .btn').off('click');
                        $('#popRecharge .btn').on('click',function(){
                            $('.mask').hide();
                            $('#popRecharge').fadeOut();
                        });
                    }
                    //日历初始化
                    var classMap = {
                        '0': 'miss',
                        '1': 'complete',
                        '2': ''
                    };
                    $.each(commonDateList, function(index,value){
                        $('.date-list li').eq(index).addClass(classMap[value]);
                    });
                    //累计投注初始化
					 var typeMap = {
                        '0': '铜',
                        '1': '银',
                        '2': '金'
                    };
                    var countDay = commonBetDays;
                    var countDays = new Array();
                    var countIsMoney = new Array();					
                    var countMoneys = new Array();
                    var countLotterys = new Array();
                    var countLotteryTypes = new Array();
                    var countIsLotterys = new Array();
					
					
                    countDays[0] = commonBetDaysLine1;
                    countDays[1] = commonBetDaysLine2;
                    countDays[2] = commonBetDaysLine3;
					//是否送錢
					countIsMoney[0] = commonBetDaysIsAmount1;
					countIsMoney[1] = commonBetDaysIsAmount2;
					countIsMoney[2] = commonBetDaysIsAmount3;		
					//送多少錢
                    countMoneys[0] = commonBetDaysReward1;
                    countMoneys[1] = commonBetDaysReward2;
                    countMoneys[2] = commonBetDaysReward3;
					
					//是否砸蛋
					countIsLotterys[0]=commonBetDaysIsLottery1;
					countIsLotterys[1]=commonBetDaysIsLottery2;
					countIsLotterys[2]=commonBetDaysIsLottery3;					
					//多少蛋
					countLotterys[0]=commonBetDaysLottery1;
					countLotterys[1]=commonBetDaysLottery2;
					countLotterys[2]=commonBetDaysLottery3;
					//蛋類型
					countLotteryTypes[0]=commonBetDayslotteryType1;
					countLotteryTypes[1]=commonBetDayslotteryType2;
					countLotteryTypes[2]=commonBetDayslotteryType3;
					
                    for (var i = 0; i < countDays.length; i++) {
                        $('.reward-list .day').eq(i).html(countDays[i]);
						var awardText = '';
						//判斷是否派錢
						if(countIsMoney[i]=='Y'){
							awardText+=countMoneys[i]+'元';
						}

						if(countIsLotterys[i]=='Y'){
							
							awardText+=countLotterys[i]+'次'+typeMap[countLotteryTypes[i]]+'蛋';
						}						
					
						$('.reward-list .money').eq(i).html(awardText);

                        if(countDay >= Number(countDays[i])){
                            $('.reward-list li').eq(i).addClass('completed')
                        }
                    }
                    //日常任务初始化
                    var achievedList = commonBetAchievement;
                   
                    $.each(achievedList,function(index,value){
                        var html = '';
                        var className = value['achieve'] ? 'completed':'';
                        $('.activity-list li').not('.activity-answer').eq(index).addClass(className);
                        if(value['times'] !== 0){
                            html+= '获得'+ value['times'] +'次砸'+ typeMap[value.type] +'蛋的机会<br>';
                        }
                        if(value['reward'] !== 0){
                            html+= '获得'+ value['reward'] +'元红包';
                        }
                        $('.activity-list .info').eq(index).html(html);
                        $('.activity-list .num strong').eq(index).html(value['standard']);
                    });
    
                    //累计答题&答题奖励初始化
                    var answersDay = commonQuestionAnswerDays;
                    var remainingDay = '';
                    var remainingtype = '';
                    var answersDays = new Array();
                    var eggNums = new Array();
                    var prizeTypes = new Array();
                    answersDays[0] = commonCopperEggDays;
                    answersDays[1] = commonSilverEggDays;
                    answersDays[2] = commonGoldEggDays;
                    eggNums[0] = commonCopperEggCount;
                    eggNums[1] = commonSilverEggCount;
                    eggNums[2] = commonGoldEggCount;
                    prizeTypes[0] = commonCopperEggCss;
                    prizeTypes[1] = commonSilverEggCss;
                    prizeTypes[2] = commonGoldEggCss;
                    
                    for(var i = 0; i < answersDays.length; i++){
                        $('.questions-prize .day').eq(i).html('连续'+answersDays[i]+'天');
                        $('.questions-prize .num').eq(i).html('X'+eggNums[i]);
                        $('.questions-prize .prize').eq(i).addClass(prizeTypes[i]);
                        //判斷剩餘天數和蛋種類
                        if (commonQuestionAnswerDays < answersDays[i]) {
                            if (remainingDay == '') {
                                remainingDay = answersDays[i] - commonQuestionAnswerDays;
                                remainingtype = i;
                            }
                        }
                    }
                    if(answersDay >= Math.max.apply(null, answersDays)){
                        $('.questions-requirement').html('您已经连续答题'+answersDay+'天！');
                    }else{
                        $('.questions-requirement').html('您已经连续答题'+answersDay+'天！'+'还有'+remainingDay+'天即可获得1枚'+typeMap[remainingtype]+'蛋');
                    }

                    //公共弹框
                    $('#popCommon .btn').off('click').on('click',function(){
                        $('.mask').hide();
                        $('#popCommon').fadeOut();

                    });

                    //点击绑定银行卡弹窗按钮
                    $('#popBank .btn').off('click');
                    $('#popBank .btn').on('click',function(){
                        $('.mask').hide();
                        $('#popBank').fadeOut();
                        title.removeClass('current').eq(0).addClass('current');
                        content.removeClass('current').eq(0).addClass('current');
                    });
                    //砸蛋失敗URL跳轉內容
                    $('#popEgg .btn').off('click');
                    $('#popEgg .btn').on('click',function(){
                        $('.mask').hide();
                        $('#popEgg').fadeOut();
                        tabChange(1);
                    });
                    //点击去砸蛋按钮
                    $('.questions-link .btn-egg').off('click');
                    $('.questions-link .btn-egg').on('click',function(){
                        tabChange(2);
                    });
                    //点击返回按钮
                    $('.questions-link .btn-back').off('click');
                    $('.questions-link .btn-back').on('click',function(){
                        tabChange(1);
                    });
                    var questionsFinishedEvent = function(hisMoney){
                        var questionsNum = $('.questions-top .num');
                        var questionsFinish = $('.questions-finish');
                        var questionsLink = $('.questions-link');
                        var icoRight = $('<i class="ico-right"></i>');
                        questionsFinish.show();
                        questionsLink.show();
                        questionsNum.find('li').removeClass('current').append(icoRight);
                        questionsFinish.find('.reward').text(hisMoney);
                    }

                    var questionsEvent = function(){
                        //问答题目初始化
                        $('.questions-dl').hide();
                        $('.questions-dl').find('dd').removeClass('current');
                        var numMap = ['一','二','三','四','五','六','七','八','九','十'],
                        selectMap = ['A','B','C','D'];
                        $.each(commonQuestionList ,function(index,value){
                            var html = '';
                            $('.questions-dl dt').eq(index).html(value['title']);
                            $.each(value['answer'],function(num,val){
                                var className = Number(value['correct'] == num) ? 'current': '';
                                $('.questions-dl').eq(index).find('dd').eq(num).addClass(className).html(selectMap[num] + ' ' + value['answer'][num]);
                            });
                        });

                        var questions = $('.questions-dl');
                        var questionsNum = $('.questions-top .num');
                        var questionsFinish = $('.questions-finish');
                        var questionsLink = $('.questions-link');
                        var nowQuestion = 0;

                        questionsNum.find('li').removeClass('current').eq(nowQuestion).addClass('current');
                        questionsNum.find('li').find('i').remove();
                        questions.eq(nowQuestion).show();
                        questionsNum.removeClass().addClass('num clearfix');
                        questionsNum.addClass('num'+nowQuestion);
                        // 正确答案点击事件
                        $('.questions-dl .current').off('click');
                        $('.questions-dl .current').on('click',function(){
                            var icoRight = $('<i class="ico-right"></i>');
                            var oldNum = nowQuestion;
                            nowQuestion++;
                            questionsNum.find('li').eq(oldNum).append(icoRight);
                            if(nowQuestion >= questions.length && IsParameterReady(token)) {
                                $.ajax({
                                    type: 'POST',
                                    url: commonQuestionUrl,
                                    contentType: "application/json; charset=utf-8",
                                    processData: false,
                                    data: '{"head":{"sowner":0,"rowner":0,"msn":0,"msnsn":0,"userId":0,"userAccount":"","sessionId":"'+token+'"},"body":{"param":{},"pager":{"startNo":0,"endNo":0}}}',
                                    dataType: 'json'
                                }).done(function(result) {
                                    var commonReturnStatus = result['body']['result']['isSuccess'];
                                    var commonReturnMoney = result['body']['result']['data']['money'];

                                    if (IsParameterReady(commonReturnStatus) &&
                                        IsParameterReady(commonReturnMoney) &&
                                        commonReturnStatus === 1
                                        ) {
                                            commonReturnMoney /= 10000;
                                            mainLoad();
                                            questions.hide();
                                            questionsFinish.show();
                                            questionsLink.show();
                                            questionsNum.removeClass().addClass('num clearfix');
                                            questionsNum.find('li').removeClass('current');
                                            questionsFinish.find('.reward').text(commonReturnMoney);
                                    } else {
                                            $('.activity-box').fadeIn();
                                            $('.questions-box').hide();
                                            tabChange(1);
                                    }
                                }).fail(function(xhr) {
                                    if(xhr.status == '404' || xhr.status == '0' || xhr.status == '401' ){
                                        alert('请重新登录');
                                    }else{
                                        alert('未知网络错误');
                                    }
                                    window.location.href = homeUrl;
                                    //$('.activity-box').fadeIn();
                                    //$('.questions-box').hide();
                                    //tabChange(1);
                                });
                            }else{
                                questions.eq(nowQuestion).fadeIn().siblings().hide();
                                questionsNum.addClass('num'+nowQuestion);
                                questionsNum.find('li').removeClass('current').eq(nowQuestion).addClass('current');    
                            }
                        });
                        // 错误答案点击事件
                        $('.questions-dl dd').not('.current').off('click');
                        $('.questions-dl dd').not('.current').on('click',function(){
                            var $wrong = $('<div class="wrong-tips">错误</div>'),
                                $this = $(this);
                            $this.append($wrong);
                            setTimeout(function(){
                                $wrong.remove();
                            },1000);
                        });
                    }
                    //点击答题
                    //檢查是否已答題
                    if (commonQuestionFinish === 0) {
                        //處理問題
                        $('.activity-answer').removeClass('completed');
                        questionsEvent();
                        $('.activity-answer').off('click');
                        $('.activity-answer').on('click',function(){
                            //確認銀行卡已綁定
                            if(newbieCardBind === 3){
                                $('.activity-box').hide();
                                $('.questions-box').fadeIn();
                            }else{
                                $('.mask').show();
                                $('#popBank').show();
                            }
                        });
                    } else {
                        $('.activity-answer').removeClass('completed');
                        $('.activity-answer').addClass('completed');
                        $('.questions-dl').hide();
                        $('.questions-top').hide();
                    }
                    
                    //砸蛋数据
                    var eggs = new Array();
                    eggs[0] = eggCopperCount;
                    eggs[1] = eggSilverCount;
                    eggs[2] = eggGoldCount;

                    var isliderDot = $('.islider-dot');
                    var eggsName = ['铜蛋','银蛋','金蛋'];
                    for(var i = 0; i < isliderDot.length; i++) {
                        isliderDot.eq(i).html('<div class="type">'+eggsName[i]+'</div><div class="btn">开砸（可砸<span class="playtimes" type="'+i+'">'+eggs[i]+'</span>次）</div><p>直接摇手机砸蛋</p>');
                    }

                    var eggEvent = function(){
                        if (eggFlag) {
                            clearTimeout(eggDelayFlag);
                            eggDelayFlag = setTimeout(function(){eggFlag = true}, eggDelayTime);
                            eggFlag = false;
                            var self = $('.islider-dot-wrap .active');
                            var type = self.index();

                            var num =  $('.islider-dot .playtimes').eq(type).html();
                            var cls = 'bg-egg'+(Number(type)+1);
                            var data = {
                                'eggType':type
                            };
                            if(num > 0 &&
                                IsParameterReady(type) &&
                                IsParameterReady(token))
                            {
                                $.ajax({
                                    url: eggDropUrl,
                                    data: '{"head":{"sowner":0,"rowner":0,"msn":0,"msnsn":0,"userId":0,"userAccount":"","sessionId":"'+token+'"},"body":{"param":{"lotteryType":'+type+'},"pager":{"startNo":0,"endNo":0}}}',
                                    contentType: "application/json; charset=utf-8",
                                    processData: false,
                                    dataType: 'json',
                                    method: 'POST',
                                    cache: false
                                }).done(function(result) {
                                        eggFlag = true;
                                        var eggReturnStatus = result['body']['result']['isSuccess'];
                                        var eggReturnMoney = result['body']['result']['data']['money'];

                                        if (IsParameterReady(eggReturnStatus) &&
                                            IsParameterReady(eggReturnMoney) &&
                                            eggReturnStatus === 1
                                            ) {
                                            eggReturnMoney /= 10000;
                                            mainLoad();
                                            $('.eggs-box').hide();
                                            $('.eggs-box-current').show();
                                            $('.eggs-box-current .bg-egg').show().addClass(cls);
                                            //锤子显示
                                            setTimeout(function(){
                                                $('.hammer').show().addClass('hammer-current');
                                            },300);
                                            //静态蛋消失，动态蛋显示
                                            setTimeout(function(){
                                                $('.eggs-box-current .bg-egg').fadeOut();
                                                $('.eggs-box-current .bg-egg-wobble').fadeIn().addClass(cls+'-wobble');
                                            },500);
                                            //锤子消失
                                            setTimeout(function(){
                                                $('.hammer').fadeOut().removeClass('hammer-current');
                                            },800);
                                            //动态蛋消失，奖励结果显示，按钮显示
                                            setTimeout(function(){
                                                $('.eggs-box-current .bg-egg').fadeOut().removeClass(cls);
                                                $('.eggs-box-current .bg-egg-wobble').fadeOut().removeClass(cls+'-wobble');
                                                $('.eggs-box-current .bg-egg-current').fadeIn().addClass(cls+'-current');
                                                $('.eggs-box-current .prize strong').html(eggReturnMoney);
                                                $('.eggs-box-current .prize').fadeIn();
                                                $('.eggs-box-current .btn').fadeIn();
                                            },1400);
                                            
                                            //点击砸蛋完成按钮
                                            $('.eggs-box-current .btn').off('click');
                                            $('.eggs-box-current .btn').on('click',function(){
                                                tabChange(2);
                                                $('.eggs-box-current').hide();
                                                $('.eggs-box').fadeIn();
                                                $('.eggs-box-current .bg-egg-current').hide().removeClass(cls+'-current');
                                                $('.eggs-box-current .prize').hide();
                                                $('.eggs-box-current .btn').hide();
                                            })
                                        } else {
                                            tabChange(2);
                                            $('.mask').show();
                                            $('#popEgg').fadeIn();
                                        }
                                }).fail(function(xhr) {
                                        eggFlag = true;
                                        if(xhr.status == '404' || xhr.status == '0' || xhr.status == '401' ){
                                            alert('请重新登录');
                                        }else{
                                            alert('未知网络错误');
                                        }
                                        window.location.href = homeUrl;
                                        //$('.eggs-box').show();
                                        //tabChange(2);
                                });
                                num--;
                                $('.islider-dot .playtimes').eq(type).html(num);
                            }else{
                                eggFlag = true;
                                $('.mask').show();
                                $('#popEgg').fadeIn();
                            }
                        }
                    }
                    $('.islider-dot .btn').off('click');
                    $('.islider-dot .btn').on('click',eggEvent);
                    var myShakeEvent = new Shake({
                        threshold: 20, // optional shake strength threshold
                        timeout: 100 // optional, determines the frequency of event generation
                    });

                    myShakeEvent.start();

                    $(window).off('shake');
                    $(window).on('shake',function(){
                        eggEvent();
                    });
                } else {//end of if 
                    alert('未知网络错误');
                    window.location.href = homeUrl;
                }
                //console.log(result);
            }).fail(function(xhr, status, error) {
                mainFlag = true;
                window.location.href = homeUrl;
                //console.log(error);
            });
        }
    }//end of function
});

