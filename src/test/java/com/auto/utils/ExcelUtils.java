package com.auto.utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.auto.constants.Constant;
import com.auto.pojo.WriteBackData;
import org.apache.poi.ss.usermodel.*;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

//        /*把两个数组关联起来
//        * 方法1：新增一个类，一个属性为API 一个属性是List<Case>
//        * 方法2：把API和Case放到数组中{{API,Case},{API,Case},{API,Case}}----采用第二种
//        * */

    public static List<WriteBackData> wbdList = new ArrayList<WriteBackData>();

    public static<T> List<T> read(String url,int sheetIndex,Class<T> clazz){

        FileInputStream fis = null;
        try {
            //加载excel的流对象
            fis = new FileInputStream(url);
            //导入参数对象（默认值：读取第一个sheet且只读取第一个，表头是sheet的第一行且只有一行）
            ImportParams params = new ImportParams();
            params.setNeedVerify(true);
            params.setStartSheetIndex(sheetIndex);
            //工具解析excel封装成List对象
            List<T> list = ExcelImportUtil.importExcel(fis, clazz, params);
            return list;
            //如果输出最后面的内容都是空，说明excel中最后几行，只是清空了内容，没有进行删除
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(fis);
        }
        return null;
    }

    public static void write(String url,int sheetIndex){

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(url);
            //2、打开
            Workbook workbook = WorkbookFactory.create(fis);
            //3、获取sheet
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            //4、获取要写的行和列定位到单元格（cell）
            //4.1、获取第x行
            for (WriteBackData wbd : wbdList){
                Row row = sheet.getRow(wbd.getRowNnum());
                //4.2、获取第x列
                Cell cell = row.getCell(wbd.getCellNum(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                //5、修改内容
                cell.setCellValue(wbd.getContent());
            }
            //到目前为止所有的操作都是在java里面
            fos = new FileOutputStream(Constant.EXCEL_PATH);
            workbook.write(fos);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(fis);
            close(fos);
        }

    }

    private static void close(Closeable fis) {
        try {
            if (fis != null){
                fis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
