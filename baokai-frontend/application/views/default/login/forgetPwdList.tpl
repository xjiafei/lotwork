<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no"/>
    <title>忘记密码</title>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/common.css"/>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/forgetPwdList.css"/>
<body>
<!--forgetPwdUname.html begin-->
<div class="container">
    <div class="page login_page">
        <!--title 部分-->
        <div class="title"><span></span>忘记密码</div>
        <!--logo 部分-->
        <h1 class="logo"><!--<p class="v">--></p></h1>


        <div class="step"></div>
        <p class="tipinput">您正在找回登录密码的账号是:<span>{$uName}</span></p>
        <p class="tipinput">请选择您找回登录密码的方式</p>

        <!--安全登录模式表单用户名-->
        <div id="findPwdList" class="login_form login_unique">
            <ol class="inputOL">
                {if $isBindMail eq 1}
                    <li isclick='true' _href='?stp=2'>您绑定的邮箱为{$mail}</li>
                {else}
                    <li isclick='false' _href='#' class='changeBg'>您尚未绑定邮箱</li>
                {/if}

                {if $isSetSafeQuest eq 1}
                    <li isclick='true' _href='?stp=3'>安全问题</li>      
                {else}
                    <li isclick='false' _href='#' class='changeBg'>安全问题未设置</li>
                {/if}

                {if $isSetSafeCode eq 1}
                    <li isclick='true' _href='?stp=4'>安全密码</li>
                {else}
                    <li isclick='false' _href='#' class='changeBg'>安全密码未设置</li>
                {/if}
               
            </ol>
        </div>		
        <div class="tip">
            <p class="tipinput">您还可以通过电脑版的在线客服进行</p>
            <p class="tipinput">人工申诉找回登录密码</p>
        </div>
    </div>
</div>


<script type="text/javascript" src="{$path_js}/js/wap/login/zepto.min.js"></script>
<script type="text/javascript" src="{$path_js}/js/wap/login/forgetPwdList.js"></script>
<!--forgetPwdUname.html end-->
</body>
</html>
