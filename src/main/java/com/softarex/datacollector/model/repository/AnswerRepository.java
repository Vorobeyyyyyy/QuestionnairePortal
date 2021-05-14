package com.softarex.datacollector.model.repository;

import com.softarex.datacollector.model.entity.answer.Answer;
import com.softarex.datacollector.model.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findAll(Pageable pageable);

    Page<Answer> findByAsker(Pageable pageable, User asker);
}
