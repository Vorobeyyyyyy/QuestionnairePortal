package com.softarex.datacollector.controller;

import com.softarex.datacollector.model.entity.answer.Answer;
import com.softarex.datacollector.model.entity.field.Field;
import com.softarex.datacollector.model.service.AnswerService;
import com.softarex.datacollector.model.service.FieldService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AnswerController {
    private static final Logger logger = LogManager.getLogger();
    private FieldService fieldService;
    private AnswerService answerService;
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/")
    public String answer(Model model) {
        List<Field> fields = fieldService.findByActive(true);
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
    public Page<Answer> findAnswers(@RequestParam(required = false, defaultValue = "0") int page,
                                    @RequestParam(required = false, defaultValue = "0", name = "size") int pageSize) {
        return answerService.findAll(page, pageSize);
    }

    @Autowired
    public void setFieldService(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @Autowired
    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
}
