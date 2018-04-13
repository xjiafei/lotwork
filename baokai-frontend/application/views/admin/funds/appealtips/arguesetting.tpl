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
							<!-- {if "FUND_WITHDRAW_APPEALTIPS_UPDATE"|in_array:$smarty.session.datas.info.acls} -->
                                    <input type="button"  class='btn btn-small' id='edittips' style='position:initial' value="编辑提示" />
							<!-- {/if} -->
							<!-- {if "FUND_WITHDRAW_APPEALTIPS_ADD"|in_array:$smarty.session.datas.info.acls} -->
                                    <input type="button"  class='btn btn-small' id='addtips' style='position:initial' value="增加提示" />
							<!-- {/if} -->
							<!-- {if "FUND_WITHDRAW_APPEALTIPS_DELETE"|in_array:$smarty.session.datas.info.acls} -->
                                    <input type="button"  class='btn btn-small' id='deletetips' style='position:initial' value="删除提示" />
							<!-- {/if} -->
                                </div>	
                        <ul class="ui-form ui-tab-content ui-tab-content-current"  id="DivRules">
                            <li> 							
                                <table class="table table-info ">																		
                                      <tbody>
                                        <input type="hidden" id="tipIndex" name="tipIndex" value="{$tipIndex}"/>
                                        <tr>
                                            <td class="text-center w-4" >审核通过 :</td>
                                            <td class="text-left" id="pass">
                                            	{foreach from=$res.appealPassTips item=tips key=index}
													<div  tipsName="appealPass{$index}">{$tips.tipsContext}</div>
                                            	{/foreach}
                                            </td>
                                        </tr>

                                        <tr>
                                            <td class="text-center w-4">审核不通过 :</td>
                                            <td class="text-left" id="unPass">
                                                {foreach from=$res.appealUnPassTips item=untips key=index}
													<div tipsName="appealUnPass{$index}">{$untips.tipsContext}</div>
                                            	{/foreach}
                                            </td>
                                        </tr>
                                        
                                        <tr>
                                            <td class="text-center w-4">待定 :</td>
                                            <td class="text-left" id="wait">
                                                {foreach from=$res.appealWaitTips item=waittips key=index}
													<div tipsName="appealWait{$index}">{$waittips.tipsContext}</div>
                                            	{/foreach}
                                            </td>
                                        </tr>
                                   </tbody>
                                </table>

                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--編輯提示-->
	  <div class="pop w-13"  id="EditTipsWindow" style="display:none;overflow:auto;overflow-y:scroll;width:450px; height:500px">
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
                     <option value="1">审核通过</option>
					 <option value="2">审核不通</option>
					 <option value="3">待定</option>
                </select>
				<br>
				<label class="ui-label w-auto">提示內容：</label><br>				
				<input name="addTips1" style=" text-align:left; margin-left:80px" type="text" size="70">提示1<br><br>
				<input name="addTips2" style=" text-align:left; margin-left:80px" type="text" size="70">提示2<br><br>					
				<input name="addTips3" style=" text-align:left; margin-left:80px" type="text" size="70">提示3<br><br>
			</li>
			
			<li>
				<input id="addConfirm" class="btn btn-important"  value="保存"  style="width:80px;"/>
				<input class="btn" name="closeIcoDiv2" value="撤销编辑"  style="width:80px;"/>
			</li>
		</ul>
      </div>
	  </div>

    <!--删除提示-->
	  <div class="pop w-8" id="DeleteTipsWindow" style="display:none;overflow-y:scroll;width:450px; height:500px">
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
{include file='/admin/script-base.tpl'}
{literal}
    <script>
    var tipsMap =new Object();
    var tipsModel = 2;
    tipsMap[1]="appealPass";
    tipsMap[2]="appealUnPass";
    tipsMap[3]="appealWait";

    var tipsValue = new Object();
    tipsValue["appealPass"]= {tipsGroupa:0,tipsGroupb:1};
    tipsValue["appealUnPass"]=  {tipsGroupa:0,tipsGroupb:0};
    tipsValue["appealWait"]=  {tipsGroupa:0,tipsGroupb:3};
    
    //增加提示
    function addTipsText(){
   	var groupa= $('#addSelectGropuA').val();
   	if(groupa==0){
   		alert("请选择审核类型");	
   		return;
   	}else{
   		
		var nowsize = $("div[tipsName^="+tipsMap[groupa]+"]").size();
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
  				var cloneTrId="pass";
			if(groupa==2){
				cloneTrId = "unPass";
			}
			var divHtml = "<div  tipsName='"+tipsMap[groupa]+$("#"+cloneTrId+" div").size()+"'>"+$(this).val()
			+"</div>";
			$("#"+cloneTrId).append(divHtml);
  			}
   		});	
	   		$("input[name^=addTips]").val("");
	  		$('#addSelectGropuA')[0].selectedIndex=0;
	  		btnsubmit();
	  		box2.Close();
    	}
   	 }
    
    function composeUpdateText(obj){
    	if($(obj).text().trim().length!=0){
    		return "<input style='text-align:left; margin-left:80px' type='text' size='70' name='"+$(obj).attr('tipsName')+"' value='"+$(obj).text().trim()+"' /><br>";
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
    
    function composeUpdateCheckBox(obj){
    	if($(obj).text().trim().length!=0){
    		return "<input type='checkbox' name='deleteTips' value='"+$(obj).attr('tipsName')+"'/>"+$(obj).text()+"<br><br>";
    	}else{
    		return "";
    	}
    }
    
    function showUpdateTips(){
    	var inputText = "";
    	var updateText ="";
    	$("#pass div").each(function(){
    		updateText = updateText+composeUpdateText(this);	
    	});
    	inputText = inputText+composeGroupText("审核通过",updateText);
    	updateText ="";
    	$("#unPass div").each(function(){
    		updateText = updateText+composeUpdateText(this);	
    	});
    	inputText = inputText+composeGroupText("审核不通过",updateText);
    	
    	updateText ="";
    	$("#wait div").each(function(){
    		updateText = updateText+composeUpdateText(this);	
    	});
    	inputText = inputText+composeGroupText("待定",updateText);
    		
    	$("#updateArea").html(inputText);
    }
    
    function showDeleteTips(){
    	var inputText = "";
    	var updateText ="";
    	//1
    	$("#pass div").each(function(){
    		updateText = updateText+composeUpdateCheckBox(this);	
    	});
    	inputText = inputText+composeGroupText("审核通过",updateText);

    	//2
    	updateText ="";
    	$("#unPass div").each(function(){
    		updateText = updateText+composeUpdateCheckBox(this);	
    	});
    	inputText = inputText+composeGroupText("审核不通过",updateText);	
    	
    	updateText ="";
    	$("#wait div").each(function(){
    		updateText = updateText+composeUpdateCheckBox(this);	
    	});
    	inputText = inputText+composeGroupText("待定",updateText);
    	
    	
    	$("#deleteDivAreea").html(inputText);
    }
    
    function updateConfirm(){
    	$("#updateArea :input").each(function(){
    		$("div[tipsName="+$(this).attr("name")+"]").text($(this).val());		
    	});
    	btnsubmit();
    }
    
    function deleteConfirm(){
    	$("#deleteDivAreea input:checked").each(function(){
    		var deleteObj =$("div[tipsName="+$(this).val()+"]");
    		$(deleteObj).remove();
    	});
    	btnsubmit();
    }
    
    function btnsubmit(){
    	var tipsList = new Array();
    	$.each(tipsMap,function(){
    		var tipsName = this;
    		$("div[tipsName^="+tipsName+"]").each(function(index){

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
            url:'/admin/Fundconfig/index?parma=saveAppealTips',
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
    
    
      (function () {
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