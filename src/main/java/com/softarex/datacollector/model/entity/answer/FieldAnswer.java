package com.softarex.datacollector.model.entity.answer;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softarex.datacollector.model.entity.field.Field;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "field_answers")
public class FieldAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "field_answer_id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @ElementCollection(fetch = FetchType.EAGER)
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

    @JsonGetter("fieldId")
    public Long getFieldId() {
        return field.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (o == null || getClass() != o.getClass()) return false;
        FieldAnswer that = (FieldAnswer) o;
        return Objects.equals(id, that.id) && Objects.equals(answer, that.answer) && Objects.equals(field, that.field) && Objects.equals(options, that.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, answer, field, options);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FieldAnswer{");
        sb.append("id=").append(id);
        sb.append(", answer=").append(answer);
        sb.append(", field=").append(field);
        sb.append(", options=").append(options);
        sb.append('}');
        return sb.toString();
    }
}
