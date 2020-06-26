<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 15.12.2019
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ tag import="by.training.machine.monitoring.app.ApplicationConstant" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:directive.attribute name="machineId" required="true"/>
<c:if test="${not empty machineId}">
    <c:if test="${ApplicationConstant.SECURITY_SERVICE.containRole(pageContext.request.session, ApplicationConstant.DEFAULT_ROLE)}">
        <c:set var="command" value="deleteAssignMachine"/>
    </c:if>
    <c:if test="${ApplicationConstant.SECURITY_SERVICE.containRole(pageContext.request.session, ApplicationConstant.MANUFACTURER_ROLE)}">
        <c:set var="command" value="deleteMachine"/>
    </c:if>
    <form id="deleteForm" method="post" action="${pageContext.request.contextPath}/app">
        <input type="text" name="commandName" value="${command}" hidden="hidden">
        <input id="inputId" type="text" name="machine.id" value="" hidden="hidden">
        <button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent" style="background: rgb(55, 71, 79);width: 100%;padding: 0;margin: 3px;">
            Yes
        </button>
    </form>
</c:if>


