
var settings = {
	
	
    //lastLoginDate: localStorage.getItem('time'),//获取上次获奖的时间,如果没有记为null,如果有,一定记为dateValue(毫秒)格式
    prizeNumber: parseInt($("#award").val()),//奖品编号,见prizeList
    qualified: $("#isQualifi" ).val(),//用户是否有资格(是否消费满1000元)
    shortOf: $("#betDifference" ).val(),//如果未满1000元,差多少钱
    startDate: new Date(parseInt($("#startDate").val())),//活动开始日期,为那天的19时,注意为utc时间需要-8小时(3-12 19:00)
    endDate: new Date(parseInt($("#endDate").val())),//活动结束时期,为那天的20时
	sysTime: new Date(parseInt($("#systemTime").val())) //後台系統時間
}
var prizeList = {
    "0": "太阳能车载淨化器",
    "1": false,
    "2": "欧乐B电动牙刷",
    "3": "30元手机话费",
    "4": "京东100元购物券",
    "5": "人头马香槟干邑",
    "6": false,
    "7": "50元手机话费",
    "8": "周生生5克金牌",
    "9": false,
    "10": "飞利浦 自然唤醒灯",
    "11": "20元手机话费"
}

$( document ).ready(function(){

    // 抽奖与倒计时逻辑
    function countDown() {
        //如果不符合条件,直接跳出
        if (!settings.qualified) return
		var sysTime =  new Date(parseInt($("#systemTime").val())) 
        var now = settings.sysTime
        var startDate = settings.startDate
        var endDate = settings.endDate
		var countMin = $("#countMin" ).val();
		var countHour = $("#countHour" ).val();
		var isActivityStart = $("#activityStart" ).val();
        //今天19时的date
        var time19 = new Date(now.setHours(countHour-1, 0, 0, 0))
        //今天20时的date
        var time20 = new Date(now.setHours(countHour, countMin, 0, 0))
        //是否在活动期间
        var activityAvailable = (sysTime > startDate) && (sysTime < endDate)
        //是否为周日
        var isSunday = 1
		//var isSunday = now.getDay() === 0
        //上次获奖的时间(string)
        /* var lastLoginDate = settings.lastLoginDate

        if (lastLoginDate) {
            //距离上次获奖的天数
            var fromLastLoginToNow = (now.getTime() - (new Date(parseInt(lastLoginDate))).getTime()) / 86400000
        } */
        //补足倒计时为个位数时,十位数的0
        function formatToZero(n) {
            var n = parseInt(n, 10);
            if (n > 0) {
                if (n <= 9) {
                    n = "0" + n;
                }
                return String(n);
            } else {
                return "00";
            }
        }
        //显示奖品
        function showAward(prize){	
			var prizeGet = prizeList[prize];			
            if (prizeGet) {
				$("#card-" + prize).addClass('gotya')
                $('#win-info').html('恭喜您在上一轮抽奖中，抽到奖品\"' + prizeGet + '\"')
                $('#win-info').slideDown()
            } else {
                $('#win-info').html('上一轮抽奖中您未中奖')
                $('#win-info').slideDown()
            }
        }
        //抽奖动画
        function sparkSpin(prizeNumber) {
			$(".prize-card").removeClass('gotya')
			var prize = false;
            var count = 0
            var cardNum = 0
            var steps = 36 + prizeNumber //先转3圈
            var gapTime = 70
            var startSpin = function () {
				
                if (cardNum > 11) {
                    cardNum = 0
                }
                if (count >= steps - 10) {
                    gapTime = gapTime + 30 //还剩8个的时候减速
                }
                if (count > steps) {
                    setTimeout(showAward(prizeNumber),500)
                    if(prizeList[prizeNumber]){
                        $("#card-" + prizeNumber).addClass('gotya')
                    }
                    //记录用户上次获奖的时间,建议记录在后端
                    //localStorage.setItem('time', now.getTime().toString())
                    return
                }
                $(".prize-card").removeClass('selected-card')
                $("#card-" + cardNum).addClass('selected-card')
                count += 1
                cardNum += 1
                setTimeout(function () {
                    startSpin()
                }, gapTime)
            }
			startSpin()            
        }
        //倒计时动画
        function startCount() {
            var nowDate = new Date()
            var finalTime = new Date((new Date(parseInt($("#systemTime").val()))).setHours(countHour, countMin, 0, 0))
            if (nowDate >= finalTime) {
				$.ajax({
					url:'/activitymb/activitystartaward',
					type:'POST',
					data : {token : $("#token").val()},
					dataType:'json',
					async: false,
					success:function(data){
						prize = data['award'];
						sparkSpin(prize)
					},
				});	
                return
            }
            var remainSecond = (finalTime.getTime() - nowDate.getTime()) / 1000
            var returnMinute = Math.floor((remainSecond / 60)) > 0 ? formatToZero(Math.floor((remainSecond / 60)) % 60) : "00";
            var returnSecond = formatToZero(remainSecond % 60)
            $('#min').html(returnMinute)
            $('#sec').html(returnSecond)
            setTimeout(startCount, 1000)

        }
        //判断活动是否结束
        if (now > endDate) {
            $('#countdown-status').html('活动已结束')
        }
        //判断是否在活动期间
        if (activityAvailable) {

			//如果是周日且在19~20之间,显示倒计时
			var nowTime = new Date(parseInt($("#systemTime").val()))
			if(isSunday&&(nowTime>=time19)&&(nowTime<=time20)){
				if(isActivityStart){
					showAward(settings.prizeNumber)
				}
                $('#countdown-time').removeClass('timestamp')
                $('#colon').css("display","inline-block")
                startCount()
            }
            //直接显示奖品
            else{
                showAward(settings.prizeNumber)
			}
        }
		//判斷整個問卷活動是否開始
		if(isActivityStart){
			showAward(settings.prizeNumber)
		}
    }
    countDown()

//弹窗居中
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
	//GA埋點
	(function(i, s, o, g, r, a, m) {
		i['GoogleAnalyticsObject'] = r;
		i[r] = i[r] || function() {
		  (i[r].q = i[r].q || []).push(arguments)
		}, i[r].l = 1 * new Date();
		a = s.createElement(o),
		  m = s.getElementsByTagName(o)[0];
		a.async = 1;
		a.src = g;
		m.parentNode.insertBefore(a, m)
		})(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');

		ga('create', 'UA-63536434-1', 'auto');
		ga('send', 'pageview');
	
	
	
    //开始填写的逻辑
    $('#start-filling').bind('click', function () {
        ga('send', 'event', '4月调研问卷', '填写问卷');
		$.ajax({
			url:'/activitymb/activitycheckbettotal',
			type:'POST',
			data : {token : $("#token").val()},
			dataType:'json',
			async: false,
		    success:function(data){
				if (data['isQualifi'] == 'true') {
					$("#popup-finish").removeClass('popup-workhard')
					$("#popup-finish").addClass('popup-welldone')
					$("#popup-info").html('<div>填写并提交后,6元彩金将于</div><div>次日18点前发放至您的账户</div>')
					window.open('http://cn.mikecrm.com/av3lLB7')
				} else {
					$("#popup-finish").addClass('popup-workhard')
					$("#popup-finish").removeClass('popup-welldone')
					$("#popup-info").html("<div>投注额满1000即可申请6元</div> <div>您还差<span style='color:#f8ff2c'>" + data['betDifference'] + "</span>元哦</div>")
				}
				showPopWindow();
			},
		});	
        
    })
	
	function showPopWindow(){	
		positionFixd('#popup');
		$("#popup").show()
		$('#mask').show()
    }
	
    $('#popup-close').bind('click', function () {
        $("#popup").hide()
        $('#mask').hide()
    })
    /* $('#popup-finish').bind('click', function () {
        location.href = '/index'
    }) */


// 轮播图
    $(function () {
        $('.jcarousel').jcarousel({
            wrap: 'both'

        });
        $('.jcarousel-control-prev')
            .on('jcarouselcontrol:active', function () {
                $(this).removeClass('inactive');
            })
            .on('jcarouselcontrol:inactive', function () {
                $(this).addClass('inactive');
            })
            .jcarouselControl({
                target: '-=1'
            });

        $('.jcarousel-control-next')
            .on('jcarouselcontrol:active', function () {
                $(this).removeClass('inactive');
            })
            .on('jcarouselcontrol:inactive', function () {
                $(this).addClass('inactive');
            })
            .jcarouselControl({
                target: '+=1'
            });

        $('.jcarousel-pagination')
            .on('jcarouselpagination:active', 'a', function () {
                $(this).addClass('active');
            })
            .on('jcarouselpagination:inactive', 'a', function () {
                $(this).removeClass('active');
            })
            .jcarouselPagination({

                'item': function (page, carouselItems) {
                    return '<a href="#' + page + '"></a>';
                }

            });
        $('.jcarousel').jcarouselAutoscroll({
            interval: 4000
        });
    });
})

