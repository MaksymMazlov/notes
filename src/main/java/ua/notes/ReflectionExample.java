package ua.notes;

import ua.notes.domain.Notes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionExample
{
    public static void main(String[] args)
    {
        Notes notes = new Notes();
        notes.setTitle("title");
        notes.setContent("asdsad");

        Class clazz = Notes.class;
        Method[] methods = clazz.getMethods();
        System.out.println(Arrays.toString(methods));
        StringBuilder stringBuilder = new StringBuilder("{");

        for (Method elem : methods)
        {
            String methodName = elem.getName();
            if (methodName.startsWith("get"))
            {
                if (methodName.equals("getClass")){
                    continue;
                }

                String field = methodName.substring(3);
                try
                {
                    Object result = elem.invoke(notes);
                    stringBuilder.append('"').append(field).append('"')
                            .append(':')
                            .append('"').append(result).append('"').append(',');

                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
                catch (InvocationTargetException e)
                {
                    e.printStackTrace();
                }
            }

        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("}");
        System.out.println(stringBuilder);
    }
}
