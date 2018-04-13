function orderBy()
{
	var order = $('#orderBy').val();
	if(order=='ID')
		{
		$('#orderBy').val('ID DESC');
		}
	else
		{
		$('#orderBy').val('ID');
		}
	
	$('#J-form').submit();
}

function changeStatus(status)
{
	$('#status').val(status);
	$('#J-form').submit();
}

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
	$('.menu-list li:eq(6)').attr("class","current");
	$('.col-side .nav dd:eq(3)').attr("class","current");
	$('.ico-preview').click(function(){
		var wd = window.open('');
		wd.document.write($(this).attr('data-content').replace(/\\/g,""));
	});
	
	
	
	var table = $('#J-table');
	//弹窗
	var Wd = phoenix.Message.getInstance(),
		Tip = phoenix.Tip.getInstance();
	
	$('#J-button-search').click(function(){
		$('#J-form').submit();
	});
	
	
	//全选
	$('#J-select-all').click(function(){
		var inputs = $('#J-table').find('input[type="checkbox"]');
		if(this.checked){
			inputs.each(function(){
				this.checked = true;
			});
		}else{
			inputs.each(function(){
				this.checked = false;
			});
		}
	});
	
	//批量删除
	$('#J-button-passall').click(function(){
		var inputs = $('#J-table').find('input[type="checkbox"]'),result = [];
		inputs.each(function(){
			if(this.checked){
				result.push(this.value);
			}
		});
		if(result.length < 1){
			return;
		}
		Wd.show({
			mask:true,
			title:'温馨提示',
			content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您选择了 '+ result.length +' 条信息，请确认删除？</h4></div></div>',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$.ajax({
					url:baseUrl+'/adAdmin/deleteNotice?ids=' + result,
					dataType:'json',
					method:'post',
					success:function(data){
						if(Number(data['status']) == 1){
							Wd.hide();
							location.href = baseUrl+'/adAdmin/goNoticeListPublish';
						}else{
							alert('msg');
							Wd.hide();
						}
					}
				});
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
	});
	
	//批量提交审核
	$('#J-button-submitall').click(function(){
		var inputs = $('#J-table').find('input[type="checkbox"]'),result = [];
		inputs.each(function(){
			if(this.checked){
				result.push(this.value);
			}
		});
		if(result.length < 1){
			return;
		}
		Wd.show({
			mask:true,
			title:'温馨提示',
			content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您选择了 '+ result.length +' 条信息，请确认提交审核？</h4></div></div>',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$.ajax({
					url:baseUrl+'/adAdmin/applyNotice?ids=' + result,
					dataType:'json',
					method:'post',
					success:function(data){
						if(Number(data['status']) == 1){
							Wd.hide();
							location.href = baseUrl+'/adAdmin/goNoticeListPublish';
						}else{
							alert('msg');
							Wd.hide();
						}
					}
				});
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
	});

	//列表删除单行
	table.on('click', '.row-delete', function(){
		var id = Number($(this).attr('data-id'));
		Wd.show({
			mask:true,
			title:'提示',
			content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">你确定要删除该条信息？</h4></div></div>',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$.ajax({
					url:baseUrl+'/adAdmin/deleteNotice?ids=' + id,
					dataType:'json',
					success:function(data){
						if(Number(data['status']) == 1){
							location.href = baseUrl+'/adAdmin/goNoticeListPublish';
						}else{
							alert('msg');
						}
					}
				});
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
	});

	
	//未通过
	table.find('.row-show-pass').each(function(){
		var dom = $(this);
		var memo = dom.attr('data-memo');
		var strArr = [];
		strArr.push('<div>原因：</div>');
		strArr.push('<div>');
		strArr.push(memo);
		strArr.push('</div>');
		dom.find('.row-show-panel').html(strArr.join(''));
		var hd = new phoenix.Hover({par:dom,triggers:dom.find('.row-show-tigger'),panels:'.row-show-panel',currPanelClass:'row-show-panel-current',hoverDelayOut:300,hoverIsBindPanels:true});
		//hd.getPanel(0).css({left:hd.getPanel(0).outerWidth()*-1 + 10,top:hd.getPanel(0).outerHeight()/2*-1});
	});	
})
