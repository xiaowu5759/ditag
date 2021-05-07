package com.xiaowu5759.common.util;


import com.alibaba.fastjson.JSON;
import com.xiaowu5759.common.model.UrlRequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaowu
 * @date 2021/1/18 2:36 PM
 */
public class UrlParseUtils {

    /**
     * 其中object 可能是string，可能是list<string>
     * 只处理特定格式的 url
     *
     * @param url
     * @return
     */
    public static Map<String, Object> getMapParams(String url) throws UnsupportedEncodingException {
        url = decode(url);
        // 封装返回结果
        Map<String, Object> mapParams = new HashMap<String, Object>();

        return mapParams;
    }

    public static UrlRequestParam getUrlRequestParam(String url) throws UnsupportedEncodingException {
        UrlRequestParam urlRequestParam = new UrlRequestParam();
        url = decode(url);
        urlRequestParam.setOriginUrl(url);
        List<String> list = listElement(url);
        if(list.get(list.size() - 1).contains("=")){
            urlRequestParam.setPathLength(list.size() - 1);
            // 添加params
            Map<String, Object> mapParams = mapParamsValue(list.get(list.size() - 1));
            urlRequestParam.setOriginParams(mapParams);
        } else {
            urlRequestParam.setPathLength(list.size());
            urlRequestParam.setOriginParams(new HashMap<String, Object>());
        }

        // 添加三段
        if (urlRequestParam.getPathLength() == 1) {
            urlRequestParam.setFirstPath(list.get(0));
        } else if (urlRequestParam.getPathLength() == 2) {
            urlRequestParam.setFirstPath(list.get(0));
            urlRequestParam.setSecondPath(list.get(1));
        } else if (urlRequestParam.getPathLength() == 3) {
            urlRequestParam.setFirstPath(list.get(0));
            urlRequestParam.setSecondPath(list.get(1));
            urlRequestParam.setThirdPath(list.get(2));
        }


        return urlRequestParam;
    }

    // 切分连字符关系


    /**
     * 切分下划线关系
     * 谷歌官方对于使用连字符还是下划线问题的建议是：我们建议您在网址中使用连字符（-）而尽量避免使用下划线 （_）
     * 下划线 underscores，下划线是一个整体
     * 连字符 hyphens，连字符是多个元素
     *
     * @param url
     * @return
     */
    public static Map<String, Object> mapUnderscores(String url) {

        String[] split = url.split("_");
        // 思考key 如何命名,under1,under2
        Map<String, Object> returnMap = new HashMap<String, Object>();
        for (int i = 0; i < split.length; i++) {
            String key = "under" + i;
            returnMap.put(key, split[i]);
        }

        return returnMap;
    }

    /**
     * 切分字符和数字关系
     * 这里必须成对，英文开头数字结尾
     *
     * @param url
     * @return
     */
    public static Map<String, Object> mapCharNumber(String url) {
        // 正则中间不能有空格
        Pattern p = Pattern.compile("[a-z]+|[A-Z]+|\\d+");
        Matcher m = p.matcher(url);
        List<String> allMatches = new ArrayList<String>();
        while (m.find()) {
            allMatches.add(m.group());
        }

        // 间隔筛选
        Map<String, Object> returnMap = new HashMap<String, Object>();
        for(int i=0; i<allMatches.size(); i=i+2){
            returnMap.put(allMatches.get(i), allMatches.get(i+1));
        }

        return returnMap;
    }

    /**
     * 切分& 符合条件的字段
     *
     * @param url
     * @return
     */
    public static Map<String, Object> mapParamsValue(String url) {
        String[] split = url.split("&");
        // 封装返回结果
        Map<String, Object> returnMap = new HashMap<String, Object>();
        for (String s : split) {
            String[] equalSplit = s.split("=");
            returnMap.put(equalSplit[0], equalSplit[1]);
        }

        return returnMap;
    }

    /**
     * 预处理
     * 去除.html，有可能是/.html或者是.html，换成 /
     * 去除问号？
     * 按照 / 切分
     *
     * @param url
     * @return
     */
    public static List<String> listElement(String url) {
        url = url.replace(".html", "/.html").replace("/.html", "/").replace("?", "");
        // 消除第一个 /
        url = url.substring(1, url.length());
        String[] split = url.split("/");

        // 封装
        return new ArrayList<String>(Arrays.asList(split));
    }

    /**
     * 中文URL解析
     *
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decode(String url) throws UnsupportedEncodingException {

        return URLDecoder.decode(url, "UTF-8");
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        // 这种理想型的才可以
        String url = "/tietu/soft_f2.html?page=1&maxPrice=350";
//        url = "/tietu/soft_f2";

        UrlRequestParam urlRequestParam = getUrlRequestParam(url);
        System.out.println(JSON.toJSON(urlRequestParam));
    }
}
