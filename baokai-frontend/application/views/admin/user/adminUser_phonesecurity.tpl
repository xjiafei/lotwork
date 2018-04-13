<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
		<!-- //////////////头部公共页面////////////// -->
			{include file='/admin/left.tpl'}
		<!-- /////////////头部公共页面/////////////// -->
		
		<div class="col-main">
				<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/user/">用户中心</a> &gt; 
			<!-- {if $_GET.typepage eq '1'} -->
			<a href="/admin/user/list?search=0"><span id="menu2">客户列表</span></a> 
			<!-- {else if $_GET.typepage eq '2'} -->
			<a href="/admin/proxy/index?search=0"><span id="menu2">总代管理</span></a> 
			<!-- {else} -->
			<a href="/admin/user/accomplaints?search=1"><span id="menu2">账号申诉管理</span></a> 
			<!-- {/if} -->
			&gt; <sapn id="menu3">{$_GET.account}</sapn>
			</div></div>
		
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
								<!-- {if ("USER_MANAGE_LIST_INFO_USERINFO"|in_array:$smarty.session.datas.info.acls and $_GET.typepage=='1') or ("USER_MANAGE_TOPAGENT_LIST_INFO_USERINFO"|in_array:$smarty.session.datas.info.acls and $_GET.typepage=='2') or ("USER_EXCEPTION_APPEAL_INFO_USERINFO"|in_array:$smarty.session.datas.info.acls and $_GET.typepage=='3')} -->
									<li><a  href="/admin/user/userdetail?id={$_GET.id}&typepage={$_GET.typepage}">基本资料</a></li>
								<!-- {/if} -->
								<!-- {if ("USER_MANAGE_LIST_INFO_BINDCARD"|in_array:$smarty.session.datas.info.acls and $_GET.typepage=='1') or ("USER_MANAGE_TOPAGENT_LIST_INFO_BINDCARD"|in_array:$smarty.session.datas.info.acls and $_GET.typepage=='2') or ("USER_EXCEPTION_APPEAL_INFO_BINDCARD"|in_array:$smarty.session.datas.info.acls and $_GET.typepage=='3')} -->
									<li><a href="/admin/user/bankcard?id={$_GET.id}&account={$_GET.account}&typepage={$_GET.typepage}">查看银行卡</a></li>
								<!-- {/if} -->
								<!-- {if ("USER_MANAGE_LIST_INFO_PRIZE"|in_array:$smarty.session.datas.info.acls and $_GET.typepage=='1') or ("USER_MANAGE_TOPAGENT_LIST_INFO_PRIZE"|in_array:$smarty.session.datas.info.acls and $_GET.typepage=='2') or ("USER_EXCEPTION_APPEAL_INFO_PRIZE"|in_array:$smarty.session.datas.info.acls and $_GET.typepage=='3')} -->
									<li><a href="/admin/user/bonusrebate?id={$_GET.id}&account={$_GET.account}&typepage={$_GET.typepage}">奖金返点</a></li>
								<!-- {/if} -->
									<li class="current">手机令牌</li>
								</ul>
							</div>
							<form action="#" method="POST" id="J-form">
                              <input type="hidden" name="id" value="{$_GET.id}" />
                              <input type="hidden" name="typepage" value="{$_GET.typepage|default:1}" />
							<div >
								<ul class="ui-form">
									<!-- {if $result.BIND_PHONE_SERIAL eq '1'} -->
									<li>
										<label class="ui-label" for=""><b>绑定时间：</b></label>
										<span class="ui-text-info">{$result.BIND_DATE}</span>
									</li>
									<li>
										<label class="ui-label" for=""><b>序列号：</b></label>
										<span class="ui-text-info">{$result.PHONE_SERIAL_NUM}</span>
									</li>
									<li>
										<label class="ui-label" for=""><b>状态：</b></label>
										<span class="ui-text-info">{if $result.LOCK_STATUS}锁定中{else}正常{/if}</span>
									</li>
									<li>
										<label class="ui-label" for=""><b>操作：</b></label>
										<span class="ui-text-info">
										<!-- {if "USER_MANAGE_LIST_INFO_MOBILETOKEN_UNBIND"|in_array:$smarty.session.datas.info.acls or "USER_MANAGE_TOPAGENT_LIST_INFO_MOBILETOKEN_UNBIND"|in_array:$smarty.session.datas.info.acls or "USER_EXCEPTION_APPEAL_INFO_MOBILETOKEN_UNBIND"|in_array:$smarty.session.datas.info.acls} -->
											<!-- {if $result.LOCK_STATUS} -->
											<a href="javascript:void(0);" id="unlockBtn" preid="{$_GET.id}">解除锁定</a>
											<!-- {/if} -->
										<!-- {/if} -->
										<!-- {if "USER_MANAGE_LIST_INFO_MOBILETOKEN_UNLOCK"|in_array:$smarty.session.datas.info.acls or "USER_MANAGE_TOPAGENT_LIST_INFO_MOBILETOKEN_UNLOCK"|in_array:$smarty.session.datas.info.acls or "USER_EXCEPTION_APPEAL_INFO_MOBILETOKEN_UNLOCK"|in_array:$smarty.session.datas.info.acls} -->
											<!-- {if $result.BIND_PHONE_SERIAL eq '1'} -->
											<a href="javascript:void(0);" id="unbindBtn" preid="{$_GET.id}">解除绑定</a>
											<!-- {/if} -->
										<!-- {/if} -->
										</span>
									</li>
									<!-- {else} -->
									<li>
										<label class="ui-label" for=""></label><span class="ui-text-info"><b>该用户当前未绑定手机令牌</b></span>
									</li>
									<!-- {/if} -->
								</ul>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div style="position:absolute;z-index:2; display:none" class="pop pop-success w-5" id="DdivOK">
			<i class="ico-success"></i>
			<h4 class="pop-text">修改成功</h4>
		</div>
		
		<div style="position:absolute;z-index:2; display:none" class="pop pop-error w-5" id="DivDeleteno" >
			<i class="ico-error"></i>
			<h4 class="pop-text">修改失败,请重试</h4>
		</div>
		</div>
	</div>	
{include file='/admin/script-base.tpl'}
<script  src="{$path_js}/js/jquery-1.9.1.min.js"></script>
{literal} 
	
	<script>
	(function($){	
		//一级菜单选中样式加载
		var type = $('input[name="typepage"]').val(),msg = new phoenix.Message(),msg1 = new phoenix.Message();
		if(type!=3){
			type = type -1;
		}
		$('.menu-list li').removeAttr("class");			
		$('.menu-list li:eq(2)').attr("class","current");
		$('.col-side .nav dd:eq('+type+')').attr("class","current");
		
		//--------------时间三级联动联动开始------------------------
			
		//操作后提示	 
		function fn(obj){
			var Idivdok = document.getElementById(obj);	
			Idivdok.style.display="block";						
			Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
			Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
		}
		
		$('#unbindBtn').click(function(){
			var id = $(this).attr('preid');
			msg.show({
				mask: true,
				content: '<h3 style="height:30px;line-height:30px;text-align:center;">确定解除绑定吗?</h3><div style="height:30px;line-height:30px;"></div>',
				confirmIsShow: 'true',
				confirmText: '确定',
				confirmFun: function(){
					$.ajax({
						url:'/admin/user/unbindphonesecurity/',				
						dataType:'json',
						method:'post',
						data:'id='+id,					
						success:function(data){
							msg.hide();
							msg1.show({
								mask: true,
								content: '<h3 style="height:30px;line-height:30px;text-align:center;">'+data.msg+'!</h3><div style="height:30px;line-height:30px;"></div>',
								confirmIsShow: 'true',
								confirmText: '确定',
								confirmFun: function(){
									if(data['isSuccess'] =='1'){	
										location.reload();
									} else {
										msg1.hide();
									}
								}
							});
						}					
					});
				},
				cancelIsShow: 'true',
				cancelText: '取消',
				cancelFun: function(){
					msg.hide();
				}
			});
		});
		
		$('#unlockBtn').click(function(){
			var id = $(this).attr('preid');
			msg.show({
				mask: true,
				content: '<h3 style="height:30px;line-height:30px;text-align:center;">确定解除锁定吗?</h3><div style="height:30px;line-height:30px;"></div>',
				confirmIsShow: 'true',
				confirmText: '确定',
				confirmFun: function(){
					$.ajax({
						url:'/admin/user/unlockphonesecurity/',				
						dataType:'json',
						method:'post',
						data:'id='+id,					
						success:function(data){
							
							msg.hide();
							msg1.show({
								//hideTitle: 'false',
								content: '<h3 style="height:30px;line-height:30px;text-align:center;">'+data.msg+'!</h3><div style="height:30px;line-height:30px;"></div>',
								confirmIsShow: 'true',
								confirmText: '确定',
								confirmFun: function(){
									if(data['isSuccess'] =='1'){
										location.reload();
									} else {
										msg1.hide();
									}
								}
							});
						}					
					});
				},
				cancelIsShow: 'true',
				cancelText: '取消',
				cancelFun: function(){
					msg.hide();
				}
			});
		});
		
	})(jQuery);
	</script>
{/literal}
