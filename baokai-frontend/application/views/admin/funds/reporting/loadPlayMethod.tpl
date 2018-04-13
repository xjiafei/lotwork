	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main sale-game-details">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt;<a href="/admin/Reporting/index?parma=sv56">总代盈亏报表</a> &gt; {$title}</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
					<form id="J-form" action="?">
						<ul class="ui-search">
							<input type="hidden" id='uId' value="{$_POST.id}"/>
							<li class="time" id="J-sp-td-SearchTime">
								<label for="" class="ui-label">时间：</label>
								<input id="J-search-time1" type="text" value="{$_POST.SearchTime1}" class="input"> 至 <input id="J-search-time2" type="text" value="{$_POST.SearchTime2}" class="input">
							</li>
							<li class="name">
								<label class="ui-label">彩种：</label>
								<select class="ui-select" id="userGameGroup">
									<!-- {foreach from=$aAwardGroup key=key item=data} -->
										<option value="{$key}" {if $_POST.userGameGroup eq $key}selected{/if}>{$data}</option>
										<!-- {/foreach} -->
								</select>
							</li>
							<li class="name">
								<label class="ui-label">模式：</label>
								<select class="ui-select" id="userUnit">
									<option value="">所有</option>
									<option value="1" {if $_POST.userUnit eq '1'}selected{/if}>元</option>
									<option value="2" {if $_POST.userUnit eq '2'}selected{/if}>角</option>
									<option value="3" {if $_POST.userUnit eq '3'}selected{/if}>分</option>
								</select>
							</li>
							<li><a href="javascript:void(0);" id="J-button-submit" class="btn btn-important">搜 索<b class="btn-inner"></b></a></li>
						</ul>
					</form>
								<table id="J-table-data" class="table table-info">
									<thead>
										<tr>
											<th>游戏</th>
											<th>玩法组</th>
											<th>玩法</th>
											<th>总代购费</th>
											<th>实际总代购费</th>
											<th>中奖金额</th>
											<th>总结算</th>
										</tr>
									</thead>
									<tbody id="showInfo">
                                </tbody>                                
                                <tfoot>
                                    <tr>
                                        <td colspan="7">
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
		</div>
	</div>
{include file='/admin/script-base.tpl'}
{literal}	
<script>
(function() {
	var isture=false;	
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuProfitLoss');
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
	$('#J-search-username').focus(function(){	
		if($('#J-search-username')[0].value=='请输入您的用户名'){ 
			$("#J-search-username")[0].value='';
		}	
		}).blur(function(){
		var v = $.trim(this.value);
		if(v == ''){
			$("#J-search-username")[0].value='请输入您的用户名';			
		}else{
			isture=true;
		}	
	});
	//获取两个input值
	var getTowInputValue = function(){
		var me = this,ipts = me.dom.find('input[type="text"]'),name = me.name,result = {};
		result[name+'1'] = ipts[0].value;
		result[name+'2'] = ipts[1].value;
		return result;
	};
	var getInputValue = function(){
		var me = this,ipts = me.dom,name = me.name,result = {};
		result[name] = ipts.value;
		return result;
	};
	//获取select类型值
	var getSelectValue = function(){
		var me = this,v = '',result = {};
		v = $(me.dom).val();
		result[me.name] = v;
		return result;
	};
	var group = new phoenix.SuperSearchGroup();	
	
	 var userGameGroup = new phoenix.SuperSearch({
		name: 'userGameGroup',		
		type: 'select',
		isAutoWidth: true,
		el: '#userGameGroup',
		group: group,
		expands:{getFormValue:getSelectValue}
	});
	 var uId = new phoenix.SuperSearch({
			name: 'id',		
			group: group,
			expands:{getValue:function(){
				return $('#uId').val();
			}}
	});
	 var userUnit = new phoenix.SuperSearch({
		name: 'userUnit',		
		type: 'select',
		isAutoWidth: true,
		el: '#userUnit',
		group: group,
		expands:{getFormValue:getSelectValue}
	});
	var SearchTime = new phoenix.SuperSearch({
		name: 'SearchTime',		
		type: 'time',
		el: '#J-sp-td-SearchTime',
		group: group,
		expands:{getFormValue:getTowInputValue}
	}); 
	
	var SearchTime1 = $('#J-search-time1'),SearchTime2 = $('#J-search-time2'),ResultMowTimeDt1,ResultMowTimeDt2;
	SearchTime1.click(function() {
		ResultMowTimeDt1 = new phoenix.DatePicker({
			input: SearchTime1,
			isLockInputType: false
		});
		ResultMowTimeDt1.show();
		ResultMowTimeDt1.addEvent('afterSetValue',
		function() {
			SearchTime2.focus();
			SearchTime2.click();
		})
	});
	SearchTime2.click(function() {
		ResultMowTimeDt2 = new phoenix.DatePicker({
			input: SearchTime2,
			isLockInputType: false
		}).show();
	});
	$('#J-button-submit').click(function(){
		SelectByWhereInfo("0");
	});
	
	SelectByWhereInfo("0");
	function SelectByWhereInfo(pages) {		
		$("#Pagination").hide();    
		$("#J-table-data>tbody").html("");				
		$.ajax({
			url:'/admin/Reporting/index?parma=loadpm',
			dataType:'json',
			method:'post',
			data: group.getFormData(),
			beforeSend:function(){	
				TableStyle("showInfo",7,1,"查询中");
			},
			success:function(data){				
				if(data.text.length>0){					
					$('.main').css('overflow-x', 'auto');					  
					var resultAll = eval(data);               
					var re = resultAll.text;
					var html = "";
					$.each(re, function (i) {
						$.each(re[i]['sets'], function (j) {
							$.each(re[i]['sets'][j]['details'], function (k) {
								html += "<tr align='center'>";
								if(j==0 && k==0){
									html += "<td rowspan='"+re[i].cnt+"' style='width:150px;'><b>" + re[i].groupCodeName + "</b></td>";	
								}
								if(k==0){
									html += "<td rowspan='"+re[i]['sets'][j].cnt+"' style='width:150px;'><b>" + re[i]['sets'][j].setCodeName + "</b></td>";	
								}
								html += "<td>" + re[i]['sets'][j]['details'][k].methodCodeName + "</td>";	
								html += "<td height='37' >" + re[i]['sets'][j]['details'][k].bet + "</td>";
								html += "<td>" + re[i]['sets'][j]['details'][k].trueBet + "</td>";
								html += "<td>" + re[i]['sets'][j]['details'][k].win + "</td>";
								html += "<td>" + re[i]['sets'][j]['details'][k].result + "</td>";
								html += "</tr>";
							});
						});
					});
					$("#J-table-data>tbody").html(html);					
				}else{
					 $("#Pagination").hide();    
					TableStyle("J-table-data>tbody",7,2,"没有相应数据");
				}				
			}, 
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 TableStyle("J-table-data>tbody",7,2,"数据异常");  
			}
		});			
	}	
	
})();	
</script>
{/literal}
</body>
</html>
