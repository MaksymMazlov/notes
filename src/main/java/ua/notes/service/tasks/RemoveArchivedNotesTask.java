package ua.notes.service.tasks;

import ua.notes.dao.NotesDao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class RemoveArchivedNotesTask implements Runnable
{
    private static final Logger LOGGER = Logger.getLogger(RemoveArchivedNotesTask.class.getName());
    @Inject
    private NotesDao notesDao;

    @Override
    public void run()
    {
        notesDao.removeArchived();
        LOGGER.log(Level.INFO,"Archived notes will be DELETED");
    }
}