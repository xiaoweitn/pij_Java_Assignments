package com.bham.pij.exercises.e1b;

import java.util.Scanner;

public class MeanGrade {

	public static void main(String[] args) {
		// This part asks for the user's input
		Scanner in = new Scanner(System.in);

		System.out.println("Please enter 4 grades.");

		int[] grades = new int[4];
		grades[0] = in.nextInt();
		grades[1] = in.nextInt();
		grades[2] = in.nextInt();
		grades[3] = in.nextInt();

		if(grades[0] > 100 || grades [0] < 0) {
			grades[0] = 0;
		}
		if(grades[1] > 100 || grades [0] < 0) {
			grades[1] = 0;
		}
		if(grades[2] > 100 || grades [0] < 0) {
			grades[2] = 0;
		}
		if(grades[3] > 100 || grades [0] < 0) {
			grades[3] = 0;
		}	

		System.out.println("The mean of the grades is:" + computeMean(grades));

	}

	// This part calculates the mean of the 4 grades
	public static double computeMean (int[] grades) {

		int sum = 0;
		for (int i = 0; i < grades.length; i++) {
			sum += grades[i];

		}
		return (sum/grades.length);

	}
}	
