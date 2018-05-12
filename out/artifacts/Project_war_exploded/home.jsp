<!DOCTYPE html>
<html>
<head>
  <title>Home</title>

  <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@ page import="Main.User" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Main.Node" %>
<%@ page import="java.io.PrintWriter" %>
<%
    User user =(User)session.getAttribute("User");
    user.initiate(request);
    ArrayList<Node> posts = (ArrayList<Node>)session.getAttribute("PostList");
    ArrayList<Integer> friends = (ArrayList<Integer>)session.getAttribute("FriendList");
    ArrayList<Integer> requests = (ArrayList<Integer>)session.getAttribute("RequestList");
    ArrayList<Integer> allUsers = (ArrayList<Integer>)session.getAttribute("AllUsers");
%>
<!-- nav -->
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="index.html">Feysbuk</a>
    </div>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="home.jsp">Home</a></li>
      <li><a href="profile.html">Profile</a></li>
      <li><a href="Logout">Logout</a></li>
    </ul>
  </div>
</nav>
<!-- ./nav -->

<!-- main -->
<main class="container">
  <div class="row">
    <div class="col-md-3">
      <!-- profile brief -->
      <div class="panel panel-default">
        <div class="panel-body">
          <h2><%=user.getUserName()%></h2>
          <p><%=user.getStatus()%></p>
        </div>
      </div>
      <!-- ./profile brief -->

      <!-- friend requests -->
      <div class="panel panel-default">
        <div class="panel-body">
          <h4>Friend Requests</h4>
          <%
            for(Integer _request : requests){
          %>
          <ul>
            <li>
              <a href="#"><%= user.getUserByID(_request)%></a>
              <a class="text-success" href="AcceptFriend?userID=<%= _request%>">[accept]</a>
              <a class="text-danger" href="DeclineRequest?userID=<%= _request%>">[decline]</a>
            </li>
          </ul>
          <% } %>
        </div>
      </div>
      <!-- ./friend requests -->
    </div>
    <div class="col-md-6">
      <!-- post form -->
      <form method="post" action="Post">
        <div class="input-group">
          <input class="form-control" type="text" name="content" placeholder="Make a post...">
          <span class="input-group-btn">
              <button class="btn btn-success" type="submit" name="post">Post</button>
            </span>
        </div>
      </form><hr>
      <!-- ./post form -->

      <!-- feed -->
      <div>
        <!-- post -->
        <%
          String txt;
          for(Node post : posts) {
              if(user.getUserID() == 1)
                  txt = "[delete]";
              else
                  txt = "";
        %>
        <div class="panel panel-default">
          <div class="panel-body">
            <p><%= post.content %></p>
          </div>
          <div class="panel-footer">
            <span>posted by <b> <%= user.getUserByID(post.userID)%> </b>| <%= post.dateTime %>  </span>
            <span class="pull-right"><a class="text-danger" href="DeletePost?postID=<%=post.ID%>"><%=txt%></a></span>
          </div>
        </div>
        <%  }  %>
        <!-- ./post -->
      </div>
      <!-- ./feed -->
    </div>
    <div class="col-md-3">
      <!-- add friend -->
      <div class="panel panel-default">
        <div class="panel-body">
          <h4>Friend Suggestions</h4>
          <%
            for(Integer users : allUsers){
          %>
          <ul>
            <li>
              <a href="#"><%=user.getUserByID(users)%></a>
              <a href="AddFriend?userID=<%=users%>">[add]</a>
            </li>
          </ul>
          <% } %>
        </div>
      </div>
      <!-- ./add friend -->

      <!-- friends -->
      <div class="panel panel-default">
        <div class="panel-body">
          <h4>Friends</h4>
          <%
            for(Integer friend : friends){
          %>
          <ul>
            <li>
              <a href="#"><%=user.getUserByID(friend)%></a>
              <a class="text-danger" href="Unfriend?userID=<%=friend%>">[unfriend]</a>
            </li>
          </ul>
          <% } %>
        </div>
      </div>
      <!-- ./friends -->
    </div>
  </div>
</main>
<!-- ./main -->

<!-- footer -->
<footer class="container text-center">
  <ul class="nav nav-pills pull-right">
    <li>FaceClone - Made by Emre Aydın</li>
  </ul>
</footer>
<!-- ./footer -->
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/script.js"></script>
</body>
</html>