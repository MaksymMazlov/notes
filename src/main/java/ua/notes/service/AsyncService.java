package ua.notes.service;

import ua.notes.service.tasks.RemoveExpiredNotesTask;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@ApplicationScoped
public class AsyncService
{
    @Inject
    private RemoveExpiredNotesTask removeExpiredNotesTask;
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public void scheduleRemoveExpTask()
    {
        System.out.println("Task scheduled");
        scheduledExecutorService.scheduleAtFixedRate(removeExpiredNotesTask, 1, 1, TimeUnit.MINUTES);

    }
    public void shutdown(){
        scheduledExecutorService.shutdown();
    }
}
