	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; <span id="titleName">提现相关配置</span></div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							{include file='/admin/funds/draw/base/drawTip.tpl'}	
								<ul class="ui-form ui-tab-content ui-tab-content-current">
									<li>
									<form action="/admin/Fundconfig/index?parma=wcf3" method="post" id="subForm">
                                        <table class="table table-info table-border">
                                            <thead>
                                                <tr>
                                                    <th colspan="2" class="text-left">风险提现-门槛设置</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <input type="hidden" id="tipIndex" name="tipIndex" value="{$tipIndex}"/>
                                                <tr>
                                                    <td class="text-left w-4" >金额与次数</td>
                                                    <td class="text-left">
                                                        <dl class="set-list">
                                                            <dt class="radio-list">
                                                                <label class="label" for="a"><input id="radioAll" type="radio" id="a" name="part" class="radio" value="1" {if $res.val.part eq '1'}checked{/if}>全部审核</label>
                                                                <label class="label" for="b"><input id="radioPart" type="radio" id="b" name="part" class="radio" value="0" {if $res.val.part eq '0'}checked{/if}>部分审核</label>
                                                                <input type="hidden" id="hidRadio"  value="{if $res.val.part eq '0'}0{else}1{/if}"/>
                                                               
                                                            </dt> 
                                                            <div id="divInfo" {if $res.val.part eq '1'} style="display:none;"{/if} >
                                                            <dd>
                                                                <span class="ui-info">单次提现金额≥</span>
                                                                <input maxlength="8" type="text" class="input w-1" value="{$res.val.amt}" name="amt" id="amtMoney"><input type="hidden" id="hidMoney" value="{$res.val.amt}"/>
                                                                <span class="ui-info">元</span>
                                                            </dd>
                                                            <dd><span class="ui-info">或</span></dd>
                                                            <dd>
                                                                <span class="ui-info">单日提现次数≥</span>
                                                                <input maxlength="4" type="text" class="input w-1" value="{$res.val.time}" name="time" id="amtTime"><input type="hidden" id="hidTime" value="{$res.val.time}" />
                                                                <span class="ui-info">次</span>
                                                            </dd>
                                                            </div>
                                                        </dl>
                                                    </td>
                                                </tr>
												<!-- {if "FUND_WITHDRAW_CONFIG_RISK_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
                                                <tr>
                                                    <td></td>
                                                    <td class="text-left ui-btn">
                                                        <a href="javascript:void(0);" class="btn btn-important" id="btnsubmit">保 存<b class="btn-inner"></b></a>
                                                        <a href="javascript:void(0);" id="btnInner" class="btn">撤销编辑<b class="btn-inner"></b></a>
                                                    </td>
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
{include file='/admin/script-base.tpl'}
{literal}
<script>
(function() {	
	/* minWindow = new phoenix.MiniWindow();
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	}); */
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuWithdrawalsConfig');
	var tipIndex = $("#tipIndex").val();
    $("#drawcfg_"+tipIndex).addClass("current");	
	$("#btnsubmit").click(function(){
		$("#subForm").submit();
	});
	$('.ui-tab-title li').click(function(){
		var indexs = $(this).val();
        window.location.href="/admin/Fundconfig/index?parma=wcf"+(parseInt(indexs));
	});
	$("#btnInner").click(function(){
		$("#amtMoney").val($("#hidMoney").val());
		$("#amtTime").val($("#hidTime").val());
		var radio=$("#hidRadio").val();
		if(radio==1)
		{
			$('input[name=part]').get(0).checked = true;
			$("#divInfo").hide();
		}else if(radio==0)
		{
			$('input[name=part]').get(1).checked = true;
		    $("#divInfo").show();
		}
	});
	$("input[type='text']").on("keyup",function()
	{
		$(this).val($(this).val().replace(/[^0-9]/g,''));
	}).on("blur",function(){
		$(this).val($(this).val().replace(/[^0-9]/g,''));
	});
	
	$("[name='part']").click(function(){
		 if($(this).val()=="1")
		 {
			 $("#divInfo").hide();
		 }else if($(this).val()=="0")
		 {
			 $("#divInfo").show();
		 }
	});
})();	
</script>
{/literal}
</body>
</html>