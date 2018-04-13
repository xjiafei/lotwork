<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>发财元宝争夺赛</title>
	<meta name="description" content="100万奖金邀您来战">
	<meta name="keywords" content="宝开 宝开娱乐 宝开彩票 奖金 排行">
	<link href="${staticFileContextPath}/static/images/activity/monkeyActivity/css/style.css" rel="stylesheet">
</head>
<body>
<div class="top">
	<div class="top_m">
		<a class="home" href="${frontContextPath}"></a>
	</div>
</div>
<div class="main">
	<div class="main_m">
		<div class="part">
			<h3 class="part_title ptitle1">参与办法</h3>
			<ul class="info_ul">
				<li>根据每日投注金额排行，达到每日投注累积门槛，并能取得规定排名名次，即可获得幸运发财金及开运利是。</li>
				<li>幸运发财金投注次日派发。开运利是在2016年2月14日~2016年2月18日投注达到金额15倍即可获得。</li>
			</ul>
			<h3 class="part_title ptitle2">比赛赛程</h3>
			<ul class="info_ul">
				<li>活动分为三轮。每轮5天。</li>
				<li>用户无需报名，系统自动计算用户每日投注量，并进行排名。</li>
			</ul>
			<div class="rankboard" id="tab1">
				<ul class="tab_title">
					<c:forEach var="round" items="${rounds}" varStatus="index" >
						<li <c:if test="${round.startDate<=nowDate and round.endDate>=checkDate}">class="current"</c:if>>
							<dl	class="title${index.count}">
								<dt>第${index.count}轮争霸</dt>
								<dd><fmt:formatDate pattern="yyyy年MM月dd日" value="${round.startDate}"/> - <fmt:formatDate pattern="yyyy年MM月dd日" value="${round.endDate}"/></dd>
								<span class="arrow"></span>
								<c:if test="${round.endDate < nowDate}">	
								<span class="over"></span>
								</c:if>
							<div class="tips">
								<p>财神太调皮，花样翻新发大财</p>
								下轮奖励更刺激哦！
							</div>

							</dl>
						</li>
					</c:forEach>
				</ul>
				<div class="tab_content">
					<c:forEach var="round" items="${rounds}" varStatus="index" >
						<div class="content" <c:if test="${round.startDate<=nowDate and round.endDate>nowDate}">style="display: block"</c:if>>
							<table class="rank_table <c:if test="${round.endDate < nowDate}">rank_table_over</c:if>">
							<c:if test="${index.count != 1 && round.startDate > nowDate}">
								<td colspan="4" rowspan="10"><p>财神太调皮，花样翻新发大财</p>
								下轮奖励更刺激哦！</td>
							</c:if>
							<c:if test="${index.count == 1 || round.startDate < nowDate}">
								<tr>
									<th width="22%">每日投注累积级</th>
									<th width="17%">每日级别</th>
									<th width="17%">元宝级别</th>
									<th width="22%">幸运发财金</th>
									<th width="22%">开运利是</th>
								</tr>
								<tr>
									<c:if test="${index.count != 3}"><th rowspan="2">≥300万</th></c:if>
									<c:if test="${index.count == 3}"><th rowspan="2">≥150万</th></c:if>
									<td>1</td>
									<td>财神元宝</td>
									<td <c:if test="${index.count != 1 && round.endDate > nowDate}">class="strong"</c:if>><fmt:formatNumber type="number" value="${round.roundItems[0].luckyMoney}" maxFractionDigits="2"/></td>
									<td><fmt:formatNumber type="number" value="${round.roundItems[0].kaiyun}" maxFractionDigits="2"/></td>
								</tr>
								<tr>
									<td>2</td>
									<td>发财元宝</td>
									<td <c:if test="${index.count != 1 && round.endDate > nowDate}">class="strong"</c:if>><fmt:formatNumber type="number" value="${round.roundItems[1].luckyMoney}" maxFractionDigits="2"/></td>
									<td><fmt:formatNumber type="number" value="${round.roundItems[1].kaiyun}" maxFractionDigits="2"/></td>
								</tr>
								<tr>
									<c:if test="${index.count != 3}"><th rowspan="3">≥150万</th></c:if>
									<c:if test="${index.count == 3}"><th rowspan="3">≥50万</th></c:if>
									<td>1</td>
									<td>招福元宝</td>
									<td <c:if test="${index.count == 2 && round.endDate > nowDate}">class="strong"</c:if>><fmt:formatNumber type="number" value="${round.roundItems[2].luckyMoney}" maxFractionDigits="2"/></td>
									<td><fmt:formatNumber type="number" value="${round.roundItems[2].kaiyun}" maxFractionDigits="2"/></td>
								</tr>
								<tr>
									<td>2</td>
									<td>招财元宝</td>
									<td <c:if test="${index.count == 2 && round.endDate > nowDate}">class="strong"</c:if>><fmt:formatNumber type="number" value="${round.roundItems[3].luckyMoney}" maxFractionDigits="2"/></td>
									<td><fmt:formatNumber type="number" value="${round.roundItems[3].kaiyun}" maxFractionDigits="2"/></td>
								</tr>
								<tr>
									<td>3</td>
									<td>幸运元宝</td>
									<td <c:if test="${index.count == 2 && round.endDate > nowDate}">class="strong"</c:if>><fmt:formatNumber type="number" value="${round.roundItems[4].luckyMoney}" maxFractionDigits="2"/></td>
									<td><fmt:formatNumber type="number" value="${round.roundItems[4].kaiyun}" maxFractionDigits="2"/></td>
								</tr>
								<tr>
									<c:if test="${index.count != 3}"><th rowspan="5">≥20万</th></c:if>
									<c:if test="${index.count == 3}"><th rowspan="5">≥10万</th></c:if>
									<td>1</td>
									<td>钻石元宝</td>
									<td><fmt:formatNumber type="number" value="${round.roundItems[5].luckyMoney}" maxFractionDigits="2"/></td>
									<td><fmt:formatNumber type="number" value="${round.roundItems[5].kaiyun}" maxFractionDigits="2"/></td>
								</tr>
								<tr>
									<td>2</td>
									<td>白金元宝</td>
									<td><fmt:formatNumber type="number" value="${round.roundItems[6].luckyMoney}" maxFractionDigits="2"/></td>
									<td><fmt:formatNumber type="number" value="${round.roundItems[6].kaiyun}" maxFractionDigits="2"/></td>
								</tr>
								<tr>
									<td>3</td>
									<td>黄金元宝</td>
									<td><fmt:formatNumber type="number" value="${round.roundItems[7].luckyMoney}" maxFractionDigits="2"/></td>
									<td><fmt:formatNumber type="number" value="${round.roundItems[7].kaiyun}" maxFractionDigits="2"/></td>
								</tr>
								<tr>
									<td>4~8</td>
									<td>白银元宝</td>
									<td><fmt:formatNumber type="number" value="${round.roundItems[8].luckyMoney}" maxFractionDigits="2"/></td>
									<td><fmt:formatNumber type="number" value="${round.roundItems[8].kaiyun}" maxFractionDigits="2"/></td>
								</tr>
								<tr>
									<td>9~15</td>
									<td>青铜元宝</td>
									<td><fmt:formatNumber type="number" value="${round.roundItems[9].luckyMoney}" maxFractionDigits="2"/></td>
									<td><fmt:formatNumber type="number" value="${round.roundItems[9].kaiyun}" maxFractionDigits="2"/></td>
								</tr>
								<tr>
									<c:if test="${index.count == 3}">
									<th>≥10万</th>
									<td colspan="2">未入榜</td>
									<td>1,000</td>
									<td>无</td>
									</c:if>
								</tr>
								</c:if>
							</table>
							<div class="part">
								<h3 class="part_title ptitle3" >规则说明</h3>
								<ul class="info_ul">
									<li>每轮比赛期间中，每日计算每个用户当日累积投注额。销量不累计至隔日，也不累计至下一轮。 </li>
									<li>每个奖项，设有相对应的最低投注金额，奖金会根据用户排名及最低投注总额共同决定，缺一不可。达到最低投注额，但已经名次未进入榜单，将顺延至下级别。</li>
								</ul>
								<c:if test="${index.count != 3}">
								<p class="info_2">例：如用户当日投注170万，排名第四，达到150万级别，但未进入榜单，将会顺延至钻石元宝。</p>
								</c:if>
								<c:if test="${index.count == 3}">
								<p class="info_2">例：如用户当日投注170万，排名第四，达到150万级别，但未进入榜单，将会顺延至招财元宝。</p>
								</c:if>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>

		
		<div class="part">
			<h3 class="part_title ptitle4">玩家排名</h3>
			<c:if test="${userScore.userName != null}">
				<div class="user-rank">
				<dl>
					<dt>当前排名</dt>
					<dd>${userRowNo}</dd>
				</dl>
				<dl>
					<dt>用户名</dt>
					<dd>${userScore.userName}</dd>
				</dl>
				<dl>
					<dt>今日累计销量</dt>
					<dd><fmt:formatNumber type="number" value="${(userScore.totalAmount-(userScore.totalAmount%100))/10000}" maxFractionDigits="2"/>元</dd>
				</dl>
				<dl style="border: none;">
					<dt>元宝级别</dt>
					<dd><c:if test="${userScore.rowNo == 1}">财神元宝</c:if>
						<c:if test="${userScore.rowNo == 2}">发财元宝</c:if>
						<c:if test="${userScore.rowNo == 3}">招福元宝</c:if>
						<c:if test="${userScore.rowNo == 4}">招财元宝</c:if>
						<c:if test="${userScore.rowNo == 5}">幸运元宝</c:if>
						<c:if test="${userScore.rowNo == 6}">钻石元宝</c:if>
						<c:if test="${userScore.rowNo == 7}">白金元宝</c:if>
						<c:if test="${userScore.rowNo == 8}">黄金元宝</c:if>
						<c:if test="${userScore.rowNo == 9}">白银元宝</c:if>
						<c:if test="${userScore.rowNo == 10}">青铜元宝</c:if>
						<c:if test="${userScore.rowNo == 11}"></c:if></dd>
				</dl>
				</div>
			</c:if>
			<c:if test="${userScore.userName == null}">
			<div class="user-rank">您目前还没有入围！请多多投注，就会上榜哦！</div>
			</c:if>
			
			<div class="user-history">
				<p class="info_3">每两小时更新一次 <span><fmt:formatDate pattern="yyyy年MM月dd日 HH:mm:ss" value="${lastUpdateDate}"/></span></p>
				<a class="history" href="history" target="_blank">查看历史排名</a>
			</div>
			<div class="rankboard" id="tab2">
				<div class="tab_content">
										<c:forEach var="i" begin="0" end="1">
						<div class="content" <c:if test="${i eq 0}">style="display: block"</c:if>>
							<table class="rank_table">
								<tr class="no-border">
									<th><a class="rank_pre" href="rank_pre">前日排行</a></th>
									<c:forEach var="round" items="${rounds}" varStatus="index" >
										<c:if test="${round.startDate<=nowDate and round.endDate>=checkDate}">
											<th colspan="2">第${index.count}轮争霸 <fmt:formatDate pattern="yyyy年MM月dd日" value="${leaderboardDate}"/> 排行榜</th>
											<input type="hidden" id="indexHidden" value="${index.count}">
										</c:if>
										<c:if test="${index.count == 1 && round.startDate > nowDate}">
											<th colspan="2">活动即将开始</th>
											<input type="hidden" id="indexHidden" value="${index.count}">
										</c:if>
									</c:forEach>
									<th><a class="rank_next" href="main">今日排行</a></th>
								</tr>
								
								<tr>
									<th width="25%">当前排名</th>
									<th width="25%">用户名</th>
									<th width="25%">累计销量</th>
									<th width="25%">元宝级别</th>
								</tr>
								
								<c:if test="${empty scores}">
									<tr>
										<td colspan="4" rowspan="10">暂未统计</td>
									</tr>
								</c:if>

								<c:forEach var="score" items="${scores}" begin="${i*25}" end="${(i+1)*25-1}" varStatus="index">
									<tr>
										<td>${index.count + i*25}</td>
										<td>${score.userName}</td>
										<td><fmt:formatNumber type="number" value="${(score.totalAmount-(score.totalAmount%100))/10000}" maxFractionDigits="2"/>元</td>
										<td><c:if test="${score.rowNo == 1}">财神元宝</c:if>
											<c:if test="${score.rowNo == 2}">发财元宝</c:if>
											<c:if test="${score.rowNo == 3}">招福元宝</c:if>
											<c:if test="${score.rowNo == 4}">招财元宝</c:if>
											<c:if test="${score.rowNo == 5}">幸运元宝</c:if>
											<c:if test="${score.rowNo == 6}">钻石元宝</c:if>
											<c:if test="${score.rowNo == 7}">白金元宝</c:if>
											<c:if test="${score.rowNo == 8}">黄金元宝</c:if>
											<c:if test="${score.rowNo == 9}">白银元宝</c:if>
											<c:if test="${score.rowNo == 10}">青铜元宝</c:if>
											<c:if test="${score.rowNo == 11}"></c:if>
										</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</c:forEach>
				</div>
				<div class="changepage">
					<a href="javascript:;" class="left">前25名</a>
					<a href="javascript:;" class="right">后25名</a>
				</div>
			</div>

		</div>
		<div class="part">
			<h3 class="part_title ptitle5">活动说明</h3>
			<ul class="info_ul">
				<li>非彩票游戏不参与。骰宝大小单双玩法不参与活动，所示销量包含大小单双，但最终排名将以去除该部分销量统计。 </li>
				<li>移动端不参与此次活动，销量不计入在活动销量内。</li>
				<li>开运利是可累计，仅在2016.2.14~2016.2.18投注有效。奖金将在2月19日派发。</li>
				<li>所有投注均指有效投注，有效投注须为已开奖投注。</li>
				<li>专业版、旗舰版两个平台投注分开计算。</li>
				<li>活动期间禁止任何刷量作弊行为，一经发现宝开娱乐平台将取消参与活动资格，严重者将被冻结账号处理。活动禁止刷量行为。</li>
				<li>宝开娱乐平台保留活动最终解释权。</li>
			</ul>
		</div>
		<div class="footer">
			<p>©2003-2016 宝开娱乐 All Rights Reserved </p>
		</div>
	</div>

</div>

<div class="fixed">
	<a class="backtop" href="javascript:;"></a>
	<a class="gotorank" href="javascript:;"></a>
	<a id="customer" class="service" href="javascript:;"></a>
</div>
</body>
<script src="${staticFileContextPath}/static/js/activity/monkeyActivity/jquery-1.9.1.min.js"></script>
<script src="${staticFileContextPath}/static/js/activity/monkeyActivity/tab.js"></script>
<script type="text/javascript">
	var index = $('#indexHidden').val();
	if(index == null){
		index = 1;
	}
	$('#tab1').tab({
		setNum: $('#indexHidden').val() , // 当前活动进行第2轮
		setNumCallback: function(obj){
			$(obj).find('.tips').show().stop().animate({top:'75px',opacity:1},200,function(){
				var o = $(this);
				setTimeout(function(){
					o.fadeOut(function(){
						$(this).css({top:'0'});
					});
				},2000)
			});
		}
	});

	$('#tab2').tab({'changepage':'.changepage'});

	$(".backtop").click(function(){
		$('body,html').animate({scrollTop:0},500);
		return false;
	});
	$(".gotorank").click(function(){
		$('body,html').animate({scrollTop: 1900},500);
		return false;
	});
	$(function(){
	 hjUserData=encodeURI('${userName}')+'|${sex}|${cellphone}|${cellphone}|${email}|${registerAddress}||||${userId}|${viplvl}|';
		function async_load(){
			var s = document.createElement('script');
			s.type = 'text/javascript';
			s.async = true;
			var vipLvl=${sessionScope.info.vipLvl};
			var temp=1;
			if(vipLvl>=1){
				temp=20;
			}else{
				temp=17;
			}
			s.src = "";
			var x = document.getElementsByTagName('script')[0];
			x.parentNode.insertBefore(s, x);
		}
		if (window.attachEvent){
			window.attachEvent('onload', async_load);
		}else{
			window.addEventListener('load', async_load, false);
		}

		function service(){
			if(typeof hj5107 != "undefined"){
				hj5107.openChat();
			}
		}
		$('#customer').click(function(){
			service();
		});
	})
	$('.rank_table tr:even').addClass('tr_even');
</script>
</html>