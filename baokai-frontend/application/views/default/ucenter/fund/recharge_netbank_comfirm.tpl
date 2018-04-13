<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />	
	{include file='/default/script-base.tpl'}
    <script type="text/javascript" src="{$path_js}/js/jquery.zclip.js"></script>
<body>
{include file='/default/ucenter/header.tpl'}
	<!-- header end -->
	<div class="g_33 common-main">
		<div class="g_5">
			<!-- //////////////左侧公共页面////////////// -->
				{include file='/default/ucenter/left.tpl'}
			<!-- /////////////左侧公共页面/////////////// -->				
		</div>
		<form action="?" method="post" id="J-form">
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
									<td><div class="con">1.选择银行并填写金额</div></td>
									<td class="current"><div class="tri"><div class="con">2.确认充值信息</div></div></td>
									<td><div class="tri"><div class="con">3.登录网上银行汇款</div></div></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="bank-check">
						<div class="bank-title">充值确认</div>
						<ul class="ui-form">
							<li>
								<label class="ui-label">充值银行：</label>
								<span class="ico-bank {$status}" id="{$status}"></span>
							</li>
							<li>
								<label class="ui-label">充值金额：</label>
								<span class="ui-singleline"><font color="red">{$chargeamount}</font> 元</span>
							</li>
							<li>
								<label class="ui-label">需用银行卡：</label>
								<span class="ui-singleline">可使用任意一张{$bankName}卡进行汇款</span>
							</li>
						</ul>
					</div>
                    <!-- {if $expireTime != ''} -->
					<div class="bank-check payee-info">
						<div class="bank-title">收款方信息<span>&nbsp;&nbsp;&nbsp;以下信息是确保您充值到账的重要信息</span></div>
						<ul class="ui-form">
						<!-- {if $rcvBank neq ''} -->
							<li>
								<a href="javascript:void(0);" class="copy" id="bankcopy">复制</a>
								<label class="ui-label">收款银行：</label>
								<span class="ui-singleline color-red" id="collectiobank">{$rcvBank}</span>
							</li>
						<!-- {/if} -->
						<!-- {if $revAccName neq ''} -->
							<li>
                            	<!--请绑定用户此次充值时间到隐藏域，-->
                            	<input type="hidden" name="takeeffect" id="takeeffect" value="" /> 
                                
								<a href="javascript:void(0);" class="copy" id="namecopy">复制</a>
								<label class="ui-label">收款账户名：</label>
								<span class="ui-singleline color-red" id="collectionname">{$revAccName}</span>
							</li>
							<!-- {/if} -->
							<!-- {if $rcvAccNum neq '' && $mode eq '1'} -->
							<li>
								<a href="javascript:void(0);" class="copy" id="emailcopy">复制</a>
								<label class="ui-label">收款账号：</label>
								<span class="ui-singleline color-red" id="collectioemail">{$rcvAccNum}</span>
							</li>
							<!-- {/if} -->
							<!-- {if $rcvEmail neq '' && $mode eq '2'} -->
							<li>
								<a href="javascript:void(0);" class="copy" id="emailcopy">复制</a>
								<label class="ui-label">收款Email地址：</label>
								<span class="ui-singleline color-red" id="collectioemail">{$rcvEmail}</span>
							</li>
							<!-- {/if} -->
							<!-- {if $chargeMemo neq ''} -->
							<li>
								<a href="javascript:void(0);" class="copy" id="pscopy">复制</a>
								<label class="ui-label">附言：</label>
								<span class="ui-singleline color-red charge-memo">{$chargeMemo}</span>
                                <i class="ico-ps"></i>
							</li>
                            <li>
								<div class="prompt-ps">附言在部分网站会以“备注“，”用途“等名词出现 请务必正确填写此项信息，填写错误或不填写会影响充值到帐</div>
							</li>
							<!-- {/if} -->
							<!-- {if $rcvBankName neq ''} -->
							<li>
								<a href="javascript:void(0);" class="copy" id="banknamecopy">复制</a>
								<label class="ui-label">开户银行名称：</label>
								<span class="ui-singleline color-red" id="collectioebankname">{$rcvBankName}</span>
							</li>
							<!-- {/if} -->
							<li class="ui-btn">
                            	
								<a href="{$url}" target="_blank" class="btn btn-primary">登录网上银行付款<b class="btn-inner"></b></a>
								<a href="/fund/history/" target="_blank" class="btn btn-link">查看充值记录</a>
							</li>
						</ul>
						<div class="payment-help">
							<div class="bank-check">
								<div class="bank-title">付款帮助：
							   <!-- {if $status  eq  'ICBC'} -->
                                   <a target="_blank" href="/help/queryGeneralDetail?helpId= 924">图片帮助</a>
                               <!-- {elseif $status eq 'CMB'} -->
                                   	<a target="_blank" href="/help/queryGeneralDetail?helpId= 953">图片帮助</a>
                               <!-- {elseif $status eq 'CCB'} -->
                                   	<a target="_blank" href="/help/queryGeneralDetail?helpId= 949">图片帮助</a>
                               <!-- {elseif $status eq 'ABC'} -->
                                   	<a target="_blank" href="/help/queryGeneralDetail?helpId= 951">图片帮助</a>
                               <!-- {elseif $status eq 'BOC'} -->
                                   	<a target="_blank" href="/help/queryGeneralDetail?helpId= 955">图片帮助</a>
                               <!-- {elseif $status eq 'COMM'} -->
                                   	<a target="_blank" href="/help/queryGeneralDetail?helpId= 975">图片帮助</a>
                               <!-- {elseif $status eq 'CMBC'} -->
                                   	<a target="_blank" href="/help/queryGeneralDetail?helpId= 954">图片帮助</a>
                               <!-- {elseif $status eq 'CITIC'} -->
                                   	<a target="_blank" href="/help/queryGeneralDetail?helpId= 969">图片帮助</a>
                               <!-- {elseif $status eq 'SPDB'} -->
                                   	<a target="_blank" href="/help/queryGeneralDetail?helpId= 970">图片帮助</a>
                               <!-- {elseif $status eq 'PSBC'} -->
                                   	<a target="_blank" href="/help/queryGeneralDetail?helpId= 968">图片帮助</a>
                               <!-- {elseif $status eq 'CEB'} -->
                                   	<a target="_blank" href="/help/queryGeneralDetail?helpId= 972">图片帮助</a>
                               <!-- {elseif $status eq 'SPABANK'} -->
                                   	<a target="_blank" href="/help/queryGeneralDetail?helpId= 956">图片帮助</a>
                               <!-- {elseif $status eq 'GDB'} -->
                                   	<a target="_blank" href="/help/queryGeneralDetail?helpId= 974">图片帮助</a>
                               <!-- {elseif $status eq 'HXBANK'} -->
                                   	<a target="_blank" href="/help/queryGeneralDetail?helpId= 973">图片帮助</a>
                               <!-- {elseif $status eq 'CIB'} -->
                                   	<a target="_blank" href="/help/queryGeneralDetail?helpId= 967">图片帮助</a>
                               <!-- {/if} -->
                               <a href=""><!-- 视频帮助 --></a>
								</div>
                                
								<div class="time"><span class="minute" id="timeminute"></span>:<span class="second" id="timesecond"></span></div>
                                <input type="hidden" id ="expireTime"  value="{$expireTime}" />
                                <input type="hidden" id ="nowTime"  value="{$nowTime}" />
								<div class="text">为保障充值成功<br />请在<span class="color-red" id="Starttime">{$longTime}</span>分钟之内完成支付</div>
                                
							</div>
						</div>
                        <!-- {/if} -->
					</div>
					<dl class="prompt">
						<dt>充值说明：</dt>
						<dd>1.务必复制"附言"到{$bankName}汇款页面的"附言"栏中进行粘帖(键盘 CTRL+V)，否则充值将无法到账。</dd>
						<dd>2.附言为随机生成，一个附言只能充值一次，过期或重复使用充值将无法到账。</dd>
						<dd>3.收款账户名和账号会不定期更换，请在获取最新信息后充值，否则充值将无法到账。</dd>
						<dd>4.订单金额与网银转账金额不符时，以实际到账金额为准，且实际转账金额必须符合充值申请时的金额要求。</dd>
						<dd>5.登陆{$bankName}网银，  
						<!-- {if $status  eq  'ICBC'} -->
                             	点击"转账汇款"后选择"E-mail汇款"。
                        <!-- {elseif $status eq 'CMB'} -->
                             	点击"转账汇款"后选择"招行同行转账"。
                        <!-- {elseif $status eq 'CCB'} -->
                            	 点击"转账汇款"后选择"跨行转账"。
                        <!-- {elseif $status eq 'ABC'} -->
                             	点击"转账汇款"后选择"单笔转账"。
                        <!-- {elseif $status eq 'BOC'} -->
                             	点击"转账汇款"后选择"国内跨行汇款"。
                        <!-- {elseif $status eq 'COMM'} -->
                             	点击"转账"后选择"转其他银行"。
                        <!-- {elseif $status eq 'CMBC'} -->
                             	点击"转账汇款"后选择"跨行转账"。
                        <!-- {elseif $status eq 'CITIC'} -->
                            	点击"转账支付"后选择"跨行转账"。
                        <!-- {elseif $status eq 'SPDB'} -->
                             	点击"转账汇款"后选择"汇到其他银行"。
                        <!-- {elseif $status eq 'PSBC'} -->
                            	 点击"转账汇款"后选择"跨行汇款"。
                        <!-- {elseif $status eq 'CEB'} -->
                             	点击"转账汇款"后选择"向他行转账"。
                        <!-- {elseif $status eq 'SPABANK'} -->
                            	 点击"汇款转账"后选择"跨行实时转账"。
                        <!-- {elseif $status eq 'GDB'} -->
                             	点击"转账汇款"后选择"一站式转账"。
                        <!-- {elseif $status eq 'HXBANK'} -->
                            	点击"跨行业务"后选择"跨行即时汇款"。
                        <!-- {elseif $status eq 'CIB'} -->
                            	点击"转账汇款"后选择"实时跨行转出"。
                        <!-- {/if} -->              
                        </dd>
						<dd>6.充值金额≥300元返还手续费，充值手续费返款会以"充值让利"形式加入账户。</dd>
					</dl>
					<div class="help-text">更多帮助 <a target="_blank" href="/help/goIndex">进入帮助中心</a></div>
				</div>
			</div>
		</div>
		</form>
	</div> 
    
<div id="DivSuccessful" class="pop pop-success w-4" style="position:absolute;z-index:2; display:none">
	<i class="ico-success"></i><h4 class="pop-text">复制成功</h4>
</div>
<div id="DivFailed" class="pop pop-error w-4" style="position:absolute;z-index:2; display:none">
	<i class="ico-error"></i><h4 class="pop-text">复制失败，请重试</h4>
</div>

<div class="pop-ps" style="left: 461.5px; top: 674px; display: none;">
								<!-- {if $status  eq  'ICBC'} -->
                                	<img src="{$path_img}/images/funds/ps-gs.png" alt="">
                               	<!-- {elseif $status eq 'CMB'} -->
                                   	<img src="{$path_img}/images/funds/ps-zs.png" alt="">
                              	<!-- {elseif $status eq 'CCB'} -->
                                   	<img src="{$path_img}/images/funds/ps-js.png" alt="">
                                <!-- {elseif $status eq 'ABC'} -->
                                   	<img src="{$path_img}/images/funds/ps-ny.png" alt="">
                                <!-- {elseif $status eq 'COMM'} -->
                                   	<img src="{$path_img}/images/funds/ps-jt.png" alt="">
                                <!-- {elseif $status eq 'CMBC'} -->
                                   	<img src="{$path_img}/images/funds/ps-ms.png" alt="">
                                <!-- {elseif $status eq 'CITIC'} -->
                                   	<img src="{$path_img}/images/funds/ps-zx.png" alt="">
                                <!-- {elseif $status eq 'SPDB'} -->
                                   	<img src="{$path_img}/images/funds/ps-pf.png" alt="">
                                <!-- {/if} -->
</div>
<script  src="{$path_js}/js/phoenix.Common.js"></script>
{literal}
<script>

(function(){
	if($('#expireTime')[0]==undefined || $('#nowTime')[0] ==undefined)
	        return;
	var end_v=$('#expireTime')[0].value;
	var now_v=$('#nowTime')[0].value;
	if(end_v=="" || now_v =="")
	{
		return;
	}
	//时间倒计时,end_time:结束时间，now_time:服务器当前时间
	//expireTime，nowTime	
	var end_time=new Date(end_v).getTime();	
	var now_time= new Date(now_v).getTime();	
	var didf_time = (end_time-now_time)/1000;	
	countDown(didf_time,null,null,".minute",".second");
	
	//操作后提示	 
	function fnDiv(obj){		
		var Idivdok = document.getElementById(obj);	
		var winScroll = document.documentElement.scrollTop || document.body.scrollTop;
		Idivdok.style.display="block";		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+winScroll-40+"px";
	}
	
    $('.copy').each(function(index, element) {
        $(this).zclip({
			path:global_path_url+'/js/ZeroClipboard.swf', //记得把ZeroClipboard.swf引入到项目中 
			copy:function(){
				return $(this).parent().find("span").text();
			},
			afterCopy:function(){
				fnDiv('DivSuccessful');
				setTimeout(function(){ $('#DivSuccessful').css("display","none");},1500);
			}
		})
    });
	/* jmpinfo */
	var jmpTimer = null;
	var oIcon = $(".ico-ps");
	var oPop = $(".pop-ps");
	var curLeft = 0, curTop = 0;
	curLeft += oIcon.offset().left;
	curTop += oIcon.offset().top;

	oIcon.mouseout(function(){
		jmpTimer = setTimeout(function(){
			oPop.fadeOut();
		} ,300);
	});
	
	oIcon.mouseover(function(){
		clearTimeout(jmpTimer);
		oPop.css("left", curLeft + oIcon.width() + 5 + 'px')
		oPop.css("top", curTop + 'px')
		oPop.fadeIn();
	});
	
	oPop.mouseover(function(){
		clearTimeout(jmpTimer);
	});
	oPop.mouseout(function(){
		jmpTimer = setTimeout(function(){
			oPop.fadeOut();
		} ,300);
    });
	/* jmpinfo */
	var jmpTimer = null;
	var oIcon = $(".ico-ps");
	var oPop = $(".pop-ps");
	var curLeft = 0, curTop = 0;
	curLeft += oIcon.offset().left;
	curTop += oIcon.offset().top;

	oIcon.mouseout(function(){
		jmpTimer = setTimeout(function(){
			oPop.fadeOut();
		} ,300);
	});
	
	oIcon.mouseover(function(){
		clearTimeout(jmpTimer);
		oPop.css("left", curLeft + oIcon.width() + 5 + 'px')
		oPop.css("top", curTop + 'px')
		oPop.fadeIn();
	});
	
	oPop.mouseover(function(){
		clearTimeout(jmpTimer);
	});
	oPop.mouseout(function(){
		jmpTimer = setTimeout(function(){
			oPop.fadeOut();
		} ,300);
	});
    
    
	
})();
</script>	
{/literal}	
<!-- //////////////底侧公共页面////////////// -->
{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->