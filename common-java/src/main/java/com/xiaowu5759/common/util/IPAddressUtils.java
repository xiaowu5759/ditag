package com.xiaowu5759.common.util;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ip工具类
 *
 * @author xiaowu
 * @date 2020/7/27 10:09
 */
public class IPAddressUtils {

    private static final String CHINAZ = "http://ip.chinaz.com";

    // 获取公网ip
    public static String getServerWideIpAddress(){
        String ipv4 = "127.0.0.1";
        StringBuilder inputLine = new StringBuilder();
        String read;
        HttpURLConnection urlConnection;
        BufferedReader in = null;
        try {
            URL url = new URL(CHINAZ);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader( new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
            while((read=in.readLine())!=null){
                inputLine.append(read).append("\r\n");
            }
            //System.out.println(inputLine.toString());		//输出http://ip.chinaz.com网页详细信息
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        Matcher m = p.matcher(inputLine.toString());
        if(m.find()){
            ipv4 = m.group(1);
//            System.out.println("ipstr：："+ipstr);
//            System.out.println("ipv4::"+ipv4);
        }
        return ipv4;
    }

    // 获取局域网ip
    // 即存在很多的网络接口，每个网络接口就包含一个IP地址，并不是所有的IP地址能被外部或局域网访问，比如说虚拟机网卡地址
    public static String getServerLocalIpAddress(){
        Enumeration<NetworkInterface> networkInterfaces;
        InetAddress candidateAddress = null;
        try {
            // 获取所有的网口
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()){
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        // 排除loopback类型地址
                        if (inetAddress.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddress.getHostAddress();
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddress;
                        }
                    }

                }
            }
            if (candidateAddress != null) {
                return candidateAddress.getHostAddress();
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress.getHostAddress();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return "127.0.0.1";
    }

    // 获取访问者的ip
    public static String getClientIpAddress(HttpServletRequest request) {
        if(request == null){
            return "127.0.0.1";
        }
        String ipAddress = null;
        ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }

        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    // 将ip地址转换为整数
    public static int ipv4ToInteger(String strIP) {

        if (Validator.isIpv4(strIP)) {
            int[] ip = new int[4];
            // 先找到IP地址字符串中.的位置
            int position1 = strIP.indexOf(".");
            int position2 = strIP.indexOf(".", position1 + 1);
            int position3 = strIP.indexOf(".", position2 + 1);
            // 将每个.之间的字符串转换成整型
            ip[0] = Integer.parseInt(strIP.substring(0, position1));
            ip[1] = Integer.parseInt(strIP.substring(position1 + 1, position2));
            ip[2] = Integer.parseInt(strIP.substring(position2 + 1, position3));
            ip[3] = Integer.parseInt(strIP.substring(position3 + 1));
            return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
        }
        return 0;
    }

    // 将整数转化为ip地址
    public static String integerToIpv4(int intIP) {
        final StringBuilder sb = new StringBuilder();
        // 直接右移24位
        // >>> "无符号"右移运算符，将运算符左边的对象向右移动运算符右边指定的位数。采用0扩展机制，也就是说，无论值的正负，都在高位补0.
        sb.append((intIP >>> 24));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(((intIP & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(((intIP & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append((intIP & 0x000000FF));
        return sb.toString();
    }

}
