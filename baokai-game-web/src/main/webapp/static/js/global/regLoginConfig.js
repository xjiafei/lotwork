$(document).ready(function(){
	$('.menu-list li:eq(1)').attr("class","current");
	var form = $('#J-form'),
		button = $('#J-form-submit'),
		buttonRollback=$("#J-form-rollback"),
		h = $('#J-input-h'),
		s = $('#J-input-s'),
		fn;
	fn = function(e){
		var me = this,v = me.value,index;
		if($.trim(me.value) == ''){
			return;
		}
		me.value = v = v.replace(/^\.$/g, '');
		me.value = v = v.replace(/(.*)(\.)/g, '');
		me.value = v = v.replace(/[^\d|^\.]/g, '');
		me.value = v = v.replace(/^00/g, '0');
		if(v!="")
		{
		   me.value = v = parseInt(v);
		}
	};
	h.keyup(fn);
	s.keyup(fn);
	button.click(function(e){
		e.preventDefault();
		form.submit();
	});
	buttonRollback.click(function(e){
		e.preventDefault();
		form[0].reset();
	});
})