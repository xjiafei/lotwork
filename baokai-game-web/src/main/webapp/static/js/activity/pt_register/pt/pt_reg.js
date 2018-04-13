//自定义弹出对话框方法
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
var ptgame_server = "http://pt.fh500.com/";
$(function(){
	//-------------总奖池----------------------
	//格式化金额
	function formatMoney(number, places, thousand, decimal) {
		number = number || 0;
		places = !isNaN(places = Math.abs(places)) ? places : 2;
		thousand = thousand || ",";
		decimal = decimal || ".";
		var negative = number < 0 ? "-" : "",
			i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
			j = (j = i.length) > 3 ? j % 3 : 0;
		return negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
	}

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
				if( num == '.'){
					objEm.addClass('ico-cdot');
				}else if(isNaN(num)){
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
	//定义获取奖池总额
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
				rollNum(formatMoney(Number(tOAmount)),oJackpot);
				function move(){
					rollNum(formatMoney(Number(tOAmount)),oJackpot);
					tOAmount = Number(tOAmount) + parseInt(Math.random()*10) + Number(Math.random().toFixed(2));
				}
				//不要做成全局的。
				setInterval(move,1000);

			},
			error: function(xml){
				//接口异常随机
				var n = parseInt( Math.random()*100000000);
				setInterval(move,2000);
				function move(){
					//getdata();
					rollNum(n,oJackpot);
					n = n+ 5;
				}
			}
		});
	}
	//获取奖池总额
	getdata();


	//————————————————backtop——————————————————
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

	//-------------banner----------------------
	var oPic=$('.banner_list');
	var oPics=$('.banner_list li');//图
	var oNums=$('.banner_num li');//焦点
	var iNow=0;
	//DOM完成执行动画事件
	oPicsLength = oPics.length;
	oPicsWidth = oPics.width();
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
	var timer=setInterval(autoRun,3000);
	oPic.hover(function(){
		clearInterval(timer);
	},function(){
		timer=setInterval(autoRun,3000);
	});

	// 点击开始游戏
	var $popLogin = $('#pop-login').proPop();
	$('.btn_start').click(function(){
		$popLogin.popshow();
	})

	var $reglink = $('.register-link');
	$reglink.click(function(){
		$popLogin.popclose();
		$("html,body").animate({scrollTop: 0},500,function(){
			$('#J-input-username').focus()
		});
		var str = '{"type":"register"}';
		var data = JSON.parse(str);
		document.getElementsByTagName('iframe')[0].contentWindow.postMessage(data, '*');
		
	})

	// 点击试玩游戏
	var $trylink = $('.btn_try');
	$trylink.click(function(){
		var code = $(this).parent().attr('data-code')
		window.open('game-trial.html?game='+code,'Games','width=1020,height=600,top=200,left=300,menubar=no,scrollbars=no,toolbar=no,location=no,directories=no,resizable=no') ;
	})

	//点击下载广告
	var $popLogin2 = $('#pop-login2').proPop();
	$('.ad_link').click(function(){
		$popLogin2.popshow();
	})
	
	$("#login-confirm2").click(function(){
		$popLogin2.popclose();
		$popLogin.popshow();
	});
	var $popLogin3 = $('#pop-login3').proPop();
	$("#login-confirm3").click(function(){
		$popLogin3.popclose();
	});
});


// 游戏列表更换
$(function(){
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

	oPics.each(function(i){
		var nPicsL = i%nCol*(nPicsWidth+nPicsMr);
		var nPicsT = Math.floor(i/nCol)*(nPicsHeight+nPicsMb);
		$(this).css('position','absolute');
		$(this).css('left',nPicsL+'px');
		$(this).css('top',nPicsT+'px');
		$(this).addClass('current');
	});

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
	
	


	//新列表生成
	function NewPic(total){
		var num = oPics.length,
		    a1 = [],
			a2 = [];
		for (var i = 0 ; i<num ;i++ ){
			a1[i] = i;
		}
		for (var j = 0 ; j<total ;j++){
			a2.push(a1.splice(Math.floor(Math.random()*a1.length) , 1));
		};

		$('.game-list-pic li').removeClass('current').stop().fadeOut(600);


		$(a2).each(function(index,element){
			oPics.eq(element).addClass('current').stop().fadeOut(600);
		})

		var oNewPics=$('.game-list-pic li.current');
		picShow(oNewPics);
	}

	$('.list_change').click(function(){
		NewPic(9);
	})

})
	$("#btn_trnslogin").click(function(){
		var str = '{"type":"login"}';
		var data = JSON.parse(str);
		
		window.parent.postMessage(data, '*');
	});
	
	var ajaxLock = false,
		$userName = $('#J-input-username'),
		$passWord = $('#J-input-password'),
		$loginParam = $('#J-loginParam'),
		$submitDom = $('#btn_login'),
		$registerLoginDom = $('#btn_login2');
	//显示消息
	showMsg = function(msg){
		var msgDom = $('#J-msg-show'),
			warningInfo = '<span class="warning"></span> '
		
		//openMsgDom();
		msgDom.html(warningInfo + msg);
	}
	$("#login-confirm").click(function(){
		//$("#login-confirm").html("登录中…");
		//setTimeout(function(){ $("#login-confirm").html("登录");},5000);
		
		var userName = $("#user-name-origin").val();
		var passWord = $("#pwd-origin").val();
		var str = '{"userName":"'+userName+'","passWord":"'+passWord+'","type":"login"}';
		var data = JSON.parse(str);
		document.getElementsByTagName('iframe')[1].contentWindow.postMessage(data, '*');
	});
	
	function login(){
		var $prent_user = $(window.parent.document).find(":text#user-name-origin").val();
		var $prent_pwd = $(window.parent.document).find("#pwd-origin").val();	
		$('#J-input-username').val($prent_user);
		$('#J-input-password').val($prent_pwd);
		$submitDom.click();
	}

	var isOpenVcode = function(){
		return $('#J-panel-vcode').css('display') != 'none' ? true : false;
	};
	//提交
	$submitDom.bind('click', function(e){
		
		
		var userName = $.trim($userName.val()),
			passWord = $.trim($passWord.val()),
			loginParam = 2,
			vVcode = $.trim($("#J-input-vcode").val());
		
		if(userName==''){
			showMsg('用户名不能为空');
			return false;
		}

		if(userName.length < 2 || userName.length > 16) {
			showMsg('用户名长度有误，请输入2-16位字符');
			return false;
		}

		if(passWord==''){
			showMsg('密码不能为空');
			return false;
		}
		//验证码
		if(isOpenVcode()){
			
			if($.trim($("#J-input-vcode").val())==''){
				showMsg('验证码不能为空');
				var str = '{"type":"showVcode"}';
				var data = JSON.parse(str);
					
				window.parent.postMessage(data, '*');
				return false;
			}
		}

		/*if(ajaxLock){
			return;
		}
		ajaxLock = true;*/
		//关闭消息提示
		/*hideMsgDom();*/
        var vUsername = $.trim(userName),vPassword = $.trim(passWord),vLoginParam = $.trim(loginParam),timer,params;
        vPassword = $.md5($.md5($.md5($.md5($.md5(vPassword))))+vLoginParam);
		//alert(vPassword);
			params = {'username':vUsername,'password':vPassword,'param':vLoginParam,'vcode':vVcode};
			
		$.ajax({
			url: '/login/login',
			type: 'POST',
			dataType: 'json',
			cache:false,
			data: params,
			beforeSend:function(){
				//timer = setTimeout(formLoading, 500);
				//isGlobalLoading = true;
				$("#btn_login").html("登录中…");
				var str1 = '{"type":"changeLogin"}';
				var data1 = JSON.parse(str1);
				
				window.parent.postMessage(data1, '*');
			}
		})
		.done(function(data) {
			$("#btn_login").html("登录");
			var str1 = '{"type":"changeLogin"}';
			var data1 = JSON.parse(str1);
			
			window.parent.postMessage(data1, '*');
			
			var url = '/index';
			if(data['errors'].length > 0){
				showMsg(data['errors'][0][1]);
				if(isOpenVcode() || (data['data'] && data['data'] > 2)){
					$('#J-panel-vcode').show();
					$("#J-input-vcode").val('');
					refreshCode();
					var str = '{"type":"showVcode"}';
					var data = JSON.parse(str);
					
					window.parent.postMessage(data, '*');
				}
				if(data['errors'][0][0] == 'password'){
					$passWord.focus();
				}
				if(data['errors'][0][0] == 'username'){
					$userName.focus();
				}
				showMsg(data['param']);
				//$loginParam.val(data['param']);
			}else{
				var loginType = 2;
				
				var str = '{"type":"closeVcode"}';
				var data = JSON.parse(str);
				window.parent.postMessage(data, '*');
				
				str = '{"type":"openTip"}';
				data = JSON.parse(str);
				window.parent.postMessage(data, '*');
				
				$.cookie('loginType', loginType, {expires: 7, path: '/'});
				url = ptgame_server;
				parent.location.href = url;
				//open(url);
				//location.reload();
			}
			
		})
		.fail(function() {
			
		})
		.always(function() {
			ajaxLock = false;
			clearTimeout(timer);
			isGlobalLoading = false;
			formLoaded();
		});
		
		e.preventDefault();
	});

	var refreshCode = function(){
		var img = $('#img-code'),src = img.attr('data-src-path');
		img.attr('src', src + '?' + Math.random());
	};
	
	
	//註冊登入
	$registerLoginDom.bind('click', function(e){
		
		
		var userName = $.trim($userName.val()),
			passWord = $.trim($passWord.val()),
			loginParam = 2,
			vVcode = $.trim($("#J-input-vcode").val());
		
		if(userName==''){
			showMsg('用户名不能为空');
			return false;
		}

		if(userName.length < 2 || userName.length > 16) {
			showMsg('用户名长度有误，请输入2-16位字符');
			return false;
		}

		if(passWord==''){
			showMsg('密码不能为空');
			return false;
		}
		//验证码
		if(isOpenVcode()){
			
			if($.trim($("#J-input-vcode").val())==''){
				showMsg('验证码不能为空');
				var str = '{"type":"showVcode"}';
				var data = JSON.parse(str);
					
				window.parent.postMessage(data, '*');
				return false;
			}
		}

		/*if(ajaxLock){
			return;
		}
		ajaxLock = true;*/
		//关闭消息提示
		/*hideMsgDom();*/
        var vUsername = $.trim(userName),vPassword = $.trim(passWord),vLoginParam = $.trim(loginParam),timer,params;
        vPassword = $.md5($.md5($.md5($.md5($.md5(vPassword))))+vLoginParam);
		//alert(vPassword);
			params = {'username':vUsername,'password':vPassword,'param':vLoginParam,'vcode':vVcode};
			
		$.ajax({
			url: '/login/login',
			type: 'POST',
			dataType: 'json',
			cache:false,
			data: params,
			beforeSend:function(){
				//timer = setTimeout(formLoading, 500);
				//isGlobalLoading = true;
				$("#btn_login").html("登录中…");
				var str1 = '{"type":"changeLogin"}';
				var data1 = JSON.parse(str1);
				
				window.parent.postMessage(data1, '*');
			}
		})
		.done(function(data) {
			$("#btn_login").html("登录");
			var str1 = '{"type":"changeLogin"}';
			var data1 = JSON.parse(str1);
			
			window.parent.postMessage(data1, '*');
			
			var url = '/index';
			if(data['errors'].length > 0){
				//showMsg(data['errors'][0][1]);
				if(isOpenVcode() || (data['data'] && data['data'] > 2)){
					//$('#J-panel-vcode').show();
					//$("#J-input-vcode").val('');
					//refreshCode();
					var str = '{"type":"login"}';
					var str2 = '{"type":"showVcode"}';
					var data = JSON.parse(str);
					var data2 = JSON.parse(str2);
					
					window.parent.postMessage(data, '*');
					window.parent.postMessage(data2, '*');
					
				}
				if(data['errors'][0][0] == 'password'){
					$passWord.focus();
				}
				if(data['errors'][0][0] == 'username'){
					$userName.focus();
				}
				showMsg(data['param']);
				//$loginParam.val(data['param']);
			}else{
				var loginType = 2;
				
				var str = '{"type":"closeVcode"}';
				var data = JSON.parse(str);
				window.parent.postMessage(data, '*');
				
				str = '{"type":"openTip"}';
				data = JSON.parse(str);
				window.parent.postMessage(data, '*');
				
				$.cookie('loginType', loginType, {expires: 7, path: '/'});
				url = ptgame_server;
				parent.location.href = url;
				//open(url);
				//location.reload();
			}
			
		})
		.fail(function() {
			
		})
		.always(function() {
			ajaxLock = false;
			clearTimeout(timer);
			isGlobalLoading = false;
			formLoaded();
		});
		
		e.preventDefault();
	});
	
$("#reg_succ").click(function(){
	$('#reg_succ').css("display","none");
	$registerLoginDom.click();
});

// ----------------注册表单------------------
$(function(){

	var username = $('#J-input-username'),password = $('#J-input-password'),password2 = $('#J-input-password2'),vcode = $('#J-input-vcode');
	var disabledCn = function(){
		this.value = this.value.replace(/[\u4E00-\u9FA5]/g, '');
	};
	var changeType = function(e){
		if(e.type == 'focus'){
			this.type = 'password';
		}else{
			this.type = 'text';
		}
	};
	var WidthCheck=function(str){
		var w = 0;
		var tempCount = 0;
		for (var i=0; i<str.length; i++) {
			var c = str.charCodeAt(i);
			//单字节加1
			if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
				w++;

			}else {
				w+=2;
			}
		}
		return w;
	}
	

	//操作后提示
	function fnDiv(obj){
		//var Idivdok = document.getElementById(obj);
		var $popLogin = $(obj).proPop();
		$popLogin.popshow();
		
		//Idivdok.style.display="block";
		//Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";
		//Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
	}

	var usernameObj = new phoenix.Input({el:username,defText:'4-16位字符'});
	var passwordObj = new phoenix.Input({el:password,defText:'6-20位字符组成'});
	var password2Obj = new phoenix.Input({el:password2,defText:'请重新输入一遍密码'});
	var vcodeObj = new phoenix.Input({el:vcode,defText:'请输入图片中的字符'});

	//username.keyup(disabledCn);
	password.keyup(disabledCn);
	password2.keyup(disabledCn);
	vcode.keyup(disabledCn);


	var testpass=function(password){
		var score = 0;
		if (password.match(/^\d+$/)){ score =1;}
		if (password.match(/^[a-zA-Z]+$/)){ score =1;}
		if (password.match(/^[!,@,#,$,%,^,&,*,?,_,~]+$/)){ score =1;}
		if ((password.match(/([!,@,#,$,%,^,&,*,?,_,~])/) && password.match(/([0-9])/)) && password.match(/[a-zA-Z]/)==null
			||(password.match(/([!,@,#,$,%,^,&,*,?,_,~])/) && password.match(/([a-zA-Z])/) && password.match(/([0-9])/)==null)
			|| (password.match(/([0-9])/) && password.match(/([a-zA-Z])/)) && password.match(/([!,@,#,$,%,^,&,*,?,_,~])/)==null)
		{
			score =2
		}
		if (password.match(/([!,@,#,$,%,^,&,*,?,_,~])/)&& password.match(/([0-9])/) && password.match(/([a-zA-Z])/) ){score =3;}
		return score;
	}


	//密码强度验证
	function checkpass(pass){
		var score = testpass(pass.val());
		var password_label = $("#password_label");
		password_label.removeAttr("class");
		if(score == 1){
			password_label.attr("class","pwd-weak");
		}else if(score == 2){
			password_label.attr("class","pwd-middle");
		}else if(score==3){
			password_label.attr("class","pwd-strong");
		}
		$("#J-div-pwdstrength").css("display","inline");
	}

	password.focus(function(){
		$(this).attr("type",'password');
		//this.type = 'password';
		password.parent().find('.check-right').hide();
		$("#J-div-pwdstrength").css("display","none");
	}).blur(function(){
		if(this.value == '' || this.value == passwordObj.defConfig.defText){
			$(this).attr("type",'text');
			//this.type = 'text';
		}
	});
	password2.focus(function(){
		//this.type = 'password';
		$(this).attr("type",'password');
		password2.parent().find('.check-right').hide();
	}).blur(function(){
		if(this.value == '' || this.value == password2Obj.defConfig.defText){
			$(this).attr("type",'text');
			//this.type = 'text';
		}
	});

	var checkUsername = function(isForm){
		var v = username.val(),isPass = false;
		if(isForm && (v == '' || v == usernameObj.defConfig.defText)){
			isPass = false;
			username.parent().find('.msg-error').html('<i class="error"></i>用户名不能为空').show();
			return isPass;
		}
		if(WidthCheck(v) < 4 || WidthCheck(v) > 16){
			isPass = false;
			username.parent().find('.msg-error').html('<i class="error"></i>长度有误，请输入4-16位字符').show();
		}else if(!(/^(?![0-9])/g).test(v)){
			isPass = false;
			username.parent().find('.msg-error').html('<i class="error"></i>用户名不能数字开头').show();
		}else if((/^\d+$/g).test(v)){
			isPass = false;
			username.parent().find('.msg-error').html('<i class="error"></i>用户名不能是纯数字').show();
		}
		else if((/[^A-Za-z0-9]/g).test(v)){
			isPass = false;
			username.parent().find('.msg-error').html('<i class="error"></i>用户名只能由字母和数字组成').show();
		}
		else{
			isPass = true;
			username.parent().find('.msg-error').hide();
		}
		if(!isPass){
			username.parent().find('.check-right').hide();
		}
		return isPass;
	};
	username.focus(function(){
		username.parent().find('.check-right').hide();
	});

	username.blur(function(){
		var v = this.value;
		if(v == '' || v == usernameObj.defConfig.defText){
			username.parent().find('.msg-error').html('<i class="error"></i>用户名不能为空').show();
			return;
		}
		if(checkUsername()){
			$.ajax({
				url:"/register/checkusername",
				dataType:'json',
				method:'post',
				data:{username:v},
				beforeSend:function(){
					username.parent().find('.check-loading').css('display', 'inline-block');
				},
				success:function(data){
					if(data['status'] == 0){
						username.parent().find('.msg-error').hide();
						username.parent().find('.check-right').show();
					}else{
						username.parent().find('.msg-error').html('<i class="error"></i>'+data['data']).show();
					}
				},
				complete:function(){
					username.parent().find('.check-loading').css('display', 'none');
				}
			});
		}
	});

	var checkPassword = function(isForm){
		var v = password.val(),isPass = false;
		if(isForm && (v == '' || v == passwordObj.defConfig.defText)){
			isPass = false;
			$("#J-div-pwdstrength").css("display","none");
			password.parent().find('.msg-error').html('<i class="error"></i>密码不能为空').show();
			return isPass;
		}
		if(v.length < 6 || v.length > 20){
			isPass = false;
			$("#J-div-pwdstrength").css("display","none");
			password.parent().find('.msg-error').html('<i class="error"></i>长度有误，请输入6-20位字符').show();
		}else if(v != '' && username.val() == v){
			isPass = false;
			$("#J-div-pwdstrength").css("display","none");
			password.parent().find('.msg-error').html('<i class="error"></i>密码不能和用户名一致').show();
		}else{
			isPass = true;
			password.parent().find('.msg-error').hide();
			password.parent().find('.check-right').show();
			checkpass(password);
		}
		if(!isPass){
			password.parent().find('.check-right').hide();
		}else{
			password.parent().find('.check-right').show();
		}
		return isPass;
	};
	password.blur(function(){
		var v = this.value;
		if(v == '' || v == passwordObj.defConfig.defText){
			password.parent().find('.msg-error').html('<i class="error"></i>密码不能为空').show();
			return;
		}
		checkPassword();
	});
	
	password.keyup(function(){
		var v = this.value;
		if(v == '' || v == passwordObj.defConfig.defText){
			password.parent().find('.msg-error').html('<i class="error"></i>密码不能为空').show();
			return;
		}
		checkPassword();
	});

	var checkPassword2 = function(isForm){
		var v = password2.val(),isPass = false;
		if(isForm && (v == '' || v == password2Obj.defConfig.defText)){
			isPass = false;
			password2.parent().find('.msg-error').html('<i class="error"></i>密码不能为空').show();
			return isPass;
		}
		if(v != password.val()){
			isPass = false;
			password2.parent().find('.msg-error').html('<i class="error"></i>两次输入的密码不一致').show();
		}else{
			isPass = true;
			password2.parent().find('.msg-error').hide();
		}
		if(!isPass){
			password2.parent().find('.check-right').hide();
		}else{
			password2.parent().find('.check-right').show();
		}
		return isPass;
	};
	password2.blur(function(){
		var v = this.value;
		if(v == '' || v == password2Obj.defConfig.defText){
			password2.parent().find('.msg-error').html('<i class="error"></i>密码不能为空').show();
			return;
		}
		checkPassword2();
	});

	var checkVcode = function(isForm){
		var v = vcode.val(),isPass = false;
		if(isForm && (v == '' || v == vcodeObj.defConfig.defText)){
			isPass = false;
			vcode.parent().find('.msg-error').html('<i class="error"></i>验证码不能为空').show();
			return isPass;
		}
		else{
			isPass = true;
			vcode.parent().find('.msg-error').hide();
		}
		return isPass;
	};
	/*var refreshCode = function(){
		var img = $('#img-code'),src = img.attr('data-src-path');
		img.attr('src', src + '?' + Math.random());
	};*/
	var verifyCode = function()
	{
		var v = vcode.val();
		if(v == '' || v == vcodeObj.defConfig.defText){
			vcode.parent().find('.msg-error').html('<i class="error"></i>验证码不能为空').show();
			return;
		}
		if(checkVcode()){
			$.ajax({
				url:"/register/checkvcode",
				type:'POST',
				dataType:'json',
				data:{vcode:v},
				beforeSend:function(){
					vcode.parent().find('.check-loading').css('display', 'inline-block');
				},
				success:function(data){
					if(data['status'] == "ok"){
						vcode.parent().find('.check-right').show();
						submitForm();
					}else{
						vcode.parent().find('.msg-error').html('<i class="error"></i>'+data["data"]).show();
						refreshCode();
						vcode.focus();
					}
				},
				complete:function(){
					vcode.parent().find('.check-loading').css('display', 'none');
				}
			});
		}
	};

	vcode.focus(function(){
		vcode.parent().find('.check-right').hide();
	}).blur(function(){
		var v = vcode.val();
		if(v == '' || v == vcodeObj.defConfig.defText){
			vcode.parent().find('.msg-error').html('<i class="error"></i>验证码不能为空').show();
			return;
		}
	});

	var submitForm=function()
	{
		var params;
		if(isOpenVcode()){
			if(!checkVcode()){
				return;
			}
			params = {'username':username.val(),'password1':password.val(),'password2':password2.val(),
				'vcode':vcode.val(),'device':'web','pt':'y'};
		}else{
			params = {'username':username.val(),'password1':password.val(),'password2':password2.val(),'device':'web','pt':'y'};
		}

		/*if(checkUsername(true) && checkPassword(true) && checkPassword2(true) && checkVcode(true)){*/
		/*var sdata="username="+username.val()+"&password1="+password.val()+"&password2="+password2.val()+"&vcode"+vcode.val();*/
		$.ajax({
			url:"/register/conform2/",
			dataType:'json',
			method:'post',
			data:params,
			beforeSend:function(jqXHR, settings){
				//RSA加密
				//settings.data = 'rsa_data=' + rsa.encrypt(settings.data) + '&uniq_id=' + uniq_id;
				
				$("#J-button-submit").attr("disabled","true");
				$("#txt_reg").html("注册中…");
			},
			success:function(data){
				$("#txt_reg").html("提交注册");
				if(data["status"]=="ok")
				{
					fnDiv('#reg_succ');
					var parent_url = parent.document.location.href;
					if(parent_url.indexOf("param=c") != -1){
						$("#reg_succ").css("margin-top","-125px");
						$("#reg_succ").css("height","235px");
					}else{
						$("#reg_succ").css("margin-top","-145px");
					}
					
					
					//setTimeout(function(){ $('#reg_succ').css("display","none");$submitDom.click();},3000);

				}else
				{
					if(isOpenVcode() || (data['dataCnt'] && data['dataCnt'] > 2)){
						$('#J-panel-vcode').show();
						vcode.val('');
						refreshCode();
					}
					if(data['type']=='1')
					{
						username.parent().find('.check-right').hide();
						username.parent().find('.msg-error').html('<i class="error"></i>'+data['data']).show();
						fnDiv('DivFailed');
						setTimeout(function(){ $('#DivFailed').css("display","none");},3000);
					}else if(data['type']=='2')
					{
						password.parent().find('.check-right').hide();
						password.parent().find('.msg-error').html('<i class="error"></i>'+data['data']).show();
						fnDiv('DivFailed');
						setTimeout(function(){ $('#DivFailed').css("display","none");},3000);

					}else if(data['type']=='3')
					{
						vcode.parent().find('.check-right').hide();
						vcode.parent().find('.msg-error').html('<i class="error"></i>'+data['data']).show();
						fnDiv('DivFailed');
						setTimeout(function(){ $('#DivFailed').css("display","none");},3000);
					}else if(data['type']=='4')
					{
						fnDiv('DivURLFailed');
						setTimeout(function(){ $('#DivURLFailed').css("display","none");},3000);
					} else if(data['type'] == '6'){
						$('#DivCusFailed').html('<i class="ico-error"></i><h4 class="pop-text">'+data['data']+'</h4>');
						fnDiv('DivCusFailed');
						setTimeout(function(){ $('#DivCusFailed').css("display","none");},3000);
					} else {
						fnDiv('DivFailed');
						setTimeout(function(){ $('#DivFailed').css("display","none");},3000);
					}
				}
			},
			complete:function(){
				$("#J-button-submit").removeAttr("disabled");
			}
		});
	};


	$('#J-button-submit').click(function(){
		checkUsername(true);
		checkPassword(true);
		checkPassword2(true);
		if(checkUsername(true) && checkPassword(true) && checkPassword2(true)){
			if(isOpenVcode()){
				verifyCode();
			}else{
				submitForm();
			}
		}
	});




})
