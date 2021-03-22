package ua.notes.servlet;

import ua.notes.domain.User;
import ua.notes.service.MainMenuService;
import ua.notes.service.TemplateService;
import ua.notes.utils.Utils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class IndexServlet extends AbstractNoteServlet
{
    @Inject
    TemplateService templateService;
    @Inject
    MainMenuService menuService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        User user = currentUser(req);

        String index = templateService.getHtmlByName("Menu.html");
        index = index.replace("{main_menu}", menuService.showMenu(user));
        Utils.writerResponse(resp, index);
    }
}
