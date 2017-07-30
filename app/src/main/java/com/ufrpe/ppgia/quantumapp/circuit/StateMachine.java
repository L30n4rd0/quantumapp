package com.ufrpe.ppgia.quantumapp.circuit;

import Jama.Matrix;

public class StateMachine {

	private static StateMachine instance;

	Gates gates = null;
	Ket ket = null;

	public StateMachine() {

		gates = Gates.getInstance();
		ket = new Ket();

	}

	public static StateMachine getInstance() {

		instance = new StateMachine();

		return instance;

	}

	public Matrix stateMachine(Qubit qubits) {

		Matrix matrix = null;

		if (qubits.getKet() == 0) {

			matrix = ket.getKetZero();

		} else {

			matrix = ket.getKetOne();

		}

		int count = 0;

		while (count < qubits.getListGates().size()) {

			switch (qubits.getListGates().get(count)) {

			// Porta de Hadamard
			case 1:

				matrix = gates.hadamard(ket.getKetZero(), ket.getKetZero(),
						qubits.getKet());

				System.out.println("Hadamard");

				break;

			// Porta Pauli-X
			case 2:

				matrix = gates.pauliX(matrix);

				System.out.println("PauliX");

				break;

			// Porta Pauli-Y
			case 3:

				matrix = gates.pauliY(matrix);

				System.out.println("PauliY");

				break;

			// Porta Pauli-Z
			case 4:

				matrix = gates.pauliZ(matrix);

				System.out.println("PauliZ");

				break;

			// Porta Phase
			case 5:

				matrix = gates.phase(matrix);

				System.out.println("Phase");

				break;

			// Porta piEight
			case 6:

				matrix = gates.piEight(matrix);

				System.out.println("piEight");

				break;

			default:

				break;

			}

			count += 1;

		}

		return matrix;

	}

}
