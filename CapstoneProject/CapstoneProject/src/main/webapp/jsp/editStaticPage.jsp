<%-- 
    Document   : EditStaticPage
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

        <script src='//cdn.tinymce.com/4/tinymce.min.js'></script>
        <script>
            tinymce.init({
  selector: "textarea",
  height: 500,
  plugins: [
    "advlist autolink autosave link image lists charmap print preview hr anchor pagebreak spellchecker",
    "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
    "table contextmenu directionality emoticons template textcolor paste fullpage textcolor colorpicker textpattern"
  ],

  toolbar1: "newdocument fullpage | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | styleselect formatselect fontselect fontsizeselect",
  toolbar2: "cut copy paste | searchreplace | bullist numlist | outdent indent blockquote | undo redo | link unlink anchor image media code | insertdatetime preview | forecolor backcolor",
  toolbar3: "table | hr removeformat | subscript superscript | charmap emoticons | print fullscreen | ltr rtl | spellchecker | visualchars visualblocks nonbreaking template pagebreak restoredraft",

  menubar: false,
  toolbar_items_size: 'small',

  style_formats: [{
    title: 'Bold text',
    inline: 'b'
  }, {
    title: 'Red text',
    inline: 'span',
    styles: {
      color: '#ff0000'
    }
  }, {
    title: 'Red header',
    block: 'h1',
    styles: {
      color: '#ff0000'
    }
  }, {
    title: 'Example 1',
    inline: 'span',
    classes: 'example1'
  }, {
    title: 'Example 2',
    inline: 'span',
    classes: 'example2'
  }, {
    title: 'Table styles'
  }, {
    title: 'Table row 1',
    selector: 'tr',
    classes: 'tablerow1'
  }],

  templates: [{
    title: 'Test template 1',
    content: 'Test 1'
  }, {
    title: 'Test template 2',
    content: 'Test 2'
  }],
  content_css: [
    '//www.tinymce.com/css/codepen.min.css'
  ]
});
        </script>

        <title>Capstone</title>

        <style>

            .errorMessages {
                color: red;
            }

            @media screen and (min-width: 768px) {

                #editStaticPageModal .modal-dialog  {width:900px;}

            }

        </style>

    </head>

    <body>

        <jsp:include page="navbar.jsp" />

        <br />
        <br />

        <div class="row">

            <div class="col-sm-6 ">

                <h2>Static Pages</h2>

                <table id="staticPageTable" class="table">

                    <tr>
                        <th>Title</th>
                        <th>Publish Date</th>
                        <th>Expiration Date</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>

                    <c:forEach items="${staticPageList}" var="staticPage">
                        <tr id="staticPage-row-${staticPage.id}">
                            <td>${staticPage.title}</td>
                            <td>${staticPage.dateToPost}</td>
                            <td>${staticPage.expirationDate}</td>
                            <td><a data-staticpage-id="${staticPage.id}" data-toggle="modal" data-target="#editStaticPageModal">Edit Page</a></td>
                            <td><a data-staticpage-id="${staticPage.id}" data-toggle="modal" data-target="#deleteStaticPageModal">Delete Page</a></td>
                        </tr>
                    </c:forEach>

                </table>

            </div>

        </div>

        <!-- Edit Static Page -->
            <div class="modal" id="editStaticPageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myEditModalLabel">Edit Static Page</h4>
                    </div>
                    <div class="modal-body">

                        <div class="container">

                            <div class="row">

                                <div class="col-sm-6">

                                    <table class="table">

                                        <input type="hidden" id="editId"/>

                                        <tr>
                                            <td>Title</td>
                                            <td>
                                                <input type="text" id="editTitle"/> <br/>
                                                <div class="errorMessages" id="edit-title-validation-error"></div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <h1 class ="text-center">Edit Static Page</h1>
                                            </td>
                                            <td>
                                                <textarea id="htmlContent"></textarea> <br />
                                                <div class="errorMessages" id="edit-htmlContent-validation-error"></div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>Publish Date</td>
                                            <td>
                                                <input type="date" id="editPostDate"/> <br/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>Expiration Date</td>
                                            <td>
                                                <input type="date" id="editExpirationDate"/><br/>
                                            </td>
                                        </tr>

                                    </table>

                                </div>

                            </div>

                        </div>

                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" id="editStaticPageButton">Save changes</button>
                    </div>

                </div>

            </div>

        </div>

        <!-- Delete Static Pages -->
        <div class="modal" id="deleteStaticPageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myDeleteModalLabel">Delete Static Page</h4>
                    </div>
                    <div class="modal-body">

                        <input type="hidden" id="deletePostId" />

                        Are you sure that you want to delete this Static Page?

                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-primary" id="deleteStaticPageButton">Confirm</button>
                    </div>

                </div>

            </div>

        </div>

        <script>
            var contextRoot = "${pageContext.request.contextPath}";
        </script>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js" ></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/staticpage.js" ></script>


    </body>

</html>