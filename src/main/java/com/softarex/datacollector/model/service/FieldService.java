package com.softarex.datacollector.model.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softarex.datacollector.model.dto.FieldDto;
import com.softarex.datacollector.model.entity.field.Field;
import com.softarex.datacollector.model.entity.user.User;
import com.softarex.datacollector.model.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldService {
    private FieldRepository fieldRepository;

    @Autowired
    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    public void updateField(Field field) {
        fieldRepository.save(field);
    }

    public void createAndSaveFromDto(FieldDto fieldDto, User asker) {
        Field field = fromDto(fieldDto);
        field.setAsker(asker);
        fieldRepository.save(field);
    }

    public List<FieldDto> findByAskerId(Long id) {
        return fieldRepository.findByAskerId(id)
                .stream()
                .map(FieldDto::new)
                .collect(Collectors.toList());
    }

    public Page<FieldDto> findByAsker(int page, int pageSize, User asker) {
        if (pageSize == 0) {
            pageSize = Integer.MAX_VALUE;
        }
        return fieldRepository.findByAsker(PageRequest.of(page, pageSize, Sort.by("id")), asker).map(FieldDto::new);
    }

    public List<Field> findByActive(Boolean active) {
        return fieldRepository.findByActive(active, Sort.by("id"));
    }

    public void removeField(long id) {
        fieldRepository.deleteById(id);
    }

    public Field fromDto(FieldDto dto) {
        Field field = new Field();
        field.setLabel(dto.getLabel());
        field.setOptions(dto.getOptions());
        field.setActive(dto.isActive());
        field.setRequired(dto.isRequired());
        field.setType(dto.getType());
        return field;
    }
}
