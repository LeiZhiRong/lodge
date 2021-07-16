package com.shags.lodge.excel;

import cn.afterturn.easypoi.excel.export.styler.AbstractExcelExportStyler;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import org.apache.poi.ss.usermodel.*;

/**
 * excel表格导出格式设置工具类
 * @author 雷智荣
 */
public class ExcelExportStylerImpl extends AbstractExcelExportStyler
        implements IExcelExportStyler {
    public ExcelExportStylerImpl(Workbook workbook) {
        super.createStyles(workbook);
    }

    @Override
    public CellStyle getHeaderStyle(short i) {
        CellStyle titleStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setBold(true); // 字体加粗
        titleStyle.setFont(font);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);//居中
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        return titleStyle;
    }

    @Override
    public CellStyle getTitleStyle(short i) {
        CellStyle titleStyle = workbook.createCellStyle();
        Font font = this.workbook.createFont();
        font.setFontHeightInPoints((short) 9);
        font.setBold(true); // 字体加粗
        titleStyle.setFont(font);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);//居中
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        titleStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());//设置颜色（黄色）
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setBorderRight(BorderStyle.THIN);//设置右边框
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);
         titleStyle.setWrapText(true);
        return titleStyle;
    }
    @Override
    public CellStyle stringSeptailStyle(Workbook workbook, boolean isWarp) {
        return getCellStyle(workbook, isWarp);
    }

    private CellStyle getCellStyle(Workbook workbook, boolean isWarp) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 9);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);//设置右边框
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setDataFormat(STRING_FORMAT);
        if (isWarp) {
            style.setWrapText(true);
        }
        return style;
    }

    @Override
    public CellStyle stringNoneStyle(Workbook workbook, boolean isWarp) {
        return getCellStyle(workbook, isWarp);
    }



}
