<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
<div class="col-content">
    <!-- //////////////头部公共页面////////////// -->
    {include file='/admin/left.tpl'}
    <!-- /////////////头部公共页面/////////////// -->
    <style>
        .showText label{	
            display:none;
            color:#fff;
            background:rgba(51,51,51,0.75);
            padding:20px;
            border-radius:4px;
            -moz-border-radius:4px;
            -webkit-border-radius:4px;
            width: 300px;
            white-space:pre-wrap;
            position: absolute;
            z-index:10;
            overflow:auto;
        }
        .showText { display: inline-block;}
        .showText:hover label{ display:block; }
    </style>
    <div class="col-main">
        <div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/user/">用户中心</a> &gt; 一代回收  </div></div>
        <div class="col-content">
            <div class="col-main">
                <form action="levelrecyclehistory" method="get" id="J-form">
                    <div class="main">
                        <div class="ui-tab">
                            <div class="ui-tab-title clearfix">
                                <ul>
                                <!-- {if "USER_MANAGE_LEVELRECYCLE_MANAGE"|in_array:$smarty.session.datas.info.acls} -->
                                    <li><a href="/admin/user/levelrecycle?type=list">回收管理</a></li>
                                <!-- {/if} -->
                                <!-- {if "USER_MANAGE_LEVELRECYCLE_HIST"|in_array:$smarty.session.datas.info.acls} -->
                                    <li class="current"><a href=""></a>回收记录</li>
                                <!-- {/if} -->                                       
                                </ul>
                            </div>
                            <div >
                                <input type="hidden" name="frsearch" value="1">
                            <!-- {if "USER_MANAGE_LEVELRECYCLE_HIST_SEARCH"|in_array:$smarty.session.datas.info.acls} -->
                                <ul class="ui-search clearfix">
                                    <li class="name">
                                        <label class="ui-label" for="name" > 用户名:&nbsp; </label>
                                        <input type="text" class="input" id="userName" name="searchtypetxt" placeholder="请输入一代用户名" value="{$searchtypetxt|default:''}">
                                    </li>
                                    <li><input type="submit" value="搜索" class="btn btn-important" id="J-button-submit"/></li>
                                </ul>
                            <!-- {/if} -->
                            <!-- {if "USER_MANAGE_LEVELRECYCLE_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
                                    <input type="hidden" id="canRedoTarget" value="1">       
                            <!-- {else} -->
                                    <input type="hidden" id="canRedoTarget" value="0">        
                            <!-- {/if} -->
                                {if $levelRecycleHistoryList}
                                    <table class="table table-info" id="levelRecycle-table">
                                        <thead>
                                            <tr>
                                                <th>用户名</th>
                                                <th>所属总代</th>
                                                <th>最后一次登录时间</th>
                                                <th>申请时间</th>
                                                <th>生效时间</th>
                                                <th>平台余额</th>
                                                <th>PT金额</th>
                                                <th>回收原因</th>
                                                <th>操作人</th>
                                                <th>状态</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <!-- {foreach from=$levelRecycleHistoryList item=historyList key=i} -->
                                            <tr>
                                                <td>{$historyList.account}
												<!-- {if $historyList.vipLvl != ''} -->&nbsp;<img src="{$path_img}/images/ucenter/safe/vip/vip{$historyList.viplevel}.png"><!-- {/if} -->
												</td>
                                                <td>{$historyList.topAgent}</td>
                                                <td>{$historyList.format_lastLoginDate}<br>[{$historyList.format_lastLoginIp}] {$historyList.lastLoginAddress}</td>
                                                <td>{$historyList.createDate}</td>
                                                <td>{$historyList.activityDate}</td>
                                                <td>{if $historyList.availBal<>''}{$historyList.availBal}{else}0{/if}元</td>
                                                <td>{if $historyList.availPtBal<>''}{$historyList.availPtBal}{else}0{/if}元</td>
                                                <td>{if $historyList.recycleReason eq $historyList.origRecycleReason}{$historyList.recycleReason}
                                                    {else}<a class="showText">{$historyList.recycleReason}<label>{$historyList.origRecycleReason}</label></a>{/if}</td>
                                                <td>{$historyList.operator}</td>
                                                <td>{if $historyList.taskStatus == '0' || $historyList.taskStatus == '1'}处理中
                                                {else if $historyList.taskStatus == '2' && $historyList.recycleStatus == '11111111'}回收成功
                                                {else if $historyList.taskStatus == '2' && $historyList.recycleStatus != '11111111'}
                                                    <a href='javascript:showRecycleStatus({$i});' class="row-action-status">部份成功</a>
                                                {else}回收失败
                                                {/if}</td>
                                    <input type="hidden" id='id{$i}' value='{$historyList.id}'>
                                    <input type="hidden" id='account{$i}' value='{$historyList.account}'>
                                    <input type="hidden" id='recycleStatus{$i}' value='{$historyList.recycleStatus}'>
                                    <input type="hidden" id='userId{$i}' value='{$historyList.userId}'>
                                    <input type="hidden" id='operator' value='{$historyList.operator}'>
                                    </tr>
                                    <!-- {/foreach} -->
                                </table>
                            </div>
                        </div>
                        {if $pages}
                            <div class="page-wrapper clearfix">
                                <span class="">共{$pages.count}条记录</span>
                                <div class="page page-right"> 
                                    {if $pages.pre && $pages.currpage.index!=1}
                                        <a class="prev" onClick="smtByPager({$pages.pre.index});" href="javascript:void(0);">上一页</a>
                                    {/if}
                                    {foreach from=$pages.steps item=item}
                                        {if $item.index == $pages.currpage.index}
                                            <a class="current" href="javascript:void(0);">{$item.text}</a>
                                        {else}
                                            <a onClick="smtByPager({$item.index});" href="javascript:void(0);">{$item.text}</a>
                                        {/if}
                                    {/foreach}
                                    {if $pages.next && $pages.currpage.index!=$pages.max.index}
                                        <a class="next" onClick="smtByPager({$pages.next.index});" href="javascript:void(0);">下一页</a>
                                    {/if}
                                    <span class="page-few">到第 <input type="text" name="page" id="page" value="{$pages.currpage.text}" class="input"> /{$pages.max.text}页</span>
                                    <input type="button" value="确 认" class="page-btn" onClick="$('#J-form').submit();">
                                </div>
                            </div>
                        {/if}
                        {else}
                            <div class="alert alert-waring">
                                <i></i>
                                <div class="txt">
                                    <h4>没有符合条件的记录，请更改查询条件！</h4>
                                </div>
                            </div>
                            {/if}
                            </div>
                        </form>

                    </div>
                </div>
            </div>
            <div class="pop pop-success w-4" style="position:absolute;left:900px;display:none;z-index: 800;" id="divApplySuccess">
            <i class="ico-success"></i><h4 class="pop-text">申请成功</h4>
    </div>
        </div>        
        {include file='/admin/script-base.tpl'}
        <script src="{$path_js}/js/userCenter/levelRecycle.js" type="text/javascript" ></script>
        {literal}            
            <script >
                (function ($) {
                    //一、二级菜单选中样式加载	
                    selectMenu('MenuUser', 'MenuUserlevelrecycle');

                    //表单提交校验
                    $('#J-button-submit').click(function (e) {
                        if ($('#userName')[0].value == '请输入您的用户名') {
                            $('#userName')[0].value = ''
                        }

                        return true;
                    });


                })(jQuery);

                function popupwindow() {
                    ('#recycleStatusView').show();
                }

                function smtByUser(id, name) {
                    $("#J-form").submit();
                }
                function smtByPager(pageIndex) {
                    $("#page").val(pageIndex);
                    $("#J-form").submit();
                }
                function changePageSize() {
                    smtByPager(1);
                }
            </script>
        {/literal}
    </body>
</html>