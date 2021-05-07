package com.softarex.datacollector.model.repository;

import com.softarex.datacollector.model.entity.field.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {
    Page<Field> findAll(Pageable pageable);

    List<Field> findByActive(Boolean active, Sort sort);
}
