<%--
  Created by IntelliJ IDEA.
  User: GRAFIK529
  Date: 16/12/19
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<c:if test="${not ApplicationConstant.SECURITY_SERVICE.isLogIn(pageContext.request.session)}">
    <footer class="mdl-mini-footer" style="height: 90px;">
        <div class="mdl-mini-footer__left-section">
            <ul class="mdl-mini-footer__link-list">
                <li><a href="https://bitbucket.org/AndreiZdanovich/java-web-training/src/master/">Git</a></li>
                <li><a href="https://bitbucket.org/AndreiZdanovich/java-web-training/src/master/">Help</a></li>
            </ul>
        </div>
        <div class="mdl-mini-footer__right-section">
            <button class="mdl-button mdl-js-button mdl-button--icon mdl-button--colored">
                <i class="material-icons">mood</i>
            </button>
            <button class="mdl-button mdl-js-button mdl-button--icon mdl-button--colored">
                <i class="material-icons">mood</i>
            </button>
            <button class="mdl-button mdl-js-button mdl-button--icon mdl-button--colored">
                <i class="material-icons">mood</i>
            </button>
        </div>
    </footer>
</c:if>
