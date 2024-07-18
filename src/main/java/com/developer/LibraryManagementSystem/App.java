package com.developer.LibraryManagementSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.developer.LibraryManagementSystem.dao.LibrarianDao;
import com.developer.LibraryManagementSystem.dao.LibrarianImpl;
import com.developer.LibraryManagementSystem.dao.StudentDao;
import com.developer.LibraryManagementSystem.dao.StudentImpl;
import com.developer.LibraryManagementSystem.databaseconnection.DatabaseConnection;
import com.developer.LibraryManagementSystem.entity.Librarian;
import com.developer.LibraryManagementSystem.entity.Student;
import com.developer.LibraryManagementSystem.entity.ValidateStudent;

public class App {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean f = true;
		while (f) {
			System.out.println("--------WELCOME TO LIBRARY MANAGEMENT SYSTEM---------");
			System.out.println("---------------------------------------------");
			System.out.println("1.LIBRARIAN LOGIN \r\n" + "2.STUDENT LOGIN \r\n" + "3.EXIT");

			System.out.println("choose your option");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				// Librarian login section
				handleLibrarianLogin(sc);
				break;

			case 2:
				// Student login section
				handleStudentLogin(sc);
				break;
			case 3:
				System.out.println("Exiting the application. Goodbye!");
				f = false; // Exit the main loop
				break;

			default:
				System.out.println("Invalid choice, please try again.");
			}
		}
		sc.close();
	}

	private static void handleLibrarianLogin(Scanner sc) {
		System.out.println("Librarian Login credentials");
		System.out.println("Enter Username  :  ");
		String username = sc.next();
		System.out.println("Enter Password  :  ");
		String password = sc.next();

		LibrarianDao ld = new LibrarianImpl();
		try {
			Librarian l = ld.Librarianlogin(username, password);
			if (l == null) System.out.println("Wrong Credentials");
			System.out.println("Logged in Successfully");
			System.out.println("Welcome " + l.getLibrarianUsername());

			boolean y = true;
			while (y) {
				System.out.println("----------------------------\r\n" + "1.ADD NEW STUDENT \r\n" + "2.ADD NEW BOOK \r\n"
						+ "3.UPDATE STUDENT DETAILS \r\n" + "4.UPDATE BOOK QUANTITY \r\n" + "5.DELETE STUDENT \r\n"
						+ "6.SHOW SPECIFIC STUDENTS DETAILS \r\n" + "7.SHOW ALL STUDENT DETAILS \r\n" + "8.Logout \r\n"
						+ "Choose your Option");

				int x = sc.nextInt();

				if (x == 1) {
					System.out.println("-------Enter new Student details--------");
					System.out.println("Enter StudentId :");
					String studentid = sc.next();
					System.out.println("Enter Student Name :");
					String studentname = sc.next();
					if (ValidateStudent.validateName(studentname)) {
						System.out.println("Enter Student Email :");
						String studentemail = sc.next();
						if (ValidateStudent.isValidEmail(studentemail)) {
							System.out.println("Enter Student Password :");
							String studentpassword = sc.next();
							if (ValidateStudent.isValidPassword(studentpassword)) {
								System.out.println("Enter Student Mobile Number :");
								String studentmobile = sc.next();
								if (ValidateStudent.validatePhone(studentmobile)) {
									System.out.println("Enter Student Department :");
									String studentdept = sc.next();
									System.out.println("Enter Student Section :");
									String studentsection = sc.next();
									try {
										ld.addStudent(studentid, studentname, studentemail, studentpassword,
												studentmobile, studentdept, studentsection);
									} catch (SQLException e) {
										System.out.println(e.getMessage());
									}
									System.out.println("----------------------------");
								} else System.out.println("Phone number is not valid... please enter valid phone number");
								
							} else System.out.println("Password is too weak... please enter strong password");
							
						} else System.out.println("E mail is not valid... please enter valid E mail address");
						
					} else System.out.println("Name should contain only characters. please enter valid name");
				}
				else if (x == 2) {
					System.out.println("-------Enter new Book details--------");
					System.out.println("Enter Book ISBN :");
					String bookisbn = sc.next();
					System.out.println("Enter Book Title :");
					String booktitle = sc.next();
					System.out.println("Enter Book auther name :");
					String bookauther = sc.next();
					System.out.println("Enter Book edition :");
					String bookedition = sc.next();
					System.out.println("Enter Book Quantity :");
					String bookquantity = sc.next();

					try {
					    ld.addBook(bookisbn, booktitle, bookauther, bookedition, bookquantity);
					} catch (ClassNotFoundException | SQLException e) {
					    System.out.println("Error: " + e.getMessage());
					}
					System.out.println("----------------------------");

				} else if (x == 3) {
					System.out.println("-------Update Student details--------");
					System.out.println("--------- OPTIONS ---------");
					System.out.println(
							"----------------------------\r\n" + "1. Update Mobile \r\n" + "2. Update Password");
					System.out.println("Choose an option");
					int in = sc.nextInt();
					switch (in) {
					case 1:
						System.out.println("Enter Student ID: ");
						int studentId = sc.nextInt();
						System.out.println("Enter new mobile number: ");
						long newMobile = sc.nextLong();
						try {
							ld.updateStudentmobile(studentId, newMobile);
						} catch (ClassNotFoundException | SQLException e) {
							System.out.println(e.getMessage());

						}
						break;
					case 2:
						System.out.println("Enter Student ID: ");
						int studentId1 = sc.nextInt();
						System.out.println("Enter new password: ");
						String password1 = sc.next();
						try {
							ld.updateStudentpassword(studentId1, password1);
						} catch (ClassNotFoundException | SQLException e) {
							System.out.println(e.getMessage());

						}
						break;

					default:
						System.out.println("Invalid option. Please choose 1 or 2.");
					}

				} else if (x == 4) {
					System.out.println("----------Update Book Quantity------------");
					System.out.println("------------------------------------------");
					System.out.println("Enter Book ID: ");
					int bookId = sc.nextInt();
					System.out.println("Enter the Quantity of a Book : ");
					int bookQuantity = sc.nextInt();
					try {
						ld.updateBookQuantity(bookId, bookQuantity);
					} catch (ClassNotFoundException | SQLException e) {
						System.out.println(e.getMessage());

					}
					System.out.println("-------------------------------------------");
				} else if (x == 5) {
					System.out.println("------------Delete Student data------------");
					System.out.println("-------------------------------------------");
					System.out.println("Please enter Student Id :");
					int studentId = sc.nextInt();
					try {
						ld.deleteStudent(studentId);
					} catch (ClassNotFoundException | SQLException e) {
						System.out.println(e.getMessage());

					}
					System.out.println("---------------------------------------------");

				} else if (x == 6) {
					System.out.println("------------Specific Student Details-----------------");
					System.out.println("Please enter Student Id:");
					int studentId = sc.nextInt();
					try {
						ld.showSingleStudent(studentId);
					} catch (ClassNotFoundException | SQLException e) {
						System.out.println(e.getMessage());
					}

				} else if (x == 7) {
					System.out.println("-----------All Students Details------------");
					try {
						String result = ld.showAllStudents(null); // Assuming the method does not use the Student
																	// parameter
						System.out.println(result);
					} catch (ClassNotFoundException | SQLException e) {
						System.out.println(e.getMessage());
					}
				}

				else if (x == 8) {
					System.out.println(" Admin Account Logged out Successfully");
					y = false;
					break;
				} else System.out.println("Invalid choice, please try again.");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void handleStudentLogin(Scanner sc) {
		System.out.println("Student Login credentials");
		System.out.println("Enter Id  :  ");
		String studentId = sc.next();
		System.out.println("Enter Password  :  ");
		String studentPassword = sc.next();

		StudentDao sd = new StudentImpl();
		try {
			Student s = sd.Studentlogin(studentId, studentPassword);
			if (s == null) {
				System.out.println("Wrong Credentials");
			}
			System.out.println("Logged in Successfully");
			System.out.println("Welcome " + s.getStudentName());

			boolean y = true;
			while (y) {
				System.out.println("----------------------------\r\n" + "1.Show Books \r\n" + "2.Borrow a Book \r\n"
						+ "3.Return a Book \r\n" + "4.Show Borrowed Books \r\n" + "5.Logout \r\n"
						+ "Choose your Option");

				int x = sc.nextInt();
				if (x == 1) {
					System.out.println("--------List of Books avalible------------");
					try (Connection connection = DatabaseConnection.getConnection()) {
						PreparedStatement ps = connection
								.prepareStatement("SELECT * FROM librarymanagementsystem.available_books");
						ResultSet rs = ps.executeQuery();
						while (rs.next()) {
							System.out.println("Book ID :" + rs.getString("bookId") + "\t" + "Available copies :"
									+ rs.getInt("availableCopies"));

						}

					} catch (SQLException e) {
						System.out.println("Error fetching books: " + e.getMessage());
					}

				} else if (x == 2) {
					try {
						System.out.println("Enter Book ID:");
						int bookId = sc.nextInt();
						String message = Handletransaction.borrowBook(studentId, bookId);
						System.out.println(message);
					} catch (SQLException e) {
						System.err.println("Error borrowing book: " + e.getMessage());

					}

					// BorrowBook.getdata();
				} else if (x == 3) {
					try {
						System.out.println("Enter Book ID:");
						int bookId = sc.nextInt();
						String message = Handletransaction.returnBook(studentId, bookId);
						System.out.println(message);
					} catch (SQLException e) {
						System.err.println("Error returning book: " + e.getMessage());
					}
				} else if (x == 4) {

					try {
						String message = sd.showBorrowedRecords(s.getStudentId()); // Pass the student ID
						if (message != null) {
							System.out.println("-----------Your Borrowed Records------------");
							System.out.println(message); // Print any error message if necessary
						} else {
							System.out.println("No Borrowed Books");
						}
					} catch (SQLException e) {
						System.out.println("Error fetching borrowed records: " + e.getMessage());
					}
				}

				else if (x == 5) {
					System.out.println(" Student Account Logged out Successfully");
					y = false;
				} else {
					System.out.println("Invalid choice, please try again.");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
