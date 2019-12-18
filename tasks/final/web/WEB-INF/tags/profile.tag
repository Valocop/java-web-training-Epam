<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 16.12.2019
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ tag import="by.training.machine.monitoring.app.ApplicationConstant" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${not empty cookie.get('Language')}">
    <c:if test="${cookie.get('Language').value.equalsIgnoreCase('EN')}">
        <fmt:setLocale value="en-EN"/>
    </c:if>
    <c:if test="${cookie.get('Language').value.equalsIgnoreCase('RU')}">
        <fmt:setLocale value="ru-RU"/>
    </c:if>
</c:if>
<fmt:setBundle basename="i18n/AppMessages"/>
<header class="demo-drawer-header">
    <img src="${pageContext.request.contextPath}/static/images/user.jpg" class="demo-avatar" alt="avatar">
    <div class="demo-avatar-dropdown">
        <span>
            <fmt:message key="msg.hello"/>
            <c:out value="${ApplicationConstant.SECURITY_SERVICE.getCurrentUser(pageContext.request.session).name}"/>
        </span>
        <div class="mdl-layout-spacer"></div>
        <button id="demo-menu-lower-right" class="mdl-button mdl-js-button mdl-button--icon">
            <i class="material-icons">arrow_drop_down</i>
        </button>
        <ul class="mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect" for="demo-menu-lower-right">
            <li class="mdl-menu__item">
                <fmt:message key="user.profile.msg"/>
            </li>
            <li class="mdl-menu__item" onclick="document.getElementById('inputPic').click()">
                <form id="loadPicForm" method="post" action="${pageContext.request.contextPath}/app" hidden="hidden">
                    <input id="inputPic" type="file" name="pic" accept="image/*" onchange="document.getElementById('loadPicForm').submit()">
                </form>
                <fmt:message key="upload.photo.msg"/>
            </li>
        </ul>
    </div>
</header>
