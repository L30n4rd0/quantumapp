package com.ufrpe.ppgia.quantumapp.circuit;

import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;

/**
 * Classe que modela cada linha do circuito quântico. Guarda o seu ket e uma
 * lista de portas que são aplicadas
 * 
 * @author luciano
 *
 */
public class CircuitLine {

	int ket;
	List<Integer> listGates;

	public CircuitLine() {

		listGates = new ArrayList<Integer>();

	}

	public int getKet() {
		return ket;
	}

	public void setKet(int ket) {
		this.ket = ket;
	}

	public List<Integer> getListGates() {
		return listGates;
	}

	public void setGate(Integer gate) {
		this.listGates.add(gate);
	}

}
