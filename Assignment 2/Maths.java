package com.bham.pij.assignments.a2a;

import java.util.Random;
import java.util.ArrayList;

public class Maths extends GAApplication {

	protected int number;
	protected int length;
	String[] operationSet = new String[]{"+", "-", "/", "*"};

	public Maths(int number, int length) {
		this.number = number;
		this.length = length;
		initPopulation();
	}

	private String createGene() {
		String genes = "";
		Random rn = new Random();
		int counter = 0;
		while (genes.length() < length) { // while length of created expression is shorter than the given expression length
			if (counter % 2 == 0) { // adds the numbers and operators in an alternate fashion
				int temp = rn.nextInt(Byte.MAX_VALUE) - rn.nextInt(Byte.MAX_VALUE); // randomly creates a number
				genes += Integer.toString(temp); // converts the integer into a string
			}
			else {
				int index = rn.nextInt(4); // takes a random operator from the operator set
				genes += operationSet[index];
			}
			counter++;
		}

		if (genes.length() > length) { // if the length of created expression is longer than the given length
			genes = genes.substring(0, length + 1); // take everything up to the given length
		}

		return genes;
	}

	private void initPopulation() { // initialising the population
		for (int i = 0; i < populationSize; i++) { 
			String gene = createGene();
			int fitness = calcFitness(gene);
			population[i] = new Individual(gene, fitness); // storing the individual genes within the population
		}
	}

	public void run() {
		//initPopulation();
		//displayPopulation();
		mutate();
		crossover();
		//System.out.println("");
		//displayPopulation();
	}

	private void mutate() {
		Random rn = new Random();
		for (int i = 0; i < populationSize; i++) { // iterates through the population
			//int mutationPoint = rn.nextInt(length);
			String gene = population[i].getGene();
			StringBuilder sb = new StringBuilder(gene);
			for (int j = 0; j < length; j++) {
				if (rn.nextFloat() < mutation) { // if mutation probability is met
					int temp = Math.abs(rn.nextInt() % 2); // flips a coin
					if (temp == 1) { // if its a 1
						if (gene.charAt(j) >= '0' && gene.charAt(j) <= '9') { // if a number is found 
							if (gene.charAt(j) == '9') { // if its a 9
								sb.setCharAt(j, '0'); // turn it to a 0
							}
							else {
								sb.setCharAt(j, (char)(gene.charAt(j) + 1)); // else, increment the number
							}
						}
						else {
							if (gene.charAt(j) == operationSet[3].charAt(0)) { // if a multiply is found
								sb.setCharAt(j, operationSet[0].charAt(0)); // change it to an addition
							}
							else {
								int index = -1;
								for (int k = 0; k < operationSet.length; k++) {
									if (operationSet[k].charAt(0) == (gene.charAt(j))) { // if an operator is found
										index = k;
										break;
									}
								}
								sb.setCharAt(j, operationSet[index+1].charAt(0)); // change it to the next one in the operation set
							}
						}
					}	
					else { // if its a 0
						if (gene.charAt(j) >= '0' && gene.charAt(j) <= '9') { // if a number is found
							if (gene.charAt(j) == '0') {  //if its a 0
								sb.setCharAt(j, '9'); // set it to a 9
							}
							else {
								sb.setCharAt(j, (char)(gene.charAt(j) - 1)); // else, decrement the value
							}
						}
						else {
							if (gene.charAt(j) == operationSet[0].charAt(0)) { // if an addition is found
								sb.setCharAt(j, operationSet[3].charAt(0)); // change it to a multiply
							}
							else {
								int index = -1;
								for (int k = 0; k < operationSet.length; k++) {
									if (operationSet[k].charAt(0) == (gene.charAt(j))) { // if an operator is found
										index = k;
										break;
									}
								}
								sb.setCharAt(j, operationSet[index-1].charAt(0)); // change it to the one before it in the operation set
							}
						}
					}
				}
			}	
			gene = sb.toString();
			int fitness = calcFitness(gene);
			population[i].setGene(gene);
			population[i].setFitness(fitness);
		}	
		sort();
	}

	private void sort() {
		Utility util = new Utility();
		util.sort(population);
	}

	private void crossover() {
		Random rn = new Random();
		Individual[] offsprings = new Individual[offspringNum];
		for (int i = 0; i < offspringNum; i += 2) {
			int crossOverPoint = 0;
			do {
				crossOverPoint = rn.nextInt(length); // crossover point is a random point in the string
			} while (crossOverPoint < 1 || crossOverPoint > length -2); 
			int rand1 = rn.nextInt(parentsNum);
			int rand2 = rn.nextInt(parentsNum);
			while (rand1 == rand2) {
				rand2 = rn.nextInt(parentsNum);
			}
				Individual parent1 = population[rand1];
				Individual parent2 = population[rand2];
				if (rn.nextFloat() < crossover) { // if crossover probability is met
					String gene1 = parent1.getGene();
					String gene2 = parent2.getGene();
					String gene3 = gene1.substring(0, crossOverPoint) + gene2.substring(crossOverPoint, gene2.length());
					String gene4 = gene2.substring(0, crossOverPoint) + gene1.substring(crossOverPoint, gene1.length());
					int fitness1 = calcFitness(gene3);
					int fitness2 = calcFitness(gene4);
					Individual offspring1 = new Individual(gene3, fitness1);
					Individual offspring2 = new Individual(gene4, fitness2);
					offsprings[i] = offspring1;
					offsprings[i+1] = offspring2;
				}
				else { 
					Individual offspring1 = parent1;
					Individual offspring2 = parent2;
					offsprings[i] = offspring1;
					offsprings[i+1] = offspring2;
				}

		}

		for (int i = 0; i < offspringNum; i++) { // replaces the worst individuals with the offspring created
			population[i] = offsprings[i];
		}
		sort();	
	}

	public Individual getBest() {
		return population[0];
	}

	public int calcFitness(String genes) {
		int fitness = 0;
		ArrayList<String> arr = new ArrayList<String>();
		int cur = 0; // current index
		while (cur < genes.length()) { // makes sure the operator and the number are in different indexes in the arraylist
			String temp;
			int pt = cur; // pt stands for last point and cur stands for current point

			for (int i = cur; i < genes.length(); i++) {
				if (genes.charAt(i) >= '0' && genes.charAt(i) <= '9') { // if the character in the string is a number
					pt++; 
					if (i == genes.length() -1 || genes.charAt(i + 1) < '0' || genes.charAt(i + 1) > '9') { // if the index is out of bounds or the next index isn't a number
						break; // break out of the loop
					}
				}
				else {
					pt++;
					if (i == genes.length() -1 || (genes.charAt(i + 1) >= '0' && genes.charAt(i + 1) <= '9')) {
						break;
					}
				}
			}

			temp = genes.substring(cur, pt); // substrings out the number or the operator
			arr.add(temp); // adds them to the arraylist
			cur += temp.length(); 
		}

		if (arr.get(0).contains("+") || arr.get(0).contains("-") || arr.get(0).contains("*") || arr.get(0).contains("/")) { // if the first index of the array contains any operators, remove them
			arr.remove(0);
		}
		
		if (arr.get(arr.size() - 1).contains("+") || arr.get(arr.size() - 1).contains("-") || arr.get(arr.size() - 1).contains("*") // if the last index of the array contains any operators, remove them
				|| arr.get(arr.size() - 1).contains("/")) {
			arr.remove(arr.size() - 1);
		}
		
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).contains("+") || arr.get(i).contains("-") || arr.get(i).contains("*") || arr.get(i).contains("/")) { // if there are more than one operator together, take the first operator 
				String temp = arr.get(i);
				if (temp.length() > 1) {
					temp = temp.substring(0, 1);
					arr.set(i, temp);
				}
			}
		}


		while (arr.size() > 1) {
			int addIndex = arr.indexOf("+");
			int subIndex = arr.indexOf("-");
			int mulIndex = arr.indexOf("*");
			int divIndex = arr.indexOf("/");
			int opIndex = -1;

			if (mulIndex != -1 && divIndex != -1) {
				if (mulIndex > divIndex) { // if division is after multiplication
					opIndex = divIndex;  // do division first
				}
				else {
					opIndex = mulIndex; // else do multiplication
				}
			}
			else if (mulIndex == -1 && divIndex == -1) { // if there's no more multiplication and division
				opIndex = -1; 
			}
			else {
				if (mulIndex == -1) { // if there's no multiplication
					opIndex = divIndex; // do division
				}
				else {
					opIndex = mulIndex; // else, do multiplication
				}
			}
			if (opIndex == -1) { // if there are no multiplications and divisions, break out of the loop
				if (addIndex != -1 && subIndex != -1) {
					if (addIndex > subIndex) { // if addition is after subtraction
						opIndex = subIndex; // do subtraction
					}
					else {
						opIndex = addIndex; // else, do addition
					}
				}
				else if (addIndex == -1 && subIndex == -1) {
					opIndex = -1;
				}
				else {
					if (addIndex == -1) { // if there are no more addition operators
						opIndex = subIndex; // do subtraction
					}
					else {
						opIndex = addIndex; // else, do addition
					}
				}
			}

			if (opIndex == -1) { // if there are no more operators, break out of the loop
				break;
			}

			String op = arr.get(opIndex); // looks for operators 
			int num1, num2;
				try {
					num1 = Integer.parseInt(arr.get(opIndex-1));
				}
				catch(NumberFormatException e) {
					num1 = Byte.MAX_VALUE;
				}
				try {
					num2 = Integer.parseInt(arr.get(opIndex+1));
				}
				catch (NumberFormatException e) {
					num2 = Byte.MAX_VALUE;
				}
			int result = 0;
			switch(op) { // takes care of operator precedence
				case "+": // finds a +
				result = num1 + num2; // adds the 2 adjacent numbers
				break;

				case "-": // finds a -
				result = num1 - num2; // subtracts the 2 adjacent numbers
				break;

				case "*": // finds a *
				result = num1 * num2; // mulitplies the 2 adjacent numbers
				break;

				case "/": // finds a / 
				if (num2 == 0) { // if the result is 0
					result = Byte.MAX_VALUE; // set it to "max"
				}
				else {
					result = num1 / num2; // else, divide the 2 adjacent numbers
				}
				break;
			}
			String res = Integer.toString(result);

			ArrayList<String> tempList = new ArrayList<String>();
			for (int i = 0; i < arr.size(); i++) {
				if (i == opIndex - 1 || i == opIndex + 1) {
					;
				}
				else if (i == opIndex) { // moves the answer to the new array
					tempList.add(res);
				}
				else {
					tempList.add(arr.get(i)); // moves everything else to the new array
				}
			}
			arr = tempList;
		}
		int answer = Integer.parseInt(arr.get(0)); // changes the answer to an integer
		fitness = Math.abs(number - answer); // fitness is equal to the difference between the result and given number

		return fitness;
	}
}