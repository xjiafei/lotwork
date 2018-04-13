<!-----------------------------------------------待复审---------------------------------------->
<ul class="ui-form ui-tab-content" name="DivRules">							
    <li>									
        <table id="J-table-data3" class="table table-info table-function">
            <thead>									
            <th id="J-sp-serial3" class="sp-td">
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
            <th id="J-sp-Daozhang3-time" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">银行到账时间</div>
                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" tabindex="1" class="input w-2" id="sp-input-Daozhang3-time-1"> - <input type="text" id="sp-input-Daozhang3-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>
            <th id="J-sp-request3-money" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">金额</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                    <li>
                        <div class="input-append">
                            <input id="J-input-request3money1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-reques3tmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>   
            </th>
            <th>手续费</th>
            <th>收款银行</th>
            <th  id="J-sp-receivablesCard3" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">收款卡</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" id="receivablesCard3" class="input w-2"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>	         
            <th  id="J-sp-palyBankName3" class="sp-td">
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
            <th  id="J-sp-palyBankUserName3" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">付款户名</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" id="palyBankUserName3" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
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
            <th id="J-sp-AuditOne3-time" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">一审时间</div>
                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" tabindex="1" class="input w-2" id="sp-input-AuditOne3-1"> - <input type="text" id="sp-input-AuditOne3-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>            
            <th  id="J-sp-auditAdmin3" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">一审管理员</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" id="auditAdmin3" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>	
            <th id="J-sp-Status3" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">状态</div>
                <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                    <ul>
                        <!-- {foreach from=$aAuitStatus key=key item=data} -->
                        <!-- {if $key eq '3' or $key eq '8' or $key eq '9'} -->
                        <li data-select-id="{$key}"><a href="#">{$data}</a></li>
                        <!-- {/if} -->
                        <!-- {/foreach} -->
                    </ul>
                </div>
                <span class="sp-filter-close"></span>
            </div>
            </th> 
            <th>操作人</th>
            <th style="text-align:center">操作</th>
            <th>备注</th>
            <th name="showType">充值用户</th>
            <th name="showType">所属总代</th>
            <th name="showType">开户行</th>
            <th name="showType">开户行地址</th>
            </thead>
            <tbody id="showInfo3">
            </tbody>                                    
            <tfoot>
                <tr>
                    <td colspan="24">
                        <div id="Pagination3" class="pagination" ></div>
                    </td>
                </tr>
            </tfoot>
        </table>
        <script type="text" id="data_item_pendingReview">
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
            <td>[[currApprer]]</td>
            <td>
            <!-- {if "FUND_RECHARGE_EXCEPTION_REVIEW_REVIEW"|in_array:$smarty.session.datas.info.acls} -->
            <a class="btn btn-small [[disble]]" href="/admin/Rechargemange/index?parma=disauit&mcSn=[[mcSn]]" style="position:initial" ><font color="[[color]]">复 审</font><b class="btn-inner"></b></a>
            <!-- {/if} -->
            </td>
            <td>[[apprMemo]]</td>
            <td name="showType">[[userName]]</td>
            <td name="showType">[[agentName]]</td>
            <td name="showType">[[bankName]]</td>
            <td name="showType">[[bankAddr]]</td>
            </tr>
        </script>
    <li>									
</ul>
{literal}
    <script>      //---------------------------------------------------(待复审)数据处理开始-------------------------------------------------------
        var PendingReviewData = function () {
            $('#downloadReport').css("display", "none");
            $('#downloadReport1').css("display", "none");
            $('#downloadReport2').css("display", "none");
            //MOW订号
            var movnumber3 = new phoenix.SuperSearch({
                name: 'movnumber',
                keyCode: 'ctrl+83',
                el: '#J-sp-serial3',
                group: group
            });

            //银行到账时间
            var Daozhang3time = new phoenix.SuperSearch({
                name: 'Daozhangtime',
                keyCode: 'ctrl+68',
                type: 'time',
                el: '#J-sp-Daozhang3-time',
                group: group,
                expands: {getFormValue: getTowInputValue}
            });
            var Daozhang3Time1 = $('#sp-input-Daozhang3-time-1'), Daozhang3Time2 = $('#sp-input-Daozhang3-time-2'), Daozhang3Dt1, Daozhang3Dt2;
            Daozhang3Time1.click(function () {
                Daozhang3Dt1 = new phoenix.DatePicker({
                    input: Daozhang3Time1,
                    isLockInputType: false
                });
                Daozhang3Dt1.show();
                Daozhang3Dt1.addEvent('afterSetValue',
                        function () {
                            Daozhang3Time2.focus();
                            Daozhang3Time2.click();
                        })
            });
            Daozhang3time.addEvent('afterFocus',
                    function () {
                        Daozhang3Time1.click()
                    });
            Daozhang3Time2.click(function () {
                Daozhang3Dt2 = new phoenix.DatePicker({
                    input: Daozhang3Time2,
                    isLockInputType: false
                }).show();
            });
            Daozhang3time.addEvent('afterBlur', function () {
                if (Daozhang3Dt1) {
                    Daozhang3Dt1.hide();
                }
                if (Daozhang3Dt2) {
                    Daozhang3Dt2.hide();
                }
            });
            //金额
            var requestMoney3 = new phoenix.SuperSearch({
                name: 'requestMoney',
                keyCode: 'ctrl+85',
                el: '#J-sp-request3-money',
                group: group,
                expands: {getFormValue: getTowInputValue}
            });
            $('#J-input-request3money-1').keyup(allowNumber).blur(allowNumber);
            $('#J-input-request3money-2').keyup(allowNumber).blur(allowNumber);
            //收款方卡
            var receivablesCard3 = new phoenix.SuperSearch({
                name: 'receivablesCard',
                keyCode: 'ctrl+83',
                el: '#J-sp-receivablesCard3',
                group: group
            });
            //付款银行
            var palyBankName3 = new phoenix.SuperSearch({
                name: 'palyBankName',
                keyCode: 'ctrl+83',
                type: 'select',
                isAutoWidth: true,
                el: '#J-sp-palyBankName3',
                group: group,
                expands: {getFormValue: getSelectValue}
            });
            //付款户名<a href="../../../../../css">css</a>
            var palyBankUserName3 = new phoenix.SuperSearch({
                name: 'palyBankUserName',
                keyCode: 'ctrl+83',
                el: '#J-sp-palyBankUserName3',
                group: group
            });
            //一审时间	
            var AuditOne3 = new phoenix.SuperSearch({
                name: 'AuditOne',
                keyCode: 'ctrl+68',
                type: 'time',
                el: '#J-sp-AuditOne3-time',
                group: group,
                expands: {getFormValue: getTowInputValue}
            });
            var AuditOne3Time1 = $('#sp-input-AuditOne3-1'), AuditOne3Time2 = $('#sp-input-AuditOne3-2'), AuditOne3Dt1, AuditOne3Dt2;
            AuditOne3Time1.click(function () {
                AuditOne3Dt1 = new phoenix.DatePicker({
                    input: AuditOne3Time1,
                    isLockInputType: false
                });
                AuditOne3Dt1.show();
                AuditOne3Dt1.addEvent('afterSetValue',
                        function () {
                            AuditOne3Time2.focus();
                            AuditOne3Time2.click();
                        })
            });
            AuditOne3.addEvent('afterFocus',
                    function () {
                        AuditOne3Time1.click()
                    });
            AuditOne3Time2.click(function () {
                AuditOne3Dt2 = new phoenix.DatePicker({
                    input: AuditOne3Time2,
                    isLockInputType: false
                }).show();
            });
            AuditOne3.addEvent('afterBlur', function () {
                if (AuditOne3Dt1) {
                    AuditOne3Dt1.hide();
                }
                if (AuditOne3Dt2) {
                    AuditOne3Dt2.hide();
                }
            });
            //一审管理员
            var auditAdmin3 = new phoenix.SuperSearch({
                name: 'auditAdmin',
                keyCode: 'ctrl+83',
                el: '#J-sp-auditAdmin3',
                group: group
            });
            //状态
            var statusType = new phoenix.SuperSearch({
                name: 'statusType',
                keyCode: 'ctrl+66',
                type: 'select',
                isAutoWidth: true,
                el: '#J-sp-Status3',
                group: group,
                expands: {getFormValue: getSelectValue}
            });
            //数据处理状态标识(待复审)
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
                var prepaidContent = function (data) {
                    var content = $('#data_item_pendingReview').text();
                    content = content.split('[[mcSn]]').join(data.mcSn);
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
                    content = content.replace('[[attachment]]', data.attachment);
                    content = content.replace('[[applyTime]]', data.applyTime);
                    content = content.replace('[[applyAccount]]', data.applyAccount);
                    content = content.replace('[[Status]]', data.Status);
                    content = content.replace('[[currApprer]]', data.currApprer);
                    content = content.replace('[[baseInfo]]', data.baseInfo);
                    content = content.replace('[[apprMemo]]', getSubString(data.apprMemo));
                    content = content.replace('[[userName]]', data.userName);
                    content = content.replace('[[agentName]]', data.agentName);
                    content = content.replace('[[bankName]]', data.bankName);
                    content = content.replace('[[bankAddr]]', data.bankAddr);
                    var disabled = data.isAbleOprate ? "" : "btn-disable";
                    var fontColor = data.isAbleOprate && data.myLocked ? "#25a38a" : "";
                    content = content.split('[[disble]]').join(disabled);
                    content = content.split('[[color]]').join(fontColor);
                    return content;
                };

                $("#J-table-data3>tbody").html("");
                $("#Pagination3").hide();
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=dv1',
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
                                var content = prepaidContent(re[i]);
                                html += content;
                            });
                            $("#J-table-data3>tbody").html(html);

                            if (isShowCell == false) {//状态判定加载相关数据		
                                $('[name="showType"]').hide();
                            }
                            else {
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
        };
        //---------------------------------------------------(待复审)数据处理结束-----------------------------------------------------
    </script>
{/literal}