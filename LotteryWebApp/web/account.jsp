<%--
  Created by IntelliJ IDEA.
  User: johnmace
  Date: 21/10/2020
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
    <script src="randomNumbers.js"></script>
</head>
<body>
<h1>User Account</h1>

<p><%= request.getAttribute("message") %></p>
<p>First Name: <%= session.getAttribute("firstname") %><br>
    Last Name: <%= session.getAttribute("lastname") %><br>
    Email: <%= session.getAttribute("email") %><br>
    Username: <%= session.getAttribute("username") %><br>
    Phone: <%= session.getAttribute("phone") %><br></p>

<h3>Enter 6 integers between 0 and 60: </h3>
<form action="AddUserNumbers" name="AddUserNumbers" method="post">
    <label for="number1">1: </label>
    <input type="number" id="number1" name="number1" min="0" max="60" value=""><br>
    <label for="number2">2: </label>
    <input type="number" id="number2" name="number2" min="0" max="60" value=""><br>
    <label for="number3">3: </label>
    <input type="number" id="number3" name="number3" min="0" max="60" value=""><br>
    <label for="number4">4: </label>
    <input type="number" id="number4" name="number4" min="0" max="60" value=""><br>
    <label for="number5">5: </label>
    <input type="number" id="number5" name="number5" min="0" max="60" value=""><br>
    <label for="number6">6: </label>
    <input type="number" id="number6" name="number6" min="0" max="60" value=""><br><br>
    <input type="button" onclick="generateNumbers()" value="Random">
    <input type="submit" value="Submit">
</form><br>
<form action="GetUserNumbers" name="GetUserNumbers" method="post">
    <input type="submit" id="GetDraws" name="Get Draws" value="Get Draws">
</form><br>
<a href="index.jsp">Home Page</a>

</body>
</html>
