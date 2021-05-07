package com.xiaowu5759.common.util.compress;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RuntimeUtil;

import java.io.File;

/**
 * @author xiaowu
 * @date 2021/2/20 1:52 PM
 */
public class RarCompressor extends Compressor {

    public static void doUnCompress(File srcFile, String destpath) {
//        rarcmd = "{}  x -ibck -inul -y  {} {}".format(winrar_apth,  file, os.path.join(file.split('.')[0], title))
        // 获取文件绝对路径
        String srcFilePath = FileUtil.getAbsolutePath(srcFile);

        String rarcmd = String.format("%s  x -ibck -inul -y  %s %s", WINRAR_PATH, srcFilePath, destpath);

        // 调用hutool
        String result = RuntimeUtil.execForStr(rarcmd);

    }
}
