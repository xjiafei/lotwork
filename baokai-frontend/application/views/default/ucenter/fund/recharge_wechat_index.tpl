<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	{include file='/default/script-base.tpl'}
</head>
<body>
	{include file='/default/ucenter/header.tpl'}
	<!-- header end -->
	<div class="g_33 common-main">
		<div class="g_5">
			<!-- //////////////左侧公共页面////////////// -->
				{include file='/default/ucenter/left.tpl'}
			<!-- /////////////左侧公共页面/////////////// -->		
		</div>
        <form action="/fund/confirm" method="post" id="J-form">
		<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">我要充值</div>
				<div class="ui-tab-title tab-title-bg clearfix appeal-link-tab">
					{include file='/default/ucenter/fund/recharge_index_title.tpl'}
				</div>
				<div class="content bank-select">
					<div class="step">
						<table class="step-table">
							<tbody>
								<tr>
									<td {if $stp eq 0}class="current"{/if}><div class="con">1.填写金额</div></td>
									<td {if $stp eq 1}class="current"{/if}><div class="tri"><div class="con">2.确认充值信息</div></div></td>
									<td {if $stp eq 2}class="current"{/if}><div class="tri"><div class="con">3.登陆微信付款</div></div></td>
								</tr>
							</tbody>
						</table>
					</div>
						<ul class="ui-form bank-select-from">
						<li><img src="{$path_img}/images/funds/u152.gif"></li>
							<li class="bank-select-from-li">
								<label class="ui-label">充值银行：</label>
								<div class="bank-more">
									<div class="bank-label">
									<input type="hidden" name="type" value="5" />
										{foreach from=$aBankCardList item=data}
                                   		{if $vipLvl =='vip'}
                                   			<span class="ico-bank {$data.logo}" name="{$data['logo']}" max="{$data.vipUpLimit}" min="{$data.vipLowLimit}"  title="{$data['name']}充值限额：最低{$data.vipLowLimit},最高{$data.vipUpLimit}"></span>
                                   		{else}
											<span class="ico-bank {$data.logo}" name="{$data['logo']}" max="{$data.upLimit}" min="{$data.lowLimit}"  title="{$data['name']}充值限额：最低{$data.lowLimit},最高{$data.upLimit}"></span>
										{/if}
										<input type="hidden" name="status" id="bankselect" value="{$data.logo}" />
										{/foreach}
									</div>
								</div>
							</li>
							<li class="ui-text">
								<div class="prompt" ><div name="initialMoney">充值金额：单笔最低充值金额为<span name="txtminmoney"></span>元，最高<span name="txtmaxmoney"></span>元<br /></div>充值时限：请在{if $iCountDown neq ''}{$iCountDown}{else}30{/if}分钟内完成充值</div>
							</li>
							<li>
								<label class="ui-label">充值金额：</label>
								<input type="text" class="input w-4" id="chargeamount" name="chargeamount"  autocomplete="off" >
	                            <span class="ui-text-info">元</span>		
								<i class="check-right"></i>
								<div name="initialMoney">			
                                <span class="ui-prompt"><div  id="div_pro" style="display:none">充值限额：最低<span name="txtminmoney"></span>元，最高<span name="txtmaxmoney"></span>元</div></span>									
                                <span class="ui-check"><i class="error"></i>充值限额：最低<span name="txtminmoney"></span>元，最高<span name="txtmaxmoney"></span>元</span>
                             </div>	
							</li>
							<li class="ui-btn">
							<a href="javascript:void(0);" id="J-next-step" class="btn btn-disable">下一步<b class="btn-inner"></b></a>
							<span id="wait_msg" style="display:none;color:red;position:relative;top:10px;">　请稍等，正在获取信息……</span></li>
						</ul>
						<!-- {if $isSetRemark neq 1} -->
						<div style="padding:20px;color:#999;">
							注：您还在为每次充值填写不一样的附言而烦恼吗？  <a href="/remark">立即设置唯一附言</a>，从此只需记一个专属您自己的附言，充值更方便。
						</div>
						<!-- {/if} -->
				</div>
			</div>
		</div>
		</form>
		<input type="hidden" id="isCharged" value="0" />
		
	</div>
<!-- //////////////底侧公共页面////////////// -->
{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->
<script  src="{$path_js}/js/phoenix.Common.js"></script>

<script type="text/tpl" id="J-template-cancel-confirm">
<div>
	<p class="wd-title" style="text-align:center;">您的充值申请已撤销成功！</p>
		<p class="wd-control-panel">
			<a href="#" class="btn btn-important closeBtn">继续充值<b class="btn-inner"></b></a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" target="_blank">充值记录</a>
		</p>
</div>
</script>
<script>
var enableRecharge = {$enableRecharge};
</script>

{literal}
<script>
(function(){
	
	var minWindow = new phoenix.MiniWindow({cls:'ui-wd-funds-expired'}),
		mask = phoenix.Mask.getInstance(),
		maxmoney = $('.bank-more span').attr('max'),
		minmoney = $('.bank-more span').attr('min');
	var isPassChargeMount=false;
	//var safePassword = $('#password'),loadingNum = 0,loadingNum2 = 0,isPassSafePassword=false;
	var chargeamount,chargeamountPar,form = $('#J-form');
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
	//数字校验，自动矫正不符合数学规范的数学(小数两位)
	var inputs =  $('#chargeamount'),checkFn;				
	checkFn = function(){
		var me = this,v = me.value,index;
		me.value = v = v.replace(/^\.$/g, '');		
		index = v.indexOf('.');
		if(index > 0){
			me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');			
			if(v.substring(index+1,v.length).length>2){				
				me.value= v  = v.substring(0, v.indexOf(".") + 3);
			}
		}	
	    getClolor(this);
		me.value = v = v.replace(/[^\d|^\.]/g, '');
		me.value = v = v.replace(/^00/g, '0');				
	};
	inputs.keyup(checkFn);
	inputs.blur(checkFn);
	var mask = phoenix.Mask.getInstance();
	//提示消息
	var maskMsg = {
		el:$('#J-ui-mask-msg'),
		show:function(isSuccess, msg){
			var me = this;
			if(isSuccess){
				me.el.find('i').removeClass().addClass('ico-success');
				me.el.removeClass().addClass('pop pop-success w-4');
			}else{
				me.el.find('i').removeClass().addClass('ico-error');
				me.el.removeClass().addClass('pop pop-error w-6');
			}
			me.el.find('.pop-text').html(msg);
			phoenix.util.toViewCenter(me.el);
			me.el.show();
			mask.show();
		},
		hide:function(){
			var me = this;
			me.el.hide();
			mask.hide();
		}
	};
	
	var loadingFn = function(el){
		var el = $(el);
	};
	var eventFocus = function(el, msg){
		$(el).parent().find('.ui-check').html(msg).removeClass().addClass('ui-check ui-check-tip').show();
	};
	var showError = function(el, msg){
		$(el).parent().find('.ui-check').html('<i></i>'+msg).removeClass().addClass('ui-check').show();
	};
	var showLoading = function(el){
		$(el).parent().find('.ui-check').html('&nbsp;&nbsp;&nbsp;&nbsp;').removeClass().addClass('ui-check ui-check-loading').show();
	};
	var hideLoading = function(el){
		$(el).parent().find('.ui-check').removeClass().addClass('ui-check').hide();
	};
	var showRight = function(el, msg){
		var msg = typeof msg == 'undefined' ? '' : msg;
		$(el).parent().find('.ui-check').html(msg).removeClass().addClass('ui-check ui-check-right').show();
	};
	var hideTip = function(el){
		$(el).parent().find('.ui-check').removeClass().addClass('ui-check').hide();
	};
	var ajaxCallBackList2 = [];
	var ajaxCallBack2 = function(obj){
		var i = 0,len = ajaxCallBackList2.length;
		if(loadingNum2 > 0){
			return;
		}
		for(;i < len;i++){
			if(ajaxCallBackList2[i].call(obj || null) === false){
				break;
			}
		}
		ajaxCallBackList2 = [];
	};
	
	var checkOrderStatus = function(){
		if($("#isCharged").val() == '1'){
			minWindow.setTitle('温馨提示');
			minWindow.setContent($('#J-template-checkstatus').val());
			minWindow.show();
			cancleApply($('#J-button-order-cancel').attr('prevalue'));
			return true;
		} else if($("#isCharged").val() == '2'){
			return false;
		}
		$.ajax({
			url:'/fund/checkchargesuccess/',
			dataType:'json',
			data:"type=5",//deposit mode
			async:false,
			success:function(data){
				//var data = {isSuccess:1, type:'', msg:'请求成功', data:{bankFrom:'充值银行名称', money:'充值金额', bankTo:'收款银行', name:'收款人', email:'收款Email地址', message:'附言', bankOpen:'开户行名称'}};
				if(Number(data['isSuccess']) == 1){
					var str = $('#J-template-checkstatus').html(),
						reg,
						p;
					for(p in data['data']){
						if(p == null){
							p = '';
						}
						reg = RegExp('<#=' + p + '#>', 'g');
						str = str.replace(reg, data['data'][p]);
					}
					$("#isCharged").val('1');
					$('#J-template-checkstatus').val(str);
					minWindow.setTitle('温馨提示');
					minWindow.setContent(str);
					minWindow.show();
					cancleApply($('#J-button-order-cancel').attr('prevalue'));
					return true;
				}else{
					$("#isCharged").val('2')
					return false;
				}
			}
		});
	};
		
	//取消充值申请
	var cancleApply = function(id){
		$('#J-button-order-cancel').click(function(e){
			e.preventDefault();
			$.ajax({
				url:'/fund/cancleorder/',
				dataType:'json',
				cache:false,
				data:"id="+id,
				beforeSend:function(){
					//禁用发送								
					var button = $('#J-button-order-cancel'),list = ['撤销中   ', '撤销中.  ', '撤销中.. ', '撤销中...'],i = 0;
						interval=setInterval(function(){
							$('#J-button-order-cancel').val(list[i]);
							i += 1;
							if(i >= list.length){
								i = 0;
							}
						}, 300);
					$("#J-button-order-cancel").attr("disabled","disabled");	
				},
				success:function(data){
					if(Number(data['isSuccess']) == 1){
						minWindow.hide();
						minWindow.setContent($('#J-template-cancel-confirm').html());
						minWindow.show();
						$("#isCharged").val('0');
					}else{
						alert(data['msg']);
					}
				},
				complete:function(){
					clearInterval(interval);
					$('#J-button-order-cancel').val("撤销申请");						 	
					$("#J-button-order-cancel").removeAttr("disabled","disabled");
				}
			});
		});
	}
	
	var checkRechargeStatus = function(){
		if(enableRecharge ==0){
			minWindow.setTitle('温馨提示');
			minWindow.setContent('<div class="pop-btn"><i class="ico-waring <#=icon-class#>"></i><h4 class="pop-text">您帐号充值功能已被冻结，请联系您的上级代理或平台客服！</h4></div><div class="pop-btn"><a class="btn closeBtn" href="#">确定<b class="btn-inner"></b></a></div>');
			minWindow.show();
			return false;
		}
		return true;
	};
	errorTypes = ['chargeamount'],
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
	
	var getClolor=function(obj){
			var v=obj.value;
			if(v.indexOf(".")==-1)
			{
				
				if(v.length == 3)
				{
					$(obj).attr("style","color:green");
				}else if(v.length == 4)
				{
					$(obj).attr("style","color:red");
				}else if(v.length >= 5)
				{
					$(obj).attr("style","color:#6B238E");
				}else
				{
					$(obj).attr("style","color:#BDBDBD");
				}		
			}else if(v.indexOf(".")>0)
			{
				var vlist=v.split(".");
				if(vlist[0].length == 3)
				{
					$(obj).attr("style","color:green");
				}else if(vlist[0].length == 4)
				{
					$(obj).attr("style","color:red");
				}else if(vlist[0].length >= 5)
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

	//充值金额验证
	chargeamount = $('#chargeamount');
	chargeamountPar = chargeamount.parent();	
	chargeamount.blur(function(){
		var v = $.trim(this.value);
		checkFn2(this);
		checkform();
		/*if(checkOrderStatus()){
			die;
		}*/
		isPassChargeMount = true;
		this.value=formatMoney(v);
	}).focus(function(){	
		if($("#bankselect")[0].value==''){				
			$('#selectBankEr').css('display', 'inline');	
		}	
		chargeamountPar.find('.ui-check').css('display', 'none');
		chargeamountPar.find('.ui-prompt').css('display', 'inline');
	}).keyup(function(){
		var v=this.value;
		var maxmoney=$($("[name='txtmaxmoney']")[0]).text();
        maxmoney = parseInt(maxmoney.replace(',','')); // 拿掉数字FORMAT 才能作比较
		//var minmoney=$($("[name='txtminmoney']")[0]).text();
		if(Number(v)>maxmoney)
		{
			$(this).val(maxmoney);
		}
		checkFn2(this);
	    checkform();
	});
	
	
	$('.bank-more-content').css("display","none");
	$('.bank-more').css("min-height","45px");

	if(maxmoney !=0){
		 $('[name="initialMoney"],#div_pro').css("display","inline");
		 $('[name="txtminmoney"]').html(formatMoney(minmoney));
		 $('[name="txtmaxmoney"]').html(formatMoney(maxmoney));	
		 chargeamount.removeAttr("disabled");
	} else {
		 $('[name="initialMoney"]').css("display","none");
	}
	//表单检测
	checkform = function(){			
		if($.trim($('#chargeamount')[0].value) != "")
		{
			if((minmoney.replace(/,/g, '') ==0 && maxmoney.replace(/,/g, '') ==0) || $('#chargeamount')[0].value.replace(/,/g, '') == '' || parseFloat($('#chargeamount')[0].value.replace(/,/g, '')) > maxmoney || parseFloat($('#chargeamount')[0].value.replace(/,/g, '')) < minmoney.replace(/,/g, '')){
				chargeamountPar.find('.ui-check').css('display', 'inline');	
				chargeamountPar.find('.ui-prompt').css('display', 'none');
				chargeamountPar.parent().find('.check-right').css('display', 'none');
				setErrorNum('chargeamount', 1);	
				$("#J-next-step").removeClass("btn btn-important").addClass("btn btn-disable");	
			}	
			else{
				chargeamountPar.find('.ui-check').css('display', 'none');
				chargeamountPar.parent().find('.check-right').css('display', 'inline-block');
				setErrorNum('chargeamount', -1);
				$("#J-next-step").removeClass("btn btn-disable").addClass("btn btn-important");
			}	
		}	
		else
		{
			chargeamountPar.find('.ui-check').css('display', 'inline');	
			chargeamountPar.find('.ui-prompt').css('display', 'none');
			chargeamountPar.parent().find('.check-right').css('display', 'none');
			setErrorNum('chargeamount', 1);	
			$("#J-next-step").removeClass("btn btn-important").addClass("btn btn-disable");	
		}
	}

	$('#J-next-step').click(function(){
		minWindow.setTitle('温馨提示');
		//送出前檢測單日充值額度
		$.ajax({
			url:'/fund/checkdaylimit',
			dataType:'json',
			cache:false,
			data:"chargeamount="+$('#chargeamount')[0].value+"&bankid=40",
			success:function(data){
				if(data['chargeLimit']==0){
					minWindow.setTitle('温馨提示');
					minWindow.setContent('<div class="pop-btn"><i class="ico-waring <#=icon-class#>"></i><h4 class="pop-text">微信充值已达到单日上限！</h4></div><div class="pop-btn">请您明日0点再次尝试，建议先使用其他充值方式充值</div><div class="pop-btn"><a class="btn closeBtn" href="#">关 闭<b class="btn-inner"></b></a></div>');
					minWindow.show();;
				}else if(data['chargeLimit']==-1){
					form.submit();
				}
			},
		});		
	});
	form.submit(function(e){			
		var err = 0;	
		checkform();	
		$.each(errorTypes, function(){
			if(typeof errorHas[this] != 'undefined'){
				err += errorHas[this];
			}
		});
		
		if(err > 0){ 
			e.preventDefault(); 
			return false;	
		} else {
			if(!checkRechargeStatus()){
				return false;
			}
			/*if(checkOrderStatus()){
				return false;
			}*/
			if(!isPassChargeMount){
				return false;
			}
			/* if(!checkSafePassword(true)){
				return false;
			} */
			if($("#isCharged").val()=='1'){
				return false;
			}
			$('#wait_msg').show();
			return true;
		}
	});
})();
</script>
{/literal}	
</body>

</html>