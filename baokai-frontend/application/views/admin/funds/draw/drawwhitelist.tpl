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
						<!-- {if "FUND_WITHDRAW_WHITELIST_ADD"|in_array:$smarty.session.datas.info.acls} -->
						<h3 class="ui-title"><a class="btn btn-small" style="float:left;" href="javascript:void(0);" id="addWhiteUserBtn">新增可提现额度白名单<b class="btn-inner"></b></a></h3>
						<!-- {/if} -->
								 <table  id="J-table-data" class="table table-info table-function text-center">
									<thead>
										<tr>
										<th><input id="J-select-all" name="" style="vertical-align: middle;" type="checkbox"> 全选</th>
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
											<th><div class="input-append">所属总代</div></th>
											<th><div class="input-append">管理员</div></th>
											<th><div class="input-append">列入白名单时间</div></th>
											<th><div class="input-append">备注</div></th>
                                        <!-- {if "FUND_WITHDRAW_WHITELIST_DELETE"|in_array:$smarty.session.datas.info.acls} -->
											<th><div class="input-append">操作</th>
                                        <!-- {/if} -->
										</tr>
									</thead>
									 <tbody id="showInfo">
                                    </tbody>                                    
                                    <tfoot id="showfoot" style="display:none;">
                                        <tr>
                                        <!-- {if "FUND_WITHDRAW_WHITELIST_DELETE"|in_array:$smarty.session.datas.info.acls} -->
                                        <td><a class='btn btn-small' href='javascript:void(0);' id="delBtn">删除<b class='btn-inner'></b></a></td>
                                        <!-- {/if} -->
                                         <td colspan="7">
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
	<div class="pop pop-success w-4" style="position:absolute;left:900px;display:none;z-index: 800;" id="divOperatSuccess">
		<i class="ico-success"></i><h4 class="pop-text">添加成功</h4>
	</div>
	<div class="pop pop-error w-4" style="position:absolute;left:1200px;display:none;z-index: 800;" id="divOperatFailure">
		<i class="ico-error"></i><h4 class="pop-text">添加失败，请重试</h4>
	</div>
<script type="text/tpl" id="J-template-addWhiteUser">
<div class="main">
        <form id="fm_main">
            <ul class="ui-form">
                <li>
                    <label class="ui-label" style="width:60px;">用户名：</label>
                     <div class="textarea w-6">
                        <textarea  id="userName" wrap="virtual" name="userName" style="height:30px;"></textarea> 
                    </div>
					<br/><label class="ui-label" style="width:60px;"></label><span class="ui-check" style="display: none;"><i class="error"></i>用户名不能为空</span> 
                </li>
                <li>
                     <label class="ui-label" style="width:60px;">备注：</label>
                    <div class="textarea w-6">
                        <textarea  id="Memo" name="Memo" maxlength="100" ></textarea>                                  
                    </div>
                    <span class="ui-info vertical-bottom"><span name='spancount'>0</span>/100<div class="ui-check"><i class="error"></i>备注不能为空</div></span>                                  
                </li>
                <li class="ui-btn">
              		<input type="button" value="添 加" class="btn btn-important" id="J-Submit-Button"></input>
                    <a href="javascript:void(0);" class="btn" id="J-Close-Button"i>返 回<b class="btn-inner"></b></a>
                </li>
            </ul>
		</form>
		
	</div>
</script>
<script>
var isEnableDel = {if "FUND_WITHDRAW_WHITELIST_DELETE"|in_array:$smarty.session.datas.info.acls}1{else}0{/if};
</script>
{include file='/admin/script-base.tpl'}
{literal}
<script>
(function() {
	var isShowCell=false;	
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuFundConfig');
	
	var minWindow = new phoenix.MiniWindow(),msg = new phoenix.Message(),msg1 = new phoenix.Message()
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});
	function fn(obj){
		var Idivdok = document.getElementById(obj);	
		
		Idivdok.style.display="block";
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";
		if(document.body.scrollTop == 0){
			Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";	
		} else {
			Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.body.scrollTop-40+"px";	
		}
	}
	$('#addWhiteUserBtn').click(function(){
		minWindow.setTitle('新增可提现用户白名单');
		minWindow.setContent($('#J-template-addWhiteUser').html());
		minWindow.show();
		$("#J-Close-Button").click(function(){
			minWindow.hide();
			$('#userName').val('');
			$('#Memo').val('');
		});
		$('#Memo').keyup(function(){
		 $('[name="spancount"]').html($(this).val().length);
	    });
		$('#J-Submit-Button').click(function(e){
			if($('#userName').val() ==''){
				$('#userName').parent().parent().find('.ui-check').css('display', 'inline');	
				return false;
			}
			var aUsernName = $('#userName').val().split(',');
			if(aUsernName.length>500){
				$("#divOperatFailure h4").html('单次新增用户不能超过500');
				fn("divOperatFailure");
				setTimeout(function(){$('#divOperatFailure').css("display","none");},3000);
				return false;
			}
			$.ajax({
				url:'/admin/Fundconfig/index?parma=sv5',
				dataType:'json',
				method:'post',
				async:false,
				cache:false,
				data:"userName="+$('#userName').val()+"&memo="+$('#Memo').val(),
				success:function(data){
					if(Number(data['isSuccess']) == 1){
						$("#divOperatSuccess h4").html('添加成功!');
						fn("divOperatSuccess");
						setTimeout(function(){$('#divOperatSuccess').css("display","none");minWindow.hide();SelectByWhereInfo('0');},3000);
					} else if(Number(data['isSuccess']) == 2){
						$('#userName').parent().parent().find('.ui-check').css('display', 'inline');
					} else {
						$("#divOperatFailure h4").html(data.data);
						fn("divOperatFailure");
						setTimeout(function(){$('#divOperatFailure').css("display","none");},3000);
					}
				}
			});
		});
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
		var me = this,li = me.dom.find('li.current'),v = '',result = {};
		if(li.size() > 0){
			v = li.eq(0).attr('data-select-id');
			result[me.name] = v;
		}
		return result;
	};
	var group = new phoenix.SuperSearchGroup();
	
	var UserName = new phoenix.SuperSearch({
		name: 'UserName',
		keyCode: 'ctrl+83',
		 el: '#J-sp-td-UserName',
		group: group
	});
	
	SelectByWhereInfo("0");	
	
	$('#J-select-all').click(function(event) {
		$('table input[type="checkbox"]').prop('checked', $(this).prop('checked'));
	});
	//提交数据
	var dataArea = $('#showInfo');
	group.addEvent('dataChange', function(){
		SelectByWhereInfo("0");				
	});
	
	function SelectByWhereInfo(pages) {
		 $("#showfoot").hide();
		 $('#J-select-all').prop('checked',false);
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
		
		 var dataArea = $('#J-table-data'),formData = group.getFormData(),mask = phoenix.Mask.getInstance();			
		 $("#J-table-data>tbody").html("");	
		 $("#Pagination").hide();  			
		 $.ajax({
			url:'/admin/Fundconfig/index?parma=sv7',
			dataType:'json',
			method:'post',
			data:formData,
			beforeSend:function(){				
				//$("#showInfo").html("<tr><td colspan=\"6\" style='height:120px;text-align:center;color:#009B7D; font-size:14px;font-weight:600;'>查询中...请稍候！</td></tr>");
				 TableStyle("showInfo",7,1,"查询中");
			},
			success:function(data){
				//debugger
				if(data.text[0]){
					$("#showfoot").show();
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
							html += "<tr><td><input name='id[]' value='" + re[i].account + "' type='checkbox' style='margin-right:29px;'></td>";
							html += "<td>" + re[i].account + "</td>";
							html += "<td>" + re[i].topAcc + "</td>";
							html += "<td>" + re[i].manager + "</td>";
							html += "<td>" + re[i].date + "</td>";
							html += "<td>" + re[i].note + "</td>";
							if(isEnableDel == 1){
								html += "<td><a href='javascript:void(0);' pro_id='" + re[i].account + "' name='deleteOper'>删 除</a></td>";
							}
							html +="</tr>";
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
		delWhiteItem(pro_id);
	});
	
	$('#delBtn').click(function(){
		var selectedItems = new Array(); 
		$("table td input[type='checkbox']:checked").each(function() {selectedItems.push($(this).val());}); 
		delWhiteItem(selectedItems);
	});
	
	
	function delWhiteItem(pro_id){
		if($.trim(pro_id) ==''){
			msg1.show({
				mask: true,
				content: '<h3 style="height:30px;line-height:30px;text-align:center;">请选择删除项!</h3><div style="height:30px;line-height:30px;"></div>',
				confirmIsShow: 'true',
				confirmText: '确定',
				confirmFun: function(){
					msg1.hide();
				}
			});
			return false;
		}
		msg.show({
			mask: true,
			content: '<h3 style="height:30px;line-height:30px;text-align:center;">确定删除该白名单用户吗?</h3><div style="height:30px;line-height:30px;"></div>',
			confirmIsShow: 'true',
			confirmText: '确定',
			confirmFun: function(){
				$.ajax({
					url:'/admin/Fundconfig/index?parma=sv6',				
					dataType:'json',
					method:'post',
					data:'id='+pro_id,					
					success:function(data){
						msg.hide();
						if(data['isSuccess'] =='1'){	
							$("#divOperatSuccess h4").html('删除成功!');
							fn("divOperatSuccess");
							setTimeout(function(){$('#divOperatSuccess').css("display","none");SelectByWhereInfo('0');},3000);
						} else if(Number(data['isSuccess']) == 2){
							$("#divOperatFailure h4").html('删除失败,请选择删除项!');
							fn("divOperatFailure");
							setTimeout(function(){$('#divOperatFailure').css("display","none");},3000);
						} else{
							$("#divOperatFailure h4").html('删除失败,请重试!');
							fn("divOperatFailure");
							setTimeout(function(){$('#divOperatFailure').css("display","none");},3000);
						}
					}					
				});	
			},
			cancelIsShow: 'true',
			cancelText: '取消',
			cancelFun: function(){
				msg.hide();
			}
		});
	}
})();	
</script>
{/literal}
</body>
</html>