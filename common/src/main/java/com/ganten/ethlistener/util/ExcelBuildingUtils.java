package com.ganten.ethlistener.util;

import com.ganten.ethlistener.annotation.ExcelColumn;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public class ExcelBuildingUtils {

    public static <T> Workbook singleSheetWorkbook(List<T> dataList, String sheetName) {
        Workbook workbook = ExcelBuildingUtils.createWorkbook();
        ExcelBuildingUtils.addDataToNewSheet(workbook, dataList, sheetName);
        return workbook;
    }

    /**
     * 添加数据到工作表的新sheet
     *
     * @param workbook  工作簿
     * @param dataList  数据列表
     * @param sheetName sheet名称
     * @param <T>       数据类型
     */
    public static <T> void addDataToNewSheet(Workbook workbook, List<T> dataList, String sheetName) {
        if (dataList == null || dataList.isEmpty()) {
            throw new IllegalArgumentException("The data list is empty or null.");
        }
        Sheet sheet = workbook.createSheet(sheetName);
        Row headerRow = sheet.createRow(0);
        Field[] fields = dataList.get(0).getClass().getDeclaredFields();

        int headerIndex = 0;
        for (Field field : fields) {
            field.setAccessible(true);
            ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
            String headerName = field.getName();
            if (Objects.nonNull(excelColumn)) {
                if (!excelColumn.need()) {
                    continue;
                }
                if (StringUtils.isEmpty(excelColumn.name())) {
                    headerName = excelColumn.name();
                }
            }
            Cell cell = headerRow.createCell(headerIndex++);
            cell.setCellValue(headerName);
            cell.setCellStyle(createHeaderCellStyle(workbook));
        }

        // Fill data rows
        int rowNum = 1;
        for (T item : dataList) {
            Row row = sheet.createRow(rowNum++);
            headerIndex = 0;
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                    if (Objects.nonNull(excelColumn)) {
                        if (!excelColumn.need()) {
                            continue;
                        }
                    }
                    if (field.get(item) != null) {
                        Cell cell = row.createCell(headerIndex++);
                        Object value = field.get(item);
                        if (value != null) {
                            cell.setCellValue(value.toString());
                        }
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new RuntimeException("Failed to access field value.", e);
                }
            }
        }
    }

    public static Workbook createWorkbook() {
        return new XSSFWorkbook();
    }

    /**
     * 导出工作簿
     *
     * @param workbook   工作簿
     * @param exportPath 导出路径
     */
    public static void exportWorkbook(Workbook workbook, String exportPath) {
        try {
            try (FileOutputStream fileOut = new FileOutputStream(exportPath)) {
                workbook.write(fileOut);
            } finally {
                workbook.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to export workbook.", e);
        }
    }

    /**
     * 创建表头样式
     *
     * @param workbook 工作簿
     * @return 表头样式
     */
    private static CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
}
