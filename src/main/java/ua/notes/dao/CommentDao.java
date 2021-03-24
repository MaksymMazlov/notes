package ua.notes.dao;

import ua.notes.domain.Comment;

import javax.enterprise.context.ApplicationScoped;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CommentDao extends AbstractDao
{
    public List<Comment> getComments(int idUser, int idNote) throws SQLException
    {
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("SELECT * FROM comments  WHERE id_user=? and id_note=?"))
        {
            pstm.setInt(1, idUser);
            pstm.setInt(2, idNote);
            try (ResultSet rset = pstm.executeQuery())
            {
                List<Comment> commentList = new ArrayList<>();
                while (rset.next())
                {
                    Comment comment = new Comment();
                    comment.setId(rset.getInt("id"));
                    comment.setText(rset.getString("text"));
                    comment.setCreated(rset.getTimestamp("created").toLocalDateTime());
                    commentList.add(comment);
                }
                return commentList;
            }
        }
    }


    public void createCommen(Comment comment) throws SQLException
    {
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("INSERT into comments (text,id_user,id_note,created) values (?,?,?,?)"))
        {
            pstm.setString(1, comment.getText());
            pstm.setInt(2, comment.getIdUser());
            pstm.setInt(3, comment.getIdNote());
            Timestamp timestamp = Timestamp.valueOf(comment.getCreated());
            pstm.setTimestamp(4, timestamp);
            pstm.execute();
        }
    }
    public void deleteCommentsById(int id, int userId) throws SQLException
    {
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("DELETE FROM comments WHERE id=? AND id_user=?"))
        {
            pstm.setInt(1, id);
            pstm.setInt(2, userId);
            pstm.execute();
        }
    }
    public void deleteCommentsByNoteId(int noteId, int userId) throws SQLException
    {
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("DELETE FROM comments WHERE id_note=? AND id_user=?"))
        {
            pstm.setInt(1, noteId);
            pstm.setInt(2, userId);
            pstm.execute();
        }
    }
}
