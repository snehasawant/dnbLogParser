package com.dnb.Parser.impl;

import com.dnb.Parser.TestSuite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class XMLLogParserTest extends TestSuite {
    @Test
    public void testparseInput()
    {
        XMLLogParser xmlLogParser = new XMLLogParser();
       // Assertions.assertTrue(xmlLogParser.parseInput(spark,"D:\\projectArea\\test", "D:\\projectArea\\activity.xsd", "D:\\projectArea\\activitycode.csv").size()>0);
    }
}
