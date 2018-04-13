<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no"/>
    <title>普通登录</title>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/common.css"/>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/forgetPwdUname.css"/>
<body>
<!--forgetPwdUname.html begin-->
<div class="container">
    <div class="page login_page">
        <!--title 部分-->
        <div class="title"><span></span>忘记密码</div>
        <!--logo 部分-->
        <h1 class="logo"><!--<p class="v"></p>--></h1>

        <!-- 普通登录或安全登录按钮部分-->

        <div class="step"></div>
        <p class="tipinput">请输入用户名和验证码</p>

        <!--安全登录模式表单用户名-->
        <div id="findpwd_username" class="login_form login_unique">
            <ol class="inputOL">
                <li>
                    <span>账户</span>
                    <input type="text" name="username" placeholder="请输入您的用户名" maxLength="16" id="username"/>
                    <b></b>
                </li>
                <li>
                    <span>验证码</span>
                    <input type="text" name="vcode" placeholder="请输入验证码" maxLength="4" class="vcode" id="vcode"/>
                    <img id="J-vcode-img" title="看不清，请点击更换图片"  class="verify-code" src="{$imageurl}" alt="验证码"  data-src-path="{$imageurl}" />
                    <i class="error"></i>
                </li>
            </ol>
        </div>

        <!--忘记密码和输入错误提示-->
        <div class="login_tips">
            <span></span>
        </div>

        <!--登录按钮-->
        <button class="btn_login" id="next">下一步</button>
        <div class="backIndex">返回登陆页</div>
    </div>
</div>


<script type="text/javascript" src="{$path_js}/js/wap/login/zepto.min.js"></script>
<script type="text/javascript" src="{$path_js}/js/wap/login/forgetPwdUname.js"></script>
<!--forgetPwdUname.html end-->
</body>
</html>
