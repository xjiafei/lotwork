
//游戏订单模块
(function(host, name, Event, undefined){
	var defConfig = {
		//主面板dom
		mainDom:'#J-gameOrder-list',
		//list
		containerDom:'#J-balls-order-container',
		//工具dom
		toolDom:'#J-gameOrder-amout-tool',
		//总注数dom
		totalLotterysNumDom:'#J-gameOrder-lotterys-num',
		//总金额dom
		totalAmountDom:'#J-gameOrder-amount',
		//每行投注记录html模板
		rowTemplate: '<li id="gameorder-<#=id#>" class="clearfix">'+
					'<span class="play-type">[<#=typeText#>]</span>'+
					'<span class="play-content"><#=lotterysText#></span>'+
					'<span class="play-odds"><#=odds#></span>'+
					'<span class="play-amount"><#=amount#></span>'+
					'<span class="play-text"><input data-param="action=amount&id=<#=id#>" type="text"></span>'+
					'<span data-param="action=del&id=<#=id#>&type=<#=type#>" class="close"></span>'+
					'</li>',
		//投注按钮Dom
		addOrderDom : '#J-add-order',
		//限制金额长度
		amountDigit: 999999
	},

	//获取当前游戏
	Games = host.Games,
	instance,
	orderID = 1;

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
		init:function(cfg){
			var me = this,cfg = me.defConfig;
			me.orderData = {};
			Games.setCurrentGameOrder(me);
			me.mainDom = $(cfg.mainDom);
			me.container = $(cfg.containerDom);
			me.toolDom = $(cfg.toolDom);
			me.totalLotterysNum = 0;
			me.totalLotterysNumDom = $(cfg.totalLotterysNumDom);
			me.totalAmount = 0.00;
			me.totalAmountDom = $(cfg.totalAmountDom);

			me.hoverTimer = null;
			me.currentInput = null;

			//事件代理
			me.eventProxy();

			//删除某条数据
			me.addEvent('afterRemoveData', function(){
				me.updateData();
				Games.getCurrentGame().getCurrentGameMethod().orderToShow(me.orderData);
			});

			//清空
			me.addEvent('afterResetData', function(){
				me.updateData();
			});

			//插入完重新排序
			me.addEvent('afterAdd', function(e,id){
				var me = this;
				//重排
				me.rearrangementOrder(id);
				me.updateData();
			});

		},
		setTotalLotterysNum:function(v){
			var me = this;
			me.totalLotterysNum = Number(v);
			me.totalLotterysNumDom.html(v);
		},
		setTotalAmount:function(v){
			var me = this;
			me.totalAmount = Number(v);
			me.totalAmountDom.html(me.formatMoney(v));
		},
		addData:function(order){
			var me = this;
			if(!!order['type']){
				if(!me.orderData[order['type']]){
					me.orderData[order['type']] = [];
				}
				me.orderData[order['type']].push(order);
				me.orderData[order['type']].sort(function(a,b){
					if (Number(a.row) > Number(b.row)) {
						return 1;
					} else if (Number(a.row) == Number(b.row)) {
						if (Number(a.odds) > Number(b.odds)) {
							return 1;
						} else if (Number(a.odds) == Number(b.odds)) {
							return Number(a.lotterys) > Number(b.lotterys) ? 1 : -1;
						} else if (Number(a.odds) < Number(b.odds)) {
							return -1;
						}
					} else if (Number(a.row) < Number(b.row)) {
						return -1;
					}

				});
			}
		},
		removeData:function(id,type){
			var me = this,
				id = Number(id),
				data = me.orderData,
				i = 0,
				len = data[type].length;
			for(;i < len;i++){
				if(data[type][i]['id'] == id){
					me.orderData[type].splice(i, 1);
					me.updateData();
					break;
				}
			}
			$('#gameorder-' + id).remove();
			me.fireEvent('afterRemoveData');
		},
		reSet:function(){
			var me = this;

			me.container.empty();
			me.orderData = {};
			me.updateData();
			me.fireEvent('afterResetData');

			return me;
		},
		updateData:function(){
			var me = this,total = me.getTotal();
			me.setTotalLotterysNum(total['count']);
			me.setTotalAmount(total['amount']);
		},
		getTotal:function(){
			var me = this,
				data = me.orderData,
				count = 0,
				amount = 0,
				$li = me.container.find('li');
			$.each($li,function(i,v){
				var nCount = Number($(v).find('.play-amount').text()),
						nAmount = Number($(v).find('input').val());
				count += nCount;
				amount += nCount*nAmount;
			});
			return {'count':count,'amount':amount,'orders':data};
		},

		//添加一条投注
		//order 参数可为单一对象或数组
		//接收参数 order {type:'玩法类型',lotterys:'投注具体数据',odds:'赔率',amount:'投注金额'}
		add:function(order){
			var me = this,
				html = '',
				tpl = me.defConfig.rowTemplate;
			me.fireEvent('beforeAdd', order);
			if(me.checkOrder(order)) {
				return;
			}
			if(order['lotterys']){
				order['id'] = orderID++;
				order['amount'] = order['amount']||1;
				html = me.formatRow(tpl, me.rebuildData(order));
				me.addData(order);
			}else{
				return;
			}
			$(html).prependTo(me.container);
			me.updateData();
			me.fireEvent('afterAdd', order['id']);
		},
		checkOrder:function(order){
			var orderData = this.orderData;
			var r;
			if(!orderData.hasOwnProperty(order['type'])) return r || false;
			$.each(orderData[order['type']],function(i,v){
				if( v['row']==order['row']&& v['lotterys'] == order['lotterys'] && v['odds']==order['odds']){
					r = true;
				}
			});
			return r || false;
		},
		checkOrderAmount: function(){
			var me = this,
				$li = me.container.find('li'),
				val,
				result;
			$.each($li,function(i,v){
				val = $(v).find('input').val();
				if(Number(val) <= 0){
					me.focusInput($(v).find('input'));
					result = true;
					return false;
				}
			});
			return result || false;
		},
		focusInput: function(dom){
			dom.focus();
			dom.addClass('flash');
		},
		render:function(){
			var me = this,orders = me.getTotal()['orders'],i = 0,len = orders.length,html = [],tpl = me.defConfig.rowTemplate;
			for(;i < len;i++){
				html[i] = me.formatRow(tpl, me.rebuildData(orders[i]));
			}
			me.updateData();
			me.container.html(html.join(''));
		},
		//填充其他数据用户界面显示
		//格式化后的数据 {typeText:'玩法类型名称',type:'玩法类型名称(英文)',lotterys:'投注具体内容',lotterysText:'显示投注具体内容的文本',amount:'总金额'}
		rebuildData:function(order){
			var me = this,
				cfg = me.defConfig,
				gameConfig = Games.getCurrentGame().getGameConfig().getInstance(),
				//typeText = gameConfig.getTitleByName(order['type'],order['sub']);
				typeText = gameConfig.getTitleByName(order['type'], null);
			order['typeText'] = typeText.join('_');
			if(order['betType'] == 'dan'||order['betType'] == 'tuo'){
				order['lotterysText'] = '<span class="has-hover">'+ order['contentText'] +'</span><div class="play-content-hover"><p>胆:'+order['content']['dan']+';拖:'+order['content']['tuo']+'</p><span class="arrow"></span></div>';
			} else if(order['betType'] == 'fushi') {
				order['lotterysText'] = '<span class="has-hover">'+ order['contentText'] +'</span><div class="play-content-hover"><p>复式:'+ order['content'] +'</p><span class="arrow"></span></div>';
			} else {
				order['lotterysText'] = order['content'];
			}
			return order;
		},
		//将注单dom 按照订单模型重排
		rearrangementOrder: function(focusid){
			var me = this,
				arr = [],
				arr2 = [],
				domArr = [],
				i = 0,
				j = 0,
				k = 0,
				position = 0;
			arr[0] = me.orderData['zhengma.pingma'];
			arr[1] = me.orderData['tema.zhixuan'];
			arr[2] = me.orderData['tema.texiao'];
			arr[3] = me.orderData['tema.saibo'];
			arr[4] = me.orderData['tema.banbo'];
			arr[5] = me.orderData['tema.liangmian'];
			arr[6] = me.orderData['zhengtema.yixiao'];
			arr[7] = me.orderData['zhengtema.buzhong'];
			arr[8] = me.orderData['zhengtema.lianxiaozhong'];
			arr[9] = me.orderData['zhengtema.lianxiaobuzhong'];
			arr[10] = me.orderData['lianma.lianma'];
			for(; i<arr.length; i++ ){
				arr2 = arr[i];
				if(arr2 != undefined){
					for(j=0; j<arr2.length; j++){
						var id = arr2[j]['id'];
						domArr.push(me.container.find('#gameorder-'+id).clone(true));
					}
				}

			}
			me.container.html('');
			for(; k<domArr.length; k++){
				if(domArr[k].attr('id') == 'gameorder-'+focusid) {
					position = k;
				}
				domArr[k].appendTo(me.container);
			}
			me.showFlash(focusid,position);
			me.eventProxy();
		},
		showFlash: function(id,pos){
			var me = this;
			var nowDom = me.container.find('#gameorder-'+id);
			//避免重复
			me.container.find('li').removeClass('flash');
			nowDom.addClass('flash');
			$(me.container).scrollTop(me.container.find('li').height()*pos);
			var timer = setTimeout(function(){
				nowDom.removeClass('flash');
			},1000);
		},

		formatRow:function(tpl, order){
			var me = this,o = order,p,reg;
			for(p in o){
				if(o.hasOwnProperty(p)){
					reg = RegExp('<#=' + p + '#>', 'g');
					tpl = tpl.replace(reg, o[p]);
				}
			}
			return tpl;
		},
		//检查数组存在某数
		arrIndexOf: function(value, arr){
		    var r;
		    for(var s=0; s<arr.length; s++){
		        if(arr[s] == value){
		            r = true;
		        };
		    }
		    return r || false;
		},
		//事件代理
		eventProxy:function(){
			var me = this,panel = me.mainDom, input = me.mainDom.find('input'),tool = me.toolDom;
			panel.off('click').click(function(e){
				var q = e.target.getAttribute('data-param'),param;
				if(q && $.trim(q) != ''){
					param = formatParam(q);
					if($.isFunction(me['exeEvent_' + param['action']])){
						me['exeEvent_' + param['action']].call(me, param, e.target);
					}
				}
			});
			input.off('mouseenter').mouseenter(function(e){
				var q = e.target.getAttribute('data-param'),param;
				if(q && $.trim(q) != ''){
					param = formatParam(q);
					me.enterInput(param, e.target)
				}
			});
			input.off('mouseleave').mouseleave(function(e){
				var q = e.target.getAttribute('data-param'),param;
				if(q && $.trim(q) != ''){
					param = formatParam(q);
					me.leaveInput(param)
				}
			});
			tool.off('mouseenter').mouseenter(function(e){
				$(this).show();
				clearTimeout(me.hoverTimer);
			});
			tool.off('mouseleave').mouseleave(function(e){
				$(this).hide();
			});

		},
		exeEvent_del:function(param){
			var me = this,id = Number(param['id']),type = param['type'];
			me.removeData(id,type);
		},
		exeEvent_addMoney:function(param){
			var me = this,value = Number(param['val']),
				$input = me.currentInput == 'all'? me.mainDom.find('input'):$('#gameorder-'+me.currentInput).find('input'),
				old = Number($input.val()) || 0,
				newValue = 0;

			if(value == 0){
				newValue = 0
			}else{
				newValue = old + value ;
			};
			newValue = (newValue >= defConfig.amountDigit) ? defConfig.amountDigit:newValue;
			$input.val(newValue);
			$input.removeClass('flash');
			me.setOrderAmountById(me.currentInput,newValue);
			me.updateData();
		},
		//显示快捷输入工具
		enterInput: function(param,target){
			var me = this,
			 	$dom = me.toolDom,
				pOffset = me.mainDom.offset(),
				pLeft = pOffset.left || 0,
				pTop = pOffset.top || 0;
			me.currentInput = param['id'];
			$dom.show();
			var top = $(target).offset().top - pTop + 2,
				left = $(target).offset().left - pLeft + 60;
			$dom.css({'top':top,'left':left});
			clearTimeout(me.hoverTimer);

		},
		//隐藏快捷输入工具
		leaveInput: function(){
			var me = this,
				$dom = me.toolDom;
			me.hoverTimer = setTimeout(function(){
				$dom.hide();
			},1000);

		},
		//将input金额存储到 订单模型
		setOrderAmountById:function(id,val){
			var me = this,
				id = Number(id),
				orderData = me.orderData,
				order;
			for(order in orderData){
				for(var i = 0; i<orderData[order].length; i++){
					if(Number(orderData[order][i]['id']) == id){
						orderData[order][i]['amount'] = Number(val);
					}else if(!id){
						orderData[order][i]['amount'] = Number(val);
					}
				}
			}
		},
		//将統一輸入input金额存储到 订单模型
		setOrderAmountByAll:function(val){
			var me = this,
				orderData = me.orderData,
				order;
			for(order in orderData){
				for(var i = 0; i<orderData[order].length; i++){					
						orderData[order][i]['amount'] = Number(val);
				}
			}
		},
		//根据选球区事件 删除订单
		deleteOrderByBall:function(param){
			var me = this,
				orderData = me.orderData,
				lotterys = param['lotterys'],
				row = param['row'],
				type = param['type'],
				typeOrder = orderData[type],
				id = 0;
			for(var i = 0; i<typeOrder.length; i++){
				if(typeOrder[i]['lotterys'] == lotterys && typeOrder[i]['row'] == row){
					id = typeOrder[i]['id'];
				}
			}

			if(id !=0){
				me.container.find('#gameorder-'+id).find('.close').trigger('click');
			}
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
			num  =  '' + parseInt(num);
			while(re.test(num)){
				num = num.replace(re,"$1,$2");
			}
			return num;
		},
		//返回订单模型
		getOrderData: function(){
			return this.orderData;
		}

	};
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host[name] = Main;

})(phoenix, "GameOrder", phoenix.Event);
