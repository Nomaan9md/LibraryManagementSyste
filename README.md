Library Management System
A comprehensive Java-based Library Management System demonstrating object-oriented programming principles including inheritance, polymorphism, encapsulation, and abstraction.

Library Management System
A comprehensive Java-based Library Management System demonstrating object-oriented programming principles including inheritance, polymorphism, encapsulation, and abstraction.
Show Image
Show Image
Show Image
📋 Table of Contents

Features
User Types
Project Structure
OOP Concepts
Installation
Usage
Sample Output
Future Enhancements
Contributing
Author
License

✨ Features

📖 Book Management: Add and track books with ISBN, title, and author information
👥 User Management: Support for multiple user types with different privileges
🔄 Borrowing System: Comprehensive borrow and return functionality with validation
⚖️ Borrow Limits: Automatic enforcement based on user type
📊 Record Keeping: Complete tracking of all borrowing history
✅ Validation: Check book availability and user borrow limits
📅 Due Date Tracking: Automatic due date calculation based on user type

👤 User Types
The system supports three types of users with different privileges:
User TypeBorrow LimitBorrow DurationUse CaseStudent3 books14 daysRegular studentsFaculty10 books30 daysProfessors and staffGuest1 book7 daysVisitors and guests
📁 Project Structure
LibraryManagementSystem/
├── README.md
├── .gitignore
└── src/
    ├── Book.java           # Book entity class
    ├── User.java           # Abstract base class for users
    ├── Student.java        # Student user type (inherits User)
    ├── Faculty.java        # Faculty user type (inherits User)
    ├── Guest.java          # Guest user type (inherits User)
    ├── BorrowRecord.java   # Borrowing record tracking
    ├── Library.java        # Main library system
    └── Main.java           # Demo application
🎯 OOP Concepts
This project demonstrates key Object-Oriented Programming principles:
1️⃣ Inheritance

User abstract base class
Three derived classes: Student, Faculty, Guest
Code reusability and hierarchical relationships

2️⃣ Polymorphism

Different user types override abstract methods
Each user type has unique borrow limits and durations
Runtime polymorphism through method overriding

3️⃣ Encapsulation

Private fields with public getters/setters
Data hiding and controlled access
Protected internal state

4️⃣ Abstraction

Abstract User class with abstract methods
getMaxBorrowLimit(), getBorrowDurationDays(), getUserType()
Hide implementation details

🚀 Installation
Prerequisites

Java Development Kit (JDK) 8 or higher
A Java IDE (Eclipse, IntelliJ IDEA, VS Code) or command line

Steps

Clone the repository

bashgit clone https://github.com/Nomaan9md/LibraryManagementSyste.git
cd LibraryManagementSyste

Compile the Java files

bashjavac src/*.java

Run the application

bashjava -cp src Main
