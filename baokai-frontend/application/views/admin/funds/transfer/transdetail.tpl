	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; 转账明细</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<ul class="ui-search">
							<li class="name">
								<label class="ui-label">用户名：</label>
								<input type="text" value="请输入您的用户名" id="userName" class="input" />
							</li>
							<li><a class="btn btn-important" href="javascript:void(0);" id="J-Submit">搜 索<b class="btn-inner"></b></a></li>
						</ul>
						
                        <ul  id="showDiv" style="display:none">
                        <li >
                            <h3 class="ui-title">用户转账汇总：</h3>
                            
                            <table id="J-table-data" class="table table-info table-function">
                                <thead>
                                    <tr>
                                        <th >
                                            <div class="sp-td-cont">
                                                <div class="sp-td-title">起始日期</div>
                                            </div>
                                        </th>
                                        <th >
                                            <div class="sp-td-cont">
                                                <div class="sp-td-title">截止日期</div>
                                            </div>
                                        </th>
                                        <th >
                                            <div class="sp-td-cont">
                                                <div class="sp-td-title">用户名</div>
                                            </div>
                                        </th>
                                        <th >
                                            <div class="sp-td-cont">
                                                <div class="sp-td-title">所属总代</div>
                                            </div>
                                        </th>
                                        <th >
                                            <div class="sp-td-cont">
                                                <div class="sp-td-title">是否VIP</div>
                                            </div>
                                        </th>
                                        <th >
                                            <div class="sp-td-cont">
                                                <div class="sp-td-title">转出次数</div>
                                            </div>
                                        </th>
                                        <th >
                                            <div class="sp-td-cont">
                                                <div class="sp-td-title">累计转出金额</div>
                                            </div>
                                        </th>
                                        <th >
                                            <div class="sp-td-cont">
                                                <div class="sp-td-title">收入次数</div>
                                            </div>
                                        </th>
                                        <th >
                                            <div class="sp-td-cont">
                                                <div class="sp-td-title">累计收入金额</div>
                                            </div>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody id="showInfo">
                                </tbody>                               
                                
                             </table>                        		
						</li>
                        <li>
                            <h3 class="ui-title">用户转账明细：</h3>
                             <table id="J-table-data2" class="table table-info table-function">
                                <thead>
                                    <tr>
                                        <th class="sp-td" id="J-sp-td-startime">									
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">起始日期</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                              <li>
                                                <div class="input-append">
                                                    <input type="text" tabindex="1" class="input w-2" id="sp-input-startime-time-1"> <!--- <input type="text" id="sp-input-startime-time-2" class="input w-2" size="10">--><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                </div>
                                               </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                                    
                                        </th>
                                        <th class="sp-td" id="J-sp-td-Deadline">										
                                             <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">截止日期</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                              <li>
                                                <div class="input-append">
                                                    <input type="text" tabindex="1" class="input w-2" id="sp-input-Deadline-time-1"> <!--- <input type="text" id="sp-input-Deadline-time-2" class="input w-2" size="10">--><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                </div>
                                               </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>
                                        <th class="sp-td" id="J-sp-td-transferTime">									
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">转账时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                              <li>
                                                <div class="input-append">
                                                    <input type="text" tabindex="1" class="input w-2" id="sp-input-transferTime-time-1"> - <input type="text" id="sp-input-transferTime-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                </div>
                                               </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>                                        
                                        </th>
                                        <th class="sp-td" id="J-sp-td-Direction">									
                                            <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">方向</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                            <li data-select-id="0"><a href="#">转出</a></li>
                                                            <li data-select-id="1"><a href="#">转入</a></li>
                                                      </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                                    
                                        </th>
                                        <th >
                                            <div class="sp-td-cont">
                                                <div class="sp-td-title">转出方</div>
                                            </div>
                                        </th>
                                        <th >
                                            <div class="sp-td-cont">
                                                <div class="sp-td-title">转入方</div>
                                            </div>
                                        </th>
                                        <th >
                                            <div class="sp-td-cont">
                                                <div class="sp-td-title">金额</div>
                                            </div>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody id="showInfo2">
                                </tbody>                                
                                <tfoot>
                                    <tr>
                                        <td colspan="7">
                                              <div id="Pagination" class="pagination" ></div>
                                        </td>
                                    </tr>
                                </tfoot>
                             </table>     			
						</li>
					</ul >
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
	selectMenu('Menufunds','MenuTransferDetail');
	//输入数据限制
	//允许输入英文字母和数字
	var allowWordAndNumber = function(v){
		return v.replace(/[^A-Za-z0-9]/g, '');
	};
	//允许输入数字和小数
	var allowNumber = function(){
		var me = this,v = me.value,index;
		me.value = v = v.replace(/^\.$/g, '');			
		index = v.indexOf('.');
		if(index > 0){
			me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');				
		}
		me.value = v = v.replace(/[^\d|^\.]/g, '');
		me.value = v = v.replace(/^00/g, '0');		
		if(v.split('.').length > 2){
			arguments.callee.call(me);
		}
	};	
	//获取两个input值
	var getTowInputValue = function(){
		var me = this,ipts = me.dom.find('input[type="text"]'),name = me.name,result = {};
		result[name+'1'] = ipts[0].value;
		result[name+'2'] = ipts[1].value;
		return result;
	};
	var getOneInputValue=function()
	{
	     var me=this,ipts=me.dom.find('input[type="text"]'),name = me.name,result = {};
		 result[name] = ipts[0].value;
		 return result;
	};
	//获取select类型值
	var getSelectValue = function(){
		var me = this,li = me.dom.find('li.current'),v = '',result = {};
		if(li.size() > 0){
			v = li.eq(0).attr('data-select-id');
			result[me.name] = v;
		}
		return result;
	};
	var group = new phoenix.SuperSearchGroup();
	
	
	//起始日期
	var startimetime = new phoenix.SuperSearch({
		name: 'startimetime',
		keyCode: 'ctrl+68',
		type: 'time',
		el: '#J-sp-td-startime',
		group: group,
		expands:{getFormValue:getOneInputValue}
	});
	var startimeTime1 = $('#sp-input-startime-time-1'),startimeDt1;
	startimeTime1.click(function() {
		startimeDt1 = new phoenix.DatePicker({
			input: startimeTime1,
			isLockInputType: false
		});
		startimeDt1.show();
	});
	startimetime.addEvent('afterFocus',
	function() {
		startimeTime1.click()
	});
	
	//截止日期
	var Deadlinetime = new phoenix.SuperSearch({
		name: 'Deadlinetime',		
		type: 'time',
		el: '#J-sp-td-Deadline',
		group: group,
		expands:{getFormValue:getOneInputValue}
	});
	var DeadlineTime1 = $('#sp-input-Deadline-time-1'),DeadlineDt1;
	DeadlineTime1.click(function() {
		DeadlineDt1 = new phoenix.DatePicker({
			input: DeadlineTime1,
			isLockInputType: false
		});
		DeadlineDt1.show();
		
	});
	Deadlinetime.addEvent('afterFocus',
	function() {
		DeadlineTime1.click()
	});
	//转账时间
	var transferTimetime = new phoenix.SuperSearch({
		name: 'transferTimetime',		
		type: 'time',
		el: '#J-sp-td-transferTime',
		group: group,
		expands:{getFormValue:getTowInputValue}
	});
	var transferTimeTime1 = $('#sp-input-transferTime-time-1'),transferTimeTime2 = $('#sp-input-transferTime-time-2'),transferTimeDt1,transferTimeDt2;
	transferTimeTime1.click(function() {
		transferTimeDt1 = new phoenix.DatePicker({
			input: transferTimeTime1,
			isLockInputType: false
		});
		transferTimeDt1.show();
		transferTimeDt1.addEvent('afterSetValue',
		function() {
			transferTimeTime2.focus();
			transferTimeTime2.click();
		})
	});
	transferTimetime.addEvent('afterFocus',
	function() {
		transferTimeTime1.click()
	});
	transferTimeTime2.click(function() {
		transferTimeDt2 = new phoenix.DatePicker({
			input: transferTimeTime2,
			isLockInputType: false
		}).show();
	});
	transferTimetime.addEvent('afterBlur',function() {
		if (transferTimeDt1) {
			transferTimeDt1.hide();
		}
		if (transferTimeDt2) {
			transferTimeDt2.hide();
		}
	});	
	group.addEvent('dataChange', function(){
		SelectByWhereInfo2("0");				
	});	
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
		
	var direction = new phoenix.SuperSearch({
		name: 'direction',
		keyCode: 'ctrl+66',
		type: 'select',
		isAutoWidth: true,
		el: '#J-sp-td-Direction',
		group: group,
		expands:{getFormValue:getSelectValue}
	});
	
	$('#userName').focus(function(){		
		if($('#userName')[0].value=='请输入您的用户名'){ $("#userName")[0].value='';}				
		}).blur(function(){
		var v = $.trim(this.value);
		if(v == ''){
			$("#userName")[0].value='请输入您的用户名';			
		}else{
			isture=true;
		}	
	});
	
	//汇总查询
	function SelectByWhereInfo() {		
		var userName=$.trim($('#userName').val());		
		$("#J-table-data>tbody").html("");	
		$('#showDiv').show();			
		$.ajax({
		url:'/admin/Transfer/index?parma=vall',
		dataType:'json',
		method:'post',			
		data:{userName:userName},
		beforeSend:function(){			
			TableStyle("showInfo",9,1,"查询中");
		},			
		success:function(data){
			if(data.text.length>0){	
				var resultAll = eval(data.text);               
				var re = resultAll.counts[0];					
				var html = "";		
				$.each(re, function (i) {			
					html += "<tr><td>" + re[i].startDate + "</td>";						
					html += "<td>" + re[i].endDate + "</td>";							
					html += "<td>" + re[i].account + "</td>";	
					html += "<td>" + re[i].group +" </td>";
					html += "<td>" + re[i].isVip + "</td>";
					html += "<td>" + re[i].transferTimes +" </td>";
					html += "<td>" + re[i].allOutTransfer + "</td>";
					html += "<td>" + re[i].incomeTimes + "</td>";
					html += "<td>" + re[i].allInTransfer +" </td></tr>";						
				});
				$("#J-table-data>tbody").html(html);	
				
			}else{	
				TableStyle("J-table-data>tbody",9,2,"没有相应数据"); 
			}
		}, 
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			 TableStyle("J-table-data>tbody",9,2,"数据异常");  
		}
		});	
		
				
		
	}	
	//明细查询
	function SelectByWhereInfo2(pages) {		
		//当前页数据行数
		 var  per_page_number = 20;		
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
		var userName  = new phoenix.SuperSearch({
			name: 'userName', 
			group: group,			
			expands:{getValue:function(){
				return $.trim($('#userName').val());	
			}}
		})
				
		$("#J-table-data>tbody").html("");	
		$('#showDiv').show();
		$("#Pagination").hide();    			
		$.ajax({
			url:'/admin/Transfer/index?parma=vper',
			dataType:'json',
			method:'post',
			data: group.getFormData(),
			beforeSend:function(){	
				TableStyle("showInfo",9,1,"查询中");
				TableStyle("showInfo2",7,1,"查询中");
			},
			success:function(data){	
				if(data.text.counts.length>0){
					var resultAll = eval(data);               
					var re = resultAll.text.counts;
					var html = "";
					$.each(re, function (i) {			
						html += "<tr><td>" + re[i].startDate + "</td>";						
						html += "<td>" + re[i].endDate + "</td>";							
						html += "<td>" + re[i].account + "</td>";	
						html += "<td>" + re[i].group +" </td>";
						html += "<td>" + re[i].isVip + "</td>";
						html += "<td>" + re[i].transferTimes +" </td>";
						html += "<td>" + re[i].allOutTransfer + "</td>";
						html += "<td>" + re[i].incomeTimes + "</td>";
						html += "<td>" + re[i].allInTransfer +" </td></tr>";						
					});
					$("#J-table-data>tbody").html(html);
					
				} else {
					TableStyle("J-table-data>tbody",9,2,"没有该用户数据");
				}
				if(data.text.details.length>0){			
					$("#Pagination").show();
					var resultAll = eval(data);
					var re = resultAll.text.details;
					var recordNum = 0;
					recordNum = resultAll.count[0].recordNum;	
					//分页回调				 
					$("#Pagination").pagination(recordNum, {
						num_edge_entries: 2,
						num_display_entries: 8,
						current_page: pages,
						items_per_page: per_page_number,
						callback: SelectByWhereInfo2
					});
					
					var html = "";
					$.each(re, function (i) {	
							html += "<tr><td>" + re[i].startDate + "</td>";	
							html += "<td>" + re[i].endDate + "</td>";
							html += "<td>" + re[i].transferTi + "</td>";
							html += "<td>" + re[i].transferDirection + "</td>";
							html += "<td>" + re[i].outAccount + "</td>";
							html += "<td>" + re[i].inAccount + "</td>";
							html += "<td>" + re[i].amount + "</td></tr>";
					});
					$("#J-table-data2>tbody").html(html);					
						
				}else{
					 $("#Pagination").hide();
					  TableStyle("J-table-data2>tbody",7,2,"没有相应数据");
					
				}				
			}, 
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 TableStyle("J-table-data>tbody",9,2,"数据异常");  
				 TableStyle("J-table-data2>tbody",7,2,"数据异常");  
			}
		});			
	}	
	
	//表单提交校验
	$('#J-Submit').click(function(){
		if(isture==false){
			return false;
		}	 

		//SelectByWhereInfo();
		SelectByWhereInfo2("0");	
	});	
	
	$("#userName").keypress(function (e) {
		var currKey = 0, e = e || event;
		currKey = e.keyCode || e.which || e.charCode;
		if (currKey == 13) {				
			//SelectByWhereInfo();	
			SelectByWhereInfo2("0");
		}
	});	
	
})();	
</script>
{/literal}
</body>
</html>