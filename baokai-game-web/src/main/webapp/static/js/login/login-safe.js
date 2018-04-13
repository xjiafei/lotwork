(function($){
	var $userName = $('#J-user-name'),
		$cycles = $('#about_new_plat_layer').fadeOut(0),
		$shadow = $('#migrate_overlay_shadow').fadeOut(0),
		$close = $cycles .find('.overlay_close'),
		$cycleLayer = $cycles.find('.plat_layer_content > img'),
		$passWord = $('#J-user-password'),
		$loginParam = $('#J-loginParam'),
		$userInfoDom = $('#J-user-info'),
		$submitDom = $('#J-form-submit'),
		ajaxLock = false,
		ajaxURL = '/login/login',
		step = 'safe-step-1';

	$cycles.find('.plat_layer_tabs a').on('click', function(){
   		$(this).parent().find('a').removeClass('active');
   		$(this).addClass('active');
   		$cycleLayer.fadeOut().eq(Number($(this).text())-1).fadeIn();;
   });

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
	
	var isOpenVcode = function(){
		return $('#J-user-var').length >0?true : false;
	};
	var refreshCode = function(){
		var img = $('#J-vcode-img'),src = img.attr('data-src-path');
		img.attr('src', src + '?' + Math.random());
	};
	
	var getRedirectParams = function(){
	    var url = location.search,pattern = "redirect=",str = "";
	    if (url.indexOf(pattern) != -1) {
			var str = url.substr(url.indexOf(pattern)+pattern.length);
		}
		return str;
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
	}

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
	
	$.ajax({
		url:'/api/jsonp/getAdverts?u=-1&k=index_pos_login&r='+Math.random(),
		cache:false,
		dataType:'jsonp',
		jsonp: "callBack",
		success:function(data){
			if(Number(data['isSuccess']) == 1){
				var me = data,list = me.data,len = list.length,listLen,dom,width,height,tplSingle,html = '';
				if(len > 0){
					for(var i=0;i<len;i++)
					{
						if(list[i]['name']=='index_pos_login')
						{
							html = '';
							width=500;
							height=288;
							dom = $('#focus');
							tplSingle='<div class="item"><a target="_blank" href="<#=link#>"><img src="<#=src#>" title="<#=title#>" alt="<#=title#>"></a></div>'
							if(dom.size() > 0){
								listLen = list[i]['list'].length;
								if(listLen > 0){
									dom.css({width:width});
									for(var j=0;j < listLen;j++){
										ad = reBuildAd(tplSingle,list[i]['list'][j],width,height);
										ad['index'] = (j + 1);
										html = phoenix.util.template(tplSingle, ad);
										dom.cycle('add',html);
									}
									
								}else{
		dom.cycle('add','<div class="item"><a target="_blank" href="/index"><img src="'+global_path_url+'/images/login/new-login/banner.jpg" alt=""></a></div>');
								}
							}
						}
					}
				}
			}else{
				$('#focus').cycle('add','<div class="item"><a target="_blank" href="/index"><img src="'+global_path_url+'/images/login/new-login/banner.jpg" alt=""></a></div>');
				
			}
		},
		error:function(xhr, type){
			$('#focus').cycle('add','<div class="item"><a target="_blank" href="/index"><img src="'+global_path_url+'/images/login/new-login/banner.jpg" alt=""></a></div>');
		}
	   });	

	//提交
	$submitDom.bind('click', function(e){
		var userName = $.trim($userName.val()),
			passWord = $.trim($passWord.val()),
			loginParam = $.trim($loginParam.val()),
			$verification = $('#J-verification');

		if(step == 'safe-step-1' && $userName.size() > 0 && userName==''){
			showMsg('用户名不能为空');
			return false;
		}

		if(step == 'safe-step-1' && $userName.size() > 0  && (userName.length < 2 || userName.length > 16)) {
			showMsg('用户名长度有误，请输入2-16位字符');
			return false;
		}

		if(step == 'safe-step-2' && $passWord.size() > 0 && passWord==''){
			showMsg('密码不能为空');
			return false;
		}

		//验证码
		if($verification.size() > 0){
			if($.trim($verification.val())==''){
				showMsg('验证码不能为空');
				return false;
			}
		}

		if(ajaxLock){
			return;
		}
		ajaxLock = true;

		//关闭消息提示
		hideMsgDom();
		var params={};
        if(isOpenVcode()){
				params = {'username':userName,'vcode':$verification.val(),'safemod':1};
			}else{
				passWord = $.md5($.md5($.md5($.md5($.md5(passWord))))+loginParam);
				params = {'password':passWord,'param':loginParam};
			}
		$.ajax({
			url: ajaxURL,
			type: 'POST',
			dataType: 'json', 
			cache:false,
			data: params
		})
		.done(function(r) {
			if(r['errors'].length > 0){
				$loginParam.val(r['param']);
				if(isOpenVcode())
				{
					showMsg(r['errors'][0][1]);
					if( r['data'] && r['data'] > 2){
						$('#J-panel-vcode').show();
						$verification.val('');
						refreshCode();
					}
					if(r['errors'][0][0] == 'password'){
						$passWord.focus();
					}
					if(r['errors'][0][0] == 'username'){
						$userName.focus();
					}
				}else{
					showMsg(r['errors'][0][1]);
					var url = '/login/showsectlogin/';
					url = getRedirectParams() == '' ? url : getRedirectParams();
					location.href = url;
				}
			}else{
				if(isOpenVcode())
				{
					$('.choose-game').show();
					if($.cookie('loginType') !=null){
						$('.choose-game input[name="choose-game"][value='+$.cookie("loginType")+']').attr("checked",'checked');
					}
	               var message=r['data'].cipher!= null ? '您设置的预留信息为：<span class="safe-code">'+r['data'].cipher + '</span>':'您尚未设置预留验证信息，请登录成功后前往安全中心设置!';
				   var message2=r['data'].cipher!= null ? '<p>若“预留验证信息”与您设置不一致！请勿输入密码！</p>':"";
				   var html = '<div class="user-code">'+
		 					  '<p>'+message+'</p>'+
							      message2+
						       '</div>';

					step = 'safe-step-2';
					ajaxURL='/login/sectlogin';
					$('#J-password-area').show();
					$('#J-login-area').prepend(html);
					$('#J-username-area').addClass('hide');
					$('#J-user-var').remove();
					$('#J-form-submit').val('登 录');
				}else
				{
					var url = '/index';
					var loginType = $('.choose-game input[name="choose-game"]:checked ').val();
					$.cookie('loginType', loginType, {expires: 7, path: '/'});
					if(loginType =='2'){
						url = ptgame_server;
					}
					url = getRedirectParams() == '' ? url : getRedirectParams();
					location.href = url;
				}
			}
		})
		.fail(function() {
			
		})
		.always(function() {
            if(isOpenVcode())
			{
				ajaxURL='/login/login';
			}else
			{
				ajaxURL='/login/sectlogin';
			}
			ajaxLock = false;
		});
		
		e.preventDefault();
	});
     
})(jQuery);