<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>新建公告</title>
	<style>
		.panel-field-urgent {display:none;}
		.ui-form .J-panel-group {margin:0;}
		.checkbox-list {border-bottom:1px dotted #CCC;display:inline-block;padding-bottom:10px;}
		.checkbox-list-last {border:0;}
	</style>
</head>

<body>
			<div class="col-crumbs"><div class="crumbs">
                <strong>当前位置：</strong>
                <a href="${currentContextPath}/adAdmin/queryPublishAdPage">营销中心</a>&gt; 新建公告</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<form action="${currentContextPath}/adAdmin/createNotice" id="J-form">
						<ul class="ui-form">
							<li>
								<label for="name" class="ui-label">公告类型：</label>
								<span class="radio-list" id="J-panel-radio">
								<permission:hasRight moduleName="MARKET_NORMALNOTICE">
									<label for="J-radio-type-1" class="label"><input name="type" type="radio" value="1" class="radio" id="J-radio-type-1" checked="checked">普通</label>
									</permission:hasRight>
								<permission:hasRight moduleName="MARKET_URGENCYNOTICE">
									<label for="J-radio-type-2" class="label"><input name="type" type="radio" value="2" class="radio" id="J-radio-type-2">紧急</label>
								</permission:hasRight>
								</span>
							</li>
							<li class="panel-field-urgent">
								<label class="ui-label" for="name">展示页面：</label>
								<select id="J-select-page" class="ui-select" name="showPages">
									<option value="">请选择</option>
									<!-- option value="ad_index">平台首页</option> -->
									<option value="ad_top">平台顶部</option>
									<!-- option value="ad_foot">平台底部</option>
									<option value="ad_reg">注册页</option>
									<option value="ad_login">登录页</option -->
									<option value="ad_cqssc">重庆时时彩</option>
									<option value="ad_jxssc">江西时时彩</option>
									<option value="ad_xjssc">新疆时时彩</option>
									<option value="ad_tjssc">天津时时彩</option>
									<option value="ad_hljssc">黑龙江时时彩</option>
									<option value="ad_shssc">上海时时彩</option>
									<option value="ad_llssc">乐利时时彩</option>
									<option value="ad_sd115">山东11选5</option>
									<option value="ad_jx115">江西11选5</option>
									<option value="ad_gd115">广东11选5</option>
									<option value="ad_cq115">重庆11选5</option>
									<option value="ad_ll115">乐利11选5</option>
									<option value="ad_sl115">顺利11选5</option>
									<option value="ad_bjkl8">北京快乐8</option>
									<option value="ad_fc3d">3D</option>
									<option value="ad_p5">排列5</option>
									<option value="ad_ssq">双色球</option>
									<option value="ad_lhc">六合彩</option>
									<option value="ad_slmmc">顺利秒秒彩</option>
                                    <option value="ad_jlffc">吉利分分彩</option>
                                    <option value="ad_jsdice">江苏骰宝</option>
                                    <option value="ad_jldice1">吉利骰宝(娱乐厅)</option>
                                    <option value="ad_jldice2">吉利骰宝(至尊厅)</option>
                                    <option value="ad_ahk3">安徽快三</option>
                                    <option value="ad_lhc">六合彩</option>
									<option value="ad_vip">VIP页面</option>
								</select>
								<span class="ui-check"><i></i>请选择页面</span>
							</li>
							<li class="J-panel-group">
								<label for="" class="ui-label">展示用户组：</label>
								<span class="checkbox-list">
									<label for="J-checkbox-all" class="label"><input id="J-checkbox-all" name="rcAll" type="checkbox" value="1" class="checkbox">全部客户</label>
								</span>
							</li>

							<li class="J-panel-group">
								<label for="" class="ui-label"></label>
								<span class="checkbox-list">
									<label for="J-usergroup-2" class="label"><input type="checkbox" name="rcTopAgent" value="2" class="checkbox" id="J-usergroup-2">总代</label>
									<label for="J-usergroup-3" class="label"><input type="checkbox" name="rcOtAgent" value="3" class="checkbox" id="J-usergroup-3">其他代理</label>
									<label for="J-usergroup-10" class="label"><input type="checkbox" name="rcCustomer" value="4" class="checkbox" id="J-usergroup-10">玩家</label>
								</span>
							</li>
							<li class="J-panel-group">
								<label for="" class="ui-label"></label>
								<span class="checkbox-list checkbox-list-last">
									<label for="J-usergroup-11" class="label"><input type="checkbox" name="rcVip" value="5" class="checkbox" id="J-usergroup-11">VIP</label>
									<label for="J-usergroup-12" class="label"><input type="checkbox" name="rcNonVip" value="6" class="checkbox" id="J-usergroup-12">非VIP</label>
								</span>
								<span class="ui-check"><i></i>请选择一个用户组</span>
							</li>
							<li>
								<label class="ui-label" for="name">公告标题：</label>
								<input id="J-input-title" type="text" name="title" class="input" value=""> <span class="ui-prompt">(限40个字)</span>
								 <span class="ui-check"><i></i>公告标题长度应为1-80个字符</span>
								
							</li>
							<li class="type">
								<label class="ui-label" for="name">公告类别：</label>
									<select id="J-select-level" class="ui-select w-2" name="level">
										<option value="0">重要通知</option>
										<option value="1">派奖信息</option>
										<option value="2">平台维护</option>
										<option value="3">平台活动</option>
										<option value="4">VIP资讯</option>
									</select>
							</li>
							<li class="time">
								<label class="ui-label" for="name">有效期：</label>
								<input id="J-input-time-start" type="text" name="gmtEffectBegin" class="input" value=""> - <input id="J-input-time-end" type="text" name="gmtEffectEnd" class="input" value="不限时间" disabled="disabled" />
								<span class="ui-check"><i></i>请选择生效时间</span>
								
							</li>
							<li>
								<label class="ui-label" for="name">公告内容：</label>
								<div class="textarea">
									<textarea id="J-content" name="content" rows="12" cols="80" style="width:100%;height:300px;"></textarea>
									<span class="ui-check"><i></i>公告内容不能为空</span>
									
								</div>
							</li>
							<li id="J-row-push-proxy">
								<label class="ui-label"></label>
								<span class="ui-info"><label for="J-checkbox-proxy" class="label"><input id="J-checkbox-proxy" type="checkbox" value="1" class="checkbox">同步推送到代理首页</label></span>
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
			
	<script type="text/javascript" src="${staticFileContextPath}/static/js/advert/adNoticeCreate.js?20151208"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/jquery/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor-1.2.1.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor_lang/zh-cn.js"></script>
			
</body>