<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>账户安全</title>
    <link rel="stylesheet" href="{$path_img}/images/common/base.css" />
    <link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
    <link rel="stylesheet" href="{$path_img}/images/common/js-ui.css">
    <script type="text/javascript">global_path_url="{$path_img}";</script>
    <script type="text/javascript">hjUserData= "{$hjUserData}";</script>
    <script type="text/javascript">global_params_notice = "all,ad_top,agent_first_page";</script>
    <script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="{$path_js}/js/jquery.easing.1.3.js"></script>
    <script type="text/javascript" src="{$path_js}/js/jquery.tmpl.min.js"></script>
    <script type="text/javascript" src="{$path_js}/js/jquery-ui-1.10.2.js"></script>
    <script type="text/javascript" src="{$path_js}/js/jquery.flot.js"></script>
    <script type="text/javascript" src="{$path_js}/js/jquery.flot.crosshair.js"></script>
    <script type="text/javascript" src="{$path_js}/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.base.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.Class.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.Event.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.util.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.Timer.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.Input.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.Tab.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.Slider.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.Hover.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.Tip.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.Mask.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.MiniWindow.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.Message.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.Validator.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.DatePicker.js"></script>
    <script type="text/javascript">
    (function() {       
        function async_load(){           
            var s = document.createElement('script');          
            s.type = 'text/javascript';          
            s.async = true;           
            s.src = "";           
            var x = document.getElementsByTagName('script')[0];          
            x.parentNode.insertBefore(s, x);      
        }       
    if (window.attachEvent)           
    window.attachEvent('onload', async_load);
    else 
    window.addEventListener('load', async_load, false);  
    })();
    </script>

<body>
    
    <!-- header start -->
    <div class="header">
        <div class="g_33">
            <h1 class="logo"><a title="首页" href="/index">宝开</a></h1>
        </div>
    </div>
    <!-- header end -->

    <div class="g_33">  
    
    <script type="text/javascript" src="{$path_js}/js/jquery.uploadprogress.0.3.js"></script>
    <script type="text/javascript">
        // 瀏灠器判斷
        navigator.sayswho= (function(){
            var ua= navigator.userAgent, tem, 
                M= ua.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i) || [];
            if(/trident/i.test(M[1])){
                tem=  /\brv[ :]+(\d+)/g.exec(ua) || [];
                return 'IE '+(tem[1] || '');
            }
            if(M[1]=== 'Chrome'){
                tem= ua.match(/\bOPR\/(\d+)/);
                if(tem!= null) return 'Opera '+tem[1];
            }
            M= M[2]? [M[1], M[2]]: [navigator.appName, navigator.appVersion, '-?'];
            if((tem= ua.match(/version\/(\d+)/i))!= null) M.splice(1, 1, tem[1]);
            return M.join(' ');
        })();
        jQuery(function () {
            if ( navigator.sayswho == 'MSIE 8' || navigator.sayswho == 'MSIE 9' )
            {
                var isUpload = 1; // 1:FALSE, 2:TRUE
                var x = 1;
                var def_id = [];
                def_id[0] = $("#idCard0");
                def_id[1] = $("#idCard1");
                def_id[2] = $("#idCard2");
                function checkImg( event ) { // TEST
                    var f = event.target;
                    if (!f.value.match(/\.(jpg|jpeg|png|gif|JPG|JPEG|PNG|GIF)$/)){
                        alert('图片只支持JPG、GIF、PNG格式');
                        //document.getElementById('f.id').value = '';
                        //$("#"+f.id).reset(); // 非圖片格式清空內容
                        //$("#"+f.id).val(''); 
                        for( var k in def_id ) {
                            if ( 'idCard'+k == f.id) {
                            // 非圖片格式清空內容
                                def_id[k].replaceWith( def_id[k] = def_id[k].clone( true ) );
                            }
                        }
                    }
                }
                $('input[type=file]',$('#upload_form') ).on('change', checkImg);
                jQuery('#upload_form').uploadProgress({ 
                    progressURL:'/appeal/upload',
                        displayFields : ['kb_uploaded','kb_average','est_sec'],
                        start: function(){
                            isUpload = 2;
                            upload_wait();
                        },
                        success: function(data) {
                            var iframe = document.getElementById("jqUploader");
                            if ( typeof iframe.contentDocument == 'unknown'){ // 回传404
                                isUpload = 1;
                                jQuery('input[type=submit]',$('#upload_form')).val('上传所有相片');
                                alert('请选择要上传的图片, 文件大小请控制在2MB以内。');
                                return;
                            }
                            var iframe_contents = iframe.contentDocument.body.innerHTML;
                            var data = JSON.parse(iframe_contents);
                            setTimeout(function(){
                            if(typeof data.error === 'undefined')
                            {
                                jQuery('input[type=submit]',$('#upload_form')).val('上传成功');
                                jQuery('input[type=submit]',$('#upload_form')).attr("disabled","disabled"); 
                            }
                            else
                            {
                                alert(data.data);
                                jQuery('input[type=submit]',$('#upload_form')).val('上传所有相片');
                            }
                            isUpload = 1;
                            }, 1000);
                        }
                });
                function upload_wait(){
                    if ( isUpload == 2 ) {
                        if ( x == 1 ) {
                            $("#submit").val("上傳中.");
                            jQuery('input[type=submit]',$('#upload_form')).val('上传中.');
                            x++;
                        }
                        else if ( x == 2  ) {
                            jQuery('input[type=submit]',$('#upload_form')).val('上传中..');
                            x++;
                        }
                        else if ( x == 3 ) {
                            jQuery('input[type=submit]',$('#upload_form')).val('上传中...');
                            x = 1;
                        }
                    }
                    setTimeout(function(){
                        upload_wait();
                    }, 1000);
                }
            }
            else {

                var data = new FormData();
                // Add events
                function prepareUpload( event ) { // TEST
                    var f = event.target;
                    if (!f.files[0].name.match(/\.(jpg|jpeg|png|gif|JPG|JPEG|PNG|GIF)$/)){
                        alert('图片只支持JPG、GIF、PNG格式');
                        $("#"+f.id).val(''); // 非圖片格式清空內容
                    }
                    else if ( f.files[0].size > ( 1024 * 1024 * 2 ) )
                    {
                        alert('文件大小请控制在2MB内');
                        $("#"+f.id).val(''); // 非圖片格式清空內容
                    }
                }
                function uploadFile(event) {
                    event.stopPropagation(); // Stop stuff happening
                    event.preventDefault();  // Totally stop stuff happening
                    var xhr = new XMLHttpRequest();
                    var fd = new FormData();
                    //var fd = document.getElementById('upload_form').getFormData();
                    var fileCheck = true;
                    for(var i=0; i<3; i++){
                        if ( !fileCheck ) {
                            continue;
                        }
                        if ( $('#idCard' +i ).val() != '' ) {
                            fd.append('idCard'+i, document.getElementById('idCard'+i).files[0]);
                        }
                        else {
                            fileCheck = false;
                        }
                    }
                    if ( fileCheck == false) {
                        alert('请选择要上传的图片');
                        return;
                    }

                    /* event listners */
                    xhr.upload.addEventListener("progress", uploadProgress, false);
                    xhr.addEventListener("load", uploadComplete, false);
                    xhr.addEventListener("error", uploadFailed, false);
                    xhr.addEventListener("abort", uploadCanceled, false);
                    /* Be sure to change the url below to the url of your upload server side script */
                    xhr.open("POST", "/appeal/upload");
                    xhr.send(fd);
                }
                function uploadProgress(evt) {
                    if (evt.lengthComputable) {
                        var percentComplete = Math.round(evt.loaded * 100 / evt.total);
                        jQuery('input[type=submit]',$('#upload_form')).val('上传中...' + percentComplete.toString() + '%' );
                    }
                    else {
                        jQuery('input[type=submit]',$('#upload_form')).val('上传中...');
                    }
                }

                function uploadComplete(evt) {
                    /* This event is raised when the server send back a response */
                    var msg = JSON.parse( evt.target.responseText );
                    if(typeof msg.error === 'undefined')
                    {
                        jQuery('input[type=submit]',$('#upload_form')).val('上传成功');
                        jQuery('input[type=submit]',$('#upload_form')).attr("disabled","disabled"); 
                    }
                    else
                    {
                        alert(msg.data);
                    }
                }

                function uploadFailed(evt) {
                    alert("图片无法上传，请稍后再试。");
                }

                function uploadCanceled(evt) {
                    alert("图片上传已取消");
                }  
                $('input[type=file]',$('#upload_form') ).on('change', prepareUpload );
                $('#upload_form').on('submit', uploadFile );

/*
                function uploadFiles(event)
                {
                    event.stopPropagation(); // Stop stuff happening
                    event.preventDefault(); // Totally stop stuff happening

                    // 上傳前驗證圖片
                    var ss = [];
                    var pic = ['JPG','GIF','PNG','jpg','gif','png','JPEG','jpeg'];
                    var isCheck = true , errMsg = '';
                    for( var i = 0; i < 3; i++)
                    {
                        if( $("#idCard"+i).val() == '' )
                        {
                            alert('请选择要上传扫描件');
                            return false;
                        }
                    }

                    $.ajax({
                        url: '/appeal/upload',
                            type: 'POST',
                            data: data,
                            cache: false,
                            dataType: 'json',
                            processData: false, // Don't process the files
                            contentType: false, // Set content type to false as jQuery will tell the server its a query string request
                            success: function(data, textStatus, jqXHR)
                        {
                            if(typeof data.error === 'undefined')
                            {

                                jQuery('input[type=submit]',$('#upload_form')).val('上传成功');
                                jQuery('input[type=submit]',$('#upload_form')).attr("disabled","disabled"); 
                            }
                            else
                            {
                                // Handle errors here
                                alert(data.data);
                            }
                        },
                            error: function(jqXHR, textStatus, errorThrown)
                            {
                                // Handle errors here
                                console.log('ERRORS: ' + textStatus);
                                // STOP LOADING SPINNER
                            }
                    });
                }
 */
            }

        function getSize(fileInput)
        {
            var nAppName = navigator.appName
            var id = $(fileInput).attr("id")+"_errorspan";
            if(nAppName =='Netscape'){
                var byteSize  = fileInput.files[0].size;
                var size =  (byteSize / 1024); // Size returned in MB. 
                if(size > 2048){
                    $("#"+id).parent().css('display', 'inline');
                    return false;
                } else {
                    $("#"+id).parent().css('display', 'none');
                    return true;
                }
            } else {
                $("#"+id).parent().css('display', 'none');
                return true;
            }
        }

    });
    function getFileSize(fileInput,disable)
    {
        var nAppName = navigator.appName
        var id = $(fileInput).attr("id")+"_errorspan";
        if(nAppName =='Netscape'){
            var byteSize  = fileInput.files[0].size;
            var size =  (byteSize / 1024); // Size returned in MB. 
            if(size > 2048){
                $("#"+id).parent().css('display', 'inline');
                if(disable){
                    $('input[type=submit]').attr("disabled","disabled");
                }
                return false;
            } else {
                $("#"+id).parent().css('display', 'none');
                if(disable){
                    $('input[type=submit]').removeAttr("disabled");
                    jQuery('#submit').val('上传所有相片');
                }
                return true;
            }
        } else {
            $("#"+id).parent().css('display', 'none');
            if(disable){
                $('input[type=submit]').removeAttr("disabled");
                jQuery('#submit').val('上传所有相片');
            }
            return true;
        }
    }
    </script>   



        <div class="appeal-content">
                <dl class="appeal-prompt">
                    <dt>申诉小提示：</dt>
                    <dd>1、申诉可以解决号码被盗、无法修改密码和修改安全信息等问题；</dd>
                    <dd>2、申诉结果将在5个工作日内发到联系邮箱里；</dd>
                    <dd>3、请填写真实的申诉资料，这将有助于账号申诉；</dd>
                    <dd>4、以下所有信息为必填项；</dd>
                </dl>
            <h3 class="ui-title">填写申诉资料<span class="color-red">请填写您的真实资料，这将有助于帐号申诉。娱乐平台不会泄漏您的个人信息。</span></h3>
            <ul class="ui-form">
                <li>
                    <label for="name" class="ui-label">用户名：</label>
                    <input type="text" value="请输入你的用户名" name="userName" id="username" class="input">
                    <div class="ui-check"><i class="error"></i><span id="usernames">用户名不能为空（3-30位）</span></div>
                </li>
                <li>
                    <label class="ui-label">申诉类型：</label>
                    <label class="label"><input type="radio" name="safeInfo" checked class="radio" value="2">安全信息（登录密码、安全密码、安全问题）</label>
                    <label class="label"><input type="radio" name="safeInfo" class="radio" value="1">安全邮箱</label>
                </li>
                <li>
                    <label class="ui-label">上传身份证扫描件：</label>
                    <span class="ui-text-info">文件大小请控制在2MB内，支持JPG/PNG/GIF</span>
                </li>
                <form id="upload_form" action="/Appeal/upload" method="post" enctype="multipart/form-data">
                <li>                    
                    
                    <fieldset>                  
                        <label class="ui-label"></label>
                        <input type="file"  onchange="getFileSize(this,true)" id="idCard0" name="idCard0"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; ">    <span  style=" font-size:11px">(身份证扫描件）</span><div class="ui-check"><i class="error"></i><span id="idCard0_errorspan">文件大小不能超过2MB</span></div>      
                    
                    </fieldset>
                
                    
                    </li>
                <!--<li>
                    <label class="ui-label"></label>
                    
                <a class="impress" href="javascript:void(0);">已上传文件名字.jpg<i class="close"></i></a>
                </li>-->
                
                <li>
                    <label class="ui-label">已绑定银行卡扫描件：</label>
                    <span class="ui-text-info">文件大小请控制在2MB内，支持JPG/PNG/GIF</span>
                </li>
                <li>                    
                        
                        <fieldset style=" ">
                                    <label class="ui-label"></label>
                                    <input type="file" id="idCard1" name="idCard1"  onchange="getFileSize(this,true)"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; "/><span style=" font-size:11px">（正面照片）</span>                    
                            <a href="javascript:void(0);" id="ico-help-image1" class="ico-help-small" title="点击查看帮助"></a>  <div class="ui-check"><i class="error"></i><span  id="idCard1_errorspan">文件大小不能超过2MB</span></div>
                        </fieldset>
                    
                </li>
                <li>                                
                        
                        <fieldset>
                            <label class="ui-label"></label>                
                            <input type="file" id="idCard2" name="idCard2"  onchange="getFileSize(this,true)"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; "/><span  style=" font-size:11px">（45°照片）</span>  
                            &nbsp;<input type="submit" name="submit" value="上传所有相片" id="submit" style='width:100px;' />          
                            <a href="javascript:void(0);" id="ico-help-image2" class="ico-help-small" title="点击查看帮助"></a> <div class="ui-check"><i class="error"></i><span id="idCard2_errorspan">文件大小不能超过2MB</span></div>
                        </fieldset>
                
                </li>
                    </form>
                    <li>
                    <label for="" class="ui-label">已绑定银行卡信息：</label>
                    
                    <input type="text" value="银行卡开户名"  class="input account-name" id="bankCardName" > <!--onKeyUp="value=value.replace(/[^\u4E00-\u9FA5]/g,'')"-->

                    <input type="text" value="银行卡号"  class="input card-number" id="bankCardNum" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" style="ime-mode:disabled"> 


                    <div class="ui-check"><i class="error"></i>银行卡信息不为空(长度不超过20位)</div>
                </li>
                <form  id="fm_main"  name="fm_main" enctype="multipart/form-data"> 
                    <li>
                        <label class="ui-label">账号注册地点：</label>
                        <select name="province" class="ui-select"  id="regAccount"></select>
                        <select name="city" class="ui-select" id="regcity"></select>
                        <div class="ui-check"><i class="error"></i>请选择注册地点</div>
                    </li>
                    <li>
                        <label class="ui-label">经常登录地区：</label>
                        <select name="province2" class="ui-select"  id="loginAccount"></select>
                        <select name="city2" class="ui-select" id="loginctyt2"></select>
                        <div class="ui-check"><i class="error"></i>请选择登录地区</div>
                    </li>
                </form>
            </ul>
            <h3 class="ui-title">申诉结果接收方式<span>申诉进展和结果将通过您填写的联系方式通知到您</span></h3>
            <ul class="ui-form">
                <li>
                    <label class="ui-label">接收结果邮箱：</label>
                    <input type="text" name="email" id="email" value="请填写接收申诉结果的邮箱" class="input">
                    <div class="ui-check"><i class="error"></i>请正确填写接收结果邮箱</div>
                </li>
                <li>
                    <label for="name" class="ui-label">再次输入邮箱：</label>
                    <input type="text" value="请确认接收申诉结果的邮箱" class="input"  name="email1" id="email1">
                    <div class="ui-check"><i class="error"></i><span id="errorEmail">请正确填写确认接收申诉结果的邮箱</span></div>
                    
                </li>
                <li class="ui-btn"><input id="J-button-submit" type="button" class="btn-submit" value="提交"  ><b class="btn-inner"></b></li>
                
            </ul>
        </div>
        
        <div class="pop w-4" style="position:absolute;left:100px; display:none" id="divOk">
            <i class="ico-success"></i>
            <h4 class="pop-text">申诉成功</h4>
        </div>
        <div class="pop w-4" style="position:absolute;left:400px; display:none" id="divNo">
            <i class="ico-error"></i>
            <h4 class="pop-text">申诉失败</h4>
        </div>
        <!-- </form> -->
    
    </div>
    
    <!-- //////////////底侧公共页面////////////// -->
    {include file='/default/ucenter/footer.tpl'}
    <!-- /////////////底侧公共页面/////////////// -->
<script  src="{$path_js}/js/phoenix.Common.js"></script>
<script src="{$path_js}/js/phoenix.area.js"></script> 
{literal}


<script > 
//首次加载
  (function($){  
    //账号注册地点    
    var oProvince=document.fm_main.province;
    var oCity=document.fm_main.city;    
    for(var i=0;i<aProvince.length;i++){oProvince.options[i]=new Option(aProvince[i]);  }
    for(var i=0;i<aCity[0].length;i++){ oCity.options[i]=new Option(aCity[0][i]);   }   
    oProvince.onchange=function(){
        oCity.length=0;
         for(var i=0;i<aCity[this.selectedIndex].length;i++){
            oCity.options[i]=new Option(aCity[this.selectedIndex][i]);
        }
    };

    //问号提示
    var tipCase = new phoenix.Tip({cls:'j-ui-tip-l j-ui-tip-yellow'});
    tipCase.setText('请提供完整清晰的银行卡正面照片，这样有助于您申诉成功。');
    $('#ico-help-image1').hover(function(){
        tipCase.show(28, -6, this);
    },function(){
        tipCase.hide();
    });
    
    //问号提示
    var tipCase1 = new phoenix.Tip({cls:'j-ui-tip-l j-ui-tip-yellow'});
    tipCase1.setText('将银行卡转45°角进行拍摄，请提供完整清晰的银行卡照片。');
    $('#ico-help-image2').hover(function(){
        tipCase1.show(28, -6, this);
    },function(){
        tipCase1.hide();
    });
    //经常登录地区
    var oProvince2=document.fm_main.province2;  
    var oCity2=document.fm_main.city2;
    for(var i=0;i<aProvince.length;i++){  oProvince2.options[i]=new Option(aProvince[i]);    }
    for(var i=0;i<aCity[0].length;i++){   oCity2.options[i]=new Option(aCity[0][i]);   }    
    oProvince2.onchange=function(){
        oCity2.length=0;
        for(var i=0;i<aCity[this.selectedIndex].length;i++){
            oCity2.options[i]=new Option(aCity[this.selectedIndex][i]);
        }
    };  

    var form = $('#fm_main'),username,usernamePar,bankCardName,bankCardNamePar,bankCardNum,bankCardNumPar,regAccount,regAccountPar,loginAccount,loginAccountPar,email,emailPar,email1,email1Par,
    
    //表单检测错误数量
    errorTypes = ['username','bankCardName','bankCardNum','regAccount','loginAccount','email','email1'],
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
    
    //用户名验证 
    username = $('#username');
    usernamePar = username.parent();
    username.focus(function(){  
        if(username[0].value=='请输入你的用户名'){ $("#username")[0].value='';  }
        usernamePar.find('.ui-check').css('display', 'inline'); 
        
    }).blur(function(){
        //debugger
        var v = $.trim(this.value);
        
        if(WidthCheck(v)==0)
        {
            usernamePar.find('.ui-check').css('display', 'inline'); $('#usernames').html('用户名不能为空（3-30位）'); setErrorNum('username', 1);
        }
        else if(WidthCheck(v)>=3 && WidthCheck(v)<=30){                 
            $.ajax({
                type:'post',
                dataType:'json',
                cache:false,
                url:'/appeal/accountisvial',            
                data:'userName='+v,                 
                success:function(data){ 
                //debugger                          
                    if(data['isUser']!="1"){
                        usernamePar.find('.ui-check').css('display', 'inline'); 
                        $('#usernames').html('用户名不存在!');
                        setErrorNum('username', 1);                 
                    }else{  usernamePar.find('.ui-check').css('display', 'none'); $('#usernames').html('用户名不能为空（3-30位）');   setErrorNum('username', -1);}   

                }
            });
        }       
        else{   usernamePar.find('.ui-check').css('display', 'inline'); $('#usernames').html('用户名长度为（3-30位）');  setErrorNum('username', 1);}    
            
        
    });
    
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
    }  
    
    //银行卡开户名
    //bankCardName 
    bankCardName = $('#bankCardName');
    bankCardNamePar = bankCardName.parent();    
    bankCardName.focus(function(){
        if(bankCardName[0].value=='银行卡开户名'){ $("#bankCardName")[0].value='';}   
        bankCardNamePar.find('.ui-check').html('<i class="error"></i>'+"银行卡开户名长度：4-20字符");
        bankCardNamePar.find('.ui-check').css('display', 'inline');         
    }).blur(function(){
        var v = $.trim(this.value);
        if(v == '' ){
            bankCardNamePar.find('.ui-check').css('display', 'inline'); 
            setErrorNum('bankCardName', 1);
        }
        else if(WidthCheck(v) < 4 || WidthCheck(v) > 20)
        {
            
            bankCardNamePar.find('.ui-check').html('<i class="error"></i>'+"银行卡开户名长度：4-20字符");
            bankCardNamePar.find('.ui-check').css('display', 'inline'); 
            setErrorNum('bankCardName', 1);
        }
        else{
            bankCardNamePar.find('.ui-check').css('display', 'none');
            setErrorNum('bankCardName', -1);
        }
        
    });
    
    //银行卡卡号 
    bankCardNum = $('#bankCardNum');
    bankCardNumPar = bankCardNum.parent();  
    bankCardNum.focus(function(){
        if(bankCardNum[0].value=='银行卡号'){ $("#bankCardNum")[0].value='';}   
        bankCardNumPar.find('.ui-check').html('<i class="error"></i>'+'银行卡信息不为空(长度不超过20位)');
        bankCardNumPar.find('.ui-check').css('display', 'inline');          
    }).blur(function(){
        var v = $.trim(this.value);
        if(v == '' || v.length>20){
            bankCardNumPar.find('.ui-check').html('<i class="error"></i>'+'银行卡信息不为空(长度不超过20位)');
            bankCardNumPar.find('.ui-check').css('display', 'inline');  
            setErrorNum('bankCardNum', 1);
        }else{
            bankCardNumPar.find('.ui-check').css('display', 'none');
            setErrorNum('bankCardNum', -1);
        }
        
    });
    
    //regAccount 
    //账号注册地点选择  
    regAccount = $('#regAccount');
    regAccountPar = regAccount.parent();    
    regAccount.blur(function(){
        var v = $.trim(this.value);
        if($('#regAccount').find("option:selected").text()=='请选择省'){
            regAccountPar.find('.ui-check').css('display', 'inline');   
            setErrorNum('regAccount', 1);
        }else{
            regAccountPar.find('.ui-check').css('display', 'none');
            setErrorNum('regAccount', -1);
        }
        
    });
    //经常登录地
    loginAccount = $('#loginAccount');
    loginAccountPar = loginAccount.parent();    
    loginAccount.blur(function(){
        var v = $.trim(this.value);
        if($('#loginAccount').find("option:selected").text()=='请选择省'){
            loginAccountPar.find('.ui-check').css('display', 'inline'); 
            setErrorNum('regAccount', 1);
        }else{
            loginAccountPar.find('.ui-check').css('display', 'none');
            setErrorNum('regAccount', -1);
        }
        
    });
    
    //邮箱验证  
    email = $('#email');
    emailPar = email.parent();
    email.focus(function()
    {
        if($("#email")[0].value=='请填写接收申诉结果的邮箱')
        { 
          $("#email")[0].value='';
        }   
        emailPar.find('.ui-check').html("<i class='error'></i>接收结果邮箱不能为空");
        emailPar.find('.ui-check').css('display', 'inline');    
    }).blur(function(){
        var v = $.trim(this.value);
        if(v =='')
        {
            emailPar.find('.ui-check').html("<i class='error'></i>接收结果邮箱不能为空");
            emailPar.find('.ui-check').css('display', 'inline');
            setErrorNum('email', 1);
            return;
        }
        if(!(/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/).test(v)){
            emailPar.find('.ui-check').html("<i class='error'></i>请正确填写接收结果邮箱");
            emailPar.find('.ui-check').css('display', 'inline');        
            setErrorNum('email', 1);
            return;
        }else{
            emailPar.find('.ui-check').css('display', 'none');      
            setErrorNum('email', -1);
            return;
        }       
    });
    
    //再次邮箱验证    
    email1 = $('#email1');
    email1Par = email1.parent();
    email1.focus(function()
    {
        if($("#email1")[0].value=='请确认接收申诉结果的邮箱')
        { 
           $("#email1")[0].value='';
        }   
        email1Par.find('.ui-check').html("<i class='error'></i>再次输入邮箱不能为空");
        email1Par.find('.ui-check').css('display', 'inline');   
    }).blur(function(){
        var v = $.trim(this.value);
        if(v =='')
        {
            email1Par.find('.ui-check').html("<i class='error'></i>再次输入邮箱不能为空");
            email1Par.find('.ui-check').css('display', 'inline');
            return;
        }
        if(!(/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/).test(v)){
            email1Par.find('.ui-check').html("<i class='error'></i>请正确填写确认接收申诉结果的邮箱");
            email1Par.find('.ui-check').css('display', 'inline');   
            setErrorNum('email1', 1);           
            return;
        }
        else if($('#email1').val()!=$('#email').val()){
            email1Par.find('.ui-check').html("<i class='error'></i>两次邮箱不一致");
            email1Par.find('.ui-check').css('display', 'inline');   
            setErrorNum('email1', 1);       
            return; 
        }
        else{
            email1Par.find('.ui-check').css('display', 'none'); 
            setErrorNum('email1', -1);
            return;
        }       
    });
    
    
    //操作后提示  
    function fn(obj){
        var Idivdok = document.getElementById(obj); 
        Idivdok.style.display="block";                      
        Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";           
        Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
    }   
    function codefans(obj){
        $('#'+obj).css("display","none");
    }   
    
    var box = new LightBox("divNo");
    //提交申诉
    $('#J-button-submit').click(function(e){
        //form.submit();
        var err = 0;        
        $.each(errorTypes, function(){
        if(typeof errorHas[this] != 'undefined'){
            err += errorHas[this];
        }
        });
        //debugger
        if(username[0].value=='请输入你的用户名'){      
        usernamePar.find('.ui-check').css('display', 'inline');  e.preventDefault(); return false;
        }
        
        //图片验证
        if($('#idCard0').val() !='' && $('#idCard1').val()!='' && $('#idCard2').val()!=''){
            var ss = $("#idCard0").val().split(".");
            var ss1 = $("#idCard1").val().split(".");
            var ss2 = $("#idCard2").val().split(".");
            if(!getFileSize($("#idCard0")[0]) && !getFileSize($("#idCard1")[0]) && !getFileSize($("#idCard2")[0])){
                return false;
            }
            if(ss.length>1 && ss1.length>1 && ss2.length>1)
            {
                var end=ss[ss.length-1];
                var end1=ss1[ss1.length-1];
                var end2=ss2[ss2.length-1];
                var isbool=true;
                if(end!='JPG'&&end!='GIF'&&end!='PNG'&&end!='jpg'&&end!='gif'&&end!='png')
                {
                    isbool=false;
                }else if(end1!='JPG'&&end1!='GIF'&&end1!='PNG'&&end1!='jpg'&&end1!='gif'&&end1!='png')
                {
                    isbool=false;
                }else if(end2!='JPG'&&end2!='GIF'&&end2!='PNG'&&end2!='jpg'&&end2!='gif'&&end2!='png')
                {
                    isbool=false;
                }
                if(!isbool)
                {
                    alert("图片只支持JPG、GIF、PNG格式");
                     return;
                }
                
            }
        }
        else
        {
            alert('请选择要上传扫描件');
            return false;
        }
        
        
        
        
        if(bankCardName[0].value=='银行卡开户名' || WidthCheck(bankCardName[0].value)<4 ||WidthCheck(bankCardName[0].value)>20){  
        bankCardNamePar.find('.ui-check').html('<i class="error"></i>'+"银行卡开户名长度：4-20字符");
        bankCardNamePar.find('.ui-check').css('display', 'inline');  e.preventDefault(); return false;  
        }
        if(bankCardNum[0].value=='银行卡号'){       
        bankCardNumPar.find('.ui-check').html('<i class="error"></i>'+'银行卡信息不为空(长度不超过20位)');
        bankCardNumPar.find('.ui-check').css('display', 'inline');   e.preventDefault(); return false;  
        }
        
        if($('#regAccount').find("option:selected").text()=='请选择省')
        {
            regAccountPar.find('.ui-check').css('display', 'inline');   
            e.preventDefault(); return false;
        }
        if($('#loginAccount').find("option:selected").text()=='请选择省')
        {
            loginAccountPar.find('.ui-check').css('display', 'inline');  
            e.preventDefault(); return false;
        }
        if($("#email")[0].value=='请填写接收申诉结果的邮箱' || $("#email")[0].value =="")
        {
            emailPar.find('.ui-check').html("<i class='error'></i>接收结果邮箱不能为空");
            emailPar.find('.ui-check').css('display', 'inline');         
            e.preventDefault(); return false;
        }
        if($("#email1")[0].value=='请确认接收申诉结果的邮箱' || $("#email1")[0].value=="")
        {
            email1Par.find('.ui-check').html("<i class='error'></i>再次输入邮箱不能为空");
            email1Par.find('.ui-check').css('display', 'inline');    
            e.preventDefault(); return false; 
        }
        if($('#idCard0').val() =='' || $('#idCard1').val()=='' || 
        $('#idCard2').val() =='' || jQuery('#submit').val()=="上传所有相片")
        {
             alert("请选择要上传扫描件");  
             e.preventDefault();  
             return false; 
        }
        if(err > 0)
        { 
          e.preventDefault(); 
          return false; 
        }
        var accountReg= $("#regAccount").val().trim()+','+$('#regcity').val().trim();
        var logctyie=$('#loginAccount').val().trim()+','+$('#loginctyt2').val().trim();
        var msgDialog = new phoenix.Message();


        $.ajax({        
            type:'post',
            dataType:'json',
            cache:false,
            url:"/appeal/userappeal",                   
            data:'userName='+$('#username').val().trim()+'&safeInfo='+$('input[name="safeInfo"]:checked').val().trim()+'&accountName='+$("#bankCardName").val().trim()+'&cardNum='+$("#bankCardNum").val().trim()+'&accountReg='+accountReg+'&loginArea='+logctyie+'&email='+$('#email').val().trim(),
            beforeSend:function(){              
                //禁用发送                              
                $('#J-button-submit').val("发送中...");                            
                $("#J-button-submit").css("color", "#CACACA").css("background-color", "#CACACA");　          
                $("#J-button-submit").attr("disabled","disabled");                  
            },  
            success:function(json){     
            
                if(json['status']=="ok")
                {                                           
                    window.location = "/appeal/apsuccess";
                }
                else
                {       
                    msgDialog.show({
                        content : json.data,
                        time : 3,
                        mask : true,
                        confirmIsShow : true,
                        confirmFun : function(){
                            msgDialog.hide();
                        }
                    });                             
                     //fn("divNo");                 
                     // $("#divNo h4").html(json.data);
                     // box.Over = true; box.OverLay.Color = "#336666" ;  box.OverLay.Opacity = 50;  box.Fixed = true;   box.Center = true;     box.Show();     
                     // setTimeout( function(){box.Close()},3000);                  
                }       
            },
            error:function(xhr, type){                                      
                 box.Over = true; box.OverLay.Color = "rgb(51, 51, 51)" ;  box.OverLay.Opacity = 50;  box.Fixed = true;  box.Center = true;     box.Show();     
                 setTimeout( function(){box.Close()},3000);             
                 $('#J-button-submit').removeAttr("disabled");          
                 
            },
            complete:function(){
                $('#J-button-submit').val("提交申诉");
                $("#J-button-submit").css("color", "#555555").css("background-color", "#CACACA");　      
                $('#J-button-submit').removeAttr("disabled");   
            }
        });
    });
    

  
   })(jQuery);

</script>
{/literal}
</body>
</html>
