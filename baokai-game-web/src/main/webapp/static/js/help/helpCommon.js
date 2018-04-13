//针对标题添加ajax校验，避免重复的title
function checkTitleBlur(obj)
{
	var title =$(obj).val();
	if(title==''){
		return;
	}
	var titObj =$(obj);
	var url = baseUrl + "/helpAdmin/checkHelpTitle";
	jQuery.ajax({
		type: "post",
		url: url,
		dataType:'json',
		data:'title='+title,
		success: function(data,textStatus){
			if(data.status == 0){
				titObj.val('').focus();
				setMessage(titObj, '已经存在名称为【'+title+'】的标题，请重新输入！');
			}else{
				hideMessage(titObj, '已经存在名称为【'+title+'】的标题，请重新输入！');
			}
		},
		error: function(xhr,status,errMsg){
		alert("操作失败!");
		}
		});
}
 function setMessage(dom, msg){
	dom.parent().find('.ui-check').html('<i></i>' + msg).show();
};
 function hideMessage(dom){
	dom.parent().find('.ui-check').hide();
};