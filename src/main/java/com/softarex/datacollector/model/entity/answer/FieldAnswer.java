package com.softarex.datacollector.model.entity.answer;

import com.softarex.datacollector.model.entity.BaseEntity;
import com.softarex.datacollector.model.entity.field.Field;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "field_answers")
public class FieldAnswer extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @ElementCollection
    @CollectionTable(name = "field_answer_options", joinColumns = @JoinColumn(name = "field_answer_id"))
    @Column(name = "option")
    private List<String> options;

    public FieldAnswer() {
    }

    public FieldAnswer(Long id, Answer answer, Field field, List<String> options) {
        this.id = id;
        this.answer = answer;
        this.field = field;
        this.options = options;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FieldAnswer that = (FieldAnswer) o;

        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "answer = " + answer + ", " +
                "field = " + field + ", " +
                "options = " + options + ")";
    }
}
