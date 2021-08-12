package com.dnb.Parser;

import org.apache.spark.sql.SparkSession;

public abstract class TestSuite {

   public SparkSession spark = SparkSession
            .builder()
            .appName("Logger Application")
            .master("local")
            .getOrCreate();
}

