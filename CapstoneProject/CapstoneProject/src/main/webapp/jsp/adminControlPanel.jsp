<%-- 
    Document   : adminControlPanel
    Created on : Sep 20, 2016, 8:02:14 PM
    Author     : David Kolesar
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>

        <title>Capstone</title>

        <style>


            #adminTable {
                text-align: center;
            }

            th {
                text-align: center;
                border-top: none !important;
            }

            td {
                text-align: center;
            }
            
            
        </style>



    </head>

    <body>

        <div class="container">

        <jsp:include page="navbar.jsp" />

        <br /> <br />

        <div class="row">

            <div class="col-sm-6 col-sm-offset-3">
                <h2 style="text-align: center">Administrator Control Panel</h2>
            </div>

        </div>

        <br />

        <div class="row">

            <div class="col-sm-6 col-sm-offset-3">
                <h4 style="text-align: center">Hello, <c:out value="${pageContext.request.remoteUser}"/></h4>
            </div>

        </div>

        <br />

        <div class="row">

            <div class="col-sm-4 col-sm-offset-4 ">

                <table id="adminTable" class="table table-bordered">

                    <th>Options</th>


                    <tr>
                        <td><a href="${pageContext.request.contextPath}/admin/createblog">New Blog Post</a></td>
                        <td><a href="${pageContext.request.contextPath}/admin/editblog">Edit Posts</a></td>

                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <td><a href="${pageContext.request.contextPath}/admin/editusers">Add/Edit User</a></td>
                        </sec:authorize>

                    </tr>

                    <tr>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <td> <a href="${pageContext.request.contextPath}/admin/reviewposts">Review Posts</a></td>
                        </sec:authorize>

                        <td><a href="${pageContext.request.contextPath}/admin/createpage">New Static Page</a></td>
                        <td><a href="${pageContext.request.contextPath}/admin/editpage">Edit Static Page</a></td>
                    </tr>

                </table>

            </div>
        </div>

        <jsp:include page="footer.jsp" />

            </div>

        <script>
            var contextRoot = "${pageContext.request.contextPath}";
        </script>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js" ></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/home.js" ></script>

    </body>

</html>