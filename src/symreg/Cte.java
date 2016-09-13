package symreg;

import java.util.Random;

import net.sf.jclec.exprtree.fun.AbstractPrimitive;
import net.sf.jclec.exprtree.fun.ExprTreeFunction;

public class Cte extends AbstractPrimitive {
	private static final long serialVersionUID = 8279083725033255980L;

	/**
	 * This operator return a double
	 * array as result.
	 */

	public Cte() {
		super(new Class<?>[] { Double.class }, Double.class);
	}

	// Genera una cte aleatoria entre min y max (entero)
			double min=0.0;
			double max=10.0;

			// tomar semilla del .cfg muy complicado
			int seed = 123;
			
			Random rand = new Random(123);
		
			double randomNum =  rand.nextDouble() * (max - min) + min;

			double cte=randomNum;
	
	
	@Override
	protected void evaluate(ExprTreeFunction context) {
		// Get arguments (in context stack)
		// Double arg1 = pop(context);
			
		// Push result in context stack
		push(context, cte);
														
	}

	// java.lang.Object methods

	public boolean equals(Object other) {
		return other instanceof Cte;
	}

	public String toString() {
		return String.valueOf(cte); 
	}
}
