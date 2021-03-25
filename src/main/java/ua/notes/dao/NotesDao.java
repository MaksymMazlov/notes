package ua.notes.dao;

import ua.notes.domain.Notes;

import javax.enterprise.context.ApplicationScoped;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class NotesDao extends AbstractDao
{

    public void createNote(Notes note) throws SQLException
    {
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("INSERT into notes (title,content,created,user_id) values (?,?,?,?)"))
        {
            pstm.setString(1, note.getTitle());
            pstm.setString(2, note.getContent());
            Timestamp timestamp = Timestamp.valueOf(note.getCreated());
            pstm.setTimestamp(3, timestamp);
            pstm.setInt(4, note.getUserId());
            pstm.execute();
        }
    }

    public void createExpiredNote(Notes note) throws SQLException
    {
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("INSERT into notes (title,content,created,user_id,expired) values (?,?,?,?,?)"))
        {
            pstm.setString(1, note.getTitle());
            pstm.setString(2, note.getContent());
            Timestamp timestamp = Timestamp.valueOf(note.getCreated());
            pstm.setTimestamp(3, timestamp);
            pstm.setInt(4, note.getUserId());
            Timestamp timestampForExp = Timestamp.valueOf(note.getExpired());
            pstm.setTimestamp(5, timestampForExp);
            pstm.execute();
        }
    }

    public List<Notes> showNotes(int userId, int limitPage, int offsetPage) throws SQLException
    {
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("SELECT * FROM notes  WHERE user_id=? ORDER BY created Desc LIMIT ? OFFSET ? "))
        {
            pstm.setInt(1, userId);
            pstm.setInt(2, limitPage);
            pstm.setInt(3, offsetPage);
            try (ResultSet rset = pstm.executeQuery())
            {
                List<Notes> notesList = new ArrayList<>();
                while (rset.next())
                {
                    Notes notes = new Notes();
                    notes.setId(rset.getInt("id"));
                    notes.setTitle(rset.getString("title"));
                    notes.setContent(rset.getString("content"));
                    notes.setCreated(rset.getTimestamp("created").toLocalDateTime());
                    notes.setUserId(rset.getInt("user_id"));
                    notes.setArchived(rset.getBoolean("archived"));
                    notesList.add(notes);
                }
                return notesList;
            }
        }
    }

    public Notes showNotesById(int userId, int idNote) throws SQLException
    {
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("SELECT * FROM notes  WHERE user_id=? and id=?"))
        {
            pstm.setInt(1, userId);
            pstm.setInt(2, idNote);
            try (ResultSet rset = pstm.executeQuery())
            {
                if (rset.next())
                {
                    Notes note = new Notes();
                    note.setId(rset.getInt("id"));
                    note.setTitle(rset.getString("title"));
                    note.setContent(rset.getString("content"));
                    note.setCreated(rset.getTimestamp("created").toLocalDateTime());
                    note.setUserId(rset.getInt("user_id"));
                    note.setArchived(rset.getBoolean("archived"));
                    return note;
                }
                return null;
            }
        }
    }

    public int getCountNotesByUserId(int userId) throws SQLException
    {
        int count = -1;
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("SELECT COUNT(id) from notes where user_id=?"))
        {
            pstm.setInt(1, userId);
            try (ResultSet rset = pstm.executeQuery())
            {
                if (rset.next())
                {
                    count = rset.getInt("COUNT(id)");
                }
            }
        }
        return count;
    }

    public int getCountSearchNotesByUserId(String searchReq, int userId) throws SQLException
    {
        int count = -1;
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("SELECT COUNT(id) from notes where title like ? and user_id=? "))
        {
            pstm.setString(1, "%" + searchReq + "%");
            pstm.setInt(2, userId);
            try (ResultSet rset = pstm.executeQuery())
            {
                if (rset.next())
                {
                    count = rset.getInt("COUNT(id)");
                }
            }
        }
        return count;
    }

    public List<Notes> searchNotes(String searchReq, int userId, int limitPage, int offsetPage) throws SQLException
    {
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("select * from notes where title like ? and user_id=? ORDER BY created Desc LIMIT ? OFFSET ? "))
        {
            pstm.setString(1, "%" + searchReq + "%");
            pstm.setInt(2, userId);
            pstm.setInt(3, limitPage);
            pstm.setInt(4, offsetPage);
            try (ResultSet rset = pstm.executeQuery())
            {
                List<Notes> notesList = new ArrayList<>();
                while (rset.next())
                {
                    Notes notes = new Notes();
                    notes.setId(rset.getInt("id"));
                    notes.setTitle(rset.getString("title"));
                    notes.setContent(rset.getString("content"));
                    notes.setCreated(rset.getTimestamp("created").toLocalDateTime());
                    notes.setUserId(rset.getInt("user_id"));
                    notesList.add(notes);
                }
                return notesList;
            }
        }
    }

    public void deleteNoteById(int id, int userId) throws SQLException
    {
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("DELETE FROM notes WHERE id=? AND user_id=?"))
        {
            pstm.setInt(1, id);
            pstm.setInt(2, userId);
            pstm.execute();
        }
    }

    public void archiveNoteById(int id, int userId) throws SQLException
    {
        try (Connection con = getConnection();
             PreparedStatement pstm = con.prepareStatement("UPDATE notes SET archived=true,archivedDateTime=now() WHERE id=? AND user_id=?"))
        {
            pstm.setInt(1, id);
            pstm.setInt(2, userId);
            pstm.execute();
        }
    }

    public void removeExpired()
    {
        try (Connection con = getConnection();
             Statement stm = con.createStatement())
        {
            stm.executeUpdate("delete from notes where expired < now() ");
        }
        catch (SQLException e)
        {
            System.out.println("Error delete");
            e.printStackTrace();
        }
    }

    public void removeArchived()
    {
        try (Connection con = getConnection();
             Statement stm = con.createStatement())
        {
            stm.executeUpdate("DELETE FROM notes WHERE date_add(archivedDateTime,INTERVAL 30 day)<now()");
        }
        catch (SQLException e)
        {
            System.out.println("Error delete Archived notes ");
            e.printStackTrace();
        }
    }
}