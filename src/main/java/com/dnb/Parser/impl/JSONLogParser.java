package com.dnb.Parser.impl;

import com.dnb.Parser.LogParser;
import com.dnb.Parser.dto.LogResponse;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;

/*
* This class implements the LogParser interface to provide the implementation of Json file parsing
* */
public class JSONLogParser implements LogParser {


    @Override
    public List<LogResponse> parseInput(SparkSession spark, String inputFilePath, String xsdPath, String csvPath) {
        JSONParser jsonParser = new JSONParser();
        List<LogResponse> logResponseList = new ArrayList<>();

        try {
            Dataset<Row> activityDataframe = spark.read().json(inputFilePath); //read Json file
            activityDataframe.printSchema();
            activityDataframe.select(activityDataframe.col("activity.userName"), activityDataframe.col("activity.websiteName"),
                    activityDataframe.col("activity.activityTypeDescription"), activityDataframe.col("activity.signedInTime")).
                    collectAsList().forEach(row -> {  //traverse through the dataset to populate the response object
                LogResponse logResponse = new LogResponse();
                logResponse.setUser(row.getAs("userName").toString());
                logResponse.setWebsite(row.getAs("websiteName").toString());
                logResponse.setActivityTypeDescription(row.getAs("activityTypeDescription").toString());
                logResponse.setSignedInTime(row.getAs("signedInTime").toString());
                logResponseList.add(logResponse);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return logResponseList;
    }
}
