package com.xiaowu5759.common.util;

/**
 * 获取文件类型
 *
 * @author xiaowu
 * @date 2021/5/7 11:04 AM
 */
public class FileUtils {

    /**
     * 获取文件类型
     * <p>
     * ("FFD8FF", "jpg");
     * ("89504E47", "png");
     * ("47494638", "gif");
     * ("49492A00", "tif");
     * ("424D", "bmp"); //
     * ("41433130", "dwg"); // CAD
     * ("38425053", "psd");
     * ("7B5C727466", "rtf"); // 日记本
     * ("3C3F786D6C", "xml");
     * ("68746D6C3E", "html");
     * ("44656C69766572792D646174653A", "eml"); // 邮件
     * ("D0CF11E0", "doc");  // word or xls
     * ("5374616E64617264204A", "mdb");
     * ("252150532D41646F6265", "ps");
     * ("255044462D312E", "pdf");
     * ("504B0304", "docx");  //word  or  xlsx
     * ("52617221", "rar");
     * ("57415645", "wav");
     * ("41564920", "avi");
     * ("2E524D46", "rm");
     * ("000001BA", "mpg");
     * ("000001B3", "mpg");
     * ("6D6F6F76", "mov");
     * ("3026B2758E66CF11", "asf");
     * ("4D546864", "mid");
     * ("1F8B08", "gz");
     * ("4D5A9000", "exe/dll");
     * ("75736167", "txt");
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String getFileType(byte[] data) throws Exception {
        //读取文件的前几个字节 文件头 来判断图片格式
        byte[] b = new byte[4];
        StringBuilder sb = new StringBuilder();
        b[0] = data[0];
        b[1] = data[1];
        b[2] = data[2];
        b[3] = data[3];
        for (int i = 0; i < b.length; i++) {
            sb.append(String.format("%02x", b[i]));
        }

        String type = sb.toString().toUpperCase();
        if (type.contains("FFD8FF")) {
            return "jpg";
        } else if (type.contains("89504E47")) {
            return "png";
        } else if (type.contains("47494638")) {
            return "gif";
        } else if (type.contains("424D")) {
            return "bmp";
        } else if (type.contains("52494646")) {
            return "webp";
        } else if (type.contains("49492A00")) {
            return "tif";
        } else if (type.contains("41433130")) {
            //cad
            return "dwg";
        } else if (type.contains("38425053")) {
            return "psd";
        } else if (type.contains("7B5C727466")) {
            // 日记本
            return "rtf";
        } else if (type.contains("3C3F786D6C")) {
            return "xml";
        } else if (type.contains("68746D6C3E")) {
            return "html";
        } else if (type.contains("44656C69766572792D646174653A")) {
            // 邮件
            return "eml";
        } else if (type.contains("D0CF11E0")) {
            return "doc";
        } else if (type.contains("5374616E64617264204A")) {
            return "mdb";
        } else if (type.contains("252150532D41646F6265")) {
            return "ps";
        } else if (type.contains("25504446")) {
            return "pdf";
        } else if (type.contains("504B0304")) {
            return "docx";
        } else if (type.contains("52617221")) {
            return "rar";
        } else if (type.contains("57415645")) {
            return "wav";
        } else if (type.contains("41564920")) {
            return "avi";
        } else if (type.contains("2E524D46")) {
            return "rm";
        } else if (type.contains("000001BA")) {
            return "mpg";
        } else if (type.contains("000001B3")) {
            return "mpg";
        } else if (type.contains("6D6F6F76")) {
            return "mov";
        } else if (type.contains("3026B2758E66CF11")) {
            return "asf";
        } else if (type.contains("4D546864")) {
            return "mid";
        } else if (type.contains("1F8B08")) {
            return "gz";
        } else if (type.contains("4D5A9000")) {
            return "exe/dll";
        } else if (type.contains("75736167")) {
            return "txt";
        } else {
            System.out.println(type);
            throw new Exception("无法识别该格式");
        }
    }
}
