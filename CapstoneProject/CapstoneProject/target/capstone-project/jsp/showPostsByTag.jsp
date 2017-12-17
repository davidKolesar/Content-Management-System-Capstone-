<%--
  Created by IntelliJ IDEA.
  User: paulharding
  Date: 10/10/16
  Time: 2:29 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>

        <title>${hashtag.tag}</title>

        <style>

            #pageTitle {
                text-align: center;
            }

        </style>

    </head>

    <body>

        <div class="container">

            <jsp:include page="navbar.jsp" /> <br />

            <h1 id="pageTitle">${hashtag.tag}</h1> <br /> <br />

            <c:forEach items="${blogPostList}" var="blogPost">

                <div class="row">

                    <div class="col-sm-6 col-sm-offset-3">

                        <table class="table" id="blogPost-row-${blogpost.id}">

                            <tr>
                                <td id="blogPostTitle"><h2>${blogPost.title}</h2></td>
                                <td style="text-align: right"><br /> <br /> ${blogPost.publishDate.month + 1}/${blogPost.publishDate.date}/${blogPost.publishDate.year + 1900}</td>
                            </tr>

                            <tr>
                                <td rowspan="2">${blogPost.htmlContent}</td>
                            </tr>

                        </table> <br /> <br />

                    </div>

                </div>

            </c:forEach>

            <jsp:include page="footer.jsp" />

        </div>

        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>

</html>
