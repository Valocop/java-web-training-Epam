<%--
  Created by IntelliJ IDEA.
  User: GRAFIK529
  Date: 06/11/19
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<body style="background-color: #f5f5f5;">
<div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header has-drawer is-upgraded is-small-screen">
    <header class="demo-header mdl-layout__header mdl-color--grey-100 mdl-color-text--grey-600" style="margin-left: auto; width: 100%">
        <div class="mdl-layout__header-row">
            <div class="mdl-layout-spacer"></div>
            <button class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon" id="hdrbtn">
                <i class="material-icons">language</i>
            </button>
            <ul class="mdl-menu mdl-js-menu mdl-js-ripple-effect mdl-menu--bottom-right" for="hdrbtn">
                <%@include file="views/languageChangeView.jsp" %>
            </ul>
        </div>
    </header>
    <main class="mdl-layout__content mdl-color--grey-100" style="margin-left: 0px">
        <div class="mdl-grid demo-content">
            <div class="demo-charts mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid" style="margin: 2px;">
                <div class="demo-charts mdl-color--white mdl-cell mdl-cell--12-col mdl-grid" style="margin: 2px;padding-bottom: 0px;">
                    <div class="mdl-cell mdl-cell--12-col" style="position: relative;padding: 2px;">
                        <h2 class="mdl-card__title-text" style="text-align: center;font-weight: 400;color: #51859e;padding-bottom: 5px;border-bottom: 1px solid #eee;display: block;">
                    <span>
                        ERROR
                    </span>
                        </h2>
                    </div>
                    <div class="mdl-cell mdl-cell--12-col" style="width: 100%;position: relative;padding: 2px;">
                        <div class="mdl-card__supporting-text mdl-color-text--grey-600" style="width: 100%;text-align: center;">
                    <span>
                        Request from ${pageContext.errorData.requestURI} is failed <br/>
                        Servlet name: ${pageContext.errorData.servletName} <br/>
                        Status code: ${pageContext.errorData.statusCode} <br/>
                        Exception: ${pageContext.exception} <br/>
                        Message from exception: ${pageContext.exception.message}
                        Cause: ${pageContext.exception.cause.message}
                    </span>
                        </div>
                    </div>
                    <div class="mdl-cell mdl-cell--12-col" style="position: relative;padding: 2px;text-align: center;">
                        <form action="${pageContext.request.contextPath}/">
                            <button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored" style="width: auto;">
                                Main
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>
<script src="${pageContext.request.contextPath}/static/js/popup.js"></script>
<script src="${pageContext.request.contextPath}/static/js/material.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/canvasjs.min.js"></script>
</body>
</html>
