<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 14.12.2019
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="by.training.machine.monitoring.app.ApplicationConstant" language="java" %>
<c:if test="${ApplicationConstant.SECURITY_SERVICE.isLogIn(pageContext.request.session)
and ApplicationConstant.SECURITY_SERVICE.containRole(pageContext.request.session, ApplicationConstant.DEFAULT_ROLE)
and pageContext.findAttribute('commandName').equalsIgnoreCase('showRegisterCar')}">
    <div class="demo-charts mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid" style="margin: 2px;">
        <div class="demo-charts mdl-color--white mdl-cell mdl-cell--12-col mdl-grid" style="margin: 2px;padding-bottom: 0px;">
            <div class="mdl-cell mdl-cell--12-col" style="position: relative;padding: 2px;">
                <span class="mdl-layout-title" style="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);font-size: 16px;font-weight: 500;color: #a2a4a2;">
                    <fmt:message key="register.your.car.msg" />
                </span>
            </div>
        </div>
        <div class="demo-charts mdl-color--white mdl-cell mdl-cell--12-col mdl-grid" style="margin: 2px;padding-bottom: 0px;padding-top: 0px;">
            <div class="mdl-cell mdl-cell--2-col" style="position: relative;padding: 2px;">
                <span class="mdl-layout-title" style="font-size: 16px;font-weight: 500;color: #a2a4a2;">
                    <fmt:message key="unic.number.msg" />
                </span>
            </div>
            <div class="mdl-cell mdl-cell--9-col" style="position: relative;padding: 2px;">
                <div class="mdl-textfield mdl-js-textfield">
                    <input class="mdl-textfield__input" type="text" id="unic-number" name="machine.uniq.number" value="${pageContext.findAttribute('machine.uniq.number')}" form="registerNewCarForm">
                    <label class="mdl-textfield__label" for="unic-number">
                        <fmt:message key="unic.number.msg" />...
                    </label>
                    <c:forEach items="${pageContext.findAttribute('machine.uniq.number.error')}" var="error">
                        <p style="color: red">${error}</p>
                    </c:forEach>
                </div>
            </div>
            <div class="mdl-cell mdl-cell--1-col" style="position: relative;padding: 2px;">
                <form action="${pageContext.request.contextPath}/app" method="post" id="registerNewCarForm">
                    <input type="text" name="commandName" value="registerNewCar" hidden="hidden">
                    <button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored" style="background: rgba(76, 175, 80, 0.63);width: 100%;padding: 0;">
                        <fmt:message key="user.save.msg" />
                    </button>
                </form>
            </div>
        </div>
    </div>
</c:if>
