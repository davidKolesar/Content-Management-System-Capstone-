<%-- 
    Document   : navbar
    Created on : Sep 14, 2016, 1:39:02 PM
    Author     : TPD
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

    <head>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
              integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" 
              integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <hr>
        <title>navbar</title>
    </head>

    <body>
        <h1><center>Gasoline Powered Air Compressors</center></h1>

        <nav class="navbar navbar-inverse">

            <div class="container">

                <ul class="nav nav-tabs">

                    <li role="presentation"><a href="${pageContext.request.contextPath}"><span class="glyphicon glyphicon-home"></span></a></li>

                    <c:forEach items="${staticPageList}" var="staticPage">

                        <li id="staticPageTitle" > <a href="${pageContext.request.contextPath}/staticpage/viewStaticPage/${staticPage.id}">${staticPage.title}</a></li>

                    </c:forEach>

                        <li role="presentation"><a href="${pageContext.request.contextPath}/contactusform"> Contact Us</a></li>
                </ul>

                <sec:authorize url="/admin">

                    <div class="row">
                        <div class="col-sm-1 col-sm-offset-11">
                            <jsp:include page="logout.jsp" />
                        </div>
                    </div>

                </sec:authorize>

            </div>
        </nav>
    </body>
</html>