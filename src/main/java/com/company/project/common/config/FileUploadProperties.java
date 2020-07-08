package com.company.project.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 文件上传参数配置类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Component
@ConfigurationProperties(prefix = "file")
public class FileUploadProperties {

    private String path;
    private String url;
    private String accessUrl;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;

        //set accessUrl
        if (StringUtils.isEmpty(url)) {
            this.accessUrl = null;
        }
        this.accessUrl = url.substring(url.lastIndexOf("/")) + "/**";
        System.out.println("accessUrl="+accessUrl);
    }

    public String getAccessUrl() {
        return accessUrl;
    }

}