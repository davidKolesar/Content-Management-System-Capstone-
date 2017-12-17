<%-- 
    Document   : contactusform
    Created on : Oct 18, 2016, 2:13:49 PM
    Author     : apprentice
--%>

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>

<body>
<jsp:include page="navbar.jsp"/>


<body>

<div class="container">
    <h1>Contact us</h1>

                <form  action= "${pageContext.request.contextPath}/contactusform" method="POST" commandName="ContactUsPageList">

                <div class="form-group">
                    <label >Name </label>
                    <input type="text" class="form-control" name="InputName" placeholder="Enter Full Name">
                </div>

                <div class="form-group">
                    <label >Email address</label>
                    <input type="text" class="form-control" width="50%" name="InputEmail" placeholder="Email">
                </div>

                <div class="form-group">
                    <label >File attachment</label>
                    <input type="file" name="InputFile">
                    <p class="help-block">Do not exceed 250MB</p>
                </div>

                <div class="form-group">
                    <label >Enter your comments here:</label>
                    <textarea class="form-control" name="InputNote" rows="3"></textarea>
                </div>

                <button type="submit" class="btn btn-success">Submit</button>

            </form>


<!--    <form action="${pageContext.request.contextPath}/contactusform" method="POST" commandName="ContactUsPageList">


        Name
        <input type="text" name="InputName" placeholder="Enter Full Name">


        Email address
        <input type="text" width="50%" name="InputEmail" placeholder="Email">


        File attachment
        <input type="file" name="InputFile">


        Enter your comments here:
        <textarea name="InputNote" rows="3"></textarea>


        <button type="submit">Submit</button>

    </form>-->

</div>

<script>
    var contextRoot = "${pageContext.request.contextPath}";
</script>
</body>

<footer>
    <div class="container">
        <center><p class="text-muted">COPYRIGHT AUGUST 2016 COHORT CAPSTONE TEAM TPD, LLC. ALL RIGHTS RESERVED.</p>
        </center>
    </div>

</footer>


</html>