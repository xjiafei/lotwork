<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="pg" uri="/tag-page"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

		<select id="J-select-lotteryid" name="J-select-lotteryid" class="ui-select">
									<option value="99101">重庆时时彩</option>
									<option value="99106">宝开时时彩</option>
									<option value="99102">江西时时彩</option>
									<option value="99103">新疆时时彩</option>
									<option value="99104">天津时时彩</option>
									<option value="99107">上海时时乐</option>
									<option value="99105">黑龙江时时彩</option>
									<option value="99301">山东11选5</option>
									<option value="99302">江西11选5</option>
									<option value="99307">江苏11选5</option>
									<option value="99303">广东11选5</option>
									<option value="99304">重庆11选5</option>
									<option value="99305">宝开11选5</option>
									<option value="99306">秒开11选5</option>	
									<option value="99201">北京快乐8</option>
									<option value="99108">3D</option>
									<option value="99109">排列5</option>
									<option value="99401">双色球</option>	
									<option value="99701">六合彩</option>										
									<option value="99111">宝开一分彩</option>
									<option value="99114">腾讯分分彩</option>
									<option value="99112">秒开时时彩</option>		
								<!-- 	<option value="99113">超级2000秒秒彩（APP版）</option> -->
									<option value="99501">江苏快三</option>	
									<option value="99502">安徽快三</option>								
									<option value="99601">江苏骰宝</option>		
									<option value="99602">高频骰宝(娱乐厅)</option>
									<option value="99603">高频骰宝(至尊厅)</option>												
		</select>	
<script>
var lotteryId = $('#lotteryId').val();
var url = $('#url').val();
$("#J-select-lotteryid").find("option[value='"+lotteryId+"']").attr("selected",true);
$("#J-select-lotteryid").change(function(){
	location.href = url + $("#J-select-lotteryid").val();
});
</script>