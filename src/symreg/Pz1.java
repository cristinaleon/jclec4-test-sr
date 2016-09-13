package symreg;

import net.sf.jclec.exprtree.fun.Argument;

public class Pz1 extends Argument<Double> {
	private static final long serialVersionUID = -653953979823993607L;

	public Pz1() {
		super(Double.class, 0);
	}

	// java.lang.Object methods

	public boolean equals(Object other) {
		return other instanceof Pz1;
	}

	public String toString() {
		return "Pz1";
	}
}