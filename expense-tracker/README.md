# 💼 ExpenseFlow — Smart Expense Tracker

A full-stack expense management system built with **Spring Boot + Thymeleaf + MySQL**.
Beautiful dark UI with role-based access: Employee, Manager, Admin.

---

## 🚀 Quick Setup (5 minutes)

### Step 1 — Install Requirements
- Java 17+ (https://adoptium.net)
- Maven (https://maven.apache.org) OR use the Maven extension in VS Code
- MySQL 8+ (https://dev.mysql.com/downloads/mysql/)

### Step 2 — Create the Database
Open MySQL Workbench or terminal and run:
```sql
CREATE DATABASE expense_tracker;
```
That's it! Tables are created automatically on first run.

### Step 3 — Configure your DB password
Open: `src/main/resources/application.properties`

Change this line to your MySQL password:
```
spring.datasource.password=root
```
(Default assumes username=root, password=root)

### Step 4 — Run the App

**In VS Code:**
1. Install the "Extension Pack for Java" extension
2. Open the project folder in VS Code
3. Open `ExpenseTrackerApplication.java`
4. Click the ▶ Run button that appears above `main()`

**OR in terminal:**
```bash
cd expense-tracker
mvn spring-boot:run
```

### Step 5 — Open in Browser
```
http://localhost:8080
```

---

## 🔑 Demo Login Accounts
(Created automatically on first startup)

| Role     | Email                    | Password     |
|----------|--------------------------|--------------|
| Admin    | admin@expense.com        | admin123     |
| Manager  | manager@expense.com      | manager123   |
| Employee | employee@expense.com     | employee123  |

Click any demo account on the login page to auto-fill!

---

## 🎯 Features

### Employee
- Submit expense claims with title, amount, category, description
- Upload receipt photo/PDF
- Track approval status in real-time (Pending / Approved / Rejected)
- View manager comments on rejected claims

### Manager
- See all pending expense claims from employees
- Approve or reject with optional comment
- View full expense history

### Admin
- System-wide overview dashboard with stats
- View all expenses across all employees
- Create new user accounts
- Assign roles (Employee / Manager / Admin)
- Delete accounts

---

## 🗂 Project Structure
```
expense-tracker/
├── src/main/java/com/expense/
│   ├── config/SecurityConfig.java     ← Login, roles, seed data
│   ├── controller/
│   │   ├── ExpenseController.java     ← Dashboard, submit
│   │   ├── ManagerController.java     ← Approve/Reject
│   │   └── AdminController.java      ← User management
│   ├── model/
│   │   ├── User.java
│   │   └── Expense.java
│   ├── repository/
│   │   ├── UserRepository.java
│   │   └── ExpenseRepository.java
│   ├── service/ExpenseService.java    ← All business logic
│   └── ExpenseTrackerApplication.java
├── src/main/resources/
│   ├── templates/                     ← HTML pages (Thymeleaf)
│   │   ├── login.html
│   │   ├── employee-dashboard.html
│   │   ├── manager-dashboard.html
│   │   ├── admin-dashboard.html
│   │   ├── admin-users.html
│   │   └── submit-expense.html
│   ├── static/css/style.css           ← All styles
│   └── application.properties
└── pom.xml
```

---

## 🏆 Interview Talking Points

1. **Spring Security** — Role-based access control (RBAC), BCrypt password hashing
2. **JPA/Hibernate** — Entity relationships, auto schema generation
3. **Service Layer Pattern** — Business logic separated from controllers
4. **Thymeleaf** — Server-side templating with Spring integration
5. **Real business workflow** — Approval workflow mirrors enterprise systems (SAP, Workday)
6. **File upload** — MultipartFile handling for receipts

Built with ❤ using Spring Boot 3.2, Java 17, MySQL 8
