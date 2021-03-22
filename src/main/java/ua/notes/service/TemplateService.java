package ua.notes.service;

import javax.enterprise.context.ApplicationScoped;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

@ApplicationScoped
public class TemplateService
{

    public String getHtmlByName(String name) throws IOException
    {
        String resourcePath = "/pages/" + name;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(TemplateService.class.getResourceAsStream(resourcePath))))
        {

            String str;
            StringBuilder strReturn = new StringBuilder();

            while ((str = reader.readLine()) != null)
            {
                strReturn.append(str);
            }
            return strReturn.toString();

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("Ошибка загрузки страници шаблона");
        }
        return null;
    }
}
