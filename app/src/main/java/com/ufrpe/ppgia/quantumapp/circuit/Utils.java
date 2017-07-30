package com.ufrpe.ppgia.quantumapp.circuit;

import Jama.Matrix;

public class Utils {

	private static Utils instance;

	public Utils() {

	}

	public static Utils getInstance() {

		instance = new Utils();

		return instance;

	}

	public void printMatrix(Matrix m) {

		m.print(m.getRowDimension(), m.getColumnDimension());

	}

}
