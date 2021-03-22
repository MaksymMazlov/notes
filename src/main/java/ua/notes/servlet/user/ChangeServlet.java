package ua.notes.servlet.user;

import ua.notes.domain.User;
import ua.notes.exception.InvalidPasswordException;
import ua.notes.service.AutorizationSingletonService;
import ua.notes.service.MainMenuService;
import ua.notes.service.TemplateService;
import ua.notes.service.UserService;
import ua.notes.servlet.AbstractNoteServlet;
import ua.notes.utils.Utils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/change")
public class ChangeServlet extends AbstractNoteServlet
{
    @Inject
    private TemplateService templateService;
    @Inject
    private MainMenuService menu;
    @Inject
    private UserService service;
    @Inject
    private AutorizationSingletonService autorizationSingletonService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        User user = requireCurrentUser(req);

        String s = templateService.getHtmlByName("ChangePassword.html");
        s = s.replace("{main_menu}", menu.showMenu(user));
        s = s.replace("{error}", "");
        Utils.writerResponse(resp, s);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String pass = req.getParameter("pass");
        String newPass = req.getParameter("newPass");

        String md5Pass = Utils.md5Apache(pass);
        String md5newPass = Utils.md5Apache(newPass);

        User user = requireCurrentUser(req);

        try
        {
            service.changePassword(md5Pass, md5newPass,user);
            String s = templateService.getHtmlByName("ChangePassword.html");
            s = s.replace("{main_menu}", menu.showMenu(user));
            s = s.replace("{error}", "Данные успешно изменены");
            Utils.writerResponse(resp, s);
        }
        catch (InvalidPasswordException e)
        {
            String s = templateService.getHtmlByName("ChangePassword.html");
            s = s.replace("{main_menu}", menu.showMenu(user));
            s = s.replace("{error}", "АХУТНГ!!!!!Не верный пароль");
            Utils.writerResponse(resp, s);
        }
    }

}
