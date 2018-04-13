$(document).ready(function(){
	$('.menu-list li:eq(6)').attr("class","current");
	$('.col-side .nav dd:eq(2)').attr("class","current");
	var st1 = new SortableTable(document.getElementById("J-table"),
			["None", "None", "None", "None", "None", "None", "Number"]);
	
	var table = $('#J-table');
	//弹窗
	var Wd = phoenix.Message.getInstance(),
		Tip = phoenix.Tip.getInstance();
	
	//缩略图
	table.on('mouseenter', '.row-img-small', function(){
		var dom = $(this),src = dom.attr('data-src-big'),w = dom.attr('data-width'),h = dom.attr('data-height');
		Tip.setText('<img width="400" src="'+ src +'" /><div style="text-align:center;font-size:12px;color:#333;line-height:180%;">尺寸：'+ w +' * '+ h +'</div>');
		Tip.show(dom.width()+5, -4, this);
	}).on('mouseleave', '.row-img-small', function(){
		Tip.hide();
	});
	
	table.on('keyup', '.input-num', function(){
		this.value = this.value.replace(/[^\d]/g, '');
	});
	
	$('#J-button-submit').click(function(){
		var vs = [];
		var resutlStr=''
		table.find('input[type="checkbox"]').each(function(){
				var index = $(this).attr("tr_index");
				if(this.checked){
					resutlStr+='{isShown:1,';
				}else{
					resutlStr+='{isShown:0,';
				}
				resutlStr+='adSpaceId:'+$("#adSpaceId").val()+',';
				resutlStr+='advertId:'+$("#adId_"+index).val()+',';
				resutlStr+='orders:'+$("#orders_"+index).val()+'},';
			});
		
		$("[name='tr_index']").each(function(){
			var index =$(this).val();
			
		})
		resutlStr = resutlStr.substring(0,resutlStr.length-1);
		resutlStr+='';
		resutlStr="unbingList="+resutlStr;
		$.ajax({
			url:baseUrl+'/adAdmin/updateUnbingAdvert',
			type: "POST",					
			dataType:'json',
			data:resutlStr,
			success:function(data){
				if(Number(data['status']) == 1){
						Wd.show({
							mask:true,
							title:'提示',
							confirmText:'确定',
							cancelText:'关闭',
							content:'<div class="bd text-center"><div class="pop-title"><i class="ico-success"></i><h4 class="pop-text">操作成功</h4></div></div>',
							confirmIsShow:true,
							cancelIsShow:true,
							confirmFun:function(){
								Wd.hide();
								location.href = location.href;
							},
							cancelFun:function(){
								Wd.hide();
								location.href = location.href;
							},
							callback:function(){
							}
						});
				}else{
					alert(data['msg']);
				}
			}
		});
	});
})