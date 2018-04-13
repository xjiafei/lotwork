//------------PT进入--------------
	iapiSetCallout('Login', calloutLogin);
	iapiSetCallout('Logout', calloutLogout);
	iapiLogin($('#ptAccount_name').val().toUpperCase(), $('#ptPasswd').val(), 1, "ch");
	
	//Logout时调用下面
	//logout(1,1)
	//<a href="javascript:logout(1,1)">Logout</a>
	
	function calloutLogout(response)
	{
		if (response.errorCode) {
		 console.log("Logout failed, " + response.errorCode);
		}
		 else {
		  window.history.back();	
		}	
	}
		
	function calloutLogin(response)
	{
		console.log(response);         
	   
	} 
	function popup(id){
	
		window.open('http://cache.download.banner.powerplay88.com/casinoclient.html?language=zh-cn&game='+id,'Games','width=800,height=600,menubar=no,scrollbars=no,toolbar=no,location=no,directories=no,resizable=no') ;
	}

	function checkUsername(){
		var username = $.trim($('#username').val()),
			disusername = $('#username').attr('disabled');
		if(username==''){
			//请输入用户名
			return 0;
		}
		else if(username.length<3 || username.length>29 ){
			//合长度有误，请输入4-8位字符
			return -1;
		}
		else if(disusername == undefined){
			 if(!/^[A-Za-z0-9_]+$/.test( username ) ){
				 
				//用户名只能使用字母和数字组成
				return -2;
			 }
			 if(!/^[A-Z0-9_]+$/.test( username ) ){
				 //用户名必须使用大写英文字母
				return -3;				 
			 }
		}		
		return 1;
	}


	function checkPhonenum(){
		var phonenum = $.trim($('#phonenum').val());
		if(phonenum.length == 0){
			//可为空
			return 1;
		}
		else if(! /^0?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/.test( phonenum ) ){
			//请输入正确的11位手机号码
			return -1;
		}
		return 1;

	}
$(function(){
	
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
	
	




	// showGuide表示页面是否播放引导
	if( showGuide ){
		//解决首页高度非固高度，引导结束后恢复自动高度
		$("body").css({
			"height":"1620px",
			"overflow":"hidden"
		})
		$.getScript(path_js+'/js/pt/jquery.indexGuide.js', function(){
			var guide = $('body').pageGuide();
		});
	}
	
	
	
	//注册用户名过长 TIP
	if($('#_userName').html().length > 10){
		var _userName = $('#_userName').html(),lostName;
		
			lostName = _userName.substring(0,10) + '...';
			$('#_userName').html(lostName);  
			
			$('.user-txt').on('mouseenter', '.user-name', function(){
				$('.usernae-tip').css({
					'top' : $(this).position().top - 5,
					'left' : $(this).position().left + $(this).width() + 5,
					'width' : '175px',
					'word-break' : 'break-all',
					'background': 'rgba(0, 0, 0, 0.4) none repeat scroll 0 0',
					'border': '1px solid #555',
					'border-radius': '3px',
					'color': '#fff',
					'padding': '5px',
					'position': 'absolute'
				}).show();			 	
			}).on('mouseleave', '.user-name', function(){
				$('.usernae-tip').hide();	
			});
			
	}
	
		





    //判断是否有注册过PT帐号	
	var newAccount =$('#newAccount').val(),
		Tip_text_name = $('#Tip_text_name'),
		userName = $('#username'),
		phonenum =$('#phonenum'),
	    disModifyAccount = $('#disModifyAccount');
	
	if(Number(newAccount) ==1 ){
		$("#isLoading").pop();
		setTimeout(function () {
			$("#isLoading").hide();
			$('#pop-land').pop();

		}, 3500);		
		
		userName.keyup(function(){
		  
		  var ss = $.trim($(this).val().replace(/^\s+/g,''));
		  $(this).val(ss);
		});

		  userName.bind('blur', function(){
				var ret=checkUsername();
				var prompt = $('.ui-text-prompt').eq(0);
				var check = $('.ui-check').eq(0);
				if(Number(ret)>0){
					prompt.show();
					check.hide();			
					$(this).css('color','green');
				}else{
					$(this).css('color','red');
					prompt.hide();
					check.show();
					if(ret == 0){
						check.html("请输入用户名");
					}
					else if(ret == -1){
						check.html("长度有误，请输入6-29位字符");
					}
					else if(ret == -2){
						check.html("用户名只能使用字母和数字组成");
					}
					else if(ret == -3){
						check.html("用户名必须使用大写英文字母");
					}
				}
				return false;
			});
	}
	
	$('#J-Button-Submit').css('border','0');
	//提交注册信息
	$('#J-Button-Submit').click(function(e){
		var usernames = checkUsername(),
			phonenums = checkPhonenum();
			
			if(Number(usernames) < 1){
				e.preventDefault();
				return false;
			}else if(Number(phonenums) < 0){
				e.preventDefault();
				return false;
			}
			$.ajax({
				url:'/pt/index/register/',
				data:{username:$.trim(userName.val()),phonenum:$.trim(phonenum.val())},
				dataType:'json',
				async:false,
				cache:false,
				beforeSend:function(){	
					$('#J-Button-Submit').removeClass('pop-land-btn').addClass('btn').css('border','0').attr('disabled','true');	
					$('#J-Button-Submit').val('提交中...');
				},
				success:function(data){
					if(data['status'] == '0'){	
					  window.location='/pt/index/';						 
					}
					else{
					  $('#J-Button-Submit').val('');
					  $('#J-Button-Submit').removeAttr("disabled").removeClass('btn').addClass('pop-land-btn').css('border','0');							  
					  alert(data['msg']);
					}
				}
			});
		
	});
	
	
		
	//-------------频道转帐开始，后期优化----------
	var oBtnFund = $('.btn-fund'),
		getdata,
		cleanFerData,
		mainAccount = $('#mainAccount'),
		ptAccount = $('#ptAccountAmount'),
		maxMoeny = $('#maxMoeny'),
		maxtptMoeny = $('#maxtptMoeny'),
		maxMuch = $('#maxMuch'),		
		transferCoe = $('#transferCoe'),
		AmountMin = $('#AmountMin'),
		AmountMax = $('#AmountMax'),		
		div_show1 = $('#div_show1'),
		div_show2 = $('#div_show2'),
		div_show4 = $('#div_show4'),
		dicrect = 0,//0:主账转PT， 1：PT转主账
		oIcoRe = $('.ico-pop-recharge'),
		accountMain = $('#account-main'), //转出
	    accountSub = $('#account-sub'),   //转入
		amoutSub = $('#amoutSub'),  //转出入金额
		errorShow = $('#errorShow'),
		isBool = false;
		
		
		
		oBtnFund.click(function(){
			
			dicrect = 0 ;
			accountMain.html('彩票账户<span style="color:#fff000">(主账户)</span>');
			accountSub.html('PT账户');
			
			cleanFerData();
			errorShow.html('');
			$('#J-buttn-transfer').removeClass("btn-loading");
			
			
			div_show1.show();
			div_show2.hide();
			$.ajax({
				url:'/pt/transfer/getdata',		
				dataType:'json',				
				cache:false,
				beforeSend:function(){
					$("#isLoading").pop();
					
				},	
				success:function(data){
					if(Number(data['status']) == 1){
						$("#isLoading").hide();	
						$('#pop-fund').pop();
						var obj1 = data['data'].bal,
							obj2 = data['data'].ptbal,
							obj3 = data['data'].TransferBal,
							obj4 = data['data'].avaliTransferTime,
							obj5 = data['data'].ptUnavailBal,
							obj6 = data['data'].tfSetting.transferCoe,
							obj7 = data['data'].tfSetting.AmountMin,
							obj8 = data['data'].tfSetting.AmountMax;						
							
							$('#J-tToken').val(data['data'].tToken);
							
							getdata(obj1,obj2,obj3,obj4,obj5,obj6,obj7,obj8);		
						
					}else{
						$("#isLoading").hide();	
						alert('抱歉，数据异常，请刷新，如问题没解决，请联系在线客服。');
					}
				}
			});
		
		});
		
		oIcoRe.click(function(){
				
			var a2 = accountMain.html(),
				a1 = accountSub.html();
				errorShow.html('');
				amoutSub.val('');
				accountMain.html(a1);
				accountSub.html(a2);
				
				if($.trim(a1) == 'PT账户'){
					dicrect = 1;
					div_show1.hide();
					div_show2.show();
				}else{
					dicrect = 0;
					div_show2.hide();
					div_show1.show();
				}			
				
				
		});
		
		
		
		amoutSub.focus(function(){
			$('#div_show3').hide();
			errorShow.html('').hide();
			if(dicrect == 1){				
				div_show1.hide();
				div_show2.show();
			}else{
				div_show1.show();
				div_show2.hide();
			}
		});
		
		//转账提交
		$('#J-buttn-transfer').click(function(e){
			isBool = false;
			if(dicrect == 0){//主账转PT
				var thisMoeny = amoutSub.val();			
				if(Number(thisMoeny) >  Number(maxMoeny.text())){
					errorShow.html('超过最多可转金额');
					errorShow.show();
					amoutSub.val(maxMoeny.text());
					isBool = false;
					e.preventDefault();
					return false;
				}else if(Number(thisMoeny) <  Number(AmountMin.text())){
					errorShow.html('小于最低限额');
					errorShow.show();
					amoutSub.val(AmountMin.text());
					isBool = false;
					e.preventDefault();
					return ;
				}else if(thisMoeny == '' || Number(thisMoeny) == 0){
					errorShow.html('输入您要转移的金额');
					errorShow.show();
					isBool = false;
					e.preventDefault();
					return;
				}else{
					errorShow.hide();
					isBool = true;
					
				}
			}else{//PT转主账
				var thisMoeny = amoutSub.val();		
				if(Number(maxtptMoeny.text()) == 0){
					errorShow.html('PT无可转余额');	
					amoutSub.val(0);					
					isBool = false;
					e.preventDefault();
					return;
				}else if(Number(thisMoeny) >  Number(maxtptMoeny.text())){
					errorShow.html('超过最多可转金额');
					amoutSub.val(maxtptMoeny.text());
					isBool = false;
					e.preventDefault();
					return ;
				}else if(thisMoeny == '' ){
					errorShow.html('输入您要转移的金额');
					isBool = false;
					e.preventDefault();
					return;
				}else{
					isBool = true;
					
				}
			}
			
			if(isBool){	
				var tToken = $.trim($('#J-tToken').val());
				$.ajax({
					url:'/pt/transfer/conform',						
					data:{bal:Number(amoutSub.val()),dicrect :dicrect,tToken:tToken},
					dataType:'json',
					type:'POST',
					beforeSend:function(){
						 $('#pop-fund .btn').addClass('btn-loading');
						 $('#J-buttn-transfer').css('background','#fff none repeat scroll 0 0');
					     $('#J-buttn-transfer').val('提交中..');			  
					     $("#J-buttn-transfer").attr('disabled','true');
					},				
					async:false,
					cache:false,
					success:function(data){
						if(Number(data['status']) == '0'){	
							$('#pop-fund .btn').removeClass('btn-loading');
							setTimeout(function () {
								$('#J-buttn-transfer').css('background','#fff000 none repeat scroll 0 0');
								$('#J-buttn-transfer').val('转账');
								$("#J-buttn-transfer").removeAttr("disabled"); 					
							}, 500);
						
							$('#div_show3').show();
							div_show1.hide();
							div_show2.hide();
							errorShow.html('').hide();
							amoutSub.val('');
							$.ajax({
								url:'/pt/transfer/getdata',		
								dataType:'json',
								async:false,
								cache:false,
								success:function(data){
									if(Number(data['status']) == 1){
										var obj1 = data['data'].bal,
											obj2 = data['data'].ptbal,
											obj3 = data['data'].TransferBal,
											obj4 = data['data'].avaliTransferTime;
											obj5 = data['data'].ptUnavailBal;											
											obj6 = data['data'].tfSetting.transferCoe,
											obj7 = data['data'].tfSetting.AmountMin,
											obj8 = data['data'].tfSetting.AmountMax;											
											$('#J-tToken').val(data['data'].tToken);
											getdata(obj1,obj2,obj3,obj4,obj5,obj6,obj7,obj8);		
										
									}else{
									    div_show1.hide();
										div_show4.show();
										$('#error4').html(data['data']);
									}
								}
							});
			
						}else{
							setTimeout(function () {
								$('#J-buttn-transfer').css('background','#fff000 none repeat scroll 0 0');
								$('#J-buttn-transfer').val('转账');
								$("#J-buttn-transfer").removeAttr("disabled"); 					
							}, 500);
							
							div_show1.hide();
							div_show4.show();
							$('#error4').html(data['data']);
						}
					}
				});
			}else{
				e.preventDefault();
				return false;
			}
		});
		
		
		//数据展示
		getdata = function(obj1,obj2,obj3,obj4,obj5,obj6,obj7,obj8){	
			mainAccount.text(obj1);
			ptAccount.text(obj2);
			maxMoeny.text(obj3);
			maxMuch.text(obj4);
			maxtptMoeny.text(obj5);			
			transferCoe.html(obj6);
			AmountMin.html(obj7);
			AmountMax.html(obj8);		
	     
		}
		
		//清空展示数据
		cleanFerData = function(){
			mainAccount.text('--');
			ptAccount.text('--');
			maxMoeny.text('--');
			maxMuch.text('--');
			maxtptMoeny.text('--');
			amoutSub.val('')
		};
		
		function checkAmount(){
			if(dicrect == 0){//主账转PT
				var thisMoeny = amoutSub.val();			
				if(Number(thisMoeny) >  Number(maxMoeny.text())){
					errorShow.html('超过最多可转金额');
					errorShow.show();
					amoutSub.val(maxMoeny.text());
					isBool = false;
					return false;
				}else if(thisMoeny == '' || Number(thisMoeny) == 0){
					errorShow.html('输入您要转移的金额');
					errorShow.show();
					isBool = false;
					return;
				}else{
					errorShow.hide();
					isBool = true;
					
				}
			}else{//PT转主账
				var thisMoeny = amoutSub.val();		
				if(Number(maxtptMoeny.text()) == 0){
					errorShow.html('PT无可转余额');	
					errorShow.show();
					amoutSub.val(0);					
					isBool = false;
					return;
				}
				else if(Number(thisMoeny) >  Number(maxtptMoeny.text())){
					errorShow.html('超过最多可转金额');
					errorShow.show();
					amoutSub.val(maxtptMoeny.text());
					isBool = false;
					return ;
				}else if(thisMoeny == '' ){
					errorShow.html('输入您要转移的金额');
					errorShow.show();
					isBool = false;
					return;
				}else{
					errorShow.hide();
					isBool = true;
					
				}
			}
			
		}
		
		//资金转移中平台查询
		$('#mainRefresh').click(function(){
			mainAccount.text('查询中...');
			searchAument(mainAccount,'bal');
			
		});
		$('#ptRefresh').click(function(){
			ptAccount.text('查询中...');
			searchAument(ptAccount,'ptbal');
			
		});	
		
		var searchAument =function(obj,datas){
			$.ajax({
				url:'/pt/transfer/getdata',		
				dataType:'json',
				async:false,
				cache:false,
				success:function(data){
					if(Number(data['status']) == 1){
					    obj.text(data['data'][datas]);	
					}
				}
			});
		
		}
		
	
	//-------------频道转帐end----------
	


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

});
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
		
		
		for(var i = 0, len = b.length; i < len; i++){
		
			if($.inArray(b[i],a)== -1){
				return false;
				
			}
		  
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

	$(".choose-model").select();







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
				phonenum.focus();
			}
		}
		return false;
	});
	
	//-------------总奖池----------------------		
	
	var tOAmount = 0;
	var oGamepot = $('.game-pot');
	var oJackpot = $('#jackpot');
	
	function rollNum(n,obj){	
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
	
	
	function getdata(){
		var num = $("#cur_num").val();
		$.ajax({
			url: 'http://tickers.playtech.com/jackpots/new_jackpotxml.php?info=3&casino=powerplay88&currency=cny',
			type: 'GET',
			dataType: 'xml',			
			cache: false,
			timeout: 10000,			
			success: function(xml){
				
				tOAmount=$(xml).find("casinodata ").children("amount-list").children('amount').text();				
				rollNum(parseInt(tOAmount),oJackpot);
				function move(){					
					rollNum(parseInt(tOAmount),oJackpot);		
					tOAmount = parseInt(tOAmount)+ 5;
				}
				//不要做成全局的。
				setInterval(move,3000);
				
			},
			error: function(xml){
				//接口异常随机
				var n = parseInt( Math.random()*100000000);
				setInterval(move,3000);
				function move(){
					//getdata();
					rollNum(n,oJackpot);		
					n = n+ 5;
				}
			}
		});
	}	
	
	getdata();	
 
});

(function($){

// 顶部用户中心
	new phoenix.Hover({
		triggers      :'#J-top-userinfo',
		panels        :'#J-top-userinfo .user-box',
		hoverDelayOut :300
	});
	// 顶部用户消息
	new phoenix.Hover({
		triggers      :'#J-top-user-message',
		panels        :'#J-top-user-message .msg-box',
		hoverDelayOut :300
	});

	//读取，修改余额可见状态值
	var cookieAllball = $.cookie("showAllBall");
	if(cookieAllball=="1"){	
		$('#hiddBall').css("display","inline"); 
		$('#hiddenBall').css("display","none");
	}else{	
		$('#hiddBall').css("display","none");	
		$('#hiddenBall').css("display","inline");
	}
	//显示余额
	$('#showAllBall').click(function(){
		$.cookie("showAllBall", "1", { expires: 7,path: '/'}); 
		$('#hiddBall').css("display","inline"); 
		$('#hiddenBall').css("display","none");
	});
	//隐藏余额
	$('#hiddBall').click(function(){
		$.cookie("showAllBall", "0", { expires: 7,path: '/'}); 
		$('#hiddBall').css("display","none");	
		$('#hiddenBall').css("display","inline");
	});
	try {
		//自动查询此用户未读信件
		$.ajax({
			type:'post',
			dataType:'json',
			cache:false,
			url:'/service2/queryunreadmessage',				
			data:'',				
			success:function(json){	
				if(json.unreadCnt !=0){																
					var html = "";
					$.each(json.receives, function (i){
                                            html += "<a href=\"/Service2/sysmessages?id="+ json.receives[i].id +"&msgTpye=uMsg&unread=1&pro=" + json.unreadCnt + "\">"+json.receives[i].sendAccount+"(未读消息"+json.unreadCnt+"笔)"+"</a>";
					});
					$("#readmsg").html(html);	
					$("#msgcount,#msg-title,#noreadmsg,#msgcount2,#noreadmsg2").html(parseInt(json.unreadCnt));												
				
				}
				else {					
					$("#readmsg").html("暂未收到新消息");
					$('#msgcount,#msg-title,#noreadmsg,#msgcount2,#noreadmsg2').html("0");	
					$('#readmsg').attr("style","text-align:center; color:black;");			
				}
			}			
		});
			
		
	
	} catch (err) {		
		
	}
	
		
	//金额刷新
	$('.refreshBall').click(function(event){
		var spanBalls = $('#spanBall');	
		try {
			//用户余额接口
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'/index/getuserbal',			
				data:'',
				beforeSend:function(){						
					 spanBalls.css('font-size','11px').html('查询中...');
					 $('.refreshBall').hide();
				},
				success:function(data){	
					if( data['status']=="ok")
					{
						spanBalls.removeAttr('style').text(data["data"]);
						$('.refreshBall').show();
					}
				},
				complete:function(){
					$('.refreshBall').show();						
				}
			});
		} catch (err) {		
			console.log("网络异常，读取信件信息失败");
		}
		 event.stopPropagation();
	});	
	$('#PtAumoutBalanceRef').click(function(event){
		var spanBalls = $('#PtAumoutBalance');	
		try {
			//用户余额接口
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'/pt/index/getuserbal',			
				data:'',
				beforeSend:function(){						
					 spanBalls.css('font-size','11px').html('查询中...');					
				},
				success:function(data){	
					if( data['status']=="ok")
					{
						spanBalls.removeAttr('style').text(data["data"]);
						$('.refresh').show();
					}else{
						spanBalls.removeAttr('style').text('----');
					}
				}
			});
		} catch (err) {		
			console.log("网络异常，读取信件信息失败");
		}
		 event.stopPropagation();
	});	

	
	$('.user-balance-title').click(function(){
		var spanBalls = $('[name="spanBall"]'),
			userBalanceTitle = $('.user-balance-title');
		 try {
			//用户余额接口
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'/index/getuserbal',				
				data:'',	
				beforeSend:function(){						
					 userBalanceTitle.addClass('user-balance-refresh');
					 $('[name=spanBall]').css('text-align','center').html('查询中...');
				},
				success:function(data){	
					if( data['status']=="ok")
					{
						setTimeout( function (){spanBalls.text(data["data"]); },500 );
						
					}
				},
				complete:function(){
					setTimeout( function (){userBalanceTitle.removeClass('user-balance-refresh'); },500 );						
				}
			});
		} catch (err) {		
			
		}
	});
	new phoenix.Tab({
		triggers      :'.pop-agr .ui-tab-title li',
		panels        :'.pop-agr .ui-tab-content',
		eventType     :'click',
		currPanelClass:'ui-tab-content-current'
	});	
	
	//数字校验，自动矫正不符合数学规范的数学(小数两位)
	var inputs =  $('#amoutSub'),checkFn;				
	checkFn = function(){
		var me = this,v = me.value,index;
		me.value = v = v.replace(/^\.$/g, '');		
		index = v.indexOf('.');
		if(index > 0){
			me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');			
			if(v.substring(index+1,v.length).length>2){				
				me.value= v  = v.substring(0, v.indexOf(".") + 3);
			}
		}else if(index >= 2){
			me.value = '';
		}	
	   
		me.value = v = v.replace(/[^\d|^\.]/g, '');
		me.value = v = v.replace(/^00/g, '0');				
	};
	inputs.keyup(checkFn);
	inputs.blur(checkFn);
	
	
		
		
	
		
})(jQuery);


(function() {       
	function async_load(){           
		var s = document.createElement('script');          
		s.type = 'text/javascript';          
		s.async = true;           
		s.src = "http://www.26hn.com/web/code/code.jsp?c=1&s="+S26hn;           
		var x = document.getElementsByTagName('script')[0];          
		x.parentNode.insertBefore(s, x);      
	}       
	if (window.attachEvent)           
	window.attachEvent('onload', async_load);
	else 
	window.addEventListener('load', async_load, false);  

	 $("#custom-service").click(function(){
		   if(typeof hj5107 != "undefined")
		   {
			 hj5107.openChat();
		   }
	 });
})();


//自定义下载弹出对话框方法
(function($) {
	function F(t, o) {
		this.opts = $.extend({
		}, o);
		this.$t = t;
		this.oMask = {};
		this.oPop = {};
		this.oPopClose = {};
		this.init();
	}
	F.prototype = {
		init: function() {
			var me = this;
			this.oPop = $(this.$t);
			this.oMask = $('.mask');
			this.oPopClose = this.oPop.find('.close');

			this.oPop.css({
				'position':'fixed',
				'left':'50%',
				'top':'50%',
				'margin-left':-this.oPop.outerWidth()/2+'px',
				'margin-top':-this.oPop.outerHeight()/2+'px'
			})

			this.oPopClose.click(function(){
				me.popclose();
			});
		},

		popshow: function(){
			this.oPop.find('input').val(""); //输入框置空
			this.oMask.show();
			this.oPop.removeClass('zoomOut').addClass('zoomIn animated').fadeIn(600);
		},
		popclose: function(){
			this.oMask.hide();
			this.oPop.removeClass('zoomIn').addClass('zoomOut').fadeOut(600);
		}
	}
	$.fn.proPop = function(o) {
		var instance;
		this.each(function() {
			instance = $.data(this, 'proPop');
			if (instance) {
			} else {
				instance = $.data(this, 'proPop', new F(this, o));
			}
		});
		return instance;
	}
})(jQuery);

function checkpasswd(str){
		if(str ==''){
			//请输入用户名
			return 0;
		}
		else if(str.length<6 || str.length>8 ){
			//合长度有误，请输入6-8位字符
			return -1;
		}
		else if(!/^[A-Za-z0-9]+$/.test( str ) ){
			//密码只能使用字母和数字组成
			return -2;
		}
		return 1;
	}

//下载客户端
$(function(){
	//密码格式校验
	function checkpassword(str){
		if(str ==''){
			//请输入用户名
			return 0;
		}
		else if(str.length<6 || str.length>8 ){
			//合长度有误，请输入6-8位字符
			return -1;
		}
		else if(!/^[A-Za-z0-9]+$/.test( str ) ){
			//密码只能使用字母和数字组成
			return -2;
		}
		return 1;
	}


	var otop = $("#download-fixed").offset().top;
	//兼容ie6 fixed属性
	$(window).scroll(function(){
		var scroll_top = parseInt($(window).scrollTop());
		if ('undefined' == typeof(document.body.style.maxHeight)) { //ie6
			$("#download-fixed").css({position:"absolute", top:$(window).scrollTop() + otop + "px"});
		}
	});

	var isChangeclick = false,
		isSetpwdclick = false;

	var $download = $('#download-fixed'),  //下载悬浮窗
		$btnDownload = $download.find('.btn-download'), //下载按钮
		$btnChangePwd = $download.find('.btn-changepwd'), //修改密码按钮
		$popDownload = $('#pop-download'),  //下载弹框
		$popChangePwd = $('#pop-changepwd');  //修改密码弹框


	//密码格式校验
	var password = $('.check-password');
	password.on('focus', function(){
		var prompt = $(this).parent().find('.ui-text-prompt');
		var check = $(this).parent().find('.ui-check');
		prompt.show();
		check.hide();
	});

	function fncheckpwd(){
		var ret = checkpassword($(this).val());
		var prompt = $(this).parent().find('.ui-text-prompt');
		var check = $(this).parent().find('.ui-check');
		if(ret>0){
			prompt.hide();
			check.hide();
		}else{
			prompt.hide();
			check.show();
			if(ret == 0){
				check.html("请输入有效密码");
			}
			else if(ret == -1){
				check.html("长度有误，请输入6-8位字符");
			}
			else if(ret == -2){
				check.html("密码只能使用字母和数字组成");
			}
		}
		return false;
	}
	//当文本框失去焦点时
	password.on('keyup', fncheckpwd);
	password.on('blur', fncheckpwd);

	//下载点击
	var dstep1 = $popDownload.find(".download_step1"),
		dstep2 = $popDownload.find(".download_step2"),
		dstep3 = $popDownload.find(".download_step3");
	var $pwdConfirm = $popDownload.find('.pwd-confirm');

	dstep1.show();
	dstep2.hide();
	dstep3.hide();
	$popDownload.proPop();
	$btnDownload.on('click',function(){
		//$.ajax({
		//	type : "GET",  //提交方式
		//	url : "",//获取是否设置过密码路径
		//	data : {
		//	},//数据，这里使用的是Json格式进行传输
		//	success : function(result) {//返回数据根据结果进行相应的处理
				$popDownload.proPop().popshow();
				//是否设置过密码
		        var result = "setted";  //请替换成服务器返回数据
				if(result == "setted"){
					dstep1.hide();
					dstep2.hide();
					dstep3.show();
				}else {
					dstep1.show();
					dstep2.hide();
					dstep3.hide();
				}

		//	}
		//});
	});
	//设置密码提交
	$pwdConfirm.on('click',function(){
		var ret = checkpassword($('#settingpwd').val());
		if(ret != 1) {  // 如果密码不正确禁止提交密码
			return;
		}


		if(!isSetpwdclick) {
			isSetpwdclick = true;
			$pwdConfirm.attr('disabled', 'true');
			$pwdConfirm.addClass('btn-loading');
			//$.ajax({
			//	type : "POST",  //提交方式
			//	url : "",//提交设置密码路径
			//	data : {
			//      pwd: $('#settingpwd').val().trim()
			//	},//数据，这里使用的是Json格式进行传输
			//	success : function(result) {//返回数据根据结果进行相应的处理
			//是否设置过密码
			var result = {
				'issucc': 'succ',
				'array': {
					'user': 'user'
				}
			};  //请替换成服务器返回数据
			if (result.issucc == "succ") {
				$("#usernm").html(result.array.user);              //填充用户账号
				$("#userpwd").html($('#settingpwd').val().trim()); //填充用户密码
				dstep1.hide();
				dstep2.show();
				dstep3.hide();
				$pwdConfirm.removeAttr('disabled');
				$pwdConfirm.removeClass('btn-loading');
				isSetpwdclick = false;
			} else {
				alert('设置失败')
			}
			//	}
			//});
		}
	})

	//修改点击
	var cstep1 = $popChangePwd.find(".changepwd_step1"),
		cstep2 = $popChangePwd.find(".changepwd_step2");
	var $changeConfirm = $popChangePwd.find('.changepwd-confirm');
	$popChangePwd.proPop();
	$btnChangePwd.on('click',function(){
		$popChangePwd.proPop().popshow();
		cstep1.show();
		cstep2.hide();
	});
	//提交修改密码
	$changeConfirm.on('click',function(){

		$popChangePwd.find('input').trigger('blur');
		var input1 = $("#pwd-origin").parent().find('.ui-check').is(":hidden"),
			input2 = $("#pwd-first").parent().find('.ui-check').is(":hidden"),
			input3 = $("#pwd-second").parent().find('.ui-check').is(":hidden");

		var value1 = $('#pwd-origin').val().trim(),
			value2 = $('#pwd-first').val().trim(),
			value3 = $('#pwd-second').val().trim();
		console.log((!!value1 && !!value2 && !!value3))
		
		
		if(!(input1 && input2 && input3) ){
			return;
		}
		if(!!value1 && !!value2 && !!value3){
		}else {
			return;
		}

		if(!isChangeclick) {
			isChangeclick = true;
			$changeConfirm.attr('disabled','true');
			$changeConfirm.addClass('btn-loading');
		//$.ajax({
		//	type : "POST",  //提交方式
		//	url : "",//提交修改密码路径
		//	data : {
		//      'origin': $('#pwd-origin').val().trim(),
		//      'pwd' : $('#pwd-first').val().trim(),
		//      'repeat': $('#pwd-second').val().trim()
		//	},//数据，这里使用的是Json格式进行传输
		//	success : function(result) {//返回数据根据结果进行相应的处理
				//是否设置过密码
				var result = 'succ' ; //请替换成服务器返回数据
				if(result == "succ"){
					var start = 5 ;
					var ospan = $popChangePwd.find('.changepwd-count span');
					ospan.html(start);
					cstep1.hide();
					cstep2.show();
					var timer = setInterval(function(){
						start--;
						ospan.html(start)
						if(start <= 0){
							$popChangePwd.proPop().popclose();
							clearInterval(timer);
						}
					},1000);
					$changeConfirm.removeAttr('disabled');
					$changeConfirm.removeClass('btn-loading');
					isChangeclick = false;
				}else {
					alert('修改失败');
				}
		//	}
		//});
		}else{
			return false;
		}

		//关闭窗口清除定时器
		$popChangePwd.find('.close').on('click',function(){
			clearInterval(timer);
		});


	})
	dstep1.show();
	dstep2.hide();
	//重复密码比对
	function passwordrepeat(){
		var prompt = $(this).parent().find('.ui-text-prompt');
		var check = $(this).parent().find('.ui-check');
		if($(this).val() != $('#pwd-first').val()){
			prompt.hide();
			check.show();
			check.html("请保持两次密码输入一致");
		}else{
			prompt.hide();
			check.hide();
			var url = "";
		}
		return false;

	}
	$('#pwd-second').on('keyup',passwordrepeat);
	$('#pwd-second').on('blur',passwordrepeat);
})
