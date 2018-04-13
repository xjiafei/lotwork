<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <script type="text/javascript">
   hjUserData=encodeURI('${userName}')+'|${sex}|${cellphone}|${cellphone}|${email}|${registerAddress}||||${userId}|${viplvl}|';
	(function() {       
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
	if (window.attachEvent)           
	window.attachEvent('onload', async_load);
	else 
	window.addEventListener('load', async_load, false);  
	})();
	</script>
<div class="title">帮助中心</div>
	<div class="help-menu-inner">
		<c:forEach items="${cateList}" var="cates">
		<dl>
			<dt>${cates.name}</dt>
				<c:forEach items="${cates.subCate}" var="subCate">
				<c:if test="${cates.id != 4}">
				<dd>|&nbsp;&nbsp;<a target="_blank" onclick="toGeneralHelpByCate('${subCate.id}','${subCate.name}')">${subCate.name}</a></dd>
				</c:if>
				<c:if test="${cates.id == 4}">
				<dd>|&nbsp;&nbsp;<a target="_blank" onclick="toLotteryHelpByCate('${subCate.id}','${subCate.name}')">${subCate.name}</a></dd>
				</c:if>
				</c:forEach>
		</dl>
		</c:forEach>
		<a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1" target="_blank" class="help-online">在线咨询</a>
		
		<c:if test="${hotLotteryDetail !=null}">
		<div class="help-side">
			<div class="title">热门彩种推荐</div>
			<ul class="game-list">
				<c:forEach items="${hotLotteryDetail}" var="hotLottery">
				<li>
					<a onclick="toLotteryHelp('${hotLottery.id}')">
						<img src="${staticFileContextPath}/dynamic/${hotLottery.lotteryLogo}" style="width:128px; height:80px" />
						<span class="game-name">${hotLottery.title}</span>
						<span>${hotLottery.lotteryAdvert}</span>
					</a>
				</li>
				</c:forEach>
			</ul>
		</div>
		</c:if>
	</div>
	<script type="text/javascript">
	<!--
	function toGeneralHelpByCate(id,name){
		window.location.href = baseUrl + "/help/queryGeneralHelp?cateId2="+id +"&cate2Name=" + encodeURI(encodeURI(name))+"&orderBy=no desc,gmt_created desc";
	}
	
	function toLotteryHelpByCate(id,name){
		window.location.href = baseUrl + "/help/queryLotterylHelp?cateId2="+id +"&cate2Name=" + encodeURI(encodeURI(name))+"&orderBy=no desc,gmt_created desc";
	}
	//-->
	function toLotteryHelp(id){
		window.location.href = baseUrl + "/help/queryLotteryDetail?helpId=" +id+ "&cate2Name=" + encodeURI(encodeURI('热门彩种'));
	}
</script>
