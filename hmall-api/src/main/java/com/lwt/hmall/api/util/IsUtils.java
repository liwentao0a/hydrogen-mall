package com.lwt.hmall.api.util;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author lwt
 * @Date 2020/2/5 13:34
 * @Description
 */
public class IsUtils {

    public static boolean isBlank(Object obj){
        if (obj==null){
            return true;
        }
        if (obj.getClass().isArray()){
            Object[] o= (Object[]) obj;
            return o.length==0;
        }
        if (obj instanceof String){
            String o= (String) obj;
            return o.isEmpty();
        }
        if (obj instanceof Collection){
            Collection o= (Collection) obj;
            return o.isEmpty();
        }
        if (obj instanceof Map){
            Map o= (Map) obj;
            return o.isEmpty();
        }
        return false;
    }

    /**
     * 判断object是否为基本类型
     * @param object
     * @return
     */
    public static boolean isBaseType(Object object) {
        Class className = object.getClass();
        if (className.equals(Integer.class) ||
                className.equals(Byte.class) ||
                className.equals(Long.class) ||
                className.equals(Double.class) ||
                className.equals(Float.class) ||
                className.equals(Character.class) ||
                className.equals(Short.class) ||
                className.equals(Boolean.class)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否存在汉字
     * @param str
     * @return
     */
    public static boolean containsChinese(String str){
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
