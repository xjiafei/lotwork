(function(){
	$('#headerLink').find('a').bind('click',function(){
		var url = $(this).attr('url');
		var form = $('#headerLinkForm');
		form.attr('action',url);
		form.submit();
	});
})();