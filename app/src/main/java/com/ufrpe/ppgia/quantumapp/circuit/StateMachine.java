package com.ufrpe.ppgia.quantumapp.circuit;

import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;

/**
 * Classe que contêm o método que executa
 * os operadores no circuito
 * @author luciano
 *
 */
public class StateMachine {

	private static StateMachine instance = null;

	Gates gates = null;
	Ket ket = null;

	private StateMachine() {

		gates = Gates.getInstance();
		ket = new Ket();

	}

	public static StateMachine getInstance() {

		if (instance == null) {
            instance = new StateMachine();
        }

		return instance;

	}

	public List<Matrix> circuitCalculator(List<CircuitLine> circuitLines) {
		
		List<Matrix> listMatrix = new ArrayList<Matrix>();

		for (int i = 0; i < circuitLines.size(); i++) {

			Matrix matrix = null;

			if (circuitLines.get(i).getKet() == 0) {

				matrix = ket.getKetZero();

			} else {

				matrix = ket.getKetOne();

			}

			for (int j = 0; j < circuitLines.get(i).getListGates().size(); j++) {

				switch (circuitLines.get(i).getListGates().get(j)) {

				// Porta de Hadamard
				case 1:

					matrix = gates.hadamard(matrix);

					break;

				// Porta Pauli-X
				case 2:

					matrix = gates.pauliX(matrix);

					break;

				// Porta Pauli-Y
				case 3:

					matrix = gates.pauliY(matrix);

					break;

				// Porta Pauli-Z
				case 4:

					matrix = gates.pauliZ(matrix);

					break;

				// Porta Phase
				case 5:

					matrix = gates.phase(matrix);

					break;

				// Porta piEight
				case 6:

					matrix = gates.piEight(matrix);

					break;

				default:

					break;

				}

			}
			
			listMatrix.add(matrix);

		}
		
		return listMatrix;

	}

}
