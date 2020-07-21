package com.auto.cases;

import com.auto.constants.Constant;
import com.auto.utils.DataUtils;
import com.auto.pojo.API;
import com.auto.pojo.Case;
import com.auto.utils.SQLUtils;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegisterCase extends BaseCase{

    @Test(dataProvider = "datas",description = "注册接口")
    public void testRegister(API api, Case c){
        //测试注册接口（数据断言必须在接口执行前后都查询）
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
        //3、添加回写内容
        addWriteBackData(c.getCaseId(),Constant.ACTUAL_RESPONSE_DATA_CELLNUM, content);
        //4、断言响应结果
        Boolean assertResponseData = assertResponseData(c, content);
        System.out.println("响应断言结果：" + assertResponseData);
        //5、数据库后置查询结果
        Object afterSQLResult = SQLUtils.query(sql);
        //6、数据库断言
        boolean assertSQL = true;
        if (StringUtils.isNoneBlank(sql)){
            assertSQL = assertSQL(beforeSQLResult, afterSQLResult);
            System.out.println("数据库断言结果：" + assertSQL);
        }
        //7、添加断言回写内容
        String passContent = (assertResponseData && assertSQL) ? "pass" : "fail";
        addWriteBackData(c.getCaseId(), Constant.ASSERT_CELLNUM, passContent);
    }

    @Step("执行注册sql断言")
    public boolean assertSQL(Object beforeSQLResult, Object afterSQLResult){
        //beforeSQLResult为long类型的object
        if ((Long)beforeSQLResult == 0 && (Long)afterSQLResult == 1){
            return true;
        }else{
            return false;
        }
    }

    @DataProvider(name = "datas")
    public Object[][] datas(){
        Object[][] datas = DataUtils.getAPIAndCaseByAPIId("1");
        return datas;
    }
}
