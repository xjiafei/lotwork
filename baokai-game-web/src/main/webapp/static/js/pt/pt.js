
//头部右侧轮播
$(function(){
	//初始跨域请求
	$.ajax({
		url:'/api/jsonp/getAdverts?k=pt_index&r='+Math.random(),
		cache:false,
		dataType:'jsonp',
		jsonp: "callBack",
		success:function(data){
			if(Number(data['isSuccess']) == 1){
				var me = data,
					list = me.data,
					len = list.length,
					html = '',
					picShow = $('#ad-info-pic-show');
					
				if(len > 0){
					for(var i=0;i<len;i++){
						for(var j=0;j<data.data[i].list.length;j++){							
							html +=  '<li  style="display: none;"><a href="'+data.data[i].list[j].link+'"><img alt="'+data.data[i].list[j].title+'" src="'+data.data[i].list[j].src+'"></a></li>';
						}						
					}
					picShow.append(html);
				}
				
				var oPic=$('.ad-info-pic');
				var oPics=$('.ad-info-pic li');//图
				var oNums=$('.ad-info-num li');//焦点
				var iNow=0;
				
				
				
				//DOM完成执行动画事件	
				oPicsLength = oPics.length;
				oPicsWidth = oPics.width();
				oPic.width(oPicsLength*oPicsWidth)
				oNums.each(function(index){
					$(this).mouseover(function(){
						clearInterval(timer);
						slide(index);//索引
					});
				});
				function slide(index){
					iNow=index;
					var nowLeft = -oPicsWidth*index;
					oNums.eq(index).addClass('current').siblings().removeClass();
					oPics.eq(index).fadeIn(800).siblings().fadeOut(800);
					//oPic.css("left",nowLeft);
				}
				function autoRun(){
					iNow++;
					if(iNow==oNums.length){
						iNow=0;
					}
					slide(iNow);
				}
				var timer=setInterval(autoRun,6000);
				oPic.hover(function(){
					clearInterval(timer);
				},function(){
					timer=setInterval(autoRun,6000);
				});
	
			
			}
		},
		error:function(xhr, type){
			console.log(xhr);
		}
	});	
	
	
});

//判断浏览器是否支持CSS3s属性
function supportCss3(style) {
	var prefix = ['webkit', 'Moz', 'ms', 'o'],
		i,
		humpString = [],
		htmlStyle = document.documentElement.style,
		_toHumb = function (string) {
			return string.replace(/-(\w)/g, function ($0, $1) {
				return $1.toUpperCase();
			});
		};
	for (i in prefix)
		humpString.push(_toHumb(prefix[i] + '-' + style));
	humpString.push(_toHumb(style));
	for (i in humpString)
		if (humpString[i] in htmlStyle) return true;
	return false;
}
//头部游戏推荐轮播
$(function(){
	var oSlider=$('.ad-slider');
	var oPics=$('.ad-slider-pic li');
	var oNums=$('.ad-slider-num li');
	var eBtn = $('.ad-change')
	var index=0;
	var timer = null;
	var historyIndex = 0;
	/*
	oNums.each(function(index){
		$(this).mouseover(function(){
			slide(index);
		})
	});
	*/
	function slide(index){
		oNums.eq(index).addClass('current').siblings().removeClass();
		if(supportCss3('transform-style')) {
			oPics.eq(index).show().addClass('current').siblings().removeClass('current');
			oPics.eq(historyIndex).show().find('.flip').addClass('out').removeClass('in');
			oPics.eq(historyIndex).delay(600).hide(100);
			oPics.eq(index).find('.flip').addClass('in').removeClass('out');
		}else{
			oPics.eq(index).fadeIn(800).siblings().fadeOut(800);
		}
		historyIndex = index;
	}
	function autoRun(){
		index++;
		if(index==oNums.length){
			index=0;
		}
		slide(index);
	}
	eBtn.click(function(){
		autoRun()
	});
	var oPre = $('.ad-slider-pic .preserve')
	for(i=0;i<oPre.length;i++){
		$(oPre[i]).mouseover(function(){
			$(this).stop().animate({
				marginTop:-10
			},300);
		});
		$(oPre[i]).mouseout(function(){
			$(this).stop().animate({
				marginTop:0
			},300);
		});
	}
})

//列表筛选
$(function(){
	var oMenus=$('.game-list-menu dd');
	var oPic=$('.game-list-pic ul');
	var oPics=$('.game-list-pic li');
	//页面初始化
	var nPicWidth=oPic.width();
	var nPicsWidth=oPics.outerWidth();
	var nPicsHeight=oPics.outerHeight();
	var nPicsLength=oPics.length;
	var nCol = Math.floor(nPicWidth/nPicsWidth);
	var nRow = Math.ceil(oPics.length/nCol);
	var nPicsMr = parseInt(oPics.css('marginRight'));
	var nPicsMb = parseInt(oPics.css('marginBottom'));
	oPic.height(oPic.height());
	//var dom = $('<div class="game-loading"></div>').appendTo('.game-list-pic');
	oPics.each(function(i){
		var nPicsL = i%nCol*(nPicsWidth+nPicsMr);
		var nPicsT = Math.floor(i/nCol)*(nPicsHeight+nPicsMb);
		$(this).css('position','absolute');
		$(this).css('left',nPicsL+'px');
		$(this).css('top',nPicsT+'px');
		$(this).addClass('current');
		//$(this).attr('data-code',i);
	});
	
	//首先给窗口绑定事件scroll
	/*
	$(window).bind("scroll",function(){
		// 然后判断窗口的滚动条是否接近页面底部，这里的20可以自定义
		if($(document).scrollTop() + $(window).height() > $(document).height() - 20) {
			//if(oPics.is(':hidden')){
			var oPicsHide = $('.game-list-pic li.hide');
				for(i=0;i<oPicsHide.length;i++){
					console.log(i);
					oPicsHide.eq(i).hide().delay((i+5)*200).fadeIn(300).removeClass('hide');
				}
				dom.fadeOut(600);
				oPic.stop().animate({
					height:nRow==0?nPicsHeight+nPicsMb:(nPicsHeight+nPicsMb)*nRow
				},1200);
			//}
		}
	})
	*/
	//点击奖池出现开始按钮
	oPics.hover(function(){
		$(this).find('.game-pot').slideUp(200);
	},function(){
		$(this).find('.game-pot').delay(200).slideDown(200);
	});
	//面板（搜索+收藏）
	var oMark = $('#game-mark');
	var oMarkTxt = $('#game-mark-text');
	var oMarkNum = $('.game-mark-text em');
	var oMarkClear = $('.game-mark-clear');
	var oSerch = $('#game-search');
	var oSerchBtn = $('.game-search-btn');
	var oSerchBtnOut = $('.game-function .game-search-btn');
	var oSerchBtnIn = $('.game-list-title .game-search-btn');
	var oSerchInput = $('.game-search-input');
	var oSerchInputOut = $('.game-function .game-search-input');
	var oSerchInputIn = $('.game-list-title .game-search-input');
	var oCH = $('.container-head');
	var oGT = $('.game-list-title');
	var oGTClose = $('.game-list-title .close');
	var oGSClose = $('#game-search-close');
	var oGMClose = $('#game-mark-close'),
		gameSearchInput1 = $('#game-search-input1'),
		gameSearchInput2 = $('#game-search-input2');
		
		
	//展开面板
	function markOpen(){
		errorAlertOut(screenNone);
		oCH.slideUp(800);
		oGT.slideDown(800);
		oPics.each(function(){
			$(this).stop().fadeOut(600);
		});
	}
	//关闭面板
	function markClose(){
		oCH.delay(800).slideDown(800);
		oGT.delay(800).slideUp(800);
		var oCur = $('.game-list-pic .current');
		if(oCur.length == 0){
			errorAlertIn(screenNone);
		}else{
			errorAlertOut(screenNone);
		}
	}
	//游戏搜索
	function search(val){		
			var tags = getNames();
			var texts = [];
			var reg1 = /^[a-zA-Z]+$/;
			var reg2 =/^(([a-z0-9]+\s+)|(\s+[a-z0-9]+))[a-z0-9\s]*$/i;
			var val = $.trim(val);
			
				if(reg1.test(val)||reg2.test(val)||val == ''){
					for(j=0;j<nPicsLength;j++){
						$(oPics[j]).removeClass('searchCur').stop().fadeOut(600);
						for(i=0;i<tags.length;i++){
							if(tags[i].sort()[0].toLowerCase() == val.toLowerCase()){
								$(oPics[i]).addClass('searchCur');
							}
						}
					}
				}else{
					texts.push($.trim(val));
					for(j=0;j<nPicsLength;j++){
						$(oPics[j]).removeClass('searchCur').stop().fadeOut(600);
						for(i=0;i<tags.length;i++){
							if(arrInclude(tags[i],texts)){
								$(oPics[i]).addClass('searchCur');
							}
						}
					}
				}
			
			var oCur = $('.game-list-pic li.searchCur');
			picShow(oCur);
			if(oCur.length == 0){
				errorAlertIn(searchNone);
			}
				
		
	}
	
	//回车事件(区分出两次)
	gameSearchInput1.keydown(function(e){
		if(e.keyCode==13){
		    oSerch.delay(800).fadeIn();
			markOpen();	
			//第一个给值第二个input
			oSerchInputIn.val(oSerchInputOut.val());			
			var val = $(this).val();			
			search(val);
		}
	}); 
	
	
	gameSearchInput2.keydown(function(e){
		if(e.keyCode==13){
		    oSerch.delay(800).fadeIn();
			markOpen();	
			//第二个反给值
			oSerchInputOut.val(oSerchInputIn.val());					
			var val = $(this).val();			
			search(val);
		}
	}); 

	//展开搜索
	oSerchBtnOut.click(function(e){
		
		oSerch.delay(800).fadeIn();
		markOpen();	
		//第一个给值第二个input
		oSerchInputIn.val(oSerchInputOut.val());			
		var val = oSerchInputOut.val();			
		search(val);
		
	}); 
	oSerchBtnIn.click(function(e){
	
		oSerch.delay(800).fadeIn();
		markOpen();	
		//第二个反给值
		oSerchInputOut.val(oSerchInputIn.val());					
		var val = oSerchInputIn.val();			
		search(val);
		
	}); 
	
	
	//搜索面板搜索
	oSerchBtnIn.click(function(){
		var val = oSerchInputIn.val();
		search(val);
	});
	//关闭搜索
	oGSClose.click(function(){
		errorAlertOut(searchNone);
		oSerch.fadeOut(800);
		markClose();
		oSerchInputOut.val(oSerchInputIn.val());
		$('.game-list-pic li.searchCur').stop().fadeOut(600);
		var oNewPics= $('.game-list-pic .current')
		setTimeout(function(){
			picShow(oNewPics);
		},1200);
		
	});
	//--------------收藏---------------------------
	//展开收藏夹
	oMarkTxt.click(function(){
		oMark.delay(800).fadeIn();
		markOpen();
		oPic.addClass('game-list-heart');
		var t = $('.game-list-pic .twinkling');
		var oNewPics = t.parent();
		picShow(oNewPics);
		$("<i class='heart-mask'>").insertAfter('.twinkling');
		var h = $('.heart-mask');
		h.each(function(){
			$(this).click(function(){
				$(this).prev().removeClass('twinkling').parent().stop().fadeOut(600);
				$(this).remove();
				var t = $('.game-list-heart .twinkling');
				var oTPics = t.parent();
				picShow(oTPics);
				markNum();
			});
		});
		var oCur = $('.game-list-pic .twinkling');
		if(oCur.length == 0){
			errorAlertIn(markNone);
		}
	});
	//清空收藏夹
	oMarkClear.click(function(){
		errorAlertOut(markNone);
		oPics.find('.twinkling').removeClass('twinkling').end().stop().fadeOut(600);
		var h = $('.heart-mask');
		h.remove();
		markNum();
		$.ajax({
			url: '/pt/index/clearusergame',
			type: 'POST',
			dataType: "json",
			data:'',
			cache: false,
			success: function(data){
				console.log(data);
			}
		});
		//t.removeClass('twinkling').fadeOut(600);
	});
	//关闭收藏夹
	oGMClose.click(function(){
		errorAlertOut(markNone);
		oPic.removeClass('game-list-heart');
		oPics.find('.twinkling').end().stop().fadeOut(600);
		var h = $('.heart-mask');
		h.remove();
		oMark.fadeOut(800);
		markClose();
		var oNewPics= $('.game-list-pic .current')
		setTimeout(function(){
			picShow(oNewPics);
		},1200);
	});
	//收藏计数
	function markNum(){
		var o = $('.game-list-pic .twinkling');
		var l = o.length;
		oMarkNum.text(l)
	}
	//点击加入收藏
	$('.heart').click(function(){
		var gameCode = $(this).parent('li').attr('data-code');
		
		if($(this).hasClass('twinkling')){
			$(this).removeClass('twinkling');
			$.ajax({
				url: '/pt/index/delusergame/',
				type: 'POST',
				dataType: "json",
				data:{'gameCode':gameCode},
				cache: false,
				success: function(data){
					console.log(data);
				}
			});
			
		}else{
			$(this).addClass('twinkling');
			$.ajax({
				url: '/pt/index/addusergame/',
				type: 'POST',
				dataType: "json",
				data:{'gameCode':gameCode},
				cache: false,
				success: function(data){
					console.log(data);
				}
			});
		}
		markNum()
	});
	$('.heart').mouseover(function(){
		if($(this).hasClass('twinkling')){
			$(this).attr('title','点击可取消收藏');
		}else{
			$(this).attr('title','点击可收藏游戏');
		}
	});
	//搜索框
	//$('.game-search-input').focus(function(){
	//	var txt_value = $(this).val();
	//	if(txt_value == '请输入你想搜索的内容'){
	//		$(this).val('');
	//	}
	//});
	//$('.game-search-input').blur(function(){
	//	var txt_value = $(this).val();
	//	if(txt_value  == ''){
	//		$(this).val('请输入你想搜索的内容');
	//	}
	//});
	//无结果
	var screenNone = $('.alert-error-screen');
	var searchNone = $('.alert-error-search');
	var markNone = $('.alert-error-mark');
	function errorAlertIn(obj){
		var oGPic = $('.game-list-pic');
		obj.prependTo(oGPic);
		obj.delay(1200).fadeIn(600);
	}
	function errorAlertOut(obj){
		obj.fadeOut(600);
	}
	//条件筛选
	oMenus.each(function(){
		$(this).click(function(){
			$(this).addClass('current').siblings().removeClass('current');
			var tags = getTags();
			var texts = getTexts();
			NewPic(texts);
			var oCur = $('.game-list-pic .current');
			if(oCur.length == 0){
				errorAlertIn(screenNone);
			}else{
				errorAlertOut(screenNone);
			}
		});
	});
	
	//获取菜单文字
	function getTexts(){
		var texts = [];
		var oCur = $('.game-list-menu .current')
		oCur.not('.all').each(function(){
			texts.push($.trim($(this).find('span').attr('data-id')));
			
		});
		return texts;
	}
	//获取列表标签
	function getTags(){
		var tags = [];
		oPics.each(function(){
			if($(this).data('tags').length > 0){
				tags.push($(this).data('tags').split(','));				
			}
			
		});
		return tags;
	}
	function getNames(){
		var names = [];
		oPics.each(function(){
			names.push($(this).data('names').split(','));
		});
		return names;
	}
	//数组A是否包含数组B
	function arrInclude(arr1,arr2){
		var a = arr1.sort().toString().toLowerCase();
		var b = arr2.sort().toString().toLowerCase();
		if(a.indexOf(b) >= 0){
			return true;
		}
	}
	//列表排列
	function picShow(obj){
		for(i=0;i<obj.length;i++){
			$(obj[i]).stop().fadeIn(600).animate({
				left:i%nCol*(nPicsWidth+nPicsMr),
				top:Math.floor(i/nCol)*(nPicsHeight+nPicsMb)
			},{duration:600,queue:false});
		}
		nRow = Math.ceil(obj.length/nCol);
		$(oPic).stop().animate({
			height:nRow==0?nPicsHeight+nPicsMb:(nPicsHeight+nPicsMb)*nRow
		},1200);
	}
	//数字数组类型算法匹配
	function isContained(a, b){
		if(!(a instanceof Array) || !(b instanceof Array)) return false;
		if(a.length < b.length) return false;
		var aStr = a.toString();
			//console.info(aStr);
		for(var i = 0, len = b.length; i < len; i++){
			//console.info(aStr.indexOf(b[i]));
		   if(aStr.indexOf(b[i]) == -1) return false;
		}
		return true;
	}

	//新列表生成
	function NewPic(texts){
		var istrues,
			tag ,
			tags = [];
			
			$('.game-list-pic li').removeClass('current').stop().fadeOut(600);
			oPics.each(function(){				
				
				tag = $(this).attr('data-tags');
				tags = tag.split(",");
				
					if(texts != '' || tags.length >0){
						istrues =isContained(tags,texts);
						
						if(istrues){
							$(this).addClass('current').stop().fadeOut(600);
						}					
					}else{
						$(this).addClass('current').stop().fadeOut(600);
						
					}					
				
			});
			
			var oNewPics=$('.game-list-pic li.current');
			picShow(oNewPics);
	}
})
$(function(){
	//numberScorll
	function getdata(){
		var num = $("#cur_num").val();
		$.ajax({
			url: 'js/pt/pt_index.json',
			type: 'GET',
			dataType: "json",
			data:{'count':num},
			cache: false,
			timeout: 10000,
			error: function(){},
			success: function(data){
				rollNum(data.count,oGamepot);
				rollNum(data.count,oGamepot);
			}
		});
	}
	
	//参数说明：num 要格式化的数字 n 保留小数位
	var formatNum = function(num,n) {
		num = String(num.toFixed(n));
		var re = /(-?\d+)(\d{3})/;
		while(re.test(num)) num = num.replace(re,"$1,$2")
		return num;
	}
	
	var n = parseInt(Math.random()*1000000);
	var oGamepot = $('.game-pot');//gamecode
	var oJackpot = $('#jackpot');//总
	rollNum(n,oJackpot);
	rollNum(n,oGamepot);
	
	
	
	
	oGamepot.each(function(){
		var
			ticker = new Ticker({info: 1, local: 1, casino: 'playtech', game: 'car',currency: 'eur'}),
			gamePro = $(this).attr('id');
			ticker.attachToTextBox(gamePro);
			ticker.tick();
	});
		
		
		
	function move(){
		//getdata();
		rollNum(n,oJackpot);
		rollNum(n,oGamepot);
		n++;
	}
	
	setInterval(move,3000);
	
	function rollNum(n,obj){
		n = formatNum(n,0);
		var nL = String(n).length;
		var objNum = obj.find('em');
		var objHeight = obj.height();
		var oL = objNum.length/obj.length
		for(var i=0;i<nL;i++){
			if(oL<=i){
				obj.append("<em><span style='background-position:0 0'></span></em>");
			}
			var num = String(n).charAt(i);
			obj.each(function(){
				var objEm = $(this).find('em').eq(i);
				var objSpan = $(this).find('span').eq(i);
				if(isNaN(num)){
					objEm.addClass('ico-comma');
				}else{
					objEm.removeClass('ico-comma');
					var y = -parseInt(num)*objHeight;
					objSpan.animate({
						backgroundPosition:'0 '+String(y)+'px'
					}, 800);
				}
			});
		}
		$("#cur_num").val(n);
	}
});
//获奖信息滚动
$(function(){
	var timer = null;
	var oList = $(".win-info");
	var oListUl = $(".win-info ul");
	var oListLi = $(".win-info li");
	var nReplace = nTopHeight = oListLi.eq(0).outerHeight();
	oListUl.html(oListUl.html() + oListUl.html());
	clearInterval(timer);
	function roll(){
		if(nTopHeight > oListUl.outerHeight()/2){
			oListUl.css("top",0);
			nTopHeight = nReplace;
		}
		oListUl.animate({
			top:-nTopHeight
		},800);
		nTopHeight += nReplace;
	}
	var timer = setInterval(roll,4000);
		oListUl.mouseover(function(){
			clearInterval(timer);
		});
		oListUl.mouseout(function(){
		timer = setInterval(roll,4000);
	});
});

//返回顶部
$(function(){
	var oBack = $('.backtop');
	var win = $(window);
	oBack.click(function(){
		$('body,html').animate({scrollTop:0},1000);
	});
	win.scroll(function(){
		clearTimeout(timer);
		var timer = setTimeout(function(){
			if(win.scrollTop() > 200){
				oBack.fadeIn(700);
			}else{
				oBack.fadeOut(700);
			}
		}, 300);
	});
});

$(function(){
	//资金转移
	
	//投注记录
	var oBtnBet = $('.btn-bet');
	oBtnBet.click(function(){
		$('#pop-bet').pop();
	});
	var oBtnAgr = $('.btn-agr');
	oBtnAgr.click(function(){
		$('#pop-agr').pop();
	});
});

//模拟下拉框

(function($){
	$.fn.select = function(){
		var o = $('.choose-list');
		$('.choose-model .arrow').click(function(){
			$('.choose-model .arrow').addClass('hover');
			o.slideDown(200);
			return false;
		});
		$('.choose-list a').click(function(){
			var txt = $(this).text();
			$('.info').text(txt);
			$('.choose-model .arrow').removeClass('hover');
			o.slideUp(200);
		});
		$(document).click(function(event){
			if( $(event.target).attr("class") != 'choose-model' ){
				$('.choose-model .arrow').removeClass('hover');
				o.slideUp(200);
			}
		});
	}
})(jQuery)
$(".choose-model").select();

//第一次登陆弹出
(function($){
	$.fn.pop = function(){
		var oMask = $('.mask');
		var oPop = $(this);
		var oPopClose = oPop.find('.close');
		var oPopBtn = oPop.find('.pop-land-btn');
		oMask.show();
		oPop.css({
			'position':'fixed',
			'left':'50%',
			'top':'50%',
			'margin-left':-oPop.outerWidth()/2+'px',
			'margin-top':-oPop.outerHeight()/2+'px'
		})
		oPop.removeClass('zoomOut').addClass('zoomIn animated').fadeIn(600);
		oPopClose.click(function(){
			oMask.hide();
			oPop.removeClass('zoomIn').addClass('zoomOut').fadeOut(600);
		});
		
	}
})(jQuery)
//$('#pop-land').pop();


function checkUsername(){
	var username = $.trim($('#username').val());
	if(username==''){
		//请输入用户名
		return 0;
	}
	else if(username.length<6 || username.length>29 ){
		//合长度有误，请输入4-8位字符
		return -1;
	}
	else if(!/^[A-Za-z0-9]+$/.test( username ) ){
		//用户名只能使用字母和数字组成
		return -2;
	}
	else if(!/^[A-Z0-9]+$/.test( username ) ){
		//用户名必须使用大写英文字母
		return -3;
	}
	return 1;
}


function checkPhonenum(){
	var phonenum = $.trim($('#phonenum').val());
	if(phonenum.length == 0){
		//长度有误，请填写11位数字
		return 0;
	}
	else if(! /^0?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/.test( phonenum ) ){
		//请输入正确的11位手机号码
		return -1;
	}
	return 1;

}

$(function(){
	var phonenum = $('#phonenum');
	phonenum.bind('focus', function(){
		var ret = checkPhonenum();
		var prompt = phonenum.parent().find('.ui-text-prompt');
		var check = phonenum.parent().find('.ui-check');
		prompt.show();
		check.hide();
	});
	//当文本框失去焦点时
	phonenum.bind('blur', function(){
		var ret = checkPhonenum();
		console.log(ret);
		var prompt = phonenum.parent().find('.ui-text-prompt');
		var check = phonenum.parent().find('.ui-check');
		if(ret >= 0){
			prompt.show();
			check.hide();
		}
		else{
			prompt.hide();
			check.show();
			if(ret == -1){
				check.html("请输入正确的11位手机号码");
			}
		}
		return false;
	});
	//-------------游戏调用----------------------
	
    

 
});


