
<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-main">
		<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt;
				<span id="titleName">充值相关配置</span>
			</div>
		</div>
		<div class="col-content">
			<div class="col-main">
				<div class="main">
					<div class="ui-tab">
						<ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
							<!-- {if "FUND_RECHARGE_BYPASS"|in_array:$smarty.session.datas.info.acls} -->
							<span><li id="liconfig3">充值分流设置</li></span>
							<!--{else}-->
							<span><li style="display: none;"></li></span>
							<!-- {/if} -->
							<!-- {if "FUND_RECHARGE_CONFIG_UPTO"|in_array:$smarty.session.datas.info.acls} -->
							<span><li id="liconfig2">充值上下限配置</li></span>
							<!--{else}-->
							<span><li style="display: none;"></li></span>
							<!-- {/if} -->
							<!-- {if "FUND_RECHARGE_CONFIG_RETURN"|in_array:$smarty.session.datas.info.acls} -->
							<span><li id="liconfig">充值返送手续费配置</li></span>
							<!--{else}-->
							<span><li style="display: none;"></li></span>
							<!-- {/if} -->
							<input type="hidden" name="step" value="{$step}" />
							<input type="hidden" name="type" value="{$type}" />
							<input type="hidden" name="index" value="{$index}" />
						</ul>
						<div class="ui-tab tab-left">

							<div class="ui-tab-content ui-tab-content-current" id="DivRules">
								<div id="J-side-menu" class="ui-tab-title clearfix"
									style="display: none;">
									<ul>
										<li class="current"><a href="javascript:void(0);">网银充值</a></li>
										<li><a href="javascript:void(0);">快捷充值</a></li>
										<li><a href="javascript:void(0);">财付通</a></li>
										<li><a href="javascript:void(0);">银联充值</a></li>
										<li><a href="javascript:void(0);">支付宝个人版</a></li>
										<li><a href="javascript:void(0);">支付宝企业版</a></li>
										<li><a href="javascript:void(0);">微信支付</a></li>
									</ul>
								</div>
								<ul style="overflow: hidden;">
									<li style="display: none;"><input type="hidden" id="index"
										name="index" value="{$index}" />
										<!-- 網銀充值 -->
										{include file='/admin/funds/recharge/relatosetting/relatosetting_netbank.tpl'}
									</li>
									<li style="display: none;">
										<!-- 快捷充值 -->
										{include file='/admin/funds/recharge/relatosetting/relatosetting_fast.tpl'}									
									</li>
									<li style="display: none;">
										<!-- 財付通 -->
										{include file='/admin/funds/recharge/relatosetting/relatosetting_tenpay.tpl'}																		
									</li>
									<li style="display: none;">
										<!-- 銀聯 -->
										{include file='/admin/funds/recharge/relatosetting/relatosetting_upy.tpl'}
									</li>

									<li style="display: none;">
										<!-- 支付寶 -->
										{include file='/admin/funds/recharge/relatosetting/relatosetting_alipay.tpl'}
										
									</li>
									<li style="display: none;">
										<!-- 支付寶企業版 -->
										{include file='/admin/funds/recharge/relatosetting/relatosetting_alipayBusiness.tpl'}
										
									</li>
									<li style="display: none;">  
										<!-- 微信配置頁面 --> 
										{include file='/admin/funds/recharge/relatosetting/relatosetting_wechat.tpl'}
									</li>

								</ul>
							</div>
							<ul class="ui-form ui-tab-content" id="DivRules2">
								<li>
									<form action="/admin/Rechargemange/index?parma=sv2&step=1"
										method="post" id="J-form2">
										<table class="table table-info table-border"name"tabs2" >
											<tbody>
												<tr>
													<td class="text-left w-4">银行名称</td>
													<td class="text-left"><span class="ui-text-info">请选择：</span>
														<select id="bankid" class="ui-select w-2" name="id">
															<!-- {foreach $banklist as $k1 => $v1} -->
															<!--隱藏企業版的資料 -->
															<!--{if ($v1.display eq 'Y')}-->
															<option value="{$v1['id']}" {if $retConfig['id'] ==$v1['id']}selected{/if}>{$v1['name']}</option>
															<!--{/if}-->
															<!-- {/foreach} -->
													</select></td>
												</tr>
												<tr>
													<td class="text-left w-4">返送条件</td>
													<td class="text-left"><span class="ui-text-info"> 金 额：≥ </span>
														<input type="text" value="{$retConfig['rtnMin']}"
														class="input w-1" name="rtnMin"> <span
														class="ui-text-info">元&nbsp;&nbsp;返手续费</span></td>
												</tr>
												<tr>
													<td class="text-left w-4">返送金额</td>
													<td class="text-left">

														<dl class="set-list">
															<dt class="radio-list">
																<label class="label"><input type="radio" id="radios1"
																	name="rtnSet" {if $retConfig['rtnSet'] ==
																	'2'}checked="checked" {/if} class="radio" value="2">公式计算金额</label>
																<label class="label"><input type="radio" id="radios2"
																	name="rtnSet" {if $retConfig['rtnSet'] ==
																	'1'}checked="checked" {/if} class="radio" value="1">MOW实际抓取金额</label>
															</dt>
															<div id="Div_1">
																{foreach from=$retConfig['rtnStruc'] key=key item=data}
																<dd>
																	<input type="text" readonly value="{$data['sm']}"
																		class="input w-1" name="sm[]"> <span class="ui-info">&le;充值金额</span>
																	{if $cnt != $key} &lt; <input type="text"
																		value="{$data['big']}" class="input w-1" name="big[]">
																	{/if} <span class="ui-info">时，返送手续费为：</span> <span
																		class="radio-list"> <label class="label"><input
																			type="radio" name="radiodd{$key}"
																			{if $data["type"] eq "1"}checked="checked"
																			{/if}   class="radio" value="1">固值</label> <label
																		class="label"><input type="radio" name="radiodd{$key}"
																			{if $data["type"] eq "2"}checked="checked"
																			{/if} class="radio" value="2">百分比</label>
																	</span> <input type="text" value="{$data['value']}"
																		class="input w-1" name="setValue[]"> <span
																		class="ui-info" name="moneyType">%</span> <span
																		class="ui-text-info"></span> {if $key=='0'} <a
																		href="javascript:void(0);" class="btn btn-small"
																		id="AddRule">添加区间<b class="btn-inner"></b></a> <span
																		class="ui-text-prompt">最多支持10个区间</span> {else if $cnt
																	!= $key} <a href="javascript:void(0);"
																		class="btn btn-link" name='J-Delete'>删除<b
																		class="btn-inner"></b></a> {/if}


																</dd>
																{/foreach}
															</div>

														</dl>
													</td>
												</tr>
												<!-- {if "FUND_RECHARGE_CONFIG_RETURN_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
												<tr>
													<td></td>
													<td class="text-left ui-btn"><a href="javascript:void(0);"
														class="btn btn-important" id="J-Submit-Button2">提 交<b
															class="btn-inner"></b></a> <a href="javascript:void(0);"
														class="btn">返 回<b class="btn-inner"></b></a></td>
												</tr>
												<!-- {/if} -->
											</tbody>
										</table>
									</form>

								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<textarea id="DivContent" style="display: none;">
    	<dd>
            <input type="text" value="0" class="input w-1" name="sm[]">
            <span class="ui-info">&le;充值金额&lt;</span>
            <input type="text" value="0" class="input w-1" name="big[]">
            <span class="ui-info">时，返送手续费为：</span>
            <span class="radio-list">
                <label class="label"><input type="radio" id=""
				name="radiodd1" class="radio" value="1">固值</label>
                <label class="label"><input type="radio" id=""
				name="radiodd1" class="radio" value="2">百分比</label>
            </span>
            <input type="text" value="0" class="input w-1"
			name="setValue[]">
            <span class="ui-info" name="moneyType"></span>
            <span class="ui-text-info"></span>
            <a href="javascript:void(0);" class="btn btn-link"
			name='J-Delete'>删除<b class="btn-inner"></b></a>
   		 </dd>
    </textarea>
<textarea id="DivUnfillContent" style="display: none;">       
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">您还有未填内容，请完整填写</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn btn-important "
				id="CloseDf">关 闭<b class="btn-inner"></b></a>   
            </div>
        </div>
    </textarea>
<textarea id="DivMaxContent" style="display: none;">       
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">充值最低金额不能大于最高金额</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn btn-important "
				id="CloseDf">关 闭<b class="btn-inner"></b></a>   
            </div>
        </div>
    </textarea>
{include file='/admin/script-base.tpl'} {literal}
<script>
(function() {	
	var form1 = $('[name="J-form1"]'),form2=$('#J-form2'),minWindow,mask,initFn,isture=false;	
	minWindow = new phoenix.MiniWindow();
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});
	//Tab			
					if($("[name='index']").val() == ""){
						var indexs=$("[name='step']").val();
					}else{
						var indexs=$("[name='index']").val();
					}
					
                    
                    //var index=$("#index").val();
                    
                    var type=$("[name='type']").val();

                    /*if(indexs==0){ //因為會強制默認 充值上下限配置 current ，所以排除
                        new phoenix.Tab({triggers:'.ui-tab-title2 li',panels:'.ui-tab-content',eventType:'click',currPanelClass:'ui-tab-content-current',index:indexs});
                    }*/
                       //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuRechargemangeConfig');
                  
                      $(".ui-tab-title2 li").eq(index).click();
                      //刷新頁面時判斷
                      if(indexs ==0){
          
            		$('#J-side-menu').css("display","inline");
		$('#J-side-menu > ul').find('li').removeClass('current');
		$('#J-side-menu > ul').find('li').eq(type).addClass('current');
		$('#J-side-menu').next('ul').find('li').css("display","none");
		$('#J-side-menu').next('ul').find('li').eq(type).css("display","inline");
                                             $('#liconfig2').addClass('current');
                                             $('#liconfig3').removeClass('current');
		$('#liconfig').removeClass('current');
		
	} else {          
               
		$('#J-side-menu').css("display","none");
		$('#DivRules2').css("display","inline");
		$('#liconfig2').removeClass('current');
                                             $('#liconfig3').removeClass('current');
		$('#liconfig').addClass('current');
		
		
	}
	$('#Tabs').find('span').click(function(){
		var tabIndexs = $(this).index();
	
                                        $("[name='step']").val(tabIndexs);
		if(tabIndexs ==1) {
                                                                    $('#DivRules2').css("display","none");
			$('#DivRules').css("display","inline");
			$('#J-side-menu').css("display","inline");
			$('#J-side-menu').next('ul').find('li').eq(0).css("display","inline");
			$('#liconfig').removeClass('current');
			$('#liconfig2').addClass('current');
                    
			
		} else if(tabIndexs == 2){
                     
                                                                    $('#DivRules2').css("display","inline");
			$('#DivRules').css("display","none");
			$('#J-side-menu').next('ul').find('li').css("display","none");
			$('#liconfig2').removeClass('current');
			$('#liconfig').addClass('current');
			
                                             } else {
                                                  window.location="/fundAdmin/Rechargemange/index?parma=bypass";
			
		}
	});
	$('#J-side-menu > ul').find('li').click(function(){
		
		var indexs = $(this).index();
		$(this).siblings('li').removeClass('current');
		$(this).addClass('current');
		$('#J-side-menu').next('ul').find('li').css("display","none");
		$('#J-side-menu').next('ul').find('li').eq(indexs).css("display","inline");
	});
	
	$('#radios1').click(function(){		
		$('#radios1').attr("checked","checked");
		$('#Div_1').css("display","inline");
	});	
	//如果选择2 隐藏Div_1
	if($('input:radio[name="rtnSet"]:checked').val() =='1'){
		$('#Div_1').css("display","none");
	}
	//切换银行
	$("#bankid").change(function(){
		window.location.href="/admin/Rechargemange/index?parma=sv2&step=1&id="+$("#bankid option:selected").val();
	});
	
	//数字型校验、正数校验、数据有效性校验（如上限需要大于等于下限等）
	var inputs = $(':text'),checkFn;
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
	
	//(检索文本框是否为空)
	function CheckFron(obj){
		$(obj).each(function () { 
			if($(this).val() == ""){
				var obj=this;
				this.focus(); 					
				isture=false;	
				//您还有未填内容,提示弹层，再文本定位到空文本处.
				minWindow.setTitle('温馨提示');
				minWindow.setContent($('#DivUnfillContent').val());
				minWindow.show();	
				$('.j-ui-miniwindow').addClass("pop");//增加图标
				$('#CloseDf').click(function(e){
					minWindow.hide();
					obj.focus(); 
					window.location.hash = "#"+this;
				});		
				return false;						
			}		
			if($(this).attr('name').indexOf('lowLimit') >=0)
			{
				var uplimit=$($(this).parent().find("input")[1]);
				if(parseFloat($(this).val())> parseFloat(uplimit.val()))
				{
					var obj=this;
				    this.focus(); 
					isture=false;	
				    minWindow.setTitle('温馨提示');
				    minWindow.setContent($('#DivMaxContent').val());
				    minWindow.show();	
					$('.j-ui-miniwindow').addClass("pop");//增加图标
					$('#CloseDf').click(function(e){
						minWindow.hide();
						obj.focus(); 
						window.location.hash = "#"+this;
				    });		
					return false;
				}
				
			}
			else{ isture = true;}		
		});	
	}
			
	//返送金额选择
	$('#radios2').click(function(){
		$('#radios2').attr("checked","checked");		
		$('#Div_1').css("display","none");
	});
	//添加区间(最多支持五个区间)
	$('#AddRule').click(function(){
		var i=0,istrue=true;	;
		var objTextSM=null;
		$('#Div_1 dd').each(function(e){		  
			i++;
			if(i > 9){
				alert('最多支持10个区间');				
			}				   
		});		
		if(i > 9){ return false;}
		//objTextSM=$("#Div_1").find("dd")[i-1].find("input");
		objTextSM=$("#Div_1").find("dd")[i-2];
		var sValue=$(objTextSM).find("input")[1].value;
		//var html = $('#addbreakmoneyHtml').val();
		var radiodd="radiodd"+i;
		addMoney=$('#Div_1 dd').eq(-2).find('[name="sm[]"]').val(),
		endMoney=$('#Div_1 dd').eq(-2).find('[name="big[]"]').val(),
		radioCheck =$('#Div_1 dd').eq(-2).find('.radio:checked').val();		
		if(endMoney=='' || $.trim(endMoney)=='0'){
			//e.preventDefault();
			istrue=false;	
		}    
		//下限不能为空跟0
		if( istrue != true){
			alert('请输入有效的充值金额上限');
			return false;
		}else{
			$('#endMoney').val($.trim(endMoney));
		}	
		//选中比例（固值，还是百分比）
		if(radioCheck ==undefined || radioCheck == ""){
			alert('请选择返送手续费方式');
			return false;
		}
		var htmls="<dd><input type='text' value="+sValue+" class='input w-1' name='sm[]' readonly='true'>\&nbsp;<span class='ui-info'>\&le;充值金额\&lt\;&nbsp;&nbsp;</span><input type='text' value='0' class='input w-1' name='big[]' id='endMoney'><span class='ui-info'>\&nbsp;时，返送手续费为：</span>\&nbsp;<span class='radio-list'><label class='label' ><input type='radio' checked='checked'  name="+radiodd+" class='radio' value='1'>固值</label>\&nbsp;<label class='label' ><input type='radio' id='' name="+radiodd+" class='radio' value='2'>百分比</label>\&nbsp;</span><input type='text' value='0' class='input w-1' name='setValue[]'>\&nbsp;<span class='ui-info' name='moneyType"+i+"'>元</span>\&nbsp;<span class='ui-text-info'></span>\&nbsp;<a href='javascript:void(0);' class='btn btn-link' name='J-Delete'>删除<b class='btn-inner'></b></a></dd>";
		$('#Div_1 dd').eq(-1).before(htmls);
							
	})
	$(document).on('blur', "[name='big[]']", function(){
		var oparent=$(this).parent();
	    var smvalue=oparent.find("[name='sm[]']").val();
		var bigvalue=$(this).val();
		if(parseInt(smvalue) >= parseInt(bigvalue))
		{
			alert("请输入有效的充值金额上限");
			$(this).focus();
			return;
		}
		$(this).parent().next().find("[name='sm[]']").val(bigvalue);
    });	
	
	$(document).on('blur', "[name='big[]']", function(){
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
    });	

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
	
	//删除
	$(document).on('click', '[name="J-Delete"]', function(){
		$(this).parent().remove();
		return false;
    });
	//(元与%)带出
	$(document).on('click', '#Div_1 :radio', function(){
		//debugger
		var boolCheck=$(this).parent().find(':radio')[0].value;			
		if(boolCheck==1){				
			$(this).parent().parent().parent().find('[name="moneyType"]').html('元');
		}else{
			$(this).parent().parent().parent().find('[name="moneyType"]').html('%');
		}		
		
	});		

	//充值返送手续费配置,百分比与固值动态判定
	$('#Div_1 dd').each(function(){		
		var boolCheck=$(this).find(':radio:eq(0)').attr("checked");		
		if(boolCheck=='checked'){				
			$(this).find('[name="moneyType"]').html('元');
		}else{
			$(this).find('[name="moneyType"]').html('%');
		}		
	
	});
	//(元与%)带出
	$(document).on('click', '#Div_1 :radio', function(){
		//debugger
		var boolCheck=$(this).parent().find(':radio')[0].value;			
		if(boolCheck=='1'){				
			$(this).parent().parent().find('[name="moneyType"]').html('元');
		}else{
			$(this).parent().parent().find('[name="moneyType"]').html('%');
		}		
		
	});
	//充值上下限配置置(表单提交校验)
	$('#J-Submit-Button1').click(function(){		
		form1.submit();	
	});			
	form1.submit(function(e){			
		var err = 0;	
		CheckFron('#J-form1 :text');
		if(isture ==false){ return false;}
		
	});		
	
    $("[name='rtnMin']").blur(function(){
		var startMoney1=$("[name='rtnMin']").val();
		$($("#Div_1 dd")[0]).find("[name='sm[]']").val($.trim(startMoney1));
	});
	
	//充值返送手续费配置(表单提交校验)
	$('#J-Submit-Button2').click(function(){	
	    var i=0,radiodd;
	    $('#Div_1 dd').each(function(){				
			$(this).find(':radio').each(function(){	
				radiodd="radiodd"+i;
				$(this).removeAttr("name").attr("name",radiodd);	
			});	
			i++;			
		});	
		form2.submit();	
	});			
	form2.submit(function(e){			
		var err = 0;	
		CheckFron('#J-form2 :text');
		if(isture ==false){ return false;}
		
	});			
})();	
</script>
{/literal}
</body>
</html>