<!DOCTYPE HTML>
<html lang="en-US">
    <head>
        <meta charset="UTF-8">
        <title>提现自助申诉</title>
        <link rel="stylesheet" href="{$path_img}/images/common/base.css" />
        <link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
        <link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
        <link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />	
        
        {include file='/default/script-base.tpl'}
        <script src="{$path_js}/js/phoenix.Common.js" type="text/javascript" ></script>
        <script type="text/javascript" src="{$path_js}/js/userCenter/uploadFile.js"></script>
        <style>
            .btn-mini{
                height:23px;
                line-height: 23px;
                font-size: 12px;
                padding-left: 5px;
                padding-right: 5px;
                margin-left:10px;
                vertical-align:bottom;
            }
        </style>
    </head>
    <body>
        <!-- header start -->
        {include file='/default/ucenter/header.tpl'}
        <!-- header end -->
        <div class="g_33 common-main">
            <div class="g_5">
                <!-- //////////////左侧公共页面////////////// -->
                {include file='/default/ucenter/left.tpl'}
                <!-- /////////////左侧公共页面/////////////// -->
            </div>
            <div class="g_28 g_last">
                <div class="common-article">
                    <div class="title">自助申诉</div>
                    <div class="content">
                        <ul class="ui-form">
                            <li>
                                <label class="ui-label">提款用户名：</label>
                                <span class="ui-singleline">{$applyAccount}</span>
                            </li>
                            <li>
                                <label class="ui-label">提款时间：</label>
                                <span class="ui-singleline">{$applyTime}</span>
                            </li>
                            <li>
                                <label class="ui-label">提款金额：</label>
                                <span class="ui-singleline">{$withdrawAmt}</span>
                            </li>
                            <li>
                                <label class="ui-label">提款银行：</label>
                                <span class="ui-singleline">{$bankName}</span>
                            </li>
                            <li>
                                <label class="ui-label">提款卡号：</label>
                                <span class="ui-singleline">{$cardNumber}</span>
                            </li>
                            <li>
                                <label class="ui-label">提款卡姓名：</label>
                                <span class="ui-singleline">{$bankAccount}</span>
                            </li>	
                            <li>							
                                <input id="drawsn" type="hidden" value={$withdrawSn}>
                                
                            </li>		

                            <li style="width: 80%; margin-left:10%;">
                                <dl class="prompt">
                                    <dt>温馨提示：</dt>
                                    <dd>请您按照查询汇款回执单步骤，查找到您最近一笔充值的汇款回执单，然后使用截图的方式保存至桌面，点击上传图片，选择文件后点击提交，请务必确认回执单是完整有效的，以便相关部门尽快为您处理。</dd>
                                </dl>
                            </li>
                            <li>
                                <label class="ui-label">回执单截图：</label>
                                <span id="uploadFile"></span>
                                <span class="ui-text-prompt">（*最多上传3张快捷充值截图） <a target="_blank" href="/help/queryGeneralHelp?cateId2=571&cate2Name=在线充值汇款回执单">如何查询汇款回执单</a></span>
                            </li>
                            <li id="photoBox" style="height:100px; display:none">
                                <label class="ui-label"></label>
                            </li>
                            <li class="ui-btn">
                                <a href="javascript:void(0);" name="successubmit" class="btn btn-important">提 交<b class="btn-inner"></b></a>
                                <a href="javascript:void(0);" name="cancelsubmit" class="btn margin-left-20">取 消<b class="btn-inner"></b></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- //////////////底侧公共页面////////////// -->
        {include file='/default/ucenter/footer.tpl'}
        <!-- /////////////底侧公共页面/////////////// -->
        <!--弹框-->
        <div id="errorAppealtips" style="position:absolute;left:100px; display:none;" class="pop w-7">
            <div class="hd"><i class="close" name="closeBox2"></i>温馨提示</div>
            <div class="bd">
                <div class="pop-title">				
                    <h4 class="pop-text">您的申诉申请提交失败！</h4>
                </div>
                <div class="pop-btn">
                    <a class="btn btn-important " name="secconfiom2" href="javascript:void(0);">确 定<b class="btn-inner"></b></a>
                </div>
            </div>
        </div>
        <!--弹框-->
        <div id="successAppealtips" style="position:absolute;left:500px; display:none;" class="pop w-7">
            <div class="hd"><i class="close" name="closeBox1"></i>温馨提示</div>
            <div class="bd">
                <div class="pop-title">			
                    <h4 class="pop-text">您的申诉申请已经提交申请，客服人员会尽快处理您的申请！<a href="#" name="secconfiom1">查看申诉进度</a></h4>
                </div>
                <div class="pop-btn">
                    <a class="btn btn-important "  name="secconfiom1" href="javascript:void(0);">确定<b class="btn-inner"></b></a>
                </div>
            </div>
        </div>
    </body>
    {literal}
        <script>
            (function () {

                var option = {zIndex: 500},
                box1 = new LightBox("successAppealtips", option),
                box2 = new LightBox("errorAppealtips", option);

                var fileUploads = (new UploadFile({
                    url: '/fundappeal/upload', 
                    fileBlock: $('[id="uploadFile"]'), 
                    photoBox: $('[id="photoBox"]'), 
                    maxUploads: 3,
                    submitBtn:$('<input type="button" class="btn btn-mini" value="上传照片"/>')
                }));
                //查看详情
                $('[name="successubmit"]').bind('click', function (e) {
                	//檔案上傳過才可提交
                	if(fileUploads.submited()){
                		var drawsn = $('#drawsn').val();
                        var sdata = {sn:drawsn};
                        $.ajax({
                            url: '/fundappeal/appealselfstart?' + sdata,
                            method: 'post',
                            dataType: 'json',
                            data: sdata,
                            success: function (data) {
                                if (data.isUpdateState == "ok") {
                                    box1.OverLay.Color = "rgb(51, 51, 51)";
                                    box1.Over = true;
                                    box1.OverLay.Opacity = 50;
                                    box1.Fixed = true;
                                    box1.Center = true;
                                    box1.Show();
                                }
                                else if (data.isUpdateState == "error") {
                                    box2.OverLay.Color = "rgb(51, 51, 51)";
                                    box2.Over = true;
                                    box2.OverLay.Opacity = 50;
                                    box2.Fixed = true;
                                    box2.Center = true;
                                    box2.Show();
                                }
                            },
                            error: function (xhr, ajaxOptions, thrownError) {
                                box2.OverLay.Color = "rgb(51, 51, 51)";
                                box2.Over = true;
                                box2.OverLay.Opacity = 50;
                                box2.Fixed = true;
                                box2.Center = true;
                                box2.Show();
                            }
                        });
                	}else{
                		alert('提交前请先上传回执单');
                	}

                });
				
                $('[name="closeBox1"]').click(function (e) {
                    box1.Close();
                	window.location.assign("/fundappeal/appealwithdrawlist");
                });

                $('[name="secconfiom1"]').click(function (e) {
                	window.location.assign("/fundappeal/appealstatuslist");
                });
				
                //取消
                $('[name="cancelsubmit"]').bind('click', function (e) {
                	window.location.assign("/fundappeal/appealwithdrawlist");
                });
                
                $('[name="closeBox2"]').bind('click', function (e) {
                    box2.Close();
                });

                $('[name="secconfiom2"]').bind('click', function (e) {
                	window.location.assign("/fundappeal/appealwithdrawlist");
                });

                
            })();
        </script>
    {/literal}
</html>