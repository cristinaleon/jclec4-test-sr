package symreg;

import net.sf.jclec.exprtree.fun.Argument;

public class Px1 extends Argument<Double> {
	private static final long serialVersionUID = -653953979823993607L;

	public Px1() {
		super(Double.class, 0);
	}

	// java.lang.Object methods

	public boolean equals(Object other) {
		return other instanceof Px1;
	}

	public String toString() {
		return "Px1";
	}
}