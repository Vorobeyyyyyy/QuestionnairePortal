package com.softarex.datacollector.model.entity.field;


import com.softarex.datacollector.model.entity.BaseEntity;
import com.softarex.datacollector.model.entity.user.User;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "fields")
public class Field extends BaseEntity {
    private String label;
    @Enumerated(EnumType.STRING)
    private FieldType type;
    @ManyToOne
    @JoinColumn(name = "asker_id")
    private User asker;

    @ElementCollection
    @CollectionTable(name = "field_options", joinColumns = @JoinColumn(name = "field_id"))
    @Column(name = "option")
    private List<String> options;
    private boolean required;
    private boolean active;


    public Field() {
    }

    public Field(long id, String label, FieldType type, boolean required, boolean active, List<String> options) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.required = required;
        this.active = active;
        this.options = options;
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

    public User getAsker() {
        return asker;
    }

    public void setAsker(User asker) {
        this.asker = asker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Field field = (Field) o;

        return id != null && id.equals(field.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "label = " + label + ", " +
                "type = " + type + ", " +
                "asker = " + asker + ", " +
                "options = " + options + ", " +
                "required = " + required + ", " +
                "active = " + active + ")";
    }
}
