$(document).ready(function() {
	$('.menu-list li:eq(4)').attr("class","current"); 
	$('.col-side .nav dd:eq(2)').attr("class","current");
	
	var Wd = phoenix.Message.getInstance(),
	Tip = phoenix.Tip.getInstance();
	
	$('#J-button-export').click(function(){
		var ipts = $('#J-form-hidden').find('input[type="hidden"]'),data = {},htmlStr = '';
		ipts.each(function(){
			var name = this.getAttribute('name');
			data[name] = this.value;
		});
		htmlStr = phoenix.util.template($('#J-tpl-export').text(), data);
		
		Wd.show({
			mask:true,
			title:'温馨提示',
			content:htmlStr,
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$('#J-download-form').submit();
				Wd.hide();
			},
			cancelFun:function(){
				Wd.hide();
			},
			callback:function(){
				var rows = Wd.win.dom.find('li[data-data]');
				rows.each(function(){
					var dom = $(this);
					if($.trim(dom.attr('data-data')) == ''){
						dom.remove();
					}
				});
			}
		});
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
	
	 var getUserName = function(){
		var me = this,input=me.dom.find('input'),v = '',result ={};
		var containSub=0;
		 if(input[0].checked){
			 containSub = 1;
		 }
		 var userName = $(input[1]).val();
		 v =userName+','+containSub;
		 result[me.name]=v;
		 return result;
	}; 
	
	//获取select类型值
		var getMultiSelectValue = function(){
			var me = this,li = me.dom.find('li.current'),v = '', result = {};

			result[me.name] = [];

			if(li.size() > 0){
				$.each(li, function(index, val) {
					v = li.eq(index).attr('data-select-id');
					result[me.name][index] = v;	 
				});
				
			}

			result[me.name] = result[me.name].join();

			return result;
		};
	
	AllData();
	function AllData(){
	
	//交易流水号
	var tid = new phoenix.SuperSearch({
		name: 'tid',
		keyCode: 'ctrl+83',
		el: '#J-sp-tid',
		group: group
	});
	//用户名
	var userName = new phoenix.SuperSearch({
		name: 'userName',
		keyCode: 'ctrl+83',
		el: '#J-sp-userName',
		group: group,
		expands:{getFormValue:getUserName}
	});
	//游戏类型
	var gameType = new phoenix.SuperSearch({
		name: 'gameType',
		keyCode: 'ctrl+83',
		type: 'select',
		isAutoWidth: true,
		el: '#J-sp-gameType',
		group: group,
		expands:{getFormValue:getSelectValue}
	});	

	//追号编号
	var planCode = new phoenix.SuperSearch({
		name: 'planCode',
		keyCode: 'ctrl+83',
		 el: '#J-sp-planCode',
		group: group
	});	
	
	//方案编号	
	var orderCode = new phoenix.SuperSearch({
		name: 'orderCode',
		keyCode: 'ctrl+83',
		el: '#J-sp-orderCode',
		group: group
	});

	var reson = new phoenix.SuperSearch({
		name: 'reson',
		keyCode: 'ctrl+83',
		type: 'multi',
		isAutoWidth: true,
		titleLimit: 5,
		el: '#J-sp-reson',
		group: group,
		expands:{getFormValue:getMultiSelectValue}
	});		
	//金额
	 var amonut = new phoenix.SuperSearch({
		name: 'amonut',
		keyCode: 'ctrl+83',
		el: '#J-sp-amonut',
		group: group
	});
	 //游戏
	 var lotteryName = new phoenix.SuperSearch({
		name: 'lotteryName',
		keyCode: 'ctrl+83',
		type: 'select',
		isAutoWidth: true,
		el: '#J-sp-lotteryName',
		group: group,
		expands:{getFormValue:getSelectValue}
	});
	 //状态
	 var game = new phoenix.SuperSearch({
		name: 'status',
		keyCode: 'ctrl+83',
		type: 'select',
		isAutoWidth: true,
		el: '#J-sp-status',
		group: group,
		expands:{getFormValue:getSelectValue}
	});

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
			url:'queryGameRiskTransactionReport',				
			method:'post',
			data:formData,
			beforeSend:function(){		
			     isLock=false;		
				 TableStyle("showInfo",17,1,"查询中");
			},
			success:function(data){
				//返回data的json格式
				if(data.body.result!=null && data.body.result.reportList!=null && data.body.result.reportList.length>0){
					$("#Pagination").show();
					$('.ui-tab-content').css('overflow-x', 'auto');	
					var re = data.body.result.reportList;						
					var recordNum = 0;
					recordNum = data.body.pager.total;
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
						if(re[i].planCode == ''|| re[i].planCode==null){	
							html += '<tr><td>' + '<a href="'+baseUrl+'/gameoa/queryOrderDetail?orderCode='+re[i].orderCode+'" target="_blank">'+re[i].tid + '</a></td>';
						}else{
							html += '<tr><td>' + '<a href="'+baseUrl+'/gameoa/queryPlanDetail?planCode='+re[i].planCode+'" target="_blank">'+re[i].tid + '</a></td>';
						}
						
							//html += "<tr><td>" + re[i].tid + "</td>";
							html += "<td>" + re[i].userName + "</td>";
							html += "<td>" + re[i].tradeDate + "</td>";
							html += "<td>" + re[i].gameType + "</td>";
							html += "<td>" + re[i].planCode + "</td>";							
							html += "<td>" + re[i].orderCode + "</td>";
							html += "<td>" + re[i].reson + "</td>";
							html += "<td>" + parseFloat(re[i].amonut/10000).toFixed(2) + "</td>";											
							html += "<td>" + re[i].lotteryName + "</td>";	
							html += "<td>" + re[i].status + "</td></tr>";						
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
					 TableStyle("J-table-data>tbody",17,2,"没有相应数据");
				}				
			}, 
			complete: function()
			{
				isLock=true;
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 TableStyle("J-table-data>tbody",17,2,"数据异常");
			}
		});			
	}	
}
	
});