
{include file='/admin/header.tpl'}

	<div class="col-content">

			{include file='/admin/left.tpl'}

		<form action="freezeuser" method="POST" id="J-form">
		<div class="col-main">
		<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong>
		<a href="/admin/user/">用户中心</a> &gt; 
		{if $typepage eq '1'} 
		<a href="/admin/user/list/"><span id="menu2">客户列表</span></a> &gt;
		{else}
		<a href="/admin/proxy/index/"><span id="menu2">总代管理</span></a> &gt;
		{/if}
		<sapn id="menu3">{$username} </sapn>
		</div>
		</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<ul class="ui-form">
							<li>
								<label for="" class="ui-label">用户账号：</label>
								<span class="ui-text-info">{$username}<!-- {if $viplevel != '' && $viplevel != '0'} -->&nbsp;<img src="{$path_img}/images/ucenter/safe/vip/vip{$viplevel}.png"/><!-- {/if} --></span>
								
								
								<input type="hidden" name="id" id="userId" value="{$userid}" />
								<input type="hidden" name="typepage" value="{$typepage|default:1}" />
							</li>

										
							<li>
								<label for="" class="ui-label">冻结范围：</label>
								<label class="label" for="rs1"><input type="radio" name="range"  class="radio" value="1" id="rs1" checked="checked" >仅冻结此用户，不冻结其下级</label>
							</li>
							<li>
							{if $userLvl neq -1}
								<label for="" class="ui-label"></label>
								<label class="label" for="rs2"><input type="radio"  name="range"  class="radio" value="0" id="rs2" >冻结此用户和所有下级</label>
							{/if}
							</li>
							<li>
								<label for="" class="ui-label">冻结方式：</label>
								<label class="label" for="rs3"><input type="radio"  name="method"  class="radio" value="1"  id="rs3"  checked="checked">完全冻结（冻结后，用户将不能登录平台）</label>
							</li>
							<li>
								<label for="" class="ui-label"></label>
								<label class="label" for="rs4"><input type="radio"  name="method"   class="radio" value="2"  id="rs4" >可登录，不可投注，不可充提</label>
							</li>
							<li>
								<label for="" class="ui-label"></label>
								<label class="label" for="rs5"><input type="radio"  name="method"   class="radio" value="3"  id="rs5" >不可投注，可充提</label>
							</li>
							<li>
								<label for="" class="ui-label"></label>
								<label class="label" for="rs6"><input type="radio"  name="method"   class="radio" value="4"  id="rs6" >不可转账，不可提现</label>
							</li>
							<li>
								<label for="" class="ui-label">冻结原因：</label>
								<div class="textarea w-5">
                                 <textarea id="ttaBeause" name="memo"></textarea>
								</div>
								<div class="ui-check"><i class="error"></i>请输入冻结原因</div>
								
							</li>
							<li class="ui-btn">
								<input  id="J-Submit" class="btn btn-important" type="button" tabindex="4" value="冻结"><b class="btn-inner"></b>
							
								<a href="javascript:void(0);" class="btn" id="J-Clean">取 消<b class="btn-inner"></b></a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</form>
		<div style="position:absolute;z-index:2; display:none" class="pop pop-success w-5" id="DdivOK">
			<i class="ico-success"></i>
			<h4 class="pop-text">冻结成功</h4>
		</div>
		
		<div style="position:absolute;z-index:2; display:none" class="pop pop-error w-5" id="DivDeleteno" >
			<i class="ico-error"></i>
			<h4 class="pop-text">冻结失败，请重试</h4>
		</div>
	</div>	
<script  src="{$path_js}/js/jquery-1.9.1.min.js"></script>
<script src="{$path_js}/js/phoenix.Common.js" type="text/javascript" ></script>
{literal}
<script > 
(function($){	
	//一级菜单选中样式加载
	var type = $('input[name="typepage"]').val();
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
	var arg = GetRequest();
	//导航加载
	if (arg != undefined) {
		var aid = arg["typepage"];
		if (aid != undefined){				
			if(aid=="2"){	$('#menu1').html("客户管理");	$('#menu2').html("总代管理");}  //加载从总代管理进入	
			else if(aid=="3"){	$('#menu1').html("冻结用户管理");	$('#menu2').html("解冻记录");}    //加载冻结用户管理进入	
			else{	$('#menu1').html("客户管理 ");	$('#menu2').html(" 客户列表 ");}  //加载从客户列表进入
		}
	}
		
	//取消，返回页面，当typepage=1时返回到客户管理主页面，当typepage=2时返回总代管理页面,type=3返回历史冻解页面
	$('#J-Clean').click(function(){		
		if (arg != undefined) {
			var aid = arg["typepage"];
			if (aid != undefined) {				
				if(aid=="2"){	window.location.href='/admin/proxy/index';}
				else if(aid=="3"){	window.location.href='/admin/user/freezeuserhistorylist';}
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
	
		
	//表单提交校验
	
	$('#J-Submit').click(function(){
	
		try {	
			var ranges=$('input[name="range"]:checked').val();
			var methods=$('input[name="method"]:checked').val();
			if(ranges==''){	alert("选择冻结范围");e.preventDefault(); return false;}
			if(methods==''){ alert("选择冻结方式");e.preventDefault(); return false;}
			if($.trim($('#ttaBeause').val())==''){	$('.ui-check').css("display","inline");	e.preventDefault(); return false;	}
			
			$.ajax({
				type:'post',
				dataType:'json',				
				cache:false,
				url:'/admin/user/freezeuser',
				data:'userid='+$('#userId')[0].value+'&range='+ranges+'&method='+methods+'&memo='+$('#ttaBeause').val(),	
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
						 var aid = arg["typepage"];
						 if (aid != undefined) {				
							if(aid=="2"){	window.location.href='/admin/proxy/index';}
							else if(aid=="3"){	window.location.href='/admin/user/freezeuserhistorylist';}
							else{	window.location.href='/admin/user/list';}
						 }
					}
					else
					{
						if(data['data']){
							$("#DivDeleteno h4").html(data['data']);
						} else {
							$("#DivDeleteno h4").html('冻结失败,请重试!');
						}
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
			
	
	});		

	
})(jQuery);
</script>
{/literal}
</body>
</html>


