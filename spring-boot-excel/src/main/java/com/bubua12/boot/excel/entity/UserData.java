package com.bubua12.boot.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 定义数据模型
 *
 * @author bubua12
 * @since 2026/5/28 13:16
 */
@Data
public class UserData {

    /**
     * 告诉 EasyExcel："这个字段对应 Excel 里叫'姓名'的那一列"
     */
    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("年龄")
    private Integer age;

    @ExcelProperty("手机号")
    private String phone;
}
