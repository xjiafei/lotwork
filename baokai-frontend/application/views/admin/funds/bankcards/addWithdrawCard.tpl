	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; <a href="/admin/Bankcardsmanage/index?parma=sv41">银行管理</a> &gt; 新增提现银行 </div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
					<form action="/admin/Bankcardsmanage/index?parma=opt3" method="post" id="fm_main">
						<table class="table table-info table-border">
							<thead>
								<tr>
									<th class="text-left" colspan="2">新增提现银行</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="text-left">银行名称</td>
									<td class="text-left">
										<select name="name" id="bankName" class="ui-select">
											<!-- {foreach from=$bankList item=data} -->
												<!-- {if $data.id lt 30} -->
												<option logo="{$data.logo}" value="{$data.name}">{$data.name}</option>
												<!-- {/if} -->
											<!-- {/foreach} -->
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
										<input type="text" name="code" class="input" value="">
									</td>
								</tr>
								<tr>
									<td class="text-left">MOW银行编码</td>
									<td class="text-left">
										<input type="text" name="mownecumId" class="input" value="{$res.mownecumId}">
									</td>
								</tr>
								<tr>
									<td class="text-left">网银地址</td>
									<td class="text-left">
										<input type="text" name="url" class="input" value="{$res.url}">
									</td>
								</tr>
								<!-- {if "FUND_BANKCARD_BANKMANAGE_WITHDRAW_CREATE"|in_array:$smarty.session.datas.info.acls} -->   
								<tr>
									<td></td>
									<td class="text-left ui-btn">
									
                                    <a class="btn btn-important" href="javascript:void(0);" id="J-Submit">新 增<b class="btn-inner"></b></a>
									<a class="btn" href="javascript:void(0);">取 消<b class="btn-inner"></b></a>
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
	var minWindow = new phoenix.MiniWindow(),isture=false,form=$('#fm_main');
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
	$("#bankName").change(function(){
		var logo = $("#bankName option:selected").attr('logo');
		$("#img_logo").attr("class","ico-bank "+logo);
		$("#name_logo").val(logo);
	});	
	
	//表单提交校验
	$('#J-Submit').click(function(){
		form.submit();	
	});			
	
	form.submit(function(e){			
		CheckFrom("#fm_main :text");
		if(isture !=true){
			return false;
		}	
		return true;	
	});			
	
})();	
</script>
{/literal}
</body>
</html>