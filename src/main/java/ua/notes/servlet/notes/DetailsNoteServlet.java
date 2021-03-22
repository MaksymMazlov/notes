package ua.notes.servlet.notes;

import ua.notes.domain.User;
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

        notes=notes.replace("{show_note}",noteService.printOneNote(user.getId(),id));


        Utils.writerResponse(resp, notes);
    }
}
