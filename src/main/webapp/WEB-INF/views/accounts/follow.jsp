<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actAcc" value="${ForwardConst.ACT_ACC.getValue()}" />
<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="commFol" value="${ForwardConst.CMD_FOLLOW.getValue()}" />
<c:set var="commAcc" value="${ForwardConst.CMD_ACCOUNT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name ="content">
        <h2>フォロー中の従業員</h2>
        <table id="follow_list">
            <tbody>
                <tr>
                    <th class="follow_name">従業員名</th>
                    <th class="entry_date">アカウント作成日</th>
                </tr>
                <c:forEach var="follow" items="${follows}" varStatus="status">
                    <fmt:parseDate value="${follow.follow.createdAt}" pattern="yyyy-MM-dd" var="entryDay" type="date" />

                    <tr class="row${status.count % 2}">
                        <td class="follow_name"><a href="<c:url value='?action=${actAcc}&command=${commAcc}&id=${follow.follow.id}' />"><c:out value="${follow.follow.name}" /></a></td>
                        <td class="entry_date"><fmt:formatDate value='${entryDay}' pattern='yyyy-MM-dd' /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${follows_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((follows_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='_action=${actFol}&command=${commFol}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </c:param>
</c:import>