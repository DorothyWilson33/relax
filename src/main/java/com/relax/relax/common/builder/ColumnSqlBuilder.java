package com.relax.relax.common.builder;

import com.relax.relax.common.annotation.RelaxColumn;
import com.relax.relax.common.annotation.RelaxId;
import com.relax.relax.common.enums.ColumnType;
import com.relax.relax.common.utils.RegexUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class ColumnSqlBuilder {
    public static String buildColumnSql(Field field) {
        StringBuilder createTableSql = new StringBuilder();
        String columnName = getColumnName(field);
        createTableSql.append(columnName);

        if (field.isAnnotationPresent(RelaxColumn.class) && !field.getAnnotation(RelaxColumn.class).type().isEmpty()) {
            RelaxColumn relaxColumn = field.getAnnotation(RelaxColumn.class);
            processColumnAnnotation(relaxColumn, createTableSql);
        } else {
            Class<?> type = field.getType();
            ColumnType columnType = getTypeStrategy(type);
            if (columnType != null) {
                createTableSql.append(columnType.getSqlType());
            } else {
                log.warn(type + "not default column type, please specify column type in @RelaxColumn");
                return "";
            }
        }
        createTableSql.append(splicingPrimary(field));
        createTableSql.append(", ");
        return createTableSql.toString();
    }

    private static String splicingPrimary(Field field) {
        boolean isPrimaryKey = field.isAnnotationPresent(RelaxId.class);

        if (isPrimaryKey) {
            return " AUTO_INCREMENT PRIMARY KEY";
        }
        return "";
    }

    private static String getColumnName(Field field) {
        RelaxColumn columnAnnotation = field.getAnnotation(RelaxColumn.class);
        String defaultColumnName = RegexUtil.camelCaseToUnderscore(field.getName());
        return (columnAnnotation != null && !columnAnnotation.name().isEmpty()) ?
                "`" + columnAnnotation.name() + "`" :
                "`" + defaultColumnName + "`";
    }

    private static void processColumnAnnotation(RelaxColumn relaxColumn, StringBuilder createTableSql) {
        createTableSql.append(relaxColumn.type());
        if (!relaxColumn.length().isEmpty()) {
            createTableSql.append("(").append(relaxColumn.length()).append(")");
        }
    }

    private static ColumnType getTypeStrategy(Class<?> type) {
        for (ColumnType columnType : ColumnType.values()) {
            if (columnType.name().equalsIgnoreCase(type.getSimpleName())) {
                return columnType;
            }
        }
        return null;
    }
}
