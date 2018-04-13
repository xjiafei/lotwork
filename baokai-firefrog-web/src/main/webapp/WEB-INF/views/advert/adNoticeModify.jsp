<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>修改公告</title>
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
                    <a href="${currentContextPath}/adAdmin/queryPublishAdPage">营销中心</a> &gt;
                    <a href="${currentContextPath}/adAdmin/goNoticeListPublish">公告管理（发布）</a> &gt; 修改公告
                </div>
            </div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<form action="${currentContextPath}/adAdmin/modifyNotice" id="J-form">
						<input type="hidden" value="${notice.gmtEffectEndStr}" id="endtimeHidden">
						<input type="hidden" value="${notice.id}" name="id" id="noticeId">
						<input type="hidden" value="${notice.type}" name="id" id="noticeType">
						<ul class="ui-form">
							<li>
								<label for="name" class="ui-label">公告类型：</label>
								<span class="radio-list" id="J-panel-radio">
									<label for="J-radio-type-1" class="label"><input name="type" type="radio" value="1" class="radio" id="J-radio-type-1"    >普通</label>
									<label for="J-radio-type-2" class="label"><input name="type" type="radio" value="2" class="radio" id="J-radio-type-2"    >紧急</label>
								</span>
							</li>
							<li class="panel-field-urgent">
								<label class="ui-label" for="name">展示页面：</label>
								<select id="J-select-page" class="ui-select" name="showPages">
									<option value="">请选择</option>
									<!-- option value="ad_index" <c:if test="${notice.showPages=='ad_index'}">selected</c:if>  >平台首页</option> -->
									<option value="ad_top" <c:if test="${notice.showPages=='ad_top'}">selected</c:if>  >平台顶部</option>
									<!-- option value="ad_foot" <c:if test="${notice.showPages=='ad_foot'}">selected</c:if>  >平台底部</option>
									<option value="ad_reg" <c:if test="${notice.showPages=='ad_reg'}">selected</c:if>  >注册页</option>
									<option value="ad_login" <c:if test="${notice.showPages=='ad_login'}">selected</c:if>  >登录页</option-->
									<option value="ad_cqssc" <c:if test="${notice.showPages=='ad_cqssc'}">selected</c:if>  >重庆时时彩</option>
									<option value="ad_jxssc" <c:if test="${notice.showPages=='ad_jxssc'}">selected</c:if>  >江西时时彩</option>
									<option value="ad_xjssc" <c:if test="${notice.showPages=='ad_xjssc'}">selected</c:if>  >新疆时时彩</option>
									<option value="ad_tjssc" <c:if test="${notice.showPages=='ad_tjssc'}">selected</c:if>  >天津时时彩</option>
									<option value="ad_hljssc" <c:if test="${notice.showPages=='ad_hljssc'}">selected</c:if>  >黑龙江时时彩</option>
									<option value="ad_shssc" <c:if test="${notice.showPages=='ad_shssc'}">selected</c:if>  >上海时时彩</option>
									<option value="ad_llssc" <c:if test="${notice.showPages=='ad_llssc'}">selected</c:if>  >乐利时时彩</option>
									<option value="ad_sd115" <c:if test="${notice.showPages=='ad_sd115'}">selected</c:if>  >山东11选5</option>
									<option value="ad_jx115" <c:if test="${notice.showPages=='ad_jx115'}">selected</c:if>  >江西11选5</option>
									<option value="ad_gd115" <c:if test="${notice.showPages=='ad_gd115'}">selected</c:if>  >广东11选5</option>
									<option value="ad_cq115" <c:if test="${notice.showPages=='ad_cq115'}">selected</c:if>  >重庆11选5</option>
									<option value="ad_ll115" <c:if test="${notice.showPages=='ad_ll115'}">selected</c:if>  >乐利11选5</option>
									<option value="ad_sl115" <c:if test="${notice.showPages=='ad_sl115'}">selected</c:if>  >顺利11选5</option>
									<option value="ad_bjkl8" <c:if test="${notice.showPages=='ad_bjkl8'}">selected</c:if>  >北京快乐8</option>
									<option value="ad_3d" <c:if test="${notice.showPages=='ad_3d'}">selected</c:if>  >3D</option>
									<option value="ad_p5" <c:if test="${notice.showPages=='ad_p5'}">selected</c:if>  >排列5</option>
									<option value="ad_ssq" <c:if test="${notice.showPages=='ad_ssq'}">selected</c:if>  >双球</option>
									<option value="ad_mmc" <c:if test="${notice.showPages=='ad_slmmc'}">selected</c:if>  >顺利秒秒彩</option>
                                    <option value="ad_jlffc" <c:if test="${notice.showPages=='jlffc'}">selected</c:if>  >吉利分分彩</option>
									<option value="ad_jsdice" <c:if test="${notice.showPages=='ad_jsdice'}">selected</c:if>  >江苏骰宝</option>
									<option value="ad_ahk3" <c:if test="${notice.showPages=='ad_ahk3'}">selected</c:if>  >安徽快三</option>
									<option value="ad_lhc" <c:if test="${notice.showPages=='ad_lhc'}">selected</c:if>  >六合彩</option>
									<option value="ad_vip" <c:if test="${notice.showPages=='ad_vip'}">selected</c:if>  >VIP页面</option>
									<option value="ad_jldice1" <c:if test="${notice.showPages=='ad_jldice1'}">selected</c:if>  >吉利骰宝(娱乐厅)</option>
									<option value="ad_jldice2" <c:if test="${notice.showPages=='ad_jldice2'}">selected</c:if>  >吉利骰宝(至尊厅)</option>
								</select>
								<span class="ui-check"><i></i>请选择页面</span>
							</li>
							<li style="margin:0;">
								<label for="" class="ui-label">展示用户组：</label>
								<span class="checkbox-list">
									<label for="J-checkbox-all" class="label"><input id="J-checkbox-all" type="checkbox" name="rcAll" value="1" class="checkbox" <c:if test="${notice.rcAll==1}">checked</c:if>>全部用户</label>
								</span>
							</li>
							<li class="J-panel-group">
								<label for="" class="ui-label"></label>
								<span class="checkbox-list">
									<label for="J-usergroup-2" class="label"><input type="checkbox" value="2" name="rcTopAgent" class="checkbox" id="J-usergroup-2" <c:if test="${notice.rcTopAgent==1}">checked</c:if>>总代</label>
									<label for="J-usergroup-3" class="label"><input type="checkbox" value="3" name="rcOtAgent" class="checkbox" id="J-usergroup-3" <c:if test="${notice.rcOtAgent==1}">checked</c:if>>其他代理</label>
									<label for="J-usergroup-10" class="label"><input type="checkbox" value="4" name="rcCustomer" class="checkbox" id="J-usergroup-10" <c:if test="${notice.rcCustomer==1}">checked</c:if>>玩家</label>
								</span>
							</li>
							<li class="J-panel-group">
								<label for="" class="ui-label"></label>
								<span class="checkbox-list">
									<label for="J-usergroup-11" class="label"><input type="checkbox" name="rcVip" value="5" class="checkbox" id="J-usergroup-11" <c:if test="${notice.rcVip==1}">checked</c:if>>VIP</label>
									<label for="J-usergroup-12" class="label"><input type="checkbox" name="rcNonVip" value="6" class="checkbox" id="J-usergroup-12" <c:if test="${notice.rcNonVip==1}">checked</c:if>>非VIP</label>
								</span>
							</li>
							<li class="J-panel-group">
								<label for="" class="ui-label"></label>
								<span class="checkbox-list checkbox-list-last">
									
								</span>
								<span class="ui-check"><i></i>请选择一个用户组</span>
							</li>
							<li>
								<label class="ui-label" for="name">公告标题：</label>
								<input id="J-input-title" type="text" name="title" class="input" value="${notice.title}"> <span class="ui-prompt">(限40个字)</span>
								 <span class="ui-check"><i></i>公告标题长度应为1-80个字符</span>
								
							</li>
							
							<li class="type">
								<label class="ui-label" for="name" value="${notice.noticelevel}">公告类别：</label>
									<select id="J-select-level" class="ui-select w-2" name="level">
									 <c:choose>
	   									<c:when test="${notice.noticelevel == '0'}">
	    										<option value="0" selected="selected">重要通知</option>
										</c:when>
										<c:when test="${notice.noticelevel == '1'}">
												<option value="1" selected="selected">派奖信息</option>
										</c:when>
										<c:when test="${notice.noticelevel == '2'}">
												<option value="2" selected="selected">平台维护</option>
										</c:when>
										<c:when test="${notice.noticelevel == '3'}">
												<option value="3" selected="selected">平台活动</option>
										</c:when>
										<c:when test="${notice.noticelevel == '4'}">
												<option value="4" selected="selected">VIP资讯</option>
										</c:when>
									</c:choose>
										<option value="0">重要通知</option>
										<option value="1">派奖信息</option>
										<option value="2">平台维护</option>
										<option value="3">平台活动</option>
										<option value="4">VIP资讯</option>
									</select>
						
								
							</li>
							
							<li class="time">
								<label class="ui-label" for="name">有效期：</label>
								<input id="J-input-time-start" type="text" class="input" name="gmtEffectBegin" value="${notice.gmtEffectBeginStr}"> - <input id="J-input-time-end" type="text" class="input" name="gmtEffectEnd" value="${notice.gmtEffectEndStr}" disabled="disabled" />
								<span class="ui-check"><i></i>请选择生效时间</span>
								
							</li>
							<li>
								<label class="ui-label" for="name">公告内容：</label>
								<div class="textarea">
									<textarea id="J-content" name="content" rows="12" cols="80" style="width:100%;height:300px;">${notice.content}</textarea>
									<span class="ui-check"><i></i>公告内容不能为空</span>
									
								</div>
							</li>
							<li id="J-row-push-proxy">
								<label class="ui-label"></label>
								<span class="ui-info"><label for="J-checkbox-proxy" class="label"><input id="J-checkbox-proxy" type="checkbox" value="1" class="checkbox" <c:if test="${notice.showPages=='agent_first_page'}">checked</c:if> >同步推送到代理首页</label></span>
							</li>
							<li class="ui-btn">
								<a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">提交审核<b class="btn-inner"></b></a>
								<a id="J-button-save" class="btn" href="javascript:void(0);">保 存<b class="btn-inner"></b></a>
							</li>
						</ul>
						</form>
					</div>
				</div>
			</div>

	<script type="text/javascript" src="${staticFileContextPath}/static/js/advert/adNoticeModify.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/jquery/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor-1.2.1.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor_lang/zh-cn.js"></script>
</body>