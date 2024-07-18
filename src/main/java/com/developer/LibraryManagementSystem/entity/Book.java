package com.developer.LibraryManagementSystem.entity;

public class Book {
	private String bookId;
	private String bookTitle;
	private String bookAuthername;
	private String bookEdition;
	private String bookQuantity;
	
	public Book() {
		super();
	}

	public Book(String bookId, String bookTitle, String bookAuthername, String bookEdition, String bookQuantity) {
		super();
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.bookAuthername = bookAuthername;
		this.bookEdition = bookEdition;
		this.bookQuantity = bookQuantity;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getBookAuthername() {
		return bookAuthername;
	}

	public void setBookAuthername(String bookAuthername) {
		this.bookAuthername = bookAuthername;
	}

	public String getBookEdition() {
		return bookEdition;
	}

	public void setBookEdition(String bookEdition) {
		this.bookEdition = bookEdition;
	}

	public String getBookQuantity() {
		return bookQuantity;
	}

	public void setBookQuantity(String bookQuantity) {
		this.bookQuantity = bookQuantity;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookTitle=" + bookTitle + ", bookAuthername=" + bookAuthername
				+ ", bookEdition=" + bookEdition + ", bookQuantity=" + bookQuantity + "]";
	}
	
	

}
