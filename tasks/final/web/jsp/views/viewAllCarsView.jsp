<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 02.12.2019
  Time: 0:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="by.training.machine.monitoring.ApplicationConstant" language="java" %>
<%--<c:if test="${ApplicationConstant.SECURITY_SERVICE.isLogIn(pageContext.request.session)--%>
<%--and ApplicationConstant.SECURITY_SERVICE.containRole(pageContext.request.session, ApplicationConstant.ADMIN_ROLE)--%>
<%--and commandName.equalsIgnoreCase('viewAllCars')}">--%>
<c:if test="${ApplicationConstant.SECURITY_SERVICE.isLogIn(pageContext.request.session)
and ApplicationConstant.SECURITY_SERVICE.containRole(pageContext.request.session, ApplicationConstant.MANUFACTURER_ROLE)}">
<%--    <c:choose>--%>
<%--        <c:when test="${not empty cars}">--%>
            <div class="demo-charts mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid"
                 style="margin: 2px;">
                <div class="demo-charts mdl-color--white mdl-cell mdl-cell--12-col mdl-grid"
                     style="margin: 2px;border-bottom: 1px solid #eee;">
                    <div class="mdl-cell mdl-cell--2-col" style="padding: 2px;">
                        Фото
                    </div>
                    <div class="mdl-cell mdl-cell--4-col" style="padding: 2px;">
                        Описание
                    </div>
                    <div class="mdl-cell mdl-cell--5-col" style="padding: 2px;">
                        Состояние
                    </div>
                    <div class="mdl-cell mdl-cell--1-col" style="padding: 2px;">

                    </div>
                </div>
                <div class="demo-charts mdl-color--white mdl-cell mdl-cell--12-col mdl-grid" style="margin: 2px;">
                    <div class="mdl-cell mdl-cell--2-col" style="padding: 2px;/* border-right: 1px solid #000; */">
                        <img id="myImg" src="https://getmdl.io/templates/dashboard/images/dog.png" alt="Snow"
                             style="width:100%;max-width: 120px;max-height: 120px;">
                    </div>
                    <div class="mdl-cell mdl-cell--4-col" style="padding: 5px;">
                        <li class="mdl-list__item" style="padding: 1px;font-size: 14px;min-height: 30px;">
                            <span class="mdl-list__item-primary-content">
                                <i class="material-icons mdl-list__item-icon" style="margin-right: 10px;">
                                    person
                                </i>
                                Bryan Cranston
                            </span>
                        </li>
                        <li class="mdl-list__item" style="padding: 1px;font-size: 14px;min-height: 30px;">
                            <span class="mdl-list__item-primary-content">
                                <i class="material-icons mdl-list__item-icon" style="margin-right: 10px;">
                                    person
                                </i>
                                Bryan Cranston
                            </span>
                        </li>
                        <li class="mdl-list__item" style="padding: 1px;font-size: 14px;min-height: 30px;">
                            <span class="mdl-list__item-primary-content">
                                <i class="material-icons mdl-list__item-icon" style="margin-right: 10px;">
                                    person
                                </i>
                                Bryan Cranston
                            </span>
                        </li>
                    </div>
                    <div class="mdl-cell mdl-cell--5-col">
                        <li class="mdl-list__item" style="padding: 1px;font-size: 14px;min-height: 30px;">
                            <span class="mdl-list__item-primary-content">
                                <i class="material-icons mdl-list__item-icon" style="margin-right: 10px;">
                                    person
                                </i>
                                Bryan Cranston
                            </span>
                        </li>
                        <li class="mdl-list__item" style="padding: 1px;font-size: 14px;min-height: 30px;">
                            <span class="mdl-list__item-primary-content">
                                <i class="material-icons mdl-list__item-icon" style="margin-right: 10px;">
                                    person
                                </i>
                                Bryan Cranston
                            </span>
                        </li>
                        <li class="mdl-list__item" style="padding: 1px;font-size: 14px;min-height: 30px;">
                            <span class="mdl-list__item-primary-content">
                                <i class="material-icons mdl-list__item-icon" style="margin-right: 10px;">
                                    person</i>
                                Bryan Cranston
                            </span>
                        </li>
                    </div>
                    <div class="mdl-cell mdl-cell--1-col">
                        <div class="mdl-cell mdl-cell--1-col" style="width: 100%;margin: 5px;">
                            <button class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored"
                                    style="background: rgba(55, 71, 79, 0.79);width: 100%;padding: 0;">
                                Edit
                            </button>
                        </div>
                        <div class="mdl-cell mdl-cell--1-col" style="width: 100%;margin: 5px;">
                            <button class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent"
                                    style="background: #d50000a1;width: 100%;padding: 0;">
                                Delete
                            </button>
                        </div>
                    </div>
                </div>
            </div>
<%--        </c:when>--%>
<%--        <c:otherwise>--%>
<%--            <fmt:message key="data.not.available.msg"/>--%>
<%--        </c:otherwise>--%>
<%--    </c:choose>--%>
</c:if>
