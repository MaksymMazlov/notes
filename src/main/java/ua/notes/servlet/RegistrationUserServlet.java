package ua.notes.servlet;

import ua.notes.service.TemplateService;
import ua.notes.service.UserService;
import ua.notes.utils.Utils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationUserServlet extends HttpServlet
{
    @Inject
    private TemplateService templateService;
    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String html = templateService.getHtmlByName("Registration.html");
        Utils.writerResponse(resp, html);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String nameUser = req.getParameter("name");
        String passwordUser = req.getParameter("pass");
        userService.createUser(nameUser, passwordUser);
        resp.sendRedirect("/");
    }
}
