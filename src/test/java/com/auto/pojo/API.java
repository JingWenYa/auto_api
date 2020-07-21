package com.auto.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class API {
    //缺少API和excel的映射关系（API字段和Excel列的关系）
    //加上@Excel注解后，easypoi就知道了映射关系

    /*校验excel中加载的内容是否为空 为空跳过不加载
    * 1、在pojo类中的表头增加@NotNull注解
    * 2、设置导入的参数对象 params.setNeedVerify(true);
    * 3、导入坐标hibernate-validator&javax.el-api
    * */

    //接口编号
    @Excel(name = "接口编号")
    @NotNull
    private String id;
    //接口名称
    @Excel(name = "接口名称")
    @NotNull
    private String name;
    //接口类提交方式
    @Excel(name = "接口提交方式")
    @NotNull
    private String type;
    //接口地址
    @Excel(name = "接口地址")
    @NotNull
    private String url;
    //参数类型
    @Excel(name = "参数类型")
    @NotNull
    private String contentType;


    public API() {
    }

    public API(String id, String name, String type, String url, String contentType) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.url = url;
        this.contentType = contentType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "API{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
