package com.bham.pij.exercises.e1f;

import java.util.Scanner;

public class ShortAddressFinder {

	// This part asks for the user's input.

	public static void main (String[] args) {

	Scanner input = new Scanner(System.in);

	System.out.println("Please enter an address.");

	String x = input.nextLine();

	System.out.println(findShortAddress(x));

	}

	// This part lists the conditions.

	public static String findShortAddress(String input) {

	String sAddress = "";

	String[] fAddress = input.split(", ", -1);

	String postcode = fAddress[fAddress.length -1];

	if (input == null) {

	return null;

	}

	// This part validates the postcode's format.

	if (Character.isLetter((postcode.charAt(0))) && Character.isDigit((postcode.charAt(1))) && Character.isDigit((postcode.charAt(2))) && Character.isDigit	((postcode.charAt(3))) && Character.isLetter((postcode.charAt(4))) && Character.isLetter((postcode.charAt(5)))) {

	sAddress += (fAddress[0] + " " + fAddress[fAddress.length - 1]);

	return sAddress;
	
	}

	return null;

	}

}

	
