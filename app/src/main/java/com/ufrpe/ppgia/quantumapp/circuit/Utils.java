package com.ufrpe.ppgia.quantumapp.circuit;

import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;

public class Utils {

	private static Utils instance = null;

	private Utils() {

	}

	public static Utils getInstance() {

        if (instance == null) {
            instance = new Utils();
        }

		return instance;

	}

	/**
	 * Método para executar o calculo do produto tensorial
	 * @param listMatrix
	 * @return matrix
	 */
	public Matrix tensor(List<Matrix> listMatrix) {

        Matrix matrixCircuitResult = listMatrix.get(0);

        if (listMatrix.size() > 1) {
            matrixCircuitResult = tensorProduct(listMatrix.get(0), listMatrix.get(1));

            for (int i = 2; i < listMatrix.size(); i++) {

                matrixCircuitResult = tensorProduct(matrixCircuitResult, listMatrix.get(i));

            }
        }

		return matrixCircuitResult;

	}

	/**
	 * Método auxiliar que calcula o produto tensorial entre duas matrizes
	 * @param matrixOne
	 * @param matrixTwo
	 * @return matrix
	 */
	public Matrix tensorProduct(Matrix matrixOne, Matrix matrixTwo) {

		Matrix matrixResult = new Matrix(matrixOne.getRowDimension()
				* matrixTwo.getRowDimension(), matrixOne.getColumnDimension()
				* matrixTwo.getColumnDimension());

		List<Double> listResult = new ArrayList<Double>();

		for (int i = 0; i < matrixOne.getRowDimension(); i++) {

			for (int j = 0; j < matrixOne.getColumnDimension(); j++) {

				for (int k = 0; k < matrixTwo.getRowDimension(); k++) {

					for (int w = 0; w < matrixTwo.getColumnDimension(); w++) {

						listResult.add(matrixOne.get(i, j)
								* matrixTwo.get(k, w));

					}

				}

			}

		}

		for (int i = 0; i < matrixResult.getRowDimension(); i++) {

			for (int j = 0; j < matrixResult.getColumnDimension(); j++) {

				matrixResult.set(i, j, listResult.get(0));
				listResult.remove(0);

			}

		}

		return matrixResult;
	}
	
	/**
	 * Método que recebe uma matrix e calcula os percentuais
	 * em cada elemento de acordo com o seu valor
	 * @param matrix
	 * @return matrix
	 */
	public Matrix percentCalculator(Matrix matrix){
		
		int denominator = 0;
		
		for (int i = 0; i < matrix.getRowDimension(); i++) {
			
			for (int j = 0; j < matrix.getColumnDimension(); j++) {
				
				if (matrix.get(i, j) != 0) {
					matrix.set(i, j, 1.0);
					denominator += 1;
				}  else {
					matrix.set(i, j, 0.0);
				}
				
			}
			
		}
		
		return matrix.times(100.0/denominator);
		
	}

	/**
	 * Método que imprime umam matriz
	 * @param m
	 */
	public void printMatrix(Matrix m) {

		m.print(m.getRowDimension(), m.getColumnDimension());

	}

}
