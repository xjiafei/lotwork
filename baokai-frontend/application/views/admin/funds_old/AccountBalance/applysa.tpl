<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><a href="/admin/Fundsmanage/accountbalance?swithval=5" class="more">返回上一步</a><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">人工资金管理</a> &gt; <a href="#">账户余额加减</a> &gt; <a href="#">加减申请</a> &gt;<span id="applytype">xxxx</span></div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title">加减申请</h3>
                      
                        <div id="div_1">
                         <form action="" method="POST" id="J-form1">
                            <ul class="ui-form">
                                <li>
                                    <label class="ui-label">类型：</label>
                                    <span class="ui-text-info">活动加币</span>
                                </li>
                                <li>
                                    <label class="ui-label">用户名：</label>
                                    <input type="text" class="input w-4" value=""  id="userName1"/>
                                    <div class="ui-check"><i class="error"></i><span id="usernames">请输入用户名(4-16位)</span></div>
                                    <div id="userinfo" style="display:none"><span class='user-level' id="userinfo2">用户层级：</span></div>                                    
                                     <!--<span class='color-green'>242354356</span> &gt; <span>thtrhft</span> &gt; <span>hgtdhthtf</span> &gt; <span class="color-red">vava</span></span>-->
                                </li>
                                <li>
                                    <label class="ui-label">金额：</label>
                                    <input type="text" class="input w-4" value="" id="usermoneny1" name="inmoney">
                                     <div class="ui-text-prompt"><span class="ui-text-prompt">小数点后最多为两位</span></div>
                                     <div class="ui-check"><i class="error"></i><span id="usernames">请输入小数点后最多为两位</span></div>
                                </li>
                                <li>
                                    <label class="ui-label">备注：</label>
                                    <div class="textarea w-4">
                                        <textarea  id="userInfo1"   maxlength="50" ></textarea>                                        
                                    </div>
                                    <span class="ui-info vertical-bottom">(<span name='spancount'>0</span>/50) 可为空</span>
                                </li>
                                <li class="ui-btn">
                                    <a href="javascript:void(0);" class="btn btn-important" id="J_submit1">提 交<b class="btn-inner"></b></a>
                                    <a href="/admin/Fundsmanage/accountbalance?swithval=5" class="btn">上一步<b class="btn-inner"></b></a>
                                </li>
                            </ul>
                          </form>
                         </div>
                      
                      
                        <div id="div_2">
                          <form action="" method="POST" id="J-form2">
						<ul class="ui-form">
							<li>
								<label class="ui-label">类型：</label>
								<span class="ui-text-info">人工扣减</span>
							</li>
							<li>
								<label class="ui-label">用户名：</label>
								<input type="text" class="input w-4" value="" id='userName2'/>
								<span class="ui-check"><i></i>用户名不存在,请输入正确用户名(4-16位)</span>
							</li>
							<li>
								<label class="ui-label">金额：</label>
								<input type="text" class="input w-4" value="" id="usermoneny2" name="inmoney">
								<div class="ui-text-prompt"><span class="ui-text-prompt">小数点后最多为两位</span></div>
                                <div class="ui-check"><i class="error"></i><span >请输入小数点后最多为两位</span></div>
							</li>
							<li>
								<label class="ui-label">备注：</label>
								<div class="textarea w-4">
									 <textarea  id="userInfo2"   maxlength="50" ></textarea>       
								</div>
								<span class="ui-info vertical-bottom">(<span name='spancount'>0</span>/50) 可为空</span>
							</li>
							<li class="ui-btn">
								<a href="javascript:void(0);" class="btn btn-important" id="J_submit2">提 交<b class="btn-inner"></b></a>
								<a href="/admin/Fundsmanage/accountbalance?swithval=5" class="btn">上一步<b class="btn-inner"></b></a>
							</li>
						</ul>
                        </form>
                        </div>
                       <div id="div_3">
                          <form action="" method="POST" id="J-form3">
                            <ul class="ui-form">
                                <li>
                                    <label class="ui-label">类型：</label>
                                    <span class="ui-text-info">理赔加币</span>
                                </li>
                                <li>
                                    <label class="ui-label">用户名：</label>
                                    <input type="text" class="input w-4" value="" id='userName3'/>
                                    <span class="ui-check"><i></i>用户名不存在</span>
                                </li>
                                <li>
                                    <label class="ui-label">金额：</label>
                                    <input type="text" class="input w-4" value=""  id="usermoneny3" name="inmoney">
                                    <div class="ui-text-prompt"><span class="ui-text-prompt">小数点后最多为两位</span></div>
                                    <div class="ui-check"><i class="error"></i><span >请输入金额，(小数点后最多为两位)</span></div>
                                </li>
                                <li>
                                    <label class="ui-label">备注：</label>
                                    <div class="textarea w-4">
                                        <textarea  id="userInfo3"   maxlength="50" ></textarea>       
                                    </div>
                                    <span class="ui-info vertical-bottom">(<span name='spancount'>0</span>/50) 可为空</span>
                                </li>
                                <li class="ui-btn">
                                    <a href="javascript:void(0);" class="btn btn-important" id="J_submit3">提 交<b class="btn-inner"></b></a>
                                    <a href="/admin/Fundsmanage/accountbalance?swithval=5" class="btn">上一步<b class="btn-inner"></b></a>
                                </li>
                            </ul>
                        </form>
                        </div>
					</div>
				</div>
			</div>
		</div>
	</div>
{include file='/admin/script-base.tpl'}
{literal}	
<script>
	
	
(function() {
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuFundConfig');
	
	var arg = GetRequest();
	//获取url中"?"符后的字串
	function GetRequest() {
		var url = location.search;   
		var json = {};
		if (url.indexOf("?") != -1) {
			var str = url.substr(1);
			strs = str.split("&");
			for (var i = 0; i < strs.length; i++) {
				json[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
			}
		}
		return json;
	}

	//数字校验，自动矫正不符合数学规范的数学(小数点保留两位)
	var inputs = $('[name="inmoney"]'),checkFn;				
	checkFn = function(){
		var me = this,v = me.value,index;
		me.value = v = v.replace(/^\.$/g, '');			
		index = v.indexOf('.');
		if(index > 0){
			me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');	
			if(v.substring(index,v.length).length>3){					
				me.value = v=v.substring(0,v.indexOf(".")+3);					
			}			
		}
		me.value = v = v.replace(/[^\d|^\.]/g, '');
		me.value = v = v.replace(/^00/g, '0');		
		if(v.split('.').length > 2){
			arguments.callee.call(me);
		}					
	};		
	inputs.keyup(checkFn);
	inputs.blur(checkFn);
	if (arg != undefined) {
		var aid = arg["label"];
		if (aid != undefined) {	
			//活动加币操作		
			if(aid=="1"){						
				$('#div_2').remove();	
				$('#div_3').remove();	
				$('#applytype').html(" 活动加币");
				ActivityEvents();			
			}
			//人工扣减操作
			else if(aid=="2"){	
				$('#div_1').remove();				
				$('#div_3').remove();
				$('#applytype').html(" 人工扣减");
				Artificialdeduct();
				
			}
			// 理赔加币操作
			else if(aid=="3"){	
				$('#div_1').remove();		
				$('#div_2').remove();					
				$('#applytype').html(" 理赔加币");
				Claimscanadian();
			}
			else{					
				$('#div_1').remove();		
				$('#div_2').remove();		
				$('#div_3').remove();	
				$('#applytype').html("");
				alert("无效访问");
				window.location.href='/admin/Fundsmanage/accountbalance?swithval=5';
			}
		}
	}

	//1:活动加币验证及提交方法
	function ActivityEvents(){
		var form = $('#J-form1'),userName1,userName1Par,isture="0",	
		//表单检测错误数量
		errorTypes = ['userName1','usermoneny1'],
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
		//表单检测
		checkform = function(){
			if($('#userName1')[0].value=='' || $('#userName1')[0].value.length<=4 || $('#userName1')[0].value.length>=16){
				userName1Par.find('.ui-check').css('display', 'inline');	
				$('#usernames').html('用户名不存在!');
				setErrorNum('userName1', 1);	
			}
			if($.trim($('#usermoneny1')[0].value)=='')
			{
				inmoneyPar.find('.ui-check').css('display', 'inline');
				inmoneyPar.find('.ui-text-prompt').css('display', 'none');
				setErrorNum('inmoney', 1);
			}			
		}		
		//备注已输入字符长度动态
		$('#userInfo1').keyup(function() {
		 $('[name="spancount"]').html($('#userInfo1')[0].value.length);
		});		
		//用户名验,判断是否已存在
		userName1 = $('#userName1');
		userName1Par = userName1.parent();
		userName1.blur(function(){
			var v = $.trim(this.value);
			$('#userinfo2').html("").removeClass();
			if(v!='' && v.length>=4 && v.length<=16){			
				$.ajax({
					type:'post',
					dataType:'json',
					cache:false,
					url:'/admin/proxy/topisset',			
					data:'username='+v,					
					success:function(data){								
						if(data['isSuccess']=="1"){	
							//展现关系层	
							userName1Par.find('.ui-check').css('display', 'none');	setErrorNum('userName1', -1);					
							$('#userinfo').css("display","inline");							
							$('#userinfo2').append("用户层级:2222").addClass("user-level");	
							isture="1";						
						}
						else{
							userName1Par.find('.ui-check').css('display', 'inline');	
							$('#usernames').html('用户名不存在!');
							setErrorNum('userName1', 1);								
						}
					}
				});
			}
			else if(v==''||v.length<=4 || v.length>=16 ){	userName1Par.find('.ui-check').css('display', 'inline');	setErrorNum('userName1', 1);	}
			else{	userName1Par.find('.ui-check').css('display', 'none');	setErrorNum('userName1', -1);}			
		});		
		//金额验证
		inmoney = $('[name="inmoney"]');
		inmoneyPar = inmoney.parent();	
		inmoney.blur(function(){
			var v = $.trim(this.value);
			if(v == ''){			
				inmoneyPar.find('.ui-check').css('display', 'inline');	
				inmoneyPar.find('.ui-text-prompt').css('display', 'none');
				setErrorNum('inmoney', 1);
			}else{				
				inmoneyPar.find('.ui-check').css('display', 'none');
				setErrorNum('inmoney', -1);
			}			
		}).focus(function(){
			inmoneyPar.find('.ui-check').css('display', 'none');
			inmoneyPar.find('.ui-text-prompt').css('display', 'inline');
		});
		//备注验证
		inmoney = $('[name="inmoney"]');
		inmoneyPar = inmoney.parent();	
		inmoney.blur(function(){
			var v = $.trim(this.value);
			if(v == ''){			
				inmoneyPar.find('.ui-check').css('display', 'inline');	
				inmoneyPar.find('.ui-text-prompt').css('display', 'none');
				setErrorNum('inmoney', 1);
			}else{				
				inmoneyPar.find('.ui-check').css('display', 'none');
				setErrorNum('inmoney', -1);
			}			
		}).focus(function(){
			inmoneyPar.find('.ui-check').css('display', 'none');
			inmoneyPar.find('.ui-text-prompt').css('display', 'inline');
		});
		
		$('#J_submit1').click(function(){
			form.submit();
		});		
		//表单提交校验
		form.submit(function(e){			
			var err = 0;
			checkform();
			$.each(errorTypes, function(){
				if(typeof errorHas[this] != 'undefined'){
					err += errorHas[this];
				}
			});	
			if(err > 0 || isture != "1"){
				e.preventDefault();
				return false;
			}
			return true;
		});
		
	}
	//,2:人工扣减验证及事件
	function Artificialdeduct()
	{
		var form = $('#J-form2'),userName2,userName2Par,isture="0",
		//表单检测
		checkform = function(){
			if($('#userName2')[0].value=='' || $('#userName2')[0].value.length<=4 || $('#userName2')[0].value.length>=16){
				userName2Par.find('.ui-check').css('display', 'inline');	
				$('#usernames').html('用户名不存在!');
				setErrorNum('userName2', 1);	
			}
			if($.trim($('#usermoneny2')[0].value)=='')
			{
				inmoneyPar.find('.ui-check').css('display', 'inline');
				inmoneyPar.find('.ui-text-prompt').css('display', 'none');
				setErrorNum('inmoney', 1);
			}			
		}
		//表单检测错误数量
		errorTypes = ['userName2','usermoneny2'],
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
		//备注已输入字符长度动态
		$('#userInfo2').keyup(function() {
		 $('[name="spancount"]').html($('#userInfo2')[0].value.length);
		});	
			
		//用户名验,判断是否已存在
		userName2 = $('#userName2');
		userName2Par = userName2.parent();
		userName2.blur(function(){
			var v = $.trim(this.value);
			$('#userinfo2').html("").removeClass();
			if(v!='' && v.length>=4 && v.length<=16){			
				$.ajax({
					type:'post',
					dataType:'json',
					cache:false,
					url:'/admin/proxy/topisset',			
					data:'username='+v,					
					success:function(data){								
						if(data['isSuccess']=="1"){	
							//展现关系层	
							userName2Par.find('.ui-check').css('display', 'none');	setErrorNum('userName2', -1);					
							$('#userinfo').css("display","inline");							
							$('#userinfo2').append("用户层级:2222").addClass("user-level");	
							isture="1";						
						}
						else{
							userName2Par.find('.ui-check').css('display', 'inline');	
							$('#usernames').html('用户名不存在!');
							setErrorNum('userName2', 1);	
							
						}
					}
				});
			}
			else if(v==''||v.length<=4 || v.length>=16 ){	userName2Par.find('.ui-check').css('display', 'inline');	setErrorNum('userName2', 1);	}
			else{	userName2Par.find('.ui-check').css('display', 'none');	setErrorNum('userName2', -1);}			
		});		
		//金额验证
		inmoney = $('[name="inmoney"]');
		inmoneyPar = inmoney.parent();	
		inmoney.blur(function(){
			var v = $.trim(this.value);
			if(v == ''){			
				inmoneyPar.find('.ui-check').css('display', 'inline');	
				inmoneyPar.find('.ui-text-prompt').css('display', 'none');
				setErrorNum('inmoney', 1);
			}else{				
				inmoneyPar.find('.ui-check').css('display', 'none');
				setErrorNum('inmoney', -1);
			}			
		}).focus(function(){
			inmoneyPar.find('.ui-check').css('display', 'none');
			inmoneyPar.find('.ui-text-prompt').css('display', 'inline');
		});
		//备注验证
		inmoney = $('[name="inmoney"]');
		inmoneyPar = inmoney.parent();	
		inmoney.blur(function(){
			var v = $.trim(this.value);
			if(v == ''){			
				inmoneyPar.find('.ui-check').css('display', 'inline');	
				inmoneyPar.find('.ui-text-prompt').css('display', 'none');
				setErrorNum('inmoney', 1);
			}else{				
				inmoneyPar.find('.ui-check').css('display', 'none');
				setErrorNum('inmoney', -1);
			}			
		}).focus(function(){
			inmoneyPar.find('.ui-check').css('display', 'none');
			inmoneyPar.find('.ui-text-prompt').css('display', 'inline');
		});
		
		$('#J_submit2').click(function(){
			form.submit();
		});		
		//表单提交校验
		form.submit(function(e){			
			var err = 0;
			checkform();
			$.each(errorTypes, function(){
				if(typeof errorHas[this] != 'undefined'){
					err += errorHas[this];
				}
			});	
			if(err > 0 || isture != "1"){
				e.preventDefault();
				return false;
			}
			return true;
		});
	}
	//,3:理赔加币验证及事件
	function Claimscanadian()
	{
		var form = $('#J-form3'),userName3,userName3Par,isture="0",
		//表单检测
		checkform = function(){
			if($('#userName3')[0].value=='' || $('#userName3')[0].value.length<=4 || $('#userName3')[0].value.length>=16){
				userName3Par.find('.ui-check').css('display', 'inline');	
				$('#usernames').html('用户名不存在!');
				setErrorNum('userName3', 1);	
			}
			if($.trim($('#usermoneny3')[0].value)=='')
			{
				inmoneyPar.find('.ui-check').css('display', 'inline');
				inmoneyPar.find('.ui-text-prompt').css('display', 'none');
				setErrorNum('inmoney', 1);
			}			
		}
		//表单检测错误数量
		errorTypes = ['userName3','usermoneny2'],
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
		//备注已输入字符长度动态
		$('#userInfo3').keyup(function() {
		 $('[name="spancount"]').html($('#userInfo3')[0].value.length);
		});	
			
		//用户名验,判断是否已存在
		userName3 = $('#userName3');
		userName3Par = userName3.parent();
		userName3.blur(function(){
			var v = $.trim(this.value);
			$('#userinfo2').html("").removeClass();
			if(v!='' && v.length>=4 && v.length<=16){			
				$.ajax({
					type:'post',
					dataType:'json',
					cache:false,
					url:'/admin/proxy/topisset',			
					data:'username='+v,					
					success:function(data){								
						if(data['isSuccess']=="1"){	
							//展现关系层	
							userName3Par.find('.ui-check').css('display', 'none');	setErrorNum('userName3', -1);					
							$('#userinfo').css("display","inline");							
							$('#userinfo2').append("用户层级:2222").addClass("user-level");	
							isture="1";						
						}
						else{
							userName3Par.find('.ui-check').css('display', 'inline');	
							$('#usernames').html('用户名不存在!');
							setErrorNum('userName3', 1);	
							
						}
					}
				});
			}
			else if(v==''||v.length<=4 || v.length>=16 ){	userName3Par.find('.ui-check').css('display', 'inline');	setErrorNum('userName3', 1);	}
			else{	userName3Par.find('.ui-check').css('display', 'none');	setErrorNum('userName3', -1);}			
		});		
		//金额验证
		inmoney = $('[name="inmoney"]');
		inmoneyPar = inmoney.parent();	
		inmoney.blur(function(){
			var v = $.trim(this.value);
			if(v == ''){			
				inmoneyPar.find('.ui-check').css('display', 'inline');	
				inmoneyPar.find('.ui-text-prompt').css('display', 'none');
				setErrorNum('inmoney', 1);
			}else{				
				inmoneyPar.find('.ui-check').css('display', 'none');
				setErrorNum('inmoney', -1);
			}			
		}).focus(function(){
			inmoneyPar.find('.ui-check').css('display', 'none');
			inmoneyPar.find('.ui-text-prompt').css('display', 'inline');
		});
		
		
		$('#J_submit3').click(function(){
			form.submit();
		});		
		//表单提交校验
		form.submit(function(e){			
			var err = 0;
			checkform();
			$.each(errorTypes, function(){
				if(typeof errorHas[this] != 'undefined'){
					err += errorHas[this];
				}
			});	
			if(err > 0 || isture != "1"){
				e.preventDefault();
				return false;
			}
			return true;
		});
	}
	
})();	
</script>
{/literal}
</body>
</html>
</body>
</html>