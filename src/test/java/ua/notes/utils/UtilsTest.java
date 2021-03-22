package ua.notes.utils;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest
{

    @Test
    public void md5Apache()
    {
        String actual = Utils.md5Apache("passwordOLOLO");
        String expected = "81d0f91e1a3da0448800afcb30ad1bde";

        Assert.assertEquals(expected,actual);
    }
}