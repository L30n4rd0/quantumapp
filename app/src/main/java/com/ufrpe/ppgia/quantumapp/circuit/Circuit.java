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
		CircuitLine circuitLine = new CircuitLine();

		/*
		 * Método que seta o ket no objeto
		 */
		circuitLine.setKet(1);

		/*
		 * Método para inserir ID das portas
		 */
		//circuitLine.setListGate(1);
		//circuitLine.setListGate(2);
		//circuitLine.setListGate(3);
		//circuitLine.setListGate(4);
		//circuitLine.setListGate(5);
		//tListGates(6);

		/*
		 * Lista que recebe os qubits da tela do circuito.
		 */
		List<CircuitLine> listCircuitLines = new ArrayList<>();
		listCircuitLines.add(circuitLine);
		
		/*
		 * Os qubits são passados para máquina de estados e devolve uma matriz.
		 * Caso seja melhor o objeto CircuitLine pode ser passado diretamente ou criado um laço para
		 * percorrer a lista e fazer de forma iterativa.
		 */
		Matrix matrix = stateMachine.circuitCalculator(listCircuitLines.get(0));
		
		circuitLine.setResult(matrix);

		utils.printMatrix(circuitLine.getResult());

	}
}
