package ua.notes.service;

import ua.notes.dao.CommentDao;
import ua.notes.dao.NotesDao;
import ua.notes.dao.UserDao;
import ua.notes.domain.Notes;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class NoteService
{
    public static final int PAGE_SIZE = 5;
    @Inject
    private NotesDao notesDao;
    @Inject
    private UserDao userDao;
    @Inject
    private TemplateService templateService;
    @Inject
    private CommentDao commentDao;

    public void createdNote(String title, String text, int userId)
    {
        Notes note = new Notes();
        note.setTitle(title);
        note.setContent(text);
        note.setCreated(LocalDateTime.now());
        note.setUserId(userId);
        try
        {
            notesDao.createNote(note);
        }
        catch (SQLException e)
        {
            System.out.println("Ошибка. Заметка не создана :" + e.getLocalizedMessage());
        }
    }

    public void createdExpiredNote(String title, String text, int userId)
    {
        Notes note = new Notes();
        note.setTitle(title);
        note.setContent(text);
        note.setCreated(LocalDateTime.now());
        note.setUserId(userId);
        note.setExpired(LocalDateTime.now().plusHours(12));

        try
        {
            notesDao.createExpiredNote(note);
        }
        catch (SQLException e)
        {
            System.out.println("Ошибка. Заметка не создана :" + e.getLocalizedMessage());
        }
    }

    public String printAllNotes(int userId, int page)
    {
        try
        {
            int offset = (page - 1) * PAGE_SIZE;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            List<Notes> notes = notesDao.showNotes(userId, PAGE_SIZE, offset);
            String result = "";
            for (Notes note : notes)
            {
                String login = userDao.findLoginById(note.getUserId());
                Map<String, String> data = new HashMap<>();
                data.put("id", String.valueOf(note.getId()));
                data.put("login", login);
                data.put("date", note.getCreated().format(formatter));
                data.put("archived", note.getArchived() ? "<span class=\"badge badge-secondary\">архивировано</span> " : "");
                data.put("title", note.getTitle());
                String content = note.getContent();
                int endIndex = Math.min(300, content.length());
                data.put("content", content.substring(0, endIndex));

                result += templateService.render("NoteItem.html", data);
            }
            return result;
        }
        catch (SQLException | IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String printAllNotesBySearch(String searchReq, int userId, int page)
    {
        try
        {
            int offset = (page - 1) * PAGE_SIZE;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            List<Notes> notes = notesDao.searchNotes(searchReq, userId, PAGE_SIZE, offset);
            String result = "";
            for (Notes note : notes)
            {
                String login = userDao.findLoginById(note.getUserId());
                Map<String, String> data = new HashMap<>();
                data.put("id", String.valueOf(note.getId()));
                data.put("login", login);
                data.put("date", note.getCreated().format(formatter));
                data.put("archived", note.getArchived() ? "<span class=\"badge badge-secondary\">архивировано</span> " : "");
                data.put("title", note.getTitle());
                String content = note.getContent();
                int endIndex = Math.min(300, content.length());
                data.put("content", content.substring(0, endIndex));

                result += templateService.render("NoteItem.html", data);
            }
            return result;
        }
        catch (SQLException | IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String printOneNote(int userId, int idNote)
    {
        try
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Notes note = notesDao.showNotesById(userId, idNote);
            String login = userDao.findLoginById(note.getUserId());

            Map<String, String> data = new HashMap<>();
            data.put("id", String.valueOf(note.getId()));
            data.put("login", login);
            data.put("date", note.getCreated().format(formatter));
            data.put("archived", note.getArchived() ? "<span class=\"badge badge-secondary\">архивировано</span> " : "");
            data.put("title", note.getTitle());
            data.put("content", note.getContent());

            return templateService.render("NoteItemDetails.html", data);
        }
        catch (SQLException | IOException throwables)
        {
            throwables.printStackTrace();
        }
        return null;
    }

    public void deleteNoteById(int id, int userId)
    {
        try
        {
            commentDao.deleteCommentsByNoteId(id, userId);
            notesDao.deleteNoteById(id, userId);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void archiveNoteById(int id, int userId)
    {
        try
        {
            notesDao.archiveNoteById(id, userId);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int getCountPages(int userId)
    {
        int pagesCount = 0;
        int pagesTotal = 0;
        try
        {
            pagesTotal = notesDao.getCountNotesByUserId(userId);
            pagesCount = pagesTotal / PAGE_SIZE + (pagesTotal % PAGE_SIZE > 0 ? 1 : 0);
            return pagesCount;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return pagesCount;
    }

    public int getCountSearchPages(String searchReq, int userId)
    {
        int pagesCount = 0;
        int pagesTotal = 0;
        try
        {
            pagesTotal = notesDao.getCountSearchNotesByUserId(searchReq, userId);
            pagesCount = pagesTotal / PAGE_SIZE + (pagesTotal % PAGE_SIZE > 0 ? 1 : 0);
            return pagesCount;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return pagesCount;
    }

}
