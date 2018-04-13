$(document).ready(function(){
	$('.error_color').css('color','red');
	
	//檢查編號是否為數字
	$('#input-id').blur(function() {
		var str = $('#input-id').val().trim();
		var num = str.replace(/^0|\D/gi,'');
			if(num < 0 || num > 999){
				$('#error').css('display','inline');
				$('#input-id').val("");
			}else{
				$('#error').css('display','none');
				$('#input-id').val(num);
			}
	});
  
  
	$('#name').blur(function() {
		var str = $('#name').val().trim();
		var num = WidthCheck(str);
			if(num > 30){
				$('#error-3').css('display','inline');
			}else{
				$('#error-3').css('display','none');
			}
	});
	
	$('#vaild').blur(function() {
		var str = $('#vaild').val().trim();
		var num = str.replace(/\D/gi,'');
			if(num <= 0){
				$('#vaild').val("0");
			}else{
				$('#vaild').val(num);
			}
		
	});
	
	$('#freeze_time').blur(function() {
		var str = $('#freeze_time').val().trim();
		var num = str.replace(/^0|\D/gi,'');
			if(num <= 0){
				$('#freeze_time').val("0");
			}else{
				$('#freeze_time').val(num);
			}
		
	});
	
	$('#freeze').blur(function() {
		var str = $('#freeze').val().trim();
		var num = str.replace(/\D/gi,'');
			if(num <= 0){
				$('#freeze').val("0");
			}else{
				$('#freeze').val(num);
			}
		
	});
	
	$('#ip').blur(function() {
		var str = $('#ip').val().trim();
		var num = str.replace(/\D/gi,'');
			if(num <= 0){
				$('#ip').val("0");
			}else{
				$('#ip').val(num);
			}
		
	});
	
	$('#ip_time').blur(function() {
		var str = $('#ip_time').val().trim();
		var num = str.replace(/\D/gi,'');
			if(num <= 0){
				$('#ip_time').val("0");
			}else{
				$('#ip_time').val(num);
			}
		
	});
	
	$('#frequency_time').blur(function() {
		var str = $('#frequency_time').val().trim();
		var num = str.replace(/\D/gi,'');
			if(num <= 0){
				$('#frequency_time').val("0");
			}else{
				$('#frequency_time').val(num);
			}
		
	});
	
	$('#frequency').blur(function() {
		var str = $('#frequency').val().trim();
		var num = str.replace(/\D/gi,'');
			if(num <= 0){
				$('#frequency').val("0");
			}else{
				$('#frequency').val(num);
			}
		
	});
	
	var reset = $('#reset').val();
		if(reset == 1){
			$('#reset').attr('checked',true);
		}
  
	function WidthCheck(str){  
			var w = 0;  
			var tempCount = 0; 
			for (var i=0; i<str.length; i++) {  
			   var c = str.charCodeAt(i);  
			   //单字节加1  
			   if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
			    w++;  
			  
			   }else {     
			    w+=3;
			   }  
			 }
			return w;
	};  
	
	$('#J-form-1').on('keyup keypress', function(e) {
		var code = e.keyCode || e.which;
		if (code == 13) { 
			e.preventDefault();
		return false;
		}
	});


	$('#J-button-submit').click(function(){
		var submit = false;
		var str = $('#input-id').val().trim();
		var num = str.replace(/\D/gi,'');
			if(num < 0 || num > 999 || num == '' || num == null){
				$('#error').css('display','inline');
				$('#input-id').val("");
			}else{
				$('#error').css('display','none');
				$('#input-id').val(num);
				submit = true;
			}
		if(submit){
			$('#J-form-1').submit();
		}
	});
	
	
	$('#J-button-Pre').click(function(){
		var str = Number($('#id').val());
		if(str-1 == 0){
			$('#input-id').val("999");	
		}else{
			$('#input-id').val(str-1);
		}
		$('#J-form-1').submit();
	});
	
	$('#J-button-Next').click(function(){
		var str = Number($('#id').val());
		if(str+1 == 1000){
			$('#input-id').val("1");
		}else{
			$('#input-id').val(str+1);
		}
		$('#J-form-1').submit();
	});
	
	$('#J-button-submit-1').click(function(){
		if ( $('#reset').is( ":checked" ) ){
			$('#reset').val(1);
		}else{
			$('#reset').val(0);
		}
		$('#J-form').submit();
	});
	
	$('#J-button-cancel').click(function(){
		$('#input-id').val($('#id').val());	
		$('#J-form-1').submit();
	});
	
	var date = new Date($( '#J-date-end' ).val());
	if($( '#J-date-end' ).val() == "" || $( '#J-date-end' ).val() == null){
		$( '#J-date-end' ).val('');
	}else{
		date = date.getFullYear()+'-'+(date.getMonth() + 1) + '-' + (date.getDate() -1)
		$( '#J-date-end' ).val(date);
	}
	
	$('#J-button-select-submit').click(function(){
		$('#value-2').val($('#value-1').val());
		$('#form').submit();
	});
	
	var status = $('#status').val();
	if(status == 1){
		$('#DivSuccessful').css("display","inline");
		setTimeout(function(){ $('#DivSuccessful').css("display","none");},3000); 
	}if(status == 2){
		$('#DivFailed').css("display","inline");
		setTimeout(function(){ $('#DivFailed').css("display","none");},3000); 
	};
	
	$("th,td").css('text-align','center');
	$(".left").css('text-align','left');
	

	minWindow = new phoenix.MiniWindow({cls:'ui-alert-custom'}),
		mask = phoenix.Mask.getInstance();
	minWindow.dom.css({
		width:300
	});
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});
	
	$('.delete').click(function(e){
		var strArr = [];
		e.preventDefault();
		strArr.push('<div class="text">确定要删除日验证次数,累计验证总数,每日申请次数,累计申请次数？</div>');
		strArr.push('<div class="control"><a style="width:36%" id="sButton" data-buttontype="1" href="javascript:void(0);" class="btn">确定<b class="btn-inner"></b></a> <a data-buttontype="0" href="javascript:void(0);" style="width:36%" class="btn">取消<b class="btn-inner"></b></a></div>');
		minWindow.setContent(strArr.join(''));
		minWindow.show();
		$('#sButton').data("dir", $(this).attr("dir"));
	});
	$(document).on('click', '.btn', function(e){
		if(e.target.getAttribute('data-buttontype') == '1'){
			var id = $(this).data("dir");
			$.post(baseUrl+"/channel/deleteDay",{id:id},function(result){
				alert("删除成功");
				$('#form').submit();
			});
			minWindow.hide();
		}else{
			minWindow.hide();
		}
	});

});

	



