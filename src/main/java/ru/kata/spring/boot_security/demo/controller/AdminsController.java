package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.ui.ModelMap;

@Controller
@RequestMapping("/admin")
public class AdminsController {
    private final UserService userService;

    @Autowired
    public AdminsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(ModelMap model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/new")
    public String newUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", userService.findAllRoles());
        return "new";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editUser(ModelMap model, @RequestParam("id") long id) {
        model.addAttribute("user", userService.show(id));
        model.addAttribute("roles", userService.findAllRoles());
        return "edit";
    }

    @GetMapping("/show")
    public String showUser(ModelMap model, @RequestParam("id") long id) {
        model.addAttribute("user", userService.show(id));
        return "show";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
