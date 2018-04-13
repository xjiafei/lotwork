	var redBowStatus =0;
	var redBowPrize =0;
	var weekType = "0";
	var activityId = 0;
	var week1HTML = ' <div class="rotate"></div> '+
					' <div class="nov-center"> '+
					'     <div class="lock"> '+
					'         <h1>吉利骰宝</h1> '+
					'         <p>大吉大利 一骰就中</p> '+
					'         <div class="btnRed">拆红包</div> '+
					'         <div class="hand anim"></div> '+
					'     </div> '+
					'     <div class="open first"> '+
					'         <h1>吉利骰宝</h1> '+
					'         <p>告诉你个秘密，在<strong>吉利骰宝</strong>里</p> '+
					'         <p id="userVipLvl">投注满8元，下次登录还有更大的红包!</p> '+
					'         <a target="_blank" href="{$game_server}/gameBet/jldice1" ><div class="btnRed">使用红包</div></a> '+
					'         <div class="cong">获得体验金<strong></strong>元</div> '+
					'         <div class="closeRedbow"></div> '+
					'     </div> '+
					'     <div class="open used"> '+
					'         <h1>吉利骰宝</h1> '+
					'         <p>连续投注，再送你 888元 大红包</p> '+
					'         <a target="_blank" href="/activity/redbow" ><div class="btnRed left">查看详情</div></a> '+
					'         <a target="_blank" href="{$game_server}/gameBet/jldice1" ><div class="btnRed right">使用红包</div></a> '+
					'         <div class="cong">获得体验金<strong></strong>元</div> '+
					'         <div class="closeRedbow"></div> '+
					'     </div> '+
					' </div> ';
	var week2HTML = ' <div class="rotate"></div>'+
					' <div class="nov-center">'+
					'<div class="lock">'+
					'    <h1>宝开自有11选5</h1>'+
					'    <p>招财进宝，11选5送红包啦！</p>'+
					'    <div class="btnRed">拆红包</div>'+
					'    <div class="hand anim"></div>'+
					'</div>'+
					'<div class="open first">'+
					'    <h1>宝开自有11选5</h1>'+
					'    <p>乐利11选5，顺利11选5</p>'+
					'    <p id="userVipLvl">投注满<span style="color:#ffd70f;">8</span>元，下次登录还有更大的红包等你拆</p>'+
					//'	 <p>下次登录还有更大的红包等你拆</p>'+
					'    <div class="btnRed">使用红包</div>'+
					'    <div class="cong">获得体验金<strong>8.00</strong>元</div>'+
					'    <div class="closeRedbow"></div>'+
					'	<a target="_blank" href="{$game_server}/gameBet/ll115" ><div class="choose">乐利11选5</div></a>'+
					'	<a target="_blank" href="{$game_server}/gameBet/sl115" ><div class="choose" style="left:230px;">顺利11选5</div></a>'+
					'</div>'+
					'<div class="open used">'+
					'    <h1>宝开自有11选5</h1>'+
					'    <p>乐利11选5，顺利11选5</p>'+
					'    <p>个位出 <span style="color:#ffd70f;">11</span>，大家都有奖</p>'+
					'    <a target="_blank" href="/activity/redbow2" style="left:128px;"><div class="btnRed left">查看详情</div></a>'+
					'    <div class="btnRed right">使用红包</div>'+
					'    <div class="cong">获得体验金<strong></strong>元</div>'+
					'    <div class="closeRedbow"></div>'+
					'	 <a target="_blank" href="{$game_server}/gameBet/ll115" ><div class="choose">乐利11选5</div></a>'+
					'	 <a target="_blank" href="{$game_server}/gameBet/sl115" ><div class="choose" style="left:230px;">顺利11选5</div></a>'+
					'</div>';
	var week3HTML = ' <div class="rotate"></div> '+
					' <div class="nov-center"> '+
					'     <div class="lock"> '+
					'         <h1>宝开时时彩家族</h1> '+
					'         <p>  时时中奖 大吉大利</p> '+
					'         <div class="btnRed">拆红包</div> '+
					'         <div class="hand anim"></div> '+
					'     </div> '+
					'     <div class="open first"> '+
					'         <h1>宝开时时彩家族</h1> '+
					'         <p>乐利时时彩 / 吉利分分彩 / 顺利秒秒彩</p>'+
					'         <p id="userVipLvl">投注满<span style="color:#ffd70f;">8</span>元,下次登录还有更大的红包等你拆</p> '+
					//' 		  <p>下次登录还有更大的红包等你拆</p> '+
					'         <div class="btnRed">使用红包</div> '+
					'         <div class="cong">获得体验金<strong>8.00</strong>元</div> '+
					'         <div class="closeRedbow"></div> '+
					' 		<a target="_blank" href="{$game_server}/gameBet/llssc" ><div class="choose" _href="ll">乐利时时彩</div></a> '+
					' 		<a target="_blank" href="{$game_server}/gameBet/jlffc" ><div class="choose" style="left:145px;" _href="jl">吉利分分彩</div></a> '+
					' 		<a target="_blank" href="{$game_server}/gameBet/slmmc" ><div class="choose" style="left:280px;" _href="sl">顺利秒秒彩</div></a> '+
					'     </div> '+
					'     <div class="open used"> '+
					'         <h1>宝开时时彩家族</h1> '+
					'         <p>乐利时时彩 / 吉利分分彩 / 顺利秒秒彩</p>'+
					'         <p>红包不够？ 每天再送  <span style="color:#ffd70f;">3</span> 部iPhone7</p> '+
					'         <a target="_blank" href="/activity/redbow3" style="left:128px;"><div class="btnRed left">查看详情</div></a> '+
					'         <div class="btnRed right" style="left:228px;">使用红包</div> '+
					'         <div class="cong">获得体验金<strong>8.00</strong>元</div> '+
					'         <div class="closeRedbow"></div> '+
					' 		<a target="_blank" href="{$game_server}/gameBet/llssc" ><div class="choose" _href="ll">乐利时时彩</div></a> '+
					' 		<a target="_blank" href="{$game_server}/gameBet/jlffc" ><div class="choose" style="left:145px;" _href="jl">吉利分分彩</div></a> '+
					' 		<a target="_blank" href="{$game_server}/gameBet/slmmc" ><div class="choose" style="left:280px;" _href="sl">顺利秒秒彩</div></a> '+
					'     </div> '+
					' </div> ';
	
	var openFunction = function(){
		/*$.ajax({
			url:'/getRedpocket',
			type:'get',
			success:function(data){
				if(data.state == 1){            //表示获取红包成功
					$('.nov_curtion .lock').hide();
				}else{
					alert('获取红包失败：'+data.msg);  //如果获取失败请返回msg
				}
			}
		})*/
		//以下内容应该放在请求成功后（226行） t = 0 表示拆过红包，1表示未拆过
		
		$('.nov_curtion .lock').hide();
		$('.nov_curtion .rotate').show();
		if(redBowStatus == 2){
			$('.nov_curtion .used').show();
			//$('.nov_curtion .nov-center').css('background','url(images/used.png) no-repeat 51px 0');
			$('.nov_curtion .nov-center').css('background','url('+global_path_url+'/images/activity/2016_NovRedBow/201611_'+weekType+'/rp-open-1.png) no-repeat center center');
		}else if(redBowStatus == 1){
			$('.nov_curtion .first').show();
			$('.nov_curtion .nov-center').css('background','url('+global_path_url+'/images/activity/2016_NovRedBow/201611_'+weekType+'/rp-open-1.png) no-repeat center center');
		}
		
		var note = "";
		if("1" == weekType){
			note = "11月活动第一周";
		} else if ("2" == weekType){
			note = "11月活动第二周";
		} else if ("3" == weekType){
			note = "11月活动第三周";
		} else {
			note = "11月活动第N周";
		}
		
		$.ajax({
		   url:'/index/redbowactivityaward',
		   method:"post",
		   data:{prize:redBowPrize, memo:note, activityId: activityId},
		   success:function(){
		   },
		   error:function(){
		   }
		});  
	};
	
	$( document ).ready(function() {
		$.ajax({
			url:'/index/redbowactivity',
			type:'POST',
			dataType:'json',
			cache: false,
			success:function(data){
				if(data['state'] == 1 || data['state'] == 2){            //表示获取红包成功
					redBowStatus = data['state'];
					redBowPrize = data['prize'];
					weekType = data['weekType'];
					activityId = data['activityId'];
					game_server = data['game_server'];
					userLv = data['userLv'];
					if(weekType == "1"){
						week1HTML = week1HTML.replace(/\{\$game_server\}/ig, game_server);
						console.log(week1HTML);
						$(".nov_curtion").html(week1HTML);
						if(userLv == 1 || userLv == "1"){
							$("#userVipLvl").html("投注≥18元，下次登录还有更大的红包!");
						} else {
							$("#userVipLvl").html("投注≥6元，下次登录还有更大的红包!");
						}
						if(window.innerHeight <811){
						   $('.rotate').css('top','-22px');
						   $('.nov_curtion .nov-center').css('top','-56px');
						}
						//拆红包鼠标动作
						$(' .nov_curtion .lock .btnRed').mouseover(function(){
						   $('.nov_curtion .lock .hand').hide();
						}).mouseout(function(){
							$('.nov_curtion .lock .hand').show();
						});
						//重新綁方法, 因為是動態塞入的
						//拆红包后
						$(' .nov_curtion .lock .btnRed').bind("click", openFunction);
						//关闭
						$('.closeRedbow').click(function(){
							$('.nov_curtion').hide();
						});
						$('.lock').css('background','url('+global_path_url+'/images/activity/2016_NovRedBow/201611_'+weekType+'/rp-lock.png) no-repeat center center');
					} else if (weekType == "2"){
						week2HTML = week2HTML.replace(/\{\$game_server\}/ig, game_server);
						console.log(week2HTML);
						$(".nov_curtion").html(week2HTML);
						if(userLv == 1 || userLv == "1"){
							$("#userVipLvl").html('投注<span style="color:#ffd70f;">≥18</span>元，下次登录还有更大的红包等你拆');
						} else {
							$("#userVipLvl").html('投注<span style="color:#ffd70f;">≥6</span>元，下次登录还有更大的红包等你拆');
						}
						if(window.innerHeight <811){
							$('.rotate').css('top','-22px');
							$('.nov_curtion .nov-center').css('top','-6px');
						}
						//拆红包鼠标动作
						$(' .nov_curtion .lock .btnRed').mouseover(function(){
						   $('.nov_curtion .lock .hand').hide();
						}).mouseout(function(){
							$('.nov_curtion .lock .hand').show();
						});
						//拆红包后
						$(' .nov_curtion .lock .btnRed').bind("click", openFunction);
						//关闭
						$('.closeRedbow').click(function(){
							$('.nov_curtion').hide();
						});
        
						//第一次使用红包
						$('.first .btnRed').click(function(){
							//location.href = '/';
							$(this).hide();
							$('.first .choose').show();
							//alert('location.href="第一次使用红包"');
						});
						
						//使用红包后
						$('.used .btnRed.right').click(function(){
							//location.href = '/';
							$(this).hide();
							$('.used .choose').show();
							//alert('location.href="使用红包链接-非第一次使用"');
						});
						$('.lock').css('background','url('+global_path_url+'/images/activity/2016_NovRedBow/201611_'+weekType+'/rp-lock.png) no-repeat center center');
					} else if (weekType == "3"){
						week3HTML = week3HTML.replace(/\{\$game_server\}/ig, game_server);
						console.log(week3HTML);
						$(".nov_curtion").html(week3HTML);
						if(userLv == 1 || userLv == "1"){
							$("#userVipLvl").html('投注<span style="color:#ffd70f;">≥18</span>元,下次登录还有更大的红包等你拆');
						} else {
							$("#userVipLvl").html('投注<span style="color:#ffd70f;">≥6</span>元,下次登录还有更大的红包等你拆');
						}
						if(window.innerHeight <811){
							$('.rotate').css('top','-22px');
							$('.nov_curtion .nov-center').css('top','-6px');
						}
						//拆红包鼠标动作
						$(' .nov_curtion .lock .btnRed').mouseover(function(){
						   $('.nov_curtion .lock .hand').hide();
						}).mouseout(function(){
							$('.nov_curtion .lock .hand').show();
						});
						//拆红包后
						$(' .nov_curtion .lock .btnRed').bind("click", openFunction);
						//关闭
						$('.closeRedbow').click(function(){
							$('.nov_curtion').hide();
						});
						//第一次使用红包
						$('.first .btnRed').click(function(){
							//location.href = '/';
							$(this).hide();
							$('.first .choose').show();
							//alert('location.href="第一次使用红包"');
						});
						//使用红包后
						$('.used .btnRed.right').click(function(){
							//location.href = '/';
							$(this).hide();
							$('.used .choose').show();
							//alert('location.href="使用红包链接-非第一次使用"');
						});
						//选择去哪个平台
						$('.choose').bind('click',function(evt){
							switch($(this).attr('_href')){
								case 'll':
									console.log('乐利时时彩');
									//location.href = '/';
									break;
								case 'jl':
									console.log('吉利分分彩');
									//location.href = '/';
									break;
								case 'sl':
									console.log('顺利秒秒彩');
									//location.href = '/';
									break;
							}
						});
						$('.lock').css('background','url('+global_path_url+'/images/activity/2016_NovRedBow/201611_'+weekType+'/rp-lock.png) no-repeat center center');
					}
					$('.nov_curtion').show();
					$(".cong strong").html(data['prize']);
					
				} else {
					console.log(data['state']);
				}
			}
		})
	});
	
	
	//第一次使用红包
	/*$('.first .btn').click(function(){
		//location.href = '/';
		//alert('location.href="第一次使用红包"');
		location.href="http://em.dev02.com/gameBet/jldice1/";
	})
	//使用红包后
	$('.used .btn.right').click(function(){
		//location.href = '/';
		alert('location.href="使用红包链接-非第一次使用"');
	})
	//查看详情
	$('.used .btn.left').click(function(){
		//location.href = '/';
		alert('location.href="查看详情链接"');
	})*/