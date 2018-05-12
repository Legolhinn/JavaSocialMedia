package Main;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;

public class User
{
    private String user, pass, status, path;
    private int ID;
    private User userObj;
    public User(int _ID, String _user, String _pass, String _stat, String _path)
    {
        ID = _ID;
        user = _user;
        pass = _pass;
        status = _stat;
        path = _path;
        userObj = this;
    }

    public String getUserName()
    {
        return user;
    }
    public String getStatus(){ return status; }
    public String getPath()
    {
        return path;
    }
    public int getUserID(){ return ID; }

    ArrayList<Integer> friendList = new ArrayList<Integer>();
    ArrayList<Integer> requestList = new ArrayList<Integer>();

    String url = "jdbc:mysql://127.0.0.1:3306/feysbuk?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false";
    String dbUser = "root";
    String dbPassword = "123123";

    public String getUserByID(int _userID) throws  Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try
        {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);

            String query = "SELECT * FROM users";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                if (rs.getInt(1) == _userID)
                    return rs.getString(2);
            }
            connection.close();
        }catch (SQLException ex) { ex.printStackTrace(); }

        return null;
    }

    void updateUser() throws Exception, SQLException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
        String query = "SELECT * FROM users";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next())
            if (rs.getInt(1) == ID)
                status = rs.getString(6);

        connection.close();
    }

    void showFriends(HttpServletRequest req) throws Exception
    {
        friendList.clear();
        requestList.clear();
        Class.forName("com.mysql.cj.jdbc.Driver");
        HttpSession session = req.getSession();
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String query = "SELECT * FROM userrelation";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next())
            {
                if(result.getInt(1) == ID)
                {
                    if (result.getString(3).equals("Y"))
                        friendList.add(result.getInt(2));
                    else
                        requestList.add(result.getInt(2));
                }

                else if (result.getInt(2) == ID)
                {
                    if (result.getString(3).equals("Y"))
                        friendList.add(result.getInt(1));
                }
            }
            session.setAttribute("FriendList", friendList);
            session.setAttribute("RequestList", requestList);
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    void showPosts(HttpServletRequest req) throws  Exception
    {
        ArrayList<Node> postList = new ArrayList<Node>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        HttpSession session = req.getSession();
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String query = "SELECT * FROM posttable ORDER BY DateAndTime DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next())
            {
                Node node = new Node();
                node.ID = result.getInt(1);
                node.content = result.getString(2);
                node.userID = result.getInt(3);
                node.dateTime = result.getString(4);
                if (friendList.contains(node.userID) || node.userID == ID)
                    postList.add(node);
            }
            session.setAttribute("PostList", postList);
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    void AddFriendList(HttpServletRequest req) throws Exception
    {
        ArrayList<Integer> allUsers = new ArrayList<Integer>();
        HttpSession session = req.getSession();
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String sql = "SELECT * FROM users ";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next())
            {
                if (result.getInt(1) != ID)
                {
                    if (!friendList.isEmpty())
                    {
                        for (int i = 0; i < friendList.size(); i++)
                        {
                            if (result.getInt(1) != friendList.indexOf(i))
                            {
                                if (!allUsers.contains(result.getInt(1)) && !friendList.contains(result.getInt(1)))
                                    allUsers.add(result.getInt(1));
                            }
                        }
                    }
                    else
                        allUsers.add(result.getInt(1));
                }
            }
            session.setAttribute("AllUsers",allUsers);
            connection.close();
        }catch (SQLException ex) { ex.printStackTrace(); }

    }

    public void AcceptFriend(HttpServletRequest req, HttpServletResponse res, int user1, int user2) throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String query = "UPDATE userrelation SET isAccepted='Y' WHERE UserID1=? AND UserID2=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user1);
            statement.setInt(2, user2);
            statement.executeUpdate();
            RequestDispatcher rd = req.getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(req, res);
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    public void DeclineRequest(HttpServletRequest req, HttpServletResponse res, int user1, int user2) throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String SQL = "DELETE FROM userrelation WHERE UserID1 = ?  AND UserID2 = ?";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.setInt(1, user1);
            statement.setInt(2, user2);
            statement.executeUpdate();
            RequestDispatcher rd = req.getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(req, res);
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    public void Unfriend(HttpServletRequest req, HttpServletResponse res, int userID1, int userID2) throws Exception, SQLException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
        String SQL = "DELETE FROM userrelation WHERE UserID1 = ?  AND UserID2 = ?";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setInt(1, userID1);
        stm.setInt(2, userID2);
        stm.executeUpdate();
        RequestDispatcher rd = req.getServletContext().getRequestDispatcher("/index.jsp");
        rd.forward(req, res);
    }

    public void AddFriend (HttpServletRequest req, HttpServletResponse res, int user1, int user2) throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String SQL = "INSERT INTO userrelation (UserID1, UserID2, isAccepted) values ('"+user2+"', '"+user1+"','N')";
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.executeUpdate();
            RequestDispatcher rd = req.getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(req, res);
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    public void DeletePost(HttpServletRequest req, HttpServletResponse res, int postID)  throws Exception, SQLException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
        String SQL = "DELETE FROM posttable WHERE PostID = ?";
        PreparedStatement stm = connection.prepareStatement(SQL);
        stm.setInt(1, postID);
        stm.executeUpdate();
        RequestDispatcher rd = req.getServletContext().getRequestDispatcher("/index.jsp");
        rd.forward(req, res);
    }

    public void initiate(HttpServletRequest req) throws Exception
    {
        showFriends(req);
        AddFriendList(req);
        showPosts(req);
        updateUser();
    }
}
