package electrical;

/**
 * This class represents complex numbers, and defines methods for performing
 * arithmetic on complex numbers.
 *
 * @author iouri
 *
 */
public class Complex {

	private double a;
	private double b;
	private double r;
	private double theta;

	/**
	 * Create a new complex number. If the number is created in polar form, the
	 * angle is in degree.
	 * 
	 * @param rOrA
	 * @param thetaOrB
	 * @param isPolar
	 */
	private Complex(double rOrA, double thetaOrB, boolean isPolar) {

		if (isPolar) {

			r = rOrA;
			theta = thetaOrB / 180. * Math.PI;
			a = r * Math.cos(theta);
			b = r * Math.sin(theta);
		}

		else {

			a = rOrA;
			b = thetaOrB;
			r = Math.sqrt(a * a + b * b);
			theta = Math.atan(b / a);
		}
	}

	public double real() {
		return a;
	}

	public double imag() {
		return b;
	}

	public double abs() {
		return r;
	}

	public double angle() {
		return theta / Math.PI * 180.;
	}

	@Override
	public String toString() {
		return "(" + a + ", " + b + ")";
	}

	/*
	 * Static methods
	 */

	// Factory
	public static Complex getComplexPol(double r, double theta) {

		return new Complex(r, theta, true);
	}

	public static Complex getComplexRect(double a, double b) {

		return new Complex(a, b, false);
	}

	// Arithmetic

	/**
	 * Addition of two complex numbers
	 * 
	 * @param c1
	 * @param c2
	 * @return c1 + c2
	 */
	public static Complex add(Complex c1, Complex c2) {
		return new Complex(c1.a + c2.a, c1.b + c2.b, false);
	}

	/**
	 * Subtraction of two complex numbers
	 * 
	 * @param c1
	 * @param c2
	 * @return c1 - c2
	 */
	public static Complex sub(Complex c1, Complex c2) {
		return new Complex(c1.a - c2.a, c1.b - c2.b, false);
	}

	/**
	 * Multiplication of two complex numbers
	 * 
	 * @param c1
	 * @param c2
	 * @return c1 * c2
	 */
	public static Complex mul(Complex c1, Complex c2) {
		return new Complex(c1.r * c2.r, c1.angle() + c2.angle(), true);
	}

	/**
	 * Division of two complex numbers
	 * 
	 * @param c1
	 * @param c2
	 * @return c1 / c2
	 */
	public static Complex div(Complex c1, Complex c2) {
		return new Complex(c1.r / c2.r, c1.angle() - c2.angle(), true);
	}

	// Un peu de bonus
	/**
	 * Return the inverse of complex number c (1/c)
	 * 
	 * @param c
	 * @return 1 / c
	 */
	public static Complex inv(Complex c) {
		return new Complex(1. / c.r, -c.angle(), true);
	}
}