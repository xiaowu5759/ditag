package com.xiaowu5759.ditag.sparktag.support;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

/**
 * spark session 获取 pool
 *
 * @author xiaowu
 * @date 2021/5/27 7:34 PM
 */
public class SparkSessionUtils {

    private static ThreadLocal<JavaSparkContext> jscPool = new ThreadLocal<>();

    private static ThreadLocal<SparkSession> ssPool = new ThreadLocal<>();

    public static JavaSparkContext getJSC4Es(Boolean auto) {
        JavaSparkContext javaSparkContext = jscPool.get();
        if (javaSparkContext != null) {
            return javaSparkContext;
        }

        SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("spark-etl");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        jscPool.set(jsc);
        return jsc;
    }

    public static SparkSession initSession() {
        if (ssPool.get() != null) {
            return ssPool.get();
        }

        SparkSession session = SparkSession.builder().appName("spark-etl")
                .master("local[*]")
                .config("spark.sql.warehouse.dir", "hdfs://gown:8020/user/hive/warehouse")
                .config("hive.metastore.uris", "thrift://emilia:9083")
                .enableHiveSupport()
                .getOrCreate();
        ssPool.set(session);
        return session;

    }


}