package ua.notes.service.tasks;

import ua.notes.dao.NotesDao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RemoveArchivedNotesTask implements Runnable
{
    @Inject
    private NotesDao notesDao;

    @Override
    public void run()
    {
        notesDao.removeArchived();
        System.out.println("Archived notes will be DELETED");
    }
}