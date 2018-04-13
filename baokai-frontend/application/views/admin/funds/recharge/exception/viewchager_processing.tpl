<!--处理中-->
<ul class="ui-form ui-tab-content" name="DivRules">
    <li>								
        <table id="J-table-data4" class="table table-info table-function">
            <thead>
            <th id="J-sp-serial4" class="sp-td">
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
            <th id="J-sp-Daozhang4-time" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">银行到账时间</div>
                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" tabindex="1" class="input w-2" id="sp-input-Daozhang4-time-1"> - <input type="text" id="sp-input-Daozhang4-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>
            <th id="J-sp-request4-money" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">金额</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                    <li>
                        <div class="input-append">
                            <input id="J-input-request4money1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-reques4tmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>   
            </th>
            <th>手续费</th>
            <th>收款银行</th>
            <th  id="J-sp-receivablesCard4" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">收款卡</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" id="receivablesCard4" class="input w-2"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>	      
            <th  id="J-sp-palyBankName4" class="sp-td">
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
            <th  id="J-sp-palyBankUserName4" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">付款户名</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" id="palyBankUserName4" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
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
            <th id="J-sp-AuditOne4-time" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">一审时间</div>
                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" tabindex="1" class="input w-2" id="sp-input-AuditOne4-1"> - <input type="text" id="sp-input-AuditOne4-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>           
            <th  id="J-sp-auditAdmin4" class="sp-td">
            <div class="sp-td-cont sp-td-cont-b">
                <div class="sp-td-title">一审管理员</div>
                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                    <li>
                        <div class="input-append">
                            <input type="text" id="auditAdmin4" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                        </div>
                    </li>
                </ul>
                <span class="sp-filter-close"></span>
            </div>
            </th>	
            <th>操作</th>
            <th>备注</th>
            <th name="showType">充值用户</th>
            <th name="showType">所属总代</th>
            <th name="showType">开户行</th>
            <th name="showType">开户行地址</th>
            </thead>
            <tbody id="showInfo4">
            </tbody>                                    
            <tfoot>
                <tr>
                    <td colspan="22">
                        <div id="Pagination4" class="pagination" ></div>
                    </td>
                </tr>
            </tfoot>
        </table>
        <script type="text" id="data_item_processing">
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
                <td>
                <!-- {if "FUND_RECHARGE_EXCEPTION_PROCESSING_CHECK"|in_array:$smarty.session.datas.info.acls} -->
                    <a class="btn btn-small [[disble]]" name="getNowStatus" mcSn="[[mcSn]]"  sn="[[sn]]"  exceptId="[[exceptId]]" style="position:initial" ><font color="[[color]]">获取状态<b class="btn-inner"></font></b></a>
                <!-- {/if} -->
                </td>
                <td>[[apprMemo]]</td>
                <td name="showType">[[userName]]</td>
                <td name="showType">[[agentName]]</td>
                <td name="showType">[[bankName]]</td>
                <td name="showType">[[bankAddr]]</td>
            </tr>
        </script>
        <script type="text" id="alertMsgContent">
            <div><h3 style="height:30px;line-height:30px;text-align:center; ">[[content]]</h3><div style="height:30px;line-height:30px;"></div>
        </script>
    <li>	
</ul>  
{literal}
    <script>
        //---------------------------------------------------(处理中)数据处理开始-----------------------------------------------------
        var ProcessingData = function () {
            $('#downloadReport').css("display", "none");
            $('#downloadReport1').css("display", "none");
            $('#downloadReport2').css("display", "none");
            //MOW订号
            var movnumber4 = new phoenix.SuperSearch({
                name: 'movnumber',
                keyCode: 'ctrl+83',
                el: '#J-sp-serial4',
                group: group
            });
            //银行到账时间
            var Daozhang4time = new phoenix.SuperSearch({
                name: 'Daozhangtime',
                keyCode: 'ctrl+68',
                type: 'time',
                el: '#J-sp-Daozhang4-time',
                group: group,
                expands: {getFormValue: getTowInputValue}
            });
            var Daozhang4Time1 = $('#sp-input-Daozhang4-time-1'), Daozhang4Time2 = $('#sp-input-Daozhang4-time-2'), Daozhang4Dt1, Daozhang4Dt2;
            Daozhang4Time1.click(function () {
                Daozhang4Dt1 = new phoenix.DatePicker({
                    input: Daozhang4Time1,
                    isLockInputType: false
                });
                Daozhang4Dt1.show();
                Daozhang4Dt1.addEvent('afterSetValue',
                        function () {
                            Daozhang4Time2.focus();
                            Daozhang4Time2.click();
                        })
            });
            Daozhang4time.addEvent('afterFocus',
                    function () {
                        Daozhang4Time1.click()
                    });
            Daozhang4Time2.click(function () {
                Daozhang4Dt2 = new phoenix.DatePicker({
                    input: Daozhang4Time2,
                    isLockInputType: false
                }).show();
            });
            Daozhang4time.addEvent('afterBlur', function () {
                if (Daozhang4Dt1) {
                    Daozhang4Dt1.hide();
                }
                if (Daozhang4Dt2) {
                    Daozhang4Dt2.hide();
                }
            });
            //金额
            var requestMoney4 = new phoenix.SuperSearch({
                name: 'requestMoney',
                keyCode: 'ctrl+85',
                el: '#J-sp-request4-money',
                group: group,
                expands: {getFormValue: getTowInputValue}
            });
            $('#J-input-request4money-1').keyup(allowNumber).blur(allowNumber);
            $('#J-input-request4money-2').keyup(allowNumber).blur(allowNumber);
            //收款方卡
            var receivablesCard4 = new phoenix.SuperSearch({
                name: 'receivablesCard',
                keyCode: 'ctrl+83',
                el: '#J-sp-receivablesCard4',
                group: group
            });
            //付款银行
            var palyBankName4 = new phoenix.SuperSearch({
                name: 'palyBankName',
                keyCode: 'ctrl+83',
                type: 'select',
                isAutoWidth: true,
                el: '#J-sp-palyBankName4',
                group: group,
                expands: {getFormValue: getSelectValue}
            });
            //付款户名
            var palyBankUserName4 = new phoenix.SuperSearch({
                name: 'palyBankUserName',
                keyCode: 'ctrl+83',
                el: '#J-sp-palyBankUserName4',
                group: group
            });
            //一审时间	
            var AuditOne4 = new phoenix.SuperSearch({
                name: 'AuditOne',
                keyCode: 'ctrl+68',
                type: 'time',
                el: '#J-sp-AuditOne4-time',
                group: group,
                expands: {getFormValue: getTowInputValue}
            });
            var AuditOne4Time1 = $('#sp-input-AuditOne4-1'), AuditOne4Time2 = $('#sp-input-AuditOne4-2'), AuditOne4Dt1, AuditOne4Dt2;
            AuditOne4Time1.click(function () {
                AuditOne4Dt1 = new phoenix.DatePicker({
                    input: AuditOne4Time1,
                    isLockInputType: false
                });
                AuditOne4Dt1.show();
                AuditOne4Dt1.addEvent('afterSetValue',
                        function () {
                            AuditOne4Time2.focus();
                            AuditOne4Time2.click();
                        })
            });
            AuditOne4.addEvent('afterFocus',
                    function () {
                        AuditOne4Time1.click()
                    });
            AuditOne4Time2.click(function () {
                AuditOne4Dt2 = new phoenix.DatePicker({
                    input: AuditOne4Time2,
                    isLockInputType: false
                }).show();
            });
            AuditOne4.addEvent('afterBlur', function () {
                if (AuditOne4Dt1) {
                    AuditOne4Dt1.hide();
                }
                if (AuditOne4Dt2) {
                    AuditOne4Dt2.hide();
                }
            });
            //一审管理员
            var auditAdmin4 = new phoenix.SuperSearch({
                name: 'auditAdmin',
                keyCode: 'ctrl+83',
                el: '#J-sp-auditAdmin4',
                group: group
            });
            //数据处理状态标识(处理中)
            var Title = new phoenix.SuperSearch({
                name: 'Title',
                group: group,
                expands: {getValue: function () {
                        return 3;
                    }}
            });
            SelectByWhereInfo4("0");
            group.removeEvent('dataChange');
            group.addEvent('dataChange', function () {
                SelectByWhereInfo4("0");
            });
            $("#per_page").change(function () {
                SelectByWhereInfo4("0");
            });
            function SelectByWhereInfo4(pages) {
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
                var formData4 = group.getFormData(), mask = phoenix.Mask.getInstance();
                var prepaidContent = function (data) {
                    var content = $('#data_item_processing').text();
                    content = content.split('[[mcSn]]').join(data.mcSn);
                    content = content.split('[[sn]]').join(data.sn);
                    content = content.split('[[exceptId]]').join(data.id);
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
                    content = content.replace('[[baseInfo]]', data.baseInfo);
                    content = content.replace('[[applyTime]]', data.applyTime);
                    content = content.replace('[[applyAccount]]', data.applyAccount);
                    content = content.replace('[[apprMemo]]', getSubString(data.apprMemo));
                    content = content.replace('[[userName]]', data.userName);
                    content = content.replace('[[agentName]]', data.agentName);
                    content = content.replace('[[bankName]]', data.bankName);
                    content = content.replace('[[bankAddr]]', data.bankAddr);
                    var disabled = (data.isAbleOprate && data.canCheckMowOprate) ? "" : "btn-disable";
                    var fontColor = data.canCheckMowOprate && data.isAbleOprate && data.myLocked ? "#25a38a" : "";
                    content = content.split('[[disble]]').join(disabled);
                    content = content.split('[[color]]').join(fontColor);
                    return content;
                };
                var bindBtnGetNow = function () {
                    //查詢狀態	
                    $('[name="getNowStatus"]').bind('click', function (e) {
                        $(this).addClass('btn-disable');
                        var sn = $(this).attr('sn');
                        var mcSn =  $(this).attr('mcSn');
                        var exceptId = $(this).attr('exceptId');
                        msg.show({
                            mask: true,
                            content:'获取中，请稍候',
                            confirmIsShow: false
                        });
                        msg.hideClose();
                        $.ajax({
                            url: '/admin/Rechargemange/index?parma=cufs',
                            dataType: 'json',
                            method: 'post',
                            data: {sn: sn ,mcSn:mcSn,exceptId:exceptId},
                            success: function (data) {
                                if (data.status == "1") {
                                    msg.show({
                                        mask: true,
                                        content:$('#alertMsgContent').text().replace('[[content]]',data.data.alertMsg),
                                        confirmIsShow: 'true',
                                        confirmText: '确定',
                                        confirmFun: function () {
                                            msg.hide();
                                            ProcessingData();
                                        }
                                    });
                                }else{
                                    msg.show({
                                        mask: true,
                                        content:$('#alertMsgContent').text().replace('[[content]]',data.data.alertMsg),
                                        confirmIsShow: 'true',
                                        confirmText: '确定',
                                        confirmFun: function () {
                                            msg.hide();
                                        }
                                    });
                                }
                            }
                        });
                    });
                };

                $("#J-table-data4>tbody").html("");
                $("#Pagination4").hide();
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=dv1',
                    dataType: 'json',
                    method: 'post',
                    data: formData4,
                    beforeSend: function () {
                        isLock = false;
                        TableStyle("showInfo4", 17, 1, "查询中");
                    },
                    success: function (data) {
                        //debugger
                        if (data.text.length > 0) {
                            $("#Pagination4").show();
                            var resultAll = eval(data);
                            var re = resultAll.text;
                            var recordNum = 0;
                            recordNum = resultAll.count[0].recordNum;
                            //分页回调				 
                            $("#Pagination4").pagination(recordNum, {
                                num_edge_entries: 2,
                                num_display_entries: 8,
                                current_page: pages,
                                items_per_page: per_page_number,
                                callback: SelectByWhereInfo4
                            });
                            var html = "";
                            //数据填充
                            $.each(re, function (i) {
                                var content = prepaidContent(re[i]);
                                html += content;
                            });
                            $("#J-table-data4>tbody").html(html);
                            if (isShowCell == false) {//状态判定加载相关数据		
                                $('[name="showType"]').hide();
                            }
                            else {
                                $('[name="showType"]').show();
                            }
                            bindBtnGetNow();
                        } else {
                            $("#Pagination4").hide();
                            TableStyle("J-table-data4>tbody", 17, 2, "没有相应数据");
                        }
                    },
                    complete: function ()
                    {
                        isLock = true;
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        TableStyle("J-table-data4>tbody", 17, 2, "数据异常");
                    }
                });
            }
        };
        //---------------------------------------------------(处理中)数据处理结束-----------------------------------------------------
    </script>
{/literal}