<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // prevent from accessing user page without permission
    if(session.getAttribute("login-role") != "user") {
        response.sendRedirect("index.jsp");
    }
%>
<html>
<head>
    <title>Account</title>
    <script type="text/javascript">
        function generateNumbers(){
        let array = new Uint8Array(6);
        window.crypto.getRandomValues(array);
        let i;
        for (i=0;i<array.length;i++) {
            document.getElementById("number" + (i + 1).toString()).value = array[i] % 61; // from 0 to 60 inclusive
            }
        }
    </script>
</head>
<body>
<h1>User Account</h1>

<p><%
    if (request.getAttribute("message") != null){
        out.print(request.getAttribute("message"));
    }
%></p>
<p>First Name: <%= session.getAttribute("firstname") %><br>
    Last Name: <%= session.getAttribute("lastname") %><br>
    Email: <%= session.getAttribute("email") %><br>
    Username: <%= session.getAttribute("username") %><br>
    Phone: <%= session.getAttribute("phone") %><br></p>

<h3>Enter 6 integers between 1 and 60: </h3>
<form action="AddUserNumbers" name="AddUserNumbers" method="post">
    <label for="number1">1: </label>
    <input type="number" id="number1" name="number1" min="0" max="60" value="" required><br>
    <label for="number2">2: </label>
    <input type="number" id="number2" name="number2" min="0" max="60" value="" required><br>
    <label for="number3">3: </label>
    <input type="number" id="number3" name="number3" min="0" max="60" value="" required><br>
    <label for="number4">4: </label>
    <input type="number" id="number4" name="number4" min="0" max="60" value="" required><br>
    <label for="number5">5: </label>
    <input type="number" id="number5" name="number5" min="0" max="60" value="" required><br>
    <label for="number6">6: </label>
    <input type="number" id="number6" name="number6" min="0" max="60" value="" required><br><br>
    <input type="button" onclick="generateNumbers()" value="Random">
    <input type="submit" value="Submit">
</form><br>
<form action="GetUserNumbers" name="GetUserNumbers" method="post">
    <input type="submit" id="GetDraws" name="GetDraws" value="Get Draws">
</form><br>
<p id="draws">
<%
    List<String> stringNumbers = (List<String>) session.getAttribute("draws");
    if (stringNumbers != null) {
        for(String draw : stringNumbers){
            out.println(draw + "<br>");
        }
        %>
        <form action="checkWinner" name="checkWinner" method="post">
            <input type="submit" value="Check Winner">
        </form>
<%
    }
%>
</p><br>
<a href="index.jsp">Home Page</a>
</body>
</html>
