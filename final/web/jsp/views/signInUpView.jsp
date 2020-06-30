<%--
  Created by IntelliJ IDEA.
  User: GRAFIK529
  Date: 19/11/19
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="by.training.machine.monitoring.app.ApplicationConstant" language="java" %>
<c:if test="${not ApplicationConstant.SECURITY_SERVICE.isLogIn(pageContext.request.session)}">
    <button class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored" style="background: rgb(152, 152, 152);"
            onclick="document.getElementById('modal-wrapper').style.display='block'">
        <fmt:message key="sign.in"/>
    </button>
</c:if>
<div id="modal-wrapper" class="modal">
    <div class="modal-content animate mdl-card mdl-shadow--2dp">
        <div class="mdl-tabs mdl-js-tabs mdl-js-ripple-effect">
            <div class="mdl-tabs__tab-bar" id="tab-navigation">
                <a href="#login-panel" id="login"
                   class="mdl-tabs__tab is-active"><fmt:message key="sign.in"/>
                </a>
                <a href="#register-panel" id="registration"
                   class="mdl-tabs__tab"><fmt:message key="sign.up"/>
                </a>
                <button class="mdl-button mdl-js-button mdl-button--icon mdl-button--colored close"
                        onclick="document.getElementById('modal-wrapper').style.display='none'"
                        title="Close PopUp">
                    <i class="material-icons">clear</i>
                </button>
            </div>
            <div class="mdl-tabs__panel is-active" id="login-panel">
                <img class="avatar" src="${pageContext.request.contextPath}/static/images/login.png"
                     alt="Avatar">
                <form class="login" method="post" action="${pageContext.request.contextPath}/app">
                    <fieldset>
                        <input type="text" name="commandName" value="loginUser" hidden="hidden">
                        <c:forEach items="${pageContext.findAttribute('user.login.password.incorrect')}" var="error">
                            <p style="color: red">${error}</p>
                        </c:forEach>
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="text" id="sample1"
                                   name="user.login"
                            <c:if test="${not empty pageContext.findAttribute('user.login.password.incorrect')}">
                                   style="border-bottom-color: red"
                            </c:if>>
                            <label class="mdl-textfield__label"
                                   for="sample1"><fmt:message key="user.login"/>...</label>
                        </div>
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="password" id="sample2"
                            <c:if test="${not empty pageContext.findAttribute('user.login.password.incorrect')}">
                                   style="border-bottom-color: red"
                            </c:if>
                                   name="user.password">
                            <label class="mdl-textfield__label"
                                   for="sample1"><fmt:message key="user.password"/>...</label>
                        </div>
                        <button type="submit"
                                class="sign-in-button mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
                            <fmt:message key="sign.in"/>
                        </button>
                    </fieldset>
                </form>
            </div>
            <div class="mdl-tabs__panel" id="register-panel">
                <img class="avatar"
                     src="${pageContext.request.contextPath}/static/images/user-registration.png"
                     alt="Avatar">
                <form class="login" method="post" action="${pageContext.request.contextPath}/app">
                    <fieldset>
                        <input type="text" name="commandName" value="registerUser" hidden="hidden">
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="text" id="sample4" name="user.reg.login"
                                   value="${param.get('user.reg.login')}"
                            <c:if test="${not empty pageContext.findAttribute('user.login.incorrect')}">
                                   style="border-bottom-color: red"
                            </c:if>>
                            <label class="mdl-textfield__label"
                                   for="sample1"><fmt:message key="user.login"/>...</label>
                        </div>
                        <c:forEach items="${pageContext.findAttribute('user.login.incorrect')}" var="error">
                            <p style="color: red">${error}</p>
                        </c:forEach>
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="password" id="sample5" name="user.password"
                            <c:if test="${not empty pageContext.findAttribute('user.password.incorrect')}">
                                   style="border-bottom-color: red"
                            </c:if>>
                            <label class="mdl-textfield__label"
                                   for="sample1"><fmt:message key="user.password"/>...</label>
                        </div>
                        <c:forEach items="${pageContext.findAttribute('user.password.incorrect')}" var="error">
                            <p style="color: red">${error}</p>
                        </c:forEach>
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="email" id="sample6" name="user.email"
                                   value="${param.get('user.email')}"
                            <c:if test="${not empty pageContext.findAttribute('user.email.incorrect')}">
                                   style="border-bottom-color: red"
                            </c:if>>
                            <label class="mdl-textfield__label"
                                   for="sample1"><fmt:message key="user.email"/>...</label>
                        </div>
                        <c:forEach items="${pageContext.findAttribute('user.email.incorrect')}" var="error">
                            <p style="color: red">${error}</p>
                        </c:forEach>
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="text" id="sample8" name="user.name"
                                   value="${param.get('user.name')}"
                            <c:if test="${not empty pageContext.findAttribute('user.name.incorrect')}">
                                   style="border-bottom-color: red"
                            </c:if>>
                            <label class="mdl-textfield__label"
                                   for="sample1"><fmt:message key="user.name"/>...</label>
                        </div>
                        <c:forEach items="${pageContext.findAttribute('user.name.incorrect')}" var="error">
                            <p style="color: red">${error}</p>
                        </c:forEach>
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="text" id="sample9" name="user.address"
                                   value="${param.get('user.address')}"
                            <c:if test="${not empty pageContext.findAttribute('user.address.incorrect')}">
                                   style="border-bottom-color: red"
                            </c:if>>
                            <label class="mdl-textfield__label"
                                   for="sample1"><fmt:message key="user.address"/>...</label>
                        </div>
                        <c:forEach items="${pageContext.findAttribute('user.address.incorrect')}" var="error">
                            <p style="color: red">${error}</p>
                        </c:forEach>
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="tel" id="sample10" name="user.tel"
                                   value="${param.get('user.tel')}"
                            <c:if test="${not empty pageContext.findAttribute('user.tel.incorrect')}">
                                   style="border-bottom-color: red"
                            </c:if>>
                            <label class="mdl-textfield__label" for="sample1"><fmt:message key="user.tel"/>
                                ...</label>
                        </div>
                        <c:forEach items="${pageContext.findAttribute('user.tel.incorrect')}" var="error">
                            <p style="color: red">${error}</p>
                        </c:forEach>
                        <button type="submit"
                                class="sign-in-button mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
                            <fmt:message key="sign.up"/>
                        </button>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>