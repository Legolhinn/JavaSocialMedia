package Main;

public class User
{
    private String user, pass;
    private User userObj;
    public User(String _user, String _pass)
    {
        user = _user;
        pass = _pass;
        userObj = this;
    }

    public String getUserName()
    {
        return user;
    }
}
