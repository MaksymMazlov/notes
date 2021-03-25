package ua.notes.service.tasks;

import ua.notes.dao.NotesDao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class RemoveExpiredNotesTask implements Runnable
{
    private static final Logger LOGGER = Logger.getLogger(RemoveExpiredNotesTask.class.getName());
    @Inject
    private NotesDao notesDao;

    @Override
    public void run()
    {
        LOGGER.log(Level.INFO,"Expired notes will be DELETED");
        notesDao.removeExpired();
    }
}
