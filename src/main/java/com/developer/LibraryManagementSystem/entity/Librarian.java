package com.developer.LibraryManagementSystem.entity;

public class Librarian {
	private String librarianUsername;
	private String librarianEmail;
	private String librarianPassword;
	public Librarian(String librarianUsername, String librarianEmail, String librarianPassword) {
		super();
		this.librarianUsername = librarianUsername;
		this.librarianEmail = librarianEmail;
		this.librarianPassword = librarianPassword;
	}
	public Librarian() {
		super();
	}
	public String getLibrarianUsername() {
		return librarianUsername;
	}
	public void setLibrarianUsername(String librarianUsername) {
		this.librarianUsername = librarianUsername;
	}
	public String getLibrarianEmail() {
		return librarianEmail;
	}
	public void setLibrarianEmail(String librarianEmail) {
		this.librarianEmail = librarianEmail;
	}
	public String getLibrarianPassword() {
		return librarianPassword;
	}
	public void setLibrarianPassword(String librarianPassword) {
		this.librarianPassword = librarianPassword;
	}
	@Override
	public String toString() {
		return "Librarian [librarianUsername=" + librarianUsername + ", librarianEmail=" + librarianEmail
				+ ", librarianPassword=" + librarianPassword + "]";
	}
	

}
