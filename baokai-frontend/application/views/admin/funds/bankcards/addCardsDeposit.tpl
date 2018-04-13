	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; <a href="/admin/Bankcardsmanage/index?parma=sv41">银行管理</a> &gt; 新增充值银行</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
                     <form action="" method="post" id="J-form" >
						<table class="table table-info table-border" id="Tables">
							<thead>
								<tr>
									<th class="text-left" colspan="2">新增充值银行</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="text-left">银行名称</td>
									<td class="text-left">
										<select name="name" id="bankName" class="ui-select">
											{foreach from=$bankList item=data}
												<option logo="{$data.logo}" value="{$data.name}" {if $res.name == $data.name}selected{/if}>{$data.name}</option>
											{/foreach}
										</select>
									</td>
								</tr>
								<tr>
									<td class="text-left">银行LOGO</td>
									<td class="text-left">
										<span class="ico-bank {$bankList[0]['logo']}" id="img_logo"></span>
										<input type="hidden" value="{$bankList[0]['logo']}" name="logo" id="name_logo"/>
									</td>
								</tr>
								<tr>
									<td class="text-left">银行代码</td>
									<td class="text-left">
										<input type="text" class="input" value="" name="code">

									</td>
								</tr>
								<tr>
									<td class="text-left">MOW银行编码</td>
									<td class="text-left">
										<input type="text" class="input" value="" name="mownecumId">
									</td>
								</tr>
								<tr>
									<td class="text-left">网银地址</td>
									<td class="text-left">
										<input type="text" class="input" value="" name="url">
									</td>
								</tr>
								<tr >
									<td class="text-left">充值上下限</td>
									<td class="text-left" name="trText">
										<ul class="set-list">
											<li>
												<span class="ui-info">普通用户：</span>
												<span class="ui-text-info">最低：</span>
												<input type="text" class="input w-1" value="" name="lowLimit">
												<span class="ui-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
												<span class="ui-text-info">最高：</span>
												<input type="text" class="input w-1" value="" name="upLimit">
												<span class="ui-info">元</span>
											</li>
											<li>
												<span class="ui-info">&nbsp;VIP用户：</span>
												<span class="ui-text-info">最低：</span>
												<input type="text" class="input w-1" value="" name="vipLowLimit">
												<span class="ui-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
												<span class="ui-text-info">最高：</span>
												<input type="text" class="input w-1" value="" name="vipUpLimit">
												<span class="ui-info">元</span>
											</li>
										</ul>
									</td>
								</tr>
								<tr >
									<td class="text-left">返送金额</td>
									<td class="text-left" name="trText">
										<dl class="set-list">
											<dt>
												<span class="ui-info">起始返送条件：</span>
												<span class="ui-info">≥</span>
												<input type="text" class="input w-1" value="0" id="startMoney1" name="rtnMin">
												<span class="ui-info">元</span>
												<span class="ui-text-info">返手续费</span>
											</dd>
											<dt>
												<span class="ui-info">返送金额：</span>
												<span class="radio-list">
													 <label class="label"  ><input type="radio" id="radios1"  name="rtnSet" checked="checked" class="radio" value="2">公式计算金额</label>
                                                     <label class="label"  ><input type="radio" id="radios2"  name="rtnSet" class="radio" value="1">MOW实际抓取金额</label>
												</span>
											</dt>
                                            <div id="Div_1">
                                                <dd>                                            
                                                    <input type="text" class="input w-1" value="0" id="startMoney2" name="sm[]" readonly='true' style="border:1px solid #CECECE;background:#E3E3E3">
                                                    <span class="ui-info">≤充值金额&lt;</span>
                                                    <input type="text" class="input w-1" value="0" name="addMoney[]">
                                                    <span class="ui-info">时，返送手续费为：</span>
                                                    <span class="radio-list">
                                                        <label class="label"><input type="radio" id="" name="radiodd1"  checked class="radio" value="1">固值</label>
                                                        <label class="label" ><input type="radio" id="" name="radiodd1"  class="radio" value="2">百分比</label>
                                                    </span>
                                                    <input type="text" class="input w-1" value="0" name="setValue[]">
                                                    <span class="ui-info" name="moneyType"></span>
                                                    <span class="ui-text-info"></span>
                                                    <a class="btn btn-small" href="javascript:void(0);"  id="AddRule">添加区间<b class="btn-inner"></b></a>
                                                    <span class="ui-text-prompt">最多支持5个区间</span>
                                                </dd>							
                                                 <dd>
                                                    <input type="text" value="0" class="input w-1" id="endMoney" name="sm5" readonly='true' style='border:1px solid #CECECE;background:#E3E3E3'>
                                                    <span class="ui-info">&le;</span>
                                                    <span class="ui-info">时，返送手续费为：</span>
                                                    <span class="radio-list">
                                                        <label class="label" ><input type="radio"  class="radio" checked name="radiodd5"  value="1">固值</label>
                                                        <label class="label" ><input type="radio"  class="radio" name="radiodd5" value="2">百分比</label>
                                                    </span>
                                                    <input type="text" value="0" class="input w-1" name="setValue5">
                                                    <span class="ui-info" name="moneyType"></span>
                                                    <span class="ui-text-info"></span>
                                                </dd> 
                                        	</div>
										</dl>
									</td>
								</tr>
								<!-- {if "FUND_BANKCARD_BANKMANAGE_RECHARGE_CREATE"|in_array:$smarty.session.datas.info.acls} -->    
								<tr>
									<td></td>
									<td class="text-left ui-btn">
										<a class="btn btn-important" href="javascript:void(0);" id="J-Submit-Button">新 增<b class="btn-inner"></b></a>
										<a class="btn" href="/admin/Bankcardsmanage/index?parma=sv41">取 消<b class="btn-inner"></b></a>
									</td>
								</tr>
								<!-- {/if} -->
							</tbody>
						</table>
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
    
	//弹窗
	minWindow = new phoenix.MiniWindow(),isture=false;
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});	
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuBanks');	
	//检索是否为空
	function CheckFrom(obj){		
		$(obj).each(function () { 
			$(this).css("border","1px solid #CECECE");	
			if($(this).val() == ""){
				var obj=this;
				$(this).focus(); 
				$(this).css("border","1px solid red");					
				isture=false;					
				window.location.hash = "#"+this;
				return false;						
			}		
			else{ isture = true;}		
		});	
	}		
	//数字型校验、正数校验、数据有效性校验（如上限需要大于等于下限等）	
	var inputs = $('[name="trText"] :text'),checkFn;	
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
	$("#bankName").change(function(){
		var logo = $("#bankName option:selected").attr('logo');
		$("#img_logo").attr("class","ico-bank "+logo);
		$("#name_logo").val(logo);
	});	
	//返送金额方式（清空之前层数据..）
	$('#radios1').click(function(){		
		$('#radios1').attr("checked","checked");
		$('#Div_1').css("display","inline");
	});	
	//如果选择2 隐藏Div_1
	if($('input:radio[name="rtnSet"]:checked').val() =='1'){
		$('#Div_1').css("display","none");
	}
	//返送金额选择
	$('#radios2').click(function(){
		$('#radios2').attr("checked","checked");		
		$('#Div_1').css("display","none");
	});
	
	$(document).on('click', $('[name="addMoney[]"]'), function(){
	//$('#Div_1 dd').eq(-2).find('[name="addMoney"]').blur(function(){
		var endMoney=$('#Div_1 dd').eq(-2).find('[name="addMoney[]"]').val();
		$('#endMoney').val($.trim(endMoney));
	});	
	
	//添加区间(最多支持五个区间)
	$('#AddRule').click(function(e){
		var i=0,istrue=true;			
		$('#Div_1 dd').each(function(){		  
			i++;			 
		});	
		if(i > 4 ){ 
			alert('最多支持5个区间');
			return false;
		}			
		var radiodd="radiodd"+i,
		addMoney=$('#Div_1 dd').eq(-2).find('[name="addMoney[]"]').val(),
		endMoney=$('#Div_1 dd').eq(-2).find('[name="addMoney[]"]').val(),
		radioCheck =$('#Div_1 dd').eq(-2).find('.radio:checked').val();		
		if(endMoney=='' || $.trim(endMoney)=='0'){
			e.preventDefault();
			istrue=false;	
		}    
		//下限不能为空跟0
		if( istrue != true){
			alert('请输入有效的充值金额下限');
			return false;
		}else{
			$('#endMoney').val($.trim(endMoney));
		}	
		//选中比例（固值，还是百分比）
		if(radioCheck ==undefined || radioCheck == ""){
			alert('请选择返送手续费方式');
			return false;
		}
		var htmls="<dd><input type='text' value="+addMoney+" class='input w-1' readonly='true' style='border:1px solid #CECECE;background:#E3E3E3' name='sm[]'>\&nbsp;<span class='ui-info'>\&le;充值金额\&lt\;&nbsp;</span><input type='text' value='' class='input w-1' name='addMoney[]'><span class='ui-info'>\&nbsp;时，返送手续费为：</span>\&nbsp;<span class='radio-list'><label class='label' ><input type='radio' id='' checked   name="+radiodd+" class='radio' value='1'>固值</label>\&nbsp;<label class='label' ><input type='radio' id='' name="+radiodd+" class='radio' value='2'>百分比</label>\&nbsp;</span><input type='text' value='0' class='input w-1' name='setValue[]'>\&nbsp;<span class='ui-info' name='moneyType'></span>\&nbsp;<span class='ui-text-info'></span>\&nbsp;<a href='javascript:void(0);' class='btn btn-link' name='J-Delete'>删除<b class='btn-inner'></b></a></dd>";
		$('#Div_1 dd').eq(-1).before(htmls);					
	})
	
	
	$('#startMoney1,#startMoney2').blur(function(){
		var startMoney1=$('#startMoney1').val();
		$('#startMoney2').val($.trim(startMoney1));
	});	
	//删除
	$(document).on('click', '[name="J-Delete"]', function(){
		$(this).parent().remove();
		return false;
    })	
	
	//(元与%)带出
	$(document).on('click', '#Div_1 :radio', function(){
		//debugger
		var boolCheck=$(this).parent().find(':radio')[0].value;		
		//alert(boolCheck);	
		if(boolCheck==1){				
			$(this).parent().parent().parent().find('[name="moneyType"]').html('元');
		}else{
			$(this).parent().parent().parent().find('[name="moneyType"]').html('%');
		}		
		
	});	
	
	//检索上下限金额大小问题，待做
	$('#J-Submit-Button').click(function(){
		var i=0,radiodd;
		CheckFrom("#Tables :text");
		if(isture !=true){
			return false;
		}		
		$('#Div_1 dd').each(function(){		
			i++;			
			$(this).find(':radio').each(function(){	
				radiodd="radiodd"+i;
				$(this).removeAttr("name").attr("name",radiodd);	
			});			
		});
		$('#J-form').submit();
	});
	
})();	
</script>
{/literal}
</body>
</html>