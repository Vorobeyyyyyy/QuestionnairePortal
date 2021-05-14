package com.softarex.datacollector.model.repository;

import com.softarex.datacollector.model.entity.field.Field;
import com.softarex.datacollector.model.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {
    Page<Field> findAll(Pageable pageable);

    Page<Field> findByAsker(Pageable pageable, User user);

    List<Field> findByAskerId(Long id);

    List<Field> findByActive(Boolean active, Sort sort);
}
