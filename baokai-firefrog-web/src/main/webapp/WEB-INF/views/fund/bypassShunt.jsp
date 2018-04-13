<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="permission" uri="/tag-permission"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<html>
	<head>
		<title>资金中心-充值相关配置</title>
	</head>
	<body>
		<div class="col-main">
			<div class="col-crumbs">
				<div class="crumbs"><strong>当前位置：</strong>
					<a href="${currentContextPath}/admin/Rechargemange/">资金中心</a> &gt; <span id="titleName">充值相关配置</span></div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<ul class="ui-tab-title ui-tab-title2 clearfix">
								<span><li class="current">充值分流设置</li></span>
								<span><li style="display:none;"></li></span>
								 <span><li><a href="/fundAdmin/Rechargemange/index?parma=sv2&index=0">充值上下限配置</a></li></span>
								<span><li style="display:none;"></li></span>
								<span><li><a href="/fundAdmin/Rechargemange/index?parma=sv2&index=1">充值返送手续费配置</a></li></span>
								<span><li style="display:none;"></li></span>
							</ul>
							<div class="ui-tab tab-left">
								<div class="ui-tab-content ui-tab-content-current" id="DivRules">
									<div id="J-side-menu" class="ui-tab-title clearfix" style="display: inline;">
										<ul>
										<!-- /**充值方式 1:快捷充值 2:網銀 3:財富通 5:銀聯 6:支付寶(個人版) 7:微信 8:支付寶(企業版)*/ -->
											<c:forEach var="items" items="${bypassMap }" varStatus="i">
												<c:choose> 
												<c:when test="${items.key == index}">
													<li class="current">
														<a href="/fundAdmin/Rechargemange/index?parma=bypass&type=${items.key}" >
														<c:if test="${items.key==1}">网银</c:if>
														<c:if test="${items.key==2}">快捷充值</c:if>
														<c:if test="${items.key==3}">財富通</c:if>
														<c:if test="${items.key==5}">银联</c:if>
														<c:if test="${items.key==6}">支付宝个人版</c:if>
														<c:if test="${items.key==7}">微信</c:if>
														<c:if test="${items.key==8}">支付宝企业版</c:if>
														<c:if test="${items.key==9}">QQ钱包</c:if>
														</a>
													</li>
												</c:when>
												<c:otherwise>
												<li>
													<a href="/fundAdmin/Rechargemange/index?parma=bypass&type=${items.key}" >
														<c:if test="${items.key==1}">网银</c:if>
														<c:if test="${items.key==2}">快捷充值</c:if>
														<c:if test="${items.key==3}">財富通</c:if>
														<c:if test="${items.key==5}">银联</c:if>
														<c:if test="${items.key==6}">支付宝个人版</c:if>
														<c:if test="${items.key==7}">微信</c:if>
														<c:if test="${items.key==8}">支付宝企业版</c:if>
														<c:if test="${items.key==9}">QQ钱包</c:if>
														</a>
												</li>
												</c:otherwise>
												</c:choose> 
											</c:forEach>
										</ul>
									</div>
								</div>
							</div>
								<ul style="overflow-y: hidden;display: block;" id="chargeSeparate">
							<!-- 快捷充值 -->
								<li>
									<table class="table table-info table-border">
										<thead>
											<tr>
												<th colspan="${fn:length(bypassList)+1 }" class="text-left">充值分流设置 </th>
											</tr>
										</thead>
										<tbody>
											
											<input type="hidden" id="chargeWay" value="${index }">
											<form id="settingForm">
												<tr>
													<td class="text-left w-0">分流开关</td>
														<!-- /**充提渠道 0:DP 1:通匯 3:匯潮 4:匯博 5:SDPAY 7:华势  8：秒付*/ -->
														<c:forEach var="bypassConfig" items="${bypassList}" varStatus="i">
															<td class="text-left w-6">
																<c:if test="${bypassConfig['agency']==1 }">DP</c:if>
																<c:if test="${bypassConfig['agency']==2 }">通汇</c:if>
																<c:if test="${bypassConfig['agency']==3 }">汇潮</c:if>
																<c:if test="${bypassConfig['agency']==4 }">汇博</c:if>
																<c:if test="${bypassConfig['agency']==5 }">SDPAY</c:if>
																<c:if test="${bypassConfig['agency']==7 }">华势</c:if>
																<c:if test="${bypassConfig['agency']==8 }">秒付</c:if>
																<c:if test="${bypassConfig['agency']==9 }">多得宝</c:if>
																<c:if test="${bypassConfig['agency']==10 }">网富通</c:if>
																<c:if test="${bypassConfig['agency']==11 }">智付</c:if>
																<c:if test="${bypassConfig['agency']==12 }">华银</c:if>
																<c:if test="${bypassConfig['agency']==13 }">银邦</c:if>
																<c:if test="${bypassConfig['agency']==14 }">金阳</c:if>
																&nbsp;&nbsp;
																
																<!-- <span id="dpSwitch" onclick="dpSwitchChange();" class='bypassOpen' alt="开"></span> -->
															
																<input type="hidden" id="agencys${i.index}" value="${bypassConfig['agency'] }">
																
																<c:choose> 
																<c:when test="${bypassConfig['isOpen'] eq 'Y'}">
																	<span  name="dpSwitch" onclick="dpSwitchChange(${i.index },this);" class="bypassOpen" alt="开" ></span>
																	<input id="${i.index }" type="hidden" name="dpSwitch2"  value="${bypassConfig['isOpen']}">
																</c:when>
																<c:otherwise>
																	<span  name="dpSwitch" onclick="dpSwitchChange(${i.index },this);" class="bypassClose" alt="关" ></span>	
																	<input id="${i.index }" type="hidden" name="dpSwitch2"  value="${bypassConfig['isOpen']}">				
																</c:otherwise>
																</c:choose>
																
																<c:if test="${bypassConfig['dailyCharge'] ge bypassConfig['dailyUplimit'] }">
																<font color="red">（已关闭，隔日自动恢复）</font>
																</c:if>
															</td>
														</c:forEach>
												</tr>
												<tr>
													<td class="text-left w-0">分流条件</td>
													<input type="hidden" id="bypasstableLength" name="bypasstableLength" value="${fn:length(bypassList)}"/>							
													<c:forEach var="bypassConfig" items="${bypassList }" varStatus="i">
															<input type="hidden" id="bypassId" name="bypassId" value="${bypassConfig['id'] }"/>	
															<td>
															<dl class="set-list">
																<dd>
																	<span class="ui-info" style="width:80px;">金额</span>
																	<input type="text" name="lowLimit" class="input w-1 checkLimit" value="${bypassConfig['singleLowlimit']}">
																	<span class="ui-text-info"></span>-<span class="ui-text-info"></span>
																	<input type="text" name="upLimit" class="input w-1 checkLimit" value="${bypassConfig['singleUplimit']}">
																	<span class="ui-info" style="width:80px;">元</span>
																</dd>
																<dd>
																	<span class="ui-info" style="width:80px;">上限</span>
																	<input type="text" name="dailyLimit" class="input w-3 checkLimit" value="${bypassConfig['dailyUplimit']}">
																	<span class="ui-info" style="width:30px;">元 </span>
																	
																<c:choose> 
																<c:when test="${bypassConfig['dailyCharge'] ge bypassConfig['dailyUplimit'] }">
																<span class="ui-info"><font color="red">今日充值金额：${bypassConfig['DailyCharge']  } 元</font></span> 
																</c:when>
																<c:otherwise>
	                                                            <span> 今日充值金额：${bypassConfig['dailyCharge']  } 元</span>
																</c:otherwise>
																</c:choose>
																	</br>
																	<span class="ui-info" style="width:80px;"></span> 成功充值的金额超过上限时将会自动关闭该渠道，次日0时自动恢复。
																</dd>
															</dl>
														</td>
													</c:forEach>
												</tr>
											</form>
											<tr>
												<td class="text-left w-2"></td>
												<td colspan="${fn:length(bypassList) }">
												
													<!-- {if "FUND_SAVE_BYPASS"|in_array:$smarty.session.datas.info.acls} -->
													<input id="saveBtn" type="button" class="btn btn-important" value="保存" style="width:80px;" />
													<input id="cancelBtn" type="button" class="btn" name="" value="撤销编辑" style="width:100px;" />
													<!-- {/if} -->
												</td>
											</tr>
										</tbody>
									</table>
								</li>
								<br/><br/>
								<li>
									<table id="J-table-data" class="table table-info table-border">
										<thead>
											<tr>
												<th colspan="${fn:length(bypassList)+1 }" class="text-left">白名單设置</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td class="text-left w-0">白名单&nbsp;&nbsp;&nbsp;</td>
												<c:forEach var="bypassConfig" items="${bypassList }" varStatus="i">
												<td>
													<table id="J-table-data${bypassConfig['agency'] }" class="table table-info table-border" style="width:451px">
														<thead>
															<tr>
																<th class="text-left w-0">账号</th>
																<th class="text-left w-0">操作</th>
																<th class="text-left w-0">添加时间</th>
																<th class="text-left w-0">备注</th>
															</tr>
														</thead>
														<tbody id="showInfo${bypassConfig['agency'] }">
														
														</tbody>
													</table>
													<br/>
													<span>
                                              			<input id="addItem${bypassConfig['agency'] }" onclick="showItem(${bypassConfig['chargeWaySet'] },${bypassConfig['agency'] })" type="button" name="02" class="btn" value="添加白名单"  style="width:120;"/>
                   									</span>
													<div class="pagination " id="pagination${bypassConfig['agency'] }"></div>
												</td>
												</c:forEach>
											</tr>
										</tbody>
									</table>
								</li>
							</ul>	
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--添加白名单-->
    <div class="pop w-6" id="AddTipsWindow" style="display:none;">
        <div class="hd">
            <i class="close" name="closeIcoDiv"></i>
            <h3 align="center">新增充值渠道白名单</h3>
        </div>
        <div class="bd">
            <ul class="ui-form ui-form-small">
                <li>
                    <form id="addForm">
                        <label class="ui-label w-auto" align="right">用戶名：</label>
                        <input id="account" type="text" id="account" name="account">
                        <br>

                        <label class="ui-label w-auto" align="right">备注：</label>			
                        <input type="text" id="memo" name="memo" form="addForm" onkeyup='wordCount()'/>
						<span class="ui-text-prompt" id='words'>0</span><span class="ui-text-prompt">/100</span>
						<input type="hidden" id="chargeWaySetItem" name="chargeWaySetItem" value=""/>
						<input type="hidden" id="agencyItem" name="agencyItem" value=""/>
                    </form>    
                <li> 
                    <a href="javascript:saveItem();" class="btn btn-important addWhite"  >添加<b class="btn-inner"></b></a>	
                    <input class="btn" type="button" name="closeIcoDiv" value="返回"  style="width:80px;"/>
                </li>
                </li>
            </ul>
        </div>
    </div>
<script>
$(function(){
	var type=${index};
	/* 充值分流设置保存事件 */
	var indexs=$("#chargeWay").val();
	$("#saveBtn").bind("click", function () {
        $.ajax({
        	url: "/fundAdmin/Rechargemange/index?parma=saveBypassCfg&type="+indexs,
            data: $('#settingForm').serialize(),
            method: 'post',
            success: function () {
              window.location = "/fundAdmin/Rechargemange/index?parma=bypass&type="+indexs;
            }
        });
    });
	/* 充值分流设置-取消编辑事件 */
    $("#cancelBtn").bind("click", function () {
        window.location = "/fundAdmin/Rechargemange/index?parma=bypass&type="+indexs;
    });
	
    var pages=0;
	/* 获取分流类型的长度 */
    var length= $("#bypasstableLength").val(); 
   for(var i=0;i<length;i++){  
	queryWhiteList($('#chargeWay').val(),$('#agencys'+i).val());
   }

/* 新增充值渠道白名单 */
$(document).on('click', '[name="closeIcoDiv"]', function (e) {
    box2.Close();
    $("#memo").val('');
    $("#account").val('');
});   
});
/* switch按钮开关切换 */
function dpSwitchChange(index,objs) {
    var switchType = $("#"+index).val();

    if (switchType == 'Y') {
    	$(objs).attr('class', 'bypassClose');
    	$("#"+index).val('N');
    } else {
    	$(objs).attr('class', 'bypassOpen');
    	$("#"+index).val('Y');
    }
}
/* 删除白名单 */
function deleteWhitelist(id, chargeWay,agencys) {
    var r = confirm("确认删除?");
    if (r != true) {
        return;
    }
    if (id > 0) {
        $.ajax({
            url: '/fundAdmin/FundRechargemange/index?parma=deleteBypassWhiteListData',
            method: "POST",
            data: {deleteId: id},
			async: false ,
			success: function () {
				queryWhiteList(chargeWay,agencys)
			}
        });
    }
}
/* 日期格式转换 */
function getDateTime(time){
	var date = new Date(time);
	Y = date.getFullYear() + '-';
	M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
	D = (date.getDate() < 10 ? '0'+date.getDate() : date.getDate()) + ' ';
	h = (date.getHours() < 10 ? '0'+date.getHours(): date.getHours()) + ':';
	m = (date.getMinutes() < 10 ? '0'+date.getMinutes() : date.getMinutes()) + ':';
	s = (date.getSeconds() < 10 ? '0'+date.getSeconds() : date.getSeconds());
	return Y+M+D+h+m+s;
}
/* 白名单数据展示 */
function queryWhiteList(chargeWay,agencys){
	var per_page_num = 10;
	var pages=0;
	var url = "/fundAdmin/FundRechargemange/index?parma=whitelist&chargeChannel="+agencys+"&chargeWaySet="+chargeWay+"&pageNo=" + pages;
	  $.ajax({
          url: url,
          dataType: 'json',
          method: 'get',
          beforeSend: function () {
              isLock = false;
              TableStyle("showInfo"+agencys, 17, 1, "查询中");
          },
          success: function (data) {
        	  var resultAll = eval(data);
        	  var re = resultAll.bypassList;
             if (re.length > 0) {
                  var i = re.length;
                 //分页回调
                   $("#pagination"+agencys).pagination(i, {
                      num_edge_entries: 2,
                      num_display_entries: 8,
                      current_page: 0,
                      items_per_page: 10,
                      callback: queryWhiteList
                  });   	
                 var html = "";
                  //数据填充
                  $.each(re, function (i) {
                      html += "<tr id='item_" + re[i].id + "'><td>" + re[i].userAccount + "</td>";
                      if(re[i].deleteFlag == 'N'){
                          html += "<td><a href='javascript:deleteWhitelist(" + re[i].id + ","+chargeWay+","+agencys+");' style='position:initial' ><font color='#25a38a'>删除</font></a></td>";
                      }else{
                          html += "<td>&nbsp;</td>";
                      }
                      var dateTime= getDateTime(re[i].createTime);
                      html += "<td>"+dateTime+ "</td>";
                      html += "<td>" + re[i].memo + "</td></tr>";
                  });
                  $("#J-table-data"+agencys+">tbody").html(html);
              } else {
                 /*  $(".Pagination3").hide(); */
                  TableStyle("showInfo"+agencys, 17, 2, "暂未添加白名单");
              } 
          },
          complete: function ()
          {
              isLock = true;
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
              TableStyle("showInfo"+agencys, 17, 2, "数据异常");
          }
      });
}
function showItem(chargeWaySet,agency){
	 /*    模态框弹出 */
    option = {zIndex: 350},
    box2 = new LightBox("AddTipsWindow", option),
            $(document).on('click', "input[id^='addItem"+agency+"']", function () {
        box2.OverLay.Color = "rgb(51, 51, 51)";
        box2.Over = true;
        box2.OverLay.Opacity = 50;
        box2.Fixed = true;
        box2.Center = true;
        box2.Show();
		var str = this.name.split("",2);
        $("#saveType").val(str[0]);
		$("#saveChargeWaySet").val(str[1]);
		$("#words").html("0");
		$("#chargeWaySetItem").val(chargeWaySet);
		$("#agencyItem").val(agency);
    });
}
/* 统计文字个数 */
function wordCount() {
    var total = $('#memo').val().length;
    if (total > 100) {
        alert("內容已達最大上限數");
        var content = $('#memo').val().substr(0, 100);
        $('#memo').val(content);
        return;
    }
    $('#words').text(total);
}
/*    保存新添加的白名单 */
function saveItem(){
    if ($('#account').val() == "") {
        alert("请填写用戶名");
        return;
    }
    if ($('#memo').val() == "") {
        alert("请填写备注!");
        return;
    }
    var type = $("#agencyItem").val();
	var chargeWaySet = $("#chargeWaySetItem").val();
	
	$.ajax({
        url: "/fundAdmin/FundRechargemange/index?parma=saveItem",
        data: $('#addForm').serialize(),
        method: 'post',
        success: function (data) {
            if (data.length > 0) {
                if (data == 'Y') {
                    alert("用户名已存在，请勿重复添加!");
                    return;
                }
            }
            queryWhiteList(chargeWaySet,type);
            box2.Close();
            $("#memo").val('');
            $("#account").val('');

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            TableStyle("showInfo" + type, 17, 2, "数据异常");
        }
	});
}
(function (){
	//数字校验，自动矫正不符合数学规范的数学(小数两位)
	var inputs =  $('.checkLimit'),checkFn;				
	checkFn = function(){
		var me = this,v = me.value,index;
		me.value = v = v.replace(/^\.$/g, '');		
		index = v.indexOf('.');
		if(index >0){
			me.value=v.substring(0, index);
		}else{
			me.value= v;
		}
		if(me.value.length >=9){
			me.value=me.value.substring(0,9);
		}	
	};
	inputs.keyup(checkFn);
	inputs.blur(checkFn);
})();
</script>
</body>
</html>