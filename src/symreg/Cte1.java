package symreg;

import net.sf.jclec.exprtree.fun.Argument;

public class Cte1 extends Argument<Double> {
	private static final long serialVersionUID = -653953979823993607L;

	public Cte1() {
		super(Double.class, 0);
	}

	// Como siempre no se como se hace esto
	//Cte1.value=1.0;
	double Cte1 = 1.0;
	//Cte1.setValue(1.0);
	// java.lang.Object methods

	public boolean equals(Object other) {
		return other instanceof Cte1;
	}

	public String toString() {
		return "Cte1";
	}
}