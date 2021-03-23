package ua.notes.service;

import ua.notes.exception.TemplateException;

import javax.enterprise.context.ApplicationScoped;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

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

    public String render(String name, Map<String, String> data) throws IOException
    {
        String str = getHtmlByName(name);
        for (Map.Entry<String, String> element : data.entrySet())
        {
            String key = element.getKey();
            String value = element.getValue();
            String pattern = "{" + key + "}";

            if (str.contains(pattern))
            {
                str = str.replace(pattern, value);
            }
            else
            {
                throw new TemplateException("Variable not found: " + pattern + " in " + name);
            }
        }
        return str;
    }
}
