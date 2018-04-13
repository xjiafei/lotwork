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
						<h3 class="ui-title">发起异常处理</h3>
						<div class="exception-handling">查询出<em>3条</em>异常信息,请选择需要处理的异常</div>
						<table class="table " id="table_init">
							<thead>
								<tr>
									<th width="16"></th>
									<th>时间</th>
									<th>付款方</th>
									<th>金额</th>
									<th>附言</th>
									<th>MOW单号</th>
									<th>支行信息</th>
								</tr>
							</thead>  
							<tbody>
							{foreach $recorders as $recorder}
								<tr>
									<td class="table-tool"><a  name="foldchick" class="ico-unfold" title="展开" href="javascript:void(0);"></a></td>
									<td>{$recorder->getMember("memo")}</td><!--时间-->
									<td>{$recorder->getMember("bankId")}<br />{$recorder->getMember("cardAcct")}-{$recorder->getMember("cardNumber")}</td><!--付款方-->
									<td>{$recorder->getMember("realChargeAmt")}元</td><!--金额-->
									<td>{$recorder->getMember("memo")}</td><!--附言--> 
									<td>{$recorder->getMember("mcSn")}</td><!--MOW单号-->
									<td>{$recorder->getMember("bankName")}<br />{$recorder->getMember("bankAddr")}</td><!--支行信息--> 
								</tr>								
							{/foreach}	
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
      
<script src="{$path_js}/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="{$path_js}/js/phoenix.Common.js" type="text/javascript" ></script>
{literal}
<script>
(function() {
	var currentHide="",CurrentObj="",seriaStr="",strInput="",preStr="",div_radion="0";	
	//加游戏币，退款，没收处理，变量初始化
	var userName,userNamePar,userName2,userName2Par,addPS,addPSPar,errorTypes0 = ['userName','userName2','addPS'],errorHas0 = {},
		selectbank,selectbank,bankName,bankNamePar,bankCarnumber,bankCarnumberPar,bankCarnumber2,bankCarnumber2Par,addBankName,addBankNamePar,addBankAdrr,addBankAdrrPar,        bankPs,bankPsPar,errorTypes1 = ['bankName','bankCarnumber','bankCarnumber2','addBankName','addBankAdrr',,'bankPs'],errorHas1 = {},
		delPs,delPsPar,errorTypes2 = ['delPs'],errorHas2 = {};
		
	//一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuRechargemangeConfig');
	
	//展开
	//$("#table_init tr").click(function(){ 
//		var inittime = $(this).children("td:eq(1)").text(); 
//		var initpaly=$(this).children("td:eq(2)").text(); 
//		var initmoney=$(this).children("td:eq(3)").text()==''?0:$(this).children("td:eq(3)").text(); 
//		var initPS=$(this).children("td:eq(4)").text(); 
//		var initMOW=$(this).children("td:eq(5)").text(); 
//		var bankinfo=$(this).children("td:eq(6)").text(); 	
//		//$("#table_init tr").apendTo("#Div_Mode"); 
//		
//		
//		
//	});
	
	$("[name='foldchick']").click(function(){ 
		if (currentHide != "") {
			//得到原先input的值
			//for (var i = 1; i <= 4; i++) {
//				strInput += jQuery("#txtCol" + i).val();
//			}
			clearValue();	
			return false;			
		}		
		
		$(this).removeClass('ico-unfold').addClass("ico-fold");
		CurrentObj = jQuery(this);
		var strHtml="<tr class='bg-none'><td colspan='7'><ul class='ui-form'><li><label class='ui-label'for=''>处理方式：</label><label for='rad_Add'><input id='rad_Add'class='radio'type='radio'value='0'name='radios_n'checked='checked'>加游戏币</label><label for='rad_Refundd'><input id='rad_Refundd'class='radio'type='radio'style=' margin-left:10px'value='1'name='radios_n'>发起退款</label><label for='rad_Confiscate'><input id='rad_Confiscate'class='radio'type='radio'style=' margin-left:10px'value='2'name='radios_n'>没收处理</label></li><div id='div_Add'><li><label class='ui-label'>用户名：</label><input type='text'value=''class='input w-4'id='userName'><div class='ui-check'><i class='error'></i>用户名不存在或格式不正确(4-16位)</div></li><li><label class='ui-label'>确认用户名：</label><input type='text'value=''class='input w-4'id='userName2'><div class='ui-check'><i class='error'></i>两次用户名输入不一致或格式不正确(4-16位)</div></li><li><label class='ui-label'>附件：</label><input type='file'value='添加附件'class='file'id='fileIsTure'><div class='ui-text-prompt'>支持rar、jpg、png格式，大小不超过2M，最多可添加三条</div><div class='ui-check'><i class='error'></i>支持rar、jpg、png格式，大小不超过2M，最多可添加三条</div></li><li><label class='ui-label'for='text'>备注：</label><textarea name='content'class='textarea'id='addPS'style='width:218px;heght:72px;'></textarea><div class='ui-text-prompt'>长度不得超过50字符</div><div class='ui-check'><i class='error'></i>请输入正文,长度不得超过50字符</div></li><li class='ui-btn'><a href='javascript:void(0);'class='btn btn-important'id='J-Subjet1'>确 定<b class='btn-inner'></b></a><a href='javascript:void(0);'class='btn btn-link'id='div_close1'>返 回<b class='btn-inner'></b></a></li></div><div id='div_Refundd'style='display:none'><li><label class='ui-label'>收款银行：</label><select class='ui-select'id='selectbank'><option value='0'>---------请选择---------</option><option value='工商银行'>工商银行</option><option value='建设银行'>建设银行</option></select><div class='ui-check'><i class='error'></i>请选择银行</div></li><li><label class='ui-label'>银行账户名：</label><input type='text'value=''class='input w-4'id='bankName'><div class='ui-check'><i class='error'></i>请输入银行账户名</div></li><li><label class='ui-label'>收款卡号：</label><input type='text'value=''class='input w-4'id='bankCarnumber'><div class='ui-text-prompt'>16-19位数，请填写银行借记卡</div><div class='ui-check'><i class='error'></i>16-19位数，请填写银行借记卡</div></li><li><label class='ui-label'>确认收款卡号：</label><input type='text'value=''class='input w-4'id='bankCarnumber2'><div class='ui-text-prompt'>请再输入一次卡号信息，并保持输入一致</div><div class='ui-check'><i class='error'></i>请再输入一次卡号信息，并保持输入一致</div></li><li><label class='ui-label'>开户行名称：</label><input type='text'value=''class='input w-4'id='addBankName'><div class='ui-check'><i class='error'></i>请输入开户行名称</div></li><li><label class='ui-label'for='text'>开户行地址：</label><textarea id='addBankAdrr'class='textarea'style='width:218px;heght:72px;'></textarea><div class='ui-text-prompt'>0/20</div><div class='ui-check'><i class='error'></i>请输入开户行地址,长度不得超过20字符</div></li><li><label class='ui-label'>附件：</label><input type='file'value='添加附件'class='file'id='fileIsTure2'><span class='ui-prompt'>支持rar、jpg、png格式，大小不超过2M，最多可添加三条</span></li><li><label class='ui-label'>备注：</label><textarea id='bankPs'style='width:218px;heght:72px;'class='textarea'></textarea><div class='ui-text-prompt'>长度不得超过50字符</div><div class='ui-check'><i class='error'></i>请输入备注,长度不得超过50字符</div></li><li class='ui-btn'><a href='javascript:void(0);'class='btn btn-important'id='J-Subjet2'>确 定<b class='btn-inner'></b></a><a href='javascript:void(0);'class='btn btn-link'id='div_close2'>返 回<b class='btn-inner'></b></a></li></div><div id='div_Confiscate'style='display:none'><li><label class='ui-label'>备注：</label><textarea id='delPs'style='width:218px;heght:72px;'class='textarea'></textarea><div class='ui-text-prompt'>长度不得超过50字符</div><div class='ui-check'><i class='error'></i>请输入备注,长度不得超过50字符</div></li><li class='ui-btn'><a href='javascript:void(0);'class='btn btn-important'id='J-Subjet3'>确 定<b class='btn-inner'></b></a><a href='javascript:void(0);'class='btn btn-link'id='div_close3'>返 回<b class='btn-inner'></b></a></li></div></ul></td></tr>";
		 var str = "";
         CurrentObj.parent().parent().after(jQuery(strHtml));
		 CurrentObj.attr("disabled", "disabled");	
		 currentHide = CurrentObj.parents("tr").next();
		 ckeckAdd();
		  //发起操作,加载相应div操作层及调用预加载验证事件
		 $(".ui-form [name='radios_n']").change(function(){			
				 if($(this).attr("value")=='1')
				{
					$('#div_Add').hide();	
					$('#div_Refundd').show();
					$('#div_Confiscate').hide();
					ckeckcRefundd();
					
				} 
				else if($(this)[0].value=='2')
				{
					$('#div_Add').hide();	
					$('#div_Refundd').hide();
					$('#div_Confiscate').show();
					checkConfiscate();
				}
				else
				{
					$('#div_Add').show();
					$('#div_Refundd').hide();
					$('#div_Confiscate').hide();
					ckeckAdd();
					
				}			 	
		 });
		 
		//--------------------------------加游戏币验证-----------------------------------------------	
		function ckeckAdd(){
			//表单检测错误数量		
			setErrorNum0 = function(name, num){
				if(typeof errorHas0[name] != 'undefined'){
					errorHas0[name] += num;
					errorHas0[name] = errorHas0[name] < 0 ? 0 : (errorHas0[name] > 1 ? 1 : errorHas0[name]);
				}
			};
			$.each(errorTypes0, function(){
				errorHas0[this] = 0;
			});			
			
			//用户名验证
			userName = $('#userName');
			userNamePar = userName.parent();	
			userName.blur(function(){
				var v = $.trim(this.value);
				if(v == '' || v.length>15 || v.length<4){				
					userNamePar.find('.ui-check').css('display', 'inline');					
					setErrorNum0('userName', 1);
				}else{				
					userNamePar.find('.ui-check').css('display', 'none');
					setErrorNum0('userName', -1);
				}			
			}).focus(function(){
				userNamePar.find('.ui-check').css('display', 'none');
				userNamePar.find('.ui-text-prompt').css('display', 'inline');
			});
			
			//确认用户名验证
			userName2 = $('#userName2');
			userName2Par = userName2.parent();	
			userName2.blur(function(){
				var v = $.trim(this.value);
				if(v == '' || v.length>15 || v.length<4 ||userName.val()!=userName2.val()){			
					userName2Par.find('.ui-check').css('display', 'inline');	
					userName2Par.find('.ui-text-prompt').css('display', 'none');
					setErrorNum0('userName2', 1);
				}else{				
					userName2Par.find('.ui-check').css('display', 'none');
					setErrorNum0('userName2', -1);
				}			
			}).focus(function(){
				userName2Par.find('.ui-check').css('display', 'none');
				userName2Par.find('.ui-text-prompt').css('display', 'inline');
			});
			
			//验证备注
			addPS = $('#addPS');
			addPSPar = addPS.parent();	
			addPS.blur(function(){
				var v = $.trim(this.value);
				if(v == '' || v.length>50 || v.length<1){			
					addPSPar.find('.ui-check').css('display', 'inline');	
					addPSPar.find('.ui-text-prompt').css('display', 'none');
					setErrorNum0('addPS', 1);
				}else{				
					addPSPar.find('.ui-check').css('display', 'none');
					setErrorNum0('addPS', -1);
				}			
			}).focus(function(){
				addPSPar.find('.ui-check').css('display', 'none');
				addPSPar.find('.ui-text-prompt').css('display', 'inline');
			});
			
		}	
		//------------------------------------------发起退款验证-------------------------------------	
		function ckeckcRefundd(){
			//表单检测错误数量		
			setErrorNum1 = function(name, num){
				if(typeof errorHas1[name] != 'undefined'){
					errorHas1[name] += num;
					errorHas1[name] = errorHas1[name] < 0 ? 0 : (errorHas1[name] > 1 ? 1 : errorHas1[name]);
				}
			};
			$.each(errorTypes1, function(){
				errorHas1[this] = 0;
			});			
			
			//收款银行验证
			selectbank = $('#selectbank');
			selectbankPar = selectbank.parent();	
			selectbank.blur(function(){
				var v = $.trim(this.value);
				if(v == '0'){			
					selectbankPar.find('.ui-check').css('display', 'inline');					
					setErrorNum1('selectbank', 1);
				}else{				
					selectbankPar.find('.ui-check').css('display', 'none');
					setErrorNum1('selectbank', -1);
				}			
			}).focus(function(){
				selectbankPar.find('.ui-check').css('display', 'none');				
			});
			
			//银行帐号名验证
			bankName = $('#bankName');
			bankNamePar = bankName.parent();	
			bankName.blur(function(){
				var v = $.trim(this.value);
				if(v == '' ){			
					bankNamePar.find('.ui-check').css('display', 'inline');	
					bankNamePar.find('.ui-text-prompt').css('display', 'none');
					setErrorNum1('bankName', 1);
				}else{				
					bankNamePar.find('.ui-check').css('display', 'none');
					setErrorNum1('bankName', -1);
				}			
			}).focus(function(){
				bankNamePar.find('.ui-check').css('display', 'none');				
			});
			
			//收款卡验证
			bankCarnumber = $('#bankCarnumber');
			bankCarnumberPar = bankCarnumber.parent();	
			bankCarnumber.blur(function(){
				var v = $.trim(this.value);
				if(v == '' || v.length<16 || v.length>19){			
					bankCarnumberPar.find('.ui-check').css('display', 'inline');	
					bankCarnumberPar.find('.ui-text-prompt').css('display', 'none');
					setErrorNum1('bankCarnumber', 1);
				}else{				
					bankCarnumberPar.find('.ui-check').css('display', 'none');
					setErrorNum1('bankCarnumber', -1);
				}			
			}).focus(function(){
				bankCarnumberPar.find('.ui-check').css('display', 'none');				
			});
			//确认收款卡验证
			bankCarnumber2 = $('#bankCarnumber2');
			bankCarnumber2Par = bankCarnumber2.parent();	
			bankCarnumber2.blur(function(){
				var v = $.trim(this.value);
				if(v == '' || v.length<16 || v.length>19 || bankCarnumber.val() !=bankCarnumber2.val()){			
					bankCarnumber2Par.find('.ui-check').css('display', 'inline');	
					bankCarnumber2Par.find('.ui-text-prompt').css('display', 'none');
					setErrorNum1('bankCarnumber2', 1);
				}else{				
					bankCarnumber2Par.find('.ui-check').css('display', 'none');
					setErrorNum1('bankCarnumber2', -1);
				}			
			}).focus(function(){
				bankCarnumber2Par.find('.ui-check').css('display', 'none');				
			});
			
			//开户行名称验证
			addBankName = $('#addBankName');
			addBankNamePar = addBankName.parent();	
			addBankName.blur(function(){
				var v = $.trim(this.value);
				if(v == '' ){			
					addBankNamePar.find('.ui-check').css('display', 'inline');	
					addBankNamePar.find('.ui-text-prompt').css('display', 'none');
					setErrorNum1('addBankName', 1);
				}else{				
					addBankNamePar.find('.ui-check').css('display', 'none');
					setErrorNum1('addBankName', -1);
				}			
			}).focus(function(){
				addBankNamePar.find('.ui-check').css('display', 'none');				
			});
			//开户行地址验证
			addBankAdrr = $('#addBankAdrr');
			addBankAdrrPar = addBankAdrr.parent();	
			addBankAdrr.blur(function(){
				var v = $.trim(this.value);
				if(v == '' || v.length<1 || v.length>20){			
					addBankAdrrPar.find('.ui-check').css('display', 'inline');	
					addBankAdrrPar.find('.ui-text-prompt').css('display', 'none');
					setErrorNum1('addBankAdrr', 1);
				}else{				
					addBankAdrrPar.find('.ui-check').css('display', 'none');
					setErrorNum1('addBankAdrr', -1);
				}			
			}).focus(function(){
				addBankAdrrPar.find('.ui-check').css('display', 'none');				
			});
			//备注验证
			bankPs = $('#bankPs');
			bankPsPar = bankPs.parent();	
			bankPs.blur(function(){
				var v = $.trim(this.value);
				if(v == '' || v.length<1 || v.length>20){			
					bankPsPar.find('.ui-check').css('display', 'inline');	
					bankPsPar.find('.ui-text-prompt').css('display', 'none');
					setErrorNum1('bankPs', 1);
				}else{				
					bankPsPar.find('.ui-check').css('display', 'none');
					setErrorNum1('bankPs', -1);
				}			
			}).focus(function(){
				bankPsPar.find('.ui-check').css('display', 'none');				
			});		
			
		}	
		
			
	
		//------------------------------------------没收处理验证-------------------------------------	
		function checkConfiscate()
		{			
			//表单检测错误数量		
			setErrorNum2 = function(name, num){
				if(typeof errorHas2[name] != 'undefined'){
					errorHas2[name] += num;
					errorHas2[name] = errorHas2[name] < 0 ? 0 : (errorHas2[name] > 1 ? 1 : errorHas2[name]);
				}
			};
			$.each(errorTypes2, function(){
				errorHas2[this] = 0;
			});			
			
			//备注验证
			delPs = $('#delPs');
			delPsPar = delPs.parent();	
			delPs.blur(function(){
				var v = $.trim(this.value);
				if(v == '' || v.length>50 ){			
					delPsPar.find('.ui-check').css('display', 'inline');	
					delPsPar.find('.ui-text-prompt').css('display', 'none');				
					setErrorNum0('delPs', 1);
				}else{				
					delPsPar.find('.ui-check').css('display', 'none');
					setErrorNum0('delPs', -1);
				}			
			}).focus(function(){
				delPsPar.find('.ui-check').css('display', 'none');
				delPsPar.find('.ui-text-prompt').css('display', 'inline');
			});
		}
		
		 //返回清空状态，退回初始样式
		 $('#div_close1,#div_close2,#div_close3').click(function(){ 
		 	clearValue();	
		 });
		 
		 //---------------------------------------------------加游戏币事件---------------------------------------------
		 $('#J-Subjet1').click(function(e){
			 
			var err = 0,userName=$('#userName').val(),userName2=$('#userName2').val(),addPS=$('#addPS').val(),istrue=true;	
			$.each(errorTypes0, function(){
			if(typeof errorHas0[this] != 'undefined'){
				err += errorHas0[this];
			}
			});
			if(err > 0){  return false;	}			
			if(userName=='' || userName.length<4 || userName.length>16){
				userNamePar.find('.ui-check').css('display', 'inline'); 
				userNamePar.find('.ui-text-prompt').css('display', 'none'); 
				e.preventDefault(); 
				istrue=false; 
			}
			if(userName2=='' || userName2.length<4 || userName2.length>16 || userName!=userName2){
				userName2Par.find('.ui-check').css('display', 'inline'); 
				userName2Par.find('.ui-text-prompt').css('display', 'none'); 
				e.preventDefault();    
				istrue=false;  			
			}
			if(addPS=='' || addPS.length<1 || addPS.length>50 ){
				addPSPar.find('.ui-check').css('display', 'inline');
				addPSPar.find('.ui-text-prompt').css('display', 'none');  
				e.preventDefault();    
				istrue=false;   
			}			
			
			//验证用户名是否存在			
			try {			
				if(userName!='' && userName.length>4 && userName.length<=16){			
					$.ajax({
						type:'post',
						dataType:'json',
						cache:false,
						url:'/admin/Rechargemange/dealway?type=1',			
						data:'username='+$.trim(userName),					
						success:function(data){								
							if(data['isSuccess']=="1"){											
									alert('执行加游戏币');	
							}
							else{
								$('#userName').parent().find('.ui-check').css('display', 'inline');	
							}
						}	
					});
				}
				else{	
					$('#userName').find('.ui-check').css('display', 'inline');
					$('#userName').parent().find('.ui-text-prompt').css('display', 'none'); 
					 e.preventDefault();   
					 return false;}
			} catch (err) {	  e.preventDefault();    return false;	}		
			
			 
		 });
		 
		 //-------------------------------------------------发起退款事件----------------------------------------------------
		 $('#J-Subjet2').click(function(){
			var err = 0,selectbank=$('#selectbank')[0].value,bankName=$('#bankName').val(),bankCarnumber=$('#bankCarnumber').val(),
			bankCarnumber2=$('#bankCarnumber2').val(),addBankName=$('#addBankName').val(),addBankAdrr=$('#addBankAdrr').val(),bankPs=$('#bankPs').val(),istrue=true;
				
			$.each(errorTypes1, function(){
			if(typeof errorHas1[this] != 'undefined'){
				err += errorHas1[this];
			}
			});
			if(err > 0){  return false;	}			
			if(selectbank=='0'){	
				selectbankPar.find('.ui-check').css('display', 'inline'); 
				return false; 
			}
			if(bankName==''){	
				bankNamePar.find('.ui-check').css('display', 'inline'); 
				return false; 	
			}
			if(bankCarnumber=='' || bankCarnumber.length<16 || bankCarnumber.length>19){	
				bankCarnumberPar.find('.ui-check').css('display', 'inline');
				bankCarnumberPar.find('.ui-text-prompt').css('display', 'none'); 
				return false; 
			}
			if(bankCarnumber2 == '' || bankCarnumber2.length<16 || bankCarnumber2.length>19 || bankCarnumber !=bankCarnumber2){	
				bankCarnumber2Par.find('.ui-check').css('display', 'inline'); 
				bankCarnumber2Par.find('.ui-text-prompt').css('display', 'none'); 
				return false; 
			}	
			if(addBankName==''){	addBankNamePar.find('.ui-check').css('display', 'inline'); istrue=false; 	}	
			if(addBankAdrr == '' || addBankAdrr.length<1 || addBankAdrr.length>20 ){	
				addBankAdrrPar.find('.ui-check').css('display', 'inline'); 
				addBankAdrrPar.find('.ui-text-prompt').css('display', 'none'); 
				return false;  	
			}	
			if(bankPs == '' || bankPs.length<1 || bankPs.length>50 ){	
				bankPsPar.find('.ui-check').css('display', 'inline'); 
				bankPsPar.find('.ui-text-prompt').css('display', 'inline');
				return false;  	
			}		
			alert("验证通过发起退款");		
		 });
		 
		 //--------------------------------------------------没收处理事件--------------------------------------------------------
		 $('#J-Subjet3').click(function(){
			
			var err = 0,delPs=$('#delPs').val();
				
			$.each(errorTypes2, function(){
			if(typeof errorHas2[this] != 'undefined'){
				err += errorHas2[this];
			}
			});
			if(err > 0){   return false;	}			
			if(delPs==''){	
				delPsPar.find('.ui-check').css('display', 'inline'); 				
				return false; 
			} 			
			alert('没收处理');	
		 });
	});
	
	
	//清空状态，还原样式
	function clearValue(){
		currentHide.remove();
		CurrentObj.attr("disabled", "");
		currentHide = "";
		strInput = "";
		preStr = "";
		$("[name='foldchick']").removeClass('ico-fold').addClass("ico-unfold");				
	
	}
	
	
		
	
})();
</script>
{/literal}
</body>
</html>