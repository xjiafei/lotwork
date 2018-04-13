<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">异常补款</a> &gt; 发起补款申请</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">

                   		<form method="post" id="J-form">
                            <h3 class="ui-title">发起补款申请</h3>
                            <ul class="ui-form">
                                <li>
                                    <label class="ui-label">异常流水号：</label>
                                    <input type="text" class="input w-4" value="" id="exceptionnumber">								
                                    <div class="ui-prompt">请输入需要补款的异常充值、在线提现、人工提现、活动派奖流水号</div>									
                                    <div class="ui-check"><i class="error"></i><span id="errortype">请输入需要补款的异常充值、在线提现、人工提现、活动派奖流水号</span></div>
                                </li>
                                <li class="ui-btn">
                                    <input type="button" class="btn btn-important" value="下一步" id="J-Submit" /><b class="btn-inner"></b>
                                    <a class="btn btn-link" href="/admin/EpReplenishment/index?parma=sv3">返 回<b class="btn-inner"></b></a>
                                </li>
                                <li class="ui-text">说明：处理中的异常流水号，不能重复发起</li>
                            </ul>
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

	var form = $('#J-form'),exceptionnumber,exceptionnumberPar,istrue="0";	
	//表单检测错误数量
	errorTypes = ['exceptionnumber'],
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
	
	//充值金额验证
	exceptionnumber = $('#exceptionnumber');
	exceptionnumberPar = exceptionnumber.parent();	
	exceptionnumber.blur(function(){
		var v = $.trim(this.value);
		if(v !=''){
			checknumeber();
		}
		else
		{			
			exceptionnumberPar.find('.ui-check').css('display', 'inline');	
			exceptionnumberPar.find('.ui-prompt').css('display', 'none');
			setErrorNum('exceptionnumber', 1);	
		}			
	}).focus(function(){		
		exceptionnumberPar.find('.ui-check').css('display', 'none');
		exceptionnumberPar.find('.ui-prompt').css('display', 'inline');
	});
	//验证异常流水号是否合法,
	function checknumeber(){
		$.ajax({
				url:'/admin/EpReplenishment/replenishment?parma=sv3',
				dataType:'json',
				method:'post',
				data:'exceptionnumber='+$('#exceptionnumber').val().trim(),
				beforeSend:function(){					
				},
				success:function(data){
					if(data.status=="1"){
						exceptionnumberPar.find('.ui-check').css('display', 'none');
						setErrorNum('exceptionnumber', -1);
						istrue = "1";
					}
					else{
						//异常返回原因，处理中不能重复发起。
						$('#errortype').html('发起异常');
						exceptionnumberPar.find('.ui-check').css('display', 'inline');	
						exceptionnumberPar.find('.ui-prompt').css('display', 'none');
						setErrorNum('exceptionnumber', 1);	
					}					
				},
				complete:function(){
				}
			});				
	}	
	
	//表单提交校验
	$('#J-Submit').click(function(){		
		form.submit();
	});	
	
	form.submit(function(e){			
		var err = 0;
		if($.trim(exceptionnumber[0].value) !=''){
			checknumeber();
		}
		else{
			exceptionnumberPar.find('.ui-check').css('display', 'inline');	
			exceptionnumberPar.find('.ui-prompt').css('display', 'none');
			setErrorNum('exceptionnumber', 1);
		}		
		$.each(errorTypes, function(){
			if(typeof errorHas[this] != 'undefined'){
				err += errorHas[this];
			}
		});	
		if(err > 0 || istrue !="1"){
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