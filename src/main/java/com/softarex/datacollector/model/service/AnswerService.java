package com.softarex.datacollector.model.service;

import com.softarex.datacollector.model.entity.answer.Answer;
import com.softarex.datacollector.model.entity.answer.FieldAnswer;
import com.softarex.datacollector.model.entity.field.Field;
import com.softarex.datacollector.model.repository.AnswerRepository;
import com.softarex.datacollector.model.repository.FieldAnswerRepository;
import com.softarex.datacollector.model.repository.FieldRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AnswerService {
    private static final Logger logger = LogManager.getLogger();
    private AnswerRepository answerRepository;
    private FieldRepository fieldRepository;
    private FieldAnswerRepository fieldAnswerRepository;

    @Autowired
    public void setAnswerRepository(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Autowired
    public void setFieldRepository(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Autowired
    public void setFieldAnswerRepository(FieldAnswerRepository fieldAnswerRepository) {
        this.fieldAnswerRepository = fieldAnswerRepository;
    }

    @Transactional
    public Answer addAnswer(MultiValueMap<String, String> rawFieldAnswers) {
        Answer answer = new Answer();
        answerRepository.save(answer);
        List<FieldAnswer> fieldAnswers = new ArrayList<>();
        rawFieldAnswers.entrySet().stream().filter(e -> !e.getValue().isEmpty()).
                forEach(e -> {
                    Long fieldId = Long.parseLong(e.getKey());
                    Optional<Field> optionalField = fieldRepository.findById(fieldId);
                    if (optionalField.isPresent()) {
                        Field field = optionalField.get();
                        FieldAnswer fieldAnswer = new FieldAnswer(null, answer, field, e.getValue());
                        fieldAnswerRepository.save(fieldAnswer);
                        fieldAnswers.add(fieldAnswer);
                    } else {
                        logger.log(Level.WARN, "Could not find Field with id: {}", fieldId);
                    }
                });
        answer.setFieldAnswers(fieldAnswers);
        answerRepository.save(answer);
        return answer;
    }

    public List<Answer> findAll() {
        return answerRepository.findAll(Sort.by("id"));
    }

    public Page<Answer> findAll(int page, int pageSize) {
        if (pageSize <= 0) {
            pageSize = Integer.MAX_VALUE;
        }
        return  answerRepository.findAll(PageRequest.of(page, pageSize, Sort.by("id").descending()));
    }

    public void addAnswer(Answer answer) {
        answerRepository.save(answer);
    }
}
