<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 07.12.2019
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="by.training.machine.monitoring.app.ApplicationConstant" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<c:if test="${ApplicationConstant.SECURITY_SERVICE.containRole(pageContext.request.session, ApplicationConstant.DEFAULT_ROLE)}">
    <c:set var="form" value="deleteAssignMachineForm"/>
</c:if>
<c:if test="${ApplicationConstant.SECURITY_SERVICE.containRole(pageContext.request.session, ApplicationConstant.MANUFACTURER_ROLE)}">
    <c:set var="form" value="deleteForm"/>
</c:if>
<div id="modal-delete" class="modal">
    <div class="modal-content animate mdl-card mdl-shadow--2dp" style="
    width: fit-content;
    height: fit-content;
    padding-bottom: 1px;
">
        <div class="mdl-cell--middle mdl-cell--2-col" style="
    padding: 20px;
    width: 100%;
    border-bottom: 2px solid #37474f;
">
            <span class="mdl-layout-title" style="font-size: 16px;font-weight: 500;color: #a2a4a2;text-align: center;">
                <fmt:message key="delete.auto.quession.msg"/>
            </span>
        </div>
        <div class="mdl-cell--middle mdl-cell--2-col" style="
    padding: 20px;
    width: auto;
    /* border-bottom: 2px solid #eee; */
">
            <span class="mdl-layout-title" style="font-size: 16px;font-weight: 500;color: #a2a4a2;text-align: center;">
                <fmt:message key="delete.car.msg"/>
            </span>
        </div>
        <div class="mdl-grid">
            <div class="mdl-cell--middle mdl-cell--1-col" style="margin: 5px;width: auto;">
                <button class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" form="${pageContext.getAttribute('form')}" style="background: rgb(55, 71, 79);width: 100%;padding: 0;margin: 3px;">
                    <fmt:message key="yes.msg"/>
                </button>
            </div>
            <div class="mdl-cell--middle mdl-cell--1-col" style="
    margin: 5px;
    width: auto;">
                <button class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" style="background: rgb(55, 71, 79);width: 100%;padding: 0;margin: 3px;"
                        onclick="document.getElementById('modal-delete').style.display='none'">
                    <fmt:message key="no.msg"/>
                </button>
            </div>
        </div>
    </div>
</div>
