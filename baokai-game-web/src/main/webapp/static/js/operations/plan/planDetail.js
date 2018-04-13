$(document).ready(function() {
	//弹窗
	var Wd = phoenix.Message.getInstance(),table = $('#J-table'),panels = table.find('.panel-lotterys-inner'),panelHeight = 144;
	
	var planid = $('#planid').val();
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(5)').attr("class","current");
	$('.cancelPlanFuture').each(function(){
		$(this).click(function(){
			fn("divFuturePrompt");
			$('#issueCode').data('issueCode',$(this).attr('name'));
		});
		});	

	$('#divclose,#divCanceled,#divPromptFailuren2').click(function(){ 
		$('#divPrompt').css("display","none");
		$('#divPromptOk').css("display","none");
	});
	
	$('#divFutureClose,#divFutureCanceled,#divFuturePromptFailuren2').click(function(){ 
		$('#divFuturePrompt').css("display","none");
		$('#divFuturePromptOk').css("display","none");
		$('#divFuturePromptFailure').css("display","none");
	});
	
  	function fn(obj){
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";			
		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
    } 
	
	
	
	$('#J-button-stop').click(function(){
		var id = this.getAttribute('data-id');
		Wd.show({
			mask:true,
			title:'温馨提示',
			content:'<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您确定要终止追号吗？</h4></div>',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				Wd.hide();
				
				//方案记录>撤销方案
				var text = "追号记录>终止追号";
				
				var url = baseUrl+"/gameUserCenter/stopPlan?planId="+planid+"&userType=2";
				var data = '';
				sendUrl(url, data);
			},
			cancelFun:function(){
				Wd.hide();
				
				
			}
		});
	});
	
	//撤销预约
	$('#J-Submit-Future').click(function(){	
		var issueCode = $('#issueCode').data('issueCode');
		$.ajax({
			type:'post',
			dataType:'json',
			contentType: "application/json",
			cache:false,
			url: baseUrl + "/gameUserCenter/reservationCalled?planId="+planid+"&issueCode="+issueCode+"&userType=2&lotteryId="+lotteryid,	
			success:function(json){				
			  if(json.status ==1)
			  {		
				 fn("divFuturePromptOk");				
				 $('#divFuturePrompt').css("display","none");
				 setTimeout("setFn('divFuturePromptOk')",1500);		
				 location.href=location.href;
			   }
			   else{
				fn("divFuturePromptFailure");				
				$('#divFuturePromptFailuren1,#divFuturePromptFailuren2').click(function(){
					$('#divFuturePrompt').css("display","none");
					$('#divFuturePromptFailure').css("display","none");
				});					
			   }            			
				
			},			  
			
			error:function(xhr, type){			
				fn("divFuturePromptFailure");				
				$('#divFuturePromptFailuren1,#divFuturePromptFailuren2').click(function(){
					$('#divFuturePrompt').css("display","none");
					$('#divFuturePromptFailure').css("display","none");
				});	
			},
	     });
	});	
	
  function setFn(obj){	$('#'+obj).css("display","none");  }
	
	$('#J-table').on('click', '.ico-view-more', function(){
		var dom = $(this),panel = dom.parent().find('.panel-lotterys');
		if(panel.height() > panelHeight){
			panel.css({height:panelHeight});
			dom.find('i').removeClass('ico-open').addClass('ico-close');
		}else{
			panel.css({height:'auto'});
			dom.find('i').removeClass('ico-close').addClass('ico-open');
		}
	});
	
	panels.each(function(){
		var dom = $(this);
		if(dom.height() > panelHeight){
			dom.parent().parent().find('.ico-view-more').show();
		}
	});
	
	function sendUrl(url, data){
		jQuery.ajax({
			type:  "post",
			url: url,
			dataType:'json', 
			contentType: "application/json; charset=utf-8",
			data: data,
			success:function(data){
				Wd.show({
					mask:true,
					content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+ data.message +'</h4></div></div>',
					cancelIsShow:true,
					cancelText:'关闭',
					cancelFun:function(){
						Wd.hide();
						window.location.reload(); 
					}
				});
			},
			error: function(){
				alert("error");
			}
		});
	}
	
});
