package com.yangnjo.dessert_atelier.orders.util.excel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExcelFileExtender {
    XLS(".xls"),
    XLSX(".xlsx");

    private String fileFormat;
}
