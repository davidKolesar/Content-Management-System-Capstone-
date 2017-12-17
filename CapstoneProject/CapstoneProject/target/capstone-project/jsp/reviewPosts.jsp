<%--
  Created by IntelliJ IDEA.
  User: paulharding
  Date: 10/15/16
  Time: 9:36 PM
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

        <title>Review Pending Posts</title>

    </head>

    <body>

        <div class="container">

            <jsp:include page="navbar.jsp" />

            <div class="row">

                <div class="col-sm-4 col-sm-offset-4">

                    <c:forEach items="${blogPostsToReview}" var="blogPost">

                        <table class="table table-bordered" id="blogPost-table-${blogPost.id}">

                            <tr>
                                <td id="blogPostTitle" style="text-align: center">
                                    <h2>${blogPost.title}</h2> <br />
                                    <span style="text-align: left">Publish Date: ${blogPost.publishDate.month + 1}/${blogPost.publishDate.date}/${blogPost.publishDate.year + 1900}</span> <br />
                                </td>
                            </tr>

                            <tr>
                                <td>${blogPost.htmlContent}</td>
                            </tr>

                            <tr>
                                <td style="text-align: right">
                                    <button class="approveButtons" data-blogpost-id="${blogPost.id}">Approve Post</button>
                                    <button class="rejectButtons" data-blogpost-id="${blogPost.id}">Delete Post</button>
                                </td>
                            </tr>

                        </table>

                        <br /> <br />

                    </c:forEach>

                </div>

            </div>

            <jsp:include page="footer.jsp" />

        </div>

        <script>
            var contextRoot = "${pageContext.request.contextPath}";
        </script>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js" ></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/reviewPosts.js"></script>

    </body>

</html>
