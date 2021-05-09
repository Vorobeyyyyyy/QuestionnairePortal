package com.softarex.datacollector.model.service;

import com.softarex.datacollector.model.entity.field.Field;
import com.softarex.datacollector.model.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldService {
    private FieldRepository fieldRepository;

    public void updateField(Field field) {
        fieldRepository.save(field);
    }

    public Page<Field> findAll(int page, int pageSize) {
        if (pageSize <= 0) {
            pageSize = Integer.MAX_VALUE;
        }
        return fieldRepository.findAll(PageRequest.of(page, pageSize, Sort.by("id")));
    }

    public List<Field> findAll() {
        return fieldRepository.findAll(Sort.by("id"));
    }

    public List<Field> findByActive(Boolean active) {
        return fieldRepository.findByActive(active, Sort.by("id"));
    }

    public void removeField(long id) {
        fieldRepository.deleteById(id);
    }

    @Autowired
    public void setFieldRepository(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }
}
