<%-- 
    Document   : genericStaticPage
    Created on : Oct 12, 2016, 1:44:20 PM
    Author     : apprentice
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>

        <title>${StaticPage.title}</title>

    </head>

    <body>

        <div class="container">

            <jsp:include page="navbar.jsp" />

            ${StaticPage.htmlContent}

            <jsp:include page="footer.jsp" />

        </div>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>

</html>
