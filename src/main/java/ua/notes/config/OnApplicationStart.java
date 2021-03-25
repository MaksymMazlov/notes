package ua.notes.config;

import ua.notes.service.AsyncService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@ApplicationScoped
public class OnApplicationStart implements ServletContextListener
{
    private static final Logger LOGGER = Logger.getLogger(OnApplicationStart.class.getName());
    @Inject
    private AsyncService asyncService;
    @Inject
    private DataSourceConfig dataSourceConfig;
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        try
        {
            LogManager.getLogManager().readConfiguration(OnApplicationStart.class.getResourceAsStream("/logging.properties"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        LOGGER.log(Level.INFO,"Приложение запустилось...");
        LOGGER.log(Level.INFO,"Регистрируем драйвер к БД....");

        try
        {
            Class.forName("org.mariadb.jdbc.Driver");
            LOGGER.log(Level.INFO,"Драйвер успешно зарегистрирован!");
        }
        catch (ClassNotFoundException e)
        {
            LOGGER.log(Level.SEVERE,"Драйвер НЕ НАЙДЕН!!!!",e);
        }
        asyncService.scheduleRemoveExpTask();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
        try
        {
            asyncService.shutdown();
            dataSourceConfig.dataSource().close();

        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

}
