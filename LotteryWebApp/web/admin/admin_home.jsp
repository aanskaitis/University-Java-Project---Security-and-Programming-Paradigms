<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // prevent from accessing admin page without permission
    if(session.getAttribute("login-role") != "admin"){
        response.sendRedirect("../index.jsp");
    }
%>
<html>
<head>
    <title>Admin page</title>
</head>
<body>
<h1>Admin page</h1>
<form action="ViewUsers" name="viewUsers" method="post">
    <input type="submit" value="View users data">
</form>
<div>
<%
    if (request.getAttribute("data") != null){
        out.print(request.getAttribute("data"));
    }
%>
</div>
<br>
<a href="../index.jsp">Home Page</a>
</body>
</html>
