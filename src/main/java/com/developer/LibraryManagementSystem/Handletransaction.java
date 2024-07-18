package com.developer.LibraryManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.developer.LibraryManagementSystem.databaseconnection.DatabaseConnection;

public class Handletransaction {
	public static String borrowBook(String studentId, int bookId) throws ClassNotFoundException, SQLException {
	    final Connection connection = DatabaseConnection.getConnection();
	    if (connection == null || connection.isClosed()) {
	        throw new SQLException("Failed to establish database connection.");
	    }
	    connection.setAutoCommit(false);
	    try {
	        String checkBook = "SELECT availableCopies FROM available_books WHERE bookId = ?";
	        try (PreparedStatement ps = connection.prepareStatement(checkBook)) {
	            ps.setInt(1, bookId);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (!rs.next() || rs.getInt("availableCopies") <= 0) {
	                    throw new SQLException("Book not available or doesn't exist.");
	                }

	                String updateBookCount = "UPDATE available_books SET availableCopies = availableCopies - 1 WHERE bookId = ?";
	                try (PreparedStatement ps1 = connection.prepareStatement(updateBookCount)) {
	                    ps1.setInt(1, bookId);
	                    ps1.executeUpdate();
	                }

	                String insertHistoryRecord = "INSERT INTO transaction_history (bookId, studentId, actionType, actionDate, borrowDate, dueDate) VALUES (?, ?, 'borrow', ?, ?, ?)";
	                try (PreparedStatement psHistory = connection.prepareStatement(insertHistoryRecord)) {
	                    psHistory.setInt(1, bookId);
	                    psHistory.setString(2, studentId);
	                    psHistory.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
	                    psHistory.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
	                    psHistory.setDate(5, java.sql.Date.valueOf(LocalDate.now().plusMonths(1)));
	                    if (psHistory.executeUpdate() == 0) {
	                        throw new SQLException("Failed to insert borrow record.");
	                    }
	                }

	                connection.commit();
	                return "Book borrowed successfully.";
	            }
	        }
	    } catch (SQLException e) {
	        connection.rollback();
	        throw e;
	    } finally {
	        connection.setAutoCommit(true);
	        connection.close();
	    }
	}




	public static String returnBook(String studentId, int bookId) throws ClassNotFoundException, SQLException {
	    final Connection connection = DatabaseConnection.getConnection();
	    if (connection == null || connection.isClosed()) {
	        throw new SQLException("Failed to establish database connection.");
	    }
	    connection.setAutoCommit(false);
	    try {
	        String checkBorrowRecord = "SELECT borrowDate FROM transaction_history WHERE bookId = ? AND studentId = ? AND actionType = 'borrow' AND returnDate IS NULL";
	        try (PreparedStatement ps = connection.prepareStatement(checkBorrowRecord)) {
	            ps.setInt(1, bookId);
	            ps.setString(2, studentId);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (!rs.next()) {
	                    throw new SQLException("The book is not borrowed by the student.");
	                }
	            }
	        }

	        String updateBookCount = "UPDATE available_books SET availableCopies = availableCopies + 1 WHERE bookId = ?";
	        try (PreparedStatement ps1 = connection.prepareStatement(updateBookCount)) {
	            ps1.setInt(1, bookId);
	            ps1.executeUpdate();
	        }

	        String updateHistoryRecord = "UPDATE transaction_history SET actionType = 'return', returnDate = ? WHERE studentId = ? AND bookId = ? AND actionType = 'borrow' AND returnDate IS NULL";
	        try (PreparedStatement psHistory = connection.prepareStatement(updateHistoryRecord)) {
	            psHistory.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
	            psHistory.setString(2, studentId);
	            psHistory.setInt(3, bookId);
	            if (psHistory.executeUpdate() == 0) {
	                throw new SQLException("Failed to update transaction history for return.");
	            }
	        }

	        connection.commit();
	        return "Book returned successfully.";
	    } catch (SQLException e) {
	        connection.rollback();
	        throw e;
	    } finally {
	        connection.setAutoCommit(true);
	        connection.close();
	    }
	}


}
