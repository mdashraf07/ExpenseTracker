package com.expense.controller;

import com.expense.model.User;
import com.expense.repository.UserRepository;
import com.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser(Authentication auth) {
        return userRepository.findByEmail(auth.getName()).orElseThrow();
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id,
                          @RequestParam(defaultValue = "") String comment,
                          Authentication auth) {
        expenseService.approveExpense(id, comment, getCurrentUser(auth));
        return "redirect:/dashboard?success=approved";
    }

    @PostMapping("/reject/{id}")
    public String reject(@PathVariable Long id,
                         @RequestParam(defaultValue = "") String comment,
                         Authentication auth) {
        expenseService.rejectExpense(id, comment, getCurrentUser(auth));
        return "redirect:/dashboard?success=rejected";
    }
}
