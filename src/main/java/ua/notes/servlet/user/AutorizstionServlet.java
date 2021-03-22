package ua.notes.servlet.user;

import ua.notes.exception.InvalidPasswordException;
import ua.notes.service.MainMenuService;
import ua.notes.service.TemplateService;
import ua.notes.service.UserService;
import ua.notes.servlet.AbstractNoteServlet;
import ua.notes.utils.Utils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/autorization")
public class AutorizstionServlet extends AbstractNoteServlet
{
    @Inject
    private TemplateService templateService;
    @Inject
    private MainMenuService menu;
    @Inject
    private UserService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

        String s = templateService.getHtmlByName("Autorization.html");
        s = s.replace("{main_menu}", menu.showMenu(null));
        s = s.replace("{error}", "");
        Utils.writerResponse(resp, s);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String name = req.getParameter("name");
        String pass = req.getParameter("pass");
        String md5Pass = Utils.md5Apache(pass);

        String secret = null;
        try
        {
            secret = service.authorization(name, md5Pass);
            resp.addCookie(new Cookie(Utils.SECRET_KEY, secret));
            resp.sendRedirect("/");
        }
        catch (InvalidPasswordException e)
        {
            String s = templateService.getHtmlByName("Autorization.html");
            s = s.replace("{main_menu}", menu.showMenu(null));
            s = s.replace("{error}", "<div class='alert alert-danger'>"+e.getMessage()+"</div>");
            Utils.writerResponse(resp, s);
        }
    }

}
