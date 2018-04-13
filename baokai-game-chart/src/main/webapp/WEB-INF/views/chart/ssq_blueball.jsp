<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<title>${lotteryName}-蓝球走势图</title>
</head>
<body class="table-trend">
	<!-- toolbar end -->
	<div class="header" style=""></div>
	<div class="select-section clearfix">
		<div class="select-function">
			<a href="#" id="J-button-showcontrol">展开功能区</a><i class="arrow-down"></i>
            <a id="J-button-export" class="btn" href="javascript:void(0);">报表下载<b class="btn-inner"></b></a>
        </div>
		<h3 class="select-title">彩种：双色球</h3>
		<ul class="select-list">
			<li><a href="ssq_basic">基本走势图</a></li>
			<li><a href="ssq_redball">红球走势图</a></li>
			<li class="current"><a href="ssq_blueball">蓝球走势图</a></li>
		</ul>
	</div>
	<div class="select-section-content clearfix" id="J-panel-control">
		<div class="title">蓝球走势图</div>
		<div class="function">
			<label class="label"><input data-action="guides" type="checkbox" class="checkbox" checked="checked">辅助线</label>
			<label class="label"><input data-action="lost" type="checkbox" class="checkbox" checked="checked">遗漏</label>
			<label class="label"><input data-action="lost-post" type="checkbox" class="checkbox">遗漏条</label>
			<label class="label"><input data-action="trend" type="checkbox" class="checkbox">走势</label>
		</div>
		<div class="time" id="J-periods-data">
			<a data-action="periods-30" href="javascript:void(0);">近30期</a>
			<a data-action="periods-50" href="javascript:void(0);">近50期</a>
			<a data-action="day-1" href="javascript:void(0);">今日数据</a>
		</div>
		<div class="search">
			<input type="text" value="" id="J-date-star" class="input w-3"> - <input id="J-date-end" type="text" value="" class="input w-3">
			<a id="J-time-periods" class="btn" href="javascript:void(0);">查 看<b class="btn-inner"></b></a>
		</div>
	</div>
	<!-- Star 五星 -->
	<div class="chart-section" id="J-chart-area">
		<table class="chart-table" id="J-chart-table">
			<thead class="thead">
				<tr class="title-text">
					<th colspan="3" rowspan="2" class="border-bottom border-right">期数</th>
					<th colspan="3" rowspan="2" class="border-right border-bottom">蓝球号码</th>
					<th colspan="18" class="border-top border-right border-bottom">双色球蓝球分布</th>
					<th colspan="2" class="border-top border-right border-bottom">单双</th>
					<th colspan="2" class="border-top border-right border-bottom">质合</th>
					<th colspan="2" class="border-top border-right border-bottom">大小</th>
					<th colspan="3" class="border-top border-right border-bottom">012路</th>
					<th colspan="6" class="border-top border-right border-bottom">蓝球数字特征</th>
					<th colspan="4" class="border-top border-right border-bottom">蓝球四区分布</th>
				</tr>
				<tr class="title-text">
					<th class="ball-none border-bottom"></th>
					<th class="border-bottom"><i class="ball-noraml">01</i></th>
					<th class="border-bottom"><i class="ball-noraml">02</i></th>
					<th class="border-bottom"><i class="ball-noraml">03</i></th>
					<th class="border-bottom"><i class="ball-noraml">04</i></th>
					<th class="border-bottom"><i class="ball-noraml">05</i></th>
					<th class="border-bottom"><i class="ball-noraml">06</i></th>
					<th class="border-bottom"><i class="ball-noraml">07</i></th>
					<th class="border-bottom"><i class="ball-noraml">08</i></th>
					<th class="border-bottom"><i class="ball-noraml">09</i></th>
					<th class="border-bottom"><i class="ball-noraml">10</i></th>
					<th class="border-bottom"><i class="ball-noraml">11</i></th>
					<th class="border-bottom"><i class="ball-noraml">12</i></th>
					<th class="border-bottom"><i class="ball-noraml">13</i></th>
					<th class="border-bottom"><i class="ball-noraml">14</i></th>
					<th class="border-bottom"><i class="ball-noraml">15</i></th>
					<th class="border-bottom"><i class="ball-noraml">16</i></th>
					<th class="ball-none border-bottom border-right"></th>
					<th class="border-bottom border-right">单</th>
					<th class="border-bottom border-right">双</th>
					<th class="border-bottom border-right">质</th>
					<th class="border-bottom border-right">合</th>
					<th class="border-bottom border-right">大</th>
					<th class="border-bottom border-right">小</th>
					<th class="border-bottom border-right">0</th>
					<th class="border-bottom border-right">1</th>
					<th class="border-bottom border-right">2</th>
					<th class="border-bottom border-right">小单质</th>
					<th class="border-bottom border-right">小双质</th>
					<th class="border-bottom border-right">小双合</th>
					<th class="border-bottom border-right">大单质</th>
					<th class="border-bottom border-right">大单合</th>
					<th class="border-bottom border-right">大双合</th>
					<th class="border-bottom border-right">01-04</th>
					<th class="border-bottom border-right">05-08</th>
					<th class="border-bottom border-right">09-12</th>
					<th class="border-bottom border-right">13-16</th>
					<th class="ball-none border-bottom border-right"></th>
				</tr>
			</thead>
			
			<tfoot class="tfoot">
				<tr class="title-text">
					<th colspan="3" rowspan="2" class="border-bottom border-right">期数</th>
					<th colspan="3" rowspan="2" class="border-right border-bottom">蓝球号码</th>
					<th class="ball-none border-bottom"></th>
					<th class="border-bottom"><i class="ball-noraml">01</i></th>
					<th class="border-bottom"><i class="ball-noraml">02</i></th>
					<th class="border-bottom"><i class="ball-noraml">03</i></th>
					<th class="border-bottom"><i class="ball-noraml">04</i></th>
					<th class="border-bottom"><i class="ball-noraml">05</i></th>
					<th class="border-bottom"><i class="ball-noraml">06</i></th>
					<th class="border-bottom"><i class="ball-noraml">07</i></th>
					<th class="border-bottom"><i class="ball-noraml">08</i></th>
					<th class="border-bottom"><i class="ball-noraml">09</i></th>
					<th class="border-bottom"><i class="ball-noraml">10</i></th>
					<th class="border-bottom"><i class="ball-noraml">11</i></th>
					<th class="border-bottom"><i class="ball-noraml">12</i></th>
					<th class="border-bottom"><i class="ball-noraml">13</i></th>
					<th class="border-bottom"><i class="ball-noraml">14</i></th>
					<th class="border-bottom"><i class="ball-noraml">15</i></th>
					<th class="border-bottom"><i class="ball-noraml">16</i></th>
					<th class="ball-none border-bottom border-right"></th>
					<th class="border-bottom border-right">单</th>
					<th class="border-bottom border-right">双</th>
					<th class="border-bottom border-right">质</th>
					<th class="border-bottom border-right">合</th>
					<th class="border-bottom border-right">大</th>
					<th class="border-bottom border-right">小</th>
					<th class="border-bottom border-right">0</th>
					<th class="border-bottom border-right">1</th>
					<th class="border-bottom border-right">2</th>
					<th class="border-bottom border-right">小单质</th>
					<th class="border-bottom border-right">小双质</th>
					<th class="border-bottom border-right">小双合</th>
					<th class="border-bottom border-right">大单质</th>
					<th class="border-bottom border-right">大单合</th>
					<th class="border-bottom border-right">大双合</th>
					<th class="border-bottom border-right">01-04</th>
					<th class="border-bottom border-right">05-08</th>
					<th class="border-bottom border-right">09-12</th>
					<th class="border-bottom border-right">13-16</th>
					<th class="ball-none border-bottom border-right"></th>
				</tr>
				<tr class="title-text">
					<th colspan="18" class="border-top border-right border-bottom">双色球蓝球分布</th>
					<th colspan="2" class="border-top border-right border-bottom">单双</th>
					<th colspan="2" class="border-top border-right border-bottom">质合</th>
					<th colspan="2" class="border-top border-right border-bottom">大小</th>
					<th colspan="3" class="border-top border-right border-bottom">012路</th>
					<th colspan="6" class="border-top border-right border-bottom">蓝球数字特征</th>
					<th colspan="4" class="border-top border-right border-bottom">蓝球四区分布</th>
			</tfoot>
			<tbody id="J-chart-content" class="chart table-guides tbody">
			</tbody>
			
			
			<!-- <tbody id="J-select-content" class="tbody">
				<tr id="J-tr-select" class="select-area">
					<td colspan="3" class="border-right border-bottom"><i data-action="addSelect" class="ico-add"></i>预选区</td>
					<td colspan="3" class="border-right border-bottom">3D</td>
					<td class="ball-none border-bottom"></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="0" class="ball-noraml">0</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="1" class="ball-noraml">1</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="2" class="ball-noraml">2</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="3" class="ball-noraml">3</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="4" class="ball-noraml">4</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="5" class="ball-noraml">5</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="6" class="ball-noraml">6</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="7" class="ball-noraml">7</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="8" class="ball-noraml">8</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="9" class="ball-noraml">9</i></td>
					<td class="ball-none border-right border-bottom"></td>
					<td class="ball-none border-bottom"></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="0" class="ball-noraml">0</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="1" class="ball-noraml">1</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="2" class="ball-noraml">2</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="3" class="ball-noraml">3</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="4" class="ball-noraml">4</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="5" class="ball-noraml">5</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="6" class="ball-noraml">6</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="7" class="ball-noraml">7</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="8" class="ball-noraml">8</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="9" class="ball-noraml">9</i></td>
					<td class="ball-none border-right border-bottom"></td>
					<td class="ball-none border-bottom"></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="0" class="ball-noraml">0</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="1" class="ball-noraml">1</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="2" class="ball-noraml">2</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="3" class="ball-noraml">3</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="4" class="ball-noraml">4</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="5" class="ball-noraml">5</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="6" class="ball-noraml">6</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="7" class="ball-noraml">7</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="8" class="ball-noraml">8</i></td>
					<td class="border-bottom"><i data-action="selectBall" data-value="9" class="ball-noraml">9</i></td>
					<td class="ball-none border-right border-bottom"></td>
					<td class="ball-none border-bottom"></td>
					<td class="border-bottom"></td>
					<td class="border-bottom"></td>
					<td class="border-bottom"></td>
					<td class="border-bottom"></td>
					<td class="border-bottom"></td>
					<td class="border-bottom"></td>
					<td class="border-bottom"></td>
					<td class="border-bottom"></td>
					<td class="border-bottom"></td>
					<td class="border-bottom"></td>
					<td class="border-right border-bottom"></td>
					<td class="border-right border-bottom" style="background:#FFF;"></td>
					<td class="border-right border-bottom" style="background:#FFF;"></td>
					<td class="border-right border-bottom" style="background:#FFF;"></td>
					<td class="border-right border-bottom" style="background:#FFF;"></td>
					<td class="border-right border-bottom" style="background:#FFF;"></td>
					<td class="border-right border-bottom" style="background:#FFF;"></td>
					<td class="border-right border-bottom" style="background:#FFF;"></td>
					<td class="border-right border-bottom" style="background:#FFF;"></td>
					<td class="border-right border-bottom" style="background:#FFF;"></td>
					<td class="ball-none border-bottom" style="background:#FFF;"></td>
				</tr>
				<tr>
					<td colspan="3" class="border-bottom"></td>
					<td colspan="3" class="border-bottom"></td>
					<td class="ball-none border-bottom"></td>
					<td colspan="47" class="border-bottom border-right">
						<form id="J-form-submit" method="get" action="?" target="_blank">
						<input id="J-input-submit-value" type="hidden" name="ball" value="" />
						<a id="J-button-submit" href="#" class="btn btn-important">添加到号码栏<b class="btn-inner"></b></a>
						</form>					</td>
					<td class="border-right border-bottom"></td>
					<td class="border-right border-bottom"></td>
					<td class="border-right border-bottom"></td>
					<td class="border-right border-bottom"></td>
					<td class="border-right border-bottom"></td>
					<td class="border-right border-bottom"></td>
					<td class="border-right border-bottom"></td>
					<td class="border-right border-bottom"></td>
					<td class="border-right border-bottom"></td>
					<td class="border-bottom"></td>
				</tr>
			</tbody> -->
			
			
			<tbody id="J-ball-content" class="tbody">
			</tbody>
		</table>
		
		<div style="height:100px;"></div>
	</div>
	<!-- End 五星 -->
<script>

var CHART;
(function($){



	CHART = new phoenix.GameCharts({currentGameType:'${view}', getDataUrl: json_data_url, currentGameMethod: '${groupCode}', expands:{
        ${view}${groupCode}Render:function(data, currentNum){
			var td,
				current,
				me = this,
				styleName = currentNum % me.getSeparateNum() == 0 ? ' border-bottom' : '',
				htmlTextArr = new Array(),
				allowCount = me.getRenderLength(),
				parentDom = document.createElement('tr');
			
			td = document.createElement('td');
			td.className = "ball-none " + styleName;
			parentDom.appendChild(td);

			//期号
			td = document.createElement('td');
			td.className = "issue-numbers " + styleName;
			td.innerHTML = data[0];
			parentDom.appendChild(td);

			td = document.createElement('td');
			td.className = "ball-none border-right" + styleName;
			parentDom.appendChild(td);

			td = document.createElement('td');
			td.className = "ball-none " + styleName;
			parentDom.appendChild(td);

			//开奖号码
			td = document.createElement('td');
			td.className = styleName;
			td.innerHTML = '<span class="lottery-numbers"><span style="padding:0 2px;color:#f53f00;">' + data[1] + '</span></span>';
			parentDom.appendChild(td);

			td = document.createElement('td');
			td.className = 'ball-none border-right' + styleName;
			parentDom.appendChild(td);
			
			// 双色球蓝球分布
			parentDom.appendChild(me.singleLotteryBall(data[2], styleName));
			
			// 单双
			parentDom.appendChild(
				me.lotteryStatus(data[3], 'bg-blue', ['单','双'])
			);

			// 质合
			parentDom.appendChild(
				me.lotteryStatus(data[4], 'bg-red', ['质','合'])
			);

			// 大小
			parentDom.appendChild(
				me.lotteryStatus(data[5], 'bg-blue', ['大','小'])
			);

			// 012路
			parentDom.appendChild(
				me.lotteryStatus(data[6], 'bg-red', ['0','1','2'])
			);

			// 蓝球数字特征
			parentDom.appendChild(
				me.lotteryStatus(data[7], 'bg-blue', ["小单质","小双质","小双合","大单质","大单合","大双合"])
			);

			// 蓝球四区分布
			parentDom.appendChild(
				me.lotteryStatus(data[8], 'bg-red')
			);
			

			//返回完整的单行TR结构
			return parentDom;
		},
		lotteryStatus: function(data, styleName, textArray){
			var d0 = data[0],
				htmlData = document.createDocumentFragment();
			if( Object.prototype.toString.call(d0) === '[object Array]' ){
				for( var i=0, d=d0, l=d.length; i<l; i++ ){
					var v = d[i], s = '';
					if( v === 0 ){
						v = data[1] || '请设置data[1]值';
						s = styleName || '';
					}
					td = document.createElement('td');
					td.className = "border-right " + s;
					td.innerHTML = v;
					htmlData.appendChild(td);
				}
			}else{
				for( var i=0, d=data, l=d.length; i<l; i++ ){
					var v = d[i], s = '';
					if( v === 0 ){
						v = textArray[i] || '请设置textArray值';
						s = styleName || '';
					}
					td = document.createElement('td');
					td.className = "border-right " + s;
					td.innerHTML = v;
					htmlData.appendChild(td);
				}
			}
			return htmlData;
		},
		singleLotteryBall: function(ballsData, styleName, blueBall) {
			// console.log(ballsData)
			var td1, td2, td3,
				me = this,
				htmlData = document.createDocumentFragment(),
				borderStyleText = styleName,
				lostBallstyle = '',
				ballStyleText = '';
			// console.log(ballsData)
			//左边界
			td1 = document.createElement('td');
			td1.className = 'ball-none' + borderStyleText;
			htmlData.appendChild(td1);

			for (var i = 0, current; i < ballsData.length; i++) {
				current = ballsData[i];
				lostBallstyle = 'l-' + current[3];
				ballStyleText = 'c-' + current[0] + '-' + current[2];

				td2 = document.createElement('td');
				if( blueBall ){
					td2.className = borderStyleText + ' ' + lostBallstyle + ' blue-ball-cell';
				}else{
					td2.className = borderStyleText + ' ' + lostBallstyle + ' red-ball-cell';
				}
				td2.innerHTML = '<i data-info="' + current.join(',') + '" class="ball-noraml ' + ballStyleText + '">' + (current[0] == 0 ? current[1] : current[0]) + '</i>'
				htmlData.appendChild(td2);
			};

			td3 = document.createElement('td');
			td3.className = 'ball-none border-right' + borderStyleText;
			htmlData.appendChild(td3);

			return htmlData;
		},
		cqsscQiansanRenderStatistics: function(data){
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
			
			
			for(i = 0, len = 35; i < len; i++){
				if( i < 16 ){
					tdstr =  (i==15) ? '<td class="ball-none border-right border-bottom"></td>' : '';

					frame1.push('<td class="border-bottom"><i class="ball-noraml">'+ data[0][i] +'</i></td>' + tdstr);
					frame2.push('<td class="border-bottom"><i class="ball-noraml">'+ data[1][i] +'</i></td>' + tdstr);
					frame3.push('<td class="border-bottom"><i class="ball-noraml">'+ data[2][i] +'</i></td>' + tdstr);
					frame4.push('<td class="border-bottom"><i class="ball-noraml">'+ data[3][i] +'</i></td>' + tdstr);
				}else{
					frame1.push('<td class="border-bottom border-right"><i class="ball-noraml">'+ data[0][i] +'</i></td>');
					frame2.push('<td class="border-bottom border-right"><i class="ball-noraml">'+ data[1][i] +'</i></td>');
					frame3.push('<td class="border-bottom border-right"><i class="ball-noraml">'+ data[2][i] +'</i></td>');
					frame4.push('<td class="border-bottom border-right"><i class="ball-noraml">'+ data[3][i] +'</i></td>');
				}				
			}

			frame1.push('</tr>');
			frame2.push('</tr>');
			frame3.push('</tr>');
			frame4.push('</tr>');
			$(me.getBallContainer()).append($(frame1.join(''))).append($(frame2.join(''))).append($(frame3.join(''))).append($(frame4.join('')));
		},
		ssqssq_blueballTrendCanvas:function(dom, data){
			var positionCount = 0,
				me = this,
				currentBallLeft = 0,
				currentBallTop = 0,
				chartTrendPosition = me.getChartTrendPosition(),
				x = 0,
				dom = dom.getElementsByTagName('i');
				
			//遍历分段渲染数据
			for (var i = 0, current; i < data.length; i++) {
				current = data[i][2];

				for (var k = 0; k < current.length; k++) {
						if(k == 0)	{
							var currentDom = dom[positionCount].parentNode,
								unitSize = me.getUnitSize(currentDom),
								top = unitSize.topNum,
								left = unitSize.leftNum,
								width = unitSize.widthNum,
								height = unitSize.heightNum;
						}

						//当前位置球
						positionCount ++;

						//当前号码
						if (current[k][0] == 0) {
							//第一排渲染
							if (typeof chartTrendPosition[x] == 'undefined') {

								//当前球的坐标
								currentBallLeft = left + (k + 1) * width - width / 2;
								currentBallTop = top + height / 2;

								chartTrendPosition[x] = {};
								chartTrendPosition[x]['top'] = currentBallTop;
								chartTrendPosition[x]['left'] = currentBallLeft;
							} else {

								//当前球的坐标
								currentBallLeft = left + (k + 1) * width - width / 2;
								currentBallTop = chartTrendPosition[x]['top'] + height;

								//绘制画布
								//绘制走势图线
								me.draw.setOption({
									parentContainer: $('#J-chart-area')[0]
								});
								me.draw.line(chartTrendPosition[x]['top'], chartTrendPosition[x]['left'], currentBallTop, currentBallLeft);

								chartTrendPosition[x]['top'] = currentBallTop;
								chartTrendPosition[x]['left'] = currentBallLeft;
							}
						}
				};
			};
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