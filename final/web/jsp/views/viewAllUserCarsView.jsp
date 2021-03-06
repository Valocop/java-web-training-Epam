<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 15.12.2019
  Time: 0:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="by.training.machine.monitoring.app.ApplicationConstant" language="java" %>
<c:if test="${ApplicationConstant.SECURITY_SERVICE.isLogIn(pageContext.request.session)
and ApplicationConstant.SECURITY_SERVICE.containRole(pageContext.request.session, ApplicationConstant.DEFAULT_ROLE)
and pageContext.findAttribute('commandName').equalsIgnoreCase('showUserListCars')}">
    <c:choose>
        <c:when test="${not empty userMachines}">
            <c:forEach items="${pageContext.findAttribute('userMachines')}" var="machine">
                <div class="demo-charts mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid" style="margin: 2px;">
                    <div class="demo-charts mdl-color--white mdl-cell mdl-cell--12-col mdl-grid"
                         style="margin: 2px;border-bottom: 1px solid #eee;">
                        <div class="mdl-cell mdl-cell--1-col" style="padding: 2px;"></div>
                        <div class="mdl-cell mdl-cell--3-col" style="padding: 2px;">
                            <span class="mdl-layout-title" style="font-size: 16px;font-weight: 500;color: #a2a4a2;">
                                <fmt:message key="model.msg"/>
                            </span>
                        </div>
                        <div class="mdl-cell mdl-cell--4-col" style="padding: 2px;">
                            <li class="mdl-list__item" style="padding: 1px;font-size: 14px;min-height: 30px;">
                            <span class="mdl-list__item-primary-content" style="font-size: 16px;font-weight: 500;color: #a2a4a2;">
                                <fmt:message key="status.msg"/>
                            </span>
                            </li>
                        </div>
                        <div class="mdl-cell mdl-cell--3-col" style="padding: 2px;">
                            <li class="mdl-list__item" style="padding: 1px;font-size: 14px;min-height: 30px;">
                            <span class="mdl-list__item-primary-content" style="font-size: 16px;font-weight: 500;color: #a2a4a2;">
                                <i class="material-icons mdl-list__item-icon" style="margin-right: 10px;">supervisor_account</i>
                                <fmt:message key="manufacturer.msg"/>
                            </span>
                            </li>
                        </div>
                        <div class="mdl-cell mdl-cell--1-col" style="padding: 2px;"></div>
                    </div>
                    <div class="demo-charts mdl-color--white mdl-cell mdl-cell--12-col mdl-grid" style="margin: 2px;">
                        <div class="mdl-cell mdl-cell--1-col" style="padding: 2px;">
                            <img id="myImg" src="${pageContext.request.contextPath}/static/images/car.png" alt="Snow" style="width:100%;max-width: 120px;max-height: 120px;">
                        </div>
                        <div class="mdl-cell--top mdl-cell--3-col" style="padding: 5px;">
                            <div class="mdl-grid" style="padding: 2px;">
                                <div class="mdl-cell--top mdl-cell--2-col" style="width: max-content;">
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;color: #a2a4a2;margin-bottom: 10px;">
                                        <fmt:message key="model.name.msg"/>:
                                    </span>
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;color: #a2a4a2;margin-bottom: 10px;">
                                        <fmt:message key="model.release.date"/>:
                                    </span>
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;color: #a2a4a2;margin-bottom: 10px;">
                                        <fmt:message key="characteristic.fuel.type"/>:
                                    </span>
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;color: #a2a4a2;margin-bottom: 10px;">
                                        <fmt:message key="characteristic.engine.volume.msg"/>:
                                    </span>
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;color: #a2a4a2;margin-bottom: 10px;">
                                        <fmt:message key="characteristic.transmission.msg"/>:
                                    </span>
                                </div>
                                <div class="mdl-cell--top mdl-cell--1-col" style="width: max-content;">
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;margin-bottom: 10px;">
                                            ${machine.modelDto.name}
                                    </span>
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;margin-bottom: 10px;">
                                        <fmt:formatDate value="${machine.modelDto.releaseDate}" type="DATE"/>
                                    </span>
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;margin-bottom: 10px;">
                                            ${machine.characteristicDto.fuelType}
                                    </span>
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;margin-bottom: 10px;">
                                            ${machine.characteristicDto.engineVolume}
                                    </span>
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;margin-bottom: 10px;">
                                            ${machine.characteristicDto.transmission}
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="mdl-cell--top mdl-cell--4-col">
                            <div class="mdl-grid" style="padding: 2px;">
                                <div class="mdl-cell--top mdl-cell--2-col" style="width: max-content;">
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;color: #a2a4a2;margin-bottom: 10px;">
                                        <fmt:message key="log.fuel.level.msg"/>:
                                    </span>
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;color: #a2a4a2;margin-bottom: 10px;">
                                        <fmt:message key="log.oil.pressure.msg"/>:
                                    </span>
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;color: #a2a4a2;margin-bottom: 10px;">
                                        <fmt:message key="log.oil.level"/>:
                                    </span>
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;color: #a2a4a2;margin-bottom: 10px;">
                                        <fmt:message key="log.coolant.tempr.msg"/>:
                                    </span>
                                </div>
                                <div class="mdl-cell--top mdl-cell--2-col" style="width: max-content;">
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;margin-bottom: 10px;">
                                        <c:if test="${not empty machine.machineLogsList}">
                                            <fmt:formatNumber value="${machine.machineLogsList[fn:length(machine.machineLogsList) - 1].fuelLevel}" pattern=".##"/>
                                        </c:if>
                                    </span>
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;margin-bottom: 10px;">
                                        <c:if test="${not empty machine.machineLogsList}">
                                            <fmt:formatNumber value="${machine.machineLogsList[fn:length(machine.machineLogsList) - 1].oilPressure}" pattern=".##"/>
                                        </c:if>
                                    </span>
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;margin-bottom: 10px;">
                                        <c:if test="${not empty machine.machineLogsList}">
                                            <fmt:formatNumber value="${machine.machineLogsList[fn:length(machine.machineLogsList) - 1].oilLevel}" pattern=".##"/>
                                        </c:if>
                                    </span>
                                    <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;margin-bottom: 10px;">
                                        <c:if test="${not empty machine.machineLogsList}">
                                            <fmt:formatNumber value="${machine.machineLogsList[fn:length(machine.machineLogsList) - 1].coolantTemp}" pattern=".##"/>
                                        </c:if>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="mdl-cell--top mdl-cell--3-col" style="padding: 2px;">
                            <li class="mdl-list__item" style="padding: 1px;font-size: 14px;min-height: 30px;">
                            <span class="mdl-list__item-primary-content">
                                <i class="material-icons mdl-list__item-icon" style="margin-right: 10px;">person</i>
                                <span class="mdl-layout-title" style="font-size: 14px;font-weight: 500;margin-bottom: 10px;">
                                        ${machine.manufactureDto.name}
                                </span>
                            </span>
                            </li>
                        </div>
                        <form id="deleteAssignMachineForm" method="post" action="${pageContext.request.contextPath}/app">
                            <input type="text" name="commandName" value="deleteUserMachine" hidden="hidden">
                            <input id="inputMachineId" type="text" name="machine.id" value="" hidden="hidden">
                        </form>
                        <div class="mdl-cell--top mdl-cell--1-col" style="padding: 2px;">
                            <button class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" style="background: rgba(38, 50, 56, 0.81);width: 100%;padding: 0px;margin: 3px;"
                                    onclick="{
                                            document.getElementById('inputMachineId').value = ${machine.machineDto.id}
                                            document.getElementById('modal-delete').style.display='block'
                                            }">
                                <fmt:message key="delete.msg"/>
                            </button>
                            <button class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" style="background: rgba(181, 54, 54, 0.68);width: 100%;padding: 0;margin: 3px;">
                                <fmt:message key="errors.msg"/>
                            </button>
                            <form method="get" action="${pageContext.request.contextPath}/app">
                                <input type="text" name="commandName" value="showMachineStatus" hidden="hidden">
                                <input type="text" name="machine.id" value="${machine.machineDto.id}" hidden="hidden">
                                <button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" style="background: rgba(23, 86, 117, 0.68);width: 100%;padding: 0;margin: 3px;">
                                    <fmt:message key="status.msg"/>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <fmt:message key="data.not.available.msg"/>
        </c:otherwise>
    </c:choose>
</c:if>
