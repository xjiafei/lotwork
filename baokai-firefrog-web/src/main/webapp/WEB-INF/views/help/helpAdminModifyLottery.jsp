<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>帮助后台 新建彩种帮助页</title>
	<%String path = request.getContextPath(); %>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/help/helpAdminModifyLottery.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tab.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/jquery/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor-1.2.1.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor_lang/zh-cn.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/ajaxfileupload.js"></script>
</head>
<body>
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">内容中心</a>&gt; <a href="${currentContextPath}/helpAdmin/goHelpManager">帮助管理</a> &gt; 修改彩种</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main panel-help-addlottery">
					
							<form id="J-form" method="post" action="${currentContextPath}/helpAdmin/createLottery">
								<input type="hidden" name="id" value="${help.id}">
										<ul class="ui-form">
											<li>
												<label class="ui-label">所属类目：</label>
									<select id="J-select-type" class="ui-select" name="cateId">
										<option value="">请选择</option>
										<c:forEach items="${cateList}" var="cateList">
											<option value="${cateList.id}" <c:if test="${cateList.id==help.cateId}">selected</c:if>>${cateList.name}</option>
										</c:forEach>
									</select>
									<select id="J-select-type-2" class="ui-select" name="cateId2">
										<option value="">请选择</option>
									</select>
												<span class="ui-check"><i></i></span>
												
											</li>
											<li>
												<label class="ui-label">标题：</label>
												<input id="J-title" type="text" value="${help.title}" class="input w-6" name="title">
												<span class="ui-check"><i></i></span>
											</li>
											<li class="checkbox-list">
												<label class="ui-label">是否推荐：</label>
												<span class="radio-list">
													<input id="radio-recommend-1" name="isRec" type="radio" value="1" class="radio" <c:if test="${help.isRec==1}">checked</c:if>><label for="radio-recommend-1" class="label">&nbsp;是</label>
													<input id="radio-recommend-0" name="isRec" type="radio" value="0" class="radio" <c:if test="${help.isRec==0}">checked</c:if>><label for="radio-recommend-0" class="label">&nbsp;否</label>
												</span>
											</li>
											<li>
												<label class="ui-label">上传LOGO：</label>
												
												<input id="J-file" type="file" value="添加附件" name="tempFile" class="file">
												<span class="ui-prompt">支持格式：JPG、GIF、PNG，大小2M内，尺寸：128*80</span>
												<span class="ui-check"><i></i></span>
											</li>
											<li>
												<label class="ui-label">预览：</label>
												<img id="pic1" src="${urlViewPic}${help.lotteryLogo}" width="75" />
											</li>
											<li>
												<label class="ui-label">彩种链接：</label>
												<input id="J-url" type="text" value="${help.lotteryLink}" class="input w-6" name="lotteryLink"/>
												<span class="ui-check"><i></i></span>
											</li>
											<li>
												<label class="ui-label">广告词：</label>
												<input id="J-word-ad" type="text" value="${help.lotteryAdvert}" class="input w-6" name="lotteryAdvert"/>
												<span class="help-input-tip">&nbsp;&nbsp;限20字</span>
												<span class="ui-check"><i></i></span>
											</li>
											<li>
												<label class="ui-label">彩种简介：</label>
                                                <div class="textarea w-6">
													<textarea  id="J-info" name="preface">${help.preface}</textarea>
                                                    <span class="ui-check" style="margin-left:360px;margin-top:-20px;"><i></i></span>
													<span class="help-input-tip" style="position:absolute;margin:-10px 0 0 310px">&nbsp;&nbsp;限150字</span>
												</div>
											</li>
											<li style="margin-top:0;">
												<label class="txtlottery" class="ui-label">内容：</label>
												<div id="J-tab-lottery-type" class="tab-lottery-type">
													<ul class="tab-lottery-type-title">
														<c:forEach items="${lotCateList}" var="lotCate" varStatus="status">
															<c:if test="${status.index==0}">
																<li class="tab-lottery-type-title-current">${lotCate.name}</li>
																<input type="hidden" name="lotCateName${lotCate.id}" value="${lotCate.name}" />
															</c:if>
															<c:if test="${status.index>0}">
																<li>${lotCate.name}</li>
																<input type="hidden" name="lotCateName${lotCate.id}" value="${lotCate.name}" />
															</c:if>
														</c:forEach>
													</ul>
													<c:forEach items="${help.lotteryContentStruc}" var="lotContent" varStatus="status">
														<c:if test="${status.index==0}">
															<div class="tab-lottery-type-panel tab-lottery-type-panel-current">
																<textarea class="txtlottery"  name="lotCateContent${lotContent.id}" rows="12" cols="80" style="width:100%;height:300px;">
																	${lotContent.content}
																</textarea>
															</div>
														</c:if>
														<c:if test="${status.index>0}">
															<div class="tab-lottery-type-panel">
																<textarea class="txtlottery" name="lotCateContent${lotContent.id}" rows="12" cols="80" style="width:100%;height:300px;">
																	${lotContent.content}
																</textarea>
															</div>
														</c:if>
													</c:forEach>
												</div>
											</li>
											<li>
												<label class="ui-label">序号：</label>
												<input id="J-order"  name="no" style="text-align:center;" type="text" value="${help.no}" class="input w-1">
												<span class="ui-check"><i></i></span>
											</li>

											<li class="ui-btn">
												<a id="J-button-submit" href="#" class="btn btn-important">确 定<b class="btn-inner"></b></a>
												<a href="javascript:history.back(-1);">返 回<b class="btn-inner"></b></a>
											</li>
										</ul>
										<input type="hidden" id="J-hide-pic" name="lotteryLogo" value="${help.lotteryLogo}"/>
							</form>

										
										<div style="height:600px;"></div>
								
						
						
						
					</div>
				</div>
			</div>
		</div>
		
<script>
 function ajaxFileUpload() {
     $.ajaxFileUpload({
                 url: '${currentContextPath}/helpAdmin/uploadImg',
                 secureuri: false,
                 fileElementId: 'J-file',
                 type: 'post',
                 dataType: 'json',
                 beforeSend: function () {
                     $("#loading").show();
                 },
                 complete: function () {
                 },
                 success: function (data, status) {
                     if (typeof (data.error) != 'undefined') {
                         if(data.error == 0) {
                             alert("图片太大无法上传");
                         } else if(data.error == 1) {
                             alert("图片格式不正确");
                         } else {
                             alert("上传失败")
                         }
                     } else {
                         $("#pic1").attr("src", "${urlViewPic}" + data.name);
                         $("#pic1_a").attr("href", "${urlViewPic}" + data.name);
                         $("#J-hide-pic").attr("value", data.name);
                     }
                 },
                 error: function (data, status, e) {
                     alert(e);
                 }
             }
     );
     return false;  
 }  	
 
	if('${msg}'!='')
	{
		alert('${msg}');
	}
	var cates = new Map();
	var cateIds = new Map();
	<c:forEach items="${cateList}" var="cate">
	var cateList${cate.id} = new Array();
	var cateListId${cate.id} = new Array();
	<c:forEach items="${cate.subCate}" var="subCate" varStatus="status">
	cateList${cate.id}[${status.index}] = '${subCate.name}';
	cateListId${cate.id}[${status.index}] = '${subCate.id}';
	</c:forEach>
	cates.put('${cate.id}',cateList${cate.id});
	cateIds.put('${cate.id}',cateListId${cate.id});
	</c:forEach>
 
	var index = $(this).val();
	var subcates = cates.get(${help.cateId});
	var idCates = cateIds.get(${help.cateId});
	var subIds = cates;
	for (var i=0; i<subcates.length; i++) {
		if(idCates[i]=='${help.cateId2}') {
			$('#J-select-type-2').append('<option value="'+idCates[i]+'" selected>'+subcates[i]+'</option>');
		}
		else {
			$('#J-select-type-2').append('<option value="'+idCates[i]+'">'+subcates[i]+'</option>');
		}
	}


	$('#J-select-type').change(function() {
		$("#J-select-type-2").empty();
		var index = $(this).val();
		var subcates = cates.get(index);
		var idCates = cateIds.get(index);
		var subIds = cates;
		$('#J-select-type-2').append('<option value=-1>请选择</option>');
		if(!subcates)
		{
			for (var i=0; i<subcates.length; i++) {
				if(idCates[i]=='${help.cateId2}') {
					$('#J-select-type-2').append('<option value="'+idCates[i]+'" selected>'+subcates[i]+'</option>');
				}
				else {
					$('#J-select-type-2').append('<option value="'+idCates[i]+'">'+subcates[i]+'</option>');
				}
			}
		}
	});	


	var cates = new Map();
	var cateIds = new Map();
	<c:forEach items="${cateList}" var="cate">
	var cateList${cate.id} = new Array();
	var cateListId${cate.id} = new Array();
	<c:forEach items="${cate.subCate}" var="subCate" varStatus="status">
	cateList${cate.id}[${status.index}] = '${subCate.name}';
	cateListId${cate.id}[${status.index}] = '${subCate.id}';
	</c:forEach>
	cates.put('${cate.id}',cateList${cate.id});
	cateIds.put('${cate.id}',cateListId${cate.id});
	</c:forEach>
</script>		
		
</body>
</html>