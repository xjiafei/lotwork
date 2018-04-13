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
		
			<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">唯一附言</div>
				<div class="content">
					<div class="notice"><i class="ico-warning"></i>
					{if $remark neq ''}
					请牢记您的附言信息，充值过程中可填写此附言完成充值。（不区分大小写）
					{else}
					为了防止您的充值附言填写错误，请设置您的充值附言，设置成功后将可以使用您的唯一附言进行充值。
					{/if}
					</div>
					<input type="hidden" value="{$remark}" id="old_remark"/>
					<input type="hidden" value="{$modfiedDays}" id="modfiedDays"/>
					<ul id="J-recharge" class="recharge">
						<li>
							<label class="ui-label">唯一附言：</label>
							<span class="code-area"></span>
							<a id="cancel-annex" class="cancel" href="#">取消唯一附言</a>
							{if $cansetremark eq '1'}
							<a id="edit-annex" class="edit" href="#"><span>修改附言</span></a>
							{else}
							&nbsp;&nbsp;&nbsp;&nbsp;修改附言
							{/if}
							{if $modfiedDays neq '0'}
							<span>（每{$modfiedDays}天最多可修改一次）</span>
							{/if}
							<div class="clearfix"></div>
							<label class="ui-label"></label>
                            <a href="/fund/" class="rechargebutton btn">立即充值<b class="btn-inner"></b></a>
						</li>
					</ul>
					<ul id="J-apply-form" class="ui-form">
						<li style="display:none;">
							<label class="ui-label">当前附言：</label>
							<span class="code-area w-3"></span>
							{if $modfiedDays neq '0'}
							<span>（每{$modfiedDays}天最多可修改一次）</span>
							{/if}
						</li>
						<li>
							<label class="ui-label">选择获取方式：</label>
							<select id="J-method-change" class="ui-select w-3">
								<option value="0">---请选择-----------</option>
								<option value="1">系统生成</option>
								<option value="2">自定义</option>
							</select>
							<span id="J-select-desc"></span>
						</li>
						<li id="J-system-generate" class="system-generate">
							<div id="J-code-area" class="code-area w-3">c1254</div>
							<a id="J-reacquire" class="btn btn-disable" href="javascript:void(0);">重新获取（30） <b class="btn-inner"></b></a>
							<p>
								请记住您获得的唯一附言！（不区分大小写）
							</p>
							<div>
	                            <a href="javascript:;" class="normal-button enter-code btn btn-important">确认<b class="btn-inner"></b></a>
	                            <a href="javascript:;" class="normal-button goback-step btn">返回<b class="btn-inner"></b></a>
	                        </div>
						</li>
						<li id="J-canceled" class="canceled">
							<label class="ui-label"></label>
							<span class="ui-text-prompt">对不起，您已于 2014/01/01 取消了唯一附言服务，{$modfiedDays}天内无法再次设置。</span>
						</li>
						<li id="J-apply-area" class="ui-btn">
							<a id="J-apply-button" href="javascript:void(0);" class="btn">立即获取 <b class="btn-inner"></b></a>
						</li>
						<li id="J-custom" class="custom">
							<input id="J-custom-id" type="text" class="input w-3" maxlength="16" value="" />
							<p id="J-custom-desc" class="ui-text-info">请输入任意6-16位的数字与字母组合。（不区分大小写）</p>
							<div>
								<a href="javascript:;" class="normal-button enter-code btn btn-important">确认<b class="btn-inner"></b></a>
	                            <a href="javascript:;" class="normal-button goback-step btn">返回<b class="btn-inner"></b></a>
	                        </div>
						</li>
					</ul>
<div id="DivSuccessful" class="pop pop-success w-4" style="position:absolute;z-index:2; display:none">
	<i class="ico-success"></i><h4 class="pop-text">修改成功</h4>
</div>

<div id="DivFailed" class="pop pop-error w-4" style="position:absolute;z-index:2; display:none">
	<i class="ico-error"></i><h4 class="pop-text">修改失败，请重试</h4>
</div>
				</div>
			</div>
		</div>
	</div>
		
<!-- //////////////底侧公共页面////////////// -->
{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->
<script  src="{$path_js}/js/phoenix.Common.js"></script>
<script>
var old_remark = "{$remark|default:'0'}";
var cansetremark = "{$cansetremark|default:'0'}";
var cancleDate = "{$cancleDate|default:'0'}";
</script>
{literal}
	<script>
	(function($){
		var message = new phoenix.Message,
			countDownTime = null,
			countDownNum = 30,
			checkResultNum = null,
			codeType = 1,
			selectDom = $('#J-method-change'),
			buttonAreaDom = $('#J-apply-area'),
			showTimeAreaDom = $('#J-reacquire'),
			showTimeArea = $('#J-reacquire'),
			customDom = $('#J-custom'),
			generate = $('#J-system-generate'),
			applyButtonDom = $('#J-apply-button'),
			customAreaDom = $('#J-custom-id'),
			customDescDom = $('#J-custom-desc'),
			rechargeDom = $('#J-recharge'),
			notice = $('.notice'),
			goBackButtonDom = $('.goback-step'),
			modfiedDays = $("#modfiedDays");
		if(old_remark!='0'){
			$('#J-apply-form').hide();
			rechargeDom.find('.code-area').text(old_remark);
			notice.html('<i class="ico-warning"></i>请牢记您的附言信息，充值过程中可填写此附言完成充值。（不区分大小写）');
			rechargeDom.show();
		} else {
			if(cansetremark == '0'){
				var dateStr = '';
				if(cancleDate !='0'){
					dateStr = '于 <span class="color-red">'+ cancleDate +' </span>';
				}
					$('#J-select-desc').html('对不起，您已'+dateStr+'取消了唯一附言服务，'+modfiedDays.val()+'天内无法再次设置。');
				selectDom.attr('disabled','disabled').val(0);
				rechargeDom.hide();
				$('#J-apply-form li').eq(0).hide();
				$('#J-apply-form').show();
			}
		}
		//操作后提示	 
	function fnDiv(obj){		
		var Idivdok = document.getElementById(obj);	
			Idivdok.style.display="block";						
			Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft-150+"px";			
			Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
	}
		//获取
		getCodeNum = function(typeId) {
			var codeArea = $('#J-code-area');
			
			$.ajax({
				url: '/remark/getsystemremark/',
				type: 'POST',
				dataType: 'json',
				cache: false
				//data: {param1: 'value1'},
			})
			.done(function(r) {
				
				if(r.isSuccess == 1) {
					//$('#J-apply-form li').eq(0).hide();
					buttonAreaDom.hide();
					generate.show();
	
					//重新获取倒计时
					startCountDown();
					//显示结果
					codeArea.text(r['data']['code']);
					//缓存结果
					checkResultNum = r['data']['code'];
					codeType =1;
					//禁用下拉
					selectDom.attr('disabled','disabled');
				} else {
					var content = '';
					if(r.isSuccess == 0){
						content = '频繁获取附言,请'+(30-parseInt(r['data']['time']))+'秒后再试!';
					} else {
						content = r['data'];
					}
					message.show({
						mask: true,
						content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text" style="width:200px;">'+content+'</h4></div></div>',
						confirmIsShow: true,
						confirmText: '确定',
						confirmFun: function() {
							this.hide();
						}
					});
				}
			})
			.fail(function() {
				checkResultNum = null;
			})
			.always(function() {
			});
		}
	
		//获取倒计时
		startCountDown = function() {
	
			showTimeArea.attr('disabled','disabled');
	
			showTimeArea.html('重新获取（'+ countDownNum +'）<b class="btn-inner"></b>');
	
			countDownNum--;
	
			countDownTime = setInterval(function() {
	
				showTimeArea.html('重新获取（'+ countDownNum +'）<b class="btn-inner"></b>');
				
				//倒计时结束
				if(countDownNum == 0) {
					stopCountDown();
				}else {
					countDownNum--;
				}
			},1000);
		}
	
		//停止计时
		stopCountDown = function() {
	
			if(countDownTime) {
				showTimeArea.removeAttr('disabled').removeClass('btn-disable').text(' 重新获取 ');
				clearInterval(countDownTime);
				countDownTime = null;
				countDownNum = 30;
			}
		}
	
		//返回第一步选择
		goBackStep = function() {
			//禁用下拉
			selectDom.removeAttr('disabled');
			//隐藏自定义区域
			customDom.hide();
			//自定义文本框清空
			customAreaDom.val('');
			//请客提示信息
			customDescDom.html('请输入任意6-16位的数字与字母组合。（不区分大小写）');
			//隐藏系统生成区域
			generate.hide();
			//显示获取按钮
			buttonAreaDom.show();
			//停止获取计时
			stopCountDown();
			//重置结果
			checkResultNum = null;
		}
	
		//自定义号码
		customCode = function() {
			
			//展开自定义区域	
			customDom.show();
			//收起
			buttonAreaDom.hide();
			//
			selectDom.attr('disabled','disabled');
		}
	
		//校验自定义号码
		checkCustomNum = function(text) {
			var result,
				testRule = /^[0-9a-zA-Z]{6,16}$/g;
	
			if(!text) {
				return;
			}
	
			result = testRule.test(text);
	
			if(result) {
	
				$.ajax({
					url: '/remark/checkremarkexist/',
					type: 'post',
					dataType: 'json',
					data: {code: text}
				})
				.done(function(r) {
						
					if(r['isSuccess'] == 1) {
						customDescDom.html('<font color="green">'+r['msg']+'</font>');
						checkResultNum = text;
						codeType =2;
					} else {
						checkResultNum = null;
						customDescDom.html('<font color="red">'+r['msg']+'</font>');
					}
				})
				.fail(function() {
					checkResultNum = null;
					customDescDom.html('<font color="red">网络错误</font>');
				})
				.always(function() {
				});
				
			}else {
	
				checkResultNum = null;
				customDescDom.html('<font color="red">请输入任意6-16位的数字与字母组合</font>');
			}
		}
	
		//确认使用附言
		submitCodeNum= function() {
			
			if(checkResultNum) {
				var url='/remark/saveremark/';
				if(old_remark!='0'){
					url='/remark/editremark/';
				}
				$.ajax({
					url: url,
					type: 'post',
					dataType: 'json',
					data: {remarkName: checkResultNum,type:codeType}
				})
				.done(function(r) {
						
					if(r['isSuccess'] == 1) {
						$('#J-apply-form').hide();
						old_remark = checkResultNum;
						$("#J-custom-id").val("");
						rechargeDom.find('.code-area').text(checkResultNum);
						notice.html('<i class="ico-warning"></i>请牢记您的附言信息，充值过程中可填写此附言完成充值。（不区分大小写）');
						if(modfiedDays.val() !=0){
							$("#edit-annex").replaceWith(function(){
								  return $(this).find("span");
								 })
						}
						fnDiv('DivSuccessful');
				        setTimeout(function(){ $('#DivSuccessful').css("display","none");},1500);
						old_remark = '0';
						rechargeDom.show();
					} else {
						//确认取消弹窗
						message.show({
							mask: true,
							content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text" style="width:200px;">'+r['data']+'</h4></div></div>',
							confirmIsShow: true,
							confirmText: '确定',
							confirmFun: function() {
								this.hide();
							}
						});
					}
				})
				.fail(function() {
				})
				.always(function() {
				});
			}
	
			
		}
	
		//重新获取
		showTimeAreaDom.on('click', function() {
			var methodTypeId = selectDom.val();
			
			if($(this).hasClass('btn-disable')){
				return;
			}

			//获取附言号码
			getCodeNum(methodTypeId);
		});
	
		applyButtonDom.on('click', function(e){
			var methodTypeId = selectDom.val();
			
			//系统生成
			if(methodTypeId == 1) {
	
				//获取附言号码
				getCodeNum(methodTypeId);
			
			//自定义
			}else if(methodTypeId == 2) {
	
				//自定义
				customCode();
			}
		});
	
		//返回按钮
		goBackButtonDom.on('click', function() {
	
			goBackStep();
		});
	
		//自定义号码校验
		customAreaDom.on('blur', function() {
			var text = $.trim($(this).val());
	
			checkCustomNum(text);
		}).keyup(function(){
			var me=this,value=me.value;
			value=value.replace(/[^\w\.\/]/ig,'');
			me.value=value.replace(/[^a-z^A-Z^0-9]/g,'');
		});
	
		//确认使用号码
		$('.enter-code').on('click', function() {
			codeType = $("#J-method-change").val();
			if(codeType ==2){
				var text = $.trim($('#J-custom-id').val());
				checkCustomNum(text);
			}
			submitCodeNum();
		});
	
		//取消附言
		$('#cancel-annex').on('click', function() {
			var confirmStr = '';
			if(modfiedDays.val() !=0){
				confirmStr = '，且'+modfiedDays.val()+'天内无法再次启用';
			}
			//确认取消弹窗
			message.show({
				mask: true,
				content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text" style="width:360px;">请注意，取消后您将不能享受唯一附言充值服务'+confirmStr+'。</h4></div></div>',
				confirmIsShow: true,
				confirmText: '确定取消',
				confirmFun: function() {
					
					
					$.ajax({
						url: '/remark/delremark/',
						type: 'post',
						dataType: 'json',
						data: {}
					})
					.done(function(r) {
							
						if(r['isSuccess'] == 1) {
							message.hide();
							var message1 = new phoenix.Message;
							message1.show({
								mask: true,
								content: '<div class="bd text-center"><div class="pop-title"><i class="ico-success"></i><h4 class="pop-text" style="width:360px;">取消成功，您已取消唯一附言服务。</h4></div></div>',
								confirmIsShow: true,
								confirmText: '确认',
								confirmFun: function() {
									var date = new Date();
									this.hide();
									goBackStep();
									if(modfiedDays.val() !=0){
										$('#J-select-desc').html('对不起，您已于 <span class="color-red">'+ date.getFullYear() +'/'+ (date.getMonth()+1)+'/'+date.getDate() +' </span>取消了唯一附言服务，'+modfiedDays.val()+'天内无法再次设置。');
										selectDom.attr('disabled','disabled').val(0);
									}
									rechargeDom.hide();
									$('#J-apply-form li').eq(0).hide();
									$('#J-apply-form').show();
								}
							});
						} else {
							//确认取消弹窗
							message.hide();
							var message1 = new phoenix.Message;
							message.show({
								mask: true,
								content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text" style="width:200px;">'+r['msg']+'</h4></div></div>',
								cancelIsShow: true,
								cancelText: '返回',
								cancelFun: function() {
									this.hide();
								}
							});
						}
					})
					.fail(function() {
					})
					.always(function() {
					});
				},
				cancelIsShow: true,
				cancelText: '取消',
				cancelFun: function() {
					this.hide();
				}
			});
		});
	
		//修改附言
		$('#edit-annex').on('click', function() {
	
			goBackStep();
			rechargeDom.hide();
			$('#J-apply-form').find('.code-area').text(old_remark);
			$('#J-apply-form li').eq(0).show();
			$('#J-apply-form').show();
		});	
	
	})(jQuery);
</script>
{/literal}	
</body>
</html>