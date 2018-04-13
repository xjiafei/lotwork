<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">用户</a> &gt; <a href="#">充值管理</a> &gt;  <a href="#">充值明细</a> &gt;发起异常处理</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
                     <form method="POST" action="/admin/Rechargemange/sechandldetail" id="fm_main"  name="fm_main" >
						<h3 class="ui-title">发起异常处理</h3>
						<ul class="ui-form">
							<li class="checkbox-list">
								<label class="ui-label">选择查询方式：</label>
								<span class="radio-list">
									<label for="a" class="label"><input type="radio" value="cardNo" checked  name="grops" id="a" class="radio" checked="checked">银行卡号</label>
									<label for="b" class="label"><input type="radio" value="account" name="grops" id="b" class="radio">开户名</label>
									<label for="c" class="label"><input type="radio" value="memo" name="grops" id="c" class="radio">附言</label>
									<label for="d" class="label"><input type="radio" value="mcSn" name="grops" id="d" class="radio">MOW单号</label>
								</span>
							</li>
							<li class="ui-other">
								<input type="text" name="inskey" id="inskey"  value="" class="input w-4">								
                                <div class="ui-text-prompt">请输入查旬信息，请勿重复发起</div>									
								<div class="ui-check"><i class="error"></i>请输入查旬信息，请勿重复发起</div>
							</li>
							<li class="ui-btn">
								<a  class="btn btn-important" id="J_submit1">下一步<b class="btn-inner"></b></a>
								<a href="javascript:void(0);" class="btn btn-link">返 回<b class="btn-inner"></b></a>
							</li>
						</ul>
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
	selectMenu('Menufunds','MenuRechargemangeConfig');
	

	var form = $('#fm_main'),inskey,inskeyPar,
	//表单检测错误数量
	errorTypes = ['inskey'],
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
	inskey = $('#inskey');
	inskeyPar = inskey.parent();	
	inskey.blur(function(){
		var v = $.trim(this.value);
		if(v == ''){			
			inskeyPar.find('.ui-check').css('display', 'inline');	
			inskeyPar.find('.ui-text-prompt').css('display', 'none');
			setErrorNum('inskey', 1);
		}else{				
			inskeyPar.find('.ui-check').css('display', 'none');
			setErrorNum('inskey', -1);
		}			
	}).focus(function(){
		inskeyPar.find('.ui-check').css('display', 'none');
		inskeyPar.find('.ui-text-prompt').css('display', 'inline');
	});
	
	
	//下一步验证
	$('#J_submit1').click(function(){
		form.submit();			
	});
	
	//表单提交校验
	form.submit(function(e){	
		
		var err = 0;		
		if(inskey[0].value==''){ 
			inskeyPar.find('.ui-check').css('display', 'inline');	
			inskeyPar.find('.ui-text-prompt').css('display', 'none');
			setErrorNum('inskey', 1);
		}
		$.each(errorTypes, function(){
		if(typeof errorHas[this] != 'undefined'){
			err += errorHas[this];
		}
		});
		if(err > 0){ 	e.preventDefault();  return false;	}	
		return true;
	});
		
		
		
})();
</script>
{/literal}
</body>
</html>