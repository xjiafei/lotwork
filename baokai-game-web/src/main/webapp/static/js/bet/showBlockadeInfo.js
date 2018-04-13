$(document).ready(function(){
	$("#pointsTable tr").each(function(){
		var ids = $(this).attr("class");
		var colspanNum=$("."+ids).size();
		 $(".td_"+ids).first().attr("rowspan",colspanNum);
		$(".td_"+ids).each(function(index ){
			if(index!=0){
				$(this).remove();
			}
		});
	})
})