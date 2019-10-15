package com.bham.pij.assignments.a2a;
import java.util.Random;

public class Weasel extends GAApplication{

	protected String weasel;

	public Weasel(String weasel) {
		this.weasel = weasel;
		initPopulation();
	}

	private String createGene() {
		String genes = "";
		Random rn = new Random();
		for (int i = 0; i < weasel.length(); i++) {
			int temp = rn.nextInt(91) + 32; // generating a random string in integer form
			char ch = (char)(temp); // converts the string into ASCII values
			genes += ch; // turns the ASCII values into strings
		}
		return genes;
	}

	private void initPopulation() { // initialising the population
		for (int i = 0; i < populationSize; i++) {
			String gene = createGene();
			int fitness = calcFitness(gene);
			population[i] = new Individual(gene, fitness); //  storing the individual genes within the population
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
			//int mutationPoint = rn.nextInt(weasel.length());
			String gene = population[i].getGene();
			StringBuilder sb = new StringBuilder(gene);
			for (int j = 0; j < weasel.length(); j++) { 
				if (rn.nextFloat() < mutation) { // if mutation probability is met
					int temp = Math.abs(rn.nextInt() % 2);
					if (temp == 1) {
						if (gene.charAt(j) == 127) { // if the character is at the upper bound
							sb.setCharAt(j, (char)(gene.charAt(j) - 1)); // decrement
						}

						else {
							sb.setCharAt(j, (char)(gene.charAt(j) + 1)); // else, increment
						}
					}

					else {
						if (gene.charAt(j) == 32) { // if the character is at the lower bound
							sb.setCharAt(j, (char)(gene.charAt(j) + 1)); // increment
						}

						else {
							sb.setCharAt(j, (char)(gene.charAt(j) - 1)); // else, decrement
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
				crossOverPoint = rn.nextInt(weasel.length()); // crossover point is a random point in the string
			} while (crossOverPoint < 1 || crossOverPoint > weasel.length() -2); 
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

	public Individual getBest() { // the fittest individual is at the top of the mutated population
		return population[0];
	}

	public int calcFitness(String genes) { // the lower the difference between the target character and the character, the fitter the individual
		int fitness = 0;
		for (int i = 0; i < genes.length(); i++) {
			int temp = Math.abs(weasel.charAt(i) - genes.charAt(i));
			fitness += temp;
		}
		return fitness;
	}
}