<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
		<!-- //////////////头部公共页面////////////// -->
			{include file='/admin/left.tpl'}
		<!-- /////////////头部公共页面/////////////// -->
				<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/user/">用户中心</a> &gt; <a href="/admin/user/accomplaints?search=1">账号申诉管理</a> &gt; 用户申诉审核</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title">用户提交的申诉资料</h3>
						<ul class="ui-form">
							<li>
								<label for="" class="ui-label">用户名：</label>
								<span class="ui-text-info">{$pleaduserinfo.account}<!-- {if $pleaduserinfo.vipLvl != '' && $pleaduserinfo.vipLvl != '0'} -->&nbsp;<img src="{$path_img}/images/ucenter/safe/vip/vip{$pleaduserinfo.vipLvl}.png"/><!-- {/if} --></span>
							</li>
							<li>
								<label for="" class="ui-label">申诉类型：</label>
								<span class="ui-text-info">{if $pleaduserinfo.appealType==1}安全邮箱{else}安全信息（登录密码、安全密码、安全问题）{/if}</span>
							</li>
							<li>
								<label for="" class="ui-label">上传身份证扫描件：</label>
								<!-- {if $idCopy} -->
								<a href="{$dynamicroot}/upload/images/{$idCopy}" target="_blank"><img width="120" height="90" class="vertical-top" src="{$dynamicroot}/upload/images/{$idCopy}"  title="身份证扫描件正面照" /></a>
								<!-- {else} -->
									<a href="{$path_img}/images/admin/bg-header.jpg" target="_blank"><img width="120" height="90" class="vertical-top" src="{$path_img}/images/admin/bg-header.jpg"  title="身份证扫描件正面照" /></a>
								<!-- {/if} -->
							</li>
							<li>
                                 <label for="" class="ui-label">已绑定的银行卡信息：</label>
								<span class="ui-text-info">{$cardstruc.name} {$cardstruc.no}</span>
							</li>
							<li>
								<label for="" class="ui-label">已绑定的银行卡扫描件：</label>
								<!-- {if $cardstruc.pic1} -->
									<a href="{$dynamicroot}/upload/images/{$cardstruc.pic1}" target="_blank"><img width="120" height="90" class="vertical-top" src="{$dynamicroot}/upload/images/{$cardstruc.pic1}"  title="证件扫描件正面照" /></a>
								<!-- {else} -->
									<a href="{$path_img}/images/admin/bg-header.jpg" target="_blank"><img width="120" height="90" class="vertical-top" src="{$path_img}/images/admin/bg-header.jpg"  title="证件扫描件正面照" /></a>
								<!-- {/if} -->
									
								<!-- {if $cardstruc.pic2} -->
									<a href="{$dynamicroot}/upload/images/{$cardstruc.pic2}" target="_blank"><img width="120" height="90" class="vertical-top" src="{$dynamicroot}/upload/images/{$cardstruc.pic2}"  title="证件扫描件45度照" /></a>
								<!-- {else} -->
									<a href="{$path_img}/images/admin/bg-header.jpg" target="_blank"><img width="120" height="90" class="vertical-top" src="{$path_img}/images/admin/bg-header.jpg"  title="证件扫描件45度照" /></a>		
								<!-- {/if} -->
							</li>
							<li>
								<label for="" class="ui-label">账号注册地点：</label>
								<span class="ui-text-info">{$pleaduserinfo.registerArea}</span>
							</li>
							<li>
								<label for="" class="ui-label">经常登录地区：</label>
								<span class="ui-text-info">{$pleaduserinfo.loginArea}</span>
							</li>
						</ul>
						<h3 class="ui-title">客服填写审核资料</h3>
						<!-- {if $pleaduserinfo.passed eq '3'} -->
	                       <form action="plead" method="post" id="formPlead">
	                           <input type="hidden" name="id" value="{$pleaduserinfo.id}" />
	                           <input type="hidden" name="account" value="{$pleaduserinfo.account}" />
	                           <input type="hidden" name="email" value="{$pleaduserinfo.email}" />
	                           <input type="hidden" name="appealType" value="{$pleaduserinfo.appealType}" />
	                           <ul class="ui-form">
								<li>
									<label for="" class="ui-label">审核结果：</label>
									<label class="label" for="rs1"><input type="radio"  name="passed"  class="radio" value="1" id="rs1"  checked="checked">通过</label>
									<label class="label" for="rs2"><input type="radio"  name="passed"  class="radio" value="2" id="rs2" >未通过</label>
								</li>
								<li>
									<label for="" class="ui-label">接受结果邮箱：</label>
									<span class="ui-text-info">{$pleaduserinfo.emailSecret}</span>
								</li>
	                            <!-- {if $pleaduserinfo.appealType!=1} -->
								<li id="li-activedate-time">
									<label for="" class="ui-label">安全信息生效时间：</label>
									<label class="label" for="rs3"><input type="radio"  name="activedate"  class="radio" value="0" id="rs3" checked="checked">立即生效</label>
									<label class="label" for="rs4"><input type="radio"  name="activedate"  class="radio" value="1" id="rs4">1天后生效</label>
									<label class="label" for="rs5"><input type="radio"  name="activedate"  class="radio" value="3" id="rs5">3天后生效</label>
									<label class="label" for="rs6"><input type="radio"  name="activedate"  class="radio" value="5" id="rs6">5天后生效</label>
									<label class="label" for="rs7"><input type="radio"  name="activedate"  class="radio" value="7" id="rs7">7天后生效</label>
								</li>
	                            <!-- {/if} -->
								<li>
									<label class="ui-label" for="">客服审核备注：</label>
									<div class="textarea w-7">
										<textarea id="text" name="memo"></textarea>
									</div>
								</li>
								<li class="ui-btn">
	                                <input type="button" value="提 交" id="btnPlead" class="btn btn-important" href="javascript:void(0);"></input>
									<a class="btn" href="/admin/user/accomplaints" >取 消<b class="btn-inner"></b></a>
								</li>
	                           </ul>
	                           </form>
                           <!-- {else} -->
		                           <ul class="ui-form">
									<li>
										<label for="" class="ui-label">审核结果：</label>
										<label class="label" for="rs1"><input type="radio"  name="passed" disabled  class="radio" value="1" id="rs1" {if $pleaduserinfo.passed eq '1'}checked="checked"{/if}>通过</label>
										<label class="label" for="rs2"><input type="radio"  name="passed" disabled  class="radio" value="2" id="rs2" {if $pleaduserinfo.passed eq '2'}checked="checked"{/if} >未通过</label>
									</li>
									<li>
										<label for="" class="ui-label">接受结果邮箱：</label>
										<span class="ui-text-info">{$pleaduserinfo.emailSecret}</span>
									</li>
		                            <!-- {if $pleaduserinfo.appealType!=1} -->
									<li id="li-activedate-time">
										<label for="" class="ui-label">安全信息生效时间：</label>
										<label class="label" for="rs3"><input type="radio"  name="activedate" disabled  class="radio" value="0" id="rs3" {if $pleaduserinfo.activedDays eq '0'}checked="checked"{/if}>立即生效</label>
										<label class="label" for="rs4"><input type="radio"  name="activedate" disabled  class="radio" value="1" id="rs4" {if $pleaduserinfo.activedDays eq '1'}checked="checked"{/if}>1天后生效</label>
										<label class="label" for="rs5"><input type="radio"  name="activedate" disabled  class="radio" value="3" id="rs5" {if $pleaduserinfo.activedDays eq '3'}checked="checked"{/if}>3天后生效</label>
										<label class="label" for="rs6"><input type="radio"  name="activedate" disabled  class="radio" value="5" id="rs6" {if $pleaduserinfo.activedDays eq '5'}checked="checked"{/if}>5天后生效</label>
										<label class="label" for="rs7"><input type="radio"  name="activedate" disabled  class="radio" value="7" id="rs7" {if $pleaduserinfo.activedDays eq '7'}checked="checked"{/if}>7天后生效</label>
									</li>
		                            <!-- {/if} -->
									<li>
										<label class="ui-label" for="">客服审核备注：</label>
										<div class="textarea w-7">
											<textarea id="text" readonly name="memo">{$pleaduserinfo.memo}</textarea>
										</div>
									</li>
		                           </ul>
                           <!-- {/if} -->
                           <div class="pop w-4" style="position:absolute;left:100px; display:none" id="divOk">
							<i class="ico-success"></i>
							<h4 class="pop-text">审核成功</h4>
						   </div>
                           <div class="pop w-4" style="position:absolute;left:400px; display:none" id="divNo">
							<i class="ico-error"></i>
							<h4 class="pop-text">审核失败，请重试</h4>
						   </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script  src="{$path_js}/js/jquery-1.9.1.min.js"></script>
    <script src="{$path_js}/js/phoenix.Common.js" type="text/javascript" ></script>
	{literal}
	<script > 
	(function($){	
		//一、二级菜单选中样式加载	
		selectMenu('MenuUser','MenuUseraccomplaints');
		
				//弹窗居中显示层
		function fn(obj){
			var Idivdok = document.getElementById(obj);	
			Idivdok.style.display="block";			
			Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
			Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)+document.documentElement.scrollTop-40+"px";	
   		} 	
		$("#btnPlead").click(function(){
			 $.ajax({
				url:'/admin/user/plead',
				dataType:'json',
				method:'post',
				data:$("#formPlead").serialize(),
				beforeSend:function(){
					//禁用发送								
					var button = $('#btnPlead'),list = ['提交中   ', '提交中.  ', '提交中.. ', '提交中...'],i = 0;
						interval=setInterval(function(){
							button.val(list[i]);
							i += 1;
							if(i >= list.length){
								i = 0;
							}
						}, 300);
						button.attr("disabled","disabled");	
				},
				success:function(data){
					if(data["isSuccess"]==1)
					{
						fn("divOk");	
						setTimeout(function(){  window.location.href=window.location.href;},3000);
					}else{
						fn("divNo");	
						setTimeout(function(){$("#divNo").css("display","none");},3000);				
					}
				},
				complete:function(){
					clearInterval(interval);
					var button = $('#btnPlead');
					button.val("提 交");	 	
					button.removeAttr("disabled","disabled");
				}
			});			
		});
		
		$("[name='passed']").click(function(){
			if($(this).val()=="2")
			{
				$("#li-activedate-time").hide();
			}
			else 
			{
				$("#li-activedate-time").show();
			}
		});
	})(jQuery);
	</script>
	{/literal}
</body>
</html>