<%-- 
    Document   : home
    Created on : Sep 20, 2016, 8:02:14 PM
    Author     : David Kolesar
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>

    <title>Capstone</title>

    <style>

        #blogPostTitle {
            font-weight: 500;
            text-align: left;
        }

        h2 {
            text-align: center;
        }

        table td {
            border-top: none !important;
        }

    </style>

</head>

<body>

    <div class="container">

        <jsp:include page="navbar.jsp"/>

        <br /> <br /> <br /> <br /> <br /> <br />

        <div class="row">

            <div class="col-sm-5 col-sm-offset-2">

                <div id="viewBlogPosts"></div>

                    <c:forEach items="${BlogPostList}" var="blogPost">
                        <table class="table table-bordered" id="blogPost-row-${blogpost.id}">

                            <tr>
                                <td id="blogPostTitle" style="text-align: center">
                                    <h2>${blogPost.title}</h2> <br />
                                    <span style="text-align: center">${blogPost.publishDate.month + 1}/${blogPost.publishDate.date}/${blogPost.publishDate.year + 1900}</span>
                                </td>
                            </tr>

                            <tr>
                                <td>${blogPost.htmlContent}</td>
                            </tr>

                            <tr>
                                <td> <span style="font-weight: 800">Categories:</span>
                                    <c:forEach items="${blogPost.blogPostCategories}" var="category" varStatus="counter">
                                        <c:if test="${counter.count mod 3 == 0}">
                                            <br />
                                        </c:if>
                                        ${category.categoryName} |
                                    </c:forEach>
                                </td>
                            </tr>

                        </table>

                        <br /> <br />

                    </c:forEach>

            </div>

            <div class="col-sm-3 col-sm-offset-1">

                <table id="HashtagsTable" class="table">

                    <th colspan="4"><h2>Tags</h2></th>

                    <c:forEach items="${HashtagList}" var="hashtag" varStatus="counter">

                        <c:if test="${(counter.count-1) mod 4 == 0}">
                            </tr> <tr>
                        </c:if>

                        <td id="hashtag-data-${hashtag.id}" style="text-align: center"><a href="${pageContext.request.contextPath}/hashtag/viewposts/${hashtag.id}">${hashtag.tag}</a></td>

                    </c:forEach>

                </table>

                <br /> <br />

                <table id="BlogPostCategoriesTable" class="table">

                    <th colspan="4"><h2>Categories</h2></th>

                    <c:forEach items="${CategoryList}" var="category" varStatus="counter">

                        <c:if test="${(counter.count-1) mod 4 == 0}">
                            </tr> <tr>
                        </c:if>

                        <td id="category-data-${hashtag.id}" style="text-align: center"><a href="${pageContext.request.contextPath}/blogpostcategory/viewposts/${category.id}">${category.categoryName}</a></td>

                    </c:forEach>

                </table>

            </div>

        </div>


        <jsp:include page="footer.jsp"/>

    </div>

    <script>
        var contextRoot = "${pageContext.request.contextPath}";
    </script>

    <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/home.js"></script>

</body>

</html>