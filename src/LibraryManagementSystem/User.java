package LibraryManagementSystem;

import java.io.Serializable;
import java.util.ArrayList;


public class User implements Serializable {
    private static final long serialVersionUID = 1L; // Add serialVersionUID
    private String userId;
    private String name;
    private String password; // Add password field
    private ArrayList<Book> borrowedBooks;

    // Constructor
    public User(String userId, String name, String password) {
        this.userId = userId;
        this.name = name;
        this.password = password; // Initialize password
        this.borrowedBooks = new ArrayList<>();
    }

    // Authenticate user
    public boolean authenticate(String password) {
        return this.password.equals(password); // Check if passwords match
    }

    // Borrow a book
    public boolean borrowBook(Book book) {
        if (book.borrowBook()) {
            borrowedBooks.add(book);
            return true;
        }
        return false;
    }

    // Return a book
    public boolean returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.returnBook();
            return true;
        }
        return false;
    }

    // View borrowed books
    public void viewBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println(name + " has no borrowed books.");
        } else {
            System.out.println(name + "'s borrowed books:");
            for (Book book : borrowedBooks) {
                System.out.println(book);
            }
        }
    }

    // Getters
    public String getUserId() {
        return userId;
    }
}
