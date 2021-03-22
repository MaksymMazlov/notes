package ua.notes.service.tasks;

import ua.notes.dao.NotesDao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RemoveExpiredNotesTask implements Runnable
{
    @Inject
    private NotesDao notesDao;

    @Override
    public void run()
    {
        System.out.println("Expired notes will be DELETED");
        notesDao.removeExpired();
    }
}
