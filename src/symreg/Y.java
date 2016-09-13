package symreg;

import net.sf.jclec.exprtree.fun.Argument;

public class Y extends Argument<Double> {
	private static final long serialVersionUID = -653953979823993607L;

	public Y() {
		super(Double.class, 0);
	}

	// java.lang.Object methods

	public boolean equals(Object other) {
		return other instanceof Y;
	}

	public String toString() {
		return "Y";
	}
}