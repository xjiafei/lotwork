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
				<!-- {if $_POST.type eq '1'} -->
				<div class="title">账户明细</div>
				<!-- {else} -->
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
				<!-- {/if} -->
				<form action="/bet/fuddetail/" id='J-form' method="post">
				<div class="content">
					<div class="ui-tab">
						<!-- {if $_POST.type neq '1'} -->
						<div class="ui-tab-title tab-title-bg clearfix">
							
							<ul>
								<li class="current">账户明细</li>
								<li><a href="/fund/history">充值记录</a></li>
								<li><a href="/withdraw/history">提现记录</a></li>
								<li><a href="/transfer/history">转账记录</a></li>
								<!-- <li>投注记录</li>
								<li>奖金与返点</li> -->
							</ul>
							
						</div>
						<!-- {/if} -->
						<div >
							<!-- start -->
							<ul class="ui-search search-fund clearfix">
								<li class="status">
									<label class="ui-label">摘要：</label>
									<select class="ui-select" name="status">
									<option value="" >所有摘要</option>
									<!-- {foreach from=$aType item=data key=key} -->
										<option value="{$key}" {if $status eq $key}selected{/if}>{$data}</option>
									<!-- {/foreach}	 -->
									</select>
								</li>
								<li class="number">
									<label for="name" class="ui-label">交易流水号：</label>
									<input type="text" value="{$_POST.sn}" id="sn" name="sn" class="input">
								</li>
								
								<li class="date">
									<label for="dateStar" class="ui-label">起始日期：</label>
									<input type="text" class="input" id="J-date-start" value="{{$_POST.fromDate}}" name="fromDate"> - <input type="text" class="input"  value="{$_POST.toDate}" id="J-date-end" name="toDate">
								</li>
								<!-- {if $_POST.type eq '1'} -->
								<li class="number">
									<label for="name" class="ui-label">用户名：</label>
									<input type="text" value="{$_POST.userName}" id="userName" name="userName1" disabled class="input">
									<input type="hidden" value="{$_POST.userName}" id="type" name="userName">
									<input type="hidden" value="{$_POST.type}" id="type" name="type">
								</li>
								<!-- {if $userLvl neq '-1'} -->
								<li>
									<label for="name" class="ui-label" style="display: inline-block;">
									<input type="checkbox" value="1" {if $_POST.include eq '1'}checked{/if} id="userName" name="include" style="margin-right: 5px;vertical-align: middle;"> 包含下级
									</label>
								</li>
								<!-- {/if} -->
								<!-- {/if} -->
								<li class="button"><a class="btn btn-important" id='J-button-submit' href="javascript:void(0);">查 询<b class="btn-inner"></b></a></li>
							</ul>
							{if $total gt '0'}
							<table class="table table-info">
								<thead>
									<tr>
									<!-- {if $_POST.type eq '1'} -->
										<th>用户名</th>
									<!-- {/if} -->
										<th>交易流水号</th>
										<th>时间</th>
										<th>摘要</th>
										<th>收入金额</th>
										<th>支出金额</th>
										<th>可用余额</th>
										<th>备注</th>
									</tr>
								</thead>
								<tbody>
								<!-- {foreach from=$content item=data} -->
									<tr>
									<!-- {if $_POST.type eq '1'} -->
										<td>{$data.account}</td>
									<!-- {/if} -->
									{if $data.exCode neq '' && $data.status neq '提现退回'}
										<td><a href="{$game_server}/gameUserCenter/queryOrderDetailManagement?orderCode={$data.exCode}&userId={$data.userId}">{$data.sn}</a></td>
									{else if $data.planCode neq ''}
									<td><a href="{$game_server}/gameUserCenter/queryPlanDetail?planCode={$data.planCode}&userId={$data.userId}">{$data.sn}</a></td>
									{else}
									<td>{$data.sn}</td>
									{/if}
										<td>{$data.gmtCreated}</td>
										<td>{$data.status}</td>
										<td>{$data.inBal}</td>
										<td>{$data.outBal}</td>
										<td>{$data.ctBal}</td>
										<td class="text-break">{$data.note}</td>
									</tr>
								<!-- {/foreach} -->
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
							{if $pages}
								<div class="page-wrapper">
									<span class="page-text">共{$total}条记录</span>
									<div class="page page-right">
										{if $pages.pre && $pages.currpage.index!=1}
				    							<a  class="prev" href="javascript:changePage({$pages.pre.index})">上一页</a>
				    						{/if}
				    					{foreach from=$pages.steps item=item}
				    						{if $item.index == $pages.currpage.index}
				    							<a class="current" href="javascript:void(0);">{$item.text}</a>
				    						{else}
				    							<a href="javascript:changePage({$item.index})">{$item.text}</a>
				    						{/if}
				    					{/foreach}
				    					{if $pages.next && $pages.currpage.index!=$pages.max.index}
				    						<a  class="next" href="javascript:changePage({$pages.next.index})">下一页</a>
				    					{/if}
										<span class="page-few">到第 <input type="text" id="pageNum" name="page" class="input" value="{$pages.currpage.index}"> /{$pages.max.index}页</span>
										<input type="button" class="page-btn" onClick="$('#J-form').submit();" value="确 认">
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
				<dt>说明：您可以查询您的账户最近15天内的账户明细。</dt>
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
        
       changePage = function(page){
           var form = $('#J-form');
           $('#pageNum').val(page);
           form.submit();
       };
       
})();
</script>	
{/literal}

{literal}
<script > 


  (function($){	
  	var form = $('#J-form');
	
	//表单提交校验
	$('#J-button-submit').click(function(){
		form.submit();
	});
  })(jQuery);	
</script>
{/literal}
</body>
</html>