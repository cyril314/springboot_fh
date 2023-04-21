package com.fit.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fit.entity.PageData;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

/**
 * 导入到EXCEL
 * 类名称：ObjectExcelView.java
 *
 * @author
 * @version 1.0
 */
public class ObjectExcelView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = sdf.format(new Date());
        //设置下载头部文件信息
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
        //创建sheet，相当于一个excelsheel
        Sheet sheet = workbook.createSheet("sheet1");
        //得到excel标题内容
        List<String> titles = (List<String>) model.get("titles");
        int len = titles.size();
        //设置单元格样式
        CellStyle cellStyle = workbook.createCellStyle(); //标题样式
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        //设置字体
        Font headerFont = workbook.createFont();    //标题字体
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 11);
        cellStyle.setFont(headerFont);
        short width = 20, height = 25 * 20;
        sheet.setDefaultColumnWidth(width);
        //创建第一行，用来放标题
        Row header = sheet.createRow(0);
        for (int i = 0; i < len; i++) { //设置标题
            String title = titles.get(i);
            Cell cell = header.createCell(i);
            cell.setCellValue(title);
            cell.setCellStyle(cellStyle);
        }
        header.setHeight(height);
        //设置内容样式
        CellStyle contentStyle = workbook.createCellStyle(); //内容样式
        contentStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        //得到controller传过来导出的数据，并填充到每一行中
        List<PageData> varList = (List<PageData>) model.get("varList");
        int varCount = varList.size();
        for (int i = 0; i < varCount; i++) {
            Row userRow = sheet.createRow(i + 1);
            PageData vpd = varList.get(i);
            int mapLen = vpd.size();
            for (int j = 0; j < mapLen; j++) {
                String varStr = vpd.getString("var" + (j + 1)) != null ? vpd.getString("var" + (j + 1)) : "";
                Cell cell = userRow.createCell(j);
                cell.setCellValue(varStr);
                cell.setCellStyle(contentStyle);
            }
        }
    }
}
