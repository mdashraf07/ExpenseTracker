package com.expense.controller;

import com.expense.model.Expense;
import com.expense.model.User;
import com.expense.repository.UserRepository;
import com.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser(Authentication auth) {
        return userRepository.findByEmail(auth.getName()).orElseThrow();
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        User user = getCurrentUser(auth);
        model.addAttribute("user", user);

        if (user.getRole() == User.Role.ADMIN) {
            model.addAttribute("allExpenses", expenseService.getAllExpenses());
            model.addAttribute("totalUsers", expenseService.getTotalUsers());
            model.addAttribute("pendingCount", expenseService.getPendingCount());
            model.addAttribute("pendingAmount", expenseService.getTotalPendingAmount());
            return "admin-dashboard";
        } else if (user.getRole() == User.Role.MANAGER) {
            model.addAttribute("pendingExpenses", expenseService.getPendingExpenses());
            model.addAttribute("allExpenses", expenseService.getAllExpenses());
            model.addAttribute("pendingCount", expenseService.getPendingCount());
            return "manager-dashboard";
        } else {
            model.addAttribute("expenses", expenseService.getExpensesForUser(user));
            model.addAttribute("totalApproved", expenseService.getTotalApprovedForUser(user));
            return "employee-dashboard";
        }
    }

    @GetMapping("/submit")
    public String submitPage(Model model, Authentication auth) {
        model.addAttribute("user", getCurrentUser(auth));
        model.addAttribute("expense", new Expense());
        return "submit-expense";
    }

    @PostMapping("/submit")
    public String submitExpense(@ModelAttribute Expense expense,
                                 @RequestParam(value = "receipt", required = false) MultipartFile receipt,
                                 Authentication auth) throws Exception {
        User user = getCurrentUser(auth);
        expenseService.submitExpense(expense, receipt, user);
        return "redirect:/dashboard?success=submitted";
    }
}
