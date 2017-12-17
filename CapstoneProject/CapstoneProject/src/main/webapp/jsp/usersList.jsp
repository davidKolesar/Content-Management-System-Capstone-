<%-- 
    Document   : usersList
    Created on : Oct 13, 2016, 3:39:02 PM
    Author     : apprentice
--%>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">


<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>


    <body>
        <jsp:include page="navbar.jsp"/>


        <div class="container"  >
            <div class="col-md-6">
                <h3> My User List </h3>
                <form>
                    <table id= "userTable" class="table">
                        <tr>

                            <th>User #</th>
                            <th>username </th>
                        </tr>

                        <c:forEach items= "${userList}" var="allusers">
                            <tr id="allusers-row-${allusers.id}">
                                <td>${allusers.id}</td>
                                <td><a data-allusers-id="${allusers.id}" data-toggle="modal" data-target="#viewUserModal">${allusers.userName}</td>
                                <td><a data-allusers-id="${allusers.id}" data-toggle="modal" data-target="#editUserModal" > Edit user details</a></td>
                                <td><a data-allusers-id="${allusers.id}" data-toggle="modal" data-target="#deleteUserModal" > Remove user</a></td>
                            </tr>
                        </c:forEach>

                    </table>
                </form>
            </div>  

            <div class="col-md-6">
                <h2> Add New User </h2>  
                <table class="table">
                    <form method="POST" commandName= "allusers" action="${pageContext.request.contextPath}/user/create" role="form"  class="form-horizontal-group">

                        <tr>
                        <div class="form-group">

                            <input type= "hidden" id="idCreate" />

                            <td> <label>User Name:</label></td> 
                            <td> <input type="text" path="userName" id="userNameCreate" placeholder="Enter new username"  />
                                <!--                                <div id="add-userName-validation-errors"> </div>   -->
                            </td>
                        </div>
                        </tr>

                        <tr>   
                        <div class="form-group">
                            <td> <label>Password:</label></td>  
                            <td> <input type="password" path="password" id="passwordCreate"/> 
                                <!--                                <div id="add-password-validation-errors"> </div> -->
                            </td>
                        </div>
                        </tr>

                        <tr>
                        <div class="form-group">
                            <td> <label>Select User:</label></td> 
                            <td> <select type="text" path="roleName" id="roleNameCreate" > 
                                <option value="ROLE_ADMIN">administrator</option>
                                <option value="ROLE_CONTRACTOR">contractor</option>
                                </select>
                                <!--                                <div id="add-permissions-validation-errors"> </div>  -->
                            </td>
                        </div>
                        </tr>



                        <tr> 
                        <div class="form-group" class="align-right">
                            <td>
                                <input id= "CreateUserButton" type ="submit" value="Add new user" />
                            </td>
                        </div>
                        </tr>
                    </form>
                </table>
            </div>



            <div class="modal fade" id="viewUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel"> User Information </h4>
                        </div>
                        <div class="modal-body">
                            <table class="table table-bordered">
                                <tr>
                                    <th> UserName: </th>
                                    <td id="viewUserName"></td>
                                </tr>
                                <tr>
                                    <th>User: </th>
                                    <td id="viewroleName"> </td>
                                </tr>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>



                    <div class="modal fade" id="editUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="myModalLabel"> Edit User Information </h4>
                                </div>
                                <div class="modal-body">

                                    <input type= "hidden" id="editId" />

                                    <table class="table table-bordered">
                                        <tr>
                                            <th> User Name: </th>
                                            <td><input type="text" id="editUserName" /> </td> <br />
                                            <div class="errorMessages" id="username-taken-error"></div>
                                        </tr>
                                        <tr>
                                            <th> Password: </th>
                                            <td><input type="password" id="editPassword" /> </td>
                                        </tr>

                                        <tr>
                                            <th> edit user role: </th>
                                            <td>
                                            <select type="text" path="roleName" id="editRoleName" > 
                                            <option value="ROLE_ADMIN">administrator</option>
                                            <option value="ROLE_CONTRACTOR">contractor</option>
                                        </select>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                    <button type="button" class="btn btn-primary" id="editUserButton" >Save changes</button>
                                </div>
                            </div>
                        </div>
                    </div>


            <div class="modal fade" id="deleteUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">Delete User Information</h4>
                        </div>
                        <form>
                            <div class="modal-body">

                                <input type="hidden" id="deleteId"/>
                                <table class="table table-bordered">
                                    <tr>
                                        <th>User Name:</th>
                                        <td id="deleteUserName" /> </td>
                                    </tr>
                                    <tr>
                                        <th>User:</th>
                                        <td id="deleteroleName"/> </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close Page</button>
                                <button type="button" class="btn btn-primary" id="deleteUserButton" class="delete-link" >Confirm Delete</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>


        </div>

        <script>
            var contextRoot = "${pageContext.request.contextPath}";
        </script>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/addEditUser.js"></script>


    </body>

    <footer>
        <div class="container">
            <center> <p class="text-muted">COPYRIGHT AUGUST 2016 COHORT CAPSTONE TEAM TPD, LLC. ALL RIGHTS RESERVED.</p></center>
        </div>

    </footer>
    
    
</html>
