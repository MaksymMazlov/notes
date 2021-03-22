package ua.notes.service;

import ua.notes.domain.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class MainMenuService
{
    @Inject
    private UserService userService;
    @Inject
    private NoteService noteService;
    @Inject
    private AutorizationSingletonService autorizationSingletonService;
    @Inject
    private TemplateService templateService;


    public String showMenu(User user)
    {
        try
        {
            if (user == null)
            {
                return templateService.getHtmlByName("component/MenuNotAuth.html");
            }
            else
            {
                return templateService.getHtmlByName("component/MenuAuth.html");
            }
        }
        catch (IOException ex){
            return "Ошибка чтения шаблона:"+ ex.getMessage();
        }
    }
}