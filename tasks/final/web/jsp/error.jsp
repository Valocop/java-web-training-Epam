<%--
  Created by IntelliJ IDEA.
  User: GRAFIK529
  Date: 06/11/19
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Error page</title>
</head>
<body>
<h5>ERROR</h5>
Request from ${pageContext.errorData.requestURI} is failed <br/>
Servlet name: ${pageContext.errorData.servletName} <br/>
Status code: ${pageContext.errorData.statusCode} <br/>
Exception: ${pageContext.exception} <br/>
Message from exception: ${pageContext.exception.message}
Cause: ${pageContext.exception.cause.message}
</body>
</html>
