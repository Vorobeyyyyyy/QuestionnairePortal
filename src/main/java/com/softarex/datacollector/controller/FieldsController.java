package com.softarex.datacollector.controller;

import com.softarex.datacollector.model.entity.field.Field;
import com.softarex.datacollector.model.entity.field.FieldDto;
import com.softarex.datacollector.model.service.FieldService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class FieldsController {
    private final static Logger logger = LogManager.getLogger();
    private ModelMapper modelMapper;
    private FieldService fieldService;

    @GetMapping("/fields")
    public String fields() {
        return "fields";
    }


    @ResponseBody
    @PostMapping("/api/addField")
    public ResponseEntity<String> addField(@RequestBody FieldDto fieldDto) {
        Field field = convertToEntity(fieldDto);
        fieldService.updateField(field);
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @GetMapping("/api/fields")
    public Page<Field> findFields(@RequestParam(required = false, defaultValue = "0") int page,
                           @RequestParam(required = false, defaultValue = "0", name = "size") int pageSize) {
        return fieldService.findAll(page, pageSize);
    }

    @ResponseBody
    @PostMapping("/api/deleteField")
    public ResponseEntity<String> deleteField(@RequestBody Map<String, Long> request) {
        long id = request.get("id");
        fieldService.removeField(id);
        return ResponseEntity.ok().build();
    }

    private Field convertToEntity(FieldDto fieldDto) {
        return modelMapper.map(fieldDto, Field.class);
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setFieldService(FieldService fieldService) {
        this.fieldService = fieldService;
    }
}
