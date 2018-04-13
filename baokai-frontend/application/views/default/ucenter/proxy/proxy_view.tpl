<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/proxy/proxy.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	
	{include file='/default/script-base.tpl'}
	<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="{$path_js}/js/excanvas.min.js"></script><![endif]-->


<body>
{include file='/default/ucenter/header.tpl'}
	<div class="g_33 common-main">
		<div class="g_5">
			<!-- //////////////左侧公共页面////////////// -->
				{include file='/default/ucenter/left.tpl'}
			<!-- /////////////左侧公共页面/////////////// -->				
		</div>
		<div class="g_28 g_last">
			<div class="common-article">
				<div class="title" id="proxyTitle">{$title}</div>
				<ul class="ui-tab-title clearfix" style="margin-bottom:10px;display:none"  id='_showTag'>
					<li class="current">彩票</li>
					<li><a href="{$ptgame_server}/pt/proxy/index">老虎机</a></li>
				</ul>
				<!-- <div class="notice"><i class="close"></i><i class="ico-warning"></i>{$msgs}</div> -->
				<div class="content">
					<!-- start -->
					<div class="statistical-info">
						<table class="table">
							<tbody>
								<tr>
									<td>团队成员：<strong class="light">{$result.teamCount}人（代理{$result.teamACount}人，玩家{$result.teamUCount}人）</strong></td>
									<td rowspan="2"><strong class="highlight">今日返点：<span id="J-data-total-rebates">0</span>元</strong><br />（不包含自己投注所得返点）</td>
								</tr>
								<tr>
									<td>团队余额：<strong class="light">{$result.teamBal}元（不包含自己）</strong></td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- end -->
					<!-- start -->
					<div class="ui-tab statistical">
						<div class="ui-tab-title tab-title-bg clearfix" id="J-chart-tab">
							<input type="hidden" id="J-data-now" value="{$timeNow}" />
							<input type="hidden" id="J-time-start" value="" />
							<input type="hidden" id="J-time-end" value="" />
							{* 后台配置可查询的天数(从今天往前推) *}
							<input type="hidden" id="J-date-bound" value="{$dateDays}" />
							<ul>
								<li class="current" data-time-fn="getTodayBound">今天</li>
								<li data-time-fn="getYestodayBound">昨天</li>
								<li data-time-fn="getBeforeWeekBound">最近7天</li>
							</ul>
							<div class="calendar">
								{literal}
								<input onselectstart="return false;" type="text" value="" id="J-input-start" class="input" />
								 - 
								<input onselectstart="return false;" type="text" value="" id="J-input-end" class="input" />
								&nbsp;
								<a id="J-button-submit" class="btn" href="#">查 询<b class="btn-inner"></b></a>
								
								{/literal}
							</div>
						</div>
						<div class="ui-tab-content ui-tab-content-current">
							<div class="statistical-data">
								<ul>
									<li>投注量<span id="J-data-nums-buy">0</span></li>
									<li>充值量<span id="J-data-nums-load">0</span></li>
									<li>提现量<span id="J-data-nums-withdraw">0</span></li>
									<li>新增用户数<span id="J-data-nums-newMem">0</span></li>
									<li>返点<span id="J-data-nums-rebates">0</span></li>
								</ul>
							</div>
							<div class="statistical-radio" id="J-cont-filter">
								<label for="J-r1"><input type="radio" name="ra_group1" value="0" class="radio" id="J-r1" checked="checked" />提现量</label>
								<label for="J-r2"><input type="radio" name="ra_group1" value="1" class="radio" id="J-r2" />充值量</label>
								<label for="J-r3"><input type="radio" name="ra_group1" value="2" class="radio" id="J-r3" />投注量</label>
								<label for="J-r4"><input type="radio" name="ra_group1" value="3" class="radio" id="J-r4" />返点</label>
								<label for="J-r5"><input type="radio" name="ra_group1" value="4" class="radio" id="J-r5" />新增用户数</label>
								<!-- <span>节假日</span> -->
							</div>
							<div class="statistical-graph" id="J-chart-cont">
							
							
							<!-- <iframe id="J-chart-frame" width="100%" height="100%" scrolling="no" data-base-url="/proxy/chart?ofc=" src="about:blank" frameborder="0"></iframe> -->
							
							
							

							</div>
							<table class="table table-info" id="J-table">

							</table>
						</div>
					</div>
				</div>
			</div>
             <dl class="fund-info-supplement">
				<dt>说明：您可以查询代理首页7天内的记录。</dt>
	         </dl>
		</div>
	</div>
{literal}
<script id="J-row-tpl" type="text/html-tmpl"> 
	<tr>
		<td>${date}</td>
		<td>${withdraw}元</td>
		<td>${load}元</td>
		<td>${buy}元</td>
		<td>${rebates}元</td>
		<td>${newMem}</td>
	</tr>
</script>
{/literal}
{literal}
<script id="J-row-tpl-header" type="text/html-tmpl"> 
	<tr>
		<th>日期</th>
		<th>提现量</th>
		<th>充值量</th>
		<th>投注量</th>
		<th>返点</th>
		<th>新增用户数</th>
	</tr>
</script>
{/literal}

	<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
	
{literal}
<script>
(function($){
	//切换
	var tab = new phoenix.Tab({
		par:'#J-chart-tab',
		triggers:'li',
		eventType:'click'
	}),
	//修改为html版图表
	//chartFrame = $('#J-chart-frame'),
	plot,
	tip,
	chartCont = $('#J-chart-cont'),
	//图表参数
	chartOptions = {
		lines: {
			show: true,
			lineWidth: 2
		},
		colors: ["#009ED0"],
		points: {
			show: true
		},
		xaxis: {
			tickDecimals: 0,
			color: '#EEE',
			tickSize: 1,
			fontSize: 12
		},
		yaxis: {
			color: '#EEE'
		},
		crosshair: {
			mode: "x",
			color:'#CCCCCC'
		},
		grid: {
			borderWidth: 1,
			color: '#D9D9D9',
			hoverable: true,
			autoHighlight: true
		},
		legend: {
			color: '#000'
		}
	},
	//触发请求
	getData,
	filter = $('#J-cont-filter'),
	//输入框
	InputStart = $('#J-input-start'),
	InputEnd = $('#J-input-end'),
	//日期过滤
	dateFilterFn = function(e){
		var Dt = new phoenix.DatePicker({input:this, setDisabled:function(){
			var me = this,tds = me.getContent().find('td'),it,tempDate,_y,_m,_d;
				//n天前的某个日期
				before = dateUtil.getOneDateTime(time_now, -1*3600*24*Number($('#J-date-bound').val()));
				
			tds.each(function(){
				it = $(this);
				_y = Number(it.attr('data-year'));
				_m = Number(it.attr('data-month'));
				_d = Number(it.text());
				tempDate = new Date(_y, _m, _d);
				//console.log(tempDate);
				if(tempDate < before || tempDate > dateUtil.now){
					it.addClass('day-disabled');
				}
			});
		}});
		Dt.show();
	},
	//年月日
	time_now,
	_arrDate = $.trim($('#J-data-now').val()).split(/[^\d]/),
	dateUtil = {},
	setStartEndTime = function(start, end){
		$('#J-time-start').val(start);
		$('#J-time-end').val(end);
	},
	time_y = Number(_arrDate[0]),
	time_m = Number(_arrDate[1]),
	time_d = Number(_arrDate[2]),
	time_h = Number(_arrDate[3]),
	time_s = Number(_arrDate[4]);
	
	time_now = new Date();
	time_now.setFullYear(time_y);
	time_now.setMonth(time_m - 1);
	time_now.setDate(time_d);
	time_now.setHours(time_h);
	time_now.setMinutes(time_s);
	
	
	jQuery.ajax({
		type:  "get",
		url: '/pt/index/checkuserstatus',
		dataType:'json', 
		contentType: "application/json; charset=utf-8",
		data: '',
		cache: false,
		success:function(data){
			if(data.status==1){
				$('#_showTag').show();
				
			}
		},
		error: function(er){
			console.log(er);
		}
	});
	
	dateUtil = {
		now:time_now,
		//获取当前日期前后n秒的日期
		getOneDateTime:function(now, n){
			var now_ms = now.getTime(),n = n || 0,d_n = now_ms + n * 1000,d2 = new Date();
			d2.setTime(d_n)
			return d2;
		},
		getYestodayBound:function(){
			var me = this,now = me.now,result = [],d = new Date();
			d.setFullYear(now.getFullYear());
			d.setMonth(now.getMonth());
			d.setDate(now.getDate() - 1);
			result.push(me.formatDateToString(d, true));
			result.push(me.formatDateToString(d, false));
			return result;
		},
		getTodayBound:function(){
			var me = this,now = me.now,result = [],d = new Date();
			d.setFullYear(now.getFullYear());
			d.setMonth(now.getMonth());
			d.setDate(now.getDate());
			result.push(me.formatDateToString(d, true));
			result.push(now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate());
			return result;
		},
		//前一周时间
		//7天前的 00:01 + 今天已过的时间
		//今天当成1天计算
		getBeforeWeekBound:function(){
			var me = this,now = me.now,result = [],d = new Date();
			d.setFullYear(now.getFullYear());
			d.setMonth(now.getMonth());
			d.setDate(now.getDate() - 6);
			result.push(me.formatDateToString(d, true));
			result.push(now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate());
			return result;
		},
		formatDateToString:function(d, isFirst){
			var str = isFirst ? '00:01' : '23:59';
			return d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate();
		}
	};
	
	
	//初始化首次起始时间
	//默认为当天小时数据
	setStartEndTime(dateUtil.getTodayBound()[0], dateUtil.getTodayBound()[1]);
	

	tab.addEvent('afterSwitch', function(){
		var me = this,fnName,date;
		fnName = me.triggers.eq(me.index).attr('data-time-fn');
		if(fnName && dateUtil[fnName]){
			date = dateUtil[fnName]();
			setStartEndTime(date[0], date[1]);
			getData();
		}
	});
	
	
	//载入数据
	//天模式为 'd' 小时模式为 'h'
	getData = function(){
		var startStr = $('#J-time-start').val(),endStr = $('#J-time-end').val(),start,end,model = 'h',url,param,
			startArr = startStr.split(/[^\d]/),endArr = endStr.split(/[^\d]/),
			type = filter.find('input:checked').val();
		//判断天模式/小时模式
		//超过跨天即为天模式
		model = (startArr[0] != endArr[0] || startArr[1] != endArr[1] || startArr[2] != endArr[2]) ? 'd' : model;
		
		//chart flash 接收参数不能带有特殊符号，故将特殊符号替换为-
		start = startStr.split(/[^\d]/).join('-');
		end = endStr.split(/[^\d]/).join('-');

		url = '/proxy/loadchartdata?timestart=' + start + '&timeend=' + end + '&model=' + model + '&type=' + type;
		
		$.ajax({
			url:url,
			cache:false,
			dataType:'json',
			beforeSend:function(){
				chartCont.html('<div style="text-align:center;height:300px;line-height:300px;">数据加载中...</div>');
			},
			success:function(data){
				var total = data['total'],fields = ['withdraw', 'load', 'buy', 'rebates', 'newMem'],table = $('#J-table'),type,typeText,typeUnit,lineData,tipDivs,lastX;
				$.each(fields, function(){
					$('#J-data-nums-' + this).html(total[this]);
				});
				//初始化页面 的今天返点数据 加载到 总的统计栏
				if(dateUtil.getTodayBound()[0] ==startStr){
					$('#J-data-total-rebates').html(total['rebates']);
				}
				table.html($('#J-row-tpl-header').html());
				$('#J-row-tpl').tmpl(data['fullData']).appendTo(table);
				
				InputStart.val(startStr);
				InputEnd.val(endStr);
				
				chartCont.html('');
				type = Number(data['chartData']['label']);
				typeUnit = type == 4 ? ' 人' : ' 元';
				typeText = $('#J-r' + (type+1)).parent().text();
				data['chartData']['label'] = '&nbsp;' + typeText;
				
				plot = $.plot(chartCont, [data['chartData']], chartOptions);
				if(tip){
					tip.remove();
				}
				tip = $('<div class="chart-tip"><span></span><div></div><div></div></div>').appendTo(document.body);
				tipDivs = tip.find('div');
				tip.css({top:chartCont.offset().top + 8, left:chartCont.offset().left});
				chartCont.bind('plothover', function(e, pos, it){
					var lineData = plot.getData()[0]['data'],i = 0,len = lineData.length,num = Math.round(pos.x),addx = !!document.all ? 0 : 1,
						dateArr,date;
					if(lastX != num){
						tip.show();
						for(;i < len;i++){
							if(lineData[i][0] == num){
								tip.css('left', pos.pageX + addx + 8);
								if(model == 'h'){
									tipDivs[0].innerHTML = '时间：' + ( num < 10 ? '0' + num + ':00' : num + ':00' );
								}else{
									dateArr = $('#J-time-start').val().split(/[^\d]/);
									date = new Date(Number(dateArr[0]), Number(dateArr[1]) - 1, Number(dateArr[2]));
									date.setDate(date.getDate() + num);
									tipDivs[0].innerHTML = '日期：' + date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
								}
								tipDivs[1].innerHTML = typeText + ': ' +lineData[i][1] + typeUnit;
							}
						}
					}
					if(pos.x < 0 || pos.x > plot.getData()[0]['xaxis']['max'] || pos.y < 0 || pos.y > plot.getData()[0]['yaxis']['max']){
						tip.hide();
					}
				});
				
			}
		});

		
	};
	
	InputStart.click(dateFilterFn);
	InputEnd.click(dateFilterFn);
	

	
	$('#J-button-submit').click(function(e){
		e.preventDefault();
		setStartEndTime(InputStart.val(), InputEnd.val());
		getData();
	});
	
	filter.find('input').click(function(){
		getData();
	});
	
	
	//首次获取数据
	$(window).load(function(){
		getData();
		setTimeout(function(){
			//去除多余样式，定位偏上的BUG
			$('#proxyTitle').css('margin-bottom','');
		}, 3000);
	});
	
	
	
	
	
})(jQuery);
</script>
{/literal}
	
	
</body>
</html>