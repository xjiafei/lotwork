	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; 银行管理</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab" >
						<input type="hidden" name="step" value="{$step}" />
							<ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
							<!-- {if "FUND_BANKCARD_BANKMANAGE_RECHARGE"|in_array:$smarty.session.datas.info.acls} -->
									<li >充值银行</li>
                                    <!--{else}-->
                                <li style="display:none;"></li>
							<!-- {/if} -->
							<!-- {if "FUND_BANKCARD_BANKMANAGE_WITHDRAW"|in_array:$smarty.session.datas.info.acls} -->
									<li>提现银行</li>
                                    <!--{else}-->
                                <li style="display:none;"></li>
							<!-- {/if} -->
							<!-- {if "FUND_BANKCARD_BANKMANAGE_BINDCARDCOUNT"|in_array:$smarty.session.datas.info.acls} -->
									<li>用户可绑定银行卡数</li>
                                    <!--{else}-->
                                <li style="display:none;"></li>
							<!-- {/if} -->
							</ul>						
							
							<ul class="ui-form ui-tab-content"  >    
							<!-- {if "FUND_BANKCARD_BANKMANAGE_RECHARGE_CREATE"|in_array:$smarty.session.datas.info.acls} -->    
                            <h3 class="ui-title"><a href="/admin/Bankcardsmanage/index?parma=opt1" class="btn btn-small" style="float:left;">新增充值银行<b class="btn-inner"></b></a></h3>                   	 
							<!-- {/if} -->
                            	<li>
                                	
                                    <table class="table table-info table-function" id="DivRules">
                                        <thead>
                                            <tr>
                                                <th>序号</th>
                                                <th >银行LOGO</th>
                                                <th>银行名称</th>
                                                <th>银行代码</th>
                                                <th>MOW银行编码</th>
                                                <th style="text-align:center">网银地址</th>
                                                <!-- {if "FUND_BANKCARD_BANKMANAGE_RECHARGE_EDIT"|in_array:$smarty.session.datas.info.acls} -->
                                                <th style="text-align:center">操作</th>
                                                <!-- {/if} -->
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <!--{foreach from=$res key=myId item=i} -->
											<!--{if $i.display == 'Y'}-->
                                            <tr>
                                                <td>{$i.id}</td> 
                                                <td><span class="ico-bank {$i.logo}"></span></td>
                                                <td>{$i.name}</td>
                                                <td>{$i.code}</td> 
                                                <td>{$i.mownecumId}</td>
                                                <td>{$i.url}</td> 
                                                 <!-- {if "FUND_BANKCARD_BANKMANAGE_RECHARGE_EDIT"|in_array:$smarty.session.datas.info.acls} -->
                                                <td style="text-align:center">
                                                    <a href="javascript:void(0);" name="editBank" pro_BankId='{$i.id}' pro_type="1">编辑</a>&nbsp;
                                                </td>
                                                 <!-- {/if} -->
                                            </tr>  
                                                <!-- {/if} -->											
                                        <!--{/foreach} -->
                                        </tbody>
                                    </table>
								  </li>						
								</ul>
                                <ul class="ui-form ui-tab-content">
                                <!-- {if "FUND_BANKCARD_BANKMANAGE_WITHDRAW_CREATE"|in_array:$smarty.session.datas.info.acls} -->   
                                 <h3 class="ui-title"><a href="/admin/Bankcardsmanage/index?parma=opt3" class="btn btn-small" style="float:left;">新增提现银行<b class="btn-inner"></b></a></h3> 
								<!-- {/if} -->
									<li>
                                    	 <table class="table table-info table-function">
                                            <thead>
                                                <tr>
                                                    <th>序号</th>
                                                    <th>银行LOGO</th>
                                                    <th>银行名称</th>
                                                    <th>银行代码</th>
                                                    <th>MOW银行编码</th>
                                                    <th style="text-align:center">网银地址</th>
                                                    <!-- {if "FUND_BANKCARD_BANKMANAGE_WITHDRAW_EDIT"|in_array:$smarty.session.datas.info.acls} -->
                                                    <th style="text-align:center">操作</th>
                                                    <!-- {/if} -->
                                                </tr>
                                            </thead>
                                            <tbody>
                                              <!-- {foreach from=$res key=myId item=i} -->
												<!--{if $i.display == 'Y'}-->											  
                                                <tr>
                                                    <td>{$i.id}</td>
                                                   <td><span class="ico-bank {$i.logo}"></span></td>
                                                    <td>{$i.name}</td>
                                                    <td>{$i.code}</td> 
                                                	<td>{$i.mownecumId}</td>
                                                    <td>{$i.url}</td>
                                                     <!-- {if "FUND_BANKCARD_BANKMANAGE_WITHDRAW_EDIT"|in_array:$smarty.session.datas.info.acls} -->                                                        
	                                                <td style="text-align:center">
                                                      	<a href="javascript:void(0);" name="editBank" pro_BankId='{$i.id}' pro_type="2">编辑</a>&nbsp;
                                                    </td>
                                                     <!-- {/if} -->
                                                   
                                                </tr>
													<!-- {/if} -->	
                                                <!-- {/foreach} -->
                                            </tbody>
                                        </table>                              
                                       
								  </li>						
								</ul>
                                 <ul class="ui-form ui-tab-content">
									<li>
                                     <form action="" method="post" id="J-form">
                                        <table class="table table-info table-border">
                                            <thead>
                                                <tr>
                                                    <th class="text-left" colspan="2">用户可绑定银行卡数</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td class="text-left">可绑银行卡数量</td>
                                                    <td class="text-left">
                                                        <select class="ui-select w-2" name="chargeCoute" id="chargeCoute">
                                                            {foreach from=$downArray item=i}
                                                            <option value="{$i}" {if $i== $bankCount}selected{/if}>{$i}</option>
                                                            {/foreach}
                                                            <option value="-1" {if "-1"== $bankCount}selected{/if}>无限</option>
                                                        </select>
                                                        <span class="ui-info">张</span>
                                                    </td>
                                                </tr>
                                                 <tr>
                                                    <td class="text-left">可绑定支付宝数量</td>
                                                    <td class="text-left">
                                                        <select class="ui-select w-2" name="aliPayChargeCoute" id="aliPayChargeCoute">
                                                            {foreach from=$downArray item=i}
                                                            <option value="{$i}" {if $i== $aliBankCount}selected{/if}>{$i}</option>
                                                            {/foreach}
                                                        </select>
                                                        <span class="ui-info">张</span>
                                                    </td>
                                                </tr>
                                                <!-- {if "FUND_BANKCARD_BANKMANAGE_BINDCARDCOUNT_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
                                                <tr>
                                                    <td></td>
                                                    <td class="text-left ui-btn">
                                                        <a class="btn btn-important" href="javascript:void(0);" id="J-Submjt-Button2">保 存<b class="btn-inner"></b></a>
                                                        <a class="btn" href="javascript:void(0);" id="J-Clean-Button">取 消<b class="btn-inner"></b></a>
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
   
    <textarea id="editBankDiv" style="display:none;">
    	 <table class="table table-info table-border">
            <thead>
                <tr>
                    <th class="text-left" colspan="2"><span id="titileName">数据异常</span></th>
                </tr>
            </thead>
            <tbody>
         		<tr>
                    <td class="text-left">银行名称</td>
                    <td class="text-left">
                       <input type="text" id="bankName" name="bankName"  class="input"  style="border:1px solid #CECECE;background:#E3E3E3" readonly />
                    </td>
                </tr>
                <tr>
                    <td class="text-left">银行LOGO</td>
                    <td class="text-left">
                        <span class="ico-bank ICBC" id="img_logo"></span>
                        <input type="hidden" value="{if $res.logo neq ''}{$res.logo}{else}ICBC{/if}" name="logo" id="name_logo"/>
                    </td>
                </tr>                
                <tr>
                    <td class="text-left">银行代码</td>
                    <td class="text-left">
                        <input type="text" class="input" value="银行代码" id="BankCode">
                    </td>
                </tr>
                <tr>
                    <td class="text-left">MOW银行编码</td>
                    <td class="text-left">
                        <input type="text" class="input" value="MOW银行编码" id="MOWCode">
                    </td>
                </tr>
                <tr>
                    <td class="text-left">网银地址</td>
                    <td class="text-left">
                        <input type="text" class="input" value="网银地址" id="OnlineBankAdd">
                    </td>
                </tr>
               <!-- {if "FUND_BANKCARD_BANKMANAGE_WITHDRAW_EDIT"|in_array:$smarty.session.datas.info.acls or "FUND_BANKCARD_BANKMANAGE_WITHDRAW_EDIT"|in_array:$smarty.session.datas.info.acls} -->
                <tr>
                    <td></td>
                    <td class="text-left ui-btn">
                        <input type="button" class="btn btn-important"  id="J-Eidet-Button"  value="确 定"/>
                        <a class="btn" href="javascript:void(0);" id="J-Close-Button">取 消<b class="btn-inner"></b></a>
                    </td>
                </tr>
                <!-- {/if} -->
                <tr>
                	<td colspan="2" style="text-align::right"><sapn id="bankInfo">编辑银行信息仅能够编辑银行基本属性，银行充值上下限、返送手续费配置请到<a href="/admin/Rechargemange/index?parma=sv2" target="_self" style="text-decoration:underline">充值银行相关配置</a>中处理</sapn></td>
                </tr>
            </tbody>
        </table>
    </textarea>
      <div id="DivFailed" style="position:absolute;z-index:2; display:none"  class="pop w-8">
        <i class="ico-error"></i>
        <h4 class="pop-text">操作失败</h4>
    </div>
     <div  id="DivSuccessful"   class="pop pop-success w-4" style="position:absolute;z-index:2; display:none" >
        <i class="ico-success"></i>
        <h4 class="pop-text">操作成功</h4>
    </div>
     <textarea id="DeleteTip" style="display:none;">
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">您确定要删除该状态吗？</h4>
            </div>
            <div class="pop-btn">
                <a href="#" class="btn btn-important " id="J-submit-safePassword">删 除<b class="btn-inner"></b></a>
                <a href="#" class="btn" id="closeDs">取 消<b class="btn-inner"></b></a>
            </div>
        </div>
    </textarea>
    
{include file='/admin/script-base.tpl'}
{literal}
<script>
(function() {	
	var isture=false,minWindow = new phoenix.MiniWindow(),dataArea = $('#DivRules'),form2=$('#J-form'),isture=false;
	minWindow = new phoenix.MiniWindow();
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});	
	//Tab（step）
	//	var tabIndex = phoenix.util.getParam('ss') == '1' ? 1 : 0；
	var stepType=$.trim($('[name="step"]').val());
	new phoenix.Tab({triggers:'.ui-tab-title2 li',panels:'.ui-tab-content',eventType:'click',currPanelClass:'ui-tab-content-current',index:stepType});	
	//操作后提示	 
	function fnshowdiv(obj){		
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
	} 
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuBanks');	
	//修改层检索是否为空
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
	
	//用户可绑定银行卡数操作		
	$('#J-Submjt-Button2').click(function(){		
		$.ajax({
			url:'/admin/Bankcardsmanage/index?parma=sv44',
			dataType:'json',
			cache:false,
			method:'post',							
			data:'chargeCoute='+$.trim($("#chargeCoute").val())+'&aliPayChargeCoute='+$.trim($("#aliPayChargeCoute").val()),
			beforeSend:function(){
				mask.dom.addClass('admin-mask-loading');
				mask.css({opacity:.9,backgroundColor:'#FFF'});
				mask.show(dataArea);
				mask.dom.css({top:mask.dom.offset().top + 2,height:mask.dom.height() - 2});
			},
			success:function(data){					
				//如果成功展示,自动隐藏
				if(data.status==true){
					fnshowdiv('DivSuccessful');
					setTimeout(function (){$("#DivSuccessful").css("display","none");},2000);
				}else{
					fnshowdiv('DivFailed');
					setTimeout(function (){$("#DivFailed").css("display","none");},2000);
					location='/admin/Bankcardsmanage/index?parma=sv41&step=2';
				}
			},
			complete:function(){
				mask.dom.fadeOut(300, function(){
					mask.hide();
				});
			}
		});
	});	
	//编辑银行(pro_type:1,充值银行编辑，pro_type：2，提款银行编辑)
	$(document).on('click', '[name="editBank"]', function(e){
		var bankId=$(this).attr("pro_BankId"),openType=$(this).attr("pro_type");			
		e.preventDefault();				
		if(openType=='1'){
			minWindow.setTitle('编辑充值银行');
		}else if(openType=='2'){	
			minWindow.setTitle('编辑提现银行');			
		}		
		else{
			minWindow.setTitle('对不起数据异常，请联系管理员');
		}		
		minWindow.setContent($('#editBankDiv').val());
		$('.j-ui-miniwindow ').css("width","700px");		
		minWindow.show();
		if(openType=='1'){
			$('#titileName').html('新增充值银行');			
		}else if(openType=='2'){
			$('#titileName').html('新增提现银行');
			$('#bankInfo').remove();
			
		}else{
			alert("对不起数据异常，请联系相关人员");
			return false;
		}		
		var BankName=$.trim($(this).parent().parent().find("td:eq(2)").text()),
		mownecumId=$.trim($(this).parent().parent().find("td:eq(0)").text()),
		bankLogo=$.trim($(this).parent().parent().find("td:eq(1) span").attr('class')),
		BankCode=$.trim($(this).parent().parent().find("td:eq(3)").text()),
		MOWCode=$.trim($(this).parent().parent().find("td:eq(4)").text()),
		OnlineBankAdd=$.trim($(this).parent().parent().find("td:eq(5)").text());	
		
		$("#bankName").val(BankName);			
		$('#img_logo').removeClass().addClass(bankLogo);
		$('#BankCode').val(BankCode);
		$('#MOWCode').val(MOWCode);
		$('#OnlineBankAdd ').val(OnlineBankAdd);
		$('#J-Eidet-Button').click(function(){
			CheckFrom('.j-ui-miniwindow :text');
			if(isture==false){ return false;}
			$.ajax({
				url:'/admin/Bankcardsmanage/index?parma=upbank',
				dataType:'json',
				cache:false,
				method:'post',
			    data:{'mownecumId':$.trim($('#MOWCode').val()),'name':$.trim(BankName),'code':$.trim($('#BankCode').val()),'url':$.trim($('#OnlineBankAdd').val()),"logo":bankLogo},	//data:'mownecumId='+$.trim($('#MOWCode').val())+'&name='+$.trim(BankName)+'&code='+$.trim($('#BankCode').val())+'&url='+$.trim($('#OnlineBankAdd').val())+"&logo="+bankLogo,
				beforeSend:function(){				
				},
				success:function(data){						
					if(data){
						minWindow.hide();
						fnshowdiv('DivSuccessful');
						setTimeout(function (){$("#DivSuccessful").css("display","none");},1500);
						location.reload();	
					}else{
						minWindow.hide();
						fnshowdiv('DivFailed');
						setTimeout(function (){$("#DivFailed").css("display","none");},1500);	
					}
				}
			});	
		});	
		
		$('#closeDs,#J-Close-Button').click(function(e){
			minWindow.hide();
		});
	});
	
	/*//提交编辑数据(充值银行，提现银行 共用)
	$(document).on('click', '#J-Eidet-Button', function(e){				
		CheckFrom(".j-ui-miniwindow table:tr>td");	
		if(isture == false){
			return false;
		}
		$.ajax({
				url:'',
				dataType:'json',
				cache:false,
				method:'post',
				data:'mownecumId=',
				beforeSend:function(){
					$("#J-Eidet-Button").attr("disabled", "true");			
				},
				success:function(data){						
					if(data){
						
						location.reload();	
					}else{
						minWindow.hide();						
					}
				},
				complete:function(){	
				 $("input:submit").removeAttr("disabled");			
				}
			});	
			
	});*/
	
	
	
	
})();	
</script>
{/literal}
</body>
</html>