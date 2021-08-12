package com.dnb.Parser;

import com.dnb.Parser.dto.LogResponse;
import org.apache.spark.sql.SparkSession;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParserApp {


    /*
    * This class is the parser App used to do the initialization of spark session, based on the file input
    * the respective file parser would instantiate and parse the data and return a arraylist of a dto object
    * Finally the arrayList is written into a output file in JSON format.
    * */
    public static void parseInput(String inputPath, String xsdPath, String outputFilePath, String csvPath) {
        SparkSession spark = SparkSession
                .builder()
                .appName("Logger Application")
                .master("local")
                .getOrCreate();

        File inputFilePath = new File(inputPath);
        File[] inputFiles = inputFilePath.listFiles();

        List<List<LogResponse>> logResponseList = new ArrayList<>();
        String fileType = "";
        try {
            for (File file : inputFiles) {
                int index = file.getName().lastIndexOf("."); //Based on the
                fileType = index > 0 ? file.getName().substring(index + 1) : "";
                //Based on the file extension , respective class is invoked
                LogParser parser = (LogParser) Class.forName("com.dnb.Parser.impl." + fileType.toUpperCase() + "LogParser").getDeclaredConstructor().newInstance();
                logResponseList.add(parser.parseInput(spark, file.getPath(), xsdPath, csvPath));
            }
            Date date = new Date();
            FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath + "_" + date.getTime());
            BufferedWriter bufferdWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));


            logResponseList.forEach(logResponse -> {
                logResponse.forEach(logResponse1 -> {
                    try {
                        bufferdWriter.write(logResponse1.toString() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            });

            bufferdWriter.close();
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        parseInput(args[0], args[1], args[2], args[3]);
    }
}
