package com.bham.pij.exercises.e1d;

import java.util.Scanner;

public class IDCreator {

	// This part asks for the user's input.

	public static void main (String[] args) {

		Scanner input = new Scanner(System.in);

		System.out.println("Please enter a student's name.");

		String name = input.nextLine();	

		String name2 = new String(name.toLowerCase());

		// This part converts the user's input into lowercase characters.

		System.out.println(createID(name2));
	}

	public static String createID(String input) {

		// This part initialises a space.

		int spacePos = input.indexOf(' ');

		// This part initialises the second space.

		int k = input.indexOf(' ', input.indexOf(' ') + 1);
		
		String ID = input.substring(0, 1);

		String ID2 = input.substring(spacePos+1, spacePos+2);

		String ID3 = input.substring(k+1, k+2);	

		// This part counts the spaces in the input.

		int spaces = 0; 

		for (int i = 0; i < input.length(); i++) {

		if (input.charAt(i) == ' ') {

		spaces++; }

		}


		if (spaces == 2) {

			return ID + ID2 + ID3; 
		}

		if (spaces == 1) {

			return ID + "x" + ID2;

		}

		if (spaces == 0) {

			return null; 

		}

		else {

			return null;

		}
	}
		
} 



		

		
