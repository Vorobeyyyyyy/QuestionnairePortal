package com.softarex.datacollector.model.dto;

import com.softarex.datacollector.model.entity.answer.Answer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AnswerDto {
    private Long askerId;
    private List<FieldAnswerDto> fieldAnswers;

    public AnswerDto() {
    }

    public AnswerDto(Answer answer) {
        this.askerId = answer.getAsker().getId();
        this.fieldAnswers = answer.getFieldAnswers().stream().map(FieldAnswerDto::new).collect(Collectors.toList());
    }

    public AnswerDto(Long askerId, List<FieldAnswerDto> fieldAnswers) {
        this.askerId = askerId;
        this.fieldAnswers = fieldAnswers;
    }

    public Long getAskerId() {
        return askerId;
    }

    public void setAskerId(Long askerId) {
        this.askerId = askerId;
    }

    public List<FieldAnswerDto> getFieldAnswers() {
        return fieldAnswers;
    }

    public void setFieldAnswers(List<FieldAnswerDto> fieldAnswers) {
        this.fieldAnswers = fieldAnswers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerDto answerDto = (AnswerDto) o;
        return Objects.equals(askerId, answerDto.askerId) && Objects.equals(fieldAnswers, answerDto.fieldAnswers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(askerId, fieldAnswers);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AnswerDto{");
        sb.append("askerId=").append(askerId);
        sb.append(", fieldAnswers=").append(fieldAnswers);
        sb.append('}');
        return sb.toString();
    }
}
