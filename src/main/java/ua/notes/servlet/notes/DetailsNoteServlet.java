package ua.notes.servlet.notes;

import ua.notes.domain.User;
import ua.notes.service.CommentService;
import ua.notes.service.MainMenuService;
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

@WebServlet("/notes/details")
public class DetailsNoteServlet extends AbstractNoteServlet
{
    @Inject
    private TemplateService templateService;
    @Inject
    private NoteService noteService;
    @Inject
    private MainMenuService menuService;
    @Inject
    private CommentService commentService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        User user = currentUser(req);
        if (user == null)
        {
            resp.sendRedirect("/");
            return;
        }
        int id = Integer.parseInt(req.getParameter("id"));

        String notes = templateService.getHtmlByName("ShowNoteDetail.html");
        notes = notes.replace("{main_menu}", menuService.showMenu(user));
        notes = notes.replace("{show_note}", noteService.printOneNote(user.getId(), id));
        notes = notes.replace("{comments}", commentService.printComments(user.getId(), id));
        Utils.writerResponse(resp, notes);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        User user = currentUser(req);
        if (user == null)
        {
            resp.sendRedirect("/");
            return;
        }
        int id = Integer.parseInt(req.getParameter("id"));
        String text = req.getParameter("text");
        commentService.createComment(text, user.getId(), id);
        resp.sendRedirect("/notes/details?id="+id);
    }
}