$(document).ready(function(){
    //弹出提示框
	$('#revSscheme').click(function(){	fn("divPrompt");});	
	$('.cancelPlanFuture').each(function(){
		$(this).click(function(){
			fn("divFuturePrompt");
			$('#issueCode').data('issueCode',$(this).attr('name'));
		});
		});	

	$('#divclose,#divCanceled,#divPromptFailuren2').click(function(){ 
		$('#divPrompt').css("display","none");
		$('#divPromptOk').css("display","none");
		location.href=location.href;
		
	});
	
	$('#divFutureClose,#divFutureCanceled,#divFuturePromptFailuren2').click(function(){ 
		$('#divFuturePrompt').css("display","none");
		$('#divFuturePromptOk').css("display","none");
		$('#divFuturePromptFailure').css("display","none");
		 location.href=location.href;
	});
  	//操作后提示	 
  	function fn(obj){
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";			
		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.body.scrollTop-40+"px";
    } 	
	 var isTure = true;
	//
	$('#J-Submit').click(function(){	
		$.ajax({
			type:'post',
			dataType:'json',
			contentType: "application/json",
			cache:false,
			url: baseUrl + "/gameUserCenter/stopPlan?userType=1&planId="+$('#planId').val(),	
			beforeSend:function(){				
				$('#revSscheme').attr('disabled',false);	
				if (isTure) {
                    $('#J-Submit').html('撤销中...');
                    $('#divCanceled').css('display', 'none').attr("disabled", "true");
                    $('#divclose').hide();
                    isTure = false;
                } else {
                    return false;
                }
			},
			success:function(json){				
			  if(json.status ==1)
			  {		
				 //alert(json);	     
				 $('#revSscheme').hide();
				 $('#revSchemeOk').show();	
				 fn("divPromptOk");				
				 $('#divPrompt').css("display","none");
				 //setTimeout("setFn('divPromptOk')",1500);			
				
			   } else if(json.status ==3){	
				fn("divPromptFailure3");						   
				$('#divPromptFailuren4,#divPromptFailuren5').click(function(){
					$('#divPrompt').css("display","none");
					$('#divPromptFailure').css("display","none");
					$('#divPromptFailure3').css("display","none");
					location.href=location.href;		
					});
			   } else{
				fn("divPromptFailure");				
				$('#divPromptFailuren1,#divPromptFailuren2').click(function(){
					$('#divPrompt').css("display","none");
					$('#divPromptFailure').css("display","none");
					$('#divPromptFailure3').css("display","none");
					location.href=location.href;
				});					
			   }            			
				
			},			  
			
			error:function(xhr, type){			
				fn("divPromptFailure");				
				$('#divPromptFailuren1,#divPromptFailuren2').click(function(){
					$('#divPrompt').css("display","none");
					$('#divPromptFailure').css("display","none");
					$('#divPromptFailure3').css("display","none");
					location.href=location.href;
				});	
			},
			complete:function(){
				 isTure = true;
		    }
	     });
	});

	//撤销预约
	$('#J-Submit-Future').click(function(){	
		var planId =  $('#planId').val();
		var issueCode = $('#issueCode').data('issueCode');
		var lotteryid = $('#lotteryid').val();
		$.ajax({
			type:'post',
			dataType:'json',
			contentType: "application/json",
			cache:false,
			url: baseUrl + "/gameUserCenter/reservationCalled?planId="+planId+"&issueCode="+issueCode+"&lotteryId="+lotteryid+"&userType=1",	
			success:function(json){				
			  if(json.status ==1)
			  {		
				 fn("divFuturePromptOk");				
				 $('#divFuturePrompt').css("display","none");
			   }else if(json.status == 0){
				   fn("divFuturePromptFailureIssue");				
					$('#divFuturePromptFailuren1Issue,#divFuturePromptFailuren2Issue').click(function(){
						$('#divFuturePrompt').css("display","none");
						$('#divFuturePromptFailureIssue').css("display","none");
						$('#divFuturePromptFailure3Issue').css("display","none");
					});
			   }else if(json.status == 3){
				   fn("divFuturePromptFailure3Issue");				
					$('#divFuturePromptFailuren4Issue,#divFuturePromptFailuren5Issue').click(function(){
						$('#divFuturePrompt').css("display","none");
						$('#divFuturePromptFailureIssue').css("display","none");
						$('#divFuturePromptFailure3Issue').css("display","none");
					});
			   }
			   else{
				fn("divFuturePromptFailure");				
				$('#divFuturePromptFailuren1,#divFuturePromptFailuren2').click(function(){
					$('#divFuturePrompt').css("display","none");
					$('#divFuturePromptFailure').css("display","none");
					$('#divFuturePromptFailure3Issue').css("display","none");
				});					
			   }            			
				
			},			  
			
			error:function(xhr, type){			
				fn("divFuturePromptFailure");				
				$('#divFuturePromptFailuren1,#divFuturePromptFailuren2').click(function(){
					$('#divFuturePrompt').css("display","none");
					$('#divFuturePromptFailure').css("display","none");
					$('#divFuturePromptFailure3Issue').css("display","none");
				});	
			},
	     });
	});	
	
  function setFn(obj){	$('#'+obj).css("display","none");  }	
});





  

