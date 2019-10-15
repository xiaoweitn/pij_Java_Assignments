package com.bham.pij.assignments.a2a;
import java.util.Random;

public class BinaryMaximiser extends GAApplication{

	protected int length;

	public BinaryMaximiser(int length) {
		this.length = length;
		initPopulation();
	}

	private String createGene() {
		String genes = "";
		Random rn = new Random();
		for (int i = 0; i < length; i++) {
			int temp = Math.abs(rn.nextInt() % 2); // generating a random binary number in integer form
			char ch = (char)(temp + 48); // convert the number into ASCII values
			genes += ch; // turns the ASCII values into strings
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
		initPopulation();
		for (int i = 0; i < populationSize; i++) { // iterates through the population
			//int mutationPoint = rn.nextInt(length);
			String gene = population[i].getGene();
			StringBuilder sb = new StringBuilder(gene); // converts everything in the gene into strings
			for (int j = 0; j < length; j++) {
				if (rn.nextFloat() < mutation) { // if mutation probability is met
					if (gene.charAt(j) == '1') { // if its a 1
						sb.setCharAt(j, '0'); // set it to a 0
					}

					else { // if its a 0
						sb.setCharAt(j, '1'); // set it to a 1
					}
				}
			}	
			gene = sb.toString();
			int fitness = calcFitness(gene);
			population[i].setGene(gene);
			population[i].setFitness(fitness);
		}
	}

	private void sort() { // sorts the population in ascending order according to the fitness
		Utility util = new Utility();
		util.sort(population);
	}

	private void crossover() {
		Random rn = new Random();
		Individual[] offsprings = new Individual[offspringNum];
		for (int i = 0; i < offspringNum; i += 2) {
			int crossOverPoint = 0;
			do {
				crossOverPoint = rn.nextInt(length);
			} while (crossOverPoint < 1 || crossOverPoint > length -2); 
			int rand1 = rn.nextInt(parentsNum);
			int rand2 = rn.nextInt(parentsNum);
			while (rand1 == rand2) {
				rand2 = rn.nextInt(parentsNum);
			}
				Individual parent1 = population[populationSize -1 -rand1];
				Individual parent2 = population[populationSize -1 -rand2];
				if (rn.nextFloat() < crossover) {
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
		return population[population.length -1];
	}

	public int calcFitness(String genes) {

		int fitness = 0;
		for (int i = 0; i < genes.length(); i++) {
			if (genes.charAt(i) == ('1')) {
				++fitness;
			}				
		}
		return fitness;
	}
}	
