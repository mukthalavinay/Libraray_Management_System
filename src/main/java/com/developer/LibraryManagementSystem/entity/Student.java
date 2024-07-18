package com.developer.LibraryManagementSystem.entity;

public class Student {
	private String studentId;
	private String studentName;
	private String studentEmail;
	private String studentPassword;
	private Long studentMobile;
	private String studentDepartment;
	private String studentSection;
	public Student() {
		super();
	}
	public Student(String studentId,String studentName,String studentEmail, String studentPassword, Long studentMobile,
			String studentDepartment, String studentSection) {
		super();
		this.studentId = studentId;
		this.studentName= studentName;
		this.studentEmail = studentEmail;
		this.studentPassword = studentPassword;
		this.studentMobile = studentMobile;
		this.studentDepartment = studentDepartment;
		this.studentSection = studentSection;
	}
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentEmail() {
		return studentEmail;
	}
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	public String getStudentPassword() {
		return studentPassword;
	}
	public void setStudentPassword(String studentPassword) {
		this.studentPassword = studentPassword;
	}
	public Long getStudentMobile() {
		return studentMobile;
	}
	public void setStudentMobile(Long studentMobile) {
		this.studentMobile = studentMobile;
	}
	public String getStudentDepartment() {
		return studentDepartment;
	}
	public void setStudentDepartment(String studentDepartment) {
		this.studentDepartment = studentDepartment;
	}
	public String getStudentSection() {
		return studentSection;
	}
	public void setStudentSection(String studentSection) {
		this.studentSection = studentSection;
	}
	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ",studentName=" + studentName + ", studentEmail=" + studentEmail + ", studentPassword="
				+ studentPassword + ", studentMobile=" + studentMobile + ", studentDepartment=" + studentDepartment
				+ ", studentSection=" + studentSection + "]";
	}
	

}
