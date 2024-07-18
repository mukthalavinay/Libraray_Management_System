package com.developer.LibraryManagementSystem.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.developer.LibraryManagementSystem.databaseconnection.DatabaseConnection;
import com.developer.LibraryManagementSystem.entity.Librarian;
import com.developer.LibraryManagementSystem.entity.Student;

public class LibrarianImpl implements LibrarianDao {

	@Override
	public Librarian Librarianlogin(String librarianUsername, String librarianPassword) throws ClassNotFoundException, SQLException {
		Librarian lib=null;
		try(Connection connection=DatabaseConnection.getConnection()) {
			PreparedStatement ps=connection.prepareStatement("select * from librarian where librarianUsername=? and librarianPassword=?");
			ps.setNString(1, librarianUsername);
			ps.setNString(2, librarianPassword);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
				String librarianusername=rs.getString("librarianUsername");
				String librarianemail=rs.getString("librarianEmail");
				String librarianpassword=rs.getString("librarianPassword");
				lib=new Librarian(librarianusername,librarianemail,librarianpassword);
			}
			
		}
		catch(SQLException e) {
			System.out.println("Invalid Username or Password");
			
		}
		
		return lib;
	}

	@Override
	public int addStudent(String studentId,String studentName, String studentEmail, String studentPassword, String studentMobile,
			String studentDepartment, String studentSection) throws ClassNotFoundException, SQLException {
		
		int sid=-1;
		try(Connection connection=DatabaseConnection.getConnection()){
			PreparedStatement ps=connection.prepareStatement("insert into studentinformation(studentId,studentName,studentEmail,studentPassword,studentMobile,studentDepartment,studentSection) values(?,?,?,?,?,?,?)");
			ps.setString(1,studentId);
			ps.setString(2,studentName);
			ps.setString(3,studentEmail);
			ps.setString(4,studentPassword);
			ps.setString(5,studentMobile);
			ps.setString(6,studentDepartment);
			ps.setString(7,studentSection);
			
			int x=ps.executeUpdate();
			
			if(x>0) {
				System.out.println("Student data is added successfully");
			}
			else System.out.println("Student data is not added to Data Table");
			
		}
		catch(SQLException e) {
			System.out.println("SQL query related error ");
			
		}
		return sid;
	}

	@Override
	public int addBook(String bookId, String bookTitle, String bookAuthorName, String bookEdition, String bookQuantity)
	        throws ClassNotFoundException, SQLException {
	    int rowsAffected = 0;
	    try (Connection connection = DatabaseConnection.getConnection()) {
	        // Inserting into book information table
	        String insertBookInfo = "INSERT INTO bookinformation(bookId, bookTitle, bookAuthername, bookEdition, bookQuantity) VALUES(?, ?, ?, ?, ?)";
	        try (PreparedStatement ps = connection.prepareStatement(insertBookInfo)) {
	            ps.setString(1, bookId);
	            ps.setString(2, bookTitle);
	            ps.setString(3, bookAuthorName);
	            ps.setString(4, bookEdition);
	            ps.setString(5, bookQuantity);

	            rowsAffected = ps.executeUpdate();
	        }

	        // Inserting into available_books table
	        String insertAvailableBooks = "INSERT INTO available_books (bookId, availableCopies) VALUES (?, ?)";
	        try (PreparedStatement psAvailable = connection.prepareStatement(insertAvailableBooks)) {
	            psAvailable.setString(1, bookId);
	            psAvailable.setString(2, bookQuantity);
	            rowsAffected += psAvailable.executeUpdate();
	        }
	        if(rowsAffected>0) {
	        	System.out.println(" New Book is added successfully");
	        }
	        else System.out.println("New Book is not added to Data Table");

	    } catch (SQLException e) {
	        System.out.println("Database error: " + e.getMessage());
	    }

	    return rowsAffected;
	}


	@Override
	public String updateStudentmobile(int studentId, long newMobile) throws ClassNotFoundException, SQLException {
		String message=null;
	    try (Connection connection = DatabaseConnection.getConnection()) {
	        PreparedStatement ps = connection.prepareStatement("UPDATE studentinformation SET studentMobile = ? WHERE studentId = ?");
	        ps.setLong(1, newMobile);
	        ps.setInt(2, studentId);

	        int rowsAffected = ps.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Mobile number updated successfully.");
	        } else System.out.println("No student found with ID: " + studentId);

	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        message=e.getMessage();
	    }
	    return message;
	}

	@Override
	public String updateStudentpassword(int studentId1, String password1) throws ClassNotFoundException, SQLException {
		String message=null;
	    try (Connection connection = DatabaseConnection.getConnection()) {
	        PreparedStatement ps = connection.prepareStatement("UPDATE studentinformation SET studentPassword = ? WHERE studentId = ?");
	        ps.setString(1, password1);
	        ps.setInt(2, studentId1);

	        int rowsAffected = ps.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Password updated successfully.");
	        } else System.out.println("No student found with ID: " + studentId1);

	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        message=e.getMessage();
	    }
	    return message;
	}

	@Override
	public int updateBookQuantity(int bookId, int bookQuantity) throws ClassNotFoundException, SQLException {
	    Connection connection = null;
	    try {
	        connection = DatabaseConnection.getConnection();
	        
	        // Check the current borrowed count for the book
	        String checkBorrowedCount = "SELECT COUNT(*) FROM transaction_history WHERE bookId = ? AND actionType = 'borrow' AND returnDate IS NULL";
	        int borrowedCount = 0;
	        try (PreparedStatement psCheck = connection.prepareStatement(checkBorrowedCount)) {
	            psCheck.setInt(1, bookId);
	            try (ResultSet rs = psCheck.executeQuery()) {
	                if (rs.next()) {
	                    borrowedCount = rs.getInt(1);
	                }
	            }
	        }

	        // Update book quantity in book information
	        String updateBookInfo = "UPDATE bookinformation SET bookQuantity = ? WHERE bookId = ?";
	        try (PreparedStatement psInfo = connection.prepareStatement(updateBookInfo)) {
	            psInfo.setInt(1, bookQuantity);
	            psInfo.setInt(2, bookId);
	            psInfo.executeUpdate();
	        }

	        // Calculate available copies and update available_books
	        int availableCopies = bookQuantity - borrowedCount;
	        String updateAvailableBooks = "UPDATE available_books SET availableCopies = ? WHERE bookId = ?";
	        try (PreparedStatement psAvailable = connection.prepareStatement(updateAvailableBooks)) {
	            psAvailable.setInt(1, availableCopies);
	            psAvailable.setInt(2, bookId);
	            int rowsAffected = psAvailable.executeUpdate();

	            if (rowsAffected > 0) {
	                System.out.println("Book Quantity and Available Copies updated successfully.");
	                return 1; // Success
	            } else {
	                System.out.println("No Book found with ID: " + bookId);
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL query related error: " + e.getMessage());
	    } finally {
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                // Log closing error if needed
	            }
	        }
	    }
	    return 0; // Failure
	}

	@Override
	public String deleteStudent(int studentId) throws ClassNotFoundException, SQLException {
		String message=null;
		try (Connection connection = DatabaseConnection.getConnection()){
			PreparedStatement ps = connection.prepareStatement("delete from studentinformation where studentId = ?");
			ps.setInt(1, studentId);
			int x =ps.executeUpdate();
			if(x > 0) {
				System.out.println("Student data with this ID "+studentId+" is deleted");
	        } else System.out.println("No student data found with ID: " + studentId);

	        
	    } catch (SQLException e) {
	    	
	    	e.printStackTrace();
	        message=e.getMessage();
	    }
		
		return message;
	}

	@Override
	public String showSingleStudent(int studentId) throws ClassNotFoundException, SQLException {
	    String message = null;
	    try (Connection connection = DatabaseConnection.getConnection()) {
	        PreparedStatement ps = connection.prepareStatement("SELECT * FROM studentinformation WHERE studentId = ?");
	        ps.setInt(1, studentId); // Set the studentId parameter
	        ResultSet rs = ps.executeQuery();
	        
	        // Check if the result set is empty
	        if (!rs.isBeforeFirst()) {
	            message = "No student found with ID: " + studentId;
	        } else {
	            // Iterate over the result set and print each student's details
	            while (rs.next()) {
	                System.out.println("Student ID: " + rs.getString("studentId") + "\t" + "Student Name: " + rs.getString("studentName")
	                    + "\t" + "Student Email: " + rs.getString("studentEmail") + "\t" + "Student Password: " + rs.getString("studentPassword")
	                    + "\t" + "Student Mobile Number: " + rs.getString("studentMobile") + "\t" + "Student Department: " + rs.getString("studentDepartment")
	                    + "\t" + "Student Section: " + rs.getString("studentSection"));
	                System.out.println("---------------------------------------------");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        message = e.getMessage();
	    }
	    return message;
	}

	@Override
	public String showAllStudents(Student student) throws ClassNotFoundException, SQLException {
	    String message = null;
	    try (Connection connection = DatabaseConnection.getConnection()) {
	        PreparedStatement ps = connection.prepareStatement("SELECT * FROM librarymanagementsystem.studentinformation");
	        ResultSet rs = ps.executeQuery();
	        
	        // Check if the result set is empty
	        if (!rs.isBeforeFirst()) {
	            message = "No students found in the system.";
	        } else {
	            // Iterate over the result set and print each student's details
	            StringBuilder studentsDetails = new StringBuilder();
	            while (rs.next()) {
	                studentsDetails.append("Student ID: ").append(rs.getString("studentId")).append("\t")
	                    .append("Student Name: ").append(rs.getString("studentName")).append("\t")
	                    .append("Student Email: ").append(rs.getString("studentEmail")).append("\t")
	                    .append("Student Password: ").append(rs.getString("studentPassword")).append("\t")
	                    .append("Student Mobile Number: ").append(rs.getString("studentMobile")).append("\t")
	                    .append("Student Department: ").append(rs.getString("studentDepartment")).append("\t")
	                    .append("Student Section: ").append(rs.getString("studentSection")).append("\n")
	                    .append("---------------------------------------------\n");
	            }
	            System.out.println(studentsDetails.toString());
	            message = "Students details retrieved successfully.";
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        message = e.getMessage();
	    }
	    return message;
	}
}
