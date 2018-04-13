<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no"/>
    <title>普通登录</title>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/common.css"/>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/findPwdByMail.css"/>
<body>
<!--forgetPwdUname.html begin-->
<div class="container">
    <div class="page login_page">
        <!--title 部分-->
        <div class="title"><span></span>忘记密码</div>
        <!--logo 部分-->
        <h1 class="logo"><!--<p class="v"></p>--></h1>


        <div class="step"></div>
        <p class="tipinput tiptext">登录密码找回邮件已发送至您</p>
        <p class="tipinput tiptext">的邮箱<span>{$email}</span></p>

        <div class='btn_login checkMail' btn='check'>查收邮件</div>
        <p class="tipinput middleP">没有收到邮件？</p>
        <div class='btn_login checkMail unbtn' btn='resend'>重新发送邮件</div><span class="count">60s</span>
        <p class="tipinput">半小时内没有收到邮件，到邮箱</p>
        <p class="tipinput">的广告邮件、垃圾邮件列表中找找</p>

    </div>
</div>


<script type="text/javascript" src="{$path_js}/js/wap/login/zepto.min.js"></script>
<script type="text/javascript" src="{$path_js}/js/wap/login/findPwdByMail.js"></script>
<!--forgetPwdUname.html end-->
</body>
</html>
