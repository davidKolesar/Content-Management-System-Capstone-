<%--
  Created by IntelliJ IDEA.
  User: paulharding
  Date: 10/10/16
  Time: 11:43 AM
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

        <title>Admin Login</title>

        <style>

            table td {
                border-top: none !important;
            }

            #loginForm {
                border-style: solid;
                border-width: 1px;
            }

        </style>

    </head>

    <body>

        <jsp:include page="navbar.jsp" />

        <br /> <br />

        <div class="container">

            <div class="row">

                <div class="col-sm-4 col-sm-offset-4">

                    <div id="loginForm">

                        <form action="${pageContext.request.contextPath}/j_spring_security_check" method="POST">

                            <table class="table">

                                <tr>
                                    <td colspan="2" style="text-align: center"><h4>Administrator Login</h4></td>
                                </tr>

                                <c:if test="${param.login_error == 1}">
                                    <tr>
                                        <td colspan="2" style="text-align: center"><h5 style="color: red;">Error logging in. Please retry.</h5></td>
                                    </tr>
                                </c:if>

                                <tr>
                                    <td>User Name</td>
                                    <td><input type="text" name="username" /> </td>
                                </tr>

                                <tr>
                                    <td>Password</td>
                                    <td><input type="password" name="password" /> </td>
                                </tr>

                                <tr>
                                    <td><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/></td>
                                </tr>

                                <tr>
                                    <td colspan="2" style="text-align: center"><input type="submit" value="Login" /> </td>
                                </tr>

                            </table>

                        </form>

                    </div>

                </div>

            </div>

            <jsp:include page="footer.jsp" />

        </div>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>

</html>
