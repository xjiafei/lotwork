	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt;<a href="/admin/Fundconfig/index?parma=sv4">可提现用户白名单</a> &gt; {$title}</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title">{$title}</h3>												
                                <form id="fm_main">
                                    <ul class="ui-form">
                                        <li>
                                            <label class="ui-label">用户名：</label>
                                             <div class="textarea w-6">
                                                <textarea  id="txtRemark1" name="userName"  maxlength="100" ></textarea>                                        
                                            </div>
                                        </li>
                                        <li>
                                             <label class="ui-label">备注：</label>
                                            <div class="textarea w-6">
                                                <textarea  id="txtRemark1" name="applyMemo"   maxlength="100" ></textarea>                                        
                                            </div>
                                            <span class="ui-info vertical-bottom"><span name='spancount'>0</span>/100<div class="ui-check"><i class="error"></i>备注不能为空</div></span>                                  
                                        </li>
                                        <li class="ui-btn">
                                        <!-- {if "FUND_WITHDRAW_WHITELIST_ADD"|in_array:$smarty.session.datas.info.acls} -->
                                      		<input type="button" value="添 加" class="btn btn-important" id="J-Submit-Button"></input>
                                        <!-- {/if} -->
                                            <a href="/admin/Bankcardsmanage/index?parma=sv43" class="btn" id="J-Close-Button"i>返 回<b class="btn-inner"></b></a>
                                        </li>
                                    </ul>
								</form>
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
{include file='/admin/script-base.tpl'}
{literal}
<script>
(function() {
		 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuBanks');	
	var form=$('#fm_main'),isture=false;
	//备注已输入字符长度动态
	$('#txtRemark1').keyup(function() {
		$('[name="spancount"]').html($('#txtRemark1')[0].value.length);
	});		
	var inputs =  $('#cardNumber'),Remark=$('#txtRemark1'),checkFn;				
	checkFn = function(){
		var me = this,v = me.value,index;
		me.value = v = v.replace(/[^\d]/g,'');		
		if(me.value=="")
		{
		    $(this).parent().find('.ui-check').css('display', 'inline');	
		}else if(!luhmCheck(me.value))
		{
			$(this).parent().find('.ui-check').css('display', 'inline');	
		}
	};
	inputs.keyup(checkFn);
	inputs.blur(checkFn);
	inputs.focus(function(){
		$('#cardNumber').parent().find('.ui-check').css('display', 'none');	
	});			
	checkFnRemark=function()
	{
		var me = this,v = me.value,index;
		if($.trim(v)=="")
		{
			$(this).parent().parent().find('.ui-check').css('display', 'inline');
		}
	}
	Remark.focus(function(){
		$(this).parent().parent().find('.ui-check').css('display', 'none');	
	});
	Remark.blur(checkFnRemark);
	
	function CheckFrom(obj){		
		$(obj).each(function () { 
			$(this).css("border","1px solid #CECECE");	
			if($(this).val() == ""){
				var obj=this;
				$(this).focus(); 
				$(this).css("border","1px solid red");					
				isture=false;					
				window.location.hash = "#"+this;
				return false;						
			}		
			else{ isture = true;}		
		});	
	}		
	$('#J-Close-Button').click(function(){
		 location.reload();
	});
	$('#J-Submit-Button').click(function(){
		if(checkForm())
		{
			var sdata="cardNumber="+$("#cardNumber").val()+"&applyMemo="+$("#txtRemark1").val();
			 $.ajax({
				url:'/admin/Bankcardsmanage/index?parma=opteradd',
				dataType:'json',
				method:'post',
				data:sdata,					
				success:function(data){
					if(data["status"]=="ok")
					{
						alert(data["data"]);
						window.location="/admin/Bankcardsmanage/index?parma=sv43";
					}else if(data["status"]=="error")
					{
						alert(data["data"]);
					}
				},
				error: function()
				{
					alert("新增黑名单操作失败");
				}					
			});	
		}
		//form.submit();
	});	
	//表单提交校验
	var checkForm=function(){
		var isType,cardNumber=$.trim($('#cardNumber').val()),cardRemark=$.trim($('#txtRemark1').val());		
		CheckFrom(":text");
		isType=luhmCheck(cardNumber);
		if(isType==false){		
			$('#cardNumber').parent().find('.ui-check').css('display', 'inline');	
			return false;
		}
		if(cardRemark=="")
		{
			$('#txtRemark1').parent().find('.ui-check').css('display', 'inline');	
			return false;
		}
		if(isture == false){
			return false;
		}		
		return true;
	};		
})();	
</script>
{/literal}
</body>
</html>