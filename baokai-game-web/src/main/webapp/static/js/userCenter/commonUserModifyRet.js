$(document).ready(function() {
	// 弹窗
	var minWindow,mask,initFn_modify,initFn_audit,isture=false;	
	minWindow = new phoenix.MiniWindow();
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});
	
	var formatMoney = function(num){
		var num = Number(num),
			re = /(-?\d+)(\d{3})/;	
		if(Number.prototype.toFixed){
			num = (num).toFixed(2);
		}else{
			num = Math.round(num*100)/100
		}
		num  =  '' + num;
		while(re.test(num)){
			num = num.replace(re,"$1,$2");
		}
		return num;  
	};
	// 数字校验，自动矫正不符合数学规范的数学
	var inputs = $('.table .input'),checkFn;				
	checkFn = function(){
		var v = this.value.replace(/\D/g, '');
		this.value = v;
	};
//	inputs.keyup(checkFn);
//	inputs.blur(checkFn);
	
	// 操作后提示
	function fnDiv(obj){		
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
	} 	
	// 检索文本框是否为空
	function CheckFron(e){
//		alert(e);
//		alert($(this).find(':text').size());
		$(this).find(':text').each( function () { 
//			alert(this);
				if($(this).val() == ""){
					var obj=this;
					this.focus(); 					
					isture=false;	
					// 您还有未填内容,提示弹层，再文本定位到空文本处.
					minWindow.setTitle('温馨提示');
					minWindow.setContent($('#DivUnfillContent').val());
					minWindow.show();	
					$('.j-ui-miniwindow').addClass("pop");// 增加图标
					$('#CloseDf').click(function(e){
						minWindow.hide();
						obj.focus(); 
						window.location.hash = "#"+this;
					});		
					return false;						
				}		
				else{ isture = true;}		
		});	
	}
	// 关闭弹窗
	$('#CloseDs,.close').click(function(e){
		$("#DivFailed").hide();
	});	
	function operationSuccess(){
		fnDiv('DivSuccessful');
		setTimeout(function (){$("#DivSuccessful").css("display","none");},1500);
		location.reload();
	}
	function operationFailure(){
		fnDiv("divPromptFailure");				
		$('#divPromptFailuren1,#divPromptFailuren2').click(function(){
			$('#divPrompt').css("display","none");
			$('#divPromptFailure').css("display","none");
		});	
	}
	
	// 弹出提示框
//	$('#proxy').click(function(){	fnDiv("divPrompt");});	
	
	$('#divclose,#divCanceled').click(function(){ $('#divPrompt').css("display","none");});
	
	$('#J-Submit').click(function(){
		$('#divPrompt').css("display","none");
//		window.location = baseUrl + "/gameUserCenter/queryBonusDetailsForModifyRetProxy";
	});
	
	
	
	//radio change event
//	$(":radio").change(function () {
//		var $currentTable = $(this).parent().parent().parent().parent().get(0);
//		var $currentDl = $(this).parent().parent().parent().parent().parent().parent().get(0);
//		$(':text',$currentDl).each(function() {
//			$(this).attr('disabled',true);
//		});
//		
//		$(':text',$currentTable).each(function() {
//			$(this).removeAttr('disabled');
//		});
//    })
    var allRadioStatusChange = function() {
    	$("input[type='radio']").each(function(){
    		var $checkedId = $(this).get(0).id;
    		  if ( $(this).is(':checked') ) {
    			$("input[parentId="+$checkedId+"-1]").removeAttr("disabled");
    			$("input[parentId="+$checkedId+"-2]").removeAttr("disabled");
    		  } else {
    			$("input[parentId="+$checkedId+"-1]").attr("disabled", true);
    			$("input[parentId="+$checkedId+"-2]").attr("disabled", true);
    		  }
    	});
    }
    allRadioStatusChange();
    
    $("input[type='radio']").change(function(){
		var $checkedId = $(this).get(0).id;
		  if ( $(this).is(':checked') ) {
			$("input[parentId="+$checkedId+"-1]").removeAttr("disabled");
			$("input[parentId="+$checkedId+"-2]").removeAttr("disabled");
		  } else {
			$("input[parentId="+$checkedId+"-1]").attr("disabled", true);
			$("input[parentId="+$checkedId+"-2]").attr("disabled", true);
			
		  }
		  allRadioStatusChange();
	});
	
    // 输入验证
	$('input:text[parentId]').each(function(){
		$(this).blur(function(){
			if ($(this).val() > $(this).attr("max")) {
				alert("返点设置超出范围，请重新设置");
				$(this).focus();
			} else if ($(this).val() < $(this).attr("min")) {
				alert("返点不能小于已设置值，请重新设置");
				$(this).focus();
			} else if ($(this).val() == "") {
				alert("返点设置不能为空，请重新设置");
				$(this).focus();
			}
		});
	});
    
    //modify the data
	$('#J-Submit-Butt1').click(function(){		
		var jsonStr = "";
		jsonStr += '{"userid":';
		jsonStr += $('#userid').val();
		jsonStr += ',"userAwardListStruc":[';
		var checkedArr = $(':checked');
		var aInputArray = $('table');
		var count = 0;
		aInputArray.each(function(i,v){
			if ($(this).find(':checked').length == 1 ) {
				count++;
				jsonStr += '{"awardName":';
				jsonStr += '"'+$(this).find('#awardName').val()+'"';
				jsonStr += ',';
				jsonStr += '"lotterySeriesCode":';
				jsonStr += $(this).find('#lotterySeriesCode').val();
				jsonStr += ',';
				jsonStr += '"directRet":';
				jsonStr += $(this).find('#directRet').val()*10;
				jsonStr += ',';
				jsonStr += '"awardGroupId":';
				jsonStr += $(this).find('#awardGroupId').val();
				jsonStr += ',';
				jsonStr += '"lotterySeriesName":';
				jsonStr += '"'+$(this).find('#lotterySeriesName').val()+'"';
				jsonStr += ',';
				jsonStr += '"status":';
				jsonStr += '1';
				jsonStr += ',';
				jsonStr += '"directLimitRet":';
				jsonStr += $(this).find('#directLimitRet').val();
				jsonStr += ',';
				jsonStr += '"threeLimitRet":';
				jsonStr += $(this).find('#threeLimitRet').val();
				jsonStr += ',';
				jsonStr += '"threeoneRet":';
				jsonStr += $(this).find('#threeoneRet').val()*10;
				jsonStr += '}';
				
				if(count != (checkedArr.size())){
					jsonStr += ',';
				}
			};
			}); 
		jsonStr += ']}';
//		alert(jsonStr);
		
		// 执行操作接口
		var url = baseUrl + "/gameUserCenter/commonUserModifyRet";
		$.ajax({
			type:  "post",
			url: url,
			dataType:'json', 
			contentType: "application/json; charset=utf-8",
			data: jsonStr,
			
			success:function(data){
				operationSuccess();
			},
			error: function(xhr, status, error) {
					operationFailure();
				 /*
					 * var err = eval("(" + xhr.responseText + ")");
					 * alert(err.Message);
					 */
				}
		});
		
		
		
	});// end of Submit button click
	
	
	
});