package com.softarex.datacollector.model.converter;

import com.softarex.datacollector.model.entity.field.FieldType;
import org.springframework.core.convert.converter.Converter;

public class StringToFieldTypeConverter implements Converter<String, FieldType> {
    @Override
    public FieldType convert(String s) {
        FieldType fieldType;
        try {
            fieldType = FieldType.valueOf(s.replace(" ", "_").toUpperCase());
        } catch (IllegalArgumentException exception) {
            fieldType = null;
        }
        return fieldType;
    }
}
