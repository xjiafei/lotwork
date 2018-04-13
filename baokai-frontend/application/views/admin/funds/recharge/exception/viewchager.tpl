<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
<div class="col-content">
    <!-- //////////////头部公共页面////////////// -->
    {include file='/admin/funds/left.tpl'}
    <!-- /////////////头部公共页面/////////////// -->
    <style>
        .btn-disable{
            pointer-events: none;
        }
    </style>
    <div class="col-main">
        <div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; {$title}</div></div>
        <div class="col-content">
            <div class="col-main">
                <div class="main">
                    <div  class="ui-tab">	

                        <ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
                            <!-- {if "FUND_RECHARGE_EXCEPTION_ALL"|in_array:$smarty.session.datas.info.acls} -->
                            <span id="AllData"><li>全部</li></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                            <!-- {if "FUND_RECHARGE_EXCEPTION_UNTREATED"|in_array:$smarty.session.datas.info.acls} -->
                            <span id="UntreatedData"><li >未处理</li></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                            <!-- {if "FUND_RECHARGE_EXCEPTION_REVIEW"|in_array:$smarty.session.datas.info.acls} -->
                            <span id="PendingReviewData"><li>待复审</li></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                            <!-- {if "FUND_RECHARGE_EXCEPTION_HANDLING"|in_array:$smarty.session.datas.info.acls} -->
                            <span id="ProcessingData"><li >处理中</li></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                            <!-- {if "FUND_RECHARGE_EXCEPTION_SOLVED"|in_array:$smarty.session.datas.info.acls} -->
                            <span id="CompletedData"><li >已完成</li></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->

                            <a>每页记录数:</a>
                            <select class="ui-select w-2" id="per_page">
                                <option  value="25">25</option>
                                <option value="50" selected="">50</option>
                                <option value="100">100</option>
                                <option value="200">200</option>	
                            </select>			
                            <!-- {if "FUND_RECHARGE_EXCEPTION_DISPLAYCOLS"|in_array:$smarty.session.datas.info.acls} -->                           
                            <a type="button" id="showDiv" class="btn btn-small" style="float:right;"  >显示选项<b class="btn-inner"></b></a>
                            <!-- {/if} -->

                            <!-- {if "FUND_RECHARGE_EXCEPTION_ALL_DOWNLOAD"|in_array:$smarty.session.datas.info.acls} -->
                            <a class="btn btn-small " id="downloadReport" style="float:right;display:none;">下载报表<b class="btn-inner"></b></a>
                            <!-- {/if} -->	
                            <!-- {if "FUND_RECHARGE_EXCEPTION_SOLVED_DOWNLOAD"|in_array:$smarty.session.datas.info.acls} -->
                            <a class="btn btn-small " id="downloadReport1" style="float:right;display:none;">下载报表<b class="btn-inner"></b></a>
                            <!-- {/if} -->
                            <!-- {if "FUND_RECHARGE_EXCEPTION_UNTREATED_DOWNLOAD"|in_array:$smarty.session.datas.info.acls} -->
                            <a class="btn btn-small " id="downloadReport2" style="float:right;display:none;">下载报表<b class="btn-inner"></b></a>
                            <!-- {/if} -->
                        </ul>
                        <!-------------------------全部-------------------------------------------->
                        {include file='/admin/funds/recharge/exception/viewchager_all.tpl'}
                        <!-------------------------未处理------------------------------------------>       
                        {include file='/admin/funds/recharge/exception/viewchager_untreated.tpl'}
                        <!-------------------------待复审------------------------------------------>
                        {include file='/admin/funds/recharge/exception/viewchager_pendingReview.tpl'}
                        <!-------------------------处理中------------------------------------------>
                        {include file='/admin/funds/recharge/exception/viewchager_processing.tpl'}
                        <!-------------------------已完成------------------------------------------>
                        {include file='/admin/funds/recharge/exception/viewchager_complete.tpl'}
                    </div>
                </div>
            </div>
        </div>
    </div>
    {include file='/admin/script-base.tpl'}
    {literal}
        <script>
            var msg = new phoenix.Message(), msg1 = new phoenix.Message();
            var isShowCell = false, group = new phoenix.SuperSearchGroup();
            //Tab	
            var sindex = phoenix.util.getParam("tabIndex"), isLock = true;
            sindex = sindex == null ? 1 : sindex;
            new phoenix.Tab({triggers: '.ui-tab-title2 li', panels: '.ui-tab-content', eventType: 'click', currPanelClass: 'ui-tab-content-current', index: sindex});
            //一、二级菜单选中样式加载	
            selectMenu('Menufunds', 'MenuRechargemange');
            //允许输入数字和小数
            var allowNumber = function () {
                var me = this, v = me.value, index;
                me.value = v = v.replace(/^\.$/g, '');
                index = v.indexOf('.');
                if (index > 0) {
                    me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');
                }
                me.value = v = v.replace(/[^\d|^\.]/g, '');
                me.value = v = v.replace(/^00/g, '0');
                if (v.split('.').length > 2) {
                    arguments.callee.call(me);
                }
            };
            //获取两个input值
            var getTowInputValue = function () {
                var me = this, ipts = me.dom.find('input[type="text"]'), name = me.name, result = {};
                result[name + '1'] = ipts[0].value;
                result[name + '2'] = ipts[1].value;
                return result;
            };
            //获取select类型值
            var getSelectValue = function () {
                var me = this, li = me.dom.find('li.current'), v = '', result = {};
                if (li.size() > 0) {
                    v = li.eq(0).attr('data-select-id');
                    result[me.name] = v;
                } else {
                    //初始化给空
                    result[me.name] = '';
                }
                return result;
            };


            //Show显示与隐藏列操作
            if (isShowCell == false) {
                $('[name="showType"]').hide();
            }
            else {
                $('[name="showType"]').show();
            }

            $('#showDiv').click(function () {
                var showName = $('#showInfo').parent('[name="showType"]').first();
                if ($('#showDiv').val() == '显示选项') {
                    $('#showDiv').val('隐藏选项');
                    $('[name="showType"]').show();
                    window.location.hash = '#' + showName;
                    isShowCell = true;
                }
                else {
                    $('#showDiv').val('显示选项');
                    $('[name="showType"]').hide();
                    isShowCell = false;
                }
            });

            if (sindex == '0') {
                AllData();
            } else if (sindex == '1') {
                UntreatedData();
            } else if (sindex == '2') {
                PendingReviewData();
            } else if (sindex == '3') {
                ProcessingData();
            } else {
                CompletedData();
            }
            //AllData();
            //全部数据查询
            $('#AllData').click(function () {
                AllData();
            });
            //未处理查询
            $('#UntreatedData').click(function () {
                UntreatedData();
            });
            //待复审查询
            $('#PendingReviewData').click(function () {
                PendingReviewData();
            });
            //处理查询
            $('#ProcessingData').click(function () {
                ProcessingData();
            });
            //已完查询
            $('#CompletedData').click(function () {
                CompletedData();
            });

            //下载报表	
            $('#downloadReport,#downloadReport1,#downloadReport2').click(function () {
                //$.get("/admin/Rechargemange/index?parma=ex",group.getFormData());
                var data = group.getFormData();
                var param = '';
                var p = '';
                for (p in data) {
                    if (data.hasOwnProperty(p)) {
                        param += '&' + p + '=' + data[p];
                    }
                }
                window.open("/admin/Rechargemange/index?parma=ex" + param);
            });
        </script>
    {/literal}
</body>
</html>