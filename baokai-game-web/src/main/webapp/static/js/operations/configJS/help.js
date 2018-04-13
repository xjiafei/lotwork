(function() {
	//菜单样式加载
	var inputTip = new phoenix.Tip.getInstance();
	$('body').on('mouseover', '.input-mark', function(){
		var dom = $(this),text = dom.attr('data-showtip');
		if(text){
			inputTip.setText(dom.attr('data-showtip'));
			inputTip.show(dom.outerWidth() + 4, dom.outerHeight()*-1, this);
		}
	}).on('mouseout', '.input-mark', function(){
		var text = this.getAttribute('data-showtip');
		if(text){
			inputTip.hide();
		}
	});
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#helpMenu').attr("class","current");
	
	//弹窗
	var minWindow,mask,initFn_modify,initFn_audit=false;	
		minWindow = new phoenix.MiniWindow();
		mask = phoenix.Mask.getInstance(),
		editConfirm = new phoenix.EditConfirm();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});
	
	//操作后提示	 
	function fnDiv(obj){		
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
	} 	
	//关闭弹窗
	$('#CloseDs,.close').click(function(e){
		$("#DivFailed").hide();
	});	
	function operationSuccess(){
		fnDiv('DivSuccessful');
		setTimeout(function (){$("#DivSuccessful").css("display","none");},1500);
		location.reload();
	}
	function operationFailure(){
		fnDiv('DivFailed');
		$(".close,.btn").click(function(e){
			$("#DivFailed").css("display","none");
		});	
	}
	
	var lotteryid = $('#lotteryid').val();
	var lotteryHelpDes;
	
	
	
	//-------------------------------------保存修改操作------------------------------
	$('#J-Modify-Butt1').click(function(e){	
		
		$("textarea", document.forms[0]).each(function(i,val){
			if(this.name == 'lotteryHelpDes'){
				lotteryHelpDes = this.value;
			}
		});
		
		var jsonStr = "";
		jsonStr += '{"lotteryid":';
		jsonStr += lotteryid;
		jsonStr += ',"lotteryHelpDes":';
		jsonStr += '"'+encodeURIComponent(encodeURIComponent(lotteryHelpDes))+'"';
		jsonStr += ',"betMethodHelpStruc":[';
		var arr = [];
		var aInputArray = $('textarea',document.forms[0]);
		
		$("textarea", document.forms[0]).each(function(i,val){
			
			if(this.name != 'lotteryHelpDes'){
				if(lotteryid != '99401'){
				if(i % 2 == 1){
					arr = this.name.split("_");
					jsonStr += '{"gameGroupCode":';
					jsonStr += arr[0];
					jsonStr += ',';
					jsonStr += '"gameSetCode":';
					jsonStr += arr[1];
					jsonStr += ',';
					jsonStr += '"betMethodCode":';
					jsonStr += arr[2];
					jsonStr += ',';
					jsonStr += '"gamePromptDes":';
					jsonStr += '"'+encodeURIComponent(encodeURIComponent(this.value))+'"';
				}else{
					jsonStr += '"gameExamplesDes":';
					jsonStr += '"'+encodeURIComponent(encodeURIComponent(this.value))+'"';
					jsonStr += '}';
				}}
				else{
					arr = this.name.split("_");
					jsonStr += '{"gameGroupCode":';
					jsonStr += arr[0];
					jsonStr += ',';
					jsonStr += '"gameSetCode":';
					jsonStr += arr[1];
					jsonStr += ',';
					jsonStr += '"betMethodCode":';
					jsonStr += arr[2];
					jsonStr += ',';
					jsonStr += '"gamePromptDes":';
					jsonStr += '"'+encodeURIComponent(encodeURIComponent(this.value))+'"';
					jsonStr += '}';
				}
				
				if(i != (aInputArray.size()-1)){
					jsonStr += ',';
				}
			}
			
		}); 
		jsonStr += ']}';
		
		//alert(jsonStr);
		
		//执行操作接口
		var url = currentContextPath + "/gameoa/modifyHelp";
		var data = jsonStr;
		var toUrl = currentContextPath + "/gameoa/toAuditHelp?lotteryid="+lotteryid;
		sendUrl(url, data, toUrl);
	});		
	
	//-------------------------------------审批通过操作tip------------------------------
	var inputTip = new phoenix.Tip.getInstance();
	$('body').on('mouseover', '.textarea-mark-cont', function(){
		var dom = $(this);
		inputTip.setText(dom.parent().find('.textarea-mark-old').html());
		inputTip.show(dom.width() + 12, 0, this);
	}).on('mouseout', '.textarea-mark-cont', function(){
		inputTip.hide();
	});

	
	//-------------------------------------审批通过操作-------------------------------------
	$('#J-Audit-Butt1').click(function(e){	
		editConfirm.isFlag = false;
		//执行操作接口
		var url = currentContextPath + "/gameoa/auditHelp";
		var jsonStr = "";
		jsonStr += '{"lotteryid":';
		jsonStr += lotteryid;
		jsonStr += ',"auditType":1';
		jsonStr += '}';
		var data = jsonStr;
		var toUrl = currentContextPath + "/gameoa/toHelp?lotteryid="+lotteryid;
		sendUrl(url, data, toUrl);	
	});	
	
	//-------------------------------------审批不通过操作-------------------------------------
	$('#J-Audit-Butt2').click(function(e){	
		editConfirm.isFlag = false;
		//执行操作接口
		var url = currentContextPath + "/gameoa/auditHelp";
		var jsonStr = "";
		jsonStr += '{"lotteryid":';
		jsonStr += lotteryid;
		jsonStr += ',"auditType":2';
		jsonStr += '}';
		var data = jsonStr;
		var toUrl = currentContextPath + "/gameoa/toHelp?lotteryid="+lotteryid;
		sendUrl(url, data, toUrl);	
	});
    //-------------------------------------审批通过操作-------------------------------------
    $('#J-Publish-Butt1').click(function(e){
        editConfirm.isFlag = false;
        //执行操作接口
        var url = currentContextPath + "/gameoa/publishHelp";
        var jsonStr = "";
        jsonStr += '{"lotteryid":';
        jsonStr += lotteryid;
        jsonStr += ',"auditType":1';
        jsonStr += '}';
        var data = jsonStr;
        var toUrl = currentContextPath + "/gameoa/toHelp?lotteryid="+lotteryid;
        sendUrl(url, data, toUrl);
    });

    //-------------------------------------审批不通过操作-------------------------------------
    $('#J-Publish-Butt2').click(function(e){
        editConfirm.isFlag = false;
        //执行操作接口
        var url = currentContextPath + "/gameoa/publishHelp";
        var jsonStr = "";
        jsonStr += '{"lotteryid":';
        jsonStr += lotteryid;
        jsonStr += ',"auditType":2';
        jsonStr += '}';
        var data = jsonStr;
        var toUrl = currentContextPath + "/gameoa/toHelp?lotteryid="+lotteryid;
        sendUrl(url, data, toUrl);
    });
    //审核跳转
	$('#_auditHelpBbutton').click(function(){
		editConfirm.isFlag = false;
		location.href = currentContextPath + '/gameoa/toAuditHelpDetail?lotteryid=' + lotteryid;			
	});
	
	//修改跳转
	$('#_updateHelpBbutton').click(function(){
		editConfirm.isFlag = false;
		location.href = currentContextPath + '/gameoa/toModifyHelp?lotteryid=' + lotteryid;				
	});
	
	function sendUrl(url,data,toUrl){
		editConfirm.isFlag = false;
		jQuery.ajax({
			type:  "post",
			url: url,
			dataType:'json', 
			contentType: "application/json; charset=utf-8",
			data: data,
			cache: false,
			success:function(data){
				if(data.status==1){
					fnDiv('DivSuccessful');
		            setTimeout(function (){$("#DivSuccessful").css("display","none");location.href = toUrl;},3000);
				}else{
					operationFailure();
				}
			},
			error: function(){
				operationFailure();
			}
		});
	}
	
})();