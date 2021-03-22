package ua.notes.servlet;

import ua.notes.domain.User;
import ua.notes.exception.AccessDenyException;
import ua.notes.service.AutorizationSingletonService;
import ua.notes.utils.Utils;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public abstract class AbstractNoteServlet extends HttpServlet
{
    @Inject
    private AutorizationSingletonService autorizationSingletonService;

    public User currentUser(HttpServletRequest request)
    {
        String sercretKey = Utils.getSecretKeyFromRequest(request);
        return autorizationSingletonService.getCurrentUser(sercretKey);
    }

    public User requireCurrentUser(HttpServletRequest request)
    {
        User user = currentUser(request);
        if (user == null)
        {
            throw new AccessDenyException("401, Ошибка авторизации");
        }

        String sercretKey = Utils.getSecretKeyFromRequest(request);
        return autorizationSingletonService.getCurrentUser(sercretKey);
    }
}
