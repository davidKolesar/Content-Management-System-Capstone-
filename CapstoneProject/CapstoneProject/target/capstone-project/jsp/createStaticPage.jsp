<%-- 
    Document   : adminStaticPage
    Created on : Sep 20, 2016, 8:42:14 PM
    Author     : David Kolesar
--%>

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

        <title>Create Static Page</title>

        <style>

            .tableContent {
                text-align: center;
            }

            .errorMessages {
                color: red;
            }

        </style>

    </head>

    <body>

        <jsp:include page="navbar.jsp"/>

        <div class="container">

            <div class="row">

                <div class="col-sm-6 col-sm-offset-3">

                    <div class="form-group">

                    <h1 class ="text-center">New Static Page</h1> <br />

                    <sf:form method="POST" action="${pageContext.request.contextPath}/staticpage" commandName="StaticPage" class="horizontal-front-group">

                        <table class="table">

                            <tr>
                                <td class="tableContent">
                                    Title <sf:input path="title" type="text" /> <br />
                                    <sf:errors class="errorMessages" path="title" />
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <sf:textarea id="htmlContent" path="htmlContent" ></sf:textarea>
                                    <sf:errors class="errorMessages" path="htmlContent" />
                                </td>
                            </tr>

                            <tr>
                                <td class="tableContent">Date to Publish Page<sf:input path="dateToPost" type="date" placeholder="MM/DD/YYYY" /></td>
                            </tr>

                            <tr>
                                <td class="tableContent">Expiration Date <sf:input path="expirationDate" type="date" placeholder="MM/DD/YYYY" /></td>
                            </tr>


                            <tr>
                                <td class="tableContent"><input id="createStaticPageButton" type="submit" /></td>
                            </tr>

                            </table>

                        </sf:form>

                    </div>

                </div>

            </div>

        </div>

        <script>
            var contextRoot = "${pageContext.request.contextPath}";
        </script>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/staticpage.js"></script>

    </body>

</html>
