

//验证类

//ajax校验不支持表单提交校验

(function(host, name, Event, $, undefined){
	var defConfig = {
		//提示成功或失败时提示容器的样式
		tipClsSussess:'.ui-validator-success',
		tipClsFail:'.ui-validator-fail',
		//提示容器
		tip:'.ui-validator-tip',
		isMust:true,
		//需要验证的类型列表，如 password , vcode
		type:'',
		//是否自动添加blur事件进行校验
		autoBlur:true
	},
	WidthCheck=function(str){  
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
	},
	fns = {
		'commonEmpty':function(v){
			var me = this,isSuccess = true,messageKey = 'msg_success',v = me.getValue();
			if(v == ''){
				isSuccess = false;
				messageKey = 'empty_error_type';
			}
			return {
				'isSuccess':isSuccess,
				'messageKey':messageKey
			};
		},
		'username':function(v){
			var me = this,isSuccess = true,messageKey = 'msg_success',v = me.getValue();
			if(WidthCheck(v) < 4 || WidthCheck(v) > 16){
				isSuccess = false;
				messageKey = 'username_error_length';
			}else if((/^\d+$/g).test(v)){
				isSuccess = false;
				messageKey = 'username_error_number';
				
			}else if((/[^A-Za-z0-9\u4E00-\u9FA5]/g).test(v)){
				isSuccess = false;
				messageKey = 'username_error_type';
			}
			return {
				'isSuccess':isSuccess,
				'messageKey':messageKey
			};
		},
		'usernameAdmin':function(v){
			var me = this,isSuccess = true,messageKey = 'msg_success',v = me.getValue();
			if(v=="" || v=="请输入用户名")
			{
				isSuccess = false;
				messageKey = 'usernameAdmin_empty';
			}
			if( WidthCheck(v) < 3 || WidthCheck(v) > 16){
				isSuccess = false;
				messageKey = 'usernameAdmin_error_length';
			}else if((/^\d+$/g).test(v)){
				isSuccess = false;
				messageKey = 'username_error_number';
				
			}else if((/[^A-Za-z0-9\u4E00-\u9FA5]/g).test(v)){
				isSuccess = false;
				messageKey = 'username_error_type';
			}
			return {
				'isSuccess':isSuccess,
				'messageKey':messageKey
			};
		},
		'password':function(v){
			var me = this,isSuccess = true,messageKey = 'msg_success',v = me.getValue();
			var length = v.length;
			if(v=="" || v=="********")
			{
				isSuccess = false;
				messageKey = 'passwordAdmin_error_empty';
			}else if(length<6 || length>20){				
				isSuccess = false;
				messageKey = 'password_error_type';
			}			
			
			return {
				'isSuccess':isSuccess,
				'messageKey':messageKey
			};			
			
		},		
		'safepassword':function(v){
			var me = this,isSuccess = true,messageKey = 'msg_success',v = me.getValue();
			var length = v.length;
			//if(!(/[a-zA-Z0-9!@#$%^&*()_+]{6,20}/).test(v)){		
			if(length==0){
				isSuccess = false;
				messageKey = 'safepassword_error_type';				
			}
			else{
				if(length<6 || length>20){				
					isSuccess = false;
					messageKey = 'safepassword1_error_type';
				}				
			}
			return {
				'isSuccess':isSuccess,
				'messageKey':messageKey
			};			
			
		},		
		'safepassword2':function(v){
			var me = this,isSuccess = true,messageKey = 'msg_success',v = me.getValue();
			var length = v.length;
			//if(!(/[a-zA-Z0-9!@#$%^&*()_+]{6,20}/).test(v)){		
			if(length==0){
				isSuccess = false;
				messageKey = 'safepassword2_error_type';				
			}
			else{
				if(length<6 || length>20){				
					isSuccess = false;
					messageKey = 'safepassword1_error_type';
				}				
			}
			return {
				'isSuccess':isSuccess,
				'messageKey':messageKey
			};			
			
		},
		'vcode':function(v){
			var me = this,isSuccess = true,messageKey = 'msg_success',v = me.getValue();
			if(!(/^[0-9a-zA-Z]{4}$/g).test(v)){
				isSuccess = false;
				messageKey = 'vcode_error_type';
			}
			return {
				'isSuccess':isSuccess,
				'messageKey':messageKey
			};
		},
		'email':function(v){
			var me = this,isSuccess = true,messageKey = 'msg_success',v = me.getValue();
			if(!(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/).test(v) || v.length <6 || v.length>50){
				isSuccess = false;
				messageKey = 'email_error_type';
			}
			return {
				'isSuccess':isSuccess,
				'messageKey':messageKey
			};
		},
		'safeAnswer':function(v){
			var me = this,isSuccess = true,messageKey = 'msg_success',v = me.getValue();
			//将中文转换为2位英文计算
			v = v.replace(/[^\x00-\xff]/g, 'xx');
			if(v==''){
				isSuccess = false;
				messageKey = 'safeAnswer1_error_type';				
			}
			else{
				if(v.length < 4 || v.length >16)
				{
					isSuccess = false;
					messageKey = 'safeAnswer_error_type';
				}else if(!(/^[0-9a-zA-Z]{4,16}$/g).test(v))
				{
					isSuccess = false;
					messageKey = 'safeAnswer2_error_type';
				}				
				
			}
			return {
				'isSuccess':isSuccess,
				'messageKey':messageKey
			};
		},
		//预留信息
		'slogan':function(v){
			var me = this,isSuccess = true,messageKey = 'msg_success',v = me.getValue();
			//将中文转换为2位英文计算
			v = v.replace(/[^\x00-\xff]/g, 'xx');
			if(v == ''){
				isSuccess = false;
				messageKey = 'slogan_error_empty';
			}else if(v.length < 4 || v.length > 16){
				isSuccess = false;
				messageKey = 'slogan_error_length';
			}else if(!(/^[a-zA-Z]{4,16}$/g).test(v)){
				isSuccess = false;
				messageKey = 'slogan_error_type';
			}
			return {
				'isSuccess':isSuccess,
				'messageKey':messageKey
			};
		},
		//动态密码
		'bindpwd':function(v){
			var me = this,isSuccess = true,messageKey = 'msg_success',v = me.getValue();
			if($.trim(v)=="" || $.trim(v)=="请输入动态码")
			{
				isSuccess = false;
				messageKey = 'bindpwdAdmin_error_type';
			}else if(!(/^([0-9]{6}|[0-9]{8})$/).test(v)){
				isSuccess = false;
				messageKey = 'bindpwd_error_type';
			}
			return {
				'isSuccess':isSuccess,
				'messageKey':messageKey
			};
		}
	},
	msgs = {
		'empty_error_type':'输入不能为空',
		'msg_success':'验证正确',
		'username_error_length':'长度有误，请输入4-16位字符',
		'usernameAdmin_empty':'请输入用户名',
		'usernameAdmin_error_length':'长度有误，请输入3-16位字符',
		'username_error_type':'用户名只能由中文、字母和数字组成',
		'username_error_number':'用户名不能是纯数字',
		'passwordAdmin_error_empty':'请输入密码',
		'password_error_type':'长度有误，请输入6-20位字符',
		'password_error_empty':'请输入',
		'password1_error_type':'请输入密码',
		'safepassword_error_type':'请输入安全密码',
		'safepassword1_error_type':'长度有误，请输入6-20位字符',
		'safepassword2_error_type':'请输入确认安全密码',
		'vcode_error_type':'验证码格式必须是4位的字母或数字',
		/*'safeAnswer_error_type':'安全问题必须是4-16位中文或字母',*/
		'safeAnswer_error_type':'长度有误，请输入4-16位字符',
		'safeAnswer1_error_type':'请输入答案',
		'safeAnswer2_error_type':'格式错误，仅支持中文、字母、数字',
		'email_error_type':'电子邮箱格式不正确',
		'slogan_error_empty':'请输入预留验证信息',
		'slogan_error_type':'格式错误，仅支持中文、字母',
		'slogan_error_length':'长度有误，请输入2-8位中文或4-8位英文',
		'bindpwdAdmin_error_type':'请输入密码卡密码',
		'bindpwd_error_type':'动态码应为6位或8位数字'
	},
	addValidator = function(name, fn){
		fns[name] = fn;
	},
	addMsg = function(name, msg){
		msgs[name] = msg;
	};
	
	
	
	var pros = {
		init:function(cfg){
			var me = this;
			me.dom = $(cfg.el);
			me.validated = false;
			if(cfg.autoBlur){
				me.dom.blur(function(){
					me.validate();
				});
			}
		},
		//获取值
		getValue:function(){
			var me = this,v = me.dom.val();
			if(me.dom.attr('data-ui-defvalue') && me.dom.attr('data-ui-defvalue') == v){
				v = '';
			}
			return $.trim(v);
		},
		//获取提示容器
		getTip:function(){
			var me = this,tip = me.dom.parent().parent().find(me.defConfig.tip);
			return tip.size() > 0 ? tip : $(me.defConfig.tipEl);
		},
		validate:function(){
			var me = this;
			me.check(me.defConfig.type);
		},
		check:function(type){
			var me = this,result = true,ajaxCfg,ajaxMethods;
			if(fns[type]){
				result = fns[type].call(me, type);
				if(result['isSuccess']){
					if(me.defConfig.ajax){
						
						ajaxMethods = {
							success:function(data){
								if(data['success']){
									me.showSuccessMessage(data['message']);
									me.setValidated(true);
								}else{
									me.showErrorMessage(data['message']);
									me.setValidated(false);
								}
							}
						};
						ajaxCfg = $.extend({}, me.defConfig.ajax, ajaxMethods);
						$.ajax(ajaxCfg);
					}else{
						me.showSuccessMessage(me.getMessage('msg_success'));
						me.setValidated(true);
					}
					
				}else{
					me.showErrorMessage(me.getMessage(result['messageKey']));
					me.setValidated(false);
				}
			}
		},
		getMessage:function(key){
			return msgs[key];
		},
		setValidated:function(isValidated){
			var me = this;
			me.validated = isValidated;
		},
		showSuccessMessage:function(msg){
			var me = this,fCls = me.defConfig['tipClsFail'],sCls = me.defConfig['tipClsSussess'];
			me.getTip().removeClass(fCls).addClass(sCls).hide();
		},
		showErrorMessage:function(msg){
			var me = this,fCls = me.defConfig['tipClsFail'],sCls = me.defConfig['tipClsSussess'];
			me.getTip().html(msg).removeClass(sCls).addClass(fCls).show();
		}
	};

	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
	host[name] = Main;
	host[name]['addValidator'] = addValidator;
	host[name]['addMsg'] = addMsg;


})(phoenix, "Validator", phoenix.Event, jQuery);







