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
					<form id="J-form" action="?">
						<ul class="ui-search">
							<li class="time" id="J-sp-td-SearchTime">
								<label for="" class="ui-label">时间：</label>
								<input id="J-search-time1" type="text" value="{$searchTime1}" class="input"> 至 <input id="J-search-time2" type="text" value="{$searchTime2}" class="input">
							</li>
							<li class="name" id="J-sp-username">
								<label class="ui-label">用户名：</label>
								<input type="text" value="请输入您的用户名" id="J-search-username" class="input">
							</li>
							<li class="name">
								<label class="ui-label">用户组：</label>
								 <select class="ui-select" id="usergroup">
										<option value="" {if $usergroup eq ''}selected{/if}>全部客户</option>
										<!-- {foreach from=$aUserGroup key=key item=data} -->
										<option value="{$key}" {if $usergroup eq $key}selected{/if}>{$data}</option>
										<!-- {/foreach} -->
									</select>
							</li>
							<li  style="clear:both" class="name">
								<label class="ui-label">游戏名称：</label>
								<select class="ui-select" id="userGameGroup">
									<option value="">所有</option>
									<!-- {foreach from=$aAwardGroup key=key item=data} -->
										<option value="{$key}" {if $usergroup eq $key}selected{/if}>{$data}</option>
										<!-- {/foreach} -->
								</select>
							</li>
							<li class="name" >
								<label class="ui-label">游戏玩法：</label>
								<select class="ui-select" id="userPlayMethod" disabled="disabled">
									<option value="">所有</option>
								</select>
							</li>
							<li class="name">
								<label class="ui-label" id="user">冻结帐号：</label>
								<select class="ui-select" id="userFreeze">
									<option value="">所有</option>
									<option value="1">是</option>
									<option value="2">否</option>
								</select>
							</li>
							<li class="name">
								<label class="ui-label">模式：</label>
								<select class="ui-select" id="userUnit">
									<option value="">所有</option>
									<option value="1">元</option>
									<option value="2">角</option>
									<option value="3">分</option>
								</select>
							</li>
							<li><a href="javascript:void(0);" id="J-button-submit" class="btn btn-important">搜 索<b class="btn-inner"></b></a></li>
						</ul>
					</form>
								<table id="J-table-data" class="table table-info text-center">
									<thead>
										<tr>
											<th>用户名</th>
											<th>用户组</th>
											<th>总代购费</th>
											<th>总返点</th>
											<th>实际总代购费用</th>
											<th>中奖金额</th>
											<th>活动礼金</th>
											<th>总结算</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody id="showInfo">
                                </tbody>                                
                                <tfoot>
                                    <tr>
                                        <td colspan="8">
                                              <!--<div id="Pagination" class="pagination"></div>-->
											  <div id="showMore" class="showMore">
												<input type="button" value="顯示更多" id="showBtn" class="showBtn" style="display: none;"></input>
											  </div>
											  <div id="subShowMore" class="subShowMore">
												<input type="button" value="顯示更多" id="subShowBtn" class="subShowBtn" style="display: none;"></input>
											  </div>
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
	//获取select类型值
	var getSelectValue = function(){
		var me = this,v = '',result = {};
		v = $(me.dom).val();
		result[me.name] = v;
		return result;
	};
	var group = new phoenix.SuperSearchGroup();	
	
	 var UserGroup = new phoenix.SuperSearch({
		name: 'userGroup',		
		type: 'select',
		isAutoWidth: true,
		el: '#usergroup',
		group: group,
		expands:{getFormValue:getSelectValue}
	});
	//状态
	 var userPlayMethod = new phoenix.SuperSearch({
		name: 'userPlayMethod',		
		type: 'select',
		isAutoWidth: true,
		el: '#userPlayMethod',
		group: group,
		expands:{getFormValue:getSelectValue}
	});
	 var userGameGroup = new phoenix.SuperSearch({
		name: 'userGameGroup',		
		type: 'select',
		isAutoWidth: true,
		el: '#userGameGroup',
		group: group,
		expands:{getFormValue:getSelectValue}
	});
	 var userFreeze = new phoenix.SuperSearch({
		name: 'userFreeze',		
		type: 'select',
		isAutoWidth: true,
		el: '#userFreeze',
		group: group,
		expands:{getFormValue:getSelectValue}
	});
	 var userUnit = new phoenix.SuperSearch({
		name: 'userUnit',		
		type: 'select',
		isAutoWidth: true,
		el: '#userUnit',
		group: group,
		expands:{getFormValue:getSelectValue}
	});
	//用户名
	var UserName = new phoenix.SuperSearch({
		name: 'username',		
		el: '#J-sp-username',
		group: group,
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
	
	$('#userGameGroup').change(function(){
		var groupValue=$(this).val();
		if(groupValue <=0){
			$('#userPlayMethod').val('');
			$('#userPlayMethod').attr('disabled','disabled');
			return;
		} else {
			$('#userPlayMethod').removeAttr('disabled');
		}
		$.ajax({             
			url: "/admin/Reporting/index?parma=gtpm&id="+groupValue,        
			type: "POST",        
			dataType: "json",
			success: function (jdata, stat){
				$('#userPlayMethod').html("");
				$('#userPlayMethod').append("<option value=''>所有</option>");
				if(jdata['isSuccess'] == '0'){
					$('#userPlayMethod').attr('disabled','disabled');
					return;
				}
	            $.each(jdata['data'],function(i,item){
	                $('#userPlayMethod').append("<option value="+item.gameBetType+">"+item.gameBetTypeShow+"</option>");
	            });
			} 
		});
	});
	
	//SelectByWhereInfo("0");
	TableStyle("J-table-data>tbody",8,2,"請輸入查詢條件");
	var  per_page_number = 30;
	var currentPages = 0;
	var totalPages = 0;
	var html = "";
	var subHtml = "";
	var time1 = "";
	var time2 = "";
	function SelectByWhereInfo(pages) {
		$("#showBtn").hide();
		$("#subShowBtn").hide();
		//当前页数据行数
		currentPages = 0;
		html="";
		time1 = "";
		time2 = "";
		var  per_page_number = 30;		
		//放入group对象中(当前页)		
		var page  = new phoenix.SuperSearch({
			name: 'page',    		
			group: group,			
			expands:{getValue:function(){
				return pages;	
			}}
		});
		//放入group对象中(当前页行数 )	
		var perPageNum  = new phoenix.SuperSearch({
			name: 'perPageNum', 
			group: group,	
			expands:{getValue:function(){
				return per_page_number;	
			}}
		});
		var formInfo = group.getFormData();
		
		time1 = $('#J-search-time1').val();
		time2 = $('#J-search-time2').val();
		$("#J-table-data>tbody").html("");				
		$.ajax({
			url:'/admin/Reporting/index?parma=tada',
			dataType:'json',
			method:'post',
			data: group.getFormData(),
			beforeSend:function(){	
				TableStyle("showInfo",8,1,"查询中");
			},
			
			success:function(data){				
				if(data.text.length>0){					
					$('.main').css('overflow-x', 'auto');					  
					var resultAll = eval(data);               
					var re = resultAll.text;
					var recordNum = 0;
					recordNum = resultAll.count[0].recordNum;	
					//$("#Pagination").show();
					if(recordNum > per_page_number){
						$("#showBtn").show();
						$("#showBtn").attr("disabled", false);
						currentPages++;
						totalPages = Math.ceil(recordNum/per_page_number);
					}
					if(formInfo["username"] != '请输入您的用户名'){
						$("#showBtn").hide();
					}
					//分页回调				 
					/*$("#Pagination").pagination(recordNum, {
						num_edge_entries: 2,
						num_display_entries: 8,
						current_page: pages,
						items_per_page: per_page_number,
						callback: SelectByWhereInfo1
					});*/
					html = "";
					$.each(re, function (i) {	
							html += "<tr><td>" + re[i].account + "</td>";	
							html += "<td>" + re[i].usergroup + "</td>";
							html += "<td>" + re[i].bet + "</td>";
							html += "<td>" + re[i].ret + "</td>";
							html += "<td>" + re[i].trueBet + "</td>";
							html += "<td>" + re[i].win + "</td>";
							html += "<td>" + re[i].activityGifts + "</td>";
							html += "<td>" + re[i].result + "</td>";
							html += "<td><a href='javascript:void(0);' pre_id='"+re[i].id+"' class='getPlayMethod'>游戏明细</a>";	
							if(re[i].userLvl >=0){
								html += "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' pre_id='"+re[i].id+"' class='getSubAgent'>查看下级</a>";
							}
							html += "</td></tr>";
					});
					$("#J-table-data>tbody").html(html);					
					$('.getSubAgent').click(function(){
						var uId = $(this).attr('pre_id');
						SelectByWhereInfo1('0',uId);
					});
					$('.getPlayMethod').click(function(){
						var uId = $(this).attr('pre_id');
						LoadPlayMethod(uId);
					});
				}else{
				   
					// $("#Pagination").hide();  
					$("#showBtn").hide();					
					TableStyle("J-table-data>tbody",8,2,"没有相应数据");
				}				
			}, 
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 TableStyle("J-table-data>tbody",8,2,"数据异常");  
			}
		});			
	}
	
	$('#showBtn').click(function() {
		$.ajax({
			url:'/admin/Reporting/index?parma=tada&SearchTime1='+time1+"&SearchTime2="+time2+"&page="+currentPages+"&perPageNum="+per_page_number,
			dataType:'json',
			method:'get',
			success:function(data){
				if(data.text.length>0){
					$('.main').css('overflow-x', 'auto');					  
					var resultAll = eval(data);               
					var re = resultAll.text;
					var recordNum = 0;
					recordNum = resultAll.count[0].recordNum;
					$.each(re, function (i) {
							html += "<tr><td>" + re[i].account + "</td>";	
							html += "<td>" + re[i].usergroup + "</td>";
							html += "<td>" + re[i].bet + "</td>";
							html += "<td>" + re[i].ret + "</td>";
							html += "<td>" + re[i].trueBet + "</td>";
							html += "<td>" + re[i].win + "</td>";
							html += "<td>" + re[i].activityGifts + "</td>";
							html += "<td>" + re[i].result + "</td>";
							html += "<td><a href='javascript:void(0);' pre_id='"+re[i].id+"' class='getPlayMethod'>游戏明细</a>";	
							if(re[i].userLvl >=0){
								html += "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' pre_id='"+re[i].id+"' class='getSubAgent'>查看下级</a>";
							}
							html += "</td></tr>";
					});
					$("#J-table-data>tbody").html(html);
					$('.getSubAgent').click(function(){
						var uId = $(this).attr('pre_id');
						SelectByWhereInfo1('0',uId);
					});
					$('.getPlayMethod').click(function(){
						var uId = $(this).attr('pre_id');
						LoadPlayMethod(uId);
					});					
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 TableStyle("J-table-data>tbody",8,2,"数据异常");  
			}
		});
		
		if(currentPages == (totalPages-1)){
			$('#showBtn').attr("disabled",true);
			return;
		}
		currentPages++;
	});
	var subUserId;
	function SelectByWhereInfo1(pages,uId) {
		subUserId = uId;
		//当前页数据行数
		$("#showBtn").hide();
		var  per_page_number = 30;	
		//当前页数据行数
		currentPages = 0;
		html="";
		time1 = "";
		time2 = "";		
		//放入group对象中(当前页)		
		var page  = new phoenix.SuperSearch({
			name: 'page',    		
			group: group,			
			expands:{getValue:function(){
				return pages;	
			}}
		});
	
		//id
		var id  = new phoenix.SuperSearch({
			name: 'id',    		
			group: group,			
			expands:{getValue:function(){
				return uId;	
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
		
		time1 = $('#J-search-time1').val();
		time2 = $('#J-search-time2').val();
		
		$("#Pagination").hide();    
		//$(".ui-search").hide();
		$("#J-table-data>tbody").html("");		
		$.ajax({
			url:'/admin/Reporting/index?parma=sada',
			dataType:'json',
			method:'post',
			data: group.getFormData(),
			beforeSend:function(){	
				TableStyle("showInfo",8,1,"查询中");
			},
			success:function(data){
				var size = data.text.length;
				
				if(data.text.length>0){	
							
					//$("#Pagination").show();	
					$('.main').css('overflow-x', 'auto');					  
					var resultAll = eval(data);               
					var re = resultAll.text;
					var recordNum = 0;
					recordNum = resultAll.count[0].recordNum;
					
					if(recordNum > per_page_number){
						$("#subShowBtn").show();
						$("#subShowBtn").attr("disabled", false);
						currentPages++;
						totalPages = Math.ceil(recordNum/per_page_number);
						
					}else{
						$("#subShowBtn").hide();
					}
					//分页回调				 
					/*$("#Pagination").pagination(recordNum, {
						num_edge_entries: 2,
						num_display_entries: 8,
						current_page: pages,
						items_per_page: per_page_number,
						callback: SelectByWhereInfo1
					});*/
					subHtml = "";
					$.each(re, function (i) {
							if(resultAll.param.id == re[i].id){
								subHtml += "<tr style='color:red'>";
							} else {
								subHtml += "<tr>";
							}
							subHtml += "<td>" + re[i].account + "</td>";	
							subHtml += "<td>" + re[i].usergroup + "</td>";
							subHtml += "<td>" + re[i].bet + "</td>";
							subHtml += "<td>" + re[i].ret + "</td>";
							subHtml += "<td>" + re[i].trueBet + "</td>";
							subHtml += "<td>" + re[i].win + "</td>";
							subHtml += "<td>" + re[i].activityGifts + "</td>";
							subHtml += "<td>" + re[i].result + "</td>";
							subHtml += "<td><a href='javascript:void(0);' pre_id='"+re[i].id+"' class='getPlayMethod'>游戏明细</a>";	
							if(re[i].userLvl >=0 && resultAll.param.id != re[i].id){
								subHtml += "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' pre_id='"+re[i].id+"' class='getSubAgent'>查看下级</a>";
							}
							subHtml += "</td></tr>";
					});
					$("#J-table-data>tbody").html(subHtml);					
					
					$('.getSubAgent').click(function(){
						var uId = $(this).attr('pre_id');
						SelectByWhereInfo1('0',uId);
					});
					$('.getPlayMethod').click(function(){
						var uId = $(this).attr('pre_id');
						LoadPlayMethod(uId);
					});
				}else{
					//$("#Pagination").hide();
					$("#subShowBtn").hide();						
					TableStyle("J-table-data>tbody",8,2,"没有相应数据");
				}			
			}, 
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 TableStyle("J-table-data>tbody",8,2,"数据异常");  
			}
		});			
	}
	
	$('#subShowBtn').click(function() {
		//post
		
		
		per_page_number = 30;
		
		$.ajax({
			url:'/admin/Reporting/index?parma=sada&SearchTime1='+time1+"&SearchTime2="+time2+"&page="+currentPages+"&perPageNum="+per_page_number+"&id="+subUserId,
			dataType:'json',
			method:'get',
			success:function(data){
				if(data.text.length>0){
					$('.main').css('overflow-x', 'auto');					  
					var resultAll = eval(data);               
					var re = resultAll.text;
					var recordNum = 0;
					recordNum = resultAll.count[0].recordNum;
					$.each(re, function (i) {
							subHtml += "<tr><td>" + re[i].account + "</td>";	
							subHtml += "<td>" + re[i].usergroup + "</td>";
							subHtml += "<td>" + re[i].bet + "</td>";
							subHtml += "<td>" + re[i].ret + "</td>";
							subHtml += "<td>" + re[i].trueBet + "</td>";
							subHtml += "<td>" + re[i].win + "</td>";
							subHtml += "<td>" + re[i].activityGifts + "</td>";
							subHtml += "<td>" + re[i].result + "</td>";
							subHtml += "<td><a href='javascript:void(0);' pre_id='"+re[i].id+"' class='getPlayMethod'>游戏明细</a>";	
							if(re[i].userLvl >=0){
								subHtml += "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' pre_id='"+re[i].id+"' class='getSubAgent'>查看下级</a>";
							}
							subHtml += "</td></tr>";
					});
					$("#J-table-data>tbody").html(subHtml);
					$('.getSubAgent').click(function(){
						var uId = $(this).attr('pre_id');
						SelectByWhereInfo1('0',uId);
					});
					$('.getPlayMethod').click(function(){
						var uId = $(this).attr('pre_id');
						LoadPlayMethod(uId);
					});					
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 TableStyle("J-table-data>tbody",8,2,"数据异常");  
			}
		});
		
		if(currentPages == (totalPages-1)){
			$('#subShowBtn').attr("disabled",true);
			return;
		}
		currentPages++;
	});
	
	function LoadPlayMethod(uId){
		var data = group.getFormData();
		var param='';
		param +='&userGameGroup='+data['userGameGroup'];
		param +='&userUnit='+data['userUnit'];
		param +='&SearchTime1='+data['SearchTime1'];
		param +='&SearchTime2='+data['SearchTime2'];
		param += '&id='+uId;
		window.open("/admin/Reporting/index?parma=sv58"+param);
	}
	
})();	
</script>
{/literal}
</body>
</html>
