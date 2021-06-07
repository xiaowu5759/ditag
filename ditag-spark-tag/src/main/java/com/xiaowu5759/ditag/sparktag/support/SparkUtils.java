package com.xiaowu5759.ditag.sparktag.support;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

/**
 * spark session 获取 pool
 *
 * @author xiaowu
 * @date 2021/5/27 7:34 PM
 */
public class SparkUtils {

    private static ThreadLocal<JavaSparkContext> jscPool = new ThreadLocal<>();

    private static ThreadLocal<SparkSession> ssPool = new ThreadLocal<>();

    private static ThreadLocal<SparkContext> scPool = new ThreadLocal<>();

    public static JavaSparkContext getJSC4Es(Boolean auto) {
        JavaSparkContext javaSparkContext = jscPool.get();
        if (javaSparkContext != null) {
            return javaSparkContext;
        }

        SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("es-demo")
                .set("spark.sql.warehouse.dir", "hdfs://gown:8020/user/hive/warehouse")
                .set("hive.metastore.uris", "thrift://emilia:9083")
                .set("es.nodes", "peter")
                .set("es.port", "9200")
                .set("es.index.auto.create", auto.toString());
        JavaSparkContext jsc = new JavaSparkContext(conf);
        jscPool.set(jsc);
        return jsc;
    }

    public static SparkContext getSC2Es(Boolean auto) {
        SparkContext sparkContext = scPool.get();
        if (sparkContext != null) {
            return sparkContext;
        }

        SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("es-demo")
                .set("spark.sql.warehouse.dir", "hdfs://gown:8020/user/hive/warehouse")
                .set("hive.metastore.uris", "thrift://emilia:9083")
                .set("es.nodes", "192.168.31.156")
                .set("es.port", "9200")
                .set("es.index.auto.create", auto.toString());
        SparkContext sc = new SparkContext(conf);
        scPool.set(sc);
        return sc;
    }

    public static SparkSession initSession() {
        if (ssPool.get() != null) {
            return ssPool.get();
        }

        SparkSession session = SparkSession.builder().appName("spark-etl")
                .master("local[*]")
                .config("spark.sql.warehouse.dir", "hdfs://gown:8020/user/hive/warehouse")
                .config("hive.metastore.uris", "thrift://emilia:9083")
                .config("es.nodes", "192.168.31.156")
                .config("es.port", "9200")
                .config("es.index.auto.create", "false")
                .enableHiveSupport()
                .getOrCreate();
        ssPool.set(session);
        return session;

    }


}
