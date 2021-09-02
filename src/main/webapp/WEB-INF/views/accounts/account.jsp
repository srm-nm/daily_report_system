<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>
<%@ page import="constants.AttributeConst"%>

<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue}" />
<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>${employee.name}のアカウントページ</h2>

        <%-- <c:import url="/WEB-INF/views/employees/show.jsp" /><br /><br /> --%>

        <h3>【<c:out value="${employee.name}" />の日報 一覧】</h3>
        <table id="report_list">
            <tbody>
                <tr>
                    <th class="report_date">日付</th>
                    <th class="report_title">タイトル</th>
                    <th class="report_action">操作</th>
                </tr>
                <c:forEach var="report" items="${reports}" varStatus="status">
                    <fmt:parseDate value="${report.reportDate}" pattern="yyyy-MM-dd" var="reportDay" type="date" />
                    <tr class="row${status.count % 2}">
                        <td class="report_date"><fmt:formatDate value='${reportDay}' pattern='yyyy-MM-dd' />
                        <td class="report_title">${report.title}</td>
                        <td class="report_action"><a href="<c:url value='?action=${actRep}&command=${commShow}&id=${report.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </c:param>
</c:import>