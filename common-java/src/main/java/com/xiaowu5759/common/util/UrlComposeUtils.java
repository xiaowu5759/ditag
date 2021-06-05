package com.xiaowu5759.common.util;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.net.url.UrlQuery;
import cn.hutool.core.util.StrUtil;
import com.xiaowu5759.common.exception.BusinessException;
import com.xiaowu5759.common.model.UrlRequestParam;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * url 作曲家
 * 将复杂的参数，组装成一个url
 * 利用hutool
 *
 * @author xiaowu
 * @date 2021/5/19 4:22 PM
 */
public class UrlComposeUtils {

    public static String genUrlByParams(String baseUrl, Map<String, String> params) {

        if(StrUtil.isBlank(baseUrl)){
            return null;
        }

        UrlRequestParam urlRequestParam = null;
        try {
            urlRequestParam = UrlParseUtils.getUrlRequestParam(baseUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(urlRequestParam == null || urlRequestParam.getDomainName() == null){
            throw new RuntimeException();
        }

        UrlBuilder urlBuilder = UrlBuilder.create()
                .setScheme("https")
                .setHost(urlRequestParam.getDomainName())
                .addPath(urlRequestParam.getFirstPath());
        for (Map.Entry<String, String> entry : params.entrySet()) {
            urlBuilder.addQuery(entry.getKey(), entry.getValue());
        }

        return urlBuilder.build();

    }
}
