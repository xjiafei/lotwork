<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no"/>
    <title>找回密码</title>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/common.css"/>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/findPwdBySecuCode.css"/>
<body>
<!--forgetPwdUname.html begin-->
<div class="container">
    <div class="page login_page">
        <!--title 部分-->
        <div class="title"><span></span>找回密码</div>
        <!--logo 部分-->
        <h1 class="logo"><!--<p class="v"></p>--></h1>

        <div class="step"></div>

        <p class="tipinput">请输入安全密码</p>

        <div class="login_form login_unique">
            <ol class="inputOL">
                <li>
                    <span>密码</span>
                    <input type="password" name="pwd" id="pwd" placeholder="请输入安全密码"/>
                </li>

            </ol>
        </div>

        <div class="login_tips">
            <span></span>
        </div>

        <!--登录按钮-->
        <button class="btn_login" id="next">下一步</button>
    </div>
</div>


<script type="text/javascript" src="{$path_js}/js/wap/login/zepto.min.js"></script>
<script type="text/javascript" src="{$path_js}/js/wap/login/findPwdBySecuCode.js"></script>
<!--forgetPwdUname.html end-->
</body>
</html>
