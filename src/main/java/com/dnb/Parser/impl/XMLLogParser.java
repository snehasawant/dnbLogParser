package com.dnb.Parser.impl;

import com.dnb.Parser.LogParser;
import com.dnb.Parser.dto.LogResponse;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

/*
* This class will load the xml file after validating against a xsd. It will also perform transformation to get the activity
* description based on activity code
* */
public class XMLLogParser implements LogParser {
    @Override
    public List<LogResponse> parseInput(SparkSession spark, String xmlPath, String xsdPath, String csvPath) {

        //StructType schema = XSDToSchema.read(Paths.get(xsdPath));

        // Validate xml against xsd and load the XML file
        Dataset<Row> df = spark.read().format("com.databricks.spark.xml").option("rowValidationXSDPath", xsdPath).option("rowTag", "activity").load(xmlPath);//
        df.printSchema();
        Dataset<Row> activityCodeDf = spark.read().option("header", "true").option("inferSchema", true).csv(csvPath);

        // join the dataframe for getting the activity description
        Dataset<Row> activityDescJoin = activityCodeDf.join(df, activityCodeDf.col("activity_code").equalTo(df.col("activityTypeCode")), "right").
                select("loggedInTime", "number_of_views", "userName", "websiteName", "acitivty_description");
        activityDescJoin.show();
        List<LogResponse> logResponseList = new ArrayList<>();


        activityDescJoin.collectAsList().forEach(row -> {
            // traverse through each row for populating the response object
            LogResponse logResponse = new LogResponse();
            logResponse.setUser(row.getAs("userName").toString());
            logResponse.setWebsite(row.getAs("websiteName").toString());
            logResponse.setActivityTypeDescription(row.getAs("acitivty_description").toString());
            logResponse.setSignedInTime(row.getAs("loggedInTime").toString());
            logResponseList.add(logResponse);
        });

        return logResponseList;
    }
}
