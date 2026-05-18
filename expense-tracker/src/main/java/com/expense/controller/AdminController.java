package com.expense.controller;

import com.expense.model.User;
import com.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/users")
    public String manageUsers(Model model) {
        model.addAttribute("users", expenseService.getAllUsers());
        model.addAttribute("newUser", new User());
        return "admin-users";
    }

    @PostMapping("/users/create")
    public String createUser(@ModelAttribute User user) {
        expenseService.createUser(user);
        return "redirect:/admin/users?success=created";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        expenseService.deleteUser(id);
        return "redirect:/admin/users?success=deleted";
    }
}
