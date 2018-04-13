$(function(){

  function easeOut(t, b, c, d) {
      return -c *(t /= d)*(t-2) + b;
  }
  function numberAnimation(){
    var $price = $('.J-price'),
        i = 0,
        num,
        target = Number($price.text());

        function tick(){
          num = easeOut(i, 0, target, 100).toFixed(2);
          $price.text(num);
          i++;
          if(i <= 100){
            frames(tick);
          }
        }
        frames(tick);
  }

  function rewardAnimation() {
    var random = -Math.round(Math.random() * 10)* 99;
    var height = '390';
    if(window.lib && window.lib.flexible){
      height /= 72;
      height += 'rem';
	  random /= 72;
      random += 'rem';
    } else {
      height += 'px';
	  random += 'px';
    }
    $('.J-hongbao').fadeIn()
      .find('.mid')
      .find('.bless').css({
        // "background-position-x": '0',
        "background-position-y": random
      }).end()
      .animate({
        height: height
      }, 800);
    numberAnimation();
  }

  function eggAnimation(){
    var $egg = $('.J-egg');
    $egg.addClass('animated bounceInDown').show();
    setTimeout(function(){
      $egg.removeClass('animated bounceInDown').hide();
      rewardAnimation();
    }, 2000);
  }

  function showPop(data){
    var classname;
    switch (data.success) {
      case 2: //前往首页
        classname = 'btn-index'
        break;
      case 3: //前往充值
        classname = 'btn-chongzhi'
        break;
      case 4: //前往投注
        classname = 'btn-touzhu'
        break;
      default:
        classname = ''
    }
    $('.J-pop')
      .find('.J-tips').text(data.message).end()
      .find('.J-btn').removeClass().addClass(classname + ' J-btn').attr('href', data.link).end()
      .show();
  }

  // 领取红包
  $(document).on('click', '.J-reward', function(){
		var $this = $(this),
        $parent = $this.parent(),
        i = 0,
        timer;

        //if($this.hasClass('disabled')){ return false;}
        //$this.addClass('disabled');

         $.ajax({
           url: '/activitymb/activity20170301award',
           type: 'post',
		   cache: false,
           dataType: 'json',
		   data : {token : $("#token").val()},
           success:function(data){
			  if (data.awardStatus == '4'){
				// 设置奖金
				// $('.J-price').text(data.yesterdayAward/10000);
				$('.J-price').text(data.yesterdayAward/10000);
				// 开始播放动画
				timer = setInterval(function(){
				  i++;
				  $parent.toggleClass('lay');
				  if (i >= 4) {
					clearInterval(timer);
					eggAnimation();
				  }
				}, 500);
			  } else if (data.awardStatus == '-12') {
				  //頻繁操作
				  showPop({success: 2, message: '操作太频繁啰!', link: 'javascript:;'});
			  } else if (data.awardStatus == '-10') {
				  showPop({success: 2, message: '领奖失败!', link: 'javascript:;'});
			  } else if (data.awardStatus == '-11') {
				  //已經領過昨日獎品!
				  if(data.status == '-6'){
					  showPop({success: 1, message: '用户尚未领取首存奖金!', link: 'javascript:;'});
				  } else if(data.status == '-1'){
					  showPop({success: 1, message: '取得初始资料错误!', link: 'javascript:;'});
				  } else if(data.status == '-3'){
					  showPop({success: 1, message: '用戶错误!', link: 'javascript:;'});
				  } else if(data.status == '-7'){
					  showPop({success: 3, message: '今日充值金额未达标', link: 'phlotto://go_recharge'});
				  } else if(data.status == '-8') {
					  showPop({success: 2, message: '今日流水未达标', link: 'phlotto://go_lottolobby'});
				  } else if(data.status == '3') {
					  showPop({success: 2, message: '明日可领取奖金', link: 'phlotto://go_lottolobby'});
				  } else if(data.status == '-4'){
					  showPop({success: 2, message: '活动将于3月6日开始，敬请期待', link: 'phlotto://go_lottolobby'});
				  } else if(data.status == '-5'){
					  showPop({success: 1, message: '活动已结束!', link: 'javascript:;'});
				  } else if(data.status == '-9') {
					  showPop({success: 1, message: '非活动时间内', link: 'javascript:;'});
				  } else {
					  showPop({success: 1, message: '不該出現的狀態,已發生錯誤!', link: 'javascript:;'});
				  }
			  } else {
				  if(data.status == '-6'){
					  showPop({success: 1, message: '用户尚未领取首存奖金!', link: 'javascript:;'});
				  } else if(data.status == '-1'){
					  showPop({success: 1, message: '取得初始资料错误!', link: 'javascript:;'});
				  } else if(data.status == '-3'){
					  showPop({success: 1, message: '用戶错误!', link: 'javascript:;'});
				  } else if(data.status == '-4'){
					  showPop({success: 2, message: '活动将于3月6日开始，敬请期待', link: 'phlotto://go_lottolobby'});
				  } else if(data.status == '-5'){
					  showPop({success: 1, message: '活动已结束!', link: 'javascript:;'});
				  } else if(data.status == '-7'){
					  showPop({success: 3, message: '今日充值金额未达标', link: 'phlotto://go_recharge'});
				  } else if(data.status == '-8') {
					  showPop({success: 2, message: '今日流水未达标', link: 'phlotto://go_lottolobby'});
				  } else if(data.status == '3') {
					  showPop({success: 2, message: '明日可领取奖金', link: 'phlotto://go_lottolobby'});
				  } else if(data.status == '-9') {
					  showPop({success: 1, message: '非活动时间内', link: 'javascript:;'});
				  } else {
					  showPop({success: 1, message: '不該出現的狀態,已發生錯誤!', link: 'javascript:;'});
				  }
			  }
			  
				
			
           //} else if (res.success != '0'){
             // 传入状态码，及message
             // showPop(res);
           //}
         }});
		 /*
		 .fail(function(err){
           console.log(err);
           alert('网络不给力，请稍后再试！');
         }).always(function(){
          $this.removeClass('disabled');
         });
		 */
  });

  // 关闭红包
  $(document).on('click', '.J-confirm', function(){
    $('.J-hongbao').hide();
  });

  // 关闭pop
  $(document).on('click', '.J-pop-close', function(){
    $('.J-pop').hide();
  });
  
  //GA
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

  $(document).on('click', '.J-chongzhi', function() {
    ga('send', 'event', '3月份第一周手機活动页[鸿运储值]', '点击鸿运储值');
  });
  
});
