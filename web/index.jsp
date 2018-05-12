<%--
  Created by IntelliJ IDEA.
  User: emrep
  Date: 30.04.2018
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Hell</title>

  <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@ page import="Main.User" %>
<%
  User user =(User)session.getAttribute("User");
  String redirectURL = "http://localhost:8080/home.jsp";
  if(user != null) {
    response.sendRedirect(redirectURL);
  }
%>

<!-- nav -->
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="index.html">FEYSBUK</a>
    </div>
  </div>
</nav>
<!-- ./nav -->

<!-- main -->
<main class="container">
  <h1 class="text-center"> Welcome to hell you fucking motherfucker ! <br><small>Gossipland</small></h1>

  <div class="row">
    <div class="col-md-6">
      <h4>What are you waiting for m8 ?</h4>

      <!-- login form -->
      <form method="post" action="Login">
        <div class="form-group">
          <input class="form-control" type="text" name="username" placeholder="Username">
        </div>

        <div class="form-group">
          <input class="form-control" type="password" name="password" placeholder="Password">
        </div>

        <input type="checkbox" name="remember" value="Remember"> Remember me<br><br>
        <div class="form-group">

          <input class="btn btn-primary" type="submit" name="login" value="Login">
        </div>

        <br>
        <%
          String msg = (String)request.getAttribute("Message");
          if (msg == null)
              msg = "";
         %>
        <%=(String) msg%>
      </form>
      <!-- ./login form -->
    </div>
    <div class="col-md-6">
      <h4>Register already ! Enough with your bullshit omg ResidentSleeper</h4>

      <!-- register form -->
      <form method="post" action="Register">
        <div class="form-group">
          <input class="form-control" type="text" name="username" placeholder="Username">
        </div>

        <div class="form-group">
          <input class="form-control" type="text" name="email" placeholder="Email">
        </div>

        <div class="form-group">
          <input class="form-control" type="password" name="password" placeholder="Password">
        </div>

        <div class="form-group">
          <input class="btn btn-success" type="submit" name="register" value="Register">
        </div>
      </form>
      <!-- ./register form -->
    </div>
  </div>
</main>
<!-- ./main -->

<!-- footer -->
<footer class="container text-center">
  <ul class="nav nav-pills pull-right">
    <li>Feysbuk clone made by Emre Aydın</li>
  </ul>
</footer>
<!-- ./footer -->
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/script.js"></script>
</body>
</html>
