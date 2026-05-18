package com.expense.service;

import com.expense.model.Expense;
import com.expense.model.User;
import com.expense.repository.ExpenseRepository;
import com.expense.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // ── Expense CRUD ──────────────────────────────────────────────

    public Expense submitExpense(Expense expense, MultipartFile receipt, User user) throws IOException {
        expense.setUser(user);
        expense.setSubmittedDate(LocalDateTime.now());
        expense.setStatus(Expense.Status.PENDING);

        if (receipt != null && !receipt.isEmpty()) {
            String filename = UUID.randomUUID() + "_" + receipt.getOriginalFilename();
            Path dir = Paths.get(uploadDir);
            Files.createDirectories(dir);
            Files.copy(receipt.getInputStream(), dir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            expense.setReceiptPath(filename);
        }

        return expenseRepository.save(expense);
    }

    public List<Expense> getExpensesForUser(User user) {
        return expenseRepository.findByUserOrderBySubmittedDateDesc(user);
    }

    public List<Expense> getPendingExpenses() {
        return expenseRepository.findByStatusOrderBySubmittedDateDesc(Expense.Status.PENDING);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAllByOrderBySubmittedDateDesc();
    }

    public Expense approveExpense(Long id, String comment, User manager) {
        Expense expense = expenseRepository.findById(id).orElseThrow();
        expense.setStatus(Expense.Status.APPROVED);
        expense.setManagerComment(comment);
        expense.setManager(manager);
        expense.setActionDate(LocalDateTime.now());
        return expenseRepository.save(expense);
    }

    public Expense rejectExpense(Long id, String comment, User manager) {
        Expense expense = expenseRepository.findById(id).orElseThrow();
        expense.setStatus(Expense.Status.REJECTED);
        expense.setManagerComment(comment);
        expense.setManager(manager);
        expense.setActionDate(LocalDateTime.now());
        return expenseRepository.save(expense);
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElseThrow();
    }

    // ── Stats ─────────────────────────────────────────────────────

    public BigDecimal getTotalApprovedForUser(User user) {
        return expenseRepository.sumApprovedByUser(user);
    }

    public long getPendingCount() {
        return expenseRepository.countPending();
    }

    public BigDecimal getTotalPendingAmount() {
        return expenseRepository.sumAllPending();
    }

    public long getTotalUsers() {
        return userRepository.count();
    }

    // ── User management ───────────────────────────────────────────

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
