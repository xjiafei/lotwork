// card - start
$(function(){
	//fontSize
	var $html = $('html');
	var $window = $(window);
	var fitPage = function(){
		var w = $html.width();
		w = w > 640 ? 640: w;
		w = w / 640;
		w = w * 100;
		$html.css({
			fontSize: w
		});
    }
    fitPage();
    var t;
    var func = function(){
	    clearTimeout(t);
	    t = setTimeout(fitPage, 25);
    }
    $window.on('resize', function(){
    	func();
    });
	
	var stepList = $('#stepList li');
	var boxList = $('#stepList .box');
	var stepBg = $('#stepList .bg-step');
	var globalprize ='';
	
	//刮刮卡
	var scratcher = function(){
	    $('body').mozUserSelect = 'none';
	    $('body').webkitUserSelect = 'none';
	 	var scratcher = $('#scratcher');
	    //var img = new Image();
	    var canvas = document.querySelector('canvas');
	    $('#canvas').css({
			position: 'absolute',
			left: 0,
			top: 0,
			backgroundColor: 'transparent'
		})
	    //img.addEventListener('load', function(e) {
	        var ctx;
	        var w = scratcher.width(),
	            h = scratcher.height();
	        var mousedown = false;
	 
	        function layer(ctx) {
	            ctx.fillStyle = '#D1D1D1';
	            ctx.fillRect(0, 0, w, h);
	        }
	 
	        function eventDown(e){
	            e.preventDefault();
	            mousedown=true;
	        }
	 		
	        function eventUp(e){
	            e.preventDefault();
	            mousedown=false;
	            var data=ctx.getImageData(0,0,w,h).data;
	            for(var i=0,j=0;i< data.length;i+=4){
				    if(data[i] && data[i+1] && data[i+2] && data[i+3]){
				        j++;
				    }
				}
				if(j<=w*h*0.8){
					if(globaldouble){
						stepList.eq(globalindex).find('.text').html('已领取<br/>' + globalprize + 'X2');
						$('#popInfo').html('哇！奖金翻倍，您获得<strong>'+globalprize*2+'奖金</strong>！<br/>明天，幸运的还是您哦！');
					}else{
						stepList.eq(globalindex).find('.text').html('已领取<br/>' + globalprize);
						$('#popInfo').html('恭喜你刮出了<strong>'+globalprize+'奖金</strong>！<br/>明天继续刮，还有翻倍奖金等着您！');
					}
					$('#popBtn').html('领取');
					stepList.eq(globalindex).removeClass('box-unlock');
					setTimeout(function(){
						stepList.eq(globalindex).addClass('box-open');
					},1000)
					var data = globalindex;
					$.ajax({   
						type:"POST",
						url:"claim/"+ globalindex,
						data:data,
						success:function(data){   
							console.log(data);   
						}  
					}); 
				}
	        }
	        function eventMove(e){
	            e.preventDefault();
	            var offsetX = $('canvas').offset().left,
	            	offsetY = $('canvas').offset().top;
	            if(mousedown) {
	                if(e.changedTouches){
	                    e=e.changedTouches[e.changedTouches.length-1];
	                }
	                
	                var x = (e.clientX + document.body.scrollLeft || e.pageX) - offsetX || 0,
	                    y = (e.clientY + document.body.scrollTop || e.pageY) - offsetY || 0;
	                with(ctx) {
	                    beginPath()
	                    arc(x, y, 10, 0, Math.PI * 2);
	                    fill();
	                }
	            }
	        }
	 
	        canvas.width=w;
	        canvas.height=h;
	        //canvas.style.backgroundImage='url('+img.src+')';
	        ctx=canvas.getContext('2d');
	        ctx.fillStyle='transparent';
	        ctx.fillRect(0, 0, w, h);
	        layer(ctx);
	 
	        ctx.globalCompositeOperation = 'destination-out';
	 
	        canvas.addEventListener('touchstart', eventDown);
	        canvas.addEventListener('touchend', eventUp);
	        canvas.addEventListener('touchmove', eventMove);
	        canvas.addEventListener('mousedown', eventDown);
	        canvas.addEventListener('mouseup', eventUp);
	        canvas.addEventListener('mousemove', eventMove);
	    //});
	    //img.src ='images/banner.jpg';
	};



	//页面初始化,从服务端获取投注金额,是否已刮奖
	$.ajax({
		url:'data',
		dataType:'json',
		success:function(res){
			//判断请求是否成功
			if(Number(res['isSuccess']) == 1){
				//是否开启
				var isOpen = res['open'];
				//是否达标
				var isStandard = res['standard'];
				//是否刮奖
				var isScratch = res['scratch'];
				//历史中奖信息
				var hisPrize = res['hisPrize'];
				//点击宝箱之后中奖信息
				var prize = res['prize'];
				//奖金是否翻倍
				var isDoublePrize = res['doublePrize'];

				$('body').append('<div id="mask" class="mask"></div>');//加蒙版
				var dataHeight = $('#stepList .date').outerHeight();
				//显示是否开启，是否达标，是否已刮奖
				for (var i = stepList.length; i >= 0; i--){
					if(isOpen[i]){
						stepList.eq(i).addClass('box-unlock');
						stepList.eq(i).find('.text').text('已开启');
						
					}else{
						stepList.eq(i).find('.text').text('未开启');
					}
					if(!isStandard[i] && isOpen[i]){
						stepList.eq(i).addClass('box-close').removeClass('box-unlock');
						boxList.eq(i).removeClass('box-unlock');
						stepList.eq(i).find('.date').text('');
						stepList.eq(i).find('.text').text('未达标');
					}

					if(isScratch[i]){
						stepList.eq(i).addClass('box-open').removeClass('box-unlock');
						if(isDoublePrize[i]){
							stepList.eq(i).find('.text').html('已领取<br/>' + hisPrize[i] + 'X2');
						}else{
							stepList.eq(i).find('.text').html('已领取<br/>' + hisPrize[i]);
						}
					}
					
				};
				//点击未解锁宝箱
				// $('.box-lock').click(function(){
				// 	event.stopPropagation();
				// 	var index = boxList.index(this);
				// 	$('#amountInfo').appendTo($(this)).show();
				// 	$('#bounsNum').text(betData[index+1].bouns);
				// 	$('#betExpect').text(betData[index+1].min);
				// 	if(index < 14){
				// 		$('#bounsTit').text('最高奖金');
				// 	}else{
				// 		$('#bounsTit').text('定额奖金');
				// 	}
				// });
				
				//点击解锁宝箱
				$('.box-unlock').click(function(e){
					if(!$(this).hasClass('box-unlock')){return;}
					var index = stepList.index(this);
					
					// $(this).find('.box-unlock').addClass('box-open').removeClass('box-unlock');
					$('#mask').delay(400).fadeIn(200);
					$('#pop').show().removeClass('zoomOut').addClass('animation zoomIn');
					scratcher();
					//从后台获取中奖数据
					$.ajax({
						url:'data',
						dataType:'json',
						success:function(res){
							globalindex = index;
							globalprize = prize[index];
							globaldouble = isDoublePrize[index];
							$('#bounsScratcher').text(prize[index]);
							if(globaldouble){
								$('#doublePrize').text('X2');
							}else{
								$('#doublePrize').text('X1');
							}
							
							// $('#popInfo').html('刮一刮 赢奖金');
							// $('#popInfo').css({marginTop: '20px'});
							// $('#popBtn').css({display: 'none'});
							
						}
					});
				});
			}else{
				//alert('请求失败，请重新刷新页面');
			}
		}
	});
	
	$('.pop-btn').click(function(){
		$('.pop').fadeOut();//removeClass('zoomIn').addClass('zoomOut').delay(200).fadeOut();
		$('#mask').fadeOut();
		setTimeout(function(){
			$('#popInfo').html('轻触屏幕，刮出好运');
			$('#popBtn').html('返回');
		},300)
		
	})

	$('#btnRules').click(function(){
		$('#popNotice').addClass('zoomIn').removeClass('zoomOut').delay(200).fadeIn();
		$('#mask').fadeIn();
	})
});
