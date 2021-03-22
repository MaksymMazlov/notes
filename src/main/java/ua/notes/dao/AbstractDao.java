package ua.notes.dao;

import ua.notes.config.DataSourceConfig;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDao
{
    @Inject
    private DataSourceConfig dataSourceConfig;

    protected Connection getConnection() throws SQLException
    {
        return dataSourceConfig.dataSource().getConnection();
    }

    protected void close(Connection connection) throws SQLException
    {
        if (connection != null)
        {
            connection.close();
        }
    }

    protected void close(Statement statement) throws SQLException
    {
        if (statement != null)
        {
            statement.close();
        }
    }

    protected void close(ResultSet resultSet) throws SQLException
    {
        if (resultSet != null)
        {
            resultSet.close();
        }
    }

    protected void close(Connection connection, Statement statement) throws SQLException
    {
        close(statement);
        close(connection);
    }

    protected void close(Connection connection, Statement statement, ResultSet resultSet) throws SQLException
    {
        close(resultSet);
        close(statement);
        close(connection);
    }

}
