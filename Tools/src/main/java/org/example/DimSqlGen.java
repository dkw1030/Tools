package org.example;

import fileOperate.text.TextReader;
import fileOperate.text.TextWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class DimSqlGen {

    static String TABLE_NAME = "my_table"; // 设置表名
    static String TABLE_COMMENT = "my_table_comment";

    public static void main(String[] args) throws IOException {
        List<String> result = new ArrayList<>();
        List<String> strings = TextReader.readFileInChunks("D:\\Code\\OutPutFiles\\类型处理后.txt");
        for (String string : strings) {
            String[] split = string.split("\n");
            for (String s : split) {
                String[] split1 = s.split(" ");
                String code = split1[0];
                result.add(code);
            }
        }
        // 修复原来的语法错误并实现所需功能
        // 创建表的SQL语句
        String createSql = "CREATE TABLE `" + TABLE_NAME + "` (`value` INTEGER) COMMENT='" + TABLE_COMMENT + "';";

        // 分批处理result中的数据，每批1000个
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(createSql).append("\n"); // 添加创建表的语句

        int batchSize = 1000;
        for (int i = 0; i < result.size(); i += batchSize) {
            int endIndex = Math.min(i + batchSize, result.size());
            List<String> batch = result.subList(i, endIndex);

            StringBuilder insertSql = new StringBuilder("INSERT INTO `" + TABLE_NAME + "` (`value`) VALUES ");
            insertSql.append("(");
            for (int j = 0; j < batch.size(); j++) {
                insertSql.append(batch.get(j));
                if (j < batch.size() - 1) {
                    insertSql.append(", ");
                }
            }
            insertSql.append(")");
            insertSql.append(";\n");
            sqlBuilder.append(insertSql.toString());

            TextWriter.cycleTextToOutput(sqlBuilder.toString(), "sql");
        }

    }
}