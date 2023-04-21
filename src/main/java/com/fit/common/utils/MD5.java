package com.fit.common.utils;

import java.security.MessageDigest;

/**
 * MD5加密工具
 */
public class MD5 {

    /**
     * 检测KEY是否正确
     *
     * @param paraname 传入参数
     * @param day 当天时间
     * @param FKEY     接收的 KEY
     * @return 为空则返回true，不否则返回false
     */
    public static boolean checkKey(String paraname, String day, String FKEY) {
        paraname = (null == paraname) ? "" : paraname;
        return md5(paraname + day + ",fh,").equals(FKEY);
    }

    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(md5("31119@qq.com" + "123456"));
        System.out.println(md5("mj1"));
    }
}
