package com.softarex.datacollector.controller;

import com.softarex.datacollector.model.dto.UserDto;
import com.softarex.datacollector.model.service.UserService;
import com.softarex.datacollector.validator.group.RegisterUser;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final static Logger logger = LogManager.getLogger();

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String newUser(@ModelAttribute("userDto") @Validated(RegisterUser.class) UserDto userDto, BindingResult bindingResult) {
        logger.log(Level.INFO, userDto);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.addUser(userDto);
        return "redirect:login";
    }
}
