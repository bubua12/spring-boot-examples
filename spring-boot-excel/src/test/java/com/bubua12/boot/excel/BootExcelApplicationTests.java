package com.bubua12.boot.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.bubua12.boot.excel.entity.UserData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author bubua12
 * @since 2026/5/28 13:18
 */
@SpringBootTest
public class BootExcelApplicationTests {

    @Test
    public void exportExcel() {
        // 准备数据
        List<UserData> data = new ArrayList<>();
        UserData user1 = new UserData();
        user1.setName("张三");
        user1.setAge(25);
        user1.setPhone("13800138000");
        data.add(user1);

        UserData user2 = new UserData();
        user2.setName("李四");
        user2.setAge(30);
        user2.setPhone("13900139000");
        data.add(user2);

        // 一行代码写出
        EasyExcel.write("用户数据.xlsx", UserData.class).sheet("用户表").doWrite(data);
    }

    @Test
    public void importExcel() {
        // 方式一：用匿名类
        List<UserData> result = new ArrayList<>();

        EasyExcel.read("用户数据.xlsx", UserData.class, new ReadListener<UserData>() {
            @Override
            public void invoke(UserData data, AnalysisContext context) {
                // 每读一行回调一次
                result.add(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 全部读完回调
                System.out.println("读取完成，共 " + result.size() + " 条");
            }
        }).sheet().doRead();
    }
}
