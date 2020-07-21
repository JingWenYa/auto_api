package com.auto.utils;

import com.alibaba.fastjson.JSONPath;
import com.auto.constants.Constant;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpRequestBase;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationUtils {

    //环境变量
    public static Map<String,String> env = new HashMap<String,String>();

    //从响应体中获取token信息存储到缓存中
    public static void storeToken(String responseBody){
        //取出token 如果找不到token返回null
        Object token = JSONPath.read(responseBody, "$.data.token_info.token");
        //存储
        if (token != null){
            env.put("token",token.toString());
            Object member_id = JSONPath.read(responseBody, "$.data.id");
            if (member_id != null){
                env.put(Constant.MEMBER_ID,member_id.toString());
            }
        }
    }

    //添加token到请求头
    public static void addToken(HttpRequestBase request){
        //如果通过键找不到值 返回null
        String token = env.get("token");
        if (token != null){
            request.addHeader("Authorization","Bearer " + token);
        }
    }
}
