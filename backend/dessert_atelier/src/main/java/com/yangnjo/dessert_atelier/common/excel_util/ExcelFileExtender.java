package com.yangnjo.dessert_atelier.common.excel_util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExcelFileExtender {
    XLS(".xls"),
    XLSX(".xlsx");

    private String fileFormat;
}
