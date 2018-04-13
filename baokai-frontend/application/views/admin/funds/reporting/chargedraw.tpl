	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; {$title}</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title"><a class="btn btn-small" style="float:left;" href="javascript:void(0);" id="J-Download-Report">下载报表<b class="btn-inner"></b></a></h3>
									<table id="J-table-data" class="table table-info table-function">
									<thead>
										<tr>											
                                           <th class="sp-td" id="J-sp-td-StarTime">									
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">起始时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                  <li>
                                                    <div class="input-append">
                                                        <input type="text" tabindex="1" class="input w-2" id="sp-input-StarTime-time-1"><!-- - <input type="text" id="sp-input-StarTime-time-2" class="input w-2" size="10">--><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                    </div>
                                                   </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>                                                        
                                            </th>
                                              <th class="sp-td" id="J-sp-td-EndTime">									
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">截止时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                  <li>
                                                    <div class="input-append">
                                                        <input type="text" tabindex="1" class="input w-2" id="sp-input-EndTime-time-1"> <!--- <input type="text" id="sp-input-EndTime-time-2" class="input w-2" size="10">--><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                    </div>
                                                   </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>                                                        
                                            </th>
                                            
											  <th id="J-sp-UserName" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">用户名</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                        <li>
                                                            <div class="input-append">
                                                             <input type="text" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>		
											<th>所属组</th>											
                                            <th id="J-sp-request-OnlineRecharge" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">在线充值</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-OnlineRecharge1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-OnlineRecharge2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>   
										    </th>
                                              
											<th>在线提现</th>
											<th>充值手续费</th>
											<th>提现手续费</th>											
                                            <th id="J-sp-request-ArtificialCad" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">人工加币</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-ArtificialCad1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-ArtificialCad2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>   
											  </th>
                                              
											<th>人工扣币</th>										
                                            <th id="J-sp-request-TotalRecharge" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">充值总计</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-TotalRecharge1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-TotalRecharge2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>   
											  </th>
                                              
										
                                            <th id="J-sp-request-TotalWithdrawals" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">提现总计</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-TotalWithdrawals1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-TotalWithdrawals2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>   
											  </th>
                                              
											<th></th>
                                             <th id="J-sp-request-Balance" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">充提结余</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-Balance1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-Balance2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>   
											  </th>
                                              
										</tr>
									</thead>
									<tbody id="showInfo">
                                    </tbody>                                
                                    <tfoot>
                                        <tr>
                                            <td colspan="19">
                                                  <div id="Pagination" class="pagination" ></div>
                                            </td>
                                        </tr>
                                    </tfoot>
                                 </table>    							
								
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
	var isture=false;		
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuReport');
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
	//获取一个input值
	var getOneInputValue = function(){
		var me = this,ipts = me.dom.find('input[type="text"]'),name = me.name,result = {};
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
	
	//起始时间
	var StarTime = new phoenix.SuperSearch({
		name: 'startDate',
		keyCode: 'ctrl+68',
		type: 'time',
		el: '#J-sp-td-StarTime',
		group: group,
		expands:{getFormValue:getOneInputValue}
	});
	var StarTimeTime1 = $('#sp-input-StarTime-time-1'),StarTimeDt1;
	StarTimeTime1.click(function() {
		StarTimeDt1 = new phoenix.DatePicker({
			input: StarTimeTime1,
			isLockInputType: false
		});
		StarTimeDt1.show();
	});
	StarTime.addEvent('afterFocus',
	function() {
		StarTimeTime1.click()
	});
	
	//截止时间
	var EndTime = new phoenix.SuperSearch({
		name: 'endDate',		
		type: 'time',
		el: '#J-sp-td-EndTime',
		group: group,
		expands:{getFormValue:getOneInputValue}
	});
	var EndTimeTime1 = $('#sp-input-EndTime-time-1'),EndTimeDt1;
	EndTimeTime1.click(function() {
		EndTimeDt1 = new phoenix.DatePicker({
			input: EndTimeTime1,
			isLockInputType: false
		});
		EndTimeDt1.show();
	});
	EndTime.addEvent('afterFocus',
	function() {
		EndTimeTime1.click()
	});
	//用户名
	var UserName = new phoenix.SuperSearch({
		name: 'UserName',		
		el: '#J-sp-UserName',
		group: group
	});
	//在线充值
	var OnlineRecharge = new phoenix.SuperSearch({
		name: 'OnlineRecharge',
		keyCode: 'ctrl+85',
		el: '#J-sp-request-OnlineRecharge',
		group: group,
		expands:{getFormValue:getTowInputValue}
	});
	$('#J-input-OnlineRecharge1').keyup(allowNumber).blur(allowNumber);
	$('#J-input-OnlineRecharge2').keyup(allowNumber).blur(allowNumber);
	//人工加币
	var ArtificialCad = new phoenix.SuperSearch({
		name: 'ArtificialCad',		
		el: '#J-sp-request-ArtificialCad',
		group: group,
		expands:{getFormValue:getTowInputValue}
	});
	$('#J-input-ArtificialCad1').keyup(allowNumber).blur(allowNumber);
	$('#J-input-ArtificialCad2').keyup(allowNumber).blur(allowNumber);
	//充值总计
	var TotalRecharge = new phoenix.SuperSearch({
		name: 'TotalRecharge',		
		el: '#J-sp-request-TotalRecharge',
		group: group,
		expands:{getFormValue:getTowInputValue}
	});
	$('#J-input-TotalRecharge1').keyup(allowNumber).blur(allowNumber);
	$('#J-input-TotalRecharge2').keyup(allowNumber).blur(allowNumber);
	//提现总计
	var TotalWithdrawals = new phoenix.SuperSearch({
		name: 'TotalWithdrawals',		
		el: '#J-sp-request-TotalWithdrawals',
		group: group,
		expands:{getFormValue:getTowInputValue}
	});
	$('#J-input-TotalWithdrawals1').keyup(allowNumber).blur(allowNumber);
	$('#J-input-TotalWithdrawals2').keyup(allowNumber).blur(allowNumber);
	//充提结余
	var Balance = new phoenix.SuperSearch({
		name: 'Balance',		
		el: '#J-sp-request-Balance',
		group: group,
		expands:{getFormValue:getTowInputValue}
	});
	$('#J-input-Balance1').keyup(allowNumber).blur(allowNumber);
	$('#J-input-Balance2').keyup(allowNumber).blur(allowNumber);
	
group.addEvent('dataChange', function(){
		SelectByWhereInfo("0");				
	});	
	
	SelectByWhereInfo("0");	
	function SelectByWhereInfo(pages) {		
		//当前页数据行数
		 var  per_page_number = 50;		
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
		 $("#J-table-data>tbody").html("");		
		 $("#Pagination").hide();   		
		 $.ajax({
			url:'/admin/Reporting/index?parma=veda',
			dataType:'json',
			method:'post',
			data: group.getFormData(),
			beforeSend:function(){		
				TableStyle("showInfo",20,1,"查询中");
			},
			success:function(data){				
				if(data.text.length>0){					
					$("#Pagination").show();
					$('.main').css('overflow-x', 'auto');	
					//var resultAll = JSON.parse(data); //jQuery.parseJSON    
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
					$.each(re, function (i) {	
							html += "<tr><td>" + re[i].startDate + "</td>";	
							html += "<td>" + re[i].endDate + "</td>";
							html += "<td>" + re[i].userName + "</td>";
							html += "<td>" + re[i].group + "</td>";
							html += "<td>" + re[i].onlineRecharge + "</td>";
							html += "<td>" + re[i].onlineDraw + "</td>";
							html += "<td>" + re[i].rechargeFee + "</td>";
							html += "<td>" + re[i].drawithFee+ "</td>";
							html += "<td>" + re[i].coinByManual + "</td>";
							html += "<td>" + re[i].coinSubtract+ "</td>";
							html += "<td>" + re[i].allRecharge + "</td>";
							html += "<td>" + re[i].allDraw + "</td>";
							html += "<td></td>";
							html += "<td>" + re[i].rechargeRest + "</td></tr>";
							
					});
					$("#J-table-data>tbody").html(html);					
						
				}else{
					 $("#Pagination").hide();  
					 TableStyle("J-table-data>tbody",19,2,"没有相应数据");  
				}				
			}, 
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 TableStyle("J-table-data>tbody",19,2,"数据异常");  
			}
		});			
	}	
	//报表下载	
	$('#J-Download-Report').click( function () { 
		//$.get("/admin/Reporting/index?parma=dchdr",group.getFormData());
		var data = group.getFormData();
		var param='';
		var p='';
		for(p in data){
			if(data.hasOwnProperty(p)){
				param += '&'+p+'='+ data[p];
			}
		}
		window.open("/admin/Reporting/index?parma=dchdr"+param);
	});
	
	
})();	
</script>
{/literal}
</body>
</html>
	
	
	
	
	
	
	