package com.softarex.datacollector.model.dto;

import com.softarex.datacollector.model.entity.answer.FieldAnswer;

import java.util.List;
import java.util.Objects;

public class FieldAnswerDto {
    private Long fieldId;
    private List<String> options;

    public FieldAnswerDto() {
    }

    public FieldAnswerDto(FieldAnswer fieldAnswer) {
        this.fieldId = fieldAnswer.getField().getId();
        this.options = fieldAnswer.getOptions();
    }

    public FieldAnswerDto(Long fieldId, List<String> options) {
        this.fieldId = fieldId;
        this.options = options;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldAnswerDto that = (FieldAnswerDto) o;
        return Objects.equals(fieldId, that.fieldId) && Objects.equals(options, that.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldId, options);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FieldAnswerDto{");
        sb.append("fieldId=").append(fieldId);
        sb.append(", options=").append(options);
        sb.append('}');
        return sb.toString();
    }
}
