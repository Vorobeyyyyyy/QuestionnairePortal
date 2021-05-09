package com.softarex.datacollector.model.service;

import com.softarex.datacollector.exception.UserNotFoundException;
import com.softarex.datacollector.model.property.MailProperty;
import com.softarex.datacollector.model.repository.UserRepository;
import com.softarex.datacollector.model.entity.user.Role;
import com.softarex.datacollector.model.entity.user.SecurityUserDetails;
import com.softarex.datacollector.model.entity.user.User;
import com.softarex.datacollector.model.entity.user.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final static Logger logger = LogManager.getLogger();
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;
    private MailProperty mailProperty;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        User user = optionalUser.get();
        Hibernate.initialize(user.getRoles());
        return new SecurityUserDetails(user);
    }

    @Transactional
    public void addUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setRoles(Collections.singleton(new Role(1,"ROLE_USER")));
        userRepository.save(user);
        mailService.sendMessage(user, mailProperty.getRegistrationSubject(), mailProperty.getRegistrationText());
    }

    @Transactional
    public void updateUser(User user, UserDto userDto) {
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        userRepository.save(user);
    }

    public User findByEmail(String email) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElseThrow(UserNotFoundException::new);
    }

    public UserDto createDto(User user) {
        return new UserDto(user.getEmail(),
                user.getFirstName(), user.getLastName(), user.getPhoneNumber(), null, null);
    }

    @Transactional
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void updateUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        mailService.sendMessage(user, mailProperty.getPasswordChangeText(), mailProperty.getPasswordChangeText());
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    public void setMailProperty(MailProperty mailProperty) {
        this.mailProperty = mailProperty;
    }
}
