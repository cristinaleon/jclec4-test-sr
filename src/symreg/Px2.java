package symreg;

import net.sf.jclec.exprtree.fun.Argument;

public class Px2 extends Argument<Double> {
	private static final long serialVersionUID = -653953979823993607L;

	public Px2() {
		super(Double.class, 0);
	}

	// java.lang.Object methods

	public boolean equals(Object other) {
		return other instanceof Px2;
	}

	public String toString() {
		return "Px2";
	}
}