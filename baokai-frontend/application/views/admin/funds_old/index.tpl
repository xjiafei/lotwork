{include file='/admin/header.tpl'}
	<div class="col-content">
		{include file='/admin/funds/left.tpl'}
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">充值管理</a> &gt; 充值明细</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<table class="table table-info table-function">
							<thead>
								<tr>
									<th rowspan="2" id="J-sp-serial" class="sp-td">
										<div class="sp-td-cont">
											<div class="sp-td-title">交易流水号</div>
											<ul class="sp-filter-cont">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input id="J-input-serial" type="text" class="input w-2" size="10" maxlength="25"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									</th>
									<th rowspan="2" id="J-sp-username" class="sp-td">
										<div class="sp-td-cont">
											<div class="sp-td-title">用户名</div>
											<ul class="sp-filter-cont">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input id="J-input-username" type="text" class="input w-2" size="10" maxlength="16"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									
									</th>
									<th colspan="4">充值申请信息</th>
									<th colspan="5">实际到账信息</th>
									<th rowspan="2" id="J-sp-order-status" class="sp-td">
										<div class="sp-td-cont">
											<div class="sp-td-title">订单处理状态</div>
											<div class="sp-filter-cont sp-filter-cont-select">
												<ul>
													<li data-select-id="1"><a href="#">等待审核</a></li>
													<li data-select-id="2"><a href="#">审核未过</a></li>
													<li data-select-id="3"><a href="#">退款成功</a></li>
													<li data-select-id="4"><a href="#">退款失败</a></li>
													<li data-select-id="5"><a href="#">加币成功</a></li>
													<li data-select-id="6"><a href="#">已没收</a></li>
												</ul>
											</div>
											<span class="sp-filter-close"></span>
										</div>
									
									</th>
								</tr>
									<th id="J-sp-request-time" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">申请时间</div>
											<ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input type="text" tabindex="1" class="input w-2" id="sp-input-time-1"> - <input type="text" id="sp-input-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									</th>
									<th id="J-sp-request-bank" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">申请充值银行</div>
											<div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
												<ul>
													<li data-select-id="1"><a href="#">招商银行</a></li>
													<li data-select-id="2"><a href="#">建设银行</a></li>
													<li data-select-id="3"><a href="#">农业银行</a></li>
													<li data-select-id="4"><a href="#">平安银行</a></li>
													<li data-select-id="5"><a href="#">浦发银行</a></li>
												</ul>
											</div>
											<span class="sp-filter-close"></span>
										</div>
									</th>
									<th id="J-sp-request-money" class="sp-td">

										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">申请金额</div>
											<ul class="sp-filter-cont sp-filter-cont-b">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input id="J-input-requestmoney1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-requestmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									
									
									</th>
									<th id="J-sp-message" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">附言</div>
											<ul class="sp-filter-cont sp-filter-cont-b">
												<li>
													<div class="input-append">
														<input type="text" class="input w-2" size="10" maxlength="200"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									</th>
									<th id="J-sp-receive-time" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">到账时间</div>
											<ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input type="text" id="sp-input-receive-time-1" class="input w-2" tabindex="1" /> - <input type="text" id="sp-input-receive-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									</th>
									<th id="J-sp-account-name" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">账户名</div>
											<ul class="sp-filter-cont sp-filter-cont-b">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input type="text" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									</th>
									<th id="J-sp-card-number" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">付款卡号</div>
											<ul class="sp-filter-cont sp-filter-cont-b" style="width:210px;">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input id="J-input-cardnumber" type="text" class="input w-3" size="10" maxlength="30"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									</th>
									<th id="J-sp-receive-money" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">到账金额</div>
											<ul class="sp-filter-cont sp-filter-cont-b" style="">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input id="J-input-receivemoney1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-receivemoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									</th>
									<th id="J-sp-loading-money" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">客户充值手续费</div>
											<ul class="sp-filter-cont sp-filter-cont-b">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input id="J-input-loadingmoney1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-loadingmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									</th>
								</tr>
							</thead>
							<tbody id="J-data-row">
								<tr>
									<td>2012061511</td>
									<td>gdhfth</td>
									<td>2012.01.05 10:20:30</td>
									<td>招商银行</td>
									<td>1,000.0000</td>
									<td>dfgfdh</td>
									<td>2013-02-02 10:20:30</td>
									<td>张**</td>
									<td>**** **** **** 8888</td>
									<td>1,000.0000</td>
									<td>10.0000</td>
									<td>等待收款</td>
								</tr>
								<tr>
									<td>2012061511</td>
									<td>gdhfth</td>
									<td>2012.01.05 10:20:30</td>
									<td>招商银行</td>
									<td>1,000.0000</td>
									<td>dfgfdh</td>
									<td>2013-02-02 10:20:30</td>
									<td>张**</td>
									<td>**** **** **** 8888</td>
									<td>1,000.0000</td>
									<td>10.0000</td>
									<td>等待收款</td>
								</tr>
								<tr>
									<td>2012061511</td>
									<td>gdhfth</td>
									<td>2012.01.05 10:20:30</td>
									<td>招商银行</td>
									<td>1,000.0000</td>
									<td>dfgfdh</td>
									<td>2013-02-02 10:20:30</td>
									<td>张**</td>
									<td>**** **** **** 8888</td>
									<td>1,000.0000</td>
									<td>10.0000</td>
									<td>等待收款</td>
								</tr>
								<tr>
									<td>2012061511</td>
									<td>gdhfth</td>
									<td>2012.01.05 10:20:30</td>
									<td>招商银行</td>
									<td>1,000.0000</td>
									<td>dfgfdh</td>
									<td>2013-02-02 10:20:30</td>
									<td>张**</td>
									<td>**** **** **** 8888</td>
									<td>1,000.0000</td>
									<td>10.0000</td>
									<td>等待收款</td>
								</tr>
								<tr>
									<td>2012061511</td>
									<td>gdhfth</td>
									<td>2012.01.05 10:20:30</td>
									<td>招商银行</td>
									<td>1,000.0000</td>
									<td>dfgfdh</td>
									<td>2013-02-02 10:20:30</td>
									<td>张**</td>
									<td>**** **** **** 8888</td>
									<td>1,000.0000</td>
									<td>10.0000</td>
									<td>等待收款</td>
								</tr>
							</tbody>
						</table>
						
						<div class="page-wrapper">
							<span class="page-text">
								<span class="lower">共102条记录</span>
							</span>
							<div class="page page-right">
								<a href="#" class="prev">上一步</a>
								<a href="#">1</a>
								<a href="#">2</a>
								<a href="#" class="current">3</a>
								<a href="#">4</a>
								<a href="#">5</a>
								<a href="#">6</a>
								<a href="#">7</a>
								<a href="#">8</a>
								<a href="#">9</a>
								<a href="#">...</a>
								<a href="#" class="next">下一步</a>
								<span class="page-few">到第 <input type="text" value="" class="input"> /100页</span>
								<input type="button" value="确 认" class="page-btn">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
{literal}	
<script>
(function(){
	
})();
</script>
{/literal}	
</body>
</html>