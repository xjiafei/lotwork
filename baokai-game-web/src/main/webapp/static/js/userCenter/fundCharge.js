(function () {
	
	//支付宝账户名
	bankNumber = $('#bankNumber');
	bankNumberPar = bankNumber.parent();	

	bankNumber.blur(function(){
		var v = $.trim(this.value),isture=false;
		var emailmatch = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z]+$/;
		var numen =  /^(?=.*[0-9])(?=.*[a-zA-Z])$/;
		var en =  /^[a-zA-Z]+$/;
		var onlynumen = /^.[A-Za-z0-9]+$/;
		var onlynum = /^[0-9]*$/;
		var onlyen = /^[a-zA-Z]*$/;
		var at = v.split("@");
		var checkCom = false;
		for(i=0;i<at.length;i++){
			if(i==1){
				checkCom = ((at[i]).toUpperCase().indexOf(".COM")<0) ;
			}
		}
		if(v==''){
			bankNumberPar.find('.ui-check').html("<i class='error'></i>支付宝账户名不可为空").removeClass().addClass('ui-check').show();
			setErrorNum('bankNumber', 1);
				
		}else if(onlynum.test(v) & countLength(v)!=11){
			
		   bankNumberPar.find('.ui-check').html("<i class='error'></i>手机号码格式不正确，请重新输入").removeClass().addClass('ui-check').show();
		   setErrorNum('bankNumber', 1);			
		}else if( onlynumen.test(v) &!onlyen.test(v) &!onlynum.test(v)){
			bankNumberPar.find('.ui-check').html("<i class='error'></i>仅支持手机或邮箱输入").removeClass().addClass('ui-check').show();
			setErrorNum('bankNumber', 1);
		}else if( (!emailmatch.test(v) &!onlynum.test(v)) | checkCom ){
		    bankNumberPar.find('.ui-check').html("<i class='error'></i>邮箱格式不正确，请重新输入").removeClass().addClass('ui-check').show();
			setErrorNum('bankNumber', 1);			
		}else{		
			bankNumberPar.find('.ui-check').html("").removeClass().addClass('ui-check ui-check-ok').show();
		    setErrorNum('bankNumber', -1);
			
		}	
		
		 $('#bankNumberQ').hide();		
	}).focus(function(){
		var position = $('#bankNumberQimg').position(); 
		var bnqh = $('#bankNumberQimg').height();
		var bnh = $('#bankNumberQ').height();
		var x = position.left+20;  
		var y = position.top-(bnh/2)-(bnqh/2);  
		 $('#bankNumberQ').show().css('top',y).css('left',x);
		/*var position = $('#bankNumber').position();  
		var x = position.left+260;  
		var y = position.top-25;  
	    //bankNumberPar.find('.ui-check').html("请输入常用的手机或邮箱支付宝账户").removeClass().addClass('ui-check ui-check-tip').show();
		bankNumberPar.find('.ui-check').html(""
				+"&nbsp"+
				
				"<a href='#' class='tooltip'>"+
				"<img src='"+$('#path_img').val()+"/images/help/question-mark.png' style='vertical-align:middle;' id='question' />"
			
	
       +"<div class='tooltiptext' style='font-size:12px;top:"+y+"px;left:"+x+"px;'>"
       +"<b style=color:red;>如何填写正确的账号：</b>"
       +"<p style=>请您登录“支付宝手机应用”，</p>" 
       +"<p>如果您使用<span style='color:blue;'>【手机号】</span>登录成功，请输入手机号。</p>"
       +"<p>如果您使用<span style='color:blue;'>【邮箱地址】</span>登录成功，请输入完整的邮箱地址。</p>"
    
       +"</div>"
      
				
						
						
		).show().removeClass().addClass('ui-check ui-check-tip').show();
		setErrorNum('bankNumber', -1);*/
	});
	
	$("#bankNumberQimg").hover(
			function() {
				$('#bankNumDemo').show();

			  }, function() {

				  $('#bankNumDemo').hide();

			  }	
	);
	$("#bankAccountQimg").hover(
			function() {
				$('#bankAccountDemo').show();

			  }, function() {

				  $('#bankAccountDemo').hide();

			  }	
	);
	
	 $('[name="bankNumber"]').bind('keyup', function () {
	       
    		var v = $.trim(this.value)
    		if((/^[0-9]*$/.test(v) & countLength(v)>11)){
    			$(this).val(v.substr(0,11))
    		}

	 });
	 
	 var dblength =70;
	 $('[name="bankAccount"]').bind('keyup', function () {
		 	var v = $.trim(this.value)
		 	if(countLength(v)>dblength){ //避免每次都重新放入到text，ie會Focus到最後一位
		 		checkcut(v);
		 	}
	 	function checkcut(v){
	 		if(countLength(v)>dblength){
	 			v = cut(v);
	 			checkcut(v);
	 		}else{
	 			$("#bankAccount").val(v);
	 		}
	 	}

		function cut(v){
			v = v.substr(0,v.length-1);
			return v;
		 		
		}

	 });
	
	 
	//绑定支付宝姓名
		bankAccount = $('#bankAccount');
		bankAccountPar = bankAccount.parent();	
	 
		bankAccount.blur(function(){
			var v = $.trim(this.value);	
			if(v == ''){		
			    bankAccountPar.find('.ui-check').html("<i class='error'></i>支付宝姓名不可为空").removeClass().addClass('ui-check').show();
				//staticPrompt(bankAccountPar,'bankAccount');	
				setErrorNum('bankAccount', 1);
			}
			/*else if(countLength(v)< 4 || countLength(v) > 30)
			{
					
				bankAccountPar.find('.ui-check').html("<i class='error'></i>长度支持4~30字符").removeClass().addClass('ui-check');
				setErrorNum('bankAccount', 1);
			}else if(!/^[\u4E00-\u9FFF]{2,15}$|^[a-zA-Z]{4,30}$|^[\u4E00-\u9FFF]+(·[\u4E00-\u9FFF]+)+$|^[a-zA-Z]+( [a-zA-Z]+)+$/.test(v)){
			
					
				bankAccountPar.find('.ui-check').html("<i class='error'></i>支付宝姓名格式不正确，请重新输入").removeClass().addClass('ui-check');
				setErrorNum('bankAccount', 1);
			}*/else{
				bankAccountPar.find('.ui-check').html("").removeClass().addClass('ui-check ui-check-ok').show();
			    setErrorNum('bankAccount', -1);	
				//staticPromptDe(bankAccountPar,'bankAccount');
			}		
			$('#bankAccountQ').hide();
		}).focus(function(){
			var position = $('#bankAccountQimg').position();  //放大鏡位置
			var baqh = $('#bankAccountQimg').height();//放大鏡高
			var bah = $('#bankAccountQ').height();//提示框高

			var x = position.left+20;  
			var y = position.top-(bah/2)-(baqh/2);  ;  
			 $('#bankAccountQ').show().css('top',y).css('left',x);
			//bankAccountPar.find('.ui-check').html("请输入您绑定的支付宝姓名").show().removeClass().addClass('ui-check ui-check-tip').show();
			/*var position = $('#bankAccount').position();  
			var x = position.left+260;  
			var y = position.top-49;  
			bankAccountPar.find('.ui-check').html(""
					+"&nbsp"+
					
					"<a href='#' class='tooltip'>"+
					"<img src='"+$('#path_img').val()+"/images/help/question-mark.png' style='vertical-align:middle;' id='question' />"
				
		
	       +"<div class='tooltiptext' style='font-size:12px;top:"+y+"px;left:"+x+"px;'>"
	       +"<b style=color:red;>如何确认您填入的【昵称】是否正确？</b>"
	       +"<p style=>请您登录“支付宝手机应用”，</p>" +
	       		"<p>点击手机右下角进入<span style='color:blue;'>【我的】</span>个人资料，查看<span style='color:blue;'>【账户详情】</span>。</p>"
	       +"<p>如您【昵称】栏，有显示个人昵称，请在平台填写正确昵称。</p>"
	       +"<p>如您【昵称】栏，显示“未设置”，</p>" +
	       		"<p>表明您的支付宝账号无昵称，此时可不用填写。</p>"
	       +"</div>"
	      
					
							
							
			).show().removeClass().addClass('ui-check ui-check-tip').show();
			
			
			
			setErrorNum('bankAccount', -1);*/
		});		
	 
	 
	//支付宝昵称
		nickName = $('#nickName');
		nickNamePar = nickName.parent();	
		
		nickName.blur(function(){
			var v = $.trim(this.value);	
			
				
			
			if(v != '' && countLength(v)>20){
					
			    nickNamePar.find('.ui-check').html("<i class='error'></i>最大可输入20位字符").removeClass().addClass('ui-check');	
				setErrorNum('nickName', 1);
			}else if(v != ''){
				nickNamePar.find('.ui-check').html("").removeClass().addClass('ui-check ui-check-ok');	
				setErrorNum('nickName', -1);
			}else{
				nickNamePar.find('.ui-check').html("");
				setErrorNum('nickName', -1);
			}
					
		}).focus(function(){
			
			var position = $('#nickName').position();  
			var x = position.left+460;  
			var y = position.top-49;  
			
			nickNamePar.find('.ui-check').html("您的支付宝昵称，若未设置请不要填写"+"&nbsp"+
					
					"<a href='#' class='tooltip'>"+
					"<img src='"+$('#path_img').val()+"/images/help/question-mark.png' style='vertical-align:middle;' id='question' />"
				
		
           +"<div class='tooltiptext' style='font-size:12px;top:"+y+"px;left:"+x+"px;'>"
           +"<b style=color:red;>如何确认您填入的【昵称】是否正确？</b>"
           +"<p style=>请您登录“支付宝手机应用”，</p>" +
           		"<p>点击手机右下角进入<span style='color:blue;'>【我的】</span>个人资料，查看<span style='color:blue;'>【账户详情】</span>。</p>"
           +"<p>如您【昵称】栏，有显示个人昵称，请在平台填写正确昵称。</p>"
           +"<p>如您【昵称】栏，显示“未设置”，</p>" +
           		"<p>表明您的支付宝账号无昵称，此时可不用填写。</p>"
           +"</div>"
          
					
							
							
			).show().removeClass().addClass('ui-check ui-check-tip').show();
			setErrorNum('nickName', -1);
			
		
		});	
	
	function countLength(stringToCount)  
 	{ 
   
    var c = stringToCount.match(/[^ -~]/g);  
    return stringToCount.length + (c ? c.length*2 : 0);  //原字串的長度 再加一次中文長度*2，中文進DB 是3格
 	}
	
	
	setErrorNum = function(name, num){
		if(typeof errorHas[name] != 'undefined'){
			errorHas[name] += num;
			errorHas[name] = errorHas[name] < 0 ? 0 : (errorHas[name] > 1 ? 1 : errorHas[name]);
		}
	};
	
	//表单检测错误数量
	errorTypes = ['bankAccount','bankNumber','nickName','securityPassword'];
	errorHas = {};
	
	$.each(errorTypes, function(){
		errorHas[this] = 0;
	});	
	
	
})();
