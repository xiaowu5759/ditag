package com.xiaowu5759.common.util;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 依赖dom4j解决xml数据解析
 *
 * @author xiaowu
 * @date 2021/5/20 7:47 PM
 */
public class XmlUtils {

    // 读取xml文件
    // str 转 xml
    public static Element str2Xml(String str){
//        //创建SAXReader对象
//        SAXReader reader = new SAXReader();
//        InputStream is = new ByteArrayInputStream("<xml><id>1</id><name>frank</name></xml>".getBytes());
//        //读取文件 转换成Document
//        Document document = reader.read(is);
//        //获取根节点元素对象
//        Element root = document.getRootElement();


        Document document = null;
        try {
            document = DocumentHelper.parseText(str);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        if(document == null){
            throw new RuntimeException();
        }

        return document.getRootElement();

    }

    // xml转字符串
    // root.asXML()

    // map转xml
    public static String map2XmlStr(Map<String, String> params, boolean isAddCDATA){

        StringBuilder sb = new StringBuilder("<xml>");

        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append("<").append(entry.getKey()).append(">");
                if (isAddCDATA) {
                    sb.append("<![CDATA[");
                    if (!StrUtil.isEmpty(entry.getValue())) {
                        sb.append(entry.getValue());
                    }
                    sb.append("]]>");
                } else {
                    if (!StrUtil.isEmpty(entry.getValue())) {
                        sb.append(entry.getValue());
                    }
                }
                sb.append("</").append(entry.getKey()).append(">");
            }
        }

        return sb.append("</xml>").toString();
    }

    // xml转map，利用递归
    public static Map<String, String> xml2Map(Element node){
        // 默认只展示第一层的
        Map<String, String> returnMap = new HashMap<>();
//        //首先获取当前节点的所有属性节点
//        List<Attribute> list = node.attributes();
//        // 遍历属性节点
//        for(Attribute attribute : list){
//            // 属性 是 style
//        }

        Iterator<Element> iterator = node.elementIterator();
        while(iterator.hasNext()){
            Element e = iterator.next();
            returnMap.put(e.getName(), e.getText());
        }


        return returnMap;
    }

    // xml转json
    public static JSONObject xml2Json(Element node){
        JSONObject json = new JSONObject();
        if(!(node.getTextTrim().equals(""))){
            json.put(node.getName(), node.getText());
        }

        // 同时迭代当前节点下面的所有子节点
        Iterator<Element> iterator = node.elementIterator();
        while(iterator.hasNext()){
            Element e = iterator.next();
            xml2Json(e);
        }

        return json;
    }

    public static void main(String[] args) throws DocumentException {
        //创建SAXReader对象
        SAXReader reader = new SAXReader();
        InputStream is = new ByteArrayInputStream("<xml><id>1</id><name>frank</name></xml>".getBytes());
        //读取文件 转换成Document
        Document document = reader.read(is);
        //获取根节点元素对象
        Element root = document.getRootElement();
        Map<String, String> returnMap = XmlUtils.xml2Map(root);
        System.out.println(returnMap.toString());

        String str = new String("<xml>\n" +
                "   <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "   <return_msg><![CDATA[OK]]></return_msg>\n" +
                "   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n" +
                "   <mch_id><![CDATA[10000100]]></mch_id>\n" +
                "   <nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str>\n" +
                "   <openid><![CDATA[oUpF8uMuAJO_M2pxb1Q9zNjWeS6o]]></openid>\n" +
                "   <sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign>\n" +
                "   <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "   <prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id>\n" +
                "   <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                "</xml>\n" +
                "\n");
        Element element = XmlUtils.str2Xml(str);
        Map<String, String> stringMap = XmlUtils.xml2Map(element);
        System.out.println(stringMap);


        Map<String,String> map = new HashMap<>();
        map.put("appid","11111111111111");
        map.put("mch_id","222222222222");
        map.put("body","测试");
        map.put("sign_type","MD5");
        String s = XmlUtils.map2XmlStr(map, true);
        System.out.println(s);
    }

}
