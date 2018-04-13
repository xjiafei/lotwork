			<div class="common-side">
				<div class="nav-side">
					<dl class="side-bet">
						<dt class="title">投注管理</dt>
						<dd><a href="{$game_server}/gameUserCenter/queryOrders?userId={$smarty.session.datas.info.id}&time=7">投注记录</a></dd>
						<dd><a href="{$game_server}/gameUserCenter/queryPlans?time=7">追号记录</a></dd>
						<dd><a href="/bet/fuddetail">账户明细</a></dd>
					</dl>
					<dl class="side-safe">
						<dt class="title">账户安全</dt>
						<dd><a href="/safepersonal/safecenter">安全中心</a></dd>
						<dd><a href="/bindcard">银行卡管理</a></dd>
						<dd><a href="/safepersonal/personalinfo">个人资料</a></dd>
						<dd><a href="/safepersonal/safecodeedit">密码管理</a></dd>
						<dd><a href="/applycenter/querybonusdetails/">奖金详情</a></dd>
					</dl>
					<dl class="side-service">
						<dt class="title">服务</dt>
						<dd><a href="/Service2/inbox"  style="text-decoration:none;">站内信&nbsp;<span class="side-message-num" id="radiuscount" >&nbsp;&nbsp;<span id="noreadmsg">0</span>&nbsp;&nbsp;</span></a></dd>
						<dd><a href="/Service/shownoticetask"  style="text-decoration:none;">通知设置</a></dd>
                                              <!--  <dd><a href="/fundappeal/appealrechargelist">催到账<span class="side-message-num" style="display: none;margin-left:3px">&nbsp;&nbsp;<span id="fundAppealNoticeCount">0</span>&nbsp;&nbsp;</span></a></dd> -->
					</dl>
					<!-- {if $smarty.session.datas.info.userLvl neq '-1' and $smarty.session.datas.info.isAllZero neq '0'} -->
					<dl class="side-proxy">
						<dt class="title">代理中心</dt>
						<dd><a href="/proxy/index">代理首页</a></dd>
						<dd><a href="/applycenter/index">开户中心</a></dd>
						<dd><a href="/proxy/cusmag">客户管理</a></dd>
						<dd><a href="{$game_server}/gameUserCenter/queryCurrentUserReport?userId={$smarty.session.datas.info.id}">报表查询</a></dd>
					</dl>
					<!-- {/if} -->
					<dl class="side-funds">
						<dt class="title">资金</dt>
						<dd><a href="/fund">我要充值</a></dd>
						<dd><a href="/withdraw">我要提现</a></dd>
						<dd><a href="/transfer">我要转账</a></dd>
						<!-- <dd><a href="/remark">唯一附言</a></dd> -->
					</dl>
				</div>
			</div>
                        <script type="text/javascript" src="{$path_js}/js/userCenter/left.js"></script>

