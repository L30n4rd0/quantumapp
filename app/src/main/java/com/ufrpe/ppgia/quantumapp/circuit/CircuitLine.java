package com.ufrpe.ppgia.quantumapp.circuit;

import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;

public class CircuitLine {

	int ket;
	Matrix result;
	List<Integer> listGates;

	public CircuitLine() {

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

	public void setListGate(Integer gates) {
		this.listGates.add(gates);
	}

}
