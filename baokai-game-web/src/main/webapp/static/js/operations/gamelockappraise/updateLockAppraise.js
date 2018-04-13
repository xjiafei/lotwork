(function($){
	
	 var gameConfig = {
			baseMoney:2000,
			downMinMoney:1000,
			profit:-0.05
		};
		var headData = [
			{title:'1500奖金组奖金示例', group:[{title:'直选', baseMoney:2000, cfgMoney:1500}, {title:'组三', baseMoney:667, cfgMoney:500}, {title:'组六', baseMoney:333, cfgMoney:250}]}, 
			{title:'1700奖金组奖金示例', group:[{title:'直选', baseMoney:2000, cfgMoney:1700}, {title:'组三', baseMoney:667, cfgMoney:570}, {title:'组六', baseMoney:333, cfgMoney:280}]}
		];
		var upData = [];
		var downData = [];
		//菜单样式加载
		$('.menu-list li').removeAttr("class");
		$('.menu-list li:eq(3)').attr("class","current"); 
		$('.col-side .nav dd:eq(0)').attr("class","current");
		$('#changeMenu').attr("class","current");
		//[[开始金额, 结束金额], 变价率]
		upData.push([[800000,1000000], 100]);
		upData.push([[600000,800000], 80]);
		upData.push([[400000,600000], 60]);
		upData.push([[200000,400000], 40]);
		upData.push([[100000,200000], 20]);
		upData.push([[0,100000], 0]);
	//根据后台数据生成表格
	var PriceTable = function(cfg){
		this.cfg = cfg;
		this.init(cfg);
	};
	PriceTable.prototype.init = function(cfg){
		var me = this;
		me.gameConfig = cfg.gameConfig;
		me.type = cfg.type;
		me.tableCont = $(cfg.tableCont);
		me.headData = cfg.headData;
		me.data = cfg.data;
		me.initDom();
	};
	PriceTable.prototype.initDom = function(){
		var me = this,
			data = me.data,
			groupLen = me.headData.length,
			i = 0,
			j = 0,
			k = 0,
			len2,
			len3,
			tdStr = [],
			space = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
			dataObj = null,
			thStr = ['<tr><th rowspan="2">变价阶段</th><th rowspan="2">浮动盈亏值</th><th rowspan="2">变价率</th>'];
		//生成表头
		for(i = 0;i < groupLen;i++){
			thStr.push('<th colspan="'+ me.headData[i]['group'].length +'">'+ me.headData[i]['title'] +'</th>');
		}
		thStr.push('<th rowspan="2">操作</th></tr>');
		thStr.push('<tr>');
		for(i = 0;i < groupLen;i++){
			len2 = me.headData[i]['group'].length;
			for(j = 0;j < len2;j++){
				thStr.push('<th>'+ me.headData[i]['group'][j]['title'] +'</th>');
			}
		}
		thStr.push('</tr>');
		me.tableCont.append($(thStr.join('')));
		
		
		//生成内容
		for(i = 0, len2 = data.length; i < len2; i++){
			tdStr = [];
			tdStr.push('<tr class="tr-row"><td><span class="row-step">'+ (me.type == 'up' ? len2 - i : (i+1)) +'阶段</span></td><td><span class="money-start">'+ (data[i][0][0].toFixed(2) == 0.00 ? space : '')  + data[i][0][0].toFixed(2) +'</span> 至 <input type="text" class="input-minmoney input w-2" value="'+ data[i][0][1].toFixed(2) +'"></td><td><input type="text" class="input-perc input w-1" value="'+ data[i][1] +'"> %</td>');
				for(j = 0; j < groupLen; j++){
					//玩法奖金
					len3 = me.headData[j]['group'].length;
					for(k = 0; k < len3; k++){
						dataObj = me.headData[j]['group'][k];
						tdStr.push('<td class="td-money-prize">'+ Math.round(me.getMoney(dataObj, data[i][1])) +'</td>');
					}
				}
				tdStr.push('<td><a data-action="add" href="#">增加</a><br><a data-action="del" href="#">删除</a></td></tr>');
			//if(i == 0){
			//	
			//}else{
			//	tdStr.push('');
			//}
			
			me.tableCont.append($(tdStr.join('')));
		}
	};
	//神奇的变价方案计算公式
	//baseMax 全额奖金
	//baseMin 向下变价极限值
	//profit 公司流水
	//perc 变价率
	PriceTable.prototype.getMoney = function(dataObj, perc){
		var me = this,
			type = me.type,
			baseMax,
			baseMin;
		//向上变价
		if(type == 'up'){
			baseMax = dataObj['cfgMoney'];
			baseMin = dataObj['baseMoney'] * (1 - me.gameConfig['profit']);
			return baseMax*(1 - (baseMax-baseMin)/baseMax*perc/100);
		}else{
		//向下变价
			baseMax = dataObj['cfgMoney'];
			baseMin = me.gameConfig['downMinMoney']/me.gameConfig['baseMoney'] * dataObj['baseMoney'];
			return baseMax*(1 - (baseMax-baseMin)/baseMax*perc/100);
		}
	}
	
	//
	var ChangePriceGroup = function(cfg){
		this.cfg = cfg;
		this.init();
	};
	ChangePriceGroup.chartOptions = {
		series: {
			bars: {
				show: true,
				barWidth: 20,
				align: "center",
				lineWidth:0
			}
		},
		lines: {
			show: true,
			lineWidth: 2
		},
		colors: ["#009ED0"],
		points: {
			show: true
		},
		xaxis: {
			tickSize: 15,
		},
		yaxis: {
			tickSize: 10
		},
		grid: {
			borderWidth: 1,
			color: '#999',
			hoverable: true,
			autoHighlight: true
		},
		legend: {
			color: '#000'
		}
	};
	ChangePriceGroup.prototype.init = function(){
		var me = this;
		me.priceTable = me.cfg.priceTable;
		me.type = me.cfg.type;
		me.table = $(me.cfg.table);
		me.chartCont = $(me.cfg.chartCont);
		me.data = null
		me.plot = null;
		me.tip = $('#J-chart-tip');
		me.chartData = {"data":[]};
		me.setData(me.cfg.data);
		me.showChart();
		me.initEvent();
	};
	ChangePriceGroup.prototype.setData = function(data){
		var me = this;
		me.data = data;
		me.chartData = {"data":[]};
		$.each(me.data, function(){
			me.chartData['data'].push([this[0][0]/10000, this[1]]);
		});
	};
	ChangePriceGroup.prototype.showChart = function(){
		var me = this;
		if(me.type == 'up'){
			ChangePriceGroup.chartOptions.series.bars.align = 'left';
		}else{
			ChangePriceGroup.chartOptions.series.bars.align = 'right';
		}
		me.chartCont.html('');
		me.plot = $.plot(me.chartCont, [me.chartData], ChangePriceGroup.chartOptions);
	};
	ChangePriceGroup.prototype.initEvent = function(){
		var me = this;
		me.chartCont.bind('plothover', function(e, pos, it){
			if (it) {
				//console.log(it.dataIndex);
				var x = it.datapoint[0].toFixed(2),
					y = it.datapoint[1].toFixed(2);
				me.tip.html('浮动盈亏: ' + x + '万至' + (me.data[it.dataIndex][0][1]/10000).toFixed(2) + '万<br/>变价率: ' + y + '%' )
					.css({top: it.pageY - 55, left: it.pageX+5})
					.fadeIn(200);
			} else {
				me.tip.hide();
			}
		});
		me.table.on('click', '[data-action]', function(e){
			e.preventDefault();
			var el = $(this),action = $.trim(el.attr('data-action'));
			switch(action){
				case 'add':
					me.addRow(el.parent().parent());
				break;
				case 'del':
					me.delRow(el.parent().parent());
				break;
				default:
				break;
			}
		});
		me.table.on('blur', '.input-minmoney', function(){
			var el = $(this);
		});
		
	};
	ChangePriceGroup.prototype.addRow = function(tr){
		var me = this,
			i = 0,
			trs,
			index,
			size,
			headData = me.priceTable.headData,
			//[[开始金额, 结束金额], 变价率]
			newData = [[0, 0], 0],
			newRow = $('<tr class="tr-row">'+ tr.html() +'</tr>');
			newTds = newRow.find('.td-money-prize');
		
		i = 0;
		$.each(headData, function(){
			var group = this['group'];
			$.each(group, function(){
				newTds.eq(i).text(Math.round(me.priceTable.getMoney(this, newData[1])));
				i++;
			});
		});
			
		newData[0][0] = Number(tr.find('.input-minmoney').val()).toFixed(2);
		
		newRow.find('.money-start').text(newData[0][0]);		
		newRow.find('.input-minmoney').val(newData[0][1]);
		newRow.find('.input-perc').val(newData[1]);
		if(me.type == 'up'){	
		
			newData[0][0] = Number($.trim(tr.find('.money-start').html())); //上一笔开始金额
			newData[0][1] = Number($.trim(tr.find('.input-minmoney').val()));//上一笔结束金额	
			newData[1] = Number($.trim(tr.find('.input-perc').val()));
				
			if(newData[0][0] > newData[0][1] || newData[0][1] < 1){
				alert('向上变价时浮动盈亏值错误');
				return false;
			}else if(newData[1] == 0 && !isNaN(newData[0][0]) && newData[0][0] != 0){ //第一阶段变价率可以0
				alert('向上变价时变价率错误');
				return false;
			}else if(newData[0][1] > 6000000){
				alert('向上变价时输入的浮动盈亏值结束金额超过实际运营金额!');
				return false;
			}			
			newRow.insertBefore(tr);
			
		}else{
			
			newData[0][0] = Number($.trim(tr.find('.money-start').html())); //上一笔开始金额
			newData[0][1] = Number($.trim(tr.find('.input-minmoney').val()));//上一笔结束金额	
			newData[1] = Number($.trim(tr.find('.input-perc').val()));
			
			if(newData[0][0] < newData[0][1] || newData[0][1] > 0 || newData[0][0] > 0){
				alert('向下变价时浮动盈亏值错误');
				return false;
			}else if(newData[1] == 0 && !isNaN(newData[0][0]) && newData[0][0] != 0){ //第一阶段变价率可以0
				alert('向下变价时变价率错误');
				return false;
			}else if(newData[0][1] < -6000000){
				alert('变价时输入的向下浮动盈亏值结束金额超过实际运营金额!');
				return false;
			}			
			
			newRow.insertAfter(tr);
		}
		trs = me.table.find('.tr-row');
		index = trs.index(newRow.get(0));
		me.data.splice(index, 0, newData);
		me.setData(me.data);
		me.showChart();
		//console.log(me.data);
		
		size = trs.size();
		trs.each(function(i){
			$(this).find('.row-step').text((me.type == 'up' ? size - i : (i+1)) + '阶段');
		});
	};
	ChangePriceGroup.prototype.delRow = function(tr){
		var me = this,
			trs = me.table.find('.tr-row'),
			index = trs.index(tr);
		if(trs.size() >= 2){
			tr.remove();
			me.data.splice(index, 1);
			me.setData(me.data);
			me.showChart();
		}
	};
	
	
	var priceTableUp,priceTableDown,chartGroupUp,chartGroupDown;
	 $.ajax({
		url:baseUrl+'/gameoa/getGameLockAppraiseChangeStruc?id=' + $('#id').val(),
		dataType:'json',
		success:function(data){
			downData = data;
		    priceTableUp = new PriceTable({type:'up', gameConfig:gameConfig, tableCont:'#J-table-chart-up', headData:headData, data:upData});
			priceTableDown = new PriceTable({type:'down', gameConfig:gameConfig, tableCont:'#J-table-chart-down', headData:headData, data:downData});
			chartGroupUp = new ChangePriceGroup({type:'up', table:'#J-table-chart-up', chartCont:'#J-chart-up', data:upData, priceTable:priceTableUp});
			chartGroupDown = new ChangePriceGroup({type:'down', table:'#J-table-chart-down', chartCont:'#J-chart-down', data:downData, priceTable:priceTableDown});
		}
	}); 
	
	
	$('.table-chart').on('keyup', '.input-perc', function(e){
		var v = $.trim(this.value);
		v = v.replace(/[^\d|\-|\.]/g, '');
		this.value = v;
	});
	$('.table-chart').on('blur', '.input-perc', function(e){
		var el = $(this),v = el.val().replace(/[^\d|\-|\.]/g, ''),tr = el.parents('.tr-row'),table = el.parents('.table-chart'),trs = table.find('.tr-row'),
			type = $.trim(table.attr('data-type')),
			i = 0,
			headData,
			chartObj,
			prizeDoms,
			index = trs.index(tr.get(0));
			v = Number(v);
		if(type == 'up'){
			chartObj = chartGroupUp;
		}else{
			chartObj = chartGroupDown;
		}
		
		prizeDoms = tr.find('.td-money-prize');
		i = 0;
		headData = chartObj.priceTable.headData
		$.each(headData, function(){
			var group = this['group'];
			$.each(group, function(){
				prizeDoms.eq(i).text(Math.round(chartObj.priceTable.getMoney(this, v)));
				i++;
			});
		});
		
		chartObj['data'][index][1] = v;
		el.val(v);
		chartObj.setData(chartObj['data']);
		chartObj.showChart();
	});
	$('.table-chart').on('blur', '.input-minmoney', function(e){
		var el = $(this),v = el.val().replace(/[^\d|\-|\.]/g, ''),tr = el.parents('.tr-row'),table = el.parents('.table-chart'),trs = table.find('.tr-row'),
			type = $.trim(table.attr('data-type')),
			preTr,
			preTr2,
			preLimitMoneyDom,
			preLimitMoneyDom2,
			limitMoney,
			chartObj,
			index = trs.index(tr.get(0));
			
			v = Number(v).toFixed(2);
			el.val(v);

		if(type == 'up'){
			chartObj = chartGroupUp;
			chartObj['data'][index][0][1] = v;
			if(index != 0){
				chartObj['data'][index - 1][0][0] = v;
			}
			preTr = trs.eq(index - 1);
			preLimitMoneyDom = preTr.find('.money-start');
			limitMoney = Number(preLimitMoneyDom.text());
			if(v >= limitMoney && limitMoney > 0){  //不触发第一阶起始金额
				preLimitMoneyDom.text(v);
			}
		}else{
			chartObj = chartGroupDown;
			chartObj['data'][index][0][1] = v;
			if(index != (trs.size() - 1)){
				chartObj['data'][index + 1][0][0] = v;
			}
			preTr = trs.eq(index); //只跟当前起始金额比较不与下一阶段比
			preTr2 = trs.eq(index + 1);
			preLimitMoneyDom = preTr.find('.money-start');
			preLimitMoneyDom2 = preTr2.find('.money-start');
			limitMoney = Number(preLimitMoneyDom.text());
			
			if(v <= limitMoney  || limitMoney == 0){
				preLimitMoneyDom2.text(v);
			}else{
				alert('向下变价时当前阶段，结束金额大于开始金额，请调整!');
				return false;
			}
		}
		
		chartObj.setData(chartObj['data']);
		chartObj.showChart();
		
	});
	
	
	$('#J-button-reset').click(function(e){
		e.preventDefault();
		location.href = location.href;
	});
	
	
	$('#J-button-submit').click(function(e){
		e.preventDefault();
		var old = null,
			isPass = true;
		
		//校验向上变价数据
		$.each(upData, function(){
			if(old){
				if(parseFloat(old[0][0]) < parseFloat(this[0][1])){
					isPass = false;
					return false;
				}
			}
			old = this;
			if(parseFloat(this[0][0]) > parseFloat(this[0][1])){
				isPass = false;
				alert('向上变价中开始金额不能大于结束金额:\n\n' + Number(this[0][0]).toFixed(2) + '至' + Number(this[0][1]).toFixed(2) + '\n\n变价率：' + this[1] + '%');
				return false;
			}
		});
		
		//校验向下变价
		old = null;
		$.each(downData, function(){
			if(old){
				if(parseFloat(old[0][1]) < parseFloat(this[0][0])){
					isPass = false;
					return false;
				}
			}
			old = this;
			if(parseFloat(this[0][0]) < parseFloat(this[0][1])){
				isPass = false;
				alert('向下变价中开始金额不能小于结束金额:\n\n' + Number(this[0][0]).toFixed(2) + '至' + Number(this[0][1]).toFixed(2) + '\n\n变价率：' + this[1] + '%');
				return false;
			}
		});
		
		
		if(isPass){
			//$('#J-input-value-up').val(upData.join('|'));
			$('#J-input-value-down').val(downData.join('|'));
			$('#J-form').submit();
		}
	});
	
	
})(jQuery);