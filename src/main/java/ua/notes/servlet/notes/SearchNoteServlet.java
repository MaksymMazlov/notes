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
import java.net.URLEncoder;

@WebServlet("/notes/search")
public class SearchNoteServlet extends AbstractNoteServlet
{
    @Inject
    private TemplateService templateService;
    @Inject
    private MainMenuService menuService;
    @Inject
    private NoteService noteService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        User user = currentUser(req);
        if (user == null)
        {
            resp.sendRedirect("/");
            return;
        }

        String search_req = req.getParameter("search_req");

        String currentPageStr = req.getParameter("page");
        int currentPage = currentPageStr == null ? 1 : Integer.parseInt(currentPageStr);
        String notes = templateService.getHtmlByName("SearchResult.html");
        notes = notes.replace("{main_menu}", menuService.showMenu(user));
        notes = notes.replace("{show_notes}", noteService.printAllNotesBySearch(search_req, user.getId(), currentPage));
        String pages = "";
        String encodeRequestUtf8 = URLEncoder.encode(search_req, "utf-8");
        for (int i = 1; i <= noteService.getCountSearchPages(search_req, user.getId()); i++)
        {
            pages += " <li class=\"page-item\"><a class=\"page-link\" href=\"/notes/search?page=" + i + "&search_req=" + encodeRequestUtf8 + "\">" + i + "</a></li>";
        }
        notes = notes.replace("{show_page}", pages);
        Utils.writerResponse(resp, notes);
    }
}
