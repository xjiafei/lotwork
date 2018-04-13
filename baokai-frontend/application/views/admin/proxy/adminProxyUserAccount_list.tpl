<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
		<!-- //////////////头部公共页面////////////// -->
			{include file='/admin/left.tpl'}
		<!-- /////////////头部公共页面/////////////// -->		
	<form method="POST" id="fm_main"  name="fm_main" >
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/user/">用户中心</a> &gt; 总代管理 </div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
								<!-- {if "USER_MANAGE_TOPAGENT_LIST"|in_array:$smarty.session.datas.info.acls} -->
									<li><a href="/admin/proxy/index">总代列表</a></li>
								<!-- {/if} -->
									<li class="current">总代开户</li>
								</ul>
							</div>
							<ul class="ui-form">
								<li>
									<label class="ui-label" for="">用户名：</label>
                                     <input type="text" class="input w-4" id="userName" name="username" value="">
									<div class="ui-check"><i class="error"></i><span id="usernames">用户名不能为空（4-16位）</span></div>
								</li>
								<li>
									<label class="ui-label" for="">登录密码：</label>
                                     <input type="password"  name="password" class="input w-4" id="txtPwd" value="">
									<div class="ui-check"><i class="error"></i>登录密码不能为空（6-12位）</div>
								</li>
								<li>
									<label class="ui-label" for="">开户配额：</label>
                                    <input type="text" class="input w-4" name="accountnum" id="txtAccount" value=""> （此处的开户配额指的是总代开一代和玩家的配额）
									<div class="ui-check"><i class="error"></i>开户配额不能为空,不能为小数</div>
								</li>
<!--								<li>
									<label class="ui-label" for="">账户类型：</label>
									<label for="rr1" class="label"><input type="radio" name="usertype"  class="radio" id="rr1"  checked="checked" value="0">正式账号</label>
									<label for="rr2" class="label"><input type="radio" name="usertype"  class="raoTest" id="rr2"  value="1" style=" margin-left:10px">测试账号</label>
									
	
								</li>-->
								<li class="ui-btn">
									<a class="btn btn-important" href="javascript:void(0);" id="J-button-submit">开 户<b class="btn-inner"></b></a>
									<a class="btn" href="javascript:void(0);" id="J-button-clean">取 消<b class="btn-inner"></b></a>
								</li>
							</ul>
						</div>
						<div class="pop w-4" style="position:absolute;left:100px; display:none" id="divOk">
							<i class="ico-success"></i>
							<h4 class="pop-text">开户成功</h4>
						</div>
						<div class="pop w-4" style="position:absolute;left:400px; display:none" id="divNo">
							<i class="ico-error"></i>
							<h4 class="pop-text">开户失败，请重试</h4>
						</div>

						
					</div>
				</div>
			</div>
		</div>
	</form>
	</div>
{include file='/admin/script-base.tpl'}
{literal}
<script > 
	(function($){	
		//一、二级菜单选中样式加载	
		selectMenu('MenuUser','MenuUsermanage');
		
		var inputs = $('.ui-form').find('#txtAccount'),checkFn,txtName;	
		//数字校验，自动矫正不符合数学规范的数(可增可减，最小值为0，最大值无限制，不支持小数点)	
		checkFn = function(){
			var me = this,v = me.value,index;
			me.value = v = v.replace(/\D/g, '');
		};
		
		inputs.keyup(checkFn);
		inputs.blur(function()
		{
			 var me=this,
			 v=$.trim(me.value);
			 if(v=="")
			 return;
			 me.value = v = parseInt(v);
	    });
		
		//弹窗居中显示层
		function fn(obj){
			var Idivdok = document.getElementById(obj);	
			Idivdok.style.display="block";			
			Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
			Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";	
   		} 	
		
		function codefans(){	$("#divOk").css("display","none");	}
		
		var form = $('#fm_main'),userName,userNamePar,txtPwd,txtPwdPar,txtAccount,txtAccountPar,
		//表单检测错误数量
		errorTypes = ['userName','txtPwd','txtAccount'],
		errorHas = {},
		setErrorNum = function(name, num){
			if(typeof errorHas[name] != 'undefined'){
				errorHas[name] += num;
				errorHas[name] = errorHas[name] < 0 ? 0 : (errorHas[name] > 1 ? 1 : errorHas[name]);
			}
		};
		$.each(errorTypes, function(){
			errorHas[this] = 0;
		});		
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
		
		//用户名验,判断是否已存在
		userName = $('#userName');
		userNamePar = userName.parent();
		userName.blur(function(){
			var v = $.trim(this.value);
		    if(v==''|| WidthCheck(v)<4 || WidthCheck(v)>16 )
			{	
			    userNamePar.find('.ui-check').html('<i class="error"></i>用户名不能为空（4-16位）').show();
				userNamePar.find('.ui-check').css('display', 'inline');	
				setErrorNum('userName', 1);	
			}else if((/^\d+$/g).test(v)){
				userNamePar.find('.ui-check').html('<i class="error"></i>用户名不能是纯数字').show();
				userNamePar.find('.ui-check').css('display', 'inline');	
				setErrorNum('userName', 1);	
			}
			else if((/[^A-Za-z0-9\u4E00-\u9FA5]/g).test(v)){
				userNamePar.find('.ui-check').html('<i class="error"></i>用户名只能由中文、字母和数字组成').show();
				userNamePar.find('.ui-check').css('display', 'inline');	
				setErrorNum('userName', 1);	
			}else{			
				$.ajax({
					type:'post',
					dataType:'json',
					cache:false,
					url:'/admin/proxy/topisset',			
					data:'username='+v,					
					success:function(data){								
						if(data['isSuccess']=="0")					
						{					
							userNamePar.find('.ui-check').html('<i class="error"></i>'+data['data']).show();
							userNamePar.find('.ui-check').css('display', 'inline');
							setErrorNum('userName', 1);					
						}
						else
						{
							userNamePar.find('.ui-check').css('display', 'none');	
							setErrorNum('userName', -1);
						}
					}
				});
			}
			
		});
		
		
		//证密码验证	
		txtPwd = $('#txtPwd');
		txtPwdPar = txtPwd.parent();
		txtPwd.blur(function(){
			var v = $.trim(this.value);			
			if(v=='' || txtPwd.val().length<6 || txtPwd.val().length>12)
			{
				txtPwdPar.find('.ui-check').html('<i class="error"></i>登录密码不能为空（6-12位）').show();
				txtPwdPar.find('.ui-check').css('display', 'inline');
				setErrorNum('txtPwd', 1);
			}
			else if(v != '' && userName.val() == v)
			{
				txtPwdPar.find('.ui-check').html('<i class="error"></i>密码不能和用户名一致').show();
				txtPwdPar.find('.ui-check').css('display', 'inline');
				setErrorNum('txtPwd', 1);
			}
			else{
				txtPwdPar.find('.ui-check').css('display', 'none');
				setErrorNum('txtPwd', -1);
			}
			
		});	
		
		//分配额度
		txtAccount = $('#txtAccount');
		txtAccountPar = txtAccount.parent();
		txtAccount.blur(function(){
			var v = $.trim(this.value);			
			if(v==''){
				txtAccountPar.find('.ui-check').css('display', 'inline');
				setErrorNum('txtAccount', 1);
			}
			else{
				txtAccountPar.find('.ui-check').css('display', 'none');
				setErrorNum('txtAccount', -1);
			}
			
		});
		
		//提交开户
		$('#J-button-submit').click(function(e){
			
			if(userName[0].value=='')
			{ 
			     userNamePar.find('.ui-check').css('display', 'inline'); 
				 setErrorNum('userName', 1);		 
				 e.preventDefault();
			}
			if(txtPwd[0].value=='')
			{ 
			     txtPwdPar.find('.ui-check').css('display', 'inline'); 
				 setErrorNum('txtPwd', 1);
				 e.preventDefault(); 
		    }
			if(txtAccount[0].value=='')
			{ 
			     txtAccountPar.find('.ui-check').css('display', 'inline'); 
				 setErrorNum('txtAccount', 1);
				 e.preventDefault(); 
		    }
			var err = 0;		
			$.each(errorTypes, function(){
			if(typeof errorHas[this] != 'undefined'){
				err += errorHas[this];
			}
			});
			if(err > 0){ 
				e.preventDefault();	
				return false;	
			}
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'/admin/proxy/account',			
				data:'account='+userName[0].value+'&passwd='+txtPwd[0].value+'&agentLimit='+txtAccount[0].value,					
				success:function(data){					
					var result = eval(data);
					if(result['isSuccess']=="1"){					
						fn("divOk");	
						setTimeout(function(){  window.location.href='/admin/proxy/index';},2000);
					}else{	
						$("#divNo h4").html(result['data']);
						fn("divNo");	
						setTimeout(function(){$("#divNo").css("display","none");},2000);				
						$('#divS').css("display","none");
					}
				}
			});
		});
		
		//取消清空文本框
		$('#J-button-clean').click( function(){
			$("#fm_main")[0].reset();
			//window.location.href='/admin/proxy/index';
		});
	})(jQuery);
</script>
{/literal}
</body>
</html>