<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
<div class="col-content">
    <!-- //////////////头部公共页面////////////// -->
    {include file='/admin/funds/left.tpl'}
    <!-- /////////////头部公共页面/////////////// -->
    <div class="col-main">
        <div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; 充值申诉处理</div></div>
        <div class="col-content">
            <div class="col-main">
                <div class="main">
                    <div  class="ui-tab">	

                        <ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
                            <!-- {if "FUND_RECHARGE_APPREAL_ALL"|in_array:$smarty.session.datas.info.acls} -->
                            <span id="AllData"><a href="/admin/Rechargemange/index?parma=aprlall"><li>全部</li></a></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                            <!-- {if "FUND_RECHARGE_APPREAL_UNTREATED"|in_array:$smarty.session.datas.info.acls} -->
                            <span id="UntreatedData"><a href="/admin/Rechargemange/index?parma=aprluntreated"><li >未处理</li></a></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                            <!-- {if "FUND_RECHARGE_APPREAL_PASSED"|in_array:$smarty.session.datas.info.acls} -->
                            <span id="passed"><a href="/admin/Rechargemange/index?parma=aprlpassed"><li>审核通过</li></a></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                            <!-- {if "FUND_RECHARGE_APPREAL_UNPASSED"|in_array:$smarty.session.datas.info.acls} -->
                            <span id="unpassed"><a href="/admin/Rechargemange/index?parma=aprlunpassed"><li >审核未通过</li></a></span>
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


                            <!-- {if "FUND_RECHARGE_EXCEPTION_ALL_DOWNLOAD"|in_array:$smarty.session.datas.info.acls} -->
                            <a class="btn btn-small " id="downloadReport" style="float:right;display:none;">下载报表<b class="btn-inner"></b></a>
                            <!-- {/if} -->	
                            <!-- {if "FUND_RECHARGE_EXCEPTION_SOLVED_DOWNLOAD"|in_array:$smarty.session.datas.info.acls} -->
                            <a class="btn btn-small " id="downloadReport1" style="float:right;display:none;">下载报表<b class="btn-inner"></b></a>
                            <!-- {/if} -->

                        </ul>
                        <ul class="ui-form ui-tab-content ui-tab-content-current"  name="DivRules">
                            <li>
                                <!-- <h3 class="ui-title"><a class="btn btn-small " id="downloadReport" style="float:left;">下载报表<b class="btn-inner"></b></a></h3>-->
                                <table  id="J-table-data3" class="table table-info table-function">
                                    <thead>
                                        <tr>
                                            <th id="J-sp-serial" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">申诉单号</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" class="input w-2" size="10" maxlength="30"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>   
                                            <th  id="J-sp-account" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">用户名</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" id="account" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
                                            <th  id="J-sp-userlvl" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">用户组</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                            <li data-select-id=""><a href="#">全部</a></li>
                                                            <li data-select-id="0"><a href="#">總代</a></li> 
                                                            <li data-select-id="1"><a href="#">一級代理</a></li>   
                                                            <li data-select-id="2"><a href="#">二級代理</a></li>                                     
                                                        </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>

                                            <th id="J-sp-request-money" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">金额</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-requestmoney1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-requestmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>   
                                            </th>

                                            <th id="J-sp-Daozhang-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">申诉发起时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-Daozhang-time-1"> - <input type="text" id="sp-input-Daozhang-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
                                            <th id="J-sp-isvip" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">是否VIP</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                            <li data-select-id="1"><a href="#">是</a></li>
                                                            <li data-select-id="0"><a href="#">否</a></li>                                                   
                                                        </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>   
                                            <th>状态</th> 
                                            <th>操作</th>
                                            <th>审核人</th>
                                            <th>审核开始时间</th>
                                            <th>审核结束时间</th>											
                                        </tr>
                                    </thead>
                                    <tbody id="showInfo">
                                    </tbody>                                    
                                    <tfoot>
                                        <tr>
                                            <td colspan="22">
                                                <div id="Pagination3" class="pagination" ></div>
                                            </td>
                                        </tr>
                                    </tfoot>
                                </table>
                            </li>						
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    {include file='/admin/script-base.tpl'} 
    {literal}
        <script>
            (function () {
                showTitle(true);
                var msg = new phoenix.Message(), msg1 = new phoenix.Message();
                var isShowCell = false, group = new phoenix.SuperSearchGroup(), isLock = true;
                //Tab	
                var sindex = phoenix.util.getParam("tabIndex");
                sindex = 2;
                new phoenix.Tab({triggers: '.ui-tab-title2 li', panels: '.ui-tab-content', eventType: 'click', currPanelClass: 'ui-tab-content-current', index: sindex});
                //一、二级菜单选中样式加载	
                selectMenu('Menufunds', 'MenuRechargeAppreal');


                //Show显示与隐藏列操作
                if (isShowCell == false) {
                    $('[name="showType"]').hide();
                } else {
                    $('[name="showType"]').show();
                }

                $('#showDiv').click(function () {
                    var showName = $('#showInfo').parent('[name="showType"]').first();
                    if ($('#showDiv').val() == '显示选项') {
                        $('#showDiv').val('隐藏选项');
                        $('[name="showType"]').show();
                        window.location.hash = '#' + showName;
                        isShowCell = true;
                    } else {
                        $('#showDiv').val('显示选项');
                        $('[name="showType"]').hide();
                        isShowCell = false;
                    }
                });

                //输入数据限制
                //允许输入英文字母和数字
                var allowWordAndNumber = function (v) {
                    return v.replace(/[^A-Za-z0-9]/g, '');
                };
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
                passed();
                                

                function passed() {
                    $('#downloadReport').css("display", "none");
                    $('#downloadReport1').css("display", "none");
                    //MOW订号
                    var movnumber = new phoenix.SuperSearch({
                        name: 'movnumber',
                        keyCode: 'ctrl+83',
                        el: '#J-sp-serial',
                        group: group
                    });

                    //用户名
                    var account = new phoenix.SuperSearch({
                        name: 'account',
                        keyCode: 'ctrl+83',
                        el: '#J-sp-account',
                        group: group
                    });

                    //用户组
                    var userlvl = new phoenix.SuperSearch({
                        name: 'userlvl',
                        keyCode: 'ctrl+66',
                        type: 'select',
                        isAutoWidth: true,
                        el: '#J-sp-userlvl',
                        group: group,
                        expands: {getFormValue: getSelectValue}
                    });
                    
                    //金额
                    var requestMoney = new phoenix.SuperSearch({
                        name: 'requestMoney',
                        keyCode: 'ctrl+85',
                        el: '#J-sp-request-money',
                        group: group,
                        expands: {getFormValue: getTowInputValue}
                    });
                    $('#J-input-requestmoney1').keyup(allowNumber).blur(allowNumber);
                    $('#J-input-requestmoney2').keyup(allowNumber).blur(allowNumber);

                    //银行到账时间
                    var Daozhangtime = new phoenix.SuperSearch({
                        name: 'Daozhangtime',
                        keyCode: 'ctrl+68',
                        type: 'time',
                        el: '#J-sp-Daozhang-time',
                        expands: {getFormValue: getTowInputValue},
                        group: group

                    });
                    var DaozhangTime1 = $('#sp-input-Daozhang-time-1'), DaozhangTime2 = $('#sp-input-Daozhang-time-2'), DaozhangDt1, DaozhangDt2;
                    DaozhangTime1.click(function () {
                        DaozhangDt1 = new phoenix.DatePicker({
                            input: DaozhangTime1,
                            isLockInputType: false
                        });
                        DaozhangDt1.show();
                        DaozhangDt1.addEvent('afterSetValue',
                                function () {
                                    DaozhangTime2.focus();
                                    DaozhangTime2.click();
                                })
                    });
                    Daozhangtime.addEvent('afterFocus',
                            function () {
                                DaozhangTime1.click()
                            });
                    DaozhangTime2.click(function () {
                        DaozhangDt2 = new phoenix.DatePicker({
                            input: DaozhangTime2,
                            isLockInputType: false
                        }).show();
                    });
                    Daozhangtime.addEvent('afterBlur', function () {
                        if (DaozhangDt1) {
                            DaozhangDt1.hide();
                        }
                        if (DaozhangDt2) {
                            DaozhangDt2.hide();
                        }
                    });

                    //是否VIP
                    var isvip = new phoenix.SuperSearch({
                        name: 'isvip',
                        keyCode: 'ctrl+66',
                        type: 'select',
                        isAutoWidth: true,
                        el: '#J-sp-isvip',
                        group: group,
                        expands: {getFormValue: getSelectValue}
                    });

                    //状态
                    /*var statusType = new phoenix.SuperSearch({
                        name: 'statusType',
                        keyCode: 'ctrl+66',
                        type: 'select',
                        isAutoWidth: true,
                        el: '#J-sp-Status',
                        group: group,
                        expands: {getFormValue: getSelectValue}
                    });*/



                    //数据处理状态标识(全部)
                    var Title = new phoenix.SuperSearch({
                        name: 'Title',
                        group: group,
                        expands: {getValue: function () {
                                return 2;
                            }}
                    });
                    SelectByWhereInfo3("0");
                    group.removeEvent('dataChange');
                    group.addEvent('dataChange', function () {
                        SelectByWhereInfo3("0");
                    });

                    $("#per_page").change(function () {
                        SelectByWhereInfo3("0");
                    });


                    function SelectByWhereInfo3(pages) {
                        //当前页数据行数
                        var per_page_number = parseInt($("#per_page").val());
                        //放入group对象中(当前页)		
                        var page = new phoenix.SuperSearch({
                            name: 'page',
                            group: group,
                            expands: {getValue: function () {
                                    return pages;
                                }}
                        });
                        //放入group2对象中(当前页行数)	
                        var perPageNum = new phoenix.SuperSearch({
                            name: 'perPageNum',
                            group: group,
                            expands: {getValue: function () {
                                    return per_page_number;
                                }}
                        });

                        var dataArea3 = $('[name="DivRules"]'), formData3 = group.getFormData(), mask = phoenix.Mask.getInstance();
                        $("#J-table-data3>tbody").html("");
                        $("#Pagination3").hide();
                        $.ajax({
                            url: '/admin/Rechargemange/index?parma=aprl&status=2',
                            dataType: 'json',
                            method: 'post',
                            data: formData3,
                            beforeSend: function () {
                                isLock = false;
                                TableStyle("showInfo3", 24, 1, "查询中");
                            },
                            success: function (data) {
                                //debugger
                                if (data.text.length > 0) {
                                    $("#Pagination3").show();
                                    var resultAll = eval(data);
                                    var re = resultAll.text;
                                    var recordNum = 0;
                                    recordNum = resultAll.count[0].recordNum;
                                    //分页回调				 
                                    $("#Pagination3").pagination(recordNum, {
                                        num_edge_entries: 2,
                                        num_display_entries: 8,
                                        current_page: pages,
                                        items_per_page: per_page_number,
                                        callback: SelectByWhereInfo3
                                    });
                                    var html = "";
                                    //数据填充
                                    
                                    $.each(re, function (i) {
                                        html += "<tr><td>" + re[i].appealSn + "</td>";
                                        
										if (re[i].vipLvl != ''){
                                       		html += "<td>" + re[i].account + "&nbsp;<img src="+re[i].path_img+"/images/ucenter/safe/vip/vip"+re[i].vipLvl+".png></td>";
                                        }else {
                                        	html += "<td>" + re[i].account + "</td>";
                                        }

                                        html += "<td>" + re[i].userlvl + "</td>";
                                        html += "<td>" + re[i].appealAmt + "</td>";
                                        html += "<td>" + re[i].appealTime + "</td>";
                                        html += "<td>" + re[i].isvip + "</td>";
                                        html += "<td>" + re[i].status + "</td>";
                                        html += "<td><a class='btn btn-small' href='/admin/Rechargemange/index?parma=review&type=aprlpassed&appealSn=" + re[i].appealSn + "' style='position:initial' ><font color='#25a38a'>查 看</font><b class='btn-inner'></b></a>&nbsp;</td>";
                                        html += "<td>" + re[i].reviewer + "</td>";
                                        html += "<td>" + re[i].reviewStartTime + "</td>";
                                        html += "<td>" + re[i].reviewEndTime + "</td>";
                                    });
                                    
                                    $("#J-table-data3>tbody").html(html);

                                    if (isShowCell == false) {//状态判定加载相关数据		
                                        $('[name="showType"]').hide();
                                    } else {
                                        $('[name="showType"]').show();
                                    }

                                } else {
                                    $("#Pagination3").hide();
                                    TableStyle("J-table-data3>tbody", 24, 2, "没有相应数据");
                                }
                            },
                            complete: function ()
                            {
                                isLock = true;
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                TableStyle("J-table-data3>tbody", 24, 2, "数据异常");
                            }
                        });
                    }
                }

               
            })();
        </script>
    {/literal}
</body>
</html>