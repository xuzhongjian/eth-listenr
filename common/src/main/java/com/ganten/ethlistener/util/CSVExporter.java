package com.ganten.ethlistener.util;

import com.ganten.ethlistener.annotation.CSVColumn;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Slf4j
public class CSVExporter {

    public static <T> void exportToCSV(List<T> objects, String filePath) {
        if (objects == null || objects.isEmpty()) {
            log.error("No data to export.");
            return;
        }
        try {
            // 确保文件和父目录存在
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // 创建父目录（如果不存在）
                file.createNewFile();          // 创建文件
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                Class<?> clazz = objects.get(0).getClass();

                // 按照注解中的顺序获取字段
                Field[] fields = Arrays.stream(clazz.getDeclaredFields())
                        .filter(field -> field.isAnnotationPresent(CSVColumn.class))
                        .peek(field -> field.setAccessible(true))
                        .sorted(Comparator.comparingInt(f -> f.getAnnotation(CSVColumn.class).order()))
                        .toArray(Field[]::new);

                // 写入每个对象的字段值
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (T obj : objects) {
                    for (int i = 0; i < fields.length; i++) {
                        Object value = fields[i].get(obj);
                        String cellValue = formatValue(value, dateFormat);
                        writer.write(cellValue);
                        if (i < fields.length - 1) {
                            writer.write(",");
                        }
                    }
                    writer.newLine();
                }

            } catch (IOException | IllegalAccessException e) {
                log.error("Failed to export data to CSV file.", e);
            }
        } catch (IOException e) {
            log.error("Failed to create file.", e);
        }
    }

    private static String formatValue(Object value, SimpleDateFormat dateFormat) {
        if (value instanceof Date) {
            return dateFormat.format((Date) value);
        } else if (value != null) {
            return value.toString();
        } else {
            return "";
        }
    }
}
