package com.softarex.datacollector.model.dto;

import com.softarex.datacollector.model.entity.field.Field;
import com.softarex.datacollector.model.entity.field.FieldType;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class FieldDto {
    private Long id;
    @Size(min = 2, max = 40)
    private String label;
    private FieldType type;
    private List<String> options;
    private boolean required;
    private boolean active;

    public FieldDto() {
    }

    public FieldDto(Field field) {
        this.id = field.getId();
        this.label = field.getLabel();
        this.type = field.getType();
        this.options = field.getOptions();
        this.required = field.isRequired();
        this.active = field.isActive();
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public FieldType getType() {
        return type;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldDto fieldDto = (FieldDto) o;
        return id == fieldDto.id && required == fieldDto.required && active == fieldDto.active && Objects.equals(label, fieldDto.label) && Objects.equals(type, fieldDto.type) && Objects.equals(options, fieldDto.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, type, options, required, active);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FieldDto{");
        sb.append("id=").append(id);
        sb.append(", label='").append(label).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", options='").append(options).append('\'');
        sb.append(", required=").append(required);
        sb.append(", active=").append(active);
        sb.append('}');
        return sb.toString();
    }
}
