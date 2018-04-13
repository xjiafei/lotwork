	<!-- //////////////头部公共页面////////////// -->
	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt;人工活动派奖</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						
						<div class="ui-tab">
							<ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
							<!-- {if "FUND_MANUAL_AWARDS_UNTREAT"|in_array:$smarty.session.datas.info.acls} -->
								<li class="current">未派奖</li>
                                <!--{else}-->
                                 <li style="display:none;"></li>
							<!-- {/if} -->
							<!-- {if "FUND_MANUAL_AWARDS_COMPLETE"|in_array:$smarty.session.datas.info.acls} -->
								<li >已派奖</li>
                                <!--{else}-->
                                <li style="display:none;"></li>
							<!-- {/if} -->
								<select class="ui-select w-2" id="per_page" style="display:none;">
                                    <option value="100" selected=""></option>	
                                </select>
								
                                 <a style="margin-left:5%">活动名称:</a>
                                 <select class="ui-select" id="act_Name" style="width:220px;">
									<!--{foreach from=$res key=key item=data}-->
                                    <option value="{$data.id}" {if $key==0}selected{/if}>{$data.activityName}</option>
									<!--{/foreach}-->	
                                </select>
								<a style="margin-left:5%">活动日期:</a>
                                 <select class="ui-select" id="act_Date" style="width:100px;">
									<!--{foreach from=$actdate key=key item=date}-->
                                    <option value="{$date}" {if $key==0}selected{/if}>{$date}</option>
									<!--{/foreach}-->
                                </select>
							</ul>
							
						<ul class="ui-form ui-tab-content ui-tab-content-current"  id="DivRules">
                        	<li>	
								<table class="table table-info text-center" id="J-table-data" style="table-layout:fixed;">
								<thead>
									<tr>
										<th><input id="J-select-all" name="" style="vertical-align: middle;" type="checkbox"> 全选</th>
										<th>用户名</th>
										<th>应发奖金</th>
										<th>实发奖金</th>
										<th>备注</th>
									</tr>
								</thead>
								<tbody id="showInfo">
								</tbody>
								<tfoot>
									<tr>
										<td colspan="1">
											<div style="float:right;margin-right:20%;" id="oprateBtnDiv" style="display:none;">
											<!-- {if "FUND_MANUAL_AWARDS_UNTREAT_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
												<a class='btn btn-small' href='javascript:void(0);' id="passBtn">提交并派奖<b class='btn-inner'></b></a>
											<!-- {/if} -->
											</div>
										</td>
                                   		<td colspan="4">
											
										
                                          <div id="Pagination" class="pagination" ></div>
                                      	</td>
                                    </tr>
								</tfoot>
							</table>
							</li>						
						</ul>
						
						<ul class="ui-form ui-tab-content"  id="DivRules1">
                        	<li>
								<table class="table table-info text-center" id="J-table-data1" style="table-layout:fixed;">
								<thead>
									<tr>
										<th><input id="J-select-all1" name="" style="vertical-align: middle;" type="checkbox"  disabled> 全选</th>
										<th>用户名</th>
										<th>应发奖金</th>
										<th>实发奖金</th>
										<th>备注</th>
									</tr>
								</thead>
								<tbody id="showInfo1">
								</tbody>
								<tfoot>
									<tr>
                                   		<td colspan="5">
                                          <div id="Pagination1" class="pagination" ></div>
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
<script type="text/javascript">
(function() {	
	var dataArea = $('#DivRules'),minWindow,mask,initFn,isture=false;
		minWindow = new phoenix.MiniWindow({cls:'ui-wd-funds-expired'});
		mask = phoenix.Mask.getInstance();
		minWindow.addEvent('beforeShow', function(){
			mask.show();
		});
		minWindow.addEvent('afterHide', function(){
			mask.hide();
		});
    var isShowCell=false,group=new phoenix.SuperSearchGroup(),msg = new phoenix.Message(),msg1 = new phoenix.Message();
    var sindex=0,sindex=phoenix.util.getParam("tabIndex");
	//var tabObj=new phoenix.Tab({triggers:'.ui-tab-title2 li',panels:'.ui-tab-content',eventType:'click',currPanelClass:'ui-tab-content-current',index:sindex});
	$('#Tabs').find('li').click(function(){
		var tabIndexs = $(this).index();
		$("[name='step']").val(tabIndexs);
		$(this).siblings('li').removeClass('current');
		$(this).addClass('current');
		if(tabIndexs ==0) {
			SelectByWhereInfoUntreat("0");
			$('#DivRules').addClass('ui-tab-content-current');
			$('#DivRules1').removeClass('ui-tab-content-current');
		}else {
			SelectByWhereInfoTreat("0");
			$('#DivRules1').addClass('ui-tab-content-current');
			$('#DivRules').removeClass('ui-tab-content-current');
		}
		
	});
	
	//一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuAwardsOpterators');	
	//获取select类型值
	var getSelectValue = function(){
		var me = this,li = me.dom.find('li.current'),v = '',result = {};
		if(li.size() > 0){
			v = li.eq(0).attr('data-select-id');
			result[me.name] = v;
		}else{
			//初始化给空
			result[me.name] = '';
		}
		return result;
	};
	//获取两个input值
    

	

	
	var changeActDate=null;
	//依活動名稱篩選
	$("#act_Name").change(function(){  //Select事件發生
		//依據活動名稱篩選出對應日期
		var index = document.getElementById("act_Name").selectedIndex;
		$.ajax({
				url:'/admin/Opterators/index',
				type:'POST',
				dataType:'text',
				method:'POST',
				data:'parma=aas&index='+index+'&status=1',
				success:function(data){
					$('#act_Date').find('option').remove();
					var html = "";
					
					var arr = JSON.parse(data);
					//数据填充
					$.each(arr, function (i,obj) {
						if(i==0){
							changeActDate=obj;
							html += "<option value='"+obj+"' selected'>"+obj+"</option>";
						}else{
							html += "<option value='"+obj+"'>"+obj+"</option>";
						}
					});
					$("#act_Date").html(html);
					
					
					
					
					//把資料帶到controller做篩選
					if($('#DivRules').hasClass( "ui-tab-content-current" )){
						SelectByWhereInfoUntreat("0"); //位於未派獎頁籤
					}else if($('#DivRules1').hasClass( "ui-tab-content-current" )){
						SelectByWhereInfoTreat("0");  //位於已派獎頁籤
					}
					
					
				}					
		});	
	});
	//依活動日期篩選
	$("#act_Date").change(function(){  //Select事件發生
		//把資料帶到controller做篩選
		if($('#DivRules').hasClass( "ui-tab-content-current" )){
			SelectByWhereInfoUntreat("0"); //位於未派獎頁籤
		}else if($('#DivRules1').hasClass( "ui-tab-content-current" )){
			SelectByWhereInfoTreat("0");  //位於已派獎頁籤
		}
	});
	
	
	
	
	
	
	SelectByWhereInfoUntreat("0");
	//撈取未派獎資料
	function SelectByWhereInfoUntreat(pages) {
			//刷新頁面就重置全選鈕
			$("#J-select-all").attr('checked', false); 
			//活動ID			
			var  act_Id = parseInt($('#act_Name option:selected').val());	
			//活動日期
			if(changeActDate!=null){   //如果是更改活動名稱，頁面顯示活動第一日資訊
				var  act_Date=changeActDate;
				changeActDate=null;  //重置
			}else{
				var  act_Date = $('#act_Date option:selected').val();
				changeActDate=null;  //重置
			}
			//当前页数据行数			
			var  per_page_number = parseInt($("#per_page").val());	
			//放入group对象中(当前页)		
			var page  = new phoenix.SuperSearch({
				name: 'page',    		
				group: group,			
				expands:{getValue:function(){
					return pages;	
				}}
			});		
			//放入group对象中(当前页行数)	
			var perPageNum  = new phoenix.SuperSearch({
				name: 'perPageNum', 
				group: group,			
				expands:{getValue:function(){
					return per_page_number;	
				}}
			});
			//放入group对象中(活動ID)	
			var activityId  = new phoenix.SuperSearch({
				name: 'activityId', 
				group: group,			
				expands:{getValue:function(){
					return act_Id;	
				}}
			});
			//放入group对象中(活動日期)	
			var activityDate  = new phoenix.SuperSearch({
				name: 'activityDate', 
				group: group,			
				expands:{getValue:function(){
					return act_Date;	
				}}
			});
			//放入group对象中(狀態)	
			var activityDate  = new phoenix.SuperSearch({
				name: 'status', 
				group: group,			
				expands:{getValue:function(){
					return 0;	
				}}
			});
			
			 var formData = group.getFormData(),mask = phoenix.Mask.getInstance();
			 $("#J-table-data>tbody").html("");				
			 $("#Pagination").hide();   
			 $("#oprateBtnDiv").hide();  
			 $.ajax({
				url:'/admin/Opterators/index?parma=aasloadmutldata',
				dataType:'json',
				method:'post',
				data:formData,
				beforeSend:function(){				
					TableStyle("showInfo",9,1,"查询中");
				},
				success:function(data){
					//debugger
					if(data.count[0].recordNum>0){
						$("#Pagination").show();
						var resultAll = eval(data);               
						var re = resultAll.text;
						var recordNum = 0;
						recordNum = resultAll.count[0].recordNum;	
						//分页回调				 
						$("#Pagination").pagination(recordNum, {
							num_edge_entries: 2,
							num_display_entries: 4,
							current_page: pages,
							items_per_page: per_page_number,
							callback: SelectByWhereInfoUntreat
						});
						var html = "";
						//数据填充
						$.each(re, function (i) {						
								html += "<tr><td><input value='" + re[i].id + "' type='checkbox' style='margin-right:4px;'>派奖</td>";    
								html += "<td>" + re[i].account + "<input type='hidden' value='"+re[i].userId+"'></td>";    
								html += "<td>" + re[i].changeAmt + "</td>"; 
								html += "<td><input type='text' class='approveAmt' style='width:50%;text-align:center;' value='" + re[i].approverAmt + "'></input></td>";
								html += "<td><input type='text' maxlength='16' style='width:85%;' value='"+re[i].approverMemo+"'></input></td>";
						});
						$("#J-table-data>tbody").html(html);
						$("#oprateBtnDiv").show();
						var $selectBox = $('#J-select-all'),
						$checkList = $('table input[type="checkbox"]');
						$selectBox.click(function(event) {
							if($(this).prop('checked')) {
								$checkList.prop('checked', true);
							}else{
								$checkList.prop('checked', false);
							}
						});
						//註冊驗證事件
						validate();
						
					}else{
						 $("#Pagination").hide();                
						 $("#oprateBtnDiv").hide();                
						 TableStyle("J-table-data>tbody",5,2,"没有相应数据");
					}				
				}, 
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					 TableStyle("J-table-data>tbody",5,2,"数据异常");  
				}
		});	
	}	

	//撈取已派獎資料
	function SelectByWhereInfoTreat(pages) {
			//活動ID			
			var  act_Id = parseInt($('#act_Name option:selected').val());	
			//活動日期			
			if(changeActDate!=null){   //如果是更改活動名稱，頁面顯示活動第一日資訊
				var  act_Date=changeActDate;
				changeActDate=null;  //重置
			}else{
				var  act_Date = $('#act_Date option:selected').val();
				changeActDate=null;  //重置
			}
			//当前页数据行数			
			var  per_page_number = parseInt($("#per_page").val());	
			//放入group对象中(当前页)		
			var page  = new phoenix.SuperSearch({
				name: 'page',    		
				group: group,			
				expands:{getValue:function(){
					return pages;	
				}}
			});		
			//放入group对象中(当前页行数)	
			var perPageNum  = new phoenix.SuperSearch({
				name: 'perPageNum', 
				group: group,			
				expands:{getValue:function(){
					return per_page_number;	
				}}
			});
			//放入group对象中(活動ID)	
			var activityId  = new phoenix.SuperSearch({
				name: 'activityId', 
				group: group,			
				expands:{getValue:function(){
					return act_Id;	
				}}
			});
			//放入group对象中(活動日期)	
			var activityDate  = new phoenix.SuperSearch({
				name: 'activityDate', 
				group: group,			
				expands:{getValue:function(){
					return act_Date;	
				}}
			});
			//放入group对象中(狀態)	
			var activityDate  = new phoenix.SuperSearch({
				name: 'status', 
				group: group,			
				expands:{getValue:function(){
					return 1;	
				}}
			});
			
			 var formData = group.getFormData(),mask = phoenix.Mask.getInstance();
			 $("#J-table-data1>tbody").html("");				
			 $("#Pagination1").hide();   
			 $("#oprateBtnDiv").hide();   
			 $.ajax({
				url:'/admin/Opterators/index?parma=aasloadmutldata',
				dataType:'json',
				method:'post',
				data:formData,
				beforeSend:function(){				
					TableStyle("showInfo",9,1,"查询中");
				},
				success:function(data){
					//debugger
					if(data.count[0].recordNum>0){
						$("#Pagination1").show();
						var resultAll = eval(data);               
						var re = resultAll.text;
						var recordNum = 0;
						recordNum = resultAll.count[0].recordNum;	
						//分页回调				 
						$("#Pagination1").pagination(recordNum, {
							num_edge_entries: 2,
							num_display_entries: 4,
							current_page: pages,
							items_per_page: per_page_number,
							callback: SelectByWhereInfoTreat
						});
						var html = "";
						//数据填充
						$.each(re, function (i) {						
								html += "<tr><td><input value='" + re[i].id + "' type='checkbox' style='margin-right:4px;'  disabled>派奖</td>";    
								html += "<td>" + re[i].account + "</td>";    
								html += "<td>" + re[i].changeAmt + "</td>"; 
								html += "<td>" + re[i].approverAmt + "</td>";
								html += "<td>" + re[i].approverMemo+"</td>";
						});
						$("#J-table-data1>tbody").html(html);
						$("#oprateBtnDiv").show();
						var $selectBox = $('#J-select-all1'),
						$checkList = $('table input[type="checkbox"]');
						$selectBox.click(function(event) {
							if($(this).prop('checked')) {
								$checkList.prop('checked', true);
							}else{
								$checkList.prop('checked', false);
							}
						});
						
					}else{
						 $("#Pagination1").hide();                
						 $("#oprateBtnDiv").hide();                
						 TableStyle("J-table-data1>tbody",5,2,"没有相应数据");
					}				
				}, 
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					 TableStyle("J-table-data1>tbody",5,2,"数据异常");  
				}
		});	
	}
	
	function validate(){
		//数字校验，自动矫正不符合数学规范的数学(小数两位)
		var inputs =  $('.approveAmt'),checkFn;	
		checkFn = function(){
			var me = $(this),v = $(this).val(),index;
			me.value = v = v.replace(/^\.$/g, '');
			index = v.indexOf('.');
			if(index > 0){
				me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');			
				if(v.substring(index+1,v.length).length>2){				
					me.value= v  = v.substring(0, v.indexOf(".") + 3);
				}
			}	
			me.value = v = v.replace(/[^\d|^\.]/g, '');
			me.value = v = v.replace(/^00/g, '0');	
			
			//驗證最大值
			var maxmoney = 9999999.99;
			var result;
			if (me.value > maxmoney) {
				result=maxmoney;
			}else{
				result=me.value;
			}
			
			$(this).val(result);
			
			
		};
		inputs.keyup(checkFn);
		inputs.blur(formatMoney);
		
	};
	
	var formatMoney = function(){
			var num = $(this).val();
			
			if(num == ''){
				num = '0';
			}
			var num = num.replace(/,/g, ''),re = /(-?\d+)(\d{3})/;
			if(isNaN(parseFloat(num))){
				var num2=0;
			}else{
				var	num2 = parseFloat(num);
			}
					
			if(Number.prototype.toFixed){
				num2 = (num2).toFixed(2);
			}else{
				num2 = Math.round(num2*100)/100
			}
			num2  =  '' + num2;
			while(re.test(num2)){
				num2 = num2.replace(re,"$1,$2");
			}
			$(this).val(num2);
		};
	
	

    //提交並派獎
	var awardsSubmit = function(){
		
		//選取的資料抽取
		var selectedItems = new Array();
		var selectedItem;
		$("table td input[type='checkbox']:checked").each(function() {
			
			var id = parseInt($(this).val()); //act_id
			var account = $(this).parent().next().text(); //account
			var user_id = parseInt($(this).parent().next().children().val()); //userId
			// 拿掉数字FORMAT 才能使用
			var changeAmt = parseInt(parseFloat($(this).parent().next().next().text().replace(',',''))*10000); //changeAmt
			var approverAmt = parseInt(parseFloat($(this).parent().next().next().next().children().val().replace(',',''))*10000); //approveAmt
			// 拿掉数字FORMAT 才能使用
			var approverMemo = $(this).parent().next().next().next().next().children().val(); //approverMemo
			var actName = $('#act_Name option:selected').text()  //actName
			
			selectedItem={'id':id,'account':account,'user_id':user_id,'changeAmt':changeAmt,'approverAmt':approverAmt,'approverMemo':approverMemo,'actName':actName};
			selectedItems.push(selectedItem);
			
		});
				

		//檢查有無勾選資料
    	if($.trim(selectedItems) ==''){
			msg.show({
				mask: true,
				content: '<h3 style="height:30px;line-height:30px;text-align:center;">请选择至少一条有效数据</h3><div style="height:30px;line-height:30px;"></div>',
				confirmIsShow: 'true',
				confirmText: '确定',
				confirmFun: function(){
					msg.hide();
				}
			});
			// 重新註冊派獎鈕功能
			$('#passBtn').click(function() {
				awardsSubmit();
			});
			return false;
		}
		//解除派獎鈕的事件防止ajax重複發送
		$('#passBtn').unbind('click');
		var jdata={"data":selectedItems};
		//送出派獎資料
		$.ajax({
				url:'/admin/Opterators/index?parma=aassubmit',				
				dataType:'json',
				method:'post',
				data:jdata,					
				success:function(data){
					//  不管成功與否都重新刷一下
					SelectByWhereInfoUntreat("0");
					// 重新註冊派獎鈕功能
					$('#passBtn').click(function() {
						awardsSubmit();
					});
					
				}					
		});
	};
	
	$('#passBtn').click(function() {
		awardsSubmit();
	});
    
    
	
	
})();	
</script>
{/literal}
</body>
</html>