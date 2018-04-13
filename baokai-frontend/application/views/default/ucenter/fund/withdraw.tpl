<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	
	{include file='/default/script-base.tpl'}
	

<body>
{include file='/default/ucenter/header.tpl'}
	<!-- header end -->
	<div class="g_33 common-main">
		<div class="g_5">
			<!-- //////////////左侧公共页面////////////// -->
				{include file='/default/ucenter/left.tpl'}
			<!-- /////////////左侧公共页面/////////////// -->				
		</div>
		<form action="/withdraw/checkdraw/" method="post" id="J-form">
		<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">我要提现</div>
				<div class="content bank-cash">
					<div class="notice"><i class="ico-warning"></i>提现所产生的银行手续费由平台为您免除。提现限额：最低<span id="lowLimit">{$aDataArray.withdrawStruc.lowLimit}</span>元，最高<span id="upLimit">{$aDataArray.withdrawStruc.upLimit}</span>元</div>
					<ul class="ui-form">
						<li style="display:none;">
							<label class="ui-label">剩余提现次数：</label>
							<span class="ui-singleline num-cash">
								<strong class="high">{$wdrawTime}次</strong>
								<span>每个用户每天最多可提{$aDataArray.withdrawStruc.time}次</span>
							</span>
						</li>
						<li>
							<label class="ui-label">可用余额：</label>
							<span class="ui-singleline">
								<strong class="bighigh" style="width:150px;">{$availBal|default:0}元</strong>
							</span>
						</li>
						<li>
							<label class="ui-label">可提现额度：</label>
							<span class="ui-singleline balance-cash">
								<strong class="high" style="width:150px;"><span id="freeMoeny">{$freeBal|default:0}</span>元</strong>
								<a target="_blank" href="/help/queryGeneralHelp?cateId2=526&cate2Name=%25E5%258F%25AF%25E6%258F%2590%25E7%258E%25B0%25E9%25A2%259D%25E5%25BA%25A6&orderBy=no%20desc,gmt_created%20desc">可提现额度计算规则</a>
							</span>
						</li>
						<li>
							<label class="ui-label">选择银行卡：</label>
							<div class="bank-more">
								<div class="bank-label">    
                                <input type="hidden"   name="id"  />                       					

									<span class="bank-drop-text" id="selectBank" >- 请选择我的银行卡 -</span>
									<a class="bank-drop" href="javascript:void(0);" ><i class="ico-down" id="icoName"></i></a>
								</div>
                                <div class="ui-check" id="errorBank"><i class="error"></i><span >请选择我的银行卡</spam></div>	
								<div class="bank-more-content" id="banklistinfo">
									<table id="tabbank" >
										<tbody>
										<!-- {foreach from=$aDataArray.userBankStruc item=data key=key} -->

											<tr pro_bankid="{$data.id}"  bankIco="{$data.bankIco}" isAbleWithdraw="{$data.isAbleWithdraw}">
                                           		<td><input type="radio" name="radiobank" class="radio_a" ></td>
												<td><label><span class="ico-bank {$data.bankIco}" id="{$data.id}"></span></label></td>												
												<td>开户人姓名：{$data.bankAccount}</td>
												<td>银行卡号：{$data.bankNumber}</td>
											</tr>
											
										<!-- {/foreach} -->
										</tbody>
									</table>
								</div>
							</div>
						</li>
						<li>
							<label class="ui-label" for="name">提现金额：</label>
							<input type="text" class="input w-4" value="" id="money" name="changeCount" autocomplete="off"> 元
							<i class="check-right"></i>
							<div class="ui-text-prompt">您本次最多提现<span id="wlimits"></span>元,今天还可以提现{$wdrawTime}次。</div>
							<!-- <div class="ui-text-prompt">提现限额：最低<span id="lowLimit">{$aDataArray.withdrawStruc.lowLimit}</span>元，最高<span id="upLimit">{$aDataArray.withdrawStruc.upLimit}</span>元</div> -->
							<div class="ui-check"><i class="error"></i><span id="errors">金额必须为整数或小数，小数点后不超过2位。</spam></div>									
							
						</li>
						<li class="ui-text">
							<span class="ui-singleline" id="chineseMoney"></span>
						</li>
						<li class="ui-btn"><a href="javascript:void(0);" class="btn btn-important" id="J-Submit">申请提现<b class="btn-inner"></b></a></li>
					</ul>
				</div>
			</div>
		</div>
		</form>
	</div>
		<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
    <script  src="{$path_js}/js/phoenix.Common.js"></script>
<script>
var enableWithdrw = {$enableWithdraw};
</script>
{literal}

<script>
	(function(){
		var minWindow = new phoenix.MiniWindow({cls:'ui-alert-custom'}),
		mask = phoenix.Mask.getInstance();
		minWindow.addEvent('beforeShow', function(){
			mask.show();
		});
		minWindow.addEvent('afterHide', function(){
			mask.hide();
		});
		var formatMoney = function(num){
			if(num == ''){
				num = '0';
			}
			var num = num.replace(/,/g, ''),
				num = parseFloat(num),
				re = /(-?\d+)(\d{3})/;
		
			if(Number.prototype.toFixed){
				num = (num).toFixed(2);
			}else{
				num = Math.round(num*100)/100
			}
			num  =  '' + num;
			while(re.test(num)){
				num = num.replace(re,"$1,$2");
			}
			return num;  
		};
		//输入金额的状态提示,参照《资金表单交互文档》
		var form = $('#J-form'), money,moneyPar,
			lowLimit=$.trim($('#lowLimit').html()).replace(/,/g, ''),upLimit=$.trim($('#upLimit').html()).replace(/,/g, ''),
			moeys= ($.trim($('#freeMoeny').html().replace(/,/g, '')) ==''?0:parseFloat($('#freeMoeny').html().replace(/,/g, ''))),	
			withdrawaLimit,		
			//表单检测错误数量
			errorTypes = ['money'],
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
		//当前用户提现限额：
		if(moeys > upLimit){
				withdrawaLimit =  upLimit;
			}else{
				withdrawaLimit =  moeys;
		}
		
		var getClolor=function(obj){
			var v=obj.value;
			if(v.indexOf(".")==-1)
			{
				
				if(v.length == 4)
				{
					$(obj).attr("style","color:green");
				}else if(v.length == 5)
				{
					$(obj).attr("style","color:red");
				}else if(v.length >= 6)
				{
					$(obj).attr("style","color:#6B238E");
				}else
				{
					$(obj).attr("style","color:#BDBDBD");
				}		
			}else if(v.indexOf(".")>0)
			{
				var vlist=v.split(".");
				if(vlist[0].length == 4)
				{
					$(obj).attr("style","color:green");
				}else if(vlist[0].length == 5)
				{
					$(obj).attr("style","color:red");
				}else if(vlist[0].length >= 6)
				{
					$(obj).attr("style","color:#6B238E");
				}else
				{
					$(obj).attr("style","color:#BDBDBD");
				}		
			}
	    }

		var checkFn2 = function(obj){
			var me = obj,v = me.value,index;
			me.value = v = v.replace(/^\.$/g, '');		
			index = v.indexOf('.');
			if(index > 0){
				me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');			
				if(v.substring(index+1,v.length).length>2){				
					me.value= v  = v.substring(0, v.indexOf(".") + 3);
				}
			}else
			{
				me.value = v = v.replace(/^0/g, '');
			}		
			me.value = v = v.replace(/[^\d|^\.]/g, '');
			me.value = v = v.replace(/^00/g, '0');				
		};
		var checkWithdrawStatus = function(){
			if(enableWithdrw ==0){
				minWindow.setTitle('温馨提示');
				minWindow.setContent('<div class="pop-title"><i class="ico-success"></i><h4 class="pop-text" style="color:#F00;">您帐号提现功能已被冻结，请联系您的上级代理或平台客服！</h4></div><div class="pop-btn"><a class="btn closeBtn" href="#">确定<b class="btn-inner"></b></a></div>');
				minWindow.show();
				return false;
			} 
			return true;
		};
		//标题验证(最低金额跟最高金额将动态带入)
		money = $('#money');
		moneyPar = money.parent();	
		money.blur(function(){
			$('#errors').html("金额必须为整数或小数，小数点后不超过2位。");			
			var me = this,v= $.trim($('#money')[0].value.replace(/,/g, ''))==''?0:Number($.trim($('#money')[0].value.replace(/,/g, '')));	
			if(v == 0 ){
				moneyPar.find('.ui-check').css('display', 'inline');	
				moneyPar.find('.ui-text-prompt').css('display', 'none');
				moneyPar.parent().find('.check-right').css('display', 'none');
				setErrorNum('money', 1);
			}else if( v < Number(lowLimit) || v > Number(upLimit)){	
				var htmls="提现限额：最低"+formatMoney(lowLimit)+",最高"+formatMoney(upLimit)+"元";
				$('#errors').html(htmls);
				moneyPar.find('.ui-check').css('display', 'inline');	
				moneyPar.find('.ui-text-prompt').css('display', 'none');
				moneyPar.parent().find('.check-right').css('display', 'none');				
				setErrorNum('money', 1);

			}else{	
			    checkFn2(this);									
				moneyPar.find('.ui-check').css('display', 'none');
				moneyPar.parent().find('.check-right').css('display', 'inline-block');
				setErrorNum('money', -1);
			}		
			me.value = formatMoney(me.value);
		}).focus(function(){
			moneyPar.find('.ui-check').css('display', 'none');
			moneyPar.find('.ui-text-prompt').css('display', 'inline');
			if($('[name="id"]').val() != ''){ 
				$('#errorBank').css("display","none");			
				
			}
		}).keyup(function(){
			var v=this.value;		
			var moeys= ($.trim($('#freeMoeny').html().replace(/,/g, '')) ==''?0:parseFloat($('#freeMoeny').html().replace(/,/g, '')));					
			if(Number(lowLimit)<Number(moeys)&& Number(moeys)<Number(upLimit)){
				checkWithdraw(this,'chineseMoney',Number(moeys));
			}
			else if(Number(moeys)>Number(upLimit)){
				checkWithdraw(this,'chineseMoney',Number(upLimit));
			}
			getClolor(this);
		
		});			
		$('.bank-more-content').css("display","none");
		$('.bank-more').css("min-height","45px");		
		$('#div_pro').css("display","inline");			
			
		$('#wlimits').html(withdrawaLimit);
		//当没有绑定银行卡时显示字，请选择银行
		$('#selectBank').css("display","inline-block").css("width","131px").css("text-align","center");	
		//储蓄卡充值
		$("#tabbank tr").click(function () {	
			$("#banklistinfo span").removeClass("ico-bank-current");
			var txtstatus = $(this).attr("pro_bankid").trim();
			var bankIco = $(this).attr("bankIco").trim();
			var isablewithdraw=$(this).attr("isablewithdraw");
			$('[name="id"]').attr("value", txtstatus);
			$('#selectBank').addClass("ico-bank "+bankIco);					

			//当有绑定银行,显示相应银行图片(动态绑定)
			$('#selectBank').removeAttr("style").removeAttr("class").addClass("ico-bank "+bankIco).attr("title",bankIco).attr("isablewithdraw",isablewithdraw).html('');	
			$('.bank-more-content').css("display","none");
			$('.bank-more').css("min-height","45px");		
			$('#div_pro').css("display","inline");
			$('#icoName').removeClass('ico-up').addClass('ico-down');			
			$(this).find(":radio").attr("checked", true);
			
		});			
		//银行卡展示与隐藏
		$('.bank-drop').click(function(){
			var classcss = $('#icoName').attr("class");
			$(this).parent().next().hide();		
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
		//表单检测
		checkform = function(){		
			var v= $.trim($('#money')[0].value.replace(/,/g, ''))==''?0:Number($.trim($('#money')[0].value.replace(/,/g, '')));			
			if(v == 0 ){
				moneyPar.find('.ui-check').css('display', 'inline');	
				moneyPar.find('.ui-text-prompt').css('display', 'none');
				setErrorNum('money', 1);
			}else if(v < Number(lowLimit) || v >  Number(withdrawaLimit)){	
				var htmls="提现限额：最低"+formatMoney(lowLimit)+",最高"+formatMoney(withdrawaLimit)+"元";
				$('#errors').html(htmls);
				moneyPar.find('.ui-check').css('display', 'inline');	
				moneyPar.find('.ui-text-prompt').css('display', 'none');				
				setErrorNum('money', 1);

			}else{										
				moneyPar.find('.ui-check').css('display', 'none');
				setErrorNum('money', -1);
			}				
		}		 
		$('#J-Submit').click(function(e){

			if($('#selectBank').attr("isablewithdraw") ==-1)
			{
				var bankcheck = $('#selectBank').parent().parent().find('.ui-check');
				bankcheck.html('<i class="error"></i><span>银行卡未生效。</span>');
				bankcheck.css('display', 'inline');
				return false;
			}else if($('#selectBank').attr("isablewithdraw") ==-2){
				var bankcheck = $('#selectBank').parent().parent().find('.ui-check');
				bankcheck.html('<i class="error"></i><span>此提款银行正在维护，暂不支持提现，请您选择其他银行卡。</span>');
				bankcheck.css('display', 'inline');
				return false;
			}
			if(!checkWithdrawStatus()){
				return false;
			}
			if($('[name="id"]').val() == ''){ 
				$('#errorBank').css("display","inline");				
				return false;
			}
			checkform();
			var err = 0;		
			$.each(errorTypes, function(){
				if(typeof errorHas[this] != 'undefined'){
					err += errorHas[this];
				}
			});
			if(err > 0){  return false;	}	
			else{
				form.submit();	
			}			
		});	
		checkWithdrawStatus();
	})(jQuery);
</script>	
{/literal}
	
</body>
</html>