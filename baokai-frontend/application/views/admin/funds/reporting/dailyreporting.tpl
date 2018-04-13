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
							<h3 class="ui-title">{$title}</h3>
							<table id="J-table-data" class="table table-info table-function text-center ">
								<thead>
									<tr>
										<th rowspan="2" id="J-sp-username" class="sp-td">
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
										<th rowspan="2">所属组</th>
										<th colspan="4">游戏币明细（团队）</th>
										<th colspan="4">游戏币明细（自身）</th>
									</tr>
									<tr>
										<!-- <th>游戏币余额(A)</th>
										<th>信用欠款(A)</th> -->
										<th id="J-game-group-balance" class="sp-td">
	                                        <div class="sp-td-cont sp-td-cont-b">
	                                            <div class="sp-td-title">账户余额(A)</div>
	                                            <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
	                                          <li>
	                                                    <div class="input-append">
	                                                        <input type="text" tabindex="1" class="input w-2" id="sp-input-group-balance-1"> - <input type="text" id="sp-input-group-balance-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
	                                                    </div>
	                                              </li>
	                                            </ul>
	                                            <span class="sp-filter-close"></span>
	                                        </div>
	                                    </th>
										<th>冻结金额(A)</th>
										<th>可用余额(A)</th>
										<th>备注1</th>
										<!-- <th>游戏币余额(B)</th>
										<th>信用欠款(B)</th> -->
										<th id="J-game-myself-balance" class="sp-td">
	                                        <div class="sp-td-cont sp-td-cont-b">
	                                            <div class="sp-td-title">账户余额(B)</div>
	                                            <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
	                                          <li>
	                                                    <div class="input-append">
	                                                        <input type="text" tabindex="1" class="input w-2" id="sp-input-myself-balance-1"> - <input type="text" id="sp-input-myself-balance-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
	                                                    </div>
	                                              </li>
	                                            </ul>
	                                            <span class="sp-filter-close"></span>
	                                        </div>
	                                    </th>
										<th>冻结金额(B)</th>
										<th>可用余额(B)</th>
										<th>备注2</th>
									</tr>
								</thead>
								<tbody id="showInfo">
								</tbody>
							</table>
							
						</div>
					</div>
				</div>
				</div>
			</div>
		</div>
	</div>
</body>
{include file='/admin/script-base.tpl'}
{literal}
<script>
(function($){
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuGenerationDetail');	
    var inputTimeStart = $('#timeStart'),
		inputTimeEnd = $('#timeEnd');

	$('#J-button-submit').click(function(){
		$('#J-form').submit();
	});

	var isShowCell=false,group = new phoenix.SuperSearchGroup();
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
		}else{
			//初始化给空
			result[me.name] = '';
		}
		return result;
	};
	
	AllData();

	function AllData(){
		
		//团队账户余额
		var Daozhang5time = new phoenix.SuperSearch({
			name: 'groupAcountBalance',
			keyCode: 'ctrl+68',
			el: '#J-game-group-balance',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		$('#sp-input-group-balance-1').keyup(allowNumber).blur(allowNumber);
		$('#sp-input-group-balance-2').keyup(allowNumber).blur(allowNumber);

		//自身账户余额
		var Daozhang5time = new phoenix.SuperSearch({
			name: 'userAcountBalance',
			keyCode: 'ctrl+69',
			el: '#J-game-myself-balance',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		$('#sp-input-myself-balance-1').keyup(allowNumber).blur(allowNumber);
		$('#sp-input-myself-balance-2').keyup(allowNumber).blur(allowNumber);

		//用户名
		var username = new phoenix.SuperSearch({
			name: 'username',
			keyCode: 'ctrl+83',
			el: '#J-sp-username',
			group: group
		});

	}

	SelectByWhereInfo("0");	
	//提交数据
	group.removeEvent('dataChange');
	group.addEvent('dataChange', function(){
		SelectByWhereInfo("0");				
	});	
	
	function SelectByWhereInfo(pages) {
		//当前页数据行数
		 var  per_page_number = 10;		
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
		
		 var dataArea = $('[name="DivRules"]'),formData = group.getFormData(),mask = phoenix.Mask.getInstance();			
		 $("#J-table-data>tbody").html("");	
		 $("#Pagination").hide();		
		 $.ajax({
			url:'/admin/Reporting/index?parma=toda',				
			method:'post',
			data:formData,
			beforeSend:function(){		
			     isLock=false;		
				 TableStyle("showInfo",12,1,"查询中");
			},
			success:function(data){
				//返回data的json格式
				
				if(data.text.length>0){
					var re = data.text;						
					var recordNum = 0;
					recordNum = data.count[0].recordNum;
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
							html += "<tr><td>" + re[i].account + "</td>";
							html += "<td>" + re[i].userLvl + "</td>";
							html += "<td>" + re[i].sumBal + "</td>";
							html += "<td>" + re[i].sumFrozen + "</td>";
							html += "<td>" + re[i].sumUseMoney + "</td>";		
							html += "<td>" + re[i].memo1 + "</td>";							
							html += "<td>" + re[i].bal + "</td>";
							html += "<td>" + re[i].frozenAmt + "</td>";
							html += "<td>" + re[i].useMoney + "</td>";							
							html += "<td>" + re[i].memo2 + "</td></tr>";
													
					});
					$("#J-table-data>tbody").html(html);
					if(isShowCell == false){//状态判定加载相关数据		
						$('[name="showType"]').hide();
					}
					else{
						$('[name="showType"]').show();
					}
						
				}else{
					 $("#Pagination").hide();  
					 TableStyle("J-table-data>tbody",12,2,"没有相应数据");
				}				
			}, 
			complete: function()
			{
				isLock=true;
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 TableStyle("J-table-data>tbody",12,2,"数据异常");
			}
		});			
	}
})(jQuery);	
</script>
{/literal}
</html>