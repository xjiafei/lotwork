<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
		<!-- //////////////头部公共页面////////////// -->
			{include file='/admin/left.tpl'}
		<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/user/">用户中心</a> &gt;
			{if $typepage eq '1'} 
			<a href="/admin/user/list/"><span id="menu2">客户列表</span></a> &gt;
			{else if $typepage eq '2'}
			<a href="/admin/proxy/index/"><span id="menu2">总代管理</span></a> &gt;
			{else}
			<a href="/admin/user/freezeuserlist/"><span id="menu2">冻结用户管理</span></a> &gt;
			{/if} 
			{$account} </div></div>
			<div class="col-content">
				<div class="col-main">
					<form action="unfreezeuser" method="POST" id="J-form">
                       <input type="hidden" name="id" id="userId" value="{$userid}" />
                       <input type="hidden" name="typepage" value="{$typepage|default:1}" />
						<div class="main">
	
							<ul class="ui-form">
								<li>
									<label for="" class="ui-label">用户账号：</label>
									<span class="ui-text-info">{$account}<!-- {if $viplevel != '' && $viplevel != '0'}-->&nbsp;<img src="{$path_img}/images/ucenter/safe/vip/vip{$viplevel}.png"><!-- {/if} --></span>
								</li>
                                <li>
									<label for="" class="ui-label">冻结说明：</label>
									<span class="ui-text-info">
									<!-- {if $freezeMethod ==1} -->
										完全冻结（冻结后，用户将不能登录平台）
									<!-- {else if $freezeMethod ==2} -->
										可登录，不可投注，不可充提
									<!-- {else if $freezeMethod ==3} -->
										不可投注，可充提
									<!-- {else if $freezeMethod ==4} -->
										不可转账，不可提现
									<!-- {else} -->
										用户帐号申诉成功，系统冻结中
									<!-- {/if} -->
									</span>
							   </li>
                               <li>
                                       <label for="" class="ui-label">解冻范围：</label>
                                       
                                       <label class="label" for="rs1"><input type="radio" name="range"  class="radio" value=1  id="rs1" >仅解冻此用户，不解冻其下级</label>
                                      <div id="divRange" class="ui-check"><i class="error"></i>请选择解冻范围</div>
                               </li>
                               <li>
                         		 <!-- {if $userLvl neq -1} -->
	                                 <label for="" class="ui-label"></label>
	                                 <label class="label" for="rs2"><input type="radio"  name="range"  class="radio" value=0 id="rs2" >解冻此用户和所有下级</label>
                       			 <!-- {/if} -->
                               </li>
								<li>
									<label for="" class="ui-label">解冻原因：</label>
									<div class="textarea w-5">
											<textarea name="memo" id="ttaBeause"></textarea>
									</div>
									
									<div class="ui-check" id="divBeause"><i class="error"></i>请输入解冻原因</div>
								</li>
								<!-- {if $freezeMethod ==1 or $freezeMethod ==2 or $freezeMethod ==3 or $freezeMethod ==4} -->
								<li class="ui-btn">
									<a href="javascript:void(0);" class="btn btn-important" id="J-Submit">解冻<b class="btn-inner"></b></a>
									<a href="javascript:void(0);" class="btn" id="J-Clean">取 消<b class="btn-inner"></b></a>
								</li>
								<!-- {/if} -->
							</ul>
	
						</div>
						<div style="position:absolute;z-index:2; display:none" class="pop pop-success w-5" id="DdivOK">
						<i class="ico-success"></i>
						<h4 class="pop-text">解冻成功</h4>
						</div>
						
						<div style="position:absolute;z-index:2; display:none" class="pop pop-error w-5" id="DivDeleteno" >
							<i class="ico-error"></i>
							<h4 class="pop-text">解冻失败，请重试</h4>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
<script  src="{$path_js}/js/jquery-1.9.1.min.js"></script>
<script src="{$path_js}/js/phoenix.Common.js" type="text/javascript" ></script>
{literal}
	<script > 
	(function($){	
		//一级菜单选中样式加载
		var type = $('input[name="typepage"]').val();
		type = type -1;
		$('.menu-list li').removeAttr("class");			
		$('.menu-list li:eq(2)').attr("class","current");
		$('.col-side .nav dd:eq('+type+')').attr("class","current");
        var isvalue='';
		//操作后提示	 
		function fn(obj){
			var Idivdok = document.getElementById(obj);	
			Idivdok.style.display="block";						
			Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft-150+"px";			
			Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
		} 	
		function codefans(obj){
			$('#'+obj).css("display","none");
		}	
		//取消，返回页面		
		$('#J-Clean').click(function(){		
			if(type =="1"){	
				window.location.href='/admin/proxy/index';	
			} else if(type =="2") {
				window.location.href='/admin/user/freezeuserlist/';
			} else {
				window.location.href='/admin/user/list';
			}			
		});
                
	   $("input:radio[name=range]").click(function() {
		   isvalue= $(this).val();      
		   $("#divRange").css("display","none");              
	   });
					
		//表单提交校验
		$('#J-Submit').click(function(e){
			if(isvalue=='')
			{
				e.preventDefault();
				$("#divRange").css("display","inline"); 
				return false;
			}		
			if($.trim($('#ttaBeause').val())=='')
			{				
				e.preventDefault(); 
				$("#divBeause").css("display","inline");
				return false;
			}
           	
			try {	
				$.ajax({
					type:'post',
					dataType:'json',				
					cache:false,
					url:'/admin/user/unfreezeuser',
					data:'userid='+$('#userId')[0].value+'&memo='+$("#ttaBeause").val().trim()+'&range='+isvalue,
					beforeSend:function(){
						isLoading = true;
					},						
					success:function(data){	
						if(data['isSuccess']=="1")
						{						
							 fn("DdivOK");
							 setTimeout(function(){codefans("DdivOK");},1500);	
							 if(type =="1"){	
								 window.location.href='/admin/proxy/index';	
							 } else if(type =="2") {
								 window.location.href='/admin/user/freezeuserlist/';
							 } else {
								 window.location.href='/admin/user/list';
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