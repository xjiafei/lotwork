$(function(){
    //localStorage.clear();
    var rich_data;

    //讀取數值
    $.ajax({
        url:'/vipmb/oct4info',
	    dataType:'json',
	    method:'post',
	    data:data,
        success:function(data) {
            rich_data = data;
            //console.log(rich_data);
            init_data(rich_data);
        },error: function(msg) {
            alert(msg);
        }
    });

    //彈窗按鈕
    $(".btn_later").click(function(){
        $(".model").hide();
    });
    $(".btn_add").click(function(){
        $(".model").hide();
		$.ajax({
			url:'/vipmb/oct4addr',
			dataType:'json',
			method:'post',
			data:data,
			success:function(data) {
			},error: function(msg) {
			}
		});
    });

	$(".singupBtn").click(function(){
		$.ajax({
			url:'/vipmb/oct4signup',
			dataType:'json',
			method:'post',
			data:data,
			success:function(data) {
				window.location.reload(true);
			},error: function(msg) {
				alert(msg);
			}
		});
    });
    //導航按鈕
    $(".nav-btn").click(function(){
        var this_id=$(this).attr("id").split("_")[1];
        //console.log(this_id);
        ScrollGoTo(this_id)
    });

    //監聽scoll
    /*$(window).scroll(function(){
        var TopPx=$(window).scrollTop();
        //console.log(TopPx);
        if(TopPx>=0 && 1300>TopPx)
        {
            $(".nav-btn").removeClass("action");
            $(".GOcom3").html("二");

            $(".GOcom2").addClass("action");
            $(".GOcom2").html("第一重<br>好礼");
        }else if(TopPx>=1300 && 1900>TopPx)
        {
            $(".nav-btn").removeClass("action");
            $(".GOcom2").html("一");

            $(".GOcom3").addClass("action");
            $(".GOcom3").html("第二重<br>好礼");
        }else if(TopPx>=1900)
        {
            $(".nav-btn").removeClass("action");
            $(".GOcom2").html("一");
            $(".GOcom3").html("二");

            $(".GOcom4").addClass("action");
        }
    });*/



    function init_data(data)
    {
        //報名
        var is_signup=data['Signup'];
        if(is_signup == "1")
        {
			$( ".signup" ).removeClass( "signup" ).addClass( "signdone" );
			$(".signdone").html("");
        }

        //好禮一
        $("#TodayDeposit").text(data['TodayDeposit']);
        $("#TodayBets").text(data['TodayBets']);
        $("#TodayPrize").text(data['TodayPrize']);


        //好禮二
        var DepositCount=data['DepositCount'];
        if(DepositCount>0 && 30000>DepositCount)
        {
            $(".sign1").show();
            $(".sign1num").text(formatNumber(DepositCount));
        }else if(DepositCount>=30000 && 300000>DepositCount)
        {
            $(".gift1").show();
            FnModel(data['Addr']);
            $(".sign2").show();
            $(".sign2num").text(formatNumber(DepositCount));
        }else if(DepositCount>=300000 && 3000000>DepositCount)
        {
            $(".gift1").show();
            $(".gift2").show();
            FnModel(data['Addr']);
            $(".sign3").show();
            $(".sign3num").text(formatNumber(DepositCount));
        }else if(DepositCount>=3000000)
        {
            $(".gift1").show();
            $(".gift2").show();
            $(".gift3").show();
			$(".sign4").show();
            FnModel(data['Addr']);
        }
    }

    //彈窗是否關閉
    function FnModel(addr)
    {
		if(addr != "0")
        {
			$(".btn_ChangeAddress").show();
        }else
        {
            $(".model").show();
        }
    }
    //數字格式化
    function formatNumber(num, precision, separator) {
        var parts;
        // 判断是否为数字
        if (!isNaN(parseFloat(num)) && isFinite(num)) {
            num = Number(num);
            // 处理小数点位数
            num = (typeof precision !== 'undefined' ? num.toFixed(precision) : num).toString();
            // 分离数字的小数部分和整数部分
            parts = num.split('.');
            // 整数部分加[separator]分隔, 借用一个著名的正则表达式
            parts[0] = parts[0].toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1' + (separator || ','));

            return parts.join('.');
        }
        return NaN;
    }

    /*//導覽列
    function ScrollGoTo(id)
    {
        $("html,body").animate({scrollTop: ($("#"+id).offset().top)}, 500);
    }*/

});