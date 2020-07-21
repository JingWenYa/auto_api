package com.auto.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import javax.validation.constraints.NotNull;

public class Case {

    @Excel(name = "用例编号")
    @NotNull
    private int caseId;
    @Excel(name = "用例描述")
    @NotNull
    private String caseDesc;
    @Excel(name = "参数")
    @NotNull
    private String caseParams;
    @Excel(name = "接口编号")
    @NotNull
    private String APIId;
    @Excel(name = "期望响应数据")
    @NotNull
    private String expectData;
    @Excel(name = "检验SQL")
    private String sql;

    public Case() {
    }

    public Case(int caseId, String caseDesc, String caseParams, String APIId, String expectData, String sql) {
        this.caseId = caseId;
        this.caseDesc = caseDesc;
        this.caseParams = caseParams;
        this.APIId = APIId;
        this.expectData = expectData;
        this.sql = sql;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getCaseDesc() {
        return caseDesc;
    }

    public void setCaseDesc(String caseDesc) {
        this.caseDesc = caseDesc;
    }

    public String getCaseParams() {
        return caseParams;
    }

    public void setCaseParams(String caseParams) {
        this.caseParams = caseParams;
    }

    public String getAPIId() {
        return APIId;
    }

    public void setAPIId(String APIId) {
        this.APIId = APIId;
    }

    public String getExpectData() {
        return expectData;
    }

    public void setExpectData(String expectData) {
        this.expectData = expectData;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "Case{" +
                "caseId=" + caseId +
                ", caseDesc='" + caseDesc + '\'' +
                ", caseParams='" + caseParams + '\'' +
                ", APIId='" + APIId + '\'' +
                ", expectData='" + expectData + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }
}
