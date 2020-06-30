<%--
  Created by IntelliJ IDEA.
  User: GRAFIK529
  Date: 27/11/19
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="by.training.machine.monitoring.app.ApplicationConstant" language="java" %>
<c:if test="${ApplicationConstant.SECURITY_SERVICE.isLogIn(pageContext.request.session)
and ApplicationConstant.SECURITY_SERVICE.containRole(pageContext.request.session, ApplicationConstant.ADMIN_ROLE)
and commandName.equalsIgnoreCase('viewUserList')}">
    <c:choose>
        <c:when test="${not empty users}">
            <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp"
                   style="display: block; overflow-x: scroll; width: calc(100% - 16px); margin: 0 auto;">
                <thead>
                <tr>
                    <th class="mdl-data-table__cell--non-numeric"><fmt:message key="user.id.msg"/></th>
                    <th class="mdl-data-table__cell--non-numeric"><fmt:message key="user.login.msg"/></th>
                    <th class="mdl-data-table__cell--non-numeric"><fmt:message key="user.email.msg"/></th>
                    <th class="mdl-data-table__cell--non-numeric"><fmt:message key="user.address"/></th>
                    <th class="mdl-data-table__cell--non-numeric"><fmt:message key="user.tel.msg"/></th>
                    <th class="mdl-data-table__cell--non-numeric"><fmt:message key="user.roles.msg"/></th>
                    <th class="mdl-data-table__cell--non-numeric"><fmt:message
                            key="user.save.msg"/></th>
                    <th class="mdl-data-table__cell--non-numeric"><fmt:message
                            key="user.del.msg"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pageContext.findAttribute('users')}" var="u">
                    <tr>
                        <td class="mdl-data-table__cell--non-numeric">${u.id}</td>
                        <td class="mdl-data-table__cell--non-numeric">${u.login}</td>
                        <td class="mdl-data-table__cell--non-numeric">${u.email}</td>
                        <td class="mdl-data-table__cell--non-numeric">${u.address}</td>
                        <td class="mdl-data-table__cell--non-numeric">${u.tel}</td>
                        <form method="post" action="${pageContext.request.contextPath}/app">
                            <fieldset>
                        <td class="mdl-data-table__cell--non-numeric">
                            <select name="selected.role">
                                <c:forEach items="${u.roleDtoList}" var="role">
                                    <option value="CURRENT" selected="">${role.roleName}</option>
                                </c:forEach>
                                <c:if test="${empty u.roleDtoList}">
                                    <option value="CURRENT" selected=""></option>
                                </c:if>
                                <c:if test="${not u.roleDtoList.contains(ApplicationConstant.DEFAULT_ROLE)}">
                                    <option value="DEFAULT">${ApplicationConstant.DEFAULT_ROLE}</option>
                                </c:if>
                                <c:if test="${not u.roleDtoList.contains(ApplicationConstant.ADMIN_ROLE)}">
                                    <option value="ADMIN">${ApplicationConstant.ADMIN_ROLE}</option>
                                </c:if>
                                <c:if test="${not u.roleDtoList.contains(ApplicationConstant.MANUFACTURER_ROLE)}">
                                    <option value="MANUFACTURER">${ApplicationConstant.MANUFACTURER_ROLE}</option>
                                </c:if>
                            </select>
                        </td>
                        <td class="mdl-data-table__cell--non-numeric">
<%--                            <form method="post" action="${pageContext.request.contextPath}/app">--%>
<%--                                <fieldset>--%>
                                    <input type="text" name="commandName" value="updateUser"
                                           hidden="hidden">
                                    <input type="text" name="user.id" value="${u.id}" hidden="hidden">
                                    <input type="text" name="user.login" value="${u.login}" hidden="hidden">
                                    <input type="text" name="user.email" value="${u.email}" hidden="hidden">
                                    <input type="text" name="user.name" value="${u.name}" hidden="hidden">
                                    <input type="text" name="user.address" value="${u.address}" hidden="hidden">
<%--                                    <input name="user.role" hidden="hidden" onselect="function get() {--%>
<%--                                        this.value = document.getElementById('select-role').value;--%>
<%--                                    }">--%>
                                    <input type="text" name="user.tel" value="${u.tel}" hidden="hidden">
                                    <button type="submit" class="mdl-button mdl-js-button mdl-button--icon">
                                        <i style="color: lawngreen" class="material-icons">done</i>
                                    </button>
                                </fieldset>
                            </form>
                        </td>
                        <td class="mdl-data-table__cell--non-numeric">
                            <form method="post" action="${pageContext.request.contextPath}/app">
                                <fieldset>
                                    <input type="text" name="commandName" value="deleteUser"
                                           hidden="hidden">
                                    <input type="text" name="user.id" value="${u.id}" hidden="hidden">
                                    <input type="text" name="user.login" value="${u.login}" hidden="hidden">
                                    <input type="text" name="user.email" value="${u.email}" hidden="hidden">
                                    <input type="text" name="user.name" value="${u.name}" hidden="hidden">
                                    <input type="text" name="user.address" value="${u.address}" hidden="hidden">
                                    <input type="text" name="user.tel" value="${u.tel}" hidden="hidden">
                                    <button class="mdl-button mdl-js-button mdl-button--icon">
                                        <i style="color: red" class="material-icons">clear</i>
                                    </button>
                                </fieldset>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <fmt:message key="data.not.available.msg"/>
        </c:otherwise>
    </c:choose>
</c:if>
