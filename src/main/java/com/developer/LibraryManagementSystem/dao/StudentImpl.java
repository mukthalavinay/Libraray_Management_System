package com.developer.LibraryManagementSystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.developer.LibraryManagementSystem.databaseconnection.DatabaseConnection;
import com.developer.LibraryManagementSystem.entity.Student;

public class StudentImpl implements StudentDao {

	@Override
	public Student Studentlogin(String studentId, String studentPassword) throws SQLException {
	    String sql = "SELECT * FROM studentinformation WHERE studentId = ? AND studentPassword = ?";
	    try (
	        Connection connection = DatabaseConnection.getConnection();
	        PreparedStatement ps = connection.prepareStatement(sql)
	    ) {
	        ps.setString(1, studentId);
	        ps.setString(2, studentPassword);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return new Student(
	                    rs.getString("studentId"),
	                    rs.getString("studentName"),
	                    rs.getString("studentEmail"),
	                    rs.getString("studentPassword"),
	                    rs.getLong("studentMobile"),
	                    rs.getString("studentDepartment"),
	                    rs.getString("studentSection")
	                );
	            } else {
	                throw new SQLException("Invalid Id or Password: No matching records found.");
	            }
	        }
	    } catch (SQLException e) {
	        throw new SQLException("Database error: " + e.getMessage());
	    }
	}

    
    public String showBorrowedRecords(String studentId) throws ClassNotFoundException, SQLException {
        String message = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT bookId, actionDate, dueDate FROM transaction_history WHERE studentId = ? AND actionType = 'borrow'";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, studentId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    System.out.println("Book ID: " + rs.getInt("bookId") +
                                       ", Borrowed Date: " + rs.getDate("actionDate") +
                                       ", Due Date: " + rs.getDate("dueDate"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message = e.getMessage();
        }
        return message;
    }

    

	
}
