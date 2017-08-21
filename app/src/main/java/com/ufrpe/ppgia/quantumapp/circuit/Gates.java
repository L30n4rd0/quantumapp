package com.ufrpe.ppgia.quantumapp.circuit;

import Jama.Matrix;

/**
 * Classe que contêm as implementações dos métodos
 * que representam os operadores
 * @author luciano
 *
 */
public class Gates {

	private static Gates instance = null;

	private Gates() {

	}

	public static Gates getInstance() {

		if (instance == null) {
            instance = new Gates();
        }

		return instance;

	}

	public Matrix hadamard(Matrix matrix) {
		
		double factor = 1 / Math.sqrt(2);

		double[][] m = { { 1, 1 }, { 1, -1 } };

		Matrix A = new Matrix(m);

		Matrix C = A.times(matrix);

		C = C.times(factor);

		return C;

	}

	public Matrix pauliX(Matrix ket) {

		double[][] matrix = { { 0, 1 }, { 1, 0 } };

		Matrix A = new Matrix(matrix);

		Matrix C = A.times(ket);

		return C;

	}

	public Matrix pauliY(Matrix ket) {

		Complex complex = new Complex(1, 1, 1);

		double[][] matrix = { { 0, -complex.getImaginary() },
				{ complex.getImaginary(), 0 } };

		Matrix A = new Matrix(matrix);

		Matrix C = A.times(ket);

		return C;

	}

	public Matrix pauliZ(Matrix ket) {

		double[][] matrix = { { 1, 0 }, { 0, -1 } };

		Matrix A = new Matrix(matrix);

		Matrix C = A.times(ket);

		return C;

	}

	public Matrix phase(Matrix ket) {

		Complex complex = new Complex(1, 1, 1);

		double[][] matrix = { { 1, 0 }, { 0, complex.getImaginary() } };

		Matrix A = new Matrix(matrix);

		Matrix C = A.times(ket);

		return C;

	}

	public Matrix piEight(Matrix ket) {

		Complex complex = new Complex(1.0, 1.0, 0.4);

		double[][] matrix = { { 1, 0 }, { 0, (1 + complex.getImaginary())/Math.sqrt(2) } };

		Matrix A = new Matrix(matrix);

		Matrix C = A.times(ket);

		return C;

	}

	public void printMatrix(Matrix m) {

		m.print(m.getRowDimension(), m.getColumnDimension());

	}

}
