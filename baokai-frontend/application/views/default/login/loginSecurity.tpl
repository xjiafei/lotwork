<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no"/>
    <title>安全登录</title>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/common.css"/>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/loginSecurity.css"/>
<body>
<!--loginSecutiry begin-->
<div class="container">
    <div class="page login_page">
        <!--title 部分-->
        <div class="title">安全登录</div>
        <!--logo 部分-->
        <h1 class="logo"></h1>

        <!-- 普通登录或安全登录按钮部分-->
        <p class="input_box">
            <a class="btn_loginType" href="/login/index">普通登录</a>
            <span class="btn_loginType btn_loginType_active">安全登录</span>
        </p>

        <!--安全登录模式表单用户名-->
        <div id="login_security_form_username" class="login_form login_unique">
            <ol class="inputOL">
                <li>
                    <span>账户</span>
                    <input type="text" name="username" placeholder="请输入您的用户名" maxLength="16" id="username"/>
                    <b></b>
                </li>
                <li>
                    <input type='text' name='vcode' style='display:none;'/>
                    <input type="text" name="vcode" placeholder="请输入验证码" maxLength="4" class="vcode" id="vcode"/>
                    <i></i>
                    <img src="/login/changevcode" alt="验证码图片"/>
                </li>
            </ol>
        </div>

        <!--安全登录模式表单密码-->
        <div id="login_security_form_pwd" class="login_unique">
            <div class="pretxt1">
            <p>您设置的预留信息为：<span id = "ciphertext"></span></p>
            <p id = "cipher1">若预留验证信与您设置不一致！ 请勿输入密码！</p>
            </div>
            <div class="pretxt2">
                <p  id = "cipher2">您尚未设置验证信息，请登录成功后前往安全中心设置!</p>
                <!--<p>若预留验证信与您设置不一致！ 请勿输入密码！</p>-->
            </div>
            <div class="login_form">
                <ol class="inputOL">
                    <li>
                        <span>密码</span>
                        <input type="password" name="pwd" id="pwd" placeholder="请输入您的密码"/>
                        <input type="hidden" name="param" id="param" style="display: none" value="{$loginRand}"/>
                        <b></b>
                    </li>
                </ol>
            </div>
        </div>

        <!--忘记密码和输入错误提示-->
        <div class="login_tips">
            <span></span><a href="/login/forgetpwd" title="忘记密码">忘记密码?</a>
        </div>

        <!--登录按钮-->
        <button class="btn_login" id="next">下一步</button>
        <button class="btn_login" id="login">登录</button>

        <!--下载链接-->
        <div class="link_downLoadApp"><a href="/login/download/" title="下载app">下载APP</a></div>
    </div>
</div>


<script type="text/javascript" src="{$path_img}/js/wap/login/zepto.min.js"></script>
<script type="text/javascript" src="{$path_img}/js/wap/login/zepto.md5.js"></script>
<script type="text/javascript" src="{$path_img}/js/wap/login/loginSecurity.js"></script>
<!--loginSecutiry end-->
</body>
</html>
