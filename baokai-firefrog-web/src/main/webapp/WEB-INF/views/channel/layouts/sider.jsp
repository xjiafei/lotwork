<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
		<div class="col-side">
			<dl class="nav">
				<permission:hasRight moduleName="CHANNEL_DEPLOY"><dt>渠道配置</dt></permission:hasRight>	
				<permission:hasRight moduleName="CHANNEL_CONFIG">
				<dd><a href="${currentContextPath}/channel/toSaveChannelTemplate">设定参数</a></dd>
				</permission:hasRight>
				<permission:hasRight moduleName="CHANNEL_SELECT">
				<dd><a href="${currentContextPath}/channel/channelView">查询参数</a></dd>
				</permission:hasRight>
			</dl>
		</div>