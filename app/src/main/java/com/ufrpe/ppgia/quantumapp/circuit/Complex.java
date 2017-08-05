package com.ufrpe.ppgia.quantumapp.circuit;

/**
 * Classe que modela o número complexo.
 * É composto por uma parte real x e uma imaginária iy
 * @author luciano
 *
 */
public class Complex {

	private double x;
	private double y = 0.5;
	private double imaginary;

	public Complex() {
		this(0.0, 0.0, 0.0);
	}

	public Complex(double r) {
		this(r, 0.0, 0.0);
	}

	public Complex(double r, double i, double y) {
		x = r;
		imaginary = i * y;
	}

	public double getReal() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getImaginary() {
		return imaginary;
	}

}
