package com.ufrpe.ppgia.quantumapp.circuit;

import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;

public class Qubit {

	int ket;
	Matrix result;
	List<Integer> listGates;

	public Qubit() {

		listGates = new ArrayList<Integer>();

	}

	public int getKet() {
		return ket;
	}

	public void setKet(int ket) {
		this.ket = ket;
	}

	public Matrix getResult() {
		return result;
	}

	public void setResult(Matrix result) {
		this.result = result;
	}

	public List<Integer> getListGates() {
		return listGates;
	}

	public void setListGates(Integer gates) {
		this.listGates.add(gates);
	}

}
