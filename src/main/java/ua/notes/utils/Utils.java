package ua.notes.utils;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Utils
{

    public static final String SECRET_KEY = "secretKey";

    public static String getSecretKeyFromRequest(HttpServletRequest request)
    {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
        {
            return null;
        }
        for (Cookie cookie : cookies)
        {
            if (cookie.getName().equals(SECRET_KEY))
            {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static void writerResponse(HttpServletResponse resp, String data) throws IOException
    {
        PrintWriter writer = resp.getWriter();
        writer.println(data);
        writer.flush();
    }

    public static String md5Apache(String st) {
        String md5Hex = DigestUtils.md5Hex(st);
        return md5Hex;
    }
}