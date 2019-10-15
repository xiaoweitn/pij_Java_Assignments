package com.bham.pij.assignments.a2a;

// this class was borrowed from www.geeksforgeeks.org/selection-sort/
// sorting is done according to selection sort

public class Utility {
	void sort(Individual arr[]) {
		int n = arr.length;
		for (int i = 0; i < n-1; i++) { // one by one move boundary of unsorted array
			int min_idx = i; // find the minimum element in unsorted array
			for (int j = i + 1; j < n; j++) {
				if (arr[j].getFitness() < arr[min_idx].getFitness()) {
					min_idx = j;
				}
			}
			Individual temp = arr[min_idx]; // swap the found minimum element with the first element
			arr[min_idx] = arr[i];
			arr[i] = temp;
		}
	}
}