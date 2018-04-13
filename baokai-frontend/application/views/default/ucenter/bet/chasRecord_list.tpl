<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
    {include file='/default/script-base.tpl'}


<body>
{include file='/default/ucenter/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="g_33 common-main">
		<div class="g_5">
		<!-- //////////////左侧公共页面////////////// -->
		{include file='/default/ucenter/left.tpl'}
		<!-- /////////////左侧公共页面/////////////// -->
		</div>
		<div class="g_28 g_last">
			<div class="common-article">
				<div class="title">追号记录</div>
			
				<div class="content">
					<form action="?" method="post" id="J-form">
					<ul class="ui-search clearfix">
						<li class="search-more"  id="divExpansion">展开∨</li>
						<li class="type">
							<label class="ui-label">彩种：</label>
							<select class="ui-select w-3"  name="lotteryId"> 
								<option value="0">全部彩种</option>
								<option value="1">重庆时时彩</option>
								<option value="2">天津时时彩</option>
								<option value="3">重庆11选5</option>
							</select>
						</li>
						<li class="time">
							<label class="ui-label">时间：</label>
							<span class="ico-tab"  pro="0">今天</span>
							<span class="ico-tab"  pro="3">三天</span>
							<span class="ico-tab  ico-tab-current"  pro="7">七天</span>
							<input type="hidden" name="time" id="time" value="7" /> 
						</li>
						<li class="state">
							<label class="ui-label">状态：</label>
							<span class="ico-tab ico-tab-current"  pro="0">全部</span>
							<span class="ico-tab"  pro="1">已中奖</span>
							<span class="ico-tab"  pro="2">未中奖</span>
							<span class="ico-tab"  pro="3">等待开奖</span>
							<input type="hidden" name="status" id="status" value="0" /> 
						</li>
					</ul>
					<ul class="ui-search ui-search-more clearfix"  id="divDetailed">
						<li class="issues">
							<label class="ui-label">期号：</label>
							<select class="ui-select w-3" name="lotteryCount">
								<option value="0">所有奖期</option>
								<option value="1">重庆时时彩</option>
								<option value="2">天津时时彩</option>
								<option value="3">重庆11选5</option>
							</select>
						</li>
						<li class="date">
							<label class="ui-label" for="dateStar">起始日期：</label>
							<input type="text" class="input" id="J-date-start" value="" name="dateStar"> - <input type="text" class="input"  value="" id="J-date-end" name="dateEnd">
						</li>
						<li class="number">
							<label class="ui-label" for="number">方案编号：</label>
							<input type="text" class="input" id="schemeNum" name="schemeNum" value="如：ABC77779">
						</li>
						<li class="btn-search">
							<a href="javascript:void(0);" class="btn btn-important" id="J-button-submit">确 定<b class="btn-inner"></b></a>
							<a href="javascript:void(0);" class="btn btn-link light" id="restoreDefaults">恢复默认项</a>
						</li>
					</ul>
					</form>
					<table class="table table-info">
						<thead>
							<tr>
								<th>追号编号</th>
								<th>彩种</th>
								<th>起始期号</th>
								<th>已追/总期数</th>
								<th>已投/总金额</th>
								<th>状态</th>
								<th>追号时间</th>
								<th>操作项</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>CQ32534</td>
								<td>重庆时时彩</td>
								<td>20121218012</td>
								<td>0/3</td>
								<td><span class="price"><dfn>&yen;</dfn>0.00</span>/<span class="price"><dfn>&yen;</dfn>30.00</span></td>
								<td>未开始</td>
								<td>2012-02-28 22:22:00</td>
								<td><a href="#">查看</a></td>
							</tr>
							<tr>
								<td>CQ32534</td>
								<td>重庆时时彩</td>
								<td>20121218012</td>
								<td>0/3</td>
								<td><span class="price"><dfn>&yen;</dfn>0.00</span>/<span class="price"><dfn>&yen;</dfn>30.00</span></td>
								<td><span class="color-green">进行中</span></td>
								<td>2012-02-28 22:22:00</td>
								<td><a href="#">查看</a></td>
							</tr>
							<tr>
								<td>CQ32534</td>
								<td>重庆时时彩</td>
								<td>20121218012</td>
								<td>0/3</td>
								<td><span class="price"><dfn>&yen;</dfn>0.00</span>/<span class="price"><dfn>&yen;</dfn>30.00</span></td>
								<td><span class="price color-red"><dfn>&yen;</dfn>0.00</span></td>
								<td>2012-02-28 22:22:00</td>
								<td><a href="#">查看</a></td>
							</tr>
							<tr>
								<td>CQ32534</td>
								<td>重庆时时彩</td>
								<td>20121218012</td>
								<td>0/3</td>
								<td><span class="price"><dfn>&yen;</dfn>0.00</span>/<span class="price"><dfn>&yen;</dfn>30.00</span></td>
								<td>未开始</td>
								<td>2012-02-28 22:22:00</td>
								<td><a href="#">查看</a></td>
							</tr>
							<tr>
								<td>CQ32534</td>
								<td>重庆时时彩</td>
								<td>20121218012</td>
								<td>0/3</td>
								<td><span class="price"><dfn>&yen;</dfn>0.00</span>/<span class="price"><dfn>&yen;</dfn>30.00</span></td>
								<td><span class="color-green">进行中</span></td>
								<td>2012-02-28 22:22:00</td>
								<td><a href="#">查看</a></td>
							</tr>
							<tr>
								<td>CQ32534</td>
								<td>重庆时时彩</td>
								<td>20121218012</td>
								<td>0/3</td>
								<td><span class="price"><dfn>&yen;</dfn>0.00</span>/<span class="price"><dfn>&yen;</dfn>30.00</span></td>
								<td><span class="price color-red"><dfn>&yen;</dfn>0.00</span></td>
								<td>2012-02-28 22:22:00</td>
								<td><a href="#">查看</a></td>
							</tr>
							<tr>
								<td>CQ32534</td>
								<td>重庆时时彩</td>
								<td>20121218012</td>
								<td>0/3</td>
								<td><span class="price"><dfn>&yen;</dfn>0.00</span>/<span class="price"><dfn>&yen;</dfn>30.00</span></td>
								<td>未开始</td>
								<td>2012-02-28 22:22:00</td>
								<td><a href="#">查看</a></td>
							</tr>
							<tr>
								<td>CQ32534</td>
								<td>重庆时时彩</td>
								<td>20121218012</td>
								<td>0/3</td>
								<td><span class="price"><dfn>&yen;</dfn>0.00</span>/<span class="price"><dfn>&yen;</dfn>30.00</span></td>
								<td><span class="color-green">进行中</span></td>
								<td>2012-02-28 22:22:00</td>
								<td><a href="#">查看</a></td>
							</tr>
							<tr>
								<td>CQ32534</td>
								<td>重庆时时彩</td>
								<td>20121218012</td>
								<td>0/3</td>
								<td><span class="price"><dfn>&yen;</dfn>0.00</span>/<span class="price"><dfn>&yen;</dfn>30.00</span></td>
								<td><span class="price color-red"><dfn>&yen;</dfn>0.00</span></td>
								<td>2012-02-28 22:22:00</td>
								<td><a href="#">查看</a></td>
															<tr>
								<td>CQ32534</td>
								<td>重庆时时彩</td>
								<td>20121218012</td>
								<td>0/3</td>
								<td><span class="price"><dfn>&yen;</dfn>0.00</span>/<span class="price"><dfn>&yen;</dfn>30.00</span></td>
								<td>未开始</td>
								<td>2012-02-28 22:22:00</td>
								<td><a href="#">查看</a></td>
							</tr>
							<tr>
								<td>CQ32534</td>
								<td>重庆时时彩</td>
								<td>20121218012</td>
								<td>0/3</td>
								<td><span class="price"><dfn>&yen;</dfn>0.00</span>/<span class="price"><dfn>&yen;</dfn>30.00</span></td>
								<td><span class="color-green">进行中</span></td>
								<td>2012-02-28 22:22:00</td>
								<td><a href="#">查看</a></td>
							</tr>
							<tr>
								<td>CQ32534</td>
								<td>重庆时时彩</td>
								<td>20121218012</td>
								<td>0/3</td>
								<td><span class="price"><dfn>&yen;</dfn>0.00</span>/<span class="price"><dfn>&yen;</dfn>30.00</span></td>
								<td><span class="price color-red"><dfn>&yen;</dfn>0.00</span></td>
								<td>2012-02-28 22:22:00</td>
								<td><a href="#">查看</a></td>
															<tr>
								<td>CQ32534</td>
								<td>重庆时时彩</td>
								<td>20121218012</td>
								<td>0/3</td>
								<td><span class="price"><dfn>&yen;</dfn>0.00</span>/<span class="price"><dfn>&yen;</dfn>30.00</span></td>
								<td>未开始</td>
								<td>2012-02-28 22:22:00</td>
								<td><a href="#">查看</a></td>
							</tr>
							<tr>
								<td>CQ32534</td>
								<td>重庆时时彩</td>
								<td>20121218012</td>
								<td>0/3</td>
								<td><span class="price"><dfn>&yen;</dfn>0.00</span>/<span class="price"><dfn>&yen;</dfn>30.00</span></td>
								<td><span class="color-green">进行中</span></td>
								<td>2012-02-28 22:22:00</td>
								<td><a href="#">查看</a></td>
							</tr>
							<tr>
								<td>CQ32534</td>
								<td>重庆时时彩</td>
								<td>20121218012</td>
								<td>0/3</td>
								<td><span class="price"><dfn>&yen;</dfn>0.00</span>/<span class="price"><dfn>&yen;</dfn>30.00</span></td>
								<td><span class="price color-red"><dfn>&yen;</dfn>0.00</span></td>
								<td>2012-02-28 22:22:00</td>
								<td><a href="#">查看</a></td>
							</tr>
						</tbody>
					</table>
					<div class="page-wrapper">
						<div class="page">
							<a class="prev" href="#">上一步</a>
							<a href="#">1</a>
							<a href="#">2</a>
							<a class="current" href="#">3</a>
							<a href="#">4</a>
							<a href="#">5</a>
							<a href="#">6</a>
							<a href="#">7</a>
							<a href="#">8</a>
							<a href="#">9</a>
							<a href="#">...</a>
							<a class="next" href="#">下一步</a>
							<span class="page-few">到第 <input type="text" class="input" value=""> /100页</span>
							<input type="button" class="page-btn" value="确 认">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->
{literal}
<script>
(function(){
	var inputStart = $('#J-date-start'),
		inputEnd = $('#J-date-end'),
		
	dateFilterFn = function(e){
		var Dt = new phoenix.DatePicker({input:this, isShowTime:true, setDisabled:function(){
			var me = this,tds = me.getContent().find('td'),it,tempDate,_y,_m,_d;
				//n天前的某个日期
				before = dateUtil.getOneDateTime(time_now, -1*3600*24*Number($('#J-date-bound').val()) + 1);
				
			tds.each(function(){
				it = $(this);
				_y = Number(it.attr('data-year'));
				_m = Number(it.attr('data-month'));
				_d = Number(it.text());
				tempDate = new Date(_y, _m, _d);
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
			d.setDate(now.getDate() - 7);
			result.push(me.formatDateToString(d, true));
			result.push(now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate());
			return result;
		},
		formatDateToString:function(d, isFirst){
			var str = isFirst ? '00:01' : '23:59';
			return d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate();
		}
	};
	inputStart.click(dateFilterFn);
	inputEnd.click(dateFilterFn);
	
	//初始化首次起始时间
	setStartEndTime(dateUtil.getTodayBound()[0], dateUtil.getTodayBound()[1]);
	
	
	
	
	
})();
</script>	
{/literal}

{literal}
<script > 


  (function($){	
  	var form = $('#J-form');
	$('#divDetailed').hide(); 

	
	//展开，收起
	$('#divExpansion').click(function(){		
		if($('#divExpansion').html()=="展开∨"){
			$('#divDetailed').show();
			$('#divExpansion').html("收起∧");
		}
		else{
			$('#divDetailed').hide();
			$('#divExpansion').html("展开∨");
		}	
	});

	//日期选择，隐藏域保存值,改变样式
	$(".time span").click(function () {		
		var txtday = $(this).attr("pro").trim();		
		$(".time span").removeClass().addClass("ico-tab");
        $(this).addClass("ico-tab ico-tab-current");		
		$("#time").attr("value", txtday);
	});

	//状态，隐藏域保存值,改变样式
	$(".state span").click(function () {		
		var txtstatus = $(this).attr("pro").trim();		
		$(".state span").removeClass().addClass("ico-tab");
        $(this).addClass("ico-tab ico-tab-current");		
		$("#status").attr("value", txtstatus);
	});				
	
	$('#schemeNum').focus(function(){
		if($('#schemeNum')[0].value=='如：ABC77779'){ $("#schemeNum")[0].value='';}	
		
		}).blur(function(){
			var v = $.trim(this.value);
			if(v == ''){
				$("#schemeNum")[0].value='如：ABC77779';
			}
	
	});
	
	//表单提交校验
	$('#J-button-submit').click(function(){
		form.submit();
	});
	
	
	form.submit(function(e){
		//e.preventDefault();
		if($('#schemeNum').val().trim()=='如：ABC77779'){$('#schemeNum')[0].value='';}
		
	});
	
	//取消重置
	$('#restoreDefaults').click(function(e){
		e.preventDefault();
		$('#J-form').get(0).reset();
		//重置样式,值初始	
		$("#time").attr("value",7);
		$("#status").attr("value",0);	
		$(".time span").removeClass().addClass("ico-tab");
		$(".time span:eq(2)").addClass("ico-tab ico-tab-current");			
		$(".state span").removeClass().addClass("ico-tab");
		$(".state span:eq(0)").addClass("ico-tab ico-tab-current");		
	});
  })(jQuery);	
</script>
{/literal}
</body>
</html>