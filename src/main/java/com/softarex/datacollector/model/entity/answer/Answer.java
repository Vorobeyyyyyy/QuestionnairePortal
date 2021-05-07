package com.softarex.datacollector.model.entity.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "answer_id")
    private List<FieldAnswer> fieldAnswers = new ArrayList<>();

    public Answer() {
    }

    public Answer(Long id, List<FieldAnswer> fieldAnswers) {
        this.id = id;
        this.fieldAnswers = fieldAnswers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<FieldAnswer> getFieldAnswers() {
        return fieldAnswers;
    }

    public void setFieldAnswers(List<FieldAnswer> fieldAnswerOptions) {
        this.fieldAnswers = fieldAnswerOptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(id, answer.id) && Objects.equals(fieldAnswers, answer.fieldAnswers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fieldAnswers);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Answer{");
        sb.append("id=").append(id);
        sb.append(", fieldAnswerOptions=").append(fieldAnswers);
        sb.append('}');
        return sb.toString();
    }
}
