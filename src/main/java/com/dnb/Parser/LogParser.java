package com.dnb.Parser;

import com.dnb.Parser.dto.LogResponse;
import org.apache.spark.sql.SparkSession;

import java.util.List;

public interface LogParser {


    List<LogResponse> parseInput(SparkSession spark, String inputFilePath, String xsdPath, String csvPath);


}
