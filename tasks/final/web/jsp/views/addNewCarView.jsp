<%--
  Created by IntelliJ IDEA.
  User: GRAFIK529
  Date: 02/12/19
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="by.training.machine.monitoring.ApplicationConstant" language="java" %>
<c:if test="${ApplicationConstant.SECURITY_SERVICE.isLogIn(pageContext.request.session)
and ApplicationConstant.SECURITY_SERVICE.containRole(pageContext.request.session, ApplicationConstant.MANUFACTURER_ROLE)}">
    <div class="demo-charts mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid" style="margin: 2px;">
        <div class="demo-charts mdl-color--white mdl-cell mdl-cell--12-col mdl-grid"
             style="margin: 2px;padding-bottom: 0px;">
            <div class="mdl-cell mdl-cell--2-col" style="position: relative;padding: 2px;">

            </div>
            <div class="mdl-cell mdl-cell--4-col" style="position: relative;padding: 2px;">
                <span class="mdl-layout-title"
                      style="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);font-size: 16px;font-weight: 500;color: #a2a4a2;"><fmt:message key="model.msg"/></span>
            </div>
            <div class="mdl-cell mdl-cell--5-col" style="padding: 2px;position: relative;">
                <span class="mdl-layout-title"
                      style="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);font-size: 16px;font-weight: 500;color: #a2a4a2;"><fmt:message key="characteristic.msg"/></span>
            </div>
            <div class="mdl-cell mdl-cell--1-col" style="padding: 2px;">

            </div>
        </div>
        <div class="demo-charts mdl-color--white mdl-cell mdl-cell--12-col mdl-grid"
             style="margin: 2px;padding-top: 0px;">
            <div class="mdl-cell mdl-cell--2-col" style="position: relative;padding: 2px;">

            </div>
            <div class="mdl-cell mdl-cell--4-col" style="padding: 2px;">
                <select style="
    border: none;
    height: 35px;
    width: 100%;
    box-shadow: 0 1px 1px 0 rgba(0, 0, 0, .10);
    font-size: 16px;">
                    <option value="volvo">Volvo</option>
                    <option value="saab">Saarfghhb</option>
                    <option value="opel">Opel</option>
                    <option value="audi">Audi</option>
                </select>
            </div>
            <div class="mdl-cell mdl-cell--5-col" style="padding: 2px;">
                <select style="
    height: 35px;
    width: 100%;
    box-shadow: 0 1px 1px 0 rgba(0, 0, 0, .10);
    border: none;
    font-size: 16px;">
                    <option value="volvo">Volvo</option>
                    <option value="saab">Saarfghhb</option>
                    <option value="opel">Opel</option>
                    <option value="audi">Audi</option>
                </select>
            </div>
            <div class="mdl-cell mdl-cell--1-col" style="padding: 2px;">
                <button class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored"
                        style="background: rgba(76, 175, 80, 0.63);width: 100%;padding: 0;"
                        data-upgraded=",MaterialButton">
                    Save
                </button>
            </div>
        </div>
        <div class="demo-charts mdl-color--white mdl-cell mdl-cell--12-col mdl-grid"
             style="margin: 2px;padding-bottom: 0px;padding-top: 0px;">
            <div class="mdl-cell mdl-cell--2-col" style="position: relative;padding: 2px;">
                <span class="mdl-layout-title"
                      style="font-size: 16px;font-weight: 500;color: #a2a4a2;"><fmt:message key="unic.number.msg"/>:</span>
            </div>
            <div class="mdl-cell mdl-cell--9-col" style="position: relative;padding: 2px;">
                <div class="mdl-textfield mdl-js-textfield is-upgraded" data-upgraded=",MaterialTextfield">
                    <input class="mdl-textfield__input" type="text" id="sample3">
                    <label class="mdl-textfield__label" for="sample3">Unic number...</label>
                </div>
            </div>
            <div class="mdl-cell mdl-cell--1-col" style="position: relative;padding: 2px;">
            </div>
        </div>
        <div class="demo-charts mdl-color--white mdl-cell mdl-cell--12-col mdl-grid" style="margin: 2px;">
            <div class="mdl-cell mdl-cell--2-col" style="padding: 2px;">

            </div>
            <div class="mdl-cell--bottom mdl-cell--4-col"
                 style="margin-right: 10px;padding: 5px;box-shadow: 0 1px 1px 0 rgba(0, 0, 0, .10);">
                <span class="mdl-layout-title"
                      style="font-size: 16px;font-weight: 500;color: #a2a4a2;/* margin: 20px; */margin-bottom: 30px;"><fmt:message key="model.add.new.msg"/></span>

                <div class="mdl-grid" style="
    padding-right: 0px;
    padding-left: 0px;
">
                    <div class="mdl-cell mdl-cell--6-col">
                        <span class="mdl-layout-title"
                              style="font-size: 14px;/* font-weight: 500; */color: #a2a4a2;/* margin: 10px; */">Add photo:</span>
                    </div>
                    <div class="mdl-cell--center mdl-cell--6-col">
                        <button class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored"
                                style="background: rgba(55, 71, 79, 0.58);width: 100%;/* padding: 0; */"
                                data-upgraded=",MaterialButton">
                            Add
                        </button>
                    </div>
                </div>


                <form action="${pageContext.request.contextPath}/app" method="post">
                    <fieldset>
                        <input type="text" name="commandName" value="addModel" hidden="hidden">
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="text" id="sample11" name="model.name" value="${param.get('model.name')}"
                            <c:if test="${not empty pageContext.findAttribute('model.name.error')}">
                                   style="border-bottom-color: red"
                            </c:if>>
                            <label class="mdl-textfield__label" for="sample11"><fmt:message key="model.name.msg"/>...</label>
                            <c:forEach items="${pageContext.findAttribute('model.name.error')}" var="error">
                                <p style="color: red">${error}</p>
                            </c:forEach>
                        </div>
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="date" name="release.date"
                            <c:if test="${not empty pageContext.findAttribute('release.date.error')}">
                                   style="border-bottom-color: red"
                            </c:if>>
                            <c:forEach items="${pageContext.findAttribute('release.date.error')}" var="error">
                                <p style="color: red">${error}</p>
                            </c:forEach>
                        </div>
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="text" id="sample33" name="model.description" value="${param.get('model.description')}"
                            <c:if test="${not empty pageContext.findAttribute('model.description.error')}">
                                   style="border-bottom-color: red"
                            </c:if>>
                            <c:forEach items="${pageContext.findAttribute('model.description.error')}" var="error">
                                <p style="color: red">${error}</p>
                            </c:forEach>
                            <label class="mdl-textfield__label" for="sample33"><fmt:message key="model.description.msg"/>...</label>
                        </div>
                        <button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored"
                                style="background: rgba(55, 71, 79, 0.79);width: 100%;padding: 0;"
                                data-upgraded=",MaterialButton">
                            <fmt:message key="model.add.msg"/>
                        </button>
                    </fieldset>
                </form>

            </div>
            <div class="mdl-cell--bottom mdl-cell--5-col"
                 style="padding: 5px;box-shadow: 0 1px 1px 0 rgba(0, 0, 0, .10);">
                <span class="mdl-layout-title" style="font-size: 16px;font-weight: 500;color: #a2a4a2;"><fmt:message key="characteristic.add.msg"/></span>
                <form action="${pageContext.request.contextPath}/app" method="post">
                    <fieldset>
                        <input type="text" name="commandName" value="addCharacteristic" hidden="hidden">
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="text" id="sample1" name="characteristic.price" value="${param.get('characteristic.price')}"
                            <c:if test="${not empty pageContext.findAttribute('characteristic.price.error')}">
                                   style="border-bottom-color: red"
                            </c:if>>
                            <label class="mdl-textfield__label" for="sample1"><fmt:message key="characteristic.price.msg"/>...</label>
                            <c:forEach items="${pageContext.findAttribute('characteristic.price.error')}" var="error">
                                <p style="color: red">${error}</p>
                            </c:forEach>
                        </div>
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="text" id="sample333" name="characteristic.power" value="${param.get('characteristic.power')}"
                            <c:if test="${not empty pageContext.findAttribute('characteristic.power.error')}">
                                   style="border-bottom-color: red"
                            </c:if>>
                            <label class="mdl-textfield__label" for="sample333"><fmt:message key="characteristic.power.msg"/>...</label>
                            <c:forEach items="${pageContext.findAttribute('characteristic.power.error')}" var="error">
                                <p style="color: red">${error}</p>
                            </c:forEach>
                        </div>
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="text" id="sample4" name="characteristic.fuel.type" value="${param.get('characteristic.fuel.type')}"
                            <c:if test="${not empty pageContext.findAttribute('characteristic.fuel.type.error')}">
                                   style="border-bottom-color: red"
                            </c:if>>
                            <label class="mdl-textfield__label" for="sample4">Fuel type...</label>
                            <c:forEach items="${pageContext.findAttribute('characteristic.fuel.type.error')}" var="error">
                                <p style="color: red">${error}</p>
                            </c:forEach>
                        </div>
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="text" id="sample5" name="characteristic.engine.volume" value="${param.get('characteristic.engine.volume')}"
                            <c:if test="${not empty pageContext.findAttribute('characteristic.engine.volume.error')}">
                                   style="border-bottom-color: red"
                            </c:if>>
                            <label class="mdl-textfield__label" for="sample5"><fmt:message key="characteristic.engine.volume.msg"/>...</label>
                            <c:forEach items="${pageContext.findAttribute('characteristic.engine.volume.error')}" var="error">
                                <p style="color: red">${error}</p>
                            </c:forEach>
                        </div>
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" type="text" id="sample6" name="characteristic.transmission" value="${param.get('characteristic.transmission')}"
                            <c:if test="${not empty pageContext.findAttribute('characteristic.transmission.error')}">
                                   style="border-bottom-color: red"
                            </c:if>>
                            <label class="mdl-textfield__label" for="sample6"><fmt:message key="characteristic.transmission.msg"/>...</label>
                            <c:forEach items="${pageContext.findAttribute('characteristic.transmission.error')}" var="error">
                                <p style="color: red">${error}</p>
                            </c:forEach>
                        </div>
                        <button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored"
                                style="background: rgba(55, 71, 79, 0.79);width: 100%;padding: 0;"
                                data-upgraded=",MaterialButton">
                            <fmt:message key="model.add.msg"/>
                        </button>
                    </fieldset>
                </form>
            </div>
            <div class="mdl-cell mdl-cell--1-col">
                <div class="mdl-cell mdl-cell--1-col" style="width: 100%;margin: 5px;">
                </div>
                <div class="mdl-cell mdl-cell--1-col" style="width: 100%;margin: 5px;">
                </div>
            </div>
        </div>
    </div>
</c:if>