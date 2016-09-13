package symreg;

import net.sf.jclec.exprtree.fun.Argument;

public class Pt1 extends Argument<Double> {
	private static final long serialVersionUID = -653953979823993607L;

	public Pt1() {
		super(Double.class, 0);
	}

	// java.lang.Object methods

	public boolean equals(Object other) {
		return other instanceof Pt1;
	}

	public String toString() {
		return "Pt1";
	}
}