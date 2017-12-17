<%--
  Created by IntelliJ IDEA.
  User: paulharding
  Date: 10/11/16
  Time: 11:27 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>

        <title>${blogPost.title}</title>

    </head>

    <style>

        #blogPostHeader {
            text-align: center;
        }

        #blogPostContent {
            text-align: center;
        }

    </style>

    <body>

        <div class="container">

            <jsp:include page="navbar.jsp" />

            <div class="row">

                <div class="col-sm-6 col-sm-offset-3">

                    <div id="blogPostHeader">
                        <h3>${blogPost.title}</h3> <br />
                        <b>Written by:</b> ${blogPost.user.userName} <br />
                        <b>Published on:</b> ${blogPost.publishDate} <br /> <br />
                    </div>

                    <span id="blogPostContent">${blogPost.htmlContent}</span>

                </div>

            </div>

        </div>

        <jsp:include page="footer.jsp" />

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>

</html>
