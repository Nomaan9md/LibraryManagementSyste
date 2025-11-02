// Library.java (Main Library System)
import java.util.*;

class Book {
    private String isbn;
    private String title;
    private String author;
    private boolean isAvailable;
    private String borrowedBy;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.borrowedBy = null;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }
    public String getBorrowedBy() { return borrowedBy; }

    public void setAvailable(boolean available) { this.isAvailable = available; }
    public void setBorrowedBy(String memberId) { this.borrowedBy = memberId; }

    @Override
    public String toString() {
        return String.format("ISBN: %s | Title: %s | Author: %s | Available: %s",
                isbn, title, author, isAvailable ? "Yes" : "No");
    }
}

// User.java (Base class with inheritance)
abstract class User {
    protected String userId;
    protected String name;
    protected String email;

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public abstract int getMaxBorrowLimit();
    public abstract int getBorrowDurationDays();
    public abstract String getUserType();
}

// Student.java (Inherits from User)
class Student extends User {
    private String studentId;

    public Student(String userId, String name, String email, String studentId) {
        super(userId, name, email);
        this.studentId = studentId;
    }

    @Override
    public int getMaxBorrowLimit() { return 3; }

    @Override
    public int getBorrowDurationDays() { return 14; }

    @Override
    public String getUserType() { return "Student"; }

    public String getStudentId() { return studentId; }
}

// Faculty.java (Inherits from User)
class Faculty extends User {
    private String department;

    public Faculty(String userId, String name, String email, String department) {
        super(userId, name, email);
        this.department = department;
    }

    @Override
    public int getMaxBorrowLimit() { return 10; }

    @Override
    public int getBorrowDurationDays() { return 30; }

    @Override
    public String getUserType() { return "Faculty"; }

    public String getDepartment() { return department; }
}

// Guest.java (Inherits from User)
class Guest extends User {
    public Guest(String userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public int getMaxBorrowLimit() { return 1; }

    @Override
    public int getBorrowDurationDays() { return 7; }

    @Override
    public String getUserType() { return "Guest"; }
}

// BorrowRecord.java
class BorrowRecord {
    private String isbn;
    private String userId;
    private String borrowDate;
    private String dueDate;
    private String returnDate;

    public BorrowRecord(String isbn, String userId, String borrowDate, String dueDate) {
        this.isbn = isbn;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
    }

    public String getIsbn() { return isbn; }
    public String getUserId() { return userId; }
    public String getBorrowDate() { return borrowDate; }
    public String getDueDate() { return dueDate; }
    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String date) { this.returnDate = date; }
    public boolean isReturned() { return returnDate != null; }
}



class Library {
    private Map<String, Book> books;
    private Map<String, User> users;
    private List<BorrowRecord> borrowRecords;

    public Library() {
        books = new HashMap<>();
        users = new HashMap<>();
        borrowRecords = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
        System.out.println("Book added: " + book.getTitle());
    }

    public void registerUser(User user) {
        users.put(user.getUserId(), user);
        System.out.println(user.getUserType() + " registered: " + user.getName());
    }

    public boolean borrowBook(String userId, String isbn) {
        User user = users.get(userId);
        Book book = books.get(isbn);

        if (user == null) {
            System.out.println("Error: User not found!");
            return false;
        }

        if (book == null) {
            System.out.println("Error: Book not found!");
            return false;
        }

        if (!book.isAvailable()) {
            System.out.println("Error: Book is already borrowed!");
            return false;
        }

        // Check borrow limit
        long currentBorrows = borrowRecords.stream()
                .filter(r -> r.getUserId().equals(userId) && !r.isReturned())
                .count();

        if (currentBorrows >= user.getMaxBorrowLimit()) {
            System.out.println("Error: Borrow limit reached for " + user.getUserType());
            return false;
        }

        // Borrow the book
        book.setAvailable(false);
        book.setBorrowedBy(userId);

        String today = new Date().toString();
        String dueDate = "Due in " + user.getBorrowDurationDays() + " days";

        BorrowRecord record = new BorrowRecord(isbn, userId, today, dueDate);
        borrowRecords.add(record);

        System.out.println(user.getName() + " borrowed: " + book.getTitle());
        System.out.println("Due date: " + dueDate);
        return true;
    }

    public boolean returnBook(String userId, String isbn) {
        User user = users.get(userId);
        Book book = books.get(isbn);

        if (user == null || book == null) {
            System.out.println("Error: User or Book not found!");
            return false;
        }

        if (book.isAvailable()) {
            System.out.println("Error: Book was not borrowed!");
            return false;
        }

        // Find the borrow record
        BorrowRecord record = borrowRecords.stream()
                .filter(r -> r.getIsbn().equals(isbn) &&
                        r.getUserId().equals(userId) &&
                        !r.isReturned())
                .findFirst()
                .orElse(null);

        if (record == null) {
            System.out.println("Error: No borrow record found!");
            return false;
        }

        // Return the book
        book.setAvailable(true);
        book.setBorrowedBy(null);
        record.setReturnDate(new Date().toString());

        System.out.println(user.getName() + " returned: " + book.getTitle());
        return true;
    }

    public void displayAllBooks() {
        System.out.println("\n=== All Books ===");
        books.values().forEach(System.out::println);
    }

    public void displayAvailableBooks() {
        System.out.println("\n=== Available Books ===");
        books.values().stream()
                .filter(Book::isAvailable)
                .forEach(System.out::println);
    }

    public void displayUserInfo(String userId) {
        User user = users.get(userId);
        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        System.out.println("\n=== User Information ===");
        System.out.println("Name: " + user.getName());
        System.out.println("Type: " + user.getUserType());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Borrow Limit: " + user.getMaxBorrowLimit());
        System.out.println("Borrow Duration: " + user.getBorrowDurationDays() + " days");

        System.out.println("\nCurrently Borrowed Books:");
        borrowRecords.stream()
                .filter(r -> r.getUserId().equals(userId) && !r.isReturned())
                .forEach(r -> {
                    Book book = books.get(r.getIsbn());
                    System.out.println("  - " + book.getTitle() + " (Due: " + r.getDueDate() + ")");
                });
    }
}

// Main.java (Demo)
public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        // Add books
        library.addBook(new Book("ISBN001", "Java Programming", "John Doe"));
        library.addBook(new Book("ISBN002", "Data Structures", "Jane Smith"));
        library.addBook(new Book("ISBN003", "Algorithms", "Bob Johnson"));
        library.addBook(new Book("ISBN004", "Design Patterns", "Alice Brown"));

        // Register users with different types
        Student student = new Student("S001", "Alice Cooper", "alice@university.edu", "ST12345");
        Faculty faculty = new Faculty("F001", "Dr. Bob Smith", "bob@university.edu", "Computer Science");
        Guest guest = new Guest("G001", "Charlie Guest", "charlie@email.com");

        library.registerUser(student);
        library.registerUser(faculty);
        library.registerUser(guest);

        System.out.println("\n" + "=".repeat(50));

        // Display all books
        library.displayAllBooks();

        System.out.println("\n" + "=".repeat(50));

        // Borrow books
        System.out.println("\n=== Borrowing Books ===");
        library.borrowBook("S001", "ISBN001");
        library.borrowBook("F001", "ISBN002");
        library.borrowBook("G001", "ISBN003");

        System.out.println("\n" + "=".repeat(50));

        // Display available books
        library.displayAvailableBooks();

        System.out.println("\n" + "=".repeat(50));

        // Display user info
        library.displayUserInfo("S001");
        library.displayUserInfo("F001");

        System.out.println("\n" + "=".repeat(50));

        // Return a book
        System.out.println("\n=== Returning Books ===");
        library.returnBook("S001", "ISBN001");

        System.out.println("\n" + "=".repeat(50));

        // Display available books again
        library.displayAvailableBooks();

        System.out.println("\n" + "=".repeat(50));

        // Test borrow limits
        System.out.println("\n=== Testing Borrow Limits ===");
        library.borrowBook("G001", "ISBN001"); // Guest trying to borrow 2nd book
        library.borrowBook("G001", "ISBN004"); // Should fail - limit is 1
    }
}