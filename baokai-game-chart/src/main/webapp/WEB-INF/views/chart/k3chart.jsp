<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
	<title>${lotteryName}-走势图</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/phoenix.GameK3Chart.js"></script>
</head>
<body class="table-trend">
	<div class="header" style=""></div>
	<div class="select-section clearfix">
		<div class="select-function">
			<a href="#" id="J-button-showcontrol">展开功能区</a><i class="arrow-down"></i>
			<a id="J-button-export" class="btn" href="javascript:void(0);">报表下载<b class="btn-inner"></b></a>
		</div>
		<h3 class="select-title">彩种：${lotteryName}</h3>
		<!-- <ul class="select-list">
			<li class="current"><a href="#">基本</a></li> -->
				<%-- /*期号*/
			var html = '<tr>';
			html = html+  '<td class="ball-none '+ styleName + '"></td>';
			html = html+ '<td class="issue-numbers '+styleName+'">'+data[0]+'</td>';
			html = html + '<td class="ball-none border-right '+styleName+'"></td>';
			html+='<td class="ball-none '+styleName+'"></td>';
			/*开奖号*/
			html+='<td class="'+styleName+'"><span class="lottery-numbers">'+data[1] +'></span></td>'
			html+='<td class="ball-none border-right '+styleName+'"></td>';
			
			/*号码走势*/
			html+='<td class="ball-none <%=styleName %>"></td>
			for(var bIndex = 0,ballTrendChart = data[2]; bIndex < ballTrendChart.length; bIndex++){
				
			} --%>
		<!-- </ul> -->
	</div>
	<div class="select-section-content clearfix" id="J-panel-control">
		<div class="title">快三</div>
		<div class="function">
			<label class="label"><input data-action="guides" type="checkbox" class="checkbox" checked="checked">辅助线</label>
			<label class="label"><input data-action="lost" type="checkbox" class="checkbox" checked="checked">遗漏</label>
			<label class="label"><input data-action="lost-post" type="checkbox" class="checkbox">遗漏条</label>
			<label class="label"><input data-action="temperature" type="checkbox" class="checkbox">号温</label>
		</div>
		<div class="time" id="J-periods-data">
			<a data-action="periods-30" href="javascript:void(0);">近30期</a>
			<a data-action="periods-50" href="javascript:void(0);">近50期</a>
			<a data-action="day-1" href="javascript:void(0);">今日数据</a>
			<a data-action="day-2" href="javascript:void(0);">近2天</a>
			<a data-action="day-5" href="javascript:void(0);">近5天</a>
		</div>
		<div class="search">
			<input type="text" value="" id="J-date-star" class="input w-3"> - <input id="J-date-end" type="text" value="" class="input w-3">
			<a id="J-time-periods" class="btn" href="javascript:void(0);">查 看<b class="btn-inner"></b></a>
		</div>
	</div>
	<!-- Star 五星 -->
		<script type="text/template" id="char-row-tpl">		
			<tr>
			<!--期号 -->
				<td class="ball-none <@=styleName @>"></td>
				<td class="issue-numbers <@=styleName @>"><@=Data[0] @></td>
				<td class="ball-none border-right <@=styleName @>"></td>
				<td class="ball-none <@=styleName @>"></td>

			<!--开奖号-->		
				<td class="<@=styleName @>"><span class="lottery-numbers"><@=Data[1] @></span></td>
				<td class="ball-none border-right <@=styleName @>"></td>

			<!--号码走势-->
			<td class="ball-none <@=styleName @>"></td>
			<@ for (var bIndex = 0,ballTrendChart = Data[2]; bIndex < ballTrendChart.length; bIndex++) {
				var isValue = !ballTrendChart[bIndex];				
				var countNum = _.countBy(Data[1].split(''));
			 @>	
				<td class="<@=styleName @>"><i class="ball-noraml  <@=isValue ? 'c-0-3':'' @>"><@ if(isValue && countNum[bIndex+1] > 1){ @><i class="ball-mark"><@=countNum[bIndex+1] @> </i> <@ } @> <@=isValue ? (bIndex+1) : ballTrendChart[bIndex]@></i></td>				
            <@ } @>
			<td class="ball-none border-right <@=styleName @>"></td>

			<!-- 和值走势 -->
			<td class="ball-none <@=styleName @>"></td>
			<@ for (var hIndex = 3, hArr = Data[3]; hIndex<19; hIndex++) {						
				var isValue = !hArr[hIndex-3][0];
				var isLostLine = hArr[hIndex-3][1];					
			@>	
				<td class="<@=styleName @> <@=isValue ? 'bg-red canvas-point':'' @> <@=isLostLine ? 'l-1':'l-0'@>"><i class="ball-noraml"><@=isValue ? hIndex : hArr[hIndex-3][0]@></i></td>
			<@}@>	
			<td class="ball-none border-right <@=styleName @>"></td>

			<!-- 和值组合形态 -->
			<@for (var numIndex = 0,source =  Data[4]; numIndex <source.length; numIndex++) {
				var isCurrentValue = !source[numIndex];
				var word = ({0:'小奇',1:'小偶',2:'大奇',3:'大偶'}[numIndex]);
			@>
				<td class="border-right <@=styleName @> <@=isCurrentValue ? 'bg-blue':'' @>"><@=isCurrentValue  ? word : source[numIndex] @></td>		
			<@}@>


			<!--  号码形态  -->
			<@for (var numIndex = 0,source =  Data[5]; numIndex <source.length; numIndex++) {
				var isCurrentValue = !source[numIndex];
				var word = ({0:['bg-red', '三同号'],1:['bg-blue','三不同号'],2:['bg-green','三连号'],3:['bg-red','二同号（复）'],4:['bg-red','二同号（单）'],5:['bg-red', '二不同号']}[numIndex]);
			@>
			<td class="border-right <@=styleName @> <@=isCurrentValue ? word[0]:'' @>"><@=isCurrentValue  ? word[1] : source[numIndex] @></td>
			<@}@>

			</tr>			
	</script>
	<div class="chart-section" id="J-chart-area">
	<table class="chart-table" id="J-chart-table">
		<thead class="thead">
			<tr class="title-text">
				<th rowspan="2" class="ball-none border-bottom"></th>
				<th rowspan="2" class="border-bottom">期号</th>
				<th rowspan="2" class="ball-none border-bottom border-right"></th>
				<th rowspan="2" class="ball-none border-bottom"></th>
				<th rowspan="2" class="border-bottom">开奖号码</th>
				<th rowspan="2" class="ball-none border-bottom border-right"></th>
				<th colspan="8" class="border-right">号码走势</th>
				<th colspan="18" class="border-right">和值</th>
				<th class="border-bottom border-right" colspan="4">和值组合形态</th>
				<th class="border-bottom border-right" colspan="6">号码形态</th>
			</tr>
			<tr class="title-number">
				<th class="ball-none border-bottom"></th>
				<th class="border-bottom"><i class="ball-noraml">1</i></th>
				<th class="border-bottom"><i class="ball-noraml">2</i></th>
				<th class="border-bottom"><i class="ball-noraml">3</i></th>
				<th class="border-bottom"><i class="ball-noraml">4</i></th>
				<th class="border-bottom"><i class="ball-noraml">5</i></th>
				<th class="border-bottom"><i class="ball-noraml">6</i></th>
				<th class="ball-none border-bottom border-right"></th>
				<th class="ball-none border-bottom"></th>
				<th class="border-bottom"><i class="ball-noraml">3</i></th>
				<th class="border-bottom"><i class="ball-noraml">4</i></th>
				<th class="border-bottom"><i class="ball-noraml">5</i></th>
				<th class="border-bottom"><i class="ball-noraml">6</i></th>
				<th class="border-bottom"><i class="ball-noraml">7</i></th>
				<th class="border-bottom"><i class="ball-noraml">8</i></th>
				<th class="border-bottom"><i class="ball-noraml">9</i></th>
				<th class="border-bottom"><i class="ball-noraml">10</i></th>
				<th class="border-bottom"><i class="ball-noraml">11</i></th>
				<th class="border-bottom"><i class="ball-noraml">12</i></th>
				<th class="border-bottom"><i class="ball-noraml">13</i></th>
				<th class="border-bottom"><i class="ball-noraml">14</i></th>
				<th class="border-bottom"><i class="ball-noraml">15</i></th>
				<th class="border-bottom"><i class="ball-noraml">16</i></th>
				<th class="border-bottom"><i class="ball-noraml">17</i></th>
				<th class="border-bottom"><i class="ball-noraml">18</i></th>
				<th class="ball-none border-bottom border-right"></th>
				<th class="border-bottom border-right">小奇</th>
				<th class="border-bottom border-right">小偶</th>
				<th class="border-bottom border-right">大奇</th>
				<th class="border-bottom border-right">大偶</th>
				<th class="border-bottom border-right">三同号</th>
				<th class="border-bottom border-right">三不同号</th>
				<th class="border-bottom border-right">三连号</th>
				<th class="border-bottom border-right">二同号（复）</th>
				<th class="border-bottom border-right">二同号（单）</th>
				<th class="border-bottom">二不同号</th>
			</tr>
		</thead>
		<tbody class="tbody"  id="J-chart-content">
				
		</tbody>
		
<tbody class="tbody tbody-footer-header">
			<tr class="auxiliary-area title-number">
				<td class="border-right border-bottom" colspan="3" rowspan="2">期号</td>
				<td class="border-right border-bottom" colspan="3" rowspan="2">开奖号码</td>
				<td class="ball-none border-bottom"></td>
				<td class="border-bottom"><i class="ball-noraml">1</i></td>
				<td class="border-bottom"><i class="ball-noraml">2</i></td>
				<td class="border-bottom"><i class="ball-noraml">3</i></td>
				<td class="border-bottom"><i class="ball-noraml">4</i></td>
				<td class="border-bottom"><i class="ball-noraml">5</i></td>
				<td class="border-bottom"><i class="ball-noraml">6</i></td>
				<td class="ball-none border-right border-bottom"></td>
				<td class="ball-none border-bottom"></td>
				<td class="border-bottom"><i class="ball-noraml">3</i></td>
				<td class="border-bottom"><i class="ball-noraml">4</i></td>
				<td class="border-bottom"><i class="ball-noraml">5</i></td>
				<td class="border-bottom"><i class="ball-noraml">6</i></td>
				<td class="border-bottom"><i class="ball-noraml">7</i></td>
				<td class="border-bottom"><i class="ball-noraml">8</i></td>
				<td class="border-bottom"><i class="ball-noraml">9</i></td>
				<td class="border-bottom"><i class="ball-noraml">10</i></td>
				<td class="border-bottom"><i class="ball-noraml">11</i></td>
				<td class="border-bottom"><i class="ball-noraml">12</i></td>
				<td class="border-bottom"><i class="ball-noraml">13</i></td>
				<td class="border-bottom"><i class="ball-noraml">14</i></td>
				<td class="border-bottom"><i class="ball-noraml">15</i></td>
				<td class="border-bottom"><i class="ball-noraml">16</i></td>
				<td class="border-bottom"><i class="ball-noraml">17</i></td>
				<td class="border-bottom"><i class="ball-noraml">18</i></td>
				<td class="ball-none border-right border-bottom"></td>
				<td class="border-bottom border-right">小奇</td>
				<td class="border-bottom border-right">小偶</td>
				<td class="border-bottom border-right">大奇</td>
				<td class="border-bottom border-right">大偶</td>
				<td class="border-bottom border-right">三同号</td>
				<td class="border-bottom border-right">三不同号</td>
				<td class="border-bottom border-right">三连号</td>
				<td class="border-bottom border-right">二同号（复）</td>
				<td class="border-bottom border-right">二同号（单）</td>
				<td class="border-bottom">二不同号</td>
			</tr>
			<tr class="auxiliary-area title-text">
				<td class="border-bottom border-right" colspan="8">号码走势</td>
				<td class="border-bottom border-right " colspan="18">和值</td>
				<td class="border-bottom border-right" colspan="4">和值组合形态</td>
				<td class="border-bottom" colspan="6">号码形态</td>
			</tr>
		</tbody>	
	</table>
	<div style="height:100px;"></div>
	</div>
	<br />
	<!-- End 五星 -->
	
	
<script>

var CHART;
(function($){
	CHART = new phoenix.GameCharts({currentGameType:'${view}', getDataUrl: json_data_url, currentGameMethod: '${groupCode}', expands:{
		${view}${groupCode}Render:function(data, currentNum){
			

			var styleName = currentNum % this.getSeparateNum() == 0 ? ' border-bottom' : '';
			
			return this.getHtmlFragment(data, currentNum,styleName);
		},
		${view}${groupCode}RenderStatistics: function(data){
			var me = this,
				index = 0,
				i = 0,
				len = 0,
				j = 0,
				len2 = 0,
				n = 0,
				tdstr = '<td class="ball-none border-right"></td><td class="ball-none"></td>',
				frame1 = [],
				frame2 = [],
				frame3 = [],
				frame4 = [];
			frame1.push('<tr class="auxiliary-area"><td class="ball-none"></td><td class="border-bottom border-top">出现总次数</td><td class="ball-none border-right border-bottom"></td><td class="ball-none  border-bottom"></td><td class="border-bottom"></td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>');
			frame2.push('<tr class="auxiliary-area"><td class="ball-none border-bottom"></td><td class="border-bottom">平均遗漏值</td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td><td class="border-bottom"></td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>');
			frame3.push('<tr class="auxiliary-area"><td class="ball-none border-bottom"></td><td class="border-bottom">最大遗漏值</td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td><td class="border-bottom"></td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>');
			frame4.push('<tr class="auxiliary-area"><td class="ball-none border-bottom"></td><td class="border-bottom">最大连出值</td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td><td class="border-bottom"></td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>');
			
			
			for(i = 0, len = 40; i < len; i++){
				tdstr = ((i+1)%10 == 0) ? '<td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>' : '';
				tdstr = (i == (len - 1)) ? '<td class="border-right border-bottom"></td>' : tdstr;

				frame1.push('<td class="border-bottom"><i class="ball-noraml">'+ data[0][i] +'</i></td>' + tdstr);
				frame2.push('<td class="border-bottom"><i class="ball-noraml">'+ data[1][i] +'</i></td>' + tdstr);
				frame3.push('<td class="border-bottom"><i class="ball-noraml">'+ data[2][i] +'</i></td>' + tdstr);
				frame4.push('<td class="border-bottom"><i class="ball-noraml">'+ data[3][i] +'</i></td>' + tdstr);
			}
			
			for(i = 0; i < 10; i++){
				//形态
				if(i > 3 && i < 7){
					frame1.push('<td class="border-bottom border-right"><i class="ball-noraml">'+ data[0][40 + i - 4] +'</i></td>');
					frame2.push('<td class="border-bottom border-right"><i class="ball-noraml">'+ data[1][40 + i - 4] +'</i></td>');
					frame3.push('<td class="border-bottom border-right"><i class="ball-noraml">'+ data[2][40 + i - 4] +'</i></td>');
					frame4.push('<td class="border-bottom border-right"><i class="ball-noraml">'+ data[3][40 + i - 4] +'</i></td>');
				}else{
					frame1.push('<td class="border-right border-bottom"></td>');
					frame2.push('<td class="border-right border-bottom"></td>');
					frame3.push('<td class="border-right border-bottom"></td>');
					frame4.push('<td class="border-right border-bottom"></td>');
				}
			}

			frame1.push('</tr>');
			frame2.push('</tr>');
			frame3.push('</tr>');
			frame4.push('</tr>');
			$(me.getBallContainer()).append($(frame1.join(''))).append($(frame2.join(''))).append($(frame3.join(''))).append($(frame4.join('')));
		},
		${view}${groupCode}TrendCanvas:function(dom, data){
			var positionCount = 0,
				me = this,
				currentBallLeft = 0,
				currentBallTop = 0;
		

						var canvasPoint = $(".canvas-point");
						canvasPoint.each(function(j){
							if((j+1) < canvasPoint.length){
							var unitSize = me.getUnitSize($(this)),
								top = unitSize.topNum,
								left = unitSize.leftNum,
								width = unitSize.widthNum,
								height = unitSize.heightNum;


							var paramLeft = left + width / 2;
							var paramTop = top+height/2;

							var unitSize = me.getUnitSize(canvasPoint.eq(j+1)),
							top = unitSize.topNum,
							left = unitSize.leftNum,
							width = unitSize.widthNum,
							height = unitSize.heightNum;
										
							var forwardparamLeft = left + width / 2;
							var forwardparamTop = top+height/2;			
							me.draw.line(paramTop, paramLeft, forwardparamTop, forwardparamLeft,width);

							}
			});
		positionCount = 0;

		},
		getHtmlFragment:function(data, currentNum,styleName){
			 _.templateSettings = {
	    evaluate    : /<@([\s\S]+?)@>/g,
	    interpolate : /<@=([\s\S]+?)@>/g,
	    escape      : /<@-([\s\S]+?)@>/g
		  };		
			var result = $('<div>').html(_.template($("#char-row-tpl").html())({Data:data,styleName:styleName,currentNum:currentNum}));
			return result.children().get(0);
		}
	}});
	
	
	
	
	CHART.addEvent('afterRenderChartHtml', function(){
		var me = this,tds = $(me.getContainer()).find('tr:last').children();
		tds.addClass('border-bottom');
	});
	CHART.show();
	
	
	//提交选球数据
	$('#J-form-submit').submit(function(e){
		var trs = $('#J-select-content').find('.select-area'),its,result = [],arrRow = [],arr = [],resultStr = '',
			cls = 'ball-orange',
			i = 0,
			len = 0;
		trs.each(function(k){
			arrRow = [];
			its = $(this).find('.ball-noraml');
			for(i = 0,len = its.length; i < len; i++){
				//arr[i] = its[i].className.indexOf(cls) != -1 ? 1 : 0;
				if(its[i].className.indexOf(cls) != -1){
					arr.push(i%10);
				}
				if((i+1) % 10 == 0){
					arrRow.push(arr.join(''));
					arr = [];
				}
			}
			if($.trim(arrRow.join('')) != ''){
				result.push(arrRow.join('-'));
			}
		});
		resultStr = result.join('_');
		$('#J-input-submit-value').val(resultStr);
				
		if(resultStr == ''){
			return false;
		}
	});
	$('#J-button-submit').click(function(e){
		e.preventDefault();
		$('#J-form-submit').submit();
	});

})(jQuery);

</script>


</body>
</html>