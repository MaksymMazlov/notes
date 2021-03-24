package ua.notes.domain;

import java.time.LocalDateTime;

public class Comment
{
    private int id;
    private String text;
    private int idUser;
    private int idNote;
    private LocalDateTime created;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public int getIdUser()
    {
        return idUser;
    }

    public void setIdUser(int idUser)
    {
        this.idUser = idUser;
    }

    public int getIdNote()
    {
        return idNote;
    }

    public void setIdNote(int idNote)
    {
        this.idNote = idNote;
    }

    public LocalDateTime getCreated()
    {
        return created;
    }

    public void setCreated(LocalDateTime created)
    {
        this.created = created;
    }
}
