<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="permission" uri="/tag-permission"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<html>

<head>
	<title>资金中心-充值相关配置</title>
</head>

<body>
	<div class="col-main">
		<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong>
				<a href="/fundAdmin/Rechargemange/">资金中心</a> &gt;
				<span id="titleName">充值相关配置</span>
			</div>
		</div>
		<div class="col-content">
			<div class="col-main">
				<div class="main">
					<div class="ui-tab">
						<ul class="ui-tab-title ui-tab-title2 clearfix">
							<span><li><a href="/fundAdmin/Rechargemange/index?parma=bypass">充值分流设置</a></li></span>
							<span><li style="display:none;"></li></span>
							<span><li><a href="/fundAdmin/Rechargemange/index?parma=sv2&index=0">充值上下限配置</a></li></span>
							<span><li style="display:none;"></li></span>
							<span><li class="current">充值返送手续费配置</li></span>
							<span><li style="display:none;"></li></span>
						</ul>

						<ul class="ui-form" id="DivRules2">
							<li>
								<form method="post" id="J-form2">
									<table class="table table-info table-border" name "tabs2">
										<tbody>
											<tr>
												<td class="text-left w-4">银行名称</td>
												
												<td class="text-left"><span class="ui-text-info">请选择：</span>
													<select id="J-select-type" class="ui-select w-3" name="itemCode">
														<c:forEach var="items" items="${bankList }" varStatus="i">
															<!--隱藏企業版的資料 -->
															<!--{if ($v1.display eq 'Y')}-->
															<option value="${items.code }">${items.name }</option>
															<!--{/if}-->
														</c:forEach>
													</select>
												</td>
											</tr>

											<tr>
												<td class="text-left w-4">返送条件</td>
												<td class="text-left"><span class="ui-text-info"> 金 额：≥ </span>
													<input type="text" value="" class="input w-1" name="rtnMin" id="rtnMin"> 
													<span class="ui-text-info">元&nbsp;&nbsp;返手续费</span></td>
											</tr>
											<tr>
												<td class="text-left w-4">返送金额</td>
												<td class="text-left">
													<input type="hidden" id="rtnStrucLength" name="rtnStrucLength" value=""/>
													<dl class="set-list">
														<dt class="radio-list">
																<label class="label">
																<input type="radio" id="radios1" name="rtnSet"  class="radio" value="2">公式计算金额</label>
																<label class="label">
																<input type="radio" id="radios2" name="rtnSet" class="radio" value="1">MOW实际抓取金额</label>
															</dt>
														<div id="Div_1">
															
															<!-- <dd>
																<input type="text"  value=""  class="input w-1" name="" readonly> 
																<span class="ui-info">&le;充值金额</span>
																 {if $cnt != $key} &lt; 
																<input type="text" value="{$data['big']}" class="input w-1" name="big[]">
																 {/if} 
																<span class="ui-info">时，返送手续费为：</span> <span class="radio-list"> 
																<label class="label"><input type="radio" name="radiodd{$key}"
																			{if $data["type"] eq "1"}checked="checked"
																			{/if}   class="radio" value="1">固值</label> 
																			<label class="label"><input type="radio" name="radiodd{$key}"
																			{if $data["type"] eq "2"}checked="checked"
																			{/if} class="radio" value="2">百分比</label>
																</span> 
																<input type="text" value="{$data['value']}" class="input w-1" name="setValue[]"> 
																<span class="ui-info" name="moneyType">%</span> 
																<span class="ui-text-info"></span> 
																{if $key=='0'}
																<a href="javascript:void(0);" class="btn btn-small" id="AddRule">添加区间<b class="btn-inner"></b></a> 
																<span class="ui-text-prompt">最多支持10个区间</span>
																 {else if $cnt != $key}
																<a href="javascript:void(0);" class="btn btn-link" name='J-Delete'>删除<b class="btn-inner"></b></a>
																 {/if}

															</dd> -->
														</div>

													</dl>
												</td>
											</tr>
											<!-- {if "FUND_RECHARGE_CONFIG_RETURN_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
											<tr>
												<td></td>
												<td class="text-left ui-btn">
													<a href="javascript:void(0);" class="btn btn-important" id="J-Submit-Button2">提 交<b class="btn-inner"></b></a>
													<input type="reset" class="btn  btn-important" value="返回">
												</td>
											</tr>
											<!-- {/if} -->
										</tbody>
									</table>
								</form>

							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>

	<script type="text/javascript">
	 
		$(function() {
			var html='';
			var rtnStruc;
			var length;
			$('#J-select-type').change(function() {
				var code = $(this).val();
				var rtnStruc;
				var i;
				 $.ajax({
			            url: '/fundAdmin/fundbankManage/queryData?code='+code,
			            method: "POST",
			            async: false ,
			            dataType: "json",
						success: function (data) {
							html='';
							$("#rtnMin").val(data.rtnMin/10000);
							
							if(data.rtnSet==2){
								$("#radios1").attr('checked', 'checked');
								$("#radios2").removeAttr("checked");
								length=data.rtnStruc.length;
								rtnStruc=data.rtnStruc;
								$("#rtnStrucLength").val(length);
								
								  $.each(rtnStruc,function(length){  
									  html+="<dd>"+"<input type='text'  value='"+rtnStruc[length].sm/10000+"'  class='input w-1' name='rtnStrucSM' readonly>";
									  html+="<span class='ui-info'>&le;充值金额</span>";
									  html+="<input type='text' value='"+rtnStruc[length].big/10000+"' class='input w-1' name='rtnStrucBig'>";
									  html+="<span class='ui-info'>时，返送手续费为：</span>";
									  html+="<span class='radio-list'>";
									  if(rtnStruc[length].type==1){
										  html+="<label class='label'> <input type='radio'  name='radioVal_"+length+"' checked='checked' onclick='radioChecked(this)' class='radio' value='1'>固值</label>";
										  html+="<label class='label'><input type='radio'   name='radioVal_"+length+"' onclick='radioChecked(this)' class='radio' value='2'>百分比</label>";
										  html+="</span> ";
										  html+="<input type='text' value='"+rtnStruc[length].value/10000 +"' class='input w-1' name='money' >";
										  html+=" <span class='ui-info' name='moneyType'>元</span>";
									  }else if(rtnStruc[length].type==2){
										  html+="<label class='label'><input type='radio'  name='radioVal_"+length+"' onclick='radioChecked(this)'  class='radio' value='1'>固值</label>" ;
										  html+="<label class='label'><input type='radio'  name='radioVal_"+length+"' onclick='radioChecked(this)' checked='checked' class='radio' value='2'>百分比</label>";
										  html+="</span> ";
										  html+="<input type='text' value='"+rtnStruc[length].value/10000 +"' class='input w-1' name='money' >";
										  html+=" <span class='ui-info' name='moneyType'>%</span>";
									  }
		                          }) ;
								  html+="<span class='ui-text-info'></span> ";
								  html+="<a href='javascript:void(0);' class='btn btn-small' onclick='addRule()'>添加区间<b class='btn-inner'></b></a>";
								  html+="<span class='ui-text-prompt'>最多支持10个区间</span>";
								  html+="</dd>";
								$("#Div_1").html(html);
							}else{
								$("#radios2").attr('checked', 'checked');
								$("#radios1").removeAttr("checked");
							}
						}
			        });
				 
				 $("#J-Submit-Button2").bind("click", function () {
					 var rtnStrucLength=$("#rtnStrucLength").val()
				        $.ajax({
				        	url: "/fundAdmin/fundbankManage/savePoundage?rtnStrucLength="+rtnStrucLength,
				            data: $('#J-form2').serialize(),
				            method: 'post',
				            success: function () {
				            alert("数据保存成功!")
				              window.location = "/fundAdmin/Rechargemange/index?parma=sv2&index=1";
				            }
				        });
				    });
			});
		
			$("#radios1").bind("click", function () {
				$("#Div_1").html(html);
		    });
			$("#radios2").bind("click", function () {
				$("#Div_1").html("");
		    });
			
			$("#addRule").bind("click", function () {
				
				/*  html+="<dd>"+"<input type='text'  value='"+0+"'  class='input w-1' name='' readonly>";
				 html+="<span class='ui-info'>&le;充值金额</span>";
				 html+="<input type='text' value='"+0+"' class='input w-1' name=''>";
				 html+="<span class='ui-info'>时，返送手续费为：</span>";
				 html+="<span class='radio-list'>";
				 html+="<label class='label'> <input type='radio'  name='radioVal' checked='checked' onclick='radioChecked(this)' class='radio' value='1'>固值</label>";
				 html+="<label class='label'><input type='radio'  name='radioVal' onclick='radioChecked(this)' class='radio' value='2'>百分比</label>";
				 html+="</span> ";
				 html+="<input type='text' value='"+0+"' class='input w-1' name='gag' >";
				 html+=" <span class='ui-info' name='moneyType'>元</span>";
				 html+="<span class='ui-text-info'></span> ";
				 html+="<a href='javascript:void(0);' class='btn btn-small' onclick='addRule()'>添加区间<b class='btn-inner'></b></a>";
				 html+="<span class='ui-text-prompt'>最多支持10个区间</span>";
				 html+="</dd>";
				 $("#Div_1").html(html); */
		    });
		
			

		})
		function radioChecked(radioObj) {
			var num=$(radioObj).val();
			if(num==1){
				$(radioObj).parent().parent().next().next().html("元");
			}else if(num==2){
				$(radioObj).parent().parent().next().next().html("%");
			}		
		}
		function addRule(){
			html+=""
		}
		
	</script>
</body>
</html>