	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; <a href="/admin/Rechargemange/index?parma=sv1">异常充值处理</a> &gt;  <span id="titleDescription"></span></div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
                    	
						<h3 class="ui-title">异常处理 - <sapn id="title2Description"></span></h3>
                        
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
									<td>{$vals.revBankInfo.name}<br /> {$vals.rcvCardNumber}{$vals.rcvEmail}</td>
									<td><span style="color:red;">{$vals.realChargeAmt}</span>元</td>
									<td><span style="color:red;">{$vals.mcBankFee}</span>元</td>
									<td>{$vals.memo}</td>
									<td>{$vals.bankName}<br/> {$vals.bankAddr}</td>
									<input type ="hidden" id ="mcSn" value="{$vals.mcSn}" />
								</tr>
							</tbody>
						</table>
						<!-- <form action="/admin/Rechargemange/index?parma=opt1&optionType=1" enctype="multipart/form-data" method="POST" id="J-form1" style="display:none">-->
                            <ul class="ui-form" id="J-form1" style="display:none">
                                <li>
                                    <label class="ui-label big">确认加游戏币信息：</label>
                                </li>
                                <li>
                                   <label class="ui-label">用户名：</label>
                                    <input type="hidden" name="exceptId" value="{$vals.id}"/>
                                    <input type="text" class="input w-4" value="" name="userName1" id="userName1"/>
                                    <div class="ui-check"><i class="error"></i><span id="usernames">请输入用户名(4-16位)</span></div>
                                    <div id="userinfo" style="display:none"><span class='user-level' id="userinfo2">用户层级：</span></div>                                    
                                     <!--<span class='color-green'>242354356</span> &gt; <span>thtrhft</span> &gt; <span>hgtdhthtf</span> &gt; <span class="color-red">vava</span></span>-->
                                </li>
                                <li>
                                    <label class="ui-label">确认用户名：</label>
                                    <input type="text" class="input w-4" value="" id="userName2" name="userName2">                                   
                                    <div class="ui-check"><i class="error"></i><span id="error2">两次用户名输入不一致</span></div>
                                </li>
                                <li>
                                <label class="ui-label">附件：</label>{$vals.attach}
								<form id="upload_form" action="/admin/Rechargemange/upload" method="post" enctype="multipart/form-data">
								<fieldset style=" text-align:left; margin-left:150px">					
						        <input type="file" id="idCard0" name="idCard0" value="添加附件1"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; ">
				            	</fieldset>
								<!--<input type="file" value="添加附件" class="file" accept="/frontend/upload/images">-->
								<fieldset style=" text-align:left; margin-left:150px">					
							    <input type="file" id="idCard1" name="idCard1" value="添加附件2"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; "/>						
						        </fieldset>
								<fieldset style=" text-align:left; margin-left:150px">					
							    <input type="file" id="idCard2" name="idCard2" value="添加附件3"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; "/>	
								<span class="ui-text-prompt">&nbsp;
								<input type="submit" name="submit" value="上传所有相片" id="submit" />支持rar、jpg、png格式，大小不超过2M，最多可添加三条</span>	 <div class="ui-check"><i class="error"></i><span id="error3">请选择附件</span></div>
					        	</fieldset>
								</form>
                                
                                   <!-- <label class="ui-label">附件：</label>
                                    <input type="file" class="file" value="添加附件" name="attachMent">
                                    <span class="ui-text-prompt">支持rar、jpg、png格式，大小不超过2M，最多可添加三条</span>-->
                                </li>
                                <li>     
                                    <label class="ui-label">备注：</label>
                                    <div class="textarea w-4">
                                        <textarea  id="txtRemark1" name="applyMemo"   maxlength="100" >{$vals.apprMemo}</textarea>                                        
                                    </div>
                                    <span class="ui-info vertical-bottom"><span name='spancount'>0</span>/100</span>
                                    
                                </li>
                                <li class="ui-btn">
                                    <a class="btn btn-important" href="javascript:void(0);" id="J-Submit-Button1">发起加币<b class="btn-inner"></b></a>
									<a class="btn btn-link" url="/admin/Rechargemange/index?parma=opt2&exceptId={$vals.id}&status={$vals.status}" id="J-Return-Button1">返 回<b class="btn-inner"></b></a>
                                </li>
                            </ul>
                         <!--</form>-->
				
						 <!--<form action="/admin/Rechargemange/index?parma=opt1&optionType=2" enctype="multipart/form-data" method="POST" id="J-form2" style="display:none">-->
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
								<form id="upload_form" action="/admin/Rechargemange/upload" method="post" enctype="multipart/form-data">
								<fieldset style=" text-align:left; margin-left:150px">					
						        <input type="file" id="idCard0" name="idCard0" value="添加附件1"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; ">
				            	</fieldset>
								<!--<input type="file" value="添加附件" class="file" accept="/frontend/upload/images">-->
								<fieldset style=" text-align:left; margin-left:150px">					
							    <input type="file" id="idCard1" name="idCard1" value="添加附件2"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; "/>						
						        </fieldset>
								<fieldset style=" text-align:left; margin-left:150px">					
							    <input type="file" id="idCard2" name="idCard2" value="添加附件3"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; "/>	
								<span class="ui-text-prompt">&nbsp;
								<input type="submit" name="submit" value="上传所有相片" id="submit" />支持rar、jpg、png格式，大小不超过2M，最多可添加三条</span>	
					        	</fieldset>
								</form>
                                    <!--<label class="ui-label">附件：</label>
                                    <input type="file" value="添加附件" class="file" name="attachMent">
                                    <span class="ui-prompt">支持rar、jpg、png格式，大小不超过2M，最多可添加三条</span>-->
                                </li>
                                <li>
                                    <label class="ui-label">备注：</label>
                                    <div class="textarea w-4">
                                        <textarea  id="txtRemark2"  maxlength="100" name="applyMemo">{$vals.apprMemo}</textarea>
                                    </div>
                                    <span class="ui-info vertical-bottom"><span name='spancount'>0</span>/100</span>                                    
                                   
                                 </li>
                                 <li class="ui-btn">
                                    <a href="javascript:void(0);" class="btn btn-important" id="J-Submit-Button2">发起退款<b class="btn-inner"></b></a>
  									<a url="/admin/Rechargemange/index?parma=opt2&exceptId={$vals.id}&status={$vals.status}" id="J-Return-Button2" class="btn btn-link">返 回<b class="btn-inner"></b></a>
                                 </li>
                            </ul>
						<!--</form>-->                        
						                      
                        
						<!--<form action="/admin/Rechargemange/index?parma=opt1&optionType=5" method="POST" id="J-form5" style="display:none">-->
                            <ul class="ui-form" id="J-form5" style="display:none">
                                <li>
                                <input type="hidden" name="exceptId" value="{$vals.id}"/>
                                <label class="ui-label">附件：</label>{$vals.attach}
                                	<form id="upload_form" action="/admin/Rechargemange/upload" method="post" enctype="multipart/form-data">
                                    <fieldset style=" text-align:left; margin-left:150px">					
                                    <input type="file" id="idCard0" name="idCard0" value="添加附件1"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; ">
                                    </fieldset>
                                    <!--<input type="file" value="添加附件" class="file" accept="/frontend/upload/images">-->
                                    <fieldset style=" text-align:left; margin-left:150px">					
                                    <input type="file" id="idCard1" name="idCard1" value="添加附件2"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; "/>						
                                    </fieldset>
                                    <fieldset style=" text-align:left; margin-left:150px">					
                                    <input type="file" id="idCard2" name="idCard2" value="添加附件3"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; "/>	
                                    <span class="ui-text-prompt">&nbsp;
                                    <input type="submit" name="submit" value="上传所有相片" id="submit" />支持rar、jpg、png格式，大小不超过2M，最多可添加三条</span>	
                                    </fieldset>
                                    </form>
                                   <!-- <label class="ui-label">附件：</label>
                                    <input type="file" value="添加附件" class="file" name="attachMent">
                                    <span class="ui-prompt">支持rar、jpg、png格式，大小不超过2M，最多可添加三条</span>-->
                                </li>
                                <li>
                                   	<label class="ui-label">备注：</label>
                                    <div class="textarea w-4">
                                        <textarea  id="txtRemark" name="applyMemo" maxlength="100">{$vals.apprMemo}</textarea>
                                    </div>
                                    <span class="ui-info vertical-bottom"><span name='spancount'>0</span>/100<div class="ui-check"><i class="error"></i><span >必填：没收原因</span></div></span>   
                                    
                                </li>
                                <li class="ui-btn">
                                    <a href="javascript:void(0);" class="btn btn-important" id="J-Submit-Button5">确认没收<b class="btn-inner"></b></a>
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
var msg = new phoenix.Message();
jQuery(function () {
	$(".closeBtn").click(function(){
		window.location.href="/admin/Rechargemange/index?parma=sv1&swithval=1";
	});
	if($('#currApprer').val()){
		msg.show({
			mask: true,
			content: '<h3 style="height:30px;line-height:30px;text-align:center; ">该订单正在被'+$('#currApprer').val()+'编辑</h3><div style="height:30px;line-height:30px;"></div>',
			confirmIsShow: 'true',
			confirmText: '确定',
			confirmFun: function(){
				window.location.href="/admin/Rechargemange/index?parma=sv1&swithval=1";
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
					window.location.href="/admin/Rechargemange/index?parma=sv1&swithval=1";
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
	
	$('.menu-list li:eq(5)').attr("class","current");
	$('.col-side .nav dd:eq(0)').attr("class","current");
		
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
	
	//导航及表单加载与删除
	var aid = arg["optionType"];
	if (arg != undefined && aid != undefined) {
		switch(aid){
			case '1':
				$('#titleDescription,#title2Description').html("加游戏币");		
				$('#J-form1').show();
				$('#J-form2,#J-form5').remove();
				AddGameCurrency();
				break;
			case '2':
				$('#titleDescription,#title2Description').html("退款");
				$('#J-form2').show();
				$('#J-form1,#J-form5').remove();
				RefundOperating();
				break;
			case '5':
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
				window.location.href='/admin/Rechargemange/index?parma=sv1&swithval=1';	
		}
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
				   window.location="/admin/Rechargemange/index?parma=sv1&tabIndex=1";
				}
			}
		});
	}
	//初审操作
	function submitBtnClick(sdata){
		$.ajax({
			url:'/admin/Rechargemange/index',				
			dataType:'json',
			method:'post',
			data:sdata,				
			success:function(data){				
				msg.show({
					mask: true,
					content: '<h3 style="height:30px;line-height:30px;text-align:center; ">'+data.data+'</h3><div style="height:30px;line-height:30px;"></div>',
					confirmIsShow: 'true',
					confirmText: '确定',
					confirmFun: function(){
						if(data['status']=="ok"){	
							   window.location="/admin/Rechargemange/index?parma=sv1&tabIndex=1";
						}else if(data['status']=="StatusError"){
							msg.hide();
							window.location="/admin/Rechargemange/index?parma=sv1&tabIndex=1";
						}else {
							msg.hide();
						}
						
					}
				});
						   
			},			
	    });
	}
	//---------------------------游戏加币检验及事件处理----------------------------------------
	function AddGameCurrency(){
		var form = $('#J-form1'),userName1,userName1Par,userName2,userName2Par,fileCardPar,isture="0",	
		fileCardPar=$("#upload_form").parent();
		//表单检测错误数量
		errorTypes = ['userName1','userName2','fildCard'],
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
			if($.trim($('#userName1')[0].value)=='' ||$.trim($('#userName1')[0].value).length>=32){
				userName1Par.find('.ui-check').css('display', 'inline');	
				$('#usernames').html('请输入正确的用户名');
				setErrorNum('userName1', 1);	
			}
			if($.trim($('#userName2')[0].value)=='')
			{
				userName2Par.find('.ui-check').css('display', 'inline');				
				$('#error2').html('请确认用户名');
				setErrorNum('userName2', 1);
			}else{
				if($.trim($('#userName1')[0].value) != $.trim($('#userName2')[0].value)){
					$('#error2').html('两次用户名输入不一致');
					setErrorNum('userName2', 1);
				}
			}
		}	
		
		$("#J-Return-Button1").click(function(){
			var surl=$(this).attr("url");
			cancleBtnClick(surl);
		});
		
		//备注已输入字符长度动态
		$('#txtRemark1').keyup(function() {
		 $('[name="spancount"]').html($('#txtRemark1')[0].value.length);
		});		
		//用户名验,判断是否已存在
		userName1 = $('#userName1');
		userName1Par = userName1.parent();
		userName1.blur(function(){
			var v = $.trim(this.value);
			$('#userinfo2').html("").removeClass();
			if(v!='' ||v.length>=32){			
				$.ajax({
					type:'post',
					dataType:'json',
					cache:false,
					url:'/admin/Rechargemange/index?parma=getlevel',			
					data:'username='+v,		
					beforeSend: function () {
						$('#userName1').val('------------------查询中---------------');
                        $('#userName1').attr("disabled", "true");
                    },
					success:function(data){								
						if(data['status']=="1"){	
							//展现关系层	
							userName1Par.find('.ui-check').css('display', 'none');	setErrorNum('userName1', -1);					
							$('#userinfo').css("display","inline");				
							var top=(data["data"]["top"]=="")?"":"<span class='light'>"+data["data"]["top"]+"</span>";
							var mid=(data["data"]["mid"]=="")?"":"<span>&gt;"+data["data"]["mid"]+"</span>";
							var low=(data["data"]["low"]=="")?"":"&gt;<span class='color-red'>"+data["data"]["low"]+"</span>";
							var sinfo=	"用户层级："+top+mid+low;		
							$('#userinfo2').append(sinfo).addClass("user-level");	
							isture="1";						
						}
						else{
							userName1Par.find('.ui-check').css('display', 'inline');	
							$('#usernames').html('用户名不存在!');
							setErrorNum('userName1', 1);								
						}
					},
					complete:function(){
						setTimeout(function(){
							$('#userName1').val(v);
							$('#userName1').removeAttr("disabled"); 
						}, 900);
						
					}
				});
			}
			else if(v==''||v.length>=32 ){	userName1Par.find('.ui-check').css('display', 'inline');	setErrorNum('userName1', 1);	}
			else{	userName1Par.find('.ui-check').css('display', 'none');	setErrorNum('userName1', -1);}			
		});	
			//金额验证
		userName2 = $('#userName2');
		userName2Par = userName2.parent();	
		userName2.blur(function(){
			var v = $.trim(this.value);
			if(v == ''){			
				userName2Par.find('.ui-check').css('display', 'inline');				
				$('#error2').html('请输入');
				setErrorNum('userName2', 1);
			}else if(v != $.trim($('#userName1')[0].value)){
				userName2Par.find('.ui-check').css('display', 'inline');	
				$('#error2').html('两次用户名输入不一致');
				setErrorNum('userName2', 1);
			}else{				
				userName2Par.find('.ui-check').css('display', 'none');
				setErrorNum('userName2', -1);
			}			
		})
		
		$('#J-Submit-Button1').click(function(){
			var err = 0;
			checkform();
			$.each(errorTypes, function(){
				if(typeof errorHas[this] != 'undefined'){
					err += errorHas[this];
				}
			});	
			if(err > 0 ){
				return false;
			}

			var sdata='parma=opt1&optionType=1&status=8&'+GetPostText("#J-form1")+'&mcSn='+$("#mcSn").val();
			submitBtnClick(sdata);
		});		
			
	}
	//---------------------------(退款)检验及事件处理----------------------------------------
	function RefundOperating(){
		var form = $('#J-form2');
		//备注已输入字符长度动态
		$('#txtRemark2').keyup(function() {
		 $('[name="spancount"]').html($('#txtRemark2')[0].value.length);
		});		
		$('#txtBankAdrr').keyup(function(){
			 $('[name="spancount2"]').html($('#txtBankAdrr')[0].value.length);
		});
		$('#J-Submit-Button2').click(function(){
			var sdata='parma=opt1&optionType=2&status=3&'+GetPostText("#J-form2")+'&mcSn='+$("#mcSn").val();
			submitBtnClick(sdata);
		});	
		$("#J-Return-Button2").click(function(){
			var surl=$(this).attr("url");
			cancleBtnClick(surl);
		});	
	}
	
	//------------------------------没收-----------------------------------
	function Expropriate(){
		var form = $('#J-form5'),txtRemark,txtRemarkPar;	
		$('#txtRemark').keyup(function() {
		 $('[name="spancount"]').html($('#txtRemark')[0].value.length);
		});				
		//表单检测错误数量
		var errorTypes = ['txtRemark'],
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
		
		//收款银行卡号,Luhm校验
		txtRemark = $('#txtRemark');
		txtRemarkPar = txtRemark.parent().parent();	
		txtRemark.blur(function(){
			var v = $.trim($(this).val());
			if(v.length == 0 ){
				txtRemarkPar.find('.ui-check').css('display', 'inline');				
				setErrorNum('txtRemark', 1);
			}else if(v.length > 100)
			{
				txtRemarkPar.find('.ui-check').css('display', 'inline');				
				setErrorNum('txtRemark', 1);
			}
			else{				
				txtRemarkPar.find('.ui-check').css('display', 'none');
				setErrorNum('txtRemark', -1);
			}			
		}).focus(function(){
			txtRemarkPar.find('.ui-check').css('display', 'none');
			
		});
		
		//表单检测
		checkform = function(){
			var v = $.trim($('#txtRemark').val());
			if(v.length == 0){
				txtRemarkPar.find('.ui-check').css('display', 'inline');			
				setErrorNum('txtRemark', 1);
			}else if(v.length > 100)
			{
				txtRemarkPar.find('.ui-check').css('display', 'inline');			
				setErrorNum('txtRemark', 1);
			}
			else{				
				txtRemarkPar.find('.ui-check').css('display', 'none');
				setErrorNum('txtRemark', -1);
			}			
			
		}	
		$("#J-Return-Button5").click(function(){
			var surl=$(this).attr("url");
			cancleBtnClick(surl);
		});	
		
		$('#J-Submit-Button5').click(function(){
			var err = 0;
			checkform();
			$.each(errorTypes, function(){
				if(typeof errorHas[this] != 'undefined'){
					err += errorHas[this];
				}
			});	
			if(err > 0 ){
				//e.preventDefault();
				return false;
			}
			var sdata='parma=opt1&optionType=5&status=9&'+GetPostText("#J-form5")+'&mcSn='+$("#mcSn").val();
			submitBtnClick(sdata);
		});		
		
	}
})();	
</script>
{/literal}
</body>
</html>