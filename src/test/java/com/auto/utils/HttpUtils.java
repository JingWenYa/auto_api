package com.auto.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class HttpUtils {

    public static BasicHeader basicHeader = new BasicHeader("X-Lemonban-Media-Type", "lemonban.v2");

    private static Logger log = Logger.getLogger(HttpUtils.class);

    /*
    * 调用httpClient发送http请求 返回字符串类型的响应体
    * */
    public static String call(String url,String params,String type,String contentType){
        // if contentType == json
        if ("json".equalsIgnoreCase(contentType)){
            // if type == post
            if ("post".equalsIgnoreCase(type)){
                return HttpUtils.testPostJson(url, params);
                // if type == get
            }else if("get".equalsIgnoreCase(type)){
                return HttpUtils.testGetJson(url, params);
                // if type == patch
            }else if ("patch".equalsIgnoreCase(type)){
                return HttpUtils.testPostForm(url,params);
            }
            // if contentType == form
        }else if("form".equalsIgnoreCase(contentType)){

            String keyValueParams = json2KeyValue(params);
            return HttpUtils.testPostForm(url, keyValueParams);
        }
        return "";
    }

    /*
    *params是json格式，可以通过fastjson转成map对象（key、value）
    * 再把map对象转成需要字符串
    * */
    private static String json2KeyValue(String params) {
        //1、params转成map对象
        Map<String,String> map = JSON.parseObject(params, Map.class);
        //2、map对象转成对应字符串mobilephone=15619227530&pwd=12345678
        Set<String> keySet = map.keySet();
//        String keyValueParams = "";
//        for (String key : keySet){
//            String value = map.get(key);
//            if (keyValueParams.length() > 0){
//                keyValueParams += "&";
//            }
//            keyValueParams += key + "=" + value;
//        }
        //方法2：
        String keyValueParams = "";
        for (String key : keySet){
            String value = map.get(key);
            keyValueParams += key + "=" + value + "&";
        }
        keyValueParams = keyValueParams.substring(0, keyValueParams.length() - 1);
        System.out.println(keyValueParams);
        return keyValueParams;
    }

    public static String testGetJson(String url,String params){
        try {
            //1、创建一个request，携带了method和url
            HttpGet httpGet = new HttpGet(url);
            //1.1、添加请求头
            //httpGet.addHeader("X-Lemonban-Media-Type", "lemonban.v1");
            httpGet.addHeader(basicHeader);
            //2、创建发送请求的客户端，HttpClients是HttpClient的工具类
            CloseableHttpClient Client = HttpClients.createDefault();
            //3、客户端调用发送get请求，立即返回http响应
            CloseableHttpResponse response = Client.execute(httpGet);

            //response整个响应对象（body、header、statuscode）
            //4.1、获取响应头
            Header[] allHeaders = response.getAllHeaders();
            //4.2、获取状态码
            int statusCode = response.getStatusLine().getStatusCode();
            //4.3、获取响应体
            HttpEntity responseEntity = response.getEntity();
            //4.4、格式化响应体
            String body = EntityUtils.toString(responseEntity);
            //5、输出响应
//            for (Header header : allHeaders) {
//                System.out.print("header:" + header);
//            }
//            System.out.println();
//            System.out.println(body);

            //Arrays.toString() java提供的工具类 把数组打印成字符串
            log.info("headers:" + Arrays.toString(allHeaders));
            log.info("statusCode:" + statusCode);
            log.info("body:" + body);

            return body;
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String testPostJson(String url,String params){
        try {
            //1、创建一个request，携带了method和url(get请求的参数直接加在url后面)
            HttpPost httpPost = new HttpPost(url);
            //1.1、添加请求头
            //httpPost.addHeader("X-Lemonban-Media-Type", "lemonban.v1");
            httpPost.addHeader(basicHeader);
            httpPost.addHeader("Content-Type", "application/json");
            //1.1.1 添加鉴权请求头
            AuthenticationUtils.addToken(httpPost);
            //1.2、添加请求体（参数）
            StringEntity requestEntity = new StringEntity(params);
            httpPost.setEntity(requestEntity);

            //2、创建发送请求的客户端，HttpClients是HttpClient的工具类
            CloseableHttpClient Client = HttpClients.createDefault();
            //3、客户端调用发送post请求，立即返回http响应
            CloseableHttpResponse response = Client.execute(httpPost);
            //response整个响应对象（body、header、statuscode）
            //4.1、获取响应头
            Header[] allHeaders = response.getAllHeaders();
            //4.2、获取状态码
            int statusCode = response.getStatusLine().getStatusCode();
            //4.3、获取响应体
            HttpEntity responseEntity = response.getEntity();
            //4.4、格式化响应体
            String body = EntityUtils.toString(responseEntity);
            //4.5 token存储
            AuthenticationUtils.storeToken(body);

            //5、输出响应
            log.info("headers:" + Arrays.toString(allHeaders));
            log.info("statusCode:" + statusCode);
            log.info("body:" + body);

            return body;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String testPatchJson(String url,String params){
        try {
            //1、创建一个request，携带了method和url(get请求的参数直接加在url后面)
            HttpPatch httpPatch = new HttpPatch(url);
            //1.1、添加请求头
            //httpPatch.addHeader("X-Lemonban-Media-Type", "lemonban.v1");
            httpPatch.addHeader(basicHeader);
            httpPatch.setHeader("Content-Type", "application/json");
            //1.1.1 添加鉴权请求头
            AuthenticationUtils.addToken(httpPatch);

            //1.2、添加请求体（参数）
            StringEntity requestEntity = new StringEntity(params);
            httpPatch.setEntity(requestEntity);

            //2、创建发送请求的客户端，HttpClients是HttpClient的工具类
            CloseableHttpClient Client = HttpClients.createDefault();
            //3、客户端调用发送post请求，立即返回http响应
            CloseableHttpResponse response = Client.execute(httpPatch);
            //response整个响应对象（body、header、statuscode）
            //4.1、获取响应头
            Header[] allHeaders = response.getAllHeaders();
            //4.2、获取状态码
            int statusCode = response.getStatusLine().getStatusCode();
            //4.3、获取响应体
            HttpEntity responseEntity = response.getEntity();
            //4.4、格式化响应体
            String body = EntityUtils.toString(responseEntity);
            //4.5 token存储
            AuthenticationUtils.storeToken(body);

            //5、输出响应
            log.info("headers:" + Arrays.toString(allHeaders));
            log.info("statusCode:" + statusCode);
            log.info("body:" + body);

            return body;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


//    public static void main(String[] args) {
//        String url = "http://test.lemonban.com/futureloan/mvc/api/member/register";
//        String url3 = "http://api.lemonban.com/futureloan/member/login";
//        String params = "{\"mobile_phone\": \"17766665566\",\"pwd\": \"12345678\"}";
//        HttpUtils.testPostJson(url,params);
//    }

    public static String testPostForm(String url,String params){
        try {
            //1、创建一个request，携带了method和url(get请求的参数直接加在url后面)
            HttpPost httpPost = new HttpPost(url);
            //1.1、添加请求头
            //httpPost.addHeader("X-Lemonban-Media-Type", "lemonban.v1");
            httpPost.addHeader(basicHeader);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            //1.1.1 添加鉴权请求头
            AuthenticationUtils.addToken(httpPost);

            //1.2、添加请求体（参数）
            StringEntity requestEntity = new StringEntity(params);
            httpPost.setEntity(requestEntity);

            //2、创建发送请求的客户端，HttpClients是HttpClient的工具类
            CloseableHttpClient Client = HttpClients.createDefault();
            //3、客户端调用发送post请求，立即返回http响应
            CloseableHttpResponse response = Client.execute(httpPost);
            //response整个响应对象（body、header、statuscode）
            //4.1、获取响应头
            Header[] allHeaders = response.getAllHeaders();
            //4.2、获取状态码
            int statusCode = response.getStatusLine().getStatusCode();
            //4.3、获取响应体
            HttpEntity responseEntity = response.getEntity();
            //4.4、格式化响应体
            String body = EntityUtils.toString(responseEntity);
            //4.5 token存储
            AuthenticationUtils.storeToken(body);

            //5、输出响应
            log.info("headers:" + Arrays.toString(allHeaders));
            log.info("statusCode:" + statusCode);
            log.info("body:" + body);

            return body;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
