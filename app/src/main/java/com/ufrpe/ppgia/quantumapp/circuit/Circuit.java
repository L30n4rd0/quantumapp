package com.ufrpe.ppgia.quantumapp.circuit;

import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;

public class Circuit {

	public static void main(String[] args) {

		Utils utils = Utils.getInstance();
		StateMachine stateMachine = StateMachine.getInstance();

		/*
		 * Classe que modela os qubits de cada linha do circuito
		 */
		CircuitLine qubit1 = new CircuitLine();
		CircuitLine qubit2 = new CircuitLine();
		CircuitLine qubit3 = new CircuitLine();
		CircuitLine qubit4 = new CircuitLine();

		/*
		 * Método que seta o ket no objeto
		 */
		qubit1.setKet(0);
		qubit2.setKet(0);
		qubit3.setKet(0);
		qubit4.setKet(0);

		/*
		 * Método para inserir ID das portas
		 */
		qubit1.setGate(1);
		qubit2.setGate(1);
		qubit3.setGate(1);
		qubit4.setGate(1);
		qubit4.setGate(1);
		qubit4.setGate(1);
		qubit4.setGate(1);

		/*
		 * Lista que recebe os qubits da tela do circuito.
		 */
		List<CircuitLine> listQubits = new ArrayList<>();
		listQubits.add(qubit1);
		listQubits.add(qubit2);
		listQubits.add(qubit3);
		listQubits.add(qubit4);
		
		/*
		 * Aplicação dos operadores
		 */
		List<Matrix> m = stateMachine.circuitCalculator(listQubits);
		
		/*
		 * Produto tensorial
		 */
		utils.printMatrix(utils.tensor(m));
		
		/*
		 * Calculo das porcentagens
		 */
		utils.printMatrix(utils.percentCalculator(utils.tensor(m)));
		
	}
}
