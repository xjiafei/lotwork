<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no"/>
    <title>找回密码</title>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/common.css"/>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/resetPwd.css"/>
<body>
<!--forgetPwdUname.html begin-->
<div class="container">
    <div class="page login_page">
        <!--title 部分-->
        <div class="title"><span></span>找回密码</div>
        <!--logo 部分-->
        <h1 class="logo"><!--<p class="v"></p>--></h1>

        <div class="step"></div>

        <p class="tipinput">设置新的登录密码</p>


        <p class="text">请输入新的密码</p>
        <div class="login_form login_unique resetPwd">
            <ol class="inputOL">
                <li>
                    <input type="password" name="pwd1" placeholder="6-20位字符组成,区分大小写" maxLength="20" id="pwd1"/>
                </li>
            </ol>
        </div>
        <div class="login_tips">
            <span id="tip1"></span>
        </div>
        <p class="text">请再次输入登录密码</p>
        <div class="login_form login_unique resetPwd">
            <ol class="inputOL">
                <li>
                    <input type="password" name="pwd2" placeholder="请再次输入登录密码" maxLength="20" id="pwd2"/>
                </li>
            </ol>
        </div>
        <div class="login_tips">
            <span id="tip2"></span>
        </div>

        <!--登录按钮-->
        <button class="btn_login" id="next">确定</button>
    </div>
</div>


<script type="text/javascript" src="{$path_js}/js/wap/login/zepto.min.js"></script>
<script type="text/javascript" src="{$path_js}/js/wap/login/resetPwd.js"></script>
<!--forgetPwdUname.html end-->
</body>
</html>
