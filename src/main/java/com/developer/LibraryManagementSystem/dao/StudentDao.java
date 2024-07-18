package com.developer.LibraryManagementSystem.dao;

import java.sql.SQLException;

import com.developer.LibraryManagementSystem.entity.Student;

public interface StudentDao {
	public Student Studentlogin(String studentId,String studentPassword) throws ClassNotFoundException, SQLException;

	public String showBorrowedRecords(String studentId) throws ClassNotFoundException, SQLException;
	
}
