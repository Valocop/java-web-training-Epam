<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 29.11.2019
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<c:if test="${empty commandName}">
    <div class="demo-charts mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid" style="margin: 2px;">
        <div class="demo-charts mdl-color--white mdl-cell mdl-cell--12-col mdl-grid" style="margin: 2px;padding-bottom: 0px;">
            <div class="mdl-cell mdl-cell--12-col" style="position: relative;padding: 2px;">
                <h2 class="mdl-card__title-text" style="text-align: center;font-weight: 400;color: #51859e;padding-bottom: 5px;border-bottom: 1px solid #eee;display: block;">
                    <span>
                        <fmt:message key="title.description.msg"/>
                    </span>
                </h2>
            </div>
            <div class="mdl-cell mdl-cell--12-col" style="position: relative;padding: 2px;">
                <img id="myImg" src="${pageContext.request.contextPath}/static/images/description.png" alt="Snow" style="width:100%;border-bottom: 1px solid #eee;">
            </div>
            <div class="mdl-cell mdl-cell--12-col" style="width: 100%;position: relative;padding: 2px;">
                <div class="mdl-card__supporting-text mdl-color-text--grey-600" style="width: 100%;text-align: center;">
                    <span>
                        <fmt:message key="service.description.msg"/>
                    </span>
                </div>
            </div>
        </div>
    </div>
</c:if>
