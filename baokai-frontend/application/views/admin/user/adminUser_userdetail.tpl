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
			{if $typepage eq '1'}
			<a href="/admin/user/list?search=0"><span id="menu2">客户列表</span></a> 
			{else if $typepage eq '2'}
			<a href="/admin/proxy/index?search=0"><span id="menu2">总代管理</span></a> 
			{else}
			<a href="/admin/user/accomplaints?search=1"><span id="menu2">账号申诉管理</span></a> 
			{/if}
			&gt; <sapn id="menu3">{$userStruc.account}</sapn>
			</div></div>
		
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<li class="current">基本资料</li>
								<!-- {if ("USER_MANAGE_LIST_INFO_BINDCARD"|in_array:$smarty.session.datas.info.acls and $typepage=='1') or ("USER_MANAGE_TOPAGENT_LIST_INFO_BINDCARD"|in_array:$smarty.session.datas.info.acls and $typepage=='2') or ("USER_EXCEPTION_APPEAL_INFO_BINDCARD"|in_array:$smarty.session.datas.info.acls and $typepage=='3')} -->
									<li><a href="/admin/user/bankcard?id={$userStruc.id}&account={$userStruc.account}&typepage={$typepage}">查看银行卡</a></li>
								<!-- {/if} -->
								<!-- {if ("USER_MANAGE_LIST_INFO_PRIZE"|in_array:$smarty.session.datas.info.acls and $typepage=='1') or ("USER_MANAGE_TOPAGENT_LIST_INFO_PRIZE"|in_array:$smarty.session.datas.info.acls and $typepage=='2') or ("USER_EXCEPTION_APPEAL_INFO_PRIZE"|in_array:$smarty.session.datas.info.acls and $typepage=='3')} -->
									<li><a href="/admin/user/bonusrebate?id={$userStruc.id}&account={$userStruc.account}&typepage={$typepage}">奖金返点</a></li>
								<!-- {/if} -->
								<!-- {if ("USER_MANAGE_LIST_INFO_MOBILETOKEN"|in_array:$smarty.session.datas.info.acls and $typepage=='1') or ("USER_MANAGE_TOPAGENT_LIST_INFO_MOBILETOKEN"|in_array:$smarty.session.datas.info.acls and $typepage=='2') or ("USER_EXCEPTION_APPEAL_INFO_MOBILETOKEN"|in_array:$smarty.session.datas.info.acls and $typepage=='3')} -->
									<li><a href="/admin/user/getphonesecurity?id={$userStruc.id}&account={$userStruc.account}&typepage={$typepage}">手机令牌</a></li>
								<!-- {/if} -->
								</ul>
							</div>
							<form action="#" method="POST" id="J-form">
                              <input type="hidden" name="id" value="{$userStruc.id}" />
                              <input type="hidden" name="typepage" value="{$typepage|default:1}" />
							<div >
								<ul class="ui-form">
									<li>
										<label class="ui-label" for="">用户名：</label>
										<span class="ui-text-info">{$userStruc.account}<!-- {if $userStruc.vipLvl != '' && $userStruc.vipLvl != '0'} -->
										&nbsp;<img src="{$path_img}/images/ucenter/safe/vip/vip{$userStruc.vipLvl}.png"/><!-- {/if} -->
										<!-- {if $userStruc.freezeMethod neq '0'} -->&nbsp;<img src="{$path_img}/images/ucenter/safe/freeze.png"/><!-- {/if} --></span>
									</li>
									<li>
										<label class="ui-label" for="">可用余额：</label>
										<span class="ui-text-info">{$userStruc.availBal}元</span>
									</li>
									<li>
										<label class="ui-label" for="">冻结余额：</label>
										<span class="ui-text-info">{$userStruc.freezeBal}元</span>
									</li>
									<li>
										<label class="ui-label" for="">团队余额：</label>
										<span class="ui-text-info">{$userStruc.teamBal}元</span>
									</li>
									<li>
										<label class="ui-label" for="">性别：</label>								
										<label for="rr1"><input type="radio" name="sex" value="0" {if $userStruc.sex==0}checked{/if} class="radio" id="rr1" />男</label>
										<label for="rr2"><input type="radio" name="sex" value="1" {if $userStruc.sex==1}checked{/if} class="radio" id="rr2"  style=" margin-left:10px"/>女</label>
									</li>
									<li>
										<label class="ui-label" for="">生日：</label>								
										
										<select id="sltYear" name="sltYear"    class="ui-select w-2">
											<option>{if $userStruc.sltyear==''}请选择{else}{$userStruc.sltyear}{/if}</option>
                                                                                        
										</select>
										
										<select id="sltMonth" name="sltMonth"   class="ui-select w-2">
											<option >{if $userStruc.sltmonth==''}请选择{else}{$userStruc.sltmonth}{/if}</option>                            
										</select>
										
										<select id="sltDay" name="sltDay" class="ui-select w-2">
											<option >{if $userStruc.sltday==''}请选择{else}{$userStruc.sltday}{/if}</option>
										</select> 
										<div class="ui-check"  id="errData"><i class="error"></i>请确保日期完正性</div>
								
									</li>
									<li>
										<label class="ui-label" for="">预留验证信息：</label>
										<input type="text" class="input w-5"  name="cipher" value="{$userStruc.cipher}" />
									</li>
									<li>
										<label class="ui-label" for="">手机号码：</label>
										<input type="text" id="J-mobile" class="input w-5" name="vcellphone" value="{$userStruc.vcellphone}" data-old-value="{$userStruc.cellphone}" />
										<input type="hidden" id="J-dmobile" class="input w-5" name="cellphone" value="{$userStruc.cellphone}" data-old-value="{$userStruc.cellphone}" />
									</li>
									<li>
										<label class="ui-label" for="">VIP客户电话：</label>
										<input type="text" id="J-vvipcellphone" class="input w-5" name="vvipCellphone" value="{$userStruc.vvipCellphone}" data-old-value="{$userStruc.vipCellphone}" />
										<input type="hidden" id="J-vipcellphone" class="input w-5" name="vipCellphone" value="{$userStruc.vipCellphone}" data-old-value="{$userStruc.vipCellphone}" />
									</li>
                                     {foreach from=$qq key=k item=q}
									<li>
										<label class="ui-label" for="">QQ</label>
										<input type="text" name="qq[{$k}][nickName]" class="input w-2" value="{$q.nickName}" />
										<input type="text" name="vqq[{$k}][qq]" class="input w-2" value="{$q.vqq}" data-old-value="{$q.qq}" />
										<input type="hidden" name="qq[{$k}][qq]" class="input w-2" value="{$q.qq}" data-old-value="{$q.qq}" />
									</li>
									{/foreach}
									<li>
										<label class="ui-label" for="">邮箱：</label>
										<span class="ui-text-info">{$userStruc.email} {if $userStruc.emailActived == 0}(未绑定){else if $userStruc.emailActived == 1} (已绑定) {/if}</span>
									</li>
									<!-- <li>
										<label class="ui-label" for="">锁定银行卡绑定功能：</label>
						
										<label for="rs1"><input type="radio" name="cardstatus" value="0" {if $userdetail.cardstatus == 1} checked="checked" {/if} class="radio" id="rs1" />锁定</label>
										<label for="rs2"><input type="radio" name="cardstatus" value="1" {if $userdetail.cardstatus == 0} checked="checked" {/if} class="radio" id="rs2"   style=" margin-left:10px"/>开启 （默认）</label>
		
									</li> -->
									<li>
										<label class="ui-label" for="">安全问题：</label>
										<span class="ui-text-info">{if $qu<>''}已设置{else}未设置{/if}</span>
									</li>
									<li>
										<label class="ui-label" for="">登录密码：</label>
										<span class="ui-text-info">{if $userStruc.passwd<>''}已设置{else}未设置{/if}</span>
									</li>
									<li>
										<label class="ui-label" for="">安全密码：</label>
										<span class="ui-text-info">{if $userStruc.withdrawPasswd<>''}已设置{else}未设置{/if}</span>
									</li>
									<li>
										<label class="ui-label" for="">注册时间：</label>
										<span class="ui-text-info">{$userStruc.registerDate}[{if $userStruc.registerIp<>''}{$userStruc.registerIp}{else}0.0.0.0{/if}] {$userStruc.registerAddress}</span>
									</li>
                                    <!-- {foreach from=$loginStruc key=key item=ls} -->
									<li>
										
										<label class="ui-label" for="" style="width:140px;">{if $key eq '0'}最近五次登录记录：{/if}</label>
										
										<span class="ui-text-info">{$ls.loginDate} [{if $ls.loginIp<>''}{$ls.loginIp}{else}0.0.0.0{/if}] {$ls.loginAddress}</span>
									</li>
									<!-- {/foreach} -->
									<!-- {if ("USER_MANAGE_LIST_INFO_USERINFO_SUBMIT"|in_array:$smarty.session.datas.info.acls and $typepage=='1') or ("USER_MANAGE_TOPAGENT_LIST_INFO_USERINFO_SUBMIT"|in_array:$smarty.session.datas.info.acls and $typepage=='2') or ("USER_EXCEPTION_APPEAL_INFO_USERINFO_SUBMIT"|in_array:$smarty.session.datas.info.acls and $typepage=='3')} -->
									<li class="ui-btn">
										<a class="btn btn-important" href="javascript:void(0);" id="J-Submit">修 改<b class="btn-inner"></b></a>
										<a class="btn" href="javascript:void(0);" id="J-Clean">取 消<b class="btn-inner"></b></a>
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
<script  src="{$path_js}/js/jquery-1.9.1.min.js"></script>
<script  src="{$path_js}/js/phoenix.Common.js"></script>
{literal} 
	
	<script>
	(function($){	
		//一级菜单选中样式加载
		var type = $('input[name="typepage"]').val(),menuName="";
		if(type!=3){
			type = type -1;
		}
		if(type==0)
		{
			menuName="MenuUserlist";
		}
		else if(type==1)
		{
			menuName="MenuUsermanage";
		}else if(type==3)
		{
			menuName="MenuUseraccomplaints";
		}
		//一、二级菜单选中样式加载	
		selectMenu('MenuUser',menuName);

		var years=$('#sltYear'),months=$('#sltMonth'),days=$('#sltDay'),arg = GetRequest();
		
		//导航加载
		if (arg != undefined) {
			var aid = arg["typepage"];
			if (aid != undefined){				
				if(aid=="2"){	$('#menu1').html("客户管理");	$('#menu2').html("总代管理");}  //加载从总代管理进入
				else if(aid=="3"){	$('#menu1').html("客户异常");	$('#menu2').html("账号申诉管理");}    //加载账号申诉管理
				else{	$('#menu1').html("客户管理 ");	$('#menu2').html(" 客户列表 ");}  //加载从客户列表进入
			}
		}
		//--------------时间三级联动联动开始------------------------
			
		// if($('#sltYear')[0].value =="请选择"){	 
		Gettimes();
		$('#J-form').on('change', '#sltYear', function(e){	 CheckYear(this.value);	 });
		$('#J-form').on('change', '#sltMonth', function(e){ CheckMonth(this.value); });	
		//}	 
		function Gettimes() {	var aa = document.getElementById("sltYear");	BindYear(aa.options[0].value);	CheckYear(aa);	}
		
		function BindYear(aa) {

			var iYear = new Date().getFullYear();
			//debugger
			for (var i = iYear; i >= 1900; i--) {
				var mYear = document.getElementById("sltYear").value;
				if (i != aa) {
					document.getElementById("sltYear").options.add(new Option("" + i + "", i));
				}
			}
		}
		function append(o, v) {
			var option = new Option(v, v);
			o.options.add(option);
		}
	
		function CheckYear(obj) {
			var iYear = new Date().getFullYear();
			var iMonth = new Date().getMonth();
			var objMonth = document.getElementById("sltMonth");
			var objDay = document.getElementById("sltDay");
			var option_M = objMonth.options[0];
			objMonth.options.length = 0;
			objMonth.options.add(option_M);
			var option_D = objDay.options[0];
			objDay.options.length = 0;
			objDay.options.add(option_D);
			var mm = document.getElementById("sltMonth");
			if (obj != -1) {
				
					for (var i = 1; i <= 12; i++) {
						if (i != mm.options[0].value) {
							document.getElementById("sltMonth").options.add(new Option("" + i + "", i));
						}
					}
				
			}
		}
		//月选取触发日
		function CheckMonth(obj) {
			var iYear = document.getElementById("sltYear").value;
			var iMonth = document.getElementById("sltMonth").value;
			var objDay = document.getElementById("sltDay");
			var option_f = objDay.options[0];
			objDay.options.length = 0;
			objDay.options.add(option_f);
			if (iMonth == 1 || iMonth == 3 || iMonth == 5 || iMonth == 7 || iMonth == 8 || iMonth == 10 || iMonth == 12) {
				for (var j = 1; j <= 31; j++) {
					if (j != option_f.value) {
						append(objDay, j);
					}
				}
			}
			if (iMonth == 4 || iMonth == 6 || iMonth == 9 || iMonth == 11) {
				for (var j = 1; j <= 30; j++) {
					if (j != option_f.value) {
						append(objDay, j);
					}
				}
			}
			if (iMonth == 2) {
				if (iYear % 4 == 0 && (iYear % 100 != 0 || iYear % 400 == 0)) {//闰年,平年得给与
					for (var j = 1; j <= 29; j++) {
						if (j != option_f.value) {
							append(objDay, j);
						}
					}
				}
				else {
					for (var j = 1; j <= 28; j++) {
						if (j != option_f.value) {
							append(objDay, j);
						}
					}
				}
			}
		}
		function CheckDay(obj) {
		}
		//--------------时间联动结束------------------------
		//取消，返回页面，当typepage=1时返回到客户管理主页面，当typepage=2时返回总代管理页面		
		$('#J-Clean').click(function(){		
			if (arg != undefined) {
				var aid = arg["typepage"];
				if (aid != undefined) {				
					if(aid=="2"){	window.location.href='/admin/proxy/index';	}
					else if(aid=="3"){	window.location.href='/admin/user/accomplaints';}
					else{	window.location.href='/admin/user/list';}
				}
			}
				
		});
		//操作后提示	 
		function fn(obj){
			var Idivdok = document.getElementById(obj);	
			Idivdok.style.display="block";						
			Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
			Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
		} 	
		function codefans(obj){
			$('#'+obj).css("display","none");
		}
		//获取url中"?"符后的字串
		function GetRequest() {
			var url = location.search;   
			var json = {};
			if (url.indexOf("?") != -1) {
				var str = url.substr(1);
				strs = str.split("&");
				for (var i = 0; i < strs.length; i++) {
					json[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
				}
			}
			return json;
		}
		
		//手机校验
		mobile = $("#J-mobile");
		mobile.blur(function(){
			var v = $.trim(this.value);
			///^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/.test(v) 手机验证
			if(/^[0-9]*$/.test(v)|| $.trim(mobile.attr('data-old-value')) == v || v==''){     
				if($.trim(mobile.attr('data-old-value')) != v || v==''){
					$('#J-dmobile').val(v);
				} else {
					$('#J-dmobile').val($('#J-dmobile').attr('data-old-value'));
				}
			}
		});
		
		//VIP客服
		vipcellphone = $("#J-vvipcellphone");
		vipcellphone.blur(function(){
			var v = $.trim(this.value);
			if(/^[0-9]*$/.test(v)){
				if($.trim(vipcellphone.attr('data-old-value')) != v || v==''){
					$('#J-vipcellphone').val(v);
				} else {
					$('#J-vipcellphone').val($('#J-vipcellphone').attr('data-old-value'));
				}
			}
		});
		
		//qq
		vqq = $("input[name^='vqq']");
		vqq.blur(function(){
			
			var v = $.trim(this.value);
			var next = $(this).next();
			if(/^[0-9]*$/.test(v)){
				if($.trim(vqq.attr('data-old-value')) != v || v==''){
					next.val(v);
				} else {
					next.val(next.attr('data-old-value'));
				}
			}
		});
		
		
		//表单提交校验
		$('#J-Submit').click(function(){
			if((years.val() == '请选择' && months.val() == '请选择' && days.val() == '请选择') || (years.val() != '请选择' && months.val() != '请选择' && days.val() != '请选择')){
				$('#errData').css("display","none");
				try {	
					var id=$('input[name="id"]').val();
					var sex=$('input[name="sex"]:checked').val();
					var sltYear=years.val();
					var sltMonth=months.val();
					var sltDay=days.val();
					var cipher=$('input[name="cipher"]').val();
					var cellphone=$('input[name="cellphone"]').val();
					var vipCellphone=$('input[name="vipCellphone"]').val();
					var typepage=$('input[name="typepage"]').val();
					var qqStr = '';
					$('input[name^="qq"]').each(function(){
						name = $(this).attr('name');
						qqStr += '&'+name+'='+$(this).val();
					});
					
					$.ajax({
						type:'post',
						dataType:'json',
						cache:false,
						url:'/admin/user/saveuserdetail/',
						data:'id='+id+'&sex='+sex+'&sltYear='+sltYear+'&sltMonth='+sltMonth+'&sltDay='+sltDay+'&cipher='+cipher+'&cellphone='+cellphone+'&vipCellphone='+vipCellphone+'&typepage='+typepage+qqStr,	
						beforeSend:function(){
							
							isLoading = true;
							//禁用发送	
							$("#J-Submit").css("color", "#CACACA");		
							$("#J-Submit").attr("disabled","disabled");						
						},						
						success:function(data){
							if(data['isSuccess']=="1")
							{						
								 fn("DdivOK");
								 setTimeout(function(){
								 codefans("DdivOK");},1500);                       
								 window.location.href=data['url'];
							}
							else
							{
								 fn("DivDeleteno");
								 setTimeout(function(){codefans("DivDeleteno");},1500);							
							}
						
						},
						error:function(xhr, type){						
							 fn("DivDeleteno");
							 setTimeout(function(){codefans("DivDeleteno");},1500);			
						},
						complete:function(){
							isLoading = false;		
							$('#J-Submit').removeAttr("disabled"); 				
							$("#J-Submit").css("color", "#FFFFFF");				
						}
					});
					} catch (err) {		
				
					return;		
				}
			}else{
				e.preventDefault(); 
				alert("请确保日期完整性");
				$('#errData').css("display","inline");					
				return false;				
			}
			return true;
		});	
		
	})(jQuery);
	</script>
{/literal}
