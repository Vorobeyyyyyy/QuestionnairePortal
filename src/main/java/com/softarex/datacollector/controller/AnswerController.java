package com.softarex.datacollector.controller;

import com.softarex.datacollector.exception.UserNotFoundException;
import com.softarex.datacollector.model.dto.AnswerDto;
import com.softarex.datacollector.model.dto.FieldDto;
import com.softarex.datacollector.model.entity.field.Field;
import com.softarex.datacollector.model.entity.user.User;
import com.softarex.datacollector.model.service.AnswerService;
import com.softarex.datacollector.model.service.FieldService;
import com.softarex.datacollector.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class AnswerController {
    private static final Logger logger = LogManager.getLogger();
    private FieldService fieldService;
    private AnswerService answerService;
    private UserService userService;
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public AnswerController(FieldService fieldService, AnswerService answerService, UserService userService, SimpMessagingTemplate messagingTemplate) {
        this.fieldService = fieldService;
        this.answerService = answerService;
        this.userService = userService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/questionnaire/{id}")
    public String answer(Model model, @PathVariable Long id) {
        List<FieldDto> fields = fieldService.findByAskerId(id);
        model.addAttribute("fields", fields);
        return "answer";
    }

    @PostMapping("/add_answer")
    public String addAnswer(@RequestBody MultiValueMap<String, String> fieldAnswers) {
        fieldAnswers.remove("_csrf");
        logger.info(fieldAnswers);
        answerService.addAnswer(fieldAnswers);
        messagingTemplate.convertAndSend("/answers", "update");
        return "redirect:/answer_success";
    }

    @GetMapping("/responses")
    public String responses() {
        return "responses";
    }

    @ResponseBody
    @GetMapping("/api/answers")
    public Page<AnswerDto> findAnswers(@RequestParam(required = false, defaultValue = "0") int page,
                                       @RequestParam(required = false, defaultValue = "0", name = "size") int pageSize,
                                       Principal principal) throws UserNotFoundException {
        User user = userService.findByEmail(principal.getName());
        return answerService.findByAsker(page, pageSize, user);
    }
}
