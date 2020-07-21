package com.auto.utils;

import com.auto.constants.Constant;
import com.auto.pojo.API;
import com.auto.pojo.Case;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {
    //把API和Case放到数组中{{API,Case},{API,Case},{API,Case}}

    public static List<API> apiList = ExcelUtils.read(Constant.EXCEL_PATH, 0, API.class);
    public static List<Case> caseList = ExcelUtils.read(Constant.EXCEL_PATH, 1, Case.class);

    //根据传入的接口编号 获取到API和Case
    public static Object[][] getAPIAndCaseByAPIId(String apiId){

        API wantAPI = null;
        //1、获取wantAPI
        for (API api : apiList){
            //传入的apiId和遍历的APIId相等
            if (apiId.equals(api.getId())){
                wantAPI = api;
                break;
            }
        }

//        2、获取wantCases
//        for (int i = 0; i < caseList.size(); i++){
//            if (apiId.equals(caseList.get(i).getAPIId())){
//                wantCases.add(caseList.get(i));
//            }
//        }
        List<Case> wantCases = new ArrayList<Case>();
        for (Case c : caseList){
            if (apiId.equals(c.getAPIId())){
                wantCases.add(c);
            }
        }

        //3、把wantAPI和wantCases合并 存入到二维数组中
        //{{API,Case},{API,Case},{API,Case}}
        Object[][] datas = new Object[wantCases.size()][2];
        for (int i = 0; i < wantCases.size(); i++){
            //第一个参数
            datas[i][0] = wantAPI;
            //第二个参数
            datas[i][1] = wantCases.get(i);
        }
        return datas;
    }
}
