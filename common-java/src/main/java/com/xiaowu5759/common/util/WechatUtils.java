package com.xiaowu5759.common.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 来自syy WxUtil
 *
 * @author xiaowu
 * @date 2020/7/27 9:35
 */
public class WechatUtils {

    //微信服务号 access_token 缓存key
    public static final String FWH_ACCEXX_TOKEN_KEY = "ins:fwh:access_token";
    // 发送模板消息的接口
    public static final String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    // 与接口配置信息中的Token要一致
    public static String token = "znzmoins";
    //微信服务号appid
    public static String appid = "wx731868c015dd79b4";
    //微信服务号secret
    public static String secret = "7d313ca9c39123ae842603ad2abe7faf";
    //小程序appid
    public static String XCX_APPID = "wx7373ab2336565a05";
    //小程序模板id
    public static String TEMPLATE_ONE = "kOdZz7Wd-pJb-5bu8RZuUEpS7CpuE8XosR4eDlv-FTE";

    // 发送模板消息的接口
    public static final String SEND_AIWXAPP_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=ACCESS_TOKEN";
    //微信AI小程序 access_token 缓存key
    public static final String AIWXAPP_ACCEXX_TOKEN_KEY = "ins:aiwxapp:access_token";
    //AI小程序 access_appid
    public static String AIWXAPP_APPID = "wx2a8f7bd48d36154b";
    //AI小程序 secret
    public static String AIWXAPP_SECRET = "5eec31f084b3dfecbf8a61a0909f267b";
    //模板消息id
    public static String AIWXAPP_TEMPLATE = "TNBLSGMytta1waS0MCXJaDIUNGD6qJLvm7zd9xS59SI";

    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";

    /**
     * 小程序手机号解密
     *
     * @param sSrc
     * @param encodingFormat
     * @param sKey
     * @param ivParameter
     * @return
     * @throws Exception
     */
    public static String decryptS5(String sSrc, String encodingFormat, String sKey, String ivParameter) {
//        encryptedData: "y2Wx/SJ+HAmQr13UwLnTMLGpVmqmWPukdnNHoqGbkYXibgNeLhMQliufZO6AdrKDQEy8KihWZBZOXLLiI5zU2DE03awIbPAg4XcuzcHdbBqCQLHuXyR+Vb8bgoSZNDoPIuwQZ7GM8+E9Z5JlocsUPCWoXzlYImE24C+vlecSw2ib+1m8lYVbg/nVhD1xLC9eEA50+HNVMa5CYk1c2cDLWA=="
//        errMsg: "getPhoneNumber:ok"
//        iv: "AbUV6ZYxcnTcinroq2b8eg=="
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] raw = decoder.decodeBuffer(sKey);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(decoder.decodeBuffer(ivParameter));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] myendicod = decoder.decodeBuffer(sSrc);
            byte[] original = cipher.doFinal(myendicod);
            String originalString = new String(original, encodingFormat);
            return originalString;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }


    }

    public static String decrypt(String data, String encodingFormat, String key, String iv) {
        BASE64Decoder decoder = new BASE64Decoder();

        byte[] dataByte = new byte[0];
        byte[] keyByte = new byte[0];
        byte[] ivByte = new byte[0];
        try {
            dataByte = decoder.decodeBuffer(data);
            keyByte = decoder.decodeBuffer(key);
            ivByte = decoder.decodeBuffer(iv);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                return new String(resultByte, encodingFormat);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 验证签名
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp,
                                         String nonce) {
        String[] arr = new String[]{token, timestamp, nonce};
        // 将token、timestamp、nonce三个参数进行字典序排序
        //Arrays.sort(arr);
        sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

    public static void sort(String a[]) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[j].compareTo(a[i]) < 0) {
                    String temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }



    /**
     * 获取微信公众号 ACCESS_TOKEN
     *
     * @return
     */
    public static String getAccessToken() {

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
        String body = null;
        try {
            body = Jsoup.connect(url).method(Connection.Method.GET).timeout(20 * 1000).ignoreContentType(true).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(body);
        JSONObject jsonObject = JSONObject.parseObject(body);
        if (jsonObject.containsKey("errcode")) {
            System.out.println("获取ACCESS_TOKEN错误:  " + body);
            return null;
        } else {
            String access_token = jsonObject.getString("access_token");
            //int expires_in = jsonObject.getIntValue("expires_in");
            return access_token;
        }


    }


    /**
     * 获取AI小程序 ACCESS_TOKEN
     *
     * @return
     */
    public static String getAIWXAPPToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + AIWXAPP_APPID + "&secret=" + AIWXAPP_SECRET;
        String body = null;
        try {
            body = Jsoup.connect(url).method(Connection.Method.GET).timeout(20 * 1000).ignoreContentType(true).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(body);
        JSONObject jsonObject = JSONObject.parseObject(body);
        if (jsonObject.containsKey("errcode")) {
            System.out.println("获取ACCESS_TOKEN错误:  " + body);
            return null;
        } else {
            String access_token = jsonObject.getString("access_token");
            //int expires_in = jsonObject.getIntValue("expires_in");
            return access_token;
        }
    }



    /**
     * 服务号 根据openId获取用户信息
     *
     * @param token
     * @param openId
     * @return {
     * "subscribe": 1,
     * "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M",
     * "nickname": "Band",
     * "sex": 1,
     * "language": "zh_CN",
     * "city": "广州",
     * "province": "广东",
     * "country": "中国",
     * "headimgurl":"http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
     * "subscribe_time": 1382694957,
     * "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * "remark": "",
     * "groupid": 0,
     * "tagid_list":[128,2],
     * "subscribe_scene": "ADD_SCENE_QR_CODE",
     * "qr_scene": 98765,
     * "qr_scene_str": ""
     * }
     */
//    public static InspirationFwhUser getUserInfoByOpenId(String token, String openId) {
//
//        InspirationFwhUser inspirationFwhUser = new InspirationFwhUser();
//        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + token + "&openid=" + openId + "&lang=zh_CN";
//        String body = null;
//        try {
//            body = Jsoup.connect(url).method(Connection.Method.GET).timeout(20 * 1000).ignoreContentType(true).execute().body();
//            System.out.println(body);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        JSONObject jsonObject = JSONObject.parseObject(body);
//        inspirationFwhUser.setSubscribe(jsonObject.getIntValue("subscribe"));
//        inspirationFwhUser.setOpenid(jsonObject.getString("openid"));
//        inspirationFwhUser.setNickname(jsonObject.getString("nickname"));
//        inspirationFwhUser.setSex(jsonObject.getIntValue("sex"));
//        inspirationFwhUser.setLanguage(jsonObject.getString("language"));
//        inspirationFwhUser.setCity(jsonObject.getString("city"));
//        inspirationFwhUser.setCountry(jsonObject.getString("country"));
//        inspirationFwhUser.setHeadimgurl(jsonObject.getString("headimgurl"));
//        if (jsonObject.getLong("subscribe_time") != null) {
//            inspirationFwhUser.setSubscribeTime(new Date(jsonObject.getLong("subscribe_time") * 1000));
//        }
//        inspirationFwhUser.setUnionid(jsonObject.getString("unionid"));
//        inspirationFwhUser.setRemark(jsonObject.getString("remark"));
//        inspirationFwhUser.setGroupid(jsonObject.getIntValue("groupid"));
//        inspirationFwhUser.setTagidList(jsonObject.getString("tagid_list"));
//        inspirationFwhUser.setSubscribeScene(jsonObject.getString("ADD_SCENE_QR_CODE"));
//        inspirationFwhUser.setQrScene(jsonObject.getString("qr_scene"));
//        inspirationFwhUser.setQrSceneStr(jsonObject.getString("qr_scene_str"));
//
//        return inspirationFwhUser;
//
//    }


    /**
     * 装载并发送模板消息
     *
     * @param accessToken
     * @param openId
     * @param templateID
     * @throws Exception
     */
//    public static void textTemplate(String accessToken, String openId, String templateID, Map<String, String> dataMap) throws Exception {
//        // 将信息封装到实体类中
//        TemplateMessage temp = new TemplateMessage();
//        // 设置模板id
//        temp.setTemplate_id(templateID);
//        // 设置接受方的openid
//        temp.setTouser(openId);
//        // 设置点击跳转的路径
//        temp.setUrl("http://mp.weixin.qq.com");
//        // 主要是这里， 设置小程序的appid和转发的页面
//        TreeMap<String, String> miniprograms = new TreeMap<String, String>();
//        miniprograms.put("appid", XCX_APPID);
//        // 注意，这里是支持传参的！！！
//        miniprograms.put("pagepath", "pages/home/index");
//        temp.setMiniprogram(miniprograms);
//        // 设置消息内容和对应的颜色
//        TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
//        // 设置消息内容，具体的按照你选择的模板消息来
//        params.put("first", TemplateMessage.item(dataMap.get("first"), "#173177"));
//        params.put("keyword1", TemplateMessage.item(dataMap.get("keyword1"), "#173177"));
//        params.put("keyword2", TemplateMessage.item(dataMap.get("keyword2"), "#173177"));
//        params.put("keyword3", TemplateMessage.item(dataMap.get("keyword3"), "#173177"));
//        params.put("remark", TemplateMessage.item(dataMap.get("remark"), "#173177"));
//        temp.setData(params);
//        // 将实体类转换为json格式
//        JSONObject data = (JSONObject) JSONObject.toJSON(temp);
//        System.out.println(data + "");
//        // 调用WeChatUtil工具类发送模板消息
//        sendTemplate(data + "", accessToken);
//    }

    /**
     * 发送模板
     *
     * @return 结果集（{"errcode":0,"errmsg":"ok","msgid":528986890112614400}）、
     * （{"errcode":40003,"errmsg":"invalid openid hint: [tv6YMa03463946]"}）
     * @throws IOException
     */
//    public static String sendTemplate(String data, String accesstoken) {
//        // 通过调用HttpUtil工具类发送post请求
//        // 调用微信发送模板消息的接口
//        String result = HttpUtil.post(SEND_TEMPLATE_URL.replace("ACCESS_TOKEN",
//                accesstoken), data);
//        System.out.println(result);
//        return result;
//    }



    /**
     * 装载并发送模板消息
     * {
     * "touser": "OPENID",
     * "template_id": "TEMPLATE_ID",
     * "page": "index",
     * "miniprogram_state":"developer",
     * "lang":"zh_CN",
     * "data": {
     * "number01": {
     * "value": "339208499"
     * },
     * "date01": {
     * "value": "2015年01月05日"
     * },
     * "site01": {
     * "value": "TIT创意园"
     * } ,
     * "site02": {
     * "value": "广州市新港中路397号"
     * }
     * }
     * }
     *
     * @param accessToken
     * @param openId
     * @param templateID
     * @throws Exception
     */
//    public static void AIWxApptextTemplate(String accessToken, String openId, String templateID, String content) throws Exception {
//
//        // 将信息封装到实体类中
//        AiWxAppTemplateMessage temp = new AiWxAppTemplateMessage();
//        // 设置模板id
//        temp.setTemplate_id(templateID);
//        // 设置接受方的openid
//        temp.setTouser(openId);
//        temp.setPage("pages/mydata/index?source=push");
//        //developer为开发版；trial为体验版；formal为正式版
//        //temp.setMiniprogram_state("developer");
//        temp.setLang("zh_CN");
//        // 设置消息内容和对应的颜色
//        TreeMap<String, Map<String, String>> params = new TreeMap<String, Map<String, String>>();
//        // 设置消息内容，具体的按照你选择的模板消息来
//        params.put("thing2", AiWxAppTemplateMessage.item("AI机器人已找到相关内容"));
//        params.put("date3", AiWxAppTemplateMessage.item(DateUtil.format(new Date(), "yyyy年MM月dd日 HH:mm")));
//        params.put("thing4", AiWxAppTemplateMessage.item("该资料24小时过期，请尽快领取"));
//        params.put("thing1", AiWxAppTemplateMessage.item(content));
//
//        temp.setData(params);
//
//        // 将实体类转换为json格式
//        JSONObject data = (JSONObject) JSONObject.toJSON(temp);
//
//        // 调用WeChatUtil工具类发送模板消息
//        sendAIWxAppTemplate(data + "", accessToken);
//    }

    /**
     * 发送模板
     *
     * @return 结果集（）
     * @throws IOException
     */
    public static String sendAIWxAppTemplate(String data, String accesstoken) throws Exception {
        // 通过调用HttpUtil工具类发送post请求
        // 调用微信发送模板消息的接口
        String result = HttpUtil.post(SEND_AIWXAPP_TEMPLATE_URL.replace("ACCESS_TOKEN",
                accesstoken), data);
        System.out.println(result);
        if (!result.contains("ok")) {
            throw new Exception("消息发送失败");
        }
        return result;
    }

}
