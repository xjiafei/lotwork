<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>{$title}</title>
<meta name="keywords" content="宝开娱乐, 宝开平台, 宝开软件, 宝开摇钱树, 宝开摇钱术, 宝开彩票, 生钱有道" />
<meta name="description" content="" />
<link rel="stylesheet" href="{$path_js}/images/common/base.css" />
<link rel="stylesheet" href="{$path_js}/css/activity/newuser_prize/style.css" />
{include file='/default/script-base.tpl'}
</head>
<body>
<!-- toolbar start -->
    {include file='/default/toolbar.tpl'}
<!-- toolbar end -->

<div id="top">
	<div id="header">
		<h1 class="seo_txt">宝开新生-送礼金</h1>
		<h2>火速体验新版！奖金手到擒来！快去！越早完成钱越多！</h2>
		<p class="tips">仅限PC端参与</p>
		<p class="date">活动时间&nbsp;&nbsp;{$startTime} - {$endTime}</p>
	</div>
	<div class="part" id="part1">
		<h2><i></i>升级任务 &#8226; <span>越早完成奖金越多</span></h2>
		<div class="content">
			<ul class="button">
				<li>
				<!-- {if $result.updateTime neq ''} -->
					<a class="done">任务完成</a>
				<!-- {else} -->
					<a target="_blank" {if $isEnabled lt 0} class="done" onclick="return notRun();"{else} href="http://www.ph278.com"{/if}>去旧平台升级</a>
				<!-- {/if} -->
				</li>
				<li>
				<!-- {if $result.fundTime neq ''} -->
					<a class="done">任务完成</a>
				<!-- {else} -->
					<a target="_blank" {if $isEnabled lt 0} class="done" onclick="return notRun();"{else} href="http://www.ph278.com"{/if}>去旧平台转移</a>
				<!-- {/if} -->
				</li>
				<li>
				<!-- {if $result.betTime neq ''} -->
					<a class="done">任务完成</a>
				<!-- {else} -->
					<a target="_blank" {if $isEnabled lt 0} class="done" onclick="return notRun();"{else} href="/index/"{/if}>点击投注</a>
				<!-- {/if} -->
				</li>
			</ul>
			<ul class="prize-list">
				<!-- {foreach from=$completeTime item=data key=key} -->
				<li class="list{$key} {if $iWeek eq $key}active{/if}">
					<p>{$data.startTime}~{$data.endtTime}完成奖励<strong>{$data.bonus}<i>元</i></strong></p>
					<div class="list-bg"></div>
				</li>
				<!-- {/foreach} -->
			</ul>
		</div>
	</div>
</div>
	

<div id="content">
	<div class="wrap">
		<div class="part" id="part2">
			<h2><i></i>新手任务  &#8226; <span>完成越多奖金越多</span></h2>
			<div class="content">
				<ul class="button">
					<li>
						<!-- {if $result.testTime neq ''} -->
							<a class="done">任务完成</a>
						<!-- {else} -->
							<a {if $isEnabled lt 0} class="done" onclick="return notRun();"{else} href="http://ask.25ik.com/q.php?qname=diaocha_37&qlang=cn"{/if} target="_blank">进入问答</a>
						<!-- {/if} -->
						<p><a href="http://www.ph158.net/user/" target="_blank">点击查看通关秘笈</a></p>
					</li>
					<li>
					<!-- {if $smarty.session.datas.info.withdrawPasswd neq '' and $smarty.session.datas.info.quStruc neq '' and $smarty.session.datas.info.emailActived neq ''} -->
						<a class="done">任务完成</a>
					<!-- {else} -->
						<a {if $isEnabled lt 0} class="done" onclick="return notRun();"{else} href="/safepersonal/safecenter"{/if} target="_blank">马上补全</a>
					<!-- {/if} -->
						<p>提升安全信息可让您的账号更安全</p>
					</li>
					<li>
					<!-- {if $smarty.session.datas.info.cellphone eq '' or $smarty.session.datas.info.qqStruc eq ''} -->
						<a {if $isEnabled lt 0} class="done" onclick="return notRun();"{else} href="/safepersonal/personalinfo/"{/if} target="_blank">马上填写</a>
					<!-- {else} -->
						<a class="done">任务完成</a>
					<!-- {/if} -->
						<p>抽取幸运用户回访，完成回访赠送话费</p>
					</li>
				</ul>
			</div>
		</div>

		<div class="part" id="part3">
			<h2><i></i>活动规则</h2>
			<div class="content">
				<ul class="button">					
					<li>活动仅限持有旧平台账号用户参与。</li>
					<li>升级任务中任务1升级平台，任务2转移资金在旧平台实现，其他任务在新平台实现。</li>
					<li>升级任务2，需要转移当前全部资金（含银行大厅、高低频道）至新平台，旧平台当前资金需大于0元，<br/>否则无法参与活动。</li>
					<li>新手任务3，请填写真实有效电话号码和QQ号，平台将随机抽取500名用户通过电话或QQ回访，完成回访即赠送话费，每个手机号码及QQ号只能参与一次，用户完成任务后三个工作日内，平台向该号码充值50元话费。</li>
					<li>用户完成任务后，奖金将在12.22、12.29、1.5、1.12派发，取最临近日期派发。</li>
					<li>活动欢迎真实用户参与，禁止作弊行为，一旦发现不予派奖。</li>
					<li>宝开彩票平台保留活动最终解释权。</li>
				</ul>
			</div>
		</div>
	</div>
</div>
<!-- {if $isEnabled lt 0} -->
<script type="text/javascript">
function notRun(){
 	alert("{$msg}");
  return false;
}
</script>
<!-- {/if} -->
<div id="footer">
	<p class="copyright">©2014-2017宝开彩票 All Rights Reserved &nbsp;&nbsp;&nbsp;&nbsp;<!-- <a href="">下载手机客户端</a> --></p>
</div>

</body>
</html>
