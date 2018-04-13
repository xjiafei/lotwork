<%@ taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title><decorator:title /></title>
	<link rel="stylesheet" type="text/css" href="${staticFileContextPath}/static/images/common/base.min.css?20151218" />
	<link rel="stylesheet" type="text/css" href="${staticFileContextPath}/static/images/common/js-ui.css" />
	<link rel="stylesheet" type="text/css" href="${staticFileContextPath}/static/images/game/chart.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/css/pagination.css" />
	<script type="text/javascript">
	        var data_url = document.URL.split("/");
	        var application_path = '${currentContextPath}';
	        var json_data_url = application_path + '/game/chart/' + data_url[data_url.length-2] + '/' + data_url[data_url.length-1] + '/data';
	        var currentGameType = data_url[data_url.length-2];
	        var currentGameMethod = data_url[data_url.length-1];
	        var myRender = currentGameType + currentGameMethod + 'Render';
			var _logOut = '${userContextPath}';	
			var hjUserData=encodeURI('${userName}')+'|${sex}|${cellphone}|${cellphone}|${email}|${registerAddress}||||${userId}|${viplvl}|';
			<%-- 2016.03.14 Tool Bar 整合  Notice--%>
			var global_params_notice = "all,ad_top,agent_first_page";
			var global_userID = "${userId}";
	</script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/base-all.min.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tip.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.DatePicker.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.SuperSearchGroup.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.SuperSearch.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Message.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.pagination.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Common.js"></script>

	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.EditConfirm.js" ></script>
	<!--[if IE]><script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/excanvas.js"></script><![endif]-->
	<!--<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/raphael-min.js" ></script>-->
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.underscorextend.js" ></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/phoenix.GameChart.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/phoenix.GameChart.case.js"></script>
	
	
	<decorator:head />
</head>
<body>
<%-- 2016.03.14 Tool Bar 整合 Start--%>
	<jsp:include page="header-front-toolbar.jsp" flush="true" />
<%-- 2016.03.14 Tool Bar 整合 End--%>

	<decorator:body />
	<!-- 公告通知 -->
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Notice.js" ></script> 
	<!-- 下载 -->
	<script type="text/javascript">
	(function($){			
		$('#J-button-export').click(function(){
			var Wd = phoenix.Message.getInstance(),
				htmlStr = '';
			//var ipts = $('#J-form-hidden').find('input[type="hidden"]'),data = {},htmlStr = '';
			//ipts.each(function(){
			//	var name = this.getAttribute('name');
			//	data[name] = this.value;
			//});
			//htmlStr = phoenix.util.template($('#J-tpl-export').text(), data);
			htmlStr = '<ul class=\"ui-form ui-form-small\"> <li><span class=\"ui-singleline\">为您导出开奖结果</span></li> </ul><li style="text-align:center;" class="radio-list">     <label for="J-radio-doc-type-1" class="label"><input type="radio" value="1" class="radio radio-doc-type" name="radio_doc_types" id="J-radio-doc-type-1s" checked="checked">xls</label><label for="J-radio-doc-type-2" class="label"><input type="radio" value="2" class="radio radio-doc-type" name="radio_doc_types" id="J-radio-doc-type-2s">txt</label> </li>';
			Wd.show({
				mask:true,
				title:'温馨提示',
				content:htmlStr,
				confirmIsShow:true,
				cancelIsShow:true,
				confirmText:'确定',
				cancelText:'确定',
				confirmFun:function(){
					//form表单给值radio
					var _value = $("input[name='radio_doc_types']:checked").val();
					 $("input[name='radio_doc_type'][value="+_value+"]").attr("checked",true);  					 
					$('#startTime').val($('#J-date-star').val());
					$('#endTime').val($('#J-date-end').val());
					$('#J-download-form').submit();
					Wd.hide();
				},
				cancelFun:function(){
					Wd.hide();
				}//,
				//callback:function(){
				//	var rows = Wd.win.dom.find('li[data-data]');
				//	rows.each(function(){
				//		var dom = $(this);
				//		if($.trim(dom.attr('data-data')) == ''){
				//			dom.remove();
				//		}
				//	});
				//}
			});
			
		});				
		
	})(jQuery);
	</script>
<div id="import" style="display: none;">
    <form action="/game/chart/${view}/${groupCode}/reportDownload/data" target="_blank" id="J-download-form" method="post" >
        <input type="hidden" id="periodsType" name="periodsType" value="periods"/>
        <input type="hidden" id="periodsNum" name="periodsNum" value="30"/>
        <input type="hidden" id="startTime" name="periodsNum[startTime]" value=""/>
        <input type="hidden" id="endTime" name="periodsNum[endTime]" value=""/>
        <ul class="ui-form ui-form-small">
            <li><span class="ui-singleline">为您导出开奖结果</span></li>
        </ul>
        <ul class="ui-form ui-form-small">
            <li style="text-align:center;"><span class="ui-singleline">请选择导出报表的格式</span>
            </li>
            <li class="radio-list" style="text-align:center;">
                <label class="label" for="J-radio-doc-type-1"><input checked="checked" id="J-radio-doc-type-1" name="radio_doc_type" type="radio" class="radio radio-doc-type" value="1">xls</label>
                <label class="label" for="J-radio-doc-type-2"><input id="J-radio-doc-type-2" name="radio_doc_type" type="radio" class="radio radio-doc-type" value="2">txt</label>
            </li>
        </ul>
    </form>
</div>
</body>
</html>