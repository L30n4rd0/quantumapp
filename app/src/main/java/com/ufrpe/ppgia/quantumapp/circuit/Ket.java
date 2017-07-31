package com.ufrpe.ppgia.quantumapp.circuit;

import Jama.Matrix;

public class Ket {

	private Matrix ketZero;
	private Matrix ketOne;

	public Ket() {

	}

	public Matrix getKetOne() {
		double[][] zero = { { 0 }, { 1 } };
		ketZero = new Matrix(zero);
		return ketZero;
	}

	public Matrix getKetZero() {
		double[][] one = { { 1 }, { 0 } };
		ketOne = new Matrix(one);
		return ketOne;
	}

}
