package com.softarex.datacollector.controller;

import com.softarex.datacollector.exception.UserNotFoundException;
import com.softarex.datacollector.model.dto.ChangePasswordDto;
import com.softarex.datacollector.model.entity.user.SecurityUserDetails;
import com.softarex.datacollector.model.entity.user.User;
import com.softarex.datacollector.model.dto.UserDto;
import com.softarex.datacollector.model.service.UserService;
import com.softarex.datacollector.validator.group.ChangeProfileInfo;
import liquibase.pro.packaged.A;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserProfileController {
    private final static Logger logger = LogManager.getLogger();
    private UserService userService;

    @Autowired
    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/edit_profile")
    public String editProfile(Model model, Principal principal) {
        User user;
        try {
            user = userService.findByEmail(principal.getName());
        } catch (UserNotFoundException exception) {
            logger.log(Level.ERROR, exception.getClass());
            return "login";
        }
        UserDto userDto = userService.createDto(user);
        model.addAttribute("userDto", userDto);
        return "edit_profile";
    }

    @PostMapping("/edit_profile")
    public String editProfileAction(@ModelAttribute("userDto") @Validated(ChangeProfileInfo.class) UserDto userDto,
                                    BindingResult bindingResult,
                                    Authentication authentication) {
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        if (!bindingResult.hasErrors()) {
            try {
                User user = userService.findByEmail(authentication.getName());
                userService.updateUser(user, userDto);
                userDetails.setUsername(userDto.getEmail());
                return "redirect:edit_profile?success";
            } catch (UserNotFoundException exception) {
                logger.log(Level.ERROR, exception.getMessage());
            }
        }
        return "edit_profile";
    }

    @GetMapping("/change_password")
    public String changePassword(Model model) {
        ChangePasswordDto dto = new ChangePasswordDto();
        model.addAttribute("changePasswordDto", dto);
        return "change_password";
    }

    @PostMapping("/change_password")
    public String changePasswordAction(@Valid ChangePasswordDto passwordDto,
                                       BindingResult bindingResult,
                                       Authentication authentication) {
        if (!bindingResult.hasErrors()) {
            try {
                User user = userService.findByEmail(authentication.getName());
                userService.updateUserPassword(user, passwordDto.getNewPassword());
                return "redirect:change_password?success";
            } catch (UserNotFoundException exception) {
                logger.log(Level.ERROR, exception.getMessage());
            }
        }
        return "change_password";
    }
}
