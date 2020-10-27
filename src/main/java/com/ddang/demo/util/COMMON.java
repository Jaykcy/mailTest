package com.ddang.demo.util;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @author SunChaoqun
 */

public class COMMON {
    public static boolean isEmpty(Object obj) {
        if (obj == null) return true;

        // 如果不为null，需要处理几种特殊对象类型
        if (obj instanceof String) {
            if ("null".equals(((String)obj).toLowerCase())) {
                return true;
            }
            else {
                return ((String)obj).trim().equals("");
            }
        }
        else if (obj instanceof Collection) {
            // 对象为集合
            Collection coll = (Collection)obj;
            return coll.size() == 0;
        }
        else if (obj instanceof Map) {
            // 对象为Map
            Map map = (Map)obj;
            return map.size() == 0;
        }
        else if (obj.getClass().isArray()) {
            // 对象为数组
            return Array.getLength(obj) == 0;
        }
        else {
            // 其他类型，只要不为null，即不为empty
            return false;
        }
    }

    public static String[] list2Array(List<String> mailList) {
        if (mailList == null || mailList.size() == 0) {
            return null;
        }
        String[] mailArray = new String[mailList.size()];
        for (int i = 0; i < mailList.size(); i++ ) {
            mailArray[i] = mailList.get(i);
        }
        return mailArray;
    }
    /*
    * 将对象中相同属性赋值
    * */
    public static Object initDtobyDto(Object t1, Object t2) {
        Class ct1 = (Class) t1.getClass();
        Class ct2 = (Class) t2.getClass();

        Field[] fs = ct1.getDeclaredFields();
        Field[] nfs = ct2.getDeclaredFields();
        for(int i = 0 ; i < fs.length; i++){
            Field f = fs[i];
            f.setAccessible(true); //设置些属性是可以访问的
            Object val = new Object();
            try {
                val = f.get(t1);//得到此属性的值
            }catch(IllegalAccessException e){

            }
            for(int j = 0; j < nfs.length; j++){
                Field nf = nfs[j];
                nf.setAccessible(true); //设置些属性是可以访问的
                if(f.getName().equals(nf.getName())){
                    try {
                        nf.set(t2, val);
                    }catch(IllegalAccessException e){

                    }
                }
            }
        }
        return t2;
    }
}