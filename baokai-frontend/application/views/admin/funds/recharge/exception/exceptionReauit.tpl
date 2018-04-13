	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; <a href="/admin/Rechargemange/index?parma=sv1">异常充值处理</a> &gt;  {$title}</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
                    	
						<h3 class="ui-title">{$title}</h3>
                        
						<table class="table table-info text-left">
							<thead>
								<tr>
									<th>时间</th>
									<th>MOW单号</th>
									<th>付款方</th>
									<th>收款方</th>
									<th>金额</th>
									<th>手续费</th>
									<th>附言</th>
									<!-- {if $vals.bankId gt 30 or $vals.rcvBank gt 30} -->
									<th>收款渠道</th>
									<!-- {else} -->
									<th>支行信息</th>
									<!-- {/if} -->
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>{$vals.realChargeTime}</td>
									<td>{$vals.mcSn}</td> 
									<td>{$vals.bankInfo.name}<br />{$vals.cardAcct} - {$vals.cardNumber}</td>
									<td>{$vals.revBankInfo.name}<br /> {$vals.rcvEmail}</td>
									<td><span style="color:red;">{$vals.realChargeAmt}</span>元</td>
									<td><span style="color:red;">{$vals.mcBankFee}</span>元</td>
									<td>{$vals.memo}</td>
									<td>{$vals.bankName}<br/> {$vals.bankAddr}</td>
								</tr>
							</tbody>
						</table>
						<input type="hidden" id="type" value="{$type}"/> 
<!--                        <form action="/admin/Rechargemange/index?parma=opt1&optionType=1" enctype="multipart/form-data" method="POST" id="J-form1" style="display:none">-->
                            <ul class="ui-form" id="J-form1" style="display:none">
                                <li>
                                    <label class="ui-label big">确认加游戏币信息：</label>
                                </li>
                                <li>
                                   <label class="ui-label">用户名：</label>
                                    <input type="hidden" name="exceptId" value="{$vals.id}"/>
                                    <input type="text" class="input w-4" value="{$vals.userName}" {if $vals.userName neq ''}disabled{/if} name="userName1" id="userName1"/>
                                    <div class="ui-check"><i class="error"></i><span id="usernames">请输入用户名(4-16位)</span></div>
                                    <div id="userinfo" style="display:none"><span class='user-level' id="userinfo2">用户层级：</span></div>                                    
                                     <!--<span class='color-green'>242354356</span> &gt; <span>thtrhft</span> &gt; <span>hgtdhthtf</span> &gt; <span class="color-red">vava</span></span>-->
                                </li>
                                <li>
                                <label class="ui-label">附件：</label>{$vals.attach}
                                </li>
                                <li>     
                                    <label class="ui-label">备注：</label>
                                    <div class="textarea w-4">
                                        <textarea  id="txtRemark1" name="applyMemo"   maxlength="100" >{$vals.apprMemo}</textarea>                                        
                                    </div>
                                    <span class="ui-info vertical-bottom"><span name='spancount'>0</span>/100</span>
                                    
                                </li>
                                <li class="ui-btn">
                                    <a class="btn btn-important" href="javascript:void(0);" id="J-Submit-Button1">通 过<b class="btn-inner"></b></a>
						            <a class="btn btn-important" href="javascript:void(0);" id="J-Cancle-Button1">拒 绝<b class="btn-inner"></b></a>
									<a class="btn btn-link" url="/admin/Rechargemange/index?parma=opt2&exceptId={$vals.id}&status={$vals.status}" id="J-Return-Button1">返 回<b class="btn-inner"></b></a>
                                </li>
                            </ul>
                         <!--</form>-->
				
<!--						<form action="/admin/Rechargemange/index?parma=opt1&optionType=2" enctype="multipart/form-data" method="POST" id="J-form2" style="display:none">-->
                            <ul class="ui-form" id="J-form2" style="display:none">
                                <li>
                                    <label class="ui-label big">确认退款信息：</label>
                                </li>
                                <li>
                                <input type="hidden" name="exceptId" value="{$vals.id}"/>
                                <input type="hidden" name="cardAcct" value="{$vals.cardAcct}"/>
                                <input type="hidden" name="cardNumber" value="{$vals.cardNumber}"/>
                                    <label class="ui-label">收款银行：</label>
                                    <select  class="ui-select w-3"id="beneficiaryBank"  name="bankId" {if $vals.bankId}disabled="disabled"{/if}>
                                    	<!-- <option value="{$vals.bankId}">{$vals.bankInfo.name}</option> -->
                                    	<!-- {foreach from=$vals._bankIcoArray item=data key=key} -->
                                     	<option value="{$key}" {if $vals.bankId eq $key}selected{/if}>{$data.name}</option>
                                    	<!-- {/foreach} -->
                                    </select>                								
                                    <div class="ui-check"><i class="error"></i><span id="errortype">请选择收款银行</span></div>
                                </li>
                                <li>
                                    <label class="ui-label">{if $vals.bankId gt 30 or $vals.rcvBank gt 30}财付通账户名：{else}银行账户名:{/if}</label>
                                    <input type="text" value="{$vals.cardAcct}" name="recBankAccount" class="input w-4" {if $vals.cardAcct}disabled="disabled"{/if}>
                                </li>
                                <li>
                                    <label class="ui-label">{if $vals.bankId gt 30 or $vals.rcvBank gt 30}收款财付通账号：{else}收款银行卡号：{/if}</label>
                                    <input type="text" value="{$vals.cardNumber}"  name="recBankNo" class="input w-4" {if $vals.cardNumber}disabled="disabled"{/if}>                                     
                                </li>
                                <li>
                                    <label class="ui-label">{if $vals.bankId gt 30 or $vals.rcvBank gt 30}收款渠道：{else}开户行名称：{/if}</label>
                                    <input type="text" value="{$vals.bankName}" name="bankName" class="input w-4" {if $vals.bankName}disabled="disabled"{/if}>
                                </li>
                                <li>
                                <label class="ui-label">附件：</label>{$vals.attach}
                                </li>
                                <li>
                                    <label class="ui-label">备注：</label>
                                    <div class="textarea w-4">
                                        <textarea  id="txtRemark2"  maxlength="100" name="applyMemo">{$vals.apprMemo}</textarea>
                                    </div>
                                    <span class="ui-info vertical-bottom"><span name='spancount'>0</span>/100</span>                                    
                                   
                                 </li>
                                 <li class="ui-btn">
                                    <a href="javascript:void(0);" class="btn btn-important" id="J-Submit-Button2">通 过<b class="btn-inner"></b></a>
  									<a class="btn btn-important" href="javascript:void(0);" id="J-Cancle-Button2">拒 绝<b class="btn-inner"></b></a>
  									<a url="/admin/Rechargemange/index?parma=opt2&exceptId={$vals.id}&status={$vals.status}" id="J-Return-Button2" class="btn btn-link">返 回<b class="btn-inner"></b></a>
                                 </li>
                            </ul>
<!--                        </form>-->                        
                        
						<!--<form action="/admin/Rechargemange/index?parma=opt1&optionType=5" method="POST" id="J-form5" style="display:none">-->
                            <ul class="ui-form" id="J-form5" style="display:none">
                                <li>
                                <input type="hidden" name="exceptId" value="{$vals.id}"/>
                                <label class="ui-label">附件：</label>{$vals.attach}
                                </li>
                                <li>
                                   	<label class="ui-label">备注：</label>
                                    <div class="textarea w-4">
                                        <textarea  id="txtRemark" name="applyMemo" maxlength="100">{$vals.apprMemo}</textarea>
                                    </div>
                                    <span class="ui-info vertical-bottom"><span name='spancount'>0</span>/100<div class="ui-check"><i class="error"></i><span >必填：没收原因</span></div></span>   
                                    
                                </li>
                                <li class="ui-btn">
                                    <a href="javascript:void(0);" class="btn btn-important" id="J-Submit-Button5">通 过<b class="btn-inner"></b></a>
                                    <a class="btn btn-important" href="javascript:void(0);" id="J-Cancle-Button5">拒 绝<b class="btn-inner"></b></a>
 									<a url="/admin/Rechargemange/index?parma=opt2&exceptId={$vals.id}&status={$vals.status}" id="J-Return-Button5" class="btn btn-link">返 回<b class="btn-inner"></b></a>
                                </li>
                            </ul>
                      <!-- </form>-->
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="currApprer" value="{$currApprer}"/>
	<input type="hidden" id="isLocked" value="{$isLocked}"/>
{include file='/admin/script-base.tpl'}
<script type="text/javascript" src="{$path_js}/js/jquery.uploadprogress.0.3.js"></script>
{literal}
<script>
var msg = new phoenix.Message(),msg1 = new phoenix.Message();
jQuery(function () {
	$(".closeBtn").click(function(){
		window.location.href="/admin/Rechargemange/index?parma=sv1&swithval=1&tabIndex=2";
	});
	if($('#currApprer').val()){
		msg.show({
			mask: true,
			content: '<h3 style="height:30px;line-height:30px;text-align:center; ">该订单正在被'+$('#currApprer').val()+'编辑</h3><div style="height:30px;line-height:30px;"></div>',
			confirmIsShow: 'true',
			confirmText: '确定',
			confirmFun: function(){
				window.location.href="/admin/Rechargemange/index?parma=sv1&swithval=1&tabIndex=2";
			}
		});
	}
	if($('#isLocked').val()){
			msg.show({
				mask: true,
				content: '<h3 style="height:30px;line-height:30px;text-align:center; ">锁定订单失败</h3><div style="height:30px;line-height:30px;"></div>',
				confirmIsShow: 'true',
				confirmText: '确定',
				confirmFun: function(){
					window.location.href="/admin/Rechargemange/index?parma=sv1&swithval=1&tabIndex=2";
				}
			});
	}
	jQuery('#upload_form').uploadProgress({ 
		progressURL:'/admin/Rechargemange/upload',
		displayFields : ['kb_uploaded','kb_average','est_sec'],
		success: function(data) { 
				if($('#idCard0').val() !='' || $('#idCard1').val()!='' || $('#idCard2').val()!=''){
					if(data.displayFields[0]){
						jQuery('input[type=submit]',this).val('上传成功');
						jQuery('input[type=submit]',this).attr("disabled","disabled");	
					}
					else
					{
						 jQuery('input[type=submit]',this).val('上传失败');											
						 jQuery('input[type=submit]',this).removeAttr("disabled");	
					}
				
			}
			else
			{
				msg.show({
					mask: true,
					content: '<h3 style="height:30px;line-height:30px;text-align:center; ">请选择要上传扫描件</h3><div style="height:30px;line-height:30px;"></div>',
					confirmIsShow: 'true',
					confirmText: '确定',
					confirmFun: function(){
						location.reload();
					}
				});
				return false;
			}
		
		}
	});
});
</script>
<script>
(function() {
	var minWindow,mask,initFn,arg = GetRequest();;		
	minWindow = new phoenix.MiniWindow();
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});		
	
	//一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuRechargemange');
		
	//获取url中"?"符后的字串
	function GetRequest() {
		var url = location.search;   
		var json = {};
		if (url.indexOf("?") != -1) {
			var str = url.substr(1);
			strs = str.split("&");
			for (var i = 0; i < strs.length; i++) {
				json[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
			}
		}
		return json;
	}
	
	function GetPostText(id)
	{
		var sName=id+" *";
		var sText="";
		var sPost="";
		$(sName).each(function(index, element) {
			 if($(this).is("input") || $(this).is("textarea") || $(this).is("select"))
			 {
				 sText=$(this).attr("name")+"="+$(this).val()+"&";
				 sPost+=sText;
		     }
        });
		return sPost;
	}
	//审核操作
	function submitAuit(status,type){
		var str = '';
		
		if(type ==1){
			str += '确定通过审核?';
		} else {
			str += '确定拒绝通过审核?';
		}
		var exceptId = $("[name='exceptId']").val();
		var applyMemo = $("[name='applyMemo']").val();
                var isConfirmed = false;
		msg.show({
			mask: true,
			content: '<h3 style="height:30px;line-height:30px;text-align:center; ">'+str+'</h3><div style="height:30px;line-height:30px;"></div>',
			confirmIsShow: 'true',
			confirmText: '确定',
			confirmFun: function(){
                            if(!isConfirmed){
                                isConfirmed = true;
                                var sData="exceptId="+exceptId+"&status="+status+"&type="+type+'&applyMemo='+applyMemo;
                                $.ajax({
                                        url:'/admin/Rechargemange/index?parma=rechargeReAuit',			
                                        dataType:'json',
                                        method:'post',
                                        data:sData,
                                        beforeSend: function()
                                        {
                                                isLock=false;
                                                $(this).attr({"disabled":"disabled"});
                                        },					
                                        success:function(data){
                                                msg.hide();
                                                msg1.show({
                                                                mask: true,
                                                                content: '<h3 style="height:30px;line-height:30px;text-align:center; ">'+data.data+'</h3><div style="height:30px;line-height:30px;"></div>',
                                                                confirmIsShow: 'true',
                                                                confirmText: '确定',
                                                                confirmFun: function(){
                                                                    location.href='/admin/Rechargemange/index?parma=sv1&tabIndex=2';
                                                                }
                                                });

                                        },
                                        complete: function()
                                        {
                                                isLock=true;
                                                $(this).removeAttr("disabled");
                                        }				
                                });
                            }
			},
			cancelIsShow: true,
			cancelText: '取消',
			cancelFun: function(){
				this.hide();
			}
		});
	}
	//返回操作
	function cancleBtnClick(surl){
		$.ajax({
			type:'get',
			dataType:'json',
			cache:false,
			url:surl,						
			success:function(data){								
				if(data['status']=="ok"){	
				   window.location="/admin/Rechargemange/index?parma=sv1&tabIndex=2";
				}
			}
		});
	}
	//导航及表单加载与删除
	var aid = $('#type').val();
	if (arg != undefined && aid != undefined) {
		switch(aid){
			case '8':
				$('#titleDescription,#title2Description').html("加游戏币");		
				$('#J-form1').show();
				$('#J-form2,#J-form5').remove();
				AddGameCurrency();
				break;
			case '3':
				$('#titleDescription,#title2Description').html("退款");
				$('#J-form2').show();
				$('#J-form1,#J-form5').remove();
				RefundOperating();
				break;
			case '9':
				$('#titleDescription,#title2Description').html("没收");
				$('#J-form5').show();
				$('#J-form2,#J-form1').remove();
				Expropriate();
				break;
			default:
				$('#J-form1,#J-form2,#J-form5').remove();
				$('#titleDescription,#title2Description').html("数据异常");	
				msg.show({
					mask: true,
					content: '<h3 style="height:30px;line-height:30px;text-align:center; ">数据异常</h3><div style="height:30px;line-height:30px;"></div>',
					confirmIsShow: 'true',
					confirmText: '确定',
					confirmFun: function(){
						msg.hide();
					}
				});
				window.location.href='/admin/Rechargemange/index?parma=sv1&swithval=1&tabIndex=2';	
		}
	}
	//---------------------------游戏加币检验及事件处理----------------------------------------
	function AddGameCurrency(){
		
		$("#J-Return-Button1").click(function(){
			var surl=$(this).attr("url");
			cancleBtnClick(surl);
		});
		
		//备注已输入字符长度动态
		$('#txtRemark1').keyup(function() {
		 $('[name="spancount"]').html($('#txtRemark1')[0].value.length);
		});
		
		$('#J-Submit-Button1').click(function(){
			submitAuit(8,1)
		});
		
		$('#J-Cancle-Button1').click(function(){
			submitAuit(8,2)
		});	
			
	}
	//---------------------------(退款)检验及事件处理----------------------------------------
	function RefundOperating(){
		//备注已输入字符长度动态
		$('#txtRemark2').keyup(function() {
		 $('[name="spancount"]').html($('#txtRemark2')[0].value.length);
		});		
		$('#J-Submit-Button2').click(function(){
			submitAuit(3,1);
		});	
		$('#J-Cancle-Button2').click(function(){
			submitAuit(3,2)
		});
		$("#J-Return-Button2").click(function(){
			var surl=$(this).attr("url");
			cancleBtnClick(surl);
		});	
	}
	//------------------------------没收-----------------------------------
	function Expropriate(){
		$('#txtRemark').keyup(function() {
		 $('[name="spancount"]').html($('#txtRemark')[0].value.length);
		});				
		
		$("#J-Return-Button5").click(function(){
			var surl=$(this).attr("url");
			cancleBtnClick(surl);
		});	
		
		$('#J-Submit-Button5').click(function(){
			submitAuit(9,1);
		});	
		$('#J-Cancle-Button5').click(function(){
			submitAuit(9,2)
		});		
		
	}
})();	
</script>
{/literal}
</body>
</html>