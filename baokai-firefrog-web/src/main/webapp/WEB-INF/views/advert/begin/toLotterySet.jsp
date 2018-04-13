\<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>抽奖设置</title>
	<style>
		.panel-field-urgent {display:none;}
		.ui-form .J-panel-group {margin:0;}
		.checkbox-list {border-bottom:1px dotted #CCC;display:inline-block;padding-bottom:10px;}
		.checkbox-list-last {border:0;}
	</style>
</head>
<body>
		<div class="col-crumbs">
               <div class="crumbs">
                   <strong>当前位置：</strong>
                  <a href="${currentContextPath}/adAdmin/queryPublishAdPage">营销中心</a> &gt; 抽奖设置
               </div>
           </div>
			<div class="col-content">
			<div class="col-main">
				<div class="main" id="DivRules">
				</div>
				<div id="DivForm">
						<div align="right">
						<permission:hasRight moduleName="BEGIN_LOTTERY_SET_LOG">
							<button class='btn btn-small'  id='lotteryLog' style='position:initial'>查询日志</button>
						</permission:hasRight>	
						<permission:hasRight moduleName="BEGIN_LOTTERY_SET_CANCEL">
							<button class='btn btn-small'  id='cancellottery' style='position:initial'>取消修改</button>
						</permission:hasRight>
						<permission:hasRight moduleName="BEGIN_LOTTERY_SET_CONFIRM">
							<button class='btn btn-small'  id='confirmlottery' style='position:initial' >确认修改</button>
						</permission:hasRight>
						</div>
						<form action="${currentContextPath}/begin/insertLotterySet" id="J-form" method="POST">
						<c:forEach items="${lotterySets}" var="lotterySet"  varStatus="status">
						<!-- 累计投注 -->
						<ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
							<li class="current">
								<c:if test="${status.index==2}">金奖</c:if>
								<c:if test="${status.index==1}">银奖</c:if>
								<c:if test="${status.index==0}">铜奖</c:if>																
							<input type="hidden" name="beginLotterySets[${status.index}].lotteryType" value="${status.index}" class="input w-2"/>								
							</li>
                        </ul>
                        <div id="DivLottery${status.index}">
						<ul class="ui-form">
							<label for="name" class="ui-label">奖励下限</label>
							<input type="text" name="beginLotterySets[${status.index}].lotteryLow" value="${lotterySet.lotteryLow}" class="input w-2" numberTag="true"/>
							<label for="name" class="ui-label">奖励上限</label>
							<input type="text" name="beginLotterySets[${status.index}].lotteryHigh" value="${lotterySet.lotteryHigh}" class="input w-2" numberTag="true"/>
							<select name="beginLotterySets[${status.index}].lotteryUnit" class="ui-select w-2" >
								<option value="-1">选择奖励单位</option>
								<option value="1" <c:if test="${lotterySet.lotteryUnit==1}">selected</c:if> >元</option>
								<option value="0" <c:if test="${lotterySet.lotteryUnit==0}">selected</c:if>>角</option>
							</select>
						</ul>
						</div>
						</c:forEach>
						</form>
				</div>
				</div>				
			</div>

	<script type="text/javascript">
	
	function confirmlottery(){
		var isOk =true;;		
		//獎勵檢核
		$("[id*=DivLottery]").each(function(){
			var low = $(this).find("[name*=lotteryLow]").val();
			var high = $(this).find("[name*=lotteryHigh]").val();			
			var lotteryUnit = $(this).find("[name*=lotteryUnit]").val();
			if(low==0 || high==0 ||(high-low)<0 || lotteryUnit==-1){
				isOk = false;	
			}
		});
		
		if(!isOk){
			alert('抽奖设置输入错误');
			return false;
		}
		
		$("#DivForm").hide();
		TableStyle("DivRules", 19, 1, "保存中");			
		$("#J-form").submit();
	}
	
	
	
	function cancellottery(){
		$("#DivForm").hide();
		TableStyle("DivRules", 19, 1, "载入中");			
		location.href='${currentContextPath}/begin/toLotterySet';
		location.assign();
	}
	function lotteryLog(){
		window.open("${currentContextPath}/begin/toBeginLog?type=2");
	}
	
	$(function(){
		$("#confirmlottery").click(confirmlottery);
		$("#cancellottery").click(cancellottery);		
		$("#lotteryLog").click(lotteryLog);		
		numberTagDefault();
	});
	
	function numberTagDefault(){
		$("[numberTag=true]").each(function(){
			var obj = this;
			$(obj).keyup(function(){
				$(this).val($(this).val().replace(/[^0-9]/g,''));
				if(!jQuery.isNumeric($(this).val())){
					$(this).val(0);
				}
			})
			$(obj).blur(function(){
				$(this).val($(this).val().replace(/[^0-9]/g,''));
				if(!jQuery.isNumeric($(this).val())){
					$(this).val(0);
				}
			})
			if(!jQuery.isNumeric($(this).val())){
				$(this).val(0);
			}
		});
	}
	
	</script>
	
</body>