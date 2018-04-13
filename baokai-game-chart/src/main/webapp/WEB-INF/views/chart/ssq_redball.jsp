<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<title>${lotteryName}-红球走势图</title>
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
			<li class="current"><a href="ssq_redball">红球走势图</a></li>
			<li><a href="ssq_blueball">蓝球走势图</a></li>
		</ul>
	</div>
	<div class="select-section-content clearfix" id="J-panel-control">
		<div class="title">红球走势图</div>
		<div class="function">
			<label class="label"><input data-action="guides" type="checkbox" class="checkbox" checked="checked">辅助线</label>
			<label class="label"><input data-action="lost" type="checkbox" class="checkbox" checked="checked">遗漏</label>
			<label class="label"><input data-action="lost-post" type="checkbox" class="checkbox">遗漏条</label>
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
					<th colspan="3" rowspan="2" class="border-right border-bottom">开奖号码</th>
					<th colspan="13" class="border-top border-right border-bottom">一区</th>
					<th colspan="13" class="border-top border-right border-bottom">二区</th>
					<th colspan="13" class="border-top border-right border-bottom">三区</th>
					<th rowspan="2" class="border-top border-right border-bottom">区比</th>
					<th rowspan="2" class="border-top border-right border-bottom">大小比</th>
					<th rowspan="2" class="border-top border-right border-bottom">奇偶比</th>
					<th rowspan="2" class="border-top border-right border-bottom">和值</th>
					<th rowspan="2" class="border-top border-right border-bottom">和值尾数</th>
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
					<th class="ball-none border-bottom border-right"></th>
					<th class="ball-none border-bottom"></th>
					<th class="border-bottom"><i class="ball-noraml">12</i></th>
					<th class="border-bottom"><i class="ball-noraml">13</i></th>
					<th class="border-bottom"><i class="ball-noraml">14</i></th>
					<th class="border-bottom"><i class="ball-noraml">15</i></th>
					<th class="border-bottom"><i class="ball-noraml">16</i></th>
					<th class="border-bottom"><i class="ball-noraml">17</i></th>
					<th class="border-bottom"><i class="ball-noraml">18</i></th>
					<th class="border-bottom"><i class="ball-noraml">19</i></th>
					<th class="border-bottom"><i class="ball-noraml">20</i></th>
					<th class="border-bottom"><i class="ball-noraml">21</i></th>
					<th class="border-bottom"><i class="ball-noraml">22</i></th>
					<th class="ball-none border-bottom border-right"></th>
					<th class="ball-none border-bottom"></th>
					<th class="border-bottom"><i class="ball-noraml">23</i></th>
					<th class="border-bottom"><i class="ball-noraml">24</i></th>
					<th class="border-bottom"><i class="ball-noraml">25</i></th>
					<th class="border-bottom"><i class="ball-noraml">26</i></th>
					<th class="border-bottom"><i class="ball-noraml">27</i></th>
					<th class="border-bottom"><i class="ball-noraml">28</i></th>
					<th class="border-bottom"><i class="ball-noraml">29</i></th>
					<th class="border-bottom"><i class="ball-noraml">30</i></th>
					<th class="border-bottom"><i class="ball-noraml">31</i></th>
					<th class="border-bottom"><i class="ball-noraml">32</i></th>
					<th class="border-bottom"><i class="ball-noraml">33</i></th>
					<th class="ball-none border-bottom border-right"></th>
				</tr>
			</thead>
			<tfoot class="tfoot">
				<tr class="title-text">
					<th colspan="3" rowspan="3" class="border-right border-bottom">期数</th>
					<th colspan="3" rowspan="3" class="border-right border-bottom">开奖号码</th>
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
					<th class="ball-none border-bottom border-right"></th>
					<th class="ball-none border-bottom"></th>
					<th class="border-bottom"><i class="ball-noraml">12</i></th>
					<th class="border-bottom"><i class="ball-noraml">13</i></th>
					<th class="border-bottom"><i class="ball-noraml">14</i></th>
					<th class="border-bottom"><i class="ball-noraml">15</i></th>
					<th class="border-bottom"><i class="ball-noraml">16</i></th>
					<th class="border-bottom"><i class="ball-noraml">17</i></th>
					<th class="border-bottom"><i class="ball-noraml">18</i></th>
					<th class="border-bottom"><i class="ball-noraml">19</i></th>
					<th class="border-bottom"><i class="ball-noraml">20</i></th>
					<th class="border-bottom"><i class="ball-noraml">21</i></th>
					<th class="border-bottom"><i class="ball-noraml">22</i></th>
					<th class="ball-none border-bottom border-right"></th>
					<th class="ball-none border-bottom"></th>
					<th class="border-bottom"><i class="ball-noraml">23</i></th>
					<th class="border-bottom"><i class="ball-noraml">24</i></th>
					<th class="border-bottom"><i class="ball-noraml">25</i></th>
					<th class="border-bottom"><i class="ball-noraml">26</i></th>
					<th class="border-bottom"><i class="ball-noraml">27</i></th>
					<th class="border-bottom"><i class="ball-noraml">28</i></th>
					<th class="border-bottom"><i class="ball-noraml">29</i></th>
					<th class="border-bottom"><i class="ball-noraml">30</i></th>
					<th class="border-bottom"><i class="ball-noraml">31</i></th>
					<th class="border-bottom"><i class="ball-noraml">32</i></th>
					<th class="border-bottom"><i class="ball-noraml">33</i></th>
					<th class="ball-none border-bottom border-right"></th>
					<th rowspan="2" class="border-top border-right border-bottom">区比</th>
					<th rowspan="2" class="border-top border-right border-bottom">大小比</th>
					<th rowspan="2" class="border-top border-right border-bottom">奇偶比</th>
					<th rowspan="2" class="border-top border-right border-bottom">和值</th>
					<th rowspan="2" class="border-top border-right border-bottom">和值尾数</th>
				</tr>
				<tr class="title-text">
					<th colspan="13" class="border-top border-right border-bottom">一区</th>
					<th colspan="13" class="border-top border-right border-bottom">二区</th>
					<th colspan="13" class="border-top border-right border-bottom">三区</th>
				</tr>
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
			for( var i=0, d=data[1].split(','), l=d.length, _h=''; i<l; i++ ){
				if( i+1 == l ){
					_h += '<span style="padding:0 2px;">+</span>';
					_h += '<span style="padding:0 2px;color:#00a2ff;">' + d[i] + '</span>';
				}else{
					_h += '<span style="padding:0 2px;color:#f53f00;">' + d[i] + '</span>';
				}				
			}
			td.innerHTML = '<span class="lottery-numbers">' + _h + '</span>';
			parentDom.appendChild(td);

			td = document.createElement('td');
			td.className = 'ball-none border-right' + styleName;
			parentDom.appendChild(td);
			
			// 红球区一
			parentDom.appendChild(me.singleLotteryBall(data[2], styleName));
			// 红球区二
			parentDom.appendChild(me.singleLotteryBall(data[3], styleName));
			// 红球区三
			parentDom.appendChild(me.singleLotteryBall(data[4], styleName));
			
			// 区比
			td = document.createElement('td');
			td.className = "bg-blue border-right " + styleName;
			td.innerHTML = data[5];
			parentDom.appendChild(td);

			// 大小比
			td = document.createElement('td');
			td.className = "bg-green border-right " + styleName;
			td.innerHTML = data[6];
			parentDom.appendChild(td);

			// 奇偶比
			td = document.createElement('td');
			td.className = "bg-blue border-right " + styleName;
			td.innerHTML = data[7];
			parentDom.appendChild(td);

			// 和值
			td = document.createElement('td');
			td.className = "bg-green border-right " + styleName;
			td.innerHTML = data[8];
			parentDom.appendChild(td);

			// 和值尾数
			td = document.createElement('td');
			td.className = "bg-blue border-right " + styleName;
			td.innerHTML = data[9];
			parentDom.appendChild(td);
			

			//返回完整的单行TR结构
			return parentDom;
		},
		singleLotteryBall: function(ballsData, styleName, blueBall) {
			var td1, td2, td3,
				me = this,
				htmlData = document.createDocumentFragment(),
				borderStyleText = styleName,
				lostBallstyle = '',
				ballStyleText = '';
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
			
			
			for(i = 0, len = 33; i < len; i++){
				tdstr = ((i+1)%11 == 0) ? '<td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>' : '';			
				tdstr = (i == (len - 1)) ? '<td class="border-right border-bottom"></td>' : tdstr;

				frame1.push('<td class="border-bottom"><i class="ball-noraml">'+ data[0][i] +'</i></td>' + tdstr);
				frame2.push('<td class="border-bottom"><i class="ball-noraml">'+ data[1][i] +'</i></td>' + tdstr);
				frame3.push('<td class="border-bottom"><i class="ball-noraml">'+ data[2][i] +'</i></td>' + tdstr);
				frame4.push('<td class="border-bottom"><i class="ball-noraml">'+ data[3][i] +'</i></td>' + tdstr);
			}
			
			for(i = 0; i < 5; i++){
				frame1.push('<td class="border-right border-bottom"></td>');
				frame2.push('<td class="border-right border-bottom"></td>');
				frame3.push('<td class="border-right border-bottom"></td>');
				frame4.push('<td class="border-right border-bottom"></td>');
			}

			frame1.push('</tr>');
			frame2.push('</tr>');
			frame3.push('</tr>');
			frame4.push('</tr>');
			$(me.getBallContainer()).append($(frame1.join(''))).append($(frame2.join(''))).append($(frame3.join(''))).append($(frame4.join('')));
		},
		cqsscQiansanTrendCanvas:function(dom, data){
			/*var positionCount = 0,
				me = this,
				currentBallLeft = 0,
				currentBallTop = 0,
				chartTrendPosition = me.getChartTrendPosition();


			//遍历分段渲染数据
			for (var i = 0, current; i < data.length; i++) {
				current = data[i];

				for (var k = 0; k < current.length; k++) {
					//选球区域
					if (k > 1 && k < 5) {
						for (var j = 0; j < current[k].length; j++) {
						
							if(j == 0)	{
								var currentDom = dom.getElementsByTagName('i')[positionCount].parentNode,
									unitSize = me.getUnitSize(currentDom),
									top = unitSize.topNum,
									left = unitSize.leftNum,
									width = unitSize.widthNum,
									height = unitSize.heightNum;
							}

							//当前位置球
							positionCount ++;

							//当前号码
							if (current[k][j][0] == 0) {
								//第一排渲染
								if (typeof chartTrendPosition[k] == 'undefined') {

									//当前球的坐标
									currentBallLeft = left + (j + 1) * width - width / 2;
									currentBallTop = top + height / 2;

									chartTrendPosition[k] = {};
									chartTrendPosition[k]['top'] = currentBallTop;
									chartTrendPosition[k]['left'] = currentBallLeft;
								} else {

									//当前球的坐标
									currentBallLeft = left + (j + 1) * width - width / 2;
									currentBallTop = chartTrendPosition[k]['top'] + height;

									//绘制画布
									//绘制走势图线
									me.draw.setOption({
										parentContainer: $('#J-chart-area')[0]
									});
									me.draw.line(chartTrendPosition[k]['top'], chartTrendPosition[k]['left'], currentBallTop, currentBallLeft);

									chartTrendPosition[k]['top'] = currentBallTop;
									chartTrendPosition[k]['left'] = currentBallLeft;
								}
							}
						};
					}
				};

				positionCount = 0;
			};*/
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