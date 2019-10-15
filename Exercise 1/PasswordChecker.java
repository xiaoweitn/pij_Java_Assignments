package com.bham.pij.exercises.e1e;

import java.util.Scanner;

public class PasswordChecker {

	// This part asks for the user's input.

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		System.out.println("Please enter a password.");

		String pw = input.nextLine();

		System.out.println(checkPassword(pw));

	}

	// This part has all the conditions.

	public static String checkPassword(String input) {

		if (input.length() < 8) {

			return null;

		}

		if (input.length() > 12) {

			return null;

		}


		char c;

		if (Character.isDigit(input.charAt(0)))

			return null;

		int uppercase= 0;

		int lowercase= 0;

		int specials = 0;

			for (int i = 0; i < input.length(); i++) {

				c = input.charAt(i);

				if (Character.isLowerCase(input.charAt(i))) {

					lowercase++;

				}

				if (Character.isUpperCase(input.charAt(i))) {

					uppercase++;

				}

				
		
				if (Character.isDigit(c)) {

					return null;

				}

				if (input.charAt(i) == 95) {

					return "OK";

				}

				if (!(Character.isLetterOrDigit(c))) {

					specials = 1;

				}

			}

		if (specials >= 1) {

			return null;

		}


				
		if (lowercase < 1) {

			return null;

		}

		if (uppercase < 1) {

			return null;

		}

		if (uppercase >= 1 && lowercase >= 1) {

			return "OK";

		}


	return null;

	}

}
			

	

		
