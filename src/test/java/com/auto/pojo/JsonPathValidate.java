package com.auto.pojo;

public class JsonPathValidate {

    //断言预期jsonpath的值
    private String value;
    //断言预期jsonpath的表达式
    private String expression;

    public JsonPathValidate() {
    }

    public JsonPathValidate(String value, String expression) {
        this.value = value;
        this.expression = expression;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "JsonPathValidate{" +
                "value='" + value + '\'' +
                ", expression='" + expression + '\'' +
                '}';
    }
}
