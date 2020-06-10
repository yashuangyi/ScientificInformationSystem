/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.yashuangyi.scientificinformationsystem.common;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Title: CopyUtils.java
 * @Package: com.gemframework.util
 * @Date: 2019/11/28 23:12
 * @Version: v1.0
 * @Description: 复制对象工具类

 * @Author: zhangysh
 * @Copyright: Copyright (c) 2019 GemStudio
 * @Company: www.gemframework.com
 */
public class GemBeanUtils {

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }


    /**
     * 单个对象属性复制
     *
     * @param source 复制源
     * @param clazz  目标对象class
     * @param <T>    目标对象类型
     * @param <M>    源对象类型
     * @return 目标对象
     */
    public static <T, M> T copyProperties(M source, Class<T> clazz) {
        if (Objects.isNull(source) || Objects.isNull(clazz))
            throw new IllegalArgumentException();
        T t = null;
        try {
            t = clazz.newInstance();
            BeanUtils.copyProperties(source, t);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }


    /**
     * 列表对象属性复制
     *
     * @param sources 源对象列表
     * @param clazz   目标对象class
     * @param <T>     目标对象类型
     * @param <M>     源对象类型
     * @return 目标对象列表
     */
    public static <T, M> List<T> copyCollections(List<M> sources, Class<T> clazz) {
        if (Objects.isNull(sources) || Objects.isNull(clazz))
            throw new IllegalArgumentException();
        return Optional.of(sources)
                .orElse(new ArrayList<>())
                .stream().map(m -> copyProperties(m, clazz))
                .collect(Collectors.toList());
    }


    /**
     * 下划线转驼峰
     */
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 驼峰转下划线
     */
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    public static void main(String[] args) throws Exception {
//        SysLog log = new SysLog();
//        log.setAccount("a");
//        log.setClientIp("b");
//        log.setCreateTime(new Date());
//
//        SysLogVo sysLogVo = GemBeanUtils.copyProperties(log,SysLogVo.class);
//        System.out.println("1==="+sysLogVo.toString());
//
//        SysLogVo sysLogVo2 = new SysLogVo();
//        GemBeanUtils.cpoyObjAttr(log,sysLogVo2,log.getClass());
//        System.out.println("2==="+sysLogVo2.toString());
//
//        SysLogVo sysLogVo3 = new SysLogVo();
//        GemBeanUtils.copyProperties(log,sysLogVo3);
//        System.out.println("3==="+sysLogVo3.toString());
//
//        SysLogVo sysLogVo4 = new SysLogVo();
//        sysLogVo4 = BeanMapper.map(log,SysLogVo.class);
//        System.out.println("4==="+sysLogVo4.toString());
    }
}
