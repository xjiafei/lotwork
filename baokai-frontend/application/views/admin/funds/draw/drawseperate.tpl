<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
<div class="col-content">
    <!-- //////////////头部公共页面////////////// -->
    {include file='/admin/funds/left.tpl'}
    <!-- /////////////头部公共页面/////////////// -->
    <div class="col-main">
        <div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; <span id="titleName">提现相关配置</span></div></div>
        <div class="col-content">
            <div class="col-main">
                <div class="main">
                    <div class="ui-tab">
                        {include file='/admin/funds/draw/base/drawTip.tpl'}
                        <input type="hidden" id="tipIndex" name="tipIndex" value="{$tipIndex}"/>
                        <ul  style="overflow: hidden;">
                            <li>
                                <form action="/admin/Fundconfig/index?parma=wcf7" method="post" id="subForm">
                                    <table class="table table-info table-border">
                                        <thead>
                                            <tr>
                                                <th colspan="3" class="text-left">大额提现拆单配置</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td class="text-left w-4">拆单起始额度</td>
                                                <td class="text-left">
                                                    <input type="text" value="{$seperateAmt}" maxlength="10" class="input w-2" name="seperateAmt">元
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="text-left w-4">最高单笔金额</td>
                                                <td class="text-left">
                                                    <input type="text" value="{$singleCut}" maxlength="10" class="input w-2" name="singleCut">元
                                                    <div class="ui-check"><i class="error"></i>单笔额度必须小于起始额度</div>
                                                </td>
                                            </tr>
                                            <!-- {if "FUND_WITHDRAW_SEPERATE_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
                                            <tr>
                                                <td class="text-left"></td>
                                                <td colspan="2">
                                                    <input type="button" id="btnsubmit" class="btn btn-important"  value="保存"  style="width:80px;"/>
                                                    <input type="button" id="cancelBtn" class="btn" name="" value="撤销编辑"  style="width:100px;"/>
                                                </td>
                                            </tr>
                                            <!-- {/if} -->
                                        </tbody>
                                    </table>
                                </form>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
{include file='/admin/script-base.tpl'}
{literal}
    <script>
        $("#cancelBtn").bind("click", function () {
            window.location = "/admin/Fundconfig/index?parma=wcf7";
        });

        (function () {

            //一、二级菜单选中样式加载	
            selectMenu('Menufunds', 'MenuWithdrawalsConfig');
            var tipIndex = $("#tipIndex").val();
            $("#drawcfg_"+tipIndex).addClass("current");
            $("#btnsubmit").click(function () {
                var seperateAmt = $('input[name="seperateAmt"]').val();
                var singleCut = $('input[name="singleCut"]').val();
                if(seperateAmt < singleCut ){
                    $('.ui-check').css('display', 'inline');
                    return;
                }
                $("#subForm").submit();
            });
            $('.ui-tab-title li').click(function(){
				var indexs = $(this).val();
                window.location.href="/admin/Fundconfig/index?parma=wcf"+(parseInt(indexs));
            });
            $("input[type='text']").on("keyup", function ()
            {
                $(this).val($(this).val().replace(/[^0-9]/g, ''));
            }).on("blur", function () {
                $(this).val($(this).val().replace(/[^0-9]/g, ''));
            });

        })();
    </script>
{/literal}
</body>
</html>