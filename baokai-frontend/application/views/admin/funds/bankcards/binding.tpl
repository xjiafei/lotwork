	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; 用户银行卡绑定管理</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<ul class="ui-search">
							<li class="name">
								<span class="ui-text-info">请选择：</span>
								<select name="type" class="ui-select w-2">
									<option value="1" {if $_POST.type eq '1'}selected{/if}>用户名</option>
									<option value="2" {if $_POST.type eq '2'}selected{/if}>银行卡号</option>
								</select>
								<label class="ui-label">：</label>
								<input type="text" value="{if $_POST.type eq '2'}{$_POST.userName|default:'请输入您的银行卡号'}{else}{$_POST.userName|default:'请输入您的用户名'}{/if}" id="userName" class="input" />
							</li>
							<li><a class="btn btn-important" href="javascript:void(0);" id="J-Submit">搜 索<b class="btn-inner"></b></a></li>
						</ul>
						<div class="ui-tab-title tab-title-bg clearfix appeal-link-tab">
						<ul>
						<li id ="bankBindPage" class="current">银行卡绑定信息</li>
						<!-- <li id ="aliBindPage" >支付宝绑定信息</li> -->
						</ul>
               			 </div>
                       	<ul class="ui-form"  id="DivRules" >  
                        	<li>
                                <table id="J-table-data" class="table table-info" style="display:none">
                                    <thead>
                                        <tr>
                                            <th >用户名</th>
                                            <th >是否VIP</th>
                                            <th >绑定数 </th>
                                            <th >当前绑定</th>
                                            <th >状态</th>
                                            <th >操作</th>
                                            <th >启用状态</th>
                                            <!-- {if "FUND_BANKCARD_BINDMANAGE_CHANGESTATUS"|in_array:$smarty.session.datas.info.acls} -->
											<input type="hidden" id="BINDMANAGE_CHANGESTATUS" value="1"/>
											<!-- {else} -->
											<input type="hidden" id="BINDMANAGE_CHANGESTATUS" value="0"/>
											<!-- {/if} -->
											<!-- {if "FUND_BANKCARD_BINDMANAGE_HISTORY"|in_array:$smarty.session.datas.info.acls} -->
											<input type="hidden" id="BINDMANAGE_HISTORY" value="1"/>
											<!-- {else} -->
											<input type="hidden" id="BINDMANAGE_HISTORY" value="0"/>
											<!-- {/if} -->
                                        </tr>
                                    </thead>
                                    <tbody id="showInfo">
                                    </tbody>
                                    
                                    <tfoot>
                                        <tr>
                                            <td colspan="7">
                                             
                                            </td>
                                        </tr>
                                    </tfoot>
                            	</table>	
                            </li>
                        </ul>      
						<ul class="ui-form"  id="DivRules2" >  
                        	<li>
                                <table id="J-table-data2" class="table table-info table-function text-center " style="display:none">
                                    <thead>
                                        <tr>
                                            <th >用户名</th>
                                            <th >所属总代</th>
                                            <th >银行账户名</th>
                                            <th >银行 </th>
                                            <th >省份</th>
                                            <th >城市</th>
                                            <th >是否冻结</th>
                                            <th >冻结类型</th>
                                            <th >新增时间</th>
                                        </tr>
                                    </thead>
                                    <tbody id="showInfo2">
                                    </tbody>
                                    
                                    <tfoot>
                                        <tr>
                                            <td colspan="9">
                                             
                                            </td>
                                        </tr>
                                    </tfoot>
                            	</table>
                            	<h3 class="ui-title" style="display:none;">历史记录</h3>
                            	<table id="J-table-data3" class="table table-info table-function text-center " style="display:none">
                                    <thead>
                                        <tr>
                                            <th >用户名</th>
                                            <th >所属总代</th>
                                            <th >银行 </th>
                                            <th >省份</th>
                                            <th >城市</th>
                                            <th >是否冻结</th>
                                            <th >冻结类型</th>
                                            <th >操作类型</th>
                                            <th >操作时间</th>
                                        </tr>
                                    </thead>
                                    <tbody id="showInfo3">
                                    </tbody>
                                    
                                    <tfoot>
                                        <tr>
                                            <td colspan="9">
                                             
                                            </td>
                                        </tr>
                                    </tfoot>
                            	</table>	
                            </li>
                        </ul>	
					</div>
				</div>
			</div>
		</div>
	</div>
{include file='/admin/script-base.tpl'}
{literal}	
<script>
(function() {
	var isture=false;
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuBankcardsManage');
	$('#userName').focus(function(){	
		if($('[name="type"]').find("option:selected").val() == '1'){
			if($('#userName')[0].value=='请输入您的用户名'){ 
				$("#userName")[0].value='';
			}	
		} else {
			if($('#userName')[0].value=='请输入您的银行卡号'){ 
				$("#userName")[0].value='';
			}	
		}
						
		}).blur(function(){
		var v = $.trim(this.value);
		if($('[name="type"]').find("option:selected").val() == '1'){
			if(v == ''){
				$("#userName")[0].value='请输入您的用户名';			
			}else{
				isture=true;
			}	
		} else {
			if(v == ''){
				$("#userName")[0].value='请输入您的银行卡号';			
			}else{
				isture=true;
			}
		}
		
	
	}).blur(function(){
		var me=this;
		$(me).val(me.value.toLowerCase());
	});
	$('[name="type"]').change(function(){
		if($(this).find("option:selected").val() == '1'){
			$("#userName")[0].value='请输入您的用户名';
		} else {
			$("#userName")[0].value='请输入您的银行卡号';
		}
	});
	
	//条件加载数据
	function SelectByWhereInfo() {		

		var bindcardType = ($('#bankBindPage').attr('class') =='current')?"0":"1";
		

		var dataArea = $('#DivRules'),dataArea2 = $('#DivRules2'),mask = phoenix.Mask.getInstance(),userName=$.trim($('#userName').val()),type=$.trim($('[name="type"]').find("option:selected").val());		
		dataArea.show();
		dataArea2.hide();
		$("#J-table-data>tbody").html("");			
		$.ajax({
			url:'/admin/Bankcardsmanage/index?parma=sv2',
			dataType:'json',
			method:'post',			
			data:'userName='+userName+'&type='+type+'&bindcardType='+bindcardType,
			beforeSend:function(){
				$("#Pagination").hide();     
				$('#J-table-data').show();
				TableStyle("showInfo",7,1,"查询中");
			},
			success:function(data){
				//debugger
				if(data.count[0].recordNum>0){					
					//var resultAll = jQuery.parseJSON    
					var resultAll = eval(data);  
					var re = resultAll.text; 
					var html = "";		
					$.each(re, function (i) {							
						html += "<tr><td>"+ re[i].account ;
						if(re[i].vipLvl!=''){
							html += "<img src="+re[i].path_img+"/images/ucenter/safe/vip/vip"+re[i].vipLvl+".png>";
						}
						html += "</td>";						
						html += "<td>" + re[i].isVip + "</td>";							
						html += "<td>" + re[i].bindCount + "</td>";	
						html += "<td><ul>";
						var bankstrucs = re[i].userBanks;
						$.each(bankstrucs,function(k){
							html += "<li><sapn>" + bankstrucs[k].bankName + "</span >&nbsp;&nbsp;-&nbsp;&nbsp; <sapn>" ;

							
							if(bankstrucs[k].nickName!=''){
								html += bankstrucs[k].nickName + "</span> &nbsp;&nbsp;-&nbsp;&nbsp; <sapn>" ;
							}
								
							html += bankstrucs[k].bankAccount + "</span> &nbsp;&nbsp;-&nbsp;&nbsp; <sapn>" ;
							html += bankstrucs[k].bankNumber + "</span> &nbsp;&nbsp;-&nbsp;&nbsp; <sapn>" ;
							html += bankstrucs[k].bindDate + "</span></li>";						
						});
						html += '</ul></td>';
						
							if( re[i].status=="2")
							{
								html += "<td><span class='color-orange'  pro_name='" + re[i].account + "'  >锁定中</span></td>";
								html += "<td>" + re[i].operator + "</td>";							
								html += "<td>";
								if($("#BINDMANAGE_CHANGESTATUS").val()=='1'){
									html += "<a href='javascript:void(0);' name='StartOper' style='TEXT-DECORATION:underline' lockedId='" + re[i].lockedId + "'  pro_name='" + re[i].account + "'>启动</a>&nbsp;&nbsp;&nbsp;&nbsp;";
								}
								if($("#BINDMANAGE_HISTORY").val()=='1'){
									html += "<a href='javascript:void(0);' style='TEXT-DECORATION:underline'   pro_name='" + re[i].account + "' name='HistoryData'>历史记录</a>";
								}
								html += "</td></tr>";
							}
							else
							{
								html += "<td><span class='color-green'  pro_name='" + re[i].account + "'  pro_type='1'>启用中</span></td>";
								html += "<td>" + re[i].operator + "</td>";
								html += "<td>";
								if($("#BINDMANAGE_CHANGESTATUS").val()=='1'){
									html += "<a href='javascript:void(0);' name='lockOper' style='TEXT-DECORATION:underline' lockedId='" + re[i].lockedId + "'  pro_name='" + re[i].account + "'>锁定</a>&nbsp;&nbsp;&nbsp;&nbsp;";
								}
								if($("#BINDMANAGE_HISTORY").val()=='1'){
									html += "<a href='javascript:void(0);' style='TEXT-DECORATION:underline'  pro_name='" + re[i].account + "'  name='HistoryData'>历史记录</a>";
								}
								html += "</td></tr>";
							}
						
					});
					$("#J-table-data>tbody").html(html);	
					
				}else{
					 $("#Pagination").hide();     
					 $('.ui-tab-content').removeClass();             
					 TableStyle("J-table-data>tbody",7,2,"没有绑卡数据");
				}
			}
		});	
	}
	
	//条件加载数据
	function SelectByWhereInfo2() {
		var bindcardType = ($('#bankBindPage').attr('class') =='current')?"0":"1";
		var dataArea = $('#DivRules'),dataArea2 = $('#DivRules2'),mask = phoenix.Mask.getInstance(),userName=$.trim($('#userName').val()),type=$.trim($('[name="type"]').find("option:selected").val());		
		dataArea2.show();
		dataArea.hide();
		$("#J-table-data>tbody").html("");
		$.ajax({
			url:'/admin/Bankcardsmanage/index?parma=sv2',
			dataType:'json',
			method:'post',			
			data:'userName='+userName+'&type='+type+'&bindcardType='+bindcardType,
			beforeSend:function(){
				$("#Pagination").hide();
				$('#J-table-data2').show();
				TableStyle("showInfo2",9,1,"查询中");
			},
			success:function(data){
				//debugger
					
				if(data.text[0]){					
					//var resultAll = jQuery.parseJSON    
					var resultAll = eval(data);  
					var re = resultAll.text; 
					var html = "";		
					$.each(re, function (i) {							
						html += "<tr><td >" + re[i].account + "</td>";						
						html += "<td >" + re[i].topAcc + "</td>";		
						var bankstrucs = re[i].userBanks[0];
						html += "<td >" + bankstrucs.bankAccount + "</td>";	
						html += "<td >" + bankstrucs.bankName + "</td>";	
						html += "<td >" + bankstrucs.province + "</td>";	
						html += "<td >" + bankstrucs.city + "</td>";	
						html += "<td >" + re[i].isFreeze + "</td>";	
						html += "<td >" + re[i].freezeMethod + "</td>";
						html += "<td >" + bankstrucs.bindDate + "</td></tr>";	
						
					});
					$("#J-table-data2>tbody").html(html);	
					
				}else{
					 $("#Pagination").hide();     
					 $('.ui-tab-content').removeClass();             
					 TableStyle("J-table-data2>tbody",9,2,"没有绑卡数据");
				}
			}
		});	
	}
	
	//锁定lockOper
	$(document).on('click', '[name="lockOper"]', function(e){
		var lockedId = $(this).attr('lockedId'),dataArea = $('#showInfo'),mask = phoenix.Mask.getInstance();
		if($.trim(lockedId) ==''){
			return false;
		}
		$.ajax({
			url:'/admin/Bankcardsmanage/index?parma=sv9',				
			dataType:'json',
			method:'post',
			data:'lockedId='+lockedId+'&type=2',
			beforeSend:function(){
				mask.dom.addClass('admin-mask-loading');
				mask.css({opacity:.9,backgroundColor:'#FFF'});
				mask.show(dataArea);
				mask.dom.css({top:mask.dom.offset().top + 2,height:mask.dom.height() - 2});
			},
			success:function(data){
				if(data.status=="ok"){					
					SelectByWhereInfo();
				}
				else{
					alert("操作失败");	
				}					
			},
			complete:function(){
				mask.dom.fadeOut(300, function(){
				mask.hide();
			});
			}
		});	
	});
	
	//启动StartOper
	$(document).on('click', '[name="StartOper"]', function(e){

		var bindcardType = ($('#bankBindPage').attr('class') =='current')?"0":"1";
		var lockedId = $(this).attr('lockedId'),dataArea = $('#showInfo'),mask = phoenix.Mask.getInstance();

		if($.trim(lockedId) ==''){
			return false;
		}
		$.ajax({
			url:'/admin/Bankcardsmanage/index?parma=sv9',				
			dataType:'json',
			method:'post',
			data:'lockedId='+lockedId+'&type=1&bindcardType='+bindcardType,
			beforeSend:function(){
				mask.dom.addClass('admin-mask-loading');
				mask.css({opacity:.9,backgroundColor:'#FFF'});
				mask.show(dataArea);
				mask.dom.css({top:mask.dom.offset().top + 2,height:mask.dom.height() - 2});
			},
			success:function(data){
				if(data.status=="ok"){					
					SelectByWhereInfo();
				}
				else{
					alert("操作失败");	
				}					
			},
			complete:function(){
				mask.dom.fadeOut(300, function(){
				mask.hide();
			});
			}
		});	
	});	
	//历史十条记录
	$(document).on('click', '[name="HistoryData"]', function(e){
		var bindcardType = ($('#bankBindPage').attr('class') =='current')?"0":"1";
		if(!$(".bg-none").is("tr"))
		{
			var currentHide="",CurrentObj="",strHtml='';
			pro_name = $(this).attr('pro_name');
			CurrentObj = jQuery(this);
			if (currentHide != "") {
				currentHide.remove();	
				currentHide = "";				
				return false;			
			}	
			if(pro_name!=''){		
				var dataArea = $('#showInfo'),mask = phoenix.Mask.getInstance(),type=$.trim($('[name="type"]').find("option:selected").val());
				//获取银行历史(10条)
				$.ajax({
					//url:'/admin/Opterators/index?parma=qbch',
					url:'/admin/Bankcardsmanage/index?parma=sv10',
					dataType:'json',
					method:'post',
					data:'username='+pro_name+'&type='+type+'&bindcardType='+bindcardType,
					beforeSend:function(){
						mask.dom.addClass('admin-mask-loading');
						mask.css({opacity:.9,backgroundColor:'#FFF'});
						mask.show(dataArea);
						mask.dom.css({top:mask.dom.offset().top + 2,height:mask.dom.height() - 2});
					},
					success:function(data){
						if(data.text[0]){
							//解析数据加载展示
							var re = eval(data.text); 
							var html = "";
							strHtml="<tr class='bg-none' ><td colspan='7' style='text-align:left;'><ul class='ui-form'><li> <span   style='top:-45px;font-size:16px; font-weight:700;'>历史记录</span>（显示最近十条）</li>";	
							$.each(re, function (i) {						
								if(re[i].action==1){
									strHtml += "<li><label style='font-size:13px;color:green '>新 增</label> &nbsp; &nbsp;<label style='font-size:13px; '>" + re[i].bankName +"&nbsp;-&nbsp;";
									if(re[i].nickName!='')
									strHtml +=  re[i].nickName +"&nbsp;-&nbsp;"  ;
									strHtml +=  re[i].bankAccount+ "&nbsp;-&nbsp;" +  re[i].bankNumber+ "&nbsp;新增时间:&nbsp;-&nbsp;" + re[i].actionTime+ "</label> </li>";						
								}
								else if(re[i].action==2){
									strHtml += "<li><label style='font-size:13px;color:red '>删 除</label> &nbsp; &nbsp;<label style='font-size:13px; '>" + re[i].bankName +"&nbsp;-&nbsp;";
									if(re[i].nickName!='')
									strHtml += re[i].nickName +"&nbsp;-&nbsp;";
									strHtml +=  re[i].bankAccount+ "&nbsp;-&nbsp;" +  re[i].bankNumber+ "&nbsp;删除时间:&nbsp;-&nbsp;" + re[i].actionTime+ "</label> </li>";											
								}else{
									strHtml += "<li><label style='font-size:13px;color:orange '>修 改</label> &nbsp; &nbsp;<label style='font-size:13px; '>" +re[i].bankName +"&nbsp;-&nbsp;";
									if(re[i].nickName!='')
									strHtml += re[i].nickName +"&nbsp;-&nbsp;";
									strHtml += re[i].bankAccount+ "&nbsp;-&nbsp;" +  re[i].bankNumber+ "&nbsp;修改时间:&nbsp;-&nbsp;" + re[i].actionTime+ "</label> </li>";											
								}					
							});
							strHtml += "</ul></td></tr>";
							$('.bg-none').remove();
							CurrentObj.parent().parent().after(jQuery(strHtml));
							//CurrentObj.attr("disabled", "disabled");	
							currentHide = CurrentObj.parents("tr").next();				
						}
						else{
							alert("历史记录查询,请核实您是否有此权限");	
						}					
					},
					complete:function(){
						mask.dom.fadeOut(300, function(){
						mask.hide();
					});
					}
				});	
			}
			else{
				return false;	 
			}	
		}else
		{
			if($(".bg-none").is(":hidden"))
			{
				$(".bg-none").show();
			}else{
			    $(".bg-none").hide();
			}
		}
	});
	
	function displayHistory(){
		var bindcardType = ($('#bankBindPage').attr('class') =='current')?"0":"1";
		var dataArea = $('#DivRules2'),mask = phoenix.Mask.getInstance(),userName=$.trim($('#userName').val()),type=$.trim($('[name="type"]').find("option:selected").val());		
		$("#J-table-data3>tbody").html("");	
		if(userName!=''){
			
			$.ajax({
				//url:'/admin/Opterators/index?parma=qbch',
				url:'/admin/Bankcardsmanage/index?parma=sv10',
				dataType:'json',
				method:'post',
				data:'username='+userName+'&type='+type+'&bindcardType='+bindcardType,
				beforeSend:function(){
					$("#Pagination").hide();  
					$(".ui-title").show();
					$('#J-table-data3').show();	
					TableStyle("showInfo3",9,1,"查询中");
				},
				success:function(data){
					if(data.text[0]){
						//解析数据加载展示
						var resultAll = eval(data);  
						var re = resultAll.text; 
						var html = "";		
						$.each(re, function (i) {							
							html += "<tr><td >" + re[i].account + "</td>";						
							html += "<td >" + re[i].topAcc + "</td>";		
							html += "<td >" + re[i].bankName + "</td>";	
							html += "<td >" + re[i].province + "</td>";	
							html += "<td >" + re[i].city + "</td>";	
							html += "<td >" + re[i].isFreeze + "</td>";	
							html += "<td >" + re[i].freezeMethod + "</td>";
							if(re[i].action == '1'){
								html += "<td style='text-align: center;color:green;' >新增</td>";
							} else if(re[i].action == '2'){
								html += "<td style='text-align: center;color:red;'>删除</td>";
							} else {
								html += "<td style='text-align: center;color:orange;'>修改</td>";	
							}
							html += "<td >" + re[i].actionTime + "</td></tr>";
							
						});
						$("#J-table-data3>tbody").html(html);				
					}
					else{
						$("#Pagination").hide();     
						 TableStyle("J-table-data3>tbody",9,2,"没有绑卡历史记录数据");
					}					
				}
			});	
		}
		else{
			 $("#Pagination").hide();     
			 TableStyle("J-table-data3>tbody",9,2,"没有绑卡历史记录数据");	 
		}	
	}
	
	
	//表单提交校验
	$('#J-Submit').click(function(){
		if(isture==false){
			return false;
		}
		var type=$.trim($('[name="type"]').find("option:selected").val());
		if(type == '1'){
			SelectByWhereInfo();
		} else {
			SelectByWhereInfo2();
			displayHistory();
		}
	});	
	
	var type=$.trim($('[name="type"]').find("option:selected").val());
	var userName=$.trim($("#userName").val());
	if(type =='2' && userName!='' && userName!='请输入您的银行卡号'){
		SelectByWhereInfo2();
		displayHistory();
		isture=true;
	}
	
	
	$("#userName").keypress(function (e) {
		var currKey = 0, e = e || event;
		currKey = e.keyCode || e.which || e.charCode;
		if (currKey == 13) {				
			var type=$.trim($('[name="type"]').find("option:selected").val());
			if(type == '1'){
				SelectByWhereInfo();
			} else {
				SelectByWhereInfo2();
				displayHistory();
			}	
		}
	});	

	$('.ui-tab-title ul li').click(function(){
		var indexs = $(this).index();
		if(indexs==0){
			$('#bankBindPage').addClass("current");
			$('#aliBindPage').removeClass("current");
		}else{
			$('#bankBindPage').removeClass("current");
			$('#aliBindPage').addClass("current");
		}
		$("#J-table-data>tbody").html("");
	});
})();	
</script>
{/literal}
</body>
</html>
</body>
</html>