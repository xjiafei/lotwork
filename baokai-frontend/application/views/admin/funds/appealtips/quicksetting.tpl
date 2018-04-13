	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
		<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
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
							<ul class="ui-form ui-tab-content ui-tab-content-current"  id="DivRules">
                            	<li>
                                    <input type="hidden" id="tipIndex" name="tipIndex" value="{$tipIndex}"/>
                            		<form action="/admin/Fundconfig/index?parma=wcf1" method="post" id="subForm">
                                    <table class="table table-info ">																		
                                        <tbody>
                                            <tr>
                                                <td class="text-left">
													<label class="ui-label w-auto" style=" text-align:left; margin-left:300px">提示內容</label>
													<input id="urgencyText" style=" text-align:left; margin-left:35px"  type="text" name="tips1" size="100"><br>
													<!-- {if "FUND_WITHDRAW_URGENCY_SAVE"|in_array:$smarty.session.datas.info.acls} --> 
													<input type="button"  style=" text-align:left; margin-left:300px;" class='btn btn-important' id='publictips' style='position:initial' value="发布" />
													<!-- {/if} -->
                                                </td>
                                            </tr>
                                            <tr>
                                               <td class="text-left">
                                               <table>
                                               	<tr>
                                               	<td>歷史發布內容<td>
                                               	<td>操作人<td>
                                               	<td>状态<td>
                                               	<td>操作<td>
                                               	</tr>

                                               	{foreach from=$res.urgencys item=urgency key=index}
													<tr>
													<td>{$urgency.createTimeText} {$urgency.urgencyContext}<td>
													<td>{if {$urgency.updateUser}!=''}{$urgency.updateUser}{else}{$urgency.createUser}{/if}<td>
													<td>{if {$urgency.cancelFlag}!='Y'} 已发布{else}已撤销{/if}<td>
													<td>
													
													<input type="button" style="height:23px;text-align:center;line-height:23px" {if {$urgency.cancelFlag}!='Y'}   class='btn btn-important'
																		  {else}   class='btn btn-disable' disabled {/if}
													id ={$urgency.id} name="cancelBtn" value = '撤销' />
													<td>
													
													</tr>     
                                            	{/foreach}
                                               </table>
			                                 </td>
                                            </tr>
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
			
	  <!--編輯提示-->
	  <div class="pop w-8" id="PublicTipsWindow" style="display:none;">
               <div class="hd">
        <i class="close" name="closeIcoDiv1"></i>
        <h3 align="center">确认框</h3>
		</div>
		<div class="bd">
		<ul class="ui-form ui-form-small">
			<li>
				<label class="ui-label w-auto" style="text-align:center;" >请问是否确认发布该提示？</label><br>
			</li>			
			<li>
				<button class='btn btn-important' name="closeIcoDiv1" href='javascript:void(0);' style='position:initial' id="addConfirm">确认发布</button>
				<button class='btn' name="closeIcoDiv1" href='javascript:void(0);' style='position:initial' >取消</button>
			</li>
		</ul>
      </div>
	  </div>
	  
	  <!--撤銷提示-->
	  <div class="pop w-8" id="CancelTipsWindow" style="display:none;">
               <div class="hd">
        <i class="close" name="closeIcoDiv2"></i>
        <h3 align="center">确认框</h3>
		</div>
		<div class="bd">
		<ul class="ui-form ui-form-small">
			<li>
				<label class="ui-label w-auto" style="text-align:center;" >请问是否确认撤销该提示？</label><br>
			</li>			
			<li>
				<button class='btn btn-important' name="closeIcoDiv2" href='javascript:void(0);' style='position:initial' id="cancelConfirm">确认发布</button>
				<button class='btn' name="closeIcoDiv2" href='javascript:void(0);' style='position:initial' >取消</button>
			</li>
		</ul>
      </div>
	  </div>
	  
	  
	  
	</div>
	</div>
	
	<input type="hidden" id='urgencyId' value ='' />  
													
{include file='/admin/script-base.tpl'}
{literal}
<script>
(function() {	
	option = {zIndex:500},	
    box1 = new LightBox("PublicTipsWindow",option),	
	box2 = new LightBox("CancelTipsWindow",option),	
	
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
	
	$("#publictips").click(function(){
		  box1.OverLay.Color = "rgb(51, 51, 51)" ; 
		  box1.Over = true;   
		  box1.OverLay.Opacity = 50;  
		  box1.Fixed = true;	 
		  box1.Center = true;
		  box1.Show();
	});
	
	
	$(document).on('click', '[name^="cancelBtn"]', function(e){
	
	
		  $('#urgencyId').val($(this).attr("id"));
        
	
 
		  box2.OverLay.Color = "rgb(51, 51, 51)" ; 
		  box2.Over = true;   
		  box2.OverLay.Opacity = 50;  
		  box2.Fixed = true;	 
		  box2.Center = true;
		  box2.Show();
	});
	
	
	$(document).on('click', '[name="closeIcoDiv1"]', function(e){
		box1.Close();
			
	});
	$(document).on('click', '[name="closeIcoDiv2"]', function(e){
		box2.Close();
			
	});
	$("#addConfirm").click(function(){
		$.ajax({
            url:'/admin/Fundconfig/index?parma=addUrgency',
            method: "post",
            data:{'data':$("#urgencyText").val()},
            type:'json',
            beforeSend: function () {
                isLock = false;
                TableStyle("DivRules", 19, 1, "保存中");
            },
            success:function(){
               location.reload();
            },
            complete: function ()
            {
                isLock = true;
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                TableStyle("DivRules", 17, 2, "数据异常");
            }
        });
		
	});
	
	
	$("#cancelConfirm").click(function(){
		$.ajax({
            url:'/admin/Fundconfig/index?parma=updateUrgency',
            method: "post",
            data:{'id':$("#urgencyId").val(),'cancelFlag':'Y'},
            type:'json',
            beforeSend: function () {
                isLock = false;
                TableStyle("DivRules", 19, 1, "保存中");
            },
            success:function(){
               location.reload();
            },
            complete: function ()
            {
                isLock = true;
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                TableStyle("DivRules", 17, 2, "数据异常");
            }
        });
		
	});

})();	


</script>
{/literal}
</body>
</html>