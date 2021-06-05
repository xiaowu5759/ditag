package com.xiaowu5759.common.util;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.RandomUtil;

import java.lang.management.ManagementFactory;

/**
 * https://www.codenong.com/8302634/
 *
 * @author xiaowu
 * @date 2021/5/14 2:52 PM
 */
public class JVMFingerprintUtils {

    // 单机上获取唯一指纹
    // 思路：Java代码如何获取运行它的JVM的唯一标识符？ 在Unix系统上，我正在寻找的示例将是运行JVM的进程的PID(假设JVM与进程之间是一对一的映射)
    // 端口号占用机制也可以
    // 因此进程号的最大值为0x7fff，即32767
    public static int getJVMIdInMachine(){
        // 9160@XiongWudeMacBook-Pro.local
        String jvmName = ManagementFactory.getRuntimeMXBean().getName();
        String[] split = jvmName.split("@");
        String pidStr = split[0];
        int pid = 0;
        try {
            pid = Integer.valueOf(pidStr);
        } catch (Exception e){
            e.printStackTrace();
        }

        if (pid == 0) {
            pid = RandomUtil.randomInt(0, 32768);
            System.out.println(pid);
        }

        return pid;
    }

    // 集群上获取唯一指纹
    // 思路：以上内容与计算机的LAN IP结合使用
    // IP地址（IPV4）是一个32位的二进制数
    public static int getJVMIdInCluster(){
        String serverLocalIpAddress = IPAddressUtils.getServerLocalIpAddress();
        return IPAddressUtils.ipv4ToInteger(serverLocalIpAddress);
    }

    // 组合获取一个服务的唯一指纹，向前位移
    // 防止出现负数 long，ip地址转换的要向后移动
    public static long getJVMIdInService() {
        int machineId = getJVMIdInMachine();
        int clusterId = getJVMIdInCluster();

        byte[] src = new byte[8];
        // 高位在前，低位在后
        src[0] = (byte) ((clusterId >>> 1) & 0xFF000000);
        src[1] = (byte) ((clusterId << 7) & 0xFF000000);


        src[6] = (byte) ((clusterId >> 7) & 0xFF);
        src[5] = (byte) ((clusterId >> 15) & 0xFF);
        src[4] = (byte) ((clusterId >> 23) & 0xFF);
        src[3] = (byte) ((clusterId << 31) & 0xFF);

        // clusterId最后一位保留
        src[2] = (byte) ((machineId << 1) & 0xFF);
        src[1] = (byte) ((machineId << 1) & 0xFF);
        src[0] = (byte) ((machineId << 1) & 0xFF);

        // byte转long

        return 0L;
    }

}
