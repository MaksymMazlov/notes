package ua.notes.servlet.notes;

import ua.notes.domain.User;
import ua.notes.service.AutorizationSingletonService;
import ua.notes.service.NoteService;
import ua.notes.service.TemplateService;
import ua.notes.servlet.AbstractNoteServlet;
import ua.notes.utils.Utils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/notes/create")
public class CreateNoteServlet extends AbstractNoteServlet
{
    @Inject
    private TemplateService templateService;
    @Inject
    private NoteService noteService;
    @Inject
    private AutorizationSingletonService autorization;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        User user = currentUser(req);
        if (user == null)
        {
            resp.sendRedirect("/");
            return;
        }
        String html = templateService.getHtmlByName("CreateNotes.html");
        Utils.writerResponse(resp, html);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        User user = requireCurrentUser(req);

        String title = req.getParameter("title");
        String text = req.getParameter("text");
        String checkExpired = req.getParameter("checkExpired");

        if (checkExpired == null)
        {
            noteService.createdNote(title, text, user.getId());
        }
        else
        {
            noteService.createdExpiredNote(title, text, user.getId());
        }
        String html = templateService.getHtmlByName("Created.html");
        Utils.writerResponse(resp, html);
    }
}