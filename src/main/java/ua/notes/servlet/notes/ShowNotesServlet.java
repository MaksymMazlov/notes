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

@WebServlet("/notes/show")
public class ShowNotesServlet extends AbstractNoteServlet
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
        String currentPageStr = req.getParameter("page");
        int currentPage = currentPageStr == null ? 1 : Integer.parseInt(currentPageStr);
        String notes = templateService.getHtmlByName("ShowNotes.html");
        notes = notes.replace("{main_menu}", menuService.showMenu(user));
        notes = notes.replace("{show_notes}", noteService.printAllNotes(user.getId(),currentPage));
        String pages = "";
        for (int i = 1; i <= noteService.getCountPages(user.getId()); i++)
        {
            pages += " <li class=\"page-item\"><a class=\"page-link\" href=\"/notes/show?page=" + i + "\">" + i + "</a></li>";
        }
        notes = notes.replace("{show_page}", pages);
        Utils.writerResponse(resp, notes);
    }
}
