<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib prefix="permission" uri="/tag-permission"%>


<html>
<head>
	
	<title>统计报表-游戏明细报表</title>
	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
</head>
<body>
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="#">审核中心</a>><a href="#">统计报表</a>>游戏明细表
			</div>
			</div>
			<div class="col-content">
				<div class="col-main" style="overflow-x: auto;">
					<div class="main">
						<h3 class="ui-title">
						
					
						<li>
						<a id="J-button-export" style="float:left;" class="btn" href="javascript:void(0);">下载报表<b class="btn-inner"></b></a>
						</li>
						
						</h3>
								<table id="J-table-data" class="table table-info table-function">
									<thead>
										<tr>
											<th id="J-sp-tid" class="sp-td">
                                               <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">交易流水号</div>
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
											<th id="J-sp-userName" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">用户名</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:220px;">                                                         
                                                        <li>
                                                            <div class="input-append">
                                                             <label class="label" for="ck1"><input type="checkbox" checked="true" class="checkbox" name="type" id="J-select-containSub">包含下级</label>
                                                            
                                                                <input type="text" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
											<th>交易时间</th>
											<th id="J-sp-gameType" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">游戏类型</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                       <ul>
                                                            <li data-select-id="投注单"><a href="#">投注单</a></li>
                                                            <li data-select-id="追号单"><a href="#">追号单</a></li> 
                                                      </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
											<th id="J-sp-planCode" class="sp-td">
                                             <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">追号编号</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                             </div>
                                            </th>
											<th id="J-sp-orderCode" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">方案编号</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                             </div>
                                            </th>
											<th id="J-sp-reson" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">摘要</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                       <ul>
                                                            <li data-select-id="投注扣款"><a href="#">投注扣款</a></li>                                                  
                                                            <li data-select-id="投注退款"><a href="#">投注退款</a></li>                                                                                            
                                                            <li data-select-id="奖金派送"><a href="#">奖金派送</a></li>  
                                                            <li data-select-id="撤销派奖"><a href="#">撤销派奖</a></li>  
                                                            <li data-select-id="投注返点"><a href="#">投注返点</a></li>  
                                                            <li data-select-id="撤销返点"><a href="#">撤销返点</a></li>   
                                                            <li data-select-id="撤单费用"><a href="#">撤单费用</a></li> 
															<li data-select-id="1"><a href="#">奖金派送</a></li>
                                                      </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                            </div>
                                            </th>
											<th id="J-sp-amonut" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">金额</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                             </div>
                                            </th>
											<th id="J-sp-lotteryName" class="sp-td">
                                             <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">游戏</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                       <ul>
                                                            <li data-select-id="重庆时时彩"><a href="#">重庆时时彩</a></li>
                                                            <li data-select-id="宝开时时彩"><a href="#">宝开时时彩</a></li>                                                           
                                                            <li data-select-id="江西时时彩"><a href="#">江西时时彩</a></li>
                                                            <li data-select-id="新疆时时彩"><a href="#">新疆时时彩</a></li>
                                                            <li data-select-id="天津时时彩"><a href="#">天津时时彩</a></li>                                                           
                                                            <li data-select-id="上海时时乐"><a href="#">上海时时乐</a></li>
                                                            <li data-select-id="黑龙江时时彩"><a href="#">黑龙江时时彩</a></li>
                                                            <li data-select-id="山东11选5"><a href="#">山东11选5</a></li>
                                                            <li data-select-id="江西11选5"><a href="#">江西11选5</a></li>                                                           
                                                            <li data-select-id="江苏11选5"><a href="#">江苏11选5</a></li>                                                           
                                                            <li data-select-id="广东11选5"><a href="#">广东11选5</a></li>
                                                            <li data-select-id="重庆11选5"><a href="#">重庆11选5</a></li>
                                                            <li data-select-id="宝开11选5"><a href="#">宝开11选5</a></li>
                                                            <li data-select-id="秒开11选5"><a href="#">秒开11选5</a></li>
                                                            <li data-select-id="北京快乐8"><a href="#">北京快乐8</a></li>                                                           
                                                            <li data-select-id="3D"><a href="#">3D</a></li>
                                                            <li data-select-id="排列5"><a href="#">排列5</a></li>
                                                            <li data-select-id="双色球"><a href="#">双色球</a></li>
                                                            <li data-select-id="宝开一分彩"><a href="#">宝开一分彩</a></li>
                                                            <li data-select-id="秒开时时彩"><a href="#">秒开时时彩</a></li>
                                                            <li data-select-id="江苏快三"><a href="#">江苏快三</a></li>
                                                            <li data-select-id="安徽快三"><a href="#">安徽快三</a></li>                                                                                                                     
                                                            <li data-select-id="江苏骰宝"><a href="#">江苏骰宝</a></li>                                                                                                                    
                                                      </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                            </div>
                                            </th>
											<th id="J-sp-status" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">状态</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                       <ul>
                                                            <li data-select-id="0"><a href="#">处理中</a></li>
                                                            <li data-select-id="1"><a href="#">已完成</a></li>
                                                            <li data-select-id="2"><a href="#">未完成</a></li>
                                                            <li data-select-id="3"><a href="#">已撤销</a></li>
                                                      </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                            </div>
                                            </th>
										</tr>
									</thead>
									<tbody id="showInfo">
									</tbody>
                                    <tfoot>
                                        <tr>
                                         <td colspan="10">
                                                <div id="Pagination" class="pagination" ></div>
                                            </td>
                                        </tr>
                                    </tfoot>
								</table>
					</div>
				</div>
			</div>
			

	<script type="text/javascript">global_path_url="..";</script>
    <script type="text/javascript" src="${staticFileContextPath}/static/js/risk/report/transactionReportList.js"></script>
<script type="text/template" id="J-tpl-export">
	<form action=exportGameRiskTransactionReport target="_blank" id="J-download-form">		
		<ul class="ui-form ui-form-small">
		<li><span class="ui-singleline">将会采用Excel，为您导出一个月的数据</span></li>	
		</ul>
	</form>
</script>
</body>