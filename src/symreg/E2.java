package symreg;

import net.sf.jclec.exprtree.fun.Argument;

public class E2 extends Argument<Double> {
	private static final long serialVersionUID = -653953979823993607L;

	public E2() {
		super(Double.class, 0);
	}

	// java.lang.Object methods

	public boolean equals(Object other) {
		return other instanceof E2;
	}

	public String toString() {
		return "E2";
	}
}