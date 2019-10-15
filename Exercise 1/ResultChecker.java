package com.bham.pij.exercises.e1c;

import java.util.Scanner;

public class ResultChecker {

	// This part asks for the user's input
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		System.out.println("Please enter 8 module marks and a project mark.");

		int[] grades = new int[9];

		for (int i = 0; i < grades.length; i++) {
		
		grades[i] = in.nextInt();
		
		}
				
		System.out.println(getResult(grades)); 

	}

	// This part determines if any of the grades are an error or a fail
	public static String getResult(int[] grades) {
		int sum = 0;
		for (int i = 0; i < grades.length; i++) {
			sum += grades[i];

			int grade = grades[i];

			if (grade < 0 || grade >100) {
		
			return ("ERROR");

			}

			else if  (grade < 40)

			return ("FAIL");

		}
		
		// This part calculates the mean of the grades and determines the result

		int mean = sum / grades.length;
	

		if (mean < 40) {

			return ("FAIL");

		}

		if (mean >= 40 && mean < 50) {

			return ("PASS");
		}

		if (mean >= 50 && mean < 70) {

			return ("MERIT");
		
		}

		if (mean >= 70) {

			return ("DISTINCTION");

		}

		return null;
	}
}




		 		

		

	
