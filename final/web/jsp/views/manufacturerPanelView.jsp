<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 01.12.2019
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="by.training.machine.monitoring.app.ApplicationConstant" language="java" %>
<c:if test="${ApplicationConstant.SECURITY_SERVICE.isLogIn(pageContext.request.session)
and ApplicationConstant.SECURITY_SERVICE.containRole(pageContext.request.session, ApplicationConstant.MANUFACTURER_ROLE)}">
    <div class="demo-drawer mdl-layout__drawer mdl-color--blue-grey-900 mdl-color-text--blue-grey-50"
         aria-hidden="true">
        <tag:profile/>
        <nav class="demo-navigation mdl-navigation mdl-color--blue-grey-800">
            <a class="mdl-navigation__link" href="${pageContext.request.contextPath}/">
                <i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">home</i>
                <fmt:message key="home.msg"/></a>
            <a class="mdl-navigation__link" onclick="document.getElementById('submit-showListCars').click()">
                <i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">airport_shuttle</i>
                <fmt:message key="auto.list.msg"/>
            </a>
            <form method="get" action="${pageContext.request.contextPath}/app">
                <input type="text" name="commandName" value="showListCars" hidden="hidden">
                <input id="submit-showListCars" type="submit" hidden="hidden">
            </form>
            <a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons"
                                                       role="presentation">build</i><fmt:message
                    key="services.list.msg"/></a>
            <a class="mdl-navigation__link" onclick="document.getElementById('submit-addcar').click()">
                <i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">add_circle_outline</i>
                <fmt:message key="add.car.msg"/>
                <form method="get" action="${pageContext.request.contextPath}/app">
                    <input type="text" name="commandName" value="showAddMachine" hidden="hidden">
                    <input id="submit-addcar" type="submit" hidden="hidden">
                </form>
            </a>
            <div class="mdl-layout-spacer"></div>
            <a id="user-logout" class="mdl-navigation__link" onclick="document.getElementById('submit-logout').click()">
                <i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">power_settings_new</i>
                <fmt:message key="user.logout"/>
                <span class="visuallyhidden">Help</span></a>
            <form method="post" action="${pageContext.request.contextPath}/app">
                <input type="text" name="commandName" value="logoutUser" hidden="hidden">
                <input id="submit-logout" type="submit" hidden="hidden">
            </form>
        </nav>
    </div>
</c:if>