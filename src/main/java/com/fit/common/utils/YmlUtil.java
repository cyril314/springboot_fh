package com.fit.common.utils;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @className: YmlConfigUtil
 * @description: 读取所有YML配置文件中的值
 * @author: Aim
 * @date: 2023/4/14
 **/
public class YmlUtil {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = new String[]{"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/"};
    private static Properties prop = new Properties();

    static {
        setProp("application.yml");
    }

    public static void setProp(String propFile) {
        //1:加载配置文件
        for (String crl : CLASSPATH_RESOURCE_LOCATIONS) {
            try {
                Resource[] resources = new PathMatchingResourcePatternResolver().getResources(crl + propFile);
                for (Resource resource : resources) {
                    if (resource.exists()) {
                        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
                        // 2:将加载的配置文件交给 YamlPropertiesFactoryBean
                        yamlPropertiesFactoryBean.setResources(resource);
                        // 3：将yml转换成 key：val
                        prop.putAll(yamlPropertiesFactoryBean.getObject());
                        if (!prop.getProperty("spring.profiles.active").isEmpty() && propFile.equals("application.yml")) {
                            setProp("application-" + prop.getProperty("spring.profiles.active") + ".yml");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String get(String key) {
        if (prop != null) {
            return String.valueOf(prop.get(key)).trim();
        }
        return null;
    }
}