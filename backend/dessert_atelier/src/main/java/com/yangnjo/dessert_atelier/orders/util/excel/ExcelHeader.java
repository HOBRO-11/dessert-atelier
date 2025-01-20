package com.yangnjo.dessert_atelier.orders.util.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelHeader {
    /*
     * 엑셀 헤더 이름을 작성하며 , Dto 클래스내 매핑을 원하는 필드위에는 무조건 작성할 것
     */
    String value();
}
