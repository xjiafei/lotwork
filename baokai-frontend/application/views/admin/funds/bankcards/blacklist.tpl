	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; 银行卡黑名单管理</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<h3 class="ui-title">银行卡黑名单</h3>
						<!-- {if "FUND_BANKCARD_BLACKLIST_CREATE"|in_array:$smarty.session.datas.info.acls} -->
						<h3 class="ui-title"><a class="btn btn-small" style="float:left;" href="/admin/Bankcardsmanage/index?parma=opteradd">新增黑名单<b class="btn-inner"></b></a></h3>
						<!-- {/if} -->
								 <table  id="J-table-data" class="table table-info table-function">
									<thead>
										<tr>
											<th id="J-sp-BankCard" class="sp-td">
												<div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">银行卡号</div>
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
											<!-- <th id="J-sp-BankUserName" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">银行账户名</div>
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
											<th class="sp-td" id="J-sp-td-UserName">
												<div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-UserName">用户名</div>
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
											<th >
												<div class="sp-td-cont">
													<div class="sp-td-title">所属总代</div>
												</div>
											</th> -->
											<th id="J-sp-Daozhang-time" class="sp-td">												
                                                 <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">列入黑名单时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                  <li>
                                                    <div class="input-append">
                                                        <input type="text" tabindex="1" class="input w-2" id="sp-input-Daozhang-time-1"> - <input type="text" id="sp-input-Daozhang-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                    </div>
                                                   </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                                
											</th>
											<th>备注</th>
											<!-- {if "FUND_BANKCARD_BLACKLIST_DELETE"|in_array:$smarty.session.datas.info.acls} -->
											<th >
												<div class="sp-td-cont">
													<div class="sp-td-title">操作</div>
												</div>
											</th>
											<!-- {/if} -->
										</tr>
									</thead>
									 <tbody id="showInfo">
                                    </tbody>                                    
                                    <tfoot>
                                        <tr>
                                         <td colspan="22">
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
<script>
var isEnableDel = {if "FUND_BANKCARD_BLACKLIST_DELETE"|in_array:$smarty.session.datas.info.acls}1{else}0{/if};
</script>
{literal}
<script>
(function() {
	var isShowCell=false;	
	var msg = new phoenix.Message(),msg1 = new phoenix.Message(),mask = phoenix.Mask.getInstance();
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenubankNameManage');
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
	//BankCard
	var BankCard = new phoenix.SuperSearch({
		name: 'BankCard',
		keyCode: 'ctrl+66',
		 el: '#J-sp-BankCard',
		group: group
	});
	var UserName = new phoenix.SuperSearch({
		name: 'UserName',
		keyCode: 'ctrl+83',
		 el: '#J-sp-td-UserName',
		group: group
	});
	var BankUserName = new phoenix.SuperSearch({
		name: 'BankUserName',
		keyCode: 'ctrl+83',
		 el: '#J-sp-BankUserName',
		group: group
	});
	//银行到账时间
	var Daozhangtime = new phoenix.SuperSearch({
		name: 'Daozhangtime',
		keyCode: 'ctrl+68',
		type: 'time',
		el: '#J-sp-Daozhang-time',
		group: group,
		expands:{getFormValue:getTowInputValue}
	});
	var DaozhangTime1 = $('#sp-input-Daozhang-time-1'),DaozhangTime2 = $('#sp-input-Daozhang-time-2'),DaozhangDt1,DaozhangDt2;
	DaozhangTime1.click(function() {
		DaozhangDt1 = new phoenix.DatePicker({
			input: DaozhangTime1,
			isLockInputType: false
		});
		DaozhangDt1.show();
		DaozhangDt1.addEvent('afterSetValue',
		function() {
			DaozhangTime2.focus();
			DaozhangTime2.click();
		})
	});
	Daozhangtime.addEvent('afterFocus',
	function() {
		DaozhangTime1.click()
	});
	DaozhangTime2.click(function() {
		DaozhangDt2 = new phoenix.DatePicker({
			input: DaozhangTime2,
			isLockInputType: false
		}).show();
	});
	Daozhangtime.addEvent('afterBlur',function() {
		if (DaozhangDt1) {
			DaozhangDt1.hide();
		}
		if (DaozhangDt2) {
			DaozhangDt2.hide();
		}
	});
	SelectByWhereInfo("0");	
	//提交数据
	var dataArea = $('#showInfo');
	group.addEvent('dataChange', function(){
		SelectByWhereInfo("0");				
	});	
	
	function SelectByWhereInfo(pages) {
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
		
		 var dataArea = $('#J-table-data'),formData = group.getFormData(),mask = phoenix.Mask.getInstance();			
		 $("#J-table-data>tbody").html("");	
		 $("#Pagination").hide();  			
		 $.ajax({
			url:'/admin/Bankcardsmanage/index?parma=sv5',
			dataType:'json',
			method:'post',
			data:formData,
			beforeSend:function(){				
				$("#showInfo").html("<tr><td colspan=\"7\" style='height:120px;text-align:center;color:#009B7D; font-size:14px;font-weight:600;'>查询中...请稍候！</td></tr>");
				 TableStyle("showInfo",17,1,"查询中");
			},
			success:function(data){
				//debugger
				if(data.text){
					$("#Pagination").show();
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
							html += "<tr><td><a href='/admin/Bankcardsmanage/index?parma=sv42&type=2&userName=" + re[i].cardNumber + "'>" + re[i].cardNumber + "</a></td>";	
							//html += "<td>" + re[i].bankAcc + "</td>";
							//html += "<td>" + re[i].account + "</td>";
							//html += "<td>" + re[i].topAcc + "</td>";
							html += "<td>" + re[i].gmtCreated + "</td>";
							html += "<td>" + re[i].memo + "</td>";
							if(isEnableDel == 1){
								html += "<td><a href='javascript:void(0);' pro_id=" + re[i].id + " name='deleteOper'>删 除</a></td>";
							}
							html += "</tr>";
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
	
	//根据传参删除
	$(document).on('click', '[name="deleteOper"]', function(e){
		var pro_id = $(this).attr('pro_id'); 
		msg.show({
			mask: true,
			content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">确定从黑名单表要删除此银行卡?</h4></div></div>',
			confirmIsShow: true,
			confirmFun: function() {
				this.hide();
				if($.trim(pro_id) !=''){
					$.ajax({
						url:'/admin/Bankcardsmanage/index?parma=sv12',				
						dataType:'json',
						method:'post',
						data:'bankId='+pro_id,	
						beforeSend:function(){		
							mask.dom.addClass('admin-mask-loading');
							mask.css({opacity:.9,backgroundColor:'#FFF'});
							mask.show(dataArea);
							mask.dom.css({top:mask.dom.offset().top + 2,height:mask.dom.height() - 2});
						},
						success:function(datas){
							mask.hide();
							msg1.show({
								mask: true,
								content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+datas.data+'</h4></div></div>',
								confirmIsShow: true,
								confirmFun: function() {
									this.hide();
									if(datas.status =='ok'){	
										SelectByWhereInfo();
									}
								}
							});
						}				
					});	
				} else {
					msg1.show({
						mask: true,
						content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">删除失败请重试</h4></div></div>',
						confirmIsShow: true,
						confirmFun: function() {
							this.hide();
						}
					});
				}
			},
			cancelIsShow: true,
			cancelFun: function() {
				this.hide();
			}
		});
	});
})();	
</script>
{/literal}
</body>
</html>