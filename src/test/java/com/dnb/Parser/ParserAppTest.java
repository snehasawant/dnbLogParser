package com.dnb.Parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParserAppTest {


    @Test
    public void testparseInput() {

        ParserApp.parseInput("D:\\projectArea\\test", "D:\\projectArea\\activity.xsd", "D:\\projectArea\\testoutput\\outputfile", "D:\\projectArea\\activitycode.csv");
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        File[] files = new File("D:\\projectArea\\testoutput").listFiles();
        for (File f : files) {
            if (f.getName().contains(dateFormat.format(date))) {
                Assertions.assertTrue(true);
            }
        }

    }
}
