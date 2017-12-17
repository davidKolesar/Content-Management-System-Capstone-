<%--
  Created by IntelliJ IDEA.
  User: paulharding
  Date: 10/15/16
  Time: 7:56 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <head>

        <title>Logout</title>

    </head>

    <body>

        <c:url var="logoutUrl" value="/logout"/>
        <form class="form-inline" action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

    </body>

</html>
