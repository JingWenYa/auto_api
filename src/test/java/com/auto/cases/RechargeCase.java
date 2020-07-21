package com.auto.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.auto.constants.Constant;
import com.auto.pojo.API;
import com.auto.pojo.Case;
import com.auto.pojo.JsonPathValidate;
import com.auto.utils.AuthenticationUtils;
import com.auto.utils.DataUtils;
import com.auto.utils.HttpUtils;
import com.auto.utils.SQLUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

public class RechargeCase extends BaseCase{

    @Test(dataProvider = "datas",description = "充值接口")
    @Description("充值接口")
    public void testRecharge(API api, Case c){
        //测试注册接口
        //面向对象思想：高内聚(当前方法核心功能) 低耦合(依赖其他类完成的功能)

        //0、参数化替换
        String params = paramReplace(c.getCaseParams());
        String sql = paramReplace(c.getSql());
        c.setCaseParams(params);
        c.setSql(sql);

        //1、数据库前置查询结果
        Object beforeSQLResult = SQLUtils.query(sql);
        //2、调用接口
        String content = call(api,c);
        //3、添加接口响应回写内容
        addWriteBackData(c.getCaseId(), Constant.ACTUAL_RESPONSE_DATA_CELLNUM, content);
        //4、断言响应结果
        Boolean assertResponseData = assertResponseData(c, content);
        System.out.println("响应断言结果" + assertResponseData);
        //5、数据库后置查询结果
        Object afterSQLResult = SQLUtils.query(sql);
        //6、数据库断言
        boolean assertSQL = true;
        if (StringUtils.isNoneBlank(sql)){
            assertSQL = assertSQL1(beforeSQLResult, afterSQLResult,c);
            System.out.println("数据库断言结果：" + assertSQL);
            System.out.println(sql);
        }
        //7、添加断言回写内容
        String passContent = (assertResponseData && assertSQL) ? "pass" : "fail";
        addWriteBackData(c.getCaseId(), Constant.ASSERT_CELLNUM, passContent);
    }

    public boolean assertSQL(Object beforeSQLResult, Object afterSQLResult,Case c){
        //beforeSQLResult afterSQLResult为bigDecimal类型的object
        BigDecimal beforeSQLBigDecimal = new BigDecimal(beforeSQLResult.toString());
        BigDecimal afterSQLBigDecimal = new BigDecimal(afterSQLResult.toString());
        Object amount = JSONPath.read(c.getCaseParams(), "$.amount");
        BigDecimal amountBigDecimal = new BigDecimal(amount.toString());
        if (afterSQLBigDecimal.subtract(beforeSQLBigDecimal).compareTo(amountBigDecimal) == 0){
            return true;
        }else{
            return false;
        }
    }

    @Step("执行充值sql断言")
    public boolean assertSQL1(Object beforeSQLResult, Object afterSQLResult,Case c){
        double beforeMoney =  Double.parseDouble(beforeSQLResult.toString());
        double afterMoney =  Double.parseDouble(afterSQLResult.toString());
        Object moneyStr = JSONPath.read(c.getCaseParams(), "$.amount");
        double money = Double.parseDouble(moneyStr.toString());
        if (afterMoney - beforeMoney == money){
            return true;
        }else{
            return false;
        }
    }


    @DataProvider(name = "datas")
    public Object[][] datas(){
        Object[][] datas = DataUtils.getAPIAndCaseByAPIId("3");
        return datas;
    }

//    @DataProvider(name = "datas")
//    public Object[][] datas(){
//
//        Object[][] datas = ExcelUtils.read("src/cases_v3.xlsx");
//        return datas;
//    }

}
