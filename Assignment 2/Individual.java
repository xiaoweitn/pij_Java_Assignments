package com.bham.pij.assignments.a2a;
import java.util.Random;

public class Individual {

	private int fitness = 0;
	private String genes;
	private int geneLength;

	public Individual(String genes, int fitness) {
		this.genes = genes;
		this.fitness = fitness;
	}

	public String getGene() {
		return genes;
	}

	public void setGene(String genes) {
		this.genes = genes;
	}

	public double getFitness() {
		return this.fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public String toString() {
		return genes;
	} 

}