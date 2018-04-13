$(document).ready(function(){
	//$('.menu-list li:eq(1)').attr("class","current");
	//弹窗
	var Wd = phoenix.Message.getInstance();

	//默认文本
	var InputUserAccunt = new phoenix.Input({defText:"多个用户名用；分开",el:'#userAcuntP'});
	var InputRemark = new phoenix.Input({defText:"可不填写",el:'#remarkP'});
	var InputSearch = new phoenix.Input({defText:"",el:'#J-search-input'});
	
	if($('#J-hidden-word').val()!= ""){
		$('#J-search-input').val($('#J-hidden-word').val());
	}
	
	if($('#userAcunt').val()!= ""){
		$('#userAcuntP').val($('#userAcunt').val());
	}
	if($('#remark').val()!= ""){
		$('#remarkP').val($('#remark').val());
	}
	if($('#mode').val() == 1){
		var ip = $('#ipAddr').val().split(".");		
		$('#classA').val(ip[0]);
		$('#classB').val(ip[1]);
		$('#classC').val(ip[2]);
		$('#classD').val(ip[3]);
	}
	if($('#typeVal').val() == 1){
		$('#type').val(1);
	}else if ($('#typeVal').val() == 0) {
		$('#type').val(0);
	}
	//搜索表单提交
	$('#J-button-submit').click(function(){
		$('#J-whiteList-form').submit();
	});
	
	if($('#msg').val() != "" && $('#msg').val() !=undefined)
	{
		alert($('#msg').val());
	}
	//新增指定IP
	$('#J-button-Add').click(function(){
		var classA = $('#classA').val();
		var classB = $('#classB').val();
		var classC = $('#classC').val();
		var classD = $('#classD').val();
		var userAccunt = $('#userAcunt').val();
		var ua = InputUserAccunt.getValue();
		var r = InputRemark.getValue();
		
		if($.trim(classA) == '' || $.trim(classB) == '' || $.trim(classC) == '' || $.trim(classD) == ''){
			alert('ip地址段不能为空');
			return;
		}
		if($.trim(ua) == '' || ua == InputUserAccunt.getDefText()){
			alert('用户名不能为空');
			InputUserAccunt.dom.focus();
			return;
		}else{
			var userNM = $('#userAcuntP').val().split(";");
			var uniqueNames = [];
			$.each(userNM, function(i, el){
				if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
			});
			for(i=0;i< uniqueNames.length;i++){			
				if(!checkAccunt(userNM[i])){
					alert( userNM[i] + ' 用户名不符合規範');
					InputUserAccunt.dom.focus();
					return;
				}			
			}			
			$('#userAcunt').val(uniqueNames.join(";"));
		}
		if($.trim(r) == '' || r == InputRemark.getDefText()){
			$('#remark').val("");
		}else{
			$('#remark').val($('#remarkP').val());
		}
		$('#ipAddr').val(classA + "." + classB + "." + classC + "." + classD);
		$('#J-whiteList-form').submit();
	});
	// change event ip地址范围每段都是“0~255”，输入时超过最大值的系统自动改为最大值
	$('#classA').on('change', function() {
		var ip = $('#classA').val();
		if($.isNumeric(ip)){
			if(ip > 255){
				ip=255;
			} else if(ip < 0){
				ip=0;
			}			
		}else{
			ip = "";
		}	
		$('#classA').val(ip);
	});
	$('#classB').on('change', function() {
		var ip = $('#classB').val();
		if($.isNumeric(ip)){
			if(ip > 255){
				ip=255;
			} else if(ip < 0){
				ip=0;
			}			
		}else{
			ip = "";
		}	
		$('#classB').val(ip);
	});
	$('#classC').on('change', function() {
		var ip = $('#classC').val();
		if($.isNumeric(ip)){
			if(ip > 255){
				ip=255;
			} else if(ip < 0){
				ip=0;
			}			
		}else{
			ip = "";
		}	
		$('#classC').val(ip);
	});
	$('#classD').on('change', function() {
		var ip = $('#classD').val();
		if($.isNumeric(ip)){
			if(ip > 255){
				ip=255;
			} else if(ip < 0){
				ip=0;
			}			
		}else{
			ip = "";
		}	
		$('#classD').val(ip);
	});
	var word = $('#J-hidden-word').val();
	if($('#typeVal').val() == 0 &&  word && word.length > 0){ //用戶名搜尋
		$('#J-table-list .user').each(function(){
			var userStr = this.innerHTML;				
			var userStrArr = userStr.split(";");
			for(i=0;i< userStrArr.length;i++){
			if(word == userStrArr[i]){
				userStrArr.splice(i,1);
			}
			var result = userStrArr.join(";");
			if(userStrArr.length > 0){
				result = '<span style="color:red;">' + word + '</span>' + ";" + result;
			}else{
				result = '<span style="color:red;">' + word + '</span>';
			}
			this.innerHTML = result
			}
		});
		$('#J-table-log-list .user').each(function(){
			if(word.length > 0){
				var userStr = this.innerHTML;
				var userStrArr = userStr.split(";");
				for(i=0;i< userStrArr.length;i++){
				if(word == userStrArr[i]){
					userStrArr.splice(i,1);
				}
			}
			var result = userStrArr.join(";");
			if(userStrArr.length > 0){
				result = '<span style="color:red;">' + word + '</span>' + ";" + result;
			}else{
				result = '<span style="color:red;">' + word + '</span>';
			}
			this.innerHTML = result
			}
		});
	}
	
	
	//全选
	$('#J-select-all').click(function(){
		var inputs = $('#J-table-list').find('input[type="checkbox"]');
		if(this.checked){
			inputs.each(function(){
				this.checked = true;
			});
		}else{
			inputs.each(function(){
				this.checked = false;
			});
		}
	});
	
	//批量删除
	$('#J-delete-all').click(function(){
		var inputs = $('#J-table-list').find('input[type="checkbox"]'),result = "";
		inputs.each(function(){
			if(this.checked){
				result = result+this.value+","
			}
		});
		if(result.length < 1){
			return;
		}
		Wd.show({
			mask:true,
			title:'提示',
			content:'你确定要删除列表中所选的ip记录吗？',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$("#ids").val(result);
				$("#J-whiteList-form").attr("action", currentPath + "/globeAdmin/deleteWhiteListIP");
				$('#J-whiteList-form').submit();
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
	});
	//列表删除单行
	$('#J-table-list').on('click', '.ip-list-delete', function(){
		var id = Number($(this).attr('data-id'));
		Wd.show({
			mask:true,
			title:'提示',
			content:'确定要删除该条记录吗？',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$("#ids").val(id);
				$("#J-whiteList-form").attr("action", currentPath + "/globeAdmin/deleteWhiteListIP");
				$('#J-whiteList-form').submit();
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
	});
	//查看单行
	$('#J-table-list').on('click', '.ip-list-show', function(){
		var id = Number($(this).attr('data-id'));
		$("#ids").val(id);
		$("#J-whiteList-form").attr("action", currentPath + "/globeAdmin/viewIp");
		$('#J-whiteList-form').submit();
	});
	//查看歷史紀錄单行
	$('#J-table-log-list').on('click', '.log-list-show', function(){
		var id = Number($(this).attr('data-id'));
		$("#ids").val(id);
		$("#J-whiteList-form").attr("action", currentPath + "/globeAdmin/viewIp");
		$('#J-whiteList-form').submit();
	});
	//關閉頁面
	$('#J-button-Close').click(function(){
		history.back(1);
	});
	//取消頁面
	$('#J-button-cancel').click(function(){
		history.back(1);
	});
	
	//修改单行
	$('#J-table-list').on('click', '.ip-list-edit', function(){
		var id = Number($(this).attr('data-id'));
		$("#ids").val(id);
		$("#J-whiteList-form").attr("action", currentPath + "/globeAdmin/editWhiteListIp");
		$('#J-whiteList-form').submit();
	});

	$('#import').click(function() {		
		$('input[name=file]').click();
		
		$("#J-upload-file").change(function(){
			var ss = $("#J-upload-file").val().split(".");
			if(ss.length>1){
				var end = ss[ss.length-1];
				if(end!='csv'&&end!='txt'&&end!='CSV'&&end!='TXT'){
				alert("文件只支持csv或txt格式");
				return;
				}
			}		
			ajaxFileUpload();
		});
	});
	
	
})
	
	function checkAccunt(str){
		if(str.length < 4 || str.length > 16){
			return false;
		}else if((/^\d+$/g).test(str)){
			return false;
		}else if((/[^A-Za-z0-9\u4E00-\u9FA5]/g).test(str)){
			return false;
		}else {
			return true;
		}
	}
	function ajaxFileUpload() {  
		$.ajaxFileUpload(  
	        {  
	            url: baseUrl+'/globeAdmin/uploadWhiteListFile',  
	            secureuri: false,  
	            fileElementId: 'J-upload-file',  
	            dataType: false,  
	            beforeSend: function() {  
	            },  
	            complete: function(data) { 
	            },  
	            success: function(data, status) {
	            	$("#userAcuntP").val(data);
	            	$("#userAcuntP").focus();
	            },  
	            error: function(data, status, e) {  
	            }  
	        }  
	    )  
		return false;  
	} 
