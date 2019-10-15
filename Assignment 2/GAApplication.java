package com.bham.pij.assignments.a2a;
import java.util.Random;

public abstract class GAApplication {

	protected int populationSize = 1000;
	protected int parentsNum = 400;
	protected int offspringNum = 1000;
	protected float crossover = 0.9999f;
	protected float mutation = 0.001f;
	protected Individual[] population = new Individual[populationSize];

	public GAApplication() {

	}
	
	public abstract void run(); 

	public abstract Individual getBest();

	/*public void displayPopulation() {
		for (int i = 0; i < populationSize; i++) {
			System.out.println(population[i].toString());
		}
	}*/

}