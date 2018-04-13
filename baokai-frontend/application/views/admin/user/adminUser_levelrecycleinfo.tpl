<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
<div class="col-content">
    <!-- //////////////头部公共页面////////////// -->
    {include file='/admin/left.tpl'}
    <!-- /////////////头部公共页面/////////////// -->
    <div class="col-main">
        <div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/user/">用户中心</a> &gt; 一代回收  </div></div>
        <div class="col-content">
            <div class="col-main">
                <div class="main">
                    <div class="ui-tab">
                        <div class="ui-tab-title clearfix">
                            <ul>
                            <!-- {if "USER_MANAGE_LEVELRECYCLE_MANAGE"|in_array:$smarty.session.datas.info.acls} -->
                                <li class="current"><a href=""></a>回收管理</li>   
                            <!-- {/if} -->
                            <!-- {if "USER_MANAGE_LEVELRECYCLE_HIST"|in_array:$smarty.session.datas.info.acls} -->
                                <li><a href="/admin/user/levelrecyclehistory">回收记录</a></li>   
                            <!-- {/if} -->                            
                            </ul>
                        </div>
                        <div >
                            <form action="levelrecyclelist?search=0" method="get" id="fm_main">
                                <input type="hidden" name="frsearch" value="1">
                            <!-- {if "USER_MANAGE_LEVELRECYCLE_MANAGE_SEARCH"|in_array:$smarty.session.datas.info.acls} -->
                                <ul class="ui-search clearfix">
                                    <li class="name">
                                        <label class="ui-label" for="name" > 用户名:&nbsp; </label>
                                        <input type="text" class="input" id="userName" name="searchtypetxt" placeholder="请输入一代用户名" value="{$searchtypetxt|default:''}">
                                    </li>
                                    <li><input type="submit" value="搜索" class="btn btn-important" id="J-button-submit"/></li>
                                </ul>
                            <!-- {/if} -->
                            </form>
                            {if $userInfo}
                                <form action="applylevelrecycle" method="post" id="levelrecycle_apply">
                                    <table class="table table-info">
                                        <thead>
                                            <tr>
                                                <th>用户名</th>
                                                <th>所属总代</th>
                                                <th>用户组</th>
                                                <th>可用余额</th>
                                                <th>PT游戏余额</th>
                                                <th>最后一次登录时间</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                        <tbody>                                       
                                        <tr>
                                            <td>{$userInfo.account}<!-- {if $userInfo.vipLvl != '' && $userInfo.vipLvl != '0'}-->&nbsp;<img src="{$path_img}/images/ucenter/safe/vip/vip{$userInfo.vipLvl}.png"><!-- {/if} -->
											
											</td>
                                            <td>{$userInfo.topAgent}</td>
                                            <td>{$format_userLvl}</td>
                                            <td>{if $format_availBal<>''}{$format_availBal}{else}0{/if}元</td>
                                            <td>{if $format_availPtBal<>''}{$format_availPtBal}{else}0{/if}元</td>
                                            <td>{$format_lastLoginDate}<br>[{$format_lastLoginIp}] {$userInfo.lastLoginAddress}</td>
                                            <td class="table-tool">
                                            <!-- {if "USER_MANAGE_LEVELRECYCLE_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
                                                    <input type="submit" class="btn btn-important" value="回收">        
                                            <!-- {/if} -->             
                                            </td>
                                        <input type="hidden" name="userId" value='{$userInfo.userId}'>
										<input type="hidden" name="viplevel" value='{$userInfo.vipLvl}'>
										
										<input type="hidden" name="path_img" value='{$path_img}'>
                                        <input type="hidden" name="account" value='{$userInfo.account}'>
                                        <input type="hidden" name="topAgent" value='{$userInfo.topAgent}'>
                                        <input type="hidden" name="availBal" value='{if $userInfo.availBal<>''}{$userInfo.availBal}{else}0{/if}'>
                                        <input type="hidden" name="lastLoginDate" value='{$userInfo.lastLoginDate}'>
                                        <input type="hidden" name="format_lastLoginDate" value='{$format_lastLoginDate}'>
                                        <input type="hidden" name="format_availBal" value='{if $format_availBal<>''}{$format_availBal}{else}0{/if}'>
                                        <input type="hidden" name="format_lastLoginIp" value='{$format_lastLoginIp}'>
                                        <input type="hidden" name="availPtBal" value='{if $userInfo.availPtBal<>''}{$userInfo.availPtBal}{else}0{/if}'>
                                        <input type="hidden" name="format_availPtBal" value='{if $format_availPtBal<>''}{$format_availPtBal}{else}0{/if}'>
                                        <input type="hidden" name="lastLoginAddress" value='{$userInfo.lastLoginAddress}'>
                                        <input type="hidden" name="lastLoginIp" value='{$userInfo.lastLoginIp}'>
                                        </tr>
                                       
                                    </table>
                                </form>
                            </div>
                        </div>
                    {else}
                        <div class="alert alert-waring">
                            <i></i>
                            <div class="txt">
                                <h4>没有符合条件的记录，请更改查询条件！</h4>
                            </div>
                        </div>
                    {/if} 
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
{include file='/admin/script-base.tpl'}
{literal}
    <script >
        (function ($) {
            //一、二级菜单选中样式加载	
            selectMenu('MenuUser', 'MenuUserlevelrecycle');

            $('#userName').focus(function () {
                if ($('#userName')[0].value == '请输入一代用户名') {
                    $("#userName")[0].value = '';
                }

            }).blur(function () {
                var v = $.trim(this.value);
                if (v == '') {
                    $("#userName")[0].value = '请输入一代用户名';
                }

            });
            //表单提交校验
            $('#J-button-submit').click(function (e) {
                if ($('#userName')[0].value == '请输入一代用户名') {
                    $('#userName')[0].value = ''
                }

                return true;
            });


        })(jQuery);
    </script>
{/literal}
</body>
</html>