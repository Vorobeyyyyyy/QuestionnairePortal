package com.softarex.datacollector.model.service;

import com.softarex.datacollector.model.dto.AnswerDto;
import com.softarex.datacollector.model.entity.answer.Answer;
import com.softarex.datacollector.model.entity.answer.FieldAnswer;
import com.softarex.datacollector.model.entity.field.Field;
import com.softarex.datacollector.model.entity.user.User;
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
    private final AnswerRepository answerRepository;
    private final FieldRepository fieldRepository;
    private final FieldAnswerRepository fieldAnswerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, FieldRepository fieldRepository, FieldAnswerRepository fieldAnswerRepository) {
        this.answerRepository = answerRepository;
        this.fieldRepository = fieldRepository;
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

    @Transactional
    public Page<AnswerDto> findByAsker(int page, int pageSize, User asker) {
        if (pageSize == 0) {
            pageSize = Integer.MAX_VALUE;
        }
        return answerRepository.findByAsker(PageRequest.of(page, pageSize, Sort.by("id").descending()), asker)
                .map(AnswerDto::new);
    }
}
