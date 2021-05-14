package com.softarex.datacollector.controller;

import com.softarex.datacollector.exception.UserNotFoundException;
import com.softarex.datacollector.model.entity.field.Field;
import com.softarex.datacollector.model.dto.FieldDto;
import com.softarex.datacollector.model.entity.user.User;
import com.softarex.datacollector.model.service.FieldService;
import com.softarex.datacollector.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Controller
public class FieldsController {
    private final static Logger logger = LogManager.getLogger();
    private ModelMapper modelMapper;
    private FieldService fieldService;
    private UserService userService;

    @Autowired
    public FieldsController(ModelMapper modelMapper, FieldService fieldService, UserService userService) {
        this.modelMapper = modelMapper;
        this.fieldService = fieldService;
        this.userService = userService;
    }

    @GetMapping("/fields")
    public String fields() {
        return "fields";
    }

    @ResponseBody
    @PostMapping("/api/addField")
    public ResponseEntity<String> addField(@RequestBody FieldDto fieldDto,
                                           Principal principal) throws UserNotFoundException {
        User user = userService.findByEmail(principal.getName());
        fieldService.createAndSaveFromDto(fieldDto, user);
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @GetMapping("/api/fields")
    public Page<FieldDto> findFields(@RequestParam(required = false, defaultValue = "0") int page,
                                  @RequestParam(required = false, defaultValue = "0", name = "size") int pageSize,
                                  Principal principal) throws UserNotFoundException {
        User user = userService.findByEmail(principal.getName());
        return fieldService.findByAsker(page, pageSize, user);
    }

    @ResponseBody
    @PostMapping("/api/deleteField")
    public ResponseEntity<String> deleteField(@RequestBody Map<String, Long> request) {
        long id = request.get("id");
        fieldService.removeField(id);
        return ResponseEntity.ok().build();
    }
}
