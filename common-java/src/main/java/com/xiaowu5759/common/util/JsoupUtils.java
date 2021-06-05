package com.xiaowu5759.common.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author xiaowu
 * @date 2021/5/7 11:07 AM
 */
public class JsoupUtils {

    // 代理隧道验证信息
    final static String ProxyUser = "HP73A503KW30F0BD";
    final static String ProxyPass = "D31BA3031DC2C4DA";

    // 代理服务器
    final static String ProxyHost = "http-dyn.abuyun.com";
    final static Integer ProxyPort = 9020;

//    @Autowired
//    OssFileService ossFileService;

    public static Document getHtmlWithProxy(String url, String referrer) {
        //通知Java您要通过代理进行连接
        Authenticator.setDefault(new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ProxyUser, ProxyPass.toCharArray());
            }
        });

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ProxyHost, ProxyPort));

        //通过Jsoup类中的静态方法connect返回Document对象，该document对象实际为整个html页面内容。
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .followRedirects(false)
                    .proxy(proxy)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
                    .header("Host", referrer)
                    .referrer(referrer)
                    .validateTLSCertificates(false)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("网页的标题是:" + doc.title() + "\n");
        return doc;
    }


    public static Document getHtml(String url, String referrer, Map<String, String> requestMap, Map<String, String> cookieMap) {
        //通过Jsoup类中的静态方法connect返回Document对象，该document对象实际为整个html页面内容。
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .data(requestMap)
                    .cookies(cookieMap)
                    .followRedirects(false)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
                    .referrer(referrer)
                    .validateTLSCertificates(false)
                    .timeout(20 * 1000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("网页的标题是:" + doc.title() + "\n");
        return doc;
    }

    public static String getJsonWithProxy(String url) {
        //通知Java您要通过代理进行连接
        Authenticator.setDefault(new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ProxyUser, ProxyPass.toCharArray());
            }
        });

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ProxyHost, ProxyPort));

        //通过Jsoup类中的静态方法connect返回Document对象，该document对象实际为整个html页面内容。
        String body = null;
        try {
            body = Jsoup.connect(url).method(Connection.Method.GET)
                    .followRedirects(false)
                    .proxy(proxy)
                    .validateTLSCertificates(false)
                    .timeout(10 * 1000)
                    .ignoreContentType(true).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }


    public static String getJsonWithProxy(String url, Map<String, String> requestMap) {
        //通知Java您要通过代理进行连接
        Authenticator.setDefault(new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ProxyUser, ProxyPass.toCharArray());
            }
        });

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ProxyHost, ProxyPort));

        //通过Jsoup类中的静态方法connect返回Document对象，该document对象实际为整个html页面内容。
        String body = null;
        Connection connection = null;

        try {
            connection = Jsoup.connect(url).method(Connection.Method.GET)
                    .data(requestMap)
                    .followRedirects(false)
                    .proxy(proxy)
                    .validateTLSCertificates(false)
                    .timeout(10 * 1000)
                    .ignoreContentType(true);
            body = connection.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static String getJsonWithProxy(String url, Map<String, String> requestMap, Connection.Method method) {
        //通知Java您要通过代理进行连接
        Authenticator.setDefault(new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ProxyUser, ProxyPass.toCharArray());
            }
        });

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ProxyHost, ProxyPort));

        //通过Jsoup类中的静态方法connect返回Document对象，该document对象实际为整个html页面内容。
        String body = null;
        try {
            body = Jsoup.connect(url).method(method)
                    .data(requestMap)
                    .followRedirects(false)
                    .proxy(proxy)
                    .validateTLSCertificates(false)
                    .timeout(5 * 1000)
                    .ignoreContentType(true).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static String getJson(String url, Map<String, String> requestMap, Connection.Method method) {

        //通过Jsoup类中的静态方法connect返回Document对象，该document对象实际为整个html页面内容。
        String body = null;
        Connection connection = null;

        try {
            connection = Jsoup.connect(url).method(method)
                    .data(requestMap)
                    .followRedirects(false)
                    .validateTLSCertificates(false)
                    .timeout(10 * 1000)
                    .ignoreContentType(true);
            body = connection.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static HashMap<String, Object> downloadImage(String imgUrl, String referrer, String path) throws Exception {
        Authenticator.setDefault(new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ProxyUser, ProxyPass.toCharArray());
            }
        });

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ProxyHost, ProxyPort));

        HashMap<String, Object> map = new HashMap<String, Object>(8);
        BufferedImage image = null;


        Connection.Response response = null;
        try {
            response = Jsoup.connect(imgUrl)
                    .proxy(proxy)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36")
                    .referrer(referrer)
                    .ignoreContentType(true)
                    .maxBodySize(0)
                    .timeout(50 * 1000)
                    .execute();
        } catch (Exception e) {
            Thread.sleep(200);
            response = Jsoup.connect(imgUrl)
                    .proxy(proxy)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36")
                    .referrer(referrer)
                    .ignoreContentType(true)
                    .maxBodySize(0)
                    .timeout(50 * 1000)
                    .execute();
        }

        byte[] img = response.bodyAsBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(img);
        image = ImageIO.read(in);
        if (image != null) {
            map.put("height", String.valueOf(image.getHeight()));
            map.put("width", String.valueOf(image.getWidth()));
        } else {
            System.out.println("图片不存在！");
            throw new Exception("图片不存在！");
        }
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");

        String suffix = "jpg";

        int lastIndex = imgUrl.lastIndexOf(".");
        if (lastIndex < imgUrl.length() - 1) {
            suffix = imgUrl.substring(lastIndex + 1);
        }

        try {
            path = path + uuid + "." + suffix;
            ImageIO.write(image, suffix, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("path", path);
        in.close();
        return map;
    }


//    /**
//     * @param imgUrl      对方图片url
//     * @param referrer
//     * @param localPath   本地存储路径
//     * @param fileDirType oss文件夹
//     * @return
//     * @throws Exception
//     */
//    public HashMap<String, Object> downloadImageWithOss(String imgUrl, String referrer, String localPath, OssFileServiceImpl.FileDirType fileDirType) throws Exception {
//        Authenticator.setDefault(new Authenticator() {
//            @Override
//            public PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(ProxyUser, ProxyPass.toCharArray());
//            }
//        });
//
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ProxyHost, ProxyPort));
//
//        HashMap<String, Object> map = new HashMap<>(8);
//        Connection.Response response = null;
//        try {
//            response = Jsoup.connect(imgUrl)
//                    .proxy(proxy)
//                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36")
//                    .referrer(referrer)
//                    .ignoreContentType(true)
//                    .maxBodySize(0)
//                    .timeout(50 * 1000)
//                    .execute();
//        } catch (Exception e) {
//            Thread.sleep(200);
//            response = Jsoup.connect(imgUrl)
//                    .proxy(proxy)
//                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36")
//                    .referrer(referrer)
//                    .ignoreContentType(true)
//                    .maxBodySize(0)
//                    .timeout(50 * 1000)
//                    .execute();
//        }
//
//        byte[] img = response.bodyAsBytes();
//        //文件名
//        String uuid = UUID.randomUUID().toString();
//        uuid = uuid.replace("-", "");
//        String suffix = "jpg";
//        int lastIndex = imgUrl.lastIndexOf(".");
//        if (lastIndex < imgUrl.length() - 1) {
//            suffix = imgUrl.substring(lastIndex + 1);
//        }
//        String filePath = localPath + uuid + "." + suffix;
//        //保存文件本地
//        byte2image(img, filePath);
//        File ossFile = new File(filePath);
//        //保存文件OSS
//        String ossName = ossFileService.uploadFile(ossFile, fileDirType);
//        String ossUrl = OssFileService.CdnPrefix + ossName;
//
//        JSONObject json = new JSONObject();
//        Integer height = null;
//        Integer width = null;
//        try {
//            String info = HttpUtil.get(OssBucket.INS_ORIGIN_PREFIX + "/" + ossName + "?x-oss-process=image/info", 10 * 1000);
//            json = JSON.parseObject(info);
//            height = json.getJSONObject("ImageHeight").getIntValue("value");
//            width = json.getJSONObject("ImageWidth").getIntValue("value");
//        } catch (Exception e) {
//            System.out.println("图片不存在！");
//            throw new RuntimeException("图片不存在！");
//        }
//        map.put("height", String.valueOf(height));
//        map.put("width", String.valueOf(width));
//        map.put("path", ossUrl);
//        return map;
//    }


    public static void byte2image(byte[] data, String path) {
        if (data.length < 3 || path.equals("")) {
            return;
        }
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            // System.out.println("Make Picture success,Please find image in " + path);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }


//    public static void main(String[] args) {
//        System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
//        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
//        String v = "https://imgvz.vsszan.com/forum/202010/08/184202sj2cb5i2.jpg";
//        String filePath = "C:\\Users\\Administrator\\Desktop\\case_cover\\";
//        try {
//            downloadImage(v, v, filePath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
