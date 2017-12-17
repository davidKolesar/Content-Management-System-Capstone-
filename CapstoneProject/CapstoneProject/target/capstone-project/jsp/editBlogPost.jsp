<%-- 
    Document   : EditBlogPost
    Created on : Sep 20, 2016, 8:42:14 PM
    Author     : David Kolesar
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>

        <%--<script>--%>
            <%--tinymce.init({--%>
                <%--selector: "textarea",--%>

                <%--// ===========================================--%>
                <%--// INCLUDE THE PLUGIN--%>
                <%--// ===========================================--%>

                <%--plugins: [--%>
                    <%--"advlist autolink lists link image charmap print preview anchor",--%>
                    <%--"searchreplace visualblocks code fullscreen",--%>
                    <%--"insertdatetime media table contextmenu paste jbimages"--%>
                <%--],--%>

                <%--// ===========================================--%>
                <%--// PUT PLUGIN'S BUTTON on the toolbar--%>
                <%--// ===========================================--%>

                <%--toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image jbimages",--%>

                <%--// ===========================================--%>
                <%--// SET RELATIVE_URLS to FALSE (This is required for images to display properly)--%>
                <%--// ===========================================--%>

                <%--relative_urls: false--%>

            <%--});--%>
        <%--</script>--%>

        <title>Capstone</title>

        <style>

            .errorMessages {
                color: red;
            }

            .borderless {
                border-top: none !important;
            }

        </style>

        <style>

            @media screen and (min-width: 768px) {

                #editBlogPostModal .modal-dialog  {width:900px;}

            }

        </style>

    </head>

    <body>

        <jsp:include page="navbar.jsp" />

        <br />
        <br />

        <div class="row">

            <div class="col-sm-6 ">

                <h2>Blog Posts</h2>

                <table id="blogPostTable" class="table">

                    <tr>
                        <th>Title</th>
                        <th>Publish Date</th>
                        <th>Expiration Date</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>

                    <c:forEach items="${blogPostList}" var="blogPost">
                        <tr id="blogPost-row-${blogPost.id}">
                            <td>${blogPost.title}</td>
                            <td>${blogPost.publishDate}</td>
                            <td>${blogPost.expirationDate}</td>
                            <td><a data-blogpost-id="${blogPost.id}" data-toggle="modal" data-target="#editBlogPostModal">Edit Post</a></td>
                            <td><a data-blogpost-id="${blogPost.id}" data-toggle="modal" data-target="#deleteBlogPostModal">Delete Post</a></td>
                        </tr>
                    </c:forEach>

                </table>

            </div>

        </div>

        <!-- Edit Blog Posts -->
        <div class="modal" id="editBlogPostModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myEditModalLabel">Edit Blog Post</h4>
                    </div>
                    <div class="modal-body">

                        <div class="container">

                            <div class="row">

                                <div class="col-sm-9">

                                    <table class="table">

                                        <input type="hidden" id="editId"/>

                                        <tr>
                                            <td style="text-align: right">Title</td>
                                            <td>
                                                <input type="text" id="editTitle"/> <br/>
                                                <div class="errorMessages" id="edit-title-validation-error"></div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td colspan="2">
                                                <textarea id="htmlContent"></textarea> <br />
                                                <div class="errorMessages" id="edit-htmlContent-validation-error"></div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td style="text-align: right">Publish Date</td>
                                            <td>
                                                <input type="date" id="editPostDate"/> <br/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td style="text-align: right">Expiration Date</td>
                                            <td>
                                                <input type="date" id="editExpirationDate"/><br/>
                                            </td>
                                        </tr>

                                        <tr style="text-align: center">
                                            <td style="text-align: center" colspan="2">
                                                <table class="table" id="categoryTable">

                                                    <tr>
                                                        <td class="borderless" colspan="4"><h4 style="text-align: center">Categories</h4></td>
                                                    </tr>

                                                    <tr class="categoryRows">
                                                        <c:forEach items="${categoryList}" var="category" varStatus="counter">
                                                            <c:if test="${(counter.count-1) mod 4 == 0}">
                                                                </tr> <tr class="categoryRows">
                                                            </c:if>
                                                            <td class="borderless" style="text-align: center;">${category.categoryName} <input class="checkboxes" type="checkbox" id="category-${category.id}" name="blogPostCategory" value="${category.id}" /></td>
                                                        </c:forEach>
                                                </tr>

                                                </table>
                                                <a data-toggle="modal" data-target="#createCategoryModal">New Category</a>
                                            </td>
                                        </tr>

                                    </table>

                                </div>

                            </div>

                        </div>

                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" id="editBlogPostButton">Save changes</button>
                    </div>

                </div>

            </div>

        </div>

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