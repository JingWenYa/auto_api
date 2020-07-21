package com.auto.pojo;

public class WriteBackData {

    //回写excel的行号
    private int rowNnum;
    //回写excel的列号
    private int cellNum;
    //回写excel的内容
    private String content;

    public WriteBackData() {
    }

    public WriteBackData(int rowNnum, int cellNum, String content) {
        this.rowNnum = rowNnum;
        this.cellNum = cellNum;
        this.content = content;
    }

    public int getRowNnum() {
        return rowNnum;
    }

    public void setRowNnum(int rowNnum) {
        this.rowNnum = rowNnum;
    }

    public int getCellNum() {
        return cellNum;
    }

    public void setCellNum(int cellNum) {
        this.cellNum = cellNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "WriteBackData{" +
                "rowNnum=" + rowNnum +
                ", cellNum=" + cellNum +
                ", content='" + content + '\'' +
                '}';
    }
}
