<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<title>奖期规则查看页-奖期修改</title>


</head>
<body>
<div id="tab_menu_id" style="display:none">issueRuleMenu</div>
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>><a href="${currentContextPath}/gameoa/gameIssueIndex?lotteryId=99101&ruleId="><spring:message code="gameCenter_jiangqiguize"/></a>>修改奖期规则
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
							<div class="ui-tab-title clearfix">
								<ul>
									<jsp:include page="../lotteryConfigHeader.jsp"></jsp:include>
								</ul>
							</div>
					
					
						<h3 class="ui-title"><a href="${currentContextPath}/gameoa/gameIssueIndex?lotteryId=${lotteryId}&ruleId=" >&lt;&lt; 返回奖期规则列表</a></h3>
						
						<div class="ui-tab">
							
								<ul class="ui-tab-title ui-tab-title2 clearfix">
									<li id="li1"><spring:message code="gameCenter_changguiguize" /></li>
									<li id="li2"><spring:message code="gameCenter_teliguize" /></li>
									<li id="li3"><spring:message code="gameCenter_xiushiweihushijian" /></li>
									<input type="hidden" value="${ruleType}" id="ruleType" />
									<input type="hidden" value="${ruleStatus}" name = "ruleStatus" id="ruleStatus"/>
									<input type="hidden" value="${gameIssue.ruleStartTime }" id="ruleStartTime"/>
									<input type="hidden" value="${gameIssue.ruleEndTime }" id="ruleEndTime"/>
									<input type="hidden" value="${gameIssue.openAwardPeriod}" id="openAwardPeriod"/>
									<input type="hidden" value="${gameIssue.issueRulesName }" id="issueRulesName"/>
									<input type="hidden" value='${salesIssueStrucs}' id="salesIssueStrucs"/>
									<input type="hidden" value="${ruleId}" id="ruleId"/>
									<input type="hidden" value="${lotteryId }" id="lotteryId"/>
									<input type="hidden" value="${gameIssue.stopStartTime }" id="stopStartTime" />
									<input type="hidden" value="${gameIssue.stopEndTime }" id="stopEndTime" />
									<input type="hidden" value="${gameIssue.seriesIssueCode }" id="seriesIssueCodeView" />
								</ul>			
								
							<!--	<h3 class="ui-title"><spring:message code="gameCenter_jiangqiguizetiaozheng" /></h3>	-->			
							
								<ul class="ui-form ui-tab-content clearfix"  id="DivRules">
									<li>
                                   
										<label for="" class="ui-label w-4"><spring:message code="gameCenter_xiugaihouguizekaishizhixingshijiandian"/>：</label>									
                                         <input type="text" class="input  w-3" id="J-date-start" value="" name="times">
									</li>
									<li>
										<label for="" class="ui-label w-4">开奖周期：</label>
										<span class="checkbox-list" >
											<label class="label" for="days"><input type="checkbox" class="checkbox"  autocomplete="off" name="checkbox1"  value="1">周一</label>
											<label class="label" for="days"><input type="checkbox" class="checkbox" autocomplete="off" name="checkbox1" value="2">周二</label>
											<label class="label" for="days"><input type="checkbox" class="checkbox" autocomplete="off" name="checkbox1" value="3">周三</label>
											<label class="label" for="days"><input type="checkbox" class="checkbox" autocomplete="off" name="checkbox1" value="4">周四</label>
											<label class="label" for="days"><input type="checkbox" class="checkbox" autocomplete="off" name="checkbox1" value="5">周五</label>
											<label class="label" for="days"><input type="checkbox" class="checkbox" autocomplete="off" name="checkbox1" value="6">周六</label>
											<label class="label" for="days"><input type="checkbox" class="checkbox" autocomplete="off" name="checkbox1" value="7">周日</label>
										</span>
									</li>
									<li  id="cgBreak">
										<label class="ui-label w-4 big" for=""><spring:message code="gameCenter_fenduanjiangqishijian" />：</label>
										<a class="ui-text-info" href="javascript:void(0);" id="AddRule"><spring:message code="gameCenter_xinzengfenduanguize" /></a>
									</li>
									<!-- <li class="break-time">
									</li> -->	
								
									<li class="ui-btn">
										<label for="" class="ui-label w-1"></label>
										<a href="javascript:void(0);" class="btn btn-important" id="J_Submit_Button">确 认<b class="btn-inner"></b></a>
										<a href="javascript:void(0);" class="btn" id="J_clean_botton">取 消<b class="btn-inner"></b></a>
									</li>
								</ul>	
								<ul class="ui-form ui-tab-content" id="tlRuleUl">
									<li>
										<label for="" class="ui-label w-4"><spring:message code="gameCenter_teliguizejiangqimingcheng" />：</label>
										<input type="text" value="${gameIssue.issueRulesName }" id="issueRulesNameId" autocomplete="off" class="input  w-2"  name="names"  />
									</li>
									<li>
										<label for="" class="ui-label w-4"><spring:message code="gameCenter_telishijianduan" />：</label>
										 <input type="text" value="" id="J-date-exception-start" class="input  w-3" autocomplete="off"  name="times"  /> - <input type="text" id="J-date-exception-end" value="" class="input  w-3"   name="times"  />
									</li>
									<li>
										<label for="" class="ui-label w-4">开奖周期：</label>
										<span class="checkbox-list">
											<label class="label" for=""><input type="checkbox" class="checkbox" autocomplete="off" name="checkbox2" value="1">周一</label>
											<label class="label" for=""><input type="checkbox" class="checkbox" autocomplete="off" name="checkbox2" value="2">周二</label>
											<label class="label" for=""><input type="checkbox" class="checkbox" autocomplete="off" name="checkbox2" value="3">周三</label>
											<label class="label" for=""><input type="checkbox" class="checkbox" autocomplete="off" name="checkbox2" value="4">周四</label>
											<label class="label" for=""><input type="checkbox" class="checkbox" autocomplete="off" name="checkbox2" value="5">周五</label>
											<label class="label" for=""><input type="checkbox" class="checkbox" autocomplete="off" name="checkbox2" value="6">周六</label>
											<label class="label" for=""><input type="checkbox" class="checkbox" autocomplete="off" name="checkbox2" value="7">周日</label>
										</span>
									</li>
									 <li id="tlBreak">
										<label for="" class="ui-label w-4 big"><spring:message code="gameCenter_fenduanjiangqishijian" />：</label>
										<a id="Addrule_a" href="javascript:void(0);" class="ui-text-info"><spring:message code="gameCenter_xinzengfenduanguize" /></a>
									</li>	
									
									<li class="ui-btn">
										<label for="" class="ui-label w-1"></label>
										<a id="J_Submit_Button_a" href="javascript:void(0);" class="btn btn-important" >确 认<b class="btn-inner"></b></a>
										<a href="javascript:void(0);" class="btn" id="J_clean_botton_a">取 消<b class="btn-inner"></b></a>
									</li>
								</ul>
								<ul class="ui-form ui-tab-content" id="xsRuleUl">
									<li>
										<label for="" class="ui-label w-4"><spring:message code="gameCenter_xiushiweihushijianmingcheng" />：</label>
										<input type="text" value="" class="input  w-2" name="names" id="X_IssueName" autocomplete="off"/>
									</li>
									<li>
										<label for="" class="ui-label w-4"><spring:message code="gameCenter_xiushiweihushijianduan" />：</label>
										<input type="text" value="" class="input  w-3"  id="maintenanceTimeStarts" name="times" autocomplete="off"/> - <input type="text" value="" class="input  w-3" id="maintenanceTimeEnd" name="times"/>
										<input type="checkbox" value="0" class="checkbox" name="seriesIssueCode" id="seriesIssueCode"><spring:message code="gameCenter_qihaolianxu" />
									</li>
									<li>
										<label for="" class="ui-label w-4">休市/维护周期：</label>
										<span class="checkbox-list">
											<label class="label" for=""><input type="checkbox" class="checkbox" autocomplete="off"  name="checkbox3" value="1">周一</label>
											<label class="label" for=""><input type="checkbox" class="checkbox"  autocomplete="off"  name="checkbox3" value="2">周二</label>
											<label class="label" for=""><input type="checkbox" class="checkbox"   autocomplete="off"  name="checkbox3" value="3">周三</label>
											<label class="label" for=""><input type="checkbox" class="checkbox"  autocomplete="off"  name="checkbox3" value="4">周四</label>
											<label class="label" for=""><input type="checkbox" class="checkbox"  autocomplete="off"  name="checkbox3" value="5">周五</label>
											<label class="label" for=""><input type="checkbox" class="checkbox"  autocomplete="off"   name="checkbox3" value="6">周六</label>
											<label class="label" for=""><input type="checkbox" class="checkbox"  autocomplete="off"  name="checkbox3" value="7">周日</label>
										</span>
									</li>
									
									<li>
												<label for="" class="ui-label w-4"><spring:message code="gameCenter_xiushiweihukaishishijian" />：</label>
												<select class="ui-select w-1"  autocomplete="off"  name="firstStopH2" id="firstStopH2">
													<option value="00">00</option>
													<option value="01">01</option>
													<option value="02">02</option>
													<option value="03">03</option>
													<option value="04">04</option>
													<option value="05">05</option>
													<option value="06">06</option>
													<option value="07">07</option>
													<option value="08">08</option>
													<option value="09">09</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="13">13</option>
													<option value="14">14</option>
													<option value="15">15</option>
													<option value="16">16</option>
													<option value="17">17</option>
													<option value="18">18</option>
													<option value="19">19</option>
													<option value="20">20</option>
													<option value="21">21</option>
													<option value="22">22</option>
													<option value="23">23</option>
												</select>&nbsp;&nbsp;：
												<select class="ui-select w-1"  autocomplete="off"  name="firstStopM2" id="firstStopM2">
													<option value="00">00</option>
													<option value="01">01</option>
													<option value="02">02</option>
													<option value="03">03</option>
													<option value="04">04</option>
													<option value="05">05</option>
													<option value="06">06</option>
													<option value="07">07</option>
													<option value="08">08</option>
													<option value="09">09</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="13">13</option>
													<option value="14">14</option>
													<option value="15">15</option>
													<option value="16">16</option>
													<option value="17">17</option>
													<option value="18">18</option>
													<option value="19">19</option>
													<option value="20">20</option>
													<option value="21">21</option>
													<option value="22">22</option>
													<option value="23">23</option>
													<option value="24">24</option>
													<option value="25">25</option>
													<option value="26">26</option>
													<option value="27">27</option>
													<option value="28">28</option>
													<option value="29">29</option>
													<option value="30">30</option>
													<option value="31">31</option>
													<option value="32">32</option>
													<option value="33">33</option>
													<option value="34">34</option>
													<option value="35">35</option>
													<option value="36">36</option>
													<option value="37">37</option>
													<option value="38">38</option>
													<option value="39">39</option>
													<option value="40">40</option>
													<option value="41">41</option>
													<option value="42">42</option>
													<option value="43">43</option>
													<option value="44">44</option>
													<option value="45">45</option>
													<option value="46">46</option>
													<option value="47">47</option>
													<option value="48">48</option>
													<option value="49">49</option>
													<option value="50">50</option>
													<option value="51">51</option>
													<option value="52">52</option>
													<option value="53">53</option>
													<option value="54">54</option>
													<option value="55">55</option>
													<option value="56">56</option>
													<option value="57">57</option>
													<option value="58">58</option>
													<option value="59">59</option>
												</select>&nbsp;&nbsp;：
												<select class="ui-select w-1"   autocomplete="off"  name="firstStopS2" id="firstStopS2">
													<option value="00">00</option>
													<option value="01">01</option>
													<option value="02">02</option>
													<option value="03">03</option>
													<option value="04">04</option>
													<option value="05">05</option>
													<option value="06">06</option>
													<option value="07">07</option>
													<option value="08">08</option>
													<option value="09">09</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="13">13</option>
													<option value="14">14</option>
													<option value="15">15</option>
													<option value="16">16</option>
													<option value="17">17</option>
													<option value="18">18</option>
													<option value="19">19</option>
													<option value="20">20</option>
													<option value="21">21</option>
													<option value="22">22</option>
													<option value="23">23</option>
													<option value="24">24</option>
													<option value="25">25</option>
													<option value="26">26</option>
													<option value="27">27</option>
													<option value="28">28</option>
													<option value="29">29</option>
													<option value="30">30</option>
													<option value="31">31</option>
													<option value="32">32</option>
													<option value="33">33</option>
													<option value="34">34</option>
													<option value="35">35</option>
													<option value="36">36</option>
													<option value="37">37</option>
													<option value="38">38</option>
													<option value="39">39</option>
													<option value="40">40</option>
													<option value="41">41</option>
													<option value="42">42</option>
													<option value="43">43</option>
													<option value="44">44</option>
													<option value="45">45</option>
													<option value="46">46</option>
													<option value="47">47</option>
													<option value="48">48</option>
													<option value="49">49</option>
													<option value="50">50</option>
													<option value="51">51</option>
													<option value="52">52</option>
													<option value="53">53</option>
													<option value="54">54</option>
													<option value="55">55</option>
													<option value="56">56</option>
													<option value="57">57</option>
													<option value="58">58</option>
													<option value="59">59</option>
												</select>
											</li>
											<li>
												<label for="" class="ui-label w-4"><spring:message code="gameCenter_xiushiweihujieshushijian" />：</label>
												<select class="ui-select w-1" id="lastStopH2"  autocomplete="off" name="lastStopH2">
													<option value="00">00</option>
													<option value="01">01</option>
													<option value="02">02</option>
													<option value="03">03</option>
													<option value="04">04</option>
													<option value="05">05</option>
													<option value="06">06</option>
													<option value="07">07</option>
													<option value="08">08</option>
													<option value="09">09</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="13">13</option>
													<option value="14">14</option>
													<option value="15">15</option>
													<option value="16">16</option>
													<option value="17">17</option>
													<option value="18">18</option>
													<option value="19">19</option>
													<option value="20">20</option>
													<option value="21">21</option>
													<option value="22">22</option>
													<option value="23">23</option>
												</select>&nbsp;&nbsp;：
												<select class="ui-select w-1" id="lastStopM2"  autocomplete="off" name="lastStopM2">
													<option value="00">00</option>
													<option value="01">01</option>
													<option value="02">02</option>
													<option value="03">03</option>
													<option value="04">04</option>
													<option value="05">05</option>
													<option value="06">06</option>
													<option value="07">07</option>
													<option value="08">08</option>
													<option value="09">09</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="13">13</option>
													<option value="14">14</option>
													<option value="15">15</option>
													<option value="16">16</option>
													<option value="17">17</option>
													<option value="18">18</option>
													<option value="19">19</option>
													<option value="20">20</option>
													<option value="21">21</option>
													<option value="22">22</option>
													<option value="23">23</option>
													<option value="24">24</option>
													<option value="25">25</option>
													<option value="26">26</option>
													<option value="27">27</option>
													<option value="28">28</option>
													<option value="29">29</option>
													<option value="30">30</option>
													<option value="31">31</option>
													<option value="32">32</option>
													<option value="33">33</option>
													<option value="34">34</option>
													<option value="35">35</option>
													<option value="36">36</option>
													<option value="37">37</option>
													<option value="38">38</option>
													<option value="39">39</option>
													<option value="40">40</option>
													<option value="41">41</option>
													<option value="42">42</option>
													<option value="43">43</option>
													<option value="44">44</option>
													<option value="45">45</option>
													<option value="46">46</option>
													<option value="47">47</option>
													<option value="48">48</option>
													<option value="49">49</option>
													<option value="50">50</option>
													<option value="51">51</option>
													<option value="52">52</option>
													<option value="53">53</option>
													<option value="54">54</option>
													<option value="55">55</option>
													<option value="56">56</option>
													<option value="57">57</option>
													<option value="58">58</option>
													<option value="59">59</option>
												</select>&nbsp;&nbsp;：
												<select class="ui-select w-1" id="lastStopS2"  autocomplete="off" name="lastStopS2">
													<option value="00">00</option>
													<option value="01">01</option>
													<option value="02">02</option>
													<option value="03">03</option>
													<option value="04">04</option>
													<option value="05">05</option>
													<option value="06">06</option>
													<option value="07">07</option>
													<option value="08">08</option>
													<option value="09">09</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="13">13</option>
													<option value="14">14</option>
													<option value="15">15</option>
													<option value="16">16</option>
													<option value="17">17</option>
													<option value="18">18</option>
													<option value="19">19</option>
													<option value="20">20</option>
													<option value="21">21</option>
													<option value="22">22</option>
													<option value="23">23</option>
													<option value="24">24</option>
													<option value="25">25</option>
													<option value="26">26</option>
													<option value="27">27</option>
													<option value="28">28</option>
													<option value="29">29</option>
													<option value="30">30</option>
													<option value="31">31</option>
													<option value="32">32</option>
													<option value="33">33</option>
													<option value="34">34</option>
													<option value="35">35</option>
													<option value="36">36</option>
													<option value="37">37</option>
													<option value="38">38</option>
													<option value="39">39</option>
													<option value="40">40</option>
													<option value="41">41</option>
													<option value="42">42</option>
													<option value="43">43</option>
													<option value="44">44</option>
													<option value="45">45</option>
													<option value="46">46</option>
													<option value="47">47</option>
													<option value="48">48</option>
													<option value="49">49</option>
													<option value="50">50</option>
													<option value="51">51</option>
													<option value="52">52</option>
													<option value="53">53</option>
													<option value="54">54</option>
													<option value="55">55</option>
													<option value="56">56</option>
													<option value="57">57</option>
													<option value="58">58</option>
													<option value="59">59</option>
												</select>
											</li>
									<li class="ui-btn">
										<label for="" class="ui-label w-1"></label>
										<a href="javascript:void(0);" class="btn btn-important" id="J_Submit_Button_b">确 认<b class="btn-inner"></b></a>
										<a href="javascript:void(0);" class="btn" id="J_clean_botton_b">取 消<b class="btn-inner"></b></a>
									</li>
								</ul>
								
								<div style="height:800px;"></div>
								
							</div>
						
					</div>
				</div>
			</div>
	<textarea id="UpdateSegmented" style="display:none;">							
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">系统检测到您已经设置过一条常规规则了，同时不能存在两条有效待发布常规规则，请勿重复添加</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn" id="closeDd">关 闭<b class="btn-inner"></b></a>
            </div>
        </div>
    </textarea>
    
    <textarea id="DeleteSegmented" style="display:none;">
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">您确定要删除该分段时间吗？</h4>
            </div>
            <div class="pop-btn">
                <a href="#" class="btn btn-important " id="J-submit-safePassword"><spring:message code="gameCenter_shanchu" /><b class="btn-inner"></b></a>
                <a href="#" class="btn" id="closeDs">取 消<b class="btn-inner"></b></a>
            </div>
        </div>
    </textarea>

    <textarea id="addbreaktimeHtml" style="display:none;">
       <li class="break-time">
			<input type="radio" name="radios" class="radio">
			<ul class="ui-form">
				<li class="close delete-close"></li>
				<!-- <li>
					<label class="ui-label w-3" for="">销售开始时间：</label>
					<select name="saleH" id="" class="ui-select w-1">
						<option value="00">00</option>
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
					</select>&nbsp;&nbsp;：
					<select name="saleM" id="" class="ui-select w-1">
						<option value="00">00</option>
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
						<option value="24">24</option>
						<option value="25">25</option>
						<option value="26">26</option>
						<option value="27">27</option>
						<option value="28">28</option>
						<option value="29">29</option>
						<option value="30">30</option>
						<option value="31">31</option>
						<option value="32">32</option>
						<option value="33">33</option>
						<option value="34">34</option>
						<option value="35">35</option>
						<option value="36">36</option>
						<option value="37">37</option>
						<option value="38">38</option>
						<option value="39">39</option>
						<option value="40">40</option>
						<option value="41">41</option>
						<option value="42">42</option>
						<option value="43">43</option>
						<option value="44">44</option>
						<option value="45">45</option>
						<option value="46">46</option>
						<option value="47">47</option>
						<option value="48">48</option>
						<option value="49">49</option>
						<option value="50">50</option>
						<option value="51">51</option>
						<option value="52">52</option>
						<option value="53">53</option>
						<option value="54">54</option>
						<option value="55">55</option>
						<option value="56">56</option>
						<option value="57">57</option>
						<option value="58">58</option>
						<option value="59">59</option>
					</select>&nbsp;&nbsp;：
					<select name="saleS" id="" class="ui-select w-1">
						<option value="00">00</option>
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
						<option value="24">24</option>
						<option value="25">25</option>
						<option value="26">26</option>
						<option value="27">27</option>
						<option value="28">28</option>
						<option value="29">29</option>
						<option value="30">30</option>
						<option value="31">31</option>
						<option value="32">32</option>
						<option value="33">33</option>
						<option value="34">34</option>
						<option value="35">35</option>
						<option value="36">36</option>
						<option value="37">37</option>
						<option value="38">38</option>
						<option value="39">39</option>
						<option value="40">40</option>
						<option value="41">41</option>
						<option value="42">42</option>
						<option value="43">43</option>
						<option value="44">44</option>
						<option value="45">45</option>
						<option value="46">46</option>
						<option value="47">47</option>
						<option value="48">48</option>
						<option value="49">49</option>
						<option value="50">50</option>
						<option value="51">51</option>
						<option value="52">52</option>
						<option value="53">53</option>
						<option value="54">54</option>
						<option value="55">55</option>
						<option value="56">56</option>
						<option value="57">57</option>
						<option value="58">58</option>
						<option value="59">59</option>
					</select>
				</li> -->
				<c:if test="${lotteryId!=99108&&lotteryId!=99109&&lotteryId!=99401&&lotteryId!=99701}">
					<li>
						<label for="" class="ui-label w-3"><spring:message code="gameCenter_xiaoshouzhouqi" />：</label>
						<input type="text" value="" class="input sale-time w-1" name="saleWeek"/>
					</li>
				</c:if>
				<li>
					<label for="" class="ui-label w-3"><spring:message code="gameCenter_dengdaikaijiangshijian" />：</label>
					<input type="text" value="" class="input sale-wait-time w-1" name="saleWait" />
				</li>
				<li>
					<label class="ui-label w-3" for=""><spring:message code="gameCenter_guanfangdiyiqikaijiangshijian" />：</label>
					<select name="firstH" id="" class="ui-select w-1">
						<option value="00">00</option>
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
					</select>&nbsp;&nbsp;：
					<select name="firstM" id="" class="ui-select w-1">
						<option value="00">00</option>
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
						<option value="24">24</option>
						<option value="25">25</option>
						<option value="26">26</option>
						<option value="27">27</option>
						<option value="28">28</option>
						<option value="29">29</option>
						<option value="30">30</option>
						<option value="31">31</option>
						<option value="32">32</option>
						<option value="33">33</option>
						<option value="34">34</option>
						<option value="35">35</option>
						<option value="36">36</option>
						<option value="37">37</option>
						<option value="38">38</option>
						<option value="39">39</option>
						<option value="40">40</option>
						<option value="41">41</option>
						<option value="42">42</option>
						<option value="43">43</option>
						<option value="44">44</option>
						<option value="45">45</option>
						<option value="46">46</option>
						<option value="47">47</option>
						<option value="48">48</option>
						<option value="49">49</option>
						<option value="50">50</option>
						<option value="51">51</option>
						<option value="52">52</option>
						<option value="53">53</option>
						<option value="54">54</option>
						<option value="55">55</option>
						<option value="56">56</option>
						<option value="57">57</option>
						<option value="58">58</option>
						<option value="59">59</option>
					</select>&nbsp;&nbsp;：
					<select name="firstS" id="" class="ui-select w-1">
						<option value="00">00</option>
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
						<option value="24">24</option>
						<option value="25">25</option>
						<option value="26">26</option>
						<option value="27">27</option>
						<option value="28">28</option>
						<option value="29">29</option>
						<option value="30">30</option>
						<option value="31">31</option>
						<option value="32">32</option>
						<option value="33">33</option>
						<option value="34">34</option>
						<option value="35">35</option>
						<option value="36">36</option>
						<option value="37">37</option>
						<option value="38">38</option>
						<option value="39">39</option>
						<option value="40">40</option>
						<option value="41">41</option>
						<option value="42">42</option>
						<option value="43">43</option>
						<option value="44">44</option>
						<option value="45">45</option>
						<option value="46">46</option>
						<option value="47">47</option>
						<option value="48">48</option>
						<option value="49">49</option>
						<option value="50">50</option>
						<option value="51">51</option>
						<option value="52">52</option>
						<option value="53">53</option>
						<option value="54">54</option>
						<option value="55">55</option>
						<option value="56">56</option>
						<option value="57">57</option>
						<option value="58">58</option>
						<option value="59">59</option>
					</select>
				</li>
				<li>
					<label class="ui-label w-3" for=""><spring:message code="gameCenter_guanfangzuihouyiqikaijiangshijian" />：</label>
					<select name="lastH" id="" class="ui-select w-1">
						<option value="00">00</option>
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
					</select>&nbsp;&nbsp;：
					<select name="lastM" id="" class="ui-select w-1">
						<option value="00">00</option>
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
						<option value="24">24</option>
						<option value="25">25</option>
						<option value="26">26</option>
						<option value="27">27</option>
						<option value="28">28</option>
						<option value="29">29</option>
						<option value="30">30</option>
						<option value="31">31</option>
						<option value="32">32</option>
						<option value="33">33</option>
						<option value="34">34</option>
						<option value="35">35</option>
						<option value="36">36</option>
						<option value="37">37</option>
						<option value="38">38</option>
						<option value="39">39</option>
						<option value="40">40</option>
						<option value="41">41</option>
						<option value="42">42</option>
						<option value="43">43</option>
						<option value="44">44</option>
						<option value="45">45</option>
						<option value="46">46</option>
						<option value="47">47</option>
						<option value="48">48</option>
						<option value="49">49</option>
						<option value="50">50</option>
						<option value="51">51</option>
						<option value="52">52</option>
						<option value="53">53</option>
						<option value="54">54</option>
						<option value="55">55</option>
						<option value="56">56</option>
						<option value="57">57</option>
						<option value="58">58</option>
						<option value="59">59</option>
					</select>&nbsp;&nbsp;：
					<select name="lastS" id="" class="ui-select w-1">
						<option value="00">00</option>
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
						<option value="24">24</option>
						<option value="25">25</option>
						<option value="26">26</option>
						<option value="27">27</option>
						<option value="28">28</option>
						<option value="29">29</option>
						<option value="30">30</option>
						<option value="31">31</option>
						<option value="32">32</option>
						<option value="33">33</option>
						<option value="34">34</option>
						<option value="35">35</option>
						<option value="36">36</option>
						<option value="37">37</option>
						<option value="38">38</option>
						<option value="39">39</option>
						<option value="40">40</option>
						<option value="41">41</option>
						<option value="42">42</option>
						<option value="43">43</option>
						<option value="44">44</option>
						<option value="45">45</option>
						<option value="46">46</option>
						<option value="47">47</option>
						<option value="48">48</option>
						<option value="49">49</option>
						<option value="50">50</option>
						<option value="51">51</option>
						<option value="52">52</option>
						<option value="53">53</option>
						<option value="54">54</option>
						<option value="55">55</option>
						<option value="56">56</option>
						<option value="57">57</option>
						<option value="58">58</option>
						<option value="59">59</option>
					</select>
				</li>
			</ul>
		</li>
    </textarea>

    <div style="position:absolute;left:100px;margin-top:300px;display:none;" class="pop w-8">
        <div class="hd"><i class="close"></i>温馨提示</div>
        <div class="bd">
            <div class="pop-title">
                <i class="ico-error"></i>
                <h4 class="pop-text">操作失败，请检查网络，并重试</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn">关 闭<b class="btn-inner"></b></a>
            </div>
        </div>
    </div>
    
    <div  id="DivSuccessful"   class="pop pop-success w-4" style="position:absolute;z-index:2; display:none" >
    <i class="ico-success"></i>
    <h4 class="pop-text">操作成功</h4>
    </div>
    
    <script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
	<style>		
	.ui-tab-content {display:none}
	.ui-tab-content-current {display:block;}
	</style>
    <script type="text/javascript" src="${staticFileContextPath}/static/js/dateUtil.js"></script>	
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gameIssue/editGameIssue.js"></script>
</body>