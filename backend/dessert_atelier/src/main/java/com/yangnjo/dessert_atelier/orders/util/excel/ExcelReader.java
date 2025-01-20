package com.yangnjo.dessert_atelier.orders.util.excel;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelReader {

    public static <T> List<T> convert(MultipartFile file, Class<T> clazz) {
        ExcelFileExtender efe = getExcelFileExtender(file);

        try (InputStream inputStream = file.getInputStream()) {
            try (Workbook workbook = efe == ExcelFileExtender.XLSX ? new XSSFWorkbook(inputStream)
                    : new HSSFWorkbook(inputStream)) {
                Sheet sheet = workbook.getSheetAt(0);
                if (sheet == null) {
                    throw new IllegalArgumentException("Excel file is empty");
                }

                Row headerRow = sheet.getRow(0);
                if (headerRow == null) {
                    throw new IllegalArgumentException("No header row found in Excel");
                }

                Map<Integer, Field> columnFieldMap = mapColumnsToFields(headerRow, clazz);

                List<T> dtoList = new ArrayList<>();
                for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if (row == null || isRowEmpty(row)) {
                        break; // 데이터가 없는 행 발견 시 종료
                    }

                    T dto = clazz.getDeclaredConstructor().newInstance();
                    for (Map.Entry<Integer, Field> entry : columnFieldMap.entrySet()) {
                        Cell cell = row.getCell(entry.getKey());
                        Field field = entry.getValue();
                        Object cellValue = getCellValue(cell, field.getType());
                        field.setAccessible(true);

                        try {
                            field.set(dto, cellValue);
                        } catch (Exception e) {
                            System.out.println(e.getLocalizedMessage());
                        }
                    }
                    dtoList.add(dto);
                }
                return dtoList;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert Excel to DTO", e);
        }

    }

    private static ExcelFileExtender getExcelFileExtender(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        if (fileExtension.equals("xlsx")) {
            return ExcelFileExtender.XLSX;
        } else if (fileExtension.equals("xls")) {
            return ExcelFileExtender.XLS;
        }

        throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
    }

    private static <T> Map<Integer, Field> mapColumnsToFields(Row headerRow, Class<T> clazz) {
        Map<Integer, Field> columnFieldMap = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();

        for (int colIndex = 0; colIndex < headerRow.getLastCellNum(); colIndex++) {
            Cell headerCell = headerRow.getCell(colIndex);
            if (headerCell == null)
                continue;

            String headerName = headerCell.getStringCellValue().trim();
            for (Field field : fields) {
                ExcelHeader excelHeader = field.getAnnotation(ExcelHeader.class);
                if (excelHeader != null && excelHeader.value().equals(headerName)) {
                    columnFieldMap.put(colIndex, field);
                    break;
                }
            }
        }

        return columnFieldMap;
    }

    private static Object getCellValue(Cell cell, Class<?> fieldType) {
        if (cell == null) {
            return null;
        }

        String cellValue = null;
        if (cell.getCellType() == CellType.STRING) {
            cellValue = cell.getStringCellValue().trim().toLowerCase(); // 소문자로 변환
        }

        // Boolean 타입 필드일 경우 처리
        if (fieldType == Boolean.class || fieldType == boolean.class) {
            if (cellValue != null) {
                switch (cellValue) {
                    case "yes":
                    case "y":
                        return true;
                    case "no":
                    case "n":
                        return false;
                }
            }
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return fieldType == String.class ? cell.getStringCellValue() : null;
            case NUMERIC:
                if (fieldType == Integer.class || fieldType == int.class) {
                    return (int) cell.getNumericCellValue();
                } else if (fieldType == Long.class || fieldType == long.class) {
                    return (long) cell.getNumericCellValue();
                } else if (fieldType == Double.class || fieldType == double.class) {
                    return cell.getNumericCellValue();
                } else if (fieldType == Float.class || fieldType == float.class) {
                    return (float) cell.getNumericCellValue();
                }
                return null;
            case BOOLEAN:
                return fieldType == Boolean.class || fieldType == boolean.class ? cell.getBooleanCellValue() : null;
            case FORMULA:
                return getCellValue(cell, fieldType); // Re-evaluate the cell if it's a formula.
            default:
                return null;
        }
    }

    private static boolean isRowEmpty(Row row) {
        for (Cell cell : row) {
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false; // 데이터가 있는 셀이 발견되면 비어있지 않음
            }
        }
        return true; // 모든 셀이 비어있으면 true 반환
    }
}