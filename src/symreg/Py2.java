package symreg;

import net.sf.jclec.exprtree.fun.Argument;

public class Py2 extends Argument<Double> {
	private static final long serialVersionUID = -653953979823993607L;

	public Py2() {
		super(Double.class, 0);
	}

	// java.lang.Object methods

	public boolean equals(Object other) {
		return other instanceof Py2;
	}

	public String toString() {
		return "Py2";
	}
}