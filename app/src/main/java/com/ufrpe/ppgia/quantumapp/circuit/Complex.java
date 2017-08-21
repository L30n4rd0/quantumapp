package com.ufrpe.ppgia.quantumapp.circuit;

/**
 * Classe que modela o número complexo.
 * É composto por uma parte real x e uma imaginária iy
 * @author luciano
 *
 */
public class Complex {

	private double x;
	private double y;
	private double imaginary;

	public Complex() {
		this(0.0, 0.0, 0.0);
	}

	public Complex(double r) {
		this(r, 0.0, 0.0);
	}

	public Complex(double r, double y, double i) {
		x = r;
		imaginary = y * i
		;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getImaginary() {
		return imaginary;
	}

}
