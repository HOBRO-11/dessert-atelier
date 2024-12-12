package com.yangnjo.dessert_atelier.common.excel_util;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {

    public static <T> void write(List<T> dataList, String path, String filename, ExcelFileExtender efe) {

        if (dataList == null) {
            return;
        }

        if (dataList.isEmpty()) {
            return;
        }

        if (efe == null) {
            throw new IllegalArgumentException("Excel file extender is null");
        }

        exportToExcel(dataList, path, filename, efe);

    }

    private static <T> void exportToExcel(List<T> data, String path, String fileName, ExcelFileExtender efe) {

        try (Workbook workbook = efe == ExcelFileExtender.XLSX ? new XSSFWorkbook() : new HSSFWorkbook();) {

            Sheet sheet = workbook.createSheet("Data");

            Field[] fields = data.get(0).getClass().getDeclaredFields();

            Row headerRow = sheet.createRow(0);
            int colIndex = 0;
            for (Field field : fields) {
                if (field.isAnnotationPresent(ExcelHeader.class)) {
                    ExcelHeader header = field.getAnnotation(ExcelHeader.class);
                    Cell cell = headerRow.createCell(colIndex++);
                    cell.setCellValue(header.value());
                }
            }

            int rowIndex = 1;
            for (T obj : data) {
                Row row = sheet.createRow(rowIndex++);
                colIndex = 0;
                for (Field field : fields) {
                    if (field.isAnnotationPresent(ExcelHeader.class)) {
                        field.setAccessible(true);
                        Object value = field.get(obj);
                        Cell cell = row.createCell(colIndex++);
                        setCellValue(cell, value);
                    }
                }
            }

            for (int i = 0; i < fields.length; i++) {
                sheet.autoSizeColumn(i);
            }

            String filename = generateFilename(path, fileName, efe);
            try (FileOutputStream fileOut = new FileOutputStream(filename);) {
                workbook.write(fileOut);
            }
            System.out.println("Excel file generated: " + filename);
        } catch (Exception e) {

        }
    }

    private static void setCellValue(Cell cell, Object value) {
        if (value == null) {
            cell.setCellValue("");
        } else if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value ? "Y" : "N");
        } else {
            cell.setCellValue(value.toString());
        }
    }

    private static String generateFilename(String path, String filename, ExcelFileExtender efe) {
        if (filename == null) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
            String formattedDateTime = now.format(formatter);

            return path + formattedDateTime + efe.getFileFormat();
        }
        return path + filename + efe.getFileFormat();
    }
}
