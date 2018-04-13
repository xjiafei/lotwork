<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <li><a href="Wuxing">五星</a></li>
        <li class="current"><a href="Zonghe">五星综合</a></li>
        <li><a href="Sixing">四星</a></li>
        <li><a href="Qiansan">前三</a></li>
        <li><a href="Zhongsan">中三</a></li>
        <li><a href="Housan">后三</a></li>
        <li><a href="Qianer">前二</a></li>
        <li><a href="Houer">后二</a></li>
    </ul>
</div>
<div class="select-section-content clearfix" id="J-panel-control">
    <div class="title">五星走势图</div>
    <div class="function">
        <label class="label"><input data-action="guides" type="checkbox" class="checkbox" checked="checked">辅助线</label>
        <label class="label"><input data-action="lost" type="checkbox" class="checkbox" checked="checked">遗漏</label>
        <label class="label"><input data-action="lost-post" type="checkbox" class="checkbox">遗漏条</label>
    </div>
    <div class="time" id="J-periods-data" <c:if test="${view eq 'slmmc'}"> style="display:none;" </c:if>>
        <a data-action="periods-30" href="javascript:void(0);">近30期</a>
        <a data-action="periods-50" href="javascript:void(0);">近50期</a>
        <a data-action="day-1" href="javascript:void(0);">今日数据</a>
        <a data-action="day-2" href="javascript:void(0);">近2天</a>
        <a data-action="day-5" href="javascript:void(0);">近5天</a>
    </div>
    <div class="search" <c:if test="${view eq 'slmmc'}"> style="display:none;" </c:if>>
        <input type="text" value="" id="J-date-star" class="input w-3"> - <input id="J-date-end" type="text" value="" class="input w-3">
        <a id="J-time-periods" class="btn" href="javascript:void(0);">查 看<b class="btn-inner"></b></a>
    </div>
</div>
<!-- Star 五星 -->
<div class="chart-section" id="J-chart-area">

    <table class="chart-table" id="J-chart-table">
        <thead class="thead">
        <tr class="title-text">
            <th rowspan="2" colspan="3" class="border-bottom border-right">
                <c:if test="${view=='slmmc'}">方案编号</c:if>
                <c:if test="${view!='slmmc'}">期号</c:if>
            </th>
            <th colspan="3" class="border-right">开奖号码</th>
            <th colspan="12" class="border-right">号码分布</th>
            <th colspan="12" class="border-right">号码跨度</th>
            <th colspan="8" class="border-right">大小比</th>
            <th colspan="8" class="border-right">单双比</th>
            <th colspan="8" class="border-right">质合比</th>
            <th rowspan="2" class="border-bottom">和值</th>
        </tr>
        <tr class="title-number">
            <th class="ball-none border-bottom"></th>
            <th class="border-bottom"><label class="label"><input type="checkbox" class="checkbox">全部</label></th>
            <th class="ball-none border-bottom border-right"></th>
            <th class="ball-none border-bottom"></th>
            <th class="border-bottom"><i class="ball-noraml">0</i></th>
            <th class="border-bottom"><i class="ball-noraml">1</i></th>
            <th class="border-bottom"><i class="ball-noraml">2</i></th>
            <th class="border-bottom"><i class="ball-noraml">3</i></th>
            <th class="border-bottom"><i class="ball-noraml">4</i></th>
            <th class="border-bottom"><i class="ball-noraml">5</i></th>
            <th class="border-bottom"><i class="ball-noraml">6</i></th>
            <th class="border-bottom"><i class="ball-noraml">7</i></th>
            <th class="border-bottom"><i class="ball-noraml">8</i></th>
            <th class="border-bottom"><i class="ball-noraml">9</i></th>
            <th class="ball-none border-bottom border-right"></th>
            <th class="ball-none border-bottom"></th>
            <th class="border-bottom"><i class="ball-noraml">0</i></th>
            <th class="border-bottom"><i class="ball-noraml">1</i></th>
            <th class="border-bottom"><i class="ball-noraml">2</i></th>
            <th class="border-bottom"><i class="ball-noraml">3</i></th>
            <th class="border-bottom"><i class="ball-noraml">4</i></th>
            <th class="border-bottom"><i class="ball-noraml">5</i></th>
            <th class="border-bottom"><i class="ball-noraml">6</i></th>
            <th class="border-bottom"><i class="ball-noraml">7</i></th>
            <th class="border-bottom"><i class="ball-noraml">8</i></th>
            <th class="border-bottom"><i class="ball-noraml">9</i></th>
            <th class="ball-none border-bottom border-right"></th>
            <th class="ball-none border-bottom"></th>
            <th class="border-bottom">5:0</th>
            <th class="border-bottom">4:1</th>
            <th class="border-bottom">3:2</th>
            <th class="border-bottom">2:3</th>
            <th class="border-bottom">1:4</th>
            <th class="border-bottom">0:5</th>
            <th class="ball-none border-bottom border-right"></th>
            <th class="ball-none border-bottom"></th>
            <th class="border-bottom">5:0</th>
            <th class="border-bottom">4:1</th>
            <th class="border-bottom">3:2</th>
            <th class="border-bottom">2:3</th>
            <th class="border-bottom">1:4</th>
            <th class="border-bottom">0:5</th>
            <th class="ball-none border-bottom border-right"></th>
            <th class="ball-none border-bottom"></th>
            <th class="border-bottom">5:0</th>
            <th class="border-bottom">4:1</th>
            <th class="border-bottom">3:2</th>
            <th class="border-bottom">2:3</th>
            <th class="border-bottom">1:4</th>
            <th class="border-bottom">0:5</th>
            <th class="ball-none border-bottom border-right"></th>
        </tr>
        </thead>
        <tbody id="J-chart-content" class="tbody  table-guides">
        </tbody>
        <tbody id="J-ball-content" class="tbody">
        </tbody>
        <tbody class="tbody tbody-footer-header">
        <tr class="auxiliary-area title-number">
            <td class="border-right" colspan="3" rowspan="2">
                <c:if test="${view=='slmmc'}">方案编号</c:if>
                <c:if test="${view!='slmmc'}">期号</c:if>
            </td>
            <td class="border-right" colspan="3" rowspan="2">开奖号码</td>
            <td class="ball-none border-bottom"></td>
            <td class="border-bottom"><i class="ball-noraml">0</i></td>
            <td class="border-bottom"><i class="ball-noraml">1</i></td>
            <td class="border-bottom"><i class="ball-noraml">2</i></td>
            <td class="border-bottom"><i class="ball-noraml">3</i></td>
            <td class="border-bottom"><i class="ball-noraml">4</i></td>
            <td class="border-bottom"><i class="ball-noraml">5</i></td>
            <td class="border-bottom"><i class="ball-noraml">6</i></td>
            <td class="border-bottom"><i class="ball-noraml">7</i></td>
            <td class="border-bottom"><i class="ball-noraml">8</i></td>
            <td class="border-bottom"><i class="ball-noraml">9</i></td>
            <td class="ball-none border-right border-bottom"></td>
            <td class="ball-none border-bottom"></td>
            <td class="border-bottom"><i class="ball-noraml">0</i></td>
            <td class="border-bottom"><i class="ball-noraml">1</i></td>
            <td class="border-bottom"><i class="ball-noraml">2</i></td>
            <td class="border-bottom"><i class="ball-noraml">3</i></td>
            <td class="border-bottom"><i class="ball-noraml">4</i></td>
            <td class="border-bottom"><i class="ball-noraml">5</i></td>
            <td class="border-bottom"><i class="ball-noraml">6</i></td>
            <td class="border-bottom"><i class="ball-noraml">7</i></td>
            <td class="border-bottom"><i class="ball-noraml">8</i></td>
            <td class="border-bottom"><i class="ball-noraml">9</i></td>
            <td class="ball-none border-right border-bottom"></td>
            <td class="ball-none border-bottom"></td>
            <td class="border-bottom">5:0</td>
            <td class="border-bottom">4:1</td>
            <td class="border-bottom">3:2</td>
            <td class="border-bottom">2:3</td>
            <td class="border-bottom">1:4</td>
            <td class="border-bottom">0:5</td>
            <td class="ball-none border-right border-bottom"></td>
            <td class="ball-none border-bottom"></td>
            <td class="border-bottom">5:0</td>
            <td class="border-bottom">4:1</td>
            <td class="border-bottom">3:2</td>
            <td class="border-bottom">2:3</td>
            <td class="border-bottom">1:4</td>
            <td class="border-bottom">0:5</td>
            <td class="ball-none border-right border-bottom"></td>
            <td class="ball-none border-bottom"></td>
            <td class="border-bottom">5:0</td>
            <td class="border-bottom">4:1</td>
            <td class="border-bottom">3:2</td>
            <td class="border-bottom">2:3</td>
            <td class="border-bottom">1:4</td>
            <td class="border-bottom">0:5</td>
            <td class="ball-none border-right border-bottom"></td>
            <td rowspan="2">和值</td>
        </tr>
        <tr class="auxiliary-area title-text">
            <td class="border-right" colspan="12">号码分布</td>
            <td class="border-right" colspan="12">号码跨度</td>
            <td class="border-right" colspan="8">大小比</td>
            <td class="border-right" colspan="8">单双比</td>
            <td class="border-right" colspan="8">质合比</td>
        </tr>
        </tbody>
    </table>


</div>
<script type="text/javascript">

var CHART;
(function($){
    CHART = new phoenix.GameCharts({currentGameType:'${view}', getDataUrl: json_data_url, currentGameMethod: '${groupCode}', expands:{
        ${view}${groupCode}Render:function(data, currentNum){
            var me = this,
				td,
				len,
				current,
				lostBallstyle = '',
				arrStr = [],
				styleName = currentNum % me.getSeparateNum() == 0 ? ' border-bottom' : '',
				htmlTextArr = new Array(),
				allowCount = me.getRenderLength(),
				parentDom = document.createElement('tr');
			if(currentGameType != 'slmmc'){
				 //期号号码
				td = document.createElement('td');
				td.className = "ball-none " + styleName;
				parentDom.appendChild(td);

				td = document.createElement('td');
				td.className = "issue-numbers " + styleName;
				td.innerHTML = data[0];
				parentDom.appendChild(td);

				td = document.createElement('td');
				td.className = "ball-none border-right" + styleName;
				parentDom.appendChild(td);


				//开奖号码
				td = document.createElement('td');
				td.className = "ball-none " + styleName;
				parentDom.appendChild(td);

				td = document.createElement('td');
				td.className = styleName;
				td.innerHTML = '<span class="lottery-numbers">' + data[1] + '</span>';
				parentDom.appendChild(td);
			}else{
				//开奖号码
				td = document.createElement('td');
				td.className = "ball-none " + styleName;
				parentDom.appendChild(td);

				td = document.createElement('td');
				td.className = styleName;
				td.setAttribute("colspan", '4');	
				td.innerHTML = '<span class="lottery-numbers">' + data[1] + '</span>';
				parentDom.appendChild(td);
			}           

            td = document.createElement('td');
            td.className = 'ball-none border-right' + styleName;
            parentDom.appendChild(td);


            //号码分布
            td = document.createElement('td');
            td.className = "ball-none " + styleName;
            parentDom.appendChild(td);
            current = data[2];
            len = current.length;
            for (var i = 0; i < len; i++) {
                //是否为开奖号
                ballStyleText = 'zh-' + current[i][0] + '-' + (current[i][2] > 1 ? 2 : 1);
                td = document.createElement('td');
                td.className = styleName + ' l-' + current[i][3];
                td.innerHTML = '<i data-info="' + current[i] + '" class="ball-noraml ' + ballStyleText + '">' + (current[i][0] == 0 ? current[i][1] : current[i][0]) + '</i>';
                parentDom.appendChild(td);
            };

            td = document.createElement('td');
            td.className = 'ball-none border-right' + styleName;
            parentDom.appendChild(td);


            //号码跨度
            td = document.createElement('td');
            td.className = 'ball-none' + styleName;
            parentDom.appendChild(td);
            current = data[3];
            len = current.length;
            ballStyleText = '';
            for (var i = 0; i < len; i++) {
                ballStyleText = 'zhkd-' + (current[i][0] > 0 ? 0 : 1);
                td = document.createElement('td');
                td.className = styleName + ' omission ' + 'l-' + current[i][2];
                td.innerHTML = '<i data-info="' + current[i] + '" class="ball-noraml ' + ballStyleText + '">' + (current[i][0] > 0 ? current[i][0] : current[i][1]) + '</i>'
                parentDom.appendChild(td);
            };

            td = document.createElement('td');
            td.className = 'ball-none border-right' + styleName;
            parentDom.appendChild(td);

            //大小比
            td = document.createElement('td');
            td.className = 'ball-none' + styleName;
            parentDom.appendChild(td);
            current = data[4];
            len = current.length;
            ballStyleText = '';
            lostBallstyle = '';
            for (var i = 0; i < len; i++) {
                lostBallstyle = 'zhdx-' + (current[i][0] == 0 ? 0 : 1) + ' l-' + current[i][3];
                td = document.createElement('td');
                td.className = styleName + ' omission ' + lostBallstyle;   
                td.innerHTML = '<i data-info="' + current[i] + '" class="ball-noraml ' + ballStyleText + '">' + (current[i][0] == 0 ? (current[i][1] + ':' + current[i][2])  : current[i][0]) + '</i>'
                parentDom.appendChild(td);
            };

            td = document.createElement('td');
            td.className = 'ball-none border-right' + styleName;
            parentDom.appendChild(td);


            //单双比
            td = document.createElement('td');
            td.className = 'ball-none' + styleName;
            parentDom.appendChild(td);
            current = data[5];
            len = current.length;
            ballStyleText = '';
            lostBallstyle = '';
            for (var i = 0; i < len; i++) {
                lostBallstyle = 'zhds-' + (current[i][0] == 0 ? 0 : 1) + ' l-' + current[i][3];
                td = document.createElement('td');
                td.className = styleName + ' omission ' + lostBallstyle;
                td.innerHTML = '<i data-info="' + current[i] + '" class="ball-noraml ' + ballStyleText + '">' + (current[i][0] == 0 ? (current[i][1] + ':' + current[i][2]) : current[i][0]) + '</i>'
                parentDom.appendChild(td);
            };

            td = document.createElement('td');
            td.className = 'ball-none border-right' + styleName;
            parentDom.appendChild(td);


            //质合比
            td = document.createElement('td');
            td.className = 'ball-none' + styleName;
            parentDom.appendChild(td);
            current = data[6];
            len = current.length;
            ballStyleText = '';
            lostBallstyle = '';
            for (var i = 0; i < len; i++) {
                lostBallstyle = 'zhzh-' + (current[i][0] == 0 ? 0 : 1) + ' l-' + current[i][3];;
                td = document.createElement('td');
                td.className = styleName + ' omission ' + lostBallstyle;
                td.innerHTML = '<i data-info="' + current[i] + '" class="ball-noraml ' + ballStyleText + '">' + (current[i][0] == 0 ? (current[i][1] + ':' + current[i][2]) : current[i][0]) + '</i>'
                parentDom.appendChild(td);
            };

            td = document.createElement('td');
            td.className = 'ball-none border-right' + styleName;
            parentDom.appendChild(td);

            //和值
            td = document.createElement('td');
            td.className = styleName;
            td.innerHTML = '<i class="ball-noraml">' + data[7] + '</i>';
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
            frame1.push('<tr class="auxiliary-area"><td class="ball-none"></td><td class="border-bottom border-top">出现总次数</td><td class="ball-none border-right border-bottom"></td><td class="ball-none  border-bottom"></td><td class="border-bottom"></td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>');
            frame2.push('<tr class="auxiliary-area"><td class="ball-none border-bottom"></td><td class="border-bottom">平均遗漏值</td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td><td class="border-bottom"></td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>');
            frame3.push('<tr class="auxiliary-area"><td class="ball-none border-bottom"></td><td class="border-bottom">最大遗漏值</td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td><td class="border-bottom"></td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>');
            frame4.push('<tr class="auxiliary-area"><td class="ball-none border-bottom"></td><td class="border-bottom">最大连出值</td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td><td class="border-bottom"></td><td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>');


            for(i = 0, len = 38; i < len; i++){
                tdstr = (i == 9 || i == 19 || i == 25 || i == 31 || i == 37) ? '<td class="ball-none border-right border-bottom"></td><td class="ball-none border-bottom"></td>' : '';
                tdstr = n == 37 ? '' : tdstr;

                frame1.push('<td class="border-bottom"><i class="ball-noraml">'+ data[0][i] +'</i></td>' + tdstr);
                frame2.push('<td class="border-bottom"><i class="ball-noraml">'+ data[1][i] +'</i></td>' + tdstr);
                frame3.push('<td class="border-bottom"><i class="ball-noraml">'+ data[2][i] +'</i></td>' + tdstr);
                frame4.push('<td class="border-bottom"><i class="ball-noraml">'+ data[3][i] +'</i></td>' + tdstr);
            }

            frame1.push('</tr>');
            frame2.push('</tr>');
            frame3.push('</tr>');
            frame4.push('</tr>');
            $(me.getBallContainer()).append($(frame1.join(''))).append($(frame2.join(''))).append($(frame3.join(''))).append($(frame4.join('')));
			if(currentGameType == 'slmmc'){			
				if($(me.defConfig['chartTableDom']).find('tr:eq(0)>th').size() > 7){        
					$(me.defConfig['chartTableDom']).find('tr:eq(0)>th:eq(1)').attr('colspan','6');
					$(me.defConfig['chartTableDom']).find('tr:eq(0)>th:eq(0)').remove();
				}				
				$(me.defConfig['chartTableDom']).find('tr:eq(1)>th:eq(1)').attr('colspan','4');
			}
			
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