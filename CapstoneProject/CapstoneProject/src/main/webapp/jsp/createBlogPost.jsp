<%-- 
    Document   : createnewstaticpage
    Created on : Oct 7, 2016, 2:26:55 PM
    Author     : apprentice
--%>

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>

        <title>Create Static Page</title>

        <style>

            .tableContent {
                text-align: center;
            }

            .errorMessages {
                color: red;
            }

            .borderless {
                border-top: none !important;
            }

        </style>

    </head>

    <body>

        <jsp:include page="navbar.jsp"/>

        <div class="container">

            <div class="row">

                <div class="col-sm-6 col-sm-offset-3">

                    <h1 class ="text-center">New Blog Post</h1> <br />

                    <sf:form method="POST" action="${pageContext.request.contextPath}/blogpost" commandName="addBlogPostCommand" class="horizontal-front-group">

                        <input type="hidden" id="categoryList" value="${categoryList}" />

                        <table class="table">

                            <tr>
                                <td class="tableContent">
                                    Title <sf:input path="title" type="text" /> <br />
                                    <sf:errors class="errorMessages" path="title" />
                                </td>
                            </tr>

                            <%--<div class="modal" id="imageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">--%>
                                <%--<div class="modal-dialog" role="document">--%>
                                    <%--<div class="modal-content">--%>
                                        <%--<div class="modal-header">--%>
                                            <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span--%>
                                                    <%--aria-hidden="true">&times;</span></button>--%>
                                            <%--<h4 class="modal-title" id="myEditModalLabel">Insert Image</h4>--%>
                                        <%--</div>--%>
                                        <%--<div class="modal-body">--%>

                                            <%--<div class="container">--%>

                                                <%--<div class="row">--%>

                                                    <%--<div class="col-sm-9">--%>

<%--<!--                                                        <table class="table">--%>


                                                            <%--<form method="POST" action="uploadFile" enctype="multipart/form-data">--%>
                                                                <%--File to upload: <input type="file" name="file" id="image">--%>

                                                            <%--</form>	-->--%>

                                                        <%--</table>--%>

                                                    <%--</div>--%>

                                                <%--</div>--%>

                                            <%--</div>--%>

                                   <%----%>

                                        <%--<div class="modal-footer">--%>
                                            <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                                            <%--<button type="button" class="btn btn-primary" id="addImage">Add Image</button>--%>
                                        <%--</div>--%>

                  

                            <!-- Delete Blog Posts -->
                            <div class="modal" id="deleteBlogPostModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                                    aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title" id="myDeleteModalLabel">Delete Blog Post</h4>
                                        </div>
                                        <div class="modal-body">

                                            <input type="hidden" id="deletePostId" />

                                            Are you sure that you want to delete this blog post?

                                        </div>

                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                            <button type="button" class="btn btn-primary" id="deleteBlogPostButton">Confirm</button>
                                        </div>

                                    </div>

                                </div>

                            </div>


                            <tr>
                                <td>
                                    <sf:textarea id="htmlContent" path="htmlContent" ></sf:textarea>
                                    <sf:errors class="errorMessages" path="htmlContent" />
                                </td>
                            </tr>

                            <tr>
                                <td class="tableContent">Date to Publish Post <sf:input path="publishDate" type="date" placeholder="MM/DD/YYYY" /></td>
                            </tr>

                            <tr>
                                <td class="tableContent">Expiration Date <sf:input path="expirationDate" type="date" placeholder="MM/DD/YYYY" /></td>
                            </tr>

                            <tr>
                                <td style="text-align: center">
                                    <table class="table" id="categoryTable">

                                        <tr>
                                            <td class="borderless" colspan="4"><h4 style="text-align: center">Categories</h4></td>
                                        </tr>

                                        <tr class="categoryRows">
                                            <c:forEach items="${categoryList}" var="category" varStatus="counter">
                                                <c:if test="${(counter.count-1) mod 4 == 0}">
                                                </tr> <tr class="categoryRows">
                                                </c:if>
                                                <td class="borderless" style="text-align: center;">${category.categoryName} <input type="checkbox" name="blogPostCategory" value="${category.id}" /></td>
                                                </c:forEach>
                                        </tr>

                                    </table>
                                    <a data-toggle="modal" data-target="#createCategoryModal">New Category</a>
                                </td>
                            </tr>

                            <tr>
                                <td class="tableContent" colspan="2"><input id="createBlogPostButton" type="submit" /></td>
                            </tr>

                        </table>

                    </sf:form>

                </div>

            </div>

            <jsp:include page="footer.jsp" />

        </div>

        <!-- Create Blog Post Category -->
        <div class="modal" id="createCategoryModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myCreateCategoryModalLabel" style="text-align: center">Create New Category</h4>
                    </div>
                    <div class="modal-body">

                        <div class="container">

                            <div class="row">

                                <div class="col-sm-2 col-sm-offset-2">

                                    <table class="table">

                                        <tr>
                                            <td style="text-align: center">Category Name</td>
                                        </tr>

                                        <tr>
                                            <td style="text-align: center">
                                                <input type="text" id="createCategoryName" /> <br />
                                                <div class="errorMessages" id="create-categoryName-validation-error" />
                                            </td>
                                        </tr>

                                    </table>

                                </div>

                            </div>

                        </div>

                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-primary" id="createCategoryButton">Submit</button>
                    </div>

                </div>

            </div>

        </div>

        <script>
            var contextRoot = "${pageContext.request.contextPath}";
        </script>



        <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/image.js"></script>
        <script src="${pageContext.request.contextPath}/js/blogpost.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/plugin/tinymce/tinymce.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/plugin/tinymce/init-tinymce.js"></script>
        <script src="${pageContext.request.contextPath}/js/plugin/tinymce/jquery.tinymce.min.js"></script>



    </body>

</html>
