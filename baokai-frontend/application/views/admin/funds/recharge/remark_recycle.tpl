	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/index">资金中心</a> &gt; <span id="titleName">唯一附言管理</span></div></div>
			<div class="col-content">
				<div class="col-main">
						<div class="main">
						<div class="ui-tab-title clearfix">
							<ul>
								<!-- {if "FUND_RECHARGE_REMARK_MANAGER"|in_array:$smarty.session.datas.info.acls} -->
									<li>唯一附言管理</li>
								<!-- {/if} -->
								<!-- {if "FUND_RECHARGE_REMARK_RECYCLE"|in_array:$smarty.session.datas.info.acls} -->
									<li class="current">唯一附言回收管理</li>
								<!-- {/if} -->
								<!-- {if "FUND_RECHARGE_REMARK_SETTING"|in_array:$smarty.session.datas.info.acls} -->
									<li>配置管理</li>
								<!-- {/if} -->
							</ul>
						</div>
						<h3 class="ui-title batch-operation">
							<!-- <span class="batch">批量操作</span> -->
							<!-- <a href="#" class="recover-btn btn btn-small">回收<b class="btn-inner"></b></a> -->
							<a href="/admin/Remark/index?parma=sv7&pageSize={$total}" target="_blank" class="report-download btn btn-small">报表下载<b class="btn-inner"></b></a>
						</h3>
								<table class="table table-info table-function info-list" id="J-table-data">
									<thead>
										<tr>
											<!-- <th id="J-select-all">反选</th> -->
											<th id="J-sp-remark" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">唯一附言</div>
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
											<th id="J-sp-serial" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">最后绑定用户</div>
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
											<th id="J-sp-bind-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">解绑时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                  <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-bindDate-time-1"> - <input type="text" id="sp-input-bindDate-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner" style="z-index:-1"></b></a>
                                                            </div>
                                                      </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody  id="showInfo">
									</tbody>
									<tfoot>
                                        <tr>
                                         <td colspan="4">
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
{include file='/admin/script-base.tpl'}
{literal}
<script>
(function($){
	var message = new phoenix.Message,
		selectAllDom = $('#J-select-all'),
		batchReBind = $('.batch-operation .recover-btn'),
		batchReport = $('.batch-operation .report-download'),
		checkBoxDom = $('input[name="annex"]');
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuRemark');
	
	$('.ui-tab-title ul li').click(function(){
		var indexs = $(this).index();
		if($(this).attr('class') !='current'){
			window.location.href="/admin/Remark/index?parma=sv"+(parseInt(indexs)+1);
		}
	});
	//根据type类型来进行判断
	//bind为绑定
	//bind-user为用户绑定
	//默认为绑定
	countCheckedNum = function(type) {
		var count = 0,
			type = 'recover-check',
			dom = $('.info-list' +' .'+type);

		$.each(dom, function(index, val) {
			var result = $(this).prop('checked');

			if(result) {
				count++;
			}
		});

		return count || -1;
	}

	//获取用户ID
	getUserId = function() {
		var saveArray =  [],
			type = 'recover-check',
			dom = $('.info-list' +' .'+ type);

		$.each(dom, function(index, val) {
			var result = $(this).prop('checked');

			if(result) {
				saveArray.push($(this).attr('data-user'));
			}
		});

		return saveArray;
	}

	//回收
	batchReBind.on('click', function() {
		var count = countCheckedNum();

		if(count != -1) {

			message.show({
				mask: true,
				content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">确认回收？</h4></div></div>',
				confirmIsShow: true,
				confirmFun: function() {
					$.ajax({
						url: '/admin/Remark/index?parma=sv9',
						type: 'POST',
						dataType: 'json',
						data: {id: getUserId('bind')},
						success: function() {

							window.location.reload();
						}
					});
				}
			});

			
		}else{
			message.show({
				mask: true,
				content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">请先选择回收对象</h4></div></div>',
				cancelIsShow: true,
				cancelFun: function() {
					this.hide();
				}
			});
		}
	});

	//报表下载
	batchReport.on('click', function() {
		var count = countCheckedNum();


	});

	//全选按钮
	selectAllDom.on('click', function() {


		$.each(checkBoxDom, function(index, val) {
			var result = $(this).prop('checked');
			
			if(result) {
				$(this).removeAttr('checked');
			}else {
				$(this).prop('checked', true);
			}

		});
	});

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
		}else{
			//初始化给空
			result[me.name] = '';
		}
		return result;
	};
	
	var group = new phoenix.SuperSearchGroup();
	//用户名
	var movnumber = new phoenix.SuperSearch({
		name: 'userName',
		keyCode: 'ctrl+83',
		 el: '#J-sp-serial',
		group: group
	});
	
	//唯一附言
	var remark = new phoenix.SuperSearch({
		name: 'remarkName',
		keyCode: 'ctrl+86',
		 el: '#J-sp-remark',
		group: group
	});
	//绑定时间
	var bindDatetime = new phoenix.SuperSearch({
		name: 'bindDate',
		keyCode: 'ctrl+69',
		type: 'time',
		el: '#J-sp-bind-time',
		expands:{getFormValue:getTowInputValue},
		group: group
		
	});
	var bindDateTime1 = $('#sp-input-bindDate-time-1'),bindDateTime2 = $('#sp-input-bindDate-time-2'),bindDateDt1,bindDateDt2;
	bindDateTime1.click(function() {
		bindDateDt1 = new phoenix.DatePicker({
			input: bindDateTime1,
			isLockInputType: false
		});
		bindDateDt1.show();
		bindDateDt1.addEvent('afterSetValue',
		function() {
			bindDateTime2.focus();
			bindDateTime2.click();
		})
	});
	bindDatetime.addEvent('afterFocus',
	function() {
		bindDateTime1.click()
	});
	bindDateTime2.click(function() {
		bindDateDt2 = new phoenix.DatePicker({
			input: bindDateTime2,
			isLockInputType: false
		}).show();
	});
	bindDatetime.addEvent('afterBlur',function() {
		if (bindDateDt1) {
			bindDateDt1.hide();
		}
		if (bindDateDt2) {
			bindDateDt2.hide();
		}
	});

	
	//提交数据
	SelectByWhereInfo("0");
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
		 var formData = group.getFormData(),mask = phoenix.Mask.getInstance();			
		 $("#J-table-data>tbody").html("");	
		 $("#Pagination").hide(); 			
		 $.ajax({
			url:'/admin/remark/index?parma=sv12',
			dataType:'json',
			method:'post',
			data:formData,
			beforeSend:function(){				
				TableStyle("showInfo",19,1,"查询中");
			},
			success:function(data){
				//debugger
				if(data.text.length>0){
					$("#Pagination").show();
					//$('.ui-tab-content').css('overflow-x', 'auto');
					//var resultAll = JSON.parse(data); //jQuery.parseJSON    
					var resultAll = eval(data);               
					var re = resultAll.text;
					var recordNum = 0;
					recordNum = resultAll.count.recordNum;	
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
						html += "<tr><td>" + re[i].remark + "</td>";
						html += "<td>" + re[i].userName + "</td>";
						html += "<td>" + re[i].createTime + "</td>";
						html += "<td><a class='recover' data-user='" + re[i].id + "' data-remark='" + re[i].remark + "'  href='#'>回收</a></td> ";
					});
					$("#J-table-data>tbody").html(html);	
					
					
					
					
					//回收操作
					$('.recover').on('click', function() {
						var address = $(this).attr('data-user');
						var remark = $(this).attr('data-remark');

						message.show({
							mask: true,
							content: '<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">确认回收？</h4></div></div>',
							confirmIsShow: true,
							confirmFun: function() {
								
								$.ajax({
									url: '/admin/Remark/index?parma=sv9',
									type: 'POST',
									dataType: 'json',
									data: {id: address,remark:remark},
									success: function() {
										window.location.reload();
									}
								});
							},
							cancelIsShow: true,
							cancelFun: function() {
								this.hide();
							}
						});
					});
						
				}else{
					 $("#Pagination").hide();    
					 TableStyle("J-table-data>tbody",17,2,"没有相应数据");
				}
			}, 
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 TableStyle("J-table-data>tbody",17,2,"数据异常");
			}
		});			
	}



})(jQuery);
</script>
{/literal}
</body>
</html>