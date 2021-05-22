package com.xiaowu5759.ditag.sparktag.etl;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author xiaowu
 * @date 2021/5/20 11:39 PM
 */
public class HotWordEtl {

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.setProperty("HADOOP_USER_NAME", "root");
        SparkConf conf = new SparkConf().setAppName("hotword").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(conf);

        JavaRDD<String> linesRDD = jsc.textFile("hdfs://gown:8020/data/SogouQ.sample");

        // string，读取进入方法的，String，javaRDD
        // string
        // integer，最终就是tuple2返回结果中
        // 实现的回调函数，叫call方法，
        // pair 一对一对
        JavaPairRDD<String, Integer> pairRDD = linesRDD.mapToPair(new PairFunction<String, String, Integer>() {
            // string --》（string, integer）返回出去，一行一行的
            public Tuple2<String, Integer> call(String s) throws Exception {
                String word = s.split("\t")[2];
                return new Tuple2<String, Integer>(word, 1);
            }
        });

        // 接下来就是reduce动作
        // 根据 pairRDD，决定泛型属性
        // 重组，将integer类型传入，call中
        JavaPairRDD<String, Integer> resultRDD = pairRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer v1, Integer v2) throws Exception {
                // （"hello"， 1），（"hello", 1）
                return v1 + v2;
            }
        });

        // 取前10条
        // 直接本地运行
//        List<Tuple2<String, Integer>> take = resultRDD.take(10);
//        for(Tuple2<String, Integer> tuple2 : take){
//            System.out.println(tuple2._1 + "===" + tuple2._2);
//        }

        // 对调key-value，应为要按照key排序
        JavaPairRDD<Integer, String> swapRDD = resultRDD.mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {
            public Tuple2<Integer, String> call(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
                // （"hello", 10） ==> (10, "hello")
                return stringIntegerTuple2.swap();
            }
        });
        // 倒序排序
        JavaPairRDD<Integer, String> sorted = swapRDD.sortByKey(false);
        JavaPairRDD<String, Integer> hotWordRDD = sorted.mapToPair(new PairFunction<Tuple2<Integer, String>, String, Integer>() {
            public Tuple2<String, Integer> call(Tuple2<Integer, String> integerStringTuple2) throws Exception {
                return integerStringTuple2.swap();
            }
        });
        List<Tuple2<String, Integer>> take = hotWordRDD.take(10);
        for(Tuple2<String, Integer> tuple2 : take){
            String word = new String(tuple2._1.getBytes("UTF-8"), "UTF-8");
            System.out.println(word + "===" + tuple2._2);
        }

    }
}
