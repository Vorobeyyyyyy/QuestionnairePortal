package com.softarex.datacollector.model.entity.field;


import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "fields")
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "field_id", unique = true, nullable = false)
    private long id;
    private String label;
    private FieldType type;
    @ElementCollection
    @CollectionTable(name = "field_options", joinColumns = @JoinColumn(name = "field_id"))
    @Column(name = "option")
    private Collection<String> options;
    private boolean required;
    private boolean active;


    public Field() {
    }

    public Field(long id, String label, FieldType type, boolean required, boolean active, Collection<String> options) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.required = required;
        this.active = active;
        this.options = options;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setType(FieldType type) {
        this.type = type;
    }

    public Collection<String> getOptions() {
        return options;
    }

    public void setOptions(Collection<String> options) {
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
        Field field = (Field) o;
        return id == field.id && required == field.required && active == field.active && Objects.equals(label, field.label) && type == field.type && Objects.equals(options, field.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, type, options, required, active);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Field{");
        sb.append("id=").append(id);
        sb.append(", label='").append(label).append('\'');
        sb.append(", type=").append(type);
        sb.append(", options=").append(options);
        sb.append(", required=").append(required);
        sb.append(", active=").append(active);
        sb.append('}');
        return sb.toString();
    }
}
