<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
<!-- /////////////头部公共页面/////////////// -->	
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">人工资金管理</a> &gt; <a href="#">现金打款</a> &gt;  发起打款</div></div>
			<div class="col-content">
				<div class="col-main">
               		<form method="POST" action="/admin/Fundsmanage/currenttransfer?swithval=7" id="fm_main"  name="fm_main" >
					<div class="main">
						<h3 class="ui-title">发起打款申请</h3>
						<ul class="ui-form">
							<li>
								<label class="ui-label">打款类型：</label>
								<select name="selav" class="ui-select">
									<option value = "1" >活动派奖</option>
									<option value = "2">人工提现</option>
								</select>
							</li>
							<li>
									<label class="ui-label" for="">用户名：</label>
                                     <input type="text" class="input w-4" id="userName" name="username" value="">
									<div class="ui-check"><i class="error"></i><span id="usernames">用户名不能为空（4-16位）</span></div>
								</li>
							<li class="ui-btn">
								<a  href="#" class="btn btn-important" id="J_submit1">下一步<b class="btn-inner"></b></a>
								<a href="/admin/Fundsmanage/currenttransfer" class="btn">返 回<b class="btn-inner"></b></a>
							</li>
						</ul>
					</div>
                   </form>
				</div>
			</div>
		</div>
	</div>
{include file='/admin/script-base.tpl'}
{literal}	
<script>	
	(function() {
		 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuWithdrawalsConfig');
		
		var form = $('#fm_main'),userName,userNamePar,istrue="0",
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
		//用户名验,判断是否已存在
		userName = $('#userName');
		userNamePar = userName.parent();
		userName.blur(function(){
			var v = $.trim(this.value);			
			if(v!='' && v.length>=4 && v.length<=16){			
				$.ajax({
					type:'post',
					dataType:'json',
					cache:false,
					url:'/admin/proxy/topisset',			
					data:'username='+v,					
					success:function(data){								
						if(data['isSuccess']=="1")					
						{					
							userNamePar.find('.ui-check').css('display', 'none');	setErrorNum('userName', -1);
							istrue="2";					
						}
						else
						{
							userNamePar.find('.ui-check').css('display', 'inline');	
							$('#usernames').html('用户名不存在，请重新输入');
							setErrorNum('userName', 1);	
							istrue="1";	
						}
					}
				});
			}
			else if(v==''||v.length<=4 || v.length>=16 ){	userNamePar.find('.ui-check').css('display', 'inline');	setErrorNum('userName', 1);	}
			else{	userNamePar.find('.ui-check').css('display', 'none');	setErrorNum('userName', -1);}			
		});
		
		//下一步验证
		$('#J_submit1').click(function(){
			form.submit();			
		});
		
		//表单提交校验
		form.submit(function(e){	
			var err = 0;		
			$.each(errorTypes, function(){
			if(typeof errorHas[this] != 'undefined'){
				err += errorHas[this];
			}
			});
			if(err > 0){ 	return false;	}
			if(userName[0].value==''){ userNamePar.find('.ui-check').css('display', 'inline'); return false;}
			if(istrue!="2" ){ 
				e.preventDefault();
				return false;				
			}			
			return true;
		});
		
    })();	
</script>
{/literal}
</body>
</html>
</body>
</html>