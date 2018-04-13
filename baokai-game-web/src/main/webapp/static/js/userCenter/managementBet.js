$(document).ready(function(){
	var form = $('#J-form');
	$('#divDetailed').hide(); 
	
	//展开，收起
	$('#divExpansion').click(function(){		
		if($('#divExpansion').html()=="展开∨"){
			$('#divDetailed').show();
			$('#divExpansion').html("收起∧");
		}
		else{
			$('#divDetailed').hide();
			$('#divExpansion').html("展开∨");
		}	
	});

	//时间选择，隐藏域保存值,改变样式
	$(".time span").click(function () {		
		var txtday = $(this).attr("pro").trim();		
		$(".time span").removeClass().addClass("ico-tab");
        $(this).addClass("ico-tab ico-tab-current");		
		$("#time").attr("value", txtday);
		
	});

	//状态，隐藏域保存值,改变样式
	$(".state span").click(function () {		
		var txtstatus = $(this).attr("pro").trim();		
		$(".state span").removeClass().addClass("ico-tab");
        $(this).addClass("ico-tab ico-tab-current");		
		$("#status").attr("value", txtstatus);
	});				
	
	$('#orderCode').focus(function(){
		if($('#orderCode').val()=='如：ABC7779')
		
		{ $("#orderCode").val('');}
		
		}).blur(function(){
			var v = $.trim($(this).val());
			if(v == ''){
				$("#orderCode").val('如：ABC7779');
			}
	
	});
	
	//-----查询条件回显开始-----//
	
	if ($('#startTime').val() != '') {
		$('#J-date-start').val(formatDate(new Date(Number($('#startTime').val()) )));
	}
	
	if ($('#endTime').val() != '') {
		$('#J-date-end').val(formatDate(new Date(Number($('#endTime').val()))));
	}
	//状态回显
	var temp = $("#statusValue").val();
	$("#status").find("option[value=" + temp + "]").attr("selected", true);

	//彩种回显
	var temp = $("#lotteryIdValue").val();
	$("#lotteryId").find("option[value=" + temp + "]").attr("selected", true);
	
	//追号回显
	var temp = $("#parentTypeValue").val();
	$("#parentType").find("option[value=" + temp + "]").attr("selected", true);

	//包含下级回显
	

	if($('#containSubValue').val()==1){
		
		$('#containSub').attr('checked','checked');
	}else{
		$('#containSub').removeAttr('checked');
	}
	
	//方案编号回显
	
	
	if($('#orderCodeValue').val()!=''){
		$("#orderCode").val($('#orderCodeValue').val());
	}

	//-----查询条件回显结束-----//
	//表单提交校验
	$('#J-button-submit').click(function(){
		if ($('#J-date-start').val() != '') {
			var time = convertDate2UnixTimestamp($('#J-date-start').val());
			$('#startTime').val(time);
		}
		if ($('#J-date-end').val() != '') {
			var time = convertDate2UnixTimestamp($('#J-date-end').val());
			$('#endTime').val(time);
		}
		


	    if($("#containSub").is(':checked')){
	    	$('#containSubValue').val(1);
	    }else{
	    	$('#containSubValue').val(0);
	    }


		
		form.submit();
	});
	
	
	form.submit(function(e){
		//e.preventDefault();
		if($('#orderCode').val().trim()=='如：ABC7779'){$('#orderCode')[0].value='';}
		
	});
	

	//取消重置
	$('#restoreDefaults').click(function(e){
		e.preventDefault();
		$('#J-form').get(0).reset();
		//重置样式,值初始	
		$("#time").attr("value",7);
		$("#status").attr("value",0);	
		$(".time span").removeClass().addClass("ico-tab");
		$(".time span:eq(2)").addClass("ico-tab ico-tab-current");			
		$(".state span").removeClass().addClass("ico-tab");
		$(".state span:eq(0)").addClass("ico-tab ico-tab-current");		
	});
	
});