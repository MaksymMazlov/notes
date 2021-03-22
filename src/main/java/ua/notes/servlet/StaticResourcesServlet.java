package ua.notes.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

@WebServlet("/static/*")
public class StaticResourcesServlet extends HttpServlet
{

    public static final String ROOT_DIR = "C:\\Users\\Mazlov-Home\\IdeaProjects\\Notes\\src\\main\\resources";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String path = req.getRequestURI();
        File file = new File(ROOT_DIR, path);
        if (!file.exists())
        {
            resp.sendError(404, "Not found");
            return;
        }
        resp.addHeader("Content-Type", Files.probeContentType(file.toPath()));
        OutputStream outputStream = resp.getOutputStream();
        FileInputStream inputStream = new FileInputStream(file);
        int result;
        while ((result = inputStream.read()) != -1)
        {
            outputStream.write(result);
        }
        outputStream.flush();
    }
}
