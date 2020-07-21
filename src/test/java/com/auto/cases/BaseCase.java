package com.auto.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.auto.pojo.API;
import com.auto.pojo.Case;
import com.auto.pojo.JsonPathValidate;
import com.auto.pojo.WriteBackData;
import com.auto.utils.AuthenticationUtils;
import com.auto.utils.ExcelUtils;
import com.auto.utils.HttpUtils;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.Set;

public class BaseCase {

    @Step("接口调用{api.url}/{c.caseParams}")
    public static String call(API api, Case c){
        String url = api.getUrl();
        String params = c.getCaseParams();
        String type = api.getType();
        String contentType = api.getContentType();
        //执行接口
        return HttpUtils.call(url, params, type, contentType);
    }

//    public static void addWriteBackData(Case c,String content){
//        //回写实际响应数据的行
//        String caseId = c.getCaseId();
//        //把字符串转为int
//        int rowNum = Integer.parseInt(caseId);
//        //回写实际响应数据的列
//        int cellnum = Constant.ACTUAL_RESPONSE_DATA_CELLNUM;
//        WriteBackData wbd = new WriteBackData(rowNum, cellnum, content);
//        //把响应内容添加到回写集合中
//        ExcelUtils.wbdList.add(wbd);
//    }
    /**
     *
     * @param
     * @param content
     */
    @Step("回写excel")
    public static void addWriteBackData(int rowNum,int cellnum,String content){
        WriteBackData wbd = new WriteBackData(rowNum, cellnum, content);
        //把响应内容添加到回写集合中
        ExcelUtils.wbdList.add(wbd);
    }

    /**
     * 断言接口响应内容 第一种判断采用json数组的多关键字匹配 第二种采用json对象的等值匹配
     * @param c
     * @param content
     * @return
     */
    @Step("断言响应内容{content}")
    public Boolean assertResponseData(Case c,String content){
        //获取期望数据
        String expectData = c.getExpectData();
        Object parse = JSON.parse(expectData);
        if (parse instanceof JSONArray){
            //期望数据转成list集合
            List<JsonPathValidate> list = JSON.parseArray(expectData, JsonPathValidate.class);
            //循环集合
            for (JsonPathValidate jpv : list){
                //通过jsonpath表达式获取实际响应中的实际值
                Object actualValue = JSONPath.read(content, jpv.getExpression());
                //获取期望值
                String expectValue = jpv.getValue();
                //拿实际值和期望值做比较
                boolean flag = expectValue.equals(actualValue.toString());
                if (!flag){//其中的某个断言已经失败
                    return false;
                }
            }
            //在循环的过程中flag都为true
            return true;
        }else if (parse instanceof JSONObject){
            boolean flag = expectData.equals(content);
            return flag;
        }
        return false;
    }


    /**
     *参数化替换方法
     * @param params
     */

    @Step("参数化替换{params}")
    public String paramReplace(String params) {
        if (StringUtils.isBlank(params)){
            return "";
        }
        Set<String> keySet = AuthenticationUtils.env.keySet();
        for (String key : keySet){
            String value = AuthenticationUtils.env.get(key);
            params = params.replace(key, value);
        }
        return params;
    }
}
