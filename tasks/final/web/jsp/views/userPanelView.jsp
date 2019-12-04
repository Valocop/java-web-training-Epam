<%--
  Created by IntelliJ IDEA.
  User: GRAFIK529
  Date: 26/11/19
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="by.training.machine.monitoring.ApplicationConstant" language="java" %>
<c:if test="${ApplicationConstant.SECURITY_SERVICE.isLogIn(pageContext.request.session)
and ApplicationConstant.SECURITY_SERVICE.containRole(pageContext.request.session, ApplicationConstant.DEFAULT_ROLE)}">
    <div class="demo-drawer mdl-layout__drawer mdl-color--blue-grey-900 mdl-color-text--blue-grey-50"
         aria-hidden="true">
        <header class="demo-drawer-header">
            <img src="${pageContext.request.contextPath}/static/images/user.jpg" class="demo-avatar">
            <div class="demo-avatar-dropdown">
                <span><fmt:message
                        key="msg.hello"/> ${ApplicationConstant.SECURITY_SERVICE.getCurrentUser(pageContext.request.session).name}</span>
                <div class="mdl-layout-spacer"></div>
                <button id="demo-menu-lower-right"
                        class="mdl-button mdl-js-button mdl-button--icon">
                    <i class="material-icons">arrow_drop_down</i>
                </button>
                <ul class="mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect"
                    for="demo-menu-lower-right">
                    <li class="mdl-menu__item"><fmt:message key="user.profile.msg"/></li>
                    <li class="mdl-menu__item"><fmt:message key="upload.photo.msg"/></li>
                        <%--                    <li disabled class="mdl-menu__item">Disabled Action</li>--%>
                </ul>
            </div>
        </header>
        <nav class="demo-navigation mdl-navigation mdl-color--blue-grey-800">
            <a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons"
                                                       role="presentation">home</i><fmt:message key="home.msg"/></a>
            <a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons"
                                                       role="presentation">directions_car</i><fmt:message
                    key="mycars.msg"/></a>
            <a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons"
                                                       role="presentation">build</i><fmt:message
                    key="order.service.msg"/></a>
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