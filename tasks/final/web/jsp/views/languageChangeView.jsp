<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 30.11.2019
  Time: 22:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<c:choose>
    <c:when test="${cookie.get('Language').value.equalsIgnoreCase('EN')}">
        <c:choose>
            <c:when test="${not empty pageContext.request.queryString}">
                <li class="mdl-menu__item"
                    onclick="location.href='${pageContext.request.contextPath}/app?${pageContext.request.queryString}&Language=RU'">
                    <fmt:message key="language.ru"/>
                </li>
            </c:when>
            <c:when test="${empty pageContext.request.queryString}">
                <li class="mdl-menu__item"
                    onclick="location.href='${pageContext.request.contextPath}/app?Language=RU'">
                    <fmt:message key="language.ru"/>
                </li>
            </c:when>
        </c:choose>
    </c:when>
    <c:when test="${cookie.get('Language').value.equalsIgnoreCase('RU')}">
        <c:choose>
            <c:when test="${not empty pageContext.request.queryString}">
                <li class="mdl-menu__item"
                    onclick="location.href='${pageContext.request.contextPath}/app?${pageContext.request.queryString}&Language=EN'">
                    <fmt:message key="language.en"/>
                </li>
            </c:when>
            <c:when test="${empty pageContext.request.queryString}">
                <li class="mdl-menu__item"
                    onclick="location.href='${pageContext.request.contextPath}/app?Language=EN'">
                    <fmt:message key="language.en"/>
                </li>
            </c:when>
        </c:choose>
    </c:when>
</c:choose>
