package ua.notes.config;

import ua.notes.service.AsyncService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

@ApplicationScoped
public class OnApplicationStart implements ServletContextListener
{
    @Inject
    private AsyncService asyncService;
    @Inject
    private DataSourceConfig dataSourceConfig;
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        System.out.println("Приложение запустилось...");
        System.out.println("Регистрируем драйвер к БД....");

        try
        {
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("Драйвер успешно зарегистрирован!");

        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Драйвер НЕ НАЙДЕН!!!!");
            e.printStackTrace();
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
