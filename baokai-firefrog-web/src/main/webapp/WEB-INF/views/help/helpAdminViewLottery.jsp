<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>帮助后台 新建彩种帮助页</title>
	<%String path = request.getContextPath(); %>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tab.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/jquery/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor-1.2.1.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor_lang/zh-cn.js"></script>
</head>
<body>
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">内容中心</a>&gt; <a href="${currentContextPath}/helpAdmin/goHelpManager">帮助管理</a> &gt; 彩种详情</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main panel-help-addlottery">
					
							<form id="J-form" method="post" action="${currentContextPath}/helpAdmin/createLottery">
					

										<ul class="ui-form">
											<li>
												<label class="ui-label">所属类目：</label>
									<select id="J-select-type" class="ui-select" name="cateId" disabled>
										<option value="">${help.cateName}</option>
									</select>
									<select id="J-select-type-2" class="ui-select" name="cateId2" disabled>
										<option value="">${help.cateName2}</option>
									</select>
												<span class="ui-check"><i></i></span>
												
											</li>
											<li>
												<label class="ui-label">标题：</label>
												<input id="J-title" type="text" value="${help.title}" class="input w-6" name="title" disabled>
												<span class="ui-check"><i></i></span>
											</li>
											<li class="checkbox-list">
												<label class="ui-label">是否推荐：</label>
												<span class="radio-list">
													<input id="radio-recommend-1" name="isRec" type="radio" value="1" class="radio" <c:if test="${help.isRec==1}">checked</c:if> disabled><label for="radio-recommend-1" class="label" >&nbsp;是</label>
													<input id="radio-recommend-0" name="isRec" type="radio" value="0" class="radio" <c:if test="${help.isRec==0}">checked</c:if> disabled><label for="radio-recommend-0" class="label">&nbsp;否</label>
												</span>
											</li>
											<li>
												<label class="ui-label">预览：</label>
												<img id="pic1" src="${urlViewPic}${help.lotteryLogo}" width="75" />
											</li>
											<li>
												<label class="ui-label">彩种链接：</label>
												<input id="J-url" type="text" value="${help.lotteryLink}" class="input w-6" name="lotteryLink" disabled/>
												<span class="ui-check"><i></i></span>
											</li>
											<li>
												<label class="ui-label">广告词：</label>
												<input id="J-word-ad" type="text" value="${help.lotteryAdvert}" class="input w-6" name="lotteryAdvert" disabled/>
												<span class="help-input-tip">&nbsp;&nbsp;限20字</span>
												<span class="ui-check"><i></i></span>
											</li>
											<li>
												<label class="ui-label">彩种简介：</label>
                                                <div class="textarea w-6">
													<textarea id="J-info">${help.preface}</textarea>
                                                    <span class="ui-check" style="padding-top:65px;margin-left:60px;"><i></i></span>
													<span class="help-input-tip" style="position:absolute;margin:-10px 0 0 310px">&nbsp;&nbsp;限150字</span>
												</div>
											</li>
											<li style="margin-top:0;">
												<label class="ui-label">内容：</label>
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
																<textarea name="lotCateContent${lotContent.id}" rows="12" cols="80" style="width:100%;height:300px;">
																	${lotContent.content}
																</textarea>
															</div>
														</c:if>
														<c:if test="${status.index>0}">
															<div class="tab-lottery-type-panel">
																<textarea  name="lotCateContent${lotContent.id}" rows="12" cols="80" style="width:100%;height:300px;">
																	${lotContent.content}
																</textarea>
															</div>
														</c:if>
													</c:forEach>
												</div>
											</li>
											<li>
												<label class="ui-label">序号：</label>
												<input id="J-order" disabled name="no" style="text-align:center;" type="text" value="${help.no}" class="input w-1">
												<span class="ui-check"><i></i></span>
											</li>

											<li class="ui-btn">
												<a href="javascript:history.back(-1);">返 回<b class="btn-inner"></b></a>
											</li>
										</ul>
										<input type="hidden" id="J-hide-pic" name="lotteryLogo" value=""/>
							</form>

										
										<div style="height:600px;"></div>
								
						
						
						
					</div>
				</div>
			</div>
		</div>
<script>
/**   
 * Simple Map   
 *    
 *    
 * var m = new Map();   
 * m.put('key','value');   
 * ...   
 * var s = "";   
 * m.each(function(key,value,index){   
 *      s += index+":"+ key+"="+value+"/n";   
 * });   
 * alert(s);   
 *    
 * @author dewitt   
 * @date 2008-05-24   
 */    
function Map() {     
    /** 存放键的数组(遍历用到) */    
    this.keys = new Array();     
    /** 存放数据 */    
    this.data = new Object();     
         
    /**   
     * 放入一个键值对   
     * @param {String} key   
     * @param {Object} value   
     */    
    this.put = function(key, value) {     
        if(this.data[key] == null){     
            this.keys.push(key);     
        }     
        this.data[key] = value;     
    };     
         
    /**   
     * 获取某键对应的值   
     * @param {String} key   
     * @return {Object} value   
     */    
    this.get = function(key) {     
        return this.data[key];     
    };     
         
    /**   
     * 删除一个键值对   
     * @param {String} key   
     */    
    this.remove = function(key) {     
        this.keys.remove(key);     
        this.data[key] = null;     
    };     
         
    /**   
     * 遍历Map,执行处理函数   
     *    
     * @param {Function} 回调函数 function(key,value,index){..}   
     */    
    this.each = function(fn){     
        if(typeof fn != 'function'){     
            return;     
        }     
        var len = this.keys.length;     
        for(var i=0;i<len;i++){     
            var k = this.keys[i];     
            fn(k,this.data[k],i);     
        }     
    };     
         
    /**   
     * 获取键值数组(类似Java的entrySet())   
     * @return 键值对象{key,value}的数组   
     */    
    this.entrys = function() {     
        var len = this.keys.length;     
        var entrys = new Array(len);     
        for (var i = 0; i < len; i++) {     
            entrys[i] = {     
                key : this.keys[i],     
                value : this.data[i]     
            };     
        }     
        return entrys;     
    };     
         
    /**   
     * 判断Map是否为空   
     */    
    this.isEmpty = function() {     
        return this.keys.length == 0;     
    };     
         
    /**   
     * 获取键值对数量   
     */    
    this.size = function(){     
        return this.keys.length;     
    };     
         
    /**   
     * 重写toString    
     */    
    this.toString = function(){     
        var s = "{";     
        for(var i=0;i<this.keys.length;i++,s+=','){     
            var k = this.keys[i];     
            s += k+"="+this.data[k];     
        }     
        s+="}";     
        return s;     
    };     
}  
 function ajaxFileUpload() {  
     $.ajaxFileUpload  
	    (  
	        {  
	            url: '${currentContextPath}/helpAdmin/uploadImg',  
	            secureuri: false,  
	            fileElementId: 'J-file',  
	            dataType: 'html',  
	            beforeSend: function() {  
	                $("#loading").show();  
	            },  
	            complete: function() { 
	            },  
	            success: function(data, status) {  
	            	$("#pic1").attr("src","../imgUpload/"+data);
	            	$("#J-hide-pic").attr("value",data);
	                if (typeof (data.error) != 'undefined') {  
	                    if (data.error != '') {  
	                        alert(data.error);  
	                    } else {  
	                        alert(data.msg);  
	                    }  
	                }  
	            },  
	            error: function(data, status, e) {  
	                alert(e);  
	            }  
	        }  
	    )  
     return false;  
 }  	

	$('#J-select-type').change(function() {
		var index = $(this).val();
		var subcates = cates.get(index);
		var idCates = cateIds.get(index);
		var subIds = cates
		for (var i=0; i<subcates.length; i++) {
			$('#J-select-type-2').append('<option value="'+idCates[i]+'">'+subcates[i]+'</option>');
		}
	});
    var tab = new phoenix.Tab({eventType:'click',par:'#J-tab-lottery-type',
	triggers:'.tab-lottery-type-title li',panels:'.tab-lottery-type-panel',
	currClass:'tab-lottery-type-title-current',currPanelClass:'tab-lottery-type-panel-current'});

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