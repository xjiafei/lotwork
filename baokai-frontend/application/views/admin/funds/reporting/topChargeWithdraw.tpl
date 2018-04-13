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
						<h3 class="ui-title">总代充提报表</h3>
						<form id="J-form" action="?">
						<ul class="ui-search">
							<li class="time"  id="J-sp-td-ResultMowTime">
								<label for="" class="ui-label">时间：</label>
                                <input type="text" tabindex="1" class="input w-2" id="sp-input-ResultMowTime-time-1" value="{$startDate}" name="ResultMowTime-time-1"> 至 <input type="text" id="sp-input-ResultMowTime-time-2" class="input w-2" value="{$endDate}" size="10" name="ResultMowTime-time-2">
							</li>
							<!-- <li class="name">
								<label class="ui-label">是否测试帐号：</label>
								<select class="ui-select">
									<option value="全部">全部</option>
									<option value="是">是</option>
									<option value="否">否</option>
								</select>
							</li>
							<li class="name">
								<label class="ui-label">是否冻结帐号：</label>
								<select class="ui-select">
									<option value="全部">全部</option>
									<option value="是">是</option>
									<option value="否">否</option>
								</select>
							</li> -->
							<li><a href="javascript:void(0);" id="J-button-submit" class="btn btn-important">搜 索<b class="btn-inner"></b></a></li>
							<!-- <li><a class="btn btn-small" style="float:left;"  href="javascript:void(0);" id="J-Download-Report">下载报表<b class="btn-inner"></b></a></li> -->
						</ul>
					</form>
						<table id="J-table-data" class="table table-info text-center">
							<thead>
								<tr>
									<th rowspan="3">总代</th>
									<th rowspan="3">所属组</th>
									<th colspan="5">游戏币</th>
									<th colspan="5">现金-钱</th>
									<th colspan="3">现金充提合计</th>
								</tr>
								<tr>
									<th colspan="3">人工加币</th>
									<th rowspan="2">人工扣币</th>
									<th rowspan="2">充值让利</th>
									<th rowspan="2">客户充值（客户在线申请）</th>
									<th rowspan="2">人工充值</th>
									<th rowspan="2">客户提现（客户在线申请）</th>
									<th rowspan="2">人工提现</th>
									<th rowspan="2">人工打款</th>
									<th rowspan="2">充值金额</th>
									<th rowspan="2">提现金额</th>
									<th rowspan="2">充提结余</th>
								</tr>
								<tr>
									<th>非活动非奖励</th>
									<th>活动类</th>
									<th>奖励类</th>
								</tr>
							</thead>
							<tbody  id="showInfo">
								
							</tbody>
							<tfoot>
                                    <tr>
                                        <td colspan="15">
                                              <div id="Pagination" class="pagination"></div>
                                        </td>
                                    </tr>
                                </tfoot>
						</table>		  
								
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
	selectMenu('Menufunds','MenuGeneration');	
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
	
	
	
	//mow返回结果时间
	var ResultMowTime = new phoenix.SuperSearch({
		name: 'ResultMowTime',		
		type: 'time',
		el: '#J-sp-td-ResultMowTime',
		group: group,
		expands:{getFormValue:getTowInputValue}
	}); 
	
	var ResultMowTimeTime1 = $('#sp-input-ResultMowTime-time-1'),ResultMowTimeTime2 = $('#sp-input-ResultMowTime-time-2'),ResultMowTimeDt1,ResultMowTimeDt2;
	ResultMowTimeTime1.click(function() {
		ResultMowTimeDt1 = new phoenix.DatePicker({
			input: ResultMowTimeTime1,
			isLockInputType: false
		});
		ResultMowTimeDt1.show();
		ResultMowTimeDt1.addEvent('afterSetValue',
		function() {
			ResultMowTimeTime2.focus();
			ResultMowTimeTime2.click();
		})
	});
	ResultMowTimeTime2.click(function() {
		ResultMowTimeDt2 = new phoenix.DatePicker({
			input: ResultMowTimeTime2,
			isLockInputType: false
		}).show();
	});
	
	
	
	$('#J-button-submit').click(function(){
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
		$("#Pagination").hide();    
		$("#J-table-data>tbody").html("");				
		$.ajax({
			url:'/admin/Reporting/index?parma=veda',
			dataType:'json',
			method:'post',
			data: group.getFormData(),
			beforeSend:function(){	
				TableStyle("showInfo",15,1,"查询中");
			},
			success:function(data){				
				if(data.text.length>0){					
					//$("#Pagination").show();	
					$('.main').css('overflow-x', 'auto');					  
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
							html += "<tr><td>" + re[i].account + "</td>";	
							html += "<td>" + re[i].userLvl + "</td>";
							html += "<td>" + re[i].game1 + "</td>";
							html += "<td>" + re[i].game2 + "</td>";
							html += "<td>" + re[i].game3 + "</td>";
							html += "<td>" + re[i].game4 + "</td>";
							html += "<td>" + re[i].game5 + "</td>";
							html += "<td>" + re[i].money1 + "</td>";
							html += "<td>" + re[i].money2 + "</td>";
							html += "<td>" + re[i].money3 + "</td>";
							html += "<td>" + re[i].money4 + "</td>";
							html += "<td>" + re[i].money5 + "</td>";
							html += "<td>" + re[i].charge + "</td>";
							html += "<td>" + re[i].withdraw + "</td>";
							html += "<td>" + re[i].chargewith + "</td></tr>";
					});
					$("#J-table-data>tbody").html(html);					
						
				}else{
					 $("#Pagination").hide();      
					TableStyle("J-table-data>tbody",15,2,"没有相应数据");
				}				
			}, 
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 TableStyle("J-table-data>tbody",15,2,"数据异常");  
			}
		});			
	}	
	
})();	
</script>
{/literal}
</body>
</html>
