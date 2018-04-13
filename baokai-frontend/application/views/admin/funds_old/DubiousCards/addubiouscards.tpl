<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
			<!-- //////////////头部公共页面////////////// -->
				{include file='/admin/funds/left.tpl'}
			<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">银行卡管理</a> &gt; 添加可疑银行</div></div>
			<div class="col-content">
				<div class="col-main">
				<form method="POST" action="/admin/Bankcardsmanage/index?parma=sv7" id="J-form1">
					<div class="main">
						<h3 class="ui-title">添加可疑银行卡</h3>
						<ul class="ui-form">
							<li>
								<label class="ui-label">*银行卡号：</label>
								<input type="text" value="" class="input w-4" id="bankcard" name="bankcard">                                 
                                <span class="ui-check"><i></i>请输入正确银行卡号(16-19)</span>
							</li>
							<li>
								<label class="ui-label">*选择状态：</label>
								<select class="ui-select" name="selectype">
									<option value="所有"  selected="selected">所有</option>
									<option value="可疑卡">可疑卡</option>
									<option value="骗子卡">骗子卡</option>
									<option value="重复付款">重复付款</option>
									<option value="其他">其他</option>
								</select>
							</li>
							<li>
								<label class="ui-label">备注：</label>
								 <div class="textarea w-4">
                                        <textarea  id="txtinfo"   maxlength="50"  name="txtinfo"></textarea>                                         
                                </div>
                                <div class="ui-text-prompt" id="txtinfoPar1"><span>(<span name='spancount'>0</span>/100)</span></div>
                                <div class="ui-check" id="txtinfoPar2"><i class="error"></i><span >请输入此备注</span></div>     
							</li>
							<li class="ui-btn">								
                                <a href="javascript:void(0);" class="btn btn-important" id="J_submit1">添加<b class="btn-inner"></b></a>
								<a href="/admin/Bankcardsmanage/index?parma=sv6&swithval=1" class="btn">返 回<b class="btn-inner"></b></a>
							</li>
						</ul>
					</div>
					</form>
				</div>
			</div>
		</div>
	</div>
<script src="{$path_js}/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="{$path_js}/js/phoenix.Common.js" type="text/javascript" ></script>
{literal}	
<script>
(function() {
		 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuAllOpterators');	
	var form = $('#J-form1'),bankcard,bankcardPar,txtinfo,txtinfoPar1,txtinfoPar2,isture="0",		
	//表单检测错误数量
	errorTypes = ['bankcard','txtinfo'],
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
		if($('#bankcard')[0].value=='' || $('#bankcard')[0].value.length > 19 || $('#bankcard')[0].value.length < 16){
			bankcardPar.find('.ui-check').css('display', 'inline');				
			setErrorNum('bankcard', 1);	
		}
		if($.trim($('#txtinfo')[0].value)=='')
		{
			txtinfoPar2.css('display', 'inline');	
			txtinfoPar1.css('display', 'none');
			setErrorNum('txtinfo', 1);
		}			
	}		
	//备注已输入字符长度动态
	$('#txtinfo').keyup(function() {
	 $('[name="spancount"]').html($('#txtinfo')[0].value.length);
	});		
	//银行卡验证
	bankcard = $('#bankcard');
	bankcardPar = bankcard.parent();	
	bankcard.blur(function(){
		var v = $.trim(this.value);		
		if(v == '' || v.length > 19 || v.length < 16){			
			bankcardPar.find('.ui-check').css('display', 'inline');	
			bankcardPar.find('.ui-text-prompt').css('display', 'none');
			setErrorNum('bankcard', 1);
		}else{				
			bankcardPar.find('.ui-check').css('display', 'none');
			setErrorNum('bankcard', -1);
		}			
	}).focus(function(){
		bankcardPar.find('.ui-check').css('display', 'none');
		bankcardPar.find('.ui-text-prompt').css('display', 'inline');
	});
	//备注不为空提醒
	txtinfo = $('#txtinfo');
	txtinfoPar1 =$('#txtinfoPar1');	
	txtinfoPar2 =$('#txtinfoPar2');	
	txtinfo.blur(function(){
		var v = $.trim(this.value);
		if(v == ''){			
			txtinfoPar2.css('display', 'inline');	
			txtinfoPar1.css('display', 'none');
			setErrorNum('txtinfo', 1);
		}else{				
			txtinfoPar2.css('display', 'none');
			setErrorNum('txtinfo', -1);
		}			
	}).focus(function(){
		txtinfoPar1.css('display', 'inline');
		txtinfoPar2.css('display', 'none');
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
		if(err > 0 ){
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