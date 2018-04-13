<!-------------------------全部------------------------------------------>
<ul class="ui-form ui-tab-content"  name="DivRules">
    <li>
        <!-- <h3 class="ui-title"><a class="btn btn-small " id="downloadReport" style="float:left;">下载报表<b class="btn-inner"></b></a></h3>-->
        <table  id="J-table-data" class="table table-info table-function">
            <thead>
            <th id="J-sp-serial" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">MOW异常订单号</div>
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
            <th>附言</th>                                               
            <th id="J-sp-Daozhang-time" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">银行到账时间</div>
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
            <th>手续费</th>
            <th>收款银行</th>                                               
            <th  id="J-sp-receivablesCard" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">收款卡</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" id="receivablesCard" class="input w-2"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>	                                               
            <th  id="J-sp-palyBankName" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">付款银行</div>
                <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                    <ul>
                        <!-- {foreach from=$bankArray item=data key=key} -->
                        <li data-select-id="{$key}"><a href="#">{$data.name}</a></li>
                        <!-- {/foreach} -->
                    </ul>
                </div>
                <span class="sp-filter-close"></span>
            </div>
            </th>			
            <th>付款卡</th>                                       
            <th  id="J-sp-palyBankUserName" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">付款户名</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" id="palyBankUserName" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>		
            <th>充值渠道</th>
            <th>充值异常原因</th>
            <th>交易单号</th>
            <th>材料</th>                                               
            <th id="J-sp-AuditOne-time" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">一审时间</div>
                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" tabindex="1" class="input w-2" id="sp-input-AuditOne-1"> - <input type="text" id="sp-input-AuditOne-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>                                               
            <th  id="J-sp-auditAdmin" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">一审管理员</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" id="auditAdmin" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>	

            <th id="J-sp-Status" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">状态</div>
                <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                    <ul>
                        <!-- {foreach from=$aRechargeStatus key=key item=data} -->
                        <li data-select-id="{$key}"><a href="#">{$data}</a></li>
                        <!-- {/foreach} --> 
                    </ul>
                </div>
                <span class="sp-filter-close"></span>
            </div>
            </th>      
            <th>备注</th>
            <th name="showType">充值用户</th>
            <th name="showType">所属总代</th>
            <th name="showType">开户行</th>
            <th name="showType">开户行地址</th>
             <th class="sp-td" id="J-sp-td-OperatingTime">                                    
                <div class="sp-td-cont sp-td-cont-b">
                    <div class="sp-td-title">DP操作时间</div>
                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                        <li>
                            <div class="input-append">
                                    <input type="text" tabindex="1" class="input w-2" id="sp-input-OperatingTime-time-1"> - <input type="text" id="sp-input-OperatingTime-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                            </div>
                        </li>
                    </ul>
                <span class="sp-filter-close"></span>
                </div>                                                        
            </th>
            </thead>
            <tbody id="showInfo">
            </tbody>                                    
            <tfoot>
                <tr>
                    <td colspan="22">
                        <div id="Pagination" class="pagination" ></div>
                    </td>
                </tr>
            </tfoot>
        </table>
        <script type="text" id="data_item_all">
            <tr>
                <td>[[mcSn]]</td>
                <td>[[memo]]</td>
                <td>[[realChargeTime]]</td>
                <td>[[realChargeAmt]]</td>
                <td>[[mcBankFee]]</td>
                <td>[[rcvBank]]</td>
                <td>[[rcvCardNumber]]</td>
                <td>[[bankInfoName]]</td>
                <td>[[cardNumber]]</td>
                <td>[[cardAcct]]</td>
                <td>[[mcChannel]]</td>
                <td>附言违规</td>
				<td>[[baseInfo]]</td>
                <td>[[attachment]]</td>
                <td>[[applyTime]]</td>
                <td>[[applyAccount]]</td>
                <td>[[Status]]</td>
                <td>[[apprMemo]]</td>
                <td name="showType">[[userName]]</td>
                <td name="showType">[[agentName]]</td>
                <td name="showType">[[bankName]]</td>
                <td name="showType">[[bankAddr]]</td>
                <td>[[operatingTime]]</td>
            </tr>
        </script>
    </li>						
</ul>
{literal}
    <script>
        //---------------------------------------------------异常充值处理(全部)数据处理开始-------------------------------------------------------
        var AllData = function () {
            $('#downloadReport').css("display", "inline");
            $('#downloadReport1').css("display", "none");
            $('#downloadReport2').css("display", "none");
            //MOW订号
            var movnumber = new phoenix.SuperSearch({
                name: 'movnumber',
                keyCode: 'ctrl+83',
                el: '#J-sp-serial',
                group: group
            });

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



            //收款方卡
            var receivablesCard = new phoenix.SuperSearch({
                name: 'receivablesCard',
                keyCode: 'ctrl+83',
                el: '#J-sp-receivablesCard',
                group: group
            });
            //付款银行
            var palyBankName = new phoenix.SuperSearch({
                name: 'palyBankName',
                keyCode: 'ctrl+83',
                type: 'select',
                isAutoWidth: true,
                el: '#J-sp-palyBankName',
                group: group,
                expands: {getFormValue: getSelectValue}
            });

            //付款户名
            var palyBankUserName = new phoenix.SuperSearch({
                name: 'palyBankUserName',
                keyCode: 'ctrl+83',
                el: '#J-sp-palyBankUserName',
                group: group
            });

            //一审时间	
            var AuditOne = new phoenix.SuperSearch({
                name: 'AuditOne',
                keyCode: 'ctrl+68',
                type: 'time',
                el: '#J-sp-AuditOne-time',
                group: group,
                expands: {getFormValue: getTowInputValue}
            });
            var AuditOneTime1 = $('#sp-input-AuditOne-1'), AuditOneTime2 = $('#sp-input-AuditOne-2'), AuditOneDt1, AuditOneDt2;
            AuditOneTime1.click(function () {
                AuditOneDt1 = new phoenix.DatePicker({
                    input: AuditOneTime1,
                    isLockInputType: false
                });
                AuditOneDt1.show();
                AuditOneDt1.addEvent('afterSetValue',
                        function () {
                            AuditOneTime2.focus();
                            AuditOneTime2.click();
                        })
            });
            AuditOne.addEvent('afterFocus',
                    function () {
                        AuditOneTime1.click()
                    });
            AuditOneTime2.click(function () {
                AuditOneDt2 = new phoenix.DatePicker({
                    input: AuditOneTime2,
                    isLockInputType: false
                }).show();
            });
            AuditOne.addEvent('afterBlur', function () {
                if (AuditOneDt1) {
                    AuditOneDt1.hide();
                }
                if (AuditOneDt2) {
                    AuditOneDt2.hide();
                }
            });
            //一审管理员
            var auditAdmin = new phoenix.SuperSearch({
                name: 'auditAdmin',
                keyCode: 'ctrl+83',
                el: '#J-sp-auditAdmin',
                group: group
            });
            //状态
            var statusType = new phoenix.SuperSearch({
                name: 'statusType',
                keyCode: 'ctrl+66',
                type: 'select',
                isAutoWidth: true,
                el: '#J-sp-Status',
                group: group,
                expands: {getFormValue: getSelectValue}
            });
            //数据处理状态标识(全部)
            var Title = new phoenix.SuperSearch({
                name: 'Title',
                group: group,
                expands: {getValue: function () {
                        return 0;
                    }}
            });

 //DP時間
    var OperatingTimetime = new phoenix.SuperSearch({
        name: 'OperatingTimetime',        
        type: 'time',
        el: '#J-sp-td-OperatingTime',
        group: group,
        expands:{getFormValue:getTowInputValue}
    });
    var OperatingTimetime1 = $('#sp-input-OperatingTime-time-1'),OperatingTimetime2 = $('#sp-input-OperatingTime-time-2'),OperatingTimeDt1,OperatingTimeDt2;
    OperatingTimetime1.click(function() {
        OperatingTimeDt1 = new phoenix.DatePicker({
            input: OperatingTimetime1,
            isLockInputType: false
        });
        OperatingTimeDt1.show();
        OperatingTimeDt1.addEvent('afterSetValue',
        function() {
            OperatingTimetime2.focus();
            OperatingTimetime2.click();
        })
    });
    OperatingTimetime.addEvent('afterFocus',
    function() {
        OperatingTimetime1.click()
    });
    OperatingTimetime2.click(function() {
        OperatingTimeDt2 = new phoenix.DatePicker({
            input: OperatingTimetime2,
            isLockInputType: false
        }).show();
    });
    OperatingTimetime.addEvent('afterBlur',function() {
        if (OperatingTimeDt1) {
            OperatingTimeDt1.hide();
        }
        if (OperatingTimeDt2) {
            OperatingTimeDt2.hide();
        }
    });
            SelectByWhereInfo("0");
            //提交数据
            group.removeEvent('dataChange');
            group.addEvent('dataChange', function () {
                SelectByWhereInfo("0");
            });

            //每页记录数更改事件
            $("#per_page").change(function () {
                SelectByWhereInfo("0");

            });

            function SelectByWhereInfo(pages) {
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
                //放入group对象中(当前页行数)	
                var perPageNum = new phoenix.SuperSearch({
                    name: 'perPageNum',
                    group: group,
                    expands: {getValue: function () {
                            return per_page_number;
                        }}
                });

                var dataArea = $('[name="DivRules"]'), formData = group.getFormData(), mask = phoenix.Mask.getInstance();

                var prepaidContent = function (data) {
                    var content = $('#data_item_all').text();
                    content = content.replace('[[mcSn]]', data.mcSn);
                    content = content.replace('[[memo]]', getSubString(data.memo));
                    content = content.replace('[[realChargeTime]]', data.realChargeTime);
                    content = content.replace('[[realChargeAmt]]', data.realChargeAmt);
                    content = content.replace('[[mcBankFee]]', data.mcBankFee);
                    content = content.replace('[[rcvBank]]', data.rcvBank);
                    content = content.replace('[[rcvCardNumber]]', data.rcvCardNumber);
                    content = content.replace('[[bankInfoName]]', data.bankInfoName);
                    content = content.replace('[[cardNumber]]', data.cardNumber);
                    content = content.replace('[[cardAcct]]', data.cardAcct);
                    content = content.replace('[[mcChannel]]', data.mcChannel);
                    content = content.replace('[[baseInfo]]', data.baseInfo);
                    content = content.replace('[[attachment]]', data.attachment);
                    content = content.replace('[[applyTime]]', data.applyTime);
                    content = content.replace('[[applyAccount]]', data.applyAccount);
                    content = content.replace('[[Status]]', data.Status);
                    content = content.replace('[[apprMemo]]', getSubString(data.apprMemo));
                    content = content.replace('[[userName]]', data.userName);
                    content = content.replace('[[agentName]]', data.agentName);
                    content = content.replace('[[bankName]]', data.bankName);
                    content = content.replace('[[bankAddr]]', data.bankAddr);
                    content = content.replace('[[operatingTime]]', data.operatingTime);
                    return content;
                };

                $("#J-table-data>tbody").html("");
                $("#Pagination").hide();
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=dv1',
                    method: 'post',
                    data: formData,
                    beforeSend: function () {
                        isLock = false;
                        TableStyle("showInfo", 17, 1, "查询中");
                    },
                    success: function (data) {
                        //debugger
                        if (data.text.length > 0) {
                            $("#Pagination").show();
                            var re = data.text;
                            var recordNum = 0;
                            recordNum = data.count[0].recordNum;
                            //分页回调				 
                            $("#Pagination").pagination(recordNum, {
                                num_edge_entries: 2,
                                num_display_entries: 8,
                                current_page: pages,
                                items_per_page: per_page_number,
                                callback: SelectByWhereInfo
                            });
                            var html = "";
                            //数据填充
                            $.each(re, function (i) {
                                var content = prepaidContent(re[i]);
                                html += (content);
                            });
                            $("#J-table-data>tbody").html(html);
                            if (isShowCell == false) {//状态判定加载相关数据		
                                $('[name="showType"]').hide();
                            }
                            else {
                                $('[name="showType"]').show();
                            }

                        } else {
                            $("#Pagination").hide();
                            TableStyle("J-table-data>tbody", 17, 2, "没有相应数据");
                        }
                    },
                    complete: function ()
                    {
                        isLock = true;
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        TableStyle("J-table-data>tbody", 17, 2, "数据异常");
                    }
                });
            }
        };
        //---------------------------------------------------(全部)数据处理结束------------------------------------------------------
    </script>
{/literal}