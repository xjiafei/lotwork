
//游戏订单模块
(function(host, name, Event, undefined){
	var defConfig = {
		//主面板dom
		containerDom:'#J-balls-order-container',
		//总注数dom
		totalLotterysNumDom:'#J-gameOrder-lotterys-num',
		//总金额dom
		totalAmountDom:'#J-gameOrder-amount',
		//当注单被选中时的样式
		selectedClass:'game-order-current',
		//每行投注记录html模板
		rowTemplate: '<li data-param="action=reselect&id=<#=id#>" id="gameorder-<#=id#>"><div class="result"><span class="moneyUnitText"><#=moneyUnitText#></span><span class="bet"><#=num#>注</span><span class="multiple"><#=multiple#>倍</span><span class="price"><span>&yen;</span><#=amountText#></span><span class="close"><a data-param="action=del&id=<#=id#>" href="javascript:void(0);" title="删除">删除</a></span></div><span>[<#=typeText#>]</span><span><#=lotterysText#></span></li>',
		//显示内容截取字符串长度
		lotterysTextLength:40,
		//投注按钮Dom
		addOrderDom : '#J-add-order'
	},
	
	//获取当前游戏
	Games = host.Games,
	instance,
	orderID = 1,
	gameOrderInstance =  host.GameOrder,
	Ts = Object.prototype.toString;
	//将来仿url类型的参数转换为{}对象格式，如 q=wahaha&key=323444 转换为 {q:'wahaha',key:'323444'}
	//所有参数类型均为字符串
	var formatParam = function(param){
		var arr = $.trim(param).split('&'),i = 0,len = arr.length,
			paramArr,
			result = {};
		for(;i < len;i++){
			paramArr = arr[i].split('=');
			if(paramArr.length > 0){
				if(paramArr.length == 2){
					result[paramArr[0]] = paramArr[1];
				}else{
					result[paramArr[0]] = '';
				}
			}
		}
		return result;
	};
	
	var pros = {
		//添加一条投注
		//order 参数可为单一对象或数组
		//接收参数 order {type:'玩法类型',lotterys:'投注具体数据',moneyUnit:'元角模式',num:'注数',multiple:'倍数',onePrice:'单价'}
		add:function(order){
			var me = this,
				html = '',
				sameIndex = -1,
				tpl = me.defConfig.rowTemplate,
				i = 0,
				j = 0,
				maxnum = Games.getCurrentGameStatistics().getMultipleLimit(),
				traceIsOpen = Games.getCurrentGameTrace().isOpen(),
				allMultiple = Games.getCurrentGameStatistics().getAllMultiple(),
				len,
				len2;
			
			me.fireEvent('beforeAdd', order);
			
			if(order['lotterys'] && order['lotterys'].length > 0){
				
					//判断是否为编辑注单
					if(me.currentSelectId > 0){
						order['id'] = me.currentSelectId;
					}else{
						sameIndex = me.checkData(order);
						//发现有相同注，则增加倍数
						if(sameIndex != -1){
							Games.getCurrentGameMessage().show({
									type : 'normal',
									closeText: '确定',
									closeFun: function(){
										me.addMultiple(order['multiple'], sameIndex);
										this.hide();
									},
									data : {
										tplData:{
											msg:'您选择的号码在号码篮已存在，将直接进行倍数累加'
										}
									}
							});
							return;
						}
						//新增唯一id标识
						order['id'] = orderID++;
					}
					
					//如果追号面板被打开，则修改倍数为1倍
					order['multiple'] = !!traceIsOpen ? 1 : order['multiple'];
					order['amountText'] = me.formatMoney(order['num'] * order['moneyUnit'] * order['multiple'] * order['onePrice']);
					//如果追号面板打开，并且正在操作盈利追号或盈利率追号，则不允许进行混投
					//清空所有追号列表
					if(traceIsOpen && (Games.getCurrentGameTrace().getRowTableType() == 'yingli' || Games.getCurrentGameTrace().getRowTableType() == 'yinglilv')){
						//不允许混投
						for(j = 0,len2 = me.orderData.length;j < len2;j++){
							if(me.orderData[j]['type'] != order['type'] || me.orderData[j]['moneyUnit'] != order['moneyUnit']){
								alert('盈利追号和盈利率追号不允许混投，\n 请确保玩法类型和元角模式一致');
								return;
							}
						}
					}
					//原始选球数据
					order['postParameter'] = Games.getCurrentGame().getCurrentGameMethod().makePostParameter(order['original'], order);
					//倍数备份，用于恢复原始选择的倍数
					order['oldMultiple'] = order['multiple'];
					//注：金额随倍数.注数，联动
					order['multiple'] = order['multiple'] * allMultiple;
					order['amount'] = me.formatMoney(order['onePrice'] * order['multiple'] *  order['moneyUnit'] * order['num']);
					order['amountText'] = order['amount'];
						
					html = me.formatRow(tpl, me.rebuildData(order));
		
					//是修改，则替换原有的order对象
					if(me.currentSelectId > 0){
						me.replaceOrder(order['id'], order);
					}else{
						me.addData(order);
					}
					
			}else{
				return;
			}
				
			
			//如果是修改注单则删除原有的dom
			if(me.currentSelectId > 0){
				$(html).replaceAll($('#gameorder-' + me.currentSelectId));
				me.cancelSelectOrder();
			}else{
				$(html).prependTo(me.container);
			}	
			
			//复位选球区
			Games.getCurrentGame().getCurrentGameMethod().reSet();
			
			Games.getCurrentGameStatistics().reSet();
			
			me.updateData();
			me.fireEvent('afterAdd', order);
		}
	};

	var Main = host.Class(pros, gameOrderInstance);
		Main.defConfig = defConfig;
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host[name] = Main;
	
})(phoenix, "GameMMCOrder");









