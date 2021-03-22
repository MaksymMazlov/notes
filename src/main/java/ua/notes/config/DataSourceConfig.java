package ua.notes.config;

import org.mariadb.jdbc.MariaDbPoolDataSource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.SQLException;

@ApplicationScoped
public class DataSourceConfig
{
    @Inject
    private Config config;

    private MariaDbPoolDataSource ds;

    public synchronized MariaDbPoolDataSource dataSource() throws SQLException
    {
        if (ds == null)
        {
            MariaDbPoolDataSource dataSource = new MariaDbPoolDataSource();
            dataSource.setUrl(config.getDbUrl());
            dataSource.setUser(config.getDbLogin());
            dataSource.setPassword(config.getDbPassword());

            dataSource.setMinPoolSize(5);    // минимальное кол-во соединений к БД
            dataSource.setMaxPoolSize(10);   // максимальное кол-во соединений к БД
            dataSource.setMaxIdleTime(60);    // время простоя неактивных подключений


            ds = dataSource;
        }

        return ds;
    }

}
