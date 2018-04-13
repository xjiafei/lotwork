<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
<div class="col-content">
    <!-- //////////////头部公共页面////////////// -->
    {include file='/admin/funds/left.tpl'}
    <!-- /////////////头部公共页面/////////////// -->
    <div class="col-main">
        <div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; <span id="titleName">申訴相关配置</span></div></div>
        <div class="col-content">
            <div class="col-main">
                <div class="main">
                    <div class="ui-tab">
                        <ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">

                            <!-- {if "FUND_APPEAL_TIPS_CONFIG"|in_array:$smarty.session.datas.info.acls} -->
                            <span><a href="/admin/Rechargemange/index?parma=abtf"><li class='current'>申诉提示配置</li></a></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                            <!-- {if "FUND_APPEAL_BANKS_CONFIG"|in_array:$smarty.session.datas.info.acls} -->
                            <span><a href="/admin/Rechargemange/index?parma=abcf"><li>申诉银行配置</li></a></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                            <!-- {if "FUND_APPEAL_BANKS_CONFIG"|in_array:$smarty.session.datas.info.acls} -->
                            <span><a href="/admin/Rechargemange/index?parma=pabcf"><li>移动端申诉银行配置</li></a></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                        </ul>
                        <ul class="ui-form ui-tab-content ui-tab-content-current"  id="DivRules">
                            <h3 class="ui-title"><a href="javascript: void(0);" id="addReview" class="btn btn-small" style="float:left;">添加审核内容<b class="btn-inner"></b></a></h3>
                            <li id="show_info">
                                <table id="passAryTB" class="table table-info1 table-function" width="300" style="table-layout:fixed;word-wrap:break-word;">
                                    <div class="text-center w-2" ><strong>申诉审核通过 :</strong></td>
                                        <thead>
                                            <tr>
                                                <th class="text-center w-1">序號</th>
                                                <th class="text-center w-1">備註</th>
                                                <th class="text-center w-4">內容</th>
                                                <th class="text-center w-1">設置</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <!-- {foreach from=$passAry key=i item=data} -->
                                            <tr>
                                                <td class="text-center w-1">{$i+1}</td>
                                                <td jsonName='tipsModel' style="display:none;">{$data.tipsModel}</td>
                                                <td jsonName='tipsGroupb' style="display:none;">{$data.tipsGroupb}</td>
                                                <td jsonName='tipsMemo'   style="text-align:left">{$data.tipsMemo}</td>
                                                <td jsonName='tipsContext'  style="text-align:left">{$data.tipsContext}</td>
                                                <td class="text-center w-1"><a href="javascript:void(0);" onclick="check(this, 'MoveUp')">向上</a>&nbsp;&nbsp;<a name="deleteOper" onclick="deleteRow(this)" href="javascript:void(0);">删除</a></td>
                                            </tr>
                                            <!-- {/foreach} -->
                                        </tbody>
                                </table>
                            </li>
                            <li>
                                <table id='unPassAryTB' class="table table-info1 table-function" width="300" style="table-layout:fixed;word-wrap:break-word;">
                                    <div class="text-center w-2" ><strong>申诉审核未通过 :</strong></td>
                                        <thead>
                                            <tr>
                                                <th class="text-center w-1">序號</th>
                                                <th class="text-center w-1">備註</th>
                                                <th class="text-center w-4" >內容</th>
                                                <th class="text-center w-1">設置</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <!-- {foreach from=$unPassAry key=i item=data} -->
                                            <tr>
                                                <td class="text-center w-1">{$i+1}</td>
                                                <td jsonName='tipsModel' style="display:none;">{$data.tipsModel}</td>
                                                <td jsonName='tipsGroupb' style="display:none;">{$data.tipsGroupb}</td>
                                                <td jsonName='tipsMemo'>{$data.tipsMemo}</td>
                                                <td jsonName='tipsContext'>{$data.tipsContext}</td>
                                                <td class="text-center w-1"><a href="javascript:void(0);" onclick="check(this, 'MoveUp')">向上</a>&nbsp;&nbsp;<a name="deleteOper" onclick="deleteRow(this)" href="javascript:void(0);">删除</a></td>
                                            </tr>
                                            <!-- {/foreach} -->
                                        </tbody>
                                        
                                </table>
                            </li>
                            <div align="center"><a class="btn btn-important w-1 card-setting-savebutton" id="J-Submit-Button">保存</a></div>
                        </ul>
                        
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--新增审核内容-->
    <div class="pop w-8" id="AddTipsWindow" style="display:none;">
        <div class="hd">
            <i class="close" name="closeIcoDiv1"></i>
            <h3 align="center">新增审核内容</h3>
        </div>
        <div class="bd">
            <ul class="ui-form ui-form-small">
                <li>
                    <form id="addForm">
                        <label class="ui-label w-auto">审核類型：</label>
                        <select id="addtype" class="ui-select w-3" name="arguetype">
                            <option value="-1" selected>请选择</option>
                            <option value="1" >申诉审核通过</option>
                            <option value="0" >申诉审核不通过</option>
                        </select>
                        <br>
                        <label class="ui-label w-auto" align="right">备注：</label>
                        <input type="text" id="memo" name="memo">
                        <br>
                    </form>
                        <label class="ui-label w-auto">提示內容：</label>			
                        <textarea id="content" name="content" form="addForm" rows="10" cols="50" onkeyup='wordCount()'></textarea><br/>
                        <div class="ui-text-prompt" id='words'>0</div><div class="ui-text-prompt">/150</div>
                        <li> 
                            <a href="javascript:void(0);" class="btn" id="addSubmit">确认增加<b class="btn-inner"></b></a>	
                            <input class="btn" name="closeIcoDiv1" value="取消"  style="width:80px;"/>
                        </li>
                    
                </li>
            </ul>
        </div>
    </div>
</div>

{include file='/admin/script-base.tpl'}
{literal}
    <script>
        function wordCount() {
            var total = $('#content').val().length;
            if(total > 150){
                alert("內容已達最大上限數");
                var content = $('#content').val().substr(0,150);
                $('#content').val(content);
                return;
            }
            $('#words').text(total);
        }
        
        (function () {
            var form1 = $('#J-form'), minWindow, mask,isLock = true;
            minWindow = new phoenix.MiniWindow();
            mask = phoenix.Mask.getInstance();
            minWindow.addEvent('beforeShow', function () {
                mask.show();
            });
            minWindow.addEvent('afterHide', function () {
                mask.hide();
            });

            selectMenu('Menufunds', 'MenuFundRechargeAppealConfig');
            option = {zIndex: 500},
            box2 = new LightBox("AddTipsWindow", option),
            $(document).on('click', "#addReview", function () {
                box2.OverLay.Color = "rgb(51, 51, 51)";
                box2.Over = true;
                box2.OverLay.Opacity = 50;
                box2.Fixed = true;
                box2.Center = true;
                box2.Show();
            });

            $(document).on('click', '[name="closeIcoDiv1"]', function (e) {
                box2.Close();
            });
            
            $('#J-Submit-Button').click(function () {
                var tableId1 = 'passAryTB';
                var tableId2 = 'unPassAryTB';
                var arr = getTableParam(tableId1);
                var arr2 = getTableParam(tableId2);
                $.merge(arr,arr2);
                
                var obj1 = new Object();
                var obj2 = new Object();
                //obj1.tipsGroupa = '0';
                obj1.tipsModel = '0';
                obj2.tips=obj1;
                obj2.tipsList=arr;
                $.ajax({
                    url:'/admin/Rechargemange/index?parma=ts',
                    method: "post",
                    //data:{data:JSON.stringify(obj2)},
                    data:{data:obj2},
                    beforeSend: function () {
                        isLock = false;
                        TableStyle("DivRules", 19, 1, "保存中");
                    },
                    success:function(){
                        location.reload();
                    },
                    complete: function ()
                    {
                        isLock = true;
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        TableStyle("DivRules", 17, 2, "数据异常");
                    }
                    
                });
            });
            //充值上下限配置置(表单提交校验)
            $('#addSubmit').click(function () {
                if($('#addtype').val() == -1){
                    alert("请选择审核類型!");
                    return;
                }
                if($('#memo').val() ==""){
                    alert("请填写备注!");
                    return;
                }
                if($('#content').val() ==""){
                    alert("请填写提示內容!");
                    return;
                }
                $.ajax({
                    url: "/admin/Rechargemange/index?parma=at",
                    data:$('#addForm').serialize(),
                    method:'post',
                    success:function(){
                        location.reload();
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        TableStyle("DivRules", 17, 2, "数据异常");
                    }
                });
            });
        })();
        
        function getTableParam(tableId){
            var param = '#' + tableId + " tbody tr";
            var ary = new Array();
            $(param).each(function(index){
                var obj = new Object();
                jQuery(this).find('td[jsonName]').each(function(){
                    //alert($(this).attr("jsonName"));                        
                    //alert($(this).text());                                                
                    obj[$(this).attr("jsonName")]=$(this).text();
                    obj.tipsGroupa = '0';
                });
                ary[index]=obj;
            });
            return ary;
        }
        
        function check(t, oper) {
            var data_tr = $(t).parent().parent(); //获取到触发的tr
            if (oper == "MoveUp") {    //向上移动
                if ($(data_tr).prev().html() == null) { //获取tr的前一个相同等级的元素是否为空
                    alert("已经是最顶部了!");
                    return;
                }
                {
                    $(data_tr).insertBefore($(data_tr).prev()); //将本身插入到目标tr的前面 
                }
            } else {
                if ($(data_tr).next().html() == null) {
                    alert("已经是最低部了!");
                    return;
                }
                {
                    $(data_tr).insertAfter($(data_tr).next()); //将本身插入到目标tr的后面 
                }
            }
        }
        
        function deleteRow(k){
            $(k).parent().parent().remove(); 
        }
    </script>
{/literal}
</body>
</html>