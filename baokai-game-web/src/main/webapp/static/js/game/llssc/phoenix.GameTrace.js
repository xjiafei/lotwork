
//追号区域
(function(host, name, Event, undefined){
	var defConfig = {
		//主面板dom
		mainPanel:'#J-trace-panel',
		//高级追号类型(与tab顺序对应)
		advancedTypeHas:['fanbei','yingli','yinglilv'],
		//追号数据表头
		dataRowHeader:'<tr><th style="width:125px;" class="text-center">序号</th><th><input data-action="checkedAll" type="checkbox"  checked="checked"/> 追号期次</th><th>倍数</th><th>金额</th><th>预计开奖时间</th></tr>',
		//追号数据列表模板
		dataRowTemplate:'<tr><td class="text-center"><#=No#></td><td><input data-action="checkedRow" class="trace-row-checked" type="checkbox" checked="checked"> <span class="trace-row-number" issueCode=<#=issueCode#>><#=traceNumber#></span></td><td><input class="trace-row-multiple" value="<#=multiple#>" type="text" style="width:30px;text-align:center;"> 倍</td><td><span  style="font-family: Arial,verdana;"><dfn>&yen;</dfn><span class="trace-row-money"><#=money#></span> 元</span> </td><td><span class="trace-row-time"><#=publishTime#></span></td></tr>',
		//高级追号盈利金额追号/盈利率追号表模板
		dataRowYingliHeader:'<tr><th class="text-center">序号</th><th><input data-action="checkedAll" type="checkbox" /> 追号期次</th><th>倍数</th><th>金额</th><th>奖金</th><th>预期盈利金额</th><th>预期盈利率</th></tr>',
		//高级盈利/盈利率追号数据列表模板
		dataRowYingliTemplate:'<tr><td class="text-center"><#=No#></td><td><input data-action="checkedRow" class="trace-row-checked" type="checkbox" checked="checked"> <span class="trace-row-number" issueCode=<#=issueCode#>><#=traceNumber#></span></td><td><input class="trace-row-multiple" value="<#=multiple#>" type="text" style="width:30px;text-align:center;"> 倍</td><td><span  style="font-family: Arial,verdana;"><dfn>&yen;</dfn><span class="trace-row-money"><#=money#></span> 元</span></td><td><span  style="font-family: Arial,verdana;"><dfn>&yen;</dfn> <span class="trace-row-userGroupMoney"><#=userGroupMoney#></span> </span>元</td><td><span  style="font-family: Arial,verdana;"><dfn>&yen;</dfn> <span class="trace-row-winTotalAmount"><#=winTotalAmout#></span> 元</span></td><td><span class="trace-row-yinglilv"><#=yinglilv#></span>%</td></tr>'
	},
	instance,
	Games = host.Games;
	
	//只允许输入正整数
	//v 值
	//def 默认值
	//mx 最大值
	var checkInputNum = function(v, def, mx){
		var v = ''+v,mx = mx || 1000000000;
		v = v.replace(/[^\d]/g, '');
		v = v == '' ? def : (Number(v) >  mx ? mx : v);
		return Number(v);
	};
	
	//只允许输入正整数
	var checkInputNumber = function(v){	
		v = v.replace(/[^\d]/g, '');	
		return Number(v);
	};

	
	var pros = {
		init:function(cfg){
			var me = this;
			Games.setCurrentGameTrace(me);
			me.panel = $(cfg.mainPanel);
			
			//追号tab
			me.TraceTab = null;
			//高级追号tab
			me.TraceAdvancedTab = null;
			
			//追号面板是否开启
			me.isOpenPanel = false;
			
			//订单数据
			me.orderData = null;
			
			//公共属性部分
			//追号类型，普通追号 高级追号
			me.traceType = 'normal';
			//追号期数
			me.times = 0;
			//追号起始期号
			me.traceStartNumber = '';
			//当前期号
			me.currentTraceNumber = '';
			
			
			
			//普通追号属性
			
			
			//高级追号属性
			//高级追号类型 
			me.advancedType = cfg.advancedTypeHas[0];
			me.typeTypeType = 'a';
			
			
			me.initEvent();
			me.setCurrentTraceNumber();

			
			//配置更新后追号面板相关更新
			//重新构建期号选择列表
			Games.getCurrentGame().addEvent('changeDynamicConfig', function(){
				me.buildStartNumberSelectDom();
				me.updateTableNumber();
			});
			
			
		},
		setAdvancedType:function(i){
			if(Object.prototype.toString.call(i) == '[object Number]'){
				this.advancedType = this.getAdvancedTypeBuIndex(i);
			}else{
				this.advancedType = i;
			}
		},
		getAdvancedType:function(){
			return this.advancedType;
		},
		getAdvancedTypeBuIndex:function(i){
			var me = this,has = me.defConfig.advancedTypeHas,len = has.length;
			if(i < len){
				return has[i];
			}
			return '';
		},
		initEvent:function(){
			var me = this;
			var userName = $("#userName");

			var traceTips=this.traceTips = new phoenix.Tip({
				cls:'j-ui-tip-close j-ui-tip-yellow j-ui-tip-t',
				text:'<h4><i class="close">ⅹ</i>温馨提示</h4><p>平台功能小调整，默认勾选中奖后停止追号</p>',
			});
			$('.j-ui-tip-close .close').click(function(){
				var d = new Date()
				d.setFullYear(d.getFullYear() + 1);
				jQuery.cookie(userName+'data',1,{expires:d})
				traceTips.hide();
			})		
			//追号显示隐藏
			$('#J-trace-switch').click(function(){
				if(this.checked){
					var orderAmount=Games.getCurrentGameOrder().getTotal()['amount'],msg = Games.getCurrentGameMessage();
				    if(orderAmount <= 0){						
						msg.show({
							type : 'mustChoose',
							msg : '请至少选择一注投注号码！',
							data : {
								tplData : {
									msg : '请至少选择一注投注号码！'
								}
							}
						});
						$('#J-trace-switch').get(0).checked = false;
						return;						
					}else{
						Games.getCurrentGameTrace().show();
						$('#J-trace-iswintimesstop').get(0).checked = true;
						var traceIswintimesstopPanel = $('#J-trace-iswintimesstop-panel');
						if(!jQuery.cookie(userName+'data')){
							traceTips.show(-100, 40, traceIswintimesstopPanel);
						}
					}
					
				}else{
					Games.getCurrentGameTrace().hide();
					traceTips.hide();
				}
			});
			
			
			//追号tab
			me.TraceTab = TraceTab = new host.Tab({par:'#J-trace-panel',triggers:'.chase-tab-t',panels:'.chase-tab-content',currPanelClass:'chase-tab-content-current',eventType:'click'});
				TraceTab.addEvent('afterSwitch', function(e, i){
					var types = ['normal', 'advanced'];
					if(i < types.length){
						me.setTraceType(types[i]);
					}
					me.updateStatistics();
				});
			//高级追号tab
			me.TraceAdvancedTab = TraceAdvancedTab = new host.Tab({par:'#J-trace-advanced-type-panel',triggers:'.tab-title li',panels:'.tab-content li',eventType:'click'});
				TraceAdvancedTab.addEvent('afterSwitch', function(e, i){
					var ipts = this.getPanel(i).find('.trace-advanced-type-switch');
					me.setAdvancedType(i);
					ipts.each(function(){
						if(this.checked){
							me.setTypeTypeType($(this).parent().attr('data-type'));
							return false;
						}
					});
				});
			
			
			//追中即停说明提示
			var TraceTip1 = new host.Hover({triggers:'#J-trace-iswintimesstop-hover',panels:'#chase-stop-tip-1',currPanelClass:'chase-stop-tip-current',hoverDelayOut:900});
			$('#chase-stop-tip-1').mouseleave(function(){
				TraceTip1.hide();
			});
			var TraceTip2 = new host.Hover({triggers:'#J-trace-iswinstop-hover',panels:'#chase-stop-tip-2',currPanelClass:'chase-stop-tip-current',hoverDelayOut:900});
			$('#chase-stop-tip-2').mouseleave(function(){
				TraceTip2.hide();
			});
			$('#J-chase-stop-switch-1').click(function(e){
				e.preventDefault();
				$('#J-trace-iswintimesstop-panel').hide();
				$('#J-trace-iswinstop-panel').show();
				$('#J-trace-iswintimesstop').get(0).checked = false;
				$('#J-trace-iswinstop').get(0).checked = true;
				$('#J-trace-iswinstop-money').removeAttr('disabled');
				$('#J-trace-iswintimesstop-times').attr('disabled', 'disabled');
			});
			$('#J-chase-stop-switch-2').click(function(e){
				e.preventDefault();
				$('#J-trace-iswinstop-panel').hide();
				$('#J-trace-iswintimesstop-panel').show();
				$('#J-trace-iswintimesstop').get(0).checked = true;
				$('#J-trace-iswinstop').get(0).checked = false;
				$('#J-trace-iswinstop-money').attr('disabled', 'disabled');
				$('#J-trace-iswintimesstop-times').removeAttr('disabled');
			});
			$('#J-trace-iswinstop-money').keyup(function(){
				this.value = checkInputNum(this.value, 1, 999999);
			});
			$('#J-trace-iswintimesstop-times').keyup(function(){
				this.value = checkInputNum(this.value, 1, 999999);
			});
			
			//是否止盈追号(按中奖次数)
			$('#J-trace-iswintimesstop').click(function(){
				var ipt = $('#J-trace-iswintimesstop-times');
				if(this.checked){
					ipt.attr('disabled', false).focus();
				}else{
					ipt.attr('disabled', 'disabled');
				}
			});
			//是否止盈追号(按中奖金额)
			$('#J-trace-iswinstop').click(function(){
				var ipt = $('#J-trace-iswinstop-money');
				if(this.checked){
					ipt.attr('disabled', false).focus();
				}else{
					ipt.attr('disabled', 'disabled');
				}
			});
			
			//普通追号事件
			//普通追号Input输入事件
			$('#J-trace-normal-times').keyup(function(){
				var	maxnum = Games.getCurrentGame().getDynamicConfig()['tracemaxtimes'],
					v = '' + this.value,
					num,
					list = $('#J-function-select-tab').find('.function-select-title li'),
					cls = 'current';
				v = v.replace(/[^\d]/g, '');
				v = v == '' ? 1 : (Number(v) >  maxnum ? maxnum : v);
				this.value = v;
				num = Number(v);
				//修改追号期数选项样式
				if(num > 0 && num <= 20 && (num%5 == 0)){
					list.removeClass(cls).eq(num/5 - 1).addClass(cls);
					NormalSelectTimesTab.index = num/5 - 1;//重新定位
				}else{
					list.removeClass(cls);
					NormalSelectTimesTab.index = 22;
				}
				me.buildDetail();
			});
			//$('#J-trace-normal-times').blur(function(){
//				me.buildDetail();
//			});
			//期数选择操作
			var NormalSelectTimesTab = new host.Tab({par:'#J-function-select-tab',triggers:'.function-select-title li',panels:'.function-select-panel li',eventType:'click',index:1});
				NormalSelectTimesTab.addEvent('afterSwitch', function(e, i){
					var tab = this,num = parseInt(tab.getTrigger(i).text());
					$('#J-trace-normal-times').val(num);
					me.buildDetail();
				});
			
			//倍数模拟下拉框
			me.normalSelectMultiple = new host.Select({realDom:'#J-trace-normal-multiple',isInput:true,expands:{inputEvent:function(){
														var me = this;
														me.getInput().keyup(function(e){
															var v = this.value,
																maxnum = 99999;
															this.value = this.value.replace(/[^\d]/g, '');
															v = Number(this.value);
															if(v < 1){
																this.value = 1;
															}
															if(v > maxnum){
																this.value = maxnum;
															}
															me.setValue(this.value);
														});
													}}});
			me.normalSelectMultiple.addEvent('change', function(e, value, text){
				var amount = me.getOrderData()['amount'],num = Number(value),maxObj = Games.getCurrentGameOrder().getOrderMaxMultiple(),maxnum = maxObj['maxnum'],Msg = Games.getCurrentGameMessage(),
					typeTitle = '';
				
				if(num > maxnum){
					typeTitle = Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(maxObj['gameMethod']).join('-');
					Msg.show({
						confirmIsShow: true,
						mask: true,
						msg : '',
						tpl:'<div class="pop-waring"><i class="ico-waring &lt;#=icon-class#&gt;"></i><h4 class="pop-text"><#=msg#></h4></div>',
						data : {
							tplData : {
								msg : '您输入的倍数超过了<b>['+ typeTitle + ']</b> 玩法的最高倍数限制，系统将自动修改为最大可输入倍数'
							}
						},
						confirmFun:function(){
							value = maxnum;
							me.normalSelectMultiple.setValue(value);
							Msg.hide();
							
							me.getTable().find('.trace-row-multiple').val(value);
							me.getTable().find('.trace-row-money').each(function(){
								var el = $(this),multiple = Number(el.parent().parent().find('.trace-row-multiple').val());
								el.html(me.formatMoney(amount * Number(value)));
							});
							me.updateStatistics();
							
							
						}
					});
				}else{
					me.getTable().find('.trace-row-multiple').val(value);
					me.getTable().find('.trace-row-money').each(function(){
						var el = $(this),multiple = Number(el.parent().parent().find('.trace-row-multiple').val());
						el.html(me.formatMoney(amount * Number(value)));
					});
					me.updateStatistics();
				}
				

			});
			//数据行限制输入正整数,(可清空,失焦自动填充一倍.首字符不能为0,单选框没选中禁用，选中初始1倍值)
			//数据行限制输入正整数
			me.panel.find('.chase-table').keyup(function(e){
				var el = $(e.target),amount = me.getOrderData()['amount'];
				if(el.hasClass('trace-row-multiple')){ //处理当删除注数时，追号倍数不限制
					
						var multiple = Number(el.val()),
							tableType = me.getRowTableType(),
							maxnum = Number(Games.getCurrentGameOrder().getOrderMaxMultiple()['maxnum']);
						if(multiple == 0){
							el.val(el.val().replace(/^0/g, '')); //去除填写0场景
							el.parent().parent().find('.trace-row-money').html('0.00');
						}
						else if(multiple > maxnum){	
							multiple = maxnum;					
							el.val(multiple);	
							el.parent().parent().find('.trace-row-money').html(me.formatMoney(amount * multiple));
						}else{	
												
							el.parent().parent().find('.trace-row-money').html(me.formatMoney(amount * multiple));
							//如果是盈利追号和盈利率追号，则需要重新计算盈利金额和盈利率
							if(tableType == 'trace_advanced_yingli_a' || tableType == 'trace_advanced_yingli_b' || tableType == 'trace_advanced_yinglilv_a' || tableType == 'trace_advanced_yinglilv_b'){
								me.rebuildYinglilvRows();
							}	
						}	
						me.updateStatistics();
				}
			}).on('blur', '.trace-row-multiple', function(e){
				var el = $(e.target),
					multiple = Number(el.val()),
					amount = me.getOrderData()['amount'];
				el.val(checkInputNum(el.val(), 1, Games.getCurrentGameOrder().getOrderMaxMultiple()['maxnum']));
				el.parent().parent().find('.trace-row-money').html(me.formatMoney(amount * Number(el.val())));
				me.updateStatistics();
			});
			
			
			
			
			//高级追号事件
			//创建期号列表
			setTimeout(function(){
				me.buildStartNumberSelectDom();
			}, 10);
			
			
			//追号期数
			$('#J-trace-advanced-times').keyup(function(){
				this.value = checkInputNum(this.value, 10, Number($('#J-trace-number-max').text()));
			});
			
			//起始倍数
			$('#J-trace-advance-multiple').keyup(function(e){
				var el = $(e.target),multiple = Number(checkInputNumber(el.val())),maxnum = Number(Games.getCurrentGameOrder().getOrderMaxMultiple()['maxnum']);
				if(multiple == 0){ 			
					el.val(el.val().replace(/^0/g, '')); 							
				}
				else if(multiple > maxnum){						
					el.val(maxnum);						
				}else{
					el.val(multiple);	
				}
								
			}).blur(function(){ //失去焦点纠正为1倍
				this.value = checkInputNum(this.value, 1, Games.getCurrentGameOrder().getOrderMaxMultiple()['maxnum']);		
			});
			
			//高级追号填写参数切换
			me.panel.find('.trace-advanced-type-switch').click(function(){
				var el = $(this),par = el.parent(),pars = par.parent().children(),_par;
				pars.each(function(i){
					_par = pars.get(i);
					if(par.get(0) != _par){
						//alert($(_par).html());
						$(_par).find('input[type="text"]').attr('disabled', 'disabled');
					}else{
						$(_par).find('input[type="text"]').attr('disabled', false).eq(0).focus();
						me.setTypeTypeType(par.attr('data-type'));
					}
					
					if(el.parent().hasClass('trace-input-multiple')){
						this.value = checkInputNum(this.value, 1, Games.getCurrentGameOrder().getOrderMaxMultiple()['maxnum']);
					}else{
						this.value = checkInputNum(this.value, 1, 99999);
					}
				
				});
			});
			//高级追号区域输入事件
			$('#J-trace-advanced-type-panel').on('keyup', 'input[type=text]', function(e){
				var dom = $(e.target);
				//如果是倍数输入框
				if(dom.hasClass('trace-input-multiple')){
					this.value = checkInputNum(this.value, 1, Games.getCurrentGameOrder().getOrderMaxMultiple()['maxnum']);
				}else{
					this.value = checkInputNum(this.value, 1, 99999);
				}
				
			});
			
			
			
			//生成追号计划事件
			$('#J-trace-builddetail').click(function(){
				me.confirmSetting();
			});
			
			//数据行选择行生效/失效事件
			me.panel.find('.chase-table').click(function(e){
				var el = $(e.target),action = $.trim(el.attr('data-action')),isChecked = true,tableType;
				if(!!action && action != ''){
					switch(action){
						case 'checkedAll':
							isChecked = !!el.get(0).checked ? true : false;
							tableType = me.getRowTableType();
							me.getTable().find('.trace-row-checked').each(function(){
								this.checked = isChecked;
							});
							//如果是盈利追号和盈利率追号，则需要重新计算盈利金额和盈利率
							if(tableType == 'trace_advanced_yingli_a' || tableType == 'trace_advanced_yingli_b' || tableType == 'trace_advanced_yinglilv_a' || tableType == 'trace_advanced_yinglilv_b'){
								me.rebuildYinglilvRows();
							}
							me.updateStatistics();
							break;
						case 'checkedRow':
							if(el.size() > 0){
								tableType = me.getRowTableType();
								//如果是盈利追号和盈利率追号，则需要重新计算盈利金额和盈利率
								if(tableType == 'trace_advanced_yingli_a' || tableType == 'trace_advanced_yingli_b' || tableType == 'trace_advanced_yinglilv_a' || tableType == 'trace_advanced_yinglilv_b'){
									me.rebuildYinglilvRows();
								}
								me.updateStatistics();
							}
							break;
						default:
							break;
					}
				}
			});
			
		},
		//创建期号列表slect元素
		buildStartNumberSelectDom:function(){
			var me = this,list = Games.getCurrentGame().getDynamicConfig()['gamenumbers'],len = list.length,i = 0,strArr = [],currentNumber = Games.getCurrentGame().getCurrentNumber(),
				currStr = '(当前期)',
				curr = currStr,
				oldValue,
				checkedStr = '';
			if(me.traceStartNumberSelect){
				oldValue = me.traceStartNumberSelect.getValue();
			}
			
			for(;i < len;i++){				
				curr = currentNumber == list[i]['number'] ? currStr : '';
				checkedStr = (!!me.traceStartNumberSelect && (list[i]['number'] == oldValue)) ? ' selected="selected" ' : '';
				strArr.push('<option value="'+ list[i]['number'] +'" '+ checkedStr +' >'+ list[i]['number'] + curr +'</option>');
			}
			$('#J-traceStartNumber').html(strArr.join(''));
			$('#J-trace-number-max').text(len);
			
			//起始号选择
			if(me.traceStartNumberSelect){
				me.traceStartNumberSelect.dom.remove();
			}
			me.traceStartNumberSelect = new host.Select({realDom:'#J-traceStartNumber',cls:'chase-trace-startNumber-select'});
			me.traceStartNumberSelect.addEvent('change', function(e, value, text){
				me.setTraceStartNumber(value);
			});
		},
		//更新表格期号
		updateTableNumber:function(){
			var me = this,list = Games.getCurrentGame().getDynamicConfig()['gamenumbers'],len = list.length,trs1,trs2,
				currNumber = Games.getCurrentGame().getCurrentNumber(),
				startNumber,
				dom,
				numberDom,
				dateDom,
				currText = '',
				index,
				traceLastNumber = '',//上期号
				//当前期
				currStr = '<span class="icon-period-current"></span>',
				traceNo,
				dateNotieNumber = 0,
				dateNotieNumber2 = 0,
				dateNotie; 

			//当当前期号发生更变时
			if(len > 0){
				trs1 = me.getNormalRowTable().find('tr');
				trs2 = me.getAdvancedRowTable().find('tr');	
								
				index = me.getStartNumberIndexByNumber(startNumber);
				trs1.each(function(i){
					if(i == 0){ //当当前(普通)期号发生更变时,跳出表头不放在trs1对象中循环
						return true;
					}
					dom = $(this);
					numberDom = dom.find('.trace-row-number');//当前开奖期号
					issuecodeNumber = dom.find('.trace-row-number').attr('issuecode'),//当前开奖期编号
					dateDom = dom.find('.trace-row-time');
					multipleDom = dom.find('.trace-row-multiple');
					startNumber = numberDom.text().replace(/[^\d]/g, '');					
					traceNo = dom.find('.text-center'); //序号 					
					dom.find('.trace-row-multiple').removeAttr('disabled'); //被禁用倍数文本框开启，倍数1
					
					if((index+1) < len){
						currText = list[index+1]['number'] == currNumber ? currStr : '';
						numberDom.html(list[index+1]['number'] + currText);
						multipleDom.text('1');
						dateDom.text(list[index+1]['time']);
						traceNo.html('').html(i); 
						if(traceLastNumber != issuecodeNumber.substr(0,8) && traceLastNumber != ""){//增加隔天期标识
							switch(dateNotieNumber){
								case 0 :	dateNotie = '明天 ';		break;
								case 1 :	dateNotie = '后天 ';		break;
								case 2 :	dateNotie = '大后天 ';	break;
								default :	dateNotie = '明天 ';		break;								
							}
							dateNotieNumber ++;
							traceNo.html('').append('<div class="icon-chase-mark">' + dateNotie + ' ' + dom.find('.trace-row-number').text().substr(0,8) + '</div>');
						}
						traceLastNumber = issuecodeNumber.substr(0,8); 
						index++;
					}
					
				});				
				
				index = me.getStartNumberIndexByNumber(startNumber);
				trs2.each(function(i){
					if(i == 0){ //去除表头（高级）
						return true;
					}
					dom = $(this);
					numberDom = dom.find('.trace-row-number');
					issuecodeNumber = dom.find('.trace-row-number').attr('issuecode'),//当前开奖期编号					
					dateDom = dom.find('.trace-row-time');
					multipleDom = dom.find('.trace-row-multiple');
					startNumber = numberDom.text().replace(/[^\d]/g, '');
					traceNo = dom.find('.text-center'); //序号 
					dom.find('.trace-row-multiple').removeAttr('disabled'); //被禁用倍数文本框开启，倍数1
					
					if((index+1) < len){
						currText = list[index+1]['number'] == currNumber ? currStr : '';
						numberDom.html(list[index+1]['number'] + currText);
						multipleDom.text('1');
						dateDom.text(list[index+1]['time']);
						traceNo.html('').html(i); 
						if(traceLastNumber != issuecodeNumber.substr(0,8) && traceLastNumber != ""){//增加隔天期标识							
							switch(dateNotieNumber2){
								case 0 :	dateNotie = '明天 ';		break;
								case 1 :	dateNotie = '后天 ';		break;
								case 2 :	dateNotie = '大后天 ';	break;
								default :	dateNotie = '明天 ';		break;
								
							}
							dateNotieNumber2 ++;
							
							traceNo.html('').append('<div class="icon-chase-mark">' + dateNotie + ' ' + dom.find('.trace-row-number').text().substr(0,8) + '</div>');
						}
						traceLastNumber = issuecodeNumber.substr(0,8); 						
						index++;
					}
				});
				
			}
			
		},
		//重新计算盈利金额和盈利率表格数据
		rebuildYinglilvRows:function(){
			var me = this,
				trs = me.getRowTable().find('tr'),
				orderData = me.getOrderData(),
				//单注预计中奖金额
				orderUserGroupMoney = me.getWinMoney(),
				
				rowDom = null,
				checkboxDom = null,
				multipleDom = null,
				multiple = 1,
				amountDom = null,
				amount = 0,
				userGroupMoneyDom = null,
				winMoneyDom = null,
				yinglilvDom = null,
				yinglilv = -1;
				
				//累计投注成本
				costAmount = 0;
			
			//console.log('rebuild');
				
			trs.each(function(i){
				//第一行为表头
				if(i > 0){
					rowDom = $(this);
					checkboxDom = rowDom.find('.trace-row-checked');
					//当该行处于选中状态
					if(checkboxDom.size() > 0 && checkboxDom.get(0).checked){
						multipleDom = rowDom.find('.trace-row-multiple');
						multiple = Number(multipleDom.val());
						amountDom = rowDom.find('.trace-row-money');
						amount = Number(amountDom.text().replace(',', ''));
						userGroupMoneyDom = rowDom.find('.trace-row-userGroupMoney');
						winMoneyDom = rowDom.find('.trace-row-winTotalAmount');
						yinglilvDom = rowDom.find('.trace-row-yinglilv');
						
						costAmount += orderData['amount'] * multiple;
						
						amountDom.text(me.formatMoney(orderData['amount'] * multiple));
						userGroupMoneyDom.text(me.formatMoney(orderUserGroupMoney * multiple));
						winMoneyDom.text(me.formatMoney(orderUserGroupMoney * multiple - costAmount));
						yinglilv = (orderUserGroupMoney * multiple - costAmount)/costAmount*100;
						yinglilvDom.text(Number(yinglilv).toFixed(2));
						
					}
				}
			});
			
		},
		isOpen:function(){
			return this.isOpenPanel;
		},
		setTypeTypeType:function(v){
			this.typeTypeType = v;
		},
		getTypeTypeType:function(){
			return this.typeTypeType;
		},
		getIsWinStop:function(){
			var me = this,stopDom1 = $('#J-trace-iswintimesstop'),stopDom2 = $('#J-trace-iswinstop');
			if(stopDom1.get(0).checked){
				return 1;	
			}
			if(stopDom2.get(0).checked){
				return 2;
			}
			return 0;
		},
		getTraceWinStopValue:function(){
			var me = this,isWinStop = me.getIsWinStop();
			if(isWinStop == 1){
				return Number($('#J-trace-iswintimesstop-times').val());
			}
			if(isWinStop == 2){
				return Number($('#J-trace-iswinstop-money').val());
			}
			return -1;
		},
		updateStatistics:function(){
			var me = this,data = me.getResultData();
			$('#J-trace-statistics-times').html(data['times']);
			$('#J-trace-statistics-lotterys-num').html(data['lotterysNum']);
			$('#J-trace-statistics-amount').html(me.formatMoney(data['amount']));
		},
		getResultData:function(){
			var me = this,orderData = me.getOrderData(),trs = me.getRowTable().find('tr'),rowDom,checkedDom,
				times = 0,
				lotterysNum = 0,
				amount = 0,
				traceData = [],
				par,
				result = {'times':0,'lotterysNum':0,'amount':0,'orderData':orderData,'traceData':[],'traceType':me.getTraceType()},
				traceLastNumber = '',//上期号
				list = Games.getCurrentGame().getDynamicConfig()['gamenumbers'],
				issueCode,
				index,
				dateNotieNumber = 0,
				dateNotie; 
				
			trs.each(function(i){
				rowDom = $(this);
				checkedDom = rowDom.find('.trace-row-checked'),				
				tracenumber = rowDom.find('.trace-row-number').attr('issuecode'),//当前开奖期编号
				traceNo = rowDom.find('.text-center'); //序号
				
				if( i != 0){
					traceNo.html('').html(i);					
				}
				if(checkedDom.size() > 0 && checkedDom.get(0).checked){	
					par = checkedDom.parent();
					index = me.getStartNumberIndexByNumber(par.find('.trace-row-number').text());	
					index = index == -1 ? 0 :index;
					issueCode = list[index]['issueCode'];
					//0倍时再选中，初始倍数为1倍					
					rowDom.find('.trace-row-multiple').removeAttr('disabled');
					if(rowDom.find('.trace-row-multiple').val() == '0'){ 
						rowDom.find('.trace-row-multiple').val('1');
						rowDom.find('.trace-row-money').text(me.formatMoney(orderData['amount'] * 1));				
						
					}							
					
					traceData.push({'traceNumber':par.find('.trace-row-number').text(),'issueCode':issueCode,'multiple':Number(par.parent().find('.trace-row-multiple').val())});
					times++;
					amount += Number(rowDom.find('.trace-row-money').text().replace(/,/g,''));	
					
					
					if(traceLastNumber != tracenumber.substr(0,8) && traceLastNumber != ""){
						switch(dateNotieNumber){
							case 0 :	dateNotie = '明天 ';		break;
							case 1 :	dateNotie = '后天 ';		break;
							case 2 :	dateNotie = '大后天 ';	break;
							default :	dateNotie = '明天 ';		break;
							
						}								
						traceNo.html('').append('<div class="icon-chase-mark">' + dateNotie + ' ' + rowDom.find('.trace-row-number').text().substr(0,8) + '</div>');
						dateNotieNumber ++;
						
					}				
					traceLastNumber = tracenumber.substr(0,8);		
				
							
				}	
				else{//没有勾选时状态
					rowDom.find('.trace-row-money').text('0');
					rowDom.find('.trace-row-multiple').val('0');					
					rowDom.find('.trace-row-multiple').attr('disabled', 'disabled').css('border-color','#CECECE'); 
					
				}					
				
						
				
			});
			
			if(!!orderData){
				lotterysNum = times * orderData['count'];
				result = {'times':times,'lotterysNum':lotterysNum,'amount':amount,'orderData':orderData,'traceData':traceData,'traceType':me.getTraceType()};
			}
			return result;
		},
		//追加或删除投注，在追号面板展开的情况下再次进行选球投注，追号相关信息追加或减少投注金额
		//isShowMessage 是否关闭提示
		updateOrder:function(isNotShowMessage){
			var me = this,orderData = Games.getCurrentGameOrder().getTotal(),tableType = me.getRowTableType(),
				maxObj = Games.getCurrentGameOrder().getOrderMaxMultiple(),maxnum = maxObj['maxnum'],
				selValue = Number(me.normalSelectMultiple.getValue()),
				inputValue = Number($('#J-trace-advance-multiple').val());
				
			me.setOrderData(orderData);
			
			//按照最新的允许设置的最大倍数，设置相关的倍数输入框和下拉框
			if(selValue > maxnum){
				me.normalSelectMultiple.setValue(maxnum);
			}
			if(inputValue > maxnum){
				$('#J-trace-advance-multiple').val(maxnum);
			}
			
			//当注单发生变化时，清空盈利追号和盈利率追号表格
			if(!isNotShowMessage && (tableType == 'trace_advanced_fanbei_a' || tableType == 'trace_advanced_fanbei_b' || tableType == 'trace_advanced_yingli_a' || tableType == 'trace_advanced_yingli_b' || tableType == 'trace_advanced_yinglilv_a' || tableType == 'trace_advanced_yinglilv_b')){
				Games.getCurrentGameMessage().show({
						type : 'normal',
						closeFun: function(){
							this.hide();
						},
						data : {
							tplData:{
								msg:'您的方案已被修改，如果需要根据最新方案进行追号，请点击生成追号计划按钮'
							}
						}
				});
			}
			//盈利追号/盈利率追号每次都清空表格
			me.getAdvancedRowTable().html('');
			

			//更新表格
			me.updateDetail(orderData['amount']);
			
			//更新界面金额
			me.updateStatistics();
		},		
		//更新详细表格单条金额
		updateDetail:function(amount){
			var me = this,trs = me.getTable().find('tr'),rowDom = null,rowAmountDom = null,rowUserGroupMoneyDom = null,rowWinTotalAmountDom = null,rowYinglilvDom = null,userGroupMoney = 0,tableType = me.getRowTableType(),advancedType;
			//console.log(me.getRowTable());
			//高级追号和普通追号表格结构不一样
			if(tableType == 'trace_advanced_yingli_a' || tableType == 'trace_advanced_yingli_b' || tableType == 'trace_advanced_yinglilv_a' || tableType == 'trace_advanced_yinglilv_b'){
				me.rebuildYinglilvRows();
			}else{
				//翻倍追号自动更新表格
				advancedType = me.getAdvancedRowTable().attr('data-type');
				if(advancedType == 'trace_advanced_fanbei_a' || advancedType == 'trace_advanced_fanbei_b'){
					trs = me.getAdvancedTable().find('tr');
					trs.each(function(){
						rowDom = $(this);
						rowMoney = rowDom.find('.trace-row-money');
						rowMultiple = Number(rowDom.find('.trace-row-multiple').val());
						rowMoney.text(me.formatMoney(rowMultiple * amount));
					});
				}
			}
			
			//普通追号每次都自动更新表格
			trs = me.getNormalTable().find('tr');
			trs.each(function(){
				rowDom = $(this);
				rowMoney = rowDom.find('.trace-row-money');
				rowMultiple = Number(rowDom.find('.trace-row-multiple').val());
				rowMoney.text(me.formatMoney(rowMultiple * amount));
			});
			

			
		},
		//计算投注内容中的预计中奖金额
		//选球内容有可能是不同的玩法内容，需要各自计算中奖将进组金额
		getWinMoney:function(){
			var me = this,orders = me.getOrderData()['orders'],i = 0,len = orders.length,winMoney = 0;
			for(;i < len;i++){
				winMoney += me.getPlayGroupMoneyByGameMethodName(orders[i]['type']) * orders[i]['moneyUnit'];
			}
			return winMoney;
		},
		//根据追号选择条件生成详细表格
		confirmSetting:function(){
			var me = this;
			me.setOrderData(Games.getCurrentGameOrder().getTotal());
			me.buildDetail();
		},
		//检测注单中是否有多奖金的玩法,在没有混投及元角模式一致情况下调用
		isMoreBounsMethod : function (){
			var me = this,orders = me.getOrderData()['orders'],
				i = 0,
				len = orders.length;
				
			for(;i < len;i++){
				type = orders[i]['type'];
				if(typeof(Games.currentIsMoreBouns['moreBouns'][type]) != 'undefined' && Games.currentIsMoreBouns['moreBouns'][type]==true){
					return orders[i].typeText;
				}				
			}
			
			return '';
		},
		//检测当前投注列表中是否全部为同一玩法
		//且元角模式一致
		isSameGameMethod:function(){
			var me = this,orders = me.getOrderData()['orders'],type = '',moneyUnit = -1;
				i = 0,
				len = orders.length;
			for(;i < len;i++){
				if(type != ''){
					if(type != orders[i]['type']){
						return false;
					}
				}else{
					type = orders[i]['type'];
				}
				
				if(moneyUnit != -1){
					if(moneyUnit != orders[i]['moneyUnit']){
						return false;
					}
				}else{
					moneyUnit = orders[i]['moneyUnit'];
				}
			}
			return true;
		},
		getSameGameMethodName:function(){
			var me = this,orders = me.getOrderData()['orders'];
			if(orders.length > 0){
				return orders[0]['type'];
			}
		},
		getSameGameMoneyUnti:function(){
			var me = this,orders = me.getOrderData()['orders'];
			if(orders.length > 0){
				return orders[0]['moneyUnit'];
			}
		},
		setOrderData:function(data){
			this.orderData = data;
		},
		getOrderData:function(){
			return this.orderData == null ? {'count':0,'amount':0,'orders':[]} : this.orderData;
		},
		//由期号获得期号在列表中的索引值
		getStartNumberIndexByNumber:function(number){
			var me = this,numberList = Games.getCurrentGame().getDynamicConfig()['gamenumbers'],len = numberList.length,i = 0;
			for(;i < len;i++){
				if(numberList[i]['number'] == number){
					return i;
				}
			}
			return -1;
		},
		getStartNumberByIndex:function(index){
			var me = this,numberList = Games.getCurrentGame().getDynamicConfig()['gamenumbers'];
			if(numberList.length > index){
				return numberList[index];
			}
			return {};
		},
		//生成追号计划详情内容
		//maxMultipleNum 如果参数中有设置该参数，则最大倍数都使用该值(用于检测倍数超出最大值后重新设置倍数)
		buildDetail:function(){
			var me = this,
				type = me.getTraceType(),
				msg = Games.getCurrentGameMessage();
			//每次获取最新的投注信息
			me.setOrderData(Games.getCurrentGameOrder().getTotal());
			orderAmount = me.getOrderData()['amount'];
			
			//投注内容为空
			if(type != 'normal' && orderAmount <= 0){
				msg.show({
					type : 'mustChoose',
					msg : '请至少选择一注投注号码！',
					data : {
						tplData : {
							msg : '请至少选择一注投注号码！'
						}
					}
				});
				return;
			}
			
			if($.isFunction(me['trace_' + type])){
				me['trace_' + type].call(me);
			}
			me.updateStatistics();
		},
		//普通追号
		trace_normal:function(){
			var me = this,
				cfg = me.defConfig,
				tpl = cfg.dataRowTemplate,
				tplArr = [],
				//类型
				type = me.getTraceType(),
				//追号期数
				times = me.getTimes(),
				//倍数
				multiple = me.getMultiple(),
				//最大倍数限制
				maxMultiple = Games.getCurrentGameOrder().getOrderMaxMultiple()['maxnum'],
				//投注金额
				orderAmount = 0,
				i = 0,
				
				//当前期
				currNumber = Games.getCurrentGame().getCurrentNumber(),
				currStr = '<span class="icon-period-current"></span>',
				//当前期文本
				currNumberText = '',
				//用户选择的开始期号
				settingStartNumber = me.traceStartNumberSelect.getValue(),
				startIndex,
				numberData,
				//期号列表长度
				numberLength = Games.getCurrentGame().getDynamicConfig()['gamenumbers'].length,
				rowData;
				
				
				
			me.setOrderData(Games.getCurrentGameOrder().getTotal());
			orderAmount = me.getOrderData()['amount'];
			
			tplArr.push(cfg.dataRowHeader);
			
			
			startIndex = me.getStartNumberIndexByNumber(settingStartNumber);
			i = startIndex;
			times += i;
			for(;i < times;i++){
				numberData = me.getStartNumberByIndex(i);
				currNumberText = numberData['number'];
				if(currNumberText == currNumber){
					currNumberText = currNumberText + currStr;
				}
				if(numberData['number']){
					rowData = {'No':i+1,'traceNumber':currNumberText,'issueCode':numberData['issueCode'],'multiple':multiple,'money':me.formatMoney(orderAmount * multiple),'publishTime':numberData['time']};
					tplArr.push(me.formatRow(tpl, rowData));
				}
			}
			me.getRowTable().html(tplArr.join(''));
			
			
			//在表格上设置最后生成列表的类型，用于区分列表类型
			me.getRowTable().attr('data-type', 'trace_normal');
			
		},
		//高级追号
		trace_advanced:function(){
			var me = this,
				type = me.getTraceType(),
				advancedType = me.getAdvancedType(),
				typeTypeType = me.getTypeTypeType(),
				fnName = 'trace_' + type + '_' + advancedType + '_' + typeTypeType,
				noticeName = '',				
				showNotice = '',
				isMoreBouns = me.isMoreBounsMethod();

			
			//盈利/盈利率追号不支持混投
			if(advancedType == 'fanbei'){}
			else if(!me.isSameGameMethod() && (advancedType == 'yingli' || advancedType == 'yinglilv') || isMoreBouns !=''){
				 if(advancedType == 'fanbei'){return;}
				if(advancedType == 'yingli'){
					noticeName = '盈利金额追号';
				}else if(advancedType == 'yinglilv'){
					noticeName = '盈利率追号';
				}else{
					noticeName = '盈利追号方式';
				}
				if(!me.isSameGameMethod()){				
					showNotice = noticeName + '不支持混投<br />请确保您的投注都为同一玩法类型<br />且元角模式一致。';
				}
				else if(isMoreBouns !=""){					
					showNotice = noticeName + '时，' + isMoreBouns + ',为多奖金玩法，不支持盈利/盈利率追号';
				}
				//清空追号期
				me.getRowTable().html('');				
				Games.getCurrentGameMessage().show({
					type : 'mustChoose',
					msg : '',
					data : {
						tplData : {
							msg : showNotice
						}
					}
				});
				return;
			}
			
			if($.isFunction(me[fnName])){
				me[fnName]();
			}
			//在表格上设置最后生成列表的类型，用于区分列表类型
			me.getRowTable().attr('data-type', fnName);
		},
		//高级追号 -- 翻倍追号 -- 间隔追号
		trace_advanced_fanbei_a:function(maxnum){
			var me = this,
				tpl = me.defConfig.dataRowTemplate,
				tplArr = [],
				//追号期数
				times = me.getTimes(),
				maxNumltipleObj = Games.getCurrentGameOrder().getOrderMaxMultiple(),
				//最大倍数限制
				maxMultiple = maxnum || maxNumltipleObj['maxnum'],
				typeTitle = Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(maxNumltipleObj['gameMethod']).join('-'),
				jiangeNum = Number($('#J-trace-advanced-fanbei-a-jiange').val()),
				//基础倍数
				multipleBase = me.getMultiple(),
				//中间运算倍数
				multiple = multipleBase,
				//间隔倍数
				multiple2 = Number($('#J-trace-advanced-fanbei-a-multiple').val()),
				testData,
				i = 0,
				//间隔临时计数器
				_i = jiangeNum,				
				
				//当前期
				currNumber = Games.getCurrentGame().getCurrentNumber(),
				currStr = '<span class="icon-period-current"></span>',
				//当前期文本
				currNumberText = '',
				//用户选择的开始期号
				settingStartNumber = me.traceStartNumberSelect.getValue(),
				startIndex,
				numberData,
				//期号列表长度
				numberLength = Games.getCurrentGame().getDynamicConfig()['gamenumbers'].length,
				rowData,
				traceLastNumber = '',//上期号
				traceNo=''; //序号列	

				tplArr.push(me.defConfig.dataRowHeader);
				
				startIndex = me.getStartNumberIndexByNumber(settingStartNumber);
				i = startIndex;
				times += i;
				numberData = me.getStartNumberByIndex(i);
				for(;i < times;i++){
					//console.log(_i);
					if(_i <= 0){
						_i = jiangeNum;
						multiple = multiple * multiple2;
					}
					_i--;
					
					
					//倍数超限时提示
					if(multiple > maxMultiple){
						Games.getCurrentGameMessage().show({
								type : 'normal',
								closeText: '确定',
								closeFun: function(){
									me.trace_advanced_fanbei_a(maxMultiple);
									me.updateStatistics();
									this.hide();
								},
								data : {
									tplData:{
										msg:'翻倍追号中的<b>['+ typeTitle +']</b>的倍数超过了最大倍数限制，系统将自动调整为最大可设置倍数'
									}
								}
						});
						if(!maxnum){
							return;
						}
					}
					
					//倍数不能超过最大允许倍数
					multiple = multiple > maxMultiple ? maxMultiple : multiple;					
					
					numberData = me.getStartNumberByIndex(i);
					if(!numberData['number']){
						break;
					}
					currNumberText = numberData['number'];
					if(currNumberText == currNumber){
						currNumberText = currNumberText + currStr;
					}
					
					rowData = {'No':traceNo,'traceNumber':currNumberText,'issueCode':numberData['issueCode'],'multiple':multiple,'money':me.formatMoney(orderAmount * multiple),'publishTime':numberData['time']};
					
					traceLastNumber = currNumberText.substr(0,8); 					
					tplArr.push(me.formatRow(tpl, rowData));
					
				}
				me.getRowTable().html(tplArr.join(''));
		},
		//高级追号 -- 翻倍追号 -- 前后追号
		trace_advanced_fanbei_b:function(){
			var me = this,
				tpl = me.defConfig.dataRowTemplate,
				tplArr = [],
				//追号期数
				times = me.getTimes(),
				//最大倍数限制
				maxMultiple = Games.getCurrentGameOrder().getOrderMaxMultiple()['maxnum'],
				jiangeNum = Number($('#J-trace-advanced-fanbei-a-jiange').val()),
				//基础倍数
				multipleBase = me.getMultiple(),
				//中间运算倍数
				multiple = 1,
				//间隔倍数
				multiple2 = Number($('#J-trace-advanced-fanbei-a-multiple').val()),
				testData,
				i = 0,
				//间隔临时计数器
				_i = jiangeNum,
				
				beforeNum = Number($('#J-trace-advanced-fanbei-b-num').val()),
				startMultiple = Number($('#J-trace-advance-multiple').val()),
				afterMultiple = Number($('#J-trace-advanced-fanbei-b-multiple').val()),
				
				
				//当前期
				currNumber = Games.getCurrentGame().getCurrentNumber(),
				currStr = '<span class="icon-period-current"></span>',
				//当前期文本
				currNumberText = '',
				//用户选择的开始期号
				settingStartNumber = me.traceStartNumberSelect.getValue(),
				startIndex,
				numberData,
				//期号列表长度
				numberLength = Games.getCurrentGame().getDynamicConfig()['gamenumbers'].length,
				rowData,
				traceLastNumber = '',//上期号
				traceNo=''; //序号列 
				
							
				tplArr.push(me.defConfig.dataRowHeader);
				
				startIndex = me.getStartNumberIndexByNumber(settingStartNumber);
				i = startIndex;
				times += i;
				numberData = me.getStartNumberByIndex(i);
				for(;i < times;i++){
					if(i < (beforeNum + startIndex)){
						multiple = startMultiple > maxMultiple ? maxMultiple : startMultiple;
					}else{
						multiple = afterMultiple > maxMultiple ? maxMultiple : afterMultiple;
					}
					
					numberData = me.getStartNumberByIndex(i);
					if(!numberData['number']){
						break;
					}
					currNumberText = numberData['number'];
					if(currNumberText == currNumber){
						currNumberText = currNumberText + currStr;
					}
					
					rowData = {'No':traceNo,'traceNumber':currNumberText,'issueCode':numberData['issueCode'],'multiple':multiple,'money':me.formatMoney(orderAmount * multiple),'publishTime':numberData['time']};
					traceLastNumber = currNumberText.substr(0,8);
					
					tplArr.push(me.formatRow(tpl, rowData));					
				}
				me.getRowTable().html(tplArr.join(''));
		},
		//高级追号 -- 盈利金额追号 -- 预期盈利金额
		trace_advanced_yingli_a:function(maxnum){
			var me = this,
				tpl = me.defConfig.dataRowTemplate,
				tplArr = [],
				//追号期数
				times = me.getTimes(),
				maxNumltipleObj = Games.getCurrentGameOrder().getOrderMaxMultiple(),
				//最大倍数限制
				maxMultiple = maxnum || maxNumltipleObj['maxnum'],
				typeTitle = Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(maxNumltipleObj['gameMethod']).join('-'),
				//基础倍数
				multipleBase = me.getMultiple(),
				//中间运算倍数
				multiple = 1,
				testData,
				i = 0,
				
				
			
				//玩法类型
				gameMethodType = me.getSameGameMethodName(),
				//每期必须要达到的盈利金额
				yingliMoney = Number($('#J-trace-advanced-yingli-a-money').val()),
				//元角模式
				moneyUnit = me.getSameGameMoneyUnti(),
				//用户奖金组中该玩法中每注的中奖金额
				userGroupMoney = me.getWinMoney(),
				//基础倍数，盈利追号和盈利率追号通过修改倍数达到预期值，所以初始值设置为1
				//multipleBase = 1,
				//启用另外表头和行模板
				tpl = me.defConfig.dataRowYingliTemplate,
				orders = me.getOrderData()['orders'],
				//投注组本金
				orderAmount = 0,
				//所有投注本金
				orderTotalAmount = 0,
				//中奖总金额
				winTotalAmout = 0,
				//盈利率
				yinglilv = 0,
				
				
				//当前期
				currNumber = Games.getCurrentGame().getCurrentNumber(),
				currStr = '<span class="icon-period-current"></span>',
				//当前期文本
				currNumberText = '',
				//用户选择的开始期号
				settingStartNumber = me.traceStartNumberSelect.getValue(),
				startIndex,
				numberData,
				//期号列表长度
				numberLength = Games.getCurrentGame().getDynamicConfig()['gamenumbers'].length,
				rowData,
				traceLastNumber = '',//上期号
				traceNo=''; //序号列 
				

			tplArr.push(me.defConfig.dataRowYingliHeader);
			
			startIndex = me.getStartNumberIndexByNumber(settingStartNumber);
			i = startIndex;
			times += i;
			numberData = me.getStartNumberByIndex(i);
			for(;i < times;i++){
				orderAmount = 0;
				winTotalAmout = 0;
				//基础倍数，盈利追号和盈利率追号通过修改倍数达到预期值，所以初始值设置为1
				//multipleBase = 1;
				//计算预计中奖金额
				$.each(orders, function(i){
					var order = this,
						num = order['num'],
						price = order['onePrice'],
						multiple = order['multiple'],
						//本金
						amount = num * multiple * price * moneyUnit,
						//单注中奖金额
						winAmout = userGroupMoney * multiple;	
						
						//该投注组盈利金额
						winTotalAmout += winAmout;
								
						orderAmount += amount;
				});
				
				
				//获得倍数
				//multipleBase = me.getMultipleByMoney(userGroupMoney, yingliMoney, orderAmount, orderTotalAmount);
				//无法达到预期目标
				if(multipleBase < 0){
					Games.getCurrentGameMessage().show({
						type : 'normal',
							closeText: '确定',
							closeFun: function(){
								this.hide();
							},
							data : {
								tplData:{
									msg:'您设置的盈利金额追号参数过高，无法达到您预期的目标值，请您重新修改参数设置。'
								}
							}
					});
					//清空之前表格数据
					me.getRowTable().html('');
					return;
				}
				
				//倍数超限时提示
				if(multipleBase > maxMultiple){
					Games.getCurrentGameMessage().show({
						type : 'normal',
							closeText: '确定',
							closeFun: function(){
								me.trace_advanced_yingli_a(maxMultiple);
								me.updateStatistics();
								this.hide();
							},
							data : {
								tplData:{
									msg:'盈利金额追号中的<b>['+ typeTitle +']</b>的倍数超过了最大倍数限制，系统将自动调整为最大可设置倍数'
								}
							}
					});
					multipleBase = maxnum;
					if(!maxnum){
						return;
					}else{
						multipleBase = maxnum;
					}
				}
				
				//花费本金
				orderAmount *= multipleBase;
				//累计本金
				orderTotalAmount += orderAmount;
				//利润减去累计花费
				winTotalAmout = (userGroupMoney * multipleBase) - orderTotalAmount;
				//盈利率
				yinglilv = winTotalAmout/orderTotalAmount;
				//小于当前设置将不生成单
				if(winTotalAmout < yingliMoney){
					break;
				}
				numberData = me.getStartNumberByIndex(i);
				if(!numberData['number']){
					break;
				}
				currNumberText = numberData['number'];
				if(currNumberText == currNumber){
					currNumberText = currNumberText + currStr;
				}
						
				rowData = {'No':traceNo,'traceNumber': currNumberText,
							'issueCode':numberData['issueCode'],
							'multiple':multipleBase,
							'money':me.formatMoney(orderAmount),
							'userGroupMoney':me.formatMoney(userGroupMoney * multipleBase),
							'winTotalAmout':me.formatMoney(winTotalAmout),
							'yinglilv':Number(yinglilv*100).toFixed(2)
							};
							
				traceLastNumber = currNumberText.substr(0,8);
				tplArr.push(me.formatRow(tpl, rowData)); 
			}
			me.getRowTable().html(tplArr.join(''));
			
		},
		//高级追号 -- 盈利金额追号 -- 前后预期盈利金额
		trace_advanced_yingli_b:function(maxnum){
			var me = this,
				tpl = me.defConfig.dataRowTemplate,
				tplArr = [],
				//追号期数
				times = me.getTimes(),
				maxNumltipleObj = Games.getCurrentGameOrder().getOrderMaxMultiple(),
				//最大倍数限制
				maxMultiple = maxnum || maxNumltipleObj['maxnum'],
				typeTitle = Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(maxNumltipleObj['gameMethod']).join('-'),
				//基础倍数
				multipleBase = me.getMultiple(),
				//中间运算倍数
				multiple = 1,
				testData,
				i = 0,
				
				
				//玩法类型
				gameMethodType = me.getSameGameMethodName(),
				//前几期
				yingliNum = Number($('#J-trace-advanced-yingli-b-num').val()),
				//第一期必须要达到的盈利金额
				yingliMoney = Number($('#J-trace-advanced-yingli-b-money1').val()),
				//第二期必须要达到的盈利金额
				yingliMoney2 = Number($('#J-trace-advanced-yingli-b-money2').val()),
				//元角模式
				moneyUnit = me.getSameGameMoneyUnti(),
				//用户奖金组中该玩法中每注的中奖金额
				userGroupMoney = me.getWinMoney(),
				//基础倍数，盈利追号和盈利率追号通过修改倍数达到预期值，所以初始值设置为1
				multipleBase = 1,
				//启用另外表头和行模板
				tpl = me.defConfig.dataRowYingliTemplate,
				orders = me.getOrderData()['orders'],
				//投注组本金
				orderAmount = 0,
				//所有投注本金
				orderTotalAmount = 0,
				//中奖总金额
				winTotalAmout = 0,
				//盈利率
				yinglilv = 0,
				
				
				
				//当前期
				currNumber = Games.getCurrentGame().getCurrentNumber(),
				currStr = '<span class="icon-period-current"></span>',
				//当前期文本
				currNumberText = '',
				//用户选择的开始期号
				settingStartNumber = me.traceStartNumberSelect.getValue(),
				startIndex,
				numberData,
				//期号列表长度
				numberLength = Games.getCurrentGame().getDynamicConfig()['gamenumbers'].length,
				rowData,
				traceLastNumber = '',//上期号
				traceNo=''; //序号列 
				
				
				
				tplArr.push(me.defConfig.dataRowYingliHeader);
				
				startIndex = me.getStartNumberIndexByNumber(settingStartNumber);
				i = startIndex;
				times += i;
				numberData = me.getStartNumberByIndex(i);
				for(;i < times;i++){
					if((i+1) > (yingliNum + startIndex)){
						yingliMoney = yingliMoney2;
					}
					orderAmount = 0;
					winTotalAmout = 0;
					//基础倍数，盈利追号和盈利率追号通过修改倍数达到预期值，所以初始值设置为1
					multipleBase = 1;
					//计算预计中奖金额
					$.each(orders, function(i){
						var order = this,
						num = order['num'],
						price = order['onePrice'],
						multiple = order['multiple'],
						//本金
						amount = num * multiple * price * moneyUnit,
						//单注中奖金额
						winAmout = userGroupMoney * multiple;	
						//该投注组盈利金额
						winTotalAmout += winAmout;
						orderAmount += amount;
					});	
							
					//获得倍数
					multipleBase = me.getMultipleByMoney(userGroupMoney, yingliMoney, orderAmount, orderTotalAmount);
					//无法达到预期目标
					if(multipleBase < 0){
						Games.getCurrentGameMessage().show({
							type : 'normal',
								closeText: '确定',
								closeFun: function(){
									this.hide();
								},
								data : {
									tplData:{
										msg:'您设置的盈利金额追号参数过高，无法达到您预期的目标值，请您重新修改参数设置。'
									}
								}
						});
						//清空之前表格数据
						me.getRowTable().html('');
						return;
					}
					
					
					//倍数超限时提示
					if(multipleBase > maxMultiple){
						Games.getCurrentGameMessage().show({
							type : 'normal',
								closeText: '确定',
								closeFun: function(){
									me.trace_advanced_yingli_b(maxMultiple);
									me.updateStatistics();
									this.hide();
								},
								data : {
									tplData:{
										msg:'盈利金额追号中的<b>['+ typeTitle +']</b>的倍数超过了最大倍数限制，系统将自动调整为最大可设置倍数'
									}
								}
						});
						multipleBase = maxnum;
						if(!maxnum){
							return;
						}else{
							multipleBase = maxnum;
						}
					}
					
					
					//花费本金
					orderAmount *= multipleBase;
					//累计本金
					orderTotalAmount += orderAmount;
					//利润减去累计花费
					winTotalAmout = (userGroupMoney * multipleBase) - orderTotalAmount;
					//盈利率
					yinglilv = winTotalAmout/orderTotalAmount;


					numberData = me.getStartNumberByIndex(i);
					if(!numberData['number']){
						break;
					}
					currNumberText = numberData['number'];
					if(currNumberText == currNumber){
						currNumberText = currNumberText + currStr;
					}
					
					rowData = {'No':traceNo,'traceNumber': currNumberText,
								'issueCode':numberData['issueCode'],
								'multiple':multipleBase,
								'money':me.formatMoney(orderAmount),
								'userGroupMoney':me.formatMoney(userGroupMoney * multipleBase),
								'winTotalAmout':me.formatMoney(winTotalAmout),
								'yinglilv':Number(yinglilv*100).toFixed(2)
							};
					traceLastNumber = currNumberText.substr(0,8); 					
					tplArr.push(me.formatRow(tpl, rowData));
				}
				
				me.getRowTable().html(tplArr.join(''));
				
		},
		//高级追号 -- 盈利率追号 -- 预期盈利率
		trace_advanced_yinglilv_a:function(maxnum){
			var me = this,
				tpl = me.defConfig.dataRowTemplate,
				tplArr = [],
				//追号期数
				times = me.getTimes(),
				maxNumltipleObj = Games.getCurrentGameOrder().getOrderMaxMultiple(),
				//最大倍数限制
				maxMultiple = maxnum || maxNumltipleObj['maxnum'],
				typeTitle = Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(maxNumltipleObj['gameMethod']).join('-'),
				//基础倍数
				multipleBase = me.getMultiple(),
				//中间运算倍数
				multiple = 1,
				testData,
				i = 0,
				
				
				//玩法类型
				gameMethodType = me.getSameGameMethodName(),
				//每期必须要达到的盈利率
				yinglilv = Number($('#J-trace-advanced-yinglilv-a').val())/100,
				yingliMoney = 0,
				//元角模式
				moneyUnit = me.getSameGameMoneyUnti(),
				//用户奖金组中该玩法中每注的中奖金额
				userGroupMoney = me.getWinMoney(),
				//基础倍数，盈利追号和盈利率追号通过修改倍数达到预期值，所以初始值设置为1
				//multipleBase = 1,
				//启用另外表头和行模板
				tpl = me.defConfig.dataRowYingliTemplate,
				orders = me.getOrderData()['orders'],
				//投注组本金
				orderAmount = 0,
				//所有投注本金
				orderTotalAmount = 0,
				//中奖总金额
				winTotalAmout = 0,
				
				
			
				//当前期
				currNumber = Games.getCurrentGame().getCurrentNumber(),
				currStr = '<span class="icon-period-current"></span>',
				//当前期文本
				currNumberText = '',
				//用户选择的开始期号
				settingStartNumber = me.traceStartNumberSelect.getValue(),
				startIndex,
				numberData,
				//期号列表长度
				numberLength = Games.getCurrentGame().getDynamicConfig()['gamenumbers'].length,
				rowData,
				traceLastNumber = '',//上期号
				traceNo=''; //序号列 
				
				
				tplArr.push(me.defConfig.dataRowYingliHeader);
				
				startIndex = me.getStartNumberIndexByNumber(settingStartNumber);
				i = startIndex;
				times += i;
				numberData = me.getStartNumberByIndex(i);
				for(;i < times;i++){
					orderAmount = 0;
					winTotalAmout = 0;
					//基础倍数，盈利追号和盈利率追号通过修改倍数达到预期值，所以初始值设置为1
					//multipleBase = 1;
					//计算预计中奖金额
					$.each(orders, function(i){
						var order = this,
						num = order['num'],
						price = order['onePrice'],
						multiple = order['multiple'],
						//本金
						amount = num * multiple * price * moneyUnit,
						//单注中奖金额
						winAmout = userGroupMoney * multiple;	
						//该投注组预计中奖金额
						winTotalAmout += winAmout;
						//订单本金
						orderAmount += amount;
					});
					
					//multipleBase = me.getMultipleByYinglilv(yinglilv, userGroupMoney, orderAmount, orderTotalAmount);
					//无法达到预期目标
					if(multipleBase < 0){
						Games.getCurrentGameMessage().show({
							type : 'normal',
								closeText: '确定',
								closeFun: function(){
									this.hide();
								},
								data : {
									tplData:{
										msg:'盈利率追号无法到达您预期设定的目标值，请修改您的设置'
									}
								}
						});
						//清空之前表格数据
						me.getRowTable().html('');
						return;
					}

					//倍数超限时提示
					if(multipleBase > maxMultiple){
						Games.getCurrentGameMessage().show({
							type : 'normal',
								closeText: '确定',
								closeFun: function(){
									me.trace_advanced_yinglilv_a(maxMultiple);
									me.updateStatistics();
									this.hide();
								},
								data : {
									tplData:{
										msg:'盈利率追号中的<b>['+ typeTitle +']</b>的倍数超过了最大倍数限制，系统将自动调整为最大可设置倍数'
									}
								}
						});
						multipleBase = maxnum;
						if(!maxnum){
							return;
						}else{
							multipleBase = maxnum;
						}
					}

					
					//花费本金
					orderAmount *= multipleBase;
					//累计本金
					orderTotalAmount += orderAmount;
					//利润减去累计花费
					winTotalAmout = (userGroupMoney * multipleBase) - orderTotalAmount;
					
					
					//盈利率追号.未达到当前预期盈利率值，将不展示
					if(Number(winTotalAmout/orderTotalAmount) < yinglilv){
						break;
					}
					numberData = me.getStartNumberByIndex(i);
					if(!numberData['number']){
						break;
					}
					currNumberText = numberData['number'];
					if(currNumberText == currNumber){
						currNumberText = currNumberText + currStr;
					}
					
					rowData = {'No':traceNo,'traceNumber': currNumberText,
								'issueCode':numberData['issueCode'],
								'multiple':multipleBase,
								'money':me.formatMoney(orderAmount),
								'userGroupMoney':me.formatMoney(userGroupMoney * multipleBase),
								'winTotalAmout':me.formatMoney(winTotalAmout),
								'yinglilv':Number(winTotalAmout/orderTotalAmount*100).toFixed(2)
					};
							
					tplArr.push(me.formatRow(tpl, rowData));
				}
				traceLastNumber = currNumberText.substr(0,8);
				me.getRowTable().html(tplArr.join(''));
				
		},
		//高级追号 -- 盈利率追号 -- 前后预期盈利率
		trace_advanced_yinglilv_b:function(maxnum){
			var me = this,
				tpl = me.defConfig.dataRowTemplate,
				tplArr = [],
				//追号期数
				times = me.getTimes(),
				maxNumltipleObj = Games.getCurrentGameOrder().getOrderMaxMultiple(),
				//最大倍数限制
				maxMultiple = maxnum || maxNumltipleObj['maxnum'],
				typeTitle = Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(maxNumltipleObj['gameMethod']).join('-'),
				//基础倍数
				multipleBase = me.getMultiple(),
				//中间运算倍数
				multiple = 1,
				testData,
				i = 0,
				
				
				//玩法类型
				gameMethodType = me.getSameGameMethodName(),
				//前期数量期次数量
				yinglilvNum = Number($('#J-trace-advanced-yinglilv-b-num').val()),
				//前期必须达到的盈利率
				yinglilv1 = Number($('#J-trace-advanced-yingli-b-yinglilv1').val())/100,
				//后期必须达到的盈利率
				yinglilv2 = Number($('#J-trace-advanced-yingli-b-yinglilv2').val())/100,
				//盈利率变量
				yinglilvVar = 0,
				yingliMoney = 0,
				//元角模式
				moneyUnit = me.getSameGameMoneyUnti(),
				//用户奖金组中该玩法中每注的中奖金额
				userGroupMoney = me.getWinMoney(),
				//基础倍数，盈利追号和盈利率追号通过修改倍数达到预期值，所以初始值设置为1
				multipleBase = 1,
				//启用另外表头和行模板
				tpl = me.defConfig.dataRowYingliTemplate,
				orders = me.getOrderData()['orders'],
				//投注组本金
				orderAmount = 0,
				//所有投注本金
				orderTotalAmount = 0,
				//中奖总金额
				winTotalAmout = 0,
				
				
				//当前期
				currNumber = Games.getCurrentGame().getCurrentNumber(),
				currStr = '<span class="icon-period-current"></span>',
				//当前期文本
				currNumberText = '',
				//用户选择的开始期号
				settingStartNumber = me.traceStartNumberSelect.getValue(),
				startIndex,
				numberData,
				//期号列表长度
				numberLength = Games.getCurrentGame().getDynamicConfig()['gamenumbers'].length,
				rowData,
				traceLastNumber = '',//上期号
				traceNo=''; //序号列 
				
				
				
				tplArr.push(me.defConfig.dataRowYingliHeader);
				
				startIndex = me.getStartNumberIndexByNumber(settingStartNumber);
				i = startIndex;
				times += i;
				numberData = me.getStartNumberByIndex(i);
				for(;i < times;i++){
					orderAmount = 0;
					winTotalAmout = 0;
					//基础倍数，盈利追号和盈利率追号通过修改倍数达到预期值，所以初始值设置为1
					multipleBase = 1;
					//计算预计中奖金额
					$.each(orders, function(i){
						var order = this,
							num = order['num'],
							price = order['onePrice'],
							multiple = order['multiple'],
							//本金
							amount = num * multiple * price * moneyUnit,
							//单注中奖金额
							winAmout = num * userGroupMoney * multiple;
								
						//该投注组盈利金额
						winTotalAmout += winAmout;
						orderAmount += amount;
					});
					//前期盈利率
					if(i < (yinglilvNum + startIndex)){
						yinglilvVar = yinglilv1;
					}else{
					//后期盈利率
						yinglilvVar = yinglilv2;
					}

							
					//multipleBase = me.getMultipleByYinglilv(yinglilvVar, userGroupMoney, orderAmount, orderTotalAmount);
					//无法达到预期目标
					if(multipleBase < 0){
						Games.getCurrentGameMessage().show({
							type : 'normal',
								closeText: '确定',
								closeFun: function(){
									this.hide();
								},
								data : {
									tplData:{
										msg:'盈利率追号无法到达您预期设定的目标值，请修改您的设置'
									}
								}
						});
						//清空之前表格数据
						me.getRowTable().html('');
						return;
					}

					//倍数超限时提示
					if(multipleBase > maxMultiple){
						Games.getCurrentGameMessage().show({
							type : 'normal',
								closeText: '确定',
								closeFun: function(){
									me.trace_advanced_yinglilv_a(maxMultiple);
									me.updateStatistics();
									this.hide();
								},
								data : {
									tplData:{
										msg:'盈利率追号中的<b>['+ typeTitle +']</b>的倍数超过了最大倍数限制，系统将自动调整为最大可设置倍数'
									}
								}
						});
						multipleBase = maxnum;
						if(!maxnum){
							return;
						}else{
							multipleBase = maxnum;
						}
					}

					//花费本金
					orderAmount *= multipleBase;
					//累计本金
					orderTotalAmount += orderAmount;
					//利润减去累计花费
					winTotalAmout = (userGroupMoney * multipleBase) - orderTotalAmount;


					numberData = me.getStartNumberByIndex(i);
					if(!numberData['number']){
						break;
					}
					currNumberText = numberData['number'];
					if(currNumberText == currNumber){
						currNumberText = currNumberText + currStr;
					}
					
					rowData = {'No':traceNo,'traceNumber': currNumberText,
								'issueCode':numberData['issueCode'],
								'multiple':multipleBase,
								'money':me.formatMoney(orderAmount),
								'userGroupMoney':me.formatMoney(userGroupMoney * multipleBase),
								'winTotalAmout':me.formatMoney(winTotalAmout),
								'yinglilv':Number(winTotalAmout/orderTotalAmount*100).toFixed(2)
							};
					traceLastNumber = currNumberText.substr(0,8); 	
					tplArr.push(me.formatRow(tpl, rowData));
				}
				
				me.getRowTable().html(tplArr.join(''));
				
		},
		
		//通过固定盈利率得到倍数
		//yinglilv 指定要达到的盈利率
		//userGroupMoney 单注中奖金额
		//amount 单笔投注成本
		//amountAll 累计投注成本
		getMultipleByYinglilv:function(yinglilv, userGroupMoney, amount, amountAll){
			//尝试倍数达到盈利率
			var i = 1,mx = 100000;
			for(;i < mx;i++){
				if((userGroupMoney*i - amount*i - amountAll)/(amount*i + amountAll) >= yinglilv){
					//console.log((userGroupMoney*i - amount*i - amountAll)/(amount*i + amountAll));
					return i;
				}
			}
			//无法达到目标盈利率
			return -1;
		},
		//通过固定盈利金额得到倍数
		//userGroupMoney 单注中奖金额
		//yingliMoney 需要达到的盈利金额
		//amount 单笔投注成本
		//amountAll 累计投注成本
		getMultipleByMoney:function(userGroupMoney, yingliMoney, amount, amountAll){
			var i = 1,mx = 100000;
			for(;i < mx;i++){
				if((userGroupMoney * i - amountAll - amount * i) > yingliMoney){
					return i;
				}
			}
			//无法达到目标
			return -1;
		},
		//根据玩法名称获得用户当前将进组中奖金额(以元模式为单位)
		//
		getPlayGroupMoneyByGameMethodName:function(name){
			var groupMoney = null;
				try{
					Games.getCurrentGame().getDynamicConfig()['gamelimit'][name]['usergroupmoney'];
				}catch(err){}
				
			var	type = Games.getCurrentGame().getCurrentGameMethod().getGameMethodName(),
				url = Games.getCurrentGame().getGameConfig().getInstance().getBetAwardUrl() + '?type=' + type + '&extent=currentFre&line=5&lenth=30';
	
			if(groupMoney && Number(groupMoney) > 0){
				return groupMoney;
			}else if(Games.cacheData['gameBonus'][url] && Games.cacheData['gameBonus'][url] > 0){  //当前奖期结束取缓存中的奖金
				return Number(Games.cacheData['gameBonus'][url]);
			}else{
				alert(Games.getCurrentGame().getGameConfig().getInstance().getTitleByName(name) + '玩法类型奖金组读取失败，请刷新页面重试！');
				
			}
		},
		formatRow:function(tpl, data){
			var me = this,o = data,p,reg;
			for(p in o){
				if(o.hasOwnProperty(p)){
					reg = RegExp('<#=' + p + '#>', 'g');
					tpl = tpl.replace(reg, o[p]);
				}
			}
			return tpl;
		},
		//将数字保留两位小数并且千位使用逗号分隔
		formatMoney:function(num){
			var num = Number(num),
				re = /(-?\d+)(\d{3})/;
				
			if(Number.prototype.toFixed){
				num = (num).toFixed(2);
			}else{
				num = Math.round(num*100)/100
			}
			num  =  '' + num;
			while(re.test(num)){
				num = num.replace(re,"$1,$2");
			}
			return num;  
		},
		getAdvancedTable:function(){
			var me = this;
			return me._advancedTable || (me._advancedTable = $('#J-trace-table-advanced'));
		},
		getAdvancedRowTable:function(){
			var me = this;
			return me._advancedTableContainer || (me._advancedTableContainer = $('#J-trace-table-advanced-body'));
		},
		getNormalTable:function(){
			var me = this;
			return me._table || (me._table = $('#J-trace-table'));
		},
		getNormalRowTable:function(){
			var me = this;
			return me._tableContainer || (me._tableContainer = $('#J-trace-table-body'));
		},
		getTable:function(){
			var me = this;
			if(me.isAdvanced()){
				return me._advancedTable || (me._advancedTable = $('#J-trace-table-advanced'));
			}
			return me._table || (me._table = $('#J-trace-table'));
		},
		getRowTable:function(){
			var me = this;
			if(me.isAdvanced()){
				return me._advancedTableContainer || (me._advancedTableContainer = $('#J-trace-table-advanced-body'));
			}
			return me._tableContainer || (me._tableContainer = $('#J-trace-table-body'));
		},
		setCurrentTraceNumber:function(v){
			var me = this;
			me.currentTraceNumber = v;
		},
		getCurrentTraceNumber:function(){
			return me.currentTraceNumber;
		},
		//追号起始期号
		setTraceStartNumber:function(v){
			var me = this;
			me.traceStartNumber = v;
		},
		getTraceStartNumber:function(){
			return me.traceStartNumber;
		},
		getMultiple:function(){
			var me = this;
			if(me.isAdvanced()){
				return me.getAdvancedMultiple();
			}
			return me.getNormalMultiple();
		},
		getNormalMultiple:function(){
			return Number(this.normalSelectMultiple.getValue());
		},
		getAdvancedMultiple:function(){
			return Number($('#J-trace-advance-multiple').val());	
		},
		setIsWinStop:function(v){
			var me = this;
			this.isWinStop = !!v;
		},
		getTimes:function(){
			var me = this;
			if(me.isAdvanced()){
				return me.getAdvancedTimes();
			}
			return Number($('#J-trace-normal-times').val());
		},
		//获取追号期数(高级)
		getAdvancedTimes:function(){
			return Number($('#J-trace-advanced-times').val());
		},
		//是否为高级追号
		isAdvanced:function(){
			var me = this;
			return me.traceType == 'advanced';
		},
		//切换追号类型
		//普通追号 'normal' 或 为空
		//高级追号 'advanced'
		setTraceType:function(type){
			var me = this;
			if(type != me.traceType){
				type = !type ? 'normal' :  type;
				me.traceType = type;
			}
		},
		getTraceType:function(){
			return this.traceType;
		},
		//获取已生成列表的追号类型
		getRowTableType:function(){
			var me = this;
			return me.getRowTable().attr('data-type');
		},
		//清空已生成的列表
		emptyRowTable:function(){
			var me = this;
			$('#J-trace-table-body').html('');
			$('#J-trace-table-advanced-body').html('');
			me.updateStatistics();
		},
		show:function(){
			var me = this;
			//展开追号区后修改所有投注倍数为1倍
			Games.getCurrentGameOrder().editMultiples(1);
			me.setOrderData(Games.getCurrentGameOrder().getTotal());
			//展开后隐藏选球区域的倍数选择框
			Games.getCurrentGameStatistics().getMultipleDom().hide();
			Games.getCurrentGameStatistics().getMultipleTextDom().show();
			
			me.panel.show();
			me.isOpenPanel = true;
			
			//渲染表格
			me.buildDetail();
		},
		hide:function(){
			var me = this;
			Games.getCurrentGameOrder().restoreMultiples();
			//收起后显示选球区域的倍数选择框
			Games.getCurrentGameStatistics().getMultipleDom().show();
			Games.getCurrentGameStatistics().getMultipleTextDom().hide();
			
			me.panel.hide();
			me.isOpenPanel = false;
			
			me.reSetTab();
			
			me.emptyRowTable();
			//追号状态复位
			$('#J-trace-switch').get(0).checked = false;	
			$('#J-trace-iswinstop-panel').hide();
			$('#J-trace-iswintimesstop-panel').show();
			$('#J-trace-iswintimesstop').get(0).checked = false;
			this.traceTips.hide(); 
		},
		//复位追号区的tab以及相关输入框默认值
		reSetTab:function(){
			var me = this,
				tab1 = me.TraceTab,
				tab2 = me.TraceAdvancedTab;
			//追号tab
			tab1.triggers.removeClass(tab1.defConfig.currClass);
			tab1.triggers.eq(0).addClass(tab1.defConfig.currClass);
			tab1.panels.removeClass(tab1.defConfig.currPanelClass);
			tab1.panels.eq(0).addClass(tab1.defConfig.currPanelClass);
			tab1.index = 0;
			//高级追号tab
			tab2.triggers.removeClass(tab2.defConfig.currClass);
			tab2.triggers.eq(0).addClass(tab2.defConfig.currClass);
			tab2.panels.removeClass(tab2.defConfig.currPanelClass);
			tab2.panels.eq(0).addClass(tab2.defConfig.currPanelClass);
			tab2.index = 0;
			
			//恢复输入框默认值
			$('#J-trace-normal-times').val(10);
			$('#J-function-select-tab .function-select-title li').removeClass('current').eq(1).addClass('current');
			me.normalSelectMultiple.setValue(1);
			
			$('#J-trace-advanced-times').val(10);
			$('#J-trace-advance-multiple').val(1);
			$('#J-trace-advanced-fanbei-a-jiange').val(2);
			$('#J-trace-advanced-fanbei-a-multiple').val(2);
			$('#J-trace-advanced-fanbei-b-num').val(5);
			$('#J-trace-advanced-fanbei-b-multiple').val(3);
			$('#J-trace-advanced-yingli-a-money').val(100);
			$('#J-trace-advanced-yingli-b-num').val(2);
			$('#J-trace-advanced-yingli-b-money1').val(100);
			$('#J-trace-advanced-yingli-b-money2').val(50);
			$('#J-trace-advanced-yinglilv-a').val(10);
			$('#J-trace-advanced-yinglilv-b-num').val(5);
			$('#J-trace-advanced-yingli-b-yinglilv1').val(30);
			$('#J-trace-advanced-yingli-b-yinglilv2').val(10);
			
			
			//设置对应的tab标记属性
			me.setTraceType('normal');
			me.advancedType = me.defConfig.advancedTypeHas[0];
			me.typeTypeType = 'a';
			
			//恢复默认的高级选项
			$('#J-trace-advanced-type-panel').find('input[type="radio"]').each(function(i){
				if((i+1)%2 != 0){
					var el = $(this),par = el.parent(),pars = par.parent().children(),_par;
					this.checked = true;
					pars.each(function(i){
						_par = pars.get(i);
						if(par.get(0) != _par){
							$(_par).find('input[type="text"]').attr('disabled', 'disabled');
						}else{
							$(_par).find('input[type="text"]').attr('disabled', false);
						}
					});
				}
			});
			
		}
	};
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host[name] = Main;
	
})(phoenix, "GameTrace", phoenix.Event);










