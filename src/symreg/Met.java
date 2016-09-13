package symreg;

import net.sf.jclec.exprtree.fun.Argument;

public class Met extends Argument<Double> {
	private static final long serialVersionUID = -653953979823993607L;

	public Met() {
		super(Double.class, 0);
	}

	// java.lang.Object methods

	public boolean equals(Object other) {
		return other instanceof Met;
	}

	public String toString() {
		return "Met";
	}
}