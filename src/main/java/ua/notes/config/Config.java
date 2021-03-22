package ua.notes.config;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@FacesConfig
@ApplicationScoped
public class Config
{
    private String dbUrl;
    private String dbLogin;
    private String dbPassword;

    @PostConstruct
    public void load()
    {
        System.out.println("Loading configs..........");

        Properties properties = new Properties();

        try (InputStream is = Config.class.getResourceAsStream("/config.properties"))
        {
            properties.load(is);

            dbUrl = properties.getProperty("JDBC_URL");
            dbLogin = properties.getProperty("JDBC_LOGIN");
            dbPassword = properties.getProperty("JDBC_PASSWORD");

            System.out.println("Configs loaded!");
        }
        catch (IOException e)
        {
            System.out.println("ERROR: Config loading FAILED");
        }
    }

    public String getDbUrl()
    {
        return dbUrl;
    }

    public String getDbLogin()
    {
        return dbLogin;
    }

    public String getDbPassword()
    {
        return dbPassword;
    }

}
