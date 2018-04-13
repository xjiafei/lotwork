<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
	{include file='/default/script-base.tpl'}
    <script type="text/javascript" src="{$path_js}/js/jquery.zclip.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.Common.js"></script>
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
		<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">我要充值</div>
				<div class="ui-tab-title tab-title-bg clearfix appeal-link-tab">
					 {include file='/default/ucenter/fund/recharge_index_title.tpl'}
				</div>
				<input type="hidden" id="chargeVersion" name="chargeVersion" value="{$chargeVersion}" />
				<!--{foreach from=$bankInfo item=data}-->
				<!--{if $data.code ==30 && $data.deposit ==1 && $isAilOpen=='Y'}-->
				<!--{if $data.version ==0}-->
				<!-- --------------------------------------------------------------個人版--------------------------------------------------------------- -->
					<div class="content bank-select">
						<div class="step">
							<table class="step-table">
								<tbody>
									<tr>
										<td><div class="con">1.选择银行并填写金额</div></td>
										<td class="current"><div class="tri"><div class="con">2.确认信息充值</div></div></td>
										<td><div class="tri"><div class="con">3.支付宝二维码支付</div></div></td>
									</tr>
								</tbody>
							</table>
						</div>
						<label class="ui-label" style="display: inline;font-size:18px">&nbsp;&nbsp;充值确认</label>
						<div class="bank-check payee-info">
							
							<ul class="ui-form">
							
								<li class="bank-select-from-li" >
									<label class="ui-label" style="display: inline">充值银行：</label>
									<div class="bank-more">
										<div class="bank-label">
										<input type="hidden" name="type" value="4" />
											
												<span class="ico-bank {$logo}" ></span>
										
										</div>
									</div>
								</li>
								
								<li>
									
									<label class="ui-label" style="display: inline">充值金额：</label>
									<span class="ui-singleline color-red">{$chargeamount}</span> 元
								</li>
								
								<li>
									
									<label class="ui-label" style="display: inline">付款支付宝：</label>
									<span class="ui-singleline color-red">{$bankAccount}&nbsp;{$nickName}&nbsp;{$bankNumber}</span>
								</li>
								<li>
									
									<label class="ui-label" style="display: inline">注意事项：</label>
									<span class="ui-singleline color-red">确保转账的支付宝账户信息与绑定的支付宝账号信息一致！</span>
								</li>
							</ul>
						
						</div>
						
						<div class="bank-check payee-info">
						
							<ul class="ui-form">
							<li><label class="ui-label" style="display: inline;font-size:18px">收款方信息</label></li>
							<li class="bank-select-from-li"  >
								<label class="ui-label" style="display: inline">支付宝二维条码：</label>
								
								<div style="text-align : center;vertical-align:middle ">
								<img id='aliQrCode' src="{$url}" width="200" height="200" alt=""/> 
								<!-- <img id='aliQrCode' src="http://imgout.ph.126.net/48007001/ALP011.jpg" width="200" height="200" alt=""/>-->
								<img  id='qrCodePs' src='{$path_img}/images/funds/ico-ps.png' style="position:absolute;top:200px;left:350px" />
								<img  id='qrCodeDemo' src='{$path_img}/images/funds/alipayQrCode-demo.png' 
									style='vertical-align:middle;display:none;z-index:3;position:absolute;top:0px;left:330px;width:300px;height:400px' />
								</div>
								
								<!-- 
								<div style="position:relative">
								<div id ='qr'  style="z-index:1">
								
								</div>
								
								<img id="ali" style="z-index:2; position:absolute;top:75px;left:75px" src="{$path_img}/images/admin/alipay50.png" width="50" height="50"/>
								</div> -->
								<input type="hidden" id="url" value='{$url}' />
								
								
								

							</li>
							<div class="text" style="text-align : center ">扫一扫二维码，可以向帐号充值哦</div>
							
							</ul>
						
						
							<div class="payment-help">
								<div class="bank-check">
									<div class="bank-title">付款帮助：
									<a href="/help/queryGeneralDetail?helpId=1743" target="_blank">支付宝如何充值</a>
									<a href="/help/queryGeneralDetail?helpId=1763" target="_blank">二维码使用说明</a>
									</div>
									<div id="J-time" class="time">--:--</div>
									<input type="hidden" id ="longTime"  value="{$longTime|default:'30'}" />
									<div class="text">为保证充值成功，请在<span class="color-red">{$longTime|default:'30'}</span>分钟之内完成支付</div>
								</div>
							</div>
						
						</div>
						
						<dl class="prompt">
							<dt>充值说明：</dt>
							<dd>1.请使用支付宝钱包的"扫一扫"进行充值付款。</dd>
							<dd>2.请勿保存平台提供的二维码信息，平台会不定期更换二维码充值信息，过期或重复使用充值将无法到帐，每次充值以平台获取的最新二维码为准。</dd>
							<dd>3.订单金额与扫码付款帐金额不符时，以实际到帐金额为准，且实际转帐金额必须符合充值申请时的金额要求。</dd>
							<dd>4.假如您付款有遇到任何问题，欢迎咨询在线客服。</dd>
						</dl>
						<div class="help-text">更多帮助 <a target="_blank" href="/help/goIndex">进入帮助中心</a></div>
					</div>
				<input type="hidden" id="chargeVersion" name="chargeVersion" value="0" />
				<!-- --------------------------------------------------------------個人版--------------------------------------------------------------- -->
				<!--{/if}		-->							
				<!--{if $data.version ==1}	-->			
				<!-- --------------------------------------------------------------企業版--------------------------------------------------------------- -->
					<div class="content bank-select">
						<div class="step">
							<table class="step-table">
								<tbody>
									<tr>
										<td><div class="con">1.选择银行并填写金额</div></td>
										<td class="current" id='step2'><div class="tri"><div class="con">2.确认信息充值</div></div></td>
										<td id='step3'><div class="tri"><div class="con">3.登陆支付宝付款</div></div></td>
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
									<label class="ui-label">支付方式：</label>
									<div class="ui-singleline">1.登陆手机支付宝扫一扫付款</div>
																	
								</li>
								<li>
									<label class="ui-label"></label>
									<div class="ui-singleline" style="margin-top:-40px;">2.去网页版支付宝登陆账户付款</div>
								</li>
							</ul>
						</div>
						<a href="javascript:void(0);" id="J-pay-for" url="{$url}" class="btn btn-important">去支付宝付款</a>
						<input type="hidden" id ="longTime"  value="{$longTime|default:'30'}" />
						<div class="help-text">更多帮助 <a target="_blank" href="/help/goIndex">进入支付宝充值帮助</a></div>
					</div>
				<input type="hidden" name="type" value="4" />
				<input type="hidden" id="chargeVersion" name="chargeVersion" value="1" />
				<!-- --------------------------------------------------------------企業版--------------------------------------------------------------- -->
				<!--{/if}-->
				<!--{/if}-->
				<!--{/foreach}-->
			</div>
			
		</div>
		
	</div>
    
     <!-- //////////////底侧公共页面////////////// -->
    {include file='/default/ucenter/footer.tpl'}
    <!-- /////////////底侧公共页面/////////////// -->
    {literal}
	<script>
	
    $(function(){
		var chargeVersion = document.getElementById("chargeVersion").value;
		

    
    	 /*$('#qr').qrcode(

    			 {
        			 width: 200,height: 200,text:  toUtf8($('#url').val())
        			 ,typeNumber:10,correctLevel    : 0
    			 }
		);*/

    	
		


    	 function toUtf8(str) {     

    		  var out, i, len, c;     
    		  out = "";     
    		  len = str.length;     
    		  for(i = 0; i < len; i++) {     
    		      c = str.charCodeAt(i);     
    		      if ((c >= 0x0001) && (c <= 0x007F)) {     
    		          out += str.charAt(i);     
    		      } else if (c > 0x07FF) {     
    		          out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));     
    		          out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));     
    		          out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));     
    		      } else {     
    		          out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));     
    		          out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));     
    		      }     
    		  }     
    		  return out;     

    		} 
        
        //充值有效时间倒计时
		
        var seconds =  $('#longTime').val()*60,
            timer,
            mm,
            ss;
        timer = setInterval(function(){
            if(seconds <= 0){
                clearInterval(timer);
                location.href = '/fund/checkchargeitem?type=4';
            }
            mm = Math.floor(seconds/60%60);
            ss = Math.floor(seconds%60);
            mm = mm < 10 ? '0' + mm : mm;
            ss = ss < 10 ? '0' + ss : ss;
            $('#J-time').text(mm + ':' + ss);
            seconds--;
        }, 1000);
        
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
		if(chargeVersion == 1){
			//企業版跳轉頁面用參數
			var msg = new phoenix.Message();
			var $payForButton = $('#J-pay-for');
			console.log($payForButton);
			var mask = phoenix.Mask.getInstance();
			msg.addEvent('beforeShow', function(){
				mask.show();
			});
			msg.addEvent('afterHide', function(){
				mask.hide();
			});
			$(".closeBtn").click(function(){
				msg.hide();
				mask.hide();
			});
			
			//去支付寶網頁充值弹层
			$payForButton.click(function(){
				$("#step2").removeClass("current");
				$("#step3").addClass("current");
				$('#J-pay-for').hide();
				var url = $(this).attr('url');
				window.open(url);
				
				//点击充值 停止超时提示
				clearInterval(timer);
				mask.show();
				msg.show({
					hideTitle: 'true',
					content: '<h3 style="height:30px;line-height:30px;text-align:center;">请在新开页面上完成汇款</h3><div style="height:30px;line-height:30px;"><a target="_blank" href="/help/queryGeneralDetail?helpId=923">付款遇到问题？</a></div>',
					confirmIsShow: 'true',
					confirmText: '查看付款结果',
					confirmFun: function(){
						window.open('/fund/history');
					},
					cancelIsShow: 'true',
					cancelText: '继续付款',
					cancelFun: function(){
						window.location.href="/fund/index?type=4&premount="+$('ul.ui-form li').eq(1).find('font').html();
					}
				});
			});




			
		};
	});

	$("#qrCodePs").hover(
			function() {
				$('#qrCodeDemo').show();

			  }, function() {

				  $('#qrCodeDemo').hide();

			  }	
	);

	$("#aliQrCode").hover(
			function() {
				$('#qrCodeDemo').show();

			  }, function() {

				  $('#qrCodeDemo').hide();

			  }	
	);
	
	
    </script>
    {/literal}
