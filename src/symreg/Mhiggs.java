package symreg;

import net.sf.jclec.exprtree.fun.Argument;

public class Mhiggs extends Argument<Double> {
	private static final long serialVersionUID = -653953979823993607L;

	public Mhiggs() {
		super(Double.class, 0);
	}

	// java.lang.Object methods

	public boolean equals(Object other) {
		return other instanceof Mhiggs;
	}

	public String toString() {
		return "MH";
	}
}