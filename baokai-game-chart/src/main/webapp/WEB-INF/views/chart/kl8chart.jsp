<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
	<title>${lotteryName}-走势图</title>
</head>
<body class="table-trend">
	<div class="header" style=""></div>
	<div class="select-section clearfix">
		<div class="select-function">
			<a href="#" id="J-button-showcontrol">展开功能区</a><i class="arrow-down"></i>
			<a id="J-button-export" class="btn" href="javascript:void(0);">报表下载<b class="btn-inner"></b></a>
		</div>
		<h3 class="select-title">彩种：${lotteryName}</h3>
		<ul class="select-list">
			<li class="current"><a href="Quwei">基本</a></li>
		</ul>
	</div>
	<div class="select-section-content clearfix" id="J-panel-control">
		<div class="title">快乐8</div>
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
	<div class="chart-section" id="J-chart-area" style="width:2110px;">
		<table class="chart-table" id="J-chart-table">
			<thead class="thead">
				<tr class="title-text">
					<th rowspan="2" colspan="3" class="border-bottom border-right">期号</th>
					<th colspan="82" class="border-right border-bottom">开奖号码</th>
					<th rowspan="2" class="border-right border-bottom">大小</th>
					<th rowspan="2" class="border-right border-bottom">单双</th>
					<th rowspan="2" class="border-right border-bottom">上下</th>
					<th rowspan="2" class="border-right border-bottom">奇偶</th>
					<th rowspan="2" class="border-bottom">和值</th>
				</tr>
				<tr class="title-number">
				
					<th class="ball-none border-bottom-header"></th>	
					
					<th class="border-bottom"><i class="ball-noraml">1</i></th>
					<th class="border-bottom"><i class="ball-noraml">2</i></th>
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
					<th class="border-bottom"><i class="ball-noraml">19</i></th>
					<th class="border-bottom"><i class="ball-noraml">20</i></th>
					
					<th class="border-bottom"><i class="ball-noraml">21</i></th>
					<th class="border-bottom"><i class="ball-noraml">22</i></th>
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
					<th class="border-bottom"><i class="ball-noraml">34</i></th>
					<th class="border-bottom"><i class="ball-noraml">35</i></th>
					<th class="border-bottom"><i class="ball-noraml">36</i></th>
					<th class="border-bottom"><i class="ball-noraml">37</i></th>
					<th class="border-bottom"><i class="ball-noraml">38</i></th>
					<th class="border-bottom"><i class="ball-noraml">39</i></th>
					<th class="border-bottom"><i class="ball-noraml">40</i></th>
					
					<th class="border-bottom"><i class="ball-noraml">41</i></th>
					<th class="border-bottom"><i class="ball-noraml">42</i></th>
					<th class="border-bottom"><i class="ball-noraml">43</i></th>
					<th class="border-bottom"><i class="ball-noraml">44</i></th>
					<th class="border-bottom"><i class="ball-noraml">45</i></th>
					<th class="border-bottom"><i class="ball-noraml">46</i></th>
					<th class="border-bottom"><i class="ball-noraml">47</i></th>
					<th class="border-bottom"><i class="ball-noraml">48</i></th>
					<th class="border-bottom"><i class="ball-noraml">49</i></th>
					<th class="border-bottom"><i class="ball-noraml">50</i></th>
					
					<th class="border-bottom"><i class="ball-noraml">51</i></th>
					<th class="border-bottom"><i class="ball-noraml">52</i></th>
					<th class="border-bottom"><i class="ball-noraml">53</i></th>
					<th class="border-bottom"><i class="ball-noraml">54</i></th>
					<th class="border-bottom"><i class="ball-noraml">55</i></th>
					<th class="border-bottom"><i class="ball-noraml">56</i></th>
					<th class="border-bottom"><i class="ball-noraml">57</i></th>
					<th class="border-bottom"><i class="ball-noraml">58</i></th>
					<th class="border-bottom"><i class="ball-noraml">59</i></th>
					<th class="border-bottom"><i class="ball-noraml">60</i></th>
					
					<th class="border-bottom"><i class="ball-noraml">61</i></th>
					<th class="border-bottom"><i class="ball-noraml">62</i></th>
					<th class="border-bottom"><i class="ball-noraml">63</i></th>
					<th class="border-bottom"><i class="ball-noraml">64</i></th>
					<th class="border-bottom"><i class="ball-noraml">65</i></th>
					<th class="border-bottom"><i class="ball-noraml">66</i></th>
					<th class="border-bottom"><i class="ball-noraml">67</i></th>
					<th class="border-bottom"><i class="ball-noraml">68</i></th>
					<th class="border-bottom"><i class="ball-noraml">69</i></th>
					<th class="border-bottom"><i class="ball-noraml">70</i></th>
					
					<th class="border-bottom"><i class="ball-noraml">71</i></th>
					<th class="border-bottom"><i class="ball-noraml">72</i></th>
					<th class="border-bottom"><i class="ball-noraml">73</i></th>
					<th class="border-bottom"><i class="ball-noraml">74</i></th>
					<th class="border-bottom"><i class="ball-noraml">75</i></th>
					<th class="border-bottom"><i class="ball-noraml">76</i></th>
					<th class="border-bottom"><i class="ball-noraml">77</i></th>
					<th class="border-bottom"><i class="ball-noraml">78</i></th>
					<th class="border-bottom"><i class="ball-noraml">79</i></th>
					<th class="border-bottom"><i class="ball-noraml">80</i></th>
					
					<td class="border-bottom border-right"></td>
					
					
				</tr>
			</thead>
			<tbody id="J-chart-content" class="chart table-guides">
				<tr></tr>
			</tbody>
			
			
			
			
			<tbody id="J-ball-content" class="tbody">

			

			</tbody>
			
			<tbody class="tbody tbody-footer-header">
				<tr class="auxiliary-area title-number">
				
					<th rowspan="2" colspan="3" class="border-bottom border-right">期号</th>
					
					<td class="border-bottom"></td>
					
					<td class="border-bottom"><i class="ball-noraml">1</i></td>
					<td class="border-bottom"><i class="ball-noraml">2</i></td>
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
					<td class="border-bottom"><i class="ball-noraml">19</i></td>
					<td class="border-bottom"><i class="ball-noraml">20</i></td>
					
					<td class="border-bottom"><i class="ball-noraml">21</i></td>
					<td class="border-bottom"><i class="ball-noraml">22</i></td>
					<td class="border-bottom"><i class="ball-noraml">23</i></td>
					<td class="border-bottom"><i class="ball-noraml">24</i></td>
					<td class="border-bottom"><i class="ball-noraml">25</i></td>
					<td class="border-bottom"><i class="ball-noraml">26</i></td>
					<td class="border-bottom"><i class="ball-noraml">27</i></td>
					<td class="border-bottom"><i class="ball-noraml">28</i></td>
					<td class="border-bottom"><i class="ball-noraml">29</i></td>
					<td class="border-bottom"><i class="ball-noraml">30</i></td>
					
					<td class="border-bottom"><i class="ball-noraml">31</i></td>
					<td class="border-bottom"><i class="ball-noraml">32</i></td>
					<td class="border-bottom"><i class="ball-noraml">33</i></td>
					<td class="border-bottom"><i class="ball-noraml">34</i></td>
					<td class="border-bottom"><i class="ball-noraml">35</i></td>
					<td class="border-bottom"><i class="ball-noraml">36</i></td>
					<td class="border-bottom"><i class="ball-noraml">37</i></td>
					<td class="border-bottom"><i class="ball-noraml">38</i></td>
					<td class="border-bottom"><i class="ball-noraml">39</i></td>
					<td class="border-bottom"><i class="ball-noraml">40</i></td>
					
					<td class="border-bottom"><i class="ball-noraml">41</i></td>
					<td class="border-bottom"><i class="ball-noraml">42</i></td>
					<td class="border-bottom"><i class="ball-noraml">43</i></td>
					<td class="border-bottom"><i class="ball-noraml">44</i></td>
					<td class="border-bottom"><i class="ball-noraml">45</i></td>
					<td class="border-bottom"><i class="ball-noraml">46</i></td>
					<td class="border-bottom"><i class="ball-noraml">47</i></td>
					<td class="border-bottom"><i class="ball-noraml">48</i></td>
					<td class="border-bottom"><i class="ball-noraml">49</i></td>
					<td class="border-bottom"><i class="ball-noraml">50</i></td>
					
					<td class="border-bottom"><i class="ball-noraml">51</i></td>
					<td class="border-bottom"><i class="ball-noraml">52</i></td>
					<td class="border-bottom"><i class="ball-noraml">53</i></td>
					<td class="border-bottom"><i class="ball-noraml">54</i></td>
					<td class="border-bottom"><i class="ball-noraml">55</i></td>
					<td class="border-bottom"><i class="ball-noraml">56</i></td>
					<td class="border-bottom"><i class="ball-noraml">57</i></td>
					<td class="border-bottom"><i class="ball-noraml">58</i></td>
					<td class="border-bottom"><i class="ball-noraml">59</i></td>
					<td class="border-bottom"><i class="ball-noraml">60</i></td>
					
					<td class="border-bottom"><i class="ball-noraml">61</i></td>
					<td class="border-bottom"><i class="ball-noraml">62</i></td>
					<td class="border-bottom"><i class="ball-noraml">63</i></td>
					<td class="border-bottom"><i class="ball-noraml">64</i></td>
					<td class="border-bottom"><i class="ball-noraml">65</i></td>
					<td class="border-bottom"><i class="ball-noraml">66</i></td>
					<td class="border-bottom"><i class="ball-noraml">67</i></td>
					<td class="border-bottom"><i class="ball-noraml">68</i></td>
					<td class="border-bottom"><i class="ball-noraml">69</i></td>
					<td class="border-bottom"><i class="ball-noraml">70</i></td>
					
					<td class="border-bottom"><i class="ball-noraml">71</i></td>
					<td class="border-bottom"><i class="ball-noraml">72</i></td>
					<td class="border-bottom"><i class="ball-noraml">73</i></td>
					<td class="border-bottom"><i class="ball-noraml">74</i></td>
					<td class="border-bottom"><i class="ball-noraml">75</i></td>
					<td class="border-bottom"><i class="ball-noraml">76</i></td>
					<td class="border-bottom"><i class="ball-noraml">77</i></td>
					<td class="border-bottom"><i class="ball-noraml">78</i></td>
					<td class="border-bottom"><i class="ball-noraml">79</i></td>
					<td class="border-bottom"><i class="ball-noraml">80</i></td>
					
					<td class="border-right border-bottom"></td>
					
					<th rowspan="2" class="border-right border-bottom">大小</th>
					<th rowspan="2" class="border-right border-bottom">单双</th>
					<th rowspan="2" class="border-right border-bottom">上下</th>
					<th rowspan="2" class="border-right border-bottom">奇偶</th>
					<th rowspan="2" class="border-right border-bottom">和值</th>
					
				</tr>
				<tr class="auxiliary-area title-text">
					<td colspan="82" class="border-bottom">开奖号码</td>
				</tr>
			
			</tbody>
			

		</table>
		
		<div style="height:100px;"></div>
	</div>
	<!-- End 五星 -->
<script type="text/javascript">
    /**
     * Created by chengjie on 14-6-12.
     */

    var CHART;
    (function($){
        CHART = new phoenix.GameCharts({currentGameType:'${view}', getDataUrl: json_data_url, currentGameMethod: '${groupCode}', expands:{
            ${view}${groupCode}Render:function(data, currentNum){
                var td,
                        current,
                        me = this,
                        i = 0,
                        len = 0,
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

                parentDom.appendChild(me.singleLotteryBall(data[2], styleName));



                td = document.createElement('td');
                td.className = "border-right border-bottom" + styleName;
                td.innerHTML = data[3] == 1 ? '大' : '小';
                parentDom.appendChild(td);

                td = document.createElement('td');
                td.className = "border-right border-bottom" + styleName;
                td.innerHTML = data[4] == 1 ? '单' : '双';
                parentDom.appendChild(td);

                td = document.createElement('td');
                td.className = "border-right border-bottom" + styleName;
                if(data[5] == 1){
                    td.innerHTML = '中';
                }else if(data[5] == 0){
                    td.innerHTML = '下';
                }else{
                    td.innerHTML = '上';
                }
                parentDom.appendChild(td);


                td = document.createElement('td');
                td.className = "border-right border-bottom" + styleName;
                if(data[6] == 1){
                    td.innerHTML = '和';
                }else if(data[6] == 0){
                    td.innerHTML = '奇';
                }else{
                    td.innerHTML = '偶';
                }
                parentDom.appendChild(td);


                td = document.createElement('td');
                td.className = "border-bottom" + styleName;
                td.innerHTML = data[7];
                parentDom.appendChild(td);


                //返回完整的单行TR结构
                return parentDom;
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
                frame1.push('<tr class="auxiliary-area"><td class="ball-none"></td><td class="border-bottom border-top">出现总次数</td><td class="ball-none border-right border-bottom"></td><td class="ball-none  border-bottom"></td>');
                frame2.push('<tr class="auxiliary-area"><td class="ball-none border-bottom"></td><td class="border-bottom">平均遗漏值</td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>');
                frame3.push('<tr class="auxiliary-area"><td class="ball-none border-bottom"></td><td class="border-bottom">最大遗漏值</td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>');
                frame4.push('<tr class="auxiliary-area"><td class="ball-none border-bottom"></td><td class="border-bottom">最大连出值</td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>');


                for(i = 0, len = 80; i < len; i++){
                    frame1.push('<td class="border-bottom"><i class="ball-noraml">'+ data[0][i] +'</i></td>');
                    frame2.push('<td class="border-bottom"><i class="ball-noraml">'+ data[1][i] +'</i></td>');
                    frame3.push('<td class="border-bottom"><i class="ball-noraml">'+ data[2][i] +'</i></td>');
                    frame4.push('<td class="border-bottom"><i class="ball-noraml">'+ data[3][i] +'</i></td>');
                }

                for(i = 0, len = 6; i < len; i++){
                    frame1.push('<td class="border-bottom border-right"></td>');
                    frame2.push('<td class="border-bottom border-right"></td>');
                    frame3.push('<td class="border-bottom border-right"></td>');
                    frame4.push('<td class="border-bottom border-right"></td>');
                }


                frame1.push('</tr>');
                frame2.push('</tr>');
                frame3.push('</tr>');
                frame4.push('</tr>');
                $(me.getBallContainer()).append($(frame1.join(''))).append($(frame2.join(''))).append($(frame3.join(''))).append($(frame4.join('')));
            },
            ${view}${groupCode}TrendCanvas:function(dom, data){

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
                        arr.push(i+1);
                        arrRow.push(arr.join('_'));
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