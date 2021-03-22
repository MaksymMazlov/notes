package ua.notes.dao;

import ua.notes.domain.User;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class UserDao extends AbstractDao
{
    public void insertUser(User user) throws SQLException
    {
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("INSERT into account (login,password) values (?,?)"))
        {
            pstm.setString(1, user.getLogin());
            pstm.setString(2, user.getPassword());
            pstm.execute();
        }
    }

    public User findUser(String login, String password) throws SQLException
    {
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("SELECT * FROM account WHERE login=? AND password=?"))
        {
            pstm.setString(1, login);
            pstm.setString(2, password);
            try (ResultSet rset = pstm.executeQuery())
            {
                if (rset.next())
                {
                    User user = new User();
                    user.setId(rset.getInt("id"));
                    user.setLogin(rset.getString("login"));
                    user.setPassword(rset.getString("password"));
                    return user;
                }
            }
        }
        return null;
    }

    public User findUserBySecret(String secret) throws SQLException
    {
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("SELECT * FROM account WHERE secret=?"))
        {
            pstm.setString(1, secret);

            try (ResultSet rset = pstm.executeQuery())
            {
                if (rset.next())
                {
                    User user = new User();
                    user.setId(rset.getInt("id"));
                    user.setLogin(rset.getString("login"));
                    user.setPassword(rset.getString("password"));
                    user.setSecret(rset.getString("secret"));
                    return user;
                }
            }
        }
        return null;
    }

    public String findLoginById(int id) throws SQLException
    {
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("SELECT login FROM account WHERE id=?"))
        {
            pstm.setInt(1, id);
            try (ResultSet rset = pstm.executeQuery())
            {
                if (rset.next())
                {
                    String login = rset.getString("login");
                    return login;
                }
            }
        }
        return null;
    }

    public void updateSecretKeyById(int id, String secretKey) throws SQLException
    {

        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("UPDATE account SET secret=? WHERE id=?"))
        {
            pstm.setString(1, secretKey);
            pstm.setInt(2, id);
            pstm.execute();
        }

    }

    public void updateUserPassword(String newPass, int id) throws SQLException
    {
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("UPDATE account SET password=? WHERE id=? "))
        {
            pstm.setString(1, newPass);
            pstm.setInt(2, id);
            pstm.execute();
        }
    }
}
