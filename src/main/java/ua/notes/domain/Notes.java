package ua.notes.domain;

import java.time.LocalDateTime;

public class Notes
{
    int id;
    String title;
    String content;
    LocalDateTime created;
    LocalDateTime expired;
    int userId;
    boolean archived;
    LocalDateTime archivedDateTime;

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public LocalDateTime getCreated()
    {
        return created;
    }

    public LocalDateTime getExpired()
    {
        return expired;
    }

    public void setExpired(LocalDateTime expired)
    {
        this.expired = expired;
    }

    public void setCreated(LocalDateTime created)
    {
        this.created = created;
    }

    public boolean getArchived()
    {
        return archived;
    }

    public void setArchived(boolean archived)
    {
        this.archived = archived;
    }

    public LocalDateTime getArchivedDateTime()
    {
        return archivedDateTime;
    }

    public void setArchivedDateTime(LocalDateTime archivedDateTime)
    {
        this.archivedDateTime = archivedDateTime;
    }
}
