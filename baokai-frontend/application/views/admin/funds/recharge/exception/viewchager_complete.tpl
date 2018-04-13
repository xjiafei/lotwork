<!-----------------------------------------------已完成---------------------------------------->
<ul class="ui-form ui-tab-content" name="DivRules">						
    <li>
        <table id="J-table-data5" class="table table-info table-function">
            <thead>
            <th>平台订单号</th>
            <th id="J-sp-serial5" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">MOW异常订单号</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                    <li>
                        <div class="input-append">
                            <input type="text" class="input w-2" size="10" maxlength="25"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>        
            <th>附言</th>
            <th id="J-sp-Daozhang5-time" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">银行到账时间</div>
                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" tabindex="1" class="input w-2" id="sp-input-Daozhang5-time-1"> - <input type="text" id="sp-input-Daozhang5-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>
            <th id="J-sp-request5-money" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">金额</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                    <li>
                        <div class="input-append">
                            <input id="J-input-request5money1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-reques5tmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>   
            </th>
            <th id="J-sp-request6-money" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">实际支付金额</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                    <li>
                        <div class="input-append">
                            <input id="J-input-request6money1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-reques6tmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>   
            </th>
            <th>手续费</th>
            <th>收款银行</th>
            <th  id="J-sp-receivablesCard5" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">收款卡</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" id="receivablesCard5" class="input w-2"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>	      
            <th  id="J-sp-palyBankName5" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">付款银行</div>
                <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                    <ul>
                        {foreach from=$bankArray item=data key=key}
                            <li data-select-id="{$key}"><a href="#">{$data.name}</a></li>
                            {/foreach}
                    </ul>
                </div>
                <span class="sp-filter-close"></span>
            </div>
            </th>	                                            
            <th>付款卡</th>
            <th  id="J-sp-palyBankUserName5" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">付款户名</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" id="palyBankUserName5" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
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
            <th id="J-sp-AuditOne5-time" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">一审时间</div>
                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" tabindex="1" class="input w-2" id="sp-input-AuditOne5-1"> - <input type="text" id="sp-input-AuditOne5-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>           
            <th  id="J-sp-auditAdmin5" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">一审管理员</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" id="auditAdmin5" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>	
            <th id="J-sp-Status5" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">状态</div>
                <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                    <ul>
                        <!-- {foreach from=$aRechargeStatus key=key item=data} -->
                        <!-- {if $key eq '1' or $key eq '2' or $key eq '5' or $key eq '6' or $key eq '7' or $key eq '10'} -->
                        <li data-select-id="{$key}"><a href="#">{$data}</a></li>
                        <!-- {/if} -->
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
            </thead>
            <tbody id="showInfo5">
            </tbody>                                    
            <tfoot>
                <tr>
                    <td colspan="22">
                        <div id="Pagination5" class="pagination" ></div>
                    </td>
                </tr>
            </tfoot>
        </table>
        <script type="text" id="data_item_complete">
            <tr>
                <td>[[sn]]</td>
                <td>[[mcSn]]</td>
                <td>[[memo]]</td>
                <td>[[realChargeTime]]</td>
                <td>[[realChargeAmt]]</td>
                <td>[[refundAmt]]</td>
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
            </tr>
        </script>
    </li>
</ul>
{literal}
    <script>
        //---------------------------------------------------(已完成)数据处理开始-----------------------------------------------------
        var CompletedData = function () {
            $('#downloadReport').css("display", "none");
            $('#downloadReport1').css("display", "inline");
            $('#downloadReport2').css("display", "none");

            //MOW订号
            var movnumber5 = new phoenix.SuperSearch({
                name: 'movnumber',
                keyCode: 'ctrl+83',
                el: '#J-sp-serial5',
                group: group
            });

            //银行到账时间
            var Daozhang5time = new phoenix.SuperSearch({
                name: 'Daozhangtime',
                keyCode: 'ctrl+68',
                type: 'time',
                el: '#J-sp-Daozhang5-time',
                group: group,
                expands: {getFormValue: getTowInputValue}
            });
            var Daozhang5Time1 = $('#sp-input-Daozhang5-time-1'), Daozhang5Time2 = $('#sp-input-Daozhang5-time-2'), Daozhang5Dt1, Daozhang5Dt2;
            Daozhang5Time1.click(function () {
                Daozhang5Dt1 = new phoenix.DatePicker({
                    input: Daozhang5Time1,
                    isLockInputType: false
                });
                Daozhang5Dt1.show();
                Daozhang5Dt1.addEvent('afterSetValue',
                        function () {
                            Daozhang5Time2.focus();
                            Daozhang5Time2.click();
                        })
            });
            Daozhang5time.addEvent('afterFocus',
                    function () {
                        Daozhang5Time1.click()
                    });
            Daozhang5Time2.click(function () {
                Daozhang5Dt2 = new phoenix.DatePicker({
                    input: Daozhang5Time2,
                    isLockInputType: false
                }).show();
            });
            Daozhang5time.addEvent('afterBlur', function () {
                if (Daozhang5Dt1) {
                    Daozhang5Dt1.hide();
                }
                if (Daozhang5Dt2) {
                    Daozhang5Dt2.hide();
                }
            });
            //金额
            var requestMoney5 = new phoenix.SuperSearch({
                name: 'requestMoney',
                keyCode: 'ctrl+85',
                el: '#J-sp-request5-money',
                group: group,
                expands: {getFormValue: getTowInputValue}
            });
            //实际支付金额
            var requestMoney6 = new phoenix.SuperSearch({
                name: 'requestRealMoney',
                keyCode: 'ctrl+85',
                el: '#J-sp-request6-money',
                group: group,
                expands: {getFormValue: getTowInputValue}
            });
            $('#J-input-request5money1').keyup(allowNumber).blur(allowNumber);
            $('#J-input-request5money2').keyup(allowNumber).blur(allowNumber);
            $('#J-input-request6money1').keyup(allowNumber).blur(allowNumber);
            $('#J-input-request6money2').keyup(allowNumber).blur(allowNumber);
            //收款方卡
            var receivablesCard5 = new phoenix.SuperSearch({
                name: 'receivablesCard',
                keyCode: 'ctrl+83',
                el: '#J-sp-receivablesCard5',
                group: group
            });
            //付款银行
            var palyBankName5 = new phoenix.SuperSearch({
                name: 'palyBankName',
                keyCode: 'ctrl+83',
                type: 'select',
                isAutoWidth: true,
                el: '#J-sp-palyBankName5',
                group: group,
                expands: {getFormValue: getSelectValue}
            });
            //付款户名
            var palyBankUserName5 = new phoenix.SuperSearch({
                name: 'palyBankUserName',
                keyCode: 'ctrl+83',
                el: '#J-sp-palyBankUserName5',
                group: group
            });
            //一审时间	
            var AuditOne5 = new phoenix.SuperSearch({
                name: 'AuditOne',
                keyCode: 'ctrl+68',
                type: 'time',
                el: '#J-sp-AuditOne5-time',
                group: group,
                expands: {getFormValue: getTowInputValue}
            });
            var AuditOne5Time1 = $('#sp-input-AuditOne5-1'), AuditOne5Time2 = $('#sp-input-AuditOne5-2'), AuditOne5Dt1, AuditOne5Dt2;
            AuditOne5Time1.click(function () {
                AuditOne5Dt1 = new phoenix.DatePicker({
                    input: AuditOne5Time1,
                    isLockInputType: false
                });
                AuditOne5Dt1.show();
                AuditOne5Dt1.addEvent('afterSetValue',
                        function () {
                            AuditOne5Time2.focus();
                            AuditOne5Time2.click();
                        })
            });
            AuditOne5.addEvent('afterFocus',
                    function () {
                        AuditOne5Time1.click()
                    });
            AuditOne5Time2.click(function () {
                AuditOne5Dt2 = new phoenix.DatePicker({
                    input: AuditOne5Time2,
                    isLockInputType: false
                }).show();
            });
            AuditOne5.addEvent('afterBlur', function () {
                if (AuditOne5Dt1) {
                    AuditOne5Dt1.hide();
                }
                if (AuditOne5Dt2) {
                    AuditOne5Dt2.hide();
                }
            });
            //一审管理员
            var auditAdmin5 = new phoenix.SuperSearch({
                name: 'auditAdmin',
                keyCode: 'ctrl+83',
                el: '#J-sp-auditAdmin5',
                group: group
            });
            //数据处理状态标识(已完成)
            var Title = new phoenix.SuperSearch({
                name: 'Title',
                group: group,
                expands: {getValue: function () {
                        return 4;
                    }}
            });
            SelectByWhereInfo5("0");
            group.removeEvent('dataChange');
            group.addEvent('dataChange', function () {
                SelectByWhereInfo5("0");
            });

            //状态
            var statusType = new phoenix.SuperSearch({
                name: 'statusType',
                keyCode: 'ctrl+66',
                type: 'select',
                isAutoWidth: true,
                el: '#J-sp-Status5',
                group: group,
                expands: {getFormValue: getSelectValue}
            });

            $("#per_page").change(function () {
                SelectByWhereInfo5("0");

            });

            function SelectByWhereInfo5(pages) {
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

                var formData5 = group.getFormData(), mask = phoenix.Mask.getInstance();
                var prepaidContent = function (data) {
                    var content = $('#data_item_complete').text();
                    content = content.replace('[[sn]]', data.sn);
                    content = content.replace('[[mcSn]]', data.mcSn);
                    content = content.replace('[[memo]]', getSubString(data.memo));
                    content = content.replace('[[realChargeTime]]', data.realChargeTime);
                    content = content.replace('[[realChargeAmt]]', data.realChargeAmt);
                    content = content.replace('[[refundAmt]]', data.refundAmt);
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
                    return content;
                };
                $("#J-table-data5>tbody").html("");
                $("#Pagination5").hide();
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=dv1',
                    dataType: 'json',
                    method: 'post',
                    data: formData5,
                    beforeSend: function () {
                        isLock = false;
                        TableStyle("showInfo5", 19, 1, "查询中");
                    },
                    success: function (data) {
                        //debugger
                        if (data.text.length > 0) {
                            $("#Pagination5").show();
                            var resultAll = eval(data);
                            var re = resultAll.text;
                            var recordNum = 0;
                            recordNum = resultAll.count[0].recordNum;
                            //分页回调				 
                            $("#Pagination5").pagination(recordNum, {
                                num_edge_entries: 2,
                                num_display_entries: 8,
                                current_page: pages,
                                items_per_page: per_page_number,
                                callback: SelectByWhereInfo5
                            });
                            var html = "";
                            //数据填充
                            $.each(re, function (i) {
                                var content = prepaidContent(re[i]);
                                html += content;
                            });
                            $("#J-table-data5>tbody").html(html);
                            if (isShowCell == false) {//状态判定加载相关数据		
                                $('[name="showType"]').hide();
                            }
                            else {
                                $('[name="showType"]').show();
                            }

                        } else {
                            $("#Pagination5").hide();
                            TableStyle("J-table-data5>tbody", 17, 2, "没有相应数据");
                        }
                    },
                    complete: function ()
                    {
                        isLock = true;
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        TableStyle("J-table-data5>tbody", 17, 2, "数据异常");
                    }
                });
            }
        };
        //---------------------------------------------------(已完成)数据处理结束------------------------------------------------------
    </script>
{/literal}