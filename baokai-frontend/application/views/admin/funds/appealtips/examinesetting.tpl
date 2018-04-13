	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; <span id="titleName">提现相关配置</span></div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							{include file='/admin/funds/draw/base/drawTip.tpl'}	
							<div align="right" class="ui-tab">
							<!-- {if "FUND_WITHDRAW_DRAWTIPS_UPDATE"|in_array:$smarty.session.datas.info.acls} -->
							<button class='btn btn-small' href='javascript:void(0);' id='edittips' style='position:initial' >编辑提示</button>
							<!-- {/if} -->
							<!-- {if "FUND_WITHDRAW_DRAWTIPS_ADD"|in_array:$smarty.session.datas.info.acls} -->							
							<button class='btn btn-small' href='javascript:void(0);' id='addtips' style='position:initial' >增加提示</button>
							<!-- {/if} -->							
							<!-- {if "FUND_WITHDRAW_DRAWTIPS_DELETE"|in_array:$smarty.session.datas.info.acls} -->
							<button class='btn btn-small' href='javascript:void(0);' id='deletetips' style='position:initial' >删除提示</button>
							<!-- {/if} -->							
							</div>	
							<ul class="ui-form ui-tab-content ui-tab-content-current"  id="DivRules">
                            	<li>
                                    <input type="hidden" id="tipIndex" name="tipIndex" value="{$tipIndex}"/>
                            		<form action="/admin/Fundconfig/index?parma=wcf1" method="post" id="subForm">
                                    <table class="table table-info table-border" >
                                        <tbody id="unCheckWithDraw">
                                        <tr style='display:none' id="uncheck">
                                                <td class="text-left w-1" tipsName="uncheckDrawPass">
                                                </td>
												<td class="text-left w-1" tipsName="uncheckDrawUnpass">
												</td>
												<td class="text-left w-1" tipsName="uncheckDrawRecheck">
												</td>
                                            </tr> 
											<tr>
											    <th class="text-center w-2" rowspan="{$res.uncheckMaxSize+1}">未处理</th>																							
                                                <th class="text-center w-4" >通过</th>                                                
												<th class="text-center w-4" >拒绝</th>
												<th class="text-center w-4" >待复审</th>
                                            </tr>
                                              {section name=uncheckMaxSize loop=$res.uncheckMaxSize}
	                                              {assign var="index" value=$smarty.section.uncheckMaxSize.index}
	                                              {assign var="uncheckDrawPass" value=$res.uncheckDrawPassTips}
	                                              {assign var="uncheckDrawUnpass" value=$res.uncheckDrawUnpassTips}
	                                              {assign var="uncheckDrawRecheck" value=$res.uncheckDrawRecheckTips}
											<tr>
                                                <td class="text-left w-1" tipsName="uncheckDrawPass{$index}">
                                                	{if $uncheckDrawPass[$index].tipsGroupb == '1'}
                                                		{$uncheckDrawPass[$index].tipsContext}
													{/if}
                                                </td>
												<td class="text-left w-1" tipsName="uncheckDrawUnpass{$index}">
                                                	{if $uncheckDrawUnpass[$index].tipsGroupb == '0'}
                                                		{$uncheckDrawUnpass[$index].tipsContext}
													{/if}
												</td>
												<td class="text-left w-1" tipsName="uncheckDrawRecheck{$index}">
				                                    {if $uncheckDrawRecheck[$index].tipsGroupb == '2'}
                                                		{$uncheckDrawRecheck[$index].tipsContext}
													{/if}
												</td>
                                            </tr>                                              
                                            {/section}
                                            </tbody>
                                            <tbody id="reviewWithDraw">
											<tr id="review" style="display: none;">
                                                <td class="text-left w-4" tipsName="reviewDrawPass">
                                                </td>
												<td class="text-left w-4" tipsName="reviewDrawUnpass">
												</td>
												<td class="text-left w-4" tipsName="reviewDrawRecheck">
												</td>
                                            </tr>                                             
                                            <tr>
											    <th class="text-center w-2" rowspan="{$res.revieMaxSize+1}">待复审</th>																							
                                                <th class="text-center w-4" >通过</th>                                                
												<th class="text-center w-4" >拒绝</th>
												<th class="text-center w-4" >待复审</th>
                                            </tr>
                                            {section name=revieMaxSize loop=$res.revieMaxSize}
	                                              {assign var="index1" value=$smarty.section.revieMaxSize.index}                        
	                                              {assign var="reviewDrawPass" value=$res.reviewDrawPassTips}
	                                              {assign var="reviewDrawUnpass" value=$res.reviewDrawUnpassTips}
	                                              {assign var="reviewDrawRecheck" value=$res.reviewDrawRecheckTips}
											<tr>
                                                <td class="text-left w-4" tipsName="reviewDrawPass{$index1}">
                                                	{if $reviewDrawPass[$index1].tipsGroupb == '1'}
                                                		{$reviewDrawPass[$index1].tipsContext}
													{/if}
                                                </td>
												<td class="text-left w-4" tipsName="reviewDrawUnpass{$index1}">
                                                	{if $reviewDrawUnpass[$index1].tipsGroupb == '0'}
                                                		{$reviewDrawUnpass[$index1].tipsContext}
													{/if}
												</td>
												<td class="text-left w-4" tipsName="reviewDrawRecheck{$index1}">
				                                    {if $reviewDrawRecheck[$index1].tipsGroupb == '2'}
                                                		{$reviewDrawRecheck[$index1].tipsContext}
													{/if}
												</td>
                                            </tr>  
                                                {/section}
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
	  <!--編輯提示-->
	  <div class="pop w-13"  id="EditTipsWindow" style="display:none;overflow-y:scroll;width:450px; height:700px">
               <div class="hd">
        <i class="close" name="closeIcoDiv1"></i>
        <h3 align="center">编辑提示</h3>
		</div>
		<div class="bd">
		<ul class="ui-form ui-form-small">
			<li>
				<div id="updateArea" style="overflow:auto;">
				
				</div>
			</li>
			
			<li>
				<input class="btn btn-important" id="updateConfirm" name="closeIcoDiv1" value="确认"  style="width:80px;"/>
				<input class="btn" name="closeIcoDiv1" value="取消"  style="width:80px;"/>
			</li>
		</ul>
      </div>
	  </div>

	  <!--增加提示-->
	  <div class="pop w-13" id="AddTipsWindow" style="display:none;">
               <div class="hd">
        <i class="close" name="closeIcoDiv2"></i>
        <h3 align="center">增加提示</h3>
		</div>
		<div class="bd">
		<ul class="ui-form ui-form-small">
			<li>
				<label class="ui-label w-auto">审核类型：</label>
				<select id="addSelectGropuA" class="ui-select w-2" name="groupA">
                     <option value="0" selected>请选择</option>
                     <option value="1">未处理</option>
					 <option value="2">待复审</option>
                </select>
				<br>				
				<label class="ui-label w-auto">操作类型：</label>
				<select id="addSelectGropuB" class="ui-select w-2" name="groupB">
                     <option value="0" selected>请选择</option>
                     <option value="1">通过</option>
					 <option value="2">拒绝</option>
					  <option value="3">待复审</option>
                </select>
				<br>
				<label class="ui-label w-auto">提示內容：</label><br>				
				<input name="addTips1" style=" text-align:left; margin-left:80px" type="text" size="70">提示1<br><br>
				<input name="addTips2" style=" text-align:left; margin-left:80px" type="text" size="70">提示2<br><br>					
				<input name="addTips3" style=" text-align:left; margin-left:80px" type="text" size="70">提示3<br><br>
			</li>
			
			<li>
				<input id="addConfirm" class="btn btn-important"  value="确认"  style="width:80px;"/>
				<input class="btn" name="closeIcoDiv2" value="撤销编辑"  style="width:80px;"/>
			</li>
		</ul>
      </div>
	  </div>	
	  
	  <!--删除提示-->
	  <div class="pop w-8" id="DeleteTipsWindow" style="display:none;overflow-y:scroll;width:450px; height:700px">
               <div class="hd">
        <i class="close" name="closeIcoDiv3"></i>
        <h3 align="center">删除提示</h3>
		</div>
		<div class="bd">
		<ul class="ui-form ui-form-small">
			<li>
				<br>
			</li>
			<div style="word-break:break-all" id="deleteDivAreea"></div>
			<li>
				<input class="btn btn-important" id="deleteConfirm" name="closeIcoDiv3" value="确认删除"  style="width:80px;"/>
				<input class="btn" name="closeIcoDiv3" value="撤销"  style="width:80px;"/>
			</li>
		</ul>
      </div>
	  </div>
	</div>
	</div>
{include file='/admin/script-base.tpl'}
{literal}
<script>

var selectMap = new Object();
var tipsModel = 1;
selectMap['11'] = 'uncheckDrawPass';	
selectMap['12'] = 'uncheckDrawUnpass';		   
selectMap['13'] = 'uncheckDrawRecheck';		   	   
selectMap['21'] = 'reviewDrawPass';		   
selectMap['22'] = 'reviewDrawUnpass';
selectMap['23'] = 'reviewDrawRecheck';		

var tipsValue = new Object();
tipsValue["uncheckDrawPass"]= {tipsGroupa:0,tipsGroupb:1};
tipsValue["uncheckDrawUnpass"]=  {tipsGroupa:0,tipsGroupb:0};
tipsValue["uncheckDrawRecheck"]= {tipsGroupa:0,tipsGroupb:2};
tipsValue["reviewDrawPass"]=     {tipsGroupa:1,tipsGroupb:1};
tipsValue["reviewDrawUnpass"]=  {tipsGroupa:1,tipsGroupb:0};
tipsValue["reviewDrawRecheck"]= {tipsGroupa:1,tipsGroupb:2};

function composeUpdateText(obj){
	if($(obj).text().trim().length!=0){
		return "<input style='text-align:left; margin-left:80px' type='text' size='70' name='"+$(obj).attr('tipsName')+"' value='"+$(obj).text().trim()+"' /><br>";
	}else{
		return "";
	}
}

function composeUpdateCheckBox(obj){
	if($(obj).text().trim().length!=0){
		return "<input type='checkbox' name='deleteTips' value='"+$(obj).attr('tipsName')+"'/>"+$(obj).text()+"<br><br>";
	}else{
		return "";
	}
}

function composeGroupText(group,input){
	if(input.trim().length!=0){
		return group+"<br>"+input;;
	}else{
		return "";
	}
}

function showDeleteTips(){
	var inputText = "";
	var updateText ="";
	//1
	$("#unCheckWithDraw td[tipsName^=uncheckDrawPass]").each(function(){
		updateText = updateText+composeUpdateCheckBox(this);	
	});
	inputText = inputText+composeGroupText("未处理-通过",updateText);

	//2
	updateText ="";
	$("#unCheckWithDraw td[tipsName^=uncheckDrawUnpass]").each(function(){
		updateText = updateText+composeUpdateCheckBox(this);	
	});
	inputText = inputText+composeGroupText("未处理-拒绝",updateText);	
	//3
	updateText ="";
	$("#unCheckWithDraw td[tipsName^=uncheckDrawRecheck]").each(function(){
		updateText = updateText+composeUpdateCheckBox(this);
	});
	inputText = inputText+composeGroupText("未处理-待复审",updateText);
	//4
	updateText ="";
	$("#reviewWithDraw td[tipsName^=reviewDrawPass]").each(function(){
		updateText = updateText+composeUpdateCheckBox(this);	
	});
	inputText = inputText+composeGroupText("待复审-通过",updateText);

	//5
	updateText ="";
	$("#reviewWithDraw td[tipsName^=reviewDrawUnpass]").each(function(){
		updateText = updateText+composeUpdateCheckBox(this);	
	});
	inputText = inputText+composeGroupText("待复审-拒绝",updateText);
	//6
	updateText ="";
	$("#reviewWithDraw td[tipsName^=reviewDrawRecheck]").each(function(){
		updateText = updateText+composeUpdateCheckBox(this);	
	});	
	inputText = inputText+composeGroupText("待复审-待复审",updateText);
	$("#deleteDivAreea").html(inputText);
}


function showUpdateTips(){
	var inputText = "";
	var updateText ="";
	//1
	$("#unCheckWithDraw td[tipsName^=uncheckDrawPass]").each(function(){
		updateText = updateText+composeUpdateText(this);	
	});
	inputText = inputText+composeGroupText("未处理-通过",updateText);

	//2
	updateText ="";
	$("#unCheckWithDraw td[tipsName^=uncheckDrawUnpass]").each(function(){
		updateText = updateText+composeUpdateText(this);	
	});
	inputText = inputText+composeGroupText("未处理-拒绝",updateText);	
	//3
	updateText ="";
	$("#unCheckWithDraw td[tipsName^=uncheckDrawRecheck]").each(function(){
		updateText = updateText+composeUpdateText(this);
	});
	inputText = inputText+composeGroupText("未处理-待复审",updateText);
	//4
	updateText ="";
	$("#reviewWithDraw td[tipsName^=reviewDrawPass]").each(function(){
		updateText = updateText+composeUpdateText(this);	
	});
	inputText = inputText+composeGroupText("待复审-通过",updateText);

	//5
	updateText ="";
	$("#reviewWithDraw td[tipsName^=reviewDrawUnpass]").each(function(){
		updateText = updateText+composeUpdateText(this);	
	});
	inputText = inputText+composeGroupText("待复审-拒绝",updateText);
	//6
	updateText ="";
	$("#reviewWithDraw td[tipsName^=reviewDrawRecheck]").each(function(){
		updateText = updateText+composeUpdateText(this);	
	});	
	inputText = inputText+composeGroupText("待复审-待复审",updateText);
	$("#updateArea").html(inputText);
}


function deleteConfirm(){
	$("#deleteDivAreea input:checked").each(function(){
		var deleteTd =$("td[tipsName="+$(this).val()+"]");
		$(deleteTd).text("");	
		//console.log($(deleteTd).parent().find('td').size());
		isTdAllEmptyRemoveRow(deleteTd);
		//針對頁面欄位做重新整理及排序
	});

	$.each(selectMap,function(){
		refreshPage(this);
	})	
	btnsubmit();
}

function refreshPage(tipsArea){
	$("td[tipsName^="+tipsArea+"]:not(:first)").each(function(){
		if($(this).text().trim().length==0){
			$(this).text("".trim());
		}
	});
	var valueTd= $("td[tipsName^="+tipsArea+"]:not(:empty):not(:first)").clone();
	
	$("td[tipsName^="+tipsArea+"]:not(:first)").each(function(index){
		var td = valueTd.get(index);
		if(td != 'undefined'){
			$(this).text($(td).text());
		}
		$(this).attr("tipsName",tipsArea+index);
		isTdAllEmptyRemoveRow(this);
	});
}

function isTdAllEmptyRemoveRow(deleteTd){
	var isEmpty = true;
	$(deleteTd).parent().find('td').each(function(){
//		console.log($(deleteTd).text().trim());
		if($(this).text().trim().length!=0){
			isEmpty= false;
		}
	});
	
	if(isEmpty){
		$(deleteTd).parent().remove();
	}
}

function updateConfirm(){
	$("#updateArea :input").each(function(){
		$("td[tipsName="+$(this).attr("name")+"]").text($(this).val());		
	});
	btnsubmit();
}

function addTipsText(){
	var groupa= $('#addSelectGropuA').val();
	var groupb= $('#addSelectGropuB').val();
	var val= selectMap[groupa+groupb];
	if(groupa==0 || groupb==0){
		alert("请选择审核类型及操作类型");	
		return;
	}else{
		var nowsize=0;
		$("td[tipsName^="+val+"]").each(function(){
			if($(this).text().trim().length!=0){
				nowsize++;
			}
		});
		
		
		var addSize = $("input[name^=addTips]").filter(function(){
			return $(this).val().length!==0;
		}).size();
		
		if(addSize+nowsize>5){
			alert("提示类型限制最多同时配置5条提示");
			return;
		}
		
		
		$("input[name^=addTips]").each(function(){
			var input = this;
			if($(input).val().trim().length!=0){
				var maxsize= $("td[tipsName^="+val+"]:not(:first)").size();

				if(nowsize >= maxsize){
					var cloneTrId="unCheckWithDraw";
					if(groupa==2){
						cloneTrId = "reviewWithDraw";
					}
					var cloneTrHtml = "<tr>"+$("#"+cloneTrId+" tr:first-child").html()+"</tr>";
					var cloneTr = $("#"+cloneTrId).append(cloneTrHtml);
					
					$("#"+cloneTrId+" tr").last().find('td').each(function(){
						$(this).attr('tipsName',function(){return $(this).attr('tipsName')+nowsize;});
						if($(this).attr('tipsName')==val+nowsize){
							$(this).text($(input).val());
						}
					});
					$("#"+cloneTrId).before().find('th').first().attr('rowspan',nowsize+2);
					nowsize++;
				}else{
					$("td[tipsName^="+val+nowsize+"]").text($(this).val());
					nowsize++;
				}
			}
		});	
		$('#addSelectGropuA')[0].selectedIndex=0;
		$('#addSelectGropuB')[0].selectedIndex=0;
		$("input[name^=addTips]").val("");
		box2.Close();
		btnsubmit();
	}
}


function btnsubmit(){
	
	var tipsList = new Array();
	$.each(selectMap,function(){
		var tipsName = this;
		$("td[tipsName^="+tipsName+"]:not(:first)").each(function(index){

			if($(this).text().trim().length!=0){
				var tips = new Object();
				tips.tipsModel =   tipsModel;
				tips.tipsGroupa =  tipsValue[tipsName].tipsGroupa;				
				tips.tipsGroupb =  tipsValue[tipsName].tipsGroupb;		
				tips.tipsContext = $(this).text().trim();			
				tipsList[tipsList.length] = tips;
			}
		})
	})	
	
	$.ajax({
        url:'/admin/Fundconfig/index?parma=saveDrawTips',
        method: "post",
        data:{'data':tipsList},
        type:'json',
        beforeSend: function () {
            isLock = false;
            TableStyle("DivRules", 19, 1, "保存中");
        },
        success:function(){
           location.reload();
        },
        complete: function ()
        {
            isLock = true;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            TableStyle("DivRules", 17, 2, "数据异常");
        }
    });
}

(function() {	
	option = {zIndex:500},	
    box1 = new LightBox("EditTipsWindow",option),	
    box2 = new LightBox("AddTipsWindow",option),
	box3 = new LightBox("DeleteTipsWindow",option);
	
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuWithdrawalsConfig');
	var tipIndex = $("#tipIndex").val();
    $("#drawcfg_"+tipIndex).addClass("current");
	$('.ui-tab-title li').click(function(){
		var indexs = $(this).val();
        window.location.href="/admin/Fundconfig/index?parma=wcf"+(parseInt(indexs));
	});
	 
	$("#edittips").click(function(){
		  box1.OverLay.Color = "rgb(51, 51, 51)" ; 
		  box1.Over = true;   
		  box1.OverLay.Opacity = 50;  
		  box1.Fixed = true;	 
		  box1.Center = true;
		  showUpdateTips();
		  box1.Show();
	});
	
	$("#addtips").click(function(){
		  box2.OverLay.Color = "rgb(51, 51, 51)" ; 
		  box2.Over = true;   
		  box2.OverLay.Opacity = 50;  
		  box2.Fixed = true;	 
		  box2.Center = true;
		  box2.Show();
	});
	
	$("#deletetips").click(function(){
		  box3.OverLay.Color = "rgb(51, 51, 51)" ; 
		  box3.Over = true;   
		  box3.OverLay.Opacity = 50;  
		  box3.Fixed = true;	 
		  box3.Center = true;
		  showDeleteTips();
		  box3.Show();
	});
	
	$(document).on('click', '[name="closeIcoDiv1"]', function(e){
		box1.Close();
		$("#updateArea").html("");
			
	});
	
	$(document).on('click', '[name="closeIcoDiv2"]', function(e){
		$('#addSelectGropuA')[0].selectedIndex=0;
		$('#addSelectGropuB')[0].selectedIndex=0;
		$("input[name^=addTips]").val("");
		box2.Close();
			
	});
	
	$(document).on('click', '[name="closeIcoDiv3"]', function(e){
		$("#deleteDivAreea").html("");
		box3.Close();
			
	});

	$("#addConfirm").click(addTipsText);
	$("#updateConfirm").click(updateConfirm);	
	$("#deleteConfirm").click(deleteConfirm);	
	
})();	



</script>
{/literal}
</body>
</html>