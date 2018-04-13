function doPre(){
	var currentPageNo = $("#pageNo").val();
	$("#pageNo").val(parseInt(currentPageNo)-1);
	$("#J-form").submit();
}

function doNext(){
	var currentPageNo = $("#pageNo").val();
	$("#pageNo").val(parseInt(currentPageNo)+1);
	$("#J-form").submit();
}

function doForward(index){
    if(index == -1){
    	var reg = /^[0-9]+$/;
    	if(reg.test($("#forwardPage").val())){
		$("#pageNo").val(parseInt($("#forwardPage").val()));}
    	else{
    		return;
    	}
    }else{ 
    	$("#pageNo").val(index);
    } 
	$("#J-form").submit();
}

function doCurrent(pageNo){
	$("#pageNo").val(pageNo);
	$("#J-form").submit();
}


$(document).ready(function(){
	var table = $('#J-table');
	$('.menu-list li:eq(6)').attr("class","current");
	table.on('click', '.ico-close', function(){
		var dom = $(this),par = dom.parent();
		par.find('.row-link-hidden').removeClass('row-link-hidden');
		dom.removeClass('ico-close').addClass('ico-open');
	});
	table.on('click', '.ico-open', function(){
		var dom = $(this),par = dom.parent(),as = par.find('a'),brs = par.find('br');
		as.each(function(i){
			if(i > 2){
				$(this).addClass('row-link-hidden');
			}
			if(i > 1 && i < brs.size()){
				$(brs[i]).addClass('row-link-hidden');
			}
		});
		dom.removeClass('ico-open').addClass('ico-close');
	});
	
	$('#J-button-submit').click(function(){
		$('#J-form').submit();
	});
		
})