<!-------------------------未处理------------------------------------------>
<ul class="ui-form ui-tab-content" name="DivRules">
    <li>								
        <table id="J-table-data2" class="table table-info table-function">
            <thead>									
            <th id="J-sp-serial2" class="sp-td">
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
            <th id="J-sp-Daozhang2-time" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">银行到账时间</div>
                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" tabindex="1" class="input w-2" id="sp-input-Daozhang2-time-1"> - <input type="text" id="sp-input-Daozhang2-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>
            <th id="J-sp-request2-money" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">金额</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                    <li>
                        <div class="input-append">
                            <input id="J-input-request2money-1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-reques2tmoney-2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>   
            </th>
            <th>手续费</th>
            <th>收款银行</th>
            <th  id="J-sp-receivablesCard2" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">收款卡</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" id="receivablesCard2" class="input w-2"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>	        
            <th  id="J-sp-palyBankName2" class="sp-td">
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
            <th  id="J-sp-palyBankUserName2" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">付款户名</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" id="palyBankUserName2" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>			
            <th>充值渠道</th>
            <th>充值异常原因</th>
            <th>交易单号</th>
            <th style=" text-align:center">操作人</th>
            <th style=" text-align:center">操作</th>
            <th name="showType">充值用户</th>
            <th name="showType">所属总代</th>
            <th name="showType">开户行</th>
            <th name="showType">开户行地址</th>
            </thead>
            <tbody id="showInfo2">
            </tbody>                                    
            <tfoot>
                <tr>
                    <td colspan="22">
                        <div id="Pagination2" class="pagination" ></div>
                    </td>
                </tr>
            </tfoot>
        </table>
        <script type="text" id="data_item_untreated">
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
                <td>[[currApprer]]</td>
                <td>
                <!-- {if "FUND_RECHARGE_EXCEPTION_UNTREATED_ADDCOINS"|in_array:$smarty.session.datas.info.acls} -->
                <a class="btn btn-small [[disble]]" href="/admin/Rechargemange/index?parma=disauit&optionType=1&mcSn=[[mcSn]]"><font color="[[color]]">加游戏币</font><b class="btn-inner"></b></a>
                <!-- {/if} -->
                <!-- {if "FUND_RECHARGE_EXCEPTION_UNTREATED_RETURNMONEY"|in_array:$smarty.session.datas.info.acls} -->
                <a class="btn btn-small [[disble]]" href="/admin/Rechargemange/index?parma=disauit&optionType=2&mcSn=[[mcSn]]"><font color="[[color]]">退 款</font><b class="btn-inner"></b></a>
                <!-- {/if} -->
                <!-- {if "FUND_RECHARGE_EXCEPTION_UNTREATED_CONFISCATE"|in_array:$smarty.session.datas.info.acls} -->
                <a class="btn btn-small [[disble]]" href="/admin/Rechargemange/index?parma=disauit&optionType=5&mcSn=[[mcSn]]"><font color="[[color]]">没 收</font><b class="btn-inner"></b></a>
                <!-- {/if} -->
                </td>
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
        //---------------------------------------------------(未处理)数据处理开始-------------------------------------------------------
        var UntreatedData = function () {
            $('#downloadReport').css("display", "none");
            $('#downloadReport1').css("display", "none");
            $('#downloadReport2').css("display", "inline");
            //MOW订号
            var movnumber2 = new phoenix.SuperSearch({
                name: 'movnumber',
                keyCode: 'ctrl+83',
                el: '#J-sp-serial2',
                group: group
            });
            //银行到账时间
            var Daozhang2time = new phoenix.SuperSearch({
                name: 'Daozhangtime',
                keyCode: 'ctrl+68',
                type: 'time',
                el: '#J-sp-Daozhang2-time',
                group: group,
                expands: {getFormValue: getTowInputValue}
            });
            var Daozhang2Time1 = $('#sp-input-Daozhang2-time-1'), Daozhang2Time2 = $('#sp-input-Daozhang2-time-2'), Daozhang2Dt1, Daozhang2Dt2;
            Daozhang2Time1.click(function () {
                Daozhang2Dt1 = new phoenix.DatePicker({
                    input: Daozhang2Time1,
                    isLockInputType: false
                });
                Daozhang2Dt1.show();
                Daozhang2Dt1.addEvent('afterSetValue',
                        function () {
                            Daozhang2Time2.focus();
                            Daozhang2Time2.click();
                        })
            });
            Daozhang2time.addEvent('afterFocus',
                    function () {
                        Daozhang2Time1.click()
                    });
            Daozhang2Time2.click(function () {
                Daozhang2Dt2 = new phoenix.DatePicker({
                    input: Daozhang2Time2,
                    isLockInputType: false
                }).show();
            });
            Daozhang2time.addEvent('afterBlur', function () {
                if (Daozhang2Dt1) {
                    Daozhang2Dt1.hide();
                }
                if (Daozhang2Dt2) {
                    Daozhang2Dt2.hide();
                }
            });
            //金额
            var requestMoney2 = new phoenix.SuperSearch({
                name: 'requestMoney',
                keyCode: 'ctrl+85',
                el: '#J-sp-request2-money',
                group: group,
                expands: {getFormValue: getTowInputValue}
            });
            $('#J-input-request2money-1').keyup(allowNumber).blur(allowNumber);
            $('#J-input-request2money-2').keyup(allowNumber).blur(allowNumber);
            //收款方卡
            var receivablesCard2 = new phoenix.SuperSearch({
                name: 'receivablesCard',
                keyCode: 'ctrl+83',
                el: '#J-sp-receivablesCard2',
                group: group
            });
            //付款银行
            var palyBankName2 = new phoenix.SuperSearch({
                name: 'palyBankName',
                keyCode: 'ctrl+83',
                type: 'select',
                isAutoWidth: true,
                el: '#J-sp-palyBankName2',
                group: group,
                expands: {getFormValue: getSelectValue}
            });
            //付款户名
            var palyBankUserName2 = new phoenix.SuperSearch({
                name: 'palyBankUserName2',
                keyCode: 'ctrl+83',
                el: '#J-sp-palyBankUserName2',
                group: group
            });
            //数据处理状态标识(未处理)
            var Title = new phoenix.SuperSearch({
                name: 'Title',
                group: group,
                expands: {getValue: function () {
                        return 1;
                    }}
            });
            //提交数据
            SelectByWhereInfo2("0");
            group.removeEvent('dataChange');
            group.addEvent('dataChange', function () {
                SelectByWhereInfo2("0");
            });

            $("#per_page").change(function () {
                SelectByWhereInfo2("0");

            });


            function SelectByWhereInfo2(pages) {
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
                var dataArea2 = $('[name="DivRules"]'), formData2 = group.getFormData(), mask2 = phoenix.Mask.getInstance();
                var prepaidContent = function (data) {
                    var content = $('#data_item_untreated').text();
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
                    content = content.replace('[[currApprer]]', data.currApprer);
                    content = content.replace('[[baseInfo]]', data.baseInfo);
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
                $("#J-table-data2>tbody").html("");
                $("#Pagination2").hide();
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=dv1',
                    dataType: 'json',
                    method: 'post',
                    data: formData2,
                    beforeSend: function () {
                        isLock = false;
                        TableStyle("showInfo2", 19, 1, "查询中");
                    },
                    success: function (data) {
                        //debugger
                        if (data.text.length > 0) {
                            $("#Pagination2").show();
                            var resultAll = eval(data);
                            var re = resultAll.text;
                            var recordNum = 0;
                            recordNum = resultAll.count[0].recordNum;
                            //分页回调				 
                            $("#Pagination2").pagination(recordNum, {
                                num_edge_entries: 2,
                                num_display_entries: 8,
                                current_page: pages,
                                items_per_page: per_page_number,
                                callback: SelectByWhereInfo2
                            });
                            var html = "";
                            //数据填充
                            $.each(re, function (i) {
                                var content = prepaidContent(re[i]);
                                html += (content);
                            });
                            $("#J-table-data2>tbody").html(html);
                            if (isShowCell == false) {//状态判定加载相关数据		
                                $('[name="showType"]').hide();
                            }
                            else {
                                $('[name="showType"]').show();
                            }

                        } else {
                            $("#Pagination2").hide();
                            TableStyle("J-table-data2>tbody", 17, 2, "没有相应数据");
                        }
                    },
                    complete: function ()
                    {
                        isLock = true;
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        TableStyle("J-table-data2>tbody", 17, 2, "数据异常");
                    }
                });
            }
        };
        //---------------------------------------------------异常充值(未处理)数据处理结束------------------------------------------------------
    </script>
{/literal}               