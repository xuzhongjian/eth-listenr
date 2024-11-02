
package com.ganten.ethlistener.util;

import com.ganten.ethlistener.annotation.CSVColumn;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class CSVImporter {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static <T> List<T> importFromCSV(String filePath, Class<T> clazz) {
        List<T> objects = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                return objects;
            }

            Field[] fields = Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(CSVColumn.class))
                    .sorted(Comparator.comparingInt(f -> f.getAnnotation(CSVColumn.class).order()))
                    .toArray(Field[]::new);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                T obj = clazz.getDeclaredConstructor().newInstance();

                for (int i = 0; i < values.length; i++) {
                    String value = values[i].trim();
                    Field field = fields[i];
                    field.setAccessible(true);
                    setFieldValue(obj, field, value);
                }

                objects.add(obj);
            }
        } catch (IOException | ReflectiveOperationException | ParseException e) {
            log.error("Failed to import data from CSV file.", e);
        }

        return objects;
    }

    private static void setFieldValue(Object obj, Field field, String value)
            throws IllegalAccessException, ParseException {
        if (field.getType() == int.class || field.getType() == Integer.class) {
            field.set(obj, Integer.parseInt(value));
        } else if (field.getType() == double.class || field.getType() == Double.class) {
            field.set(obj, Double.parseDouble(value));
        } else if (field.getType() == long.class || field.getType() == Long.class) {
            field.set(obj, Long.parseLong(value));
        } else if (field.getType() == Date.class) {
            field.set(obj, DATE_FORMAT.parse(value));
        } else {
            field.set(obj, value);
        }
    }
}
