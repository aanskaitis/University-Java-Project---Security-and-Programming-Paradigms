<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Home</title>
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
      <script src="registerValidation.js"></script>
      <script src="loginValidation.js"></script>
  </head>
  <body>

  <h2>Register: </h2>
  <p><%
      if (request.getAttribute("message") != null){
          out.print(request.getAttribute("message"));
      }
  %></p>
  <form action="CreateAccount" name="registration" method="post">
      <label for="firstname">First name:</label><br>
      <input type="text" id="firstname" name="firstname"><br>
      <label for="lastname">Last name:</label><br>
      <input type="text" id="lastname" name="lastname"><br>
      <label for="username">Username:</label><br>
      <input type="text" id="username" name="username"><br>
      <label for="phone">Phone:</label><br>
      <input type="text" id="phone" name="phone"><br>
      <label for="email">Email:</label><br>
      <input type="text" id="email" name="email"><br>
      <label for="password">Password:  </label><br>
      <input type="password" id="password" name="password"><br><br>
      <label>Role:</label>
      <select name="role" required>
          <option value="" selected="selected"> - select role - </option>
          <option value="admin">Admin</option>
          <option value="user">User</option>
      </select>
      <input type="submit" value="Submit">
  </form>
  <%
      Integer attempts = (Integer) session.getAttribute("attempts");
  %>
  <h2>Login: </h2>
  <form action="UserLogin" id="login" name="login" method="post">
      <label for="loginUsername">Username:</label><br>
      <input type="text" id="loginUsername" name="username"><br>
      <label for="loginPassword">Password:</label><br>
      <input type="password" id="loginPassword" name="password"><br><br>
      <label>Role:</label>
      <select name="role" required>
          <option value="" selected="selected"> - select role - </option>
          <option value="admin">Admin</option>
          <option value="user">User</option>
      </select>
      <input type="submit" id="loginSubmit" value="Submit">
  </form>
  <script type="text/javascript">
      let login_attempts = "<%=attempts%>";
      if (login_attempts == 3) {
          document.getElementById("loginUsername").disabled=true;
          document.getElementById("loginPassword").disabled=true;
          document.getElementById("loginSubmit").disabled=true;
          document.getElementById("login").disabled=true;
      }
  </script>

  </body>
</html>
