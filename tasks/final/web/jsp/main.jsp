<%--
  Created by IntelliJ IDEA.
  User: GRAFIK529
  Date: 06/11/19
  Time: 11:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="by.training.machine.monitoring.app.ApplicationConstant" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${empty cookie.get('Language')}">
    <fmt:setLocale value="en-EN"/>
</c:if>
<c:if test="${not empty cookie.get('Language')}">
    <c:if test="${cookie.get('Language').value.equalsIgnoreCase('EN')}">
        <fmt:setLocale value="en-EN"/>
    </c:if>
    <c:if test="${cookie.get('Language').value.equalsIgnoreCase('RU')}">
        <fmt:setLocale value="ru-RU"/>
    </c:if>
</c:if>
<fmt:setBundle basename="i18n/AppMessages"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="content-type" content="text/html">
    <meta name="description" content="A front-end template that helps you build fast, modern mobile web apps.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title><fmt:message key="title.msg"/></title>
    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes">
    <link rel="icon" sizes="192x192" href="${pageContext.request.contextPath}/static/images/android-desktop.png">
    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Material Design Lite">
    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/static/images/ios-desktop.png">
    <!-- Tile icon for Win8 (144x144 + tile color) -->
    <meta name="msapplication-TileImage" content="images/touch/ms-touch-icon-144x144-precomposed.png">
    <meta name="msapplication-TileColor" content="#3372DF">

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/images/favicon.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/fronts.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/material.icons.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/material.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/popup.css">
</head>
<body>
<div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header has-drawer is-upgraded is-small-screen">
    <header class="demo-header mdl-layout__header mdl-color--grey-100 mdl-color-text--grey-600"
            <c:if test="${not ApplicationConstant.SECURITY_SERVICE.isLogIn(pageContext.request.session)}">
                style="margin-left: auto; width: 100%"
            </c:if>>
        <div class="mdl-layout__header-row">
            <%@include file="views/signInUpView.jsp" %>
            <%@include file="views/deleteMachineView.jsp" %>
            <div class="mdl-layout-spacer"></div>
            <button class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon" id="hdrbtn">
                <i class="material-icons">language</i>
            </button>
            <ul class="mdl-menu mdl-js-menu mdl-js-ripple-effect mdl-menu--bottom-right" for="hdrbtn">
                <%@include file="views/languageChangeView.jsp" %>
            </ul>
        </div>
    </header>
    <%@include file="views/userPanelView.jsp" %>
    <%@include file="views/adminPanelView.jsp" %>
    <%@include file="views/manufacturerPanelView.jsp" %>
    <main class="mdl-layout__content mdl-color--grey-100"
<%--          display: flex;flex-direction: column;justify-content: space-between;--%>
            <c:if test="${not ApplicationConstant.SECURITY_SERVICE.isLogIn(pageContext.request.session)}"> style="margin-left: 0px;"
            </c:if>>
        <div class="mdl-grid demo-content">
            <%@include file="views/viewMachineStatusView.jsp" %>
            <%@include file="views/viewAllUsersView.jsp" %>
            <%@include file="views/mainInfoView.jsp" %>
            <%@include file="views/viewAllCarsView.jsp" %>
            <%@include file="views/addNewCarView.jsp" %>
            <%@include file="views/registerNewCarView.jsp" %>
            <%@include file="views/viewAllUserCarsView.jsp" %>
        </div>
        <%@include file="views/viewFooterView.jsp" %>
    </main>
</div>
<button id="demo-show-toast" class="mdl-button mdl-js-button mdl-button--raised" type="button" style="display: none">
    Show Toast
</button>
<div id="demo-toast-example" class="mdl-js-snackbar mdl-snackbar">
    <div class="mdl-snackbar__text"></div>
    <button class="mdl-snackbar__action" type="button"></button>
</div>
<script src="${pageContext.request.contextPath}/static/js/popup.js"></script>
<script src="${pageContext.request.contextPath}/static/js/material.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/canvasjs.min.js"></script>
<%@include file="views/modalContent.jsp" %>
</body>
</html>
