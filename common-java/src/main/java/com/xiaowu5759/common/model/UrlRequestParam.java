package com.xiaowu5759.common.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaowu
 * @date 2021/3/1 11:29 AM
 */

public class UrlRequestParam {

    private String originUrl;

    private String domainName;

    private String firstPath;

    private String secondPath;

    private String thirdPath;

    // 依次是0，1，2，3
    private int pathLength;

    // 有可能存在array
    private Map<String, Object> originParams = new HashMap<>();

    private Map<String, Object> businessParams = new HashMap<>();

    public UrlRequestParam() {
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getFirstPath() {
        return firstPath;
    }

    public void setFirstPath(String firstPath) {
        this.firstPath = firstPath;
    }

    public String getSecondPath() {
        return secondPath;
    }

    public void setSecondPath(String secondPath) {
        this.secondPath = secondPath;
    }

    public String getThirdPath() {
        return thirdPath;
    }

    public void setThirdPath(String thirdPath) {
        this.thirdPath = thirdPath;
    }

    public int getPathLength() {
        return pathLength;
    }

    public void setPathLength(int pathLength) {
        this.pathLength = pathLength;
    }

    public Map<String, Object> getOriginParams() {
        return originParams;
    }

    public void setOriginParams(Map<String, Object> originParams) {
        this.originParams = originParams;
    }

    public Map<String, Object> getBusinessParams() {
        return businessParams;
    }

    public void setBusinessParams(Map<String, Object> businessParams) {
        this.businessParams = businessParams;
    }
}
