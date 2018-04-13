<html>
    <head>
        <meta charset="utf-8" />
        <title>活动管理后台</title>
    </head>
    <body style="text-align:center;">
        <div style="margin:0 auto;line-height: 30px">
            每日销量上传<br/><br/><br/>
            <div>
                上传资料表<br/>
                <font color="blue">日期格式: yyyy/mm/dd => 2016/01/25<br/>上传档案名称建议仅使用 => 英文, 数字, -_ 符号</font><br/>
                <form action="/event160125/upload" method="POST" enctype="multipart/form-data">
                <input type="file" name="fileUpload" /> <input type="submit" value="提交" />
                <div><?php echo $msg_upload; ?></div>
                </form>
                <br/><br/>历史上传资料表<br/>
                <table style="border: solid 1px black;" align="center">
                    <?php
                        $dh = opendir("./application/logs/event160125/");
                        while($file = readdir($dh))
                            if(strpos($file, '.csv'))
                                echo '<tr><td>'.$file.'</td><td>'.
                                    date ("Y-m-d H:i:s", filemtime("./application/logs/event160125/".$file)).
                                    '</td><td><input type="button" value="下载" onclick="window.open(\'/event160125/download/'.$file.'\')" /></td></tr>';
                        closedir($dh);
                    ?>
                </table>
            </div>
        </div>

        <div style="position: absolute;z-index: 5;top: 10px;left: 10px;width: 120px;height: 100%;text-align: left;border-right: solid 1px #000000;">
            每日销量上传<br/>
            <a href="/event160125/index/2">用户查询</a><br/>
            <a href="/event160125/index/3">使用者管理</a><br/>
        </div>
    </body>
</html>