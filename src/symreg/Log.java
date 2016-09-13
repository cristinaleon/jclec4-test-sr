package symreg;

import net.sf.jclec.exprtree.fun.AbstractPrimitive;
import net.sf.jclec.exprtree.fun.ExprTreeFunction;

public class Log extends AbstractPrimitive {
	private static final long serialVersionUID = 8279083725033255980L;

	/**
	 * This operator receives one double array as arguments and return a double
	 * array as result.
	 */

	public Log() {
		super(new Class<?>[] { Double.class }, Double.class);
	}

	@Override
	protected void evaluate(ExprTreeFunction context) {
		// Get arguments (in context stack)
		Double arg1 = pop(context);

		// Push result in context stack
		push(context, Math.cos(arg1));
														
	}

	// java.lang.Object methods

	public boolean equals(Object other) {
		return other instanceof Log;
	}

	public String toString() {
		return "L";
	}
}
