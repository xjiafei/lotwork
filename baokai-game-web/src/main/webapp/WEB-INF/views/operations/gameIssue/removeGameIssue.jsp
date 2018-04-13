<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<title>下架设置</title>
	
</head>
<body>
<div id="tab_menu_id" style="display:none">removeMenu</div>
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>>下架设置
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<jsp:include page="../lotteryConfigHeader.jsp"></jsp:include>
								</ul>
								<input type="hidden" id="lotteryId" value="${lotteryId}"/>
								<input type="hidden" id="url" value="<%=request.getContextPath() %>/gameRemove/gameIssueRemoved?lotteryid=">
							</div>
							<div class=" ">
								<h3 class="ui-title">
								<spring:message code="gameCenter_caizhongmingcheng" />：<jsp:include page="../lotteryListSelect.jsp"></jsp:include></h3>
								
								<div style="padding:30px;">
									
									<div id="lotteryName">彩种名称：</div><br><br>
									<div>奖金详情下架时间：<c:if test="${awardTime == null}">尚未设置</c:if><c:if test="${awardTime != null}">${awardTime}</c:if></div><br><br>
									<div>游戏入口下架时间：<c:if test="${gameTime == null}">尚未设置</c:if><c:if test="${gameTime != null}">${gameTime}</c:if></div><br><br>
									<div>投注记录下架时间：<c:if test="${orderTime == null}">尚未设置</c:if><c:if test="${orderTime != null}">${orderTime}</c:if></div><br><br>
									
									<div id="read_block">
										<a id="btn_mdf" class="btn btn-important" href="javascript:mdf();">修改</a>
									</div>

									<div id="set_block">
										是否开启彩种下架：
											<input id="open" name="status" type="radio" value="1" > 开启　　
											<input id="close" name="status" type="radio" value="0"  checked > 关闭（默认）<br><br>
										<div id="setTime_block">
											修改奖金详情下架时间：<input id="J-date-award" class="input w-3" type="text" readonly="readonly"><br><br>
											修改游戏入口下架时间：<input id="J-date-game" class="input w-3" type="text" readonly="readonly"><br><br>
											修改投注记录下架时间：<input id="J-date-order" class="input w-3" type="text" readonly="readonly"><br><br>
											<div id="error" style="color: red"><font size="5px">下架时间不可空白</font></div><br><br>
										</div>
										<a id="btn_save" class="btn btn-important" href="javascript:save();">保存</a>
										<a id="btn_cancel" class="btn btn-important" href="javascript:cancel();">取消</a>
									</div>
								</div>
							</div>
						</div>
						
					</div>
				</div>
			</div>
    
    <div id="DivFailed" style="position:absolute;z-index:2; display:none"  class="pop w-8">
        <div class="hd"><i class="close"></i>温馨提示</div>
        <div class="bd">
            <div class="pop-title">
                <i class="ico-error"></i>
                <h4 class="pop-text">操作失败，请检查网络，并重试</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn">关 闭<b class="btn-inner"></b></a>
            </div>
        </div>
    </div>
    
    <div  id="DivSuccessful" class="pop pop-success w-4" style="position:absolute;z-index:2; display:none" >
        <i class="ico-success"></i>
        <h4 class="pop-text">操作成功</h4>
    </div>
    
    <script type="text/javascript">
    
    $('#set_block').hide();
    $('#setTime_block').hide();
    $('#error').hide();
    $('#lotteryName').text("彩种名称："+ $('option[value='+$('#lotteryId').val()+']').text());
    $( "input[name=status]" ).change(function() {
    	 var status = $('input[name=status]:checked').val();
    	    
    	 if(status == 0){
    	   $('#setTime_block').hide();
    	   $('#error').hide();
    	  }else{
   			$('#setTime_block').show();
    	  	}
    });
    
    
 	 function mdf(){
			$('#read_block').hide();
			$('#set_block').show();
		}
 	 
 	function save(){
 		var status = $('input[name=status]:checked').val();
 		var checkSave = false;
 		var lotteryid = $('#lotteryId').val();
 		if(status == 0){
 			checkSave = true;
 		}else{
 			var award = $('#J-date-award').val(),game = $('#J-date-game').val(),order = $('#J-date-order').val(),lotteryid = $('#lotteryId').val();
 			if(award =="" || game == "" || order == ""){
 				$('#error').show();
 				return;
 			}else{
 				checkSave = true;
 				$('#error').hide();
 			}
 		}
		if(checkSave){
			var str = "lotteryid="+lotteryid+"&awardTime="+award+"&gameTime="+game+"&orderTime="+order+"&status="+status;
			jQuery.ajax({
				type: "GET",
				url: '/gameRemove/updateIssueRemovedTime',
				data : str,
				dataType :"json",
				async: false,
				success: function(data){
					location.reload();
				},
				complete:function(){
					
				},
				error: function(xhr,status,errMsg){
					alert("错误！请洽系统管理者");
					return false;
				}
			});
		}
 	}

 	function cancel(){
 		$('#read_block').show();
 		$('#set_block').hide();
 		$('#error').hide();
 	}
	var baseUrl = '${currentContextPath}';
	</script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gameAward/gameAwardIndex.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		// 日期控件js 开始
		var inputStartaward = $('#J-date-award'), inputStartgame = $('#J-date-game'),inputStartorder = $('#J-date-order'),

		dateFilterFn = function(e) {
			var Dt = new phoenix.DatePicker(
					{
						input : this,
						
						isShowTime : true,
						setDisabled : function() {
							var me = this, tds = me.getContent()
									.find('td'), it, tempDate, _y, _m, _d;
							// n天前的某个日期
							before = dateUtil
									.getOneDateTime(
											time_now,
											-1
													* 3600
													* 24
													* Number($(
															'#J-date-bound')
															.val())
													+ 1);

							tds
									.each(function() {
										it = $(this);
										_y = Number(it
												.attr('data-year'));
										_m = Number(it
												.attr('data-month'));
										_d = Number(it.text());
										tempDate = new Date(_y, _m,
												_d);
										if (tempDate < before
												|| tempDate > dateUtil.now) {
											it
													.addClass('day-disabled');
										}
									});
						}
					});
			Dt.show();
		},
		// 年月日
		time_now, _arrDate = $.trim($('#J-data-now').val()).split(
				/[^\d]/), dateUtil = {}, setStartEndTime = function(
				start, end) {
			$('#J-time-start').val(start);
			$('#J-time-end').val(end);
		}, time_y = Number(_arrDate[0]), time_m = Number(_arrDate[1]), time_d = Number(_arrDate[2]), time_h = Number(_arrDate[3]), time_s = Number(_arrDate[4]);

		time_now = new Date();

		time_now.setFullYear(time_y);
		time_now.setMonth(time_m - 1);
		time_now.setDate(time_d);
		time_now.setHours(time_h);
		time_now.setMinutes(time_s);

		dateUtil = {
			now : time_now,
			// 获取当前日期前后n秒的日期
			getOneDateTime : function(now, n) {
				var now_ms = now.getTime(), n = n || 0, d_n = now_ms
						+ n * 1000, d2 = new Date();
				d2.setTime(d_n)
				return d2;
			},
			getYestodayBound : function() {
				var me = this, now = me.now, result = [], d = new Date();
				d.setFullYear(now.getFullYear());
				d.setMonth(now.getMonth());
				d.setDate(now.getDate() - 1);
				result.push(me.formatDateToString(d, true));
				result.push(me.formatDateToString(d, false));
				return result;
			},
			getTodayBound : function() {
				var me = this, now = me.now, result = [], d = new Date();
				d.setFullYear(now.getFullYear());
				d.setMonth(now.getMonth());
				d.setDate(now.getDate());
				result.push(me.formatDateToString(d, true));
				result.push(now.getFullYear() + '-'
						+ (now.getMonth() + 1) + '-'
						+ now.getDate());
				return result;
			},
			// 前一周时间
			// 7天前的 00:01 + 今天已过的时间
			// 今天当成1天计算
			getBeforeWeekBound : function() {
				var me = this, now = me.now, result = [], d = new Date();
				d.setFullYear(now.getFullYear());
				d.setMonth(now.getMonth());
				d.setDate(now.getDate() - 7);
				result.push(me.formatDateToString(d, true));
				result.push(now.getFullYear() + '-'
						+ (now.getMonth() + 1) + '-'
						+ now.getDate());
				return result;
			},
			formatDateToString : function(d, isFirst) {
				var str = isFirst ? '00:01' : '23:59';
				return d.getFullYear() + '-' + (d.getMonth() + 1)
						+ '-' + d.getDate();
			}
		};
		inputStartaward.click(dateFilterFn);
		inputStartgame.click(dateFilterFn);
		inputStartorder.click(dateFilterFn);

		// 初始化首次起始时间
		setStartEndTime(dateUtil.getTodayBound()[0], dateUtil
				.getTodayBound()[1]);

		// 日期控件js 结束
	});
	</script>
</body>