<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置:</strong><a href="#">资金</a> &gt; <a href="#">人工资金管理</a> &gt; <a href="#">现金打款</a> &gt;  打款申请</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
                   	 <form method="POST" action="/admin/Fundsmanage/index?parma=applying"  id="fm_main"  name="fm_main" >
						<h3 class="ui-title">发起打款申请</h3>
						
						{if $selav == "1"}
						<ul class="ui-form">
							<li>
								<label class="ui-label">打款类型：</label>
								<span class="ui-text-info">活动派奖</span>
							</li>
							<li>
								<label class="ui-label">用户名：</label>
								<span class="ui-text-info">{$username}</span>
								<span class="user-level">用户层级： <span class="color-green">242354356</span> &gt; <span>thtrhft</span> &gt; <span>hgtdhthtf</span> &gt; <span class="color-red">vava</span></span>
							</li>
							<li>
								<label class="ui-label">选择银行卡：</label>
								<div class="select-position">
									<table class="table table-info">
										<tbody>
											<tr>
												<td class="text-left"><label class="label"><input type="radio" class="radio" name="radion_bank">招商银行&nbsp;&nbsp;&nbsp;张*&nbsp;&nbsp;&nbsp;**** **** **** 3400</label></td>
											</tr>
											<tr>
												<td class="text-left"><label class="label"><input type="radio" class="radio" name="radion_bank">招商银行&nbsp;&nbsp;&nbsp;张*&nbsp;&nbsp;&nbsp;**** **** **** 3400</label></td>
											</tr>
											<tr>
												<td class="text-left"><label class="label"><input type="radio" class="radio" name="radion_bank">招商银行&nbsp;&nbsp;&nbsp;张*&nbsp;&nbsp;&nbsp;**** **** **** 3400</label></td>
											</tr>
											<tr>
												<td class="text-left"><label class="label"><input type="radio" class="radio" name="radion_bank">招商银行&nbsp;&nbsp;&nbsp;张*&nbsp;&nbsp;&nbsp;**** **** **** 3400</label></td>
											</tr>
											<tr>
												<td class="text-left"><label class="label"><input type="radio" class="radio" name="radion_bank">招商银行&nbsp;&nbsp;&nbsp;张*&nbsp;&nbsp;&nbsp;**** **** **** 3400</label></td>
											</tr>
										</tbody>
									</table>
								</div>
							</li>
							<li>
								<label class="ui-label">打款金额：</label>
								<input type="text" class="input w-4" value="" name="inmoney">								
                                <div class="ui-text-prompt">小数点后最多为两位</div>
                                <div class="ui-check"><i class="error"></i><span >小数点后最多为两位</span></div>
							</li>
							<li>
								<label class="ui-label">备注：</label>
								<div class="textarea w-4">
									<textarea id="text"></textarea>
								</div>								
                                 <div class="ui-text-prompt">0/50</div>
                                <div class="ui-check"><i class="error"></i><span >请输入备注，0-50个字符</span></div>
							</li>
							<li class="ui-btn">
								<button type="submit" class="btn btn-important">提 交<b class="btn-inner"></b></button>
								<a href="/admin/Fundsmanage/currenttransfer?swithval=2" class="btn btn-link">上一步<b class="btn-inner"></b></a>
							</li>
						</ul>
						{else}
						<ul class="ui-form">
							<li>
								<label class="ui-label">打款类型：</label>
								<span class="ui-text-info">人工提现</span>
							</li>
							<li>
								<label class="ui-label">用户名：</label>
								<span class="ui-text-info">wefohwe</span>
								<span class="user-level">用户层级： <span class="color-green">242354356</span> &gt; <span>thtrhft</span> &gt; <span>hgtdhthtf</span> &gt; <span class="color-red">vava</span></span>
							</li>
							<li>
								<label class="ui-label">选择银行卡：</label>
								<div class="select-position">
									<table class="table table-info">
										<tbody>
											<tr>
												<td class="text-left"><label class="label"><input type="radio" class="radio"  name="radion_bank">招商银行&nbsp;&nbsp;&nbsp;张*&nbsp;&nbsp;&nbsp;**** **** **** 3400</label></td>
											</tr>
											<tr>
												<td class="text-left"><label class="label"><input type="radio" class="radio"  name="radion_bank">招商银行&nbsp;&nbsp;&nbsp;张*&nbsp;&nbsp;&nbsp;**** **** **** 3400</label></td>
											</tr>
											<tr>
												<td class="text-left"><label class="label"><input type="radio" class="radio"  name="radion_bank">招商银行&nbsp;&nbsp;&nbsp;张*&nbsp;&nbsp;&nbsp;**** **** **** 3400</label></td>
											</tr>
											<tr>
												<td class="text-left"><label class="label"><input type="radio" class="radio"  name="radion_bank">招商银行&nbsp;&nbsp;&nbsp;张*&nbsp;&nbsp;&nbsp;**** **** **** 3400</label></td>
											</tr>
											<tr>
												<td class="text-left"><label class="label"><input type="radio" class="radio"  name="radion_bank">招商银行&nbsp;&nbsp;&nbsp;张*&nbsp;&nbsp;&nbsp;**** **** **** 3400</label></td>
											</tr>
										</tbody>
									</table>
								</div>
							</li>
							<li>
								<label class="ui-label">可用余额：</label>
								<span class="ui-text-info"><span class="color-red">1992.6500</span> 元</span>
								<span class="ui-prompt">扣除相应游戏币</span>
							</li>
							<li>
								<label class="ui-label">可提现余额：</label>
								<span class="ui-text-info"><span class="color-red">1000.0000</span> 元</span>
								<span class="ui-prompt">忽视不可提现限制</span>
							</li>
							<li>
								<label class="ui-label">打款金额：</label>
								<input type="text" class="input w-4" value="" name="inmoney">								
                                <div class="ui-text-prompt">小数点后最多为两位</div>
                                <div class="ui-check"><i class="error"></i><span >小数点后最多为两位</span></div>
							</li>
							<li>
								<label class="ui-label">备注：</label>
								<div class="textarea w-4">
									<textarea id="text" name="textarinfo"></textarea>
								</div>
								 <div class="ui-text-prompt">0/50</div>
                                <div class="ui-check"><i class="error"></i><span >请输入备注，0-50个字符</span></div>
							</li>
							<li class="ui-btn">
								<button type="submit" class="btn btn-important">提 交<b class="btn-inner"></b></button>
								<a href="/admin/Fundsmanage/currenttransfer?swithval=2" class="btn">上一步<b class="btn-inner"></b></a>
							</li>
						</ul>
						{/if}
                        </form>
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
	selectMenu('Menufunds','MenuWithdrawalsConfig');
		
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
		
		
		
		var form = $('#fm_main'),inmoney,inmoneyPar,istrue="0",
		//表单检测错误数量
		errorTypes = ['inmoney'],
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
		//标题验证
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
		
		textarinfo = $('[name="inmoney"]');
		textarinfoPar = textarinfo.parent();	
		textarinfo.blur(function(){
			var v = $.trim(this.value);
			if(v == ''){			
				textarinfoPar.find('.ui-check').css('display', 'inline');	
				textarinfoPar.find('.ui-text-prompt').css('display', 'none');
				setErrorNum('textarinfo', 1);
			}else{				
				textarinfoPar.find('.ui-check').css('display', 'none');
				setErrorNum('textarinfo', -1);
			}			
		}).focus(function(){
			textarinfoPar.find('.ui-check').css('display', 'none');
			textarinfoPar.find('.ui-text-prompt').css('display', 'inline');
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