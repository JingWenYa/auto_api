package com.auto.cases;

import com.auto.constants.Constant;
import com.auto.utils.AuthenticationUtils;
import com.auto.utils.DataUtils;
import com.auto.utils.ExcelUtils;
import com.auto.pojo.API;
import com.auto.pojo.Case;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class LoginCase extends BaseCase {

    /**log4j用法
     * 1、添加坐标
     * 2、拷贝log4j.property文件
     * 3、创建log对象
     */
    //在哪个类中定义 就传哪个类的字节码
    private Logger log = Logger.getLogger(LoginCase.class);

    @BeforeSuite
    public void init() {
        AuthenticationUtils.env.put(Constant.REGISTER_MOBILEPHONE_TEXT, Constant.REGISTER_MOBILEPHONE_VALUE);
        AuthenticationUtils.env.put(Constant.REGISTER_PASSWORD_TEXT, Constant.REGISTER_PASSWORD_VALUE);
        AuthenticationUtils.env.put(Constant.LOGIN_MOBILEPHONE_TEXT, Constant.LOGIN_MOBILEPHONE_VALUE);
        AuthenticationUtils.env.put(Constant.LOGIN_PASSWORD_TEXT, Constant.LOGIN_PASSWORD_VALUE);
    }

    @Test(dataProvider = "datas",description = "登陆测试")
    public void testLogin(API api, Case c) {

        log.info("============0、参数化替换===============" + api.getUrl());
        //0、参数化替换
        String params = paramReplace(c.getCaseParams());
        String sql = paramReplace(c.getSql());
        c.setCaseParams(params);
        c.setSql(sql);

        //测试注册接口
        // 面向对象思想：高内聚(当前方法核心功能) 低耦合(依赖其他类完成的功能)
        log.info("============1、调用接口==============");
        //1、调用接口
        String content = call(api, c);
        log.info("============2、添加回写内容==============");
        //2、添加回写内容
        addWriteBackData(c.getCaseId(),Constant.ACTUAL_RESPONSE_DATA_CELLNUM,content);
        log.info("============3、断言响应结果==============");
        // 3、断言响应结果
        Boolean assertResponseData = assertResponseData(c, content);
        System.out.println("断言结果"+assertResponseData);
        log.info("断言结果"+assertResponseData);

        log.info("=========4、添加断言回写内容===========");
        // 4、添加断言回写内容
        String passContent = (assertResponseData) ? "pass" : "fail";
        addWriteBackData(c.getCaseId(),Constant.ASSERT_CELLNUM,passContent);
}

    @AfterTest
    public void finish(){
        log.info("=========批量回写===========");
        ExcelUtils.write(Constant.EXCEL_PATH,1);
    }

    @DataProvider(name = "datas")
    public Object[][] datas(){
        Object[][] datas = DataUtils.getAPIAndCaseByAPIId("2");
        return datas;
    }
}
