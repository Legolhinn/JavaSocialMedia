
<!DOCTYPE html>
<html>
<head>
  <title>Profile</title>

  <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@ page import="Main.User" %>
<%@ page import="Main.Node" %>
<%@ page import="java.util.ArrayList" %>
<%
  User user =(User)session.getAttribute("User");
  user.showFriends(request, Integer.parseInt(request.getParameter("userID")));
  user.showPosts(request, Integer.parseInt(request.getParameter("userID")));
  user.AddFriendList(request);
  ArrayList<Node> posts = (ArrayList<Node>)session.getAttribute("PostList");
  ArrayList<Integer> friends = (ArrayList<Integer>)session.getAttribute("FriendList");
%>
<!-- nav -->
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="home.jsp">Feysbuk</a>
    </div>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="home.jsp">Home</a></li>
      <li><a href="profile.jsp?userID=<%=user.getUserID()%>">Profile</a></li>
      <li><a href="Logout">Logout</a></li>
    </ul>
  </div>
</nav>
<!-- ./nav -->

<!-- main -->
<main class="container">
  <div class="row">
    <div class="col-md-3">
      <!-- edit profile -->
      <%
        if (Integer.parseInt(request.getParameter("userID")) == user.getUserID()) {
      %>
      <div class="panel panel-default">
        <div class="panel-body">
          <h4>Edit profile</h4>
          <form method="post" action="Status">
            <div class="form-group">
              <input class="form-control" type="text" name="status" placeholder="Status" value="">
            </div>
            <div class="form-group">
              <input class="btn btn-primary" type="submit" name="update_profile" value="Save">
            </div>
          </form>
        </div>
      </div>
      <% } %>
      <!-- ./edit profile -->
    </div>
    <div class="col-md-6">
      <!-- user profile -->
      <div class="media">
        <div class="media-left">
          <img src="<%=user.getImgPath(Integer.parseInt(request.getParameter("userID")))%>" class="media-object" style="width: 128px; height: 128px;">
        </div>
        <div class="media-body">
          <h2 class="media-heading"><%=user.getUserByID(Integer.parseInt(request.getParameter("userID")))%></h2>
          <p><%=user.updateUser(Integer.parseInt(request.getParameter("userID")))%> </p>
        </div>
      </div>
      <!-- user profile -->

      <hr>
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
      <!-- timeline -->
      <div>
        <!-- post -->
        <%
          String txt1;
          for(Node post : posts) {
            if(post.userID == user.getUserID())
              txt1 = "[delete]";
            else
              txt1 = "";
        %>
        <div class="panel panel-default">
          <div class="panel-body">
            <p><%= post.content %></p>
          </div>
          <div class="panel-footer">
            <span>posted by <b> <%= user.getUserByID(post.userID)%> </b>| <%= post.dateTime %>  </span>
            <span class="pull-right"><a class="text-danger" href="DeletePost?postID=<%=post.ID%>"><%=txt1%></a></span>
          </div>
        </div>
        <% } %>
        <!-- ./post -->
      </div>
      <!-- ./timeline -->
    </div>
    <div class="col-md-3">
      <!-- friends -->
      <div class="panel panel-default">
        <div class="panel-body">
          <h4>Friends</h4>
          <%
            String txt2;
            for(Integer friend : friends){
                if(user.getUserID() == Integer.parseInt(request.getParameter("userID")))
                  txt2 = "[unfriend]";
                else
                  txt2 = "";
          %>
          <ul>
            <li>
              <a href="profile.jsp?userID=<%=friend%>"><%=user.getUserByID(friend)%></a>
              <a class="text-danger" href="Unfriend?userID=<%=friend%>"><%=txt2%></a>
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
    <li>Made by Emre Aydin</li>
  </ul>
</footer>
<!-- ./footer -->
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/script.js"></script>
</body>
</html>