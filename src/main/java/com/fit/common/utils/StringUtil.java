package com.fit.common.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串相关方法
 */
public class StringUtil {

    private static final String EMPTY = "";

    /**
     * 验证邮箱
     */
    public static boolean checkEmail(String email) {
        return regexStr(email, "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
    }

    /**
     * 验证手机号码
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        return regexStr(mobileNumber, "^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
    }

    private static boolean regexStr(String str, String reg) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile(reg);
            Matcher matcher = regex.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
        }
        return flag;
    }

    /**
     * 用默认的分隔符(,)将字符串转换为字符串数组
     *
     * @param str 字符串
     */
    public static String[] str2StrArray(String str) {
        return str2StrArray(str, ",\\s*");
    }

    /**
     * 字符串转换为字符串数组
     *
     * @param str        字符串
     * @param splitRegex 分隔符
     */
    public static String[] str2StrArray(String str, String splitRegex) {
        if (isEmpty(str)) {
            return null;
        }
        return str.split(splitRegex);
    }

    /**
     * 获取字符串编码
     */
    public static String getEncoding(String str) {
        String[] ENCODES = {"GB2312", "ISO-8859-1", "GBK", "UTF-8"};
        for (String encode : ENCODES) {
            try {
                if (str.equals(new String(str.getBytes(encode), encode))) {
                    return encode;
                }
            } catch (Exception e) {
                continue;
            }
        }

        return "";
    }

    /**
     * 将集合转化为字符串
     *
     * @param separator 分隔符
     * @param list      参数集合
     */
    public static String join(String separator, List<?> list) {
        Object[] objs = list.toArray();
        return join(separator, objs);
    }

    /**
     * 将数组转化为字符串
     *
     * @param array     参数数组
     * @param separator 分隔符
     */
    public static String join(String separator, Object[] array) {
        if (array == null) {
            return null;
        } else if (array.length <= 0) {
            return EMPTY;
        } else if (array.length == 1) {
            return String.valueOf(array[0]);
        } else {
            StringBuilder sb = new StringBuilder(array.length * 16);
            for (int i = 0; i < array.length; ++i) {
                if (i > 0) {
                    sb.append(separator);
                }
                sb.append(array[i]);
            }
            return sb.toString();
        }
    }

    /**
     * 判断某字符串是否为空或长度为0或由空白符构成
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 判断是否为空
     *
     * @param cs
     * @return
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 字符串首字母大写
     *
     * @param name
     * @return
     */
    public static String capitalize(String name) {
        if (isEmpty(name)) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static String substringAfter(final String str, final String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return EMPTY;
        }
        final int pos = str.indexOf(separator);
        if (pos == -1) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    public static String substringBefore(final String str, final String separator) {
        if (isEmpty(str) || separator == null) {
            return str;
        }
        if (separator.isEmpty()) {
            return EMPTY;
        }
        final int pos = str.indexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 去掉指定后缀
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     */
    public static String removeSuffix(CharSequence str, CharSequence suffix) {
        String s = str.toString();
        if (!isEmpty(suffix)) {
            if (s.endsWith(suffix.toString())) {
                return s.substring(0, s.length() - suffix.length());
            }
        }
        return s;
    }
}
