package com.bham.pij.exercises.e1e;

import java.util.Scanner;

public class GradeChecker {

	public static void main(String[] args) {	

		Scanner in = new Scanner(System.in);

		System.out.println("Please enter a grade.");
		
		int grade = in.nextInt();

	

		if(isValid(grade) && isPass(grade)) {
			System.out.println("Pass.");
		}

		if(isValid(grade) && !isPass(grade)) {
			System.out.println("Fail.");
		}

		if(!isValid(grade)) {
			System.out.println("Invalid input!");
		}
	}

	public static boolean isValid(int grade) {

		if (grade >= 0 && grade <= 100 ) { 
			return true;
		}

		else {
			return false;
		}
	}

	public static boolean isPass(int grade) {

		if (grade >= 40 ) {
			return true;
		}

		else {

			return false;
		}
	
	}

}




