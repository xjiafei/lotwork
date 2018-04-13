<%@ page language="java" contentType="text/html; charset=UTF-8"
	 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- <link rel="stylesheet" href="${staticFileContextPath}/static/images/game/chart.css" /> --%>
<table class="chart-table" style="width:100%;">
<thead class="thead">
<tr class="title-text">
    <th rowspan="2" class="ball-none border-bottom"></th>
    <th rowspan="2" class="border-bottom" id="">期号</th>
    <th rowspan="2" class="ball-none border-bottom border-right"></th>
    <th colspan="13" class="border-right" id="">红一区</th>
    <th colspan="14" class="border-right">红二区</th>
    <th colspan="13" class="border-right">红三区</th>
    <th colspan="19" class="border-right">蓝区</th>
</tr>
<tr class="title-number">
    <th class="ball-none border-bottom"></th>
    <th class="border-bottom"><i class="ball-noraml">01</i></th>
    <th class="border-bottom"><i class="ball-noraml">02</i></th>
    <th class="border-bottom"><i class="ball-noraml">03</i></th>
    <th class="border-bottom"><i class="ball-noraml">04</i></th>
    <th class="border-bottom"><i class="ball-noraml">05</i></th>
    <th class="border-bottom"><i class="ball-noraml">06</i></th>
    <th class="border-bottom"><i class="ball-noraml" id="">07</i></th>
    <th class="border-bottom"><i class="ball-noraml">08</i></th>
    <th class="border-bottom"><i class="ball-noraml">09</i></th>
    <th class="border-bottom"><i class="ball-noraml">10</i></th>
    <th class="border-bottom"><i class="ball-noraml">11</i></th>
    <th class="ball-none border-bottom border-right"></th>
    <th class="ball-none border-bottom"></th>
    <th class="border-bottom"><i class="ball-noraml">12</i></th>
    <th class="border-bottom"><i class="ball-noraml">13</i></th>
    <th class="border-bottom"><i class="ball-noraml">14</i></th>
    <th class="border-bottom"><i class="ball-noraml">15</i></th>
    <th class="border-bottom"><i class="ball-noraml">16</i></th>
    <th class="border-bottom"><i class="ball-noraml">17</i></th>
    <th class="border-bottom"><i class="ball-noraml">18</i></th>
    <th class="border-bottom"><i class="ball-noraml">19</i></th>
    <th class="border-bottom"><i class="ball-noraml">20</i></th>
    <th class="border-bottom"><i class="ball-noraml">21</i></th>
    <th class="border-bottom"><i class="ball-noraml">22</i></th>
    <th class="ball-none border-bottom border-right"></th>
    <th class="ball-none border-bottom"></th>
    <th class="border-bottom"><i class="ball-noraml">23</i></th>
    <th class="border-bottom"><i class="ball-noraml">24</i></th>
    <th class="border-bottom"><i class="ball-noraml">25</i></th>
    <th class="border-bottom"><i class="ball-noraml">26</i></th>
    <th class="border-bottom"><i class="ball-noraml">27</i></th>
    <th class="border-bottom"><i class="ball-noraml">28</i></th>
    <th class="border-bottom"><i class="ball-noraml">29</i></th>
    <th class="border-bottom"><i class="ball-noraml">30</i></th>
    <th class="border-bottom"><i class="ball-noraml">31</i></th>
    <th class="border-bottom"><i class="ball-noraml">32</i></th>
    <th class="border-bottom"><i class="ball-noraml">33</i></th>
    <th class="ball-none border-bottom border-right"></th>
    <th class="ball-none border-bottom"></th>
    <th class="border-bottom"><i class="ball-noraml">01</i></th>
    <th class="border-bottom"><i class="ball-noraml">02</i></th>
    <th class="border-bottom"><i class="ball-noraml">03</i></th>
    <th class="border-bottom"><i class="ball-noraml">04</i></th>
    <th class="border-bottom"><i class="ball-noraml">05</i></th>
    <th class="border-bottom"><i class="ball-noraml">06</i></th>
    <th class="border-bottom"><i class="ball-noraml">07</i></th>
    <th class="border-bottom"><i class="ball-noraml">08</i></th>
    <th class="border-bottom"><i class="ball-noraml">09</i></th>
    <th class="border-bottom"><i class="ball-noraml">10</i></th>
    <th class="border-bottom"><i class="ball-noraml">11</i></th>
    <th class="border-bottom"><i class="ball-noraml">12</i></th>
    <th class="border-bottom"><i class="ball-noraml">13</i></th>
    <th class="border-bottom"><i class="ball-noraml">14</i></th>
    <th class="border-bottom"><i class="ball-noraml">15</i></th>
    <th class="border-bottom"><i class="ball-noraml">16</i></th>
    <th class="ball-none border-bottom"></th>
    <th class="ball-none border-bottom border-right"></th>
</tr>
</thead>
<tbody class="tbody">
<c:forEach items="${result}" var="ball">
<tr>
    <td class="ball-none"></td>
    <td class="issue-numbers" id="">${ball.webIssueCode}</td>
    <td class="ball-none border-right"></td>
    <td class="ball-none"></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==1}">ball-red</c:if></c:forEach>">01</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==2}">ball-red</c:if></c:forEach>">02</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==3}">ball-red</c:if></c:forEach>">03</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==4}">ball-red</c:if></c:forEach>">04</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==5}">ball-red</c:if></c:forEach>">05</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==6}">ball-red</c:if></c:forEach>">06</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==7}">ball-red</c:if></c:forEach>">07</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==8}">ball-red</c:if></c:forEach>">08</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==9}">ball-red</c:if></c:forEach>">09</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==10}">ball-red</c:if></c:forEach>">10</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==11}">ball-red</c:if></c:forEach>">11</i></td>
    <td class="ball-none border-right"></td>
    <td class="ball-none"></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==12}">ball-red</c:if></c:forEach>">12</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==13}">ball-red</c:if></c:forEach>">13</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==14}">ball-red</c:if></c:forEach>">14</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==15}">ball-red</c:if></c:forEach>">15</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==16}">ball-red</c:if></c:forEach>">16</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==17}">ball-red</c:if></c:forEach>">17</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==18}">ball-red</c:if></c:forEach>">18</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==19}">ball-red</c:if></c:forEach>">19</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==20}">ball-red</c:if></c:forEach>">20</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==21}">ball-red</c:if></c:forEach>">21</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==22}">ball-red</c:if></c:forEach>">22</i></td>
    <td class="ball-none border-right"></td>
    <td class="ball-none"></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==23}">ball-red</c:if></c:forEach>">23</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==24}">ball-red</c:if></c:forEach>">24</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==25}">ball-red</c:if></c:forEach>">25</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==26}">ball-red</c:if></c:forEach>">26</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==27}">ball-red</c:if></c:forEach>">27</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==28}">ball-red</c:if></c:forEach>">28</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==29}">ball-red</c:if></c:forEach>">29</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==30}">ball-red</c:if></c:forEach>">30</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==31}">ball-red</c:if></c:forEach>">31</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==32}">ball-red</c:if></c:forEach>">32</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" end="5"><c:if test="${b==33}">ball-red</c:if></c:forEach>">33</i></td>
    <td class="ball-none border-right"></td>
    <td class="ball-none"></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" begin="6"><c:if test="${b==1}">ball-blue</c:if></c:forEach>">01</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" begin="6"><c:if test="${b==2}">ball-blue</c:if></c:forEach>">02</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" begin="6"><c:if test="${b==3}">ball-blue</c:if></c:forEach>">03</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" begin="6"><c:if test="${b==4}">ball-blue</c:if></c:forEach>">04</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" begin="6"><c:if test="${b==5}">ball-blue</c:if></c:forEach>">05</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" begin="6"><c:if test="${b==6}">ball-blue</c:if></c:forEach>">06</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" begin="6"><c:if test="${b==7}">ball-blue</c:if></c:forEach>">07</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" begin="6"><c:if test="${b==8}">ball-blue</c:if></c:forEach>">08</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" begin="6"><c:if test="${b==9}">ball-blue</c:if></c:forEach>">09</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" begin="6"><c:if test="${b==10}">ball-blue</c:if></c:forEach>">10</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" begin="6"><c:if test="${b==11}">ball-blue</c:if></c:forEach>">11</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" begin="6"><c:if test="${b==12}">ball-blue</c:if></c:forEach>">12</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" begin="6"><c:if test="${b==13}">ball-blue</c:if></c:forEach>">13</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" begin="6"><c:if test="${b==14}">ball-blue</c:if></c:forEach>">14</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" begin="6"><c:if test="${b==15}">ball-blue</c:if></c:forEach>">15</i></td>
    <td><i class="ball-noraml <c:forEach items="${ball.balls}" var="b" begin="6"><c:if test="${b==16}">ball-blue</c:if></c:forEach>">16</i></td>
    <td class="ball-none"></td>
    <td class="ball-none border-right"></td>
</tr>
</c:forEach>
</tbody>
</table>