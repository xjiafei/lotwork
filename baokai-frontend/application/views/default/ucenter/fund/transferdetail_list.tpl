<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
    <link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
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
				<div class="recharge" style="display:block">
					<table>
						<tr>
							<td>{$account}</td>
							<td>可用余额：<span class="color-red">{$accInfo.bal}元</span></td>
							<td>可提现额度：<span class="color-red">{$accInfo.availBal}元</span></td>
							<td><a class="btn" href="/fund">充 值<b class="btn-inner"></b></a></td>
						</tr>
					</table>
				</div>
				<form id="fm_main" name="fm_main" method="GET">
				<div class="content">
					<div class="ui-tab">
						<div class="ui-tab-title tab-title-bg clearfix">
							<ul>
								<li><a href="/bet/fuddetail">账户明细</a></li>
								<li><a href="/fund/history">充值记录</a></li>
								<li><a href="/withdraw/history">提现记录</a></li>
								<li class="current">转账记录</li>
								<!-- <li>投注记录</li>
								<li>奖金与返点</li> -->
							</ul>
						</div>
						<div class="">
							<!-- start -->
							<ul class="ui-search search-fund clearfix">
								<li class="status">
									<label class="ui-label">类型：</label>
									<select class="ui-select" name="status">
									{foreach from=$aStatusArray item=data key=key}
										<option value="{$key}" {if $_POST.status eq $key}selected{/if}>{$data}</option>
									{/foreach}
									</select>
								</li>
								<li class="number">
									<label for="name" class="ui-label">交易流水号：</label>
									<input type="text" value="{$_POST.tCode}" name="tCode" class="input">
								</li>
								<li class="date">
									<label for="dateStar" class="ui-label">起始日期：</label>
									<input type="text" class="input" id="J-date-start" name="fromDate" value="{{$_POST.fromDate}}"> - <input type="text" class="input" id="J-date-end" name="toDate" value="{$_POST.toDate}">
								</li>
								<li class="button"><a class="btn btn-important" href="javascript:void(0);" onClick="$('#fm_main').submit();">查 询<b class="btn-inner"></b></a></li>
							</ul>
							{if $total gt '0'}
							<table class="table table-info">
								<thead>
									<tr>
										<th>交易流水号</th>
										<th>发起时间</th>
										<th>类型</th>
										<th>付款用户名</th>
										<th>收款用户名</th>
										<th>转账金额</th>
										<th>状态</th>
									</tr>
								</thead>
								<tbody>
								{foreach from=$content item=data}
									<tr>
										<td>{$data.sn}</td>
										<td>{$data.transferTime}</td>
										<td>{$aStatusArray[$data.status]}</td>
										<td>{$data.userAccount}</td>
										<td>{$data.rcvAccount}</td>
										<td>{$data.transferAmt}</td>
										<td>已成功</td>
									</tr>
								{/foreach}
								</tbody>
							</table>
							{else}
								<div class="alert alert-waring">
									<i></i>
									<div class="txt">
										<h4>没有符合条件的记录！</h4>
									</div>
								</div>
							{/if}
							<div class="cumulative-recharge">累计转入：{$aTotalCharge.input}元，累计转出：{$aTotalCharge.output}元</div>
							{if $pages}
								<div class="page-wrapper">
									<span class="page-text">共{$total}条记录</span>
									<div class="page page-right">
										{if $pages.pre && $pages.currpage.index!=1}
			    							<a  class="prev" onClick="$('#fm_main').submit();" href="?page={$pages.pre.index}&tCode={$_POST.tCode}&fromDate={$_POST.fromDate}&toDate={$_POST.toDate}&status={$_POST.status}">上一页</a>
			    						{/if}
				    					{foreach from=$pages.steps item=item}
				    						{if $item.index == $pages.currpage.index}
				    							<a class="current" href="javascript:void(0);">{$item.text}</a>
				    						{else}
				    							<a onClick="$('#fm_main').submit();" href="?page={$item.index}&tCode={$_POST.tCode}&fromDate={$_POST.fromDate}&toDate={$_POST.toDate}&status={$_POST.status}">{$item.text}</a>
				    						{/if}
				    					{/foreach}
				    					{if $pages.next && $pages.currpage.index!=$pages.max.index}
				    						<a  class="next" onClick="$('#fm_main').submit();" href="?page={$pages.next.index}&tCode={$_POST.tCode}&fromDate={$_POST.fromDate}&toDate={$_POST.toDate}&status={$_POST.status}">下一页</a>
				    					{/if}
										<span class="page-few">到第 <input type="text" name="page" class="input" value="{$pages.currpage.index}"> /{$pages.max.index}页</span>
										<input type="button" class="page-btn" onClick="$('#fm_main').submit();" value="确 认">
									</div>
								</div>
							{/if}
							<!-- end -->
						</div>
					</div>
				</div>
				</form>
			</div>
			<dl class="fund-info-supplement">
				<dt>说明：您可以查询您的账户最近7天内的转账记录。</dt>
			</dl>
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
		var Dt = new phoenix.DatePicker({input:this, isShowTime:false, setDisabled:function(){
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