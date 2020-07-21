package com.auto.demo;

import com.auto.pojo.API;

public class Reflection {
    public static void main(String[] args) throws Exception{
        //反射：java在运行时动态加载一个类(所以可以适用不同的项目)，并且创建对象、调用属性和方法
        //反射的基本：必须要有字节码对象（.class文件）
        //Class字节码对象（类中的所有内容）
//        //1、
//        Class clazz1 = API.class;
//        //2、
//        API api = new API();
//        Class clazz2 = api.getClass();
        //3、最通用 灵活的方法
        String className = "com.auto.pojo.API";
        Class clazz3 = Class.forName(className);
        //调用空参构造
        Object obj = clazz3.newInstance();
        System.out.println(obj);
        System.out.println("===============");
        API api = new API();
        System.out.println(api);
    }
}
