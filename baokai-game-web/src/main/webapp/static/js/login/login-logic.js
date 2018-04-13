
;(function($){
	var ajaxLock = false,
	    isGlobalLoading = false,
		$cycles = $('#about_new_plat_layer').fadeOut(0),
		$shadow = $('#migrate_overlay_shadow').fadeOut(0),
		$close = $cycles .find('.overlay_close'),
		$cycleLayer = $cycles.find('.plat_layer_content > img'),
		$userName = $('#J-user-name'),
		$passWord = $('#J-user-password'),
		$loginParam = $('#J-loginParam'),
	    mask = phoenix.Mask.getInstance(),
		$submitDom = $('#J-form-submit');
   
   mask.css({'opacity':0});
   $cycles.find('.plat_layer_tabs a').on('click', function(){
   		$(this).parent().find('a').removeClass('active');
   		$(this).addClass('active');
   		$cycleLayer.fadeOut().eq(Number($(this).text())-1).fadeIn();;
   });
  if($.cookie('loginType') !=null){
		$('.choose-game input[name="choose-game"][value='+$.cookie("loginType")+']').attr("checked",'checked');
	}
    //  禁止复制和黏贴文本框中的内容 
	$("input:password").bind("copy cut paste",function(e){ 
      return false; 
    });

    var getRedirectParams = function(){
	    var url = location.search,pattern = "redirect=",str = "";
	    if (url.indexOf(pattern) != -1) {
			var str = url.substr(url.indexOf(pattern)+pattern.length);
		}
		return str;
	};

     var formLoading = function(){
		var textArr = ['加载中   ', '加载中.  ', '加载中.. ', '加载中...'],i = 0;
		isGlobalLoading = true;
		mask.show($submitDom);
		clearInterval(formLoading.timer);
		formLoading.timer = setInterval(function(){
			$submitDom.val(textArr[i]);
			i = i >= textArr.length - 1 ? 0 : i + 1;
		}, 200);
	};
	var formLoaded = function(){
		var text = '登 录';
		clearInterval(formLoading.timer);
		isGlobalLoading = false;
		$submitDom.val(text);
		mask.hide();
	};
	
	var isOpenVcode = function(){
		return $('#J-panel-vcode').css('display') != 'none' ? true : false;
	};
	var refreshCode = function(){
		var img = $('#J-vcode-img'),src = img.attr('data-src-path');
		img.attr('src', src + '?' + Math.random());
	};
	
	var checkVcode = function(){
		var dom = $('#J-verification'),v = $.trim(dom.val()),isPass = false;
		if(!isOpenVcode()){
			return true;
		}
		if(v == ''){
			isPass = false;
			showMsg('验证码不能为空');
		}else if(!(/[A-Za-z0-9]{4}/).test(v)){
			isPass = false;
			showMsg('验证码格式不正确');
		}else{
			isPass = true;
		}
		if(isPass){
			//关闭消息提示
		    hideMsgDom();
		}else{
			dom.focus();
		}
		return isPass;
	}; 
	 
	
    //显示弹层
    showDialog = function(){
    	
    	if($cycles.is(':hidden') && $shadow.is(':hidden')){
    		$cycles.show();
    		$shadow.show(); 
    	}
    };

    hideDialog = function(){

    	if(!$cycles.is(':hidden') && !$shadow.is(':hidden')){
    		$cycles.hide();
    		$shadow.hide(); 
    	}
    };

    $('#J-question-button').click(function(event) {
    	showDialog();
    });

    $(document).click(function(event) {
    	var $dom = $(event.target);
    	if($dom.is($cycles) || $cycles.has($dom).size()>0 || $dom.is($('#J-question-button'))){
    		return;
    	}
    	hideDialog();
    });

    $close.click(function(event) {
    	hideDialog();
    });


	//检查用户信息
	checkInfo = function(dom){
		var $dom = $(dom),
			name = $(dom).attr('data-name'),
			$tipsDom = $('.clear-' + name),
			text = $.trim($dom.val());

		if(text){
			$tipsDom.show();
		} else {
			$tipsDom.hide();
		}
	};

	$('.clear-password, .clear-name').click(function(event) {
		var mark = $(this).attr('data-name');

		$('#J-user-' + mark).val('');
		$(this).hide();

		if(mark == 'password'){
			$('.forget-password').css('right', 10);
		}
	});

	//用户名称
	$userName.bind('input propertychange', function(){
		checkInfo(this);
	});

	//用户密码
	$passWord.bind('input propertychange', function(){
		checkInfo(this);
	});

	$passWord.focus(function(event) {
		$('.forget-password').css('right', -200);
	})
	.blur(function(){
		if($passWord.val()){
			return;
		}
		$('.forget-password').css('right', 10);
	});

	//显示消息
	showMsg = function(msg){
		var msgDom = $('#J-msg-show'),
			warningInfo = '<span class="warning"></span> '

		openMsgDom();
		msgDom.html(warningInfo + msg);
	}

	//关闭消息显示
	hideMsgDom = function(){
		var msgDom = $('#J-msg-show');

		msgDom.css('height', 0).attr('data-display', 'hide').html('');
	}

	//关闭消息显示
	openMsgDom = function(){
		var msgDom = $('#J-msg-show'),
			status = msgDom.attr('data-display');
		
		if(status == 'hide'){
			msgDom.css('height', 20).attr('data-display', 'show');
		}
	}
	
	
		//处理广告数据
		var reBuildAd=function(tpl,ad,w,h){
			 var me = tpl;
				ad['link'] = reBuildLink(ad['id'], ad['link']);
				ad['src'] = ad['src'];
				ad['width'] = w;
				ad['height'] = h;
			return ad;
		}
	   var reBuildLink=function(id, link){
			return link;
		}
	
	$('#focus').html('<div class="item"><a target="_blank" href="http://mobile.ios188.com:6060/mobileApp/index.html"><img src="'+global_path_url+'/images/login/new-login/login_banner.jpg" alt=""></a></div>');
			

	//提交
	$submitDom.bind('click', function(e){
		var userName = $.trim($userName.val()),
			passWord = $.trim($passWord.val()),
			loginParam = $.trim($loginParam.val()),
			$verification = $('#J-verification');

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
			if($.trim($verification.val())==''){
				showMsg('验证码不能为空');
				return false;
			}
		}

		if(ajaxLock){
			return false;
		}
		ajaxLock = true;
		//关闭消息提示
		hideMsgDom();
        var vUsername = $.trim(userName),vPassword = $.trim(passWord),vLoginParam = $.trim(loginParam),vVcode,timer,params;
        vPassword = $.md5($.md5($.md5($.md5($.md5(vPassword))))+vLoginParam);
		//alert(vPassword);
        	if(isOpenVcode()){
				if(!checkVcode()){
					return;
				}
				vVcode = $.trim($verification.val()).toUpperCase();
				params = {'username':vUsername,'password':vPassword,'param':vLoginParam,'vcode':vVcode};
			}else{
				params = {'username':vUsername,'password':vPassword,'param':vLoginParam};
			}
		$.ajax({
			url: '/login/login',
			type: 'POST',
			dataType: 'json',
			cache:false,
			data: params,
			beforeSend:function(){
				timer = setTimeout(formLoading, 500);
				isGlobalLoading = true;
			}
		})
		.done(function(data) {
			var url = '/index';
			if(data['errors'].length > 0){
				showMsg(data['errors'][0][1]);
				if(isOpenVcode() || (data['data'] && data['data'] > 2)){
					$('#J-panel-vcode').show();
					$verification.val('');
					refreshCode();
				}
				if(data['errors'][0][0] == 'password'){
					$passWord.focus();
				}
				if(data['errors'][0][0] == 'username'){
					$userName.focus();
				}
				$loginParam.val(data['param']);
			}else{
				var loginType = $('.choose-game input[name="choose-game"]:checked ').val();
				$.cookie('loginType', loginType, {expires: 7, path: '/'});
				if(loginType =='2'){
					url = ptgame_server;
				}
				url = getRedirectParams() == '' ? url : getRedirectParams();
				location.href = url;
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

})(jQuery);