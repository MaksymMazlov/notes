package ua.notes.service;

import ua.notes.dao.UserDao;
import ua.notes.domain.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.SQLException;

@ApplicationScoped
public class AutorizationSingletonService
{
    @Inject
    private UserDao userDao;


    public User getCurrentUser(String secretKey)
    {
        if (secretKey == null)
        {
            return null;
        }

        try
        {
            return userDao.findUserBySecret(secretKey);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }


}
