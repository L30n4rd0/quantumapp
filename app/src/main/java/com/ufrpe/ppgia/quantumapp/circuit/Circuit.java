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
		Qubit qubit = new Qubit();

		/*
		 * Método que seta o ket no objeto
		 */
		qubit.setKet(1);

		/*
		 * Método para inserir ID das portas
		 */
		qubit.setListGates(2);
		qubit.setListGates(2);
		qubit.setListGates(3);
		qubit.setListGates(4);
		qubit.setListGates(5);
		qubit.setListGates(6);

		/*
		 * Lista que recebe os qubits da tela do circuito.
		 */
		List<Qubit> listQubits = new ArrayList<>();
		listQubits.add(qubit);
		
		/*
		 * Os qubits são passados para máquina de estados e devolve uma matriz.
		 * Caso seja melhor o objeto Qubit pode ser passado diretamente ou criado um laço para
		 * percorrer a lista e fazer de forma iterativa.
		 */
		Matrix matrix = stateMachine.stateMachine(listQubits.get(0));
		
		qubit.setResult(matrix);

		utils.printMatrix(qubit.getResult());

	}
}
