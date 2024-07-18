package com.developer.LibraryManagementSystem.dao;
import java.sql.SQLException;

import com.developer.LibraryManagementSystem.entity.Librarian;
import com.developer.LibraryManagementSystem.entity.Student;

public interface LibrarianDao {
	public Librarian Librarianlogin(String librarianUsername,String librarianPassword) throws ClassNotFoundException, SQLException ;
	
	public int addStudent(String studentId,String studentName,String studentEmail,String studentPassword,String studentMobile,String studentDepartment,String studentSection) throws ClassNotFoundException, SQLException;
	public int addBook(String bookId,String bookTitle,String bookAuthername,String bookEdition,String bookQuantity) throws ClassNotFoundException, SQLException;
	public String updateStudentmobile(int studentId,long mobile ) throws ClassNotFoundException, SQLException ;
	public String updateStudentpassword(int studentId1,String password1) throws ClassNotFoundException, SQLException;

	public int updateBookQuantity(int bookId, int bookQuantity) throws ClassNotFoundException, SQLException;
	public String deleteStudent(int studentId) throws ClassNotFoundException, SQLException;
	public String showSingleStudent(int studentId) throws ClassNotFoundException, SQLException;
	public String showAllStudents(Student student) throws ClassNotFoundException, SQLException;
}
