package ua.notes.servlet.notes;

import ua.notes.domain.User;
import ua.notes.service.AutorizationSingletonService;
import ua.notes.service.NoteService;
import ua.notes.servlet.AbstractNoteServlet;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/notes/archive")
public class ArchiveNoteServlet extends AbstractNoteServlet
{
    @Inject
    private NoteService noteService;
    @Inject
    private AutorizationSingletonService autorization;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        User user = currentUser(request);
        if (user == null)
        {
            response.sendRedirect("/");
            return;
        }
        int id = Integer.parseInt(request.getParameter("id"));
        noteService.archiveNoteById(id, user.getId());
        response.sendRedirect("/notes/show");
    }
}
