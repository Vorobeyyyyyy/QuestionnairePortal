package com.softarex.datacollector.model.entity.answer;

import com.softarex.datacollector.model.entity.BaseEntity;
import com.softarex.datacollector.model.entity.user.User;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "answers")
public class Answer extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "asker_id")
    private User asker;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "answer_id")
    private List<FieldAnswer> fieldAnswers = new ArrayList<>();

    public Answer() {
    }

    public Answer(Long id, List<FieldAnswer> fieldAnswers) {
        this.id = id;
        this.fieldAnswers = fieldAnswers;
    }

    public List<FieldAnswer> getFieldAnswers() {
        return fieldAnswers;
    }

    public void setFieldAnswers(List<FieldAnswer> fieldAnswerOptions) {
        this.fieldAnswers = fieldAnswerOptions;
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
        Answer answer = (Answer) o;

        return id != null && id.equals(answer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "asker = " + asker + ")";
    }
}
