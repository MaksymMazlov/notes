package ua.notes.service;

import ua.notes.dao.UserDao;
import ua.notes.domain.User;
import ua.notes.exception.InvalidPasswordException;
import ua.notes.utils.Utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.UUID;

@ApplicationScoped
public class UserService
{
    @Inject
    private UserDao userDao;
    @Inject
    private AutorizationSingletonService autorizationSingletonService;

    public void createUser(String name, String pass)
    {
        User user = new User();
        user.setLogin(name);
        String md5Pass = Utils.md5Apache(pass);
        user.setPassword(md5Pass);

        try
        {
            userDao.insertUser(user);
        }
        catch (SQLException e)
        {
            System.out.println("Ошибка. Пользователь не создан :" + e.getLocalizedMessage());
        }
    }

    public String authorization(String name, String pass) throws InvalidPasswordException
    {

        String userName = name;
        String password = pass;

        try
        {
            User user = userDao.findUser(userName, password);
            if (user == null)
            {
                throw new InvalidPasswordException("Не правильный пароль или не верный логин");
            }
            else
            {
                UUID uuid = UUID.randomUUID();
                String secretKey = uuid.toString();
                userDao.updateSecretKeyById(user.getId(), secretKey);

                System.out.println("Успешная авторизация");
                return secretKey;
            }
        }
        catch (SQLException e)
        {
            System.out.println("ДБ ошибка");
            e.printStackTrace();
        }
        throw new IllegalStateException("Ошибка авторизации");
    }

    public void logoutUser(int userId)
    {
        try
        {
            userDao.updateSecretKeyById(userId, null);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void changePassword(String pass, String newPass, User user) throws InvalidPasswordException
    {
        try
        {
            String oldPass = user.getPassword();

            if (!pass.equals(oldPass))
            {
                System.out.println("Пароли не совпадают");
                throw new InvalidPasswordException("Не правильный пароль или не верный логин");
            }
            else
            {
                userDao.updateUserPassword(newPass,user.getId());
                System.out.println("Успешно изменено");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
