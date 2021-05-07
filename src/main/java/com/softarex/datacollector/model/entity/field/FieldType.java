package com.softarex.datacollector.model.entity.field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum FieldType {
    SINGLE_LINE_TEXT("Single Line Text"),
    MULTILINE_TEXT("Multiline Text"),
    RADIO_BUTTON("Radio Button"),
    CHECKBOX("Checkbox"),
    COMBOBOX("Combobox"),
    DATE("Date");

    @JsonValue
    private final String name;

    FieldType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
