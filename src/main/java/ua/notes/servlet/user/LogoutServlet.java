package ua.notes.servlet.user;

import ua.notes.domain.User;
import ua.notes.service.AutorizationSingletonService;
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

@WebServlet("/logout")
public class LogoutServlet extends AbstractNoteServlet
{
    @Inject
    AutorizationSingletonService autorization;
    @Inject
    UserService service;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        User user = requireCurrentUser(request);
        service.logoutUser(user.getId());

        Cookie cookie = new Cookie(Utils.SECRET_KEY, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("/");
    }
}
