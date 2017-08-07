package com.ufrpe.ppgia.quantumapp.circuit;

import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;

/**
 * Classe que contêm as implementações dos métodos que representam os operadores
 * 
 * @author luciano
 *
 */
public class Gates {

	private static Gates instance;

	public Gates() {

	}

	public static Gates getInstance() {

		instance = new Gates();

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

		Complex complex = new Complex(1, 1, 1);

		double[][] matrix = { { 1, 0 },
				{ 0, Math.exp((complex.getImaginary() * Math.PI) / 4) } };

		Matrix A = new Matrix(matrix);

		Matrix C = A.times(ket);

		return C;

	}

	public void controlledNot(List<CircuitLine> circuitLines, int line,
			int indexGate) {

		int ket = circuitLines.get(line).getKet();

		int gate = circuitLines.get(line).getListGates().get(indexGate);

		for (int i = line + 1; i < circuitLines.size(); i++) {

			if (ket == 1) {

				if (!circuitLines.get(i).getListGates().isEmpty()) {

					if (circuitLines.get(i).getListGates().get(indexGate) == gate) {

						if (circuitLines.get(i).getKet() == 0) {

							circuitLines.get(i).setKet(1);

						} else {

							circuitLines.get(i).setKet(0);

						}

					}

				}

			}

		}

	}

	public void swap(List<CircuitLine> circuitLines, int line, int indexGate) {

	}

	public void printMatrix(Matrix m) {

		m.print(m.getRowDimension(), m.getColumnDimension());

	}

}
