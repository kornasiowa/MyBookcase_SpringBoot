package com.kornas.mybookcase.controllers;

import com.kornas.mybookcase.entity.User;
import com.kornas.mybookcase.repository.UserRepository;
import com.kornas.mybookcase.validation.EditValidation;
import com.kornas.mybookcase.validation.RegisterValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping(value = {""})
public class UserController {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String loginPage() {
        //zwrócenie nazwy widoku logowania - login.html
        return "login";
    }

    @GetMapping("/login/error")
    public String loginPage(Model m) {
        String error = "Wprowadzone dane są nieprawidłowe ";
        m.addAttribute("error", error);
        //zwrócenie nazwy widoku logowania - login.html
        return "login";
    }

    @GetMapping("/register")
    public String register(Model m, User user) {
        //dodanie do modelu nowego użytkownika
        m.addAttribute("user", new User());
        //zwrócenie nazwy widoku rejestracji - register.html
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute(value = "user") @Validated({RegisterValidation.class}) User user, BindingResult bindingResult) {
        boolean flag = true;

        if (bindingResult.hasErrors()) {
            return new ModelAndView("register");
        }

        for (User iUser : userRepository.findAll()) {
            if (iUser.getLogin().equals(user.getLogin())) {
                flag = false;
                break;
            }
        }

        //zarejestrowany zostanie tylko użytkownik o unikalnym loginie
        if (flag) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            //przekierowanie do adresu url: /login po pomyślnej rejestracji
            return new ModelAndView("redirect:/login");
            //powrót do formularza po nieudanej rejestracji
        } else return new ModelAndView("redirect:/register", "error", true);
    }

    @GetMapping(value = "/delete")
    public ModelAndView deleteUser(Principal principal) {
        User userFromDB = userRepository.findByLogin(principal.getName());
        userRepository.delete(userFromDB);

        return new ModelAndView("redirect:logout");
    }

    @GetMapping(value = "/edit")
    public String editUser(Model m, Principal principal, User user) {
        User userFromDB = userRepository.findByLogin(principal.getName());
        userFromDB.setPassword("");
        m.addAttribute("user", userFromDB);

        return "editUser";
    }

    @PostMapping(value = "/edit")
    public String editUser(@ModelAttribute(value = "user") @Validated({EditValidation.class}) User
                                   user, BindingResult bindingResult) {
        User userFromDB = userRepository.findByLogin(user.getLogin());

        if (bindingResult.hasErrors()) {
            return "editUser";
        }

        if (userFromDB != null) {
            userFromDB.setName(user.getName());
            userFromDB.setSurname(user.getSurname());
            userFromDB.setLogin(user.getLogin());
            if (!user.getPassword().isEmpty()) {
                userFromDB.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            }
            userRepository.save(userFromDB);
        }
        return "redirect:/web/books";
    }

}
