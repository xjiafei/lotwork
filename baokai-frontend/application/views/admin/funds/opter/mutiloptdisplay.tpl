	<!-- //////////////头部公共页面////////////// -->
	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt;人工批量操作</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						
						<div class="ui-tab">
							<ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
							<!-- {if "FUND_MANUAL_BATCH_UNTREAT"|in_array:$smarty.session.datas.info.acls} -->
								<li class="current">未处理</li>
                                <!--{else}-->
                                 <li style="display:none;"></li>
							<!-- {/if} -->
							<!-- {if "FUND_MANUAL_BATCH_COMPLETE"|in_array:$smarty.session.datas.info.acls} -->
								<li class="ProcessingData">处理中</li>
                                <!--{else}-->
                                 <li style="display:none;"></li>
							<!-- {/if} -->
							<!-- {if "FUND_MANUAL_BATCH_COMPLETE"|in_array:$smarty.session.datas.info.acls} -->
								<li >已完成</li>
                                <!--{else}-->
                                <li style="display:none;"></li>
							<!-- {/if} -->
							<!-- {if "FUND_MANUAL_BATCH_UPLOAD"|in_array:$smarty.session.datas.info.acls} -->
								<li >批量上传</li>
                                <!--{else}-->
                                <li style="display:none;"></li>
							<!-- {/if} -->
                                 <a>每页记录数:</a>
                                 <select class="ui-select w-2" id="per_page">
                                    <option value="25">25</option>
                                    <option value="50" selected="">50</option>
                                    <option value="100">100</option>
                                    <option value="200">200</option>	
                                </select>
							</ul>
							
						<ul class="ui-form ui-tab-content ui-tab-content-current"  id="DivRules">
                        	<li>	
								<table class="table table-info text-center" id="J-table-data">
								<thead>
									<tr>
										<th><input id="J-select-all" name="" style="vertical-align: middle;" type="checkbox"> 全选</th>
										<th>人工单单号</th>
										<th>人工单类型</th>
										<th>涉及金额</th>
										<th>涉及用户名</th>
										<th>是否VIP</th>
										<th>建单时间</th>
										<th>建单管理员</th>
										<th>建单原因</th>
									</tr>
								</thead>
								<tbody id="showInfo">
								</tbody>
								<tfoot>
									<tr>
										<td colspan="2">
											<div style="float:left;" id="oprateBtnDiv" style="display:none;">
											<!-- {if "FUND_MANUAL_BATCH_UNTREAT_PASS"|in_array:$smarty.session.datas.info.acls} -->
												<a class='btn btn-small' href='javascript:void(0);' id="passBtn">通过<b class='btn-inner'></b></a>
											<!-- {/if} -->
											<!-- {if "FUND_MANUAL_BATCH_UNTREAT_REFUSE"|in_array:$smarty.session.datas.info.acls} -->
												<a class='btn btn-small' href='javascript:void(0);' id="unPassBtn">拒绝<b class='btn-inner'></b></a>
											<!-- {/if} -->
											</div>
										</td>
                                   		<td colspan="7">
                                          <div id="Pagination" class="pagination" ></div>
                                      	</td>
                                    </tr>
								</tfoot>
							</table>
							</li>						
						</ul>
						
						<ul class="ui-form ui-tab-content"  id="DivRules1">
                        	<li>
								<table class="table table-info table-function" id="J-table-data1">
								<thead>
									<tr>
										<th>人工单单号</th>
										<th>人工单类型</th>
										<th id="J-sp-status" class="sp-td">
                                        <div class="sp-td-cont sp-td-cont-b">
                                            <div class="sp-td-title">状态</div>
                                            <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                <ul>
                                        <!-- {foreach from=$aStatusArray item=data key=key} -->
                                            <li data-select-id="{$key}"><a href="#">{$data}</a></li>
                                        <!-- {/foreach} -->
                                              </ul>
                                            </div>
                                            <span class="sp-filter-close"></span>
                                        </div>
                                        </th> 
										<th>涉及金额</th>
										<th>涉及用户名</th>
										<th>是否VIP</th>
										<th>建单时间</th>
										<th>建单管理员</th>
										<th>建单原因</th>
										<th>审核管理员</th>
										<th>审核结束时间</th>
									</tr>
								</thead>
								<tbody id="showInfo1">
								</tbody>
								<tfoot>
									<tr>
                                   		<td colspan="11">
                                          <div id="Pagination1" class="pagination" ></div>
                                      	</td>
                                    </tr>
								</tfoot>
							</table>
							</li>						
						</ul>
						
						<ul class="ui-form ui-tab-content"  id="DivRules2">
                        	<li>	
								<h3 class="ui-title">
								1.批量上传
								</h3>
								<ul class="ui-form">
									<li>
										<label class="ui-label big">建单类型：</label>
										<select class="ui-select w-3" name="type" id="multiType">
											<option value="0">请选择人工建单类型</option>
											<!-- {foreach from=$aTypeArray key=key item=data} -->
												<option value="{$key}">{$data}</option>
											<!-- {/foreach} -->
										</select>
										<span class="ui-text-info">请慎重选择建单类型，并填写清楚建单原因</span>
									</li>
										<form id="J-form-file" method="post" enctype="multipart/form-data" action="/admin/Opterators/index?parma=multiupload" target="check_file_frame">
									<li>
											<label class="ui-label big">上传文件：</label>
											<input type="file" name="file" value="" class="input w-4 file">
											<input id="J-file-updata" type="submit" value="载入文件" class="btn btn-important">
											<input type="hidden" name="type" id="postType">
											<input id="J-reset-form" type="reset" style="outline:none;-ms-filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=0);filter:alpha(opacity=0);opacity: 0;width:0px; height:0px;z-index:1;background:#000">
											<iframe src="" name="check_file_frame" style="display:none;"></iframe>
										</form>
									</li>
									<li>
										<h4 class="ui-label">注意信息：</h4>
										<p class="ui-multiline">
										1) 下载模板文件: <a href="/upload/files/加币-活动礼金模板.xlsx">加币-活动加币模板</a> 请按照模板格式添加编辑<br>
										2) 请点击浏览按钮，选择本地准备好用于批量充值的Excel文档。<br>
										3) 统一转换成2007/2010/2013版的Excel文档，<span class="color-red">看系统支持哪个版本的Excel</span>，然后点击载入文件按钮预览按钮<br>
										4) 仔细核对文档，不要重复充值，错误金额等，确认无误后，点击最下面的批量充值按钮进行充值<br>
										5) 一次性充值量不能太大，最大500条记录
										</p>
									</li>
									<li>
										<h4 class="ui-label">表单校验：</h4>
										<p class="ui-multiline">
										1) 用户名必须是在平台存在，否则不能提交<br>
										2) 金额必须为“数字”， <span class="color-red">金额不能大于5000.00元</span><br>
										3) 建单原因不能为空
										</p>
									</li>
								</ul>
								<div class="ui-title"></div>
								<form id="J-amount-rules" action="#" method="post">
									<ul class="ui-form">
										<li>
											<label class="ui-label"></label>
											<div id="J-table-data3" class="select-position">
												您还没有上传任何加币规则
											</div>
										</li>
										<li>
											<label class="ui-label"></label>
											<!-- {if "FUND_MANUAL_BATCH_UPLOAD_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
											<input id="J-add-amount" type="submit" class="btn-submit" value="批量加币">
											<!-- {/if} -->
										</li>
									</ul>
								</form>
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
			AllData();
			$('#DivRules').addClass('ui-tab-content-current');
			$('#DivRules1').removeClass('ui-tab-content-current');
			$('#DivRules2').removeClass('ui-tab-content-current');
		}else if(tabIndexs ==1) {
			ProcessingData();
			$('#DivRules1').addClass('ui-tab-content-current');
			$('#DivRules').removeClass('ui-tab-content-current');
			$('#DivRules2').removeClass('ui-tab-content-current');
		}  else if(tabIndexs ==2) {
			completeData();
			$('#DivRules1').addClass('ui-tab-content-current');
			$('#DivRules').removeClass('ui-tab-content-current');
			$('#DivRules2').removeClass('ui-tab-content-current');
		} else {
			$('#DivRules2').addClass('ui-tab-content-current');
			$('#DivRules2').removeAttr('style');
			$('#DivRules2').find('.ui-form').removeAttr('style');
			$('#DivRules1').removeClass('ui-tab-content-current');
			$('#DivRules').removeClass('ui-tab-content-current');
			
		}
		
	});
	
		 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuAllOpterators');	
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
    
	AllData();
	
	function AllData(){
		
		$('#J-select-all').prop('checked', false);
		//数据处理状态标识(未处理)
		 var Title = new phoenix.SuperSearch({
			name: 'Title',			
			group: group,
			expands:{getValue:function(){
					return 0;	
				}}
		});
		SelectByWhereInfo("0");	
		//每页记录数更改事件
		$("#per_page").change(function(){
			SelectByWhereInfo("0");			
		
		});		
		function SelectByWhereInfo(pages) {
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
			
			 var formData = group.getFormData(),mask = phoenix.Mask.getInstance();
			 $("#J-table-data>tbody").html("");				
			  $("#Pagination").hide();   
			  $("#oprateBtnDiv").hide();   
			 $.ajax({
				url:'/admin/Opterators/index?parma=loadmutldata',
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
							num_display_entries: 8,
							current_page: pages,
							items_per_page: per_page_number,
							callback: SelectByWhereInfo
						});
						var html = "";
						//数据填充
						$.each(re, function (i) {						
								html += "<tr><td><input value='" + re[i].id + "|" + re[i].typeId + "' type='checkbox' style='margin-right:29px;'></td>";    
								html += "<td>" + re[i].sn + "</td>";    
								html += "<td>" + re[i].typeName + "</td>"; 
								html += "<td>" + re[i].withdrawAmt + "</td>";
								html += "<td>" + re[i].rcvAccount + "</td>";
								html += "<td>" + (re[i].isVip =="1" ?"是":"否") + "</td>";
								html += "<td>" + re[i].applyTime + "</td>";	
								html += "<td>" + re[i].applyAccount + "</td>";	
								html += "<td>" + re[i].memo + "</td>";	
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
						
					}else{
						 $("#Pagination").hide();                
						 $("#oprateBtnDiv").hide();                
						 TableStyle("J-table-data>tbody",9,2,"没有相应数据");
					}				
				}, 
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					 TableStyle("J-table-data>tbody",9,2,"数据异常");  
				}
			});	
		}	
	}
	function ProcessingData(){
		
	   //状态
		var status = new phoenix.SuperSearch({
			name: 'status',			
			type: 'select',
			isAutoWidth: true,
			 el: '#J-sp-status',			
			expands:{getValue:function(){
					return 1;	
				}},
			group: group
		});
		//数据处理状态标识(已完成)
		 var Title = new phoenix.SuperSearch({
			name: 'Title',			
			group: group,
			expands:{getValue:function(){
					return 1;	
				}}
		});
		SelectByWhereInfo("0");	
		group.removeEvent('dataChange');
		//提交数据
		group.addEvent('dataChange', function(){
			SelectByWhereInfo("0");				
		});	
			//每页记录数更改事件
		$("#per_page").change(function(){
			SelectByWhereInfo("0");			
		
		});	
		function SelectByWhereInfo(pages) {
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
			
			 var formData = group.getFormData(),mask = phoenix.Mask.getInstance();
			 $("#J-table-data1>tbody").html("");				
			  $("#Pagination1").hide();   
			 $.ajax({
				url:'/admin/Opterators/index?parma=loadmutldata',
				dataType:'json',
				method:'post',
				data:formData,
				beforeSend:function(){				
					TableStyle("showInfo1",11,1,"查询中");
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
							num_display_entries: 8,
							current_page: pages,
							items_per_page: per_page_number,
							callback: SelectByWhereInfo
						});
						var html = "";
						//数据填充
						$.each(re, function (i) {	
								html += "<tr><td>" + re[i].sn + "</td>";    
								html += "<td>" + re[i].typeName + "</td>"; 
								html += "<td>" + re[i].status + "</td>";
								html += "<td>" + re[i].withdrawAmt + "</td>";
								html += "<td>" + re[i].rcvAccount + "</td>";
								html += "<td>" + (re[i].isVip =="1" ?"是":"否") + "</td>";
								html += "<td>" + re[i].applyTime + "</td>";	
								html += "<td>" + re[i].applyAccount + "</td>";	
								html += "<td>" + re[i].memo + "</td>";	
								html += "<td>" + re[i].apprAccount + "</td>";	
								html += "<td>" + re[i].apprTime + "</td></tr>";
						});
						$("#J-table-data1>tbody").html(html);
					}else{
						 $("#Pagination").hide();                
						 TableStyle("J-table-data1>tbody",11,2,"没有相应数据");
					}				
				}, 
					 error: function(XMLHttpRequest, textStatus, errorThrown) {
					 TableStyle("J-table-data1>tbody",11,2,"数据异常");  
				}
			});	
			 
			 
		}	
	}
	

	function completeData(){
		
	   //状态
		var status = new phoenix.SuperSearch({
			name: 'status',			
			type: 'select',
			isAutoWidth: true,
			 el: '#J-sp-status',			
			expands:{getFormValue:getSelectValue},
			group: group
		});
		//数据处理状态标识(已完成)
		 var Title = new phoenix.SuperSearch({
			name: 'Title',			
			group: group,
			expands:{getValue:function(){
					return 1;	
				}}
		});
		SelectByWhereInfo("0");	
		group.removeEvent('dataChange');
		//提交数据
		group.addEvent('dataChange', function(){
			SelectByWhereInfo("0");				
		});	
			//每页记录数更改事件
		$("#per_page").change(function(){
			SelectByWhereInfo("0");			
		
		});	
		function SelectByWhereInfo(pages) {
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
			
			 var formData = group.getFormData(),mask = phoenix.Mask.getInstance();
			 $("#J-table-data1>tbody").html("");				
			  $("#Pagination1").hide();   
			 $.ajax({
				url:'/admin/Opterators/index?parma=loadmutldata',
				dataType:'json',
				method:'post',
				data:formData,
				beforeSend:function(){				
					TableStyle("showInfo1",11,1,"查询中");
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
							num_display_entries: 8,
							current_page: pages,
							items_per_page: per_page_number,
							callback: SelectByWhereInfo
						});
						var html = "";
						//数据填充
						$.each(re, function (i) {	
								html += "<tr><td>" + re[i].sn + "</td>";    
								html += "<td>" + re[i].typeName + "</td>"; 
								html += "<td>" + re[i].status + "</td>";
								html += "<td>" + re[i].withdrawAmt + "</td>";
								html += "<td>" + re[i].rcvAccount + "</td>";
								html += "<td>" + (re[i].isVip =="1" ?"是":"否") + "</td>";
								html += "<td>" + re[i].applyTime + "</td>";	
								html += "<td>" + re[i].applyAccount + "</td>";	
								html += "<td>" + re[i].memo + "</td>";	
								html += "<td>" + re[i].apprAccount + "</td>";	
								html += "<td>" + re[i].apprTime + "</td></tr>";
						});
						$("#J-table-data1>tbody").html(html);
					}else{
						 $("#Pagination").hide();                
						 TableStyle("J-table-data1>tbody",11,2,"没有相应数据");
					}				
				}, 
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					 TableStyle("J-table-data1>tbody",11,2,"数据异常");  
				}
			});	
			 
			 
		}	
	}
	
	//通过
	$(document).on('click', '#passBtn', function(e){			
		var stext="确定通过审核？";
		var selectedItems = new Array();
		$("table td input[type='checkbox']:checked").each(function() {selectedItems.push($(this).val());});
		operateFunction(selectedItems,stext,1);
	});
	
    //拒绝
	$(document).on('click', '#unPassBtn', function(e){			
	    var stext="确定拒绝审核 ？";
		var selectedItems = new Array();
		$("table td input[type='checkbox']:checked").each(function() {selectedItems.push($(this).val());});
		operateFunction(selectedItems,stext,2);
	});
    //审核操作
    var operateFunction = function(sdata,stext,status){
    	if($.trim(sdata) ==''){
			msg.show({
				mask: true,
				content: '<h3 style="height:30px;line-height:30px;text-align:center;">请选择审核项!</h3><div style="height:30px;line-height:30px;"></div>',
				confirmIsShow: 'true',
				confirmText: '确定',
				confirmFun: function(){
					msg.hide();
				}
			});
			return false;
		}
    	var postdata = {'id':sdata,'status':status};
    	msg.show({
			mask: true,
			content: '<h3 style="height:30px;line-height:30px;text-align:center;">'+stext+'!</h3><div style="height:30px;line-height:30px;"></div>',
			confirmIsShow: 'true',
			confirmText: '确定',
			confirmFun: function(){
                                msg.win.doConfirm = function(){
                                    //console.log('cannot send again!')
                                };
				$.ajax({
					url:'/admin/Opterators/index?parma=dealmutil',				
					dataType:'json',
					method:'post',
					data:postdata,					
					success:function(data){
						msg.hide();
						msg1.show({
							mask: true,
							content: '<h3 style="height:30px;line-height:30px;text-align:center;">'+data.data+'!</h3><div style="height:30px;line-height:30px;"></div>',
							confirmIsShow: 'true',
							confirmText: '确定',
							confirmFun: function(){
								msg1.hide();
							//  不管成功與否都重新刷一下
							//	if(data['status']=='ok'){	
									AllData();
							//	}
							}
						});
					}					
				});
			},
			cancelIsShow: 'true',
			cancelText: '取消',
			cancelFun: function(){
				msg.hide();
			}
		});
    };
    
    
	//下载报表	
	$('#J-Download-Report').click(function(){	   
		var data = group.getFormData();
		var param='';
		var p='';
		for(p in data){
			if(data.hasOwnProperty(p)){
				param += '&'+p+'='+ data[p];
			}
		}
		window.open("/admin/Opterators/index?parma=ex"+param);
	});
	
	
	var $button = $('#J-file-updata'),
	$buttonb = $('#J-add-amount'),
	$multiType = $('#multiType');

	$button.bind('click', function(e) {
		var form = $(this).parent();
		if(!checkType()){
			return false;
		}
		checkFile(form.find('.file').get(0), form);
		e.preventDefault();
	});
	var updateRulesEvent = function(e) {
		updataRules();
		e.preventDefault();
	};
	$buttonb.bind('click', updateRulesEvent);
	
	var checkType = function(){
		if($multiType.val()<=0){
			msg.show({
				mask: true,
				content: '<h3 style="height:30px;line-height:30px;text-align:center;">对不起，请选择建单类型 ！</h3><div style="height:30px;line-height:30px;"></div>',
				confirmIsShow: 'true',
				confirmText: '确定',
				mask: true,
				confirmFun: function(){
					msg.hide();
					$multiType.removeAttr("disabled");
				}
			});
			return false;
		} else {
			$multiType.attr("disabled","disabled");
			$('#postType').val($multiType.val());
		}
		return true;
	}
	
	//检测上传
	var checkFile = function(dom, form) {
		var result = dom.value,
			fileext = result.substring(result.lastIndexOf("."), result.length),
			fileext = fileext.toLowerCase();
	    if(fileext=='')
		{
			msg.show({
				mask: true,
				content: '<h3 style="height:30px;line-height:30px;text-align:center;">您还未上传文件，请选择上传文件！</h3><div style="height:30px;line-height:30px;"></div>',
				confirmIsShow: 'true',
				confirmText: '确定',
				mask: true,
				confirmFun: function(){
					msg.hide();
				}
			});
			return false;
		}
		if (fileext != '.xlsx') {
			msg.show({
				mask: true,
				content: '<h3 style="height:30px;line-height:30px;text-align:center;">对不起，导入数据格式必须是.xlsx格式文件哦，请您调整格式后重新上传，谢谢  ！</h3><div style="height:30px;line-height:30px;"></div>',
				confirmIsShow: 'true',
				confirmText: '确定',
				mask: true,
				confirmFun: function(){
					msg.hide();
				}
			});
			return false;
		}
	
		form[0].submit();
	};
	
	//接收文件
	window.getFile = function(data) {
		var formArea = $('#J-table-data3'),
		    strTable="",
			str="",
			$resetDom = $('#J-reset-form');
		if (Number(data['isSuccess'] == 1)) {
			if (data['data']) {
		       strTable='<table class="table table-info text-center w-8"><thead><tr><th>用户账户名</th><th>加币金额</th><th>建单原因</th><th>备注</th></tr></thead><tbody>';
			   for(var i=0;i< data['data'].length;i++)
			   {
				       str+='<tr>';
				       if(data['data'][i]['viliable'] == '0'){
				    	   str+='<td style="color:red;" name="false">'+data['data'][i]['account']+'</td>';
				       } else {
				    	   str+='<td>'+data['data'][i]['account']+'</td>';
				       }
					   str+='<td>'+data['data'][i]['mount']+'</td>';
					   str+='<td>'+data['data'][i]['memo']+'</td>';
					   str+='<td>'+data['data'][i]['note']+'</td>';
				       str+='</tr>';
			   }
			    strTable=strTable+str;
				strTable+='</tbody></table>';
				formArea.html(strTable);
			}
		} else {
			msg.show({
				content:data['data'],
				mask: true,
				time: 3
			})
			$multiType.removeAttr("disabled");
		}
	
		$resetDom.trigger('click');
	}
	
	//上传加币规则
	updataRules= function(e){
		var rulesForm = $('#J-amount-rules'),
			data = rulesForm.serialize(),
			formArea = $('#J-table-data3'),
			form=$('#J-form-file'),
			msg = new phoenix.Message();
	    if(!checkType()){
			return false;
		}
		if(formArea.find('table').length==0)
		{
			if(!checkFile(form.find('.file').get(0), form))
			{
				return false;
			}
		}else
		{
			if($('#J-table-data3').find('table').find('[name="false"]').length>0)
			{
				msg.show({
					content:'您提交的内容中部分用户名不存在，请核实后重新上传!',
					mask: true,
					time: 3
				})
				return false;
			}
		}
		$buttonb.unbind('click');
		$.ajax({
			url: '/admin/Opterators/index?parma=addmutil',
			type: 'post',
			dataType: 'json',
			data: data,
		})
		.done(function(r) {
	
			if (Number(r['status'] == 'ok')) {
				$multiType.removeAttr("disabled");
				msg.show({
					content:'上传成功，请在未处理页面中查看',
					mask: true,
					time: 3
				})
				formArea.text('您还没有上传任何加币规则');
                                $buttonb.bind('click', updateRulesEvent);
			}else 
			{
				msg.show({
					content:r['data'],
					mask: true,
					time: 3
				})
			}
	
		})
		.fail(function() {
			//console.log("error");
		})
		.always(function() {
			//console.log("complete");
		});
	}
	
})();	
</script>
{/literal}
</body>
</html>