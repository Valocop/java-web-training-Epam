<%--
  Created by IntelliJ IDEA.
  User: GRAFIK529
  Date: 26/11/19
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="by.training.machine.monitoring.app.ApplicationConstant" language="java" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<c:if test="${ApplicationConstant.SECURITY_SERVICE.isLogIn(pageContext.request.session)
and ApplicationConstant.SECURITY_SERVICE.containRole(pageContext.request.session, ApplicationConstant.DEFAULT_ROLE)}">
    <div class="demo-drawer mdl-layout__drawer mdl-color--blue-grey-900 mdl-color-text--blue-grey-50" aria-hidden="true">
        <tag:profile/>
        <nav class="demo-navigation mdl-navigation mdl-color--blue-grey-800">
            <a class="mdl-navigation__link" href="${pageContext.request.contextPath}/"><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">home</i>
                <fmt:message key="home.msg"/>
            </a>
            <a class="mdl-navigation__link" href="${pageContext.request.contextPath}/app?commandName=showRegisterCar" >
                <i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">add_circle_outline</i>
                <fmt:message key="user.register.car.msg"/>
            </a>
            <a class="mdl-navigation__link" href="${pageContext.request.contextPath}/app?commandName=showUserListCars">
                <i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">directions_car</i>
                <fmt:message key="mycars.msg"/>
            </a>
            <a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">build</i>
                <fmt:message key="order.service.msg"/>
            </a>
            <div class="mdl-layout-spacer"></div>
            <a id="user-logout" class="mdl-navigation__link" onclick="document.getElementById('submit-logout').click()">
                <i class="mdl-color-text--blue-grey-400 material-icons" role="presentation">power_settings_new</i>
                <fmt:message key="user.logout"/>
                <span class="visuallyhidden">Help</span>
            </a>
            <form method="post" action="${pageContext.request.contextPath}/app">
                <input type="text" name="commandName" value="logoutUser" hidden="hidden">
                <input id="submit-logout" type="submit" hidden="hidden">
            </form>
        </nav>
    </div>
</c:if>