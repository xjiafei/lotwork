	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">

			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt;<a href="/admin/Opterators/index?parma=sv1">人工资金操作审核流程</a> &gt; 创建新人工单</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title">人工资金操作建单</h3>
						<ul class="ui-form">
							<li>
								<label class="ui-label big">建单类型：</label>
								<select class="ui-select w-3" id="bankName">
									<option value="0">请选择人工建单类型</option>
									{foreach from=$_arrayType item=data key=key}
	                                   	{if $key eq '1' or $key eq '2'}
	                                   		<option value="{$key}">{$data}</option>
                                       {else if $key eq '3' or $key eq '4' or $key eq '5' or $key eq '6' or $key eq '9' or $key eq '10' or $key eq '11' or $key eq '12' or $key eq '13'}
                                       		<option value="{$key}">{$data}</option>
                                       {else if $key eq '7' or $key eq '8'}
                                       		<option value="{$key}">{$data}</option>
                                       {/if}  
	                               {/foreach} 
								</select>
								<span class="ui-text-info" style="color:#FF0000;">请慎重选择建单类型，并填写清楚建单原因</span>
                                <div class="ui-check"><i class="error"></i><span >请选择建单类型</span></div>
							</li>
							<li>
								<label class="ui-label big">涉及用户名：</label>
								<input type="text" value="" class="input w-4" id="txtUser">						
								<input type="hidden" value="" id="userid">						
								<input type="hidden" value="" id="availBal">						
								<input type="hidden" value="" id="canWithdrawBal">						
                                <input type="button" class="btn btn-important" name="btnCheack" value="确 定" id="btnCheack"/><b class="btn-inner"></b> <div class="ui-check"><i class="error"></i><span >请填写涉及用户名</span></div>
							</li>
						</ul>
						<!--style="display:none;"-->
						<ul class="ui-form" style="display:none;" id="Div-From">
							<li>
								<label class="ui-label">建单类型：</label>
								<span class="ui-info" id="typeCreate"></span>
							</li>
							<li>
								<label class="ui-label">类型说明：</label>
								<span class="ui-info" id="typeMessage"></span>
								<span class="ui-text-info" id="typeInfo"></span>
							</li>
							<li>
								<label class="ui-label" id="selectBank">选择银行卡：</label>
								<div class="bank-more">
								<div class="bank-label">
									<span style="display:none;" id="BankIco" class="ico-bank CCB" title="建设银行"></span>
									<span class="bank-drop-text"  id="selectBankEr">- 请选择银行 -</span>
									<input type="hidden"   name="id"  />
									<a href="javascript:void(0);" class="bank-drop"><i class="ico-down" id="icoName"></i></a>
								</div>
                                <div class="ui-check"><i class="error"></i><span >请选择银行</span></div>
								<div style="display:none;" class="bank-more-content">
									<table id="tabbank">
										<tbody>
										</tbody>
									</table>
									<div class="bank-list" id="banklistinfo">
									</div>
									<input type="hidden" name="status" id="bankselect" value="" /> 
                                   	<input type="hidden" name="type" value="0" />
								</div>
							</div>
							</li>
							<li>
								<li>
									<label class="ui-label" id="lbnote">备注：</label>
									<div class="textarea h-1 w-4">
	                                    <textarea  id="note" name="note" maxlength="30" ></textarea>

	                                </div>
	                                 <span class="ui-info vertical-bottom">选填项</span>
	                                <span class="ui-text-info vertical-bottom" style="color:#FF0000;"> 以上信息内容将会推送给客户
	                                	<div class="ui-check"><i class="error"></i><span >以上信息内容将会推送给客户</span></div></span>     
								</li>
								<label class="ui-label" id="lblMoney">打款金额：</label>
								<input type="text" value="" class="input w-4" name="UserMoney">
                                 <div class="ui-check"><i class="error"></i><span >请填写打款金额</span></div>
                                <span class="ui-text-info" id="charge_price" style="display:none">元</span>		
								<i class="check-right"></i>
								<div id="reduceMoney">			
	                                <span class="ui-check"><i class="error"></i>扣币金额不能超过用户可用余额<span id="txtreducemoney"></span>元</span>
	                            </div>
	                            <div id="withdrawMoney">			
	                                <span class="ui-check"><i class="error"></i>提现金额不能超过用户可提现额度<span id="txtwithdrawmoney"></span>元</span>
	                            </div>
	                            <div name="initialMoney">			
	                                <span class="ui-prompt"><div  id="div_pro" style="display:none">充值限额：最低<span name="txtminmoney"></span>元，最高<span name="txtmaxmoney"></span>元</div></span>									
	                                <span class="ui-check"><i class="error"></i>充值限额：最低<span name="txtminmoney"></span>元，最高<span name="txtmaxmoney"></span>元</span>
	                            </div>
                                 
							</li>
							<li>
							    	
								<label class="ui-label">附件：</label>
								<form id="upload_form" action="/admin/Opterators/upload" method="post" enctype="multipart/form-data">
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
								
							</li>
							<li>
								<label class="ui-label">建单原因：</label>	
                                <div class="textarea w-6">
                                    <textarea  id="text" name="text"   maxlength="100" ></textarea>                                        
                                </div>
                                <span class="ui-info vertical-bottom"><span name='spancount'>0</span>/100</span>
                                <span class="ui-text-info vertical-bottom" style="color:#FF0000;">请填写清楚建单原因<div class="ui-check"><i class="error"></i><span >请务必写明建单原因</span></div></span>                                
							</li>
							<!-- {if "FUND_MANUAL_PROCEDURE_CREATE"|in_array:$smarty.session.datas.info.acls} -->
							<li class="ui-btn">
								<!--<a href="javascript:void(0);" class="btn btn-important" id="J-Submit-Button">提 交<b class="btn-inner"></b></a>-->
                                <input type="button" value="提 交" class="btn btn-important" id="J-Submit-Button">
                                <input type="button" value="上一步" class="btn" id="J-Previous">
								<!--<a href="javascript:void(0);" class="btn" id="J-Previous">上一步<b class="btn-inner"></b></a>-->
							</li>
							<!-- {/if} -->
						</ul>
						
						
					</div>
				</div>
			</div>
		</div>
	</div>
{include file='/admin/script-base.tpl'}
<script type="text/javascript" src="{$path_js}/js/jquery.uploadprogress.0.3.js"></script>



{literal}
<script>
jQuery(function () {
	jQuery('#upload_form').uploadProgress({ 
		progressURL:'/admin/Opterators/upload',
		displayFields : ['kb_uploaded','kb_average','est_sec'],
		success: function(data) { 
				if($('#idCard0').val() !='' || $('#idCard1').val()!='' || $('#idCard2').val()!=''){
					if(data.displayFields[0]){
						jQuery('input[type=submit]',this).val('上传成功');
						jQuery('input[type=submit]',this).attr("disabled","disabled");	
					}
					else
					{											
						jQuery('input[type=submit]',this).removeAttr("disabled");	
					}
				
			}
			else
			{
				alert('请选择要上传扫描件');
				return false;
			}
		
		}
	});
});
</script>	
<script>
(function() {	
    var minWindow,mask,initFn,isShowCell=false,isture=false;
	minWindow = new phoenix.MiniWindow();
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});
	var msg = new phoenix.Message();
	//Tab	
	new phoenix.Tab({triggers:'.ui-tab-title2 li',panels:'.ui-tab-content',eventType:'click',currPanelClass:'ui-tab-content-current',index:0});
	
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuOpterators');	
	
	var inputs =  $('[name="UserMoney"]'),checkFn;				
		checkFn = function(){
			var me = this,v = me.value,index;
			
			var sSelectValue=$("#bankName option:selected").attr('value');
			
			if(sSelectValue == '8'){
				var sAvailBal=$.trim($("#availBal").val());
				if(parseInt(v) > parseInt(sAvailBal)){
					$('#reduceMoney').find('.ui-check').css("display","inline");
					//$('[name="UserMoney"]').val(sAvailBal);
					v = sAvailBal;
				} else {
					$('#reduceMoney').find('.ui-check').css("display","none");
					$('#reduceMoney').prev('.check-right').css("display","inline");
				}
			} else if (sSelectValue == '2'){
				var sCanWithdrawBal=$.trim($("#canWithdrawBal").val());
				if(parseInt(v) > parseInt(sCanWithdrawBal)){
					$('#withdrawMoney').find('.ui-check').css("display","inline");
					//$('[name="UserMoney"]').val(sCanWithdrawBal);
					v = sCanWithdrawBal;
				} else {
					$('#withdrawMoney').find('.ui-check').css("display","none");
					$('#withdrawMoney').prev('.check-right').css("display","inline");
				}
			}
			
			
			me.value = v = v.replace(/^\.$/g, '');		
			index = v.indexOf('.');
			if(index > 0){
				me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');			
				if(v.substring(index+1,v.length).length>2){				
					me.value= v  = v.substring(0, v.indexOf(".") + 3);
				}
			}		
			me.value = v = v.replace(/[^\d|^\.]/g, '');
			me.value = v = v.replace(/^00/g, '0');
		};
	inputs.keyup(checkFn);
	inputs.blur(checkFn);
		
	
   //银行卡展示与隐藏
	$('.bank-drop').click(function(){
		var classcss = $('#icoName').attr("class");		
		if(classcss=='ico-down'){
			$('#icoName').removeClass('ico-down').addClass('ico-up');			
			$('.bank-more-content').show();	
			$('#J-submit1').css("display","inline");
		}
		else{
			$('#icoName').removeClass('ico-up').addClass('ico-down');
			$('.bank-more').css("min-height","45px");
			$('.bank-more-content').css("display","none");
		} 		
	});		
                        
	 $(document).on('click','#tabbank tr',function(){
			var txtstatus = $(this).attr("pro_bankid").trim();
			var bankIco = $(this).attr("bankIco").trim();
			$('[name="id"]').attr("value", txtstatus);			
             $('[name="id"]').parent().parent().find('.ui-check').css('display', 'none');
			//当有绑定银行,显示相应银行图片(动态绑定)
			$('#BankIco').removeAttr("style").removeAttr("class").addClass("ico-bank "+bankIco).attr("title",bankIco).html('');	
			$('.bank-more-content').css("display","none");
			$('#selectBankEr').attr("style","display:none");
			$('.bank-more').css("min-height","45px");		
			$('#icoName').removeClass('ico-up').addClass('ico-down');		
	 }); 	 
    //通过
	$(document).on('click', '[name="btnCheack"]', function(){			
		selectUser();	
	});	
		
	$('#bankName').change(function(){
		var sVal=$(this).select().val();
		if(sVal==0)
		{
			$(this).parent().find('.ui-check').css('display', 'inline');
		}else
		{
			$(this).parent().find('.ui-check').css('display', 'none');
		}
	});
		
	$('#J-Submit-Button').click(function(){
		var isLock=true;		
		$(this).attr({"disabled":"disabled"});
		var sUserName=$.trim($("#txtUser").val());
		var sUserId=$.trim($("#userid").val());
		var sAvailBal=$.trim($("#availBal").val());
		var sCanWithdrawBal=$.trim($("#canWithdrawBal").val());
		var sSelectValue=$("#bankName option:selected").attr('value');		
		var sMoney=$.trim($('[name="UserMoney"]').val());
		var sBankId=$('[name="id"]').val();
		var sTxtReason=$.trim($("#text").val());
		var sTxtNote=$.trim($("#note").val());

		//type 建单类型 ，rcvAct 涉及用户名 ，bankCard 银行卡，depositAmt 打款金额 ，memo 建单原因
		if(sUserName==""){	
			$(sUserName).parent().find('.ui-check').css('display', 'inline');
			$("#txtUser").focus();
			$(this).removeAttr("disabled");
		    return false;
		}
	    if(sSelectValue=="0")
		{			
		   $("#bankName option:selected").parent().parent().find('.ui-check').css('display', 'inline');
		   $(this).removeAttr("disabled");
		   return false;
		} else if(sSelectValue == '8'){
			if(parseInt(sMoney) > parseInt(sAvailBal)){
				$('#reduceMoney').find('.ui-check').css("display","inline");
				$('[name="UserMoney"]').val(sAvailBal);
				return false;
			} else {
				$('#reduceMoney').find('.ui-check').css("display","none");
				$('#reduceMoney').prev('.check-right').css("display","inline");
			}
		} else if (sSelectValue == '2'){
			if(parseInt(sMoney) > parseInt(sCanWithdrawBal)){
				$('#withdrawMoney').find('.ui-check').css("display","inline");
				$('[name="UserMoney"]').val(sCanWithdrawBal);
			} else {
				$('#withdrawMoney').find('.ui-check').css("display","none");
				$('#withdrawMoney').prev('.check-right').css("display","inline");
			}
		}
	    
		if(!$("#selectBank").parent().is(":hidden"))
		{			
		   if(sBankId=="")
		   {
			  $('[name="id"]').parent().parent().find('.ui-check').css('display', 'inline');
			  $(this).removeAttr("disabled");
		      return false;
		   }
		}
		if(sMoney=="")
		{  	
			$('[name="UserMoney"]').focus();
		    $('[name="UserMoney"]').parent().find('.ui-check').css('display', 'inline');
			$(this).removeAttr("disabled");
		    return false;
		}else
		{
			 $('[name="UserMoney"]').parent().find('.ui-check').css('display', 'none');
		}
	    if(sTxtReason=="")
		{
			$("#text").focus();
			$("#text").parent().parent().find('.ui-check').css('display', 'inline');
			$(this).removeAttr("disabled");
		    return false;
		}else
		{
			$("#text").parent().parent().find('.ui-check').css('display', 'none');
		}
		if(isLock)
		{
		var sData = "rcvAct=" + sUserName + 
					"&depositAmt=" + sMoney + 
					"&memo=" + sTxtReason + 
					"&id=" + sBankId + 
					"&userId=" + sUserId + 
					"&sSelectValue=" + sSelectValue + 
					"&note=" + sTxtNote;
		$.ajax({
			url:'/admin/Opterators/index?parma=opter1',				
			dataType:'json',
			method:'post',
			data:sData,
			beforeSend: function()
			{
				isLock=false;
				//禁用发送								
				var button = $('#J-Submit-Button'),list = ['提交中   ', '提交中.  ', '提交中.. ', '提交中...'],i = 0;
				interval=setInterval(function(){
					button.val(list[i]);
					i += 1;
					if(i >= list.length){
						i = 0;
					}
				}, 300);
				button.attr("disabled","disabled");	
				$("#J-Previous").attr("disabled","disabled");	
			},					
			success:function(data){
				msg.show({
						mask: true,
						content: '<h3 style="height:30px;line-height:30px;text-align:center; ">'+data.data+'</h3><div style="height:30px;line-height:30px;"></div>',
						confirmIsShow: 'true',
						confirmText: '确定',
						confirmFun: function(){
							if(data.status =='ok'){
								location.href='/admin/Opterators/index?parma=sv1';
							} else {
								location.reload();
							}
						}
				});
			},
			complete: function()
			{
				isLock=true;
				clearInterval(interval);
				var button = $('#J-Submit-Button');
				button.val("提 交");	 	
				button.removeAttr("disabled","disabled");
				$("#J-Previous").removeAttr("disabled","disabled");
			}		
		});
		}
	});		
	//查询
	var selectUser=function(){
		var sUserName=$.trim($("#txtUser").val());
		var sValue=$("#bankName option:selected").attr('value');
		var sText=$("#bankName option:selected").text();
		if(sValue=='0')
		{
			$("#bankName option:selected").parent().parent().find('.ui-check').css('display', 'inline');
			return false;
		}
		if(sUserName=='')
		{
			$("#txtUser").parent().find('.ui-check').css('display', 'inline');
			$("#txtUser").parent().find('.ui-check').html("<i class='error'></i><span >请填写涉及用户名</span>");
			return false;
		}
		var sdata="username="+sUserName+"&type="+sValue;
		$.ajax({
			url:'/admin/Opterators/index?parma=qbbc',				
			dataType:'json',
			method:'post',
			data:sdata,	
			beforeSend:function(){	
				$('[name="btnCheack"]').val("查询中...");
				$('[name="btnCheack"]').attr('disabled',"true");
				
			},				
			success:function(data){		
				if(data!=null && data.status =='error'){
					$('[name="btnCheack"]').removeAttr("disabled"); 
					$("#txtUser").parent().find('.ui-check').html("<i class='error'></i><span >"+data.data+"</span>");
					$("#txtUser").parent().find('.ui-check').css('display', 'inline');
					return false;
				}
				if(data != null && data.status =='ok'){	   
				   $("#txtUser").parent().find('.ui-check').css('display', 'none');
				   $('[name="btnCheack"]').css("background","#CECECE");
				   $('#btnCheack,#bankName,#txtUser').attr("disabled", "true");					  
			       var resultAll = eval(data.data);
			       $("#userid").val(resultAll[0].userid);
			       $("#availBal").val(resultAll[0].availBal);
			       $("#canWithdrawBal").val(resultAll[0].canWithdrawBal);
				   var re = resultAll[0].userBankStruc;
				   var html = "";
				   $.each(re, function (i) {
				       html+="<tr pro_bankid="+re[i].id+"  bankIco='ico-bank "+re[i].bankIco+"'>";
					   html+="<td><span class='ico-bank "+re[i].bankIco+"'></span></td>";
					   html+="<td>开户人姓名："+re[i].bankAccount+"</td>";
					   html+="<td>银行卡号："+re[i].bankNumber+"</td></tr>";
				   });
				   
				   var re1 = resultAll[0].bankStruc;
				   var html1 = "";
				   $.each(re1, function (i) {
					   html1+='<span class="ico-bank '+re1[i].logo+'" name="'+re1[i].logo+'" id="'+re1[i].id+'"'; 
					   html1+=' max="'+re1[i].upLimit+'" min="'+re1[i].lowLimit+'"';
					   html1+=' title="'+re1[i].name+'充值限额：最低'+re1[i].lowLimit+',最高'+re1[i].upLimit+'"></span>';				   
				   });
				   
				   var message="";
				   var message2="";
				   switch(sValue)
				   {
				       case "1":
					   message="仅适用于运营活动中奖后，需要给用户现金派奖的运营场景";
					   message2="打款金额：";
					     break;
					   case "2":
					   message="适用于线上提现流程不能满足的特殊情况，如VIP客户暂时不能上网申请提现的紧急情况";
					   message2="打款金额：";
					   $('#txtwithdrawmoney').html($('#canWithdrawBal').val());
					     break;
					   case "3":
					   message="适用于各类运营活动用户中奖后，需要给用户增加游戏币的运营场景";
					   message2="游戏币数量：";
					     $("#selectBank").parent().attr("style","display:none");
					     break;
					   case "4":
					   message="仅适用于平台信赖的客户，由于暂时无法进行充值，申请先预支一部分游戏币的特殊情况";
					   message2="游戏币数量：";
					     $("#selectBank").parent().attr("style","display:none");
					     break;
					   case "5":
					   message="仅适用于平台对用户造成损失后，或客户合理投诉后，公司决定对用户损失进行赔偿的情景";
					   message2="游戏币数量：";
					   $("#selectBank").parent().attr("style","display:none");
					     break;
					   case "6":
					   message="适用于客户对平台提出优质建议、提出重要bug等后，平台决定对用户给予少量加币奖励的场景";
					   message2="游戏币数量：";
					   $("#selectBank").parent().attr("style","display:none");
					     break;
					   case "7":
					   message="在特殊情况下，如上下级充值错误，需要管理员进行调整";
					   message2="游戏币数量：";
					   $("#selectBank").parent().attr("style","display:none");
					     break;
					   case"8":
					   message="适用于人为因素造成加币错误，需要进行纠正的情况，如代理转账对象错误等";
					   message2="扣除的游戏币数量：";
					   $("#selectBank").parent().attr("style","display:none");
					   $('#txtreducemoney').html($('#availBal').val());
					     break;
					   case"9":
					   message="适用于线上充值流程不能满足的特殊情况，如客户大额充值";
					   message2="充值金额：";
					   $('#charge_price').attr("style","display:none");
					   $("#banklistinfo").html(html1);
					   var minmoney=0,maxmoney=0;
						$("#banklistinfo span").click(function () {
							var sValue=$("#bankName option:selected").attr('value');
							
							$("#banklistinfo span").removeClass("ico-bank-current");
							var id =$.trim($(this).attr("id"));	
							var txtstatus =$.trim($(this).attr("name"));	
							minmoney =$.trim($(this).attr("min"));
							maxmoney = $.trim($(this).attr("max"));	
							$(this).addClass("ico-bank ico-bank-current "+txtstatus);		
							$('[name="id"]').attr("value", id);		
							//当有绑定银行,显示相应银行图片(动态绑定)
							$('#selectBankEr').removeAttr("style").removeAttr("class").addClass("ico-bank "+txtstatus).attr("title",txtstatus).html('');	
							$('.bank-more-content').css("display","none");
							$('.bank-more').css("min-height","45px");	
							if(sValue==9)
							{
								$('[name="initialMoney"]').hide();
							}else
							{
							    $('#div_pro,[name="initialMoney"]').show();
							}
							$('#icoName').removeClass('ico-up').addClass('ico-down');
							$('[name="txtminmoney"]').html(minmoney);
							$('[name="txtmaxmoney"]').html(maxmoney);
							$("[name='UserMoney']").removeAttr("disabled");
							if($("#bankselect")[0].value!=''){
								//$('#selectBankEr').css('display', 'none');
							}
						});
					   break;
					   case "10":
						   message="对于使用分红模式的代理，派发分红时所用的人工单类型";
						   message2="游戏币数量：";
						   $("#selectBank").parent().attr("style","display:none");
					   break;
					   case "11":
						   message="彩票分红";
						   message2="游戏币数量：";
						   $("#selectBank").parent().attr("style","display:none");
					   break;
					  case "12":
						   message="当希望将活动金额累计计算到PT代理盈亏表中-活动奖励列，以便于在派发分红时扣除时，使用该类型进行礼金的人工派发自动转入PT";
						   message2="游戏币数量：";
						   $("#selectBank").parent().attr("style","display:none");
					   break;
					  case "13":
						   message="当希望将活动金额累计计算到PT代理盈亏表中-活动奖励列，以便于在派发分红时扣除时";
						   message2="游戏币数量：";
						   $("#selectBank").parent().attr("style","display:none");
					   break;

				   }
				   $("#typeCreate").text(sText);
				   $("#typeMessage").text(sText);
				   $("#typeInfo").text(message);
				   $("#lblMoney").text(message2);
				   $("#tabbank>tbody").html(html);
				   $(".ui-form").show();
				}
				else{
					$('[name="btnCheack"]').removeAttr("disabled"); 
					msg.show({
						mask: true,
						content: '<h3 style="height:30px;line-height:30px;text-align:center;">不存在此用户，请确认输入的用户正确!</h3><div style="height:30px;line-height:30px;"></div>',
						confirmIsShow: 'true',
						confirmText: '确定',
						confirmFun: function(){
							msg.hide();
						}
					});
				}
			
			},
			complete:function(){
				$('[name="btnCheack"]').val("确 定");
			}				
		});	
	}	
	
	//上一步
	$(document).on('click', '#J-Previous', function(){	
		location.reload();	
	});   	
	$('#text').keyup(function() {
	 $('[name="spancount"]').html($('#text')[0].value.length);
	});		
	$('#txtUser').keypress(function (e) {
		var currKey = 0, e = e || event;
		currKey = e.keyCode || e.which || e.charCode;
		if (currKey == 13) {
			selectUser();	
		}
	}); 
	

})();	
</script>
{/literal}
</body>
</html>