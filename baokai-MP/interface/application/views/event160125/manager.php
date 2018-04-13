<html>
    <head>
        <meta charset="utf-8" />
        <title>活动管理后台</title>
    </head>
    <body style="text-align:center;">
        <div style="margin:0 auto;line-height: 30px">
            使用者管理<br/>
            <form action="/event160125/pass" method="POST">
                主管理者<input type="text" disabled="disabled" value="<?php echo $this->Mem_cache_model->get("admin_user"); ?>" /><br/>
                修改密码<input type="password" name="pass" /><br/>
                <div style="color:red"><?php echo $msg_pass; ?></div>
                <input type="submit" value="提交" />
            </form>
        </div>

        <div style="position: absolute;z-index: 5;top: 10px;left: 10px;width: 120px;height: 100%;text-align: left;border-right: solid 1px #000000;">
            <a href="/event160125">每日销量上传</a><br/>
            <a href="/event160125/index/2">用户查询</a><br/>
            使用者管理<br/>
        </div>
    </body>
</html>