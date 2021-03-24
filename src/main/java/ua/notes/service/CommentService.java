package ua.notes.service;

import ua.notes.dao.CommentDao;
import ua.notes.domain.Comment;

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
public class CommentService
{
    @Inject
    private CommentDao commentDao;
    @Inject
    private TemplateService templateService;

    public String printComments(int userId, int noteId)
    {
        try
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            List<Comment> commentList = commentDao.getComments(userId, noteId);
            if (commentList.isEmpty())
            {
                return "Нет комментариев";
            }
            String allComm = "";
            for (Comment el : commentList)
            {
                Map<String, String> data = new HashMap<>();
                data.put("text", el.getText());
                data.put("created", el.getCreated().format(formatter));

                allComm+= templateService.render("component/CommentItem.html", data);
            }
            return allComm;
        }
        catch (SQLException | IOException e)
        {
            e.printStackTrace();
        }
        return "Произошла ошибка";
    }


    public void createComment(String text, int userId, int noteId)
    {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setIdUser(userId);
        comment.setIdNote(noteId);
        comment.setCreated(LocalDateTime.now());
        try
        {
            commentDao.createCommen(comment);
        }
        catch (SQLException e)
        {
            System.out.println("Ошибка. Заметка не создана :" + e.getLocalizedMessage());
        }
    }
}
