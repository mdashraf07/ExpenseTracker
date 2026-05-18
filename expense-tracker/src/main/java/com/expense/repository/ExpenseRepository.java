package com.expense.repository;

import com.expense.model.Expense;
import com.expense.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserOrderBySubmittedDateDesc(User user);
    List<Expense> findByStatusOrderBySubmittedDateDesc(Expense.Status status);
    List<Expense> findAllByOrderBySubmittedDateDesc();

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user = :user AND e.status = 'APPROVED'")
    java.math.BigDecimal sumApprovedByUser(User user);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.status = 'PENDING'")
    java.math.BigDecimal sumAllPending();

    @Query("SELECT COUNT(e) FROM Expense e WHERE e.status = 'PENDING'")
    long countPending();
}
