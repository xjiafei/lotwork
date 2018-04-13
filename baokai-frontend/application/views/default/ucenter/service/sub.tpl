<!DOCTYPE HTML>
<html lang="UTF-8">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/service/service.css" />
	
	 <link rel="stylesheet" href="{$path_img}/css/jquery-ui.css">
	{include file='/default/script-base.tpl'}
	

<body>

<!-- //////////////头部公共页面////////////// -->
{include file='/default/ucenter/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="g_33 common-main">
		<div class="g_5">
		<!-- //////////////左侧公共页面////////////// -->
			{include file='/default/ucenter/left.tpl'}
		<!-- /////////////左侧公共页面/////////////// -->			
		</div>
		<div class="g_28 g_last">
			<div class="common-article">
				<div class="title">
					<div class="more">
						{if $smarty.session.datas.info.userLvl neq 0}
						<a href="/Service/servicesup?unread={$unread}">给上级发信</a>
						{/if}
						{if $smarty.session.datas.info.userLvl >= 0}
						<a href="/Service/servicesub?unread={$unread}" class="current">给下级发信</a>
						{/if}
					</div>	
					站内信(<span id="msgcount2">0</span>)
				</div>
				<div class="content">
					<div class="ui-tab">
					
						<div class="ui-tab-content" >
							<ul class="ui-form form-mail">
								<li>
								<div style="" id="IDivAdd2">
									<label class="ui-label" for="">收件人：</label>
									<a href="javascript:void(0);" class="btn" id="ShowDiv">点击添加收件人<b class="btn-inner"></b></a><--<div class="ui-text-prompt">每次最多可发送20人</div>								
									<div class="ui-text-prompt" id="error0"></div>														-->	
									<div class="ui-check" id="error1"><i class="error"></i>请选择收件人</div>
									</div>
								</li>
								<li >
									<div id="IDivAdd">  
										<label class="ui-label" for="">收件人：</label>
										<div class="textarea" id="Divsadduser" style="overflow: auto;" >											
											<a  class="btn btn-small" id="ContinueAdd" >继续添加<b class="btn-inner"></b></a>											
										</div>
																				
										<div class="ui-check" id="error3"><i class="error"></i>请选择发送人</div>
									<div>
								</li>
								<li>									
									<label class="ui-label" for="title">主题：</label>
									<input type="text" name="title" class="input w-5" id="Txttitle"  >											
									<div class="ui-text-prompt">主题长度不得超过30字符</div>									
									<div class="ui-check"><i class="error"></i>请输入主题,长度不得超过30字符</div>
								</li>
								<li>
									<label class="ui-label" for="text">正文：</label>							
									<textarea name="content"  class="textarea" id="TxtText" style=" width:420px;height:150px"></textarea>									
									<div class="ui-text-prompt">正文长度不得超过300字符</div>															
									<div class="ui-check"><i class="error"></i>请输入正文,长度不得超过300字符</div>
								</li>
								<li class="ui-btn">
									<!-- <a href="javascript:void(0);" class="btn btn-important">发 送<b class="btn-inner"></b></a> -->
									
							        <a href="javascript:void(0);" class="btn btn-disabled" id="subSendDisabled">发 送<b class="btn-inner"></b></a>
									<a href="javascript:void(0);" class="btn btn-important" style="display:none" id="subSend">发 送<b class="btn-inner"></b></a>
									<a href="javascript:void(0);" class="btn" id="CancelRrturn">取 消<b class="btn-inner"></b></a>
								</li>
							</ul>
						</div>
					</div>
				
				</div>
			</div>
		</div>
	</div>
	<!-- //////////////底侧公共页面////////////// -->
	{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
	<div style="position:absolute;z-index:2; display:none" class="pop pop-success w-5" id="DdivAddOk">
		<i class="ico-success"></i>
		<h4 class="pop-text">添加成功</h4>
	</div>
	<div style="position:absolute;z-index:2; display:none" class="pop pop-success w-5" id="divSendOk">
		<i class="ico-success"></i>
		<h4 class="pop-text">发送成功</h4>
	</div>
	<div style="position:absolute;z-index:2; display:none" class="pop pop-success w-5" id="DdivOK">
		<i class="ico-success"></i>
		<h4 class="pop-text">删除成功</h4>
	</div>
	
	<div style="position:absolute;z-index:2; display:none" class="pop pop-success w-5" id="Dsendno">
		<i class="ico-error"></i>
		<h4 class="pop-text">发送失败，请重试</h4>
	</div>
	<div style="position:absolute;z-index:2; display:none" id="DivDeleteno"  class="pop pop-error w-5">
		<i class="ico-error"></i>
		<h4 class="pop-text">删除失败，请重试</h4>
	</div>
	<div style="position:absolute;z-index:2; display:none" id="Divcus"  class="pop pop-error w-5">
		<i class="ico-error"></i>
		<h4 class="pop-text"></h4>
	</div>
    <div style="position:absolute;z-index:10000; display:none" id="DivMaxpserson"  class="pop pop-error w-5">
		<i class="ico-error"></i>
		<h4 class="pop-text">每次最多可发送20人</h4>
	</div>
	
	<div class="pop pop-search" id="Idivs" style="position:absolute;z-index:2; display:none">  
		<div class="hd"><i class="close" id="CloseDiv"></i>添加收件人</div>							
		
		<div class="bd">
			<div class="search-form">
				<label class="ui-label" autocomplete>搜索用户：</label>
				<input type="text" class="input w-4" id="titletext" >
				<a href="javascript:void(0);" class="btn" id="Search">搜 索<b class="btn-inner"></b></a>
			</div>
			<div class="search-textarea" id="searchUser">			

			<!-- <label class="label"><input type="checkbox" class="checkbox" />1</label> -->				
			</div>
			<div class="pop-btn " id="ImgRadio">
				<label class="ui-label"><input type="radio" class="radio" id="allrad"/>全选</label>
				<label class="ui-label"><input type="radio" class="radio" id="norad"/>反选</label>
				<a href="javascript:void(0);" class="btn" id="AddUser">添 加<b class="btn-inner"></b></a>
			</div>
		</div>
	</div>
<script  src="{$path_js}/js/phoenix.Common.js"></script>
<script type="text/javascript" src="{$path_js}/js/phoenix.Verification.js"></script>
{literal}
<script >   
//全局调用(保存选中状态)
var userCount,nowUserCount,idStr; 		
(function($){	
	//验证标题与正文
	var Txttitle,TxttitlePar,TxtText,TxtTextPar,
	//表单检测错误数量
		errorTypes = ['Txttitle','TxtText'],
		errorHas = {},
		setErrorNum = function(name, num){
		if(typeof errorHas[name] != 'undefined'){
			errorHas[name] += num;
			errorHas[name] = errorHas[name] < 0 ? 0 : (errorHas[name] > 1 ? 1 : errorHas[name]);
			}
		};
		$.each(errorTypes, function(){
			errorHas[this] = 0;
		});
		var checkContent = function(){
			var v = $.trim($('#Txttitle').val());
			if(v == ''){
				setErrorNum('Txttitle', 1);
			}else{
				setErrorNum('Txttitle', -1);
			}
			var v2 =$.trim($("#TxtText").val());
			if(v2 == '')
			{
				setErrorNum('TxtText', 1);
			}else{
				setErrorNum('TxtText', -1);
			}
		};
		var checkForm = function(){
			checkContent();
			var err = 0;		
			$.each(errorTypes, function(){
			if(typeof errorHas[this] != 'undefined'){
				err += errorHas[this];
			}
			});
		    if(err > 0){ 	
			  $('#subSend').hide();
			  $('#subSendDisabled').show();
			}else
			{
			  $('#subSend').show();
			  $('#subSendDisabled').hide();
			}
		};
		checkForm();	
		//标题验证
		Txttitle = $('#Txttitle');
		TxttitlePar = Txttitle.parent();	
		Txttitle.blur(function(){
			var v = $.trim(this.value);
			v = v.replace(/[^\x00-\xff]/g, 'xx');		
			if(v == '' || WidthCheck(v)>30){			
				TxttitlePar.find('.ui-check').css('display', 'inline');	
				TxttitlePar.find('.ui-text-prompt').css('display', 'none');
				setErrorNum('Txttitle', 1);
			}else{				
				TxttitlePar.find('.ui-check').css('display', 'none');
				setErrorNum('Txttitle', -1);
			}
			checkForm();			
		}).focus(function(){
			TxttitlePar.find('.ui-check').css('display', 'none');
			TxttitlePar.find('.ui-text-prompt').css('display', 'inline');
			checkForm();
		});
		
		//正文验证
		TxtText = $('#TxtText');
		TxtTextPar = TxtText.parent();	
		TxtText.blur(function(){
			var v = $.trim(this.value);
			v = v.replace(/[^\x00-\xff]/g, 'xx');		
			if(v == '' || WidthCheck(v)>300){		
				TxtTextPar.find('.ui-check').css('display', 'inline');	
				TxtTextPar.find('.ui-text-prompt').css('display', 'none');
				setErrorNum('TxtText', 1);
			}else{				
				TxtTextPar.find('.ui-check').css('display', 'none');
				setErrorNum('TxtText', -1);
			}			
			checkForm();
		}).focus(function(){
			TxtTextPar.find('.ui-check').css('display', 'none');
			TxtTextPar.find('.ui-text-prompt').css('display', 'inline');
			checkForm();
		});	
	
   	//弹遮挡层控制
	var htmls="",box = new LightBox("Idivs"),box1=new LightBox("divSendOk"),box2=new LightBox("DivDeleteno"),box3=new LightBox("DdivAddOk"),box4=new LightBox("Divcus");		
	var WidthCheck=function(str){  
		var w = 0;  
		var tempCount = 0; 
		for (var i=0; i<str.length; i++) {  
		   var c = str.charCodeAt(i);  
		   //单字节加1  
		   if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
			w++;  
		  
		   }else {     
			w+=2;
		   }  
		 }
		return w;
	};  
		
	$('#ShowDiv').click(function(){	  OpenFrom(); });			
	//全选 
	$('#allrad').click(function(){	
		$('[name=items]:checkbox').each(function () {	this.checked = true;  });
		$(":radio[id='norad']").attr("checked", false);    
    });   
	$('#norad').click(function () {	 	
		$(":radio[id='allrad']").attr("checked", false);
		$('[name=items]:checkbox').each(function () {	this.checked = !this.checked;   });
	});      
    $('#CloseDiv').click(function (){	box.Close();});	
	$('#IDivAdd').parent().css('display','none');
	//添加收件人（去重复）
	nowUserCount=new Array();	
	ids=new Array();
	$('#AddUser').click(function (obj){  	
		var istrue=true;		
		var ar1=[],ar2=[];
		//if(($('input[name="items"]:checked').length +nowUserCount.length) <= 20)
		//{
			$('input[name="items"]:checked').each(function(){  
				if(jQuery.inArray($(this).val(),nowUserCount)==-1){
					nowUserCount.push($(this).val()); ar1.push($(this).val()); 	
					ids.push($(this).attr("pro"));ar2.push($(this).attr("pro"));
				}
			})   		
		//}else
		//{
		//	 fn("DivMaxpserson");	
		//     setTimeout(function(){$("#DivMaxpserson").css("display","none");},1000);
		//	 return;
		//}
		if(nowUserCount.length ==0 || ids.length ==0){ istrue=false;}
		/*if(nowUserCount.length >20 )
		{ 
		   fn("DivMaxpserson");	
		   setTimeout(function(){$("#DivMaxpserson").css("display","none");},1000);
		  
		   istrue=false;
		 };	*/
		if(istrue==false){	obj.preventDefault();return false;}			
		else{
			GetUserInfo(); 
			if(Sys.ie!=null){	fn("DdivAddOk");	setTimeout("addDdivOk()",1000);		}
			else{	
				box3.Over = true;
				box3.OverLay.Color = "rgb(0, 0, 0)" ;
				box3.OverLay.Opacity = 50;  
				box3.Fixed = true;	 
				box3.Center = true; 	
				box3.Show(); 
				setTimeout(function(){	box.Close();box3.Close();},500);}
		}	
	});
	
	//继续添加	
	$('#ContinueAdd').click(function () {	
	   /* if($('.impress').length >= 20)
		{
			 fn("DivMaxpserson");	
		     setTimeout(function(){$("#DivMaxpserson").css("display","none");},1000);
		}
		else
		{*/
		 OpenFrom();
		//}
	});			
	/*------------------------------------------------公用方法开始------------------------------------------*/    
	 //状态清空
	 function CleanText() {
		   $('#titletext').val(''); 
		   $(":radio[id='allrad']").attr("checked", false);	
		   $(":radio[id='norad']").attr("checked", false);
	 }
		
	//加载收件人
	function GetUserInfo()
	{		
		var tests="";
		if(nowUserCount.length>0){		
			for(var i=0;i<nowUserCount.length;i++){
				if(nowUserCount[i]!=undefined){		
					tests +="<a  class='impress' id=close"+i+"  onclick=\"DeleteUserInfo('close" + i + "')\" pro='"+nowUserCount[i]+"' >"+nowUserCount[i]+"<i class='close'></i></a>";	
				}			
			}			
		}		
		tests +="<a  class='btn btn-small'  id='ContinueAdd' >继续添加<b class='btn-inner'></b></a>";		
		$('#IDivAdd').parent().show();
		$('#IDivAdd2').hide();
		$('#Idivs').hide();
		$('#Divsadduser').html(tests);		
		$('#ContinueAdd').click(function () {	
			if($('.impress').length >= 20)
			{
				 fn("DivMaxpserson");	
				 setTimeout(function(){$("#DivMaxpserson").css("display","none");},1000);
			}
			else
			{
				$('#titletext').autocomplete({	source: userCount}); 
				OpenFrom();
			}
		});	
	}	
	//弹出添加收件人
	function OpenFrom()
	{
		$('#searchUser').html("");
		CleanText();	
		//IE时层样式不兼容性不放遮挡层
		if(Sys.ie!=null){	fn("Idivs");}
		else{ 
			box.OverLay.Color = "rgb(51, 51, 51)" ; 
			box.Over = true;   
			box.OverLay.Opacity = 50;  
			box.Fixed = true;	 
			box.Center = true; box.Show();	
		}			
		//当层展现后，直接显示下级的名单		
		try {	
			$.ajax({
				type:'post',
				dataType:'json',				
				cache:false,
				url:'/proxy/querysubuser',
				data:'',	
				beforeSend:function(){	isLoading = true;},						
				success:function(data){							
					if(data!="" && data !="[]")
					{						
						htmls="";
						var json =eval(data); 	
						if(json.status !='0'){
							userCount=new Array(json.length);
									
							for(var i=0; i<json.length; i++)  {
								if($.inArray(data[i]['account'], nowUserCount) == -1){
									htmls +="<label class='label'><input type='checkbox' class='checkbox' name='items' pro='"+data[i].id+"'  value='"+data[i].account+"'/><a title='"+data[i].account+"' >"+data[i].account+"</a></label>";
									userCount[i]=data[i].account.toString();
								}				
							}					
							$('#searchUser').html(htmls);
						
							if(userCount.length>0){	$('#error1').css('display', 'none');	$('#error3').css('display', 'none');}
						}	
						else{	$('#searchUser').html("---------------------------------------无下级成员-------------------------------------");}	
						
					}
					
			
				},
				complete:function(){ isLoading = false; 	$('#CloseDiv').css("display","inline");	}
			});
		} catch (err) {	
			$('#ContinueAdd').removeAttr("disabled"); 	
			return;	
			}	
		}
	  //向下发送信件
	  $('#subSend').click(function(e){		    
		//发送人选择数	
		if(nowUserCount.length==0||ids.length==0 ){			
			$('#error0').css('display', 'none');
			$('#error1').css('display', 'inline');				
			e.preventDefault(); 
			return false;	
		}				
		var err = 0;		
		$.each(errorTypes, function(){
		if(typeof errorHas[this] != 'undefined'){
			err += errorHas[this];
		}
		});
		if(err > 0){ return false;	}		
	    var Ttitle=$('#Txttitle').val(),
		Ttitle = Ttitle.replace(/[^\x00-\xff]/g, 'xx'),
		TxtText=$('#TxtText').val(),
		TxtText = TxtText.replace(/[^\x00-\xff]/g, 'xx'),
		istrue=true;			 
	    if(Ttitle=='' ||  WidthCheck(Ttitle) > 30)  { 
			TxttitlePar.find('.ui-check').css('display', 'inline'); 
			TxttitlePar.find('.ui-text-prompt').css('display', 'none');  
			e.preventDefault();    
			istrue=false;  
		}	
	    if(TxtText == ''|| WidthCheck(TxtText) > 300)  { 
			TxtTextPar.find('.ui-check').css('display', 'inline'); 
			TxtTextPar.find('.ui-text-prompt').css('display', 'none'); 
			e.preventDefault();   
			istrue= false;  
		}				
		if(istrue==false){	
			e.preventDefault(); 
			return false;
		}
		var js=nowUserCount.join(","); 
		var idds=ids.join(","); 
		//debugger
		$.ajax({		
				type:'post',
				dataType:'json',
				cache:false,
				url:"/service/sendmessage",					
				data:'receiveId='+idds+'&sendAccount='+js+'&title='+$("#Txttitle").val().trim()+'&content='+$("#TxtText").val().trim()+'&typeid='+1,	
				beforeSend:function(){				
					//禁用发送												
					$('#subSendDisabled').text("发送中...");						 	
					$('#subSend').hide();
			        $('#subSendDisabled').show();						
				},	
				success:function(json){	
					if(json['status']=="0"){
						if(Sys.ie!=null){	box1.OverLay.Color = "#ffffff"; }
						
						if(Sys.ie!=null){	fn("divSendOk");	setTimeout(function(){	$("#divSendOk").css("display","none");},1000);	}
						else{ 	box1.OverLay.Color = "rgb(51, 51, 51)" ; 		
							box1.Over = true;  box1.OverLay.Opacity = 50;  box1.Fixed = true;	 box1.Center = true; 	box1.Show();									
						}
						window.location = "/Service/inbox";
					}else{		
						$("#Divcus h4").html(json['data']);
						if(Sys.ie!=null){	
							box4.OverLay.Color = "#ffffff"; 
						}
						else{ 
							box4.OverLay.Color = "rgb(51, 51, 51)" ;
						}		
						box4.Over = true; 
						box4.OverLay.Opacity = 50;  
						box4.Fixed = true;	 
						box4.Center = true; 	
						box4.Show();		 
						setTimeout(function(){
							box.Close();
							box4.Close();
							$("#Divcus h4").html('');
							$('#subSendDisabled').text("发送"); 
									
						},1500);
						//window.location = window.location;
					}					
				},
				error:function(xhr, type)
				{	 
				   fn("Dsendno");	
				   setTimeout(function(){$("#Dsendno").css("display","none");},1500);	
				},
				complete:function(){
					$('#subSendDisabled').text("发送");
					$('#subSend').hide();
			        $('#subSendDisabled').show();
				}
		});
		
	}); 
	
	//是否为空
	function isNull( str ){ if ( str == "" ) return true;  var regu = "^[ ]+$"; 	var re = new RegExp(regu); 	return re.test(str); 	} 	
	/*----------------------------------------------------公用方法结束-----------------------------------*/
	//搜索下线用户,重新接口调用查找
	$('#Search').click(function (){
		var titleText=$('#titletext').val();
		//if(titleText==""){ 	return false;}
		$('#searchUser').html('');			
		htmls="";		
		try {	
			$.ajax({
			type:'post',
			dataType:'json',				
			cache:false,
			url:'/proxy/querysubuser',
			data:'account='+$("#titletext").val().trim(),
			beforeSend:function(){	isLoading = true;},						
			success:function(data){							
				if(data['status']!=0 && data !="[]")
				{							
					htmls="";
					var json =eval(data); 			
					userCount=new Array(json.length);						
					for(var i=0; i<json.length; i++)  {
						htmls +="<label class='label'><input type='checkbox' class='checkbox' name='items' pro='"+data[i].id+"'  value='"+data[i].account+"'/>"+data[i].account+"</label>";
						userCount[i]=data[i].account.toString();					
					}					
					$('#searchUser').html(htmls);				
					if(userCount.length>0){	
						$('#error1').css('display', 'none');	
						$('#error3').css('display', 'none');
					}
					else{	
						
						$('#searchUser').attr("style","text-align:center");
						$('#searchUser').html("找不到此玩家，请确认输入");
					}
				}
				else{	
						$('#searchUser').attr("style","text-align:center");
						$('#searchUser').html("找不到此玩家，请确认输入");
				}
		
			},
			complete:function(){ 
				isLoading = false; 	
				$('#CloseDiv').css("display","inline");
			}
		});
		} catch (err) {	$('#ContinueAdd').removeAttr("disabled"); 	return;	}		 
			
		
      });	
	//取消返回上一页
	$('#CancelRrturn').click(function (){	window.location = "/Service/inbox";	});
	
	//
	
})(jQuery);

	//删除收件人时（先删除此标签，再删除数组相应数据）
	function DeleteUserInfo(obj)
	{  
		//debugger	
		var ui=$('#'+obj).attr('pro');			
		$('#'+obj).remove();			
		
		ids.splice($.inArray(ui,nowUserCount),1); 
		nowUserCount.splice($.inArray(ui,nowUserCount),1);  		
		fn("DdivOK");
		setTimeout("codefans2()",1000);	
		
		if(nowUserCount.length==0 || ids.length==0){ 	
			$('#IDivAdd2').css("display","inline");
			$('#IDivAdd').parent().css('display','none');
			
		}	
		
	}	
   
	//操作后提示	 
	function fn(obj){
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";						
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
	} 	
 
    function codefans(){	$("#DdivOK").css("display","none");  }	
	function codefans2(){	$("#DdivOK").css("display","none");  }
    function addDdivOk(){	$("#DdivAddOk").css("display","none"); }

</script>

{/literal}
</body>
</html>