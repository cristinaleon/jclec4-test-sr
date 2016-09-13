package symreg;

import net.sf.jclec.exprtree.fun.Argument;

public class Mhiggs_squared extends Argument<Double> {
	private static final long serialVersionUID = -653953979823993607L;

	public Mhiggs_squared() {
		super(Double.class, 0);
	}

	// java.lang.Object methods

	public boolean equals(Object other) {
		return other instanceof Mhiggs_squared;
	}

	public String toString() {
		return "MH2";
	}
}