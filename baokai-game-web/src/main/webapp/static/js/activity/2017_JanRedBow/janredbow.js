	var redBowStatus =0;
	var janboHTML = '<div class="redpacket" id="redpacket">'+
					'	<div class="sidai"></div>'+
					'	<div class="rp-cover1"></div>'+
					'	<div class="rp-cover2"></div>'+
					' 	<div class="rp-result">'+
					'     <span class="rpresult">18</span>'+
					' 	</div>'+
					' 	<div class="rp-content">'+
					'   	<div class="rp-c-con">'+
					'       	<h3>宝开自有彩种</h3>'+
					'       	<p>大吉大利，一投就中</p>'+
					'       	<div class="rp-button">申领红包</div>'+
					'       </div>'+
					'       <div class="rp-c-con tips1">'+
					'			<h3>宝开自有彩种</h3>'+
					'        	<p class="con-info">告诉你个秘密<br>'+
					'           	在以下游戏里投注满<strong>6</strong>元<br>'+
					'           	下次登录还有更大的红包等你拆</p>'+
					'       		<p class="selfline"></p>'+
					'	          	<p class="con-linkbtn">'+
					'			   <a target="_self" href="{$game_server}/gameBet/jlffc" >吉利分分彩</a> '+
					'              <a target="_self" href="{$game_server}/gameBet/slmmc" >顺利秒秒彩</a>'+
					' 			   <a target="_self" href="{$game_server}/gameBet/llssc" >乐利时时彩</a> '+
					'    		   <a target="_self" href="{$game_server}/gameBet/jldice1">吉利骰宝</a> '+ 
					'         	</p>'+
					'      </div>'+
					'      <div class="rp-c-con tips2">'+
					'          <h3>宝开自有彩种</h3>'+
					'          <p class="con-info">APP连续投注，再送你<strong>888</strong>元大红包</p>'+
					'          <p class="selfline"></p>'+
					'          <p class="con-linkbtn">'+
					'			   <a target="_self" href="{$game_server}/gameBet/jlffc" >吉利分分彩</a> '+
					'              <a target="_self" href="{$game_server}/gameBet/slmmc" >顺利秒秒彩</a>'+
					' 			   <a target="_self" href="{$game_server}/gameBet/llssc" >乐利时时彩</a> '+
					'    		   <a target="_self" href="{$game_server}/gameBet/jldice1" >吉利骰宝</a> '+ 
					'          </p>'+
					'      </div>'+
					'	   <div class="rp-c-con tips3">'+
					'          <h3>宝开自有彩种</h3>'+
					'          <p class="selfline"></p>'+
					'          <p class="con-linkbtn">'+
					'			   <a target="_self" href="{$game_server}/gameBet/jlffc" >吉利分分彩</a> '+
					'              <a target="_self" href="{$game_server}/gameBet/slmmc" >顺利秒秒彩</a>'+
					' 			   <a target="_self" href="{$game_server}/gameBet/llssc" >乐利时时彩</a> '+
					'    		   <a target="_self" href="{$game_server}/gameBet/jldice1" >吉利骰宝</a> '+ 
					'          </p>'+
					'     </div>'+
					'	</div>'+
					'	<div class="fhlogo"></div>'+
					'</div>';      
	var  container ,startBtn;	
	$( document ).ready(function() {
		$.ajax({
			url:'/index/janredbowactivity',
			type:'POST',
			dataType:'json',
			cache: false,
			success:function(data){
				if(data['janstatus'] == 1 || data['janstatus'] == 2){            //表示获取红包成功
					game_server = data['game_server'];
					janboHTML = janboHTML.replace(/\{\$game_server\}/ig, game_server);
					console.log(janboHTML);
					$(".jan_curtion").html(janboHTML);
					container = $('#redpacket');  //红包容器
      				startBtn = container.find('.rp-button'); //拆红包 按钮

					//红包置中
					setPacketPos();
					$(window).on('resize',function(){
				    	setPacketPos();
					});

					//点击抽奖
					startBtn.on('click',function(){
						$.ajax({
							url:'/index/janredbowactivityaward',
							type:'POST',
							dataType:'json',
							cache: false,
							success:function(data){
								console.log(data['janstate']);
								if(data['janstate'] == 1){         //表示获取红包成功
									container.addClass('animated');
									container.find('.rp-c-con').hide();
									// var t = 1;以data.type形式返回
									if(data['janredbowstatus'] == 1){
										container.find('.rpresult').html(data['janredbowprize']);
										container.find('.tips1').delay(800).fadeIn();
									} else{
										var Now = new Date();
										var ThreeDate = "2017-01-15 00:00:00";
										if((Now < Date.parse(ThreeDate)).valueOf()){
											container.find('.rpresult').html(data['janredbowprize']);
											container.find('.tips2').delay(800).fadeIn();
										}else{
											container.find('.rpresult').html(data['janredbowprize']);
											container.find('.tips3').delay(800).fadeIn();
										}
									}	
								}else{
									alert('获取红包失败');  //如果获取失败请返回msg
								}
							}
						})
					});					
				} else {
					console.log(data['janstatus']);
				}
			}
		})	
	});
  	 				
	function setPacketPos(){
  	    var bw = container.width(),
   	      	bh = container.height();
    		container.stop().animate({
    		left: ($(window).width()- bw)/2,
    		top: ($(window).height()- bh)/2
    			})
    		}
  