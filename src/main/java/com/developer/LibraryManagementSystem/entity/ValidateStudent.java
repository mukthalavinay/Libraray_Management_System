package com.developer.LibraryManagementSystem.entity;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidateStudent {
static Scanner s = new Scanner(System.in);
	
	//Validating whether string contains only alphabets or not
	public static boolean validateName(String name) {
			 return name.matches("[a-zA-Z]+");
	}
	
	//Validating Users Phone Number
	public static boolean  validatePhone(String studentMobile) {
		String l = String.valueOf(studentMobile);
		boolean pt = Pattern.matches("[6789][0-9]{9}",l);
		if(pt) return true;
		else return false;
	}
	// Validates email
    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regex, email);
    }
    public static boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        String upperCaseChars = "(.*[A-Z].*)";
        String lowerCaseChars = "(.*[a-z].*)";
        String numbers = "(.*[0-9].*)";
        String specialChars = "(.*[!@#$%^&*].*)";
        return password.matches(upperCaseChars) && password.matches(lowerCaseChars)
                && password.matches(numbers) && password.matches(specialChars);
    }

}
