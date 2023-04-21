package com.fit.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;


public class RequestUtil {

    public static void main(String[] args) {
        System.out.println("本机的ip=" + getIp());
    }


    /**
     * 获取本机访问地址
     */
    public static String getIp() {
        try {
            InetAddress inet = InetAddress.getLocalHost();
            return inet.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String PathAddress(HttpServletRequest request) {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(request.getScheme() + "://");
        strBuf.append(request.getServerName() + ":");
        strBuf.append(request.getServerPort() + "");
        strBuf.append(request.getContextPath() + "/");
        return strBuf.toString();
    }

    /**
     * 获取请求的所有参数
     */
    protected Map<String, Object> getRequestParamsMap(HttpServletRequest request) {
        // 返回值Map
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            // 参数Map
            Map<String, String[]> properties = request.getParameterMap();
            String value = "";
            for (Iterator<?> iter = properties.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry<?, ?> element = (Map.Entry<?, ?>) iter.next();
                Object strKey = element.getKey();
                Object strObj = element.getValue();
                if (null == strObj) {
                    value = "";
                } else if (strObj instanceof String[]) {
                    String[] values = (String[]) strObj;
                    for (int i = 0; i < values.length; i++) { // 用于请求参数中有多个相同名称
                        value = values[i] + ",";
                    }
                    value = value.substring(0, value.length() - 1);
                } else {
                    value = strObj.toString();// 用于请求参数中请求参数名唯一
                }
                if (StringUtils.hasLength(value)) {
                    returnMap.put(strKey.toString(), value);
                }
            }
        } catch (Exception e) {
            System.out.println(String.format("getRequestParamsMap错误信息：%s", e.getMessage()));
        }
        return returnMap;
    }
}